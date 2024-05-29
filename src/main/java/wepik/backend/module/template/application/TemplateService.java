package wepik.backend.module.template.application;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PrimitiveIterator;
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
import wepik.backend.module.question.dto.QuestionRequest;
import wepik.backend.module.question.dto.QuestionResponse;
import wepik.backend.module.template.dao.Tag;
import wepik.backend.module.template.dao.TagRepository;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateRepository;
import wepik.backend.module.template.dao.TemplateTag;
import wepik.backend.module.template.dto.TemplateCreateRequest;
import wepik.backend.module.template.dto.TemplateListResponse;
import wepik.backend.module.template.dto.TemplateRequest;
import wepik.backend.module.template.dto.TemplateResponse;
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

    public TemplateResponse save(final TemplateCreateRequest request) {
        File file = fileRepository.findByStoredName(request.getStoredName())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_FILE));

        List<Question> questions = questionRepository.findQuestionByIdIn(request.getQuestions());
        List<Tag> tags = tagRepository.findByNameIn(request.getTags());

        Template template = Template.builder()
                .title(request.getTitle())
                .file(file)
                .questions(new ArrayList<>(questions))
                .templateTags(new ArrayList<>())
                .build();

        for (Tag tag : tags) {
            TemplateTag templateTag = TemplateTag.builder()
                    .tag(tag)
                    .template(template)
                    .build();

            template.addTemplateTag(templateTag);
        }

        Template saveTemplate = templateRepository.save(template);
        return TemplateResponse.fromEntity(saveTemplate);
    }

    @Transactional(readOnly = true)
    public TemplateResponse findTemplateById(final Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        return TemplateResponse.fromEntity(template);
    }

    @Transactional(readOnly = true)
    public List<TemplateListResponse> findTemplates() {
        List<Template> templates = templateRepository.findAll();
        return templates.stream().map(template -> TemplateListResponse.fromEntity(template)).collect(Collectors.toList());
    }

    public void deleteById(final Long templateId) {
        templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        templateRepository.deleteById(templateId);
    }

    @Transactional(readOnly = true)
    public List<String> findAllTags() {
        return tagRepository.findAll().stream().map(Tag::getName).collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<File> getQuestionFiles(final TemplateCreateRequest request) {
//        return fileRepository.findAllByStoredNameIn(
//                request.getQuestions().stream()
//                        .map(QuestionRequest::getStoredName)
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.toList())
//        );
//    }
}
