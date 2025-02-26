package org.example.dto.response.answer;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.question.QuestionResponse;
import org.example.entity.form.FormAnswer;
import org.example.entity.question.Question;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerResponse {
    String id;

    String answer;

    QuestionResponse questionResponse;
}
