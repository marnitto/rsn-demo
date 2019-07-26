<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.PageIndex"%>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.summary.SummaryMgr
                 "
%>

<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	SummaryMgr sMgr = new SummaryMgr();
	
	String wDate = pr.getString("wDate");
	String p_docid = pr.getString("p_docid");
	String r_trnd = pr.getString("r_trnd", "");
	String r_relation_word = pr.getString("r_relation_word", "");
	int totalCnt = pr.getInt("totalCnt",0);
	System.out.println("========================================");
	pr.printParams();
	
	int nowpage = Integer.parseInt( pr.getString("nowpage","1") );
	int rowCnt = 99999999;
	int totalPage = 0;
	
	ArrayList arData = new ArrayList();
	arData = sMgr.getReplyDataList(nowpage, rowCnt, wDate.replaceAll("-", ""), p_docid, r_trnd, r_relation_word, SS_M_ID);
	
	if(totalCnt == 0){
		totalCnt = sMgr.getReplyTotalCnt();
	}
	totalPage = totalCnt / rowCnt;
	if(totalCnt % rowCnt > 0){
		totalPage += 1;
	}
	
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
<table style="width:600px; table-layout:fixed; text-align: center;" border="1" cellspacing="1" >
	<colgroup>
		<col width="200">
		<col width="200">
		<col width="200">
	</colgroup>
	<tr>
		<th>수집일자</th>
		<th>내용</th>
		<th>성향</th>										
	</tr>
									
			<%
			Map map = null;
			String senti = "";
			if(	arData.size() > 0	){
				for(int i=0; i < arData.size(); i++){
					map = new HashMap();
					map = (HashMap)arData.get(i);
					
					if("1".equals(map.get("r_trend").toString())){
						senti= "긍정";
					}else if("2".equals(map.get("r_trend").toString())){
						senti= "부정";
					}else{
						senti= "중립";
					}
			%>
			
			<tr>
				<td><%=map.get("r_datetime")%></td>
				<td><p style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;" title="<%=map.get("r_content")%>"><%=map.get("r_content")%></p></td>
				<td><%=senti%></td>
			</tr>
			<%
				}
			}
			%>
</table>
</body>
</html>