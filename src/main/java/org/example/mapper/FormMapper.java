package org.example.mapper;

import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.question.QuestionRequest;
import org.example.dto.response.form.FormResponse;
import org.example.dto.response.question.QuestionResponse;
import org.example.entity.form.Form;
import org.example.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FormMapper {
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "questions", source = "questions", qualifiedByName = "mapQuestions")
    Form toForm(FormCreationRequest request);

    @Mapping(source = "questions", target = "questions", qualifiedByName = "mapQuestionsResponse") // Chuyển danh sách câu hỏi
    FormResponse toFormResponse(Form form);

    @Named("mapQuestionsResponse")
    default List<QuestionResponse> mapQuestionsResponse(List<Question> questions) {
        return questions.stream()
                .map(QuestionMapper.INSTANCE::mapToQuestionResponse)
                .collect(Collectors.toList());
    }

    @Named("mapQuestions")
    default List<Question> mapQuestions(List<QuestionRequest> questionRequests) {
        if (questionRequests == null) return null;
        return questionRequests.stream()
                .map(QuestionMapper.INSTANCE::mapToQuestion) // Gọi `mapQuestion` từ `QuestionMapper`
                .collect(Collectors.toList());
    }
}
