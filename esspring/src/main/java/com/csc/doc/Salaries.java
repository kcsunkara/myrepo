package com.csc.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="salaries")
@IdClass(SalaryKey.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id, fromDate")
@JsonIgnoreProperties("id")
//@Document(indexName = "org", type = "salaries", shards = 1, replicas = 0)
public class Salaries implements Serializable {

	private static final long serialVersionUID = 1381649684891283073L;
	
	@Id
	@Column(name="emp_no")
	private Integer id;
	
	@Id
	@Column(name="from_date")
	private Date fromDate;
	
	private Date to_date;
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
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
}
