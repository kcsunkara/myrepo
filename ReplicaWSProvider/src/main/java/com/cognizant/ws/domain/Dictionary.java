/**
 * 
 */
package com.cognizant.ws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 236320
 *
 */
@Entity
@Table(name = "dictionary")
public class Dictionary implements java.io.Serializable{
	
	
    @Id
	@Column
	private String dictionary;

	@Column
	private String keyname;

	@Column
	private String value;

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
