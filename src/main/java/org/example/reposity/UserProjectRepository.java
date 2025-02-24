package org.example.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.entity.user.UserProject;

import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, String> {
    Optional<UserProject> findByUserIdAndProjectId(String userId, String projectId);
}
