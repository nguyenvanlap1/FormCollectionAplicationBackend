package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ProjectRole;
import org.example.dto.request.ProjectCreationRequest;
import org.example.dto.response.project.ProjectResponse;
import org.example.entity.project.Project;
import org.example.entity.user.User;
import org.example.entity.user.UserProject;
import org.example.mapper.ProjectMapper;
import org.example.reposity.ProjectRepository;
import org.example.reposity.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @PreAuthorize("hasRole('USER')")
    public ProjectResponse createProject(ProjectCreationRequest request) {

        Project project = projectMapper.toProject(request);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        String projectRole= ProjectRole.OWNER.name();
        UserProject userProject = UserProject.builder()
                .user(user)
                .roles(projectRole)
                .build();

        project.getUserProjects().add(userProject);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }
}
