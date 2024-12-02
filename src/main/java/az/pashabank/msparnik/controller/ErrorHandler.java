package az.pashabank.msparnik.controller;

import az.pashabank.msparnik.model.UnknownDeviceException;
import az.pashabank.msparnik.model.dto.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnknownDeviceException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleUnknownDeviceException(UnknownDeviceException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }
}

