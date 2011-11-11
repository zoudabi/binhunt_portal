package com.smu.binhunt.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;
import com.smu.binhunt.service.NativeBinHuntService;

public class DownloadAction extends ActionSupport
{
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}
	
	//to get the file content, from a file or database
	public InputStream getInputStream() throws Exception
	{
		//return new FileInputStream("somefile.rar");
		NativeBinHuntService nbh = new NativeBinHuntService();
		int nId = Integer.parseInt(id);
		return nbh.getResultFile(nId);
		//return new ByteArrayInputStream(("Struts2 file download testing. id is: " + id).getBytes());
	}
	
	//corresponding to ${fileName}
	public String getFileName()
	{
		return id+".zip";
	}
}
