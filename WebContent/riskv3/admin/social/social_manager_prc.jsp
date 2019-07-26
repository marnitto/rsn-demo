<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>    
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.issue.IssueMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	IssueMgr im = new IssueMgr();
	String script = "";
	
	String type = pr.getString("type", "");
	String date = pr.getString("date");
	String time = pr.getString("time");
	String data1 = pr.getString("data1", "0");
	String data2 = pr.getString("data2", "0");
	String data3 = pr.getString("data3", "0");
	
	if("delete".equals(pr.getString("op"))){
		im.deleteSocial(type, date);
	}else{
		im.updateSocial(type, date, time, data1, data2, data3);
		script = "parent.document.location = 'social_manager.jsp?sdate="+date+"'";
	}
%>

<script type="text/JavaScript">
<%=script%>
</script>

	
