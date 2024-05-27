package wepik.backend.module.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wepik.backend.module.member.application.MemberService;
import wepik.backend.module.member.dto.AdminLoginResponse;
import wepik.backend.module.member.dto.LoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/admin/login")
    public AdminLoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        if (memberService.login(loginRequest)) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", loginRequest.getEmail());
            session.setMaxInactiveInterval(1800);

            return AdminLoginResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .email(loginRequest.getEmail())
                    .build();
        } else {
            return AdminLoginResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .email(loginRequest.getEmail())
                    .build();
        }
    }

    @GetMapping("/admin/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
