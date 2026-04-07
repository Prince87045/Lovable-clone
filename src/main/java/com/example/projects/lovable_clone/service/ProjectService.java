package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.project.ProjectRequest;
import com.example.projects.lovable_clone.dto.project.ProjectResponse;
import com.example.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects(Long userId);

    ProjectResponse getUserProjectById(Lond id, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);
}
