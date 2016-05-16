package com.csc.doc;

import java.io.Serializable;

public class PhoneKey implements Serializable {

	private static final long serialVersionUID = 7072471030960543184L;
	
	private Integer id;
	private Character phoneType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(Character phoneType) {
		this.phoneType = phoneType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((phoneType == null) ? 0 : phoneType.hashCode());
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
		PhoneKey other = (PhoneKey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phoneType == null) {
			if (other.phoneType != null)
				return false;
		} else if (!phoneType.equals(other.phoneType))
			return false;
		return true;
	}

}
