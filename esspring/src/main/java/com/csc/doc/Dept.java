package com.csc.doc;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="dept")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties("emps")
@Document(indexName = "dept", type = "dept", shards = 1, replicas = 0)
public class Dept implements Serializable {

	private static final long serialVersionUID = 1792338408546855300L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dept_no")
	@org.springframework.data.annotation.Id
	private Integer id;
	
	@Column(name="dept_name")
	private String deptName;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "dept")
	public List<Emp> emps;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<Emp> getEmps() {
		return emps;
	}
	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}
	
}
