package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.example.projects.lovable_clone.error.ResourceNotFoundException;
import com.example.projects.lovable_clone.repo.UserRepository;
import com.example.projects.lovable_clone.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("user",username));
    }
}
