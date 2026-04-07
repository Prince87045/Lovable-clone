package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.Enumeration.PreviewStatus;

import java.time.Instant;

public class Preview {
    Long id;
    Project project;
    String namespace;
    String podName;
    String previewUrl;

    PreviewStatus status;
    Instant startedAt;
    Instant terminatedAt;
    Instant createdAt;
}
