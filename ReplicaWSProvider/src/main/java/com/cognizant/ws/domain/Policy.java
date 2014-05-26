package com.cognizant.ws.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the policies database table.
 * 
 */
@Entity
@Table(name = "policies")
@NamedQueries(
		{@NamedQuery(name = "allPolicies", query = "from  Policy order by len(fs_path) desc")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Policy implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "active")
	private boolean active;

	@Transient
	private Long customer_id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer; 

	@Column(name = "encrypt")
	private boolean encrypt;

	@Column(name = "fs_path")
	private String fs_path;

	@Column(name = "md5_check")
	private boolean md5_check;

	@Column(name = "policy_name")
	private String policy_name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "policy")
	public List<Asset> assets;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "policy")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<PolicySites> policySitesList;
	
	public Policy() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getCustomer_id() {
		return customer.getId();
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public String getFs_path() {
		return fs_path;
	}

	public void setFs_path(String fs_path) {
		this.fs_path = fs_path;
	}

	public boolean isMd5_check() {
		return md5_check;
	}

	public void setMd5_check(boolean md5_check) {
		this.md5_check = md5_check;
	}

	public String getPolicy_name() {
		return policy_name;
	}

	public void setPolicy_name(String policy_name) {
		this.policy_name = policy_name;
	}

	@JsonIgnore
	public List<Asset> getAssets() {
		return assets;
	}

	@JsonIgnore
	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	@JsonIgnore
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<PolicySites> getPolicySitesList() {
		return policySitesList;
	}

	public void setPolicySitesList(List<PolicySites> policySitesList) {
		this.policySitesList = policySitesList;
	}

}