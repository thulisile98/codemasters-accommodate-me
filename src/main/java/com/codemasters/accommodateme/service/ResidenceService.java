package com.codemasters.accommodateme.service;

import java.util.*;

import com.codemasters.accommodateme.entity.Residence;

public interface ResidenceService {

    String addResidence(Residence residence, Integer adminId);

    List<Residence> getAllResidences();

    Residence updateResidence(Residence residence, Long resId, Integer adminId);

    Optional<Residence> getResidenceById(Long resId);

    Optional<Residence> findResidenceByEmail(String email);

    void deleteResidence(Long resId);
}
