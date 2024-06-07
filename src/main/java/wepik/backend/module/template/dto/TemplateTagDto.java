package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.template.dao.Tag;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateTag;
@Data
@Builder
@AllArgsConstructor
public class TemplateTagDto {

    @Schema(description = "템플릿 태그", example = "사랑")
    private String tagName;

    private Template template;
    public static TemplateTag toEntity(String tagName) {
        Tag tag = Tag.builder().name(tagName).build();
        return TemplateTag.builder()
                .tag(tag)
                .build();
    }
    public static TemplateTag updateTags(String tagName, Template template) {
        Tag tag = Tag.builder().name(tagName).build();
        return TemplateTag.builder()
                .tag(tag)
                .template(template)
                .build();
    }
}
