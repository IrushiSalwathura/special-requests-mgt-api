package com.personal.specialrequestsmanagementapi.service;

import com.personal.specialrequestsmanagementapi.dtos.UserDto;
import com.personal.specialrequestsmanagementapi.entities.User;
import com.personal.specialrequestsmanagementapi.repositories.UserRepository;
import com.personal.specialrequestsmanagementapi.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserResponse> userLogin(UserDto userDto) {
       try{
           Map<String, String> errorMap = validateUser(userDto);
           if(!errorMap.isEmpty()){
               return new ResponseEntity<>(new UserResponse( 400, "Failure", "User Validation Failed", errorMap),HttpStatus.BAD_REQUEST);
           }
           User user = userRepository.findByEmail(userDto.getEmail());
           if (user != null) {
               if(userDto.getEmail().equals(user.getEmail()) && userDto.getPassword().equals(user.getPassword())){
                   userDto.setId(user.getId());
                   userDto.setName(user.getName());
                   userDto.setEmail(user.getEmail());
                   userDto.setRoleId(user.getRole().getId());

                   return new ResponseEntity<>(new UserResponse(200, "Success", "User logged in successfully", userDto), HttpStatus.OK);
               } else {
                   return new ResponseEntity<>(new UserResponse(400, "Failure", "Username or password is incorrect"), HttpStatus.BAD_REQUEST);
               }
           } else{
               return new ResponseEntity<>(new UserResponse(400, "Failure", "User not found"), HttpStatus.BAD_REQUEST);
           }

       } catch (Exception e){
           return new ResponseEntity<>(new UserResponse(400, "Failure", "User login failed",
                   Map.of("service-error", "Internal Server Error")), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    private Map<String, String> validateUser(UserDto userDto) {
        Map<String, String> errorMap = new HashMap<>();
        handleMandatoryValidations(userDto,errorMap);
        handleFormatValidations(userDto,errorMap);
        return errorMap;
    }

    private void handleMandatoryValidations(UserDto userDto, Map<String, String> errorMap) {
        if(userDto.getEmail() == null || userDto.getEmail().isEmpty())
            errorMap.put("user-email", "Email is Required");
        if(userDto.getPassword() == null || userDto.getPassword().isEmpty())
            errorMap.put("user-password", "Password is Required");
    }

    private void handleFormatValidations(UserDto userDto, Map<String, String> errorMap) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(userDto.getEmail()).matches()){
            errorMap.put("user-email", "Invalid Email");
        }

        if(userDto.getPassword().length() < 8)
            errorMap.put("user-password", "Password should be at least 8 characters");
    }
}
