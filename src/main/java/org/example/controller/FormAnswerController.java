package org.example.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.formAnswer.FormAnswerRequest;
import org.example.dto.response.authentication.ApiResponse;
import org.example.entity.form.Form;
import org.example.service.FormAnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.dto.response.formAnswer.FormAnswerResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/{formId}/formAnswers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormAnswerController {
    FormAnswerService formAnswerService;

    @PostMapping
    ApiResponse<FormAnswerResponse> createFormAnser(@PathVariable String formId, @RequestBody @Valid FormAnswerRequest request){
        ApiResponse<FormAnswerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formAnswerService.createFormAnswerResponse(request, formId));

        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<FormAnswerResponse>> getAllAnswers(@PathVariable String formId){
        ApiResponse<List<FormAnswerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formAnswerService.getAllAnswersResponse(formId));
        return apiResponse;
    }
}
