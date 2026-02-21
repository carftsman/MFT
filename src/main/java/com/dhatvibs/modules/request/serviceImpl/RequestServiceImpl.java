package com.dhatvibs.modules.request.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.repository.FormRepository;
import com.dhatvibs.modules.request.dto.ResendRequestDto;
import com.dhatvibs.modules.request.repository.RequestRepository;
import com.dhatvibs.modules.request.service.RequestService;

import jakarta.servlet.http.HttpSession;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FormRepository formRepository;

    // 2️⃣ Executive Request Resend
    @Override
    public void requestResend(ResendRequestDto dto, HttpSession session) {

        Long executiveId = (Long) session.getAttribute("userId");

        Form form = requestRepository.findById(dto.getFormId())
                .orElseThrow(() -> new RuntimeException("Form not found"));

        if (!form.getExecutiveId().equals(executiveId)) {
            throw new RuntimeException("Unauthorized");
        }

        form.setResendRequested(true);
        form.setResendReason(dto.getReason());
        form.setResendApproved(false);

        requestRepository.save(form);
    }

    // 5️⃣ Manager View Requests
    @Override
    public List<FormResponseDto> getManagerResendRequests(HttpSession session) {

        return requestRepository
                .findByResendRequestedTrueAndResendApprovedFalse()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 6️⃣ Approve
    @Override
    public void approveResend(Long formId, HttpSession session) {

        Form form = requestRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.setResendApproved(true);

        requestRepository.save(form);
    }

    // 7️⃣ Reject
    @Override
    public void rejectResend(Long formId, HttpSession session) {

        Form form = requestRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.setResendRequested(false);
        form.setResendReason(null);
        form.setResendApproved(false);

        requestRepository.save(form);
    }

    // 3️⃣ Executive Get Approved Forms
    @Override
    public List<FormResponseDto> getApprovedResendForms(HttpSession session) {

        Long executiveId = (Long) session.getAttribute("userId");

        return requestRepository
                .findByExecutiveIdAndResendApprovedTrue(executiveId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 4️⃣ Resubmit Form
    @Override
    public FormResponseDto resubmitForm(Long formId,
                                        FormRequestDto dto,
                                        HttpSession session) {

        Form form = requestRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.setVendorShopName(dto.getVendorShopName());
        form.setVendorName(dto.getVendorName());
        form.setContactNumber(dto.getContactNumber());
        form.setMailId(dto.getMailId());
        form.setStatus(dto.getStatus());
        form.setReview(dto.getReview());

        // Reset resend flags
        form.setResendRequested(false);
        form.setResendApproved(false);
        form.setResendReason(null);

        requestRepository.save(form);

        return mapToDto(form);
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
                .tag(form.getTag())
                .status(form.getStatus())
                .review(form.getReview())
                .createdAt(form.getCreatedAt())
                .build();
    }
}