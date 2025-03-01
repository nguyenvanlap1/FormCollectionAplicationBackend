package org.example.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.form.FormUpdateRequest;
import org.example.dto.response.form.FormResponse;
import org.example.service.FormService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.example.dto.response.authentication.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/projects/{projectId}/forms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormController {
    FormService formService;

    @PostMapping
    ApiResponse<FormResponse> createForm(@PathVariable String projectId,
                                         @RequestBody @Valid FormCreationRequest request) {
        ApiResponse<FormResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formService.createForm(projectId, request));
        return  apiResponse;
    }

    @PutMapping("/{formId}")
    ApiResponse<FormResponse> updateForm(@PathVariable String projectId, @PathVariable String formId,
                                         @RequestBody @Valid FormUpdateRequest request) {
        ApiResponse<FormResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formService.updateForm(projectId, formId, request));
        return  apiResponse;
    }

    @GetMapping("/{formId}")
    ApiResponse<FormResponse> GetFormById(@PathVariable String formId) {
        ApiResponse<FormResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formService.getFormById(formId));
        return apiResponse;
    }

    @DeleteMapping("/{formId}")
    ApiResponse<String> DeleteFormById(@PathVariable String formId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formService.deleteFormById(formId));
        return apiResponse;
    }
}
