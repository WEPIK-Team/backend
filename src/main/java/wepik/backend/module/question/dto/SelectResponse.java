package wepik.backend.module.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.question.dao.SelectQuestion;

@Data
@Builder
@AllArgsConstructor
public class SelectResponse {

    @Schema(description = "Selected id", example = "1")
    private Long id;

    @Schema(description = "객관식 제목", example = "셋 중에 더 드랍하고 싶은 강의는?")
    private String title;

    public static SelectResponse fromEntity (SelectQuestion selectQuestion) {
        return SelectResponse.builder()
                .id(selectQuestion.getId())
                .title(selectQuestion.getTitle())
                .build();
    }
}
