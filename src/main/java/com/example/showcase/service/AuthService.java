package com.example.showcase.service;

import com.example.showcase.domain.user.User;
import com.example.showcase.dto.request.GoogleLoginRequest;
import com.example.showcase.dto.response.AuthResponse;
import com.example.showcase.repository.UserRepository;
import com.example.showcase.security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleOAuthService googleOAuthService;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserRepository userRepository,
            GoogleOAuthService googleOAuthService,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.googleOAuthService = googleOAuthService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthResponse authenticateWithGoogle(GoogleLoginRequest request) {
        GoogleIdToken.Payload payload = googleOAuthService.verifyGoogleToken(request.getGoogleToken());

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String googleId = payload.getSubject();
        String pictureUrl = (String) payload.get("picture");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User(name, email, googleId, pictureUrl);
                    return userRepository.save(newUser);
                });

        if (user.getGoogleId() == null) {
            throw new RuntimeException("User already exists with email/password. Please login with email.");
        }

        String jwtToken = jwtUtil.generateToken(user.getId(), user.getEmail());

        return new AuthResponse(
                jwtToken,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getProfilePictureUrl()
        );
    }
}
