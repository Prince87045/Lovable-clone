package com.example.projects.lovable_clone.security;

import com.example.projects.lovable_clone.Enumeration.ProjectPermission;
import com.example.projects.lovable_clone.Enumeration.ProjectRole;
import com.example.projects.lovable_clone.repo.ProjectMemberRepository;
import com.example.projects.lovable_clone.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpressions {

    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    private boolean hasPermission(Long projectId, ProjectPermission projectPermission){
        Long userId = authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.getPermissions().contains(projectPermission))
                .orElse(false);
    }


    public boolean canViewProject(Long projectId){
        Long userId = authUtil.getCurrentUserId();
        return hasPermission(projectId, ProjectPermission.VIEW);
    }

    public boolean canEditProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean canViewMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }
}
