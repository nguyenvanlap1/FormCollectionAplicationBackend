package org.example.entity.answer;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.example.entity.form.FormAnswer;
import org.example.entity.question.Question;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   String id;

   String answer;

   @ManyToOne
   @JoinColumn(name = "form_answer_id")
   FormAnswer formAnswer;

   @ManyToOne
   @JoinColumn(name = "question_id")
   Question question;
}