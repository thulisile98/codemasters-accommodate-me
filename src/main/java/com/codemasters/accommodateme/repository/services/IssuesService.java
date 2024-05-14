package com.codemasters.accommodateme.repository.services;

import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.enumeration.IssuesPriority;
import com.codemasters.accommodateme.enumeration.IssuesStatus;

import java.time.Instant;
import java.util.List;

public interface IssuesService {

    List<Issues> findAllIssues();

    List<Issues> findIssuesByPriority(IssuesPriority priority);

    List<Issues> findIssuesByStatus(IssuesStatus status);

    List<Issues> findIssuesByTitle(String title);

    List<Issues> findIssuesByDescription(String description);

    List<Issues> findIssuesByReportedAt(Instant reportedAt);

    List<Issues> findIssuesBySolvedAt(Instant solvedAt);

    Issues highPriority(Long id);

    Issues mediumPriority(Long id);

    Issues lowPriority(Long id);

    Issues solveIssue(Long id);

    Issues reportedIssue(Long id);

    Issues inProgressIssue(Long id);

    List<Issues> searchIssue(String title,
                              String description,
                              Instant reportedAt,
                              Instant solvedAt,
                              IssuesStatus status,
                              IssuesPriority priority);
}
