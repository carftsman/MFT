package com.dhatvibs.modules.executive.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExecutiveAttendanceResponseDto {

    private Long userId;
    private String userName;
    private String role;

    private LocalDate attendanceDate;

    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;

    private Long workingMinutes;

    private String signInAddress;
    private String signOutAddress;
}