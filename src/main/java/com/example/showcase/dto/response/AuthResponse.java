package com.example.showcase.dto.response;

public class AuthResponse {
    private final String token;
    private final Long userId;
    private final String email;
    private final String name;
    private final String profilePictureUrl;

    public AuthResponse(String token, Long userId, String email, String name, String profilePictureUrl) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
}
