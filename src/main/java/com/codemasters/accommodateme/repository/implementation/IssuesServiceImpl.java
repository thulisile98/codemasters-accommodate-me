package com.codemasters.accommodateme.repository.implementation;

import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.enumeration.IssuesPriority;
import com.codemasters.accommodateme.enumeration.IssuesStatus;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.IssuesRepository;
import com.codemasters.accommodateme.repository.services.IssuesService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IssuesServiceImpl implements IssuesService {

    @Autowired
    private final IssuesRepository issuesRepository;

    @Autowired
    private final EntityManager entityManager;

    @Override
    public List<Issues> findAllIssues() {
        return issuesRepository.findAll();
    }

    @Override
    public List<Issues> findIssuesByPriority(IssuesPriority priority) {
        return issuesRepository.findByPriority(priority);
    }

    @Override
    public List<Issues> findIssuesByStatus(IssuesStatus status) {
        return issuesRepository.findByStatus(status);
    }

    @Override
    public List<Issues> findIssuesByTitle(String title) {
        return issuesRepository.findByTitleContaining(title);
    }

    @Override
    public List<Issues> findIssuesByDescription(String description) {
        return issuesRepository.findByDescriptionContaining(description);
    }

    @Override
    public List<Issues> findIssuesByReportedAt(Instant reportedAt) {
        return issuesRepository.findByReportedAt(reportedAt);
    }

    @Override
    public List<Issues> findIssuesBySolvedAt(Instant solvedAt) {
        return issuesRepository.findBySolvedAt(solvedAt);
    }

    @Override
    public Issues highPriority(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setPriority(IssuesPriority.HIGH);

        return issuesRepository.save(issues);
    }

    @Override
    public Issues mediumPriority(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setPriority(IssuesPriority.MEDIUM);

        return issuesRepository.save(issues);
    }

    @Override
    public Issues lowPriority(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setPriority(IssuesPriority.LOW);

        return issuesRepository.save(issues);
    }

    @Override
    public Issues solveIssue(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setStatus(IssuesStatus.SOLVED);

        return issuesRepository.save(issues);
    }

    @Override
    public Issues reportedIssue(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setStatus(IssuesStatus.REPORTED);

        return issuesRepository.save(issues);
    }

    @Override
    public Issues inProgressIssue(Long id) {
        Issues issues = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issues not found with ID: " + id));

        issues.setStatus(IssuesStatus.IN_PROGRESS);

        return issuesRepository.save(issues);
    }


    @Override
    public List<Issues> searchIssue(String title, String description, Instant reportedAt, Instant solvedAt, IssuesStatus status, IssuesPriority priority) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Issues> cq = cb.createQuery(Issues.class);
        Root<Issues> root = cq.from(Issues.class);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(cb.lower(cb.trim(root.get("title"))), "%" + title.toLowerCase() + "%"));
        }
        if (description != null && !description.isEmpty()) {
            predicates.add(cb.like(cb.lower(cb.trim(root.get("description"))), "%" + description.toLowerCase() + "%"));
        }
        if (reportedAt != null) {
            predicates.add(cb.equal(root.get("reportedAt"), reportedAt));
        }
        if (solvedAt != null) {
            predicates.add(cb.equal(root.get("solvedAt"), solvedAt));
        }
        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status));
        }
        if (priority != null) {
            predicates.add(cb.equal(root.get("priority"), priority));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
