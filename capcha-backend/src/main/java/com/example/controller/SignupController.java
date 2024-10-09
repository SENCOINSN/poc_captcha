package com.example.controller;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponse;
import com.example.exceptions.InvalidRequestException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @RequestBody UserRequestDto request) throws InvalidRequestException, ResourceNotFoundException {
       return ResponseEntity.ok(userService.signup(request));
    }
}
