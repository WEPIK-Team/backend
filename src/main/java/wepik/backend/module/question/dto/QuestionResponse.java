package wepik.backend.module.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.question.dao.AnswerType;
import wepik.backend.module.question.dao.Question;

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

    @Schema(description = "이미지 URL", example = "https://wepik-static-files4df23447-2355-45h2-8783-7f6gd2ceb848_고양이.jpg")
    private String imageURL;


    public static QuestionResponse fromEntity(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .type(question.getType())
                .imageURL(null) // TODO: 파일 만들기 전까지 임시 null
                .build();
    }
}
