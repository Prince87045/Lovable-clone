package com.example.projects.lovable_clone.entity;

import java.time.Instant;

public class ProjectFile {
    Long id;
    Project project;
    String path;
    String minioObjectKey;

    Instant createdAt;
    Instant updatedAt;
    User createdBy;
    User updatedBy;
}
