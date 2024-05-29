package wepik.backend.module.result.application;

import static wepik.backend.module.question.dao.AnswerType.*;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.result.dao.Result;
import wepik.backend.module.result.dao.ResultRepository;
import wepik.backend.module.result.dto.ResultAnswerDto;
import wepik.backend.module.result.dto.ResultResponse;
import wepik.backend.module.result.dto.SelectQuestionDto;
import wepik.backend.module.question.dao.SelectQuestion;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultResponse loadResult(String senderUUID, String receiverUUID) {

        //sender
        Result senderResult = resultRepository.findResultBySenderId(senderUUID);
        List<ResultAnswerDto> senderResultAnswerDto = toAnswerDto(senderResult);

        //receiver
        Result receiverResult = resultRepository.findResultByReceiverId(receiverUUID);
        List<ResultAnswerDto> receiverResultAnswerDto = toAnswerDto(receiverResult);

        return ResultResponse.builder()
                .templateTitle(senderResult.getTemplate().getTitle())
                .senderAnswers(senderResultAnswerDto)
                .receiverAnswers(receiverResultAnswerDto)
                .build();
    }

    protected List<ResultAnswerDto> toAnswerDto(Result senderResult) {
        List<Answer> sortedAnswers = senderResult.getAnswers().stream()
                .sorted(Comparator.comparingInt(a -> a.getQuestion().getQuestionSequence()))
                .toList();

        List<ResultAnswerDto> resultAnswerDtos = sortedAnswers.stream()
                .map(answer -> ResultAnswerDto.builder()
                        .sequence(answer.getQuestion().getQuestionSequence())
                        .title(answer.getQuestion().getTitle())
                        .type(answer.getType())
                        .answer(answer.getContent())
                        .imgPath(answer.getQuestion().getFile() != null ? answer.getQuestion().getFile().getPath() : null)
                        .selectQuestionDtos(answer.getQuestion().getType() == SELECT ? toSelectQuestionDto(answer.getQuestion().getSelectQuestions()) : null)
                        .build())
                .toList();

        return resultAnswerDtos;
    }

    protected List<SelectQuestionDto> toSelectQuestionDto(List<SelectQuestion> selectQuestions) {
        List<SelectQuestionDto> selectQuestionDtos = new ArrayList<>();

        for (SelectQuestion selectQuestion : selectQuestions) {
            selectQuestionDtos.add(new SelectQuestionDto(selectQuestion.getSelectSequence(), selectQuestion.getTitle()));
        }

        return selectQuestionDtos;
    }
}
