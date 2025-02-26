package org.example.mapper;

import org.example.dto.request.answer.AnswerRequest;
import org.example.dto.request.formAnswer.FormAnswerRequest;
import org.example.dto.response.answer.AnswerResponse;
import org.example.dto.response.form.FormSummary;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.example.dto.response.question.QuestionResponse;
import org.example.dto.response.user.UserSummary;
import org.example.entity.answer.Answer;
import org.example.entity.form.Form;
import org.example.entity.form.FormAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;
import org.example.entity.user.User;

@Mapper(componentModel = "spring")
public interface FormAnswerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "form", ignore = true)
    @Mapping(target = "answers", source = "answers", qualifiedByName = "mapAnswers")
    FormAnswer toFormAnswer(FormAnswerRequest request);

    @Mapping(target = "userSummary", source = "user", qualifiedByName = "mapUserSummary")
    @Mapping(target = "answerResponses", source = "answers", qualifiedByName = "mapAnswerResponses")
    @Mapping(target = "formSummary", source = "form", qualifiedByName = "mapFormSummary")
    FormAnswerResponse toFormAnswerResponse(FormAnswer formAnswer);

    @Named("mapAnswers")
    default List<Answer> mapAnswers(List<AnswerRequest> AnswerRequest){
        return AnswerRequest.stream()
                .map(AnswerMapper.INSTANCE::toAnswer)
                .collect(Collectors.toList());
    }

    @Named("mapUserSummary")
    default UserSummary mapUserSummary(User user){
        return UserMapper.INSTANCE.toUserSummary(user);
    }

    @Named("mapAnswerResponses")
    default List<AnswerResponse> mapAnswerResponse(List<Answer> answers) {
        return answers.stream()
                .map(answer-> {
                    return AnswerResponse.builder()
                        .id(answer.getId())
                        .questionResponse(
                                QuestionMapper.INSTANCE.mapToQuestionResponse(answer.getQuestion()))
                        .answer(answer.getAnswer())
                        .build();
                }).collect(Collectors.toList());
    }

    @Named("mapFormSummary")
    default FormSummary mapFormSummary(Form form) {
        return FormSummary.builder()
                .introduction(form.getIntroduction())
                .id(form.getId())
                .name(form.getName())
                .build();
    }
}
