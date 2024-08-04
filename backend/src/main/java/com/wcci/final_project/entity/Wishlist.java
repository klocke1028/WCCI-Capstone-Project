package com.wcci.final_project.entity;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"wishlist\"")
@Getter
@Setter
public class Wishlist {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Wishlist() {
        this.games = new ArrayList<>();
    }

    public Wishlist(User user) {
        this.user = user;
        this.games = new ArrayList<>();
    }

    public Wishlist(User user, List<Game> games) {
        this.user = user;
        this.games = games;
    }
}
