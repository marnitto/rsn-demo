<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.Log"%>
<%@page import="risk.util.StringUtil"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.util.StringUtil"%>
<%@page import="risk.util.PageIndex"%>
<%@page import="risk.search.userEnvInfo"%>
<%@page import="risk.search.userEnvMgr"%>
<%@page import="risk.search.MetaMgr"%>
<%@page import="risk.search.PortalBean"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	userEnvInfo uei = null;
	uei = (userEnvInfo) session.getAttribute("ENV");
	MetaMgr smgr = new MetaMgr();
	
	String sCurrDate = du.getCurrentDate();
	String sInit = (String)session.getAttribute("INIT");
	
	//Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
	if ( sInit != null  && sInit.equals("INIT") ) {
	    userEnvMgr uem = new userEnvMgr();
	    uei = uem.getUserEnv( SS_M_ID );
	    session.removeAttribute("INIT");
	
	    if ( Integer.parseInt(uei.getSt_interval_day()) > 1 ) {
	        du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
	        uei.setDateFrom( du.getDate() );
	        uei.setDateTo  ( sCurrDate    ) ;
	    } else {
	        uei.setDateFrom( sCurrDate  ) ;
	        uei.setDateTo  ( sCurrDate  ) ;
	    }
	}
	
    uei.setDateFrom( pr.getString("sDateFrom",uei.getDateFrom() ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,uei.getDateTo()   ) ) ;

	String keyword = pr.getString("keyword", "");
	int nowpage = pr.getInt("nowpage", 1);
	//portalList
	ArrayList portalList = new ArrayList();
	portalList = smgr.getPortalList(nowpage, Integer.parseInt(uei.getSt_list_cnt()), uei.getDateFrom(), uei.getDateTo(), keyword);

	int iTotalCnt= smgr.portalCnt;
    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}

</style>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/timer.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/input_date.js" type="text/javascript"></script>
<script type="text/JavaScript">
<!--

	var sCurrDate = '<%=sCurrDate%>';

	function setDateTerm( day ) {
	    var f = document.fSend;
	    f.sDateFrom.value   = AddDate( day );
	    f.sDateTo.value     = sCurrDate;
	    f.Period.value      = day + 1;
	}

	function java_all_trim(a) {
		for (; a.indexOf(" ") != -1 ;) {
			a = a.replace(" ", "");
		}
		return a;
	}
	
	function AddDate( day ) {
	
		var newdate = new Date();
		sdate = newdate.getTime();
		
		edate = sdate - ( day * 24 * 60 * 60 * 1000);
		newdate.setTime(edate);
		
		last_ndate = newdate.toLocaleString();
		
		last_date = java_all_trim(last_ndate);
		last_year = last_date.substr(0,4);
		last_month = last_date.substr(5,2);
		last_mon = last_month.replace("월","");
		
		if(last_mon < 10) {
		
		    last_m = 0+last_mon;
		    last_day = last_date.substr(7,2);
		    last_da = last_day.replace("일","");
		
		    if(last_da < 10) {
		        last_d = 0+last_da;
		    }else{
		        last_d = last_da;
		    }
		
		}else{
		    last_m = last_mon;
		
		    last_day = last_date.substr(8,2);
		    last_da = last_day.replace("일","");
		
		    if(last_da < 10) {
		        last_d = 0+last_da;
		    }else{
		        last_d = last_da;
		    }
		
		}
		
		last_time = last_year + '-' + last_m + '-' + last_d;
		
		return last_time;
	}

    function show_me(i){
		div_show.innerHTML = eval('div'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
    }

    function close_me(i){
    	div_show.style.display='none';
    }

    function pageClick( paramUrl ) {
    	var f = document.fSend;
        f.action = "search_main_top.jsp" + paramUrl;
        doSubmit();
    }

    function doSubmit() {
    	var f = document.fSend;
		f.submit();
    }

    function doSearch() {
    	var f = document.fSend;
    	f.nowpage.value = '1';
		f.submit();
    }
    
	var chkPop = 1;
	function hrefPop(url){
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');
		chkPop++;
	}


	/**
	* 이슈등록 팝업 열기
	*/
	function send_issue(mode, md_seq){

		if(mode=='insert'){
			document.send_mb.mode.value = mode;
			document.send_mb.md_seq.value = md_seq;
			document.send_mb.subMode.value = 'TOP';		
        	popup.openByPost('send_mb','pop_issue_data_form.jsp',708,950,false,true,false,'send_issue');
		}
	}
	
//-->

</script>
</head>

<body leftmargin="10" topmargin="5" marginwidth="0" marginheight="0">
<DIV id=div_show style="FONT-SIZE: 12px; PADDING-RIGHT: 5px; DISPLAY: none; PADDING-LEFT: 5px; LEFT: 140px;
                        width:500px;height:50px;
                        PADDING-BOTTOM: 5px; PADDING-TOP: 5px; POSITION: absolute; BACKGROUND-COLOR: #FFFFCC; border: #ff6600 2px solid;">
</DIV>


<form name="send_mb" id="send_mb" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="md_seq">
<input type="hidden" name="subMode">
</form>

<form name="fSend" action="search_main_top.jsp" method="post">
<input type="hidden" name="Period" value="">
<input type="hidden" name="nowpage" value="<%=nowpage%>">

<table align="center" style="width:820px;margin-left:20px" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tbody><tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tbody><tr>
						<td style="text-align: left;"><img src="../../images/search/tit_icon.gif"><img src="../../images/search/tit_0102.gif"></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tbody><tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">전체키워드검색</td>
							</tr>
						</tbody></table>
						</td>
					</tr>
				</tbody></table>
				</td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<form name="fSend" id="fSend" action="search_main_contents.jsp" method="post" style="margin-bottom: 0px">
					<input type="hidden" name="xp" value="">
					<input type="hidden" name="yp" value="">
					<input type="hidden" name="zp" value="">
					<input type="hidden" name="timer" value="Y">
					<input type="hidden" name="interval" value="5">
					<input type="hidden" name="type" value="7">
					<input type="hidden" name="searchtype" value="1">
					<input type="hidden" name="sOrder" value="MD_DATE">
					<input type="hidden" name="sOrderAlign" value="DESC">
					<input type="hidden" name="sg_seq" value="42,17,18,25,19,20">
					<input type="hidden" name="sd_gsn" value="">
					<input type="hidden" name="searchmode" value="ALLKEY">
					<input type="hidden" name="Period" value="5">
					<input name="nowpage" type="hidden" value="1">          
					<input name="del_mname" type="hidden" value="139">
					<input type="hidden" name="SaveList">
					<input name="bookMarkYn" type="hidden">
					<input name="tiers" type="hidden">
					<input name="language" type="hidden" value="KOR">
				<div class="article">
				 	<div class=" ui_searchbox_00" style="height: 230px;">
						<div class="c_wrap">
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>검색단어</span>
								<span class="txts">
									<select class="ui_select_04" name="searchType">
										<option value="1" selected="selected">제목</option>
						            	<option value="2">제목+내용</option>
						            	<option value="3">출처</option>
									</select><input id="input_date_00" type="text" class="ui_input_02" name="sKeyword" value="" onkeydown="javascript:if(event.keyCode == 13){doSearch();}"><label for="input_date_00" class="invisible">검색어입력</label><button class="ui_btn_02" onclick="doSearch();"><span class="icon search_01">-</span>검색</button>
								</span>
							</div>
						</div>
						<div class="c_sort" style="top: 40px;">
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>검색기간</span>
								<span class="txts">
									<input name="sDateFrom" id="sDateFrom" value="2016-06-22" type="text" class="ui_datepicker_input input_date_first hasDatepicker" readonly=""><label for="sDateFrom">날짜입력</label>
									<select id="stime" name="stime" class="ui_select_04">
									
									<option value="0" selected="">0시</option>
									
									<option value="1">1시</option>
									
									<option value="2">2시</option>
									
									<option value="3">3시</option>
									
									<option value="4">4시</option>
									
									<option value="5">5시</option>
									
									<option value="6">6시</option>
									
									<option value="7">7시</option>
									
									<option value="8">8시</option>
									
									<option value="9">9시</option>
									
									<option value="10">10시</option>
									
									<option value="11">11시</option>
									
									<option value="12">12시</option>
									
									<option value="13">13시</option>
									
									<option value="14">14시</option>
									
									<option value="15">15시</option>
									
									<option value="16">16시</option>
									
									<option value="17">17시</option>
									
									<option value="18">18시</option>
									
									<option value="19">19시</option>
									
									<option value="20">20시</option>
									
									<option value="21">21시</option>
									
									<option value="22">22시</option>
									
									<option value="23">23시</option>
									
									</select><label for="stime"></label>~
									<input name="sDateTo" id="sDateTo" value="2016-06-22" type="text" class="ui_datepicker_input input_date_last hasDatepicker" readonly=""><label for="sDateTo">날짜입력</label>
									<select id="etime" name="etime" class="ui_select_04">
									
									<option value="0">0시</option>
									
									<option value="1">1시</option>
									
									<option value="2">2시</option>
									
									<option value="3">3시</option>
									
									<option value="4">4시</option>
									
									<option value="5">5시</option>
									
									<option value="6">6시</option>
									
									<option value="7">7시</option>
									
									<option value="8">8시</option>
									
									<option value="9">9시</option>
									
									<option value="10">10시</option>
									
									<option value="11">11시</option>
									
									<option value="12">12시</option>
									
									<option value="13">13시</option>
									
									<option value="14">14시</option>
									
									<option value="15">15시</option>
									
									<option value="16">16시</option>
									
									<option value="17">17시</option>
									
									<option value="18">18시</option>
									
									<option value="19">19시</option>
									
									<option value="20">20시</option>
									
									<option value="21">21시</option>
									
									<option value="22">22시</option>
									
									<option value="23" selected="">23시</option>
									
									</select><label for="etime"></label>
									<a href="javascript:setDateTerm(0);" class="ui_btn_06">금일</a>
									<a href="javascript:setDateTerm(2);" class="ui_btn_06">3일</a>
									<a href="javascript:setDateTerm(6);" class="ui_btn_06">7일</a>
								</span>
							</div>
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>정보유형</span>
								<span class="txts">
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_00_00" name="chkAll" onclick="javascript:checkAll();" checked="" value=""><label for="ui_input_chk_00_00"><span class="chkbox_00"></span><strong>전체</strong></label></div>
									<div class="dcp m_r_10"><input type="checkbox" name="chkGisa" onclick="javascript:checkEtc();" checked="" id="ui_input_chk_00_01" value=""><label for="ui_input_chk_00_01"><span class="chkbox_00"></span><span class="ui_ico_news">-</span>기사</label></div>
									<div class="dcp m_r_10"><input type="checkbox" name="chkGesi" onclick="javascript:checkEtc();" checked="" id="ui_input_chk_00_02" value=""><label for="ui_input_chk_00_02"><span class="chkbox_00"></span><span class="ui_ico_post">-</span>게시</label></div>
									<div class="dcp">		<input type="checkbox" name="chkGongji" onclick="javascript:checkEtc();" checked="" id="ui_input_chk_00_03" value=""><label for="ui_input_chk_00_03"><span class="chkbox_00"></span><span class="ui_ico_notice">-</span>공지</label></div>
								</span>
							</div>
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>검색유형</span>
								<span class="txts">
								
								<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_00" name="sltSiteGroup" value="" class="boardAllChecker" checked=""><label for="ui_input_chk_01_00"><span class="chkbox_00"></span><strong>전체</strong></label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_01" name="sltSiteGroup" value="42" checked=""><label for="ui_input_chk_01_01"><span class="chkbox_00"></span>언론사</label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_02" name="sltSiteGroup" value="17" checked=""><label for="ui_input_chk_01_02"><span class="chkbox_00"></span>트위터</label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_03" name="sltSiteGroup" value="18" checked=""><label for="ui_input_chk_01_03"><span class="chkbox_00"></span>블로그</label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_04" name="sltSiteGroup" value="25" checked=""><label for="ui_input_chk_01_04"><span class="chkbox_00"></span>카페</label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_05" name="sltSiteGroup" value="19" checked=""><label for="ui_input_chk_01_05"><span class="chkbox_00"></span>커뮤니티</label></div>
								 
									<div class="dcp m_r_10"><input type="checkbox" id="ui_input_chk_01_06" name="sltSiteGroup" value="20" checked=""><label for="ui_input_chk_01_06"><span class="chkbox_00"></span>공공기관</label></div>
								
								</span>
							</div>
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>매체관리</span>
								<span class="txts">
									<div class="dcp m_r_10"><input name="tier" type="checkbox" id="ui_input_chk_02_00" class="boardAllChecker" value=""><label for="ui_input_chk_02_00"><span class="chkbox_00"></span><strong>Tier 전체</strong></label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" id="ui_input_chk_02_01" value="1"><label for="ui_input_chk_02_01"><span class="chkbox_00"></span>Tiering 1</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" id="ui_input_chk_02_02" value="2"><label for="ui_input_chk_02_02"><span class="chkbox_00"></span>Tiering 2</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" id="ui_input_chk_02_03" value="3"><label for="ui_input_chk_02_03"><span class="chkbox_00"></span>Tiering 3</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" id="ui_input_chk_02_04" value="4"><label for="ui_input_chk_02_04"><span class="chkbox_00"></span>Tiering 4</label></div>
								</span>
							</div>
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>이슈등록여부</span>
								<span class="txts">
									<div class="dcp m_r_10"><input name="issue_check" id="issue_check" type="checkbox" value="Y"><label for="issue_check"><span class="chkbox_00"></span>등록</label></div>&nbsp;
								</span>
							</div>
						</div>
					</div>
					<div class="ui_searchbox_toggle">
						<a href="#" class="btn_toggle active">검색조건 열기/닫기</a>
					</div>
				</div>
		<!-- // 검색 -->
				</form>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<!-- <td style="height:45px;background:url('../../images/search/list_top_line.gif') bottom repeat-x;"> -->
				<td style="height:25px;padding-top:15px">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tbody><tr>
						<td style="text-align:left"><img src="../../images/search/icon_search_bullet.gif" style="vertical-align:middle"> <strong>전체키워드</strong>에 대한 검색결과 /<strong> 6490</strong> 건, <strong>1</strong>/<strong>325</strong> pages</td>
						<td width="150" align="right" style="padding: 2px 0px 0px 3px;">
							<span class="search_reset_time" id="watch"><strong>00:28초전</strong> 정보입니다.</span>
						</td>
						<td width="140" style="text-align:right">
							<select name="slttimer" class="t" onchange="setTimer(this.value);">
								<option value="0">사용하지 않음</option>
								<option value="5" selected="">5분마다 새로고침</option>
								<option value="10">10분마다 새로고침</option>
								<option value="20">20분마다 새로고침</option>
								<option value="30">30분마다 새로고침</option>
							</select>
						</td>
					</tr>
				</tbody></table>	
				</td>
			</tr>
			
			<tr>
				<td style="height:25px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tbody><tr>
						<td style="text-align:left"><img src="../../images/search/icon_search_bullet.gif" style="vertical-align:middle"> <strong>출처별</strong> 검색결과(유사포함) : 전체:<strong>11,779</strong>건 / 언론사:<strong>4,433</strong>건 / 트위터:<strong>415</strong>건 / 블로그:<strong>5,103</strong>건 / 카페:<strong>1,439</strong>건 / 커뮤니티:<strong>381</strong>건 / 공공기관:<strong>8</strong>건</td>
					</tr>
				</tbody></table>	
				</td>
			</tr>
		</tbody></table>
		<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">

				<colgroup><col width="5%"><col width="12%"><col width="*"><col width="6%"><col width="5%"><col width="5%"><col width="10%">

					</colgroup><tbody><tr>
						<th><input type="checkbox" name="chkAllCheck" value="" onclick="reverseAll(this.checked);"></th>
						<th style="cursor:pointer" onclick="setOrder('MD_SITE_NAME');">출처</th>

						<th style="cursor:pointer" onclick="setOrder('MD_TITLE');">제목</th>
						<th></th>

						<th>관리</th>

						<th style="cursor:pointer" onclick="setOrder('MD_SAME_COUNT');">유사</th>
						<th style="cursor:pointer" onclick="setOrder('MD_DATE');">수집시간▼</th>

					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829271"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/duren82263?Redirect=Log&amp;logNo=220743181282&amp;from=section', '139', '23829271');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(0);" onmouseout="close_me(0);"><font id="list23829271">자동차보험료비교견적사이트 ★ 객관적 특징</font></a><font id="list23829271">
							
							</font></div><font id="list23829271">
							<div id="div0" style="display:none;width:200px;">
								<font color="0000CC"><b>자동차보험</b></font>료비교견적사이트 ★ 객관적 특징




젊을 때 우리는 자신의 늙을 것이라고는 생각을

안 했었는데, 당 떨어진다는 느낌이 어떤 것인지

이해할 수 있게 되고 비 오는 날 관절에서

소리가 나기도 하면서 이전보다는 늙었다는 것을

몸으로 깨닫게 되는데요.




















저를 기다려주지도 않고 바르게 흘러가 버린 시간이

이럴 ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829271','3554','%EC%9E%90%EB%8F%99%EC%B0%A8%EB%B3%B4%ED%97%98%EB%A3%8C%EB%B9%84%EA%B5%90%EA%B2%AC%EC%A0%81%EC%82%AC%EC%9D%B4%ED%8A%B8+%E2%98%85+%EA%B0%9D%EA%B4%80%EC%A0%81+%ED%8A%B9%EC%A7%95');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829271" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829271');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829271" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829272"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/dbdmsdid1243?Redirect=Log&amp;logNo=220743190169&amp;from=section', '139', '23829272');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(1);" onmouseout="close_me(1);"><font id="list23829272">빗물이 어느정도 고이면 차가 지나가도 될까?</font></a><font id="list23829272">
							
							</font></div><font id="list23829272">
							<div id="div1" style="display:none;width:200px;">
								...

빨리 달리면 본네트를 통해서 엔진에 물이 들어가기 때문입니다.












그리고 이렇게 침수지역을 빠져나온 다음에는 차를 말려야 됩니다.




역시 천천히 달리면서 브레이크를 반복해서 밟아줘야 된다고 합니다.

제동장치는 방수가 되지 않아서 물이 들어가면 마찰력이 떨어지거든요




주차해 둔 차가 갑자기 불어난 물에 침수됐다.

이럴때는 <font color="0000CC"><b>자동차보험</b></font>에 자기차량손해담보에 가입했다면 걱정하지 않아도 됩니다.












하지만, 깜밖해서 차 문이나 썬루프등을 열어뒀다가

침수가 되면 어떻게 될까요?




이건 보상받을 수 없습니다.

차량관리과실로 간주가 된다고 합니다.     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829272','3554','%EB%B9%97%EB%AC%BC%EC%9D%B4+%EC%96%B4%EB%8A%90%EC%A0%95%EB%8F%84+%EA%B3%A0%EC%9D%B4%EB%A9%B4+%EC%B0%A8%EA%B0%80+%EC%A7%80%EB%82%98%EA%B0%80%EB%8F%84+%EB%90%A0%EA%B9%8C%3F');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829272" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829272');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829272" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829273"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/shinzzi2?Redirect=Log&amp;logNo=220743192295&amp;from=section', '139', '23829273');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(2);" onmouseout="close_me(2);"><font id="list23829273">자동차보험비교견적사이트 ★실시간 인터넷으로 확인!</font></a><font id="list23829273">
							
							</font></div><font id="list23829273">
							<div id="div2" style="display:none;width:200px;">
								<font color="0000CC"><b>자동차보험</b></font>비교견적사이트 ★실시간 인터넷으로 확인!

 



&#8203;

&#8203;

무더운 날씨에 모두들 

건강하게 보내고 있으신가요?

저는 요즘 에어컨을 붙들고 살고있답니다.

잠깐만 창문을 열었다 하면 뜨거운 열기가

확~~ 하고 안으로 들어오는데

정말 한중막에 들어온 느낌이란니다.

&#8203;

이런날씨에도 도로위에서 고생하시는 분들을 

생...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829273','3554','%EC%9E%90%EB%8F%99%EC%B0%A8%EB%B3%B4%ED%97%98%EB%B9%84%EA%B5%90%EA%B2%AC%EC%A0%81%EC%82%AC%EC%9D%B4%ED%8A%B8+%E2%98%85%EC%8B%A4%EC%8B%9C%EA%B0%84+%EC%9D%B8%ED%84%B0%EB%84%B7%EC%9C%BC%EB%A1%9C+%ED%99%95%EC%9D%B8%21');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829273" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829273');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829273" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829274"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/hmir9?Redirect=Log&amp;logNo=220743193934&amp;from=section', '139', '23829274');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(3);" onmouseout="close_me(3);"><font id="list23829274">서초동 여드름 흉터 (유민선원장)</font></a><font id="list23829274">
							
							</font></div><font id="list23829274">
							<div id="div3" style="display:none;width:200px;">
								...065
      
 
 
 
  
   
 
한미르한의원 : 네이버 블로그 
☏02-521-1088

▶한방성형 : 브이라인리프팅 주름치료 안면비대칭

▶피부질환 : 여드름 여드름흉터 지루성피부염

▶다이어트 : 한방다이어트 해독다이어트 바디라인

▶여성질환 : 자궁질환 갱년기장애 불임 산후풍

▶안과질환 : 황반변성 녹내장 안구건조증 비문증

▶통증, <font color="0000CC"><b>자동차보험</b></font>치료 
blog.naver.com     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829274','3554','%EC%84%9C%EC%B4%88%EB%8F%99+%EC%97%AC%EB%93%9C%EB%A6%84+%ED%9D%89%ED%84%B0+%28%EC%9C%A0%EB%AF%BC%EC%84%A0%EC%9B%90%EC%9E%A5%29');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829274" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829274');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829274" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829275"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/taehusm325?Redirect=Log&amp;logNo=220743205068&amp;from=section', '139', '23829275');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(4);" onmouseout="close_me(4);"><font id="list23829275">부천교통사고한의원 주5일야간진료 토일 치료</font></a><font id="list23829275">
							
							</font></div><font id="list23829275">
							<div id="div4" style="display:none;width:200px;">
								...서 진행하는 교통사고후유증 치료는 어혈을 풀어주는 치료 이외에도 침과 약침, 뜸, 부항,

물리치료, 운동요법 등으로 기혈순환과 면역력 증진을 도와주고 있습니다.




&#8203;



&#8203;

&#8203;

이 가운데 약침 치료는 통증을 느끼거나 불편한 부위를 정밀하게 타격해주는 역할을 합니다.

또한 추나치료는틀어진 몸의 균형을 바로 잡아줍니다.

이런 교통사고 치료들은 <font color="0000CC"><b>자동차보험</b></font>이 적용되기 때문에 환자분들은 본인부담금 없이 치료를 받으실 수 있습니다.















부천교통사고한의원 더그린에서는 교통사고 치료 시 한약부터, 침, 뜸, 부항, 추나, 약침까지 모두 보험처리가

가능합니다. 정형외과와 같은 양방 병원에 입원한 상태에서도 교통사고 치료를 위해 한의원에서 치료를

받을 수 있어 편리합니다.




&#8203;...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829275','3554','%EB%B6%80%EC%B2%9C%EA%B5%90%ED%86%B5%EC%82%AC%EA%B3%A0%ED%95%9C%EC%9D%98%EC%9B%90+%EC%A3%BC5%EC%9D%BC%EC%95%BC%EA%B0%84%EC%A7%84%EB%A3%8C+%ED%86%A0%EC%9D%BC+%EC%B9%98%EB%A3%8C');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829275" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829275');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829275" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829276"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/bmwoman1?Redirect=Log&amp;logNo=220743206911&amp;from=section', '139', '23829276');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(5);" onmouseout="close_me(5);"><font id="list23829276">공릉교통사고 후유증예방치료</font></a><font id="list23829276">
							
							</font></div><font id="list23829276">
							<div id="div5" style="display:none;width:200px;">
								... 잘 관리해주어야 합니다.

 

&#8203;

&#8203;

&#8203;

&#8203;



 

&#8203;

&#8203;

&#8203;

&#8203;사고 이후에는 많이 놀라고 긴장하기 때문에

&#8203;

불면, 가슴두근거림, 두통, 어지럼증과 같은 이상 반응이 올 수 있습니다.

 

따라서 마음을 안정시키고 사고로 생긴 어혈을 제거하기 위해

&#8203;

한약을 처방해드리게 되는데요.

 

모두 <font color="0000CC"><b>자동차보험</b></font>으로 드릴 수 있기 때문에

 

비용 없이 치료에 필요한 약을 드실 수 있습니다.

 

&#8203;

&#8203;

&#8203;



 

 

 

 

 

통증이 있는 부위는 약침치료와 침, 부항, 뜸치료를 통해

 

통증을 가라앉히고 관절과 인대의 손상을 치료하며

 

물리치료와 온열요법, 운동치료요법을 함께 병행하게 됩니다.

...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829276','3554','%EA%B3%B5%EB%A6%89%EA%B5%90%ED%86%B5%EC%82%AC%EA%B3%A0+%ED%9B%84%EC%9C%A0%EC%A6%9D%EC%98%88%EB%B0%A9%EC%B9%98%EB%A3%8C');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829276" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829276');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829276" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829270"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/carskincare?Redirect=Log&amp;logNo=220743185650&amp;from=section', '139', '23829270');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(6);" onmouseout="close_me(6);"><font id="list23829270">5개월된 제너시스 스크레치완화! 듀얼폴리싱과 프리미엄유리막코팅시공...</font></a><font id="list23829270">
							
							</font></div><font id="list23829270">
							<div id="div6" style="display:none;width:200px;">
								...추천
,동대문구,동대문구광택,동대문구유리막코팅,동대문구자동차복원,동대문구도색,동대문구실내크리닝,
동대문구추천자동차,중랑구,중랑구광택,중랑구유리막코팅,중랑구듀얼광택,중랑구도색,
중랑구자동차복원,중랑구신차유리막,중랑구자동차광택,중랑구차,중랑구도색하는곳,중랑구,용산구
,용산구광택,용상구유리막코팅,용산구듀얼광택,용산구도색,용산구자동차복원,용산구실내크리닝,

용산구<font color="0000CC"><b>자동차보험</b></font>,용산구차량수리,용산구도색잘하는곳,용산구신차유리막코팅,성동구,성동구광택,

성동구유리막코팅,성동구듀얼광택,성동구도색,성동구자동차복원,성동구실내크리닝,
성동구<font color="0000CC"><b>자동차보험</b></font>,성동구차량수리,성동구도색잘하는곳,성동구자동차유리막코팅,성동구신차유리막,

강북구,듀얼광택,듀얼폴리싱,강북구듀얼광택,강북구광택,강북구유리막코팅,강북구실내크리닝,
강북구자동차복원,강북구공...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829270','3554','5%EA%B0%9C%EC%9B%94%EB%90%9C+%EC%A0%9C%EB%84%88%EC%8B%9C%EC%8A%A4+%EC%8A%A4%ED%81%AC%EB%A0%88%EC%B9%98%EC%99%84%ED%99%94%21+%EB%93%80%EC%96%BC%ED%8F%B4%EB%A6%AC%EC%8B%B1%EA%B3%BC+%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84%EC%9C%A0%EB%A6%AC%EB%A7%89%EC%BD%94%ED%8C%85%EC%8B%9C%EA%B3%B5...');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829270" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829270');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829270" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23829169"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">다음 카페</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://cafe.daum.net/qpwo303/EcG2/3142', '139', '23829169');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(7);" onmouseout="close_me(7);"><font id="list23829169">악성코드 정의/종류/증상/처리방법</font></a><font id="list23829169">
							<img src="../../images/search/yellow_star.gif" style="cursor:pointer" onclick="portalSearch('4943','%EC%95%85%EC%84%B1%EC%BD%94%EB%93%9C+%EC%A0%95%EC%9D%98%2F%EC%A2%85%EB%A5%98%2F%EC%A6%9D%EC%83%81%2F%EC%B2%98%EB%A6%AC%EB%B0%A9%EB%B2%95')">
							</font></div><font id="list23829169">
							<div id="div7" style="display:none;width:200px;">
								...프로그램과 각종 스파이웨어 등을 꼽을 수 있습니다. 


2. <font color="0000CC"><b>악성코드</b></font>의 종류

스파이웨어(spyware)
스파이(spy)와 소프트웨어(software)의 합성어다른 사람의 컴퓨터에 잠입해 중요한 <font color="0000CC"><b>개인정보</b></font>를 빼내는 소프트웨어를 말합니다.무료 공개 소프트웨어를 내려 받거나 인증서 없는 active-x 프로그램을 실행할 때 자동적으로 함께 설치되면서 개인 및 <font color="0000CC"><b>금융</b></font>정보를 <font color="0000CC"><b>유출</b></font>시킵니다. 


 

미국의 인터넷 광고전문회사인 라디에이트(radiate)에서 개인 사용자의 취향을 파악하기 위하여 처음 개발되었습니다. 초기에는 사용자의 컴퓨터에 번호를 매겨 몇 명의 사용자가 광고를 보고 있는지를 알기 위한 단순한 것이었습니다. 
어떤 사람이나 조직에 관한 정보를 수집하는데 도움을 주는 프로그램 또는 모듈을 뜻하며, ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23829169','4943','%EC%95%85%EC%84%B1%EC%BD%94%EB%93%9C+%EC%A0%95%EC%9D%98%2F%EC%A2%85%EB%A5%98%2F%EC%A6%9D%EC%83%81%2F%EC%B2%98%EB%A6%AC%EB%B0%A9%EB%B2%95');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23829169" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23829169');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23829169" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23828357"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">시사INLive</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://www.sisainlive.com/news/articleView.html?idxno=26365', '139', '23828357');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(8);" onmouseout="close_me(8);"><font id="list23828357">여자는 집에서 밥이나 하라고?</font></a><font id="list23828357">
							
							</font></div><font id="list23828357">
							<div id="div8" style="display:none;width:200px;">
								...29일 민주노총 여성위원회 등 8개 여성 단체 회원들이 서울 광화문 광장에서 '여성노동자 결혼퇴직 관행 철폐를 위한 금복주 불매선언 여성, 노동계 기자회견'을 열었다. 
	


한국 사회에서 여성은 노동자로 제대로 인정받고 존중받으면서 일해본 적이 없다. 물론 한때(2006년경) ‘알파걸’이라는 신조어가 있었는데, 바로 꺾였다. 2008년 <font color="0000CC"><b>금융</b></font>위기 때문이다. 한국여성노동자회에서 2009년 관련 토론회(관련 자료 링크)를 열었는데, 당시 노동연구원 조사에 따르면 미국발 <font color="0000CC"><b>금융</b></font>위기 여파로 한국 여성들의 일자리가 무려 150만 개나 사라졌다고 한다. 전체 사라진 일자리의 75%가 여성 일자리였다. 연령대로 봤을 때는 30대가 두드러졌는데, 아이도 키우고 일도 해야 하는 이중부담에 시달리는 여성들이 ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23828357','11433','%EC%97%AC%EC%9E%90%EB%8A%94+%EC%A7%91%EC%97%90%EC%84%9C+%EB%B0%A5%EC%9D%B4%EB%82%98+%ED%95%98%EB%9D%BC%EA%B3%A0%3F');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23828357" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23828357');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23828357" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23828391"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">줌</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://news.zum.com/articles/31395421?t=t', '139', '23828391');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(9);" onmouseout="close_me(9);"><font id="list23828391">혜택 커지는 'ISA 시즌 2', 주부·은퇴자·미성년자도 가입 추진…4인 가족 10년 이상 부으면 매년 수백만원 세금 절감</font></a><font id="list23828391">
							
							</font></div><font id="list23828391">
							<div id="div9" style="display:none;width:200px;">
								...간과 범위가 따로 명시돼 있지 않다. isa로 벌어들인 소득에 대해선 평생 한 푼도 세금을 받지 않겠다는 얘기다. 현행 제도는 5년간 금융소득 200만원(연봉소득 5000만원 이상 기준) 이하까지는 세금을 받지 않고, 이를 넘어서는 소득에 대해서는 9.9%의 세금을 분리과세하도록 돼있다.

이런 상황에서 전면 비과세가 시행되면 실질 투자수익률이 높아진다. <font color="0000CC"><b>금융상품</b></font>을 통해 1000만원의 수익을 냈다고 가정할 때 원칙적으로 내야 할 세금은 수익의 15.4%인 154만원이다. 하지만 isa를 활용하면 세금이 절반 수준으로 줄어든다. 전체 소득 중 200만은 세금이 없고 나머지 800만원에 대해선 9.9%인 79만2000원을 물어야 한다. 하지만 더민주 개정안이 국회 문턱을 넘으면 79만2000원의 세금 역시 투자...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23828391','5016219','%ED%98%9C%ED%83%9D+%EC%BB%A4%EC%A7%80%EB%8A%94+%27ISA+%EC%8B%9C%EC%A6%8C+2%27%2C+%EC%A3%BC%EB%B6%80%C2%B7%EC%9D%80%ED%87%B4%EC%9E%90%C2%B7%EB%AF%B8%EC%84%B1%EB%85%84%EC%9E%90%EB%8F%84+%EA%B0%80%EC%9E%85+%EC%B6%94%EC%A7%84%E2%80%A64%EC%9D%B8+%EA%B0%80%EC%A1%B1+10%EB%85%84+%EC%9D%B4%EC%83%81+%EB%B6%80%EC%9C%BC%EB%A9%B4+%EB%A7%A4%EB%85%84+%EC%88%98%EB%B0%B1%EB%A7%8C%EC%9B%90+%EC%84%B8%EA%B8%88+%EC%A0%88%EA%B0%90');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23828391" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23828391');"></td>
						<!--↓↓유사-->
						<td>

							<a href="javascript:openSameList('23828391','23828391','ALLKEY');">1</a>

						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:45</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23828391" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23826999"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">Twitter</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://twitter.com/goodtingub2/status/745537340798443520', '139', '23826999');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(10);" onmouseout="close_me(10);"><font id="list23826999">레드벨벳 #공략 #매니아몬스터헌터 #팁 피규어 연예인스폰 박병호 바로 무한도전 수원KT위즈파크 https://t.co/KxmWTyVLsC #쌘놈 #앱 #몬스터헌터 유연석 #모바일 30대 제주항공 자동차보험 올댓뷰티 고경표 #게임순위 튜브메이트 플래너</font></a><font id="list23826999">
							
							</font></div><font id="list23826999">
							<div id="div10" style="display:none;width:200px;">
								레드벨벳 #공략 #매니아몬스터헌터 #팁 피규어 연예인스폰 박병호 바로 무한도전 수원kt위즈파크 https://t.co/kxmwtyvlsc #쌘놈 #앱 #몬스터헌터 유연석 #모바일 30대 제주항공 <font color="0000CC"><b>자동차보험</b></font> 올댓뷰티 고경표 #게임순위 튜브메이트 플래너     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23826999','10464','%EB%A0%88%EB%93%9C%EB%B2%A8%EB%B2%B3+%23%EA%B3%B5%EB%9E%B5+%23%EB%A7%A4%EB%8B%88%EC%95%84%EB%AA%AC%EC%8A%A4%ED%84%B0%ED%97%8C%ED%84%B0+%23%ED%8C%81+%ED%94%BC%EA%B7%9C%EC%96%B4+%EC%97%B0%EC%98%88%EC%9D%B8%EC%8A%A4%ED%8F%B0+%EB%B0%95%EB%B3%91%ED%98%B8+%EB%B0%94%EB%A1%9C+%EB%AC%B4%ED%95%9C%EB%8F%84%EC%A0%84+%EC%88%98%EC%9B%90KT%EC%9C%84%EC%A6%88%ED%8C%8C%ED%81%AC+https%3A%2F%2Ft.co%2FKxmWTyVLsC+%23%EC%8C%98%EB%86%88+%23%EC%95%B1+%23%EB%AA%AC%EC%8A%A4%ED%84%B0%ED%97%8C%ED%84%B0+%EC%9C%A0%EC%97%B0%EC%84%9D+%23%EB%AA%A8%EB%B0%94%EC%9D%BC+30%EB%8C%80+%EC%A0%9C%EC%A3%BC%ED%95%AD%EA%B3%B5+%EC%9E%90%EB%8F%99%EC%B0%A8%EB%B3%B4%ED%97%98+%EC%98%AC%EB%8C%93%EB%B7%B0%ED%8B%B0+%EA%B3%A0%EA%B2%BD%ED%91%9C+%23%EA%B2%8C%EC%9E%84%EC%88%9C%EC%9C%84+%ED%8A%9C%EB%B8%8C%EB%A9%94%EC%9D%B4%ED%8A%B8+%ED%94%8C%EB%9E%98%EB%84%88');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23826999" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23826999');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23826999" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825941"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">Twitter</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://twitter.com/ssoltalkub2/status/745537349279354880', '139', '23825941');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(11);" onmouseout="close_me(11);"><font id="list23825941">줄리엔강 메이플스토리2 용산 에프엑스 #쌘놈 sk엔카 #모바일 강남 #앱 구하라 https://t.co/SuJ5G6MlZ8 MLB 이영애 #매니아화이트데이 국내여행 #게임순위 자동차보험 게스트하우스 케이토토 호텔 #화이트데이 에이프릴 #팁 #공략 천년</font></a><font id="list23825941">
							
							</font></div><font id="list23825941">
							<div id="div11" style="display:none;width:200px;">
								줄리엔강 메이플스토리2 용산 에프엑스 #쌘놈 sk엔카 #모바일 강남 #앱 구하라 https://t.co/suj5g6mlz8 mlb 이영애 #매니아화이트데이 국내여행 #게임순위 <font color="0000CC"><b>자동차보험</b></font> 게스트하우스 케이토토 호텔 #화이트데이 에이프릴 #팁 #공략 천년     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825941','10464','%EC%A4%84%EB%A6%AC%EC%97%94%EA%B0%95+%EB%A9%94%EC%9D%B4%ED%94%8C%EC%8A%A4%ED%86%A0%EB%A6%AC2+%EC%9A%A9%EC%82%B0+%EC%97%90%ED%94%84%EC%97%91%EC%8A%A4+%23%EC%8C%98%EB%86%88+sk%EC%97%94%EC%B9%B4+%23%EB%AA%A8%EB%B0%94%EC%9D%BC+%EA%B0%95%EB%82%A8+%23%EC%95%B1+%EA%B5%AC%ED%95%98%EB%9D%BC+https%3A%2F%2Ft.co%2FSuJ5G6MlZ8+MLB+%EC%9D%B4%EC%98%81%EC%95%A0+%23%EB%A7%A4%EB%8B%88%EC%95%84%ED%99%94%EC%9D%B4%ED%8A%B8%EB%8D%B0%EC%9D%B4+%EA%B5%AD%EB%82%B4%EC%97%AC%ED%96%89+%23%EA%B2%8C%EC%9E%84%EC%88%9C%EC%9C%84+%EC%9E%90%EB%8F%99%EC%B0%A8%EB%B3%B4%ED%97%98+%EA%B2%8C%EC%8A%A4%ED%8A%B8%ED%95%98%EC%9A%B0%EC%8A%A4+%EC%BC%80%EC%9D%B4%ED%86%A0%ED%86%A0+%ED%98%B8%ED%85%94+%23%ED%99%94%EC%9D%B4%ED%8A%B8%EB%8D%B0%EC%9D%B4+%EC%97%90%EC%9D%B4%ED%94%84%EB%A6%B4+%23%ED%8C%81+%23%EA%B3%B5%EB%9E%B5+%EC%B2%9C%EB%85%84');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825941" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825941');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825941" style="display:none;"></div>
						</td>
					</tr>

					<tr onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825831"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/potionss?Redirect=Log&amp;logNo=220743195685&amp;from=section', '139', '23825831');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(12);" onmouseout="close_me(12);"><font id="list23825831">[공유] 換리스크 없이 `절반 수수료`로 홍콩H주 선물 투자 가능</font></a><font id="list23825831">
							
							</font></div><font id="list23825831">
							<div id="div12" style="display:none;width:200px;">
								...k.co.kr

&#8203;

`예탁금 3천만원` 적격 개인투자자제 손봐야…홍콩은 자유롭게 거래

&#8203;

한국거래소와 홍콩거래소 간 지수선물상품 교차 상장이 우리에게 이득만 있는 것은 아니다. 규제 완화가 뒤따르지 않을 경우 자칫 국내 선물에 투자됐던 자금의 상당 부분이 홍콩으로 넘어갈지 모른다는 염려도 나온다.

&#8203;

가장 큰 문제는 적격 개인투자자 제도다. 우리 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>당국</b></font>은 2014년부터 파생상품 거래에 따른 개인투자자 <font color="0000CC"><b>피해</b></font>를 최소화하기 위해 실질적인 투자 능력을 갖춘 투자자에 한해 파생상품 거래를 허용하고 있다. 현재 개인투자자가 선물 거래를 하려면 기본예탁금 3000만원을 맡기고 교육 및 모의거래 과정까지 이수해야 한다.

&#8203;

이런 원칙은 교차 상장 후에도 적용된다. 국내에 상장된 h주 선물 투자를 원하는 개인...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825831','3554','%5B%EA%B3%B5%EC%9C%A0%5D+%E6%8F%9B%EB%A6%AC%EC%8A%A4%ED%81%AC+%EC%97%86%EC%9D%B4+%60%EC%A0%88%EB%B0%98+%EC%88%98%EC%88%98%EB%A3%8C%60%EB%A1%9C+%ED%99%8D%EC%BD%A9H%EC%A3%BC+%EC%84%A0%EB%AC%BC+%ED%88%AC%EC%9E%90+%EA%B0%80%EB%8A%A5');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825831" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825831');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825831" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825876"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">다음 카페</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://cafe.daum.net/tkdska3/NMXY/16525', '139', '23825876');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(13);" onmouseout="close_me(13);"><font id="list23825876">6월22일 코스피 기관 순매수,도 상위20종목</font></a><font id="list23825876">
							<img src="../../images/search/yellow_star.gif" style="cursor:pointer" onclick="portalSearch('4943','6%EC%9B%9422%EC%9D%BC+%EC%BD%94%EC%8A%A4%ED%94%BC+%EA%B8%B0%EA%B4%80+%EC%88%9C%EB%A7%A4%EC%88%98%2C%EB%8F%84+%EC%83%81%EC%9C%8420%EC%A2%85%EB%AA%A9')">
							</font></div><font id="list23825876">
							<div id="div13" style="display:none;width:200px;">
								...모비스(012330)
134.08

6
lg화학(051910)
121.69

7
lg디스플레이(034220)
101.32

8
lg생활건강(051900)
100.06

9
oci(010060)
96.14

10
sk이노베이션(096770)
92.87

11
kb금융(105560)
80.77

12
한미약품(128940)
73.91

13
하나<font color="0000CC"><b>금융지주</b></font>(086790)
73.04

14
naver(035420)
63.74

15
s-oil(010950)
57.15

16
cj제일제당(097950)
48.11

17
현대중공업(009540)
44.77

18
한국전력(015760)
44.19

19
우리은행(000030)
44.14

20
gs(078930)
40.50

코스피 기관 ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825876','4943','6%EC%9B%9422%EC%9D%BC+%EC%BD%94%EC%8A%A4%ED%94%BC+%EA%B8%B0%EA%B4%80+%EC%88%9C%EB%A7%A4%EC%88%98%2C%EB%8F%84+%EC%83%81%EC%9C%8420%EC%A2%85%EB%AA%A9');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825876" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825876');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825876" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825803"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">아시아경제</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://www.asiae.co.kr/news/sokbo/sokbo_view.htm?idxno=2016062217382877510', '139', '23825803');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(14);" onmouseout="close_me(14);"><font id="list23825803">벤츠파이낸셜서비스코리아, 여름용 특별 금융상품 출</font></a><font id="list23825803">
							
							</font></div><font id="list23825803">
							<div id="div14" style="display:none;width:200px;">
								[아시아경제 배경환 기자] 메르세데스-벤츠 파이낸셜 서비스 코리아가 여름을 맞아 1% 캠페인, 무이자·저금리 할부 등의 상품을 내놓는다.

'1% 캠페인'은 월 납입금 고정 프로그램이다. 더 a클래스, 더 b클래스, 더 cla, 더 gla 등을 매달 차량가액 1%의 납입금으로 구입할 수 있다. 계약에는 신차 교환 프로그램이 포함되며 중도 상환...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825803','4789','%EB%B2%A4%EC%B8%A0%ED%8C%8C%EC%9D%B4%EB%82%B8%EC%85%9C%EC%84%9C%EB%B9%84%EC%8A%A4%EC%BD%94%EB%A6%AC%EC%95%84%2C+%EC%97%AC%EB%A6%84%EC%9A%A9+%ED%8A%B9%EB%B3%84+%EA%B8%88%EC%9C%B5%EC%83%81%ED%92%88+%EC%B6%9C');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825803" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825803');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825803" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825806"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">아시아경제</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://www.asiae.co.kr/news/sokbo/sokbo_view.htm?idxno=2016062217414008388', '139', '23825806');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(15);" onmouseout="close_me(15);"><font id="list23825806">㈜한화, 4000억 유상증자…"테크윈 인수자금"</font></a><font id="list23825806">
							
							</font></div><font id="list23825806">
							<div id="div15" style="display:none;width:200px;">
								[아시아경제 고형광 기자] ㈜한화가 4000억원 규모의 유상증자를 단행한다. 이 자금으로 한화테크윈(옛 삼성테크윈) 인수 잔금을 치르고 일부는 재무구조 개선에 사용할 예정이다.

22일 <font color="0000CC"><b><font color="0000CC"><b>금융감독</b></font>원</b></font>에 따르면 ㈜한화는 운영자금 4000억원 모집을 위해 주주배정후 실권주 일반공모 방식으로 주당 5000원에 우선주 2000만주 유상증자를 결정했다.

㈜한화가 이처럼 대규모 자금 조달에 나선 것은 한화테크윈 인수 대금 중 잔금(3513억원)을 치르기 위한 것이다. 한화그룹 관계자는 "4000억원 중 테크윈 (인수)잔금에 3500억원을, 나머...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825806','4789','%E3%88%9C%ED%95%9C%ED%99%94%2C+4000%EC%96%B5+%EC%9C%A0%EC%83%81%EC%A6%9D%EC%9E%90%E2%80%A6%22%ED%85%8C%ED%81%AC%EC%9C%88+%EC%9D%B8%EC%88%98%EC%9E%90%EA%B8%88%22');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825806" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825806');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825806" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825812"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">네이버 블로그</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_02" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://blog.naver.com/glooddh?Redirect=Log&amp;logNo=220743185985&amp;from=section', '139', '23825812');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(16);" onmouseout="close_me(16);"><font id="list23825812">오늘의 그랜저 LPG HG300 익스클루시브 중고차의 시세를 금자네중고차에서 알아보자!...</font></a><font id="list23825812">
							
							</font></div><font id="list23825812">
							<div id="div16" style="display:none;width:200px;">
								...차명그랜저(grandeur)2.자동차
2.등록번호29고15283.주행거리
3.및 계기상태72,970 km
작동불량
4.연식20115.검사 유효기간2015.04.27 ~ 2017.04.26
6.최초등록일2011.04.278.변속기 종류
자동수동세미오토
무단변속기기타(   )
7.원동기형식l6db 
9.차대번호kmhfh41nbba040327 10.보증유형
자가<font color="0000CC"><b>보증보험</b></font>사보증
11.동일성확인(차대번호 표기)
양호상이부식훼손(오손)변조(변타)도말
12.불법구조변경  유   무13.사고(단순수리제외)유무14.침수유무유무
15.자기
13.진단
13.사항항목양호정비요16.배출
14.가스
  일산화탄소(co) :0.8 %
  탄화수소(hc) :121 ppm
  매연 :%
원동기
변속기
17.주요장치항목해당부품상태
원동기작동...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825812','3554','%EC%98%A4%EB%8A%98%EC%9D%98+%EA%B7%B8%EB%9E%9C%EC%A0%80+LPG+HG300+%EC%9D%B5%EC%8A%A4%ED%81%B4%EB%A3%A8%EC%8B%9C%EB%B8%8C+%EC%A4%91%EA%B3%A0%EC%B0%A8%EC%9D%98+%EC%8B%9C%EC%84%B8%EB%A5%BC+%EA%B8%88%EC%9E%90%EB%84%A4%EC%A4%91%EA%B3%A0%EC%B0%A8%EC%97%90%EC%84%9C+%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90%21...');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825812" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825812');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825812" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825557"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">와우넷</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://news.wownet.co.kr/newscenter/news/view.asp?bcode=T30001000&amp;wowcode=W000&amp;articleid=X20160622174121%20%20%20%20%20&amp;compcode=HK&amp;wowcode=W000', '139', '23825557');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(17);" onmouseout="close_me(17);"><font id="list23825557">'금융공기업 CEO 교체' 큰 장 선다</font></a><font id="list23825557">
							
							</font></div><font id="list23825557">
							<div id="div17" style="display:none;width:200px;">
								9월~내년 3월 줄줄이 임기 만료…경제관료 등 벌써 물밑 경쟁

서근우 신보 이사장 후임 이르면 내달 말부터 공모
기보 이사장·수출입은행장, 차기 ceo는 연말께 윤곽
기업·우리은행장 거취 관심


[ 김일규 / 이태명 기자 ] 올 9월부터 내년 3월까지 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>공기업</b></font>에 초대형 인사 태풍이 불어닥친다. 6개 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>공기업</b></font>의 최고경영자(ceo)와 3개 <font color="0000CC"><b>금융</b></font> 유관 기관장 임기가 이 기간에 끝나기 때문이다. 박근혜 정부에서 사실상 마지막으로 임명하는 대규모 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>공기업</b></font> 인사인 만큼 벌써부터 자리를 차지하려는 물밑 경쟁이 뜨겁다. 몇몇 현직 ceo가 연임을 추진하는 가운데 전·현직 경제관료와 해당 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>공기업</b></font> 출신 인사들이 ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825557','8849','%27%EA%B8%88%EC%9C%B5%EA%B3%B5%EA%B8%B0%EC%97%85+CEO+%EA%B5%90%EC%B2%B4%27+%ED%81%B0+%EC%9E%A5+%EC%84%A0%EB%8B%A4');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825557" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825557');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825557" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825568"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">소비자를 위한 신문</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://www.consumertimes.kr/sub_read.html?uid=24533&amp;section=sc4&amp;section2=종합', '139', '23825568');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(18);" onmouseout="close_me(18);"><font id="list23825568">청주녹색소비자연대, 2016년 취약계층 소비자교육홍보 및 모집안내</font></a><font id="list23825568">
							
							</font></div><font id="list23825568">
							<div id="div18" style="display:none;width:200px;">
								...비자의 건강하고 스마트한 소비생활을 위해 건강기능식품과 가정용의료기기, 상조서비스 등 다소비품목에 대해 이해하고 다소비품목 소비자<font color="0000CC"><b>피해</b></font> 구제 방법에 대해 정확하게 인식하도록 하는 노인대상교육과 주부대상교육으로 가계경제를 책임지는 주부는 <font color="0000CC"><b>금융</b></font>에 관심이 많으나 <font color="0000CC"><b>금융</b></font> 용어가 어렵고, 상품도 너무 다양하여 쉽게 접근하지 못하고 있으며, <font color="0000CC"><b>금융</b></font> 관련 모집인들만을 믿고 <font color="0000CC"><b><font color="0000CC"><b>금융</b></font>상품</b></font>에 가입하는 경우가 많다. 

 

이에 똑똑한 <font color="0000CC"><b>금융</b></font>소비자로 거듭나기 위해 <font color="0000CC"><b>금융</b></font> 및 통신 분야의 이해도를 높이고, <font color="0000CC"><b>금융</b></font>소비자<font color="0000CC"><b>피해</b></font> 발생 시 대처요령에 관한 교육과 결혼이민자들이 언어소통의 어려움으로 자유로운 경제활동이나 소비가 어려울 뿐 아니라 우리나라의 실정을 몰라 소비자 <font color="0000CC"><b>피해</b></font>가 빈번하게 발생하고 있어 다양한 소비환경에 적응할 수 있도록 기초교육...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825568','5018302','%EC%B2%AD%EC%A3%BC%EB%85%B9%EC%83%89%EC%86%8C%EB%B9%84%EC%9E%90%EC%97%B0%EB%8C%80%2C+2016%EB%85%84+%EC%B7%A8%EC%95%BD%EA%B3%84%EC%B8%B5+%EC%86%8C%EB%B9%84%EC%9E%90%EA%B5%90%EC%9C%A1%ED%99%8D%EB%B3%B4+%EB%B0%8F+%EB%AA%A8%EC%A7%91%EC%95%88%EB%82%B4');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825568" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825568');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825568" style="display:none;"></div>
						</td>
					</tr>

					<tr style="background-color:" onmouseover="this.style.backgroundColor='#F3F3F3';" onmouseout="this.style.backgroundColor='';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="23825569"></td>
						<!--↓↓출처-->
						<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;">소비자를 위한 신문</font></td>
						<!--↓↓제목-->
						<td> 
							<div class="board_01_tit_01" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:400px;">
							<a onclick="hrefPop('http://www.consumertimes.kr/sub_read.html?uid=24534&amp;section=sc56&amp;section2=고발/적발/단속', '139', '23825569');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(19);" onmouseout="close_me(19);"><font id="list23825569">인터넷(IT)전당포, “과도한 이자·담보물 임의처분” 주의</font></a><font id="list23825569">
							
							</font></div><font id="list23825569">
							<div id="div19" style="display:none;width:200px;">
												
 						

 
















최근 it기기를 담보로 대학생, 취업준비생 및 저신용자 등 <font color="0000CC"><b>금융</b></font>취약계층에게 금전을 대부하는 인터넷전당포가 증가하고 있다. 그러나 이들 업체로부터 과도한 대부이자를 요구받거나, 담보물 임의처분 <font color="0000CC"><b>피해</b></font>를 입는 사례가 있어 주의가 요구된다.

 

한국소비자원(원장 한견표)이 최근 3년간(2013∼2015년)1372소비자상담센터에 접수된 전당포 관련 소비자 <font color="0000CC"><b>피해</b></font>상담 166건을 분석한 결과, ‘계약의 중요 내용에 대한 ...     
							</div>
						</font></td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('23825569','5018302','%EC%9D%B8%ED%84%B0%EB%84%B7%28IT%29%EC%A0%84%EB%8B%B9%ED%8F%AC%2C+%E2%80%9C%EA%B3%BC%EB%8F%84%ED%95%9C+%EC%9D%B4%EC%9E%90%C2%B7%EB%8B%B4%EB%B3%B4%EB%AC%BC+%EC%9E%84%EC%9D%98%EC%B2%98%EB%B6%84%E2%80%9D+%EC%A3%BC%EC%9D%98');">
						</td>
						<!--↓↓관리버튼-->
						<td><img id="issue_menu_icon23825569" width="18" height="17" style="cursor:pointer" src="../../images/search/btn_manage_off.gif" title="이슈 미등록 정보입니다." onclick="send_issue('insert','23825569');"></td>
						<!--↓↓유사-->
						<td>
0
						</td>
						<!--↓↓수집시간-->
						<td>06-22 17:44</td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_23825569" style="display:none;"></div>
						</td>
					</tr>

				</tbody></table>
		
		
<%-- 달력 테이블 --%>
	<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td bgcolor="#F2F2F2"><table width="100%" border="0" cellspacing="3" cellpadding="3">
			<tr>
				<td height="25" align="center" bgcolor="#FFFFFF"><table  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="center" class="t">
							<%=PageIndex.getPageIndex(nowpage ,iTotalPage,"","" )  %>
						</td>
					</tr>
				</table></td>
			</tr>
		</table></td>
	</tr>
</table>
</form>
</body>
</html>


