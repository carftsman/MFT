package com.dhatvibs.modules.bporequest.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.entity.WorkflowStatus;
import com.dhatvibs.modules.form.repository.FormRepository;

import jakarta.servlet.http.HttpSession;

@Service
@Transactional
public class BpoRequestServiceImpl implements BpoRequestService {

	@Autowired
	private FormRepository formRepository;

	// 1️⃣ BPO HISTORY
	/*
	 * @Override public List<FormResponseDto> getBpoHistory(HttpSession session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId");
	 * 
	 * return formRepository.findAll().stream() .filter(f ->
	 * bpoId.equals(f.getAssignedBpoId()) && f.getBpoActionDate() !=
	 * null).map(this::mapToDto) .collect(Collectors.toList()); }
	 */ 
	@Override
	public List<FormResponseDto> getBpoHistory(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    if (bpoId == null) {
	        throw new RuntimeException("Unauthorized - Login Required");
	    }

	    List<Form> forms =
	            formRepository.findByAssignedBpoIdAndBpoActionDateIsNotNull(bpoId);

	    return forms.stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	}

	// 2️⃣ REQUEST CORRECTION
	@Override
	public FormResponseDto requestCorrection(Long formId, CorrectionRequestDto dto, HttpSession session) {

		Form form = formRepository.findById(formId).orElseThrow(() -> new RuntimeException("Form not found"));

		form.setResendRequested(true);
		form.setBpoReason(dto.getBpoReason());
		form.setWorkflowStatus(WorkflowStatus.CORRECTION_REQUESTED);

		return mapToDto(formRepository.save(form));
	}

	// 3️⃣ MANAGER VIEW REQUESTS
	@Override
	public List<FormResponseDto> getManagerCorrectionRequests(HttpSession session) {

		return formRepository.findAll().stream()
				.filter(f -> WorkflowStatus.CORRECTION_REQUESTED.equals(f.getWorkflowStatus())).map(this::mapToDto)
				.collect(Collectors.toList());
	}

	// 4️⃣ MANAGER APPROVE / REJECT
	@Override
	public FormResponseDto approveCorrection(Long formId, ManagerApprovalDto dto, HttpSession session) {

		Long managerId = (Long) session.getAttribute("userId");
		String managerName = (String) session.getAttribute("userName");

		Form form = formRepository.findById(formId).orElseThrow(() -> new RuntimeException("Form not found"));

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
	/*
	 * @Override public FormResponseDto modifyAndResubmit(Long formId, BpoModifyDto
	 * dto, HttpSession session) {
	 * 
	 * Form form = formRepository.findById(formId) .orElseThrow(() -> new
	 * RuntimeException("Form not found"));
	 * 
	 * if (!WorkflowStatus.REOPENED.equals(form.getWorkflowStatus())) { throw new
	 * RuntimeException("Form is not editable"); }
	 * 
	 * // Replace old data
	 * 
	 * form.setVendorShopName(dto.getVendorShopName());
	 * form.setVendorName(dto.getVendorName());
	 * form.setContactNumber(dto.getContactNumber());
	 * form.setMailId(dto.getMailId());
	 * form.setVendorLocation(dto.getVendorLocation());
	 * form.setDoorNumber(dto.getDoorNumber());
	 * form.setStreetName(dto.getStreetName()); form.setAreaName(dto.getAreaName());
	 * form.setPinCode(dto.getPinCode()); form.setState(dto.getState());
	 * 
	 * form.setAction(dto.getAction()); form.setIdNumber(dto.getIdNumber());
	 * form.setBpoName(dto.getBpoName());
	 * form.setExecutiveReview(dto.getExecutiveReview());
	 * form.setVendorReview(dto.getVendorReview());
	 * 
	 * form.setWorkflowStatus(WorkflowStatus.RESUBMITTED);
	 * form.setResendRequested(false); form.setResendApproved(false);
	 * 
	 * return mapToDto(formRepository.save(form)); }
	 */ 
	
	@Override
	public FormResponseDto modifyAndResubmit(Long formId,
	                                         BpoModifyDto dto,
	                                         HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    if (bpoId == null) {
	        throw new RuntimeException("Unauthorized - Login Required");
	    }

	    Form form = formRepository.findById(formId)
	            .orElseThrow(() -> new RuntimeException("Form not found"));

	    if (!WorkflowStatus.REOPENED.equals(form.getWorkflowStatus())) {
	        throw new RuntimeException("Form is not editable");
	    }

	    form.setIdNumber(dto.getIdNumber());
	    form.setBpoName(dto.getBpoName());
	    form.setExecutiveReview(dto.getExecutiveReview());
	    form.setVendorReview(dto.getVendorReview());

	    form.setBpoActionDate(LocalDateTime.now());

	    if ("SOLVED".equalsIgnoreCase(dto.getAction())) {

	        form.setSolved(true);
	        form.setTag(FormTag.GREEN);

	    } else {

	        form.setSolved(false);
	    }

	    form.setWorkflowStatus(WorkflowStatus.RESUBMITTED);
	    form.setResendRequested(false);
	    form.setResendApproved(false);

	    return mapToDto(formRepository.save(form));
	}
	/*
	 * // DTO Mapper private FormResponseDto mapToDto(Form form) {
	 * 
	 * return FormResponseDto.builder() .id(form.getId())
	 * .executiveId(form.getExecutiveId()) .executiveName(form.getExecutiveName())
	 * .teamleadId(form.getTeamleadId()) .teamleadName(form.getTeamleadName())
	 * .vendorShopName(form.getVendorShopName()) .vendorName(form.getVendorName())
	 * .status(form.getStatus()) .tag(form.getTag()) .createdAt(form.getCreatedAt())
	 * .build(); }
	 */

	private FormResponseDto mapToDto(Form form) {

		return FormResponseDto.builder().id(form.getId()).executiveId(form.getExecutiveId())
				.executiveName(form.getExecutiveName()).teamleadId(form.getTeamleadId())
				.teamleadName(form.getTeamleadName()).vendorShopName(form.getVendorShopName())
				.vendorName(form.getVendorName()).contactNumber(form.getContactNumber()).mailId(form.getMailId())
				.vendorType(form.getVendorType()).vendorLocation(form.getVendorLocation()).latitude(form.getLatitude())
				.longitude(form.getLongitude()).doorNumber(form.getDoorNumber()).streetName(form.getStreetName())
				.areaName(form.getAreaName()).pinCode(form.getPinCode()).state(form.getState()).tag(form.getTag()).bpoReason(form.getBpoReason())  //added
				.status(form.getStatus()).review(form.getReview())

				// BPO
				.solved(form.getSolved()).idNumber(form.getIdNumber()).bpoName(form.getBpoName())
				.executiveReview(form.getExecutiveReview()).vendorReview(form.getVendorReview())

				// Correction
				.resendRequested(form.getResendRequested()).bpoReason(form.getBpoReason())
				.resendApproved(form.getResendApproved())

				.createdAt(form.getCreatedAt()).build();
	} 
	
	@Override
	public Long getMyRequestCount(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    return formRepository.findAll()
	            .stream()
	            .filter(f -> bpoId.equals(f.getAssignedBpoId())
	                    && Boolean.TRUE.equals(f.getResendRequested()))
	            .count();
	}

	@Override
	public Map<String, Long> getMyApprovalStats(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    long approved = formRepository.findAll()
	            .stream()
	            .filter(f -> bpoId.equals(f.getAssignedBpoId())
	                    && Boolean.TRUE.equals(f.getResendApproved()))
	            .count();

	    long rejected = formRepository.findAll()
	            .stream()
	            .filter(f -> bpoId.equals(f.getAssignedBpoId())
	                    && Boolean.FALSE.equals(f.getResendApproved())
	                    && Boolean.FALSE.equals(f.getResendRequested()))
	            .count();

	    Map<String, Long> map = new HashMap<>();
	    map.put("approved", approved);
	    map.put("rejected", rejected);

	    return map;
	}  
	 
	
	@Override
	public List<FormResponseDto> getReopenedForms(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    if (bpoId == null) {
	        throw new RuntimeException("Unauthorized - Login Required");
	    }

	    List<Form> forms = formRepository
	            .findByAssignedBpoIdAndWorkflowStatus(
	                    bpoId,
	                    WorkflowStatus.REOPENED
	            );

	    return forms.stream()
	            .filter(f -> Boolean.TRUE.equals(f.getResendApproved()))
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	}
	
}