package com.personal.specialrequestsmanagementapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.specialrequestsmanagementapi.dtos.RequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResponse extends BaseResponse {
    private List<RequestDto> data;
    private RequestDto requestData;

    public RequestResponse(int statusCode, String status, String message) {
        super(statusCode, status, message);
    }

    public RequestResponse(int statusCode, String status, String message, List<RequestDto> data) {
        super(statusCode, status, message);
        this.data = data;
    }

    public RequestResponse(int statusCode, String status, String message, RequestDto data) {
        super(statusCode, status, message);
        this.requestData = data;
    }

    public RequestResponse(int statusCode, String status, String message, Map<String, String> error) {
        super(statusCode, status, message, error);
    }
}
