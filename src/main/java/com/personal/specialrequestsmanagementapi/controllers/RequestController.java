package com.personal.specialrequestsmanagementapi.controllers;

import com.personal.specialrequestsmanagementapi.dtos.RequestDto;
import com.personal.specialrequestsmanagementapi.response.RequestResponse;
import com.personal.specialrequestsmanagementapi.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<RequestResponse> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestResponse> getRequestById(@PathVariable Long id) {
        return requestService.getRequestById(id);
    }

    @PostMapping
    public ResponseEntity<RequestResponse> createRequest(@RequestBody RequestDto requestDto) {
        return requestService.createRequest(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestResponse> updateRequest(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        return requestService.updateRequest(id,requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RequestResponse> deleteRequest(@PathVariable Long id) {
        return requestService.deleteRequest(id);
    }
}
