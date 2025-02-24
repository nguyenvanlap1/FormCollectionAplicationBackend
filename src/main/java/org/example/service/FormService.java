package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.response.form.FormResponse;
import org.example.dto.response.question.QuestionResponse;
import org.example.entity.project.Project;
import org.example.entity.user.User;
import org.example.entity.user.UserProject;
import org.example.exception.AppException;
import org.example.mapper.FormMapper;
import org.example.reposity.ProjectRepository;
import org.example.reposity.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.entity.form.Form;
import java.util.List;
import org.example.reposity.UserProjectRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormService {
    FormMapper formMapper;
    org.example.repository.FormRepository formRepository;
    ProjectRepository projectRepository;
    UserProjectRepository userProjectRepository;
    UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    public FormResponse creatForm(FormCreationRequest request) {
        Form form = formMapper.toForm(request);
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXISTED));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        log.info("UserProject: {}", userProjectRepository.findByUserIdAndProjectId(user.getId(), request.getProjectId()));
        log.info("User ID: {}, Project ID: {}", user.getId(), request.getProjectId());

        UserProject userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.FORBIDDEN));

        if (!userProject.getRoles().contains("OWNER")) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        form.setProject(project);
        project.getForms().add(form);

        return formMapper.toFormResponse(formRepository.save(form));
    }
}
