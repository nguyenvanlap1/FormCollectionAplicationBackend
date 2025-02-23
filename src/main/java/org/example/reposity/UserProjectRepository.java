package org.example.reposity;

import jakarta.persistence.JoinColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.entity.user.UserProject;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, String> {
}
