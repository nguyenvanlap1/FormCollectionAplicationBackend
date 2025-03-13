package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.formAnswer.FormAnswerRequest;
import org.example.dto.response.answer.FileResponse;
import org.example.dto.response.authentication.ApiResponse;
import org.example.entity.form.Form;
import org.example.service.FormAnswerService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/{formId}/formAnswers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormAnswerController {
    FormAnswerService formAnswerService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FormAnswerResponse> createFormAnser(@PathVariable String formId,
                                                    @RequestPart("data") String request,
                                                    @RequestParam HashMap<String, MultipartFile> files){
        ApiResponse<FormAnswerResponse> apiResponse = new ApiResponse<>();
        try {
            // Chuyển String JSON thành object FormAnswerRequest
            ObjectMapper objectMapper = new ObjectMapper();
            FormAnswerRequest formAnswerRequest = objectMapper.readValue(request, FormAnswerRequest.class);
            apiResponse.setResult(formAnswerService.createFormAnswerResponse(formAnswerRequest, files, formId));
        } catch (Exception e) {
            apiResponse.setMessage("Lỗi khi xử lý dữ liệu: " + e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<FormAnswerResponse>> getAllAnswers(@PathVariable String formId){
        ApiResponse<List<FormAnswerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(formAnswerService.getAllAnswersResponse(formId));
        return apiResponse;
    }

    @GetMapping("/download/{answerId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String answerId) {
        FileResponse fileResponse = formAnswerService.downloadFile(answerId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResponse.getFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, fileResponse.getFileType());

        System.out.println("Response Headers: " + headers);
        System.out.println("File Type: " + fileResponse.getFileType());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileResponse.getFileType()))
                .headers(headers)
                .body(fileResponse.getResource());
    }

}
