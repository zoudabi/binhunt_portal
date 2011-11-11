package com.smu.binhunt.service;

import java.util.ArrayList;

import com.smu.binhunt.pojo.BinHuntJob;
import com.smu.binhunt.pojo.BinHuntResult;

public interface BinHuntService
{
	public int JOB_STATUS_FAILED = -1;
	public int JOB_STATUS_INIT = 0;
	public int JOB_STATUS_DOING = 1;
	public int JOB_STATUS_FINISHED = 2;
	
	/**
	 * @param job
	 * @return jobid when success, -1 when fail
	 */
	public int saveJob(BinHuntJob job); 
	
	public ArrayList<BinHuntResult> getResult(String useremail);
}
