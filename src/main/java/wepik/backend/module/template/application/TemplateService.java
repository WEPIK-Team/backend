package wepik.backend.module.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.file.dao.FileRepository;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dao.QuestionRepository;
import wepik.backend.module.template.dao.*;
import wepik.backend.module.template.dto.TemplateListResponse;
import wepik.backend.module.template.dto.TemplateRequest;
import wepik.backend.module.template.dto.TemplateResponse;
import wepik.backend.module.template.dto.TemplateTagDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final FileRepository fileRepository;

    public TemplateResponse save(final TemplateRequest request) {
        File file = fileRepository.findByStoredName(request.getStoredName())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_FILE));
        List<Question> questions = findQuestionsByIdsInOrder(request.getQuestionIds());
        Template template = request.toEntity(file, questions);
        return TemplateResponse.fromEntity(templateRepository.save(template));
    }

    @Transactional(readOnly = true)
    public TemplateResponse findTemplateById(final Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        return TemplateResponse.fromEntity(template);
    }

    @Transactional(readOnly = true)
    public List<TemplateListResponse> findTemplates() {
        List<Template> templates = templateRepository.findByActiveTrue();
        return templates.stream()
                .map(template -> TemplateListResponse.fromEntity(template))
                .collect(Collectors.toList());
    }

    public void deleteById(final Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        List<TemplateQuestion> templateQuestions = template.getTemplateQuestions();
        for (TemplateQuestion templateQuestion : templateQuestions) { // 템플릿이 삭제될 때 중간테이블 데이터들 null 처리
            templateQuestion.setQuestion(null);
            templateQuestion.setTemplate(null);
        }
        template.delete();
    }

    @Transactional(readOnly = true)
    public List<String> findAllTags() {
        return tagRepository.findAllDistinctNames();
    }

    @Transactional(readOnly = true)
    public List<TemplateListResponse> findTemplatesByTag(String tagName) {
        List<Template> templates = templateRepository.findsByTemplateTagsContaining(tagName);
        return templates.stream()
                .map(template -> TemplateListResponse.fromEntity(template))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<TemplateListResponse> findTemplatesByUseCount() {
        List<Template> templates = templateRepository.findAllOrderByUseCountDesc();
        return templates.stream()
                .map(template -> TemplateListResponse.fromEntity(template))
                .collect(Collectors.toList());

    }

    public void updateTemplate(final Long templateId, final TemplateRequest request) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        File file = fileRepository.findByStoredName(request.getStoredName())
                .orElseGet(() -> template.getFile());

        List<TemplateQuestion> updatedQuestions = updateQuestion(template, request);
        List<TemplateTag> updatedTags = updateTag(template, request);
        template.update(request.getTitle(), file, updatedQuestions, updatedTags);
    }

    public void increaseCount(final Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        template.increaseUseCount();
    }

    public List<TemplateQuestion> updateQuestion(Template template, TemplateRequest request) {
        List<TemplateQuestion> templateQuestions = template.getTemplateQuestions();

        templateQuestions.clear();

        List<Long> questionIds = request.getQuestionIds();
        List<Question> questions = questionRepository.findQuestionByIdIn(questionIds);
        return questions.stream()
                .map(question -> TemplateQuestion.createTemplateQuestion(template, question))
                .collect(Collectors.toList());
    }

    public List<TemplateTag> updateTag(Template template, TemplateRequest request) {
        List<TemplateTag> templateTags = new ArrayList<>(template.getTemplateTags());
        for (TemplateTag templateTag : templateTags) {
            templateTag.setTemplate(null);
        }
        
        List<String> tags = request.getTags();
        List<TemplateTag> updatedTags = new ArrayList<>();
        for (String tagName : tags) {
            updatedTags.add(TemplateTagDto.updateTags(tagName, template));
        }
        return updatedTags;
    }

    private List<Question> findQuestionsByIdsInOrder(List<Long> ids) {
        List<Question> questions = questionRepository.findQuestionByIdIn(ids);
        return questions.stream()
                .sorted(Comparator.comparingInt(q -> ids.indexOf(q.getId())))
                .collect(Collectors.toList());
    }
}
