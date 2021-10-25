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

import com.sportyshoes.dao.AdminRepository;
import com.sportyshoes.model.Admin;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

@RestController
public class AdminController {
	
	  	@Autowired
	    private AdminRepository adminRepository;

	  	@GetMapping("/")
	  	public String home()
        {
            return "Json Api";
        }
	
	@PostMapping("/register")
    public HashMap<String, String> createAdmin(@Valid @RequestBody Admin admin) throws Exception {
        HashMap<String, String> return_message = new HashMap<String, String>();
        try {
        	
        	String encodedpass = Base64.getEncoder().encodeToString(admin.getPassword().getBytes());
        	admin.setPassword(encodedpass);
        	adminRepository.save(admin);
        	
            return_message.put("message", "Admin created successfully");
            
        } catch (Exception e) {
        	
            return_message.put("errors", e.getMessage());
        }
        return return_message;
    }
	
	@PutMapping("/admin/login/{id}")
    public HashMap<String, String>  adminLogin(@PathVariable int id) throws Exception {
		HashMap<String, String> return_message = new HashMap<String, String>();
        Admin admin = adminRepository.findById(id).orElse(null);
        
        if (admin == null) {
            
            return_message.put("message", "Admin account not found");
        }
        else {
        	
            admin.setIsLoggedin(true);
            adminRepository.save(admin);
            return_message.put("message", "Admin loggedin successfully.");
        }
        
        return return_message;
    }
	
	@PutMapping("/admin/logout/{id}")
    public HashMap<String, String>  adminLogout(@PathVariable int id) throws Exception {
		HashMap<String, String> return_message = new HashMap<String, String>();
        Admin admin = adminRepository.findById(id).orElse(null);
        
        if (admin == null) {
            
            return_message.put("message", "Admin account not found");
        }
        else {
        	
            admin.setIsLoggedin(false);
            adminRepository.save(admin);
            return_message.put("message", "Admin loggedout successfully.");
        }
        
        return return_message;
    }
	
	
	@PutMapping("/register/{id}")
    public HashMap<String, String> updateAdmin(@RequestBody Admin adminbody, @PathVariable int id) {

		   Admin admin = adminRepository.findById(id).orElse(null);
		    admin.setUsername(adminbody.getUsername());
		    String encodedpass = Base64.getEncoder().encodeToString(adminbody.getPassword().getBytes());
        	admin.setPassword(encodedpass);
	        adminRepository.save(admin);
		
	        HashMap<String, String> return_message = new HashMap<String, String>();
	        return_message.put("message", "Admin updated successfully.");
	        return return_message;
    }
	
	@GetMapping("/register/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable int id) throws Exception {
        Admin admin = adminRepository.findById(id).orElse(null);
        
        if (admin == null) {
            throw new Exception("Admin Record Not Found");
        }
        
        byte[] decodedBytes = Base64.getDecoder().decode(admin.getPassword());
        String decodedString = new String(decodedBytes);
        admin.setPassword(decodedString);
        return ResponseEntity.ok().body(admin);
    }
	
	@GetMapping("/register/admin")
    public ResponseEntity<List<Admin>> getAllAdmin() throws Exception {
        List<Admin> allAdmin = adminRepository.findAll();
        
        if (allAdmin == null) {
            throw new Exception("Admin Record Not Found");
        }
        
        for(Admin admin : allAdmin) {
        	byte[] decodedBytes = Base64.getDecoder().decode(admin.getPassword());
        	String decodedString = new String(decodedBytes);
        	admin.setPassword(decodedString);
        }
             
        return ResponseEntity.ok().body(allAdmin);
    }
	
	@DeleteMapping("/register/{id}")
    public HashMap<String, String> DeleteAdmin(@PathVariable int id) {
		
		adminRepository.deleteById(id);
        
        HashMap<String, String> return_message = new HashMap<String, String>();
        return_message.put("message", "Admin deleted successfully.");
        return return_message;
    }
}
