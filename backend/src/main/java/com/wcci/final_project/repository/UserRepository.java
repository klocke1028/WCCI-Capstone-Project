package com.wcci.final_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcci.final_project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}