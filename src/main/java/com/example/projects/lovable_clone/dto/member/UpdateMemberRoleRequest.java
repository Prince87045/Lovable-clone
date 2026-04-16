package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.Enumeration.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
