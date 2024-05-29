package wepik.backend.module.template.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    List<Tag> findByNameIn(List<String> name);
}
