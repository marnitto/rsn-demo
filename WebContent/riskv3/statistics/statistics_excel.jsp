<%@page import="java.net.URLEncoder"%>
<%@page import="javax.sound.sampled.AudioFormat.Encoding"%>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>
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

String type = pr.getString("type");


StatisticsMgr stMgr = new StatisticsMgr();

response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
response.setHeader("Content-Description", "JSP Generated Data");


%>    
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
   input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
   .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
a:link { color: #333333; text-decoration: none; }
a:visited { text-decoration: none; color: #000000; }
a:hover { text-decoration: none; color: #FF9900; }
a:active { text-decoration: none; }

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	SCROLLBAR-face-color: #F2F2F2;
	SCROLLBAR-shadow-color: #999999;
	SCROLLBAR-highlight-color: #999999;
	SCROLLBAR-3dlight-color: #FFFFFF;
	SCROLLBAR-darkshadow-color: #FFFFFF;
	SCROLLBAR-track-color: #F2F2F2;
	SCROLLBAR-arrow-color: #333333;
     }
.menu_black {  font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #000000}
.textw { font-family: "돋움", "돋움체"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}

.menu_blue {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_gray {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #4B4B4B
}
.menu_red {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #CC0000
}
.menu_blueOver {

	font-family: Tahoma;
	font-size: 11px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_blueTEXTover {


	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3366CC;
	font-weight: normal;
}
.textwbig {
font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal
}
.textBbig {

font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal
}
.menu_grayline {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #4B4B4B;
	text-decoration: underline;
}
.menu_grayS {

font-family: "돋움", "돋움체"; font-size: 11px; line-height: 16px; color: #4B4B4B
}
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" border="1" >
			<%
			if( "1".equals(type) ){
				
				JSONArray list1 = stMgr.getWeeklyChannelVolumn(sDate, eDate); 
				
			%>
			<table summary="주간 정보량 및 부정율" border="1" cellspacing="0" cellpadding="0">
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
				
			</colgroup>
			<thead>
				<tr style="background: #f3f3f3;">
					<th scope="col">일자</th>
					<th scope="col">언론+포탈</th>
					<th scope="col">블로그</th>
					<th scope="col">카페</th>
					<th scope="col">커뮤니티</th>
					<th scope="col">트위터</th>
					<th scope="col">페이스북</th>
					<th scope="col">대구광역시sns</th>
					<th scope="col">총 합계</th>					
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
			
			<%
			}else if("4".equals(type)){
				JSONArray list4 = stMgr.getWeeklyInfoVolumn2(sDate, eDate);
				
			%>
			
			<table summary="주간 대분류/소분류별 정보량 현황" id="weekly_info2"  border="1" cellspacing="0" cellpadding="0">
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
			JSONObject obj = null;
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
			
			
			
			<%
			}else if("5".equals(type)){
				JSONArray list5 = stMgr.getWeeklyInfoVolumn3(sDate, eDate);
				
			%>
			
			<table summary="주간 실국/부서별 정보량 현황" id="weekly_info2"  border="1" cellspacing="0" cellpadding="0">
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
			<tbody id="weekly_info_tb2">
			<%
			JSONObject obj = null;
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
			
			
			
			<%
			}else if("2".equals(type)){
				JSONArray list2 = stMgr.getWeeklyInfoVolumn(sDate, eDate);
				
			%>
			
			<table summary="주간 주요시정별 정보량 현황" id="weekly_info" border="1" cellspacing="0" cellpadding="0">
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
				<tr style="background: #f3f3f3;">
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
			JSONObject obj = null;
			if(list2.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list2.length(); i++){
					obj = (JSONObject)list2.get(i);
					
			%>
			<tr height="25">
				<td><%=obj.get("type2")%></td>
				<td><%=su.addComma(obj.getString("neg1"))%></td>
				<td><%=su.addComma(obj.getString("cnt2"))%></td>
				<td><%=obj.getString("type21")%></td>
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
			
			<%
			
			}else if("3".equals(type)){
				JSONArray list3 = stMgr.getWeeklyInfluencerTop10(sDate, eDate);
			%>
	
			<table summary="주간 주요 영향력자 Top10" border="1" cellspacing="0" cellpadding="0">
			<caption>주간 주요 영향력자 Top10</caption>
			<colgroup>
				<col style="width:120">
				<col style="width:120">
				<col style="width:400">
				<col style="width:120">
			</colgroup>
			<thead>
				<tr style="background: #f3f3f3;">
					<th scope="col">순위</th>
					<th scope="col">출처</th>
					<th scope="col">제목</th> 
					<th scope="col">확산수량</th>
				</tr>
			</thead>
			<tbody> 
			<%
			JSONObject obj = null;
			if(list3.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list3.length(); i++){
					obj = (JSONObject)list3.get(i);
					String url = obj.getString("id_url");
					url = URLEncoder.encode(url, "UTF-8");
			%>
			<tr height="25">
				<td align="center"><%=(i+1)%></td>
				<td align="center"><%=obj.get("source")%></td>
				<td align="left"><a href='http://hub.buzzms.co.kr?url=<%=url%>' target="_blank"> <%=obj.get("id_title")%> </a></td>
				<td align="right"><%=su.addComma(obj.getString("cnt"))%></td>
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
			<%
			}else if("6".equals(type)){ 
				JSONObject obj1 = stMgr.getSentiVolume(sDate, eDate);
			%>
			<table summary="성향별 정보량 현황" border="1" cellspacing="0" cellpadding="0">
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
				<td style="background: #fff2cc;"><%=su.addComma(obj1.getString("TOTAL_CNT")) %></td>
				<td><%=Math.round(obj1.getDouble("TOTAL_CNT")/obj1.getDouble("TOTAL_CNT")*100.00 ) %>%</td>
			</tr>
			
			
			</tbody>
			</table>
						
			<%}else if("7".equals(type)){
			%>
			<p style="text-align: left;">실국별 온라인 관심도</p>
			<%	
			JSONObject obj2 = stMgr.getOniineVolume(sDate, eDate);
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
			<table summary="주간 실국/부서별 정보량 현황"  border="1" cellspacing="0" cellpadding="0">
			<%if(x==0){%>			
			<caption>○ 정보건수 : 뉴스 <%=su.addComma(obj2.getString("news"))%>건 / 개인버즈 <%=su.addComma(obj2.getString("indi"))%>건 / 공식SNS <%=su.addComma(obj2.getString("sns"))%>건 / 댓글 <%=su.addComma(obj2.getString("reply"))%>건</caption>	
			
			<%}%>
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
			<tbody id="weekly_info_tb3">
			
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
				<td><%=news[i] %></td>	
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
				<td><%=indi[i] %></td>	
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
				<td><%=sns[i] %></td>	
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
				<td><%=reply[i] %></td>	
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
				
			<%}else if("8".equals(type)){
				JSONArray list8 = stMgr.ReplyDataExcel(sDate, eDate);
				
			%>
			
			<table summary="댓글 상세정보" id="weekly_info2"  border="1" cellspacing="0" cellpadding="0">
			<caption>댓글 상세정보</caption>
			<colgroup>				
				<col>
				<col>
				<col style="width:12%">
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">제목</th>
					<th scope="col">URL</th>
					<th scope="col">댓글수</th>
					<th scope="col">실국</th>
				</tr>
			</thead>
			<tbody id="weekly_info_tb2">
			<%
			JSONObject obj = null;
			if(list8.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list8.length(); i++){
					obj = (JSONObject)list8.get(i);
					
			%>
			<tr height="25">
				<td><%=obj.get("title")%></td>
				<td><%=obj.get("url")%></td>
				<td><%=su.addComma(obj.getString("cnt"))%></td>
				<td><%=obj.get("name")%></td>
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
			}
			%>
			</tbody>
			</table>
</body>
</html>