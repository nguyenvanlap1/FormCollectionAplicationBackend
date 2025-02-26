package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.formAnswer.FormAnswerRequest;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.example.entity.answer.Answer;
import org.example.entity.form.FormAnswer;
import org.example.entity.question.Question;
import org.example.entity.user.User;
import org.example.exception.AppException;
import org.example.mapper.FormAnswerMapper;
import org.example.reposity.FormAnswerRepository;
import org.example.reposity.FormRepository;
import org.example.reposity.QuestionRepository;
import org.example.reposity.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormAnswerService {
    FormAnswerRepository formAnswerRepository;
    FormAnswerMapper formAnswerMapper;
    FormRepository formRepository;
    UserRepository userRepository;
    QuestionRepository questionRepository;

    @PreAuthorize("hasRole('USER')")
    public FormAnswerResponse createFormAnswerResponse(FormAnswerRequest request, String formId){
        FormAnswer formAnswer = formAnswerMapper.toFormAnswer(request);

        formAnswer.setForm(formRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND)));

        formAnswer.setUser(getUser());

        IntStream.range(0, formAnswer.getAnswers().size())
                .forEach(index -> {
                    Answer answer = formAnswer.getAnswers().get(index);
                    String questionId = request.getAnswers().get(index).getQuestionId();

                    // Lấy question từ DB
                    Question question = questionRepository.findById(questionId)
                            .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

                    answer.setQuestion(question);

                    answer.setFormAnswer(formAnswer);
                });
        return formAnswerMapper.toFormAnswerResponse(formAnswerRepository.save(formAnswer));
    }

    private User getUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
