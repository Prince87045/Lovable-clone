package com.example.projects.lovable_clone.mapper;

import com.example.projects.lovable_clone.dto.auth.SignupRequest;
import com.example.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.example.projects.lovable_clone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignupRequest request);
    UserProfileResponse toUserProfileResponse(User user);
}
