package org.spring.springsecuritydemo.exception;

import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @Order(1)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("GlobalExceptionHandler(handleMethodArgumentNotValidException)");
        BindingResult bindingResult = e.getBindingResult(); //유효성 실패 정보 가져옴

        Map<String, Object> errorMessages = new HashMap<>();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors(); //오류 필드와 메시지를 list에 담음
        for (FieldError fieldError : fieldErrors) {
            errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiResponse<?> response = new ApiResponse<>(false, "유효성 검사 실패", errorMessages);

        return ResponseEntity.badRequest().body(response);
    }

    @Order(2)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException e) {
        log.info("GlobalExceptionHandler(handleUserNotFoundException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Order(3)
    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ApiResponse<?>> handleSamePasswordException(SamePasswordException e) {
        log.info("GlobalExceptionHandler(handleSamePasswordException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @Order(4)
    @ExceptionHandler(RoleIdNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleRoleIdNotFoundException(RoleIdNotFoundException e) {
        log.info("GlobalExceptionHandler(handleRoleIdNotFoundException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Order(5)
    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoticeNotFoundException(NoticeNotFoundException e) {
        log.info("GlobalExceptionHandler(handleNoticeNotFoundException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Order(6)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        log.info("GlobalExceptionHandler(handleRuntimeException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @Order(7)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.info("GlobalExceptionHandler(handleException)");
        ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
