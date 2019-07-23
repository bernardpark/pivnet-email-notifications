package com.bpark.pivnetemail.model;

public class Release {
	
	String id;
	String version;
	String release_type;
	String release_date;
	String release_notes_url;
	String availability;
	String description;
	Eula eula;
	String end_of_support_date;
	String eccn;
	String license_exception;
	String updated_at;
	String software_files_updated_at;
	Link _links;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public Eula getEula() {
		return eula;
	}
	
	public void setEula(Eula eula) {
		this.eula = eula;
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
	
	public Link get_links() {
		return _links;
	}
	
	public void set_links(Link _links) {
		this._links = _links;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_links == null) ? 0 : _links.hashCode());
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eccn == null) ? 0 : eccn.hashCode());
		result = prime * result + ((end_of_support_date == null) ? 0 : end_of_support_date.hashCode());
		result = prime * result + ((eula == null) ? 0 : eula.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((license_exception == null) ? 0 : license_exception.hashCode());
		result = prime * result + ((release_date == null) ? 0 : release_date.hashCode());
		result = prime * result + ((release_notes_url == null) ? 0 : release_notes_url.hashCode());
		result = prime * result + ((release_type == null) ? 0 : release_type.hashCode());
		result = prime * result + ((software_files_updated_at == null) ? 0 : software_files_updated_at.hashCode());
		result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Release other = (Release) obj;
		if (_links == null) {
			if (other._links != null)
				return false;
		} else if (!_links.equals(other._links))
			return false;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eccn == null) {
			if (other.eccn != null)
				return false;
		} else if (!eccn.equals(other.eccn))
			return false;
		if (end_of_support_date == null) {
			if (other.end_of_support_date != null)
				return false;
		} else if (!end_of_support_date.equals(other.end_of_support_date))
			return false;
		if (eula == null) {
			if (other.eula != null)
				return false;
		} else if (!eula.equals(other.eula))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (license_exception == null) {
			if (other.license_exception != null)
				return false;
		} else if (!license_exception.equals(other.license_exception))
			return false;
		if (release_date == null) {
			if (other.release_date != null)
				return false;
		} else if (!release_date.equals(other.release_date))
			return false;
		if (release_notes_url == null) {
			if (other.release_notes_url != null)
				return false;
		} else if (!release_notes_url.equals(other.release_notes_url))
			return false;
		if (release_type == null) {
			if (other.release_type != null)
				return false;
		} else if (!release_type.equals(other.release_type))
			return false;
		if (software_files_updated_at == null) {
			if (other.software_files_updated_at != null)
				return false;
		} else if (!software_files_updated_at.equals(other.software_files_updated_at))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Release [id=" + id + ", version=" + version + ", release_type=" + release_type + ", release_date="
				+ release_date + ", release_notes_url=" + release_notes_url + ", availability=" + availability
				+ ", description=" + description + ", eula=" + eula + ", end_of_support_date=" + end_of_support_date
				+ ", eccn=" + eccn + ", license_exception=" + license_exception + ", updated_at=" + updated_at
				+ ", software_files_updated_at=" + software_files_updated_at + ", _links=" + _links + "]";
	}
	
}
