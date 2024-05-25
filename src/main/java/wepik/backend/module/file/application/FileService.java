package wepik.backend.module.file.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import wepik.backend.global.exception.ErrorCode;
import wepik.backend.global.exception.WepikException;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.file.dao.FileRepository;
import wepik.backend.module.file.dao.FileType;
import wepik.backend.module.file.dto.FileResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final String S3_URL =  "https://wepik-s3-bucket.s3.ap-northeast-2.amazonaws.com/";
    private static final String UPLOAD_DIR = "wepik-images/files/";
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final FileRepository fileRepository;

    public FileResponse uploadImageToS3(MultipartFile image) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new WepikException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        String originalName = image.getOriginalFilename();
        String storedName = String.format("%s_%s", UUID.randomUUID(), originalName);
        long length = image.getSize();
        FileType fileType = validateFileType(image.getContentType());

        try (InputStream inputStream = image.getInputStream()){
            putObjectToS3(UPLOAD_DIR + storedName, inputStream, length);
            File file = fileRepository.save(createFile(originalName, storedName, S3_URL + UPLOAD_DIR, fileType, length));

            return FileResponse.fromEntity(file);
        } catch (IOException e) {
            throw new WepikException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private FileType validateFileType(String mimeType) {
        if (mimeType == null) {
            throw new WepikException(ErrorCode.MIME_TYPE_EXCEPTION);
        }

        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getMimeType().equalsIgnoreCase(mimeType))
                .findFirst()
                .orElseThrow(() -> new WepikException(ErrorCode.MIME_TYPE_MISMATCH));
    }

    private void putObjectToS3(String key, InputStream inputStream, long length) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, length));
    }

    private File createFile(String originalName, String storedName, String path, FileType fileType, long length) {
        return File.builder()
                .originalName(originalName)
                .storedName(storedName)
                .path(path)
                .type(fileType)
                .size(length)
                .build();
    }
}
