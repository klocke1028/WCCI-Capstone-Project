package com.wcci.final_project.entity;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    
    private String title;

    private double price;

    private String itadId;

    private String boxArtLink;

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

    public Game(String title) {
        this.title = title;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, String itadId) {
        this.title = title;
        this.itadId = itadId;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, double price) {
        this.title = title;
        this.price = price;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, double price, List<Review> reviews) {
        this.title = title;
        this.price = price;
        this.reviews = reviews;
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, List<PriceAlert> priceAlerts, double price) {
        this.title = title;
        this.priceAlerts = priceAlerts;
        this.price = price;
        this.reviews = new ArrayList<>();
    }

    public Game(String title, double price, List<Review> reviews, List<PriceAlert> priceAlerts) {
        this.title = title;
        this.price = price;
        this.reviews = new ArrayList<>();
        this.priceAlerts = priceAlerts;
    }

    @Override
    public String toString() {
        String gameToString = title + "\n" +
                                "ITAD ID: " + itadId;
        return gameToString;
    }
}
