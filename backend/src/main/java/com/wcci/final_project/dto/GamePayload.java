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

    private double gamePrice;

    private List<Long> gameReviewIds;

    private Long wishlistId;

    private List<Long> priceAlertIds;

    public GamePayload() {
        this.gameReviewIds = new ArrayList<>();
        this.priceAlertIds = new ArrayList<>();
        }

    public GamePayload(double gamePrice) {
        this.gameReviewIds = new ArrayList<>();
        }
}
