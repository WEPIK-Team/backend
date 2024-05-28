package wepik.backend.module.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.file.dao.FileType;
@Data
@Builder
@AllArgsConstructor
public class FileResponse {

    @Schema(description = "파일 id", example = "1")
    private Long id;

    @Schema(description = "사용자가 업로드한 파일명", example = "사진")
    private String originalName;

    @Schema(description = "서버에 저장되는 파일명", example = "9b30159f-4281-40a3-ae3c-7e850dc132fc_7069124730_486616_30b35cd95fe41e7c1c5c4744bb79eb4f.jpg")
    private String storedName;

    @Schema(description = "업로드한 파일의 경로", example = "https://wepik-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/")
    private String path;

    @Schema(description = "업로드한 파일 확장자", example = "JPEG")
    private FileType type;

    @Schema(description = "업도르한 파일 사이즈", example = "236")
    private Long size; // 바이트 단위로 파일 크기 저장

    public File toEntity() {
        return File.builder()
                .originalName(originalName)
                .storedName(storedName)
                .path(path)
                .type(type)
                .size(size)
                .build();
    }

    public static FileResponse fromEntity(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .originalName(file.getOriginalName())
                .storedName(file.getStoredName())
                .path(file.getPath())
                .type(file.getType())
                .size(file.getSize())
                .build();
    }
}
