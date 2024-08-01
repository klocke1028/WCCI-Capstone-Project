package com.wcci.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.WishlistService;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Wishlist wishlist = wishlistService.getWishlistById(game.getId());
        if (wishlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        game.setTitle(game.getTitle());
        game.setPrice(game.getPrice());
        game.setWishlist(wishlist);

        gameService.saveGame(game);

        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
