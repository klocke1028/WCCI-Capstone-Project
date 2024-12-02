package com.wcci.final_project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcci.final_project.dto.GamePayload;
import com.wcci.final_project.dto.WishlistPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.Review;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.PriceAlertService;
import com.wcci.final_project.service.UserService;
import com.wcci.final_project.service.WishlistService;
import com.wcci.final_project.service.ReviewService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PriceAlertService priceAlertService;

    @PostMapping
    public ResponseEntity<Wishlist> addWishlist(@RequestBody WishlistPayload wishlistPayload) {
        Wishlist newWishlist = new Wishlist();

        Long newWishlistUserId = wishlistPayload.getUserId();

        User newWishlistUser = userService.findUserById(newWishlistUserId);

        if (newWishlistUser != null) {
            newWishlist.setUser(newWishlistUser);
            newWishlistUser.setWishlist(newWishlist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return new ResponseEntity<>(wishlistService.createWishlist(newWishlist), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable("id") Long id) {
        Wishlist foundWishlist = wishlistService.findWishlistById(id);

        if (foundWishlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(foundWishlist);
    }

    @PostMapping("/{id}/add-game")
    public ResponseEntity<Wishlist> addGameToWishlist(@PathVariable("id") Long id, @RequestBody GamePayload gamePayload)
            throws IOException {
        String newGameItadId = gamePayload.getItadId();
        Wishlist existingWishlist = wishlistService.findWishlistById(id);

        List<Game> gamesInDatabase = gameService.getAllGames();
        List<Game> gamesInExistingWishlist = existingWishlist.getGames();
        List<Game> againGamesInExistingWishlist = new ArrayList<>();

        for (Game gameInExistingWishlist : gamesInExistingWishlist) {
            againGamesInExistingWishlist.add(gameInExistingWishlist);
        }

        boolean isGameAlreadyInWishlist = false;
        boolean isGameAlreadyInDatabase = false;

        for (Game gameInExistingWishlist : againGamesInExistingWishlist) {
            String gameInExistingWishlistItadId = gameInExistingWishlist.getItadId();

            if (gameInExistingWishlistItadId.equals(newGameItadId)) {
                isGameAlreadyInWishlist = true;
                break;
            }
        }

        Game databaseGame = new Game();

        if (!isGameAlreadyInWishlist) {
            for (Game gameInDatabase : gamesInDatabase) {
                String gameInDatabaseItadId = gameInDatabase.getItadId();

                if (gameInDatabaseItadId.equals(newGameItadId)) {
                    isGameAlreadyInDatabase = true;
                    databaseGame = gameInDatabase;
                    break;
                }
            }
        }

        if (isGameAlreadyInDatabase) {
            againGamesInExistingWishlist.add(databaseGame);
        } else {
            Game newGame = new Game();

            String newGameTitle = gamePayload.getTitle();

            newGame.setTitle(newGameTitle);

            if (newGameItadId != null)
                newGame.setItadId(newGameItadId);

            String newGameBoxArtUrl = gamePayload.getBoxArtUrl();

            if (newGameBoxArtUrl != null)
                newGame.setBoxArtLink(newGameBoxArtUrl);

            String shopIds = priceAlertService.getItadShopIds();

            newGameItadId = '"' + newGameItadId + '"';

            double priceWhenAdded = gameService.getBestPrice(shopIds, newGameItadId);

            if (priceWhenAdded != 0.0)
                newGame.setPriceWhenAdded(priceWhenAdded);

            List<Long> gameReviewIds = gamePayload.getGameReviewIds();

            if (gameReviewIds != null && !gameReviewIds.isEmpty()) {
                List<Review> gameReviews = new ArrayList<>();

                for (Long gameReviewId : gameReviewIds) {
                    Review gameReview = reviewService.findReviewById(gameReviewId);

                    if (gameReview != null)
                        gameReviews.add(gameReview);
                }

                newGame.setReviews(gameReviews);
            }

            newGame.setWishlist(existingWishlist);

            Game savedNewGame = gameService.saveGame(newGame);
            againGamesInExistingWishlist.add(savedNewGame);
            existingWishlist.setGames(againGamesInExistingWishlist);
        }

        return ResponseEntity.ok(wishlistService.updateWishlist(existingWishlist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Wishlist> removeGameFromWishlist(@PathVariable("id") Long id,
            @RequestBody GamePayload gamePayload) {
        Wishlist existingWishlist = wishlistService.findWishlistById(id);

        if (existingWishlist == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<Game> wishlistGames = existingWishlist.getGames();
        List<Game> againGamesInExistingWishlist = new ArrayList<>();

        againGamesInExistingWishlist.addAll(wishlistGames);

        int index = 0;

        for (Game wishlistGame : againGamesInExistingWishlist) {
            Long wishlistGameId = wishlistGame.getId();
            if (wishlistGame.getItadId().equals(gamePayload.getItadId())) {
                Game game = gameService.findGameById(wishlistGameId);

                if (game != null) {
                    againGamesInExistingWishlist.remove(index);
                    break;
                }

            } else {
                index++;
            }
        }

        existingWishlist.setGames(againGamesInExistingWishlist);

        return ResponseEntity.ok(wishlistService.updateWishlist(existingWishlist));
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<Game>> getGamesByWishlistId(@PathVariable("id") Long id) {
        Wishlist existingWishlist = wishlistService.findWishlistById(id);

        if (existingWishlist == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<Game> existingWishlistGames = existingWishlist.getGames();

        return ResponseEntity.ok(existingWishlistGames);
    }
}