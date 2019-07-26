<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.dashboard.opinion.OpinionMgr"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.dashboard.mrt.MrtMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%

ParseRequest pr = new ParseRequest(request);
pr.printParams();

OpinionMgr oMgr = new OpinionMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");

String title = pr.getString("title");
String pCode = pr.getString("pCode");
String source = pr.getString("source");
String limit = pr.getString("limit");
String senti = pr.getString("senti");

JSONArray arr = new JSONArray();
JSONArray arr2 = new JSONArray();
JSONArray arr3 = new JSONArray();

if("1".equals(section)){
	arr = oMgr.getinfoCountData(scope, keyword, sDate, eDate);
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
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			<th scope="col"><span>총정보량</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("pos")%></span></td>
			<td><span><%=obj.get("neg")%></span></td>
			<td><span><%=obj.get("neu")%></span></td>
			<td><span><%=obj.get("cnt")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
 }else if("2".equals(section)){
arr = oMgr.getWeekData(scope, keyword, sDate, eDate);
%>

	<table style="width:500px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:100px">
			<col style="width:100px">
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
			<th scope="col"><span>날짜</span></th>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			<th scope="col"><span>총정보량</span></th>
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
			<td><span><%=obj.get("column-1")%></span></td>
			<td><span><%=obj.get("column-2")%></span></td>
			<td><span><%=obj.get("column-3")%></span></td>
			<td><span><%=obj.get("column-4")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("3".equals(section)){
 arr = oMgr.getInfoDivCountData(scope, keyword, sDate, eDate, pCode);
 %>

 	<table style="width:500px; table-layout:fixed;" border="1" cellspacing="1" >
 	    <caption></caption>
 		<colgroup>
 			<col style="width:100px">
 			<col style="width:100px">
 			<col style="width:100px">
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
 			<th scope="col"><span>정보구분-상세</span></th>
 			<th scope="col"><span>긍정</span></th>
 			<th scope="col"><span>부정</span></th>
 			<th scope="col"><span>중립</span></th>
 			<th scope="col"><span>총정보량</span></th>
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
 			<td><span><%=obj.get("cnt")%></span></td>
 		</tr>
 		<%
 	    	}
 	    %>
 	    </tbody>
 	</table>
<%
  }else if("4".equals(section)){
arr = oMgr.getMainIssueCountData(scope, keyword, sDate, eDate);
%>

	<table style="width:600px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:200px">
			<col style="width:100px">
			<col style="width:100px">
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
			<th scope="col"><span>주요이슈</span></th>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			<th scope="col"><span>총정보량</span></th>
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
			<td><span><%=obj.get("cnt")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("5_1".equals(section)){
 arr = oMgr.getMediaData(scope, keyword, sDate, eDate);
 %>

	<table style="width:200px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
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
			<th scope="col"><span>매체</span></th>
			<th scope="col"><span>총정보량</span></th>
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
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("5_2".equals(section)){
 arr = oMgr.getMediaData(scope, keyword, sDate, eDate);
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
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
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
	    </tbody>
	</table>
<%
}else if("6".equals(section)){
 arr = oMgr.getMainMediaData(scope, keyword, sDate, eDate, source, limit);
 %>

	<table style="width:500px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:200px">
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
			<th scope="col"><span>주요매체-상세</span></th>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
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
}else if("7".equals(section)){
 arr = oMgr.getRelationKeywordData(scope, keyword, sDate, eDate, source, limit, senti);
 %>

	<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:200px">
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
			<th scope="col"><span>연관키워드</span></th>
			<th scope="col"><span>총합계</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    %>
		<tr>
			<td><span><%=obj.get("wordname")%></span></td>
			<td><span><%=obj.get("keycnt")%></span></td>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("8".equals(section)){
	arr = oMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "1");
	arr2 = oMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "2");
	arr3 = oMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "3");
%>

	<table style="width:600px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:200px">
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
				<th scope="col" colspan="<%=colspan%>"><%=sDate %> ~ <%=eDate %></th>
			</tr>
			<tr>
			<th scope="col"><span>긍정</span></th>
			<th scope="col"><span>부정</span></th>
			<th scope="col"><span>중립</span></th>
			</tr>
		</thead>
	    <tbody>
	    <%
		    int length = arr.length();
		    int length2 = arr2.length();
		    int length3 = arr3.length();
		    
		    String html = ""; 
	    	for(int i=0; i<10; i++){
	    		 html = "";
	    	%>
	    	<tr>
	    	<%
				JSONObject obj = new  JSONObject();
				JSONObject obj2 = new  JSONObject();
				JSONObject obj3 = new  JSONObject();
				
				if(i<length){
					obj = (JSONObject)arr.get(i);
					html += "<td><span>" + obj.get("wordname") + "(" + obj.get("cnt") + ")" + "</span></td>";
				}else{
					html += "<td></td>";
				}
				
				if(i<length2){
					obj2 = (JSONObject)arr2.get(i);
					html += "<td><span>" + obj2.get("wordname") + "(" + obj2.get("cnt") + ")" + "</span></td>";
				}else{
					html += "<td></td>";
				}
				
				if(i<length3){
					obj3 = (JSONObject)arr3.get(i);
					html += "<td><span>" + obj3.get("wordname") + "(" + obj3.get("cnt") + ")" + "</span></td>";
				}else{
					html += "<td></td>";
				}
	 		%>
	 		<%=html%>
	 		</tr>
	 		<%
		 		}
	    	%>
	    </tbody>
	</table>
<%
}else if("9_1".equals(section)){
 arr = oMgr.getRelaltionInfoList_excel(scope, keyword, sDate, eDate, "1",  source);
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
		<th scope="col"><span>유사</span></th>
		<th scope="col"><span>날짜</span></th>
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
		<td><span><%=obj.get("date")%></span></td>
	</tr>
	<%
    	}
    %>
    </tbody>
</table>
<%
}else if("9_2".equals(section)){
	 arr = oMgr.getRelaltionInfoList_excel(scope, keyword, sDate, eDate, "2",  source);
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
		<th scope="col"><span>유사</span></th>
		<th scope="col"><span>날짜</span></th>
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