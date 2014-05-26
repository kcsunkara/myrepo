package com.cognizant.ws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@Table(name = "batchjobs")
public class BatchJob implements java.io.Serializable {

	protected static Logger logger = Logger.getLogger(BatchJob.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="job_id")
	private Long id;

	@Column
	private String job_type;

	@Column
	private Long asset_instance_id;

	@Column
	private Long storage_id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date start_date;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date end_date;

	@Column
	private String error_message;

	@Column
	private String job_status;
	
	@Column
	private String job_detail;
	
	@Column(name="request_host_name")
	private String requestHostName;
	

	/**
	 * @return the requestHostName
	 */
	public String getRequestHostName() {
		return requestHostName;
	}

	/**
	 * @param requestHostName the requestHostName to set
	 */
	public void setRequestHostName(String requestHostName) {
		this.requestHostName = requestHostName;
	}

	public String getJob_detail() {
		return job_detail;
	}

	public void setJob_detail(String job_detail) {
		this.job_detail = job_detail;
	}


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
	 * @return the job_type
	 */
	public String getJob_type() {
		return job_type;
	}

	/**
	 * @param job_type
	 *            the job_type to set
	 */
	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	/**
	 * @return the asset_instance_id
	 */
	public Long getAsset_instance_id() {
		return asset_instance_id;
	}

	/**
	 * @param asset_instance_id
	 *            the asset_instance_id to set
	 */
	public void setAsset_instance_id(Long asset_instance_id) {
		this.asset_instance_id = asset_instance_id;
	}

	/**
	 * @return the storage_id
	 */
	public Long getStorage_id() {
		return storage_id;
	}

	/**
	 * @param storage_id
	 *            the storage_id to set
	 */
	public void setStorage_id(Long storage_id) {
		this.storage_id = storage_id;
	}

	/**
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date
	 *            the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}

	/**
	 * @param error_message
	 *            the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	/**
	 * @return the job_status
	 */
	public String getJob_status() {
		return job_status;
	}

	/**
	 * @param job_status
	 *            the job_status to set
	 */
	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}

}
