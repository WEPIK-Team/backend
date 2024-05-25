package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dto.QuestionResponse;
import wepik.backend.module.template.dao.Template;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class TemplateResponse {

    @Schema(description = "템플릿 id", example = "1")
    private Long id;

    @Schema(description = "템플릿 제목", example = "연인이랑 가고싶은 장소")
    private String title;

    @Schema(description = "사용된 횟수", example = "23")
    private int useCount;

    @Schema(description = "템플릿 썸네일", example = "아직 null")
    private File file;

    private List<TemplateTagDto> templateTags;

    private List<QuestionResponse> questions;

    public static TemplateResponse fromEntity(Template template) {
        return TemplateResponse.builder()
                .id(template.getId())
                .title(template.getTitle())
                .useCount(template.getUseCount())
                //.file() TODO: 파일 추가 예정
                .templateTags(getTemplateTags(template))
                .questions(getQuestions(template))
                .build();
    }

    private static List<TemplateTagDto> getTemplateTags(Template template) {
        return template.getTemplateTags().stream()
                .map(TemplateTagDto::fromEntity)
                .collect(Collectors.toList());
    }

    private static List<QuestionResponse> getQuestions(Template template) {
        return template.getQuestions().stream()
                .map(QuestionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
