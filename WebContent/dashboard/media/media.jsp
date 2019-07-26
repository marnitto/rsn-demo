<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,java.net.URLEncoder
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.media.MediaMgr"
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

MediaMgr mMgr = new MediaMgr();

JSONArray arr = mMgr.getRealstateList();
%>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->

<body>
	<script type="text/javascript" src="media.js"></script>
	<script type="text/javascript">
		gnbIDX = "0400";
		setPageTitle( "대구시청 - 온라인 홍보 매체현황" );
		
		$(function(){
			
			//getRealstateList();
				hndl_search();	
				
			$("#s_sel03").change(function(){				
				getDvisionList($(this).val(),$("#s_sel04"));
				
				
				setTimeout(function(){
					section01($("#s_sel04").val());	
				},100);
				
			});
			
			$("#s_sel04").change(function(){				
				
				section01($("#s_sel04").val());	
				
			});
			
			
			$("#s_sel05").change(function(){					
				getDvisionList($(this).val(),$("#s_sel06"));
				
				setTimeout(function(){
					section02($("#s_sel06").val());	
				},100);
				
			});
			
			$("#s_sel06").change(function(){				
				
				section02($("#s_sel06").val());	
				
			});
			
			
			$("#s4_c_sel03").change(function(){				
				
				section03_graph();
				section03_info(1);
				
			});
			
			
			
			$("#ui_search_input").keypress(function(e){
		        if (e.which == 13) {
		        	hndl_search();
		        }
			});
			
		});

		// 검색
		function hndl_search(){
			var sDateChk = $("#searchs_dp_start").val();
			var eDateChk = $("#searchs_dp_end").val();
			
			if(getDateDiff(sDateChk, eDateChk)>365){
				alert("31일까지 검색 가능 합니다.");
			}else{
				//$(".ui_box_00").find(".v0").fadeIn(80);
				scope = $("#search_sel_01").val();
				keyword = $("#ui_search_input").val();
				sDate = $("#searchs_dp_start").val();
				eDate = $("#searchs_dp_end").val();
				getDvisionList('',$("#s_sel04"));
				getDvisionList('',$("#s_sel06"));
				
				section01("5",$("#s_sel03"));
				section02("5",$("#s_sel05"));	
				section03_graph();
				section03_info(1);
			}
		}
	</script>

	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->

	<!-- Include HEADER -->
	<jsp:include page="../inc/inc_header.jsp" flush="false" />
	<!-- // Include HEADER -->

	<div class="page_wrap" id="s_top">
		<!-- Content Container -->
		<div id="container" class="p_insta">
			<div id="content">

				<h2 class="invisible">온라인 홍보 매체현황</h2>

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
								var sel_sDate = getBeforeDay(getToday(), 6);		// 시작 날짜 설정
								var sel_eDate = getToday();							// 종료 날짜 설정
							</script>
							<button type="button" class="ui_btn_01" onclick="hndl_search()"><strong>검색</strong></button>
						</section>
					</div>
				</form>
				<!-- // Search -->

				<!-- 온라인 홍보매체 게제 현황 -->
				<div class="ui_boxs_00">
					<section id="s_c4_1" class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>온라인 홍보매체 게재 현황</h3>
						<div class="title_rc">
							<strong>담당실국</strong>
							<div class="dcp">
								<select id="s_sel03" class="ui_hidden">
									<option value="" selected>전체</option>
									<%
									if(arr != null){
										JSONObject obj = null;
									for(int i=0 ; i<arr.length() ; i++){
										obj = (JSONObject)arr.get(i);
									%>
									<option value="<%=obj.getString("code") %>"><%=obj.getString("name") %></option>
									<%} }%>
								</select><label for="s_sel03" class="select_00"></label>
							</div>
							<strong>운영부서</strong>
							<div class="dcp">
								<select id="s_sel04" class="ui_hidden">
									<option selected>전체</option>
								</select><label for="s_sel04" class="select_00"></label>
							</div>
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('1','온라인 홍보매체 게재 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart4_01" class="ui_chart" style="height:250px"></div>
							<script type="text/javascript">
									var chartOption = {
										"type": "serial",
										"categoryField": "category",
										"columnWidth": 0.7,
										"marginTop": 10,
										"colors": [
											"#4761a1"
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
												"bullet": "round",
												"bulletBorderThickness": 0,
												"bulletSize": 7,
												"id": "AmGraph-1",
												"lineThickness": 2,
												"title": "경제정책관",
												"valueField": "column-1"
											}
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
											"enabled": false,
											"align": "center",
											"autoMargins": false,
											"color": "#444444",
											"equalWidths": false,
											"spacing": 15,
											"markerLabelGap": 6,
											"markerSize": 11,
											"markerType": "circle",
											"valueText": "",
											"verticalGap": 0
										},
										"titles": [],
										"dataProvider": [
											
										]
									};
									var chart1 = AmCharts.makeChart("chart4_01", chartOption);
									chart1.addListener("clickGraphItem", function($e){										
										var codeList = "6@10/"+$("#s_sel04").val().replaceAll(",","@");
										var date = $e.item.dataContext.fulldate;
										
										var param = "sDate="+date+"&eDate="+date+"&codeList="+codeList+"&scope="+scope+"&keyword="+keyword;
										popupOpen( "../popup/rel_info.jsp" ,param);
									});
							</script>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 온라인 홍보매체 게제 현황 -->

				<!-- 채널별 게재 현황 -->
				<div class="ui_boxs_00">
					<section id="s_c3_2" class="ui_box_00">
						<h3><span class="ui_bullet_00">●</span>채널별 게재 현황</h3>
						<div class="title_rc">
							<strong>담당실국</strong>
							<div class="dcp">
								<select id="s_sel05" class="ui_hidden">
									<option value="" selected>전체</option>
									<%
									if(arr != null){
										JSONObject obj = null;
									for(int i=0 ; i<arr.length() ; i++){
										obj = (JSONObject)arr.get(i);
									%>
									<option value="<%=obj.getString("code") %>"><%=obj.getString("name") %></option>
									<%} }%>
								</select><label for="s_sel05" class="select_00"></label>
							</div>
							<strong>운영부서</strong>
							<div class="dcp">
								<select id="s_sel06" class="ui_hidden">								
								</select><label for="s_sel06" class="select_00">전체</label>
							</div>
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2','채널별 게재 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<!-- 채널 정보량/성향 현황 -->
							<div class="f_clear">
								<section class="fl" style="width:300px;">
									<div class="content">
										<div id="chart4_03" class="ui_chart" style="height:280px"></div>
										<script type="text/javascript">
												var chartOption = {
												"type": "pie",
												"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
												"innerRadius": "50%",
												"labelRadius": -17,
												"labelText": "[[percents]]%",
												"pullOutRadius": "0%",
												"radius": "45%",
												"startRadius": "0%",
												"colors": [
													"#3b5999",
													"#5bc17a",
													"#b13aae",
													"#01d6ff",
													"#fdd035",
													"#d12b10",
													"#ed7014"
												],
												"marginBottom": 0,
												"marginTop": 0,
												"pullOutDuration": 0,
												"startDuration": 0,
												"titleField": "category",
												"valueField": "column_1",
												"addClassNames": true,
												"color": "#FFFFFF",
												"percentPrecision": 0,
												"allLabels": [
													{
													"align": "center",
													"bold": true,
													"color": "#4761a1",
													"id": "Label-1",
													"size": 14,
													"text": "VOLUME",
													"y": 80
													},
													{
													"align": "center",
													"bold": true,
													"color": "#444444",
													"id": "Label-2",
													"size": 30,
													"text": "3,694",
													"y": 100
													}
												],
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
													"position": "bottom",
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
													{
													"category": "페이스북",
													"column_1": 25
													},
													{
													"category": "블로그",
													"column_1": 36
													},
													{
													"category": "인스타그램",
													"column_1": 36
													},
													{
													"category": "트위터",
													"column_1": 36
													},
													{
													"category": "카카오스토리",
													"column_1": 36
													},
													{
													"category": "유튜브",
													"column_1": 36
													},
													{
													"category": "카페",
													"column_1": 36
													}
												]
												};
												var chart2 = AmCharts.makeChart("chart4_03", chartOption);
												chart2.addListener("clickSlice", function($e){
													
													var codeList = "6@10/"+$("#s_sel06").val().replaceAll(",","@");
													var s_seq = $e.dataItem.dataContext.s_seq;
													
													var param = "sDate="+sDate+"&eDate="+eDate+"&codeList="+codeList+"&scope="+scope+"&keyword="+keyword+"&s_seq="+s_seq;
													
													
													popupOpen( "../popup/rel_info.jsp" , param);
												});
										</script>
									</div>
          
									<!-- Loader -->
									<div class="ui_loader v0"><span class="loader">Load</span></div>
									<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
									<!-- // Loader -->
								</section>

								<!-- 채널 성향 현황 -->
								<section class="fr" style="width:556px;">
									<div class="content">
										<div id="chart4_04" class="ui_chart" style="height:280px"></div>
										<script type="text/javascript">
												var chartOption = {
													"type": "serial",
													"categoryField": "category",
													"columnSpacing": 7,
													"columnWidth": 0.5,
													"marginTop": 10,
													"marginRight": 0,
													"colors": [
														"#4761a1"
													],
													"addClassNames": true,
													"pathToImages": "../asset/img/amchart/",
													"percentPrecision": 0,
													"categoryAxis": {
														"autoRotateAngle": 50,
														"autoRotateCount": 11,
														"autoWrap": true,
														"gridPosition": "start",
														"twoLineMode": true,
														"axisColor": "#D8D8D8",
														"centerLabels": true,
														"centerRotatedLabels": false,
														"color": "#444444",
														"gridAlpha": 0,
														"tickLength": 0,
														"titleFontSize": 12,
														"titleRotation": 0
													},
													"trendLines": [],
													"graphs": [
														{
															"balloonText": "<strong>[[title]]</strong> : [[value]]건",
															"bulletBorderThickness": 0,
															"fillAlphas": 1,
															"id": "AmGraph-1",
															"title": "Volume",
															"type": "column",
															"valueField": "column_1"
														}
													],
													"guides": [],
													"valueAxes": [
														{
															"id": "ValueAxis-1",
															"autoGridCount": false,
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
														"fadeOutDuration": 0,
														"fillAlpha": 0.67,
														"fixedPosition": false,
														"fontSize": 9,
														"horizontalPadding": 10,
														"pointerWidth": 5,
														"shadowAlpha": 0.3
													},
													"titles": [],
													"dataProvider": [
														{
															"category": "페이스북",
															"column_1": "25"
														},
														{
															"category": "블로그",
															"column_1": "12"
														},
														{
															"category": "인스타그램",
															"column_1": "35"
														},
														{
															"category": "트위터",
															"column_1": "35"
														},
														{
															"category": "카카오스토리",
															"column_1": "35"
														},
														{
															"category": "유튜브",
															"column_1": "35"
														},
														{
															"category": "카페",
															"column_1": "35"
														}
													]
												};
												var chart3 = AmCharts.makeChart( "chart4_04", chartOption );
												chart3.addListener("clickGraphItem", function( $e ){
													var codeList = "6@10/"+$("#s_sel06").val().replaceAll(",","@");
													var s_seq = $e.item.dataContext.s_seq;
													
													var param = "sDate="+sDate+"&eDate="+eDate+"&codeList="+codeList+"&scope="+scope+"&keyword="+keyword+"&s_seq="+s_seq;
													
													popupOpen( "../popup/rel_info.jsp" , param);
												});
										</script>
									</div>
									<!-- Loader -->
									<div class="ui_loader v0"><span class="loader">Load</span></div>
									<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
									<!-- // Loader -->
								</section>
								<!-- // 채널 성향 현황 -->
							</div>
							<!-- // 채널 정보량/성향 현황 -->
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!--// 채널별 게재 현황 -->

				<!-- 실국별 게재 현황 -->
				<div class="ui_boxs_00">
					<section id="s_c8_1" class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>실국별 게재 현황</h3>
						<div class="title_rc">
							<strong>담당실국</strong>
							<div class="dcp">
								<select id="s4_c_sel03" class="ui_hidden">
									<option value="" selected>전체</option>
									<%
									if(arr != null){
										JSONObject obj = null;
									for(int i=0 ; i<arr.length() ; i++){
										obj = (JSONObject)arr.get(i);
									%>
									<option value="<%=obj.getString("code") %>"><%=obj.getString("name") %></option>
									<%} }%>
								</select><label for="s4_c_sel03" class="select_00"></label>
							</div>
							<!-- <strong>출처</strong>
							<div class="dcp">
								<select id="s4_c_sel04" class="ui_hidden">
									<option selected>전체</option>
								</select><label for="s4_c_sel04" class="select_00"></label>
							</div> -->
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3','실국별 게재 현황'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart4_05" class="ui_chart none_click" style="height:250px"></div>
							<script type="text/javascript">
									var chartOption = {
										"type": "serial",
										"categoryField": "category",
										"columnSpacing": 7,
										"columnWidth": 0.5,
										"marginTop": 10,
										"marginRight": 0,
										"colors": [
											"#4761a1"
										],
										"addClassNames": true,
										"pathToImages": "../asset/img/amchart/",
										"percentPrecision": 0,
										"categoryAxis": {
											"autoRotateAngle": 50,
											"autoRotateCount": 11,
											"autoWrap": true,
											"gridPosition": "start",
											"twoLineMode": true,
											"axisColor": "#D8D8D8",
											"centerLabels": true,
											"centerRotatedLabels": false,
											"color": "#444444",
											"gridAlpha": 0,
											"tickLength": 0,
											"titleFontSize": 12,
											"titleRotation": 0
										},
										"trendLines": [],
										"graphs": [
											{
												"balloonText": "<strong>[[title]]</strong> : [[value]]건",
												"bulletBorderThickness": 0,
												"fillAlphas": 1,
												"id": "AmGraph-1",
												"title": "Volume",
												"type": "column",
												"valueField": "column-1"
											}
										],
										"guides": [],
										"valueAxes": [
											{
												"id": "ValueAxis-1",
												"autoGridCount": false,
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
											"fadeOutDuration": 0,
											"fillAlpha": 0.67,
											"fixedPosition": false,
											"fontSize": 9,
											"horizontalPadding": 10,
											"pointerWidth": 5,
											"shadowAlpha": 0.3
										},
										"titles": [],
										"dataProvider": [
											/* {
												"category": "여성가족정책과",
												"column-1": "25"
											},
											{
												"category": "홍보브랜드담당관",
												"column-1": "12"
											},
											{
												"category": "대구상수도사업본부",
												"column-1": "35"
											},
											{
												"category": "대구미술관",
												"column-1": "35"
											},
											{
												"category": "관광과",
												"column-1": "35"
											},
											{
												"category": "문화콘텐츠과",
												"column-1": "35"
											},
											{
												"category": "대구문화예술회관",
												"column-1": "35"
											},
											{
												"category": "대구오페라하우스",
												"column-1": "35"
											},
											{
												"category": "보건건강과",
												"column-1": "35"
											},
											{
												"category": "대구엑스코",
												"column-1": "35"
											},
											{
												"category": "대구예술발전소",
												"column-1": "35"
											},
											{
												"category": "정책기획관",
												"column-1": "35"
											},
											{
												"category": "도시재생과",
												"column-1": "35"
											},
											{
												"category": "대구도시철도공사",
												"column-1": "35"
											},
											{
												"category": "문화콘텐츠과",
												"column-1": "35"
											},
											{
												"category": "복지정책관",
												"column-1": "35"
											},
											{
												"category": "스마트시티과",
												"column-1": "35"
											},
											{
												"category": "스마트시티과",
												"column-1": "35"
											},
											{
												"category": "대구수목원관리사무소",
												"column-1": "35"
											},
											{
												"category": "대구시설공단",
												"column-1": "35"
											},
											{
												"category": "대구시의회",
												"column-1": "35"
											} */
										]
									};
									var chart4 = AmCharts.makeChart( "chart4_05", chartOption );
									
							</script>
							<p style="width: 100%;margin-bottom: 26px;">
							<button style="float:right" type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4','실국별 게재 현황'); return false;"><span class="icon excel">Excel Download</span></button>
							</p>
							<div class="ui_board_list_00">
								<table>
								<caption>관련정보 목록</caption>
								<colgroup>
								<col style="width:120px">
								<col style="width:100px;">
								<col>
								<col style="width:120px">
								<col style="width:150px">
								</colgroup>
								<thead>
								<tr>
								<th scope="col"><span>날짜</span></th>
								<th scope="col"><span>출처</span></th>
								<th scope="col"><span>제목</span></th>
								<th scope="col"><span>담당실국</span></th>
								<th scope="col"><span>운영부서</span></th>
								</tr>
								</thead>
								<tbody id="section03_list">
								<!-- 데이터 없는 경우
								<tr><td colspan="5" style="height:300px">데이터가 없습니다</td></tr>
								-->
								<tr>
								<td>2017-01-01</td>
								<td><span title="네이버">네이버블로그</span></td>
								<td class="title"><a href="#" target="_blank" title=""><span class="ui_bullet_cafe">cafe</span>섬유산업 미래 융복합 혁신기술의 방향 제시와 새로운 비즈니스 플랫폼</a></td>
								<td><span>문화체육관광국</span></td>
								<td><span title="관광과">관광과</span></td>
								</tr>
								<tr>
								<td>2017-01-01</td>
								<td><span title="네이버">네이버블로그</span></td>
								<td class="title"><a href="#" target="_blank" title=""><span class="ui_bullet_cafe">cafe</span>섬유산업 미래 융복합 혁신기술의 방향 제시와 새로운 비즈니스 플랫폼</a></td>
								<td><span>문화체육관광국</span></td>
								<td><span title="관광과">관광과</span></td>
								</tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5"></td></tr>
								</tbody>
								</table>
							</div>
							<div class="paginate" id="section03_Paging">
								<a href="#" class="page_first disabled">처음 페이지</a>		<!-- 버튼 활성화시 disabled 클래스 제거 -->
								<a href="#" class="page_prev disabled">이전페이지</a>		<!-- 버튼 활성화시 disabled 클래스 제거 -->
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
								<a href="#" class="page_next">다음페이지</a>
								<a href="#" class="page_last">마지막 페이지</a>
							</div>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 실국별 게재 현황 -->
			</div>
		</div>

		<!-- Include Footer -->
		<jsp:include page="../inc/inc_footer.jsp" flush="false" />
		<!-- // Include Footer -->
	</div>
</body>
</html>