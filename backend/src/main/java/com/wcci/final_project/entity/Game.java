package com.wcci.final_project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private double price;

    @OneToMany(mappedBy = "games", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @JsonIgnoreProperties("game")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wishlist wishlist;
    
    @OneToMany(mappedBy = "games", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceAlert> priceAlerts;

    public Game() {
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(double price) {
        this.price = price;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(double price, List<Review> reviews) {
        this.price = price;
        this.reviews = reviews;
        this.priceAlerts = new ArrayList<>();
    }

    public Game(List<PriceAlert> priceAlerts, double price) {
        this.priceAlerts = priceAlerts;
        this.price = price;
        this.reviews = new ArrayList<>();
    }

    public Game(double price, List<Review> reviews, List<PriceAlert> priceAlerts) {
        this.price = price;
        this.reviews = new ArrayList<>();
        this.priceAlerts = priceAlerts;
    }
}
