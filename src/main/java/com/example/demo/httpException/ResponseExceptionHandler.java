package com.example.demo.httpException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@SuppressWarnings("unused")
public class ResponseExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ResponseException> handle(ResponseException e) {
        return new ResponseEntity<>(e, e.getHttpStatus());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ResponseException> handle(MethodArgumentNotValidException e) {
        final String message = e.getFieldError() != null
                ? e.getFieldError().getDefaultMessage() + " (" + e.getFieldError().getField() + ")"
                : e.getGlobalError() != null ? e.getGlobalError().getDefaultMessage() : e.getMessage();
        ResponseException ex = ResponseError.BadRequest.METHOD_ARGUMENT_NOT_VALID.getResponseException(message);
        return handle(ex);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ResponseException> handle(Throwable t) {
        ResponseException ex = ResponseError.InternalServerError.UNEXPECTED_ERROR.getResponseException(t.getMessage());
        return handle(ex);
    }
}