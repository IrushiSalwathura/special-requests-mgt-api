package com.personal.specialrequestsmanagementapi.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BaseResponse {
    private int statusCode;
    private String status;
    private String message;
    private Map<String, String> error;

    public BaseResponse(int statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    public BaseResponse(int statusCode, String status, String message, Map<String, String> error) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
