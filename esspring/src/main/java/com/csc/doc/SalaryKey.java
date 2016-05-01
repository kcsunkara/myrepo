package com.csc.doc;

import java.io.Serializable;
import java.util.Date;

public class SalaryKey implements Serializable {
	
	private static final long serialVersionUID = 323228733305970210L;
	
	private Integer id;
	private Date fromDate;
	
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

}
