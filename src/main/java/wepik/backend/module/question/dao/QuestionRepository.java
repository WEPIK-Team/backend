package wepik.backend.module.question.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findQuestionByIdIn(List<Long> ids);
}
