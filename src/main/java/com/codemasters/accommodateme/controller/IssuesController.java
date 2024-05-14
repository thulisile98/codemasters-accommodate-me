package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.enumeration.IssuesPriority;
import com.codemasters.accommodateme.enumeration.IssuesStatus;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.services.IssuesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
@AllArgsConstructor
public class IssuesController {

    private final IssuesService issuesService;

    @GetMapping("/all")
    public ResponseEntity<List<Issues>> getAllIssues() {
        return ResponseEntity.ok(issuesService.findAllIssues());
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Issues>> findIssuesByPriority(@PathVariable IssuesPriority priority) {
        return ResponseEntity.ok(issuesService.findIssuesByPriority(priority));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Issues>> findIssuesByStatus(@PathVariable IssuesStatus status) {
        return ResponseEntity.ok(issuesService.findIssuesByStatus(status));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Issues>> findIssuesByTitle(@PathVariable String title) {
        return ResponseEntity.ok(issuesService.findIssuesByTitle(title));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Issues>> findIssuesByDescription(@PathVariable String description) {
        return ResponseEntity.ok(issuesService.findIssuesByDescription(description));
    }

    @GetMapping("/reportedAt")
    public ResponseEntity<List<Issues>> findIssuesByReportedAt(@RequestParam Instant reportedAt) {
        return ResponseEntity.ok(issuesService.findIssuesByReportedAt(reportedAt));
    }

    @GetMapping("/solvedAt")
    public ResponseEntity<List<Issues>> findIssuesBySolvedAt(@RequestParam Instant solvedAt) {
        return ResponseEntity.ok(issuesService.findIssuesBySolvedAt(solvedAt));
    }

    @PatchMapping("/{id}/highPriority/")
    public ResponseEntity<Issues> setHighPriority(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.highPriority(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/priority/mediumPriority")
    public ResponseEntity<Issues> setMediumPriority(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.mediumPriority(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}/priority/lowPriority")
    public ResponseEntity<Issues> setLowPriority(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.lowPriority(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status/reported")
    public ResponseEntity<Issues> markAsReported(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.reportedIssue(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status/solved")
    public ResponseEntity<Issues> markAsSolved(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.solveIssue(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status/inProgress")
    public ResponseEntity<Issues> markInProgress(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issuesService.inProgressIssue(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Issues> searchIssues(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) Instant reportedAt,
                                     @RequestParam(required = false) Instant solvedAt,
                                     @RequestParam(required = false) IssuesStatus status,
                                     @RequestParam(required = false) IssuesPriority priority) {
        return issuesService.searchIssue(title, description, reportedAt, solvedAt, status, priority);
    }

}
