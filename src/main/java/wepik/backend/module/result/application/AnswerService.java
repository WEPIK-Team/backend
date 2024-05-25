package wepik.backend.module.result.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.question.dao.Question;
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
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final TemplateRepository templateRepository;

    @Transactional
    public AnswerResponse saveAnswer(AnswerRequest answerRequest) {
        Template template = templateRepository.findById(answerRequest.getTemplateId())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));

        Result result = Result.builder()
                .id(answerRequest.getUuid())
                .template(template)
                .build();

        List<Answer> answers = toAnswers(answerRequest.getAnswerDtos(), result);
        result.addAnswers(answers);

        answerRepository.saveAll(answers);

        return AnswerResponse.builder()
                .target("receiver")
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    private List<Answer> toAnswers(List<AnswerDto> answerDtos, Result result) {
        return answerDtos.stream()
                .map(answerDto -> Answer.builder()
                        .content(answerDto.getContent())
                        .type(answerDto.getType())
                        .question(questionRepository.findById(answerDto.getQuestionId())
                                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION)))
                        .result(result)
                        .build())
                .toList();
    }


}
