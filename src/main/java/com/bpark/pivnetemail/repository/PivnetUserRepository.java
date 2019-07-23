package com.bpark.pivnetemail.repository;

import org.springframework.data.repository.CrudRepository;

import com.bpark.pivnetemail.Entity.PivnetUser;

public interface PivnetUserRepository extends CrudRepository<PivnetUser, Integer> {

}
