package com.personal.specialrequestsmanagementapi.controllers;

import com.personal.specialrequestsmanagementapi.dtos.UserDto;
import com.personal.specialrequestsmanagementapi.response.UserResponse;
import com.personal.specialrequestsmanagementapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> userLogin(@RequestBody UserDto userDto) {
        return userService.userLogin(userDto);
    }
}
