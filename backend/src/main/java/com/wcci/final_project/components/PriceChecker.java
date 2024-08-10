package com.wcci.final_project.components;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wcci.final_project.controller.GameController;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.service.GameService;

@Component
public class PriceChecker {
    @Autowired
    private ApplicationContext applicationContext;

    @SuppressWarnings("null")
    @Scheduled(initialDelay = 20000, fixedRate = 60000)
    // 10000 milliseconds = 10 seconds
    // 300000 milliseconds = 5 minutes
    public void callApi() {
        GameController gameController = applicationContext.getBean(GameController.class);
        GameService gameService = applicationContext.getBean(GameService.class);

        ResponseEntity<List<Game>> response = gameController.getAllGames();
        System.out.println("API Response1: " + response);
        System.out.println("API Response2: " + response.getBody());
        System.out.println("API Response3: " + response.getStatusCode());

        List<Game> gamesInDatabase = response.getBody();

        for (Game gameInDatabase : gamesInDatabase) {

            try {
                // TODO change this to searchGameOnItadByItadId once every game has an itadId
                List<Game> itadGames = gameService.searchGamesByTitle(gameInDatabase.getTitle());
                Game itadGame = itadGames.get(0);
                /*
                 * Price is currently 0 because we're not YET grabbing the best price for a
                 * game. That's in progress.
                 */
                double price = itadGame.getBestPrice();
                System.out.println("itadGame price: " + price);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
