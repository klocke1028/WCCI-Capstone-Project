package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.repository.WishlistRepository;

public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist createReview(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public void updateReview(Wishlist updatedWishlist) {
        wishlistRepository.save(updatedWishlist);
    }

    public boolean deleteWishlist(Long id) {
        if (!wishlistRepository.existsById(id)) {
            return false;
        }

        wishlistRepository.deleteById(id);
        return true;
    }
}
