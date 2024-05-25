package wepik.backend.module.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wepik.backend.module.question.dao.AnswerType;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AnswerDto {

    @Schema(description = "답변 내용", example = "한강")
    private String content;

    @Schema(description = "질문에 대한 타입", example = "INPUT")
    private AnswerType type;

    @Schema(description = "질문 id", example = "1")
    private Long questionId;
}
