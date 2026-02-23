package com.dhatvibs.modules.executive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;
import com.dhatvibs.modules.executive.service.ExecutiveAttendanceService;

@RestController
@RequestMapping("/api/executive/attendance")
public class ExecutiveAttendanceController {

    @Autowired
    private ExecutiveAttendanceService service;

    // 1️⃣ Store location
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(
            @RequestBody ExecutiveAttendanceRequestDto dto) {

        return ResponseEntity.ok(service.markAttendance(dto));
    }

    // 2️⃣ Check attendance (dashboard enable or not)
    @GetMapping("/check")
    public ResponseEntity<?> checkAttendance() {

        boolean marked = service.isAttendanceMarkedToday();

        if (marked) {
            return ResponseEntity.ok("Dashboard Enabled");
        } else {
            return ResponseEntity.status(403)
                    .body("Location permission required. Dashboard Disabled.");
        }
    }
}