package com.blog.application.project.Exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
