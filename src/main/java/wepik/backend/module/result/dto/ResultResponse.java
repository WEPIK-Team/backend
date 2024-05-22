package wepik.backend.module.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultResponse {

    @Schema(description = "템플릿 제목", example = "연인과 주고 받을 질문 10개")
    private String templateTitle;

    @Schema(description = "질문 생성자의 답변", example = "")
    private List<AnswerDto> senderAnswers;

    @Schema(description = "질문 받은 사람의 답변", example = "")
    private List<AnswerDto> receiverAnswers;
}
