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
<!-- <link rel="stylesheet" href="../../css/old_search/basic.css" type="text/css"> -->
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
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}
	
	function setIssue_check(t_seq){
		var yn = $("[name=issue_check_"+t_seq+"]").val();
		$.ajax({
			type : "POST"
			,url: "aj_data_prc.jsp"
			,timeout: 30000
			,data : {t_seq:t_seq, issue_check:yn}
			,dataType : 'text'
			,success : function(data){
				if(data == t_seq){
					alert("수정되었습니다.");
				}else{
					alert("수정 실패 되었습니다.");
				}
			}
		});
	}
	
</script>
</head>
<body>
<DIV id=div_show style="FONT-SIZE: 12px; PADDING-RIGHT: 5px; DISPLAY: none; PADDING-LEFT: 5px; LEFT: 140px;
                        width:500px;height:50px;
                        PADDING-BOTTOM: 5px; PADDING-TOP: 5px; POSITION: absolute; BACKGROUND-COLOR: #FFFFCC; border: #ff6600 2px solid;">
</DIV>

<form name="send_mb" id="send_mb" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="md_seq">
<input type="hidden" name="subMode">
</form>

<table style="height:100%" border="0" cellpadding="0" cellspacing="0"><!-- width:100% -->
	<tr>
		<td class="body_bg_top" valign="top" style="width:auto">
		<table align="center" style="width:820px;margin-left:20px" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="text-align: left;"><img src="../../images/search/tit_icon.gif" /><img src="../../images/search/tit_0199.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">포탈TOP검색</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<form name="fSend" action="search_main_top.jsp" method="post">
				<input type="hidden" name="Period" value="">
				<input type="hidden" name="nowpage" value="<%=nowpage%>">
				<div class="article">
				 	<div class=" ui_searchbox_00">
						<div class="c_wrap">
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>검색단어</span>
								<span class="txts">
									<input id="input_date_00" type="text" class="ui_input_02" name="keyword"  value="<%=keyword%>" onkeydown="javascript:if(event.keyCode == 13){doSearch();}"><label for="input_date_00" class="invisible">검색어입력</label
									><button class="ui_btn_02" onclick="doSearch();" ><span class="icon search_01">-</span>검색</button>
								</span>
							</div>
						</div>
						<div class="c_sort">
							<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>검색기간</span>
								<span class="txts">
									<input name="sDateFrom" id="sDateFrom" value="<%=uei.getDateFrom()%>" type="text" class="ui_datepicker_input input_date_first" readonly><label for="sDateFrom">날짜입력</label>
									<select id="stime" name="stime" class="ui_select_04">
									<%
									  String timeSelected = "";	
									  for(int i =0; i < 24; i ++){
										if(Integer.parseInt(uei.getStime()) == i){
											timeSelected = "selected";
										}else{
											timeSelected = "";
										}
										
									%>
									<option value="<%=i%>" <%=timeSelected%> ><%=i%>시</option>
									<%} %>
									</select><label for="stime"></label>~
									<input name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>" type="text" class="ui_datepicker_input input_date_last" readonly><label for="sDateTo">날짜입력</label>
									<select id="etime" name="etime" class="ui_select_04">
									<%
									  timeSelected = "";	
									  for(int i =0; i < 24; i ++){
										if(Integer.parseInt(uei.getEtime()) == i){
											timeSelected = "selected";
										}else{
											timeSelected = "";
										}
										
									%>
									<option value="<%=i%>" <%=timeSelected%> ><%=i%>시</option>
									<%} %>
									</select><label for="etime"></label>
									<a href="javascript:setDateTerm(0);" class="ui_btn_06">금일</a>
									<a href="javascript:setDateTerm(2);" class="ui_btn_06">3일</a>
									<a href="javascript:setDateTerm(6);" class="ui_btn_06">7일</a>
								</span>
							</div>
						</div>
					</div>
					<div class="ui_searchbox_toggle">
						<a href="#" class="btn_toggle active">검색조건 열기/닫기</a>
					</div>
				</div>
				</form>
		<!-- // 검색 -->
				</td>
			</tr>
			<tr>
				<td height="30" valign="bottom" align="right"><strong> <%=iTotalCnt%></strong> 건 <strong><%=iTotalCnt==0?0:nowpage%></strong>/<strong><%=iTotalPage%></strong> pages</td>
			</tr>
		</table>
		<table id="board_01"  style="width:820px;margin-left:20px;table-layout: fixed;" border="0" cellpadding="0" cellspacing="0">
			<colgroup><col width="5%"><col width="12%"><col width="12%"><col width="*"><col width="15%"><col width="10%"><col width="10%"></colgroup>
			<tr>
				<th><input type="checkbox" name="checkall" id="tt" onclick="checkAll(this.checked);" value=""></th>
				<th>출처</th>
				<th>게시판</th>
				<th>제목</th>
				<th>수집일시</th>
				<th>비노출</th>
				<th>노출시간</th>
			</tr>
<%
	for(int i = 0; i < portalList.size(); i++){
		String overColor = "#F3F3F3";
    	String outColor = "";
    	String bookMarkColor = "";
    	String comfirmColor = "";
    	
		PortalBean pb = (PortalBean)portalList.get(i);
		
		//if(pb.getS_seq().equals("6050")){
		//	pb.setName("네이버("+pb.getT_board()+")");
		//}
		
		String selected="";
		if(pb.getIssue_check().equals("Y")){
			selected="selected";
		}else{
			selected="";
		}
		
		if(i != 0){
%>
<%
		}
%>
			<tr style="background-color:<%=bookMarkColor%>" onmouseover="this.style.backgroundColor='<%=overColor%>';" onmouseout="this.style.backgroundColor='<%=outColor%>';">
				<td><input name="check" type="checkbox" value="<%=pb.getNo()%>" onclick="getIssue(this);" ></td>
				<td><font style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;"><%=pb.getName()%></font>
				<td><%=pb.getT_board()%></td>
				<td style="text-align: left;"><div style="width:250px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01"
				title="<%=pb.getTitle()%>"><a onClick="hrefPop('<%=pb.getUrl()%>');" class="sch_title" href="javascript:void(0);"
					><%=su.cutKey(su.toHtmlString(pb.getTitle()), "금감원 금융감독원", 450, "blue")%></a></div>
				</td>
				<td align="center"><%=du.getDate(pb.getStime(),"yyyy-MM-dd HH:mm:ss")%></td>
				
				<%-- <td align="center"><%=managerButton%></td> --%>
				<td align="center"><select name="issue_check_<%=pb.getNo()%>" onchange="setIssue_check(<%=pb.getNo()%>);"><option value="N" <%=selected%>>N</option><option value="Y" <%=selected%> >Y</option>
					</select>
				</td>
				
				<td align="center"><%if(pb.getExposeTime()==null){out.print("00:00:00");}else{out.print(pb.getExposeTime());}%></td>
			</tr>
<%
	}
%>
		</table>
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 페이징 -->
			<tr>
				<td align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage ,iTotalPage,"","" )  %>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td></tr>
	</table>
</body>
</html>


