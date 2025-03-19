package org.example.reposity;

import org.example.entity.form.FormAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormAnswerRepository extends JpaRepository<FormAnswer, String> {
    List<FormAnswer> findByFormId(String formId);
    @Query("SELECT f FROM FormAnswer f WHERE f.user.id = :userId")
    List<FormAnswer> findByUserId(@Param("userId") String userId);
}
