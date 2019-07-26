<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*" %>
<%@ include file="inc/sessioncheck.jsp" %>
<%

	ParseRequest pr = new ParseRequest(request);	

	String mType = pr.getString("mType","");
	
	String chgM = "";
	if(mType.equals("m")){
		chgM = "_m";
	}

	LoginManager lm = LoginManager.getInstance();
	lm.removeSession(SS_M_ID+chgM);	
%>
<SCRIPT LANGUAGE="JavaScript">
	document.location.href = "../index.jsp";
</SCRIPT>
