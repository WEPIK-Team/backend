package wepik.backend.module.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Long id;

    @Column(name = "upload_name", nullable = false)
    private String uploadName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "size", nullable = false)
    private Long Size;
}
