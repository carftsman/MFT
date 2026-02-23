package com.dhatvibs.modules.executive.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "executive_attendance",
       uniqueConstraints = @UniqueConstraint(columnNames = {"executiveName", "attendanceDate"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutiveAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String executiveName;

    private String teamleadName;

    private Double latitude;

    private Double longitude;

    private LocalDate attendanceDate;   // Only one per day

    private LocalDateTime createdAt;
}