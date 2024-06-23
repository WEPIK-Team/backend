package wepik.backend.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.member.dao.MemberRepository;

@Aspect
@Component
@RequiredArgsConstructor
public class SessionAspect {

    private final MemberRepository memberRepository;

    @Around("@annotation(RequestMember)")
    public Object aroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Member member = null;

        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) arg;

                HttpSession session = request.getSession();
                member = (Member) session.getAttribute("user");

                if (member == null) {
                    throw new WepikException(ErrorCode.NOT_FOUND_SESSION);
                }
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Member) {
                args[i] = member;
            }
        }

        return joinPoint.proceed(args);
    }
}
