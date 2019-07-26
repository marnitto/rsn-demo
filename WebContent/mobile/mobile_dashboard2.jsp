
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*
			,java.util.Hashtable
			,java.util.ArrayList
			,risk.util.*
			,risk.JfreeChart.GetChartData
			,risk.JfreeChart.MakeChart
			,risk.search.keywordInfo
			,risk.mobile.MobileMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	GetChartData gData = new GetChartData();
	MakeChart mc = new MakeChart();
	
	String sDateTo = du.getCurrentDate("yyyy-MM-dd");
	String sDateFrom = du.addDay(sDateTo,-6);
	
	ArrayList arrChartdata = gData.getMobileChart( sDateFrom, sDateTo, "1" );
	String chartUrl = mc.getKeywordLineChartTwitter(sDateFrom.replaceAll("-",""), sDateTo.replaceAll("-",""), arrChartdata, "character_"+du.getCurrentDate("yyyyMMddHHmmssss"), 300, 150 );
	
	ArrayList arrIssueData = gData.getIssueData( sDateFrom, sDateTo, "1" );
	
// 휴대폰 화면 사이즈 불러와서 width 지정
%>
<html>
<head>
<title>SPEED</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<script language="JavaScript" type="text/javascript">

</script>
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
	background-image: url(images/login_bg01.gif);
	background-color: #384b5a;
}
-->
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="mobile_list" method="post">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="42" background="images/list_top_gray.gif" style="padding:4px 13px 0px 13px">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        
         <tr>
		    <td height="2" ><img src="images/brank.gif" height="5"></td>
		  </tr>
        <tr>
        <td align="left"><strong class="menu_gray" >성향 통계</strong></td>
        </tr>
         <tr>
		    <td height="2" ><img src="images/brank.gif" height="5"></td>
		  </tr>
        
        </table>
        </td>
        
  </tr>
  <tr>
    <td style="padding:4px 13px 0px 13px" bgcolor="#ffffff">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        
         <tr>
		    <td height="2" ><img src="images/brank.gif" height="5"></td>
		  </tr>
        <tr>
        <td align="left"><img src="<%=chartUrl%>"></td>
        </tr>
         <tr>
		    <td height="2" ><img src="images/brank.gif" height="5"></td>
		  </tr>
        
        </table>
    </td>
  </tr>
   <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  
  </tr>  
   <%
   String font = "";
  for(int i = 0; i < arrIssueData.size(); i++)
  {
	  Hashtable mBean = (Hashtable) arrIssueData.get(i);
	  font = "";
	  if(((String)mBean.get("IC_NAME")).equals("긍정")) font = "<font color='#0A6EFF'>";
	  if(((String)mBean.get("IC_NAME")).equals("부정")) font = "<font color='#FF5675'>";
	  if(((String)mBean.get("IC_NAME")).equals("중립")) font = "<font color='gray'>";
	  
  %>
  <tr>
  
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0" onclick="window.open('<%=mBean.get("ID_URL")%>')">
      <tr>
        <td height="23" class="menu_blue"><a href="<%=mBean.get("ID_URL")%>" target="_blank">
        <%=font%>[<%=mBean.get("IC_NAME")%>] </font><%=su.subStrTitle((String)mBean.get("ID_TITLE"), 20)%></a></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=mBean.get("MD_SITE_NAME")%></span> <span class="menu_gray02"><%=du.getDate((String)mBean.get("MD_DATE"), "yy/MM/dd HH:mm")%></span></td>
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
    <td height="1" bgcolor="#d3d3d3" style="padding:0px 13px 0px 13px"></td>
  </tr>
  
  <tr>
    <td height="2" background="images/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="2" background="images/list_line02.gif"></td>
  </tr>

</table>
</form>
</body>
</html>
