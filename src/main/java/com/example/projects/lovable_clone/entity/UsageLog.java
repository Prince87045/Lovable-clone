package com.example.projects.lovable_clone.entity;

import java.time.Instant;

public class UsageLog {
   Long id;
   User user;
   Project project;
   String action;
   Integer tokensUsed;
   Integer durationMs;
   String metaData; //Json of model used, prompt used
    Instant createdAt;
}
