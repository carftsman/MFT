package com.dhatvibs.modules.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userCode;   // ITS102 etc

    private String name;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isActivated = false;

    private Boolean isActive = true;
}
