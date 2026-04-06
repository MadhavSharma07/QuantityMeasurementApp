package com.app.quantitymeasurementapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtService — creates, validates, and reads JWT tokens.
 *
 * HOW JWT WORKS:
 * A JWT has 3 parts separated by dots:  header.payload.signature
 *
 * Header  — algorithm used (HS256)
 * Payload — data inside the token (username, role, expiry)
 * Signature — HMAC hash of header+payload using the secret key
 *
 * The server signs the token on login.
 * On every request the server verifies the signature — if valid, user is authenticated.
 * No database lookup needed on every request — that is why JWT is stateless.
 */
@Service
public class JwtService {

    // Secret key loaded from application.properties
    // Must be at least 256 bits (32 characters) for HS256
    @Value("${jwt.secret}")
    private String secretKey;

    // Token expiry — 24 hours in milliseconds
    @Value("${jwt.expiration}")
    private long expirationMs;

    // -------- Generate Token --------

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // -------- Validate Token --------

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // -------- Extract Username --------

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // -------- Private Helpers --------

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}