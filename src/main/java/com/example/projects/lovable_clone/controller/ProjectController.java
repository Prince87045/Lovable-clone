package com.example.projects.lovable_clone.controller;

import com.example.projects.lovable_clone.dto.project.ProjectRequest;
import com.example.projects.lovable_clone.dto.project.ProjectResponse;
import com.example.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects(){
        return ResponseEntity.ok(projectService.getUserProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
    Long userId = 1L;
    return ResponseEntity.ok(projectService.getUserProjectById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request){
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest request){
        Long userId=1L;
        return ResponseEntity.ok(projectService.updateProject(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        Long userId=1L; //update later with real Spring Security
        projectService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
