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
<%@page import="risk.search.SearchPortalBean"%>
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
	
	SearchPortalBean spb = new SearchPortalBean();
	
	
	
	
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
	uei.setSiteValue(pr.getString("site_value","0"));
	String[] sitelength = null;
	if(!uei.getSiteValue().equals("")){
		sitelength = uei.getSiteValue().split(",");
	}
	//ArrayList portalList = new ArrayList();
	//portalList = smgr.getPortalList(nowpage, Integer.parseInt(uei.getSt_list_cnt()), uei.getDateFrom(), uei.getDateTo(), keyword);
	ArrayList get_potal_list = new ArrayList();

	get_potal_list = smgr.Get_portal_total_list(uei.getSiteValue(),nowpage,Integer.parseInt(uei.getSt_list_cnt()),keyword,uei.getDateFrom(),uei.getDateTo());
	

	int iTotalCnt= smgr.miTotalCnt;
    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script src="<%=SS_URL%>js/timer.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/input_date.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
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
		//div_show.innerHTML = eval('div'+i).innerHTML;
    	//div_show.style.top = event.y + document.body.scrollTop + 15;
    	//div_show.style.display='';
    }

    function close_me(i){
    	//div_show.style.display='none';
    }

    function pageClick( paramUrl ) {
    	var f = document.fSend;
        f.action = "search_main_initall.jsp" + paramUrl;
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
	
	function check_cause(no){
    	var f = document.fSend.portalCheck;
    	var e = document.fSend;
    	var temp_value = '';
        var cnt = 0;
        for(var i = 0; i < f.length; i++){
            if(f(i).checked == true){
                if(temp_value==''){
                    temp_value = f(i).value;
                    cnt++;
                }else{
                    temp_value += ','+f(i).value;
                    cnt++;
                }
            }
        }
        if(cnt == 1){
            document.all.all_check.checked = false;
        }
        if(document.all.all_check.checked == false){
            if(cnt == 0){
                f(no).checked = true;
            }
        }
        if(cnt == f.length){
        	document.all.all_check.checked = true;
            for(var j = 0; j < f.length; j++){
                f(j).checked = false;
            }
        }
		e.site_value.value = temp_value;
    }
	function checkall(){
        var f = document.fSend.portalCheck;
        var cnt = 0;
        for(var i = 0; i < f.length; i++){
            if(f(i).checked == true){
                cnt++;
            }
        }
        if(cnt >= 1){
            for(var j = 0; j < f.length; j++){
                f(j).checked = false;
            }
 			document.all.all_check.checked = true;           
        }else{
        	document.all.all_check.checked = true;
        }
        document.fSend.site_value.value = "0";
    }
//-->

</script>
</head>

<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<DIV id=div_show style="FONT-SIZE: 12px; PADDING-RIGHT: 5px; DISPLAY: none; PADDING-LEFT: 5px; LEFT: 140px;
                        width:500px;height:50px;
                        PADDING-BOTTOM: 5px; PADDING-TOP: 5px; POSITION: absolute; BACKGROUND-COLOR: #FFFFCC; border: #ff6600 2px solid;">
</DIV>

<form name="fSend" action="search_main_initall.jsp" method="post">
<input type="hidden" name="Period" value="">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="site_value" value="<%=uei.getSiteValue()%>">
<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" style="padding: 0px 0px 0px 0px;"><table width="780" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="35" align="center" background="images/issue_search_bg01.gif"><table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="16"><img src="images/issue_ico01.gif" width="11" height="12"></td>
						<td width="70" style="padding: 3px 0px 0px 0px;"><strong>검색단어 :</strong></td>
						<td width="350"><input name="keyword" type="text" class="txtbox" value="<%=keyword%>" size="40" OnKeyDown="Javascript:if (event.keyCode == 13) { doSearch( this.value ); }" style="vertical-align:middle">
							<a href="javascript:doSearch();"><img src="images/btn_search.gif" width="61" height="22" hspace="3" style="vertical-align:middle"></a>
						</td>
						<td class="t" style="padding: 5px 0px 0px 0px;">&nbsp;</td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td background="images/issue_search_bg02.gif"><img src="images/brank.gif" width="1" height="5"></td>
			</tr>
			<tr>
				<td align="center" background="images/issue_search_bg02.gif"><table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="400" height="30"><table width="390" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12" align="center"><img src="images/issue_ico02.gif" width="5" height="9"></td>
								<td width="70" style="padding: 3px 0px 0px 3px;"><strong>검색기간 :</strong></td>
								<td width="305">
									<input name="sDateFrom" type="text" size="8" maxlength="9" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=uei.getDateFrom()%>" style="vertical-align:middle"> ~
									<input name="sDateTo" type="text" size="8" maxlength="10" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=uei.getDateTo()%>" style="vertical-align:middle">
									<a href="javascript:setDateTerm(0);"><img src="images/con_data01.gif" width="30" height="18" style="vertical-align:middle"></a>
									<a href="javascript:setDateTerm(2);"><img src="images/con_data02.gif" width="27" height="18" style="vertical-align:middle"></a>
									<a href="javascript:setDateTerm(6);"><img src="images/con_data03.gif" width="27" height="18" style="vertical-align:middle"></a>
								</td>
							</tr>
						</table></td>
					</tr>
				</table></td>
			</tr>
			
			<tr>
       			<td colspan="2" background="images/issue_dotbg.gif"><img src="images/brank.gif" width="1" height="1"></td>
      		</tr>
    
			<tr>
				<td align="center" background="images/issue_search_bg02.gif"><table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="500" height="30"><table width="500" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12" align="center"><img src="images/issue_ico02.gif" width="5" height="9"></td>
								<td width="70" style="padding: 3px 0px 0px 3px;"><strong>검색대상 :</strong></td>
								<td width="400">
									<table width="100%">
										<col width="16%"><col width="16%"><col width="16%"><col width="16%"><col width="16%"><col width="16%">
											<tr>
												<td><input name="all_check" type="checkbox" onclick="checkall();" <%if(uei.getSiteValue().equals("0")){out.print("checked");}else{if(sitelength.length==5){out.print("checked");}}%>><strong>전체</strong></td>
												<td><input name="portalCheck" type="checkbox" onclick="check_cause(0);" value="6050" <%if(uei.getSiteValue().length()!=0){if(su.inarray(sitelength,"6050")){out.print("checked");}}%>>네이버</td>
												<td><input name="portalCheck" type="checkbox" onclick="check_cause(1);" value="6051" <%if(uei.getSiteValue().length()!=0){if(su.inarray(sitelength,"6051")){out.print("checked");}}%>>다음</td>
												<td><input name="portalCheck" type="checkbox" onclick="check_cause(2);" value="6052" <%if(uei.getSiteValue().length()!=0){if(su.inarray(sitelength,"6052")){out.print("checked");}}%>>네이트</td>
												<td><input name="portalCheck" type="checkbox" onclick="check_cause(3);" value="6055" <%if(uei.getSiteValue().length()!=0){if(su.inarray(sitelength,"6055")){out.print("checked");}}%>>야후</td>
												<td><input name="portalCheck" type="checkbox" onclick="check_cause(4);" value="6054" <%if(uei.getSiteValue().length()!=0){if(su.inarray(sitelength,"6054")){out.print("checked");}}%>>파란</td>
											</tr>
									</table>
								</td>
							</tr>
						</table></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td background="images/issue_search_bg02.gif"><img src="images/brank.gif" width="1" height="5"></td>
			</tr>
			<tr>
				<td bgcolor="#D5D5D5"><img src="images/brank.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="30" valign="bottom" align="right"><strong> <%=iTotalCnt%></strong> 건 <strong><%=iTotalCnt==0?0:nowpage%></strong>/<strong><%=iTotalPage%></strong> pages</td>
	</tr>
	<tr>
		<td><table width="780" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="5" bgcolor="#7CA5DD"><img src="images/brank.gif" width="1" height="2"></td>
			</tr>
			<tr>
				<td width="169" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>출처</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="images/con_bar03.gif" width="1" height="27"></td>
				<td width="490" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>제목</strong></td>
				<td width="1" valign="bottom" bgcolor="#EEEEEE"><img src="images/con_bar03.gif" width="1" height="27"></td>
				<td width="119" align="center" bgcolor="#EEEEEE" style="padding: 3px 0px 0px 0px;"><strong>수집시간</strong></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="#CDD4D6"><img src="images/brank.gif" width="1" height="1"></td>
			</tr>
<%
	for(int i = 0; i < get_potal_list.size(); i++){
		spb = (SearchPortalBean)get_potal_list.get(i);
		if(i != 0){
%>
			<tr>
				<td colspan="5"><img src="images/con_dotline.gif" width="100%" height="1"></td>
			</tr>
<%
		}
%>
			<tr>
				<td align="center"><%=spb.getSD_NAME()%></td>
				<td width="1" valign="bottom">&nbsp;</td>
				<td style="padding: 3px 0px 0px 5px;">
					<nobr style="text-overflow:ellipsis; overflow:hidden; width:450">
					<a onClick="hrefPop('<%=spb.getP_URL()%>');" href="javascript:void(0);" onmouseover="show_me(<%=i%>);" onmouseout="close_me(<%=i%>);" class="sch_title">
					<%=su.cutKey(su.toHtmlString(spb.getP_TITLE()), "삼성 SDS", 450, "blue")%></a></nobr>
					
<!--					<DIV id=div<%=i%> style="DISPLAY: none;width:200px;height:80px;">     -->
<!--			        </DIV>-->
				</td>
				<td width="1" valign="bottom">&nbsp;</td>
				<td align="center"><%=spb.getP_STIME().substring(5, 16)%></td>
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


