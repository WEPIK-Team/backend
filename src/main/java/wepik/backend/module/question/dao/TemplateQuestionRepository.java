package wepik.backend.module.question.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateQuestion;

@Repository
public interface TemplateQuestionRepository extends JpaRepository<TemplateQuestion, Long> {
    List<Question> findByTemplate(Template template);
}
