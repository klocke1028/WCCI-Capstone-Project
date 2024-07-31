package com.wcci.final_project.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistPayload {

    private List<Long> gameIds;

    private Long userId;

    public WishlistPayload() {
        this.gameIds = new ArrayList<>();
    }

    public WishlistPayload(Long userId) {
        this.userId = userId;
        this.gameIds = new ArrayList<>();
    }

    public WishlistPayload(Long userId, List<Long> gameIds) {
        this.userId = userId;
        this.gameIds = gameIds;
    }
}
