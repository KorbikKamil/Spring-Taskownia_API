package pl.taskownia.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    //TODO: rewrite - https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/exception/CustomException.java
    //private static final long serialVersionUID = 1L; //TODO: serialVersionUID

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
