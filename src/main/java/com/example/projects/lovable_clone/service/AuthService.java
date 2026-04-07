package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.auth.AuthResponse;
import com.example.projects.lovable_clone.dto.auth.LoginRequest;
import com.example.projects.lovable_clone.dto.auth.SignupRequest;
import org.jspecify.annotations.Nullable;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    @Nullable AuthResponse login(LoginRequest request);
}
