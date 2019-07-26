<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>    
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.admin.sns_manager.snsManagerMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	snsManagerMgr im = new snsManagerMgr();
	String s_seq = pr.getString("s_seq");
	String s_type = pr.getString("s_type", "");
	String s_date = pr.getString("s_date");
	String s_content = pr.getString("s_content");
	String s_cnt1 = pr.getString("s_cnt1", "0");
	String s_cnt2 = pr.getString("s_cnt2", "0");
	String mode = pr.getString("mode");
	
	if("insert".equals(mode)){
		im.insertSnsInfo(s_type, s_date, s_content, s_cnt1, s_cnt2);
	}else if("update".equals(mode)){
		im.updateSnsInfo(s_seq, s_type, s_date, s_content, s_cnt1, s_cnt2);
	}else{
		im.deleteSnsInfo(s_seq);
	}
	
	String script = "parent.document.location = 'sns_manager.jsp?sDate="+s_date+"'";
%>

<script type="text/JavaScript">
<%=script%>
</script>

	
