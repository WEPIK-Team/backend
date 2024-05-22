package wepik.backend.module.result.application;

import static wepik.backend.module.template.dao.AnswerType.*;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.result.dao.Result;
import wepik.backend.module.result.dao.ResultRepository;
import wepik.backend.module.result.dto.AnswerDto;
import wepik.backend.module.result.dto.ResultDto;
import wepik.backend.module.result.dto.SelectQuestionDto;
import wepik.backend.module.template.dao.SelectQuestion;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultDto loadResult(String senderUUID, String receiverUUID) {

        //sender
        Result senderResult = resultRepository.findResultById(senderUUID);
        List<AnswerDto> senderAnswerDto = toAnswerDto(senderResult);

        //receiver
        Result receiverResult = resultRepository.findResultById(receiverUUID);
        List<AnswerDto> receiverAnswerDto = toAnswerDto(receiverResult);

        return ResultDto.builder()
                .templateTitle(senderResult.getTemplate().getTitle())
                .senderAnswers(senderAnswerDto)
                .receiverAnswers(receiverAnswerDto)
                .build();
    }

    protected List<AnswerDto> toAnswerDto(Result senderResult) {
        List<Answer> sortedAnswers = senderResult.getAnswers().stream()
                .sorted(Comparator.comparingInt(a -> a.getQuestion().getQuestionSequence()))
                .toList();

        List<AnswerDto> answerDtos = sortedAnswers.stream()
                .map(answer -> AnswerDto.builder()
                        .sequence(answer.getQuestion().getQuestionSequence())
                        .title(answer.getQuestion().getTitle())
                        .type(answer.getType())
                        .answer(answer.getContent())
                        .imgPath(answer.getQuestion().getFile() != null ? answer.getQuestion().getFile().getPath() : null)
                        .selectQuestionDtos(answer.getQuestion().getType() == SELECT ? toSelectQuestionDto(answer.getQuestion().getSelectQuestions()) : null)
                        .build())
                .toList();

        return answerDtos;
    }

    protected List<SelectQuestionDto> toSelectQuestionDto(List<SelectQuestion> selectQuestions) {
        List<SelectQuestionDto> selectQuestionDtos = new ArrayList<>();

        for (SelectQuestion selectQuestion : selectQuestions) {
            selectQuestionDtos.add(new SelectQuestionDto(selectQuestion.getSelectSequence(), selectQuestion.getTitle()));
        }

        return selectQuestionDtos;
    }
}
