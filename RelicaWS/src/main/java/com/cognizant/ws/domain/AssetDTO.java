package com.cognizant.ws.domain;

/**
 * Comments
 */

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import com.cognizant.ws.util.DateFormatValid;
import com.cognizant.ws.util.JsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@Entity
@Table(name="assets")
@NamedQuery(name="getAsset",query="from  AssetDTO where" +
					" name=:name and filesize!=:filesize and fs_path=:fsPath")
public class AssetDTO implements java.io.Serializable{
	
	private static final Logger LOG = Logger.getLogger(AssetDTO.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "{instances.not.empty}")
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "assetDTO")
	@Valid
	public List<AssetInstancesDTO> assetInstances;
	
	@Column
	private String dam_internal_id;
	
	@Column
	@NotEmpty(message = "{enter.name}")
	private String name;
	
	@Column
	private String repository;
	
	@Column
	@NotNull(message = "{enter.filesize}")
	private Long filesize;
	
	@Column
	private Long policy_id;
	
	@Column
	private String asset_md5;
	
	@Column
	private String user_md5;
	
	@Column
	@NotNull(message = "{enter.created_date}")
	@DateFormatValid
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm")
    private Date created_date;
	
	@Column
	private Date  delete_date;
	
	@Column
	private String l_path;

	@Column
	@NotNull(message = "{enter.fspath}")
	private String fs_path;
	

	public String getFs_path() {
		return fs_path;
	}
	public void setFs_path(String fsPath) {
		fs_path = fsPath;
	}
	@Transient 
	private boolean isAssetExists;
	
	@Transient
	public boolean isAssetExists() {
		return isAssetExists;
	}
	public void setAssetExists(boolean isAssetExists) {
		this.isAssetExists = isAssetExists;
	}
	public List<AssetInstancesDTO> getAssetInstances() {
		return assetInstances;
	}
	public void setAssetInstances(List<AssetInstancesDTO> assetInstances) {
		this.assetInstances = assetInstances;
	}
	
	public String getDam_internal_id() {
		return dam_internal_id;
	}
	public void setDam_internal_id(String dam_internal_id) {
		this.dam_internal_id = dam_internal_id;
	}
	
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public Long getPolicy_id() {
		return policy_id;
	}
	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
	}
	public String getAsset_md5() {
		return asset_md5;
	}
	public void setAsset_md5(String asset_md5) {
		this.asset_md5 = asset_md5;
	}
	public String getUser_md5() {
		return user_md5;
	}
	public void setUser_md5(String user_md5) {
		this.user_md5 = user_md5;
	}
//	@JsonSerialize(using=JsonDateSerializer.class)
	public Date  getCreated_date() {
		return created_date;
	}
	@JsonDeserialize(using=JsonDateDeserializer.class)
	public void setCreated_date(Date  created_date) {
		this.created_date = created_date;
	}
	public Date getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}
	public String getL_path() {
		return l_path;
	}
	public void setL_path(String l_path) {
		this.l_path = l_path;
	}

	
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
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}

	@PostPersist
	 public void setParentID(){
		 LOG.info("Inside Postpersist");
	    for(AssetInstancesDTO ai : this.assetInstances){
	        ai.setAssetDTO(this);
	        LOG.info("asset id********************"+ai.getAssetDTO().getId());
	    }
	 }

	
}
