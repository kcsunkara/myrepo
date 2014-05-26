package com.cognizant.ws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.log4j.Logger;

@Entity
@Table(name = "storage_locations")
@NamedQuery(name = "findBasePath", query = "select path from  StorageLocations where"
				+ " id=:storageLocationId")
		
public class StorageLocations implements java.io.Serializable {

	protected static Logger logger = Logger.getLogger(StorageLocations.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String physical_name;

	@Column
	private String storage_type;

	@Column
	private String name;
	@Column
	private Long site_id;
	@Column
	private String path;
	@Column
	private String read_only;
	@Column
	private String online;
	@Column
	private Long signiant_id;
	@Column
	private Long md5_pool_large;
	@Column
	private Long copy_pool_large;
	@Column
	private Long restore_pool_large;
	@Column
	private Long md5_pool_small;
	@Column
	private Long copy_pool_small;
	@Column
	private Long restore_pool_small;

	/**
	 * @return the signiant_id
	 */
	public Long getSigniant_id() {
		return signiant_id;
	}

	/**
	 * @param signiant_id
	 *            the signiant_id to set
	 */
	public void setSigniant_id(Long signiant_id) {
		this.signiant_id = signiant_id;
	}

	/**
	 * @return the md5_pool_large
	 */
	public Long getMd5_pool_large() {
		return md5_pool_large;
	}

	/**
	 * @param md5_pool_large
	 *            the md5_pool_large to set
	 */
	public void setMd5_pool_large(Long md5_pool_large) {
		this.md5_pool_large = md5_pool_large;
	}

	/**
	 * @return the copy_pool_large
	 */
	public Long getCopy_pool_large() {
		return copy_pool_large;
	}

	/**
	 * @param copy_pool_large
	 *            the copy_pool_large to set
	 */
	public void setCopy_pool_large(Long copy_pool_large) {
		this.copy_pool_large = copy_pool_large;
	}

	/**
	 * @return the restore_pool_large
	 */
	public Long getRestore_pool_large() {
		return restore_pool_large;
	}

	/**
	 * @param restore_pool_large
	 *            the restore_pool_large to set
	 */
	public void setRestore_pool_large(Long restore_pool_large) {
		this.restore_pool_large = restore_pool_large;
	}

	/**
	 * @return the md5_pool_small
	 */
	public Long getMd5_pool_small() {
		return md5_pool_small;
	}

	/**
	 * @param md5_pool_small
	 *            the md5_pool_small to set
	 */
	public void setMd5_pool_small(Long md5_pool_small) {
		this.md5_pool_small = md5_pool_small;
	}

	/**
	 * @return the copy_pool_small
	 */
	public Long getCopy_pool_small() {
		return copy_pool_small;
	}

	/**
	 * @param copy_pool_small
	 *            the copy_pool_small to set
	 */
	public void setCopy_pool_small(Long copy_pool_small) {
		this.copy_pool_small = copy_pool_small;
	}

	/**
	 * @return the restore_pool_small
	 */
	public Long getRestore_pool_small() {
		return restore_pool_small;
	}

	/**
	 * @param restore_pool_small
	 *            the restore_pool_small to set
	 */
	public void setRestore_pool_small(Long restore_pool_small) {
		this.restore_pool_small = restore_pool_small;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhysical_name() {
		return physical_name;
	}

	public void setPhysical_name(String physicalName) {
		physical_name = physicalName;
	}

	public String getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(String storageType) {
		storage_type = storageType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long siteId) {
		site_id = siteId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRead_only() {
		return read_only;
	}

	public void setRead_only(String readOnly) {
		read_only = readOnly;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

}