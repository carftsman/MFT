
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
  
  
 
  
  
  
//Get available form for BPO dashboard
	/*
	 * @Query(""" SELECT f FROM Form f WHERE f.tag !=
	 * com.dhatvibs.modules.form.entity.FormTag.GREEN AND ( f.assignedBpoId IS NULL
	 * OR (f.solved = false AND f.reappearDate <= CURRENT_TIMESTAMP) ) ORDER BY
	 * f.createdAt ASC """) List<Form> findAvailableFormsForBpo();
	 */
  
	/*
	 * @Query(""" SELECT f FROM Form f WHERE f.tag !=
	 * com.dhatvibs.modules.form.entity.FormTag.GREEN AND ( f.assignedBpoId IS NULL
	 * OR f.assignedBpoId = :bpoId OR (f.solved = false AND f.reappearDate <=
	 * CURRENT_TIMESTAMP) ) ORDER BY f.createdAt ASC """) List<Form>
	 * findAvailableFormsForBpo(Long bpoId);
	 */ 
  
	/*
	 * @Query(""" SELECT f FROM Form f WHERE f.tag !=
	 * com.dhatvibs.modules.form.entity.FormTag.GREEN AND ( (f.assignedBpoId IS
	 * NULL) OR (f.assignedBpoId = :bpoId AND f.bpoActionDate IS NULL) OR (f.solved
	 * = false AND f.reappearDate <= CURRENT_TIMESTAMP) ) ORDER BY f.createdAt ASC
	 * """) List<Form> findAvailableFormsForBpo(Long bpoId);
	 */ 
  
	/*
	 * @Query(""" SELECT f FROM Form f WHERE f.tag !=
	 * com.dhatvibs.modules.form.entity.FormTag.GREEN AND ( f.assignedBpoId IS NULL
	 * OR (f.assignedBpoId = :bpoId AND f.bpoActionDate IS NULL) OR (f.solved =
	 * false AND f.reappearDate IS NOT NULL AND f.reappearDate <= CURRENT_TIMESTAMP)
	 * ) ORDER BY f.createdAt ASC """) List<Form> findAvailableFormsForBpo(Long
	 * bpoId);
	 */  
  
  @Query("""
		    SELECT f FROM Form f
		    WHERE f.tag != com.dhatvibs.modules.form.entity.FormTag.GREEN
		    AND (
		            f.assignedBpoId IS NULL
		         OR (f.assignedBpoId = :bpoId AND f.bpoActionDate IS NULL)
		         OR (f.solved = false 
		             AND f.reappearDate IS NOT NULL 
		             AND f.reappearDate <= CURRENT_TIMESTAMP)
		        )
		    ORDER BY 
		        CASE 
		            WHEN f.reappearDate IS NOT NULL 
		                 AND f.reappearDate <= CURRENT_TIMESTAMP 
		            THEN 0 
		            ELSE 1 
		        END,
		        f.createdAt ASC
		""")
		List<Form> findAvailableFormsForBpo(Long bpoId);
  
  List<Form> findByAssignedBpoIdAndBpoActionDateIsNotNull(Long assignedBpoId);  //added
  
  List<Form> findByAssignedBpoIdAndWorkflowStatus(
	        Long assignedBpoId,
	        WorkflowStatus workflowStatus);
 
  }
   


	/*
	 * package com.dhatvibs.modules.form.repository;
	 * 
	 * import java.util.List;
	 * 
	 * import org.springframework.data.domain.Pageable; import
	 * org.springframework.data.jpa.repository.*; import
	 * org.springframework.stereotype.Repository;
	 * 
	 * import com.dhatvibs.modules.form.entity.Form;
	 * 
	 * import jakarta.persistence.LockModeType;
	 * 
	 * @Repository public interface FormRepository extends JpaRepository<Form, Long>
	 * {
	 * 
	 * List<Form> findByExecutiveId(Long executiveId);
	 * 
	 * List<Form> findByTeamleadId(Long teamleadId);
	 * 
	 * List<Form> findByAssignedBpoIdAndBpoSolvedIsNull(Long bpoId);
	 * 
	 * @Lock(LockModeType.PESSIMISTIC_WRITE)
	 * 
	 * @Query("SELECT f FROM Form f WHERE f.isAssigned = false AND f.bpoSolved IS NULL"
	 * ) List<Form> findUnassignedFormsForUpdate(Pageable pageable); }
	 */