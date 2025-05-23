package org.example.dto.request.question;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionRequest {
    private int numericalOrder;
    private String question;
    private String type;
    private List<String> options;
}

