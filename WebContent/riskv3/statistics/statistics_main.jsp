<%@page import="java.net.URLEncoder"%>
<%@page import="javax.sound.sampled.AudioFormat.Encoding"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@page import="risk.json.*"%>
<%@ page import="java.util.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.statistics.StatisticsMgr"%>

<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

String eDate = pr.getString("eDate",du.getCurrentDate());

String sDate = pr.getString("sDate",du.addDay_v2(eDate, -7));


StatisticsMgr stMgr = new StatisticsMgr();
JSONArray list1 = stMgr.getWeeklyChannelVolumn(sDate, eDate); 
//JSONArray list2 = stMgr.getWeeklyInfoVolumn(sDate, eDate);
JSONArray list3 = stMgr.getWeeklyInfluencerTop10(sDate, eDate);

//JSONArray list4 = stMgr.getWeeklyInfoVolumn2(sDate, eDate);
JSONArray list5 = stMgr.getWeeklyInfoVolumn3(sDate, eDate);

JSONObject obj1 = stMgr.getSentiVolume(sDate, eDate);

JSONObject obj2 = stMgr.getOniineVolume(sDate, eDate);


%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/devel.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	$("#weekly_info_tb tr").each(function(row){
		$("#weekly_info").rowspan2(row,3);
	});	
	
	$("#weekly_info_tb2 tr").each(function(row){
		$("#weekly_info2").rowspan2(row,3);
	});
	
	$("#weekly_info_tb3 tr").each(function(row){
		$("#weekly_info3").rowspan2(row,3);
	});
	
});

function getSearch(){
	$("#frm").attr("action","statistics_main.jsp");
	$("#frm").attr("target","");
	$("#frm").find("[name=sDate]").val( $("#sDate").val() );
	$("#frm").find("[name=eDate]").val( $("#eDate").val() );
	$("#frm").submit();
}


function excelDownload(type){
	
	$("#frm").attr("action","statistics_excel.jsp");
	$("#frm").find("[name=type]").val( type );
	$("#frm").find("[name=sDate]").val( $("#sDate").val() );
	$("#frm").find("[name=eDate]").val( $("#eDate").val() );
	$("#frm").attr("target","ifr_empty");
	$("#frm").submit();
}

function getReplyCnt(){
	$('#fr_content').css("cursor","wait");
	var param = "sDate="+$("#sDate").val()+"&eDate="+$("#eDate").val();
	
	$.ajax({      
        type:"POST",  
        url:"statistics_reply.jsp",
        data:param,
        dataType:"text",        
        success:function(data){        	
        	if(data.trim() == "success"){
        		$('#fr_content').css('cursor','auto');
        		alert("업데이트하였습니다.");
        		location.reload();
        	}
        }
	});
}

</script>
</head>

<body>
<form action="statistics_main.jsp" id="frm">
<input type="hidden" name="sDate" >
<input type="hidden" name="eDate" >
<input type="hidden" name="type" >
</form>
<div id="fr_content">
		<!-- 페이지 타이틀 -->
		<h3><span class="icon">-</span><img src="../../images/statistics/tit_0201.gif" alt=""></h3>
		<!-- // 페이지 타이틀 -->

		<!-- Locator -->
		<div class="ui_locator">
			<span class="home"></span>HOME<span class="arrow">></span>관리자<span class="arrow">></span><strong>통계 관리</strong>
		</div>
		<!-- // Locator -->

		<!-- 검색 -->
		<div class="article ui_searchbox_00">
			<div class="c_wrap">
				<div class="ui_row_00">
					<span class="title"><span class="icon">-</span>검색기간</span>
					<span class="txts">
						<input id="sDate" type="text" class="ui_datepicker_input input_date_first" value="<%=sDate%>" readonly><label for="sDate">날짜입력</label>
						<span>~</span>
						 <input id="eDate" type="text" class="ui_datepicker_input input_date_last" value="<%=eDate%>" readonly><label for="eDate">날짜입력</label>
						<button class="ui_btn_02" onclick="getSearch();">검색</button>
					</span>
				</div>
			</div>
		</div>
		<!-- // 검색 -->
		
		<!-- 블로그 -->
		<div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>주간 정보량 및 부정율</h4>		
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('1'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>	
			<table summary="주간 정보량 및 부정율">
			<caption>주간 정보량 및 부정율</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<!-- <col style="width:10%">  -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">일자</th>
					<th scope="col">언론+포탈</th>
					<th scope="col">블로그</th>
					<th scope="col">카페</th>
					<th scope="col">커뮤니티</th>
					<th scope="col">트위터</th>
					<th scope="col">페이스북</th>
					<th scope="col">대구광역시sns</th>
					<th scope="col">총 합계</th>
					<!-- <th scope="col">부정율</th> -->
				</tr>
			</thead>
			<tbody>
			<%
			JSONObject obj = null;
			if(list1.length() > 0){
				int count[] = new int[8];
				
				for(int i=0; i < list1.length(); i++){
					obj = (JSONObject)list1.get(i);
					
					count[0] += obj.getInt("news");
					count[1] += obj.getInt("blog");
					count[2] += obj.getInt("caffe");
					count[3] += obj.getInt("community");
					count[4] += obj.getInt("twitter");
					count[5] += obj.getInt("facebook");
					count[6] += obj.getInt("daegu");
					count[7] += obj.getInt("total");
					
			%>
			<tr height="25">
				<td><%=obj.get("md_date")%></td>	
				<td><%=su.addComma(obj.getString("news")) %></td>
				<td><%=su.addComma(obj.getString("blog"))%></td>
				<td><%=su.addComma(obj.getString("caffe"))%></td>
				<td><%=su.addComma(obj.getString("community"))%></td>
				<td><%=su.addComma(obj.getString("twitter"))%></td>
				<td><%=su.addComma(obj.getString("facebook"))%></td>
				<td><%=su.addComma(obj.getString("daegu"))%></td>
				<td><%=su.addComma(obj.getString("total"))%></td>
				<%-- <td><%=su.addComma(obj.getString("neg"))%>%</td> --%>
			</tr>		
			<%
					
				}
				
			%>
			<tr style="background: #f3f3f3;">
				<td>총 합계</td>
				<td><%=su.addComma(count[0]+"")%></td>
				<td><%=su.addComma(count[1]+"")%></td>
				<td><%=su.addComma(count[2]+"")%></td>
				<td><%=su.addComma(count[3]+"")%></td>
				<td><%=su.addComma(count[4]+"")%></td>
				<td><%=su.addComma(count[5]+"")%></td>
				<td><%=su.addComma(count[6]+"")%></td>
				<td><%=su.addComma(count[7]+"")%></td>
			</tr>
			<%
			float persent[] = new float[8];
			int a = 8;
			float b = 0;
			
			b = (a/3)*100;
			System.out.println( b );
			
			persent[0] = Math.round((Double.parseDouble(count[0]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[1] = Math.round((Double.parseDouble(count[1]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[2] = Math.round((Double.parseDouble(count[2]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[3] = Math.round((Double.parseDouble(count[3]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[4] = Math.round((Double.parseDouble(count[4]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[5] = Math.round((Double.parseDouble(count[5]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[6] = Math.round((Double.parseDouble(count[6]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[7] = Math.round((Double.parseDouble(count[7]+"")/Double.parseDouble(count[7]+""))*100.00);
			%>
			<tr style="background: #f3f3f3;">
				<td>백분율</td>
				<td><%=persent[0]%>%</td>
				<td><%=persent[1]%>%</td>
				<td><%=persent[2]%>%</td>
				<td><%=persent[3]%>%</td>
				<td><%=persent[4]%>%</td>
				<td><%=persent[5]%>%</td>
				<td><%=persent[6]%>%</td>
				<td><%=persent[7]%>%</td>
			</tr>
			<%
			}else{
			%>
			<tr height="25">
				<td align="center" bgcolor="#FFFFFF" colspan="10">데이터가 없습니다.</td>
			</tr>	
			<%
			}
			 %>
			</tbody>
			</table>
		</div>
		
		
		<%-- <div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>주간 주요시정별 정보량 현황</h4>
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>			
			<table summary="주간 주요시정별 정보량 현황" id="weekly_info">
			<caption>주간 주요시정별 정보량 현황</caption>
			<colgroup>
				<col >
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">정보구분</th>
					<th scope="col">총부정 정보량</th>
					<th scope="col">총 정보량</th>
					<th scope="col">상세구분</th>
					<th scope="col">부정 정보량</th>
					<th scope="col">정보량</th>
				</tr>
			</thead>
			<tbody id="weekly_info_tb">
			<%
			if(list2.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list2.length(); i++){
					obj = (JSONObject)list2.get(i);
					
			%>
			<tr height="25">
				<td><%=obj.get("type2")%></td>
				<td><%=su.addComma(obj.getString("neg1"))%></td>
				<td><%=su.addComma(obj.getString("cnt2"))%></td>
				<td><%=obj.get("type21")%></td>
				<td><%=su.addComma(obj.getString("neg2"))%></td>
				<td><%=su.addComma(obj.getString("cnt21"))%></td>
			</tr>
			<%	
				}
			}else{
			%>
			<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="6">데이터가 없습니다.</td>
			</tr>
			<%	
			}
			%>
			</tbody>
			</table>
		</div> --%>
		
		<%-- <div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>주간 대분류/소분류별 정보량 현황</h4>
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>			
			<table summary="주간 대분류/소분류별 정보량 현황" id="weekly_info2">
			<caption>주간 대분류/소분류별 정보량 현황</caption>
			<colgroup>
				<col >
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">정보구분</th>
					<th scope="col">총부정 정보량</th>
					<th scope="col">총 정보량</th>
					<th scope="col">상세구분</th>
					<th scope="col">부정 정보량</th>
					<th scope="col">정보량</th>
				</tr>
			</thead>
			<tbody id="weekly_info_tb2">
			<%
			if(list4.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list4.length(); i++){
					obj = (JSONObject)list4.get(i);
					
			%>
			<tr height="25">
				<td><%=obj.get("type3")%></td>
				<td><%=su.addComma(obj.getString("neg1"))%></td>
				<td><%=su.addComma(obj.getString("cnt3"))%></td>
				<td><%=obj.get("type31")%></td>
				<td><%=su.addComma(obj.getString("neg2"))%></td>
				<td><%=su.addComma(obj.getString("cnt31"))%></td>
			</tr>
			<%	
				}
			}else{
			%>
			<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="6">데이터가 없습니다.</td>
			</tr>
			<%	
			}
			%>
			</tbody>
			</table>
		</div> --%>
		
		<div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>성향별 정보량 현황</h4>
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('6'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>			
			<table summary="성향별 정보량 현황" id="senti_info">
			<caption>성향별 정보량 현황</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">성향</th>
					<th scope="col">정보량</th>
					<th scope="col">백분율</th>
				</tr>
			</thead>
			<tbody id="weekly_info_tb2">
			
			<tr height="25">
				<td>긍정</td>
				<td><%=su.addComma(obj1.getString("POS")) %></td>
				<td><%=Math.round(obj1.getDouble("POS")/obj1.getDouble("TOTAL_CNT")*100.00 ) %>%</td>				
			</tr>
			
			<tr height="25">
				<td>부정</td>
				<td><%=su.addComma(obj1.getString("NEG")) %></td>
				<td><%=Math.round(obj1.getDouble("NEG")/obj1.getDouble("TOTAL_CNT")*100.00 ) %>%</td>
			</tr>
			
			<tr height="25">
				<td>중립</td>
				<td><%=su.addComma(obj1.getString("NEU")) %></td>
				<td><%=Math.round(obj1.getDouble("NEU")/obj1.getDouble("TOTAL_CNT")*100.00 ) %>%</td>
			</tr>
			
			<tr height="25" style="background: #f3f3f3;">
				<td>총계</td>
				<td ><%=su.addComma(obj1.getString("TOTAL_CNT")) %></td>
				<td><%=Math.round(obj1.getDouble("TOTAL_CNT")/obj1.getDouble("TOTAL_CNT")*100.00 ) %>%</td>
			</tr>
			
			
			</tbody>
			</table>
		</div>
		
		
		<%-- <div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>주간 실국/부서별 정보량 현황</h4>
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>			
			<table summary="주간 실국/부서별 정보량 현황" id="weekly_info3">
			<caption>주간 실국/부서별 정보량 현황</caption>
			<colgroup>
				<col >
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">정보구분</th>
					<th scope="col">총부정 정보량</th>
					<th scope="col">총 정보량</th>
					<th scope="col">상세구분</th>
					<th scope="col">부정 정보량</th>
					<th scope="col">정보량</th>
				</tr>
			</thead>
			<tbody id="weekly_info_tb3">
			<%
			if(list5.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list5.length(); i++){
					obj = (JSONObject)list5.get(i);
					
			%>
			<tr height="25">
				<td><%=obj.get("type5")%></td>
				<td><%=su.addComma(obj.getString("neg1"))%></td>
				<td><%=su.addComma(obj.getString("cnt5"))%></td>
				<td><%=obj.get("type51")%></td>
				<td><%=su.addComma(obj.getString("neg2"))%></td>
				<td><%=su.addComma(obj.getString("cnt51"))%></td>
			</tr>
			<%	
				}
			}else{
			%>
			<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="6">데이터가 없습니다.</td>
			</tr>
			<%	
			}
			%>
			</tbody>
			</table>
		</div> --%>
		
		<div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>실국별 온라인 관심도</h4>
						
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('7'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>
			<button class="ui_btn_02 ui_shadow_01" onclick="excelDownload('8'); return false;" style="float: right;margin-bottom: 2px;margin-right: 5px">
				<span>댓글 상세 엑셀 다운로드</span>
			</button>
			<button class="ui_btn_02 ui_shadow_01" onclick="getReplyCnt(); return false;" style="float: right;margin-bottom: 2px;margin-right: 5px">
				<span>댓글 업데이트</span>
			</button>						
			<p style="margin-top: 8px;margin-bottom: 8px;">○ 정보건수 : 뉴스 <%=su.addComma(obj2.getString("news"))%>건 / 개인버즈 <%=su.addComma(obj2.getString("indi"))%>건 / 공식SNS <%=su.addComma(obj2.getString("sns"))%>건 / 댓글 <%=su.addComma(obj2.getString("reply"))%>건</p>
						
			<%
			JSONArray arr = obj2.getJSONArray("list");
			int size = 0;
			int[] length = new int [2];
			int[] strNum = new int [2];
			if(arr.length()%2==0){
				size = arr.length()/2;
				length[0] = size;
				length[1] = arr.length();
				strNum[0] = 0;
				strNum[1] = size;
			}else{
				size = (arr.length()/2);
				length[0] = size+1;
				length[1] = arr.length();
				strNum[0] = 0;
				strNum[1] = size+1;
			}
			for(int x=0 ; x<2 ; x++){
			%>			
			<table summary="주간 실국/부서별 정보량 현황" id="weekly_info3">
			<caption>실국별 온라인 관심도</caption>
			<colgroup>
				<%if(x==0){ %>
				<col style="width:15%">
				<col style="width:12%">
				<col style="width:12%">
				<%} %>
				<%
				
				String[] name = new String[size];
				int[] news = new int[size];
				int[] indi = new int[size];
				int[] sns = new int[size];
				int[] reply = new int[size];
				
				if(arr.length()%2==0){
					name = new String[size];
					news = new int[size]; 
					indi = new int[size]; 
					sns = new int[size];
					reply = new int[size];
				}else{
					if(x==0){
						name = new String[size+1];
						news = new int[size+1]; 
						indi = new int[size+1];
						sns = new int[size+1];
						reply = new int[size+1];	
					}else{
						name = new String[size];
						news = new int[size]; 
						indi = new int[size]; 
						sns = new int[size];
						reply = new int[size];
					}
					
				}
				
				int index  = 0;
				
				for(int i=strNum[x] ; i<length[x] ; i++){
					JSONObject object = (JSONObject)arr.get(i);
					name[index] = object.getString("name");
					news[index] = object.getInt("news");
					indi[index] = object.getInt("indi");
					sns[index] = object.getInt("sns");
					reply[index] = object.getInt("reply");
					index++;
				%>
					<col style="width:12%">
				<%
				}
				%>				
			</colgroup>
			<thead>
				<tr>
				<%if(x==0){ %>
					<th scope="col" colspan="2">구분</th>
					<th scope="col" >계</th>
				<%} %>
					<%					
					for(int i=0 ; i<name.length ; i++){
					%>	
					<th scope="col"><%=name[i] %></th>	
					<%	
					}	
					%>
					
				</tr>
			</thead>
			<tbody id="">
			
			<tr height="25">
			<%if(x==0){ %>
				<td rowspan="4">정보구분</td>
				<td>뉴스</td>
			<%} %>
				<%
				if(x==0){
				%>
				<td><%=su.addComma(obj2.getString("news"))%></td>
				<%} %>
				<%					
				for(int i=0 ; i<news.length ; i++){
				%>	
				<td><%=su.addComma(news[i]+"") %></td>	
				<%	
				}	
				%>
			</tr>
			
			<tr height="25">
				<%
				if(x==0){
				%>
				<td>개인</td>
				<td><%=su.addComma(obj2.getString("indi"))%></td>
				<%} %>
				<%					
				for(int i=0 ; i<indi.length ; i++){
				%>	
				<td><%=su.addComma(indi[i]+"") %></td>	
				<%	
				}	
				%>
			</tr>
			
			<tr height="25">
				<%
				if(x==0){
				%>
				<td>공식SNS</td>
				<td><%=su.addComma(obj2.getString("sns"))%></td>
				<%} %>
				<%					
				for(int i=0 ; i<sns.length ; i++){
				%>	
				<td><%=su.addComma(sns[i]+"") %></td>	
				<%	
				}	
				%>
			</tr>
			
			<tr height="25">
				<%
				if(x==0){
				%>
				<td>댓글</td>
				<td><%=su.addComma(obj2.getString("reply"))%></td>
				<%} %>
				<%					
				for(int i=0 ; i<reply.length ; i++){
				%>	
				<td><%=su.addComma(reply[i]+"") %></td>	
				<%	
				}	
				%>
			</tr>
						
			</tbody>
			</table>
			
				<%if(x==0){ %>
				<br>
				<%} %>
			<%} %>
			
		</div>
		
		
		<div class="article ui_table_00 m_t_30">
			<h4 style="display:inline;margin-bottom: 10px;"><span class="icon">-</span>주간 주요 영향력자 Top10</h4>
			<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3'); return false;" style="float: right;margin-bottom: 2px;"><span class="icon excel">Excel Download</span></button>			
			<table summary="주간 주요 영향력자 Top10">
			<caption>주간 주요 영향력자 Top10</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">순위</th>
					<th scope="col">출처</th>
					<th scope="col">제목</th>
					<th scope="col">확산수량</th>
				</tr>
			</thead>
			<tbody>
			<%
			if(list3.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list3.length(); i++){
					obj = (JSONObject)list3.get(i);
					String url = obj.getString("id_url");
					url = URLEncoder.encode(url, "UTF-8");
			%>
			<tr height="25">
				<td><%=(i+1)%></td>
				<td><%=obj.get("source")%></td>
				<td><a href='http://hub.buzzms.co.kr?url=<%=url%>' target="_blank"> <%=obj.get("id_title")%> </a></td>
				<td><%=su.addComma(obj.getString("cnt"))%></td>
			</tr>	
			<%
			
				}
			}else{
			%>
			<tr height="25">
				<td align="center" bgcolor="#FFFFFF" colspan="4">데이터가 없습니다.</td>
			</tr>	
			<%	
			}
			%>
		
			</tbody>
			</table>
		</div>				
	</div>
	<br/>
	<br/>
	<br/>
<iframe style="display: none" width="0" id="ifr_empty"></iframe>	
</body>
</html>