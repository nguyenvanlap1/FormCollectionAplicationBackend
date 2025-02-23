package org.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.enums.ErrorCode;


@Setter
@Getter
public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
