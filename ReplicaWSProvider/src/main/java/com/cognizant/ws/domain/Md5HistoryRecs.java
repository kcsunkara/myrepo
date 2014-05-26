package com.cognizant.ws.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "md5_history_recs")
public class Md5HistoryRecs implements Serializable {

	protected static Logger logger = Logger.getLogger(Md5HistoryRecs.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column
    private Long asset_instance_id;
    
    @Column
    private String md5;
    
    @Column
    private Date create_date;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the asset_instance_id
	 */
	public Long getAsset_instance_id() {
		return asset_instance_id;
	}

	/**
	 * @param asset_instance_id the asset_instance_id to set
	 */
	public void setAsset_instance_id(Long asset_instance_id) {
		this.asset_instance_id = asset_instance_id;
	}

	/**
	 * @return the md5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * @param md5 the md5 to set
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
    
   

}
