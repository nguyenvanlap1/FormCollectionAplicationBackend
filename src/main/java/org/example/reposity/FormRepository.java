package org.example.reposity;

import org.example.entity.form.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, String> {
}
