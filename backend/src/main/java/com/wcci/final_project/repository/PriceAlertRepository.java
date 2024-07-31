package com.wcci.final_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcci.final_project.entity.PriceAlert;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

}
