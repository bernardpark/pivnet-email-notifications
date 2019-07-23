package com.bpark.pivnetemail.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PivnetUser {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	private String email;
    @OneToMany(mappedBy = "pivnetUser", cascade = CascadeType.ALL)
    private Set<PivnetUserProduct> pivnetUserProducts;
    
    public PivnetUser() {
    	pivnetUserProducts = new HashSet<PivnetUserProduct>();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<PivnetUserProduct> getPivnetUserProducts() {
		return pivnetUserProducts;
	}

	public void setPivnetUserProducts(Set<PivnetUserProduct> pivnetUserProducts) {
		this.pivnetUserProducts = pivnetUserProducts;
	}
	
}
