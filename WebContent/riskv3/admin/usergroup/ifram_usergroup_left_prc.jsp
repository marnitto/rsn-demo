<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.membergroup.*
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

	ParseRequest    pr = new ParseRequest(request);
	
	

    String mode = pr.getString("mode");
    String mgseq = pr.getString("mgseq");
	String val = pr.getString("val");
	String msg = "";
	
//	val = new String( val.getBytes("8859_1"), "euc-kr" );
	
	

	membergroupMng MGmng = membergroupMng.getInstance();
	


	if( mode.equals("ins") ) {
		MGmng.insertMG(val);
	} else if( mode.equals("edit") ) {
		MGmng.updateMG(mgseq, val);
	} else if( mode.equals("del") ) {
		if( MGmng.deleteMG(mgseq) > 0 ) msg = "parent.alert_msg();";
	}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%=msg%>
	location.href = 'ifram_usergroup_left.jsp';
//-->
</SCRIPT>