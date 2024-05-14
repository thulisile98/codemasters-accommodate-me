package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.enumeration.IssuesPriority;
import com.codemasters.accommodateme.enumeration.IssuesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IssuesRepository extends JpaRepository<Issues,Long> {

    List<Issues> findByPriority(IssuesPriority priority);
    List<Issues> findByStatus(IssuesStatus status);
    List<Issues> findByTitleContaining(String title);
    List<Issues> findByDescriptionContaining(String description);
    List<Issues> findByReportedAt(Instant reportedAt);
    List<Issues> findBySolvedAt(Instant solvedAt);

    @Query("SELECT a FROM Issues a WHERE LOWER(a.title) LIKE %:title% " +
            "AND LOWER(a.description) LIKE %:description% " +
            "AND a.reportedAt = :reportedAt " +
            "AND a.solvedAt = :solvedAt " +
            "AND a.status = :status " +
            "AND a.priority = :priority")
    List<Issues> searchIssues(@Param("title") String title,
                              @Param("description") String description,
                              @Param("reportedAt") Instant reportedAt,
                              @Param("solvedAt") Instant solvedAt,
                              @Param("status") IssuesStatus status,
                              @Param("priority") IssuesPriority priority);
}
