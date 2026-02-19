package com.dhatvibs.modules.teamlead.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.auth.entity.Role;
import com.dhatvibs.modules.auth.entity.User;
import com.dhatvibs.modules.auth.repository.UserRepository;
//import com.dhatvibs.modules.form.dto.FormResponseDto;
//import com.dhatvibs.modules.form.repository.FormRepository;
import com.dhatvibs.modules.teamlead.dto.*;
import com.dhatvibs.modules.teamlead.service.TeamLeadService;

import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.repository.FormRepository;

import jakarta.servlet.http.HttpSession;

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
    
    
    
    @Autowired
    private FormRepository formRepository;

    
    @Override
    public List<FormResponseDto> getMyExecutivesForms(HttpSession session) {

        Long teamleadId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (teamleadId == null) {
            throw new RuntimeException("Unauthorized - Please login");
        }

        if (!"TEAMLEAD".equals(role)) {
            throw new RuntimeException("Access Denied - Not a TeamLead");
        }

        List<Form> forms = formRepository.findByTeamleadId(teamleadId);

        return forms.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private FormResponseDto mapToDto(Form form) {
        return FormResponseDto.builder()
                .id(form.getId())

                .executiveId(form.getExecutiveId())
                .executiveName(form.getExecutiveName())

                .teamleadId(form.getTeamleadId())
                .teamleadName(form.getTeamleadName())

                .vendorShopName(form.getVendorShopName())
                .vendorName(form.getVendorName())
                .contactNumber(form.getContactNumber())
                .mailId(form.getMailId())
                .areaName(form.getAreaName())
                .state(form.getState())

                .tag(form.getTag())
                .status(form.getStatus())
                .review(form.getReview())

                .createdAt(form.getCreatedAt())
                .build();
    }
    
    
    
}
