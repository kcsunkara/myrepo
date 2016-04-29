package com.csc.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "org", type = "emp", shards = 1, replicas = 0)
public class Emp {

	@Id
	private long emo_no;
	private String first_name;
	private String last_name;
	private Date birth_date;
	private Character gender;
	private String email;
	private Date hire_date;
	
	public long getEmo_no() {
		return emo_no;
	}
	public void setEmo_no(long emo_no) {
		this.emo_no = emo_no;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
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
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	@Override
	public String toString() {
		return "Emp [emo_no=" + emo_no + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", birth_date=" + birth_date
				+ ", gender=" + gender + ", email=" + email + ", hire_date="
				+ hire_date + "]";
	}
	
}
