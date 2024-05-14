package com.codemasters.accommodateme.entity;

import com.codemasters.accommodateme.enumeration.IssuesPriority;
import com.codemasters.accommodateme.enumeration.IssuesStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_issues")
public class Issues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issues_id")
    private Long issuesId;

    private String title;

    private String description;

    @Column(name = "reported_at", columnDefinition = "TIMESTAMP")
    private Date reportedAt;

    @Column(name = "solved_at", columnDefinition = "TIMESTAMP")
    private Date solvedAt;

    @ManyToOne
    @JoinColumn(name = "id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "residence_id", nullable = true)
    private Residence residence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuesStatus status = IssuesStatus.REPORTED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuesPriority priority = IssuesPriority.MEDIUM;

}