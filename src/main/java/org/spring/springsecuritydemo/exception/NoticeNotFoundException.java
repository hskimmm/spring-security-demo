package org.spring.springsecuritydemo.exception;

public class NoticeNotFoundException extends RuntimeException {
    public NoticeNotFoundException(String message) {
        super(message);
    }
}
