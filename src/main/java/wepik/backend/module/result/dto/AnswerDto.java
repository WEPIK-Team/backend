package wepik.backend.module.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wepik.backend.module.question.dao.AnswerType;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AnswerDto {

    @Schema(description = "질문 순서", example = "1")
    private int sequence;

    @Schema(description = "질문 제목", example = "내가 제일 좋아하는 데이트 장소는?")
    private String title;

    @Schema(description = "질문에 대한 타입", example = "INPUT")
    private AnswerType type;

    @Schema(description = "답변 내용", example = "한강")
    private String answer;


    @Schema(description = "질문 이미지 사진", example = "http://weplik.co.kr/img/123456")
    private String imgPath;

    @Schema(description = "객관식 문항 리스트", example = "")
    private List<SelectQuestionDto> selectQuestionDtos;

}
