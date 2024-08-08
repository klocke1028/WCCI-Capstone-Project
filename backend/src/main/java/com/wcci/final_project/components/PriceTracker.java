package com.wcci.final_project.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PriceTracker {

        // public Double trackPrice(Long gameId) throws IOException {
    //     PriceAlert priceAlert = new PriceAlert();

    //     Game priceAlertGame = gameService.findGameById(gameId);
    //     priceAlert.setGame(priceAlertGame);

    //     String gameTitle = priceAlertGame.getTitle();

    //     List<Integer> shopIds = getItadShopIds();

    //     Runnable track = new Runnable() {
    //         @Override
    //         public void run() {
    //             for (int shopId : shopIds) {
    //                 // check the price for the game at every shop and return a price if it's lower than the current one
    //             }
                
    //         }
    //     };    
    // }

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
