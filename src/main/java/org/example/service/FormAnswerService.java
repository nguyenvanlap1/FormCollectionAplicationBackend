package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.enums.FormStatus;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.formAnswer.FormAnswerRequest;
import org.example.dto.response.answer.FileResponse;
import org.example.dto.response.formAnswer.FormAnswerResponse;
import org.example.entity.answer.Answer;
import org.example.entity.form.Form;
import org.example.entity.form.FormAnswer;
import org.example.entity.question.Question;
import org.example.entity.user.User;
import org.example.exception.AppException;
import org.example.mapper.FormAnswerMapper;
import org.example.reposity.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
    AnswerRepository answerRepository;

    @PreAuthorize("hasRole('USER')")
    public FormAnswerResponse createFormAnswerResponse(FormAnswerRequest request, HashMap<String, MultipartFile> files, String formId){
        FormAnswer formAnswer = formAnswerMapper.toFormAnswer(request);

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if(form.getStatus().equals(FormStatus.UNOPENED.name())) {
            throw new AppException(ErrorCode.FORM_IS_NOT_OPENED);
        }

        if(form.getStatus().equals(FormStatus.CLOSE.name())) {
            throw new AppException(ErrorCode.FORM_IS_CLOSED);
        }

        formAnswer.setForm(form);

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

                    String fileKey = "files[" + questionId + "]";
                    if (files.containsKey(fileKey)) {
                        MultipartFile file = files.get(fileKey);
                        try {
                            answer.setFileContent(file.getBytes());
                            answer.setFileName(file.getOriginalFilename());
                            answer.setFileType(file.getContentType());
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
                        }
                    }
                });
        return formAnswerMapper.toFormAnswerResponse(formAnswerRepository.save(formAnswer));
    }

    private User getUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public List<FormAnswerResponse> getAllAnswersResponse(String formId) {
        List<FormAnswer> formAnswers = formAnswerRepository.findByFormId(formId);
        return formAnswers.stream()
                .map(formAnswerMapper::toFormAnswerResponse)
                .collect(Collectors.toList());
    }

    public FileResponse downloadFile(String answerId) {

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        return new FileResponse(
                answer.getFileName(),
                answer.getFileType(),
                new ByteArrayResource(answer.getFileContent())
        );
    }
}
