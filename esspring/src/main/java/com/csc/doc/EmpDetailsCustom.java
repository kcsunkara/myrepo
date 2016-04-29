package com.csc.doc;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(indexName = "pss_db", type = "emp_details", shards = 1, replicas = 0)
public class EmpDetailsCustom {

	@Id
	private long empno;
	private String firstname;
	private String lastname;
	private long deptno;
	private String deptname;
	private long salary;
	private Date fromdate;
	private Date todate;
	
	@JsonIgnore
	private String type;

	public long getEmpno() {
		return empno;
	}

	public void setEmpno(long empno) {
		this.empno = empno;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public long getDeptno() {
		return deptno;
	}

	public void setDeptno(long deptno) {
		this.deptno = deptno;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EmpDetailsCustom [empno=" + empno + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", deptno=" + deptno
				+ ", deptname=" + deptname + ", salary=" + salary
				+ ", fromdate=" + fromdate + ", todate=" + todate + "]";
	}

}
