package com.csc.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="salaries")
@IdClass(SalaryKey.class)
@JsonIgnoreProperties("id")
@Document(indexName = "salary", type = "salary", shards = 1, replicas = 0)
public class Salary implements Serializable {

	private static final long serialVersionUID = 1381649684891283073L;
	
	@Id
	@Column(name="emp_no")
	private Integer id;
	
	@Id
	@Column(name="from_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", timezone="Asia/Kolkata")
	private Date fromDate;
	
	@Column(name="to_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", timezone="Asia/Kolkata")
	private Date toDate;
	
	private Integer salary;
	
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
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
}
