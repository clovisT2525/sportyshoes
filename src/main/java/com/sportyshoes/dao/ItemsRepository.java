package com.sportyshoes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Item;

public interface ItemsRepository extends JpaRepository<Item, Integer> {
	Item findByName(String name);
	
	List<Item> findByCategory(String category);
	
	List<Item> findByPriceBetween(float priceMin, float priceMax);
}
