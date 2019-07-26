<%@ page contentType="text/html; charset=EUC_KR" %>
<%@ include file="../inc/sessioncheck.jsp" %>  
<%@ page import="risk.util.ParseRequest"
%>
<%
    ParseRequest    pr = new ParseRequest(request);
    String init =  pr.getString("INIT");
%>
<FRAMESET COLS="300,*"  frameborder="0" border="0">
	<FRAME SRC="statistics_left_menu.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAME SRC="statistics_main.jsp" NAME="contentsFrame" noresize>
</FRAMESET>