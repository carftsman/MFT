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

	
	/*
	 * @Override
	 * 
	 * @Transactional public List<FormResponseDto> getDashboardForms(HttpSession
	 * session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId"); String bpoName = (String)
	 * session.getAttribute("userName");
	 * 
	 * if (bpoId == null) { throw new
	 * RuntimeException("Unauthorized - Session expired"); }
	 * 
	 * // List<Form> forms = formRepository.findAvailableFormsForBpo(); List<Form>
	 * forms = formRepository.findAvailableFormsForBpo(bpoId);
	 * 
	 * 
	 * for (Form form : forms) {
	 * 
	 * if (form.getAssignedBpoId() == null) {
	 * 
	 * form.setAssignedBpoId(bpoId); form.setAssignedBpoName(bpoName);
	 * formRepository.saveAndFlush(form); // 🔥 Important
	 * 
	 * break; } }
	 * 
	 * // added System.out.println("Session ID: " + session.getId());
	 * System.out.println("Session userId: " + session.getAttribute("userId"));
	 * 
	 * return forms.stream().map(this::mapToDto).collect(Collectors.toList()); }
	 */  
	
	
	
	/*
	 * @Override
	 * 
	 * @Transactional public List<FormResponseDto> getDashboardForms(HttpSession
	 * session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId"); String bpoName = (String)
	 * session.getAttribute("userName");
	 * 
	 * if (bpoId == null) { throw new RuntimeException("Session expired"); }
	 * 
	 * List<Form> forms = formRepository.findNewForms();
	 * 
	 * for (Form form : forms) {
	 * 
	 * if (form.getAssignedBpoId() == null) {
	 * 
	 * form.setAssignedBpoId(bpoId); form.setAssignedBpoName(bpoName);
	 * 
	 * formRepository.saveAndFlush(form);
	 * 
	 * return List.of(mapToDto(form)); } }
	 * 
	 * return List.of(); }
	 */ 
	
	/*
	 * @Override
	 * 
	 * @Transactional public List<FormResponseDto> getDashboardForms(HttpSession
	 * session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId"); String bpoName = (String)
	 * session.getAttribute("userName");
	 * 
	 * if (bpoId == null) { throw new RuntimeException("Session expired"); }
	 * 
	 * // Step 1: Assign NEW forms to BPO List<Form> newForms =
	 * formRepository.findNewForms();
	 * 
	 * for (Form form : newForms) {
	 * 
	 * if (form.getAssignedBpoId() == null) {
	 * 
	 * form.setAssignedBpoId(bpoId); form.setAssignedBpoName(bpoName);
	 * 
	 * formRepository.save(form); } }
	 * 
	 * // Step 2: Fetch ALL forms assigned to this BPO List<Form> assignedForms =
	 * formRepository .findByAssignedBpoIdAndSolvedFalse(bpoId);
	 * 
	 * return assignedForms .stream() .map(this::mapToDto)
	 * .collect(Collectors.toList()); }
	 */ 
	@Override
	@Transactional
	public List<FormResponseDto> getDashboardForms(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");
	    String bpoName = (String) session.getAttribute("userName");

	    if (bpoId == null) {
	        throw new RuntimeException("Session expired");
	    }

	    // Step 1: check how many forms BPO already has
	    List<Form> assignedForms =
	          //  formRepository.findByAssignedBpoIdAndSolvedFalse(bpoId);
	    	  //	formRepository.findByAssignedBpoIdAndSolvedFalseAndReappearDateIsNull(bpoId);
	    		formRepository.findByAssignedBpoIdAndSolvedFalseAndReappearDateIsNullAndBpoActionDateIsNull(bpoId);
	    

	    int limit = 10;

	    if (assignedForms.size() < limit) {

	        int needed = limit - assignedForms.size();

	        List<Form> newForms = formRepository.findNewForms(
	                org.springframework.data.domain.PageRequest.of(0, needed)
	        );

	        for (Form form : newForms) {

	            form.setAssignedBpoId(bpoId);
	            form.setAssignedBpoName(bpoName);

	            formRepository.save(form);

	            assignedForms.add(form);
	        }
	    }

	    return assignedForms
	            .stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	}
	
	
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<FormResponseDto>
	 * getReappearForms(HttpSession session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId");
	 * 
	 * if (bpoId == null) { throw new RuntimeException("Session expired"); }
	 * 
	 * return formRepository.findReappearForms(bpoId) .stream() .map(this::mapToDto)
	 * .collect(Collectors.toList()); }
	 */ 
	
	@Override
	@Transactional(readOnly = true)
	public List<FormResponseDto> getReappearForms(HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    if (bpoId == null) {
	        throw new RuntimeException("Session expired");
	    }

	    List<Form> forms = formRepository.findReappearForms(bpoId);

	    return forms.stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	}
	
	/*
	 * @Override
	 * 
	 * @Transactional public FormResponseDto submitForm(Long formId, BpoSubmitDto
	 * dto, HttpSession session) {
	 * 
	 * Long bpoId = (Long) session.getAttribute("userId");
	 * 
	 * if (bpoId == null) { throw new RuntimeException("Session expired"); }
	 * 
	 * Form form = formRepository.findById(formId) .orElseThrow(() -> new
	 * RuntimeException("Form not found"));
	 * 
	 * if (form.getAssignedBpoId() == null ||
	 * !form.getAssignedBpoId().equals(bpoId)) {
	 * 
	 * throw new RuntimeException("Form not assigned to this BPO"); }
	 * 
	 * form.setIdNumber(dto.getIdNumber()); form.setBpoName(dto.getBpoName());
	 * form.setExecutiveReview(dto.getExecutiveReview());
	 * form.setVendorReview(dto.getVendorReview());
	 * form.setBpoActionDate(LocalDateTime.now());
	 * 
	 * if ("SOLVED".equalsIgnoreCase(dto.getAction())) {
	 * 
	 * form.setSolved(true);
	 * 
	 * // Remove assignment after solving // form.setAssignedBpoId(null); //added //
	 * form.setAssignedBpoName(null); //added
	 * 
	 * if (form.getStatus() == FormStatus.INTERESTED || form.getStatus() ==
	 * FormStatus.NOT_INTERESTED) {
	 * 
	 * form.setTag(FormTag.GREEN); } }
	 * 
	 * else if ("NOT_SOLVED".equalsIgnoreCase(dto.getAction())) {
	 * 
	 * form.setSolved(false); form.setReappearDate(LocalDateTime.now().plusDays(2));
	 * form.setAssignedBpoId(null); form.setAssignedBpoName(null); }
	 * 
	 * return mapToDto(formRepository.saveAndFlush(form)); }
	 */ 
	
	@Override
	@Transactional
	public FormResponseDto submitForm(Long formId, BpoSubmitDto dto, HttpSession session) {

	    Long bpoId = (Long) session.getAttribute("userId");

	    if (bpoId == null) {
	        throw new RuntimeException("Session expired");
	    }

	    Form form = formRepository.findById(formId)
	            .orElseThrow(() -> new RuntimeException("Form not found"));

	    if (!bpoId.equals(form.getAssignedBpoId())) {
	        throw new RuntimeException("Form not assigned to this BPO");
	    }

	    form.setIdNumber(dto.getIdNumber());
	    form.setBpoName(dto.getBpoName());
	    form.setExecutiveReview(dto.getExecutiveReview());
	    form.setVendorReview(dto.getVendorReview());
	    form.setVendorReady(dto.getVendorReady());
	    form.setOnboardInDays(dto.getOnboardInDays());
	    form.setBpoActionDate(LocalDateTime.now());
	    
	   

		/*
		 * // Vendor onboarding logic if (Boolean.TRUE.equals(dto.getVendorReady()) &&
		 * dto.getOnboardInDays() != null) {
		 * 
		 * LocalDateTime followUpDate =
		 * LocalDateTime.now().plusDays(dto.getOnboardInDays());
		 * 
		 * form.setVendorReady(true); form.setOnboardInDays(dto.getOnboardInDays());
		 * form.setOnboardFollowupDate(followUpDate); }
		 */
         
	    
	    String message = "";

	    if (Boolean.TRUE.equals(dto.getVendorReady()) && dto.getOnboardInDays() != null) {

	        LocalDateTime followUpDate = LocalDateTime.now().plusDays(dto.getOnboardInDays());

	        form.setVendorReady(true);
	        form.setOnboardInDays(dto.getOnboardInDays());
	        form.setOnboardFollowupDate(followUpDate);

	        message = "Go to vendor. He is ready to onboard on " + followUpDate.toLocalDate();
	    }
	    else {

	        form.setVendorReady(false);
	        message = "Currently vendor is not ready to onboard";
	    }
	     
	    form.setVendorMessage(message);
	    
	    if ("SOLVED".equalsIgnoreCase(dto.getAction())) {

	        form.setSolved(true);
	        form.setReappearDate(null);

	        if (form.getStatus() == FormStatus.INTERESTED ||
	            form.getStatus() == FormStatus.NOT_INTERESTED) {

	            form.setTag(FormTag.GREEN);
	        }

	    }

	    else if ("NOT_SOLVED".equalsIgnoreCase(dto.getAction())) {

	        form.setSolved(false);
	        form.setReappearDate(LocalDateTime.now().plusDays(2));

	        // IMPORTANT: keep same BPO
	        form.setAssignedBpoId(bpoId); 
	        form.setBpoActionDate(null);
	    }

	    //return mapToDto(formRepository.saveAndFlush(form)); 
	    
	    //Form savedForm = formRepository.saveAndFlush(form);

	    //FormResponseDto response = mapToDto(savedForm);

	    //System.out.println(message); // optional log

	   // return response; 
	    Form savedForm = formRepository.saveAndFlush(form);

	    FormResponseDto response = mapToDto(savedForm);
	    response.setVendorMessage(message);

	    return response;
	}
	
	
	@Override
	public List<FormResponseDto> getSolvedForms(HttpSession session) {

	    Long executiveId = (Long) session.getAttribute("userId");

	    return formRepository.findByExecutiveIdAndSolvedTrue(executiveId)
	            .stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	}

	// =========================
	// DTO MAPPING
	// =========================
	private FormResponseDto mapToDto(Form form) {

		return FormResponseDto.builder().id(form.getId()).executiveId(form.getExecutiveId())
				.executiveName(form.getExecutiveName()).teamleadId(form.getTeamleadId())
				.teamleadName(form.getTeamleadName()).vendorShopName(form.getVendorShopName())
				.vendorName(form.getVendorName()).contactNumber(form.getContactNumber())
				.vendorLocation(form.getVendorLocation()).mailId(form.getMailId()).vendorType(form.getVendorType()).doorNumber(form.getDoorNumber())
				.latitude(form.getLatitude()).longitude(form.getLongitude()).streetName(form.getStreetName()).areaName(form.getAreaName()).pinCode(form.getPinCode()).district(form.getDistrict())
				.state(form.getState()).tag(form.getTag()).status(form.getStatus()).review(form.getReview()).idNumber(form.getIdNumber()).bpoName(form.getBpoName()).executiveReview(form.getExecutiveReview()).vendorReview(form.getVendorReview())
				.vendorMessage(form.getVendorMessage()).reappearDate(form.getReappearDate()).createdAt(form.getCreatedAt()).build();
	}
}
