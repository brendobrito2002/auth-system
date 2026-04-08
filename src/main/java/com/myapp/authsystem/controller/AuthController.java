package com.myapp.authsystem.controller;

import com.myapp.authsystem.dto.ApiResponse;
import com.myapp.authsystem.dto.LoginRequest;
import com.myapp.authsystem.dto.RegisterRequest;
import com.myapp.authsystem.exception.InvalidCredentialsException;
import com.myapp.authsystem.service.AuthService;

import jakarta.validation.Valid;

import com.myapp.authsystem.config.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, 
                          JwtUtil jwtUtil, 
                          AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Usuário registrado com sucesso!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            String accessToken = jwtUtil.generateAccessToken(request.email());

            Map<String, String> data = new HashMap<>();
            data.put("accessToken", accessToken);
            data.put("tokenType", "Bearer");

            return ResponseEntity.ok(ApiResponse.success("Login realizado com sucesso", data));

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Email ou senha inválidos");
        }
    }
}