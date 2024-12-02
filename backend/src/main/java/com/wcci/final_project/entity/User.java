package com.wcci.final_project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @JsonIgnoreProperties({ "user", "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Review> reviews;

    @JsonIgnoreProperties({ "user", "hibernateLazyInitializer", "handler" })
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    private Wishlist wishlist;

    public User() {
        this.reviews = new ArrayList<>();
        this.wishlist = new Wishlist();
    }

    public User(String email) {
        this.email = email;
        this.reviews = new ArrayList<>();
        this.wishlist = new Wishlist();
    }

    public User(String email, Wishlist wishlist) {
        this.email = email;
        this.wishlist = wishlist;
        this.reviews = new ArrayList<>();
    }

    public User(String email, List<Review> reviews) {
        this.email = email;
        this.reviews = reviews;
        this.wishlist = new Wishlist();
    }

    public User(String email, Wishlist wishlist, List<Review> reviews) {
        this.email = email;
        this.wishlist = wishlist;
        this.reviews = reviews;
    }
}