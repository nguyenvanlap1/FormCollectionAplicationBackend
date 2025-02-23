package org.example.dto.response.project;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.user.UserSummary;
import org.example.dto.response.form.FormSummary;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    String id;
    String name;
    String description;
    Set<UserSummary> users;
    Set<FormSummary> forms;
}
