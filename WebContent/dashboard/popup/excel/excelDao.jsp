<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.dashboard.excel.ExcelDownload"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONArray arr = new JSONArray();

ExcelDownload excel = new ExcelDownload();

String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String codeList = pr.getString("codeList");
String relationKeyword = pr.getString("relationKeyword");
String source = pr.getString("source"); 	/*	 주요 매체 현황 매체 구분 값	 */
String category = pr.getString("category");
String tier = pr.getString("tier","");

int nowPage = pr.getInt("nowPage", 1);
int rowCnt = pr.getInt("rowCnt", 10);

if(!"".equals(category)){  /* 주요시정 모니터링 메뉴 - 주요 매체 현황 팝업 */ 
	arr = excel.getExcelData2(scope, keyword, sDate, eDate, codeList, source, category);
}else{
	arr = excel.getExcelData(scope, keyword, sDate, eDate, codeList, relationKeyword,tier);
}

%>

<table style="width:1200px; table-layout:fixed;" border="1" cellspacing="1" >
    <caption></caption>
	<colgroup>
		<col style="width:200px">
		<col style="width:400px">
		<col style="width:400px">
		<col style="width:100px">
		<col style="width:100px">
		<col>
	</colgroup>
	<thead>
		<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
		<%
			int colspan = 5;
		%>
		<tr style="height:40px">
			<th scope="col" colspan="<%=colspan%>">관련정보</th>
		</tr>
		<tr style="height:30px">
			<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
		</tr>
		<tr>
		<th scope="col"><span>출처</span></th>
		<th scope="col"><span>제목</span></th>
		<th scope="col"><span>URL</span></th>
		<th scope="col"><span>감성</span></th>
		<th scope="col"><span>수집시간</span></th>
		</tr>
	</thead>
    <tbody>
    
    <%
    	for(int i=0; i<arr.length(); i++){
    		JSONObject obj = new  JSONObject();
			obj = (JSONObject)arr.get(i);
    %>
	<tr>
		<td><span><%=obj.get("source")%></span></td>
		<td><span><%=obj.get("title")%></span></td>
		<td><span><%=obj.get("url")%></span></td>
		<td><span><%=obj.get("senti")%></span></td>
		<td><span><%=obj.get("date")%></span></td>
	</tr>
	<%
    	}
    %>
    </tbody>
</table>