<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>

<%@ page import="risk.util.*,
				 java.util.*,
				 risk.admin.membergroup.membergroupMng
				 "%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	String mgseq = null;
	String[] mg_menu = null;
	String[] mg_kg = null;
	String[] mg_site = null;
	String menuset = "";
	String xpset = "";
	String siteset = "";

	StringUtil su = new StringUtil();
	membergroupMng mgmng = membergroupMng.getInstance();

	if( request.getParameter("mg_menu") != null ) {
		mg_menu = request.getParameterValues("mg_menu");
		menuset = su.dissplit(mg_menu, ",");
	}
	if( request.getParameter("mg_kg") != null ) {
		mg_kg = request.getParameterValues("mg_kg");
		xpset = su.dissplit(mg_kg, ",");
	}
	if( request.getParameter("mg_site") != null ) {
		mg_site = request.getParameterValues("mg_site");
		siteset = su.dissplit(mg_site, ",");
	}

	if( request.getParameter("mgseq") != null ) {
		mgseq = request.getParameter("mgseq");
		mgmng.updateMGset(mgseq, menuset, xpset, siteset);
	}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	location.href = 'ifram_usergroup_right.jsp?mgseq='+<%=mgseq%>;
//-->
</SCRIPT>