package com.example.dto;

public record UserRequestDto(String firstName,
                             String lastName,
                             String email,
                             String password,
                             String token) {
}
