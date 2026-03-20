package com.dhatvibs.modules.executive.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;
import com.dhatvibs.modules.executive.entity.ExecutiveAttendance;
import com.dhatvibs.modules.executive.service.ExecutiveAttendanceService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/executive/attendance")
public class ExecutiveAttendanceController {

    @Autowired
    private ExecutiveAttendanceService service;

    // API 1 - Mark Attendance
    @PostMapping("/mark")
    public String markAttendance(
            @RequestBody ExecutiveAttendanceRequestDto dto,
            HttpSession session) {

        return service.markAttendance(dto, session);
    }

    // API 2 - Check Attendance
    @GetMapping("/check")
    public boolean checkAttendance(HttpSession session) {

        return service.isAttendanceMarkedToday(session);
    }  
    
    @GetMapping("/all")
    public List<ExecutiveAttendance> getAllExecutiveAttendance(HttpSession session) {
        return service.getAllExecutiveAttendance(session);
    }  
    
	/*
	 * @GetMapping("/{executiveName}") public List<ExecutiveAttendance>
	 * getAttendanceByNameAndDateRange(
	 * 
	 * @PathVariable String executiveName,
	 * 
	 * @RequestParam LocalDate startDate,
	 * 
	 * @RequestParam LocalDate endDate, HttpSession session) {
	 * 
	 * return service.getAttendanceByNameAndDateRange( executiveName, startDate,
	 * endDate, session ); }
	 */  
    
    @GetMapping("/{userCode}")
    public List<ExecutiveAttendance> getAttendanceByUserCodeAndDateRange(
            @PathVariable String userCode,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            HttpSession session) {

        return service.getAttendanceByUserCodeAndDateRange(
                userCode,
                startDate,
                endDate,
                session
        );
    } 
}