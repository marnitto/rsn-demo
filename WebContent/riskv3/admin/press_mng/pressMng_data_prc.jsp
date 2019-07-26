<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.*
                 	,risk.admin.pressMng.pressMng
					,risk.util.*
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	pressMng pMgr = new pressMng();
	
	
	String d_seq = pr.getString("d_seq");
	String keywords = pr.getString("keywords", "");
	
	int result = pMgr.updateKeywords(d_seq , keywords);

	out.print(result);
	
%>