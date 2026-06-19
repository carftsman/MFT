
  package com.dhatvibs.modules.form.repository;
  
import java.util.List;  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;  
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.entity.WorkflowStatus;
  
  @Repository 
  public interface FormRepository extends JpaRepository<Form, Long>
  {
  
  List<Form> findByExecutiveId(Long executiveId);
  
  List<Form> findByTagNot(FormTag tag); // For BPO loop (exclude GREEN)
  
  List<Form> findByTeamleadId(Long teamleadId);
  
  List<Form> findByBpoActionDateIsNotNull();  //added
  
  
  
  @Query("""
		    SELECT f
		    FROM Form f
		    WHERE f.bpoActionDate IS NULL
		    ORDER BY f.createdAt DESC
		""")
		List<Form> findExecutiveForms();
  
  
  
  
  @Query("""
		  SELECT f FROM Form f
		  WHERE f.tag != com.dhatvibs.modules.form.entity.FormTag.GREEN
		  AND f.assignedBpoId IS NULL
		  AND f.solved = false
		  AND f.reappearDate IS NULL
		  ORDER BY f.createdAt ASC
		  """)
		  List<Form> findNewForms(org.springframework.data.domain.Pageable pageable);
  
  //added
  @Query("""
		  SELECT f FROM Form f
		  WHERE f.assignedBpoId = :bpoId
		  AND f.solved = false
		  AND f.reappearDate IS NOT NULL
		  AND f.reappearDate <= CURRENT_TIMESTAMP
		  ORDER BY f.reappearDate ASC
		  """)
		  List<Form> findReappearForms(Long bpoId);
  
  //added for executive get solved forms
  List<Form> findByExecutiveIdAndSolvedTrue(Long executiveId); 
  
  //List<Form> findByAssignedBpoIdAndSolvedFalse(Long assignedBpoId);//added
  //List<Form> findByAssignedBpoIdAndSolvedFalseAndReappearDateIsNull(Long assignedBpoId);  //added
  List<Form> findByAssignedBpoIdAndSolvedFalseAndReappearDateIsNullAndBpoActionDateIsNull(Long assignedBpoId);
  
  List<Form> findByAssignedBpoIdAndBpoActionDateIsNotNull(Long assignedBpoId);  //added
  
  List<Form> findByAssignedBpoIdAndWorkflowStatus(
	        Long assignedBpoId,
	        WorkflowStatus workflowStatus);
 
  }
   


	