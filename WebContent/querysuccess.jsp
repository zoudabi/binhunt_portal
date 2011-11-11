<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.smu.binhunt.pojo.BinHuntResult"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>BinHunt Results</title>
</head>
<body>
<h1>BinHunt online service</h1>
<hr>
<p>You are welcome to use this website to submit your binary files to compare their semantic differences using BinHunt.</p>
<br>
<br>

Your jobs' results are as follows.
<br>

<table border="1" >
<tr>
	<td>job_id</td>
	<td>job_desc</td>
	<td>files</td>
	<td>job_status/job_result</td>
	<td>result download</td>
</tr>
<s:iterator value="reslist">
	<tr>
    <td><s:property value="jobid" /></td>
    <td><s:property value="jobdesc" /></td>
    <td><s:property value="fname1" /> and <s:property value="fname2" /></td>
    <td><s:property value="result" /></td>
    <td><s:property value="downloadlink" escape="false"/></td>
    </tr>
</s:iterator>
</table>

<br>
<br>
<hr>
<h2>Copy right reserved.</h2>
</body>
</html>