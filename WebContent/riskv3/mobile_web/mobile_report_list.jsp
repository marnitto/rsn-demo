<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder" %>

<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	
	String ir_type = pr.getString("ir_type","E");
	int nowpage = pr.getInt("nowpage",1);	
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	
	ArrayList arrIrBean = null;
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	
	arrIrBean = irMgr.getIssueReportList(nowpage,pageCnt,"",ir_type,"","","");
	totalCnt = irMgr.getTotalCnt();
	
	//페이징 처리
	totalPage = 0;	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/mobile/basic.css">
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<style type="text/css">
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(../../images/mobile/login_bg01.gif);
	background-color: #384b5a;
}

-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--

	

	
 	function getSearch(type){
	 	 		
 		var f = document.fSend;
		
		f.ir_type.value = type;
		
		f.nowpage.value = 1;
		f.target = '';
		f.action = 'mobile_report_list.jsp';
 		f.submit(); 	
 	}
	function fn_before()
	{
		var f = document.fSend;
		
		var nowpage = Number(f.nowpage.value);
		
		if( nowpage > 1)
		{
			f.nowpage.value = nowpage - 1;
			f.target = '';
			f.action = 'mobile_report_list.jsp';
			f.submit();
		}
	}
	function fn_next()
	{
		var f = document.fSend;
		
		var nowpage = Number(f.nowpage.value);
		var totalPage = Number(f.totalPage.value);
		
		if( nowpage < totalPage)
		{
			f.nowpage.value = nowpage + 1;
			f.target = '';
			f.action = 'mobile_report_list.jsp';
			f.submit();
		}
	}

	function goPcVersion(){
		document.location = '../main.jsp';		
	}
	function logOut(){
		document.location = '../logout.jsp?mType=m';
	}

	function getMenu(menu){

		if(menu == 'search'){
			document.search.action = 'mobile_content_list.jsp';
			document.search.target = '';
			document.search.submit();
		}else if(menu == 'issue'){

			document.issue.action = 'mobile_issue_list.jsp';
			document.issue.target = ''; 
			document.issue.submit();
			
		}else if(menu == 'report'){ 

			document.fSend.nowpage.value = '1';
			document.fSend.totalPage.value = '';
			document.fSend.ir_type.value = '';


			document.fSend.action = 'mobile_report_list.jsp';
			document.fSend.target = '';
			document.fSend.submit();
		}else if(menu == 'dashboard'){
			document.location.href = 'mobile_dashboard.jsp';
		} 
		
	}

	//저장된 보고서 보기 페이지를 띄운다
 	function showReport(ir_seq){
	 	var f = document.fSend;
		f.mode.value = 'view';
		f.ir_seq.value = ir_seq;
		popup.openByPost('fSend','../report/pop_report.jsp',790,945,false,true,false,'goReportView');		
 	}
	
//-->	
</script>
</head>

<body onload="window.scrollTo(0,1);" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="search" id="search" action="mobile_content_list.jsp" method="post">
	<input type="hidden" name="nowpage" value="1">
	<input type="hidden" name="xp" value="">
	<input type="hidden" name="yp" value="">
	<input type="hidden" name="zp" value="">
	<input type="hidden" name="sg_seq" value="">
	<input type="hidden" name="kXp" value="">
</form>

<form name="issue" id="issue" action="mobile_issue_list.jsp" method="post">
	<input type="hidden" name="nowpage" value="1">
	<input type="hidden" name="typeCode4" value="">
	<input type="hidden" name="totalPage" value="">
	<input type="hidden" name="sDateFrom" value="">
	<input type="hidden" name="sDateTo" value="">
</form>



<form name="fSend" id="fSend" action="mobile_report_list.jsp" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage %>">
<input type="hidden" name="totalPage" value="<%=totalPage%>">
<input type="hidden" name="ir_type" value="<%=ir_type%>">
<input type="hidden" name="mode">
<input type="hidden" name="ir_seq">


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="background:url(../../images/mobile/bg_header_00.gif)"><img src="../../images/mobile/list_top.gif"></td>
  </tr>
    
  <tr>
    <td height="35" background="../../images/mobile/menu_bg_03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('search')"><img src="../../images/mobile/menu_01_off.gif" width="22" height="22" align="absmiddle" ><strong> 정보검색</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('issue')"><img src="../../images/mobile/menu_02_off.gif" width="19" height="18" align="absmiddle" ><strong> 이슈관리</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_01.gif" class="menu_blueOver" style="cursor: pointer;" onclick="getMenu('report')"><img src="../../images/mobile/menu_03_on.gif" width="18" height="20" align="absmiddle" > <strong>보고서</strong></td>
      </tr>
    </table></td>
  </tr>
    <tr>
    <td height="31" bgcolor="#e4e4e4" style="padding:3px 13px 0px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><strong class="menu_gray">보고서</strong></td>
        <td align="right">

		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="" bgcolor="#B9B9B9"></td>
  </tr>
  <tr>
    <td height="30" background="../../images/mobile/menu_bg_04.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
<%
	
	ArrayList arReportType = new ArrayList();
	String[] arBean = null;
	
	
	arBean = new String[2];
	arBean[0] = "D1";
	arBean[1] = "주간보고서";
	arReportType.add(arBean);
	
	/* arBean = new String[2];
	arBean[0] = "I";
	arBean[1] = "일일보고서(주요이슈포함)";
	arReportType.add(arBean); */
	
	arBean = new String[2];
	arBean[0] = "E";
	arBean[1] = "이슈보고서";
	arReportType.add(arBean);
	
	for(int i = 0; i < arReportType.size(); i++){
		arBean = (String[]) arReportType.get(i);
		
		if(ir_type.equals("")){
			if (i==0){
				out.print("<td onclick=\"getSearch('"+arBean[0]+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">"+arBean[1]+"</td>");
			}else{
				out.print("<td onclick=\"getSearch('"+arBean[0]+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">"+arBean[1]+"</td>");	
			}	
		}else{
			if ( ir_type.equals(arBean[0])){
				out.print("<td onclick=\"getSearch('"+arBean[0]+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">"+arBean[1]+"</td>");
			}else{
				out.print("<td onclick=\"getSearch('"+arBean[0]+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">"+arBean[1]+"</td>");	
			}
		}
		
	}
	
%>      

		

     
      </tr>
    </table></td>
  </tr>

<%

	for(int i =0; i < arrIrBean.size(); i++){
		irBean = (IssueReportBean)arrIrBean.get(i);
%>
  
  <tr>
    <td align="right" bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50%" height="23" class="menu_gray"><a onClick="showReport('<%=irBean.getIr_seq()%>');" href="javascript:void(0);"><strong><%=irBean.getIr_title()%></strong></a></td>
        <td width="50%" align="right" class="menu_gray"><%=irBean.getFormatIr_regdate("yyyy-MM-dd")%></td>
        </tr>
      
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  </tr>
  
<%
	}
%> 
  
  <tr>
    <td height="2" background="../../images/mobile/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="28" background="../../images/mobile/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onclick="fn_before();" style="cursor: pointer;"><strong class="textwhite02">&lt; 이전</strong></td>
        <td width="50%" align="center" onclick="fn_next();" background="../../images/mobile/list_bar01.gif" class="textwhite02" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line03.gif"></td>
  </tr>
  <tr>
    <td height="38" background="../../images/mobile/list_bg03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onclick="goPcVersion();" style="cursor: pointer;"><strong>PC버전</strong></td>
        <td width="50%" align="center" onclick="logOut();" background="../../images/mobile/list_bar03.gif" class="textwhite" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">로그아웃</td>
      </tr>
    </table></td>
  </tr>
</table>






</form>
</body>
</html>