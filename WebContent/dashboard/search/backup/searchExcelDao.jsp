<%@page import="risk.dashboard.search.SearchMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.DateUtil"%>

<%

ParseRequest pr = new ParseRequest(request);
pr.printParams();
DateUtil du = new DateUtil();
SearchMgr searchMgr = new SearchMgr();

String section = pr.getString("section");
String i_sdate = pr.getString("i_sdate");
String i_edate = pr.getString("i_edate");
String i_sourcetype = pr.getString("i_sourcetype");				// 채널

/* 		첫번째 검색 키워드의 파라미터		 */ 
String i_and_text = pr.getString("i_and_text");					// AND 검색 키워드
String i_exact_text = pr.getString("i_exact_text");				// 구문 검색 키워드
String i_or_text = pr.getString("i_or_text");						// OR 검색 키워드
String i_exclude_text = pr.getString("i_exclude_text");			// 제외단어 키워드


/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text2 = pr.getString("i_and_text2");				// AND 검색 키워드
String i_exact_text2 = pr.getString("i_exact_text2");				// 구문 검색 키워드
String i_or_text2 = pr.getString("i_or_text2");						// OR 검색 키워드
String i_exclude_text2 = pr.getString("i_exclude_text2");		// 제외단어 키워드

String title = pr.getString("title");
String keyword = pr.getString("keyword");
String keyword1 = pr.getString("keyword1");
String keyword2 = pr.getString("keyword2");
int limit = pr.getInt("limit");											// 연관키워드 개수

JSONArray arr = new JSONArray();
JSONArray arr2 = new JSONArray();

if("2".equals(section)){
	if("".equals(keyword1)){ //키워드2만있을때,
		arr = searchMgr.getInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, "2");
%>
	<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
		    <caption></caption>
			<colgroup>
				<col style="width:100px">
				<col style="width:200px">
			</colgroup>
			<thead>
				<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
				<%
					int colspan = 2;
				%>
				<tr style="height:40px">
					<th scope="col" colspan="<%=colspan%>"><%=title%></th>
				</tr>
				<tr style="height:30px">
					<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
				</tr>
				<tr>
				<th scope="col"><span>날짜</span></th>
				<th scope="col"><span><%=keyword2%> 총정보량</span></th>
				</tr>
			</thead>
		    <tbody>
		    
		    <%
		    	for(int i=0; i<arr.length(); i++){
		    		JSONObject obj = new  JSONObject();
					obj = (JSONObject)arr.get(i);
		    %>
			<tr>
				<td><span><%=obj.get("fulldate")%></span></td>
				<td><span><%=obj.get("column-2")%></span></td>
			</tr>
			<%
		    	}
		    %>
		    </tbody>
		</table>
<%
	}else if("".equals(keyword2)){
		arr = searchMgr.getInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, "1");
%>
		<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
		    <caption></caption>
			<colgroup>
				<col style="width:100px">
				<col style="width:200px">
			</colgroup>
			<thead>
				<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
				<%
					int colspan = 2;
				%>
				<tr style="height:40px">
					<th scope="col" colspan="<%=colspan%>"><%=title%></th>
				</tr>
				<tr style="height:30px">
					<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
				</tr>
				<tr>
				<th scope="col"><span>날짜</span></th>
				<th scope="col"><span><%=keyword1%> 총정보량</span></th>
				</tr>
			</thead>
		    <tbody>
		    
		    <%
		    	for(int i=0; i<arr.length(); i++){
		    		JSONObject obj = new  JSONObject();
					obj = (JSONObject)arr.get(i);
		    %>
			<tr>
				<td><span><%=obj.get("fulldate")%></span></td>
				<td><span><%=obj.get("column-1")%></span></td>
			</tr>
			<%
		    	}
		    %>
		    </tbody>
		</table>
<%
	}else{
		arr = searchMgr.getInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, "1");
		arr2 = searchMgr.getInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, "2");
%>
	<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:200px">
			<col style="width:200px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 3;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
			</tr>
			<tr>
			<th scope="col"><span>날짜</span></th>
			<th scope="col"><span><%=keyword1%> 총정보량</span></th>
			<th scope="col"><span><%=keyword2%> 총정보량</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
	    		JSONObject obj2= new  JSONObject();
				obj = (JSONObject)arr.get(i);
				obj2 = (JSONObject)arr2.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("fulldate")%></span></td>
			<td><span><%=obj.get("column-1")%></span></td>
			<td><span><%=obj2.get("column-2")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
	}
}else if("3_1".equals(section) || "3_2".equals(section)){
	if("3_1".equals(section)){
		arr = searchMgr.getSentiData02(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype);
	}else if("3_2".equals(section)){
		arr = searchMgr.getSentiData02(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype);
	}
%>
<table style="width:700px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:200px">
			<col style="width:200px">
			<col style="width:200px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 4;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
			</tr>
			<tr>
			<th scope="col"><span>매체</span></th>
			<th scope="col"><span><%=keyword%> 긍정</span></th>
			<th scope="col"><span><%=keyword%> 부정</span></th>
			<th scope="col"><span><%=keyword%> 중립</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("category")%></span></td>
			<td><span><%=obj.get("column-1")%></span></td>
			<td><span><%=obj.get("column-2")%></span></td>
			<td><span><%=obj.get("column-3")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("4_1".equals(section) || "4_2".equals(section)){
	long diffCnt = du.DateDiff("yyyyMMdd",i_edate,i_sdate);
	if(diffCnt > 31){
		i_sdate = du.addDay_v2(i_edate, -31).replaceAll("-", "");
	}
	
	if("4_1".equals(section)){
		arr = searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, limit);
	}else if("4_2".equals(section)){
		arr = searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, limit);
	}
%>
<table style="width:200px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:200px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 1;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
			</tr>
			<tr>
			<th scope="col"><span>연관키워드</span></th>
			<th scope="col"><span>수량</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("word")%></span></td>
			<td><span><%=obj.get("cnt")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("5_1".equals(section) || "5_2".equals(section)){
	JSONObject tmpObj = null;
	if("5_1".equals(section)){
		tmpObj = searchMgr.getRelationInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, 1000, 0);
		arr = tmpObj.getJSONArray("data");
	}else if("5_2".equals(section)){
		tmpObj = searchMgr.getRelationInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, 1000, 0);
		arr = tmpObj.getJSONArray("data");
	}
%>
<table style="width:1000px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:400px">
			<col style="width:400px">
			<col style="width:100px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 4;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
			</tr>
			<tr>
			<th scope="col"><span>출처</span></th>
			<th scope="col"><span>제목</span></th>
			<th scope="col"><span>URL</span></th>
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
			<td><span><%=obj.get("sitename")%></span></td>
			<td><span><%=obj.get("title")%></span></td>
			<td><span><%=obj.get("url")%></span></td>
			<td><span><%=obj.get("date")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}
%>
