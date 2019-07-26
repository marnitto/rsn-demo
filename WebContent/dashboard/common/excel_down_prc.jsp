<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ page import="risk.util.DateUtil" %>
<%@ page import="risk.util.PageIndex" %>
<%@ page import="risk.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<%
ParseRequest pr = new ParseRequest(request);
DateUtil du = new DateUtil();

String dataToDisplay = pr.getString("dataToDisplay");
String title = pr.getString("title", "");

if (!"".equals(title)) {
	title = URLEncoder.encode(title, "UTF-8");
	title = title.replaceAll("\\+", " ");
}
String search_date = pr.getString("search_date");
String fileName = title + "_" + search_date + du.getCurrentDate("yyyyMMdd") + ".xls";

System.out.println("excel_down_prc.jsp start");
//pr.printParams();
System.out.println("title=> " + title);
System.out.println("excel_down_prc.jsp end");
%>


<%
response.setContentType("application/vnd.ms-excel; charset=UTF-8");
response.setHeader("Content-Disposition", "attachment;filename="+(fileName));
response.setHeader("Content-Description", "JSP Generated Data");
%>

<%
//Cookie cookie = new Cookie("fileDownload", "true");
//cookie.setPath("/");
//response.addCookie(cookie);
%>
<html>
<head>
<title>서울특별시 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%=dataToDisplay%>
</body>
</html>
