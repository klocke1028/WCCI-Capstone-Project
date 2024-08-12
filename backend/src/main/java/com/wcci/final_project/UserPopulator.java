package com.wcci.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wcci.final_project.entity.User;
import com.wcci.final_project.entity.Wishlist;
import com.wcci.final_project.repository.UserRepository;
import com.wcci.final_project.repository.WishlistRepository;



@Component
public class UserPopulator implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        Wishlist wishlistForUser1 = new Wishlist();
        Wishlist wishlistForUser2 = new Wishlist();
        Wishlist wishlistForUser3 = new Wishlist();

        User user1 = new User("katlynlocke1028@gmail.com", wishlistForUser1);
        User user2 = new User("Ross_Mcdonald@outlook.com", wishlistForUser2);
        User user3 = new User("deidra.zamonskiblake@gmail.com", wishlistForUser3);

        wishlistForUser1.setUser(user1);
        wishlistForUser2.setUser(user2);
        wishlistForUser3.setUser(user3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        wishlistRepository.save(wishlistForUser1);
        wishlistRepository.save(wishlistForUser2);
        wishlistRepository.save(wishlistForUser3);
    }
}

