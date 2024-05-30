package wepik.backend.module.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.question.dao.AnswerType;

@Data
@Builder
@AllArgsConstructor
public class AnswerDto {

    @Schema(description = "답변 내용", example = "한강")
    private String content;

    @Schema(description = "질문에 대한 타입", example = "INPUT")
    private AnswerType type;

    @Schema(description = "질문 id", example = "1")
    private Long questionId;

    @Schema(description = "답변 순서", example = "1")
    private int sequence;
}
