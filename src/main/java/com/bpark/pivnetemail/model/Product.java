package com.bpark.pivnetemail.model;

import java.util.List;

public class Product {
	
	List<Release> releases;
	ReleaseLink _links;
	
	public List<Release> getReleases() {
		return releases;
	}
	
	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}
	
	public ReleaseLink get_links() {
		return _links;
	}
	
	public void set_links(ReleaseLink _links) {
		this._links = _links;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_links == null) ? 0 : _links.hashCode());
		result = prime * result + ((releases == null) ? 0 : releases.hashCode());
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
		Product other = (Product) obj;
		if (_links == null) {
			if (other._links != null)
				return false;
		} else if (!_links.equals(other._links))
			return false;
		if (releases == null) {
			if (other.releases != null)
				return false;
		} else if (!releases.equals(other.releases))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [releases=" + releases + ", _links=" + _links + "]";
	}

}
