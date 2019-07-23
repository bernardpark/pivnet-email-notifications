package com.bpark.pivnetemail.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bpark.pivnetemail.entity.PivnetProduct;

public interface PivnetProductRepository extends CrudRepository<PivnetProduct, Integer> {
	List<PivnetProduct> findById( String id );
}
