package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.enums.ProjectRole;
import org.example.dto.request.project.ProjectCreationRequest;
import org.example.dto.response.project.ProjectResponse;
import org.example.entity.project.Project;
import org.example.entity.user.User;
import org.example.entity.user.UserProject;
import org.example.exception.AppException;
import org.example.mapper.ProjectMapper;
import org.example.reposity.ProjectRepository;
import org.example.reposity.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String projectRole= ProjectRole.OWNER.name();
        UserProject userProject = UserProject.builder()
                .user(user)
                .project(project)
                .roles(projectRole)
                .build();

        project.getUserProjects().add(userProject);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('USER')")
    public List<ProjectResponse> getUserProjects() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Project> projects = projectRepository.findByUserProjects_User(user);

        return projects.stream()
                .map(projectMapper::toProjectResponse)
                .collect(Collectors.toList());
    }
}
