package org.example.mapper;

import org.example.dto.enums.QuestionType;
import org.example.dto.request.question.QuestionRequest;
import org.example.dto.response.question.QuestionResponse;
import org.example.entity.question.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.File;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    TextQuestion toTextQuestion(QuestionRequest object);
    RadioQuestion toRadioQuestion(QuestionRequest object);
    CheckboxQuestion toCheckBoxtQuestion(QuestionRequest object);
    FileQuestion toFileQuestion(QuestionRequest object);

    @Mapping(target = "options", ignore = true)
    @Mapping(target = "userAnswer", ignore = true)
    QuestionResponse toQuestionResponse(TextQuestion question);

    @Mapping(target = "options", ignore = true)
    @Mapping(target = "userAnswer", ignore = true)
    QuestionResponse toQuestionResponse(FileQuestion question);

    @Mapping(target = "userAnswer", ignore = true)
    @Mapping(source = "options", target = "options")
    QuestionResponse toRadioQuestionResponse(RadioQuestion question);

    @Mapping(target = "userAnswer", ignore = true)
    @Mapping(source = "options", target = "options")
    QuestionResponse toCheckboxQuestionResponse(CheckboxQuestion question);

    default QuestionResponse mapToQuestionResponse(Question question) {
        if (question instanceof RadioQuestion) {
            return toRadioQuestionResponse((RadioQuestion) question);
        } else if (question instanceof CheckboxQuestion) {
            return toCheckboxQuestionResponse((CheckboxQuestion) question);
        } else if(question instanceof FileQuestion) {
            return toQuestionResponse((FileQuestion) question);
        } else {
            return toQuestionResponse((TextQuestion) question);
        }
    }

    // Chuyển `QuestionRequest` thành `Question`
    default Question mapToQuestion(QuestionRequest questionRequest) {
        if (questionRequest == null) return null;

        QuestionType type;
        try {
            type = QuestionType.valueOf(questionRequest.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown question type: " + questionRequest.getType());
        }

        return switch (type) {
            case RADIO -> RadioQuestion.builder()
                    .numericalOrder(questionRequest.getNumericalOrder())
                    .question(questionRequest.getQuestion())
                    .type(questionRequest.getType())
                    .options(questionRequest.getOptions())
                    .build();
            case CHECKBOX -> CheckboxQuestion.builder()
                    .numericalOrder(questionRequest.getNumericalOrder())
                    .question(questionRequest.getQuestion())
                    .type(questionRequest.getType())
                    .options(questionRequest.getOptions())
                    .build();
            case TEXT -> TextQuestion.builder()
                    .numericalOrder(questionRequest.getNumericalOrder())
                    .question(questionRequest.getQuestion())
                    .type(questionRequest.getType())
                    .build();
            case FILE -> FileQuestion.builder()
                    .numericalOrder(questionRequest.getNumericalOrder())
                    .question(questionRequest.getQuestion())
                    .type(questionRequest.getType())
                    .build();
        };
    }
}
