package com.smu.binhunt.service;

import com.smu.binhunt.pojo.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class NativeBinHuntService implements BinHuntService
{
	public int saveJob(BinHuntJob job)
	{
		FileInputStream in1 = null;
		FileInputStream in2 = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String useremail = job.getUseremail();
			String jobdesc = job.getJobdesc();
			String fname1 = job.getFname1();
			String fname2 = job.getFname2();
			String userorg = job.getUserorg();
			Boolean packed = job.getPacked();
			in1 = new FileInputStream(job.getBinary1());
			in2 = new FileInputStream(job.getBinary2());
			
			String sql = "insert into tbl_job(useremail, jobdesc, fname1, fname2, file1, file2, status, userorg, packed) value(?,?,?,?,?,?,?,?,?)";
			conn = GetConnection.getConn();
			if (conn == null)
			{
				System.out.println("null connection");
				return -1;
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, useremail);
			ps.setString(2, jobdesc);
			ps.setString(3, fname1);
			ps.setString(4, fname2);
			ps.setBinaryStream(5, in1, in1.available());
			ps.setBinaryStream(6, in2, in2.available());
			ps.setInt(7, JOB_STATUS_INIT);
			ps.setString(8, userorg);
			ps.setBoolean(9, packed);
			
			if (ps.executeUpdate() > 0) 
			{
				int jobid = -1;
				String sqlid="select @@IDENTITY";
				PreparedStatement psid = conn.prepareStatement(sqlid);
				ResultSet rsid = psid.executeQuery();
				if(rsid.next())
				{
					jobid = rsid.getInt(1);
				}
				GetConnection.close(conn, ps, null);
				return jobid;
			}
			else
			{
				GetConnection.close(conn, ps, null);
				return -1;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			GetConnection.close(conn, ps, null);
			return -1;
		}
	}
	
	public ArrayList<BinHuntResult> getResult(String useremail)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = GetConnection.getConn();
			if (conn == null)
			{
				System.out.println("could not get a connection!");
				return null;
			}
			//first select all the jobs submitted by the user
			//then query result if finished
			//or show the position in the queue if waiting
			ArrayList<BinHuntResult> list = new ArrayList<BinHuntResult>();
			String sqlJob = "select id, jobdesc, fname1, fname2, status from tbl_job where useremail=?";
			ps = conn.prepareStatement(sqlJob);
			ps.setString(1, useremail);
			ResultSet rsjob = ps.executeQuery();
			while(rsjob.next())
			{
				int jobid = rsjob.getInt(1);
				BinHuntResult bhr = new BinHuntResult();
				bhr.setJobid("" + jobid);
				bhr.setStatus(rsjob.getInt(5));
				bhr.setJobdesc(rsjob.getString(2));
				bhr.setFname1(rsjob.getString(3));
				bhr.setFname2(rsjob.getString(4));
				bhr.setDownloadlink("Not ready");
				
				if(bhr.getStatus() == JOB_STATUS_FAILED || bhr.getStatus() == JOB_STATUS_FINISHED)
				{
					String sqlRes = "select resultscore from tbl_job_result where id=?";
					PreparedStatement psRes = conn.prepareStatement(sqlRes);
					psRes.setInt(1, jobid);
					ResultSet rsRes = psRes.executeQuery();
					if(rsRes.next())
					{
						bhr.setResult(rsRes.getString(1));
					}
					if(bhr.getStatus() == JOB_STATUS_FAILED)
					{
						bhr.setDownloadlink("Not ready");
					}
					else bhr.setDownloadlink("<a href=\"Download.action?id=" + jobid + "\">download</a>");
				}
				else if(bhr.getStatus() == JOB_STATUS_INIT)//waiting
				{
					int nCount = getPosition(conn, jobid);
					String strSts = "Your job is waiting in the queue as No. " + nCount;
					if(nCount<0)strSts = "Error while querying status";
					bhr.setResult(strSts);
				}
				else if(bhr.getStatus() == JOB_STATUS_DOING)
				{
					String strSts = "Your job is running at the moment, please check it later.";
					bhr.setResult(strSts);
				}
				
				list.add(bhr);
			}
			
			GetConnection.close(conn, ps, null);
			return list;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			GetConnection.close(conn, ps, null);
			return null;
		}
	}
	
	public int getPosition(Connection conn, int jobid)
	{
		try
		{
			boolean bConn = false;
			if(conn == null)
			{
				bConn = true;
				conn = GetConnection.getConn();
				if(conn == null)
				{
					System.out.println("Error getting connection.");
					return -1;
				}
			}
			String strT = "select count(*) from tbl_job where status=? and id<?";
			PreparedStatement psT = conn.prepareStatement(strT);
			psT.setInt(1, JOB_STATUS_INIT);
			psT.setInt(2, jobid);
			ResultSet rsT = psT.executeQuery();
			int nCount = 0;
			if(rsT.next())
			{
				nCount = rsT.getInt(1);
			}
			nCount += 1;
			
			if(bConn)
			{
				GetConnection.close(conn, psT, null);
			}
			else
			{
				psT.close();
			}
			return nCount;
		}
		catch(Exception ex)
		{
			return -1;
		}
	}
	
	public InputStream getResultFile(int id)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		InputStream is = null;
		try
		{
			conn = GetConnection.getConn();
			if (conn == null)
			{
				System.out.println("could not get a connection!");
				return null;
			}
			
			String sqlJob = "select resultzip from tbl_job_result where id=?";
			ps = conn.prepareStatement(sqlJob);
			ps.setInt(1, id);
			ResultSet rsjob = ps.executeQuery();
			if(rsjob.next())
			{
				Blob sc = rsjob.getBlob(1);
				is = sc.getBinaryStream();
				//return is;
			}
			
			GetConnection.close(conn, ps, null);
			return is;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			GetConnection.close(conn, ps, null);
			return null;
		}
	}
}

class GetConnection
{
	/**
	 * acquire db connection
	 * @return Connection
	 * 
	 */
	public static Connection getConn()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://10.4.8.24:3306/binhunt?user=user&password=pass";
			Connection connection = DriverManager.getConnection(url);
			return connection;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	  * release
	  * @param conn
	  * @param rs
	  * @param st
	  */
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs)
	{
		if(rs!=null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try
			{
				ps.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(conn!=null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
