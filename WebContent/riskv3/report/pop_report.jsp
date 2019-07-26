<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean							
				,risk.util.PageIndex
				,risk.issue.IssueReportBean
				"%>
<%
	ParseRequest pr = new ParseRequest(request);
	ArrayList arrIrBean = new ArrayList();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	
	irBean = irMgr.getReportBean(pr.getString("ir_seq"));	
%>
<%=irBean.getIr_html()%>           

