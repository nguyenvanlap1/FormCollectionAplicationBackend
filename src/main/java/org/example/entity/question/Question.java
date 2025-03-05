package org.example.entity.question;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.entity.answer.Answer;
import org.example.entity.form.Form;

import java.util.List;

@SuperBuilder
@Setter
@Getter
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;

   private int numericalOrder;
   private String question;
   private String type;

   @ManyToOne
   @JoinColumn(name = "form_id")
   Form form;

   @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Answer> answers;
}
