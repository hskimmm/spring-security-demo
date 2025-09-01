package org.spring.springsecuritydemo.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class ApiResponse<T> {
    /*
           반환할 데이터가 있는 경우 ApiResponse<?>, ApiResponse<Object>, ApiResponse<String> 등
           반환할 데이터가 없는 경우 ApiResponse<Void>
       */
    private final boolean success;
    private final String message;
    private T data; //단일 데이터
    private Map<String, Object> items; //다중 데이터

    // 다중 데이터인 경우 생성자
    public ApiResponse(boolean success, String message, Map<String, Object> items) {
        this.success = success;
        this.message = message;
        this.items = items;
    }

    // 단일 데이터가 있는 경우 생성자
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 데이터가 없는 경우 생성자
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
