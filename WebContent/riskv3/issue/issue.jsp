<%@ page contentType="text/html; charset=EUC_KR" %>
<%@ include file="../inc/sessioncheck.jsp" %>  
<%@ page import="risk.util.ParseRequest"
%>
<%
    ParseRequest    pr = new ParseRequest(request);
    String init =  pr.getString("INIT");
    String rtnpage =  pr.getString("rtnpage");
    
    if (init.equals("INIT") ) {
        session.setAttribute("INIT","INIT");
    }
    
    if(rtnpage.equals("")) {
    	rtnpage = "issue_data_list.jsp";
    }
    
%>
<FRAMESET COLS="300,*"  frameborder="0" border="0">
	<FRAME SRC="issue_left_menu.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAME SRC="<%=rtnpage %>" NAME="contentsFrame" noresize>
</FRAMESET>