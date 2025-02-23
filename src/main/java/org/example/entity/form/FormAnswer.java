package org.example.entity.form;

import java.util.Set;

import jakarta.persistence.*;
import org.example.entity.answer.Answer;
import org.example.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormAnswer {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   User user;

   @OneToMany(mappedBy = "formAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
   List<Answer> answer;

   @ManyToOne
   @JoinColumn(name = "form_id")
   Form form;
}