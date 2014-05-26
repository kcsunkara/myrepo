package com.cognizant.ws.domain;

/**
 * Comments
 */
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="customers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id") 
public class Customer implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	@NotEmpty(message = "{enter.name}")
	String name;
	
	@Column
	private String email;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "customer")
	public List<Policy> policyList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Policy> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}
	
	
}
