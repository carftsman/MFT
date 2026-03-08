package com.dhatvibs.modules.executive.service;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;
import com.dhatvibs.modules.executive.entity.ExecutiveAttendance;

public interface ExecutiveAttendanceService {

    String markAttendance(ExecutiveAttendanceRequestDto dto, HttpSession session);

    boolean isAttendanceMarkedToday(HttpSession session); 
    
   // List<ExecutiveAttendance> getMyAttendance(HttpSession session); 
    
    List<ExecutiveAttendance> getAllExecutiveAttendance(HttpSession session); 
    
    
    //added
    List<ExecutiveAttendance> getAttendanceByNameAndDateRange(
            String executiveName,
            LocalDate startDate,
            LocalDate endDate,
            HttpSession session
    );
}