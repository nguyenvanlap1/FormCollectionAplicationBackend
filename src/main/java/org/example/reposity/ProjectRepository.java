package org.example.reposity;

import org.example.entity.project.Project;
import org.example.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    @Query("SELECT p FROM Project p JOIN p.userProjects up WHERE up.user = :user")
    List<Project> findByUserProjects_User(@Param("user") User user);
}
