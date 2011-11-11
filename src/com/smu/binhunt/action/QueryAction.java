package com.smu.binhunt.action;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionSupport;
import com.smu.binhunt.pojo.BinHuntResult;
import com.smu.binhunt.service.BinHuntService;
import com.smu.binhunt.service.NativeBinHuntService;

public class QueryAction extends ActionSupport 
{
	private String useremail;
	private List reslist;

	public List getReslist() {
		return reslist;
	}

	public void setReslist(List reslist) {
		this.reslist = reslist;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	
    public void validate()
    {
    	if(getUseremail().length() == 0)
    	{
    		addFieldError("useremail", getText("useremail.required"));
    	}
    	else
    	{
	    	String check = "^([a-z0-9A-Z]+[-\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    	Pattern regex = Pattern.compile(check);
	    	Matcher matcher = regex.matcher(getUseremail());
	    	boolean isMatched = matcher.matches();
	    	if(!isMatched)
	    	{
	    		addFieldError("useremail", getText("useremail.badformat"));
	    	}
    	}
    }
    
    public String execute()
    {
    	BinHuntService bhs = new NativeBinHuntService();
    	ArrayList<BinHuntResult> listres = bhs.getResult(useremail);
    	reslist = new ArrayList();
    	for(int i=0; i<listres.size(); ++i)
    	{
    		reslist.add(listres.get(i));
    	}
    	
    	return SUCCESS;
    }
}
