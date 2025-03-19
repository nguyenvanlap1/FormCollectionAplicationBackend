package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.enums.Role;
import org.example.dto.request.user.UserCreationRequest;
import org.example.dto.request.user.UserUpdateRequest;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.example.dto.response.user.UserResponse;
import org.example.entity.form.FormAnswer;
import org.example.entity.user.User;
import org.example.exception.AppException;
import org.example.mapper.FormAnswerMapper;
import org.example.mapper.UserMapper;
import org.example.reposity.FormAnswerRepository;
import org.example.reposity.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    FormAnswerRepository formAnswerRepository;
    UserMapper userMapper;
    FormAnswerMapper formAnswerMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw (new AppException(ErrorCode.USER_EXISTED));
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUser() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("In method get User by Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = getRolesFromJwt();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        System.out.println("Roles: " + roles);
        if(!roles.contains("ROLE_"+ Role.ADMIN.name()) && !name.equals(user.getUsername())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        userMapper.updateUser(user, request);
        if(!Objects.isNull(request.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    private List<String> getRolesFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            // Lấy giá trị từ claim "scope"
            String scope = jwtAuth.getToken().getClaim("scope");

            // Tách scope thành danh sách quyền
            return scope != null ? Arrays.asList(scope.split(" ")) : List.of();
        }

        return List.of();
    }

    public List<FormAnswerResponse> getAllFormAnswers() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<FormAnswer> formAnswers = formAnswerRepository.findByUserId(user.getId());
        log.info(user.getId());
        return formAnswers.stream()
                .map(formAnswerMapper::toFormAnswerResponse)
                .collect(Collectors.toList());
    }
}
