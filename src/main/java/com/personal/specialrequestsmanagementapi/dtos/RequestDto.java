package com.personal.specialrequestsmanagementapi.dtos;

import com.personal.specialrequestsmanagementapi.entities.User;
import com.personal.specialrequestsmanagementapi.enums.RequestStatus;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class RequestDto {
    private Long id;
    private String name;
    private String email;
    private String type;
    private String description;
    private RequestStatus status;
    private LocalDate preferredDate;
    private Time preferredTime;
    private String feedback;
    private Long userId;
}
