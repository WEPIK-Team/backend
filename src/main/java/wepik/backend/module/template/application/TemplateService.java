package wepik.backend.module.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateRepository;
import wepik.backend.module.template.dao.TemplateTag;
import wepik.backend.module.template.dto.TemplateRequest;
import wepik.backend.module.template.dto.TemplateResponse;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateResponse save(final TemplateRequest request) {
        Template template = request.toEntity();

        List<TemplateTag> templateTags = template.getTemplateTags();
        for (TemplateTag templateTag : templateTags) {
            templateTag.addTemplate(template);
        }
        return TemplateResponse.fromEntity(templateRepository.save(template));
    }

    @Transactional(readOnly = true)
    public TemplateResponse findTemplateById(final Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        return TemplateResponse.fromEntity(template);
    }

    public void deleteById(final Long templateId) {
        templateRepository.findById(templateId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));
        templateRepository.deleteById(templateId);
    }
}
