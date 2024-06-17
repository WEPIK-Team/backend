package wepik.backend.module.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dto.QuestionResponse;
import wepik.backend.module.template.dao.Tag;
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

    @Schema(description = "삭제 여부", example = "true")
    private Boolean active;

    @Schema(description = "이미지 URL", example = "https://wepik-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/1a5de0ff-7948-4ca1-8979-15701385f778_programmers.jpeg")
    private String imageURL;

    private List<String> templateTags;

    private List<QuestionResponse> questions;

    public static TemplateResponse fromEntity(Template template) {
        File file = template.getFile();
        return TemplateResponse.builder()
                .id(template.getId())
                .title(template.getTitle())
                .useCount(template.getUseCount())
                .active(template.getActive())
                .imageURL(file != null ? file.getPath() + file.getStoredName() : null)
                .templateTags(getTemplateTags(template))
                .questions(getQuestions(template))
                .build();
    }

    public static List<String> getTemplateTags(Template template) {
        return template.getTemplateTags().stream()
                .map(templateTag -> templateTag.getTag().getName())
                .collect(Collectors.toList());
    }

    private static List<QuestionResponse> getQuestions(Template template) {
        return template.getTemplateQuestions().stream()
                .map(templateQuestion -> QuestionResponse.fromEntity(templateQuestion.getQuestion()))
                .collect(Collectors.toList());
    }

}
