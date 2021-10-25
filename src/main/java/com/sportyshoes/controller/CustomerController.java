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

import com.sportyshoes.dao.CustomerRepository;
import com.sportyshoes.model.Customer;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

@RestController
public class CustomerController {
	
	@Autowired
    private CustomerRepository cusRepository;

	@PostMapping("/register/customer")
	public HashMap<String, String> createCustomer(@Valid @RequestBody Customer cus) throws Exception {
		HashMap<String, String> return_message = new HashMap<String, String>();
			try {
    	
				String encodedpass = Base64.getEncoder().encodeToString(cus.getPassword().getBytes());
				cus.setPassword(encodedpass);
				cusRepository.save(cus);
    	
				return_message.put("message", "Customer created successfully");
        
				} catch (Exception e) {
    	
				return_message.put("errors", e.getMessage());
				}
			return return_message;
		}
	
	@PutMapping("/customer/login/{id}")
    public HashMap<String, String>  adminLogin(@PathVariable int id) throws Exception {
		HashMap<String, String> return_message = new HashMap<String, String>();
		Customer cus = cusRepository.findById(id).orElse(null);
        
        if (cus == null) {
            
            return_message.put("message", "Customer account not found");
        }
        else {
        	
        	cus.setIsLoggedin(true);
            cusRepository.save(cus);
            return_message.put("message", "Customer loggedin successfully.");
        }
        
        return return_message;
    }
	
	@PutMapping("/customer/logout/{id}")
    public HashMap<String, String>  adminLogout(@PathVariable int id) throws Exception {
		HashMap<String, String> return_message = new HashMap<String, String>();
		Customer cus = cusRepository.findById(id).orElse(null);
        
        if (cus == null) {
            
            return_message.put("message", "Customer account not found");
        }
        else {
        	
        	cus.setIsLoggedin(false);
            cusRepository.save(cus);
            return_message.put("message", "Customer loggedout successfully.");
        }
        
        return return_message;
    }
	
	@PutMapping("/customer/{id}")
    public HashMap<String, String> updateCustomer(@RequestBody Customer cusbody, @PathVariable int id) {

		   Customer cus = cusRepository.findById(id).orElse(null);
		   cus.setUsername(cusbody.getUsername());
		    String encodedpass = Base64.getEncoder().encodeToString(cusbody.getPassword().getBytes());
		    cus.setPassword(encodedpass);
        	cusRepository.save(cus);
		
	        HashMap<String, String> return_message = new HashMap<String, String>();
	        return_message.put("message", "Customer updated successfully.");
	        return return_message;
    }
	
	@GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) throws Exception {
        Customer cus = cusRepository.findById(id).orElse(null);
        
        if (cus == null) {
            throw new Exception("Customer Record Not Found");
        }
        
        byte[] decodedBytes = Base64.getDecoder().decode(cus.getPassword());
        String decodedString = new String(decodedBytes);
        cus.setPassword(decodedString);
        return ResponseEntity.ok().body(cus);
    }
	
	@GetMapping("/customer/username/{username}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String username) throws Exception {
        Customer cus = cusRepository.findByUsername(username);
        
        if (cus == null) {
            throw new Exception("Customer Record Not Found");
        }
        
        byte[] decodedBytes = Base64.getDecoder().decode(cus.getPassword());
        String decodedString = new String(decodedBytes);
        cus.setPassword(decodedString);
        return ResponseEntity.ok().body(cus);
    }
	
	@GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomer() throws Exception {
        List<Customer> allcus = cusRepository.findAll();
        
        if (allcus == null) {
            throw new Exception("Customer Record Not Found");
        }
        
        for(Customer cus : allcus) {
        	byte[] decodedBytes = Base64.getDecoder().decode(cus.getPassword());
        	String decodedString = new String(decodedBytes);
        	cus.setPassword(decodedString);
        }
             
        return ResponseEntity.ok().body(allcus);
    }
	
	@DeleteMapping("/customer/{id}")
    public HashMap<String, String> DeleteCustomer(@PathVariable int id) {
		
		cusRepository.deleteById(id);
        
        HashMap<String, String> return_message = new HashMap<String, String>();
        return_message.put("message", "Customer deleted successfully.");
        return return_message;
    }
}
