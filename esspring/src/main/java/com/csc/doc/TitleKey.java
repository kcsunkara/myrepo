package com.csc.doc;

import java.io.Serializable;
import java.util.Date;

public class TitleKey implements Serializable {
	
	private static final long serialVersionUID = 1071913889863225642L;
	
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
