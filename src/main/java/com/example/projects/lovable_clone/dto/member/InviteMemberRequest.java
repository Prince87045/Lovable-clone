package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.Enumeration.ProjectRole;

public record InviteMemberRequest (
    String email,
    ProjectRole role
){
}
