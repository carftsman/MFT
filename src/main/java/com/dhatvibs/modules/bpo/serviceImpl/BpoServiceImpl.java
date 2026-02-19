package com.dhatvibs.modules.bpo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhatvibs.modules.bpo.dto.BpoSubmitRequestDto;
import com.dhatvibs.modules.bpo.service.BpoService;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.repository.FormRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class BpoServiceImpl implements BpoService {

    @Autowired
    private FormRepository formRepository;

    @Override
    @Transactional
    public List<FormResponseDto> getFormsForBpo(HttpSession session) {

        Long bpoId = (Long) session.getAttribute("userId");
        String bpoName = (String) session.getAttribute("userName");

        if (bpoId == null) {
            throw new RuntimeException("Unauthorized");
        }

        // 1️⃣ Check already assigned forms
        List<Form> assignedForms =
                formRepository.findByAssignedBpoIdAndBpoSolvedIsNull(bpoId);

        if (!assignedForms.isEmpty()) {
            return assignedForms.stream().map(this::mapToDto).toList();
        }

        // 2️⃣ Fetch new unassigned forms
        Pageable limitFive = PageRequest.of(0, 5);

        List<Form> newForms =
                formRepository.findUnassignedFormsForUpdate(limitFive);

        for (Form form : newForms) {
            form.setIsAssigned(true);
            form.setAssignedBpoId(bpoId);
            form.setAssignedBpoName(bpoName);
        }

        formRepository.saveAll(newForms);

        return newForms.stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public void submitForm(Long formId,
                           BpoSubmitRequestDto dto,
                           HttpSession session) {

        Long bpoId = (Long) session.getAttribute("userId");

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

		/*
		 * if (!form.getAssignedBpoId().equals(bpoId)) { throw new
		 * RuntimeException("Not authorized"); }
		 */
        
        if (form.getAssignedBpoId() == null ||                         //Added
        	    !form.getAssignedBpoId().equals(bpoId)) {

        	    throw new RuntimeException("Form is not assigned to you");
        	}


        form.setBpoReview(dto.getReview());
        form.setBpoSolved(dto.getSolved());

        if (Boolean.TRUE.equals(dto.getSolved())) {

            form.setNextFollowUpDate(null);

            if (form.getTag() == FormTag.ORANGE ||
                form.getTag() == FormTag.YELLOW) {

                form.setTag(FormTag.GREEN);
            }

        } else {

            form.setNextFollowUpDate(
                    LocalDateTime.now().plusDays(2));

            form.setIsAssigned(false);
            form.setAssignedBpoId(null);
            form.setAssignedBpoName(null);
        }
      
        System.out.println("Assigned BPO: " + form.getAssignedBpoId());
        System.out.println("Header BPO: " + bpoId);

        formRepository.save(form);
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
