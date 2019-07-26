<%@page import="risk.dashboard.popup.CmnPopupLucyMgr"%>
<%@page import="risk.dashboard.search.SearchMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.util.ParseRequest"%>
<%@include file="../../../riskv3/inc/sessioncheck.jsp" %>

<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONObject obj = new JSONObject();
JSONArray arr = new JSONArray();

CmnPopupLucyMgr cPopupLucyMgr = new CmnPopupLucyMgr();

String i_sdate = pr.getString("i_sdate");
String i_edate = pr.getString("i_edate");
String i_and_text = pr.getString("i_and_text");						// AND 검색 키워드
String i_exact_text = pr.getString("i_exact_text");					// 구문 검색 키워드
String i_or_text = pr.getString("i_or_text");						// OR 검색 키워드
String i_exclude_text = pr.getString("i_exclude_text");			// 제외단어 키워드
String i_sourcetype = pr.getString("i_sourcetype");				// 채널
String i_trend = pr.getString("i_trend", "");						// 감성
String I_relation_word = pr.getString("I_relation_word", "");	// 연관키워드

obj = cPopupLucyMgr.getPopupData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, i_trend, I_relation_word, 1000, 0, SS_M_ID);
arr = obj.getJSONArray("data");

%>

<table style="width:1100px; table-layout:fixed;" border="1" cellspacing="1" >
    <caption></caption>
	<colgroup>
		<col style="width:100px">
		<col style="width:400px">
		<col style="width:400px">
		<col style="width:100px">
		<col style="width:100px">
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
			<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
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
    		JSONObject obj2 = new  JSONObject();
			obj2 = (JSONObject)arr.get(i);
			
			String senti = "";
			if(("1").equals(obj2.getString("senti"))){
				senti = "긍정";
			}else if(("2").equals(obj2.getString("senti"))){
				senti = "부정";
			}else if(("3").equals(obj2.getString("senti"))){
				senti = "중립";
			}
    %>
	<tr>
		<td><span><%=obj2.get("sitename")%></span></td>
		<td><span><%=obj2.get("title")%></span></td>
		<td><span><%=obj2.get("url")%></span></td>
		<td><span><%=senti%></span></td>
		<td><span><%=obj2.get("date")%></span></td>
	</tr>
	<%
    	}
    %>
    </tbody>
</table>