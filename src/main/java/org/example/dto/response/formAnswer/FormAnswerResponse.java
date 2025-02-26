package org.example.dto.response.formAnswer;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.response.answer.AnswerResponse;
import org.example.dto.response.form.FormSummary;
import org.example.dto.response.user.UserProjectRoleResponse;
import org.example.dto.response.user.UserSummary;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormAnswerResponse {
    String id;
    UserSummary userSummary;
    List<AnswerResponse> answerResponses;
    FormSummary formSummary;
}
