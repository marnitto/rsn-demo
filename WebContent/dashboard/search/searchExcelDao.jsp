<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@page import="risk.dashboard.search.SearchMgr"%>
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

/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text3 = pr.getString("i_and_text3");				// AND 검색 키워드
String i_exact_text3 = pr.getString("i_exact_text3");				// 구문 검색 키워드
String i_or_text3 = pr.getString("i_or_text3");						// OR 검색 키워드
String i_exclude_text3 = pr.getString("i_exclude_text3");		// 제외단어 키워드

String i_remove_rt = pr.getString("i_remove_rt");

String title = pr.getString("title");
String keyword = pr.getString("keyword");
String keyword1 = pr.getString("keyword1");
String keyword2 = pr.getString("keyword2");
String keyword3 = pr.getString("keyword3");
int limit = pr.getInt("limit");											// 연관키워드 개수

JSONArray arr = new JSONArray();
JSONArray arr2 = new JSONArray();
JSONArray arr3 = new JSONArray();

if("2".equals(section)){
	
		arr = searchMgr.getInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, "1", SS_M_ID);
		arr2 = searchMgr.getInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, "2", SS_M_ID);
		arr3 = searchMgr.getInfoData(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, "3", SS_M_ID);
%>
	<table style="width:300px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:200px">
			<%if(!"".equals(keyword2)){ %>
			<col style="width:200px">
			<%} %>
			<%if(!"".equals(keyword3)){ %>
			<col style="width:200px">
			<%} %>
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 2;
			if(!"".equals(keyword2) ){
				colspan++;
			}
			if(!"".equals(keyword3) ){
				colspan++;
			}
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
			<%if(!"".equals(keyword2)){ %>
			<th scope="col"><span><%=keyword2%> 총정보량</span></th>
			<%} %>
			<%if(!"".equals(keyword3)){ %>
			<th scope="col"><span><%=keyword3%> 총정보량</span></th>
			<%} %>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
	    		JSONObject obj2= new  JSONObject();
	    		JSONObject obj3= new  JSONObject();
	    		obj = (JSONObject)arr.get(i);
	    		
	    		if(!"".equals(keyword2)){
		    		obj2 = (JSONObject)arr2.get(i);
	    		}
	    		
	    		if(!"".equals(keyword3)){
		    		obj3 = (JSONObject)arr3.get(i);
	    		}
				
	    %>
		<tr>
			<td><span><%=obj.get("fulldate")%></span></td>
			<td><span><%=obj.get("column-1")%></span></td>
			<%if(!"".equals(keyword2)){ %>
			<td><span><%=obj2.get("column-2")%></span></td>
			<%} %>
			<%if(!"".equals(keyword3)){ %>
			<td><span><%=obj3.get("column-3")%></span></td>
			<%} %>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
	
	
}else if("3_s1_1".equals(section) || "3_s1_2".equals(section) || "3_s1_3".equals(section)){
	if("3_s1_1".equals(section)){
		arr = searchMgr.getInfoDistribution_right(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s1_2".equals(section)){
		arr = searchMgr.getInfoDistribution_right(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s1_3".equals(section)){
		arr = searchMgr.getInfoDistribution_right(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, SS_M_ID);
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
}else if("3_s2_1".equals(section) || "3_s2_2".equals(section) || "3_s2_3".equals(section) ){
	if("3_s2_1".equals(section)){
		arr = searchMgr.getSentiTrend(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s2_2".equals(section)){
		arr = searchMgr.getSentiTrend(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s2_3".equals(section)){
		arr = searchMgr.getSentiTrend(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, SS_M_ID);
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
			<th scope="col"><span>날짜</span></th>
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
}else if("3_s3_1".equals(section) || "3_s3_2".equals(section) || "3_s3_3".equals(section)){
	if("3_s3_1".equals(section)){
		arr = searchMgr.getChannelTrend(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s3_2".equals(section)){
		arr = searchMgr.getChannelTrend(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, SS_M_ID);
	}else if("3_s3_3".equals(section)){
		arr = searchMgr.getChannelTrend(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, SS_M_ID);
	}
	
	String[] tmpChannel = i_sourcetype.split(" ");
%>
<table style="width:<%=100+(200*tmpChannel.length)%>; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<%
				for(int i=0; i<tmpChannel.length; i++){
					out.println("<col style='width:200px'>");
				}
			%>
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = tmpChannel.length+1;
			%>
			<tr style="height:40px">
				<th scope="col" colspan="<%=colspan%>"><%=title%></th>
			</tr>
			<tr style="height:30px">
				<th scope="col" colspan="<%=colspan%>"><%=i_sdate %> ~ <%=i_edate %></th>
			</tr>
			<tr>
			<th scope="col"><span>날짜</span></th>
			<%
				for(int i=0; i<tmpChannel.length; i++){
					if("TW".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 트위터</span></th>");
					}else if("FA".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 페이스북</span></th>");
					}else if("BL".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 블로그</span></th>");
					}else if("DC".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 커뮤니티</span></th>");
					}else if("CF".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 카페</span></th>");
					}else if("DN".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 언론</span></th>");
					}else if("PR".equals(tmpChannel[i])){
						out.println("<th scope='col'><span>" + keyword + " 포탈댓글</span></th>");
					}
				}
			%>
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
		<%
			for(int j=0; j<tmpChannel.length; j++){
				if("TW".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("TW") + "</span></td>");
				}else if("FA".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("FA") + "</span></td>");
				}else if("BL".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("BL") + "</span></td>");
				}else if("DC".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("DC") + "</span></td>");
				}else if("CF".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("CF") + "</span></td>");
				}else if("DN".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("DN") + "</span></td>");
				}else if("PR".equals(tmpChannel[j])){
					out.println("<td><span>" + obj.get("PR") + "</span></td>");
				}
			}
		%>
		</tr>
		<%
	    	}
	    %>
	    </tbody>
	</table>
<%
}else if("4_1".equals(section) || "4_2".equals(section) || "4_3".equals(section)){
	long diffCnt = du.DateDiff("yyyyMMdd",i_edate,i_sdate);
	if(diffCnt > 31){
		i_sdate = du.addDay_v2(i_edate, -31).replaceAll("-", "");
	}
	
	if("4_1".equals(section)){
		arr = searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, limit, SS_M_ID);
	}else if("4_2".equals(section)){
		arr = searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, limit, SS_M_ID);
	}else if("4_3".equals(section)){
		arr = searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, limit, SS_M_ID);
	}
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
}else if("5_1".equals(section) || "5_2".equals(section) || "5_3".equals(section)){
	JSONObject tmpObj = null;
	if("5_1".equals(section)){
		tmpObj = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		arr = tmpObj.getJSONArray("data");
	}else if("5_2".equals(section)){
		tmpObj = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		arr = tmpObj.getJSONArray("data");
	}else if("5_3".equals(section)){
		tmpObj = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
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
