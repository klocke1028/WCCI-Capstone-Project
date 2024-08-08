package com.wcci.final_project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcci.final_project.entity.PriceAlert;
import com.wcci.final_project.repository.PriceAlertRepository;

@Service
public class PriceAlertService {

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    public PriceAlert createPriceAlert(PriceAlert priceAlert) {
        return priceAlertRepository.save(priceAlert);
    }

    public PriceAlert findPriceAlertById(Long id) {
        return priceAlertRepository.findById(id).orElse(null);
    }

    public boolean deletePriceAlert(Long id) {
        if (!priceAlertRepository.existsById(id)) {
            return false;
        }

        priceAlertRepository.deleteById(id);
        return true;
    }

    public String getItadShopIds() throws IOException {
        List<Integer> intItadShopIds = new ArrayList<>();

        URL getShopsUrl = new URL("https://api.isthereanydeal.com/service/shops/v1?country=US");
        HttpsURLConnection getShopsConnection = (HttpsURLConnection) getShopsUrl.openConnection();

        getShopsConnection.setRequestMethod("GET");

        int getShopsResponseCode = getShopsConnection.getResponseCode();

        if (getShopsResponseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader shopsBufferedReader = new BufferedReader(
                    new InputStreamReader(getShopsConnection.getInputStream()));
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
