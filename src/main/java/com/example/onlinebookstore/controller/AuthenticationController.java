package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookstore.dto.user.UserResponseDto;
import com.example.onlinebookstore.exception.RegistrationException;
import com.example.onlinebookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User authentication management",
        description = "Endpoints for managing users authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user",
            description = "Endpoint for user registration")
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
