package wepik.backend.module.template.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wepik.backend.module.question.dto.QuestionResponse;
import wepik.backend.module.template.application.TemplateService;
import wepik.backend.module.template.dto.TemplateListResponse;
import wepik.backend.module.template.dto.TemplateRequest;
import wepik.backend.module.template.dto.TemplateResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
@Tag(name = "Template", description = "템플릿 API")
public class TemplateController {

    private final TemplateService templateService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "템플릿 생성", description = "템플릿을 생성한다")
    public TemplateResponse createTemplate(@RequestBody TemplateRequest templateRequest) {
        return templateService.save(templateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "템플릿 조회", description = "id 값에 해당하는 템플릿을 조회한다")
    public TemplateResponse findTemplate(@PathVariable Long id) {
        return templateService.findTemplateById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "템플릿 리스트 조회", description = "모든 템플릿 리스트를 조회한다")
    public List<TemplateListResponse> findAll() {
        return templateService.findTemplates();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @Operation(summary = "템플릿 삭제", description = "id 값에 해당하는 템플릿을 삭제한다")
    public void deleteTemplate(@PathVariable Long id) {
        templateService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tag")
    @Operation(summary = "모든 태그 조회", description = "저장되어 있는 모든 태그를 조회한다.")
    public List<String> findAllTag() {
        return templateService.findAllTags();
    }
}
