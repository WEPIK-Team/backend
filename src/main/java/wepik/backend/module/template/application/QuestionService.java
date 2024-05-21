package wepik.backend.module.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.template.dao.Question;
import wepik.backend.module.template.dao.QuestionRepository;
import wepik.backend.module.template.dto.QuestionRequest;
import wepik.backend.module.template.dto.QuestionResponse;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void save(final QuestionRequest questionRequest) {
        Question question = questionRequest.toEntity();
        questionRepository.save(question);
    }

    public QuestionResponse findQuestion(final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION));

        return QuestionResponse.fromEntity(question);
    }

    @Transactional
    public void delete(final Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new WepikException(ErrorCode.NOT_FOUND_QUESTION));
        questionRepository.deleteById(questionId);
    }
}
