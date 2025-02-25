package org.example.reposity;

import jakarta.transaction.Transactional;
import org.example.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, String> {
    @Modifying
    @Query("DELETE FROM Question q WHERE q.form.id = :formId")
    void deleteByFormId(@Param("formId") String formId);
}
