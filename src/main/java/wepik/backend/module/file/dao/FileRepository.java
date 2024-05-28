package wepik.backend.module.file.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByStoredName(String storedName);
    List<File> findAllByStoredNameIn(List<String> storedNames);
}
