package com.personal.specialrequestsmanagementapi.service;

import com.personal.specialrequestsmanagementapi.dtos.RequestDto;
import com.personal.specialrequestsmanagementapi.entities.Request;
import com.personal.specialrequestsmanagementapi.entities.User;
import com.personal.specialrequestsmanagementapi.repositories.RequestRepository;
import com.personal.specialrequestsmanagementapi.repositories.UserRepository;
import com.personal.specialrequestsmanagementapi.response.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<RequestResponse> getAllRequests() {
        try{
            List<Request> requests = requestRepository.findAll();

            List<RequestDto> requestList = requests.stream().map(request -> {
                RequestDto requestDto = new RequestDto();
                requestDto.setId(request.getId());
                requestDto.setName(request.getName());
                requestDto.setEmail(request.getEmail());
                requestDto.setType(request.getType());
                requestDto.setDescription(request.getDescription());
                requestDto.setStatus(request.getStatus());
                requestDto.setPreferredDate(request.getPreferredDate());
                requestDto.setPreferredTime(request.getPreferredTime());
                requestDto.setFeedback(request.getFeedback());
                requestDto.setUserId(request.getUser().getId());
                return requestDto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(new RequestResponse(200, "Success" , "Requests found Successfully", requestList),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(500, "Failure", "Error finding requests",
                    Map.of("service-error", e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RequestResponse> getRequestById(Long id) {
        try{
            Request request = requestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            RequestDto requestDto = new RequestDto();
            requestDto.setId(request.getId());
            requestDto.setName(request.getName());
            requestDto.setEmail(request.getEmail());
            requestDto.setType(request.getType());
            requestDto.setDescription(request.getDescription());
            requestDto.setStatus(request.getStatus());
            requestDto.setPreferredDate(request.getPreferredDate());
            requestDto.setPreferredTime(request.getPreferredTime());
            requestDto.setFeedback(request.getFeedback());
            requestDto.setUserId(request.getUser().getId());

            return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request found Successfully", requestDto),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(500, "Failure", "Error finding the request",
                    Map.of("service-error", "Internal server error")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RequestResponse> createRequest(RequestDto requestDto) {
        try{
            Map<String, String> errorMap = validateRequest(requestDto);
            if(!errorMap.isEmpty()){
                return new ResponseEntity<>(new RequestResponse(400, "Failure", "Request validation failed", errorMap),HttpStatus.BAD_REQUEST);
            }

//            if (requestRepository.existsByEmail(requestDto.getEmail())) {
//                Request existingRequest = requestRepository.findByEmail(requestDto.getEmail());
//                requestDto.setId(existingRequest.getId());
//                requestDto.setName(requestDto.getName());
//                requestDto.setEmail(requestDto.getEmail());
//                requestDto.setType(requestDto.getType());
//                requestDto.setDescription(requestDto.getDescription());
//                requestDto.setStatus(requestDto.getStatus());
//                requestDto.setPreferredDate(requestDto.getPreferredDate());
//                requestDto.setPreferredTime(requestDto.getPreferredTime());
//                requestDto.setFeedback(requestDto.getFeedback());
//                requestDto.setUserId(requestDto.getUserId());
//                return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request already exists", requestDto),HttpStatus.OK);
//            } else {
                User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

                Request request = new Request();

                request.setName(requestDto.getName());
                request.setEmail(requestDto.getEmail());
                request.setType(requestDto.getType());
                request.setDescription(requestDto.getDescription());
                request.setStatus(requestDto.getStatus());
                request.setPreferredDate(requestDto.getPreferredDate());
                request.setPreferredTime(requestDto.getPreferredTime());
                request.setFeedback(requestDto.getFeedback());
                request.setUser(user);

                requestRepository.save(request);
                requestDto.setId(request.getId());
                return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request added successfully", requestDto),HttpStatus.CREATED);
//            }
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(500, " Failure", "Request create failed",
                    Map.of("service-error", e.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RequestResponse> updateRequest(Long id, RequestDto requestDto) {
        try {
            Map<String, String> errorMap = validateRequest(requestDto);
            if(!errorMap.isEmpty()){
                return new ResponseEntity<>(new RequestResponse(400, "Failure", "Request validation failed", errorMap),HttpStatus.BAD_REQUEST);
            }

            User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));

            request.setName(requestDto.getName());
            request.setEmail(requestDto.getEmail());
            request.setType(requestDto.getType());
            request.setDescription(requestDto.getDescription());
            request.setStatus(requestDto.getStatus());
            request.setPreferredDate(requestDto.getPreferredDate());
            request.setPreferredTime(requestDto.getPreferredTime());
            request.setFeedback(requestDto.getFeedback());
            request.setUser(user);

            requestRepository.save(request);
            requestDto.setId(request.getId());
            return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request updated successfully", requestDto),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(400, "Failure", "Request update failed",
                    Map.of("service-error", "Internal Server Error")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RequestResponse> deleteRequest(Long id) {
        try{
            if(requestRepository.existsById(id)) {
                requestRepository.deleteById(id);
            }
            return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request removed successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(400, "Failure", "Request delete failed",
                    Map.of("service-error", "Internal server error")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RequestResponse> reviewRequest(Long id, RequestDto requestDto) {
        try{
            Map<String, String> errorMap = validateRequest(requestDto);
            if(!errorMap.isEmpty()){
                return new ResponseEntity<>(new RequestResponse(400, "Failure", "Request validation failed", errorMap),HttpStatus.BAD_REQUEST);
            }

            Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));

            request.setFeedback(requestDto.getFeedback());
            request.setStatus(requestDto.getStatus());

            requestRepository.save(request);
            requestDto.setId(request.getId());

            return new ResponseEntity<>(new RequestResponse(200, "Success" , "Request reviewed successfully", requestDto),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new RequestResponse(500, "Failure", "Review Request Failed",
                    Map.of("service-error", e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> validateRequest(RequestDto requestDto) {
        Map<String, String> errorMap = new HashMap<>();
        handleMandatoryValidations(requestDto,errorMap);
        handleFormatValidations(requestDto, errorMap);
        return errorMap;
    }

    private void handleFormatValidations(RequestDto requestDto, Map<String, String> errorMap) {
        if(requestDto.getName().length()<3)
            errorMap.put("name", "Name too short");

        if(requestDto.getDescription().length()<3)
            errorMap.put("description", "Description too short");

//        if(requestDto.getFeedback().length()<3)
//            errorMap.put("feedback", "Feedback too short");

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(requestDto.getEmail()).matches())
            errorMap.put("email", "Invalid email");

        if(requestDto.getPreferredDate().isBefore(LocalDate.now()))
            errorMap.put("date", "Date cannot be in the past");

    }

    private void handleMandatoryValidations(RequestDto requestDto, Map<String, String> errorMap) {
        if(requestDto.getName() == null || requestDto.getName().isEmpty())
            errorMap.put("name", "Name is Required");
        if(requestDto.getEmail() == null || requestDto.getEmail().isEmpty())
            errorMap.put("email", "Email is Required");
        if(requestDto.getType() == null || requestDto.getType().isEmpty())
            errorMap.put("type", "Type is Required");
        if(requestDto.getDescription() == null || requestDto.getDescription().isEmpty())
            errorMap.put("description", "Description is Required");
        if(requestDto.getStatus() == null)
            errorMap.put("status", "Status is Required");
        if(requestDto.getPreferredDate() == null)
            errorMap.put("preferredDate", "Preferred Date is Required");
        if(requestDto.getPreferredTime() == null)
            errorMap.put("preferredTime", "Preferred Time is Required");
    }
}
