<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.search.userEnvInfo"%>
<%@ include file="sessioncheck.jsp" %>
<%
	String MGmenu = "";
	String[] arrMGmenu = null;

	StringUtil su = new StringUtil();

	userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
	MGmenu = env.getMg_menu();
	System.out.println("MGmenu:"+MGmenu);
	if( MGmenu != null )
	arrMGmenu = MGmenu.split(",");
	
	int link_sel = request.getParameter("link_sel") == null ? 1 : Integer.parseInt(request.getParameter("link_sel").trim());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../css/left.css" />
<script>
	var link = new Array(
			"",
	"../search/search.jsp?INIT=INIT",
	"../issue/issue.jsp",
	"../report/report.jsp",
	"../statistics/statistics.jsp",
	"../admin/admin_main.jsp"
	);

	function init(){
		selectObj = document.getElementsByName('lm')[<%=link_sel-1%>];
	}
	window.onload = init;
	var selectObj;
	
	function mnu_click(obj,seq){
		if(seq == '1'){
			top.bottomFrame.location.href=link[seq]+"&link_seq="+seq;
		}else{
			top.bottomFrame.location.href=link[seq]+"?link_seq="+seq;
		}
	}

	function mouse_over(obj){
		if(selectObj != obj){
			if(obj.src.indexOf("01") > -1){
				obj.src = "../../images/common/menu01_on.gif";
			}else if(obj.src.indexOf("02") > -1){
				obj.src = "../../images/common/menu02_on.gif";
			}else if(obj.src.indexOf("03") > -1){
				obj.src = "../../images/common/menu03_on.gif";
			}else if(obj.src.indexOf("04") > -1){
				obj.src = "../../images/common/menu04_on.gif";
			}else if(obj.src.indexOf("05") > -1){
				obj.src = "../../images/common/menu05_on.gif";
			}
		}
	}
	function mouse_out(obj){
		if(selectObj != obj){
			if(obj.src.indexOf("01") > -1){
				obj.src = "../../images/common/menu01.gif";
			}else if(obj.src.indexOf("02") > -1){
				obj.src = "../../images/common/menu02.gif";
			}else if(obj.src.indexOf("03") > -1){
				obj.src = "../../images/common/menu03.gif";
			}else if(obj.src.indexOf("04") > -1){
				obj.src = "../../images/common/menu04.gif";
			}else if(obj.src.indexOf("05") > -1){
				obj.src = "../../images/common/menu05.gif";
			}
		}
	}
</script>
</head>
<body>
<table style="width:215px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td style="height:66px;"><img src="../../images/common/logo.gif" onclick="mnu_click(this, 1);" style="cursor:pointer"/></td>
	</tr>
	<tr>
		<td valign="top" style="text-align:center;">
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table style="width:205px;" border="0" cellpadding="0" cellspacing="0">
					<tr><td><img src="../../images/common/menu_bg_top.gif" /></td></tr>
					<tr>
						<td style="background:url('../../images/common/menu_bg.gif');text-align:center;">
						<table align="center" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="padding-left:10px" align="center">
<%
if(arrMGmenu != null){
	if(su.inarray(arrMGmenu, "1")){
%>
									<div style="width:61px;height:55;float:left"><img src="../../images/common/menu01<%if(link_sel == 1){out.print("_on");}%>.gif" onmouseover="mouse_over(this)" onmouseout="mouse_out(this)" name="lm" alt="정보검색" onclick="mnu_click(this, 1);" style="cursor:pointer"></div>
<%
	}
	if(su.inarray(arrMGmenu, "2")){
%>
									<div style="width:61px;height:55;float:left"><img src="../../images/common/menu02<%if(link_sel == 2){out.print("_on");}%>.gif" onmouseover="mouse_over(this)" onmouseout="mouse_out(this)" name="lm" alt="이슈관리" onclick="mnu_click(this, 2);" style="cursor:pointer"></div>
<%
	}
	if(su.inarray(arrMGmenu, "3")){
%>
									<div style="width:61px;height:55;float:left"><img src="../../images/common/menu03<%if(link_sel == 3){out.print("_on");}%>.gif" onmouseover="mouse_over(this)" onmouseout="mouse_out(this)" name="lm" alt="보고서관리" onclick="mnu_click(this, 3);" style="cursor:pointer"></div>
<%
	}
	if(su.inarray(arrMGmenu, "4")){
%>
									<div style="width:61px;height:55;float:left"><img src="../../images/common/menu04<%if(link_sel == 4){out.print("_on");}%>.gif" onmouseover="mouse_over(this)" onmouseout="mouse_out(this)" name="lm" alt="통계관리" onclick="mnu_click(this, 4);" style="cursor:pointer"></div>
<%
	}
	if(su.inarray(arrMGmenu, "5")){
%>
									<div style="width:61px;height:55;float:left"><img src="../../images/common/menu05<%if(link_sel == 5){out.print("_on");}%>.gif" onmouseover="mouse_over(this)" onmouseout="mouse_out(this)" name="lm" alt="관리자" onclick="mnu_click(this, 5);" style="cursor:pointer"></div>
<%
	}
}
%>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr><td style="height:16px;background:url('../../images/common/menu_bg_btm.gif');text-align:center;"><img src="../../images/common/btn_menu_close.gif" /></td></tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>