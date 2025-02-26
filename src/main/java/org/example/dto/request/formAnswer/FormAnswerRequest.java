package org.example.dto.request.formAnswer;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.request.answer.AnswerRequest;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormAnswerRequest {
    List<AnswerRequest> answers;
}
