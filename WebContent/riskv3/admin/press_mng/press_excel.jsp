<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page import="	java.util.*
                 	,risk.admin.pressMng.pressMng
					,risk.util.*
					"%>
<%@page import="risk.util.PageIndex"%>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);

    pressMng pMgr = new pressMng();
    
    int rowCnt = 99999999;
	int iNowPage = pr.getInt("nowpage", 1);

	String searchKey = pr.getString("searchKey", "");

	/*****시간설정*****/
	//일자
	String sDate = pr.getString("sDate",
			du.addDay_v2(du.getCurrentDate(), -7));
	String eDate = pr.getString("eDate", du.getCurrentDate());

	//시간
	String sTime = pr.getString("sTime", "00");
	String eTime = pr.getString("eTime", "24");

	String sTimeSet = sTime + ":00:00";
	String eTimeSet = "";
	if (eTime.equals("24")) {
		eTimeSet = "23:59:59";
	} else {
		eTimeSet = eTime + ":00:00";
	}
	
    ArrayList arList = pMgr.getPressDataExcelList(sDate, eDate, sTimeSet, eTimeSet, searchKey);
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=press_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
  
%>
<html>
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
<table style="width:1200px; table-layout:fixed; text-align: center;" border="1" cellspacing="1" >
	<col width="200">
	<col width="200">
	<col width="200">
	<col width="200">
	<col width="200">
	<col width="200">									
	<tr>
		<th>문서번호</th>
		<th>수집일자</th>
		<th>제목</th>
		<th>url</th>
		<th>주요키워드</th>										
		<th>유사(News)</th>
		<th>유사(Twitter)</th>
		<th>출입언론사</th>
		<th>비율</th>
		<th>포탈노출</th>
		<th>댓글</th>
		<th>유사도 상(100-66)</th>
		<th>유사도 중(65-31)</th>
		<th>유사도 하(30)</th>
		<th>유사도</th>
	</tr>
	
	<%
	Map map = null;
	if(	arList.size() > 0	){
		for(int i=0; i < arList.size(); i++){
			map = new HashMap();
			map = (HashMap)arList.get(i);
	%>
	
	<tr>
		<td style="text-align:center;"><%=map.get("d_seq")%></td>
		<td style="text-align:center;"><%=map.get("d_date")%></td>	
		<td style="text-align:left;"><%=map.get("d_title")%></td>
		<td style="text-align:left;"><%=map.get("d_url")%></td>
		<td style="text-align:center;"><%=map.get("d_keyword")%></td>
		<td style="text-align:center;"> <%=map.get("d_same_count")%></td>
		<td style="text-align:center;"><%=map.get("d_tw_count")%></td>
		
		<td style="text-align:center;"><%=map.get("media_chk_cnt")%> </td>
		<td style="text-align:center;"><%=map.get("result_percent")%>%</td>
		<td style="text-align:center;"><%=map.get("d_top")%> </td>
		<td style="text-align:center;"><%=map.get("reply_cnt")%> </td>
		<td style="text-align:center;"><%=map.get("same_per_1")%> </td>
		<td style="text-align:center;"><%=map.get("same_per_2")%> </td>
		<td style="text-align:center;"><%=map.get("same_per_3")%> </td>
		
		<td style="text-align:center;"><%=map.get("same_grade")%> </td>
	</tr>
	<%
		}
	}
	%>
</table>
</body>
</html>