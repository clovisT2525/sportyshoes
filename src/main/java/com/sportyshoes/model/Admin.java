package com.sportyshoes.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String username;
	
	private String password;
	
	public Boolean getIsLoggedin() {
		return isLoggedin;
	}

	public void setIsLoggedin(Boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	@JsonIgnore
	private Boolean isLoggedin;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
