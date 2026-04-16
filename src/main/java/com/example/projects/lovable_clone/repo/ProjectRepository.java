package com.example.projects.lovable_clone.repo;

import com.example.projects.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query(
            """
                   SELECT p from Project p
                   WHERE p.deletedAt IS NULL
                   AND owner.id = :userId
                   ORDER By p.updatedAt DESC 
            """
    )
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
            SELECT p from Project p
            LEFT JOIN FETCH p.owner
            where p.id = :projectId
              AND p.deletedAt IS NULL
              AND p.owner.id = :userId
            """)
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId,
                                                @Param("userId") Long userId);
}
