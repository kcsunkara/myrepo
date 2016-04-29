package com.csc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "org", type = "dept", shards = 1, replicas = 0)
public class Dept {

	@Id
	private long dept_no;
	private String dept_name;
	
	public long getDept_no() {
		return dept_no;
	}
	public void setDept_no(long dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
	
}
