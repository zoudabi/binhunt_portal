package com.smu.binhunt.action;

import java.io.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.smu.binhunt.pojo.BinHuntJob;
import com.smu.binhunt.service.BinHuntService;
import com.smu.binhunt.service.NativeBinHuntService;


public class AddAction extends ActionSupport{
	private static final int BUFFER_SIZE = 16 * 1024 ;
	
	private String useremail;
	private String jobdesc;
	private File[] binary;
	private String[] binaryFileName;
	private String[] binaryContentType;
	
	private Boolean packed;
	private String userorg;
	
	private int position;//position in the waiting queue;
	private String qrysuccess;
	
	public String getQrysuccess()
	{
		return qrysuccess;
	}
	
	public int getPosition()
	{
		return this.position;
	}

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


	public String[] getBinaryFileName() {
		return binaryFileName;
	}


	public void setBinaryFileName(String[] binaryFileName) {
		this.binaryFileName = binaryFileName;
	}


	public String[] getBinaryContentType() {
		return binaryContentType;
	}


	public void setBinaryContentType(String[] binaryContentType) {
		this.binaryContentType = binaryContentType;
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


	public File[] getBinary() {
		return binary;
	}


	public void setBinary(File[] binary) {
		this.binary = binary;
	}

    public void validate()
    {
    	if(getUseremail().length() == 0)
    	{
    		//getText() from ActionSupport
    		//it will return the corresponding configured value from xxx.properties
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
    	
    	if(getBinary()==null || getBinary().length <2)
    	{
    		addFieldError("binary", getText("binary.required"));
    	}
    }
	
	private static void copy(File src, File dst)
	{
		try
		{
			InputStream in = null ;
			OutputStream out = null ;
			try
			{
				in = new BufferedInputStream( new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);
				byte [] buffer = new byte [BUFFER_SIZE];
				while (in.read(buffer) > 0 )
				{
					out.write(buffer);
				}
			}
			finally
			{
				if ( null != in)
				{
					in.close();
				}
				if ( null != out)
				{
					out.close();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
   
    private static String getExtention(String fileName)
    {
    	int pos = fileName.lastIndexOf( "." );
    	return fileName.substring(pos);
    } 
	
	public String execute()
	{
		BinHuntJob job = new BinHuntJob();
		job.setJobdesc(jobdesc);
		job.setUseremail(useremail);
		job.setBinary1(binary[0]);
		job.setBinary2(binary[1]);
		job.setFname1(binaryFileName[0]);
		job.setFname2(binaryFileName[1]);
		job.setPacked(packed);
		job.setUserorg(userorg);
		
		NativeBinHuntService bhs = new NativeBinHuntService();
		int jobid = -1;
		jobid = bhs.saveJob(job);
		if(jobid >= 0)//useremail.equals("admin") && jobdesc.equals("admin"))
		{
			sendMail();
			Connection conn = null;
			position = bhs.getPosition(conn, jobid);
			qrysuccess = "<a href=\"Query.action?useremail=" + useremail + "\">here</a>";
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}
	}
	
	private String username = getText("sendmailuser");
	private String password = getText("sendmailpass");
	private String mailsubject = "BinHunt job submission succeed";
	private String body = "Your job submission to BinHunt website succeeded. You may get your result notification when it is finished.";
	private String mailfrom = "binhunt@flyer.sis.smu.edu.sg";
	private String personalName = "binhunt";
	private boolean sendMail()
	{
		try
		{
			String strHeadName = "Hi ";
			String strHeadValue = useremail;
			int posAt = strHeadValue.indexOf('@');
			strHeadValue = strHeadValue.substring(0, posAt);
			
			Properties props = new Properties();
			Authenticator auth = new Email_Autherticator();
			props.put("mail.smtp.host", "smtp.smu.edu.sg");
			
			//props.put("mail.smtp.socketFactory.port", "465");
			//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			//props.put("mail.smtp.port", "465");
			
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			
			MimeMessage message = new MimeMessage(session);
			message.setSubject(mailsubject);
			
			String urlqry = "https://flyer.sis.smu.edu.sg/binhunt/Query.action?useremail=" + useremail;
			String strqry= "<br>Please follow this link <a href=\" + urlqry + \">" + urlqry + "</a> to check your job's status";
			body = "Hello " + strHeadValue + "<br>" + body + strqry;
			//message.setText(body);
			message.setContent(body, "text/html");
			message.setHeader(strHeadName, strHeadValue);
			message.setSentDate(new Date());
			Address address = new InternetAddress(mailfrom, personalName);
			message.setFrom(address);
			Address toAddress = new InternetAddress(useremail);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message);
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
	
	class Email_Autherticator extends Authenticator
	{
		public Email_Autherticator()
		{
			super();
		}

		public Email_Autherticator(String user, String pwd)
		{
			super();
			username = user;
			password = pwd;
		}
		
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(username, password);
		}
	}
}
