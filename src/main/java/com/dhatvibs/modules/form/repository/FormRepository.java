/*
 * package com.dhatvibs.modules.form.repository;
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.dhatvibs.modules.form.entity.Form; import
 * com.dhatvibs.modules.form.entity.FormTag;
 * 
 * @Repository public interface FormRepository extends JpaRepository<Form, Long>
 * {
 * 
 * List<Form> findByExecutiveId(Long executiveId);
 * 
 * List<Form> findByTagNot(FormTag tag); // For BPO loop (exclude GREEN)
 * 
 * List<Form> findByTeamleadId(Long teamleadId);
 * 
 * }
 */  


package com.dhatvibs.modules.form.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dhatvibs.modules.form.entity.Form;

import jakarta.persistence.LockModeType;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    List<Form> findByExecutiveId(Long executiveId);

    List<Form> findByTeamleadId(Long teamleadId);

    List<Form> findByAssignedBpoIdAndBpoSolvedIsNull(Long bpoId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Form f WHERE f.isAssigned = false AND f.bpoSolved IS NULL")
    List<Form> findUnassignedFormsForUpdate(Pageable pageable);
}
