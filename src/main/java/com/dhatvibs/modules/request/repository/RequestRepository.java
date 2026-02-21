package com.dhatvibs.modules.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhatvibs.modules.form.entity.Form;

@Repository
public interface RequestRepository extends JpaRepository<Form, Long> {

    List<Form> findByResendRequestedTrueAndResendApprovedFalse();

    List<Form> findByExecutiveIdAndResendApprovedTrue(Long executiveId);
}