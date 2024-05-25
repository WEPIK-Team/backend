package wepik.backend.module.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnswerResponse {

    private String target;
    private String uuid;
}
