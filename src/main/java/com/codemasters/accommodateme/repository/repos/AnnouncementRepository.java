package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByUsersId(Integer userId);
    List<Announcement> findByResidenceResidenceId(Long residenceId);
    List<Announcement> findByCreatedAt(Instant createdAt);

    @Query("SELECT a FROM Announcements a WHERE LOWER(a.heading) LIKE %:heading% " +
            "AND LOWER(a.body) LIKE %:body% AND a.createdAt = :createdAt")
    List<Announcement> searchAnnouncement(String heading, String body, Instant createdAt);
}
