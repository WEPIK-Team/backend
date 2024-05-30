package wepik.backend.module.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dao.AnswerType;
import wepik.backend.module.question.dao.Question;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class QuestionResponse {

    @Schema(description = "질문 id", example = "1")
    private Long id;

    @Schema(description = "질문 제목", example = "질문을 뭐라고 적지?")
    private String title;

    @Schema(description = "질문 타입", example = "BAR")
    private AnswerType type;

    @Schema(description = "이미지 URL", example = "https://wepik-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/15ee44f4-eaad-400d-aaa1-ff38fd23df58_03445adc-ceb1-476e-ad62-ab643347a473_강아지.jpeg")
    private String imageURL;

    private List<SelectResponse> selectQuestions;


    public static QuestionResponse fromEntity(Question question) {
        File file = question.getFile();
        List<SelectResponse> selectResponses = question.getSelectQuestions().stream()
                .map(SelectResponse::fromEntity)
                .collect(Collectors.toList());
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .type(question.getType())
                .imageURL(file != null ? file.getPath() + file.getStoredName() : null)
                .selectQuestions(selectResponses)
                .build();
    }
}
