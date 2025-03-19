package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.user.UserCreationRequest;
import org.example.dto.request.user.UserUpdateRequest;
import org.example.dto.response.authentication.ApiResponse;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.example.dto.response.user.UserResponse;
import org.example.entity.user.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    UserMapper userMapper;

    @Operation(summary = "Tạo người dùng", description = "API này dùng để tạo một người dùng mới.")
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @Operation(summary = "Lấy danh sách người dùng", description = "API này trả về danh sách tất cả người dùng.")
    @GetMapping
    public ApiResponse<List<UserResponse>> getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser());
        return apiResponse;
    }

    @Operation(summary = "Lấy thông tin cá nhân", description = "Trả về thông tin của người dùng đang đăng nhập.")
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng", description = "Trả về thông tin chi tiết của một người dùng dựa trên ID.")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(userId));
        return apiResponse;
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "Cập nhật thông tin người dùng dựa trên ID.")
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userId, request));
        return apiResponse;
    }

    @Operation(summary = "Xóa người dùng", description = "Xóa một người dùng khỏi hệ thống dựa trên ID.")
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User was deleted";
    }

    @GetMapping("/myFormAnswers")
    ApiResponse<List<FormAnswerResponse>> getAllMyFormAnswers(){
        ApiResponse<List<FormAnswerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllFormAnswers());
        return apiResponse;
    }
}

