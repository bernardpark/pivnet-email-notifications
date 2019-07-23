package com.bpark.pivnetemail.repository;

import org.springframework.data.repository.CrudRepository;

import com.bpark.pivnetemail.entity.LatestRelease;

public interface LatestReleaseRepository extends CrudRepository<LatestRelease, Integer> {

}
