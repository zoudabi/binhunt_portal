package com.smu.binhunt.pojo;

public class BinHuntResult 
{
	private String jobid;
	private String useremail;
	private String result;
	private int status;
	private String jobdesc;
	private String fname1;
	private String fname2;
	private String downloadlink;
	
	public String getDownloadlink() {
		return downloadlink;
	}
	public void setDownloadlink(String downloadlink) {
		this.downloadlink = downloadlink;
	}
	public String getJobdesc() {
		return jobdesc;
	}
	public void setJobdesc(String jobdesc) {
		this.jobdesc = jobdesc;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
