package org.example.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.project.ProjectCreationRequest;
import org.example.dto.request.project.ProjectUpdateRequest;
import org.example.dto.response.authentication.ApiResponse;
import org.example.dto.response.project.ProjectResponse;
import org.example.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    ApiResponse<List<ProjectResponse>> getUserProjects() {
        ApiResponse<List<ProjectResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(projectService.getUserProjects());
        return apiResponse;
    }

    @GetMapping("/{projectId}")
    ApiResponse<ProjectResponse> getUserProject(@PathVariable String projectId){
        ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(projectService.getUserProjectById(projectId));
        return apiResponse;
    }

    @PutMapping("/{projectId}")
    ApiResponse<ProjectResponse> updateProject(@PathVariable String projectId, @RequestBody ProjectUpdateRequest request){
        ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(projectService.updateProject(projectId, request));
        return apiResponse;
    }

    @DeleteMapping("/{projectId}")
    ApiResponse<ProjectResponse> deleteProject(@PathVariable String projectId){
        projectService.deleteProject(projectId);
        ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(null);
        return apiResponse;
    }

}
