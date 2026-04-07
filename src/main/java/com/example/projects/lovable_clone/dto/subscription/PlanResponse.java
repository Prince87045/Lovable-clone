package com.example.projects.lovable_clone.dto.subscription;

public record PlanResponse(
        Long id,
        String name,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Boolean unlimitedAi, //unlimited access to LLM ignore maxTokensPerDay if true
        String price
) {
}
