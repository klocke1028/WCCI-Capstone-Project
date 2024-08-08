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

    private String itadId;
    
    private String boxArtUrl;

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

    public GamePayload(String title, String itadId) {
        this.title = title;
        this.itadId =  itadId;
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(String title, String itadId, double gamePrice) {
        this.title = title;
        this.itadId =  itadId;
        this.gamePrice = gamePrice;
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(String title, String itadId, String boxArtUrl) {
        this.title = title;
        this.itadId =  itadId;
        this.boxArtUrl = boxArtUrl;
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

        public GamePayload(String title, String itadId, String boxArtUrl, double gamePrice) {
            this.title = title;
            this.itadId =  itadId;
            this.boxArtUrl = boxArtUrl;
            this.gamePrice = gamePrice;
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
