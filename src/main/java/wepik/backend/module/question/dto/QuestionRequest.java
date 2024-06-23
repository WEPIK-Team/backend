package wepik.backend.module.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.question.dao.AnswerType;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dao.Question.QuestionBuilder;
import wepik.backend.module.question.dao.SelectQuestion;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class QuestionRequest {

    @Schema(description = "질문 제목", example = "질문을 뭐라고 적지?")
    private String title;

    @Schema(description = "질문 타입", example = "BAR")
    private AnswerType type;

    @Schema(description = "DB에 저장되는 이미지 이름", example = "4df23447-2355-45h2-8783-7f6gd2ceb848_고양이.jpg")
    private String storedName;

    private List<SelectRequest> selectQuestions;

    public Question toEntity(File file, Member member) {
        QuestionBuilder builder = Question.builder()
                .title(title)
                .type(type)
                .member(member)
                .selectQuestions(getSelectedQuestion(selectQuestions));

        if (file != null) {
            builder.file(file);
        }
        return builder.build();
    }

    public static List<SelectQuestion> getSelectedQuestion(List<SelectRequest> requests) {
        return Optional.ofNullable(requests)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(SelectRequest::toEntity)
                .collect(Collectors.toList());
    }
}
