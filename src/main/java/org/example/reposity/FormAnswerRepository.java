package org.example.reposity;

import org.example.entity.form.FormAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormAnswerRepository extends JpaRepository<FormAnswer, String> {
}
