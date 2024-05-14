package com.codemasters.accommodateme.repository.implementation;

import com.codemasters.accommodateme.dto.AnnouncementDTO;
import com.codemasters.accommodateme.entity.Announcement;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.AnnouncementRepository;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.repository.services.AnnouncementService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private final AnnouncementRepository announcementRepository;

    @Autowired
    private final UserRepo userRepository;
    @Autowired
    private final ResidenceRepository residenceRepository;

    private final EntityManager entityManager;

    @Override
    public String addAnnouncement(Announcement announcement, Long resId, Integer adminId) {
        Optional<Residence> resFound = residenceRepository.findById(resId);
        Optional<User> adminFound = userRepository.findById(adminId);

        if (adminFound.isPresent() && resFound.isPresent()) {
            User user = adminFound.get();
            Residence residence = resFound.get();

            announcement.setUsers(user);
            announcement.setResidence(residence);

            announcementRepository.save(announcement);

            return "Announcement successfully saved";
        } else {
            return "Error saving announcement: Admin with ID " + adminId + " or Residence with ID " + resId + " does not exist";
        }
    }


    @Override
    public AnnouncementDTO updateAnnouncement(Announcement announcement, Long id, Long resId, Integer adminId) {
        Optional<Announcement> foundAnnouncement = announcementRepository.findById(id);

        if (foundAnnouncement.isEmpty()) {
            throw new EntityNotFoundException("Announcement with ID " + id + " not found");
        }

        Announcement existingAnnouncement = foundAnnouncement.get();

        if (!existingAnnouncement.getResidence().getResidenceId().equals(resId)) {
            throw new EntityNotFoundException("Residence ID does not match the original residence ID of the announcement");
        }

        if (!existingAnnouncement.getUsers().getId().equals(adminId)) {
            throw new SecurityException("Admin ID does not match the original admin ID of the announcement");
        }

        existingAnnouncement.setBody(announcement.getBody());
        existingAnnouncement.setHeading(announcement.getHeading());
        existingAnnouncement.setImageUrl(announcement.getImageUrl());

        Announcement updatedAnnouncement = announcementRepository.save(existingAnnouncement);

        return convertToDTO(updatedAnnouncement);
    }

    @Override
    public AnnouncementDTO getAnnouncementById(Long id) {
        Optional<Announcement> foundAnnouncement = announcementRepository.findById(id);

        if (foundAnnouncement.isPresent()) {
            return convertToDTO(foundAnnouncement.get());
        } else {

            throw new EntityNotFoundException("Announcement with ID " + id + " not found");
        }
    }


    @Override
    public List<AnnouncementDTO> getAllAnnouncement() {
        List<Announcement> announcement = announcementRepository.findAll();
        return announcement.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnnouncementDTO convertToDTO(Announcement announcement) {
        return new AnnouncementDTO(
                announcement.getAnnouncementId(),
                announcement.getHeading(),
                announcement.getBody(),
                announcement.getImageUrl(),
                (announcement.getUsers() != null) ? announcement.getUsers().getEmail() : null,
                (announcement.getResidence() != null) ? announcement.getResidence().getName() : null,
                announcement.getCreatedAt()
        );
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementByAdminId(Integer adminId) {
        if (adminId == null) {
            throw new RuntimeException("Admin ID cannot be null");
        }

        List<Announcement> announcement = announcementRepository.findByUsersId(adminId);
        return announcement.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementByResidenceId(Long resId) {
        if (resId == null) {
            throw new RuntimeException("Residence ID cannot be null");
        }

        List<Announcement> announcement = announcementRepository.findByResidenceResidenceId(resId);
        return announcement.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementByDatePosted(Instant createdAt) {
        if (createdAt == null) {
            throw new RuntimeException("Residence ID cannot be null");
        }

        List<Announcement> announcement = announcementRepository.findByCreatedAt(createdAt);
        return announcement.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Announcement> searchAnnouncements(String heading, String body, Instant createdAt) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> cq = cb.createQuery(Announcement.class);
        Root<Announcement> root = cq.from(Announcement.class);

        List<Predicate> predicates = new ArrayList<>();
        if (heading != null && !heading.isEmpty()) {
            predicates.add(cb.like(cb.lower(cb.trim(root.get("heading"))), "%" + heading.toLowerCase() + "%"));
        }
        if (body != null && !body.isEmpty()) {
            predicates.add(cb.like(cb.lower(cb.trim(root.get("body"))), "%" + body.toLowerCase() + "%"));
        }
        if (createdAt != null) {
            predicates.add(cb.equal(root.get("createdAt"), createdAt));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
