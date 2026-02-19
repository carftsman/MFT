package com.dhatvibs.modules.form.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.FormTag;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    List<Form> findByExecutiveId(Long executiveId);

    List<Form> findByTagNot(FormTag tag);   // For BPO loop (exclude GREEN)
}
