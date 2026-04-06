package com.app.quantitymeasurementapp.security;

import com.app.quantitymeasurementapp.model.AuthResponse;
import com.app.quantitymeasurementapp.model.LoginRequest;
import com.app.quantitymeasurementapp.model.User;
import com.app.quantitymeasurementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService — handles user registration and login.
 *
 * REGISTER FLOW:
 * 1. Check username not already taken
 * 2. Hash the password using BCrypt
 * 3. Save user to DB
 * 4. Return success message — NO token generated here
 *
 * LOGIN FLOW:
 * 1. AuthenticationManager verifies username + password
 * 2. If wrong → throws exception automatically (401)
 * 3. If correct → generate JWT token
 * 4. Return token to client
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // -------- Register --------

    public AuthResponse register(LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent())
            throw new RuntimeException("Username already exists: " + request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        // No token generated — user must login separately to get token
        return new AuthResponse(null, user.getUsername(), user.getRole());
    }

    // -------- Login --------

    public AuthResponse login(LoginRequest request) {
        // This throws BadCredentialsException if wrong credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new AuthResponse(token, user.getUsername(), user.getRole());
    }
}