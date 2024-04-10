package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.service.ResidenceService;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/vi/residences")
public class ResidenceController {

    @Autowired
    public ResidenceService residenceService;

    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @PostMapping("/save/{adminId}")
    public String addResidence(@RequestBody Residence residence, @PathVariable Integer adminId) {

        return residenceService.addResidence(residence, adminId);
    }

    @GetMapping("/all")
    public List<Residence> getAllResidences() {

        return residenceService.getAllResidences();
    }

    @GetMapping("/findById/{id}")
    public Optional<Residence> getResidenceById(@PathVariable Long id) {
        return residenceService.getResidenceById(id);
    }

    @GetMapping("/findByEmail/{email}")
    public Optional<Residence> findResidenceByEmail(@PathVariable String email) {
        return residenceService.findResidenceByEmail(email);
    }

    @PutMapping("update/{id}/{adminId}")
    public Residence updateResidence(@PathVariable Long id, @PathVariable Integer adminId,
            @RequestBody Residence residence) {

        try {

            return residenceService.updateResidence(residence, id, adminId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while updating residence", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteResidence(@PathVariable Long id) {
        residenceService.deleteResidence(id);
    }
}
