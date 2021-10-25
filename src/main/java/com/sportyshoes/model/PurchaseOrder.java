package com.sportyshoes.model;

import javax.persistence.*;

import java.util.List;

@Entity
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String username;
	
	private String datePurchase;
	
	private String status;
	
	private float total;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatePurchase() {
		return datePurchase;
	}
	
	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getTotal() {
		return total;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}
	
	@Embedded 
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private List<Items> items;

}

