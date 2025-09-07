package org.spring.springsecuritydemo.exception;

public class RoleIdNotFoundException extends RuntimeException {
    public RoleIdNotFoundException(String message) {
        super(message);
    }
}
