package pl.taskownia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    //TODO: rewrite - https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/exception/CustomException.java
    //private static final long serialVersionUID = 1L; //TODO: serialVersionUID

    private final String message;
    private final HttpStatus httpStatus;
}
