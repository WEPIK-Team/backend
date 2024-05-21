package wepik.backend.module.result.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wepik.backend.module.result.dao.Answer;

@Getter
@Setter
@Builder
public class ResultDto {

    private String templateTitle;

    private List<AnswerDto> senderAnswers;

    private List<AnswerDto> receiverAnswers;
}
