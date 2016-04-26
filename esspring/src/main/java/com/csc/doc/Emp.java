package com.csc.doc;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "org", type = "emp", shards = 1, replicas = 0)
public class Emp {

	@Id
//	@Field(type = FieldType.Long, store = true)
	private long id;
	
	private String name;
	
	/*@Field(type = FieldType.Nested)
	private Dept dept;*/
	
	private Date fromDate;
	
	private Timestamp toDate;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}
	
}
