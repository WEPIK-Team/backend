package wepik.backend.module.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String uploadName;

    @Column(nullable = false)
    private String storedName;

    @NotBlank
    @Column(nullable = false)
    private String path;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private FileType type;

    @NotNull
    @Column(nullable = false)
    private Long Size;
}
