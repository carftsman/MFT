package com.dhatvibs.modules.teamlead.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.teamlead.dto.*;
import com.dhatvibs.modules.teamlead.service.TeamLeadService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/teamlead")
@CrossOrigin
public class TeamLeadController {

    @Autowired
    private TeamLeadService teamLeadService;

    @PostMapping("/create-executive")
    public ExecutiveResponseDto createExecutive(
            @RequestBody CreateExecutiveRequestDto request,
            HttpSession session) {

        String teamLeadCode = (String) session.getAttribute("userCode");

        if (teamLeadCode == null) {
            throw new RuntimeException("Please login first");
        }

        return teamLeadService.createExecutive(request, teamLeadCode);
    }
}
