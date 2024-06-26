package wepik.backend.module.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wepik.backend.module.member.application.MemberService;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.member.dto.JoinRequest;
import wepik.backend.module.member.dto.LoginRequest;
import wepik.backend.module.member.dto.MemberInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member", description = "멤버 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/admin/login")
    @Operation(summary = "관리자 로그인",description = "이메일과 비밀번호를 입력해서 로그인을 진행한다.")
    public MemberInfo adminLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return memberService.adminLogin(loginRequest, request);
    }

    @GetMapping("/admin/logout")
    @Operation(summary = "관리자 로그 아웃", description = "세션을 무효화하여 로그아웃 처리를 진행한다.")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    @Operation(summary = "회원 가입",description = "닉네임, 이메일, 비밀번호를 입력하여 회원 가입을 한다.")
    public String join(@RequestBody JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return "회원 가입이 완료되었습니다.";
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    @Operation(summary = "회원 로그인", description = "이메일과 비밀번호를 입력해서 로그인을 진행한다.")
    public String memberLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return memberService.userLogin(loginRequest, request);
    }

    @ResponseStatus
    @GetMapping("/session")
    @Operation(summary = "세션ID로 회원 정보 반환", description = "쿠키에 세션ID를 담아 요청하면 해당 세션의 회원 정보를 보내준다.")
    public MemberInfo getSessionMemberInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("user");

        return MemberInfo.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole().toString())
                .build();
    }
}
