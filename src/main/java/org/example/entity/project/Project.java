package org.example.entity.project;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.example.entity.form.Form;
import org.example.entity.user.UserProject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;
   
   String name;
   String description;

   @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
   public Set<Form> forms = new HashSet<>();

   @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
   public Set<UserProject> userProjects = new HashSet<>();

   public Set<UserProject> getUserProjects() {
      if (userProjects == null) {
         userProjects = new HashSet<>(); // Đảm bảo không bị null
      }
      return userProjects;
   }
}