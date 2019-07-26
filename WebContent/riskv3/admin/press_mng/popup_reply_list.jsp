<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.PageIndex"%>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.summary.SummaryMgr
                 "
%>

<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	SummaryMgr sMgr = new SummaryMgr();
	
	String wDate = pr.getString("wDate");
	String p_docid = pr.getString("p_docid");
	String r_trnd = pr.getString("r_trnd", "");
	String r_relation_word = pr.getString("r_relation_word", "");
	int totalCnt = pr.getInt("totalCnt",0);
	System.out.println("========================================");
	pr.printParams();
	
	int nowpage = Integer.parseInt( pr.getString("nowpage","1") );
	int rowCnt = 10;
	int totalPage = 0;
	
	ArrayList arData = new ArrayList();
	arData = sMgr.getReplyDataList(nowpage, rowCnt, wDate.replaceAll("-", ""), p_docid, r_trnd, r_relation_word, SS_M_ID);
	
	if(totalCnt == 0){
		totalCnt = sMgr.getReplyTotalCnt();
	}
	totalPage = totalCnt / rowCnt;
	if(totalCnt % rowCnt > 0){
		totalPage += 1;
	}
	
	//String srtMsg = "총 건수 : "+su.digitFormat(totalCnt)+" 건, "+su.digitFormat(nowpage)+"/"+su.digitFormat(totalPage)+" pages";
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
	document.fSend.nowpage.value = paramUrl.substr(
			paramUrl.indexOf("=") + 1, paramUrl.length);
	document.fSend.action = "popup_reply_list.jsp";
	document.fSend.target = "";
	document.fSend.submit();
}


function search_list(){
	document.fSend.action = "popup_reply_list.jsp";
	document.fSend.submit();
}

function excel(){
	//document.iframePrc.location = "press_excel.jsp";
	document.fSend.action = "press_list_excel.jsp";
	document.fSend.target = "iframePrc";
	document.fSend.submit();
}

function popup(d_date, p_docid){
	
	var f = document.fSend;	
	window.open("about:blank",'popup_portal_reply', 'width=850,height=700,scrollbars=yes');
	f.action = 'popup_reply_list.jsp?d_type='+flag+"&p_docid="+d_seq+"&wDate="+wDate;
	f.target='popup_portal_reply';
	f.submit();
	
}
</script>
</head>
<body style="margin-left: 15px;">
	<form name="fSend" method="post" action="popup_reply_list.jsp">
		<input type="hidden" name="mode" value="">
		<input type="hidden" name="p_docid" value="<%=p_docid%>">
		<input type="hidden" name="wDate" value="<%=wDate%>">
		<input type="hidden" name="nowpage" value="<%=nowpage%>">
		<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="body_bg_top" valign="top">
					<table style="width: 820px;" border="0" cellpadding="0"
						cellspacing="0">
						<!-- 검색 시작 -->
						<tr>
							<td class="search_box">
							</td>
						</tr>
						<tr>
							<td>
								<table id="board_01" border="0" cellpadding="0" cellspacing="0"
									style="table-layout: fixed;">
									<col width="15%">
									<col width="*">
									<col width="10%">
									<tr>
										<th>수집일자</th>
										<th>내용</th>
										<th>성향</th>										
									</tr>
									
									<%
									Map map = null;
									String senti = "";
									if(	arData.size() > 0	){
										for(int i=0; i < arData.size(); i++){
											map = new HashMap();
											map = (HashMap)arData.get(i);
											
											if("1".equals(map.get("r_trend").toString())){
												senti= "긍정";
											}else if("2".equals(map.get("r_trend").toString())){
												senti= "부정";
											}else{
												senti= "중립";
											}
									%>
									
									<tr>
										<td><%=map.get("r_datetime")%></td>
										<td><p style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;" title="<%=map.get("r_content")%>"><%=map.get("r_content")%></p></td>
										<td><%=senti%></td>
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
										<!-- <td align="right"><img
											src="../../../images/issue/btn_allexelsave.gif"
											onclick="excel();" style="cursor: pointer;" /></td> -->
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
													<%=PageIndex.getPageIndex(nowpage, totalPage, "", "")%>
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