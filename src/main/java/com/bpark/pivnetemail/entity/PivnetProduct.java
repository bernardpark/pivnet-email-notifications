package com.bpark.pivnetemail.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PivnetProduct {

	@Id
	private String id;
	private String name;
	private String version;
	private String release_type;
	private String release_date;
	private String release_notes_url;
	private String availability;
	private String description;
	private String end_of_support_date;
	private String eccn;
	private String license_exception;
	private String updated_at;
	private String software_files_updated_at;
	private String _links;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRelease_type() {
		return release_type;
	}
	public void setRelease_type(String release_type) {
		this.release_type = release_type;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public String getRelease_notes_url() {
		return release_notes_url;
	}
	public void setRelease_notes_url(String release_notes_url) {
		this.release_notes_url = release_notes_url;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnd_of_support_date() {
		return end_of_support_date;
	}
	public void setEnd_of_support_date(String end_of_support_date) {
		this.end_of_support_date = end_of_support_date;
	}
	public String getEccn() {
		return eccn;
	}
	public void setEccn(String eccn) {
		this.eccn = eccn;
	}
	public String getLicense_exception() {
		return license_exception;
	}
	public void setLicense_exception(String license_exception) {
		this.license_exception = license_exception;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getSoftware_files_updated_at() {
		return software_files_updated_at;
	}
	public void setSoftware_files_updated_at(String software_files_updated_at) {
		this.software_files_updated_at = software_files_updated_at;
	}
	public String get_links() {
		return _links;
	}
	public void set_links(String _links) {
		this._links = _links;
	}
	public String toStringEmail() {
		return "Product ID : " + id + "\nProduct Name : " + name + "\nProduct Version : " + version + "\nProduct Release Type : " + release_type
				+ "\nRelease Date : " + release_date + "\nRelease Notes URL : " + release_notes_url + "\nDescription : \n" + description + "\nLinks : " + _links;
	}
	
	
}
