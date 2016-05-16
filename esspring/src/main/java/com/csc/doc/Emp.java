package com.csc.doc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="emp")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@Setting(settingPath = "/settings/settings.json")
@Mapping(mappingPath = "/mappings/emp_mappings.json")
@Document(indexName = "org", type = "emp", shards = 1, replicas = 0)
public class Emp implements Serializable {

	private static final long serialVersionUID = 3963588439614553174L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="emp_no")
	@org.springframework.data.annotation.Id
//	@Field(type = FieldType.Integer)
	private Integer id;
	
	@Column(name="first_name")
//	@Field(type = FieldType.String, analyzer="ngram_analyzer", searchAnalyzer="ngram_analyzer")
//	@Field(type = FieldType.String)
	private String firstName;
	
	@Column(name="last_name")
//	@Field(type = FieldType.String, analyzer="ngram_analyzer", searchAnalyzer="ngram_analyzer")
//	@Field(type = FieldType.String)
	private String lastName;
	
	@Column(name="birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", timezone="Asia/Kolkata")
//	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd/MMM/yyyy")
	private Date birthDate;
	
	@Column(name="gender")
//	@Field(type = FieldType.String)
	private Character gender;
	
	@Column(name="email")
//	@Field(type = FieldType.String)
	private String email;
	
	@Column(name="hire_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", timezone="Asia/Kolkata")
//	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd/MMM/yyyy")
	private Date hireDate;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Dept.class)
	@JoinColumn(name = "dept_no")
//	@Field(type = FieldType.Nested, includeInParent=true)
	private Dept dept;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER, mappedBy="")
	@JoinColumn(name="emp_no")
//	@Field(type = FieldType.Nested, includeInParent=true)
	private Set<Salary> salary;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="emp_no")
//	@Field(type = FieldType.Nested, includeInParent=true)
	private Set<Title> titles;

	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="emp_no")
//	@Field(type = FieldType.Nested, includeInParent=true)
	private Set<Phone> phones;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="emp_no")
//	@Field(type = FieldType.Nested, includeInParent=true)
	private Set<Address> addresses;
	
	@Transient
//	@Field(type = FieldType.Integer)
	private Integer deptNo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	@JsonIgnore
	public Dept getDept() {
		return dept;
	}

	@JsonIgnore
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Set<Salary> getSalary() {
		return salary;
	}

	public void setSalary(Set<Salary> salary) {
		this.salary = salary;
	}

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Integer getDeptNo() {
		if(dept != null) {
			return dept.getId();
		}
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}
	
}
