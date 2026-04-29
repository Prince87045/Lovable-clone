package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.auth.AuthResponse;
import com.example.projects.lovable_clone.dto.auth.LoginRequest;
import com.example.projects.lovable_clone.dto.auth.SignupRequest;
import com.example.projects.lovable_clone.entity.User;
import com.example.projects.lovable_clone.error.BadRequestException;
import com.example.projects.lovable_clone.mapper.UserMapper;
import com.example.projects.lovable_clone.repo.UserRepository;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("User already exists with username" + request.username());
        });
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token,userMapper.toUserProfileResponse(user));
    }

    @Override
    public @Nullable AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password())
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token,userMapper.toUserProfileResponse(user));
    }
}
