package wepik.backend.module.result.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wepik.backend.module.template.dao.AnswerType;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AnswerDto {

    //질문 순서
    private int sequence;

    //질문 제목
    private String title;

    //답변 타입
    private AnswerType type;

    //답변 내용
    private String answer;

    //질문 이미지 사진
    private String imgPath;

    //객관식
    private List<SelectQuestionDto> selectQuestionDtos;

}
