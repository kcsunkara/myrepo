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

}
