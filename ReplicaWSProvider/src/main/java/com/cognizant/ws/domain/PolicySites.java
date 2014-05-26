package com.cognizant.ws.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "policy_sites")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="compositId")
public class PolicySites implements java.io.Serializable {
	protected static Logger logger = Logger.getLogger(PolicySites.class);

	@EmbeddedId
	private PolicySitesPK compositId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "policy_id", insertable=false, updatable=false)
	private Policy policy;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "site_id", insertable=false, updatable=false)
	private Site site;

	/*@Column
	@NotEmpty(message = "{enter.site_id}")
	private Long site_id;*/

	@Column
	private Long number_copies;

	@Column
	private Long restore_priority;
	
	public PolicySitesPK getCompositId() {
		return compositId;
	}

	public void setCompositId(PolicySitesPK compositId) {
		this.compositId = compositId;
	}

	/**
	 * @return the number_copies
	 */
	public Long getNumber_copies() {
		return number_copies;
	}

	/**
	 * @param number_copies
	 *            the number_copies to set
	 */
	public void setNumber_copies(Long number_copies) {
		this.number_copies = number_copies;
	}

	/**
	 * @return the restore_priority
	 */
	public Long getRestore_priority() {
		return restore_priority;
	}

	/**
	 * @param restore_priority
	 *            the restore_priority to set
	 */
	public void setRestore_priority(Long restore_priority) {
		this.restore_priority = restore_priority;
	}

	@JsonIgnore
	public Policy getPolicy() {
		return policy;
	}

	@JsonIgnore
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
}
