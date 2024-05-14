package com.codemasters.accommodateme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String description;
    private Instant createdAt;
    private String user;
    private String residence;
}
