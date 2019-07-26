<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page
	import="	java.util.*
                 	,risk.admin.pressMng.pressMng
					,risk.util.ParseRequest
					"%>
<%@page import="risk.util.PageIndex"%>

<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pressMng pMgr = new pressMng();
	pr.printParams();

	int rowCnt = 10;
	int iNowPage = pr.getInt("nowpage", 1);

	String searchKey = pr.getString("searchKey", "");
	int d_type = pr.getInt("d_type");
	String tw_type = pr.getString("tw_type", "");
	String d_seq = pr.getString("d_seq", "");
	
	String checked1="";
	String checked2="";
	String checked3="";
	String checked4="";
	if(tw_type.equals("R")){
		checked4="checked";
	}else if(tw_type.equals("T")){
		checked2="checked";
	}else if(tw_type.equals("M")){
		checked3="checked";
	}else{
		checked1="checked";
	}

	/*****시간설정*****/
	//일자
	String wDate = pr.getString("wDate", du.addDay_v2(du.getCurrentDate(), -3));
	String sDate = pr.getString("sDate", wDate);
	sDate = du.addDay_v2(wDate, -3);
	String eDate = du.addDay_v2(wDate, 3);
	//String sDate = pr.getString("sDate", du.addDay_v2(du.getCurrentDate(), -7));
	//String eDate = pr.getString("eDate", du.getCurrentDate());	
	

	//시간
	String sTime = pr.getString("sTime", "00");
	String eTime = pr.getString("eTime", "24");

	String sTimeSet = sTime + ":00:00";
	String eTimeSet = "";
	if (eTime.equals("24")) {
		eTimeSet = "23:59:59";
	} else {
		eTimeSet = eTime + ":00:00";
	}
	/*****************/
	ArrayList arList = pMgr.getPressSameDataList(iNowPage, rowCnt,
			sDate, eDate, sTimeSet, eTimeSet, searchKey, d_type, tw_type, d_seq);

	int iTotalCnt = pMgr.getTotalCnt();
	int iTotalPage = iTotalCnt / rowCnt;
	if ((iTotalCnt % rowCnt) > 0)
		iTotalPage++;

/* 	String strMsg = "";
	strMsg = " <b>" + iTotalCnt + "건</b>, " + iNowPage + "/"
			+ iTotalPage + " pages"; */
%>

<%@page import="risk.util.DateUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style>
iframe.hide {
	border: 0px solid red;
	position: absolute;
	top: 0px;
	left: 0px;
	z-index: -99;
	width: 148px;
	height: 150px;
	filter: alpha(opacity = 0);
}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script type="text/javascript">
function pageClick(paramUrl) {
	//document.fSend.nowpage.value = paramUrl.substr(paramUrl.indexOf("=") + 1, paramUrl.length);
	document.fSend.action = "popup_press_list.jsp"+paramUrl;
	document.fSend.target = "";
	document.fSend.submit();
}


function search_list(){
	document.fSend.action = "popup_press_list.jsp";
	document.fSend.submit();
}

function excel(){
	//document.iframePrc.location = "press_excel.jsp";
	document.fSend.action = "press_list_excel.jsp";
	document.fSend.target = "iframePrc";
	document.fSend.submit();
}

function reply_popup(d_date, p_docid){
	
	var f = document.fSend;	
	window.open("about:blank",'popup_portal_reply', 'width=850,height=700,scrollbars=yes');
	document.fSend.nowpage.value = 1;
	f.action = 'popup_reply_list.jsp?p_docid='+p_docid+'&wDate='+d_date;
	f.target='popup_portal_reply';
	f.submit();
	
}
</script>
</head>
<body style="margin-left: 15px;">
	<form name="fSend" method="post" action="popup_press_list.jsp">
		<input type="hidden" name="mode" value="">
		<input type="hidden" name="wDate" value="<%=wDate%>">
		<input type="hidden" name="d_seq" value="<%=d_seq%>">
		<input type="hidden" name="d_type" value="<%=d_type%>">
		<input type="hidden" name="nowpage" value="<%=iNowPage%>">
		<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="body_bg_top" valign="top">
					<table style="width: 820px;" border="0" cellpadding="0"
						cellspacing="0">
						<!-- 검색 시작 -->
						<tr>
							<td class="search_box">
								<table id="search_box" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th style="height: 30px;">검색단어</th>
										<td style="vertical-align: middle"><input
											style="width: 300px; vertical-align: middle;" class="textbox"
											type="text" name="searchKey" value="<%=searchKey%>"
											onkeydown="javascript:if (event.keyCode == 13) { search_list(); }">
										</td>
										<td style="width: 10px"></td>
										<th style="height: 30px;">검색기간</th>
										<td>
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="86"><input
														style="width: 90px; text-align: center" class="textbox"
														type="text" id="sDate" name="sDate" value="<%=sDate%>"></td>
													<td width="27"><img
														src="../../../images/admin/pressgroup/btn_calendar01.png"
														style="cursor: pointer"
														onclick="calendar_sh(document.getElementById('wDate'))" /></td>
													<td width="11" align="center">~</td>
													<td width="86"><input
														style="width: 90px; text-align: center" class="textbox"
														type="text" id="eDate" name="eDate" value="<%=eDate%>"></td>
													<td width="27"><img
														src="../../../images/admin/pressgroup/btn_calendar01.png"
														style="cursor: pointer"
														onclick="calendar_sh(document.getElementById('eDate'))" /></td>
												</tr>
											</table>
										</td>
										<td style="width: 5px"></td>
										<td><img
											src="../../../images/admin/receiver/btn_search.gif"
											onclick="search_list();"
											style="cursor: pointer; vertical-align: middle" /></td>
									</tr>
									<%if(2 == d_type){ %>
									<tr><th style="height: 30px;">검색구분</th>
													<td colspan="6"><input name="tw_type" type="checkbox" value="" <%=checked1%> >ALL													
													<input name="tw_type" type="checkbox" value="T" <%=checked2%>>T													
													<input name="tw_type" type="checkbox" value="M" <%=checked3%>>M
													<input name="tw_type" type="checkbox" value="R" <%=checked4%>>RT</td>
												</tr>
									<%} %>
								</table>
							</td>
						</tr>
						<!-- 검색 끝 -->
						<tr>
							<td style="height: 40px;">
								<table style="width: 100%;" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<!-- <td style="width: 76px;"><img
											src="../../../images/admin/receiver/btn_allselect.gif"
											onclick="allselect();" style="cursor: pointer;" /></td>
										<td><img src="../../../images/admin/receiver/btn_del.gif"
											onclick="PressgroupDataDel();" style="cursor: pointer;" /></td> -->
										<td style="width: 88px;" align="right"><img
											src="../../../images/issue/btn_allexelsave.gif"
											onclick="excel();" style="cursor: pointer;" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- 게시판 시작 -->
						<tr>
							<td>
								<table id="board_01" border="0" cellpadding="0" cellspacing="0"
									style="table-layout: fixed;">
									<col width="15%">
									<col width="12%">
									<col width="*">
									<col width="12%">
									<col width="9%">									
									<!-- <col width="9%"> -->
									<% if(d_type==1){%>
									<col width="9%">
									<col width="9%">
									<% }%>
									<tr>
										<th>문서번호</th>
										<th>매체명</th>
										<th>제목</th>
										<th>수집일자</th>
										<th>유사율</th>										
										<!-- <th>TOP노출</th> -->
										<% if(d_type==1){%>
										<th>TOP노출</th>
										<th>댓글 수</th>
										<%}else{%>
										<th>구분</th>
										<%}%>
									</tr>
									
									<%
									Map map = null;
									if(	arList.size() > 0	){
										String siteNm = "";
										String topYn = "";
										for(int i=0; i < arList.size(); i++){
											map = new HashMap();
											map = (HashMap)arList.get(i);
											
											if(d_type == 1){
												siteNm = map.get("d_board").toString();
											}else{
												siteNm = map.get("d_site").toString();
											}
											
											if(map.get("d_top").toString().equals("1")){
												topYn = "Y";
											}else{
												topYn = "N";
											}
									%>
									
									<tr>
										<td><%=map.get("i_docid")%></td>
										<td><%=siteNm%></td>	
										<td><p style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;" title="<%=map.get("d_title")%>"
										><a  onclick="window.open('http://hub.buzzms.co.kr?url=<%=map.get("encode_url")%>', 'new_popup','width=850,height=700,scrollbars=yes;');return false;" target="_blank" href="#" 
										><%=map.get("d_title")%></a></p></td>
										<td><%=map.get("d_date")%></td>	
										<td><%=map.get("d_percent")%>%</td>
										<%-- <td><%=map.get("d_top")%></td> --%>
										<%if(d_type==1){ %>
										<td><%=topYn%></td>
										<td><a href="javascript:reply_popup('<%=map.get("d_date")%>','<%=map.get("p_docid")%>');" ><%=map.get("reply_count")%></a></td>
										<%}else{%>
										<td><%=map.get("d_type")%></td>
										<%}%>
									</tr>
									<%
										}
									}
									%>
								</table>
							</td>
						</tr>
						<!-- 게시판 끝 -->
						<tr>
							<td style="height: 40px;">
								<table style="width: 100%;" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td style="width: 128px;">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<!-- <td><img
														src="../../../images/admin/receiver/btn_allselect.gif"
														onclick="allselect();" style="cursor: pointer;" /></td>
													<td><img
														src="../../../images/admin/receiver/btn_del.gif"
														onclick="PressgroupDataDel();" style="cursor: pointer;" /></td> -->
												</tr>
											</table>
										</td>
										<td align="right"><img
											src="../../../images/issue/btn_allexelsave.gif"
											onclick="excel();" style="cursor: pointer;" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table align="center" style="padding-top: 10px;" border="0"
									cellpadding="0" cellspacing="1">
									<tr>
										<td>
											<table id="paging" border="0" cellpadding="0" cellspacing="2">
												<tr>
													<%=PageIndex.getPageIndex(iNowPage, iTotalPage, "", "")%>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</form>
	<iframe id="iframePrc" name="iframePrc" style="display: none;"
		width="0px" height="0px"></iframe>
	<%-- 달력 테이블 --%>
	<table width="162" border="0" cellspacing="0" cellpadding="0"
		id="calendar_conclass" style="position: absolute; display: none;">
		<tr>
			<td><img src="../../../images/calendar/menu_bg_004.gif"
				width="162" height="2"></td>
		</tr>
		<tr>
			<td align="center"
				background="../../../images/calendar/menu_bg_005.gif"><table
					width="148" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="6"></td>
					</tr>
					<tr>
						<td id="calendar_calclass"></td>
					</tr>
					<tr>
						<td height="5"></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td><img src="../../../images/calendar/menu_bg_006.gif"
				width="162" height="2"></td>
		</tr>
	</table>
</body>
</html>