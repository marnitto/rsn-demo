<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->

<body>
	<script type="text/javascript" src="search_new.js"></script>
	<script type="text/javascript">
		gnbIDX = "0500";
		setPageTitle( "대구광역시 - 검색" );
		
		$(document).ready(function(){
			$("#searchs_chk_00").click();
			$( "#searchs_input_01_01" ).val("대구광역시");
			$( "#searchs_input_02_01" ).val("서문시장");
			//$( "#searchs_input_03_01" ).val("대구광역시장");
			
			
			$("#tw_chk").click(function(){
				if($(this).prop("checked")){
					$("[name=searchs_chk_00]").each(function(){
						
						if( $(this).val() == 'TW'){
							$(this).prop("checked", true);
						}else{
							$(this).prop("checked", false);	
						}
					});
					
				}else{
					$("[name=searchs_chk_00]").prop("checked", true);
				}
			});
						
			deleteKeyword(3);
			hndl_search();
			
			$(".keyword_group > .row > .ui_input_00").keypress(function(e){
		        if (e.which == 13) {
		        	hndl_search();
		        }
			});
			
		});

		// 검색
		function hndl_search(){
			sel_sDate = $( "#searchs_dp_start" ).val();				// 시작 날짜
			sel_eDate = $( "#searchs_dp_end" ).val();				// 종료 날짜

			//console.log( "Date Change  >>  " + sel_sDate + " ~ " + sel_eDate );
			
			var channelChk = "";
			$("input[name='searchs_chk_00']:checked").not("#searchs_chk_00").each(function(){
				channelChk += $(this).val() + " ";
			});
			channelChk = channelChk.replace(/ $/, "");
			
			if( $("[name=tw_chk]").prop("checked") ){
				if(channelChk != "TW"){
					alert("트튀터 RT포함 수치는 트위터 채널 전용 입니다. 채널을 트위터만 선택해주세요.");
					return;
				}
			}
			
			if(($( "#searchs_input_01_01" ).val()=="" && $( "#searchs_input_01_02" ).val()=="" && $( "#searchs_input_01_03" ).val()=="") ){
				alert("검색 키워드를 입력하세요.");
			}else if(channelChk==""){
				alert("채널을 선택하세요.");
			}else{
				
				//검색 시작
				$(".ui_box_00").find(".v0").fadeIn(80);
				
				setSearchOption(channelChk);		//검색 설정 세팅

				section01();			// 실시간 검색 데이터
				section02();			// 정보검색
				
				// 감성현황(정보 분포)
				
				for(var i=1; i < 4; i++){
					if( getKeyword(i) == "" ){
						$("#s_c2_"+i).css("display", "none");
						$("#s_c3_"+i).css("display", "none");
						$("#s_c4_"+i).css("display", "none");
						$("#s_c5_"+i).css("display", "none");
					}else{
						//정보 분포
						$("#s_c2_"+i).css("display", "block");
						$("#s_c2_"+i+" > h4").text(getKeyword(i));
						section03_1(i, getKeywordParam(i));
						
						//감성 추이	
						$("#s_c3_"+i).css("display", "block");
						$("#s_c3_"+i+" > h4").text(getKeyword(i));
						section03_2(i, getKeywordParam(i));
						
						//채널별 추이
						$("#s_c4_"+i).css("display", "block");
						$("#s_c4_"+i+" > h4").text(getKeyword(i));
						section03_3(i, getKeywordParam(i));
						
						//연관키워드
						$("#s_c5_"+i).css("display", "block");
						$("#s_c5_"+i+" > h4").text(getKeyword(i));
						section04(i, i, "30");
					}
				}
				
				// 관련정보
				var htmlTab = "";
				$("#s_c4_text").html("<span class='ui_bullet_00'>●</span>관련정보");
				if( getKeyword(1) != '' ){
					htmlTab += "<li><input id='ri_radio_00' type='radio' name='ri_radio' value='" + 1 + "'><label for='ri_radio_00'><span>" + getKeyword(1) + "</span></label></li>";	
				}
				if( getKeyword(2) != '' ){
					htmlTab += "<li><input id='ri_radio_01' type='radio' name='ri_radio' value='" + 2 + "'><label for='ri_radio_01'><span>" + getKeyword(2) + "</span></label></li>";	
				}
				if( getKeyword(3) != '' ){
					htmlTab += "<li><input id='ri_radio_02' type='radio' name='ri_radio' value='" + 3 + "'><label for='ri_radio_02'><span>" + getKeyword(3) + "</span></label></li>";	
				}
			    
			    $("#s_c6_1 > .content > .ui_tab_00").html(htmlTab);
			    $("#ri_radio_00").prop("checked", true);
				section05(1, getKeywordParam(1));
				
				$("input[name=ri_radio]").click(function(){
			    	if($(this).val()=="1"){
			    		section05(1, getKeywordParam(1));
			    	}else if($(this).val()=="2"){
			    		section05(1, getKeywordParam(2));
			    	}else if($(this).val()=="3"){
			    		section05(1, getKeywordParam(3));
			    	}
				});
				
			}
			
		}
		
		function setSearchOption(channelChk){
			global_keyword01_1 = $( "#searchs_input_01_01" ).val();
			global_keyword01_2 = $( "#searchs_input_01_02" ).val();
			global_keyword01_3 = $( "#searchs_input_01_03" ).val();
			global_keyword01_4 = $( "#searchs_input_01_04" ).val();
			
			global_keyword02_1 = $( "#searchs_input_02_01" ).val();
			global_keyword02_2 = $( "#searchs_input_02_02" ).val();
			global_keyword02_3 = $( "#searchs_input_02_03" ).val();
			global_keyword02_4 = $( "#searchs_input_02_04" ).val();
			
			global_keyword03_1 = $( "#searchs_input_03_01" ).val();
			global_keyword03_2 = $( "#searchs_input_03_02" ).val();
			global_keyword03_3 = $( "#searchs_input_03_03" ).val();
			global_keyword03_4 = $( "#searchs_input_03_04" ).val();

			global_sDate  = $( "#searchs_dp_start" ).val();
			global_eDate = $( "#searchs_dp_end" ).val();
			global_channel = channelChk;
		}
		
		function deleteKeyword(target){
			
			$("#searchKeyword"+target).css("display","none");
			$( "#searchs_input_0"+target+"_01" ).val('');
			$( "#searchs_input_0"+target+"_02" ).val('');
			$( "#searchs_input_0"+target+"_03" ).val('');
			$( "#searchs_input_0"+target+"_04" ).val('');
			
		}
		
		function addKeyword(){
			
			var noneLength = 0;
			
			$(".keyword_set").each(function(idx){
				
				if( $(this).css("display") == 'none' ){
					$(this).css("display", "");
					noneLength++;
					return false;
				}
				
			});
			
			if(noneLength == 0 ){
				alert("검색 키워드는 최대 3개까지만 추가 가능 합니다.");
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
		<div id="container" class="p_search">
			<div id="content">

				<h2 class="invisible">대체식품</h2>

				<!-- Search -->
				<section class="searchs ui_shadow_00">
					<h3>검색 조건 설정</h3>
					<form action="#">
						<div class="searchs_container">
							<div class="wrap">
								<table>
								<caption>검색 조건</caption>
								<colgroup>
								<col style="width:100px">
								<col>
								<col style="width:100px">
								</colgroup>
								<tbody>
								<tr>
								<th scope="row">&middot; 검색 키워드</th>
								<td>
									<div class="keyword_group">
										<div class="row">
											<input id="searchs_input_01_01" type="text" class="ui_input_00" placeholder="중요단어"><label for="searchs_input_01_01" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (AND 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_01_02" type="text" class="ui_input_00" placeholder="정확하게 일치"><label for="searchs_input_01_02" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기를 포함한 정확한 검색 (구문 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_01_03" type="text" class="ui_input_00" placeholder="단어 중 아무거나 포함"><label for="searchs_input_01_03" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (OR 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_01_04" type="text" class="ui_input_00" placeholder="단어 제외"><label for="searchs_input_01_04" class="invisible">정확하게 일치</label>
											<span class="fs11">- 제외단어 설정 (콤마로 복수 설정)</span>
										</div>
									</div>
								</td>
								<td>
									<button type="button" class="ui_btn_key_add ui_wid100p vt" onclick="addKeyword();"; ><span class="icon">-</span><span class="txt">추가</span></button>
									<button type="button" class="ui_btn_search ui_wid100p vt" onclick="hndl_search();" ><span class="icon">-</span><span class="txt">검색</span></button>
								</td>
								</tr>
								<!-- 추가된 키워드 -->
								<tr id="searchKeyword2" class="keyword_set">
								<th scope="row">&middot; 검색 키워드</th>
								<td>
									<div class="keyword_group">
										<button type="button" class="ui_btn_k_del" onclick="deleteKeyword(2);"><span class="icon">-</span></button>
										<div class="row">
											<input id="searchs_input_02_01" type="text" class="ui_input_00" placeholder="중요단어"><label for="searchs_input_02_01" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (AND 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_02_02" type="text" class="ui_input_00" placeholder="정확하게 일치"><label for="searchs_input_02_02" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기를 포함한 정확한 검색 (구문 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_02_03" type="text" class="ui_input_00" placeholder="단어 중 아무거나 포함"><label for="searchs_input_02_03" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (OR 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_02_04" type="text" class="ui_input_00" placeholder="단어 제외"><label for="searchs_input_02_04" class="invisible">정확하게 일치</label>
											<span class="fs11">- 제외단어 설정 (콤마로 복수 설정)</span>
										</div>
									</div>
								</td>
								<td></td>
								</tr>
								<tr id="searchKeyword3" class="keyword_set">
								<th scope="row">&middot; 검색 키워드</th>
								<td>
									<div class="keyword_group">
										<button type="button" onclick="deleteKeyword(3);" class="ui_btn_k_del"><span class="icon">-</span></button>
										<div class="row">
											<input id="searchs_input_03_01" type="text" class="ui_input_00" placeholder="중요단어"><label for="searchs_input_03_01" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (AND 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_03_02" type="text" class="ui_input_00" placeholder="정확하게 일치"><label for="searchs_input_03_02" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기를 포함한 정확한 검색 (구문 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_03_03" type="text" class="ui_input_00" placeholder="단어 중 아무거나 포함"><label for="searchs_input_03_03" class="invisible">정확하게 일치</label>
											<span class="fs11">- 띄어쓰기로 검색어 구분 (OR 검색)</span>
										</div>
										<div class="row">
											<input id="searchs_input_03_04" type="text" class="ui_input_00" placeholder="단어 제외"><label for="searchs_input_03_04" class="invisible">정확하게 일치</label>
											<span class="fs11">- 제외단어 설정 (콤마로 복수 설정)</span>
										</div>
									</div>
								</td>
								<td></td>
								</tr>
								<tr>
								<td colspan="3"><hr></td>
								</tr>
								<!-- // 추가된 키워드 -->
								<tr>
								<th scope="row">&middot; 기간 설정</th>
								<td colspan="2" style="padding-left:0">
									<div class="ui_search_date">
										<div class="ui_datepicker_input_range">
											<input id="searchs_date_range" type="text" class="date_result" readonly="" value=""><label for="searchs_date_range" class="invisible">검색기간</label>		<!-- 선택시 input에 'active'클래스 추가 -->
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
									</div>
									<script type="text/javascript">
										// 날짜 설정
										var sel_sDate = getBeforeDay(getToday(), 6);		// 시작 날짜 설정
										var sel_eDate = getToday();							// 종료 날짜 설정
									</script>
								</td>
								</tr>
								<tr>
								<th scope="row">&middot; 채널 선택</th>
								<td colspan="2" style="padding-left:0">
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_00" name="searchs_chk_00" class="boardAllChecker"><label for="searchs_chk_00"><strong>전체</strong></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_01" name="searchs_chk_00" value="TW" ><label for="searchs_chk_01"><span>트위터</span></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_02" name="searchs_chk_00" value="FA" ><label for="searchs_chk_02"><span>페이스북</span></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_03" name="searchs_chk_00" value="BL" ><label for="searchs_chk_03"><span>블로그</span></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_04" name="searchs_chk_00" value="DC" ><label for="searchs_chk_04"><span>커뮤니티</span></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_05" name="searchs_chk_00" value="CF" ><label for="searchs_chk_05"><span>카페</span></label></div>
									<div class="dcp m_r_15"><input type="checkbox" id="searchs_chk_06" name="searchs_chk_00" value="DN" ><label for="searchs_chk_06"><span>언론</span></label></div>
									<div class="dcp"><input type="checkbox" id="searchs_chk_08" name="searchs_chk_00" value="PR"><label for="searchs_chk_08"><span>포탈댓글</span></label></div>
								</td>
								</tr>
								<tr>
									<th scope="row">&middot; 트위터 설정</th>
									<td colspan="2" style="padding-left:0">
										<div class="dcp m_r_15"><input type="checkbox" id="tw_chk" name="tw_chk" value="1" ><label for="tw_chk"><span>트위터 RT 포함</span></label></div>
									</td>
								</tr>
								</tbody>
								</table>
							</div>
						</div>
					</form>
				</section>
				<!-- // Search -->

				<!-- 실시간 검색 데이터 -->
				<div class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00 f_clear rt_portal_data">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>실시간 검색 데이터</h3>
						<div class="content">
							<!-- 네이버 -->
							<section class="ui_box_00 fl" style="width:449px">
								<h4 class="no_bd">네이버</h4>
								<div class="title_rc">
									<span><img src="../asset/img/naver.gif" alt="네이버"></span>
								</div>
								<div class="content" style="height:365px">
									<div class="ui_board_list_00 naver">
										<table id="section01_1_contents">
										<caption>관련정보 목록</caption>
										<colgroup>
										<col style="width:60px">
										<col>
										</colgroup>
										<thead>
										<tr>
										<th scope="col"><span>Rank</span></th>
										<th scope="col"><span>키워드</span></th>
										</tr>
										</thead>
										<tbody>
										<tr><td>1</td><td><a href="#"><strong>인서전트</strong></a></td></tr>
										<tr><td>2</td><td><a href="#"><strong>정유라 패딩</strong></a></td></tr>
										<tr><td>3</td><td><a href="#"><strong>심이영</strong></a></td></tr>
										<tr><td>4</td><td><a href="#"><strong>정미홍</strong></a></td></tr>
										<tr><td>5</td><td><a href="#"><strong>최원영</strong></a></td></tr>
										<tr><td>6</td><td><a href="#"><strong>일라이</strong></a></td></tr>
										<tr><td>7</td><td><a href="#"><strong>막돼먹은 영애씨</strong></a></td></tr>
										<tr><td>8</td><td><a href="#"><strong>라스푸틴</strong></a></td></tr>
										<tr><td>9</td><td><a href="#"><strong>기각</strong></a></td></tr>
										<tr><td>10</td><td><a href="#"><strong>우주소녀</strong></a></td></tr>
										</tbody>
										</table>
									</div>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<!-- // Loader -->
							</section>
							<!-- // 네이버 -->

							<!-- 다음 -->
							<section class="ui_box_00 fr" style="width:449px">
								<h4 class="no_bd">다음</h4>
								<div class="title_rc">
									<span><img src="../asset/img/daum.gif" alt="다음"></span>
								</div>
								<div class="content" style="height:365px">
									<div class="ui_board_list_00 daum">
										<table id="section01_2_contents">
										<caption>관련정보 목록</caption>
										<colgroup>
										<col style="width:40px">
										<col>
										</colgroup>
										<thead>
										<tr>
										<th scope="col"><span>Rank</span></th>
										<th scope="col"><span>키워드</span></th>
										</tr>
										</thead>
										<tbody>
										<tr><td>1</td><td><a href="#"><strong>정미홍</strong></a></td></tr>
										<tr><td>2</td><td><a href="#"><strong>정유라 패딩</strong></a></td></tr>
										<tr><td>3</td><td><a href="#"><strong>안희정</strong></a></td></tr>
										<tr><td>4</td><td><a href="#"><strong>심이영</strong></a></td></tr>
										<tr><td>5</td><td><a href="#"><strong>한국사 능력시험</strong></a></td></tr>
										<tr><td>6</td><td><a href="#"><strong>인간 광우병</strong></a></td></tr>
										<tr><td>7</td><td><a href="#"><strong>정갑윤</strong></a></td></tr>
										<tr><td>8</td><td><a href="#"><strong>안재현</strong></a></td></tr>
										<tr><td>9</td><td><a href="#"><strong>신동욱</strong></a></td></tr>
										<tr><td>10</td><td><a href="#"><strong>이세영</strong></a></td></tr>
										</tbody>
										</table>
									</div>
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<!-- // Loader -->
							</section>
							<!-- // 다음 -->

						</div>
					</section>
				</div>
				<!-- // 실시간 검색 데이터 -->

				<!-- Side Navigator -->
				<aside id="navigator">
					<a href="#s_top"><span class="icon">-</span><span class="txt">TOP</span></a>
					<ul>
						<li><a href="#s_c1" title="정보검색">정보검색</a></li>
						<li><a href="#s_c2" title="감성현황">감성현황</a></li>
						<li><a href="#s_c3" title="연관키워드">연관키워드</a></li>
						<li><a href="#s_c4" title="관련정보">관련정보</a></li>
					</ul>
					<a href="#s_end"><span class="txt">END</span><span class="icon">-</span></a>
				</aside>
				<!-- // Side Navigator -->

				<!-- 정보검색 -->
				<div id="s_c1" class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00">
						<h3><span class="ui_bullet_00">●</span>정보검색</h3>
						<div class="title_rc">
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('2', '정보검색'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<div id="chart_01" class="ui_chart" style="height:300px">
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
												"#00cc9e",
												"#c055e3"
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
													"bullet": "round",
													/* "fillAlphas": 1, */
													"id": "AmGraph-1",													
													"markerType": "circle",
													"title": "",
													"type": "line",
													"valueField": "column-1"
												},
												{
													"balloonText": "<strong>[[title]]</strong> : [[value]]",
													"bulletBorderThickness": 0,
													"bulletSize": 5,
													"bullet": "round",
													/* "fillAlphas": 1, */
													"id": "AmGraph-2",
													"markerType": "circle",
													"title": "",
													"type": "line",
													"valueField": "column-2"
												},
												{
													"balloonText": "<strong>[[title]]</strong> : [[value]]",
													"bulletBorderThickness": 0,
													"bulletSize": 5,
													"bullet": "round",
													/* "fillAlphas": 1, */
													"id": "AmGraph-3",
													"markerType": "circle",
													"title": "",
													"type": "line",
													"valueField": "column-3"
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
											"chartScrollbar": {
												"enabled": true,
												"dragIconHeight": 25,
												"dragIconWidth": 25,
												"offset": 20,
												"scrollbarHeight": 8
											},
											"titles": [],
											"dataProvider": []
										};
										chart1 = AmCharts.makeChart("chart_01", chartOption);
										chart1.addListener("clickGraphItem", function(event){
											var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
											var field = event.item.graph.valueField;
											var param = "i_sdate=" + date + "&i_edate=" +  date;
											if(field=="column-1"){
												param +=  "&i_and_text=" +global_keyword01_1 + "&i_exact_text=" + global_keyword01_2
													+ "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4 + "&i_sourcetype=" + global_channel;
												
											}else if(field=="column-2"){
												param +=  "&i_and_text=" +global_keyword02_1 + "&i_exact_text=" + global_keyword02_2
												+ "&i_or_text=" + global_keyword02_3 + "&i_exclude_text=" + global_keyword02_4 + "&i_sourcetype=" + global_channel;
												
											}else if(field=="column-3"){
												param +=  "&i_and_text=" +global_keyword03_1 + "&i_exact_text=" + global_keyword03_2
												+ "&i_or_text=" + global_keyword03_3 + "&i_exclude_text=" + global_keyword03_4 + "&i_sourcetype=" + global_channel;
												
											}
												
											popupOpen( "../popup/rel_info_lucy.jsp?" + param);
										
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
				</div>
				<!-- // 정보검색 -->

				<!-- 감성현황 -->
				<div id="s_c2" class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>감성현황</h3>
						<div class="content">

							<!-- 정보 분포 -->
							<section class="ui_box_00">
								<h4 class="no_bd">정보 분포</h4>
								<div class="content">
									<section id="s_c2_1" class="ui_box_00">
										<h4>검색키워드</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s1_1', '정보 분포'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c2_1_1" class="ui_chart"></div>
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
													chart2 = AmCharts.makeChart("chart_c2_1_1", chartOption);
													chart2.addListener("clickSlice", function(event){
														var senti = event.dataItem.dataContext.category;
														var param = getKeywordParam(1);
														
														if(senti=="긍정"){
															param += "&i_trend=1";
														}else if(senti=="부정"){
															param += "&i_trend=2";
														}else if(senti=="중립"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
													});
												})();
											</script>
											<div id="chart_c2_1_2" class="ui_chart"></div>
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
																"minVerticalGap": 0,
																/* "autoWrap": true, */
																"gridPosition": "start",
																/* "twoLineMode": true, */
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
													chart3 = AmCharts.makeChart("chart_c2_1_2", chartOption);
													chart3.addListener("clickGraphItem", function(event){
														var channel = event.item.dataContext.channel;
														var field = event.item.graph.valueField;
														var param = "";
														
														param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" +  global_eDate.replace(/-/gi, "") + "&i_and_text=" +global_keyword01_1 + "&i_exact_text=" + global_keyword01_2
														+ "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4 + "&i_sourcetype=" + channel;	
														
														if(field=="column-1"){
															param += "&i_trend=1";
														}else if(field=="column-2"){
															param += "&i_trend=2";
														}else if(field=="column-3"){
															param += "&i_trend=3";
														}
														
														
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
													});
											})();
										</script>
										</div>

										<!-- Loader -->
										<div class="ui_loader v0"><span class="loader">Load</span></div>
										<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
										<!-- // Loader -->
									</section>

									<section id="s_c2_2" class="ui_box_00 m_t_20">
										<h4>검색키워드 2</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s1_2', '정보 분포'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c2_2_1" class="ui_chart"></div>
											<script type="text/javascript">
												var chart4;				
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
													chart4 = AmCharts.makeChart("chart_c2_2_1", chartOption);
													chart4.addListener("clickSlice", function(event){
														var senti = event.dataItem.dataContext.category;
														var param = getKeywordParam(2);
														
														if(senti=="긍정"){
															param += "&i_trend=1";
														}else if(senti=="부정"){
															param += "&i_trend=2";
														}else if(senti=="중립"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
													});
												})();
											</script>	
											<div id="chart_c2_2_2" class="ui_chart"></div>
											<script type="text/javascript">
												var chart5;
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
												chart5 = AmCharts.makeChart("chart_c2_2_2", chartOption);
												chart5.addListener("clickGraphItem", function(event){
													var channel = event.item.dataContext.channel;
													var field = event.item.graph.valueField;
													var param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" +  global_eDate.replace(/-/gi, "") + "&i_and_text=" +global_keyword02_1 + "&i_exact_text=" + global_keyword02_2
													+ "&i_or_text=" + global_keyword02_3 + "&i_exclude_text=" + global_keyword02_4 + "&i_sourcetype=" + channel;
													
													if(field=="column-1"){
														param += "&i_trend=1";
													}else if(field=="column-2"){
														param += "&i_trend=2";
													}else if(field=="column-3"){
														param += "&i_trend=3";
													}
													popupOpen( "../popup/rel_info_lucy.jsp?" + param);
												});
											})();
										</script>
										</div>

										<!-- Loader -->
										<div class="ui_loader v0"><span class="loader">Load</span></div>
										<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
										<!-- // Loader -->
									</section>

									<section id="s_c2_3" class="ui_box_00 m_t_20">
										<h4>검색키워드 3</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s1_3', '정보 분포'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c2_3_1" class="ui_chart">
												<script type="text/javascript">
												var chart6;				
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
													chart6 = AmCharts.makeChart("chart_c2_3_1", chartOption);
													chart6.addListener("clickSlice", function(event){
														var senti = event.dataItem.dataContext.category;
														var param = getKeywordParam(3);
														
														if(senti=="긍정"){
															param += "&i_trend=1";
														}else if(senti=="부정"){
															param += "&i_trend=2";
														}else if(senti=="중립"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
													});
												})();
											</script>	
											</div>
											<div id="chart_c2_3_2" class="ui_chart">
											<script type="text/javascript">
												var chart7;
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
												chart7 = AmCharts.makeChart("chart_c2_3_2", chartOption);
												chart7.addListener("clickGraphItem", function(event){
													var channel = event.item.dataContext.channel;
													var field = event.item.graph.valueField;
													var param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" +  global_eDate.replace(/-/gi, "") + "&i_and_text=" +global_keyword03_1 + "&i_exact_text=" + global_keyword03_2
													+ "&i_or_text=" + global_keyword03_3 + "&i_exclude_text=" + global_keyword03_4 + "&i_sourcetype=" + channel;
													
													if(field=="column-1"){
														param += "&i_trend=1";
													}else if(field=="column-2"){
														param += "&i_trend=2";
													}else if(field=="column-3"){
														param += "&i_trend=3";
													}
													popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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
								</div>
							</section>
							<!-- // 정보 분포 -->

							<!-- 감성 추이 -->
							<section class="ui_box_00 m_t_20">
								<h4 class="no_bd">감성 추이</h4>
								<div class="content">
									<section id="s_c3_1" class="ui_box_00">
										<h4>검색키워드</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s2_1', '감성현황_감성추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c3_1" class="ui_chart" style="height:200px">
											<script type="text/javascript">
												var chart8;									
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
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-1",
																	"markerType": "circle",
																	"title": "긍정",
																	"type": "line",
																	"valueField": "column-1"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-2",
																	"markerType": "circle",
																	"title": "부정",
																	"type": "line",
																	"valueField": "column-2"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-3",
																	"markerType": "circle",
																	"title": "중립",
																	"type": "line",
																	"valueField": "column-3"
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart8 = AmCharts.makeChart("chart_c3_1", chartOption);
													chart8.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
														param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword01_1 + "&i_exact_text=" + global_keyword01_2
														+ "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4 + "&i_sourcetype=" + global_channel;
														
														if(field=="column-1"){
															param += "&i_trend=1";
														}else if(field=="column-2"){
															param += "&i_trend=2";
														}else if(field=="column-3"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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

									<section id="s_c3_2" class="ui_box_00 m_t_20">
										<h4>검색키워드 2</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s2_2', '감성현황_감성추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c3_2" class="ui_chart" style="height:200px">
											<script type="text/javascript">
												var chart9;									
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
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-1",
																	"markerType": "circle",
																	"title": "긍정",
																	"type": "line",
																	"valueField": "column-1"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-2",
																	"markerType": "circle",
																	"title": "부정",
																	"type": "line",
																	"valueField": "column-2"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-3",
																	"markerType": "circle",
																	"title": "중립",
																	"type": "line",
																	"valueField": "column-3"
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart9 = AmCharts.makeChart("chart_c3_2", chartOption);
													chart9.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
														param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword02_1 + "&i_exact_text=" + global_keyword02_2
														+ "&i_or_text=" + global_keyword02_3 + "&i_exclude_text=" + global_keyword02_4 + "&i_sourcetype=" + global_channel;
														
														if(field=="column-1"){
															param += "&i_trend=1";
														}else if(field=="column-2"){
															param += "&i_trend=2";
														}else if(field=="column-3"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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
									
									<section id="s_c3_3" class="ui_box_00 m_t_20">
										<h4>검색키워드 3</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s2_3', '감성현황_감성추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c3_3" class="ui_chart" style="height:200px">
											<script type="text/javascript">
												var chart10;									
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
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-1",
																	"markerType": "circle",
																	"title": "긍정",
																	"type": "line",
																	"valueField": "column-1"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-2",
																	"markerType": "circle",
																	"title": "부정",
																	"type": "line",
																	"valueField": "column-2"
																},
																{
																	"balloonText": "<strong>[[title]]</strong> : [[value]]",
																	"bulletBorderThickness": 0,
																	/* "fillAlphas": 1, */
																	"bullet": "round",
																	"bulletSize": 5,
																	"lineThickness": 1,
																	"id": "AmGraph-3",
																	"markerType": "circle",
																	"title": "중립",
																	"type": "line",
																	"valueField": "column-3"
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart10 = AmCharts.makeChart("chart_c3_3", chartOption);
													chart10.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
														param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword03_1 + "&i_exact_text=" + global_keyword03_2
														+ "&i_or_text=" + global_keyword03_3 + "&i_exclude_text=" + global_keyword03_4 + "&i_sourcetype=" + global_channel;
														
														if(field=="column-1"){
															param += "&i_trend=1";
														}else if(field=="column-2"){
															param += "&i_trend=2";
														}else if(field=="column-3"){
															param += "&i_trend=3";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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
								</div>
							</section>
							<!-- // 감성 추이 -->

							<!-- 채널별 추이 -->
							<section class="ui_box_00 m_t_20">
								<h4 class="no_bd">채널별 추이</h4>
								<div class="content">
									<section id="s_c4_1" class="ui_box_00">
										<h4>검색키워드</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s3_1', '감성현황_채널별 추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c4_1" class="ui_chart" style="height:250px">
											<script type="text/javascript">
												var chart11;									
												(function(){
													var chartOption = {
														"type": "serial",
														"categoryField": "category",
														"columnWidth": 0.4,
														"marginBottom": 0,
														"marginTop": 10,
														"colors": [],
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
														"graphs": [],
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart11 = AmCharts.makeChart("chart_c4_1", chartOption);
													chart11.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
															param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword01_1 + "&i_exact_text=" + global_keyword01_2
															+ "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4;
														
														if(field=="TW"){
															param += "&i_sourcetype=TW";
														}else if(field=="FA"){
															param += "&i_sourcetype=FA";
														}else if(field=="BL"){
															param += "&i_sourcetype=BL";
														}else if(field=="DC"){
															param += "&i_sourcetype=DC";
														}else if(field=="CF"){
															param += "&i_sourcetype=CF";
														}else if(field=="DN"){
															param += "&i_sourcetype=DN";
														}else if(field=="PR"){
															param += "&i_sourcetype=PR";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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

									<section id="s_c4_2" class="ui_box_00 m_t_20">
										<h4>검색키워드 2</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s3_2', '감성현황_채널별 추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c4_2" class="ui_chart" style="height:250px">
											<script type="text/javascript">
												var chart12;									
												(function(){
													var chartOption = {
														"type": "serial",
														"categoryField": "category",
														"columnWidth": 0.4,
														"marginBottom": 0,
														"marginTop": 10,
														"colors": [],
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
														"graphs": [],
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart12 = AmCharts.makeChart("chart_c4_2", chartOption);
													chart12.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
															param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword02_1 + "&i_exact_text=" + global_keyword02_2
															+ "&i_or_text=" + global_keyword02_3 + "&i_exclude_text=" + global_keyword02_4;
														
														if(field=="TW"){
															param += "&i_sourcetype=TW";
														}else if(field=="FA"){
															param += "&i_sourcetype=FA";
														}else if(field=="BL"){
															param += "&i_sourcetype=BL";
														}else if(field=="DC"){
															param += "&i_sourcetype=DC";
														}else if(field=="CF"){
															param += "&i_sourcetype=CF";
														}else if(field=="DN"){
															param += "&i_sourcetype=DN";
														}else if(field=="PR"){
															param += "&i_sourcetype=PR";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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
									
									<section id="s_c4_3" class="ui_box_00 m_t_20">
										<h4>검색키워드 3</h4>
										<div class="title_rc">
											<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('3_s3_3', '감성현황_채널별 추이'); return false;"><span class="icon excel">Excel Download</span></button>
										</div>
										<div class="content">
											<div id="chart_c4_3" class="ui_chart" style="height:250px">
											<script type="text/javascript">
												var chart13;									
												(function(){
													var chartOption = {
														"type": "serial",
														"categoryField": "category",
														"columnWidth": 0.4,
														"marginBottom": 0,
														"marginTop": 10,
														"colors": [],
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
														"graphs": [],
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
														"chartScrollbar": {
															"enabled": true,
															"dragIconHeight": 25,
															"dragIconWidth": 25,
															"offset": 20,
															"scrollbarHeight": 8
														},
														"titles": [],
														"dataProvider": []
													};
													chart13 = AmCharts.makeChart("chart_c4_3", chartOption);
													chart13.addListener("clickGraphItem", function(event){
														var date = (event.item.dataContext.fulldate).replace(/-/gi, "");
														var field = event.item.graph.valueField;
														var param = "";
															param = "i_sdate=" + date + "&i_edate=" +  date + "&i_and_text=" +global_keyword03_1 + "&i_exact_text=" + global_keyword03_2
															+ "&i_or_text=" + global_keyword03_3 + "&i_exclude_text=" + global_keyword03_4;
														
														if(field=="TW"){
															param += "&i_sourcetype=TW";
														}else if(field=="FA"){
															param += "&i_sourcetype=FA";
														}else if(field=="BL"){
															param += "&i_sourcetype=BL";
														}else if(field=="DC"){
															param += "&i_sourcetype=DC";
														}else if(field=="CF"){
															param += "&i_sourcetype=CF";
														}else if(field=="DN"){
															param += "&i_sourcetype=DN";
														}else if(field=="PR"){
															param += "&i_sourcetype=PR";
														}
														popupOpen( "../popup/rel_info_lucy.jsp?" + param);
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
								</div>
							</section>
							<!-- // 채널별 추이 -->

						</div>
					</section>
				</div>
				<!-- // 감성현황 -->

				<!-- 연관키워드 -->
				<div id="s_c3" class="ui_boxs_00">
					<section class="ui_box_00 ui_shadow_00">
						<h3 class="no_bd"><span class="ui_bullet_00">●</span>연관키워드</h3>
						<div class="content">
							<section id="s_c5_1" class="ui_box_00">
								<h4>검색키워드</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4_1', '연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<div id="tag_cloud_01" class="ui_word_cloud"></div>				<!-- 데이터 없는 경우 no_data 클래스 추가 -->
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>

							<section id="s_c5_2" class="ui_box_00 m_t_20">
								<h4>검색키워드 2</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4_2', '연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<div id="tag_cloud_02" class="ui_word_cloud"></div>				<!-- 데이터 없는 경우 no_data 클래스 추가 -->
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>

							<section id="s_c5_3" class="ui_box_00 m_t_20">
								<h4>검색키워드 3</h4>
								<div class="title_rc">
									<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('4_3', '연관키워드'); return false;"><span class="icon excel">Excel Download</span></button>
								</div>
								<div class="content">
									<div id="tag_cloud_03" class="ui_word_cloud"></div>				<!-- 데이터 없는 경우 no_data 클래스 추가 -->
								</div>

								<!-- Loader -->
								<div class="ui_loader v0"><span class="loader">Load</span></div>
								<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
								<!-- // Loader -->
							</section>

						</div>
					</section>
				</div>
				<!-- // 연관키워드 -->

				<!-- 관련정보 -->
				<div id="s_c4" class="ui_boxs_00">
					<section id="s_c6_1" class="ui_box_00 ui_shadow_00">
						<h3 id="s_c4_text" class="no_bd"><span class="ui_bullet_00">●</span>관련정보<!-- <strong>검색어 1</strong> --></h3>
						<div class="title_rc">
							<span><!-- <strong><span class="ui_bullet_02">-</span> 총 1659건</strong>, 1/166 pages --></span>
							<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="excelDownload('5', '관련정보'); return false;"><span class="icon excel">Excel Download</span></button>
						</div>
						<div class="content">
							<ul class="ui_tab_00">
								<li><input id="ri_radio_00" type="radio" name="ri_radio" checked><label for="ri_radio_00"><span>검색어 키워드 1</span></label></li>
								<li><input id="ri_radio_01" type="radio" name="ri_radio"><label for="ri_radio_01"><span>검색어 키워드 2</span></label></li>
								<li><input id="ri_radio_02" type="radio" name="ri_radio"><label for="ri_radio_02"><span>검색어 키워드 3</span></label></li>
							</ul>
							<div class="ui_board_list_00">
								<table id="info_contents">
								<caption>관련정보 목록</caption>
								<colgroup>
								<col style="width:150px">
								<col>
								<col style="width:110px">
								</colgroup>
								<thead>
								<tr>
								<th scope="col"><span>출처</span></th>
								<th scope="col"><span>제목</span></th>
								<th scope="col"><span>수집시간</span></th>
								</tr>
								</thead>
								<tbody>
								<!-- 데이터 없는 경우
								<tr>
								<td colspan="3" class="no_data" style="height:330px"></td>
								</tr>
								-->
								<tr>
								<td><strong>급식뉴스</strong></td>
								<td class="title"><a href="#" target="_blank">황사,미세먼지 이길 '디톡스식품' 인기리 판매</a></td>
								<td>3분전</td>
								</tr>
								<tr>
								<td><strong>급식뉴스</strong></td>
								<td class="title"><a href="#1" target="_blank">황사,미세먼지 이길 '디톡스식품' 인기리 판매</a></td>
								<td>3분전</td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								<tr>
								<td>-</td>
								<td class="title"></td>
								<td></td>
								</tr>
								</tbody>
								</table>
							</div>
							<div class="paginate" id="section05_paging" >
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
							</div>
						</div>

						<!-- Loader -->
						<div class="ui_loader v0"><span class="loader">Load</span></div>
						<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
						<!-- // Loader -->
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