package wepik.backend.module.template.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT DISTINCT t.name FROM Tag t")
    List<String> findAllDistinctNames();

}
