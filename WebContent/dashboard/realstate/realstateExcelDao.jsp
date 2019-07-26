<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.dashboard.realstate.RealstateMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

RealstateMgr rMgr = new RealstateMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String title = pr.getString("title");

if("1".equals(section)){
	String type51 = pr.getString("type51");
	String[] type51_arr = type51.split(",");
	JSONArray rList = rMgr.getOnlineTrend(sDate,eDate,scope,keyword,type51);	
%>
<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:100px">
			<col style="width:100px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = type51_arr.length;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan+1%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan+1%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>날짜</span></th>
			<%
			for(int i=0 ; i<type51_arr.length ; i++){
			%>
			<th scope="col"><span><%=type51_arr[i].split("@@")[0] %></span></th>
			<%} %>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<rList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)rList.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("full_date")%></span></td>
			<%
			for(int x=0 ; x<type51_arr.length ; x++){
			%>
			<td><span><%=obj.getString("CNT_"+(x+1))%></span></td>
			<%} %>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>

<%}else if("2".equals(section)){
	String code = pr.getString("code");
	JSONObject object = rMgr.getChannelInfo(sDate,eDate,scope,keyword,code);
	
	JSONObject 	obj = object.getJSONObject("pie");
	JSONArray 	arr = object.getJSONArray("graph");
%>

<table style="width:400px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:100px">
			<col style="width:100px">
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
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>매체</span></th>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj1 = new  JSONObject();
				obj1 = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj1.get("category")%></span></td>
			<td><span><%=obj1.get("column-1")%></span></td>
			<td><span><%=obj1.get("column-2")%></span></td>
			<td><span><%=obj1.get("column-3")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    <tr>
			<td><span>총 정보량</span></td>
			<td><span><%=obj.get("pos")%></span></td>
			<td><span><%=obj.get("neg")%></span></td>
			<td><span><%=obj.get("neu")%></span></td>
		</tr>
	    </tbody>
	</table>
<%}else if("3".equals(section)){
	String senti = pr.getString("senti");	
	String code = pr.getString("code");
	JSONObject object = rMgr.getRelInfo(sDate,eDate,scope,keyword,senti,code,1,10,"true");
	JSONArray arr = object.getJSONArray("data");
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
				int colspan = 6;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>날짜</span></th>
			<th scope="col"><span>제목</span></th>
			<th scope="col"><span>URL</span></th>
			<th scope="col"><span>출처</span></th>
			<th scope="col"><span>성향</span></th>
			<th scope="col"><span>부서</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				String type9 = "";
				if("1".equals(obj.getString("senti"))){
					type9 = "긍정";
				}else if("2".equals(obj.getString("senti"))){
					type9 = "부정";
				}else{
					type9 = "중립";
				}
	    %>
		<tr>
			<td><span><%=obj.get("date").toString()%></span></td>
			<td><span><%=obj.get("title")%></span></td>
			<td><span><%=obj.get("url")%></span></td>
			<td><span><%=obj.get("site_name")%></span></td>
			<td><span><%=type9%></span></td>
			<td><span><%=obj.get("name")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%}%>