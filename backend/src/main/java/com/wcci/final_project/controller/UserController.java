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

import com.wcci.final_project.entity.Review;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User foundUser = userService.getUserById(id);
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingUser.setEmail(updatedUser.getEmail());

        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);

        if(!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewByUserId(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Review> reviews = user.getReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}/wishlist")
    public ResponseEntity<Wishlist> getWishlistByUserId(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Wishlist wishlist = user.getWishlist();
        return ResponseEntity.ok(wishlist);
    }
}
