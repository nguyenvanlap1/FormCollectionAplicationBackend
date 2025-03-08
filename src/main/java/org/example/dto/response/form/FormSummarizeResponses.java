package org.example.dto.response.form;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormSummarizeResponses {
    FormSummary form;
    private List<Map<String, Object>> rowData;
}
