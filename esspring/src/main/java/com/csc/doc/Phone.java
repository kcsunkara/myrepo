package com.csc.doc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="phones")
@IdClass(PhoneKey.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id, fromDate")
@JsonIgnoreProperties("id")
@Document(indexName = "phone", type = "phone", shards = 1, replicas = 0)
public class Phone implements Serializable {

	private static final long serialVersionUID = 4763537878197693079L;

	@Id
	@Column(name="emp_no")
	private Integer id;
	
	@Id
	@Column(name="phone_type")
	private Character phoneType;
	
	@Column(name="phone_no")
	private String phoneNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Character getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Character phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
}
