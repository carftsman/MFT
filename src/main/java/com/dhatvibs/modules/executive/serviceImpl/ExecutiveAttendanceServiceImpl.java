package com.dhatvibs.modules.executive.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;
import com.dhatvibs.modules.executive.entity.ExecutiveAttendance;
import com.dhatvibs.modules.executive.repository.ExecutiveAttendanceRepository;
import com.dhatvibs.modules.executive.service.ExecutiveAttendanceService;

import jakarta.servlet.http.HttpSession;

@Service
public class ExecutiveAttendanceServiceImpl implements ExecutiveAttendanceService {

    @Autowired
    private ExecutiveAttendanceRepository repository;

    @Override
    public String markAttendance(ExecutiveAttendanceRequestDto dto, HttpSession session) {

        Long executiveId = (Long) session.getAttribute("userId");
        //String executiveName = (String) session.getAttribute("name");
        String userCode = (String) session.getAttribute("userCode");
        String executiveName = (String) session.getAttribute("executiveName");
        String teamleadName = (String) session.getAttribute("teamleadName");

        if (executiveId == null) {
            throw new RuntimeException("User not logged in");
        }

        LocalDate today = LocalDate.now();

        Optional<ExecutiveAttendance> existing =
                repository.findByExecutiveIdAndAttendanceDate(executiveId, today);

        if (existing.isPresent()) {
            return "Attendance already marked for today";
        }

        ExecutiveAttendance attendance = ExecutiveAttendance.builder()
                .executiveId(executiveId)
                .executiveName(executiveName)
                .teamleadName(teamleadName)
                .userCode(userCode)            
                .latitude(dto.getLatitude()) 
                .longitude(dto.getLongitude())
                .attendanceDate(today)
                .loginTime(LocalDateTime.now())
                .build();

        repository.save(attendance);

        return "Attendance marked successfully";
    }

    @Override
    public boolean isAttendanceMarkedToday(HttpSession session) {

        Long executiveId = (Long) session.getAttribute("userId");

        if (executiveId == null) {
            throw new RuntimeException("User not logged in");
        }

        LocalDate today = LocalDate.now();

        Optional<ExecutiveAttendance> existing =
                repository.findByExecutiveIdAndAttendanceDate(executiveId, today);

        return existing.isPresent();
    }  
    
    @Override
    public List<ExecutiveAttendance> getAllExecutiveAttendance(HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            throw new RuntimeException("User not logged in");
        }

        // Allow only ADMIN or MANAGER
        if (!role.equals("ADMIN") && !role.equals("MANAGER")) {
            throw new RuntimeException("Access Denied: Only ADMIN or MANAGER can view attendance");
        }

        return repository.findAllByOrderByAttendanceDateDesc();
    }  
    
    
	/*
	 * @Override public List<ExecutiveAttendance> getAttendanceByNameAndDateRange(
	 * String executiveName, LocalDate startDate, LocalDate endDate, HttpSession
	 * session) {
	 * 
	 * String role = (String) session.getAttribute("role");
	 * 
	 * if (role == null) { throw new RuntimeException("User not logged in"); }
	 * 
	 * if (!role.equals("ADMIN") && !role.equals("MANAGER") &&
	 * !role.equals("REPORTER")) { throw new
	 * RuntimeException("Access Denied: Only ADMIN or MANAGER or Data Analyst can view attendance"
	 * ); }
	 * 
	 * if (startDate == null || endDate == null) { throw new
	 * RuntimeException("Start date and End date are required"); }
	 * 
	 * return repository
	 * .findByExecutiveNameAndAttendanceDateBetweenOrderByAttendanceDateDesc(
	 * executiveName, startDate, endDate ); }
	 */  
    
    @Override
    public List<ExecutiveAttendance> getAttendanceByUserCodeAndDateRange(
            String userCode,
            LocalDate startDate,
            LocalDate endDate,
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            throw new RuntimeException("User not logged in");
        }

        if (!role.equals("ADMIN") && 
            !role.equals("MANAGER") && 
            !role.equals("REPORTER") &&
            !role.equals("TEAMLEAD")) {
        	
        	

            throw new RuntimeException(
                "Access Denied: Only ADMIN, MANAGER or REPORTER can view attendance"
            );
        }

        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and End date are required");
        }

        return repository
                .findByUserCodeAndAttendanceDateBetweenOrderByAttendanceDateDesc(
                        userCode,
                        startDate,
                        endDate
                );
    }
}