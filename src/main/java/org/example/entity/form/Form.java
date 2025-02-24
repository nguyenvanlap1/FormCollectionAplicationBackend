package org.example.entity.form;

import java.util.Set;

import jakarta.persistence.*;
import org.example.entity.project.Project;
import org.example.entity.question.Question;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Form {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;

   String name;
   String introduction;

   @ManyToOne
   @JoinColumn(name = "project_id")
   Project project;

   @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
   List<Question> questions;

   @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
   Set<FormAnswer> formAnswers;
}