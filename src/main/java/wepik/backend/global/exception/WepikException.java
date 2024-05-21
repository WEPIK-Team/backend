package wepik.backend.global.exception;

import lombok.Getter;

@Getter
public class WepikException extends RuntimeException{
    private final ErrorCode errorCode;

    public WepikException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
