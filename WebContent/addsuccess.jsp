<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="com.smu.binhunt.action.AddAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>Job submission succeeded</title>
</head>
<body>

<h1>BinHunt online service</h1>
<hr>
<p>You are welcome to use this website to submit your binary files to compare their semantic differences using BinHunt.</p>
<br>
<br>

<h3>You have just successfully submit a job.</h3>
<br>
You are waiting as No.<s:property value="position"/> in the queue, and it will probably take <s:property value="position"/> half-hours to be done.
<br>
You can query the comparison results by your email: <s:property value="useremail" /> later.
<br>
The query page is <s:property value="qrysuccess" escape="false"/>.


<br>
<br>
<hr>
<h2>Copy right reserved.</h2>

</body>
</html>