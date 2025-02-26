package org.example.mapper;

import org.example.dto.request.user.UserCreationRequest;
import org.example.dto.request.user.UserUpdateRequest;
import org.example.dto.response.user.UserResponse;
import org.example.dto.response.user.UserSummary;
import org.example.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreationRequest request);

    // @Mapping(source = "firstName", target = "lastName")
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserSummary toUserSummary(User user);
}
