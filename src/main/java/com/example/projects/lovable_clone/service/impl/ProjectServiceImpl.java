package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.Enumeration.ProjectRole;
import com.example.projects.lovable_clone.dto.project.ProjectRequest;
import com.example.projects.lovable_clone.dto.project.ProjectResponse;
import com.example.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.example.projects.lovable_clone.entity.Project;
import com.example.projects.lovable_clone.entity.ProjectMember;
import com.example.projects.lovable_clone.entity.ProjectMemberId;
import com.example.projects.lovable_clone.entity.User;
import com.example.projects.lovable_clone.error.ResourceNotFoundException;
import com.example.projects.lovable_clone.mapper.ProjectMapper;
import com.example.projects.lovable_clone.repo.ProjectMemberRepository;
import com.example.projects.lovable_clone.repo.ProjectRepository;
import com.example.projects.lovable_clone.repo.UserRepository;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;
    AuthUtil authUtil;

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
        return projectRepository.findAllAccessibleByUser(userId)
                .stream()
                .map(projectMapper::toProjectSummaryResponse)
                .collect(Collectors.toList());

    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectResponse getUserProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId,userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
//        User owner = userRepository.findById(userId).orElseThrow(
//                () -> new ResourceNotFoundException("User",userId.toString())
//        );
        User owner = userRepository.getReferenceById(userId);
        Project project = Project.builder()
                .name(request.name())
                .build();

        project = projectRepository.save(project);
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
            projectMemberRepository.save(projectMember);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#projectId)")
    //PreAuthorize("@security.hasPermission(#projectId, T(com.example.projects.lovable_clone.Enumeration.ProjectPermission('project:delete'))")
    public void softDelete(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId,userId);


    project.setDeletedAt(Instant.now());
    projectRepository.save(project);
    }

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = projectRepository.findAccessibleProjectById(id,userId).orElseThrow();
        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }


    //Internal Function
    public Project getAccessibleProjectById(Long projectId,Long userId){
        return projectRepository.findAccessibleProjectById(projectId,userId)
                .orElseThrow(()-> new ResourceNotFoundException("Project",projectId.toString()));
    }
}
