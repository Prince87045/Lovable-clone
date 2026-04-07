package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.Enumeration.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
