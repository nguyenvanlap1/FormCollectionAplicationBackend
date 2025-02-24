package org.example.dto.response.form;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.question.QuestionResponse;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormResponse {
    private String id;
    private String name;
    private String introduction;
    private List<QuestionResponse> questions;
}
