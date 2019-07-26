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
<link rel="stylesheet" href="../../css/old_search/basic.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}

</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/timer.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/input_date.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
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
<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" style="padding: 0px 0px 0px 0px;"><table width="780" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="35" align="center" background="../../images/old_search/issue_search_bg01.gif"><table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="16"><img src="../../images/old_search/issue_ico01.gif" width="11" height="12"></td>
						<td width="70" style="padding: 3px 0px 0px 0px;"><strong>검색단어 :</strong></td>
						<td width="350"><input name="keyword" type="text" class="txtbox" value="<%=keyword%>" size="40" OnKeyDown="Javascript:if (event.keyCode == 13) { doSearch( this.value ); }" style="vertical-align:middle">
							<a href="javascript:doSearch();"><img src="../../images/old_search/btn_search.gif" width="61" height="22" hspace="3" style="vertical-align:middle"></a>
						</td>
						<td class="t" style="padding: 5px 0px 0px 0px;">&nbsp;</td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td background="../../images/old_search/issue_search_bg02.gif"><img src="../../images/old_search/brank.gif" width="1" height="5"></td>
			</tr>
			<tr>
				<td align="center" background="../../images/old_search/issue_search_bg02.gif"><table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="400" height="30"><table id="search_box" width="390" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12" align="center"><img src="../../images/old_search/issue_ico02.gif" width="5" height="9"></td>
								<td width="70" style="padding: 3px 0px 0px 3px;"><strong>검색기간 :</strong></td>
								<td width="305">
									<input name="sDateFrom" type="text" size="8" maxlength="9" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=uei.getDateFrom()%>" style="vertical-align:middle"> ~
									<input name="sDateTo" type="text" size="8" maxlength="10" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=uei.getDateTo()%>" style="vertical-align:middle">
									<a href="javascript:setDateTerm(0);"><img src="../../images/old_search/con_data01.gif" width="30" height="18" style="vertical-align:middle"></a>
									<a href="javascript:setDateTerm(2);"><img src="../../images/old_search/con_data02.gif" width="27" height="18" style="vertical-align:middle"></a>
									<a href="javascript:setDateTerm(6);"><img src="../../images/old_search/con_data03.gif" width="27" height="18" style="vertical-align:middle"></a>
								</td>
							</tr>
						</table></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td background="../../images/old_search/issue_search_bg02.gif"><img src="../../images/old_search/brank.gif" width="1" height="5"></td>
			</tr>
			<tr>
				<td bgcolor="#D5D5D5"><img src="../../images/old_search/brank.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="30" valign="bottom" align="right"><strong> <%=iTotalCnt%></strong> 건 <strong><%=iTotalCnt==0?0:nowpage%></strong>/<strong><%=iTotalPage%></strong> pages</td>
	</tr>
	<tr>
		<td><table width="780" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="9" bgcolor="#7CA5DD"><img src="../../images/old_search/brank.gif" width="1" height="2"></td>
			</tr>
			<tr>
				<td width="169" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>출처</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="../../images/old_search/con_bar03.gif" width="1" height="27"></td>
				<td width="301" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>제목</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="../../images/old_search/con_bar03.gif" width="1" height="27"></td>
				<td width="169" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>수집시간</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="../../images/old_search/con_bar03.gif" width="1" height="27"></td>
				<td width="40" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>관리</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="../../images/old_search/con_bar03.gif" width="1" height="27"></td>
				<td width="89" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>노출시간</strong></td>
			</tr>
			<tr>
				<td colspan="9" bgcolor="#CDD4D6"><img src="../../images/old_search/brank.gif" width="1" height="1"></td>
			</tr>
<%
	for(int i = 0; i < portalList.size(); i++){
		PortalBean pb = (PortalBean)portalList.get(i);
		
		
		//관리버튼
	   	String managerButton = "";
		if(uei.getSearchMode().equals("ALLKEY")){	  		
			managerButton ="<img id=\"issue_menu_icon"+pb.getNo()+"\" width=\"18\" height=\"17\" style=\"cursor:pointer\" ";
			if(pb.getIssue_check().equals("Y")){
				managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\">";
			}else{
				managerButton += "src=\"../../images/search/btn_manage_off.gif\" title=\"이슈 미등록 정보입니다.\" onclick=\"send_issue('insert','"+pb.getNo()+"');\">";
			}
		}
		
		
		
		if(i != 0){
%>
			<tr>
				<td colspan="9"><img src="../../images/old_search/con_dotline.gif" width="100%" height="1"></td>
			</tr>
<%
		}
%>
			<tr height="20">
				<td align="center"><%=pb.getName()%></td>
				<td width="1" valign="bottom">&nbsp;</td>
				<td style="padding: 3px 0px 0px 5px;">
					<nobr style="text-overflow:ellipsis; overflow:hidden; width:301">
					<a onClick="hrefPop('<%=pb.getUrl()%>');" href="javascript:void(0);"  class="sch_title">
					<%=su.cutKey(su.toHtmlString(pb.getTitle()), "포스코 POSCO", 450, "blue")%></a></nobr>
					
					<DIV id=div<%=i%> style="DISPLAY: none;width:200px;height:80px;">
					<%=pb.getHighrightHtml(250, "blue", "포스코 POSCO")%>     
			        </DIV>
				</td>
				<td width="1" valign="bottom">&nbsp;</td>
				<td align="center"><%=du.getDate(pb.getStime(),"yyyy-MM-dd HH:mm:ss")%></td>
				
				
				<td width="1" valign="bottom">&nbsp;</td>
				<td align="center"><%=managerButton%></td>
				
				
				<td width="1" valign="bottom">&nbsp;</td>
				<td align="center"><%if(pb.getExposeTime()==null){out.print("00:00:00");}else{out.print(pb.getExposeTime());}%></td>
			</tr>
<%
	}
%>

		</table></td>
	</tr>
	<tr>
		<td height="10"></td>
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


