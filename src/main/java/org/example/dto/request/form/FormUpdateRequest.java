package org.example.dto.request.form;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.request.question.QuestionRequest;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormUpdateRequest {
    private String name;
    private String introduction;
    private List<QuestionRequest> questions;
}
