package com.personal.specialrequestsmanagementapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.specialrequestsmanagementapi.dtos.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse extends BaseResponse {
    private RoleDto data;

    public RoleResponse(int statusCode, String status, String message, RoleDto data) {
        super(statusCode, status, message);
        this.data = data;
    }

    public RoleResponse(int statusCode, String status, String message, Map<String, String> error) {
        super(statusCode, status, message, error);
    }
}
