package wepik.backend.module.result.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {

    Result findResultBySenderId(String answerId);

    Result findResultByReceiverId(String receiverId);
}
