<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueReportMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.util.StringUtil
                ,risk.util.DateUtil" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	
	StringUtil	su 	= new StringUtil();
	
	String info = pr.getString("info","");
	String[] ar_info = info.split(",");  
	
	String l_seq = ar_info[0];
	String ab_seq = ar_info[1];

	IssueReportMgr iMgr = new IssueReportMgr();
	iMgr.AddCnt(l_seq, ab_seq);
%>
