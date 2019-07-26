<%@ page contentType="text/html; charset=EUC_KR" %>
<%@ page import="risk.util.ParseRequest"
%>
<%
    ParseRequest    pr = new ParseRequest(request);
    if ( pr.getString("INIT").equals("INIT") ) {
        session.setAttribute("INIT","INIT");
    }
%>
<FRAMESET COLS="300,*" frameborder="0" border="0">
	<FRAME SRC="search_main_left.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAME SRC="search_main_contents.jsp" NAME="contentsFrame" noresize>
</FRAMESET>
