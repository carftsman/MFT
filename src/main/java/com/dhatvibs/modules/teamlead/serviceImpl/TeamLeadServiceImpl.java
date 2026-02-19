package com.dhatvibs.modules.teamlead.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.auth.entity.Role;
import com.dhatvibs.modules.auth.entity.User;
import com.dhatvibs.modules.auth.repository.UserRepository;
import com.dhatvibs.modules.teamlead.dto.*;
import com.dhatvibs.modules.teamlead.service.TeamLeadService;

@Service
public class TeamLeadServiceImpl implements TeamLeadService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExecutiveResponseDto createExecutive(
            CreateExecutiveRequestDto request,
            String teamLeadCode) {

        User teamLead = userRepository.findByUserCode(teamLeadCode)
                .orElseThrow(() -> new RuntimeException("TeamLead not found"));

        if (!teamLead.getRole().equals(Role.TEAMLEAD)) {
            throw new RuntimeException("Only TeamLead can create Executive");
        }

        if (userRepository.findByUserCode(request.getExecutiveCode()).isPresent()) {
            throw new RuntimeException("Executive ID already exists");
        }

		/*
		 * User executive = User.builder() .userCode(request.getExecutiveCode())
		 * .name(request.getName()) .phone(request.getPhone()) .role(Role.EXECUTIVE)
		 * .isActivated(false) .isActive(true) .build();
		 */
        User executive = User.builder()
                .userCode(request.getExecutiveCode())
                .name(request.getName())
                .phone(request.getPhone())
                .role(Role.EXECUTIVE)
                .teamleadId(teamLead.getId())   // ✅ IMPORTANT LINE
               
                .isActivated(false)
                .isActive(true)
                .build();


        userRepository.save(executive);

        return new ExecutiveResponseDto(
                executive.getUserCode(),
                executive.getName(),
                executive.getPhone(),
                "Executive created successfully");
    }
}
