package com.wcci.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wcci.final_project.entity.User;
import com.wcci.final_project.repository.UserRepository;



@Component
public class UserPopulator implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        User user1 = new User("katlynlocke1028@gmail.com");
        User user2 = new User("Ross_Mcdonald@outlook.com");
        User user3 = new User("deidra.zamonskiblake@gmail.com");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}

