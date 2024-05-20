package wepik.backend.module.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepik.backend.module.template.dao.Question;
import wepik.backend.module.template.dao.QuestionRepository;
import wepik.backend.module.template.dto.QuestionRequest;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void save(final QuestionRequest questionRequest) {
        Question question = questionRequest.toEntity();
        questionRepository.save(question);
    }

    public Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id값에 맞는 질문이 없습니다."));
    }

    @Transactional
    public void delete(final Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id값에 맞는 질문이 없습니다."));
        questionRepository.deleteById(questionId);
    }
}
