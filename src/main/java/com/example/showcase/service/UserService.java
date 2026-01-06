package com.example.showcase.service;

import com.example.showcase.domain.user.User;
import com.example.showcase.dto.request.CreateUserRequest;
import com.example.showcase.dto.response.UserResponse;
import com.example.showcase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        if(userRepository.findByPhone(request.getPhone()).isPresent()){
            throw new RuntimeException("Phone already exists");
        }

        User user = new User(request.getName(), request.getEmail(), request.getPhone());
        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone());
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone())).toList();
    }
}
