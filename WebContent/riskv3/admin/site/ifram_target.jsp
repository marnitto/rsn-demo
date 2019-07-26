<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.*" %>
<%@ page import="risk.admin.site.SiteBean" %>
<%@ page import="risk.admin.site.SitegroupBean" %>
<%@ page import="risk.admin.site.SiteMng" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	List sglist = null;
	List sitelist = null;
	int sg=0;
	String sch = "";
	ParseRequest pr = new ParseRequest(request);

	//if( request.getParameter("sg_seq") != null ) sg = Integer.parseInt(request.getParameter("sg_seq"));
	
	//20110511 미확인된 에러 난다고 하여 int변환 숫자체크 보강
	String strSg = pr.getString("sg_seq","0");
	
	int language = pr.getInt("language",0); 
	
	char ch;
	for(int i = 0 ; i < strSg.length(); i++){
		ch = strSg.charAt(i);
		if(ch < 48 || ch >57){
			strSg = "0";
			break; 		
		}
	}
	sg = Integer.parseInt(strSg);
	
	
	
	if( pr.getString("tg_sch") != null ) sch = pr.getString("tg_sch");

	SiteMng sitemng = new SiteMng();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<%=SS_URL%>css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"dotum"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
body {
	margin-left: 4px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F3F3F3;
}
	-->
	</style>
<script language="JavaScript" type="text/JavaScript">
<!--
	function edit_sg()
	{
		if( tg_site.sg_seq.value ) {
			var url = "pop_sitegroup.jsp?mode=edit&sgseq="+tg_site.sg_seq.value+"&language="+ tg_site.language.value;
			window.open(url, "pop_sitegroup", "width=400,height=150");
		}
	}

	function del_sg()
	{
		if( tg_site.sg_seq.value ) {
			if( confirm('사이트그룹을 삭제하시겠습니까?') ) {
				location.href = "ifram_target_prc.jsp?mode=del&sgseq="+tg_site.sg_seq.value+"&language="+ tg_site.language.value;
			}
		}
	}

	function search_TargetSite_frm()
	{
		if( parent.target_form.tg_sch.value ) {
			tg_site.tg_sch.value = parent.target_form.tg_sch.value;
		}
		tg_site.submit();
	}
//-->
</script>
</head>

<body>
<table width="315" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" style="padding-left:10px">
    <table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" height="5"><!-- <img src="../../../images/admin/site/brank.gif" > --></td>
      </tr>
      
      <tr>
		<td>
		<form name="tg_site" action="ifram_target.jsp" method="GET">
			<input name="tg_sch" type="hidden">
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
				
				<tr>
					<td width="140">
						<select name="language" class="t" style="width:140px;" onchange="search_TargetSite_frm();">
					       <option value="0" <%if( language == 0) out.println("selected");%>>전체
					       <option value="1" <%if( language == 1) out.println("selected");%>>한국어
					       <option value="2" <%if( language == 2) out.println("selected");%>>영어
    					 </select>
					</td>
					<td width="30"></td>
					
					<td width="140">
						<select name="sg_seq" class="t"  style="width:140px;" onchange="search_TargetSite_frm();">
							<option value="0">전체</option>
					<%
						sglist = sitemng.getSGList();
					
						for(int i=0; i < sglist.size();i++) {
							SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
					%>
					<option value="<%=SGinfo.get_seq()%>" <%if( SGinfo.get_seq() == sg) out.println("selected");%>><%= SGinfo.get_name()%></option>
					<%
						}
					%>
							</select>
					</td>
				</tr>
				
			</table>
			</form>
		</td>
	</tr>
	<tr height="3"><td></td></tr>
      
      
      
      
      
      
      
      
      
      
      
<!--      <tr>-->
<!--        <td><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">사이트 그룹 선택</td>-->
<!--      </tr>-->
<!--	  <form name="tg_site" action="ifram_target.jsp" method="GET">-->
<!--	  <input name="tg_sch" type="hidden">-->
<!--      <tr>-->
<!--        <td height="25">-->
<!--		<select name="sg_seq" class="t"  style="width:310px;" onchange="search_TargetSite_frm();">-->
<!--		<option value="0">전체</option>-->
<!--<%-->
<!--	sglist = sitemng.getSGList();-->
<!---->
<!--	for(int i=0; i < sglist.size();i++) {-->
<!--		SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);-->
<!--%>-->
<!--<option value="<%//=SGinfo.get_seq()%>" <%//if( SGinfo.get_seq() == sg) out.println("selected");%>><%//= SGinfo.get_name()%></option>-->
<!--<%-->
<!--	}-->
<!--%>-->
<!--		</select></td>-->
<!--      </tr>-->
<!--	  </form>-->
      <tr>
        <td align="right" ><img src="../../../images/admin/site/btn_groupedit.gif" width="72" height="21" onclick="edit_sg();" style="cursor:hand;"><img src="../../../images/admin/site/btn_groupdel.gif" width="59" height="21" hspace="5" onclick="del_sg();" style="cursor:hand;"></td>
      </tr>
      <tr>
        <td width="1" height="6"><!-- <img src="../../../images/admin/site/brank.gif" > --></td>
      </tr>
      <tr>
        <td height="20"><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">수집사이트 리스트</td>
      </tr>
	   <form name="sg_mng" action="ifram_source_prc.jsp" method="POST">
	   <input type="hidden" name="mode" value="del">
	   <input type="hidden" name="sgseq">
	   <input type="hidden" name="code1" >
      <tr>
        <td><select name="gsn"  multiple style="width:310px;height:200px;" class="t">강동건설
<%
	
	sitelist = sitemng.getList(sch,sg,language);

	/*
	if( sch == null  ) {
		sitelist = sitemng.getList();
	}
	
	else if( sch != null && sg > 0 ) {
		sitelist = sitemng.getList(sch,sg);
	}else if( sch != null ) {
		sitelist = sitemng.getList(sch);
	} else if( sg == 0 ) {
		sitelist = sitemng.getList();
	} else {
		sitelist = sitemng.getList(sg);
	}
     */
        

	for(int i=0; i < sitelist.size();i++) {
		SiteBean siteinfo = (SiteBean)sitelist.get(i);
%>
<option value="<%= siteinfo.get_gsn()%>"><%= siteinfo.get_name()%></option>
<%
	}
%>
			</select></td>
      </tr>
	  </form>

    </table></td>
  </tr>
</table>
</body>
</html>
