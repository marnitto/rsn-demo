<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->

<body>
	<script type="text/javascript">
		gnbIDX = "0200";
		setPageTitle( "대구광역시 - 주요시정 모니터링" );
		
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
					/* "lineThickness":1, */
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
					"minimum": -1,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {},
			"titles": []
		};

		// 검색
		function hndl_search(){
			var sDateChk = $("#searchs_dp_start").val();
			var eDateChk = $("#searchs_dp_end").val();
			
			if(getDateDiff(sDateChk, eDateChk)>365){
				alert("365일까지 검색 가능 합니다.");
			}else{
				$(".ui_box_00").find(".v0").fadeIn(80);
				scope = $("#search_sel_01").val();
				keyword = $("#ui_search_input").val();
				sDate = $("#searchs_dp_start").val();
				eDate = $("#searchs_dp_end").val();
				section01(1, 1);
			}
		}
		
		$(document).ready(function(){			
			hndl_search();
			
			$("#ui_search_input").keypress(function(e){
		        if (e.which == 13) {
		        	hndl_search();
		        }
			});
			
			$("input[name=s_c6_1_radio]").click(function(){
				
				/* var tab_val = $(this).val();
				if(tab_val == 0 || tab_val ==1){
					$("#issue_title").text('주요시정');
				}else{
					$("#issue_title").text('대구시장');
				} */
				
				$(".ui_box_00").eq(0).find(".v0").fadeIn(80);
				section01(1, 1);
			});
			
			$("#media_sel_00").change(function(){
				var limit = $("#media_sel_01").val();
				section04($(this).val(), limit);
			});
			
			$("#media_sel_01").change(function(){
				var source = $("#media_sel_00").val();
				section04(source, $(this).val());
			});
			
			$("#sel_keyword_00_00").change(function(){
				var limit = $("#sel_keyword_00_01").val();
				var senti = $("#sel_keyword_00_02").val();
				
				section05($(this).val(), limit, senti);
			});
			
			$("#sel_keyword_00_01").change(function(){
				var source = $("#sel_keyword_00_00").val();
				var senti = $("#sel_keyword_00_02").val();
				
				section05(source, $(this).val(), senti);
			});
			
			$("#sel_keyword_00_02").change(function(){
				var source = $("#sel_keyword_00_00").val();
				var limit = $("#sel_keyword_00_01").val();
				
				section05(source, limit, $(this).val());
			});
			
			$("#info_sel_00").change(function(){
				section07(1, $(this).val());
			});
		});
	</script>
	
	<script type="text/javascript" src="mrt.js"></script>

	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->

	<!-- Include HEADER -->
	<jsp:include page="../inc/inc_header.jsp" flush="false" />
	<!-- // Include HEADER -->

	<div class="page_wrap" id="s_top">
		<!-- Content Container -->
		<div id="container" class="p_mrt">
			<div id="content">

				<h2 class="invisible">대체식품</h2>

				<!-- Search -->
				<form action="#">
					<div class="f_clear">
						<section class="ui_searchs fr">
							<h3 class="invisible">검색조건</h3>
							<div class="ui_search_type">
								<div class="dcp">
									<select id="search_sel_01" class="ui_hidden">
										<option value="1">제목</option>
										<option value="2">내용</option>
										<option value="3" selected>제목 + 내용</option>
									</select><label for="search_sel_01" class="select_00"></label>
								</div>
								<input type="text" id="ui_search_input" class="ui_input_01" style="width:400px"><label for="ui_search_input" class="invisible">검색어 입력</label>
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
							<script type="text/javascript">
								// 날짜 설정
								var sel_sDate = getBeforeDay(getToday(), 6);				// 시작 날짜 설정
								var sel_eDate = getToday();									// 종료 날짜 설정
							</script>
							<button type="button" class="ui_btn_01" onclick="hndl_search()"><strong>검색</strong></button>
						</section>
					</div>
				</form>
				<!-- // Search -->

				<!-- 주요 시정 확산 목록 -->
				<div class="ui_boxs_00">
					<section id="s_c1_1" class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>주요 시정 확산 목록</h3>
						<div class="content">
							<!-- <ul class="ui_tab_00">
								<li><input id="s_c6_1_radio_00" type="radio" name="s_c6_1_radio" value="0" checked><label for="s_c6_1_radio_00"><span>전체</span></label></li>
								<li><input id="s_c6_1_radio_01" type="radio" name="s_c6_1_radio" value="1"><label for="s_c6_1_radio_01"><span>주요시정</span></label></li>
								<li><input id="s_c6_1_radio_02" type="radio" name="s_c6_1_radio" value="2"><label for="s_c6_1_radio_02"><span>대구시장</span></label></li>
							</ul> -->
							
							<div class="ui_board_list_00 v2">
								<table id="section01_contents">
								<caption>목록</caption>
								<colgroup>
								<col>
								<col style="width:200px">
								<col style="width:100px">
								<col style="width:200px">
								<col style="width:80px">
								</colgroup>
								<thead>
								<tr>
								<th scope="col"><span>주요이슈</span></th>
								<th scope="col"><span>정보량 (기사건수/온라인건수)</span></th>
								<th scope="col"><span>추세</span></th>
								<th scope="col"><span>성향</span></th>
								<th scope="col"><span>*부정율</span></th>
								</tr>
								</thead>
								<tbody>
								<!-- 데이터 없는 경우 -->
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">데이터가 없습니다</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								
								<!-- <tr class="active">
								<td class="title"><a href="#" target="_blank" title="서울형 도시재생">서울형 도시재생</a></td>
								<td><strong title="총정보량:999,999">999,999</strong> <span class="fs11">(<span title="기사건수:999,999">999,999</span>/<span title="온라인건수:999,999">999,999</span>)</span></td>
								<td><div id="brd_chart_00_00" class="ui_chart" style="height:18px"></div></td>
								<td><span class="ui_fc_blue" title="긍정 : 999,999,999">99,999K</span> / <span class="ui_fc_red" title="부정 : 999,999,999">99,999K</span> / <span class="ui_fc_green" title="중립 : 999,999,999">99,999K</span></td>
								<td><span class="pie_chart lv_0" title="0%">0%</span></td>			lv_0 ~ lv_10
								</tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr>
								<tr><td colspan="5">&nbsp;</td></tr> -->
								</tbody>
								</table>
								<script type="text/javascript">
									var chart_00_00;
									var chart_00_01;
									var chart_00_02;
									var chart_00_03;
									var chart_00_04;
									var chart_00_05;
									var chart_00_06;
									var chart_00_07;
									var chart_00_08;
									var chart_00_09;
								
									/* (function($) {
										chart_00_00 = AmCharts.makeChart("brd_chart_00_00", sparkChartOption);
										chart_00_00.dataProvider = [
											{
												"category": "category 1",
												"column-1": 8
											},
											{
												"category": "category 2",
												"column-1": 6
											},
											{
												"category": "category 3",
												"column-1": 2
											},
											{
												"category": "category 4",
												"column-1": 1
											},
											{
												"category": "category 5",
												"column-1": 2
											},
											{
												"category": "category 6",
												"column-1": 3
											},
											{
												"category": "category 7",
												"column-1": 6
											}
										];
										chart_00_00.validateData();
									})(jQuery); */
								</script>
							</div>
							<div id="section01_paging" class="paginate">
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
							<div class="bot_con_info" style="text-align: right;"><span class="fs11">*<strong>부정율</strong> : 부정 / ( 긍정 + 부정 ) * 100</span></div>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 주요 시정 확산 목록 -->

				<!-- 선택된 주요 시정 컨텐츠 -->
				<section id="hidden_section" class="ui_box_00 ui_shadow_00 f_clear mrt_sub_contents">
					<h3 class="no_bd"><span class="ui_bullet_00">●</span><span id="issue_title">주요 시정</span><strong id="section02_subTitle"></strong></h3>
					<div class="content">
						<!-- 주간 추이 현황 -->
						<section class="ui_box_00">
							<h4>주간 추이 현황</h4>
							<div class="title_rc">
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2', '주간 추이 현황'); return false;"><span class="icon excel">Excel Download</span></button>
							</div>
							<div class="content" style="height:300px">
								<div id="chart_01" class="ui_chart" style="height:300px"></div>
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
												"#5ba1e0",
												"#ea7070",
												"#888888",
												"#7e0000"
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
													"bulletBorderThickness": 0,
													"bulletSize": 5,
													"fillAlphas": 1,
													"id": "AmGraph-1",
													"markerType": "circle",
													"title": "긍정",
													"type": "column",
													"valueField": "column-1"
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
													"valueField": "column-2"
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
													"valueField": "column-3"
												},
												{
													"balloonText": "<strong>[[title]]</strong> : [[value]]",
													"bulletBorderThickness": 0,
													"bulletSize": 5,
													"bullet": "round",
													/* "dashLength": 4, */
													"id": "AmGraph-4",
													"lineThickness": 2,
													"title": "Total Volume",
													"valueAxis": "ValueAxis-1",
													"valueField": "column-4"
												}
											],
											"guides": [],
											"valueAxes": [
												{
													"id": "ValueAxis-1",
													/* "stackType": "regular", */
													"axisThickness": 0,
													"color": "#c0c0c0",
													"dashLength": 2,
													"gridAlpha": 1,
													"gridColor": "#D8D8D8",
													"tickLength": 0,
													"title": ""
												}/* ,
												{
													"id": "ValueAxis-2",
													"maximum": 100,
													"position": "right",
													"stackType": "regular",
													"unit": "%",
													"autoGridCount": false,
													"axisThickness": 0,
													"color": "#c0c0c0",
													"dashLength": 2,
													"gridAlpha": 1,
													"gridColor": "#D8D8D8",
													"gridCount": 2,
													"gridThickness": 0,
													"tickLength": 0,
													"title": ""
												} */
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
											"dataProvider": [
												{
													"category": "01-01",
													"column-1": 0,
													"column-2": 0,
													"column-3": 0,
													"column-4": 0
												}
											]
										};
										chart1 = AmCharts.makeChart("chart_01", chartOption);
										chart1.addListener("clickGraphItem", function(event){
											var fulldate = event.item.dataContext.date;
											var field = event.item.graph.valueField;
											var codeList = "4@" + issueCode;
											
											if(field=="column-1"){
												codeList += "/9@1";
											}else if(field=="column-2"){
												codeList += "/9@2";
											}else if(field=="column-3"){
												codeList += "/9@3";
											}
											
											var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + fulldate + "&eDate=" + fulldate + "&codeList=" + codeList;
											popupOpen( "../popup/rel_info.jsp?" + param);
											
										});
									})();
								</script>
							</div>

							<!-- Loader -->
							<div class="ui_loader v0"><span class="loader">Load</span></div>
							<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
							<!-- // Loader -->
						</section>
						<!-- // 주간 추이 현황 -->

						<!-- 매체별 정보량/성향 현황 -->
						<div class="f_clear">
							<!-- 매체별 정보량 -->
							<section class="ui_box_00 fl" style="width:449px">
								<h4>매체별 정보량</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_1', '매체별 정보량'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content" style="height:280px">
									<div id="chart_02" class="ui_chart" style="width:407px;height:280px"></div>
									<script type="text/javascript">
										var chart2;
										(function(){
											var chartOption = {
												"type": "pie",
												"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
												"innerRadius": "50%",
												"labelRadius": -17,
												"labelText": "[[percents]]%",
												"hideLabelsPercent": 2,
												"pullOutRadius": "0%",
												"radius": "35%",
												"startRadius": "0%",
												"colors": [
													"#db9e4e",
													"#fd87a7",
													"#58bd53",
													"#ff704f",
													"#01d6ff",
													"#3b5999",
													"#b13aae"
												],
												"marginBottom": 0,
												"marginTop": 0,
												"pullOutDuration": 0,
												"startDuration": 0,
												"titleField": "category",
												"valueField": "column-1",
												"addClassNames": true,
												"color": "#FFFFFF",
												"percentPrecision": 0,
												"allLabels": [],
												"balloon": {
													"color": "#FFFFFF",
													"fadeOutDuration": 0,
													"fillAlpha": 0.67,
													"fillColor": "#000000",
													"fixedPosition": false,
													"fontSize": 11,
													"horizontalPadding": 10,
													"pointerWidth": 5,
													"shadowAlpha": 0.3
												},
												"legend": {
													"enabled": true,
													"position": "right",
													"align": "center",
													"autoMargins": false,
													"color": "#444444",
													"marginLeft": 0,
													"marginRight": 0,
													"markerLabelGap": 6,
													"markerSize": 11,
													"markerType": "circle",
													"spacing": 5,
													"verticalGap" : 2,
													"valueText": ""
												},
												"titles": [],
												"dataProvider": [
													/*{
														"category": "",
														"column-1": 0
													}*/
												]
											};
											chart2 = AmCharts.makeChart("chart_02", chartOption);
											chart2.addListener("clickSlice", function(event){
												var sourceCode = event.dataItem.dataContext.iccode;
												var codeList = "4@" + issueCode + "/6@" + sourceCode;
												
												var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList;
												popupOpen( "../popup/rel_info.jsp?" + param);										
											});
										})();
									</script>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							<!-- 매체별 정보량 -->

							<!-- 매체별 성향 현황 -->
							<section class="ui_box_00 fr" style="width:449px">
								<h4>매체별 성향 현황</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_2', '매체별 성향 현황'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content" style="height:280px">
									<div id="chart_03" class="ui_chart" style="height:280px;"></div>
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
													"autoWrap": true,
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
													},
													{
														"id": "ValueAxis-2",
														"maximum": 100,
														"position": "right",
														"stackType": "regular",
														"unit": "%",
														"autoGridCount": false,
														"axisThickness": 0,
														"color": "#c0c0c0",
														"dashLength": 2,
														"gridAlpha": 1,
														"gridColor": "#D8D8D8",
														"gridCount": 2,
														"gridThickness": 0,
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
												"dataProvider": [
													/* {
														"category": "언론",
														"column-2": 46,
														"column-3": 46,
														"column-4": 91
													},
													{
														"category": "포탈",
														"column-2": 22,
														"column-3": 61,
														"column-4": 48
													},
													{
														"category": "블로그",
														"column-2": 16,
														"column-3": 29,
														"column-4": 68
													},
													{
														"category": "카페",
														"column-2": 70,
														"column-3": 66,
														"column-4": 5
													},
													{
														"category": "트위터",
														"column-2": 51,
														"column-3": 13,
														"column-4": 88
													},
													{
														"category": "페이스북",
														"column-2": 67,
														"column-3": 38,
														"column-4": 4
													} */
												]
											};
											chart3 = AmCharts.makeChart("chart_03", chartOption);
											chart3.addListener("clickGraphItem", function(event){
												var source = event.item.dataContext.iccode;
												var field = event.item.graph.valueField;
												var codeList = "4@" + issueCode + "/6@" + source;
												
												if(field=="column-2"){
													codeList += "/9@1";
												}else if(field=="column-3"){
													codeList += "/9@2";
												}else if(field=="column-4"){
													codeList += "/9@3";
												}
												
												var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList;
												popupOpen( "../popup/rel_info.jsp?" + param);
											});
										})();
									</script>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							<!-- // 매체별 성향 현황 -->
						</div>
						<!-- // 매체별 정보량/성향 현황 -->

						<!-- 주요 매체 현황 -->
						<section class="ui_box_00">
							<h4>주요 매체 현황</h4>
							<div class="title_rc">
								<strong>매체</strong>
								<div class="dcp">
									<select id="media_sel_00" class="ui_hidden">
									</select><label for="media_sel_00" class="select_00">언론</label>
								</div>
								<strong style="padding:0 0 0 5px">순위</strong>
								<div class="dcp">
									<select id="media_sel_01" class="ui_hidden">
										<option value="0" selected>1위 ~ 10위</option>
										<option value="1">11위 ~ 20위</option>
									</select><label for="media_sel_01" class="select_00">전체</label>
								</div>
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4', '주요 매체 현황'); return false;"><span class="icon excel">Excel Download</span></button>
							</div>
							<div class="content" style="height:300px">
								<div id="chart_04" class="ui_chart" style="height:300px"></div>
								<script type="text/javascript">
									var chart4;	
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
												/* "autoWrap": true, */
												"gridPosition": "start",
												"twoLineMode": true,
												"axisColor": "#D8D8D8",
												"color": "#444444",
												"gridAlpha": 0,
												"tickLength": 0,
												"labelFunction":function(data){
													if(data.length>6){
														return data.substring(0,6) + "..";	
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
													"valueField": "column-1"
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
													"valueField": "column-2"
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
													"valueField": "column-3"
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
												},
												{
													"id": "ValueAxis-2",
													"maximum": 100,
													"position": "right",
													"stackType": "regular",
													"unit": "%",
													"autoGridCount": false,
													"axisThickness": 0,
													"color": "#c0c0c0",
													"dashLength": 2,
													"gridAlpha": 1,
													"gridColor": "#D8D8D8",
													"gridCount": 2,
													"gridThickness": 0,
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
											"dataProvider": [
												/* {
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												},
												{
													"category": "이타임즈넷",
													"column-1": 46,
													"column-2": 46,
													"column-3": 91
												} */
											]
										};
										chart4 = AmCharts.makeChart("chart_04", chartOption);
										chart4.addListener("clickGraphItem", function(event){
											var category = "";
											var source = event.item.dataContext.source;
											var field = event.item.graph.valueField;
											var codeList = "4@" + issueCode + "/6@" + source;
											
											if(field=="column-1"){
												codeList += "/9@1";
											}else if(field=="column-2"){
												codeList += "/9@2";
											}else if(field=="column-3"){
												codeList += "/9@3";
											}
											
											if(source=="1"){
												category = event.item.dataContext.site;
											}else{
												category = event.item.dataContext.category;
											}

											var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&source=" + source + "&category=" + category;
											popupOpen( "../popup/rel_info.jsp?" + param);
										});
									})();
								</script>
							</div>

							<!-- Loader -->
							<div class="ui_loader v0"><span class="loader">Load</span></div>
							<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
							<!-- // Loader -->
						</section>
						<!-- // 주요 매체 현황 -->

						<!-- 주요 연관키워드/현황 -->
						<div class="f_clear">
							<!-- 주요 연관키워드 -->
							<section class="ui_box_00 fl" style="width:449px">
								<h4 class="no_bd">주요 연관키워드</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5', '주요 연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content" style="height:365px">
									<div class="ui_sort_box_02">
										<div class="sort_container">
											<ul>
												<li>
													<strong>매체</strong>
													<div class="dcp">
														<select id="sel_keyword_00_00" class="ui_hidden" style="width:122px">
															<option value="0" selected>전체</option>
														</select><label for="sel_keyword_00_00" class="select_00"></label>
													</div>
												</li>
												<li>
													<strong>개수</strong>
													<div class="dcp">
														<select id="sel_keyword_00_01" class="ui_hidden" style="width:70px">
															<option value="10" selected>10개</option>
															<option value="15">15개</option>
															<option value="20">20개</option>
														</select><label for="sel_keyword_00_01" class="select_00"></label>
													</div>
												</li>
												<li>
													<strong>성향</strong>
													<div class="dcp">
														<select id="sel_keyword_00_02" class="ui_hidden" style="width:70px">
															<option value="0" selected>전체</option>
															<option value="1">긍정</option>
															<option value="2">부정</option>
															<option value="3">중립</option>
														</select><label for="sel_keyword_00_02" class="select_00"></label>
													</div>
												</li>
											</ul>
										</div>
									</div>
									<div class="spider_wrap" style="height:316px;">
										<div id="spider_00" class="ui_spider_box">
											<!-- <div class="line_area"></div>
											<div class="all"><a href="#"><strong>전체</strong><span>(총 한수원(999K)건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 1</strong><span>(총 999건)</span><div class="f_clear"><span style="width:0%">긍정</span><span style="width:60%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 2</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 3</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템아이템아이템 4</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 5</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 6</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 7</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 8</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템아이템 9</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 10</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 11</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템아이템 12</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 13</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템아이템아이템 14</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div>
											<div class="item"><a href="#"><strong>아이템 15</strong><span>(총 999건)</span><div class="f_clear"><span style="width:30%">긍정</span><span style="width:30%">부정</span><span style="width:40%">중립</span></div></a></div> -->
										</div>
										<script type="text/javascript" src="../asset/js/jquery.ui_spider.js"></script>
										<script type="text/javascript" src="../asset/js/jquery.domline.js"></script>
										<script type="text/javascript" src="../asset/js/jquery.transform2d.min.js"></script>
									</div>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							<!-- 주요 연관키워드 -->

							<!-- 연관키워드 현황 -->
							<section class="ui_box_00 fr" style="width:449px">
								<h4 class="no_bd">연관키워드 현황</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('6', '연관키워드 현황'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content" style="height:365px">
									<div class="ui_board_list_00">
										<table id="section06_contents">
										<caption>목록</caption>
										<colgroup>
										<col>
										<col style="width:31%">
										<col style="width:31%">
										<col style="width:31%">
										</colgroup>
										<thead>
										<tr>
										<th scope="col"><span></span></th>
										<th scope="col"><span>긍정</span></th>
										<th scope="col"><span>부정</span></th>
										<th scope="col"><span>중립</span></th>
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
										<th scope="row">1</th>
										<td><a href="#" class="ui_fc_blue" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트테스트(999,999)">테스트테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_red" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_green" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										</tr>
										<tr>
										<th scope="row">2</th>
										<td><a href="#" class="ui_fc_blue" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_red" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_green" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										</tr>
										<tr>
										<th scope="row">3</th>
										<td><a href="#" class="ui_fc_blue" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_red" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										<td><a href="#" class="ui_fc_green" onclick="popupOpen( '../popup/rel_info.jsp' );return false;" title="테스트(999,999)">테스트(999,999)</a></td>
										</tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr><td colspan="4">&nbsp;</td></tr> -->
										</tbody>
										</table>
									</div>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>
							<!-- // 연관키워드 현황 -->
						</div>
						<!-- // 주요 연관키워드/현황 -->

						<!-- 관련정보 보기 -->
						<section class="ui_box_00">
							<h3 class="no_bd">관련정보 보기</h3>
							<div class="title_rc">
								<strong>매체</strong>
								<div class="dcp">
									<select id="info_sel_00" class="ui_hidden">
										<option value="0" selected>전체</option>
									</select><label for="info_sel_00" class="select_00"></label>
								</div>
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('7', '관련정보 보기'); return false;"><span class="icon excel">Excel Download</span></button>
							</div>
							<div class="content">
								<div class="ui_board_list_00">
									<table id="section07_contents">
									<caption>목록</caption>
									<colgroup>
									<col style="width:100px">
									<col>
									<col style="width:180px">
									<col style="width:80px">
									<col style="width:100px">
									<!-- <col style="width:70px"> -->
									</colgroup>
									<thead>
									<tr>
									<th scope="col"><span>날짜</span></th>
									<th scope="col"><span>제목</span></th>
									<th scope="col"><span>출처</span></th>
									<th scope="col"><span>성향</span></th>
									<th scope="col"><span>주요이슈</span></th>
									<!-- <th scope="col"><span>댓글</span></th> -->
									</tr>
									</thead>
									<tbody>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">데이터가 없습니다</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<!-- <tr>
									<td>2016-01-01</td>
									<td class="title"><a href="#" target="_blank" title="독점 본격수사 임박... 재게...">독점 본격수사 임박... 재게...</a></td>
									<td><span title="연합뉴스_한민족센터">연합뉴스_한민족센터</span></td>
									<td><span class="ui_fc_blue">긍정</span></td>
									<td><span title="TM">TM</span></td>
									<td><span title="999,999,999">999,999K</span></td>
									</tr>
									<tr>
									<td>2016-01-01</td>
									<td class="title"><a href="#" target="_blank" title="독점 본격수사 임박... 재게...">독점 본격수사 임박... 재게...</a></td>
									<td><span title="연합뉴스_한민족센터">연합뉴스_한민족센터</span></td>
									<td><span class="ui_fc_red">부정</span></td>
									<td><span title="TM">TM</span></td>
									<td><span title="999,999,999">999,999K</span></td>
									</tr>
									<tr>
									<td>2016-01-01</td>
									<td class="title"><a href="#" target="_blank" title="독점 본격수사 임박... 재게...">독점 본격수사 임박... 재게...</a></td>
									<td><span title="연합뉴스_한민족센터">연합뉴스_한민족센터</span></td>
									<td><span class="ui_fc_green">중립</span></td>
									<td><span title="TM">TM</span></td>
									<td><span title="999,999,999">999,999K</span></td>
									</tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr>
									<tr><td colspan="5">&nbsp;</td></tr> -->
									</tbody>
									</table>
								</div>
								<div id="section07_paging" class="paginate">
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

							<!-- Loader -->
							<div class="ui_loader v0"><span class="loader">Load</span></div>
							<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
							<!-- // Loader -->
						</section>
						<!-- // 관련정보 보기 -->
					</div>
				</section>
				<!-- // 선택된 주요 시정 컨텐츠 -->

			</div>
		</div>

		<!-- Include Footer -->
		<jsp:include page="../inc/inc_footer.jsp" flush="false" />
		<!-- // Include Footer -->
	</div>
</body>
</html>