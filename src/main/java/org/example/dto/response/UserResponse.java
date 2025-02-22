package org.example.dto.response;

import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Role;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;

    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
}
