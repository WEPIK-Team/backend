package wepik.backend.module.file.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wepik.backend.module.file.application.FileService;
import wepik.backend.module.file.dto.FileResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "File", description = "파일 업로드 API")
public class FileController {
    private final FileService fileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "파일 업로드", description = "S3에 파일 업로드를 진행한다.")
    public FileResponse uploadFile(@RequestPart(value = "image") MultipartFile image) {
        return fileService.uploadImageToS3(image);
    }
}
