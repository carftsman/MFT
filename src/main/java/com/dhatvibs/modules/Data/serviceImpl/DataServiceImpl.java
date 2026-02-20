package com.dhatvibs.modules.Data.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.Data.dto.DataResponseDto;
import com.dhatvibs.modules.Data.service.DataService;
import com.dhatvibs.modules.form.entity.Form;
import com.dhatvibs.modules.form.repository.FormRepository;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private FormRepository formRepository;

    @Override
    public List<DataResponseDto> getAllFormData() {

        return formRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private DataResponseDto mapToDto(Form form) {

        return DataResponseDto.builder()
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

                .doorNumber(form.getDoorNumber())
                .streetName(form.getStreetName())
                .areaName(form.getAreaName())
                .pinCode(form.getPinCode())
                .state(form.getState())

                .tag(form.getTag())
                .status(form.getStatus())
                .review(form.getReview())

                .assignedBpoId(form.getAssignedBpoId())
                .assignedBpoName(form.getAssignedBpoName())

                .solved(form.getSolved())
                .executiveReview(form.getExecutiveReview())
                .vendorReview(form.getVendorReview())

                .bpoActionDate(form.getBpoActionDate())
                .reappearDate(form.getReappearDate())

                .createdAt(form.getCreatedAt())
                .updatedAt(form.getUpdatedAt())

                .build();
    }
}