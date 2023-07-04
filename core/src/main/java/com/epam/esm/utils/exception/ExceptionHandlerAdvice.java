package com.epam.esm.utils.exception;

import com.epam.esm.service.exception.ServiceException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            ServiceException.class
    })
    public ResponseEntity<ExceptionResponse> handleExceptions(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        String exceptionMessage = exception.getMessage();
        String exceptionCode = ((ServiceException) exception).getCode();
        HttpStatus httpStatus = ((ServiceException) exception).getHttpStatus();

        exceptionResponse.setErrorMessage(exceptionMessage);
        exceptionResponse.setErrorCode(exceptionCode);

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler({
            PropertyReferenceException.class
    })
    public final ResponseEntity<ExceptionResponse> handleBadSortPropertyException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(exception.getMessage());
        exceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
/*    @ExceptionHandler({
            AuthenticationException.class
    })
    public final ResponseEntity<ExceptionResponse> handle403AuthenticatedException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(exception.getMessage() + " Invalid username or password");
        exceptionResponse.setErrorCode(HttpStatus.UNAUTHORIZED.toString());
        HttpStatus httpStatus = ((AccessDeniedException)exception).getHttpStatus();

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }*/

/*    @ExceptionHandler({
            JsonProcessingException.class
    })
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions() {
        ExceptionResponse errorResponse = new ExceptionResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }*/
}
