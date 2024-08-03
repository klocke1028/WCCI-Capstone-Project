package com.wcci.final_project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
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
import com.wcci.final_project.dto.GamePayload;
import com.wcci.final_project.dto.SearchPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.PriceAlert;
import com.wcci.final_project.entity.Review;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.PriceAlertService;
import com.wcci.final_project.service.ReviewService;


@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PriceAlertService priceAlertService;

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody GamePayload gamePayload) {
        Game game = new Game();

        game.setTitle(gamePayload.getTitle());
        game.setPrice(gamePayload.getGamePrice());
        
        List<Long> gameReviewIds = gamePayload.getGameReviewIds();
        List<Long> gamePriceAlertIds = gamePayload.getPriceAlertIds();        
        
        if (!(gameReviewIds == null)) {
            List<Review> gameReviews = new ArrayList<>();
            for (Long reviewId : gameReviewIds) {
                Review review = reviewService.getReviewById(reviewId);
                if(!(review == null)) gameReviews.add(review);
            }
            game.setReviews(gameReviews);
        } 

        if (!(gamePriceAlertIds == null)) {
            List<PriceAlert> gamePriceAlerts = new ArrayList<>();
            for (Long priceAlertId : gamePriceAlertIds) {
                PriceAlert priceAlert = priceAlertService.getPriceAlertById(priceAlertId);
                if(!(priceAlert == null)) gamePriceAlerts.add(priceAlert);
            }
            game.setPriceAlerts(gamePriceAlerts);
        } 

        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findGameById(@PathVariable Long id) {
        Game foundGame = gameService.getGameById(id);

        if (foundGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(foundGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody GamePayload gamePayload) {
        Game existingGame = gameService.getGameById(id);

        existingGame.setTitle(gamePayload.getTitle());
        existingGame.setPrice(gamePayload.getGamePrice());
        
        List<Long> gameReviewIds = gamePayload.getGameReviewIds();
        List<Long> gamePriceAlertIds = gamePayload.getPriceAlertIds();        
        
        if (!(gameReviewIds == null)) {
            List<Review> gameReviews = new ArrayList<>();
            for (Long reviewId : gameReviewIds) {
                Review review = reviewService.getReviewById(reviewId);
                if(!(review == null)) gameReviews.add(review);
            }
            existingGame.setReviews(gameReviews);
        } 

        if (!(gamePriceAlertIds == null)) {
            List<PriceAlert> gamePriceAlerts = new ArrayList<>();
            for (Long priceAlertId : gamePriceAlertIds) {
                PriceAlert priceAlert = priceAlertService.getPriceAlertById(priceAlertId);
                if(!(priceAlert == null)) gamePriceAlerts.add(priceAlert);
            }
            existingGame.setPriceAlerts(gamePriceAlerts);
        } 

        return new ResponseEntity<>(existingGame, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGame(@PathVariable Long id) {
        boolean isDeleted = gameService.deleteGame(id);

        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchForGamesByTitle(@RequestBody SearchPayload searchPayload) throws IOException {

        String searchTerm = searchPayload.getSearchTerm();

        List<Game> searchResults = gameService.searchForGamesByTitle(searchTerm);

        if (searchResults == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(searchResults);
    }

}
