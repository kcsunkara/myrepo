package com.cognizant.ws.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sites")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Site implements java.io.Serializable {
	protected static Logger logger = Logger.getLogger(Site.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotEmpty(message = "{enter.tier}")
	private String tier;

	@Column
	@NotEmpty(message = "{enter.name}")
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "policy")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<PolicySites> policySitesList;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tier
	 */
	public String getTier() {
		return tier;
	}

	/**
	 * @param tier
	 *            the tier to set
	 */
	public void setTier(String tier) {
		this.tier = tier;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public List<PolicySites> getPolicySitesList() {
		return policySitesList;
	}

	@JsonIgnore
	public void setPolicySitesList(List<PolicySites> policySitesList) {
		this.policySitesList = policySitesList;
	}

}
