package com.wcci.final_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {

    @Id
    @GeneratedValue
    private Long id;

    private double newPrice;

    @JsonIgnoreProperties("price-alert")
    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;
}
