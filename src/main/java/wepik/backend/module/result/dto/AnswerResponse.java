package wepik.backend.module.result.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnswerResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String receiverId;
}
