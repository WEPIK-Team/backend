package wepik.backend.module.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "로그인할 이메일", example = "wepik@gmail.com")
    @Email
    private String email;

    @Schema(description = "비밀번호", example = "12345678")
    @NotBlank
    private String password;
}
