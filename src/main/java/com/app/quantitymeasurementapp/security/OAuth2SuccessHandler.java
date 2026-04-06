package com.app.quantitymeasurementapp.security;

import com.app.quantitymeasurementapp.model.User;
import com.app.quantitymeasurementapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OAuth2SuccessHandler — called automatically by Spring after Google login succeeds.
 * Instead of redirecting to a URL, it writes a JWT token directly as JSON response.
 */
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public OAuth2SuccessHandler() {
        // Do not redirect anywhere — we write the response directly
        super();
        setRedirectStrategy((request, response, url) -> {
            // No redirect — intentionally empty
        });
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Google sends "email", GitHub sends "login"
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            String login = oAuth2User.getAttribute("login");
            email = (login != null ? login : "unknown") + "@github.com";
        }

        final String username = email;

        // Auto-register user if first time logging in with Google/GitHub
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword("OAUTH2_NO_PASSWORD");
            newUser.setRole("ROLE_USER");
            return userRepository.save(newUser);
        });

        // Build UserDetails to generate JWT
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );

        String token = jwtService.generateToken(userDetails);

        // Write JWT as JSON response
        Map<String, String> body = new HashMap<>();
        body.put("token",    token);
        body.put("username", user.getUsername());
        body.put("role",     user.getRole());
        body.put("message",  "Login successful. Use this token in Authorization header.");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.getWriter().flush();
    }
}