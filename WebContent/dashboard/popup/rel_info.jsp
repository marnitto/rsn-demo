<%@page import="risk.util.StringUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.dashboard.popup.CmnPopupMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	StringUtil su = new StringUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	
	CmnPopupMgr cPopupMgr = new CmnPopupMgr();
	
	String scope = pr.getString("scope");
	String keyword = pr.getString("keyword");
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String codeList = pr.getString("codeList");
	String relationKeyword = pr.getString("relationKeyword");
	String source = pr.getString("source"); 	/*	 주요 매체 현황 매체 구분 값	 */
	String category = pr.getString("category");
	String tier = pr.getString("tier");
	String s_seq = pr.getString("s_seq");
	String excelParam = "";
	
	int nowPage = pr.getInt("nowPage", 1);
	int rowCnt = pr.getInt("rowCnt", 10);
	
	if(!"".equals(category)){  /* 주요 매체 현황(주요시정 모니터링 메뉴) 팝업 */ 
		obj = cPopupMgr.getPopupData2(nowPage, rowCnt, scope, keyword, sDate, eDate, codeList, source, category);
		excelParam = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&source=" + source + "&category=" + category;
	}else{
		obj = cPopupMgr.getPopupData(nowPage, rowCnt, scope, keyword, sDate, eDate, codeList, relationKeyword,tier,s_seq);
		excelParam = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&relationKeyword=" + relationKeyword+"&tier="+tier+"&s_seq="+s_seq;
	}
	arr = obj.getJSONArray("data");
	
	int totalCount = obj.getInt("total");
	double totalPage = (double)totalCount / (double)rowCnt;

	String totalCountComma = su.digitFormat(totalCount, "###,###");
	String nowPageComma = su.digitFormat(nowPage, "###,###");
	String totalPageComma = su.digitFormat((int)Math.ceil(totalPage), "###,###");
	
	
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
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="getExcel(event, '../popup/excel/excelDao.jsp', '<%=excelParam%>', '관련정보'); return false;"><span class="icon excel">Excel Download</span></button>
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
								if(("3555").equals(obj2.getString("sitecode"))  || ("4943").equals(obj2.getString("sitecode"))){
									caffeImg = "<a href=\"javascript:portalSearch('" + obj2.getString("sitecode") + "" + "','" + obj2.getString("title").replaceAll("'", "") + "')\" class=\"ui_bullet_cafe\">Cafe</a>";
								}else{
									caffeImg= "";
								}
								if(("긍정").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_blue";
								}else if(("부정").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_red";
								}else if(("중립").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_gray";
								}
								
								String encodeUrl = java.net.URLEncoder.encode(obj2.getString("url"));
								
								String title = (obj2.getString("title")).replaceAll("\\\"", "&#34;");
								title = title.replaceAll("\\\'", "&#39;");
						%>
						<tr> 
						<td><%=obj2.getString("source")%></td>
						<td class="al"><%=caffeImg%><a href="http://hub.buzzms.co.kr?url=<%=encodeUrl%>" target="_blank" data-txts="<%=title%>" onmouseenter="previewOn( this, $( '.popup_item .container' ) )" onmouseleave="previewOff( this )"><%=obj2.getString("title")%></a></td>
						<td><span class="<%=colorClass%>"><%=obj2.getString("senti")%></span></td>
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
					<div id="cmnPopup_paging" class="paginate">
						<script type="text/javascript">
							function cmnPopupPaging(nowPage){
								popupOpen( "../popup/rel_info.jsp?nowPage=" + nowPage + "&rowCnt=" + "<%=rowCnt%>" + "&scope=" + "<%=scope%>" + "&keyword=" + "<%=keyword%>"
										+ "&sDate=" + "<%=sDate%>" +"&eDate=" + "<%=eDate%>" + "&codeList=" + "<%=codeList%>"+ "&relationKeyword=" + "<%=relationKeyword%>"+"&tier="+"<%=tier%>"+"&s_seq="+"<%=s_seq%>");
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