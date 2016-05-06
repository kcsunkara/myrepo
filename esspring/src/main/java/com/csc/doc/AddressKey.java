package com.csc.doc;

import java.io.Serializable;

public class AddressKey implements Serializable {

	private static final long serialVersionUID = 4291367480128832435L;
	
	private Integer id;
	private Character addressType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getAddressType() {
		return addressType;
	}
	public void setAddressType(Character addressType) {
		this.addressType = addressType;
	}

}
