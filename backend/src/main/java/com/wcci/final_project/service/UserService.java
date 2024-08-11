package com.wcci.final_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
