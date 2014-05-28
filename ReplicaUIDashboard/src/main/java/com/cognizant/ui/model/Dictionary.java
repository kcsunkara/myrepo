package com.cognizant.ui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dictionary")
public class Dictionary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="dictionary")
	private String dictionary;
	
	@Column(name = "keyname")
	private String keyname;
	
	@Column(name = "value")
	private String value;
	
	
	public Dictionary() {
	}

	/**
	 * @return the dictionary
	 */
	public String getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary the dictionary to set
	 */
	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * @return the keyname
	 */
	public String getKeyname() {
		return keyname;
	}

	/**
	 * @param keyname the keyname to set
	 */
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
