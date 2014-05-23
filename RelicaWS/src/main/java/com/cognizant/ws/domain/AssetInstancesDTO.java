package com.cognizant.ws.domain;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="asset_instances")
public class AssetInstancesDTO implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	

	@ManyToOne
    @JoinColumn(name="asset_id")
	private AssetDTO assetDTO;
	
	public AssetDTO getAssetDTO() {
		return assetDTO;
	}

	public void setAssetDTO(AssetDTO assetDTO) {
		this.assetDTO = assetDTO;
	}

	@Column(name = "storage_location_id")	
	private Long storageLocId;

	
	@Column
	@NotEmpty(message = "{enter.filename}")
	private String filename;
	
	@Column
	private String location_md5;
	
	@Column
	private Date create_date = new Date();

	@Column
	private Timestamp last_check;
	
	@Column
//	@NotNull(message="Please enter purge_date")      //JSR 303 Validated ?
//	@Past (message="Only the past is valid")     //JSR 303 Validated ?
//	@DateTimeFormat(pattern="YYYY/mm/dd")
	private Date purge_date;
	
	@Column
	private String status = "N";

	@Column
	private String encrypted = "";


	

	public Long getStorageLocId() {
		return storageLocId;
	}

	public void setStorageLocId(Long storageLocId) {
		this.storageLocId = storageLocId;
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

	public Timestamp getLast_check() {
		return last_check;
	}

	public void setLast_check(Timestamp last_check) {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
/*
	@Override
	public String toString() {
		return "AssetInstances [id=" + id + ", asset=" + asset
				+ ", storageLocationId=" + storageLocationId + ", filename="
				+ filename + ", path=" + path + ", location_md5="
				+ location_md5 + ", create_date=" + create_date
				+ ", last_check=" + last_check + ", purge_date=" + purge_date
				+ ", status=" + status + ", encrypted=" + encrypted + "]";
	}*/

	


}
