package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.auth.AuthResponse;
import com.example.projects.lovable_clone.dto.auth.LoginRequest;
import com.example.projects.lovable_clone.dto.auth.SignupRequest;
import com.example.projects.lovable_clone.service.AuthService;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse signup(SignupRequest request) {
        return null;
    }

    @Override
    public @Nullable AuthResponse login(LoginRequest request) {
        return null;
    }
}
