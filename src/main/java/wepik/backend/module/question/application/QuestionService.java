package wepik.backend.module.question.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.file.dao.FileRepository;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dao.QuestionRepository;
import wepik.backend.module.question.dao.SelectQuestion;
import wepik.backend.module.question.dto.QuestionRequest;
import wepik.backend.module.question.dto.QuestionResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final FileRepository fileRepository;
    public QuestionResponse save(final QuestionRequest questionRequest) {
        File file = fileRepository.findByStoredName(questionRequest.getStoredName())
                .orElse(null);
        Question question = questionRequest.toEntity(file);

        List<SelectQuestion> selectQuestions = question.getSelectQuestions();
        for (SelectQuestion selectQuestion : selectQuestions) {
            selectQuestion.addSelectedQuestion(question);
        }
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

    public void updateQuestion(final Long questionId, final QuestionRequest request) {
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION));
        File file = fileRepository.findByStoredName(request.getStoredName())
                .orElse(null);

        findQuestion.getSelectQuestions().clear();

        List<SelectQuestion> updatedSelectQuestions = QuestionRequest.getSelectedQuestion(request.getSelectQuestions());
        for (SelectQuestion updatedSelectQuestion : updatedSelectQuestions) {
            updatedSelectQuestion.addSelectedQuestion(findQuestion);
        }
        findQuestion.update(request.getTitle(), request.getType(), updatedSelectQuestions, file);
    }
}
