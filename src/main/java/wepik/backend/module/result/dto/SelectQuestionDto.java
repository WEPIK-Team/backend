package wepik.backend.module.result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectQuestionDto {

    private int sequence;

    private String title;
}
