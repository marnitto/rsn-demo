<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.realstate.RealstateMgr"
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

RealstateMgr rMgr = new RealstateMgr();

JSONArray arr = rMgr.getRealstateList();

%>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->

<body>
	<script type="text/javascript" src="realstate.js"></script>
	<script type="text/javascript">
		gnbIDX = "0300";
		setPageTitle( "대구시청 - 실국별 현황분석" );
		var pcode = "";

		$(function(){
			
			//getRealstateList();
			
			setTimeout(function(){
				pcode = $("[name=realstate01]:checked").val();
				getDvisionList(pcode);
				
				setTimeout(function(){
					hndl_search();	
				},100);				
				
				$("[name=realstate01]").change(function(){	
					$(".select_00").eq(1).text("전체");
					$(".select_00").eq(2).text("전체");
					$(".select_00").eq(3).text("전체");
					
					getDvisionList($(this).val());					
					setTimeout(function(){
						hndl_search();	
					},100);					
				});
				
				$("#s_sel01").change(function(){					
					section02();
				});
				
				$("#s_sel02").change(function(){					
					section03(1);
				});
				
				$("#s_sel03").change(function(){					
					section03(1);
				});
				
			},100);
			
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
				$(".ui_box_00").find(".v0").fadeIn(80);
				scope = $("#search_sel_01").val();
				keyword = $("#ui_search_input").val();
				sDate = $("#searchs_dp_start").val();
				eDate = $("#searchs_dp_end").val();		
				pcode = $("[name=realstate01]:checked").val();			
				
				section01();
				section02();
				section03(1);
				
			}

		}

		/* // Spart Chart Default Option
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
		}; */
	</script>

	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->

	<!-- Include HEADER -->
	<jsp:include page="../inc/inc_header.jsp" flush="false" />
	<!-- // Include HEADER -->

	<div class="page_wrap" id="s_top">
		<!-- Content Container -->
		<div id="container" class="">
			<div id="content">

				<h2 class="invisible">실국별 현황 분석</h2>

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

				<!-- 실국별 현황 분석 -->
				<div id="s_c3" class="ui_boxs_00">
					<div class="ui_tab_type01">
						<ul id="realstateList">
						
						<%
						if(arr != null){
							JSONObject obj = null;
							String checked = "";
						for(int i=0 ; i<arr.length() ; i++){
							obj = (JSONObject)arr.get(i);
							checked = "";
							if(i==0){checked = "checked";}
						%>
						<li>
								<input type="radio" name="realstate01" class="" value="<%=obj.getString("code") %>" id="tab_li_0<%=i+1%>" <%=checked%>>
								<div class="bg"></div>
								<span><%=obj.getString("name") %></span>
								<div><label for="tab_li_0<%=i+1%>"></label></div>
							</li>
						<%} }%>
							
							<!-- <li>
								<input type="radio" name="realstate01" class="" id="tab_li_02">
								<div class="bg"></div>
								<span>교통국</span>
								<div><label for="tab_li_02"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_03">
								<div class="bg"></div>
								<span>기획<br>조정실</span>
								<div><label for="tab_li_03"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_04">
								<div class="bg"></div>
								<span>녹색<br>환경국</span>
								<div><label for="tab_li_04"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_05">
								<div class="bg"></div>
								<span>도시재<br>창조국</span>
								<div><label for="tab_li_05"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_06">
								<div class="bg"></div>
								<span>도시<br>철도건설<br>본부</span>
								<div><label for="tab_li_06"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_07">
								<div class="bg"></div>
								<span>문화<br>체육<br>관광국</span>
								<div><label for="tab_li_07"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_08">
								<div class="bg"></div>
								<span>보건<br>복지국</span>
								<div><label for="tab_li_08"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_09">
								<div class="bg"></div>
								<span>상수도<br>사업<br>본부</span>
								<div><label for="tab_li_09"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_10">
								<div class="bg"></div>
								<span>소방<br>안전본부</span>
								<div><label for="tab_li_10"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_11">
								<div class="bg"></div>
								<span>시민<br>안전실</span>
								<div><label for="tab_li_11"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_12">
								<div class="bg"></div>
								<span>시민<br>행복<br>교육</span>
								<div><label for="tab_li_12"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_13">
								<div class="bg"></div>
								<span>여성<br>가족<br>청소년국</span>
								<div><label for="tab_li_13"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_14">
								<div class="bg"></div>
								<span>일자리<br>투자국</span>
								<div><label for="tab_li_14"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_15">
								<div class="bg"></div>
								<span>자치<br>행정국</span>
								<div><label for="tab_li_15"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_16">
								<div class="bg"></div>
								<span>통합<br>신공항<br>추진본부</span>
								<div><label for="tab_li_16"></label></div>
							</li>
							<li>
								<input type="radio" name="realstate01" class="" id="tab_li_17">
								<div class="bg"></div>
								<span>혁신<br>성장국</span>
								<div><label for="tab_li_17"></label></div>
							</li> -->
						</ul>
					</div>

					<section class="ui_box_00 ui_shadow_00 no_bdt">
						<h3 class="no_bd invisible"><span class="ui_bullet_00">●</span>경제국</h3>
						<div class="content p_t_20">
							<!-- 온라인 동향 -->
							<section id="s_c3_1" class="ui_box_00">
								<h4>온라인 동향</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('1','온라인동향'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<div id="chart_05" class="ui_chart" style="height:250px"></div>
									<script type="text/javascript">
										
											var chartOption = {
												"type": "serial",
												"categoryField": "category",
												"columnWidth": 0.7,
												"marginTop": 10,
												"colors": [
													"#4a80c1",
													"#726d6e",
													"#58b530",
													"#116e27",
													"#ecb900",
													"#a25a25",
													"#7258c8",
													"#e3458b"
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
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-2",
														"lineThickness": 2,
														"title": "기계로봇과",
														"valueField": "column-2"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-3",
														"lineThickness": 2,
														"title": "농산유통과",
														"valueField": "column-3"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-4",
														"lineThickness": 2,
														"title": "농수산물도매시장",
														"valueField": "column-4"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-5",
														"lineThickness": 2,
														"title": "대구농업기술센터",
														"valueField": "column-5"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-6",
														"lineThickness": 2,
														"title": "민생경제과",
														"valueField": "column-6"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-7",
														"lineThickness": 2,
														"title": "산단진흥과",
														"valueField": "column-7"
													},
													{
														"balloonText": "<strong>[[title]]</strong> : [[value]]",
														"bullet": "round",
														"bulletBorderThickness": 0,
														"bulletSize": 7,
														"id": "AmGraph-8",
														"lineThickness": 2,
														"title": "섬유패션과",
														"valueField": "column-8"
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
													"enabled": true,
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
													/* {
														"category": "06-01",
														"column-1": 46,
														"column-2": 26,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 22,
														"column-2": 61,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 16,
														"column-2": 29,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 70,
														"column-2": 66,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 51,
														"column-2": 13,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 67,
														"column-2": 38,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													},
													{
														"category": "06-01",
														"column-1": 76,
														"column-2": 88,
														"column-3": 16,
														"column-4": 29,
														"column-5": 50,
														"column-6": 10,
														"column-7": 8,
														"column-8": 11
													} */
												]
											};
											var chart1 = AmCharts.makeChart("chart_05", chartOption);
											chart1.addListener("clickGraphItem", function($e){
												var date = $e.item.dataContext.fulldate;
												var code = "51@";
												code += $e.graph.code;
												var param = "sDate="+date+"&eDate="+date+"&codeList="+code+"&scope="+scope+"&keyword="+keyword;
												popupOpen( "../popup/rel_info.jsp" ,param);
											});
										
									</script>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>

							<!-- 채널별 성향 -->
							<section id="s_c3_2" class="ui_box_00 m_t_20">
								<h4 class="no_bd">채널별 성향</h4>
								<div class="title_rc">
									<strong>부서</strong>
									<div class="dcp">
										<select id="s_sel01" class="ui_hidden">
											<!-- <option selected="">전체</option>
											<option>경제정책관</option>
											<option>기계로봇과</option>
											<option>농산유통과</option>
											<option>농수산물도매시장</option>
											<option>대구농업기술센터</option>
											<option>민생경제과</option>
											<option>산단진흥과</option>
											<option>섬유패션과</option> -->
										</select><label for="s_sel01" class="select_00">전체</label>
									</div>
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2','채널별 성향'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<!-- 채널 정보량/성향 현황 -->
									<div class="f_clear">
										<section class="fl">
											<div class="content" style="width:300px; height:280px">
												<div id="chart_02" class="ui_chart" style="height:280px"></div>
												<script type="text/javascript">
													
														var chartOption = {
															"type": "pie",
															"balloonText": "<strong>[[category]]</strong><br>[[value]]건",
															"innerRadius": "50%",
															"labelRadius": -17,
															"labelText": "[[percents]]%",
															"pullOutRadius": "0%",
															"radius": "35%",
															"startRadius": "0%",
															"colors": [
																/*"#3b5999",
																"#5bc17a",
																"#b13aae",
																"#01d6ff",
																"#fdd035",
																"#d12b10",
																"#ed7014"
																*/
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
																	"y": 100
																},
																{
																	"align": "center",
																	"bold": true,
																	"color": "#444444",
																	"id": "Label-2",
																	"size": 30,
																	"text": "0",
																	"y": 120
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
														var chart2 = AmCharts.makeChart("chart_02", chartOption);
														chart2.addListener("clickSlice", function($e){
															var senti = "9@"+$e.dataItem.dataContext.code;
															var code = $("#s_sel01 option:selected").val().replaceAll(",","@");												
															
															var codeList = senti+"/"+code;
															
															var param = "sDate="+sDate+"&eDate="+eDate+"&codeList="+codeList+"&scope="+scope+"&keyword="+keyword;
															popupOpen( "../popup/rel_info.jsp" ,param);
														});
													
												</script>
											</div>

											<!-- Loader -->
											<div class="ui_loader v0"><span class="loader">Load</span></div>
											<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
											<!-- // Loader -->
										</section>

										<!-- 채널 성향 현황 -->
										<section class="fr">
											<div class="content" style="width:556px;height:280px">
												<div id="chart_03" class="ui_chart" style="height:280px;"></div>
												<script type="text/javascript">
													
														var chartOption = {
															"type": "serial",
															"categoryField": "category",
															"columnWidth": 0.4,
															"marginBottom": 0,
															"marginTop": 20,
															"colors": [
																"#5ba1e0",
																"#ea7070",
																"#888888"
															],
															"addClassNames": true,
															"pathToImages": "../asset/img/amchart/",
															"percentPrecision": 0,
															"categoryAxis": {
																"autoRotateAngle": 45,
																"autoRotateCount": 12,
																"autoWrap": true,
																"gridPosition": "start",
																"twoLineMode": true,
																"axisColor": "#D8D8D8",
																"color": "#444444",
																"gridAlpha": 0,
																"tickLength": 0,
																"minHorizontalGap": 0
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
																	"code": "1",
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
																	"code": "2",
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
																	"code": "3",
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
																	"category": "언론",
																	"column-1": 46,
																	"column-2": 46,
																	"column-3": 91
																},
																{
																	"category": "포탈",
																	"column-1": 22,
																	"column-2": 61,
																	"column-3": 48
																},
																{
																	"category": "블로그",
																	"column-1": 16,
																	"column-2": 29,
																	"column-3": 68
																},
																{
																	"category": "카페",
																	"column-1": 70,
																	"column-2": 66,
																	"column-3": 5
																},
																{
																	"category": "커뮤니티",
																	"column-1": 51,
																	"column-2": 13,
																	"column-3": 88
																},
																{
																	"category": "트위터",
																	"column-1": 67,
																	"column-2": 38,
																	"column-3": 4
																},
																{
																	"category": "페이스북",
																	"column-1": 76,
																	"column-2": 88,
																	"column-3": 62
																},
																{
																	"category": "대구광역시",
																	"column-1": 76,
																	"column-2": 88,
																	"column-3": 62
																} */
															]
														};
														var chart3 = AmCharts.makeChart("chart_03", chartOption);
														chart3.addListener("clickGraphItem", function($e){
															var type6 = "6@"+$e.item.dataContext.code;
															
															var senti = "9@"+$e.graph.code;
															var code = $("#s_sel01 option:selected").val().replaceAll(",","@");												
															
															var codeList = type6+"/"+senti+"/"+code;
															
															var param = "sDate="+sDate+"&eDate="+eDate+"&codeList="+codeList+"&scope="+scope+"&keyword="+keyword;
															popupOpen( "../popup/rel_info.jsp" ,param);
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

							<!-- 관련정보 -->
							<section id="s_c3_3" class="ui_box_00 m_t_20">
								<h4 class="no_bd">관련정보</h4>
								<div class="title_rc">
									<strong>성향</strong>
									<div class="dcp">
										<select id="s_sel02" class="ui_hidden">
											<option value="" selected>전체</option>
											<option value="1" >긍정</option>
											<option value="2" >부정</option>
											<option value="3" >중립</option>
										</select><label for="s_sel02" class="select_00"></label>
									</div>
									<strong>부서</strong>
									<div class="dcp">
										<select id="s_sel03" class="ui_hidden">
											<option selected>전체</option>
										</select><label for="s_sel03" class="select_00"></label>
									</div>
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3','관련정보'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<!--관련정보 목록 -->
									<div class="ui_boxs_00">
										<section id="s_c8_1" class="">
											<div class="content">
												<div class="ui_board_list_00">
													<table>
													<caption>관련정보 목록</caption>
													<colgroup>
													<col style="width:120px">
													<col>
													<col style="width:100px;">
													<col style="width:70px">
													<col style="width:150px">
													</colgroup>
													<thead>
													<tr>
													<th scope="col"><span>날짜</span></th>
													<th scope="col"><span>제목</span></th>
													<th scope="col"><span>출처</span></th>
													<th scope="col"><span>성향</span></th>
													<th scope="col"><span>부서</span></th>
													</tr>
													</thead>
													<tbody id="section03_list">
													
													<tr><td colspan="5" style="height:300px">데이터가 없습니다</td></tr>
													
													<!-- <tr>
													<td>2017-01-01</td>
													<td class="title"><a href="#" target="_blank" title="">섬유산업 미래 융복합 혁신기술의 방향 제시와 새로운 비즈니스 플랫폼</a></td>
													<td><span title="네이버">네이버블로그</span></td>
													<td><span>중립</span></td>
													<td><span title="경제정책관">경제정책관</span></td>
													</tr>
													<tr>
													<td>2017-01-01</td>
													<td class="title"><a href="#" target="_blank" title="">섬유산업 미래 융복합 혁신기술의 방향 제시와 새로운 비즈니스 플랫폼</a></td>
													<td><span title="네이버">네이버블로그</span></td>
													<td><span>중립</span></td>
													<td><span title="대구농업기술센터">대구농업기술센터</span></td>
													</tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr>
													<tr><td colspan="5"></td></tr> -->
													</tbody>
													</table>
												</div>
												<div class="paginate" id="section03_Paging">
													<!-- <a href="#" class="page_first disabled">처음 페이지</a>		버튼 활성화시 disabled 클래스 제거
													<a href="#" class="page_prev disabled">이전페이지</a>		버튼 활성화시 disabled 클래스 제거
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
													<a href="#" class="page_last">마지막 페이지</a> -->
												</div>
											</div>

											<!-- Loader -->
											<div class="ui_loader v0"><span class="loader">Load</span></div>
											<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
											<!-- // Loader -->
										</section>
									</div>
									<!-- // 관련정보 목록 -->
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>

							<!-- <script type="text/javascript">
								// http://mistic100.github.io/jQCloud/ - 태그 클라우드 사용법 : 좌측 URL 참조
								$( "#tag_cloud_01" ).each( function(){
									var words =[
										{text: "ITEM 01", weight: 25, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 02", weight: 24, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 03", weight: 23, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 04", weight: 22, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 05", weight: 21, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 06", weight: 20, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 07", weight: 19, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 08", weight: 18, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 09", weight: 17, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 10", weight: 16, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 11", weight: 15, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 12", weight: 14, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 13", weight: 13, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 14", weight: 12, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 15", weight: 11, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 16", weight: 10, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 17", weight: 9, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 18", weight: 8, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 19", weight: 7, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 20", weight: 6, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" }
									];
									$( this ).jQCloud( words, {
										autoResize: true
									});
								});
								$( "#tag_cloud_02" ).each( function(){
									var words =[
										{text: "ITEM 01", weight: 25, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 02", weight: 24, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 03", weight: 23, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 04", weight: 22, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 05", weight: 21, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 06", weight: 20, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 07", weight: 19, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 08", weight: 18, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 09", weight: 17, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 10", weight: 16, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 11", weight: 15, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 12", weight: 14, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 13", weight: 13, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 14", weight: 12, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 15", weight: 11, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 16", weight: 10, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 17", weight: 9, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 18", weight: 8, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 19", weight: 7, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 20", weight: 6, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" }
									];
									$( this ).jQCloud( words, {
										autoResize: true
									});
								});
								$( "#tag_cloud_03" ).each( function(){
									var words =[
										{text: "ITEM 01", weight: 25, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 02", weight: 24, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 03", weight: 23, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 04", weight: 22, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 05", weight: 21, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 06", weight: 20, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 07", weight: 19, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 08", weight: 18, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 09", weight: 17, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 10", weight: 16, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 11", weight: 15, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 12", weight: 14, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 13", weight: 13, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 14", weight: 12, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 15", weight: 11, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 16", weight: 10, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 17", weight: 9, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 18", weight: 8, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 19", weight: 7, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" },
										{text: "ITEM 20", weight: 6, link : "javascript:popupOpen( '../popup/rel_info.jsp', 'a=1' )" }
									];
									$( this ).jQCloud( words, {
										autoResize: true
									});
								});
							</script> -->
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
					</section>
				</div>
				<!-- // 실국별 현황 분석 -->

			</div>
		</div>

		<!-- Include Footer -->
		<jsp:include page="../inc/inc_footer.jsp" flush="false" />
		<!-- // Include Footer -->
	</div>
</body>
</html>