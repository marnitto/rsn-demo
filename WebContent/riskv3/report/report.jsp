<%@ page contentType = "text/html; charset=utf-8" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<FRAMESET COLS="300,*"  frameborder="0" border="0">
	<FRAME SRC="report_left_menu.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAME SRC="issue_report_list.jsp?ir_type=D1" NAME="contentsFrame" noresize>
</FRAMESET>