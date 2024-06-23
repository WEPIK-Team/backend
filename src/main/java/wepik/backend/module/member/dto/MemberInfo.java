package wepik.backend.module.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberInfo {

    @Schema(description = "사용자 이메일", example = "wepik@gmail.com")
    private String email;

    @Schema(description = "사용자 닉네임", example = "wepik")
    private String nickname;

    @Schema(description = "사용자 역할", example = "ADMIN")
    private String role;
}
