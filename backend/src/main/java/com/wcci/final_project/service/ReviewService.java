package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.wcci.final_project.entity.Review;
import com.wcci.final_project.repository.ReviewRepository;


public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public void updateReview(Review updatedReview) {
        reviewRepository.save(updatedReview);
    }

    public boolean deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }

        reviewRepository.deleteById(id);
        return true;
    }
}
