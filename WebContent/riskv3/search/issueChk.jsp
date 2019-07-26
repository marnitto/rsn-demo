<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import ="risk.issue.*"%>
<%@ page import ="risk.util.ParseRequest"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%	
	ParseRequest pr = new ParseRequest(request);
pr.printParams();
	String md_seq = pr.getString("md_seq", "");
	
	IssueMgr im = new IssueMgr();
	out.print(im.issueChk(md_seq));
%>