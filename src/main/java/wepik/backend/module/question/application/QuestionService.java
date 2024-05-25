package wepik.backend.module.question.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dao.QuestionRepository;
import wepik.backend.module.question.dto.QuestionRequest;
import wepik.backend.module.question.dto.QuestionResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionResponse save(final QuestionRequest questionRequest) {
        Question question = questionRequest.toEntity();
        return QuestionResponse.fromEntity(questionRepository.save(question));
    }

    @Transactional(readOnly = true)
    public QuestionResponse findQuestion(final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION));

        return QuestionResponse.fromEntity(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> QuestionResponse.fromEntity(question)).collect(Collectors.toList());
    }

    public void delete(final Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION));
        questionRepository.deleteById(questionId);
    }
}
