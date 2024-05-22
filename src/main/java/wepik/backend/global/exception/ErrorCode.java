package wepik.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 404
    NOT_FOUND_QUESTION(NOT_FOUND, "질문을 찾을 수 없습니다."),
    NOT_FOUND_TEMPLATE(NOT_FOUND, "템플릿을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
