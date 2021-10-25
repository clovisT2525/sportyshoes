package com.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Items;

public interface PurchaseItems extends JpaRepository<Items, Integer> {

}
