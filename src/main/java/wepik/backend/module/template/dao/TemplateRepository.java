package wepik.backend.module.template.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepik.backend.module.question.dao.Question;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Question> findQuestionById(Long id);
}
