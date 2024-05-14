package com.codemasters.accommodateme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AnnouncementDTO {
    private Long announcementId;
    private String heading;
    private String body;
    private String imageUrl;
    private String user;
    private String residence;
    private Instant createdAt;
}
