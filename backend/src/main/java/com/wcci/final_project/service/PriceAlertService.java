package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.wcci.final_project.entity.PriceAlert;
import com.wcci.final_project.repository.PriceAlertRepository;

public class PriceAlertService {

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    public PriceAlert createPriceAlert(PriceAlert priceAlert) {
        return priceAlertRepository.save(priceAlert);
    }

    public PriceAlert getPriceAlertById(Long id) {
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
