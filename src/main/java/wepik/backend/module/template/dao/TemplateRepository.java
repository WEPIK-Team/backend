package wepik.backend.module.template.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query("SELECT t FROM Template t " +
            "JOIN t.templateTags tt " +
            "JOIN tt.tag tg " +
            "WHERE tg.name = :tagName")
    List<Template> findsByTemplateTagsContaining(String tagName);
}
