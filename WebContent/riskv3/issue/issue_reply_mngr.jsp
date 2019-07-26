<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>

<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	//String ir_stime = pr.getString("ir_stime","8");
	//String ir_etime = pr.getString("ir_etime","18");
	
	String searchKey = pr.getString("searchKey", "");
	String pressYn = pr.getString("pressYn", "");
	String r_trend = pr.getString("r_trend", "");
	
	String pseqVal = pr.getString("pseqVal", "");
	
	String mode = "list";
	
	int nowpage = pr.getInt("nowpage", 1);
	int rowCnt = 100;
	int totalCnt = 0;
	int totalPage = 0;
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}
	
	IssueMgr isMgr = new IssueMgr();
	ArrayList pressList = new ArrayList();
	pressList = isMgr.getPressList();
	
	//System.out.println("sDateFrom : " + sDateFrom);
	//System.out.println("sDateTo : " + sDateTo);
	
	ArrayList replyList = new ArrayList();
	replyList = isMgr.replyAnalysisList(sDateFrom, sDateTo, nowpage, searchKey, pressYn, pseqVal, r_trend, mode);
	
	totalCnt = isMgr.getTotalIssueCnt();
	totalPage = totalCnt / rowCnt;
	if(totalCnt % rowCnt > 0){
		totalPage += 1;
	}

%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="shortcut icon" href="../asset/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/normalize.css">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design_version2.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jqcloud.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jquery-treemap.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/common.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/amcharts.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/serial.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/pie.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/radar.js"></script>
<!--[if lte IE 8]>
	<script type="text/javascript" src="../asset/js/ie_chk.js"></script>
<![endif]-->
<script type="text/javascript">
	
	//검색
	function search(page){
		if(page == undefined){
			page = 1;
		}
		var f = document.fSend;
		$("#fSend").append("<input type='hidden' name='nowpage' value='"+page+"' />");
		
		f.action = 'issue_reply_mngr.jsp';
		f.target = '';
		f.submit();
	}
	
	//전체선택
    function reverseAll( chked ) {
        var frm = document.getElementById('fSend');
        for (var i = 0; i < frm.elements.length; i++) {
            var e = frm.elements[i];
            if ( e.type == "checkbox" ) {
                e.checked = chked;
            }
        }
    }
	
	//일괄저장
	function allsave(){
		var trend = $("input[name=senti_radio]:checked").val();
		var chkedseq = '';
 		$("[name=check_tmp]").each(function(){
			if(this.checked){
				if(this.value != ''){
					if(chkedseq == ''){
						chkedseq = this.value;
					}else{
						chkedseq += ','+this.value;
					}
				}
			}
		});
 		var f = document.fSend;
		if(chkedseq == '') {
			alert('선택된 정보가 없습니다.'); return;
		}else{
			if(confirm('성향을 수정합니다.')){
				f.mode.value = 'update_all';
				f.r_seq.value = chkedseq;
				f.trend.value = trend;
				f.action="issue_reply_prc.jsp";
				f.target='processFrm';
				f.submit();
			}
		}
	}
	
	function pageClick(nowPage){
		search(nowPage);
	}
	
	function oneSave(r_seq, trend){
		var f = document.fSend;
		f.mode.value = 'update_one';
		f.r_seq.value = r_seq;
		f.trend.value = trend;
		f.action="issue_reply_prc.jsp";
		f.target='processFrm';
		f.submit();
	}
	
	function goExcelTo(){
 		var f = document.fSend;
		f.action = 'issue_reply_excel.jsp';
		f.target = 'processFrm';
		f.submit();
		f.action = 'issue_reply_mngr.jsp';
		f.target = 'processFrm';
 	}
	
</script>

</head>
<body>
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="mode" value="">
<input type="hidden" name="trend" value="">
<input type="hidden" name="r_seq" value="">
	<div class="page_wrap">
		<div id="container" class="p_press">
			<h1 class="invisible">금융감독원</h1>
			<div id="header">
				<h2><img src="../../images/issue/title_01.gif" alt="댓글 분류"></h2>
				<span class="navigator">
					<span class="home">HOME</span>
					<span>이슈관리</span>
					<strong>댓글분류</strong>
				</span>
			</div>
			<div id="content">

				<!-- 검색조건 -->
				<div class="ui_searchbox_00" style="height: 110px;">
					<div class="c_wrap">
						<div class="ui_row_00">
							<span class="title"><span class="icon">-</span>검색단어</span>
							<span class="txts">
								<input id="searchKey" name="searchKey" type="text" class="ui_input_02" style="width:160px" value="<%=searchKey%>"><label for="input_search_char" class="invisible">검색어 입력</label>
							</span>
							<span class="title m_l_20"><span class="icon">-</span>검색기간</span>
							<span class="txts">
								<input id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>" type="text" class="ui_datepicker_input input_date_first" style="width:70px" readonly><label for="sDateFrom">날짜입력</label
								><%-- <select id="ir_stime" name="ir_stime" class="ui_select_04" style="margin-right:0">
								<% 
								String sBasics  = ir_stime.equals("") ? "8" :  ir_stime;
			                	String eBasics  = ir_etime.equals("") ? "18" :  ir_etime;
								for(int i=0; i< 24; i++){
								
								%>
								<option value="<%=i%>" <%if(sBasics.equals(i+"")){out.print("selected");} %>><%=i%>시</option>
								<%} %>
								</select><label for="ir_stime"></label
								> --%> ~ <input id="sDateTo" name="sDateTo" value="<%=sDateTo%>" type="text" class="ui_datepicker_input input_date_last" style="width:70px" readonly><label for="sDateTo">날짜입력</label
								><%-- <select id="ir_etime" name="ir_etime" class="ui_select_04">
								<% 
								for(int i=0; i< 24; i++){
								
								%>
								<option value="<%=i%>" <%if(eBasics.equals(i+"")){out.print("selected");} %>><%=i%>시</option>
								<% 
								}	
								%>
								</select><label for="ir_etime"></label
								> --%><button class="ui_btn_02" onclick="search();return false;"><span class="icon search_01">-</span>검색</button>
							</span>
						</div>
					</div>
					<div class="c_sort" style="top: 30px;">
						<div class="ui_row_00">
							<span class="title"><span class="icon">-</span>성향</span>
							<span class="txts">
								<select name="r_trend" id="r_trend" class="ui_select_00" style="height:20px">
								<option selected value="">선택하세요</option>
								<option <%if(r_trend.equals("1"))out.print("selected");%> value="1">긍정</option>
								<option <%if(r_trend.equals("2"))out.print("selected");%> value="2">부정</option>
								<option <%if(r_trend.equals("3"))out.print("selected");%> value="3">중립</option>
								</select><label for="r_trend" class="invisible">보도자료 유무 선택</label>
							</span>						
						</div>
						<div class="ui_row_00">
							<span class="title"><span class="icon">-</span>보도자료 유무</span>
							<span class="txts" style="min-width:auto">
								<select name="pressYn" id="pressYn" class="ui_select_00" style="height:20px">
								<option selected value="">선택하세요</option>
								<option <%if(pressYn.equals("Y"))out.print("selected");%>>Y</option>
								<option <%if(pressYn.equals("N"))out.print("selected");%>>N</option>
								</select><label for="pressYn" class="invisible">보도자료 유무 선택</label>
							</span>
							<span class="title"><span class="icon">-</span>보도자료명</span>
							<span class="txts" style="padding:0 20px 0 0">
								<select name="pseqVal" id="pseqVal" class="ui_select_00" style="width:400px;height:20px">
								<option selected value="">선택하세요</option>
								<%
								Map pMap = null;
								for(int p=0; p<pressList.size(); p++){
									pMap =  new HashMap();
									pMap = (HashMap)pressList.get(p);
									String slted = "";
									if(pseqVal.equals(pMap.get("P_SEQ").toString())){
										slted = "selected";
									}
									%>
									<option value="<%=pMap.get("P_SEQ")%>" <%=slted%>> <%=pMap.get("P_TITLE")%> </option>
									<%
								}
								%>
								</select><label for="pseqVal" class="invisible">보도자료 유무 선택</label>
							</span>
						</div>
					</div>
				</div>
				<!-- // 검색조건 -->

				<div class="ui_board">
					<div class="board_header">
						<div class="floatbox_left">
							<!-- <button type="button" class="brd_btn"><img src="../../images/issue/btn_allselect.gif"></button> -->
							<button type="button" class="brd_btn"><img src="../../images/issue/btn_allselect.gif" onclick="reverseAll(!document.getElementById('fSend').checkall.checked);"></button>
							<!-- <button type="button" class="brd_btn"><img src="../../images/issue/btn_exelsave.gif" onclick="goExcelTo();"></button> -->
							<button type="button" class="brd_btn" onclick="goExcelTo();"><img src="../../images/issue/btn_exelsave.gif"></button>
						</div>
						<div class="floatbox_right">
							<div class="dcp_c"><input type="radio" id="senti_radio_00" name="senti_radio" class="postive" value="1"><label for="senti_radio_00"><span>긍정</span></label></div>
							<div class="dcp_c"><input type="radio" id="senti_radio_01" name="senti_radio" class="neutral" value="3"><label for="senti_radio_01"><span>중립</span></label></div>
							<div class="dcp_c"><input type="radio" id="senti_radio_02" name="senti_radio" class="negative" value="2" checked><label for="senti_radio_02"><span>부정</span></label></div>
							<button type="button" class="brd_btn"><img src="../../images/issue/btn_allsave.gif" onclick="allsave();"></button>
						</div>
					</div>
					<div class="board_wrap">
						<table>
							<colgroup>
								<col style="width:40px">
								<col style="width:80px">
								<col style="width:150px">
								<col style="width:150px">
								<col style="width:100px">
								<col>
								<col style="width:60px">
								<col style="width:80px">
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" name="checkall" onclick="reverseAll(this.checked);"></th>
									<th>일자</th>
									<th>보도자료명</th>
									<th>기사명</th>
									<th>출처</th>
									<th>댓글내용</th>
									<th>자동성향</th>
									<th>수동성향</th>
								</tr>
							</thead>
							<tbody>
								<!-- <tr> -->
									<%
									Map rMap = null;
									for(int r=0; r<replyList.size(); r++){
										rMap =  new HashMap();
										rMap = (HashMap)replyList.get(r);
										String r_trend_tmp = rMap.get("R_TREND").toString();
										if(r_trend_tmp.equals("1")){
											r_trend_tmp = "긍정";
										}else if(r_trend_tmp.equals("2")){
											r_trend_tmp = "부정";
										}else{
											r_trend_tmp = "중립";
										}
									%><tr>
										<td class="ac"><input type="checkbox" name="check_tmp" value="<%=rMap.get("R_SEQ")%>"></td>
										<td><%=rMap.get("R_SDATE")%></td>
										<td class="al" title="<%=rMap.get("P_TITLE")%>"><%=rMap.get("P_TITLE")%></td>
										<td class="al" title="<%=rMap.get("R_TITLE")%>"><%=rMap.get("R_TITLE")%></td>
										<td><%=rMap.get("P_SITENAME") %></td>
										<td class="al" title="<%=rMap.get("R_CONTENT")%>"><%=rMap.get("R_CONTENT")%></td>
										<td><%=r_trend_tmp%></td>
										<td>
											<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_00_<%=r %>" name="b_senti_radio_00_<%=r %>" class="only postive" onclick="javascript:oneSave(<%=rMap.get("R_SEQ")%>, 1)" <%if(rMap.get("M_TREND").toString().equals("1")) out.print("checked");%>><label for="b_senti_radio_00_00_<%=r %>"></label></div>
											<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_01_<%=r %>" name="b_senti_radio_00_<%=r %>" class="only neutral" onclick="javascript:oneSave(<%=rMap.get("R_SEQ")%>, 3)" <%if(rMap.get("M_TREND").toString().equals("3")) out.print("checked");%>><label for="b_senti_radio_00_01_<%=r %>"></label></div>
											<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_02_<%=r %>" name="b_senti_radio_00_<%=r %>" class="only negative" onclick="javascript:oneSave(<%=rMap.get("R_SEQ")%>, 2)" <%if(rMap.get("M_TREND").toString().equals("2")) out.print("checked");%>><label for="b_senti_radio_00_02_<%=r %>"></label></div>
										</td>
										</tr>
									<%
									}
									%>
									<!-- 
									<td class="ac"><input type="checkbox" name="check_tmp"></td>
									<td>2016-09-20</td>
									<td class="al" title="IT내부통제 강화를 위한 금융권 워크샵 개최">IT내부통제 강화를 위한 금융권 워크샵 개최</td>
									<td class="al" title="'한진해운 회생' 말하던 법원, 파산을 말하기 시작">'한진해운 회생' 말하던 법원, 파산을 말하기 시작</td>
									<td>조선일보</td>
									<td class="al" title="한진해운 파산을 시켜서라도 회장 임원진이 몰래 빼돌린 자금 회수 해라.">한진해운 파산을 시켜서라도 회장 임원진이 몰래 빼돌린 자금 회수 해라.</td>
									<td>부정</td>
									<td>
										<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_00" name="b_senti_radio_00" class="only postive"><label for="b_senti_radio_00_00"></label></div>
										<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_01" name="b_senti_radio_00" class="only neutral"><label for="b_senti_radio_00_01"></label></div>
										<div class="dcp_c v2"><input type="radio" id="b_senti_radio_00_02" name="b_senti_radio_00" class="only negative"><label for="b_senti_radio_00_02"></label></div>
									</td>
									 -->
								<!-- </tr> -->
							</tbody>
						</table>
					</div>
					<%
					/*** 페이징 처리 ***/
					String pageHtml = "<div class=\"paging\">";
					pageHtml += "<div class=\"page_wrap\">";
				    
					int lastPage = totalPage;
					String strNowPage = Integer.toString(nowpage - 1); 
					String display = "";
					
					int ptPage = Integer.parseInt(strNowPage.substring(strNowPage.length() - 1 , strNowPage.length()));
				
					int startPage = (nowpage - 1) - ptPage;         // 0 : 10 : 20 ...       
					int endPage   = (nowpage - 1) + (10 - ptPage);  //10 : 20 : 30 ...
					if(endPage > lastPage) endPage = lastPage;
					
					//왼쪽화살표
					String href = "";
					if(startPage <= 0){
						display = "disabled";
						//href = "#";
					}else{
						href = "href=\"javascript:pageClick('"+startPage+"')\"";
					}
					pageHtml += "<a "+href+" class=\"page_prev "+display+"\"><img src=http://fss.realsn.co.kr//images/common/btn_paging_prev.gif border=0 align=absmiddle></a>";
					
					if(startPage < endPage){
						//숫자
						do{
							startPage++;
							if(startPage == nowpage){
								pageHtml += "<a class=\"active\">"+ startPage +"</a>"; //강조
								
							}else{
								pageHtml += "<a href=\"javascript:pageClick('"+startPage+"')\">"+startPage+"</a>"; //기본
								
							}
							
						}while(startPage != endPage);
					}
					
					//오른쪽화살표
					display = "";
					href = "";
					if(endPage >= lastPage){
						display = "disabled";
						//href = "#";
					}else{
						href = "href=\"javascript:pageClick('"+(endPage + 1)+"')\"";
					}
					pageHtml += "<a "+href+" class=\"page_next "+display+"\"><img src=http://fss.realsn.co.kr//images/common/btn_paging_next.gif border=0 align=absmiddle></a>";
					
					pageHtml += "</div>";
					pageHtml += "</div>";
					out.println(pageHtml);
					%>
					<!-- <div class="paging">
						<div class="page_wrap">
							<a href="#"><img src="http://fss.realsn.co.kr//images/common/btn_paging_prev.gif" border="0" align="absmiddle"></a>
							<a href="#" class="page_item active">1</a>
							<a href="#" class="page_item">2</a>
							<a href="#" class="page_item">3</a>
							<a href="#" class="page_item">4</a>
							<a href="#" class="page_item">5</a>
							<a href="#" class="page_item">6</a>
							<a href="#" class="page_item">7</a>
							<a href="#" class="page_item">8</a>
							<a href="#" class="page_item">9</a>
							<a href="#" class="page_item">10</a>
							<a href="#"><img src="http://fss.realsn.co.kr//images/common/btn_paging_next.gif" border="0" align="absmiddle"></a>
						</div>
					</div> -->
				</div>
			</div>
		</div>
	</div>
</form>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
</body>
</html>