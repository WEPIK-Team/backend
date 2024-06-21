package wepik.backend.module.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectQuestionDto {

    @Schema(description = "객관식 질문 번호에 대한 내용", example = "한강 데이트")
    private String title;
}
