package com.dhatvibs.modules.executive.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhatvibs.modules.executive.entity.ExecutiveAttendance;

@Repository
public interface ExecutiveAttendanceRepository 
        extends JpaRepository<ExecutiveAttendance, Long> {

    Optional<ExecutiveAttendance> 
    findByExecutiveIdAndAttendanceDate(Long executiveId, LocalDate attendanceDate); 
    
	/*
	 * List<ExecutiveAttendance> findByExecutiveIdOrderByAttendanceDateDesc(Long
	 * executiveId);
	 */
    
    List<ExecutiveAttendance> findAllByOrderByAttendanceDateDesc();  
    
    
    //added
    List<ExecutiveAttendance> 
    findByExecutiveNameAndAttendanceDateBetweenOrderByAttendanceDateDesc(
            String executiveName,
            LocalDate startDate,
            LocalDate endDate
    );
}