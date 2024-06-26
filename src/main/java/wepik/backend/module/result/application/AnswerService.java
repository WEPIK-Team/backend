package wepik.backend.module.result.application;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.question.dao.QuestionRepository;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.result.dao.AnswerRepository;
import wepik.backend.module.result.dao.Result;
import wepik.backend.module.result.dao.ResultRepository;
import wepik.backend.module.result.dto.AnswerDto;
import wepik.backend.module.result.dto.AnswerRequest;
import wepik.backend.module.result.dto.AnswerResponse;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateRepository;
@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final TemplateRepository templateRepository;
    private final ResultRepository resultRepository;

    @Transactional
    public AnswerResponse saveAnswer(AnswerRequest answerRequest, Member member) {
        Template template = templateRepository.findById(answerRequest.getTemplateId())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));

        Result result = getResult(member, template);

        AnswerResponse answerResponse;

        //최조 요청
        if (answerRequest.getUuid() == null) {
            answerResponse = AnswerResponse.builder()
                    .senderId(result.getId())
                    .build();
        } else {
            answerResponse = AnswerResponse.builder()
                    .senderId(answerRequest.getUuid())
                    .receiverId(result.getId())
                    .build();
        }

        List<Answer> answers = toAnswers(answerRequest.getAnswerDtos(), result);
        result.addAnswers(answers);

        answerRepository.saveAll(answers);
        return answerResponse;
    }

    private static Result getResult(Member member, Template template) {
        Result.ResultBuilder resultBuilder = Result.builder()
                .id(UUID.randomUUID().toString())
                .template(template);

        if (member != null) {
            resultBuilder.member(member);
        }

        return resultBuilder.build();
    }


    private List<Answer> toAnswers(List<AnswerDto> answerDtos, Result result) {
        return answerDtos.stream()
                .map(answerDto -> Answer.builder()
                        .content(answerDto.getContent())
                        .type(answerDto.getType())
                        .sequence(answerDto.getSequence())
                        .question(questionRepository.findById(answerDto.getQuestionId())
                                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION)))
                        .result(result)
                        .build())
                .toList();
    }


}
