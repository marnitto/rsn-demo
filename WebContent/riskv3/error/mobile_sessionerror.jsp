<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<html>
<head>
<title>SPEED System</title>
</head>
<body>
<%
/*
HttpSession sess = request.getSession(true);
         out.print("<b>[Requested Session유효여부]:</b>" +  request.isRequestedSessionIdValid() + "<BR>");
         out.print("<b>[Session 쿠키사용여부]:</b>" + request.isRequestedSessionIdFromCookie() + "<BR>"); 
         out.print("<b>[Session URL사용여부]:</b>" + request.isRequestedSessionIdFromURL() + "<BR>"); 
         out.print("<b>[Requested Session Id]:</b>" + request.getRequestedSessionId() + "<BR>");
         out.print("<b>[Session ID]:</b>" + sess.getId() + "<BR>");
		 out.print("<b>[Session Max Time]:</b>" + session.getMaxInactiveInterval() + "<BR>");


		 String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;

	out.print("SS_M_NO : " + SS_M_NO + "<br>");
	out.print("SS_M_ID : " + SS_M_ID + "<br>");
	out.print("SS_M_NAME : " + SS_M_NAME + "<br>");
	out.print("SS_MG_NO : " + SS_MG_NO + "<br>");
	out.print("SS_TITLE : " + SS_TITLE + "<br>");
	out.print("SS_URL : " + SS_URL + "<br>");


	if ((SS_M_ID.equals("")) ) {

		out.print("세션 없음");
    }else {
	}
	*/
%>
</br>
</br>
</br>

<h2 align="center">
세션에서 사용자 정보를 찾을수 없습니다.
</h2>

</br>
<center>
시스템에 다시 접속하시기 바랍니다.
</center>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	document.location = '../../index.jsp';
</SCRIPT>