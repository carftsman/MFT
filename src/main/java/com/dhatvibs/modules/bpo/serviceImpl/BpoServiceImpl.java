package com.dhatvibs.modules.bpo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhatvibs.modules.bpo.dto.BpoSubmitDto;
import com.dhatvibs.modules.bpo.service.BpoService;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.entity.FormStatus;
import com.dhatvibs.modules.form.repository.FormRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class BpoServiceImpl implements BpoService {

    @Autowired
    private FormRepository formRepository;

    // =========================
    // 1️⃣ DASHBOARD API
    // =========================
    @Override
    @Transactional
    public List<FormResponseDto> getDashboardForms(HttpSession session) {

        Long bpoId = (Long) session.getAttribute("userId");
        String bpoName = (String) session.getAttribute("userName");

        if (bpoId == null) {
            throw new RuntimeException("Unauthorized - Session expired");
        }

        List<Form> forms = formRepository.findAvailableFormsForBpo();

        // Assign first available unassigned form
        for (Form form : forms) {

            if (form.getAssignedBpoId() == null) {

                form.setAssignedBpoId(bpoId);
                form.setAssignedBpoName(bpoName);
                formRepository.save(form);

                break;
            }
            
        }
        
        //added
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session userId: " + session.getAttribute("userId"));


        return forms.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // =========================
    // 2️⃣ SUBMIT API
    // =========================
    @Override
    @Transactional
    public FormResponseDto submitForm(Long formId, BpoSubmitDto dto, HttpSession session) {

        Long bpoId = (Long) session.getAttribute("userId");

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        System.out.println("Form assignedBpoId: " + form.getAssignedBpoId());
        System.out.println("Session userId: " + bpoId);

        
        if (!bpoId.equals(form.getAssignedBpoId())) {
            throw new RuntimeException("Form not assigned to this BPO");
        }

        /*form.setReview(dto.getReview()); */
        form.setExecutiveReview(dto.getExecutiveReview());
        form.setVendorReview(dto.getVendorReview());
       
        form.setBpoActionDate(LocalDateTime.now());

        // ---------------- SOLVED ----------------
        if ("SOLVED".equalsIgnoreCase(dto.getAction())) {

            form.setSolved(true);

            // Tag change logic
            if (form.getStatus() == FormStatus.INTERESTED ||
                form.getStatus() == FormStatus.NOT_INTERESTED) {

                form.setTag(FormTag.GREEN);
            }

            // If ONBOARDED -> already GREEN (no change)

            //form.setAssignedBpoId(null);
           // form.setExecutiveReview(null);
           // form.setVendorReview(null);
           // form.setAssignedBpoName(null);
        }

        // ---------------- NOT SOLVED ----------------
        else if ("NOT_SOLVED".equalsIgnoreCase(dto.getAction())) {

            form.setSolved(false);

            form.setReappearDate(LocalDateTime.now().plusDays(2));

            form.setAssignedBpoId(null);
            form.setAssignedBpoName(null);
        }
        
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session userId: " + session.getAttribute("userId"));

        
        return mapToDto(formRepository.save(form));
    }

    // =========================
    // DTO MAPPING
    // =========================
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
                .vendorLocation(form.getVendorLocation())
                .mailId(form.getMailId())
                .doorNumber(form.getDoorNumber())
                .streetName(form.getStreetName())
                .areaName(form.getAreaName())
                .pinCode(form.getPinCode())
                .state(form.getState())
                .tag(form.getTag())
                .status(form.getStatus())
                .review(form.getReview())
                .createdAt(form.getCreatedAt())
                .build();
    }
}
