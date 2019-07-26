<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="risk.search.GetKGMenu,
 				 risk.util.DateUtil,
                 risk.admin.keyword.KeywordMng,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    //pr.printParams();
	KeywordMng kg = new KeywordMng();
	
	int xp = pr.getInt("xp",0);
	int yp = pr.getInt("yp",0);
	int zp = pr.getInt("zp",0);
	
	String sCurrentDate = du.getCurrentDate("yyyy-MM-dd");
	//System.out.println("sCurrentDate : " + sCurrentDate );
	
	String sDateFrom = pr.getString("sDateFrom",sCurrentDate );
	String sDateTo   = pr.getString("sDateFrom",sCurrentDate );
	
	kg.setImagesURL("../../../images/admin/keyword/");
	kg.setSelected(xp,yp,zp);	
	kg.setBaseTarget("parent.keyword_right");
	kg.setBaseURL("admin_keyword_right.jsp?mod=");
	
	String kgHtml   = kg.GetHtml( sDateFrom , sDateTo, "","false" );
	String kgScript = kg.GetScript();
	String kgStyle  = kg.GetStyle();
	
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
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<%
	out.println(kgStyle);	
	out.println(kgScript);	
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function clk_total()
	{
		parent.keyword_right.location.href = 'admin_keyword_right.jsp';
	}
//-->
</SCRIPT>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">
<form name="leftmenu" onsubmit="return false;">
<input type="hidden" name="xp">
<input type="hidden" name="yp">
<input type="hidden" name="zp">
<input type="hidden" name="onoffmode">
</form>
<span id="total" class="kgmenu" onclick="clk_total();" onmouseover="kg_over(this);"onmouseout="kg_out(this);"><b>전체 키워드그룹</b></span></br>
<%
	out.println(kgHtml);
%>
</body>