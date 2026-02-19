
package com.dhatvibs.modules.form.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.repository.FormRepository;
import com.dhatvibs.modules.form.service.FormService;

import jakarta.servlet.http.HttpSession;

@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private FormRepository formRepository;

    @Override
    public FormResponseDto createForm(FormRequestDto requestDto, HttpSession session) {

        Long executiveId = (Long) session.getAttribute("userId");
        String executiveName = (String) session.getAttribute("executiveName");

        Long teamleadId = (Long) session.getAttribute("teamleadId");
        String teamleadName = (String) session.getAttribute("teamleadName");

        if (executiveId == null || teamleadId == null) {
            throw new RuntimeException("Unauthorized - Session expired");
        }

        // ✅ STEP-1: Dynamic Tag Logic Based On Status
        FormTag tag;

        switch (requestDto.getStatus()) {

            case ONBOARDED:
                tag = FormTag.GREEN;
                break;

            case INTERESTED:
                tag = FormTag.ORANGE;
                break;

            case NOT_INTERESTED:
                tag = FormTag.YELLOW;
                break;

            default:
                tag = FormTag.ORANGE;
        }

        // ✅ STEP-2: Save Review + Names + Tag
        Form form = Form.builder()
                .executiveId(executiveId)
                .executiveName(executiveName)
                .teamleadId(teamleadId)
                .teamleadName(teamleadName)
                .vendorShopName(requestDto.getVendorShopName())
                .vendorName(requestDto.getVendorName())
                .contactNumber(requestDto.getContactNumber())
                .mailId(requestDto.getMailId())
                .vendorLocation(requestDto.getVendorLocation())  //added
                .doorNumber(requestDto.getDoorNumber())
                .streetName(requestDto.getStreetName())
                .areaName(requestDto.getAreaName())
                .pinCode(requestDto.getPinCode())
                .state(requestDto.getState())
                .status(requestDto.getStatus())
                .review(requestDto.getReview())   // ✅ review added
                .tag(tag)                         // ✅ dynamic tag
                .build();

        form = formRepository.save(form);

        return mapToDto(form);
    }

    @Override
    public List<FormResponseDto> getAllForms() {

        return formRepository.findAll()
                .stream()
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
                .vendorLocation(form.getVendorLocation())
                .doorNumber(form.getDoorNumber())  //added
                .streetName(form.getStreetName()) //added
                .areaName(form.getAreaName())
                .pinCode(form.getPinCode())      //added
                .state(form.getState())

                .tag(form.getTag())
                .status(form.getStatus())
                .review(form.getReview())

                .createdAt(form.getCreatedAt())
                .build();
    }
}

