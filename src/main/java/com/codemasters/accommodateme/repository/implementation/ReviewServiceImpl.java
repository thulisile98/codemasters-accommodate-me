package com.codemasters.accommodateme.repository.implementation;

import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.Review;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
import com.codemasters.accommodateme.repository.repos.ReviewRepository;
import com.codemasters.accommodateme.repository.services.ReviewService;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final ResidenceRepository residenceRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    private final EntityManager entityManager;

    @Override
    public List<Review> findByResidenceId(Long resId) {
        Optional<Residence> foundResidence = residenceRepository.findById(resId);

        if (foundResidence.isPresent()) {
            return reviewRepository.findByResidenceResidenceId(resId);
        } else {
            throw new EntityNotFoundException("Residence with ID: " + resId + " not found");
        }
    }

    @Override
    public List<Review> searchReviews(String description, Instant postedAt) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> root = cq.from(Review.class);

        List<Predicate> predicates = new ArrayList<>();
        if (description != null && !description.isEmpty()) {
            predicates.add(cb.like(cb.lower(cb.trim(root.get("description"))), "%" + description.toLowerCase() + "%"));
        }

        if (postedAt != null) {
            predicates.add(cb.equal(root.get("postedAt"), postedAt));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }

}
