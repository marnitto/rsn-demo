<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	IssueMgr im = new IssueMgr();
	
	String tagGroupCode = pr.getString("tagGroupCode");
	
	ArrayList tagList = new ArrayList();	
	tagList = im.getTagCode(tagGroupCode);
	
	String outTag = "";
	if(tagList.size() > 0){
		for(int i = 0;i < tagList.size(); i++){
			IssueDataBean idb = (IssueDataBean)tagList.get(i);
			if(outTag.equals("")){
				outTag = idb.getItc_seq()+","+idb.getItc_name();
			}else{
				outTag += "@"+idb.getItc_seq()+","+idb.getItc_name();
			}
		}
		out.print(outTag);
	}else{
		out.print("");
	}
%>