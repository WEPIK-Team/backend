package wepik.backend.module.template.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public String deleteTemplate(@PathVariable Long id) {
        templateService.deleteById(id);
        return "템플릿이 정상적으로 삭제되었습니다.";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "템플릿 수정", description = "id값에 해당하는 템플릿을 수정한다.")
    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateRequest request) {
        templateService.updateTemplate(id, request);
        return "템플릿이 정상적으로 수정되었습니다.";
    }
  
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tags")
    @Operation(summary = "모든 태그 조회", description = "저장되어 있는 모든 태그를 조회한다.")
    public List<String> findAllTag() {
        return templateService.findAllTags();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tag")
    @Operation(summary = "태그 검색 기능", description = "해당 태그에 일치하는 모든 템플릿을 조회한다.")
    public List<TemplateListResponse> findTemplatesByTag(@RequestParam String tagName) {
        return templateService.findTemplatesByTag(tagName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/popular")
    @Operation(summary = "인기 템플릿 조회", description = "사용 횟수가 많은 템플릿을 우선 순위로 조회한다.")
    public List<TemplateListResponse> findTemplatesByUseCount() {
        return templateService.findTemplatesByUseCount();
    }
}
