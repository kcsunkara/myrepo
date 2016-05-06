package com.csc.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="titles")
@IdClass(TitleKey.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id, fromDate")
@JsonIgnoreProperties("id")
@Document(indexName = "title", type = "title", shards = 1, replicas = 0)
public class Title implements Serializable {

	private static final long serialVersionUID = -5246410547252139497L;

	@Id
	@Column(name="emp_no")
	private Integer id;
	
	@Id
	@Column(name="from_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd/MMM/yyyy")
	private Date fromDate;
	
	@Column(name="to_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd/MMM/yyyy")
	private Date toDate;
	
	@Column(name="title")
	private String title;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
