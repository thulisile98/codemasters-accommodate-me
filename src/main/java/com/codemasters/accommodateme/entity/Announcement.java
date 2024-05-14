package com.codemasters.accommodateme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@Table(name="tbl_announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;

    private String heading;
    private String body;
    private String imageUrl;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id", nullable = false)
    private User users;

    @ManyToOne
    @JoinColumn(name = "resId", referencedColumnName = "residence_id", nullable = false)
    private Residence residence;

    public Announcement() {
        this.createdAt = Instant.now();
    }

}
