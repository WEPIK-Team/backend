package wepik.backend.module.template.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemplateQuestionRepository extends JpaRepository<TemplateQuestion, Long> {

    @Query("SELECT CASE WHEN COUNT(tq) > 0 THEN true ELSE false END FROM TemplateQuestion tq WHERE tq.question.id = :questionId")
    boolean existsByQuestionId(Long questionId);
}
