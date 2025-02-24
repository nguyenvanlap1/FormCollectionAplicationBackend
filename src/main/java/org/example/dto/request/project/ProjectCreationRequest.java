package org.example.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.user.UserProject;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectCreationRequest {
    String name;
    String description;
}
