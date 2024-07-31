package com.wcci.final_project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class GamePayload {

    private String title;

    private double gamePrice;

    private List<Long> gameReviewIds;

    private Long wishlistId;

    private List<Long> priceAlertIds;

    public GamePayload() {
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(String title, double gamePrice) {
        this.title = title;
        this.gamePrice =  gamePrice;
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(String title, double gamePrice, Long wishlistId) {
        this.title = title;
        this.gamePrice =  gamePrice;
        this.wishlistId = wishlistId;
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(String title, double gamePrice, Long wishlistId, List<Long> gameReviewIds) {
        this.title = title;
        this.gamePrice =  gamePrice;
        this.wishlistId = wishlistId;
        this.gameReviewIds = gameReviewIds;
        this.priceAlertIds = new ArrayList<>();
        }
    }