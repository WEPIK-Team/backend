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
        List<Question> questions = questionRepository.findQuestionByIdIn(request.getQuestionIds());
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
        List<Template> templates = templateRepository.findAll();

        return templates.stream()
                .map(template -> TemplateListResponse.fromEntity(template))
                .collect(Collectors.toList());
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
}
