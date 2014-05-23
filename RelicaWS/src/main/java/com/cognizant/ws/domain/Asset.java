package com.cognizant.ws.domain;

/**
 * Comments
 */
import java.util.Date;
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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import com.cognizant.ws.util.DateFormatValid;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="assets")
@NamedQueries(
		{
			@NamedQuery(name="updatePolicy",query="update Asset a set a.policy=:policyObj where a.id=:assetId"),
			@NamedQuery(name="allAssets",query="select id,dam_internal_id," +
			"name,fs_path,repository,filesize," +
			"policy.id,asset_md5,user_md5 from  Asset"),
			@NamedQuery(name="isAssetExists",query="select 'x' from  Asset where" +
					" name=:name and filesize=:filesize and fs_path=:fsPath")
		}
		)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
//@FilterDef(name="statusFilter", parameters={@ParamDef(name="param1", type="string"), @ParamDef(name="param2", type="string")})

public class Asset implements java.io.Serializable{
	
	private static Logger LOG = Logger.getLogger(Asset.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	@NotEmpty(message = "{enter.name}")
	private String name;
	
	@Column
	@NotNull(message = "{enter.filesize}")
	private Long filesize;
	
	@Transient
	private Long policy_id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Policy.class)
	@JoinColumn(name = "policy_id")
	private Policy policy;
	
	@Column
	private String asset_md5;
	
	@Column
	private String user_md5;
	
	@Column
	@DateFormatValid
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm")
    private Date created_date = new Date();
	
	@Column
	private Date  delete_date;
	
	@Column
	@NotNull(message = "{enter.fspath}")
	private String fs_path;
	
	@NotEmpty(message = "{instances.not.empty}")
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "asset")
	@Valid
	//@Filter(name = "statusFilter", condition="status.equals(:param1) or status.equals(:param2)")
	public List<AssetInstances> assetInstances;
	
	public List<AssetInstances> getAssetInstances() {
		return assetInstances;
	}
	
	public void setAssetInstances(List<AssetInstances> assetInstances) {
		this.assetInstances = assetInstances;
	}
	
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
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
	//@JsonDeserialize(using=JsonDateDeserializer.class)
	public void setCreated_date(Date  created_date) {
		this.created_date = created_date;
	}
	public Date getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
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
	
	public String getFs_path() {
		return fs_path;
	}

	public void setFs_path(String fsPath) {
		fs_path = fsPath;
	}
	
	@JsonIgnore
	public Policy getPolicy() {
		return policy;
	}

	@JsonIgnore
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	
	public Long getPolicy_id() {
		if(this.policy != null)
			return this.policy.getId();
		else
			return policy_id;
	}

	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
	}

	@PostPersist
	 public void setParentID(){
		 LOG.info("Inside Postpersist");
	    for(AssetInstances ai : this.assetInstances){
	        ai.setAsset(this);
	        LOG.info("asset id********************"+ai.getAsset().getId());
	    }
	 }

	
}
