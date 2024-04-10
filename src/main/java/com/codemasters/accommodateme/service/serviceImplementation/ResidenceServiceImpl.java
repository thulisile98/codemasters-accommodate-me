package com.codemasters.accommodateme.service.serviceImplementation;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codemasters.accommodateme.repository.ResidenceRepository;
import com.codemasters.accommodateme.repository.UserRepository;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;

import com.codemasters.accommodateme.service.ResidenceService;

@Service
public class ResidenceServiceImpl implements ResidenceService {

    private ResidenceRepository residenceRepository;

    private UserRepository userRepository;

    @Autowired

    public ResidenceServiceImpl(ResidenceRepository residenceRepository, UserRepository userRepository) {
        this.residenceRepository = residenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String addResidence(Residence residence, Integer adminId) {

        Optional<User> adminOptional = userRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            User admin = adminOptional.get();

            if (residence.getTotalNumberOfRooms() < 0 || residence.getTotalNumberOfSingleRooms() < 0
                    || residence.getTotalNumberOfDoubleRooms() < 0) {
                return "Error saving residence: Room numbers cannot be negative";
            }

            Optional<Residence> existingResidenceOptional = residenceRepository
                    .findResidenceByEmail(residence.getEmail());
            if (existingResidenceOptional.isPresent()) {
                throw new RuntimeException(
                        "Error saving residence: Residence with email " + residence.getEmail() + " already exists");
            } else if ((residence.getTotalNumberOfSingleRooms() + residence.getTotalNumberOfDoubleRooms()) != residence
                    .getTotalNumberOfRooms()) {
                throw new RuntimeException(
                        "Error saving residence: Total number of single rooms and double rooms should equal total number of rooms");
            } else {
                residence.setUsers(admin);

                residenceRepository.save(residence);

                return "Residence successfully saved";
            }
        } else {
            return "Error saving residence: Admin with ID " + adminId + " does not exist";
        }
    }

    @Override
    public List<Residence> getAllResidences() {
        return residenceRepository.findAll();
    }

    @Override
    public Residence updateResidence(Residence updatedResidence, Long id, Integer adminId) {
        Optional<Residence> foundResidence = residenceRepository.findById(id);

        if (foundResidence.isPresent()) {
            Residence existingResidence = foundResidence.get();

            if (!existingResidence.getUsers().getId().equals(adminId)) {
                throw new RuntimeException("Unauthorized to update this residence");
            }

            existingResidence.setName(updatedResidence.getName());
            existingResidence.setSlogan(updatedResidence.getSlogan());
            existingResidence.setRegNo(updatedResidence.getRegNo());
            existingResidence.setProfileImage(updatedResidence.getProfileImage());
            existingResidence.setTotalNumberOfRooms(updatedResidence.getTotalNumberOfRooms());
            existingResidence.setTotalNumberOfSingleRooms(updatedResidence.getTotalNumberOfSingleRooms());
            existingResidence.setTotalNumberOfDoubleRooms(updatedResidence.getTotalNumberOfDoubleRooms());
            existingResidence.setNsfasDocument(updatedResidence.getNsfasDocument());
            existingResidence.setImages(updatedResidence.getImages());
            existingResidence.setUtility(updatedResidence.getUtility());

            return residenceRepository.save(existingResidence);
        } else {
            throw new RuntimeException("Residence with ID " + id + " not found");
        }
    }

    @Override
    public Optional<Residence> getResidenceById(Long resId) {
        Optional<Residence> foundResidence = residenceRepository.findById(resId);

        if (foundResidence.isPresent()) {
            return foundResidence;
        } else {

            throw new RuntimeException("Residence not with ID " + resId + "found");
        }
    }

    @Override
    public Optional<Residence> findResidenceByEmail(String email) {
        Optional<Residence> foundResidence = residenceRepository.findResidenceByEmail(email);

        if (foundResidence.isPresent()) {
            return foundResidence;
        } else {

            throw new RuntimeException("Residence not with Email " + email + "found");
        }
    }

    @Override
    public void deleteResidence(Long id) {
        residenceRepository.deleteById(id);
    }

}