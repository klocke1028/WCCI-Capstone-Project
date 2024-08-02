package com.wcci.final_project.dto;

import com.wcci.final_project.entity.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPayload {

    private String text;

    private Long gameId;

    private Long userId;
}
