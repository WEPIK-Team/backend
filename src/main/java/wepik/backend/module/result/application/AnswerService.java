package wepik.backend.module.result.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.question.dao.QuestionRepository;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.result.dao.AnswerRepository;
import wepik.backend.module.result.dao.Result;
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
    public AnswerResponse saveAnswer(AnswerRequest answerRequest) {
        Template template = templateRepository.findById(answerRequest.getTemplateId())
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_TEMPLATE));

        Result result;
        AnswerResponse answerResponse;

        //최조 요청
        if (answerRequest.getUuid() == null) {
            result = Result.builder()
                    .targetId(UUID.randomUUID().toString())
                    .sourceId(UUID.randomUUID().toString())
                    .template(template)
                    .build();

            answerResponse = AnswerResponse.builder()
                    .receiverId(result.getSourceId())
                    .build();
        } else {
            log.info("응답자 UUID={}", answerRequest.getUuid());
            String targetId = resultRepository.findTargetIdBySourceId(answerRequest.getUuid());

            result = Result.builder()
                    .targetId(answerRequest.getUuid())
                    .sourceId(targetId)
                    .template(template)
                    .build();

            answerResponse = AnswerResponse.builder()
                    .senderId(targetId)
                    .receiverId(answerRequest.getUuid())
                    .build();
        }

        List<Answer> answers = toAnswers(answerRequest.getAnswerDtos(), result);
        result.addAnswers(answers);

        answerRepository.saveAll(answers);
        return answerResponse;
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
