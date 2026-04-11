package com.myapp.authsystem.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {}