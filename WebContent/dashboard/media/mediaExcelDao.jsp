<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.dashboard.media.MediaMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

MediaMgr mMgr = new MediaMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String title = pr.getString("title");


if("1".equals(section)){	
	String pcode = pr.getString("pcode");
	JSONArray mList = mMgr.getPublicityTrend(sDate,eDate,scope,keyword,pcode);
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
				int colspan = 2;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>날짜</span></th>			
			<th scope="col"><span>정보량</span></th>
			
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<mList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)mList.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("fulldate")%></span></td>			
			<td><span><%=obj.getString("column-1")%></span></td>
			
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>


<%}else if("2".equals(section)){
	String pcode = pr.getString("pcode");
	JSONArray mList = mMgr.getChannelInfo(sDate,eDate,scope,keyword,pcode);
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
				int colspan = 2;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>채널</span></th>			
			<th scope="col"><span>정보량</span></th>
			
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<mList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)mList.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("category")%></span></td>			
			<td><span><%=obj.getString("column_1")%></span></td>
			
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>

<%}else if("3".equals(section)){
	String pcode = pr.getString("pcode");
	
	JSONArray mList = mMgr.getRealstateGraph(sDate, eDate, scope, keyword, pcode);
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
				int colspan = 2;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>실국</span></th>			
			<th scope="col"><span>정보량</span></th>
			
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<mList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)mList.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("category")%></span></td>			
			<td><span><%=obj.getString("column-1")%></span></td>
			
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>

<%}else{
	String pcode = pr.getString("pcode");
	
	JSONArray mList = mMgr.getRealstateInfo_Excel(sDate, eDate, scope, keyword, pcode);
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
			<th scope="col"><span>출처</span></th>
			<th scope="col"><span>제목</span></th>
			<th scope="col"><span>URL</span></th>
			<th scope="col"><span>담당실국</span></th>
			<th scope="col"><span>운영부서</span></th>
			
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<mList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)mList.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("date")%></span></td>			
			<td><span><%=obj.getString("site_name")%></span></td>
			<td><span><%=obj.getString("title")%></span></td>
			<td><span><%=obj.getString("url")%></span></td>
			<td><span><%=obj.getString("type5")%></span></td>
			<td><span><%=obj.getString("type51")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>

<%}%>