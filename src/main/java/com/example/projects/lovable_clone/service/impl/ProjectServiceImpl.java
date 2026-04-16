package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.project.ProjectRequest;
import com.example.projects.lovable_clone.dto.project.ProjectResponse;
import com.example.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.example.projects.lovable_clone.entity.Project;
import com.example.projects.lovable_clone.entity.User;
import com.example.projects.lovable_clone.error.ResourceNotFoundException;
import com.example.projects.lovable_clone.mapper.ProjectMapper;
import com.example.projects.lovable_clone.repo.ProjectRepository;
import com.example.projects.lovable_clone.repo.UserRepository;
import com.example.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        return projectRepository.findAllAccessibleByUser(userId)
                .stream()
                .map(projectMapper::toProjectSummaryResponse)
                .collect(Collectors.toList());

    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        Project project = getAccessibleProjectById(id,userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .build();
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
    Project project = getAccessibleProjectById(id,userId);

    if(!project.getOwner().getId().equals(userId)){
        throw new RuntimeException("You are not allowed to delete");
    }
    project.setDeletedAt(Instant.now());
    projectRepository.save(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
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
