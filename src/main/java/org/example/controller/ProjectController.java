package org.example.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.ProjectCreationRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.project.ProjectResponse;
import org.example.entity.project.Project;
import org.example.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    ProjectService projectService;

    @PostMapping
    ApiResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectCreationRequest request){
        ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(projectService.createProject(request));
        return apiResponse;
    }
}
