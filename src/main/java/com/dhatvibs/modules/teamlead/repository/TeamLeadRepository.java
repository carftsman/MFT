package com.dhatvibs.modules.teamlead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhatvibs.modules.teamlead.entity.TeamLead;

@Repository
public interface TeamLeadRepository extends JpaRepository<TeamLead, Long> {
}
