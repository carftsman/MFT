package com.dhatvibs.modules.bporequest.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhatvibs.modules.bporequest.dto.BpoModifyDto;
import com.dhatvibs.modules.bporequest.dto.CorrectionRequestDto;
import com.dhatvibs.modules.bporequest.dto.ManagerApprovalDto;
import com.dhatvibs.modules.bporequest.service.BpoRequestService;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.WorkflowStatus;
import com.dhatvibs.modules.form.repository.FormRepository;

import jakarta.servlet.http.HttpSession;

@Service
@Transactional
public class BpoRequestServiceImpl implements BpoRequestService {

    @Autowired
    private FormRepository formRepository;

    // 1️⃣ BPO HISTORY
    @Override
    public List<FormResponseDto> getBpoHistory(HttpSession session) {

        Long bpoId = (Long) session.getAttribute("userId");

        return formRepository.findAll()
                .stream()
                .filter(f -> bpoId.equals(f.getAssignedBpoId())
                        && f.getBpoActionDate() != null)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 2️⃣ REQUEST CORRECTION
    @Override
    public FormResponseDto requestCorrection(Long formId,
                                             CorrectionRequestDto dto,
                                             HttpSession session) {

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.setResendRequested(true);
        form.setResendReason(dto.getReason());
        form.setWorkflowStatus(WorkflowStatus.CORRECTION_REQUESTED);

        return mapToDto(formRepository.save(form));
    }

    // 3️⃣ MANAGER VIEW REQUESTS
    @Override
    public List<FormResponseDto> getManagerCorrectionRequests(HttpSession session) {

        return formRepository.findAll()
                .stream()
                .filter(f -> WorkflowStatus.CORRECTION_REQUESTED
                        .equals(f.getWorkflowStatus()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 4️⃣ MANAGER APPROVE / REJECT
    @Override
    public FormResponseDto approveCorrection(Long formId,
                                             ManagerApprovalDto dto,
                                             HttpSession session) {

        Long managerId = (Long) session.getAttribute("userId");
        String managerName = (String) session.getAttribute("userName");

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        if (dto.getApproved()) {

            form.setResendApproved(true);
            form.setWorkflowStatus(WorkflowStatus.REOPENED);
            form.setManagerId(managerId);
            form.setManagerName(managerName);
            form.setResendApprovedDate(LocalDateTime.now());

        } else {

            form.setResendRequested(false);
            form.setWorkflowStatus(WorkflowStatus.BPO_VERIFIED);
        }

        return mapToDto(formRepository.save(form));
    }

    // 5️⃣ BPO MODIFY & RESUBMIT
    @Override
    public FormResponseDto modifyAndResubmit(Long formId,
                                             BpoModifyDto dto,
                                             HttpSession session) {

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        if (!WorkflowStatus.REOPENED.equals(form.getWorkflowStatus())) {
            throw new RuntimeException("Form is not editable");
        }

        // Replace old data
        form.setVendorShopName(dto.getVendorShopName());
        form.setVendorName(dto.getVendorName());
        form.setContactNumber(dto.getContactNumber());
        form.setMailId(dto.getMailId());
        form.setVendorLocation(dto.getVendorLocation());
        form.setDoorNumber(dto.getDoorNumber());
        form.setStreetName(dto.getStreetName());
        form.setAreaName(dto.getAreaName());
        form.setPinCode(dto.getPinCode());
        form.setState(dto.getState());

        form.setExecutiveReview(dto.getExecutiveReview());
        form.setVendorReview(dto.getVendorReview());

        form.setWorkflowStatus(WorkflowStatus.RESUBMITTED);
        form.setResendRequested(false);
        form.setResendApproved(false);

        return mapToDto(formRepository.save(form));
    }

    // DTO Mapper
    private FormResponseDto mapToDto(Form form) {

        return FormResponseDto.builder()
                .id(form.getId())
                .executiveId(form.getExecutiveId())
                .executiveName(form.getExecutiveName())
                .teamleadId(form.getTeamleadId())
                .teamleadName(form.getTeamleadName())
                .vendorShopName(form.getVendorShopName())
                .vendorName(form.getVendorName())
                .status(form.getStatus())
                .tag(form.getTag())
                .createdAt(form.getCreatedAt())
                .build();
    }
}