<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="risk.admin.site.SiteMng,
                 risk.util.DateUtil,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    String mode = pr.getString("mode");
    
    SiteMng sm = new SiteMng();
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=site_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    String siteHtml = "";
    if( mode.equals("all") ) {
		siteHtml = sm.getAllSiteHtml();
    }else {
    	siteHtml = sm.getSiteHtml();
    }
%>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
<!--
body {
	scrollbar-face-color: #FFFFFF; 
	scrollbar-shadow-color:#B3B3B3; 
	scrollbar-highlight-color:#B3B3B3; 
	scrollbar-3dlight-color: #FFFFFF; 
	scrollbar-darkshadow-color: #EEEEEE; 
	scrollbar-track-color: #F6F6F6; 
	scrollbar-arrow-color: #8B9EA6;
}
-->
</style>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">
<%
	out.println(siteHtml);
%>
</body>