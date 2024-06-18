package wepik.backend.module.member.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.member.dao.Role;
import wepik.backend.module.member.dto.LoginRequest;
import wepik.backend.module.member.dao.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean login(LoginRequest loginRequest) {
        Member member = memberRepository.findMemberByEmailAndRole(loginRequest.getEmail(), Role.ADMIN)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_EMAIL));
        return passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());
    }

}
