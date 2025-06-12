package com.login.model;

import java.io.Serializable;
import java.sql.Date;

public class Worker implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pid;
	private String name;
	private String sex;
	private int age;
	private String nationality;
	private String idcard;
	private Date birthday;
	private String college;
	private String address;
	private String tele;
	private String job;
	private Date jobtime;
	private String photo;
	private String password;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getJobtime() {
		return jobtime;
	}
	public void setJobtime(Date jobtime) {
		this.jobtime = jobtime;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		// 原逻辑：使用姓名生成文件名（导致乱码）
		// this.photo = this.pid + "_" + this.name;
		// 修正后：使用身份证号生成文件名（匹配真实信息）
		this.photo = this.pid + "_" + this.idcard;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
