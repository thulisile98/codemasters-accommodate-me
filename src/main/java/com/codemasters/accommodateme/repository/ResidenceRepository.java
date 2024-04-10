package com.codemasters.accommodateme.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemasters.accommodateme.entity.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {
    Optional<Residence> findResidenceByEmail(String email);

}
