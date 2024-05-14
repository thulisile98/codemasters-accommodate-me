package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.AnnouncementDTO;
import com.codemasters.accommodateme.entity.Announcement;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.services.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@AllArgsConstructor
public class AnnouncementController {

    @Autowired
    private final AnnouncementService announcementsService;

    @PostMapping("/save/{adminId}/{resId}")
    public String addAnnouncement(@RequestBody Announcement announcement, @PathVariable Integer adminId, @PathVariable Long resId) {
        return announcementsService.addAnnouncement(announcement, resId, adminId);
    }


    @GetMapping("/all")
    public List<AnnouncementDTO> getAllAnnouncements() {
        return announcementsService.getAllAnnouncement();
    }

    @PutMapping("/update/{id}/{adminId}/{resId}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @RequestBody Announcement announcements,
            @PathVariable Long id,
            @PathVariable Integer adminId,
            @PathVariable Long resId) {

        try {
            AnnouncementDTO updatedAnnouncement = announcementsService.updateAnnouncement(announcements, id, resId, adminId);
            return ResponseEntity.ok(updatedAnnouncement);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException se) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



    @GetMapping("/find/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        try {
            AnnouncementDTO announcement = announcementsService.getAnnouncementById(id);
            return ResponseEntity.ok(announcement);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByAdminId(@PathVariable Integer adminId) {
        try {
            List<AnnouncementDTO> announcementsDTOs = announcementsService.getAnnouncementByAdminId(adminId);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/find/res/{resId}")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByResidenceId(@PathVariable Long resId) {
        try {
            List<AnnouncementDTO> announcementsDTOs = announcementsService.getAnnouncementByResidenceId(resId);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/find/date")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByDatePosted(@RequestParam Instant postedDate) {
        try {
            List<AnnouncementDTO> announcementsDTOs = announcementsService.getAnnouncementByDatePosted(postedDate);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public List<Announcement> searchAnnouncements(
            @RequestParam(required = false) String heading,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) Instant createdAt) {
        return announcementsService.searchAnnouncement(heading, body, createdAt);
    }
}
