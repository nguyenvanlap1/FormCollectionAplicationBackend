package org.example.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.project.Project;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProject {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;

   String roles;

   @ManyToOne
   @JoinColumn(name = "user_id")
   User user;

   @ManyToOne
   @JoinColumn(name = "ProjectId")
   Project project;

}