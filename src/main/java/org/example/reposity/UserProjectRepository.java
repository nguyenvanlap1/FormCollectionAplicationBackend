package org.example.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.entity.user.UserProject;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, String> {
    Optional<UserProject> findByUserIdAndProjectId(String userId, String projectId);

    @Query("SELECT up.user.id FROM UserProject up WHERE up.project.id = :projectId AND up.roles = 'OWNER'")
    List<String> findOwnerIdsByProjectId(@Param("projectId") String projectId);
}
