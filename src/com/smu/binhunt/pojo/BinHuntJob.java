package com.smu.binhunt.pojo;

import java.io.File;
import java.sql.Blob;

public class BinHuntJob implements java.io.Serializable
{
	private int id;
	private String useremail;
	private String jobdesc;
	private String fname1;
	private String fname2;
	private File binary1;
	private File binary2;
	private Boolean packed;
	private String userorg;
	
	public Boolean getPacked() {
		return packed;
	}
	public void setPacked(Boolean packed) {
		this.packed = packed;
	}
	public String getUserorg() {
		return userorg;
	}
	public void setUserorg(String userorg) {
		this.userorg = userorg;
	}
	public String getFname1() {
		return fname1;
	}
	public void setFname1(String fname1) {
		this.fname1 = fname1;
	}
	public String getFname2() {
		return fname2;
	}
	public void setFname2(String fname2) {
		this.fname2 = fname2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getJobdesc() {
		return jobdesc;
	}
	public void setJobdesc(String jobdesc) {
		this.jobdesc = jobdesc;
	}
	public File getBinary1() {
		return binary1;
	}
	public void setBinary1(File binary1) {
		this.binary1 = binary1;
	}
	public File getBinary2() {
		return binary2;
	}
	public void setBinary2(File binary2) {
		this.binary2 = binary2;
	}
	
}
