package com.bpark.pivnetemail.model;

public class Link {
	
	Self self;
	EulaAcceptance eula_acceptance;
	ProductFiles product_files;
	FileGroups file_groups;
	UserGroups user_groups;
	
	public Self getSelf() {
		return self;
	}
	
	public void setSelf(Self self) {
		this.self = self;
	}
	
	public EulaAcceptance getEula_acceptance() {
		return eula_acceptance;
	}
	
	public void setEula_acceptance(EulaAcceptance eula_acceptance) {
		this.eula_acceptance = eula_acceptance;
	}
	
	public ProductFiles getProduct_files() {
		return product_files;
	}
	
	public void setProduct_files(ProductFiles product_files) {
		this.product_files = product_files;
	}
	
	public FileGroups getFile_groups() {
		return file_groups;
	}
	
	public void setFile_groups(FileGroups file_groups) {
		this.file_groups = file_groups;
	}
	
	public UserGroups getUser_groups() {
		return user_groups;
	}
	
	public void setUser_groups(UserGroups user_groups) {
		this.user_groups = user_groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eula_acceptance == null) ? 0 : eula_acceptance.hashCode());
		result = prime * result + ((file_groups == null) ? 0 : file_groups.hashCode());
		result = prime * result + ((product_files == null) ? 0 : product_files.hashCode());
		result = prime * result + ((self == null) ? 0 : self.hashCode());
		result = prime * result + ((user_groups == null) ? 0 : user_groups.hashCode());
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
		Link other = (Link) obj;
		if (eula_acceptance == null) {
			if (other.eula_acceptance != null)
				return false;
		} else if (!eula_acceptance.equals(other.eula_acceptance))
			return false;
		if (file_groups == null) {
			if (other.file_groups != null)
				return false;
		} else if (!file_groups.equals(other.file_groups))
			return false;
		if (product_files == null) {
			if (other.product_files != null)
				return false;
		} else if (!product_files.equals(other.product_files))
			return false;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		if (user_groups == null) {
			if (other.user_groups != null)
				return false;
		} else if (!user_groups.equals(other.user_groups))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Link [self=" + self + ", eula_acceptance=" + eula_acceptance + ", product_files=" + product_files
				+ ", file_groups=" + file_groups + ", user_groups=" + user_groups + "]";
	}
	
}
