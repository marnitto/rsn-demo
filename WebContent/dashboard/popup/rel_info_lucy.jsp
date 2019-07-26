<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.popup.CmnPopupLucyMgr"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.dashboard.popup.CmnPopupMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>

<%
	StringUtil su = new StringUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	
	CmnPopupLucyMgr cPopupLucyMgr = new CmnPopupLucyMgr();
	
	String i_sdate = pr.getString("i_sdate");
	String i_edate = pr.getString("i_edate");
	String i_and_text = pr.getString("i_and_text");							// AND 검색 키워드
	String i_exact_text = pr.getString("i_exact_text");						// 구문 검색 키워드
	String i_or_text = pr.getString("i_or_text");							// OR 검색 키워드
	String i_exclude_text = pr.getString("i_exclude_text");				// 제외단어 키워드
	String i_sourcetype = pr.getString("i_sourcetype");					// 채널
	String i_trend = pr.getString("i_trend", "");							// 감성
	String I_relation_word = pr.getString("I_relation_word", "");		// 연관키워드
	String excelParam = "";
	
	int rowCnt = pr.getInt("rowCnt", 10);
	int nowPage = pr.getInt("nowPage", 1);

	obj = cPopupLucyMgr.getPopupData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, i_trend, I_relation_word, rowCnt, nowPage, SS_M_ID);
	excelParam = "i_sdate=" + i_sdate + "&i_edate=" + i_edate + "&i_and_text=" + i_and_text + "&i_exact_text=" + i_exact_text + "&i_or_text=" + i_or_text + "&i_exclude_text=" + i_exclude_text
			+ "&i_sourcetype=" + i_sourcetype + "&i_trend=" + i_trend + "&I_relation_word=" + I_relation_word;
	
	int totalCount = obj.getInt("total");
	double totalPage = (double)totalCount / (double)rowCnt;
	
	String totalCountComma = su.digitFormat(totalCount, "###,###");
	String nowPageComma = su.digitFormat(nowPage, "###,###");
	String totalPageComma = su.digitFormat((int)Math.ceil(totalPage), "###,###");
	
	arr = obj.getJSONArray("data");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>팝업컨텐츠</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../asset/css/normalize.css">
<link rel="stylesheet" type="text/css" href="../asset/css/design.css">
</head>
<body>
	<!-- Popup -->
	<div id="popup" style="display:block">
		<h2 class="invisible">팝업 컨텐츠</h2>
		<div class="bg"></div>
		<div class="container ui_shadow">
			<section style="width:700px">
				<div class="header">
					<h3>관련정보</h3>
					<a href="#" class="close" onclick="hndl_popupClose();return false;">팝업닫기</a>
				</div>
				<div class="content">
					<div class="ui_board_list_00">
						<div class="ui_board_header f_clear">
							<div class="fl"><span><strong><span class="ui_bullet_02">-</span> 총 <%=totalCountComma%>건</strong>, <%=nowPageComma%>/<%=totalPageComma%> pages</span></div>
							<div class="fr">
								<!-- <div class="ui_searchbox"><input id="pop_search_00" type="text" placeholder="제목검색"><label for="pop_search_00"><button type="button"><span>검색</span></button></label></div> -->
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="getExcel(event, '../popup/excel/excelLucyDao.jsp', '<%=excelParam%>', '관련정보'); return false;"><span class="icon excel">Excel Download</span></button>
							</div>
						</div>
						<table>
						<caption>연관키워드 목록</caption>
						<colgroup>
						<col style="width:150px">
						<col>
						<col style="width:70px">
						<col style="width:80px">
						</colgroup>
						<thead>
						<tr>
						<th scope="col"><span>출처</span></th>
						<th scope="col"><span>제목</span></th>
						<th scope="col"><span>감성</span></th>
						<th scope="col"><span>수집시간</span></th>
						</tr>
						</thead>
						<tbody>
						<%
							int tmpRow = rowCnt - arr.length();
						
							JSONObject obj2 = null;
							for(int i=0; i<arr.length(); i++){
								obj2 = new  JSONObject();
								obj2 = (JSONObject)arr.get(i);
								
								String caffeImg = "";
								String colorClass ="";
								String senti ="";
								if(("3555").equals(obj2.getString("sitecode"))  || ("4943").equals(obj2.getString("sitecode"))){
									caffeImg = "<a href=\"javascript:portalSearch('" + obj2.getString("sitecode") + "" + "','" + obj2.getString("title").replaceAll("'", "") + "')\" class=\"ui_bullet_cafe\">Cafe</a>";
								}else{
									caffeImg= "";
								}
								if(("1").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_blue";
									senti = "긍정";
								}else if(("2").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_red";
									senti = "부정";
								}else if(("3").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_gray";
									senti = "중립";
								}
								
								String encodeUrl = java.net.URLEncoder.encode(obj2.getString("url"));
								
								//String title = (obj2.getString("title")).replaceAll("\\\"", "&#34;");								
								//title = title.replaceAll("\\\'", "&#39;");
								String title = "pr".equals(String.valueOf(obj2.get("i_sourcetype")).toLowerCase()) ? String.valueOf(obj2.get("i_content")) : String.valueOf(obj2.get("title"));
								
						%>
						<tr> 
						<td><%=obj2.getString("sitename")%></td>
						<td class="al"><%=caffeImg%><a href="http://hub.buzzms.co.kr?url=<%=encodeUrl%>" target="_blank" data-txts="<%=title.replaceAll("\"", "'")%>" onmouseenter="previewOn( this, $( '.popup_item .container' ) )" onmouseleave="previewOff( this )"><%=obj2.getString("title")%></a></td>
						<td><span class="<%=colorClass%>"><%=senti%></span></td>
						<td><%=obj2.getString("date")%>
						</td>
						</tr>
						<%
							}
							
							for(int j=0; j<tmpRow; j++){
						%>	
						<tr>
						<td></td>
						<td class="al"></td>
						<td></td>
						<td></td>
						</tr>	
						<%
						}
						%>
						</tbody>
						</table>
					</div>
					<div style="text-align: right;color: #999999;font-size: 11px;padding: 8px 0 0 0;">* 트위터 RT 미포함</div>
					<div id="cmnPopup_paging" class="paginate">
						<script type="text/javascript">
							function cmnPopupPaging(nowPage){
								var param = "i_sdate=" + "<%=i_sdate%>" + "&i_edate=" +  "<%=i_edate%>"
									+ "&i_and_text=" + "<%=i_and_text%>" + "&i_exact_text=" + "<%=i_exact_text%>" + "&i_or_text=" + "<%=i_or_text%>" + "&i_exclude_text=" + "<%=i_exclude_text%>"
									+ "&i_sourcetype=" + "<%=i_sourcetype%>" + "&i_trend=" + "<%=i_trend%>" + "&I_relation_word=" + "<%=I_relation_word%>" + "&nowPage=" + nowPage;
								popupOpen( "../popup/rel_info_lucy.jsp?" + param);
							}
							paging(10, <%=rowCnt%>, <%=obj.getString("total")%>, <%=nowPage%>, $("#cmnPopup_paging"), "cmnPopupPaging");
							
						</script>
					</div>
					<!-- Loader -->
					<div class="ui_loader v0"><span class="loader">Load</span></div>
					<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
					<!-- // Loader -->
				</div>
			</section>
		</div>
	</div>
	<!-- // Popup -->
</body>
</html>