package com.bpark.pivnetemail.repository;

import org.springframework.data.repository.CrudRepository;

import com.bpark.pivnetemail.entity.PivnetUser;

public interface PivnetUserRepository extends CrudRepository<PivnetUser, Integer> {

}
