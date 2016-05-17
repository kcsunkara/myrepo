package com.csc.doc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="addresses")
@IdClass(AddressKey.class)
@JsonIgnoreProperties("id")
@Document(indexName = "address", type = "address", shards = 1, replicas = 0)
public class Address implements Serializable {

	private static final long serialVersionUID = 2996254502211760328L;

	@Id
	@Column(name="emp_no")
	private Integer id;
	
	@Id
	@Column(name="address_type")
	private Character addressType;
	
	@Column(name="address_1")
	private String primaryAddress;
	
	@Column(name="address_2")
	private String alternateAddress;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="postal_code")
	private String postalCode;

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

	public String getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public String getAlternateAddress() {
		return alternateAddress;
	}

	public void setAlternateAddress(String alternateAddress) {
		this.alternateAddress = alternateAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
