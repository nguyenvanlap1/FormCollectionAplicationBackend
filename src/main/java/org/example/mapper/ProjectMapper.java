package org.example.mapper;

import org.example.dto.response.project.ProjectResponse;
import org.example.dto.response.form.FormSummary;
import org.example.dto.response.user.UserSummary;
import org.example.dto.request.ProjectCreationRequest;
import org.example.entity.project.Project;
import org.example.entity.form.Form;
import org.example.entity.user.UserProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "userProjects", ignore = true) // Sẽ gán riêng trong service
    Project toProject(ProjectCreationRequest request);

    @Mapping(target = "users", source = "userProjects", qualifiedByName = "mapUsers")
    @Mapping(target = "forms", source = "forms", qualifiedByName = "mapForms")
    ProjectResponse toProjectResponse(Project project);

    @Named("mapUsers")
    default Set<UserSummary> mapUsers(Set<UserProject> userProjects) {
        return userProjects.stream()
                .map(up -> new UserSummary(
                        up.getUser().getId(),
                        up.getUser().getFirstName(),
                        up.getUser().getLastName(),
                        up.getRoles()
                ))
                .collect(Collectors.toSet());
    }

    @Named("mapForms")
    default Set<FormSummary> mapForms(Set<Form> forms) {
        if (forms == null) {
            return new HashSet<>(); // Tránh NullPointerException
        }
        return forms.stream()
                .map(f -> new FormSummary(f.getId(), f.getName()))
                .collect(Collectors.toSet());
    }

}
