package com.example.showcase.dto.request;

public class CreateUserRequest {

    private String name;
    private String email;
    private String phone;

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }
}
