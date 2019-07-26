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
		gnbIDX = "0300";
		setPageTitle( "대구광역시 - 여론동향" );
		
		// 검색
		function hndl_search(){
			var sDateChk = $("#searchs_dp_start").val();
			var eDateChk = $("#searchs_dp_end").val();
			
			if(getDateDiff(sDateChk, eDateChk)>365){
				alert("365일까지 검색 가능 합니다.");
			}else{
				$("#s_c2_1_sel_00").val("0");
				$("#s_c2_1_sel_00 ~ label").text("전체");
				
				$("#s_c4_1_sel_00").val("1");
				$("#s_c4_1_sel_00 ~ label").text("언론");
				$("#s_c4_1_sel_01").val("0");
				$("#s_c4_1_sel_01 ~ label").text("1위 ~ 10위");
				
				$("#sel_keyword_00_00").val("0");
				$("#sel_keyword_00_00 ~ label").text("전체");
				$("#sel_keyword_00_01").val("10");
				$("#sel_keyword_00_01 ~ label").text("10개");
				$("#sel_keyword_00_02").val("0");
				$("#sel_keyword_00_02 ~ label").text("전체");
				
				$("#s_c6_1_sel_00").val("0");
				$("#s_c6_1_sel_00 ~ label").text("전체");
				$("#s_c6_2_sel_00").val("0");
				$("#s_c6_2_sel_00 ~ label").text("전체");
				
				$(".ui_box_00").find(".v0").fadeIn(80);
				scope = $("#search_sel_01").val();
				keyword = $("#ui_search_input").val();
				sDate = $("#searchs_dp_start").val();
				eDate = $("#searchs_dp_end").val();
				
				section01();
				section02();
				section03("0");
				section04();
				
				//setTimeout(function(){
					section05();
					section06("1", "0");
					section09_1("1", "0");
					section09_2("1", "0");
				//},1000);
				
				//setTimeout(function(){
					section07("0", "10", "0");
					section08();
				//},1000);
				
			}
		}
		
		$(document).ready(function(){
			initSetting();
			hndl_search();
			
			$("#ui_search_input").keypress(function(e){
		        if (e.which == 13) {
		        	hndl_search();
		        }
			});
			
			$("#s_c2_1_sel_00").change(function(){
				section03($(this).val());
			});
			
			$("#s_c4_1_sel_00").change(function(){
				var limit = $("#s_c4_1_sel_01").val();
				section06($(this).val(), limit);
			});
			
			$("#s_c4_1_sel_01").change(function(){
				var source = $("#s_c4_1_sel_00").val();
				section06(source, $(this).val());
			});
			
			$("#sel_keyword_00_00").change(function(){
				var limit = $("#sel_keyword_00_01").val();
				var senti = $("#sel_keyword_00_02").val();
				section07($(this).val(), limit, senti);
			});
			
			$("#sel_keyword_00_01").change(function(){
				var source = $("#sel_keyword_00_00").val();
				var senti = $("#sel_keyword_00_02").val();
				section07(source, $(this).val(), senti);
			});
			
			$("#sel_keyword_00_02").change(function(){
				var source = $("#sel_keyword_00_00").val();
				var limit = $("#sel_keyword_00_01").val();
				section07(source, limit, $(this).val());
			});
			
			$("#s_c6_1_sel_00").change(function(){
				section09_1("1", $(this).val());
			});
			
			$("#s_c6_2_sel_00").change(function(){
				section09_2("1", $(this).val());
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
	
	<script type="text/javascript" src="opinion.js"></script>

	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->

	<!-- Include HEADER -->
	<jsp:include page="../inc/inc_header.jsp" flush="false" />
	<!-- // Include HEADER -->

	<div class="page_wrap" id="s_top">
		<!-- Content Container -->
		<div id="container" class="p_opnion">
			<div id="content">

				<h2 class="invisible">여론동향</h2>

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

				<!-- 정보량 현황 & 주간 정보량 추이 -->
				<div class="ui_boxs_00">
					<!-- 정보량 현황 -->
					<section id="s_c1_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>정보량 현황</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('1', '정보량 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div class="ui_volumes">
								<div class="chart_wrap">
									<div id="chart_c1_01" class="ui_chart"></div>
									<script type="text/javascript">
										var chart1;				
										(function(){
											var chartOption = {
												"type": "pie",
												"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
												"innerRadius": "50%",
												"labelRadius": -15,
												"labelText": "[[percents]]%",
												"pullOutRadius": "0%",
												"radius": "45%",
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
												"allLabels": [],
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
												"dataProvider": [
													/* {
														"category": "긍정",
														"column-1": 25
													},
													{
														"category": "부정",
														"column-1": 36
													},
													{
														"category": "중립",
														"column-1": 36
													} */
												]
											};
											chart1 = AmCharts.makeChart("chart_c1_01", chartOption);
											chart1.addListener("clickSlice", function(event){
												var senti = event.dataItem.dataContext.category;
												
												//1@2 (분류체계-구분-박원순 서울시장)
												var codeList = "1@2";
												
												if(senti=="긍정"){
													codeList += "/9@1";
												}else if(senti=="부정"){
													codeList += "/9@2";
												}else if(senti=="중립"){
													codeList += "/9@3";
												}
												
												var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList;
												popupOpen( "../popup/rel_info.jsp?" + param);
											});
										})();
									</script>
								</div>
								<div class="info_wrap">
									<h4>VOLUME</h4>
									<a href="#" class="dv"><!-- 999,999K --></a>
									<div class="senti_infos">
										<p>
											<strong class="senti">긍정</strong>
											<a href="#" class="dv"><!-- 999,999건 --></a>
										</p>
										<p>
											<strong class="senti">부정</strong>
											<a href="#" class="dv"><!-- 999,999건 --></a>
										</p>
										<p>
											<strong class="senti">중립</strong>
											<a href="#" class="dv"><!-- 999,999건 --></a>
										</p>
									</div>
								</div>
							</div>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
					<!-- // 정보량 현황 -->

					<!-- 주간 정보량 추이 -->
					<section id="s_c1_2" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>주간 정보량 추이</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2', '주간 정보량 추이'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c1_02" class="ui_chart"></div>
							<script type="text/javascript">
									var chart2;
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
													"dragIconHeight": 20,
													"dragIconWidth": 20,
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
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 5,
														"dashLength": 0,
														"id": "AmGraph-4",
														"lineThickness": 2,
														"title": "Total Volume",
														/* "valueAxis": "ValueAxis-2", */
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
													/* {
														"category": "06-01",
														"column-1": 10,
														"column-2": 30,
														"column-3": 60,
														"column-4": 100
													},
													{
														"category": "06-02",
														"column-1": 30,
														"column-2": 30,
														"column-3": 30,
														"column-4": 90
													},
													{
														"category": "06-03",
														"column-1": 20,
														"column-2": 50,
														"column-3": 10,
														"column-4": 80
													} */
												]
											};
										chart2 = AmCharts.makeChart("chart_c1_02", chartOption);
										chart2.addListener("clickGraphItem", function(event){
											var fulldate = event.item.dataContext.date;
											var field = event.item.graph.valueField;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2";
											
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
					<!-- // 주간 정보량 추이 -->
				</div>
				<!-- // 정보량 현황 & 주간 정보량 추이 -->

				<!-- 정보구분 별 정보량 & 주요이슈 별 정보량 -->
				<div class="ui_boxs_00">
					<!-- 정보구분 별 정보량 -->
					<section id="s_c2_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>정보구분 별 정보량</h3>
						<div class="title_rc">
						<strong>주제그룹</strong>
							<div class="dcp">
								<select id="s_c2_1_sel_00" class="ui_hidden" style="width:120px;">
								</select><label for="s_c2_1_sel_00" class="select_00">전체</label>
							</div>
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3', '정보구분 별 정보량'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c2_01" class="ui_chart"></div>
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
													"gridPosition": "start",
													"twoLineMode": true,
													"axisColor": "#D8D8D8",
													"color": "#444444",
													"gridAlpha": 0,
													"labelRotation": 45,
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
										chart3 = AmCharts.makeChart("chart_c2_01", chartOption);
										chart3.addListener("clickGraphItem", function(event){
											var iccode = event.item.dataContext.iccode;
											var field = event.item.graph.valueField;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2/21@" + iccode;
											
											if( $("#s_c2_1_sel_00").val() == 0){
												codeList = "1@2/2@" + iccode;
											}
											
											if(field=="column-1"){
												codeList += "/9@1";
											}else if(field=="column-2"){
												codeList += "/9@2";
											}else if(field=="column-3"){
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
					<!-- // 정보구분 별 정보량 -->

					<!-- 주요이슈 별 정보량 -->
					<section id="s_c2_2" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>주요이슈 별 정보량</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4', '주요이슈 별 정보량'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c2_02" class="ui_chart"></div>
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
														if(chart4.dataProvider.length==10){
															return data.substring(0,2) + "..";	
														}else if(chart4.dataProvider.length>10){
															return data.substring(0,4) + "..";	
														}else{
															if(data.length>4){
																return data.substring(0,3) + "..";	
															}else{
																return data;
															}
														}
													}
												},
												"chartCursor": {
													"enabled": true,
													"cursorColor": "#000000"
												},
												"chartScrollbar": {
													"enabled": true,
													"dragIconHeight": 20,
													"dragIconWidth": 20,
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
												"dataProvider": [
													/* {
														"category": "title01",
														"column-1": 10,
														"column-2": 30,
														"column-3": 60
													},
													{
														"category": "title02",
														"column-1": 30,
														"column-2": 30,
														"column-3": 30
													},
													{
														"category": "title03",
														"column-1": 20,
														"column-2": 50,
														"column-3": 10
													},
													{
														"category": "title04",
														"column-1": 70,
														"column-2": 80,
														"column-3": 10
													},
													{
														"category": "title05",
														"column-1": 10,
														"column-2": 30,
														"column-3": 20
													},
													{
														"category": "title06",
														"column-1": 10,
														"column-2": 10,
														"column-3": 60
													},
													{
														"category": "title07",
														"column-1": 40,
														"column-2": 30,
														"column-3": 10
													} */
												]
											};
										chart4 = AmCharts.makeChart("chart_c2_02", chartOption);
										chart4.addListener("clickGraphItem", function(event){
											var iccode = event.item.dataContext.iccode;
											var field = event.item.graph.valueField;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2/4@" + iccode;
											
											if(field=="column-1"){
												codeList += "/9@1";
											}else if(field=="column-2"){
												codeList += "/9@2";
											}else if(field=="column-3"){
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
					<!-- // 주요이슈 별 정보량 -->
				</div>
				<!-- // 정보량 현황 & 주간 정보량 추이 -->

				<!-- 매체 별 정보량 & 매체 별 성향 현황 -->
				<div class="ui_boxs_00">
					<!-- 매체 별 정보량 -->
					<section id="s_c3_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>매체 별 정보량</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5_1', '매체 별 정보량'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c3_01" class="ui_chart"></div>
								<script type="text/javascript">
									var chart5_1;
									(function(){
										var chartOption = {
												"type": "pie",
												"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
												"innerRadius": "50%",
												"labelRadius": -17,
												"labelText": "[[percents]]%",
												"hideLabelsPercent": 5,
												"pullOutRadius": "0%",
												"radius": "45%",
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
													/* {
														"category": "출처1",
														"column-1": 50
													},
													{
														"category": "출처2",
														"column-1": 20
													},
													{
														"category": "출처3",
														"column-1": 100
													},
													{
														"category": "출처4",
														"column-1": 10
													},
													{
														"category": "출처5",
														"column-1": 60
													},
													{
														"category": "출처6",
														"column-1": 90
													},
													{
														"category": "출처7",
														"column-1": 100
													} */
												]
											};
										chart5_1 = AmCharts.makeChart("chart_c3_01", chartOption);
										chart5_1.addListener("clickSlice", function(event){	
											var iccode = event.dataItem.dataContext.iccode;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2/9@1,2,3/6@" + iccode;
			
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
					<!-- // 매체 별 정보량 -->

					<!-- 매체 별 성향 현황 -->
					<section id="s_c3_2" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>매체 별 성향 현황</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5_2', '매체 별 성향 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c3_02" class="ui_chart"></div>
							<script type="text/javascript">
									var chart5_2;
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
														"category": "title01",
														"column-2": 10,
														"column-3": 30,
														"column-4": 60
													},
													{
														"category": "title02",
														"column-2": 30,
														"column-3": 30,
														"column-4": 30
													},
													{
														"category": "title03",
														"column-2": 20,
														"column-3": 50,
														"column-4": 10
													},
													{
														"category": "title04",
														"column-2": 70,
														"column-3": 80,
														"column-4": 10
													},
													{
														"category": "title05",
														"column-2": 10,
														"column-3": 30,
														"column-4": 20
													} */
												]
											};
										chart5_2 = AmCharts.makeChart("chart_c3_02", chartOption);
										chart5_2.addListener("clickGraphItem", function(event){
											var iccode = event.item.dataContext.iccode;
											var field = event.item.graph.valueField;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2/6@" + iccode;
											
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
					<!-- // 매체 별 성향 현황 -->
				</div>
				<!-- // 정보량 현황 & 주간 정보량 추이 -->

				<!-- 주요 매체 현황 -->
				<div class="ui_boxs_00">
					<section id="s_c4_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>주요 매체 현황</h3>
						<div class="title_rc">
							<strong>매체</strong>
							<div class="dcp">
								<select id="s_c4_1_sel_00" class="ui_hidden" style="width:80px;">
								</select><label for="s_c4_1_sel_00" class="select_00">언론</label>
							</div>
							<strong style="padding:0 0 0 5px">순위</strong>
							<div class="dcp">
								<select id="s_c4_1_sel_01" class="ui_hidden">
									<option value="0" selected>1위 ~ 10위</option>
									<option value="1">11위 ~ 20위</option>
								</select><label for="s_c4_1_sel_00" class="select_00"></label>
							</div>
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('6', '주요 매체 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_c4_01" class="ui_chart"></div>
							<script type="text/javascript">
									var chart6;
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
														"category": "title01",
														"column-1": 10,
														"column-2": 30,
														"column-3": 60
													},
													{
														"category": "title02",
														"column-1": 30,
														"column-2": 30,
														"column-3": 30
													},
													{
														"category": "title03",
														"column-1": 20,
														"column-2": 50,
														"column-3": 10
													},
													{
														"category": "title04",
														"column-1": 70,
														"column-2": 80,
														"column-3": 10
													},
													{
														"category": "title05",
														"column-1": 10,
														"column-2": 30,
														"column-3": 20
													},
													{
														"category": "title06",
														"column-1": 10,
														"column-2": 10,
														"column-3": 60
													},
													{
														"category": "title07",
														"column-1": 40,
														"column-2": 30,
														"column-3": 10
													} */
												]
											};
										chart6 = AmCharts.makeChart("chart_c4_01", chartOption);
										chart6.addListener("clickGraphItem", function(event){
											var category = "";
											var source = event.item.dataContext.source;
											var field = event.item.graph.valueField;
											
											//1@2 (분류체계-구분-박원순 서울시장)
											var codeList = "1@2/6@" + source;
											
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
				</div>
				<!-- // 주요 매체 현황 -->

				<!-- 연관키워드 -->
				<div class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00 f_clear">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>연관키워드</h3>
						<div class="content">
							<!-- 주요 연관키워드/현황 -->
							<div class="f_clear">
								<!-- 주요 연관키워드 -->
								<section id="s_c5_1" class="ui_box_00">
									<h4 class="no_bd">주요 연관키워드</h4>
									<div class="title_rc">
										<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('7', '주요 연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
									</div>
									<div class="content" style="height:365px">
										<div class="ui_sort_box_02">
											<div class="sort_container">
												<ul>
													<li>
														<strong>매체</strong>
														<div class="dcp">
															<select id="sel_keyword_00_00" class="ui_hidden" style="width:122px">
																<option selected>전체</option>
															</select><label for="sel_keyword_00_00" class="select_00">전체</label>
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
										<div class="spider_wrap">
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
								<section id="s_c5_2" class="ui_box_00">
									<h4 class="no_bd">연관키워드 현황</h4>
									<div class="title_rc">
										<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('8', '연관키워드 현황'); return false;"><span class="icon excel">Excel Download</span></button>
									</div>
									<div class="content">
										<div class="ui_board_list_00">
											<table id="section08_contents">
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
						</div>
					</section>
				</div>
				<!-- // 연관키워드 -->

				<!-- 관련정보 -->
				<div class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00">
						<h3 class="invisible">식품트렌드 분석/정보량</h3>
						<div class="content">
							<div class="f_clear">
								<!-- 긍정 관련 정보 -->
								<section id="s_c6_1">
									<h3 class="no_bd"><span class="ui_bullet_00">●</span>긍정 관련 정보</h3>
									<div class="title_rc v2">
										<strong>매체</strong>
										<div class="dcp">
											<select id="s_c6_1_sel_00" class="ui_hidden">
												<option selected>전체</option>
											</select><label for="s_c6_1_sel_00" class="select_00">전체</label>
										</div>
										<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('9_1', '긍정 관련 정보'); return false;"><span class="icon excel">Excel Download</span></button>
									</div>
									<div class="content">
										<div class="ui_board_list_00">
											<table id="section09_1_contents">
											<caption>목록</caption>
											<colgroup>
											<col style="width:120px">
											<col>
											<col style="width:60px">
											<col style="width:80px">
											</colgroup>
											<thead>
											<tr>
											<th scope="col"><span>출처</span></th>
											<th scope="col"><span>제목</span></th>
											<th scope="col"><span>유사</span></th>
											<th scope="col"><span>날짜</span></th>
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
											<td><span title="네이버 블로그">네이버 블로그</span></td>
											<td class="al"><a href="#" target="_blank" class="ui_fc_blue" title="촉촉한초코칩촉촉한초코칩">촉촉한초코칩촉촉한초코칩</a></td>
											<td><a href="#" target="_blank" title="999,999,999" onclick="popupOpen( '../popup/rel_info.jsp', 'a=1' );return false;">999,999K</a></td>
											<td>2016-12-31</td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr> -->
											</tbody>
											</table>
										</div>
										<div id="section09_1_paging" class="paginate">
											<!-- <a href="#" class="page_prev disabled">이전페이지</a>		버튼 활성화시 disabled 클래스 제거
											<a href="#" class="active">1</a>
											<a href="#">2</a>
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
								<!-- // 긍정 관련 정보 -->

								<!-- 부정 관련 정보 -->
								<section id="s_c6_2">
									<h3 class="no_bd"><span class="ui_bullet_00">●</span>부정 관련 정보</h3>
									<div class="title_rc v2">
										<strong>매체</strong>
										<div class="dcp">
											<select id="s_c6_2_sel_00" class="ui_hidden">
												<option selected>전체</option>
											</select><label for="s_c6_2_sel_00" class="select_00">전체</label>
										</div>
										<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('9_2', '부정 관련 정보'); return false;"><span class="icon excel">Excel Download</span></button>
									</div>
									<div class="content">
										<div class="ui_board_list_00">
											<table id="section09_2_contents">
											<caption>목록</caption>
											<colgroup>
											<col style="width:120px">
											<col>
											<col style="width:60px">
											<col style="width:80px">
											</colgroup>
											<thead>
											<tr>
											<th scope="col"><span>출처</span></th>
											<th scope="col"><span>제목</span></th>
											<th scope="col"><span>유사</span></th>
											<th scope="col"><span>날짜</span></th>
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
											<td><span title="네이버 블로그">네이버 블로그</span></td>
											<td class="al"><a href="#" target="_blank" class="ui_fc_red" title="촉촉한초코칩촉촉한초코칩">촉촉한초코칩촉촉한초코칩</a></td>
											<td><a href="#" target="_blank" title="999,999,999" onclick="popupOpen( '../popup/rel_info.jsp', 'a=1' );return false;">999,999K</a></td>
											<td>2016-12-31</td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr>
											<tr>
											<td></td>
											<td>-</td>
											<td></td>
											<td></td>
											</tr> -->
											</tbody>
											</table>
										</div>
										<div id="section09_2_paging" class="paginate">
											<!-- <a href="#" class="page_prev disabled">이전페이지</a>		버튼 활성화시 disabled 클래스 제거
											<a href="#" class="active">1</a>
											<a href="#">2</a>
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
								<!-- // 부정 관련 정보 -->
							</div>
						</div>
					</section>
				</div>
				<!-- // 관련정보 -->

			</div>
		</div>

		<!-- Include Footer -->
		<jsp:include page="../inc/inc_footer.jsp" flush="false" />
		<!-- // Include Footer -->
	</div>
</body>
</html>