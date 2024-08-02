package com.wcci.final_project.controller;

import java.util.ArrayList;
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

import com.wcci.final_project.dto.WishlistPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.UserService;
import com.wcci.final_project.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<Wishlist> addWishlist(@RequestBody WishlistPayload wishlistPayload) {
        User user = userService.getUserById(wishlistPayload.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUser(user);

        List<Long> wishlistGameIds = wishlistPayload.getGameIds();
        if(!(wishlistGameIds == null)) {
            List<Game> wishlistGames = new ArrayList<>();
            for (Long gameId : wishlistGameIds) {
                Game game = gameService.getGameById(gameId);
                if(!(game == null)) wishlistGames.add(game);
            }
            wishlist.setGames(wishlistGames);
        }
        
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> findWishlistById(@PathVariable Long id) {
        Wishlist foundWishlist = wishlistService.getWishlistById(id);
        if (foundWishlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundWishlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long id, @RequestBody WishlistPayload wishlistPayload) {
        User user = userService.getUserById(wishlistPayload.getUserId());
        Wishlist existingWishlist = wishlistService.getWishlistById(id);
        if (existingWishlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingWishlist.setUser(user);

        return ResponseEntity.ok(existingWishlist);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishlist(@PathVariable Long id) {
        boolean isDeleted = wishlistService.deleteWishlist(id);

        if(!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }
}
