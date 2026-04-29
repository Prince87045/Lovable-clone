package com.example.projects.lovable_clone.Enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.example.projects.lovable_clone.Enumeration.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    EDITOR(VIEW,EDIT,DELETE, VIEW_MEMBERS),
    VIEWER(VIEW, VIEW_MEMBERS),
    OWNER(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS);

    ProjectRole(ProjectPermission... permissions) {
        this.permissions = Set.of(permissions);
    }

    private final Set<ProjectPermission> permissions;
}
