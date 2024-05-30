package wepik.backend.module.result.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a WHERE a.result.targetId = :resultId ORDER BY a.sequence")
    List<Answer> findAnswerByResultIdOrderBySequence(@Param("resultId") String resultId);
}
