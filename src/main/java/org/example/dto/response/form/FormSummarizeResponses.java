package org.example.dto.response.sumarizeForm;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.form.FormSummary;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SummarizeFormResponses {
    FormSummary form;
    private Map<String, Object> rowData;
}
