<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.dashboard.summary.SummaryMgr"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.dashboard.mrt.MrtMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

SummaryMgr sMgr = new SummaryMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String portal_date = pr.getString("portal_date", "");
String title = pr.getString("title");
String docid = pr.getString("docid","");
String pos_cnt = pr.getString("pos_cnt","");
String neg_cnt = pr.getString("neg_cnt","");
String neu_cnt = pr.getString("neu_cnt","");
String tab = pr.getString("tab", "0");
String tier = pr.getString("tier", "");
JSONArray arr = new JSONArray();

sMgr.getLucySwitch();

if("1".equals(section)){
	arr = sMgr.getOnlineTrendData(scope, keyword, sDate, eDate,tier);
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
			<th scope="col"><span>대구광역시</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("date")%></span></td>
			<td><span><%=obj.get("column-2")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("2".equals(section)){
	arr = sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "02",tier);
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
	    	int pos = 0;
	    	int neg = 0;
	    	int neu = 0;
	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				pos += Integer.parseInt((String)obj.get("column-2"));
				neg += Integer.parseInt((String)obj.get("column-3"));
				neu += Integer.parseInt((String)obj.get("column-4"));
	    %>
		<tr>
			<td><span><%=obj.get("category")%></span></td>
			<td><span><%=obj.get("column-2")%></span></td>
			<td><span><%=obj.get("column-3")%></span></td>
			<td><span><%=obj.get("column-4")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    <tr>
			<td><span>총 정보량</span></td>
			<td><span><%=pos%></span></td>
			<td><span><%=neg%></span></td>
			<td><span><%=neu%></span></td>
		</tr>
	    </tbody>
	</table>
<%
}else if("3".equals(section)){
	arr = sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "03",tier);
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
	    	int pos = 0;
	    	int neg = 0;
	    	int neu = 0;
	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				pos += Integer.parseInt((String)obj.get("column-2"));
				neg += Integer.parseInt((String)obj.get("column-3"));
				neu += Integer.parseInt((String)obj.get("column-4"));
	    %>
		<tr>
			<td><span><%=obj.get("category")%></span></td>
			<td><span><%=obj.get("column-2")%></span></td>
			<td><span><%=obj.get("column-3")%></span></td>
			<td><span><%=obj.get("column-4")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    <tr>
			<td><span>총 정보량</span></td>
			<td><span><%=pos%></span></td>
			<td><span><%=neg%></span></td>
			<td><span><%=neu%></span></td>
		</tr>
	    </tbody>
	</table>
<%
}else if("4".equals(section)){
	JSONObject obj2 = new  JSONObject();
	obj2 = sMgr.getPotalTopList(scope, keyword, sDate, eDate, tab, 1, true);
	arr = obj2.getJSONArray("list"); 
%>

	<table style="width:1200px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:200px">
			<col style="width:400px">
			<col style="width:400px">
			<col style="width:100px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 5;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>포탈구분</span></th>
			<th scope="col"><span>영역</span></th>
			<th scope="col"><span>제목</span></th>
			<th scope="col"><span>URL</span></th>
			<th scope="col"><span>최초노출일시</span></th>
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
			<td><span><%=obj.get("boardname")%></span></td>
			<td><span><%=obj.get("title")%></span></td>
			<td><span><%=obj.get("url")%></span></td>
			<td><span><%=obj.get("date")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%}else if("5_1".equals(section)){ 
	//arr = sMgr.getSummaryPortalList_excel(portal_date, scope, keyword, tab);
	arr = sMgr.getSummaryPortalList3_Excel(portal_date, keyword,SS_M_ID);
%>
<table style="width:1240px; table-layout:fixed;" border="1" cellspacing="1" >
<colgroup>
<col style="width:120px">
<col style="width:220px">
<col style="width:400px">
<col style="width:400px">
<col style="width:100px">
</colgroup>
<thead>
<%
	int colspan = 5;
%>
<tr style="height:40px">
	<th scope="col" colspan="<%=colspan%>"><%=title%></th>
</tr>
<tr style="height:30px">
	<th scope="col" colspan="<%=colspan%>"><%=eDate %></th>
</tr>
<tr>
<th scope="col"><span>포탈구분</span></th>
<th scope="col"><span>댓글</span></th>
<th scope="col"><span>제목</span></th>
<th scope="col"><span>URL</span></th>
<th scope="col"><span>일자</span></th>
</tr>
</thead>
<tbody>
<%
if(arr.length() > 0){
	for(int i=0; i < arr.length(); i++){
		JSONObject obj = new  JSONObject();
		obj = (JSONObject)arr.get(i);
%>		
<tr>
	<td><%=obj.get("MD_SITE_NAME")%></td>
	<td><%=obj.get("DOC_CNT")%>(긍정:<%=obj.get("POS_CNT")%>/부정:<%=obj.get("NEG_CNT")%>/중립:<%=obj.get("NEU_CNT")%>)</td>
	<td><%=obj.get("MD_TITLE")%></td>
	<td><%=obj.get("EX_URL")%></td>
	<td><%=obj.get("MD_DATE")%></td>
</tr>	
<%		
	}
}
%>
</tbody>
</table>

<%}else if("5_2".equals(section)){ %>
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
				<th scope="col" colspan="<%=colspan%>"><%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>총정보량</span></th>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			</tr>
		</thead>
	    <tbody>
		<tr>
			<td><span><%=Integer.parseInt(pos_cnt)+Integer.parseInt(neg_cnt)+Integer.parseInt(neu_cnt) %></span></td>
			<td><span><%=pos_cnt%></span></td>
			<td><span><%=neg_cnt%></span></td>
			<td><span><%=neu_cnt%></span></td>
		</tr>
	    </tbody>
	</table>

<%}else if("5_3".equals(section)){ 
	arr = sMgr.getRelationKeyword(docid, eDate, SS_M_ID);

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
				int colspan = 2;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>연관 클라우드</span></th>
			<th scope="col"><span>총 건수</span></th>
			</tr>
		</thead>
	    <tbody>
	    <%if(arr.length() > 0){
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
	    		obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("word_nm") %></span></td>
			<td><span><%=obj.get("cnt") %></span></td>
		</tr>
		<%}
		}else{%>
			<tr>
			<td colspan="2">검색 된 데이터가 없습니다.</td>
		</tr>
		<%}
	    %>
	    </tbody>
	</table>
<%
}else if("6".equals(section)){
	arr = sMgr.getSocialIssueList_excel(scope, keyword, sDate, eDate);
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
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>출처</span></th>
			<th scope="col"><span>제목</span></th>
			<th scope="col"><span>URL</span></th>
			<th scope="col"><span>확산건수</span></th>
			<th scope="col"><span>수집일</span></th>
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
			<td><span><%=obj.get("samecnt")%></span></td>
			<td><span><%=obj.get("date").toString()%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}
%>