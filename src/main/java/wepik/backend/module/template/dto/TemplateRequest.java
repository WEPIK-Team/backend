package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.File;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateTag;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class TemplateRequest {

    @Schema(description = "템플릿 제목", example = "연인이랑 가고싶은 장소")
    private String title;

    @Schema(description = "DB에 저장되는 이미지 이름", example = "아직 Null")
    private String storedImageName;

    private List<TemplateTagDto> tags;

    public Template toEntity() {
        return Template.builder()
                .title(title)
                // .file() TODO: 파일 추가 예정
                .templateTags(getTemplateTags())
                .build();
    }

    private List<TemplateTag> getTemplateTags() {
        return tags.stream().map(TemplateTagDto::toEntity).collect(Collectors.toList());
    }
}
