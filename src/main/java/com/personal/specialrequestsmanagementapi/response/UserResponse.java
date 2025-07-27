package com.personal.specialrequestsmanagementapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.specialrequestsmanagementapi.dtos.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseResponse {
    private UserDto data;

    public UserResponse(int statusCode, String status, String message) {
        super(statusCode, status, message);
    }

    public UserResponse(int statusCode, String status, String message, UserDto data) {
        super(statusCode, status, message);
        this.data = data;
    }

    public UserResponse(int statusCode, String status, String message, Map<String, String> error) {
        super(statusCode, status, message, error);
    }
}
