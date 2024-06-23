package wepik.backend.module.result.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.result.application.AnswerService;
import wepik.backend.module.result.dto.AnswerRequest;
import wepik.backend.module.result.dto.AnswerResponse;
@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "답변하기", description = "답변하기")
    public AnswerResponse createAnswer(HttpServletRequest request, @RequestBody AnswerRequest answerRequest) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("user");

        return answerService.saveAnswer(answerRequest, member);
    }

}
