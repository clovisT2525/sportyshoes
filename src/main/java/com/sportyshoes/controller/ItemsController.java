package com.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.dao.ItemsRepository;
import com.sportyshoes.model.Item;
import com.sportyshoes.model.PriceRange;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

@RestController
public class ItemsController {
	
	@Autowired
    private ItemsRepository itemRepository;
	
	@PostMapping("/item")
    public HashMap<String, String> createItem(@Valid @RequestBody Item item) throws Exception {
        HashMap<String, String> return_message = new HashMap<String, String>();
        try {
        	
        	itemRepository.save(item);
        	
            return_message.put("message", "Item created successfully");
            
        } catch (Exception e) {
        	
            return_message.put("errors", e.getMessage());
        }
        return return_message;
    }
	
	@PutMapping("/item/{id}")
    public HashMap<String, String> updateItem(@RequestBody Item itembody, @PathVariable int id) {

		   Item item = itemRepository.findById(id).orElse(null);
		    item.setName(itembody.getName());
		    item.setDescription(itembody.getDescription());
		    item.setCategory(itembody.getCategory());
		    item.setPrice(itembody.getPrice());
        	itemRepository.save(item);
		
	        HashMap<String, String> return_message = new HashMap<String, String>();
	        return_message.put("message", "Item updated successfully.");
	        return return_message;
    }
	
	@GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) throws Exception {
        Item item = itemRepository.findById(id).orElse(null);
        
        if (item == null) {
            throw new Exception("Item Record Not Found");
        }
       
        return ResponseEntity.ok().body(item);
    }
	
	@GetMapping("/item/name/{name}")
    public ResponseEntity<Item> getItemByName(@PathVariable String name) throws Exception {
        Item item = itemRepository.findByName(name);
        
        if (item == null) {
            throw new Exception("Item Record Not Found");
        }
       
        return ResponseEntity.ok().body(item);
    }
	
	@GetMapping("/item/category/{category}")
    public ResponseEntity<List<Item>> getItemByCategory(@PathVariable String category) throws Exception {
        List<Item> item = itemRepository.findByCategory(category);
        
        if (item == null) {
            throw new Exception("Item Record Not Found");
        }
       
        return ResponseEntity.ok().body(item);
    }
	
	@GetMapping("/item/price-range")
    public ResponseEntity<List<Item>> getItemByPriceRange(@Valid @RequestBody PriceRange priceRange) throws Exception {
		 List<Item> item = itemRepository.findByPriceBetween(priceRange.getPriceMin(),priceRange.getPriceMax());
    
        if (item == null) {
            throw new Exception("Item Record Not Found");
        }
       
        return ResponseEntity.ok().body(item);
    }
	
	@GetMapping("/item")
    public ResponseEntity<List<Item>> getAllItems() throws Exception {
        List<Item> allitem = itemRepository.findAll();
        
        if (allitem == null) {
            throw new Exception("Item Record Not Found");
        }
        
             
        return ResponseEntity.ok().body(allitem);
    }
	
	@DeleteMapping("/item/{id}")
    public HashMap<String, String> DeleteItem(@PathVariable int id) {
		
		itemRepository.deleteById(id);
        
        HashMap<String, String> return_message = new HashMap<String, String>();
        return_message.put("message", "Item deleted successfully.");
        return return_message;
    }

}
