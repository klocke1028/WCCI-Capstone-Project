package com.wcci.final_project.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewPayload {

    private String text;

    private List<Long> gameIds;

    private Long userId;

    public ReviewPayload() {
        this.gameIds = new ArrayList<>();
    }
}
