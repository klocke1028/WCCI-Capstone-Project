package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.repository.WishlistRepository;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist findWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public Wishlist updateWishlist(Wishlist updatedWishlist) {
        return wishlistRepository.save(updatedWishlist);
    }

    public boolean deleteWishlist(Long id) {
        if (!wishlistRepository.existsById(id)) {
            return false;
        }

        wishlistRepository.deleteById(id);
        return true;
    }
}
