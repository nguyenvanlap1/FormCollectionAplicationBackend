package org.example.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.response.form.FormResponse;
import org.example.dto.response.project.ProjectResponse;
import org.example.mapper.FormMapper;
import org.example.service.FormService;
import org.springframework.web.bind.annotation.*;
import org.example.dto.response.authentication.ApiResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormController {
    FormService formService;

    @PostMapping
    ApiResponse<FormResponse> createForm(@RequestBody @Valid FormCreationRequest request) {
        ApiResponse<FormResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formService.creatForm(request));

        return  apiResponse;
    }
}
