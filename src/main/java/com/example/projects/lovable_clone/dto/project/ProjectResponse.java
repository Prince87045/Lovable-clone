package com.example.projects.lovable_clone.dto.project;

import com.example.projects.lovable_clone.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(Long id, String name, Instant createdAt, Instant updatedAt, UserProfileResponse owner) {
}
