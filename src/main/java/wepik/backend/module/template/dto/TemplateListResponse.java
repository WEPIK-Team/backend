package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dto.QuestionResponse;
import wepik.backend.module.template.dao.Template;

@Data
@Builder
@AllArgsConstructor
public class TemplateListResponse {

    @Schema(description = "템플릿 id", example = "1")
    private Long id;

    @Schema(description = "템플릿 제목", example = "연인이랑 가고싶은 장소")
    private String title;

    @Schema(description = "사용된 횟수", example = "23")
    private int useCount;

    @Schema(description = "템플릿 썸네일", example = "아직 null")
    private File file;

    private List<String> templateTags;

    public static TemplateListResponse fromEntity(Template template) {
        return TemplateListResponse.builder()
                .id(template.getId())
                .title(template.getTitle())
                .useCount(template.getUseCount())
                //.file() TODO: 파일 추가 예정
                .templateTags(getTemplateTags(template))
                .build();
    }

    private static List<String> getTemplateTags(Template template) {
        return template.getTemplateTags().stream()
                .map(templateTag -> templateTag.getTag().getName())
                .collect(Collectors.toList());
    }
}
