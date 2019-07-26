<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,java.net.URLEncoder
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil"
%>
<%
ParseRequest pr = new ParseRequest(request);
DateUtil du = new DateUtil();
String currentDate = du.getCurrentDate("yyyy-MM-dd");
String yesterDay = du.addDay(currentDate, -1);
String preWeekDay = du.addDay(currentDate, -7);
StringUtil su = new StringUtil();
String sDate = pr.getString("sDate", preWeekDay);
String eDate = pr.getString("eDate", currentDate);
String searchKey = pr.getString("searchKey" , "");

%>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->
<body>
	<script type="text/javascript" src="summary.js"></script>
	<script type="text/javascript">
		gnbIDX = "0100";
		setPageTitle( "대구광역시 - Summary" );
		
		$(function(){
			// Date picker 설정
			// 달력 START
			if( $( ".ui_datepicker_input" ).length > 0 ) {
				$( ".ui_datepicker_input" ).datepicker({
					dateFormat: "yy-mm-dd",
					monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
					dayNamesMin: ['일','월','화','수','목','금','토']
				});
				$( ".input_date_first.ui_datepicker_input" ).datepicker().change(function(){
					var tempDate = $( this ).datepicker("getDate");
					var tempDate2 = $( this ).datepicker("getDate");
					tempDate2.setDate( tempDate2.getDate() + 30 );
					$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
					$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
					$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "minDate", $( this ).val());
					$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "maxDate", tempDate2);
				});
				$( ".input_date_last.ui_datepicker_input" ).datepicker().change(function(){
					var tempDate = $( this ).datepicker("getDate");
					$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
					$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
				});
			}
			// 달력 END
			
		});

		// 검색
		function hndl_search(){
			var sDateChk = $("#searchs_dp_start").val();
			var eDateChk = $("#searchs_dp_end").val();
			
			if(getDateDiff(sDateChk, eDateChk)>365){
				alert("31일까지 검색 가능 합니다.");
			}else{
				$(".ui_box_00").find(".v0").fadeIn(80);
				scope = $("#search_sel_01").val();
				keyword = $("#ui_search_input").val();
				sDate = $("#searchs_dp_start").val();
				eDate = $("#searchs_dp_end").val();
				tier = "";
				
				$('[name="tier"]').each(function() {
					if($(this).is(":checked")==true){
						if(tier == ""){
							tier = $(this).val();
						}else{
							tier += ","+$(this).val();
						}
					}
	 			});
				
				section01();	//온라인 동향
				
				setTimeout(function(){
					section02();    //주요시정 채널 별 성향	
				},400);
				setTimeout(function(){
					section04(1);    //포탈 TOP 노출 현황	
				},600);
				
				$("#portal_tbody tr:first-child").addClass("active");
				summary.portalSearch(1);	//포탈댓글 형황
				
				section06("1");	// 알리미앱글 목록	
			}
		}
		
		$(document).ready(function(){
			hndl_search();
			
			
			$("#ui_search_input").keypress(function(e){
		        if (e.which == 13) {
		        	hndl_search();
		        }
			});
			
			$("[name=s_c6_1_radio]").click(function(){
				$(".ui_box_00").eq(3).find(".v0").fadeIn(80);
				section04(1);
			});
			
			$("[name=s_c6_2_radio]").click(function(){
				summary.portalSearch(1);
			});
			
		});

		// Spart Chart Default Option
		var sparkChartOption = {
			"type": "serial",
			"categoryField": "category",
			"autoMargins": false,
			"marginBottom": 0,
			"marginLeft": 0,
			"marginRight": 0,
			"marginTop": 0,
			"colors": [
				"#404142"
			],
			"categoryAxis": {
				"gridPosition": "start",
				"startOnAxis": true,
				"axisThickness": 0,
				"axisColor": "#FFFFFF",
				"gridAlpha": 0,
				"gridThickness": 0,
				"labelsEnabled": false,
				"tickLength": 0
			},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": "[[title]] of [[category]]:[[value]]",
					"id": "AmGraph-1",
					"title": "graph 1",
					"valueField": "column-1"
				}
			],
			"guides": [],
			"valueAxes": [
				{
					"id": "ValueAxis-1",
					"axisColor": "#FFFFFF",
					"axisThickness": 0,
					"gridAlpha": 0,
					"gridThickness": 0,
					"labelsEnabled": false,
					"tickLength": -1,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {},
			"titles": []
		};
	</script>

	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->

	<!-- Include HEADER -->
	<jsp:include page="../inc/inc_header.jsp" flush="false" />
	<!-- // Include HEADER -->

	<div class="page_wrap" id="s_top">
		<!-- Content Container -->
		<div id="container" class="p_summary">
			<div id="content">

				<h2 class="invisible">여론동향</h2>

				<!-- Search -->
				<form action="#" class="section_hidden" >
					<div class="f_clear" >
						<section class="ui_searchs fr" >
							<h3 class="invisible">검색조건</h3>
							<div class="ui_search_type">
								<div class="dcp">
									<select id="search_sel_01" class="ui_hidden">
										<option value="1">제목</option>
										<option value="2">내용</option>
										<option value="3" selected>제목 + 내용</option>
									</select><label for="search_sel_01" class="select_00"></label>
								</div>
								<input type="text" id="ui_search_input" class="ui_input_01" style="width:205px"><label for="ui_search_input" class="invisible">검색어 입력</label>
							</div>
							<div class="ui_search_date">
								<div class="ui_datepicker_input_range">
									<input id="searchs_date_range" type="text" class="date_result" readonly value=""><label for="searchs_date_range" class="invisible">검색기간</label>		<!-- 선택시 input에 'active'클래스 추가 -->
									<div class="calendars">
										<span class="arrow"></span>
										<div class="calendars_container">
											<div id="searchs_dp_start" class="dp"></div>
											<div id="searchs_dp_end" class="dp"></div>
											<div class="btns">
												<button type="button" class="btn_ok"><span>선택완료</span></button>
											</div>
										</div>
									</div>
								</div>
								<div class="ui_date_group">
									<div class="group"><input id="group_01" type="radio" name="date_group" value="7"><label for="group_01">7월</label></div>
									<div class="group"><input id="group_02" type="radio" name="date_group" value="8"><label for="group_02">8월</label></div>
									<div class="group"><input id="group_03" type="radio" name="date_group" value="29"><label for="group_03">30일</label></div>
									<div class="group"><input id="group_04" type="radio" name="date_group" value="6"><label for="group_04">7일</label></div>
									<div class="group"><input id="group_05" type="radio" name="date_group" value="1"><label for="group_05">1일</label></div>
								</div>
							</div>
							<div class="ui_icon_box line">
								<div class="ui_icon_group">
									<div class="group"><input type="checkbox" name="tier" value="1" id="tier01"><label for="tier01">Tier1</label></div>
									<div class="group"><input type="checkbox" name="tier" value="2" id="tier02"><label for="tier02">Tier2</label></div>
									<div class="group"><input type="checkbox" name="tier" value="3" id="tier03"><label for="tier03">Tier3</label></div>
									<div class="group"><input type="checkbox" name="tier" value="4" id="tier04"><label for="tier04">Tier4</label></div>
									<div class="group"><input type="checkbox" name="tier" value="5" id="tier05"><label for="tier05">Tier5</label></div>
								</div>
							</div>
							 <script type="text/javascript">
								// 날짜 설정
								var sel_sDate = getBeforeDay(getToday(), 6);		// 시작 날짜 설정
								var sel_eDate = getToday();							// 종료 날짜 설정
							</script>
							<button type="button" class="ui_btn_01" onclick="hndl_search()"><strong>검색</strong></button>
						</section>
					</div>
				</form>
				<!-- // Search -->

				<!-- 온라인 동향 -->
				<div class="ui_boxs_00">
					<section id="s_c4_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>온라인 동향</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('1', '온라인 동향'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c4_01" class="ui_chart"></div>
							<script type="text/javascript">
									var chart1;
									(function(){
										var chartOption = {
												"type": "serial",
												"categoryField": "category",
												"columnWidth": 0.4,
												"marginBottom": 0,
												"marginTop": 10,
												"colors": [
													"#0083cc",
													"#db9e4e"
												],
												"addClassNames": true,
												"pathToImages": "../asset/img/amchart/",
												"percentPrecision": 0,
												"categoryAxis": {
													"gridPosition": "start",
													"twoLineMode": true,
													"axisColor": "#D8D8D8",
													"color": "#444444",
													"gridAlpha": 0,
													"tickLength": 0
												},
												"chartCursor": {
													"enabled": true,
													"cursorColor": "#000000"
												},
												"chartScrollbar": {
													"enabled": true,
													"dragIconHeight": 25,
													"dragIconWidth": 25,
													"offset": 20,
													"scrollbarHeight": 8
												},
												"trendLines": [],
												"graphs": [
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 8,
														"dashLength": 0,
														"id": "AmGraph-1",
														"lineThickness": 3,
														"title": "대구광역시",
														"valueField": "column-2"
													} /* ,
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 8,
														"dashLength": 0,
														"id": "AmGraph-2",
														"lineThickness": 3,
														"title": "대구시장",
														"valueField": "column-3"
													}  */
												],
												"guides": [],
												"valueAxes": [
													{
														"id": "ValueAxis-1",
														"axisThickness": 0,
														"color": "#c0c0c0",
														"dashLength": 2,
														"gridAlpha": 1,
														"gridColor": "#D8D8D8",
														"tickLength": 0,
														"title": ""
													}
												],
												"allLabels": [],
												"balloon": {
													"color": "#FFFFFF",
													"fadeOutDuration": 0,
													"fillAlpha": 0.67,
													"fillColor": "#000000",
													"fontSize": 11,
													"horizontalPadding": 10,
													"pointerWidth": 5,
													"shadowAlpha": 0.3
												},
												"legend": {
													"enabled": true,
													"align": "center",
													"autoMargins": false,
													"color": "#444444",
													"equalWidths": false,
													"marginLeft": 0,
													"marginRight": 0,
													"markerLabelGap": 6,
													"markerSize": 11,
													"markerType": "circle",
													"spacing": 15,
													"useGraphSettings": true,
													"valueText": "",
													"verticalGap": 2
												},
												"titles": [],
												"dataProvider": []
											};
										chart1 = AmCharts.makeChart("chart_c4_01", chartOption);
										chart1.addListener("clickGraphItem", function(event){
											var fulldate = event.item.dataContext.date;
											var field = event.item.graph.valueField;
											
											//1@1 (분류체계-구분-대구시청)
											var codeList = "";
											
											if(field=="column-2"){
												codeList = "1@1";
											}
											var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + fulldate + "&eDate=" + fulldate + "&codeList=" + codeList+"&tier="+tier;
											popupOpen( "../popup/rel_info.jsp?" + param);
										});
									})();
								</script>
						</div>
						<div style="width: 100%;text-align: right;margin-top: 15px;">
							<span class="fs11"> * 설정된 키워드로 수집된 데이터 중 유효데이터의 일자별 추이 그래프 </span>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
						
					</section>
				</div>
				<!-- // 온라인 동향 -->

				<!-- 주요 시정 채널 별 성향 & 서울시장 채널 별 성향 -->
				<div class="ui_boxs_00" class="section_hidden">
					<!-- 주요 시정 채널 별 성향 -->
					<section id="s_c5_1" class="ui_box_00 ui_shadow_00" style="width: 100%">
						<h3><span class="ui_bullet_00">●</span>채널 별 성향</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2', '채널 별 성향'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c5_01" class="ui_chart"></div>
							<script type="text/javascript">
										var chart2;				
										(function(){
											var chartOption = {
												"type": "pie",
												"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
												"innerRadius": "70%",
												"labelRadius": -15,
												"labelText": "[[percents]]%",
												"pullOutRadius": "0%",
												"radius": "47%",
												"startRadius": "0%",
												"colors": [
													"#5ba1e0",
													"#ea7070",
													"#888888"
												],
												"marginBottom": 0,
												"marginTop": 0,
												"pullOutDuration": 0,
												"startDuration": 0,
												"titleField": "category",
												"valueField": "column-1",
												"color": "#FFFFFF",
												"percentPrecision": 0,
												"allLabels": [
													{
														"align": "center",
														"bold": true,
														"color": "#5ba1e0",
														"id": "Label-1",
														"size": 17,
														"text": "VOLUME",
														"y": 60
													},
													{
														"align": "center",
														"bold": true,
														"color": "#444444",
														"id": "Label-2",
														"size": 30,
														"text": "",
														"y": 90
													}
												],
												"balloon": {
													"color": "#FFFFFF",
													"fadeOutDuration": 0,
													"fillAlpha": 0.67,
													"fillColor": "#000000",
													"fontSize": 11,
													"horizontalPadding": 20,
													"pointerWidth": 5,
													"shadowAlpha": 0.3,
													"verticalPadding": 6
												},
												"titles": [],
												"dataProvider": []
											};
											chart2 = AmCharts.makeChart("chart_c5_01", chartOption);
											chart2.addListener("clickSlice", function(event){
												var senti = event.dataItem.dataContext.category;
												var codeList = "";
												
												if(senti=="긍정"){
													codeList += "9@1";
												}else if(senti=="부정"){
													codeList += "9@2";
												}else if(senti=="중립"){
													codeList += "9@3";
												}
												
												var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList+"&tier="+tier;
												popupOpen( "../popup/rel_info.jsp?" + param);
											});
										})();
									</script>
							<div id="chart_c5_02" class="ui_chart"></div>
							<script type="text/javascript">
									var chart3;
									(function(){
										var chartOption = {
												"type": "serial",
												"categoryField": "category",
												"columnWidth": 0.4,
												"marginBottom": 0,
												"marginTop": 10,
												"colors": [
													"#5ba1e0",
													"#ea7070",
													"#888888"
												],
												"addClassNames": true,
												"pathToImages": "../asset/img/amchart/",
												"percentPrecision": 0,
												"categoryAxis": {
													"labelRotation": 45,
													"minHorizontalGap": 0,
													/* "autoWrap": true, */
													"gridPosition": "start",
													"twoLineMode": true,
													"axisColor": "#D8D8D8",
													"color": "#444444",
													"gridAlpha": 0,
													"tickLength": 0,
													"labelFunction":function(data){
														if(data.length>4){
															return data.substring(0,4) + "..";	
														}else{
															return data;
														}
													}
												},
												"chartCursor": {
													"enabled": true,
													"cursorColor": "#000000"
												},
												"trendLines": [],
												"graphs": [
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bulletBorderThickness": 0,
														"bulletSize": 5,
														"fillAlphas": 1,
														"id": "AmGraph-1",
														"markerType": "circle",
														"title": "긍정",
														"type": "column",
														"valueField": "column-2"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bulletBorderThickness": 0,
														"bulletSize": 5,
														"fillAlphas": 1,
														"id": "AmGraph-2",
														"markerType": "circle",
														"title": "부정",
														"type": "column",
														"valueField": "column-3"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bulletBorderThickness": 0,
														"bulletSize": 5,
														"fillAlphas": 1,
														"id": "AmGraph-3",
														"markerType": "circle",
														"title": "중립",
														"type": "column",
														"valueField": "column-4"
													}
												],
												"guides": [],
												"valueAxes": [
													{
														"id": "ValueAxis-1",
														"stackType": "regular",
														"axisThickness": 0,
														"color": "#c0c0c0",
														"dashLength": 2,
														"gridAlpha": 1,
														"gridColor": "#D8D8D8",
														"tickLength": 0,
														"title": ""
													}
												],
												"allLabels": [],
												"balloon": {
													"color": "#FFFFFF",
													"fadeOutDuration": 0,
													"fillAlpha": 0.67,
													"fillColor": "#000000",
													"fontSize": 11,
													"horizontalPadding": 10,
													"pointerWidth": 5,
													"shadowAlpha": 0.3
												},
												"legend": {
													"enabled": true,
													"align": "center",
													"autoMargins": false,
													"color": "#444444",
													"equalWidths": false,
													"marginLeft": 0,
													"marginRight": 0,
													"markerLabelGap": 6,
													"markerSize": 11,
													"markerType": "circle",
													"spacing": 15,
													"useGraphSettings": true,
													"valueText": "",
													"verticalGap": 2
												},
												"titles": [],
												"dataProvider": []
											};
										chart3 = AmCharts.makeChart("chart_c5_02", chartOption);
										chart3.addListener("clickGraphItem", function(event){
											var iccode = event.item.dataContext.iccode;
											var field = event.item.graph.valueField;
										
											var codeList = "6@"+iccode;
											
											if(field=="column-2"){
												codeList += "/9@1";
											}else if(field=="column-3"){
												codeList += "/9@2";
											}else if(field=="column-4"){
												codeList += "/9@3";
											}
											
											var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList+"&tier="+tier;
											popupOpen( "../popup/rel_info.jsp?" + param);
										});
									})();
								</script>
						</div>
						<div style="width: 100%;text-align: right;margin-top: 15px;">
							<span class="fs11"> * 설정된 키워드로 수집된 데이터 중 유효데이터의 채널별 정보량 및 성향 </span>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
					<!-- // 주요 시정 채널 별 성향 -->
				</div>
				<!-- // 주요 시정 채널 별 성향 & 서울시장 채널 별 성향 -->

				<!-- 포탈 TOP 노출 현황 -->
				<div class="ui_boxs_00" class="section_hidden">
					<section id="s_c6_1" class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>포탈 TOP 노출 현황</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4', '포탈 TOP 노출 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<!-- <ul class="ui_tab_00">
								<li><input id="s_c6_1_radio_00" type="radio" name="s_c6_1_radio" value="0" checked><label for="s_c6_1_radio_00"><span>전체</span></label></li>
								<li><input id="s_c6_1_radio_01" type="radio" name="s_c6_1_radio" value="1" ><label for="s_c6_1_radio_01"><span>대구광역시</span></label></li>
								<li><input id="s_c6_1_radio_02" type="radio" name="s_c6_1_radio" value="2" ><label for="s_c6_1_radio_02"><span>대구시장</span></label></li>
							</ul> -->
							<div class="ui_board_list_00">
								<table id="section04_contents">
								<caption>관련정보 목록</caption>
								<colgroup>
								<col style="width:130px">
								<col style="width:130px">
								<col>
								<col style="width:110px">
								</colgroup>
								<thead>
								<tr>
								<th scope="col"><span>포탈구분</span></th>
								<th scope="col"><span>영역</span></th>
								<th scope="col"><span>제목</span></th>
								<th scope="col"><span>최초노출일시</span></th>
								</tr>
								</thead>
								<tbody>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4">데이터가 없습니다</td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<!-- <tr>
								<td><strong>네이버</strong></td>
								<td><strong>뉴스</strong></td>
								<td class="title"><a href="http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=101&oid=015&aid=0003828416" target="_blank">광주광역시 주민참여형 '우리동네 살리기' 현장 가보니</a></td>
								<td>2017-09-25 17:41</td>
								</tr>
								
								<tr>
								<td><strong>네이버</strong></td>
								<td><strong>뉴스</strong></td>
								<td class="title"><a href="http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=102&oid=079&aid=0003016303" target="_blank">'광주 알바 청소년' 10 명 중 3명 최저임금 못 받아</a></td>
								<td>2017-09-25 17:35</td>
								</tr>
								
								<tr>
								<td><strong>네이버</strong></td>
								<td><strong>뉴스</strong></td>
								<td class="title"><a href="http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=102&oid=079&aid=0003016293" target="_blank">윤장현 광주시장, 5·18 세계에 알린 기여 공로패 받아</a></td>
								<td>2017-09-25 17:31</td>
								</tr>
								
								<tr>
								<td><strong>네이버</strong></td>
								<td><strong>뉴스</strong></td>
								<td class="title"><a href="http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=102&oid=001&aid=0009569932" target="_blank">"써준 원고 읽는 시정질문…" 광주시 사무관 SNS글 파문</a></td>
								<td>2017-09-25 11:32</td>
								</tr>
								
								<tr>
								<td><strong>네이버</strong></td>
								<td><strong>뉴스</strong></td>
								<td class="title"><a href="http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=100&oid=018&aid=0003931495" target="_blank">수도요금 서울 ㎥당 569원 불과…울산 865원·부산 728원</a></td>
								<td>2017-09-25 11:16</td>
								</tr> -->
								
								<!-- <tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr> --> 
								</tbody>
								</table>
							</div>
						</div>
						<div id="top_paging" class="paginate">
							<a href="#" class="page_prev disabled">이전페이지</a>
							<a href="#" class="active" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">1</a>
							<!-- <a href="#" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">2</a>
							<a href="#">3</a>
							<a href="#">4</a>
							<a href="#">5</a>
							<a href="#">6</a>
							<a href="#">7</a>
							<a href="#">8</a>
							<a href="#">9</a>
							<a href="#">10</a> -->
							<a href="#" class="page_next">다음페이지</a>
						</div>
						<div style="width: 100%;text-align: right;margin-top: 15px;">
							<span class="fs11"> * 제목에 ‘대구‘ 키워드가 있는 기사 중 포탈메인화면에 노출된 기사리스트 </span>
						</div>
						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 포탈 TOP 노출 현황 -->

				<!-- 포탈 댓글 현황/분석 -->
				<div class="ui_boxs_00">
					<div class="ui_box_00 ui_shadow_00 f_clear">
						<div class="content">
							<!-- 포탈 댓글 현황 -->
							<section id="s_c7_1">
								<h3 class="no_bd"><span class="ui_bullet_00">●</span>포탈 댓글 현황</h3>
								<div class="title_rc v2">
								<strong>날짜</strong>
								<input type="text" id="c_15_dp_00" class="ui_datepicker_input" value="<%=eDate%>"><label for="c_15_dp_00" class="invisible">날짜선택</label>
								<button type="button" class="ui_btn_02 ui_shadow_01" title="검색" onclick="summary.portalSearch(1);"><span class="icon search">검색</span></button>
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick='excelDownload("5_1", "포탈 댓글 현황"); return false;'><span class="icon excel">Excel Download</span></button>
<!-- 									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="getExcel( event, &quot;../temp/temp_excel.jsp&quot;, &quot;{\&quot;test\&quot;:\&quot;test\&quot;}&quot; );"><span class="icon excel">Excel Download</span></button> -->
								</div>
								<div class="content">
									<!-- <ul class="ui_tab_00">
										<li><input id="s_c6_1_radio_03" type="radio" name="s_c6_2_radio" value="0"  checked><label for="s_c6_1_radio_03"><span>전체</span></label></li>
										<li><input id="s_c6_1_radio_04" type="radio" name="s_c6_2_radio" value="1" ><label for="s_c6_1_radio_04"><span>대구광역시</span></label></li>
										<li><input id="s_c6_1_radio_05" type="radio" name="s_c6_2_radio" value="2" ><label for="s_c6_1_radio_05"><span>대구시장</span></label></li>
									</ul> -->
									<div class="ui_board_list_00">
										<table>
										<caption>관련정보 목록</caption>
										<colgroup>
										<col style="width:150px">
										<col style="width:170px">
										<col>
										<col style="width:110px">
										</colgroup>
										<thead>
										<tr>
										<th scope="col"><span>포탈구분</span></th>
										<th scope="col"><span>댓글</span></th>
										<th scope="col"><span>제목</span></th>
										<th scope="col"><span>일자</span></th>
										</tr>
										</thead>
										<tbody id="portal_tbody" >
										<!-- 데이터 없는 경우
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4">데이터가 없습니다</td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										-->
										<!-- <tr class="active">
										<td><span>nKBS 뉴스</span></td>
										<td><a href="#">636</a> (<a href="#" class="ui_fc_blue" title="긍정 : 999,999,999">999K</a>/<a href="#" class="ui_fc_red" title="부정 : 999,999,999">999K</a>/<a href="#" class="ui_fc_green" title="중립 : 999,999,999">999K</a>)</td>
										<td class="title"><a href="#" target="_blank">황사,미세먼지 이길 '디톡스식품' 인기리 판매</a></td>
										<td>12/20</td>
										</tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr>
										<tr><td colspan="4"></td></tr> -->
										</tbody>
										</table>
									</div>

									<div id="section04_paging" class="paginate">
									<!-- 	<a href="#" class="page_prev disabled">이전페이지</a>		버튼 활성화시 disabled 클래스 제거
										<a href="#" class="active" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">1</a>
										<a href="#" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">2</a>
										<a href="#">3</a>
										<a href="#">4</a>
										<a href="#">5</a>
										<a href="#">6</a>
										<a href="#">7</a>
										<a href="#">8</a>
										<a href="#">9</a>
										<a href="#">10</a>
										<a href="#" class="page_next">다음페이지</a> -->
									</div>
									<div style="height:3px;"></div>
									<!-- <div class="bot_con_info" style="text-align: right;">* 포탈 댓글은 최초 수집 후 72시간까지 제공하며, 수동 수집은 요청 후 최대 30분까지 소요됩니다.</div> -->
								</div>
								
								<div style="width: 100%;text-align: right;margin-top: 15px;">
									<span class="fs11"> * ‘대구시’, ‘대구광역시’로 수집되는 모든 기사리스트, 댓글 많은 순으로 정렬 </span>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0" id="portal_reply_loader" ><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							<!-- 포탈 댓글 현황 -->

							<!-- 기사 댓글 분석 -->
							<section id="s_c7_2">
								<h3 class="no_bd"><span class="ui_bullet_00">●</span>기사 댓글 분석<strong id="article_reply_ana"></strong></h3>
								<div class="content">
									<!-- 성향별 점유율 -->
									<section id="s_c7_2_1" class="ui_box_00">
										<h4>성향별 점유율</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5_2', '성향별 점유율'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c7_01" class="ui_chart">
											<script type="text/javascript">
													var chart6;
													(function(){
														var chartOption ={
																"type": "pie",
																"addClassNames": true,
																"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
																"innerRadius": "70%",
																"labelRadius": -17,
																"labelText": "[[percents]]%",
																"pullOutRadius": "0%",
																"radius": "47%",
																"startRadius": "0%",
																"colors": [
																	"#5ba1e0",
																	"#ea7070",
																	"#888888"
																],
																"marginBottom": 0,
																"marginTop": 0,
																"pullOutDuration": 0,
																"startDuration": 0,
																"titleField": "category",
																"valueField": "column1",
																"color": "#FFFFFF",
																"percentPrecision": 0,
																"allLabels": [
																	{
																		"align": "center",
																		"bold": true,
																		"color": "#5ba1e0",
																		"id": "Label-1",
																		"size": 22,
																		"text": "VOLUME",
																		"y": 90
																	},
																	{
																		"align": "center",
																		"bold": true,
																		"color": "#444444",
																		"id": "Label-2",
																		"size": 32,
																		"text": "",
																		"y": 130
																	}
																],
																"balloon": {
																	"color": "#FFFFFF",
																	"fadeOutDuration": 0,
																	"fillAlpha": 0.67,
																	"fillColor": "#000000",
																	"fontSize": 11,
																	"horizontalPadding": 20,
																	"pointerWidth": 5,
																	"shadowAlpha": 0.3,
																	"verticalPadding": 6
																},
																"legend": {
																	"enabled": true,
																	"align": "center",
																	"autoMargins": false,
																	"color": "#444444",
																	"equalWidths": false,
																	"marginLeft": 0,
																	"marginRight": 0,
																	"markerLabelGap": 6,
																	"markerSize": 11,
																	"markerType": "circle",
																	"spacing": 10,
																	"valueText": ""
																},
																"titles": [],
																"dataProvider": []
															};
														/* {
															"type": "pie",
															"addClassNames": true,
															"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
															"innerRadius": "50%",
															"labelRadius": -17,
															"labelText": "[[percents]]%",
															"pullOutRadius": "0%",
															"radius": "40%",
															"startRadius": "0%",
															"colors": [
																"#5ba1e0",
																"#ea7070",
																"#888888"
															],
															"marginBottom": 0,
															"marginTop": 0,
															"pullOutDuration": 0,
															"startDuration": 0,
															"titleField": "category",
															"valueField": "column1",
															"color": "#FFFFFF",
															"percentPrecision": 0,
															"allLabels": [],
															"balloon": {
																"color": "#444444",
																"fadeOutDuration": 0,
																"fillAlpha": 0.9,
																"fillColor": "#ffffff",
																"fontSize": 11,
																"horizontalPadding": 20,
																"pointerWidth": 5,
																"shadowAlpha": 0.3,
																"verticalPadding": 6
															},
															"categoryAxis": {
																"gridPosition": "start",
																"twoLineMode": true,
																"axisColor": "#D8D8D8",
																"color": "#444444",
																"gridAlpha": 0,
																"tickLength": 0,
																"labelFunction":function(data){
																	if(data.length>4){
																		return data.substring(0,4) + "..";	
																	}else{
																		return data;
																	}
																}
															},
															"legend": {
																"enabled": true,
																"align": "center",
																"autoMargins": false,
																"color": "#444444",
																"equalWidths": false,
																"marginLeft": 0,
																"marginRight": 0,
																"markerLabelGap": 6,
																"markerSize": 11,
																"markerType": "circle",
																"spacing": 10,
																"valueText": ""
															},
															"titles": [],
															"dataProvider": [
																{
																	"category": "긍정",
																	"column1": 0
																},
																{
																	"category": "부정",
																	"column1": 0
																},
																{
																	"category": "중립",
																	"column1": 0
																}
															]
														}; */
														chart6 = AmCharts.makeChart("chart_c7_01", chartOption);
														chart6.addListener("clickSlice", function(event){
															var senti = event.dataItem.dataContext.category;
															var r_trnd = "";
															if(senti=="긍정"){
																r_trnd = "1";
															}else if(senti=="부정"){
																r_trnd = "2";
															}else if(senti=="중립"){
																r_trnd = "3";
															}
															var p_date = $("#c_15_dp_00").val();
															
															summary.getReplyPopUp(DOC_ID, p_date, 0, 1, r_trnd, '');
														});
													})();
											</script>
											</div>
										</div>

										<!-- Loader -->
										<div class="ui_loader v0"><span class="loader">Load</span></div>
										<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
										<!-- // Loader -->
									</section>
									<!-- // 성향별 점유율 -->

									<!-- 연관어 클라우드 -->
									<!-- <section id="s_c7_2_2" class="ui_box_00">
										<h4>연관어 클라우드</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5_3', '연관어 클라우드'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div class="spider_wrap">
												<div id="spider_00" class="ui_spider_box v2">
													<div class="line_area"></div>
													<div class="all"><a href="#"><strong>대구광역시</strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item postive"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item negative"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item negative"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item negative"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item negative"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item negative"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item neutral"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item neutral"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item neutral"><a href="#"><strong> </strong><span> </span></a></div>
													<div class="item neutral"><a href="#"><strong> </strong><span> </span></a></div>
												</div>
												<script type="text/javascript" src="../asset/js/jquery.ui_spider.js"></script>
												<script type="text/javascript" src="../asset/js/jquery.domline.js"></script>
												<script type="text/javascript" src="../asset/js/jquery.transform2d.min.js"></script>
												<script type="text/javascript">
													(function($) {
														$( "#spider_00" ).ui_spider();
														$( "#spider_00 a" ).click( function($e){
															popupOpen( "../popup/rel_info.jsp" );
															return false;
														});
													})(jQuery);
												</script>
											</div>
										</div>

										Loader
										<div class="ui_loader v0"><span class="loader">Load</span></div>
										<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
										// Loader
									</section> -->
									<!-- // 연관어 클라우드 -->
									
									<!-- 연관키워드 -->
				
							<!-- <section id="s_c3_1" class="ui_box_00">
								<h4></h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4_1', '연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<div id="tag_cloud_01" class="ui_word_cloud no_data"></div>				데이터 없는 경우 no_data 클래스 추가
								</div>

								Loader
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								// Loader
							</section> -->
							
							<!-- 연관어 클라우드 -->
									<section id="s_c7_2_2" class="ui_box_00">
										<h4>연관어 클라우드</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5_3', '연관어 클라우드'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div class="wrap">
												<div class="ui_jqw_cloud" style="height:299px">
													<div id="tag_cloud_02_04_01" class="wrap"></div>
												</div>
												<!-- <script>
													(function(){
														$("#tag_cloud_02_04_01").each( function(){
															var opt = {
																words: [
																	{weight: 33721, color: "#5ba1e0", word: "좋아요"},
																	{weight: 30674, color: "#5ba1e0", word: "스타"},
																	{weight: 20729, color: "#5ba1e0", word: "팔로우"},
																	{weight: 13375, color: "#5ba1e0", word: "인스타그램"},
																	{weight: 11904, color: "#5ba1e0", word: "인스타"},
																	{weight: 10050, color: "#5ba1e0", word: "구매"},
																	{weight: 9840,  color: "#5ba1e0", word: "사용"},
																	{weight: 8801,  color: "#5ba1e0", word: "갤럭시"},
																	{weight: 8676,  color: "#5ba1e0", word: "카톡"},
																	{weight: 7756,  color: "#5ba1e0", word: "카메라"},
																	{weight: 7733,  color: "#5ba1e0", word: "아이폰7"},
																	{weight: 7582,  color: "#ea7070", word: "너무"},
																	{weight: 7571,  color: "#ea7070", word: "폰케이스"},
																	{weight: 7462,  color: "#ea7070", word: "주말"},
																	{weight: 7414,  color: "#ea7070", word: "애플"},
																	{weight: 6618,  color: "#ea7070", word: "맛집"},
																	{weight: 6409,  color: "#ea7070", word: "핸드폰"},
																	{weight: 6287,  color: "#ea7070", word: "광안리"},
																	{weight: 6270,  color: "#ea7070", word: "여행"},
																	{weight: 5982,  color: "#ea7070", word: "일상愛"},
																	{weight: 5738,  color: "#888888", word: "커피"},
																	{weight: 5655,  color: "#888888", word: "아이폰6"},
																	{weight: 5510,  color: "#888888", word: "스마트폰"},
																	{weight: 5315,  color: "#888888", word: "한남동"},
																	{weight: 5188,  color: "#888888", word: "패션"},
																	{weight: 5100,  color: "#888888", word: "iphone"},
																	{weight: 4845,  color: "#888888", word: "할인"},
																	{weight: 4783,  color: "#888888", word: "디자인"},
																	{weight: 4773,  color: "#888888", word: "구글"},
																	{weight: 4691,  color: "#888888", word: "모바일"},
																],
																clickFunc: function(){
																	popupOpen( "../popup/rel_info.jsp" );
																	console.log( "클릭 메서드 연결" );

																	// 선택아이템 제거
																	//$( "#tag_cloud_02_04_02" ).data( "jqw_cloud" ).activeDisable();
																	//$( "#tag_cloud_02_04_03" ).data( "jqw_cloud" ).activeDisable();
																}
															}
															$( this ).jqw_cloud( opt );
														});
													})();
												</script> -->
											</div>
										</div>

										<!-- Loader -->
										<div class="ui_loader v0"><span class="loader">Load</span></div>
										<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
										<!-- // Loader -->
									</section>
									<!-- // 연관어 클라우드 -->
								</div>

								<!-- Loader -->
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							
						
				<!-- // 연관키워드 -->
									
									
									
								
							<!-- 포탈 댓글 분석 -->
						</div>
					</div>
				</div>
				<!-- 포탈 댓글 현황/분석 -->
				
				<!-- 소셜 이슈 리스트 -->
				<div class="ui_boxs_00">
					<section id="s_c8_1" class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>소셜미디어 이슈 리스트</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('6', '소셜미디어 이슈 리스트'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div class="ui_board_list_00">
								<table id="section06_contents">
								<caption>관련정보 목록</caption>
								<colgroup>
								<col style="width:200px">
								<col>
								<col style="width:120px">
								<col style="width:120px">
								</colgroup>
								<thead>
								<tr>
								<th scope="col"><span>출처</span></th>
								<th scope="col"><span>제목</span></th>
								<th scope="col"><span>확산 건수</span></th>
								<th scope="col"><span>수집일</span></th>
								</tr>
								</thead>
								<tbody>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">데이터가 없습니다</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<!-- <tr>
								<td><span title="네이버">네이버</span></td>
								<td>2017-01-01</td>
								<td class="title"><a href="#" target="_blank">황사,미세먼지 이길 '디톡스식품' 인기리 판매</a></td>
								<td><span title="이재현,최순실 이재현">이재현,최순실 이재현</span></td>
								</tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr>
								<tr><td colspan="4"></td></tr> -->
								</tbody>
								</table>
							</div>
							<div id="section06_paging" class="paginate">
								<!-- <a href="#" class="page_prev disabled">이전페이지</a>		버튼 활성화시 disabled 클래스 제거
								<a href="#" class="active" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">1</a>
								<a href="#" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">2</a>
								<a href="#">3</a>
								<a href="#">4</a>
								<a href="#">5</a>
								<a href="#">6</a>
								<a href="#">7</a>
								<a href="#">8</a>
								<a href="#">9</a>
								<a href="#">10</a>
								<a href="#" class="page_next">다음페이지</a> -->
							</div>
						</div>
						<div style="width: 100%;text-align: right;margin-top: 15px;">
							<span class="fs11"> * ‘대구시’, ‘대구광역시’로 수집되는 개인미디어(블로그,카페,커뮤니티,트위터,페이스북) 게시물리스트, 확산 수량 많은 순으로 정렬 </span>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 소셜 이슈 리스트 -->
				
			</div>
		</div>

		<!-- Include Footer -->
		<jsp:include page="../inc/inc_footer.jsp" flush="false" />
		<!-- // Include Footer -->
	</div>
</body>
</html>