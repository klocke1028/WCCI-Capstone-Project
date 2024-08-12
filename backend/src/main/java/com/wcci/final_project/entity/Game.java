package com.wcci.final_project.entity;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"game\"")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    
    private String title;

    private double bestPrice;

    private double priceWhenAdded;

    private String shopWithBestPrice;    

    private String itadId;

    private String boxArtLink;

    private List<String> tags;

    @JsonIgnoreProperties({ "game", "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Wishlist wishlist;
    
    @JsonIgnoreProperties({ "game", "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
    
    public Game(String title, String itadId, String boxArtLink) {
        this.title = title;
        this.itadId = itadId;
        this.boxArtLink = boxArtLink;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, double bestPrice) {
        this.title = title;
        this.bestPrice = bestPrice;
        this.reviews = new ArrayList<>();
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, double bestPrice, List<Review> reviews) {
        this.title = title;
        this.bestPrice = bestPrice;
        this.reviews = reviews;
        this.priceAlerts = new ArrayList<>();
    }

    public Game(String title, List<PriceAlert> priceAlerts, double bestPrice) {
        this.title = title;
        this.priceAlerts = priceAlerts;
        this.bestPrice = bestPrice;
        this.reviews = new ArrayList<>();
    }

    public Game(String title, double bestPrice, List<Review> reviews, List<PriceAlert> priceAlerts) {
        this.title = title;
        this.bestPrice = bestPrice;
        this.reviews = reviews;
        this.priceAlerts = priceAlerts;
    }

    public Game(String title, String itadId, String boxArtLink, List<String> tags) {
        this.title = title;
        this.itadId = itadId;
        this.boxArtLink = boxArtLink;
        this.tags = tags;
    }

    public void setReviews(List<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(review -> review.setGame(null));
        }
        if (reviews != null) {
            reviews.forEach(review -> review.setGame(this));
        }
        this.reviews.clear();
        this.reviews.addAll(reviews);
    }

    public void setPriceAlerts(List<PriceAlert> priceAlerts) {
        if (this.priceAlerts != null) {
            this.priceAlerts.forEach(priceAlert -> priceAlert.setGame(null));
        }
        if (priceAlerts != null) {
            priceAlerts.forEach(priceAlert -> priceAlert.setGame(this));
        }
        this.priceAlerts.clear();
        this.priceAlerts.addAll(priceAlerts);
    }
    
    @Override
    public String toString() {
        String gameToString = title + "\n" +
                                "ITAD ID: " + itadId;
        return gameToString;
    }
}
