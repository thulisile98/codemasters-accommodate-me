package com.codemasters.accommodateme.repository.repos;
import com.codemasters.accommodateme.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review save(Review review);
    List<Review> findAll();

    List<Review> findByResidenceResidenceId(Long residenceId);

    @Query("SELECT a FROM Review a WHERE LOWER(a.description) LIKE %:description% " +
            "AND a.postedAt = :postedAt")
    List<Review> searchReviews(String description, Instant postedAt);

}
