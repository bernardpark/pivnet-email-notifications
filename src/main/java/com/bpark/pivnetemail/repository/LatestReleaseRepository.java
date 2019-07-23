package com.bpark.pivnetemail.repository;

import org.springframework.data.repository.CrudRepository;

import com.bpark.pivnetemail.Entity.LatestRelease;

public interface LatestReleaseRepository extends CrudRepository<LatestRelease, Integer> {

}
