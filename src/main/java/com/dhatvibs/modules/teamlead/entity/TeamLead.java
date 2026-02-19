package com.dhatvibs.modules.teamlead.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teamleads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
}
