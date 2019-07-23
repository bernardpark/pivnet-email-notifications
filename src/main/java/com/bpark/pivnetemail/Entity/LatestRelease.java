package com.bpark.pivnetemail.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LatestRelease {
	
	@Id
	private String name;
	private String releaseDate;
    private String releaseId;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	
}
