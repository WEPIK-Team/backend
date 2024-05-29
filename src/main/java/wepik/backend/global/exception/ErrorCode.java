package wepik.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400
    EMPTY_FILE_EXCEPTION(BAD_REQUEST, "이미지 파일이 존재하지 안습니다."),
    MIME_TYPE_EXCEPTION(BAD_REQUEST, "파일 타입이 존재하지 않습니다."),
    MIME_TYPE_MISMATCH(BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    FILE_UPLOAD_FAILED(BAD_REQUEST, "파일 업로드에 실패했습니다."),

    // 404
    NOT_FOUND_QUESTION(NOT_FOUND, "질문을 찾을 수 없습니다."),
    NOT_FOUND_TEMPLATE(NOT_FOUND, "템플릿을 찾을 수 없습니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_FOUND_FILE(NOT_FOUND, "이미지를 찾을 수 없습니다."),
    NOT_FOUND_ANSWER(NOT_FOUND, "답변을 찾을 수 없습니다.");



    private final HttpStatus httpStatus;
    private final String message;
}
