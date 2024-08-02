package com.wcci.final_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcci.final_project.dto.ReviewPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.Review;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.ReviewService;
import com.wcci.final_project.service.UserService;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewPayload reviewPayload) {
        Game game = gameService.getGameById(reviewPayload.getGameIds());
        User user = userService.getUserById(reviewPayload.getUserId());
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Review review = new Review();

        review.setText(reviewPayload.getText());

        review.setGame(game);
        review.setUser(user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable Long id) {
        Review foundReview = reviewService.getReviewById(id);
        if (foundReview == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> modifyReview(@PathVariable Long id, @RequestBody ReviewPayload reviewPayload) {
        Review existingReview = reviewService.getReviewById(id);
        if (existingReview == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingReview.setText(reviewPayload.getText());

        reviewService.updateReview(existingReview);

        return ResponseEntity.ok(existingReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReview(@PathVariable Long id) {
        boolean isDeleted = reviewService.deleteReview(id);

        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.noContent().build();
    }

}
