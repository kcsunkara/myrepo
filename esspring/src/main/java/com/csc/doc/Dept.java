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
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="dept")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties("emps")
@Document(indexName = "org", type = "dept", shards = 1, replicas = 0)
//@Setting(settingPath = "/dept_settings.json")
//@Mapping(mappingPath = "/mappings/dept_mappings.json")
public class Dept implements Serializable {

	private static final long serialVersionUID = 1792338408546855300L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dept_no")
	@org.springframework.data.annotation.Id
	@Field(type = FieldType.Integer)
	private Integer id;
	
	@Column(name="dept_name")
	@Field(type = FieldType.String, analyzer="ngram_analyzer")
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
