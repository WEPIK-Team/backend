package wepik.backend.module.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wepik.backend.module.question.dao.SelectQuestion;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectRequest {

    @Schema(description = "Selected 제목", example = "셋 중에 더 드랍하고 싶은 강의는?")
    private String title;

    public SelectQuestion toEntity() {
        return SelectQuestion.builder()
                .title(title)
                .build();
    }
}
