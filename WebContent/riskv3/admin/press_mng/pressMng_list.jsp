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

	/*****시간설정*****/
	//일자
	String sDate = pr.getString("sDate",
			du.addDay_v2(du.getCurrentDate(), -7));
	String eDate = pr.getString("eDate", du.addDay_v2(du.getCurrentDate(), -1));

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

	ArrayList arList = pMgr.getPressDataList(iNowPage, rowCnt,
			sDate, eDate, sTimeSet, eTimeSet, searchKey);

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
	//document.fSend.nowpage.value = paramUrl.substr(	paramUrl.indexOf("=") + 1, paramUrl.length);
	document.fSend.action = "pressMng_list.jsp"+paramUrl;
	document.fSend.target = "";
	document.fSend.submit();
}


function search_list(){
	document.fSend.nowpage.value = 1;
	document.fSend.action = "pressMng_list.jsp";
	document.fSend.target = "";
	document.fSend.submit();
}

function excel(){
	//document.iframePrc.location = "press_excel.jsp";
	document.fSend.action = "press_excel.jsp";
	document.fSend.target = "iframePrc";
	document.fSend.submit();
}

function popup(flag, d_seq, wDate){
	var f = document.fSend;
	document.fSend.nowpage.value = 1;
	window.open("about:blank",'popup_press', 'width=850,height=700,scrollbars=yes');
	f.action = 'popup_press_list.jsp?d_type='+flag+"&d_seq="+d_seq+"&wDate="+wDate;
	f.target='popup_press';
	f.submit();
}

function updateKeyword(d_seq){
	var f = document.fSend;
	window.open("about:blank",'popup_press_update', 'width=850,,height=150,scrollbars=yes');
	f.action = 'popup_addKeyword.jsp?d_seq='+d_seq;
	f.target='popup_press_update';
	f.submit();
}
</script>
</head>
<body style="margin-left: 15px;">
	<form name="fSend" method="post" action="pressMng_list.jsp">
		<input type="hidden" name="mode" value=""> <input
			type="hidden" name="nowpage" value="<%=iNowPage%>">
		<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="body_bg_top" valign="top">
					<table style="width: 820px;" border="0" cellpadding="0"
						cellspacing="0">
						<!-- 타이틀 시작 -->
						<tr>
							<td class="tit_bg" style="height: 67px; padding-top: 20px;">
								<table style="width: 100%;" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td><img
											src="../../../images/admin/receiver/tit_icon.gif" /><img
											src="../../../images/admin/pressgroup/tit_002222.gif" /></td>
										<td align="right">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td class="navi_home">HOME</td>
													<td class="navi_arrow">관리자</td>
													<td class="navi_arrow2">보도자료관리</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="15"></td>
						</tr>
						<!-- 타이틀 끝 -->

						<!-- 검색 시작 -->
						<tr>
							<td class="search_box">
								<table id="search_box" border="0" cellpadding="0"
									cellspacing="0">
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
														onclick="calendar_sh(document.getElementById('sDate'))" /></td>
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
									<col width="8%">
									<col width="15%">
									<col width="*">
									<col width="12%">
									<col width="5%">
									<col width="13%">									
									<col width="8%">
									<tr>
										<th>문서번호</th>
										<th>작성일자</th>
										<th>제목</th>
										<th colspan="2">주요키워드</th>										
										<th>유사<br>(News/Twitter)</th>
										<th>전체<br>댓글수</th>
									</tr>
									
									<%
									Map map = null;
									if(	arList.size() > 0	){
										String keyword ="";
										for(int i=0; i < arList.size(); i++){
											map = new HashMap();
											map = (HashMap)arList.get(i);
											keyword = map.get("d_keyword").toString();											
											//keyword = keyword.split(" ")[0];
									%>
									<tr>
										<td style="line-height:16px"><%=map.get("d_seq")%></td>
										<td style="line-height:16px"><%=map.get("d_date")%></td>	
										<td style="line-height:16px"><p style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;" title="<%=map.get("d_title")%>">
											<a href="http://hub.buzzms.co.kr?url=<%=map.get("encode_url")%>" target="_blank"><%=map.get("d_title")%></a>
											</p>
										</td>
										<td style="line-height:16px;text-align: left;"><p title="<%=map.get("d_keyword").toString()%>" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;display:inlie-block;padding:0 8px 0 0;line-height:16px;vertical-align:top"><%=keyword%></p></td>
										<td style="line-height:16px;"><div><img src="../../../images/admin/pressgroup/btn_modify.gif" title="<%=map.get("d_keyword").toString()%>" onclick="updateKeyword('<%=map.get("d_seq")%>');" style="cursor: pointer;vertical-align:top"></div></td>
										<td style="line-height:16px"><a href="javascript:popup(1,<%=map.get("d_seq")%>,'<%=map.get("d_date")%>');" ><%=map.get("d_same_count")%></a> / <a href="javascript:popup(2,<%=map.get("d_seq")%>,'<%=map.get("d_date")%>');"><%=map.get("d_tw_count")%></a></td>
										<td  style="line-height:16px"><%=map.get("reply_count") %> </td>
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