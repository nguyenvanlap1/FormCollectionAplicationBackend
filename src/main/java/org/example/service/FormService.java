package org.example.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.enums.ErrorCode;
import org.example.dto.enums.FormStatus;
import org.example.dto.enums.ProjectRole;
import org.example.dto.request.form.FormCreationRequest;
import org.example.dto.request.form.FormUpdateRequest;
import org.example.dto.response.form.FormResponse;
import org.example.dto.response.question.QuestionResponse;
import org.example.entity.form.FormAnswer;
import org.example.entity.project.Project;
import org.example.entity.question.CheckboxQuestion;
import org.example.entity.question.Question;
import org.example.entity.question.RadioQuestion;
import org.example.entity.question.TextQuestion;
import org.example.entity.user.User;
import org.example.entity.user.UserProject;
import org.example.exception.AppException;
import org.example.mapper.FormMapper;
import org.example.mapper.QuestionMapper;
import org.example.reposity.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.entity.form.Form;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormService {
    FormMapper formMapper;
    FormRepository formRepository;
    ProjectRepository projectRepository;
    UserProjectRepository userProjectRepository;
    UserRepository userRepository;
    FormAnswerRepository formAnswerRepository;
    QuestionRepository questionRepository;

    QuestionMapper questionMapper;

    @PreAuthorize("hasRole('USER')")
    public Object summarizeForm(){

    }

    @PreAuthorize("hasRole('USER')")
    public FormResponse createForm(String projectId, FormCreationRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXISTED));

        User user = getUser();

        /* Kiểm tra quyền của người dùng*/
        if(!hasPermission(user.getId(), project.getId())){
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        Form form = formMapper.toForm(request);

        form.setProject(project);
        project.getForms().add(form);
        form.setStatus(FormStatus.UNOPENED.name());

        formRepository.save(form);
        form.getQuestions().forEach(question -> question.setForm(form));

        return formMapper.toFormResponse(formRepository.save(form));
    }

    @PreAuthorize("hasRole('USER')")
    public FormResponse getFormById(String formId) {
        User user = getUser();
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if(!hasPermission(user.getId(), form.getProject().getId())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        return formMapper.toFormResponse(form);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    public FormResponse updateForm(String projectId, String formId, FormUpdateRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXISTED));

        User user = getUser();

        /* Kiểm tra quyền của người dùng*/
        if(!hasPermission(user.getId(), project.getId())){
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if(!form.getStatus().equals(FormStatus.UNOPENED)) {
            throw new AppException(ErrorCode.FORM_OPENED);
        }

        if(!form.getProject().getId().equals(projectId)){
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        form.setName(request.getName());
        form.setIntroduction(request.getIntroduction());

        questionRepository.deleteByFormId(formId);

        request.getQuestions().forEach(question -> {
            if(question.getType().equals("TEXT")){
                TextQuestion textQuestion = questionMapper.toTextQuestion(question);
                textQuestion.setForm(form);
                form.getQuestions().add(textQuestion);
                questionRepository.save(textQuestion);
            } else if(question.getType().equals("CHECKBOX")){
                CheckboxQuestion checkBoxQuestion = questionMapper.toCheckBoxtQuestion(question);
                checkBoxQuestion.setForm(form);
                form.getQuestions().add(checkBoxQuestion);
                questionRepository.save(checkBoxQuestion);
            } else if(question.getType().equals("RADIO")){
                RadioQuestion radioQuestion = questionMapper.toRadioQuestion(question);
                radioQuestion.setForm(form);
                form.getQuestions().add(radioQuestion);
                questionRepository.save(radioQuestion);
            };
        });

        return formMapper.toFormResponse(formRepository.save(form));
    }
//    ????????? tôi tạo hàm mới rồi ??????
    /* Kiểm tra người dùng có phải là chử của dự án hay không */
//    private boolean hasPermission(String userId, String projectId){
//        if(userProjectRepository.findOwnerIdsByProjectId(projectId).contains(userId)){
//            return true;
//        } else {
//            return false;
//        }
//    }

    @PreAuthorize("hasRole('USER')")
    public String deleteFormById(String formId) {
        User user = getUser();
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if(!hasPermission(user.getId(), form.getProject().getId())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        formRepository.deleteById(form.getId());

        return "Form has been delete";
    }

    @PreAuthorize("hasRole('USER')")
    public String publishForm(String formId) {
        User user = getUser();

        Form form = formRepository.findById(formId).orElseThrow(()-> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        Project project =  form.getProject();

        if(!hasPermission(user.getId(), project.getId())){
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        form.setStatus(FormStatus.OPEN.name());

        formRepository.save(form);

        return "Form is opened";
    }

    @PreAuthorize("hasRole('USER')")
    public String closeForm(String formId) {
        User user = getUser();

        Form form = formRepository.findById(formId).orElseThrow(()-> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        Project project =  form.getProject();

        if(!hasPermission(user.getId(), project.getId())){
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        form.setStatus(FormStatus.CLOSE.name());

        formRepository.save(form);

        return "form is closed";
    }

    /* Kiểm tra người dùng có phải là chử của dự án hay không*/
    private boolean hasPermission(String userId, String projectId){
        try {
        UserProject userProject = userProjectRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

            if(ProjectRole.OWNER.name().equals(userProject.getRoles())) {
                return true;
            }

        } catch (NoSuchElementException e) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return false;
    }

    /* Lấy tên của người dùng hiện tại */
    private User getUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    /* Lấy dữ liệu từ request gán qua form */
    private void updateForm(Form form, FormUpdateRequest request){

    }
}
