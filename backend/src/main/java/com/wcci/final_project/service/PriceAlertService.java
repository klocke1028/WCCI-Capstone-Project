package com.wcci.final_project.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.PriceAlert;
import com.wcci.final_project.repository.PriceAlertRepository;

@Service
public class PriceAlertService {

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Autowired
    private GameService gameService;

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

    // public PriceAlert trackPrice(Long gameId) throws IOException {
    //     PriceAlert priceAlert = new PriceAlert();

    //     Game priceAlertGame = gameService.getGameById(gameId);
    //     priceAlert.setGame(priceAlertGame);

    //     String gameTitle = priceAlertGame.getTitle();

    //     List<Integer> shopIds = getItadShopIds();

    //     Runnable track = new Runnable() {
    //         @Override
    //         public void run() {
    //             /*
    //              * URL getPriceUrl = new URL("");
    //              * HttpsURLConnection getPriceConnection = (HttpsURLConnection) getPriceUrl.openConnection(); 
    //              */
                
    //         }
    //     };    
    // }

    // private List<Integer> getItadShopIds() throws IOException {
    //     URL getShopsUrl = new URL("https://api.isthereanydeal.com/service/shops/v1?country=US");
    //     HttpsURLConnection getShopsConnection = (HttpsURLConnection) getShopsUrl.openConnection();

    //     getShopsConnection.setRequestMethod("GET");

    //     int getShopsResponseCode = getShopsConnection.getResponseCode();

    //     System.out.println(getShopsResponseCode);

        
    // }
}
