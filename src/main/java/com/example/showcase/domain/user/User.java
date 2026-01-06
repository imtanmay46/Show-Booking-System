package com.example.showcase.domain.user;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.enums.AuthProvider;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    protected User(){}

    public User(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.authProvider = AuthProvider.LOCAL;
    }

    public User(String name, String email, String googleId, String profilePictureUrl){
        this.name = name;
        this.email = email;
        this.googleId = googleId;
        this.profilePictureUrl = profilePictureUrl;
        this.authProvider = AuthProvider.GOOGLE;
    }

    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getPhone(){ return phone; }
    public String getGoogleId(){ return googleId; }
    public AuthProvider getAuthProvider(){ return authProvider; }
    public String getProfilePictureUrl(){ return profilePictureUrl; }

    public void setPhone(String phone){ this.phone = phone; }
    public void setName(String name){ this.name = name; }
    public void setProfilePictureUrl(String profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl;
    }
}