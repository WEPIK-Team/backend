package wepik.backend.module.result.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnswerRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "답변 uuid", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uuid;

    @Schema(description = "템플릿 id", example = "1")
    private Long templateId;

    @Schema(description = "답변 리스트")
    private List<AnswerDto> answerDtos;

}
