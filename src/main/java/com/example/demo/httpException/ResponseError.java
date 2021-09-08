package com.example.demo.httpException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public interface ResponseError {

    @RequiredArgsConstructor
    enum BadRequest {
        METHOD_ARGUMENT_NOT_VALID(""),
        BAD_REQUEST("bad request"),
        INVALID_EMAIL("invalid email"),
        ALREADY_EXISTS_EMAIL("already exists email");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.UNAUTHORIZED, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }

    @RequiredArgsConstructor
    enum Unauthorized {
        UNAUTHORIZED("need to login");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.UNAUTHORIZED, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }

    @RequiredArgsConstructor
    enum Forbidden {
        NO_AUTHORITY("no authority");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.FORBIDDEN, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }

    @RequiredArgsConstructor
    enum NotFound {
        USER_NOT_EXISTS("user not exists"),
        POST_NOT_EXISTS("post not exists");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.NOT_FOUND, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }

    @RequiredArgsConstructor
    enum Conflict {
        ALREADY_UPDATED("already update");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.CONFLICT, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }

    @RequiredArgsConstructor
    enum InternalServerError {
        TODO_CODE_ERROR("critical error"),
        UNEXPECTED_ERROR("unexcepected error");

        private final String message;

        public ResponseException getResponseException() {
            return getResponseException(null);
        }

        public ResponseException getResponseException(String appendMessage) {
            return new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR, name(),
                    appendMessage != null ? message + "(" + appendMessage + ")" : message);
        }
    }
}