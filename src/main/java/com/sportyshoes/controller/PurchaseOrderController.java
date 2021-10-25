package com.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.dao.CustomerRepository;
import com.sportyshoes.dao.ItemsRepository;
import com.sportyshoes.dao.PurchaseOrderRepository;
import com.sportyshoes.model.*;

import java.util.*;

import javax.validation.Valid;

@RestController
public class PurchaseOrderController {
	
	@Autowired
    private PurchaseOrderRepository poRepository;
	
	@Autowired
    private CustomerRepository cusRepository;
	
	@Autowired
    private ItemsRepository itemRepository;
	
	private int validateItem = 0;
	
	@PostMapping("/purchase")
    public HashMap<String, String> createPO(@Valid @RequestBody PurchaseOrder purchaseOrder) throws Exception {
		
		HashMap<String, String> return_message = new HashMap<String, String>();
		Customer cus = cusRepository.findByUsername(purchaseOrder.getUsername());
		if(cus == null || cus.getIsLoggedin() == null) {
			return_message.put("message", "Customer not present or Please Login");
		}
		else {
			if(cus.getIsLoggedin()) {
				for(Items item : purchaseOrder.getItems()) {
					Item itemval = itemRepository.findById(item.getItemId()).orElse(null);
					if(itemval == null) {
						validateItem++;
						break;
					}
				}
				try {
		        	if(validateItem>0) {
		        		return_message.put("message", "Item not present");
		        		validateItem = 0;
		        	}
		        	else {
		        	  	poRepository.save(purchaseOrder);
			        	
			            return_message.put("message", "PO created successfully");
		        	}
		            
		        } catch (Exception e) {
		        	
		            return_message.put("errors", e.getMessage());
		        }
			}
			else {
				return_message.put("message", "Please Login to continue.");
			}
			
		}
        return return_message;
    }
	
	@GetMapping("/all/po")
    public ResponseEntity<List<PurchaseOrder>> getAllPO() throws Exception {
		
        List<PurchaseOrder> allitem = poRepository.findAll();
        
        if (allitem == null) {
            throw new Exception("Item Record Not Found");
        }
        
        return ResponseEntity.ok().body(allitem);
    }
	
	@GetMapping("/po/{id}")
    public ResponseEntity<PurchaseOrder> getPO(@PathVariable int id) throws Exception {
		PurchaseOrder po = poRepository.findById(id).orElse(null);
        
        if (po == null) {
            throw new Exception("Item Record Not Found");
        }
        
        return ResponseEntity.ok().body(po);
    }
	
	@GetMapping("/po/customer/{name}")
    public ResponseEntity<List<PurchaseOrder>> getPOByName(@PathVariable String name) throws Exception {
		List<PurchaseOrder> item = poRepository.findByUsername(name);
        
        if (item == null) {
            throw new Exception("Item Record Not Found");
        }
		
        return ResponseEntity.ok().body(item);
    }
	
	@PutMapping("/po/{id}")
    public HashMap<String, String> updatePO(@RequestBody PurchaseOrder purchaseOrder, @PathVariable int id) {

		HashMap<String, String> return_message = new HashMap<String, String>();
		PurchaseOrder po = poRepository.findById(id).orElse(null);
		Customer cus = cusRepository.findByUsername(purchaseOrder.getUsername());
		if(cus == null || cus.getIsLoggedin() == null) {
			return_message.put("message", "Customer not present  or Please Login");
		}
		else {
			if(cus.getIsLoggedin()) {
			po.setUsername(purchaseOrder.getUsername());
			po.setDatePurchase(purchaseOrder.getDatePurchase());
			po.setStatus(purchaseOrder.getStatus());
			po.setTotal(purchaseOrder.getTotal());
			
			for(Items item : purchaseOrder.getItems()) {
				Item itemval = itemRepository.findById(item.getItemId()).orElse(null);
				if(itemval == null) {
					validateItem++;
					break;
				}
			}
			
			if(validateItem>0) {
        		return_message.put("message", "Item not present");
        		validateItem = 0;
        	}
        	else {
        		//po.setItems();
        		po.setItems(purchaseOrder.getItems());
        	  	poRepository.save(po);
	        	
	            return_message.put("message", "PO updated successfully");
        	}
			}
			else {
				return_message.put("message", "Please Login to continue.");	
			}
		}
	
	        return return_message;
    }

}
