package org.example.mapper;

import org.example.dto.request.answer.AnswerRequest;
import org.example.dto.response.answer.AnswerResponse;
import org.example.entity.answer.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface AnswerMapper {


    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formAnswer", ignore = true)
    @Mapping(target = "question", ignore = true)
    Answer toAnswer(AnswerRequest request);

//    AnswerResponse toAnswerResponse(Answer answer);
}
