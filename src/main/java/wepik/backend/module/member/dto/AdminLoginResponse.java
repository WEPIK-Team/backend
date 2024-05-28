package wepik.backend.module.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class AdminLoginResponse {

    @Schema(description = "로그인 성공 여부", example = "200")
    private HttpStatus httpStatus;

    @Schema(description = "로그인한 관리자 이메일", example = "wepik@gmail.co.kr")
    private String email;
}
