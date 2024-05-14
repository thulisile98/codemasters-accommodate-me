package com.codemasters.accommodateme.repository.services;

import com.codemasters.accommodateme.dto.AnnouncementDTO;
import com.codemasters.accommodateme.entity.Announcement;

import java.time.Instant;
import java.util.List;

public interface AnnouncementService {
    String addAnnouncement(Announcement announcement, Long resId, Integer adminId);

    List<AnnouncementDTO> getAllAnnouncement();

    AnnouncementDTO updateAnnouncement(Announcement announcement, Long id, Long resId, Integer adminId);

    AnnouncementDTO getAnnouncementById(Long id);

    List<AnnouncementDTO> getAnnouncementByAdminId(Integer adminId);

    List<AnnouncementDTO> getAnnouncementByResidenceId(Long residenceId);

    List<AnnouncementDTO> getAnnouncementByDatePosted(Instant createdAt);

    List<Announcement> searchAnnouncement(String heading, String body, Instant createdAt);
}
