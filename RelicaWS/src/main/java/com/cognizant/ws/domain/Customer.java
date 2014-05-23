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
	@NotEmpty(message = "{}")
	private Long allocation_bu_id;
	
	@Column
	@NotEmpty(message = "{}")
	private String allocation_cost_center;
	
	@Column
	@NotEmpty(message = "{}")
	private String allocation_company_code;

	@Column
	private String allocation_geo_id;
	
	@Column
	@NotEmpty(message = "{}")
	private String allocation_general_ledger;
	
	@Column
	@NotEmpty(message = "{}")
	private String owner_contact;
	
	@Column
	private String email;
	
	@Column
	private String allocation_wbs;
	
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

	public Long getAllocation_bu_id() {
		return allocation_bu_id;
	}

	public void setAllocation_bu_id(Long allocation_bu_id) {
		this.allocation_bu_id = allocation_bu_id;
	}

	public String getAllocation_cost_center() {
		return allocation_cost_center;
	}

	public void setAllocation_cost_center(String allocation_cost_center) {
		this.allocation_cost_center = allocation_cost_center;
	}

	public String getAllocation_company_code() {
		return allocation_company_code;
	}

	public void setAllocation_company_code(String allocation_company_code) {
		this.allocation_company_code = allocation_company_code;
	}

	public String getAllocation_geo_id() {
		return allocation_geo_id;
	}

	public void setAllocation_geo_id(String allocation_geo_id) {
		this.allocation_geo_id = allocation_geo_id;
	}

	public String getAllocation_general_ledger() {
		return allocation_general_ledger;
	}

	public void setAllocation_general_ledger(String allocation_general_ledger) {
		this.allocation_general_ledger = allocation_general_ledger;
	}

	public String getOwner_contact() {
		return owner_contact;
	}

	public void setOwner_contact(String owner_contact) {
		this.owner_contact = owner_contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAllocation_wbs() {
		return allocation_wbs;
	}

	public void setAllocation_wbs(String allocation_wbs) {
		this.allocation_wbs = allocation_wbs;
	}

	public List<Policy> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}
	
	
}
