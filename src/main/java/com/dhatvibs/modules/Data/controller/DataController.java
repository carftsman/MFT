package com.dhatvibs.modules.Data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.Data.dto.DataResponseDto;
import com.dhatvibs.modules.Data.service.DataService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/forms")
    public List<DataResponseDto> getAllFormData(HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            throw new RuntimeException("Unauthorized - Please login");
        }

        if (!(role.equals("ADMIN") ||
              role.equals("MANAGER") ||
              role.equals("REPORTER"))) {

            throw new RuntimeException("Access Denied - Not Authorized");
        }

        return dataService.getAllFormData();
    }
}