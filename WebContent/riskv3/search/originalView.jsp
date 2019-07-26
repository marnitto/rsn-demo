<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.search.MetaMgr" %>
<%@ page import="risk.search.MetaBean" %>
<%@ page import="risk.util.ParseRequest" %>
<%
	ParseRequest pr = new ParseRequest(request);
	MetaMgr meta = new MetaMgr();
	MetaBean mbean = new MetaBean();
	String md_seq = pr.getString("md_seq");
	String st_mame = pr.getString("st_name","");
	
	mbean = meta.getMetaData(md_seq,st_mame);
	
	String content = mbean.getMd_content();
%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=SS_URL%>/riskv3/css/basic.css" type="text/css">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><%=content.replaceAll("\n", "<br>")%></td>
	</tr>	
</table>
</body>
</html>