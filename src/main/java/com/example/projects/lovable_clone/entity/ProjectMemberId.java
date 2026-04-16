package com.example.projects.lovable_clone.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class ProjectMemberId {
    Long projectId;
    Long userId;
}
