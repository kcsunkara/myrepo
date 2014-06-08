package com.cognizant.ui.json;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AssetInstancesDTO implements java.io.Serializable{
	
	private Long id;
	private AssetDTO assetDTO;
	private Long storageLocId;
	private String filename;
	private String location_md5;
	private String create_date;
	private String last_check;
	private String purge_date;
	private String status;
	private String encrypted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AssetDTO getAssetDTO() {
		return assetDTO;
	}
	public void setAssetDTO(AssetDTO assetDTO) {
		this.assetDTO = assetDTO;
	}
	public Long getStorageLocId() {
		return storageLocId;
	}
	public void setStorageLocId(Long storageLocId) {
		this.storageLocId = storageLocId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getLocation_md5() {
		return location_md5;
	}
	public void setLocation_md5(String location_md5) {
		this.location_md5 = location_md5;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getLast_check() {
		return last_check;
	}
	public void setLast_check(String last_check) {
		this.last_check = last_check;
	}
	public String getPurge_date() {
		return purge_date;
	}
	public void setPurge_date(String purge_date) {
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

}
