package com.csc.doc;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "org", type = "dept", shards = 1, replicas = 0)
public class Dept {

	@Id
//	@Field(type = FieldType.Long, store = true)
	private long id;
	
	private String name;
	
	@Field(type = FieldType.Nested)
	private List<Emp> empList;
	
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
	
	public List<Emp> getEmpList() {
		return empList;
	}
	public void setEmpList(List<Emp> empList) {
		this.empList = empList;
	}
	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + ", empList=" + empList
				+ "]";
	}
	
}
