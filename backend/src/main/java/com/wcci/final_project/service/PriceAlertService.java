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



    
}
