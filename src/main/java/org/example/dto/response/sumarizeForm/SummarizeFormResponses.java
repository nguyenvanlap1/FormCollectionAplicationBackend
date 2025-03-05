package org.example.dto.response.sumarizeForm;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.form.FormSummary;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SummarizeFormResponses {
    FormSummary form;
    String [] questions;
    String []
}
