package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.Review;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.services.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/public")
@AllArgsConstructor
public class ReviewController {

    @Autowired
    private final ReviewService reviewsService;

//    @PostMapping("/saveReview")
//    public Review submitReview(@RequestBody Review review) {
//        return reviewsService.save(review);
//    }
//
//    @GetMapping("/getAll")
//    @ResponseBody
//    public List<Review> getAllReviews(){
//        return reviewsService.getAllReviews();
//    }


    @GetMapping("/find/{resId}")
    public ResponseEntity<List<Review>> getAllResidenceReviews(@PathVariable Long resId) {
        try {
            List<Review> reviews = reviewsService.findByResidenceId(resId);
            return ResponseEntity.ok(reviews);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Review> searchReviews(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Instant postedAt) {
        return reviewsService.searchReviews(description, postedAt);
    }

}
