package com.dhatvibs.modules.executive.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;
import com.dhatvibs.modules.executive.entity.ExecutiveAttendance;
import com.dhatvibs.modules.executive.repository.ExecutiveAttendanceRepository;
import com.dhatvibs.modules.executive.service.ExecutiveAttendanceService;

@Service
public class ExecutiveAttendanceServiceImpl implements ExecutiveAttendanceService {

    @Autowired
    private ExecutiveAttendanceRepository repository;

    @Autowired
    private HttpSession session;

    @Override
    public String markAttendance(ExecutiveAttendanceRequestDto dto) {

        String executiveName = (String) session.getAttribute("username");
        String teamleadName = (String) session.getAttribute("teamleadName");

        if (executiveName == null) {
            throw new RuntimeException("User not logged in");
        }

        LocalDate today = LocalDate.now();

        // Check already marked
        boolean exists = repository
                .findByExecutiveNameAndAttendanceDate(executiveName, today)
                .isPresent();

        if (exists) {
            return "Attendance already marked today";
        }

        ExecutiveAttendance attendance = ExecutiveAttendance.builder()
                .executiveName(executiveName)
                .teamleadName(teamleadName)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .attendanceDate(today)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(attendance);

        return "Attendance marked successfully";
    }

    @Override
    public boolean isAttendanceMarkedToday() {

        String executiveName = (String) session.getAttribute("username");

        if (executiveName == null) {
            throw new RuntimeException("User not logged in");
        }

        return repository
                .findByExecutiveNameAndAttendanceDate(executiveName, LocalDate.now())
                .isPresent();
    }
}