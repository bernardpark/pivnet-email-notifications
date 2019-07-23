package com.bpark.pivnetemail.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	String email;
	List<String> products;
	
	public User( String email ) {
		this.email = email;
		this.products = new ArrayList<String>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	
}
