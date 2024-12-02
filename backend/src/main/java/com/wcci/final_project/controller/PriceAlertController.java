package com.wcci.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcci.final_project.dto.PriceAlertPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.PriceAlert;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.PriceAlertService;

import java.io.IOException;

@RestController
@RequestMapping("/price-alerts")
public class PriceAlertController {

    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<PriceAlert> addPriceAlert(@RequestBody PriceAlertPayload priceAlertPayload) {
        PriceAlert priceAlert = new PriceAlert();

        Double priceAlertNewPrice = priceAlertPayload.getNewPrice();
        Long priceAlertGameId = priceAlertPayload.getGameId();

        Game priceAlertGame = gameService.findGameById(priceAlertGameId);

        if (priceAlertGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (priceAlertNewPrice != null)
            priceAlert.setNewPrice(priceAlertNewPrice);
        priceAlert.setGame(priceAlertGame);

        return new ResponseEntity<>(priceAlertService.createPriceAlert(priceAlert), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceAlert> getPriceAlertById(@PathVariable("id") Long id) {
        PriceAlert foundPriceAlert = priceAlertService.findPriceAlertById(id);

        if (foundPriceAlert == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(foundPriceAlert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceAlert> updatePriceAlert(@PathVariable("id") Long id,
            @RequestBody PriceAlertPayload priceAlertPayload) {
        PriceAlert existingPriceAlert = priceAlertService.findPriceAlertById(id);

        Double priceAlertNewPrice = priceAlertPayload.getNewPrice();
        Long priceAlertGameId = existingPriceAlert.getGame().getId();

        Game priceAlertGame = gameService.findGameById(priceAlertGameId);

        if (priceAlertGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingPriceAlert.setNewPrice(priceAlertNewPrice);
        existingPriceAlert.setGame(priceAlertGame);

        return new ResponseEntity<>(priceAlertService.createPriceAlert(existingPriceAlert), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePriceAlert(@PathVariable("id") Long id) {
        boolean isDeleted = priceAlertService.deletePriceAlert(id);

        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shop-ids")
    public ResponseEntity<String> getItadShopIds() throws IOException {
        String itadShopIds = priceAlertService.getItadShopIds();

        return ResponseEntity.ok(itadShopIds);
    }

}