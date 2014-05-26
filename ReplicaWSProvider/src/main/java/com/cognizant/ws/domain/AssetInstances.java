package com.cognizant.ws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "asset_instances")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id") 
public class AssetInstances implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "asset_id")
	private Asset asset;
	
	@Transient
	private Long asset_id;

	@Column(name = "storage_location_id")
	@NotEmpty(message = "{enter.storage_location_id}")
	private String storageLocationId;

	@Column
	@NotEmpty(message = "{enter.filename}")
	private String filename;

	@Column
	private String location_md5;

	@Column
	private Date create_date;

	@Column
	private Date last_check;

	@Column
	// @NotNull(message="Please enter purge_date") //JSR 303 Validated ?
	// @Past (message="Only the past is valid") //JSR 303 Validated ?
	// @DateTimeFormat(pattern="YYYY/mm/dd")
	private Date purge_date;

	@Column
	private String status = "N";

	@Column
	private String encrypted = "";
	
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Long getAsset_id() {
		if(this.asset != null)
			return this.asset.getId();
		else
			return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
	}

	public String getLocation_md5() {
		return location_md5;
	}

	public void setLocation_md5(String location_md5) {
		this.location_md5 = location_md5;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLast_check() {
		return last_check;
	}

	public void setLast_check(Date last_check) {
		this.last_check = last_check;
	}

	public Date getPurge_date() {
		return purge_date;
	}

	public void setPurge_date(Date purge_date) {
		this.purge_date = purge_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(String storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	/*
	 * @Override public String toString() { return "AssetInstances [id=" + id +
	 * ", asset=" + asset + ", storageLocationId=" + storageLocationId +
	 * ", filename=" + filename + ", path=" + path + ", location_md5=" +
	 * location_md5 + ", create_date=" + create_date + ", last_check=" +
	 * last_check + ", purge_date=" + purge_date + ", status=" + status +
	 * ", encrypted=" + encrypted + "]"; }
	 */

}
