package com.app.quantitymeasurementapp.controller;

import com.app.quantitymeasurementapp.model.AuthResponse;
import com.app.quantitymeasurementapp.model.LoginRequest;
import com.app.quantitymeasurementapp.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController — public endpoints for register and login.
 * No JWT required to access these endpoints.
 *
 * POST /auth/register  → create account, returns JWT token
 * POST /auth/login     → login, returns JWT token
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register and Login endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}