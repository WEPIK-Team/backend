package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.template.dao.Tag;
import wepik.backend.module.template.dao.TemplateTag;
@Data
@Builder
@AllArgsConstructor
public class TemplateTagDto {

    @Schema(description = "템플릿 태그", example = "사랑")
    private String tagName;

    public static TemplateTag toEntity(String tagName) {
        return TemplateTag.builder()
                .tag(Tag.builder().name(tagName).build())
                .build();
    }

    public static TemplateTagDto fromEntity (TemplateTag templateTag) {
        return TemplateTagDto.builder()
                .tagName(templateTag.getTag().getName())
                .build();
    }
}
