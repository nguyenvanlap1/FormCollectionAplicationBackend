package org.example.reposity;

import org.example.entity.form.FormAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormAnswerRepository extends JpaRepository<FormAnswer, String> {
    List<FormAnswer> findByFormId(String formId);
}
