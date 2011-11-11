<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>Query BinHunt Jobs</title>
</head>
<body>

<h1>BinHunt online service</h1>
<hr>
<p>You are welcome to use this website to submit your binary files to compare their semantic differences using BinHunt.</p>
<br>
<br>
	<s:form action="Query" method="POST" enctype="multipart/form-data">
		<s:textfield name="useremail" label="Your email" size="65" required="true"/>
		<s:submit/>
	</s:form>

<br>
<br>
<hr>
<h2>Copy right reserved.</h2>
</body>
</html>