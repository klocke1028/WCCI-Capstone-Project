package com.wcci.final_project.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.PriceAlertService;

@Component
public class PriceTracker {

    @Autowired
    private GameService gameService;

    @Autowired
    private PriceAlertService priceAlertService;

    // @SuppressWarnings("null")
    @Scheduled(initialDelay = 10000, fixedRate = 300000)
    public void checkForNewBestPrice() throws IOException {
        List<Game> gamesInDatabase = gameService.getAllGames();
        List<String> gamesInDatabaseItadIds = new ArrayList<>();
        String shopIds = priceAlertService.getItadShopIds();

        for (Game gameInDatabase : gamesInDatabase) {
            String gameInDatabaseItadId = gameInDatabase.getItadId();

            gamesInDatabaseItadIds.add(gameInDatabaseItadId);
        }

        for (String gameInDatabaseItadId : gamesInDatabaseItadIds) {
            double newBestPrice = gameService.getBestPrice(shopIds, gameInDatabaseItadId);

            Game gameInDatabase = gameService.getGameByItadId(gameInDatabaseItadId);
            double gameInDatabaseCurrentBestPrice = gameInDatabase.getBestPrice();

            if (gameInDatabaseCurrentBestPrice != newBestPrice) gameInDatabaseCurrentBestPrice = newBestPrice;
        }
    }

    public String getItadShopIds() throws IOException {
        List<Integer> intItadShopIds = new ArrayList<>();

        URL getShopsUrl = new URL("https://api.isthereanydeal.com/service/shops/v1?country=US");
        HttpsURLConnection getShopsConnection = (HttpsURLConnection) getShopsUrl.openConnection();

        getShopsConnection.setRequestMethod("GET");

        int getShopsResponseCode = getShopsConnection.getResponseCode();

        if (getShopsResponseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader shopsBufferedReader = new BufferedReader(new InputStreamReader(getShopsConnection.getInputStream()));
            String shopsResponse = shopsBufferedReader.readLine();
            ObjectMapper shopsObjectMapper = new ObjectMapper();
            JsonNode shopsNode = shopsObjectMapper.readTree(shopsResponse);

            for (JsonNode shopNode : shopsNode) {
                int shopId = shopNode.path("id").asInt();
                intItadShopIds.add(shopId);
            }
        } else {
            System.out.println("Error in getting shop IDs. Response Code: " + getShopsResponseCode);
        }

        StringBuilder itadIdsBuilder = new StringBuilder();

        int index = 0;

        for (int shopId : intItadShopIds) {
            itadIdsBuilder.append(shopId + ",");

            if (index == intItadShopIds.size() - 1) {
                itadIdsBuilder.append(shopId);
                break;
            }

            index++;
        }

        String itadShopIds = itadIdsBuilder.toString();

        return itadShopIds;
    }   
}
