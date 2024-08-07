package com.wcci.final_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"review\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @JsonIgnoreProperties({ "reviews", "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @JsonIgnoreProperties({ "reviews", "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
