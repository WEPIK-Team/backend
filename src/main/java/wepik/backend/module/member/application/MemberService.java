package wepik.backend.module.member.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.member.dao.Role;
import wepik.backend.module.member.dto.JoinRequest;
import wepik.backend.module.member.dto.LoginRequest;
import wepik.backend.module.member.dao.MemberRepository;
import wepik.backend.module.member.dto.MemberInfo;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberInfo adminLogin(LoginRequest loginRequest, HttpServletRequest request) {
        Member member = memberRepository.findMemberByEmailAndRole(loginRequest.getEmail(), Role.ADMIN)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_EMAIL));

        if (passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {

            HttpSession session = request.getSession();
            session.setAttribute("user", member);
            session.setMaxInactiveInterval(1800);

            return MemberInfo.builder()
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .role(member.getRole().toString())
                    .build();
        } else {
            throw new WepikException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void join(final JoinRequest joinRequest) {
        if (memberRepository.existsByEmail(joinRequest.getEmail())) { // 이메일 중복 체크
            throw new WepikException(ErrorCode.ALREADY_REGISTERED_EMAIL);
        }
        if (memberRepository.existsByNickname(joinRequest.getNickname())){ // 닉네임 중복체크
            throw new WepikException(ErrorCode.ALREADY_REGISTERED_NICKNAME);
        }
        joinRequest.setPassword(passwordEncoder.encode(joinRequest.getPassword()));
        Member member = joinRequest.toEntity();
        memberRepository.save(member);
    }

    public String userLogin(final LoginRequest loginRequest, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_EMAIL));
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())){
            throw new WepikException(ErrorCode.INVALID_PASSWORD);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", member);
        session.setMaxInactiveInterval(1800);
        return session.getId();

    }

}
