<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@page import="risk.util.PageIndex"%>
<%@ page import="risk.admin.site.SiteMng
                ,risk.util.DateUtil
                ,risk.util.ParseRequest 
				,risk.admin.alimi.AlimiLogMgr
				,risk.admin.alimi.AlimiLogSuperBean
              	,risk.admin.alimi.AlimiSettingDao
              	,risk.mobile.AlimiSettingBean
				,risk.util.ParseRequest
				,java.util.ArrayList
				,java.util.*				
				" %>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	String searchKey = pr.getString("searchKey");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String mal_type = pr.getString("mal_type","");
	String as_type = pr.getString("as_type","");
	String as_seq = pr.getString("as_seq","");
	
	AlimiLogMgr lMgr = new AlimiLogMgr();
	ArrayList reData = lMgr.getAlimiLogList_Excel(sDateFrom, sDateTo, mal_type, searchKey, as_type, as_seq);
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=site_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
.tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
a:link { color: #333333; text-decoration: none; }
a:visited { text-decoration: none; color: #000000; }
a:hover { text-decoration: none; color: #FF9900; }
a:active { text-decoration: none; }

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	SCROLLBAR-face-color: #F2F2F2;
	SCROLLBAR-shadow-color: #999999;
	SCROLLBAR-highlight-color: #999999;
	SCROLLBAR-3dlight-color: #FFFFFF;
	SCROLLBAR-darkshadow-color: #FFFFFF;
	SCROLLBAR-track-color: #F2F2F2;
	SCROLLBAR-arrow-color: #333333;
}
.menu_black {  font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 12px; line-height: 16px; color: #000000}
.textw { font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}
.menu_blue {
	font-family: "µ¸¿ò", "µ¸¿òÃ¼";
	font-size: 12px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_gray {font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 12px; line-height: 16px; color: #4B4B4B}
.menu_red {font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 12px; line-height: 16px; color: #CC0000}
.menu_blueOver {
	font-family: Tahoma;
	font-size: 11px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_blueTEXTover {
	font-family: "µ¸¿ò", "µ¸¿òÃ¼";
	font-size: 12px;
	line-height: 16px;
	color: #3366CC;
	font-weight: normal;
}
.textwbig {font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal}
.textBbig {font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal}
.menu_grayline {
	font-family: "µ¸¿ò", "µ¸¿òÃ¼";
	font-size: 12px;
	line-height: 16px;
	color: #4B4B4B;
	text-decoration: underline;
}
.menu_grayS {font-family: "µ¸¿ò", "µ¸¿òÃ¼"; font-size: 11px; line-height: 16px; color: #4B4B4B}
</style>
</head>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">
<table width="1200" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<th width="140" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">Á¦ ¸ñ</th>
						<th width="80" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">¼ö½ÅÀÚ</th>
						<th width="320" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">³» ¿ë</th>
						<th width="320" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">URL</th>
						<th width="80" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">»ó ÅÂ</th>
						<th width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">¹ß¼Û½Ã°£</th>
					</tr>
<%
	
	if(reData.size()>0){
		AlimiLogSuperBean.AlimiLogList abean = null;
		String status = "";
		String source = "";
	  	for(int i=0; i < reData.size(); i++)
		{
	  		abean = 	(AlimiLogSuperBean.AlimiLogList)reData.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		
	  		if(abean.getMal_type().equals("1")){
	  			status = "¹ß¼Û";
	  		}else if(abean.getMal_type().equals("0")){
	  			status = "½ÇÆÐ";
	  		}else if(abean.getMal_type().equals("2")){
	  			status = "À¯»ç";
	  		}else{
	  			status = "";
	  		}
	  		
	  		
%>
				<tr>
					<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;"><%=abean.getAs_title()%></td>
					<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;"><%=abean.getAs_cnt()+"¸í"%></td>
					<td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=abean.getSend_message()%></td>
					<td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=abean.getMt_url()%></td>
					<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;"><%=status%></td>
					<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;"><%= du.getDate(abean.getMal_send_date(), "MM-dd HH:mm")%></td>
				</tr>
<%
		}
	}else{
%>
				<tr>
					<td colspan="6" width="1200" style="font-weight:bold;height:40px" align="center">Á¶°Ç¿¡ ¸Â´Â µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù.</td>
				</tr>
<%		
	}
%>
				</table>
</body>