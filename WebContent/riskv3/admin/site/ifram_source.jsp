<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.*" %>
<%@ page import="risk.admin.site.SiteBean" %>
<%@ page import="risk.admin.site.SiteMng" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ include file="../../inc/sessioncheck.jsp" %>

<%
	List SGlist = null;
	int code1=0;
	int language=0;
	String sch_key = "";
	
	// kor / new
	String order  = "";
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	

		
try{	

	if( request.getParameter("code1") != null && !request.getParameter("code1").equals("") ) code1 = Integer.parseInt(request.getParameter("code1"));
	if( request.getParameter("language") != null && !request.getParameter("language").equals("") ) language = Integer.parseInt(request.getParameter("language"));
	if( request.getParameter("sch_key") != null && !request.getParameter("sch_key").equals("") ) sch_key = request.getParameter("sch_key");
	if( request.getParameter("order") != null && !request.getParameter("order").equals("") ) {order = request.getParameter("order");} else{order = "kor";}
	
	SiteMng sitemng = new SiteMng();


	SGlist = sitemng.get46List(sch_key, code1, language, order);
	
	/*
	if( sch_key != null && code1 > 0 ) {
		SGlist = sitemng.get46List(sch_key, code1);
	}else if( sch_key != null ) {
		SGlist = sitemng.get46List(sch_key);
	}else {
		if( code1 > 0 ) {
			SGlist = sitemng.get46List(code1);
		}else {
			SGlist = sitemng.get46List();
		}
	}
	*/

%>
<html>
<head>
<title>X-MAS Solution</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<%=SS_URL%>css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"dotum"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
body {
	margin-left: 14px;
	margin-top: 10px;
	margin-right: 0px;
	margin-bottom: 0px;
}
	-->
	</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function search_SourceSite_frm()
	{
		if( parent.source_form.sch_key.value ) {
			sg_change.sch_key.value = parent.source_form.sch_key.value;
		}
		sg_change.submit();
	}

	function search_order_frm(order)
	{
		sg_change.order.value = order;
		sg_change.submit();
	}
//-->
</SCRIPT>
</head>

<body>
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">언어선택
					</td>
					<td width="30"></td>
					<td>
						<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">사이트 그룹 선택
					</td>
				</tr>
				<tr height="3"><td></td></tr>
				<form name="sg_change" action="ifram_source.jsp" method="GET">
 					<input name="sch_key" type="hidden">
 					<input name="order" type="hidden">
				<tr>
					<td width="140">
						<select name="language" class="t" style="width:140px;" onchange="search_SourceSite_frm();">
					       <option value="0" <%if( language == 0) out.println("selected");%>>전체
					       <option value="1" <%if( language == 1) out.println("selected");%>>한국어
					       <option value="2" <%if( language == 2) out.println("selected");%>>영어
    					 </select>
					</td>
					<td width="30"></td>
					
					<td width="140">
						<select name="code1" class="t" style="width:140px;" onchange="search_SourceSite_frm();">
					       <option value="0" <%if( code1 == 0) out.println("selected");%>>전체
					       <option value="1" <%if( code1 == 1) out.println("selected");%>>언론사
					       <option value="2" <%if( code1 == 2) out.println("selected");%>>정부공공기관
					       <option value="3" <%if( code1 == 3) out.println("selected");%>>금융
					       <option value="4" <%if( code1 == 4) out.println("selected");%>>NGO
					       <option value="5" <%if( code1 == 5) out.println("selected");%>>기업/단체
     					</select>
					</td>
				</tr>
				</form>
			</table>
		</td>
	</tr>
	<tr height="3"><td></td></tr>
	<tr>
        <td align="right" ><img src="../../../images/admin/site/btn_order_kor.gif" height="21" onclick="search_order_frm('kor');" style="cursor:hand;"><img src="../../../images/admin/site/btn_order_update.gif" height="21" hspace="5" onclick="search_order_frm('new');" style="cursor:hand;"></td>
      </tr>
      <tr>
        <td width="1" height="6"><!-- <img src="../../../images/admin/site/brank.gif" > --></td>
      </tr>

<!--   <tr>-->
<!--    <td><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">사이트 그룹 선택</td>-->
<!-- </tr>-->
<!-- <form name="sg_change" action="ifram_source.jsp" method="GET">-->
<!-- <input name="sch_key" type="hidden">-->
<!--   <tr>-->
<!--     <td height="25">-->
<!--	 <select name="code1" class="t" style="width:310px;" onchange="search_SourceSite_frm();">-->
<!--       <option value="0" <%if( code1 == 0) out.println("selected");%>>전체-->
<!--       <option value="1" <%if( code1 == 1) out.println("selected");%>>언론사-->
<!--       <option value="2" <%if( code1 == 2) out.println("selected");%>>정부공공기관-->
<!--       <option value="3" <%if( code1 == 3) out.println("selected");%>>금융-->
<!--       <option value="4" <%if( code1 == 4) out.println("selected");%>>NGO-->
<!--       <option value="5" <%if( code1 == 5) out.println("selected");%>>기업/단체-->
<!--     </select></td>-->
<!--   </tr>-->
<!--   </form>-->
   <tr>
     <td width="1" height="5"><!-- <img src="../../../images/admin/site/brank.gif" > --></td>
   </tr>
   <tr>
     <td height="18"><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">사이트 리스트</td>
   </tr>
   <form name="sg_mng" action="ifram_source_prc.jsp" method="post">
   <input type="hidden" name="mode" value="ins">
   <input type="hidden" name="sgseq">
   <input type="hidden" name="code1" >

   <tr>
     <td><select name="gsn" multiple style="width:310px;height:200px;" class="t">
<%
	for(int i=0; i < SGlist.size();i++) {
		SiteBean SGinfo = (SiteBean)SGlist.get(i);
%>
<option value="<%= SGinfo.get_gsn()%>"><%= SGinfo.get_name()%></option>
<%
	}
	}catch(Exception e){System.out.println("error ifram_source line: 103"+e);}
%>
</select></td>
   </tr>
   </form>
</table>

</body>
</html>
