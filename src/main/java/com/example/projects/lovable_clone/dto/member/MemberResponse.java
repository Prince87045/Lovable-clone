package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.Enumeration.ProjectRole;
import com.example.projects.lovable_clone.entity.ProjectMemberId;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
