<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page
	import="	java.util.ArrayList
                 	,risk.admin.pressgroup.PressgroupDataBean
                 	,risk.admin.pressgroup.PressgroupDataMgr
					,risk.util.ParseRequest
					"%>
<%@page import="risk.util.PageIndex"%>

<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	PressgroupDataBean pBean = null;
	PressgroupDataMgr pMgr = new PressgroupDataMgr();
	pr.printParams();

	int rowCnt = 10;
	int iNowPage = pr.getInt("nowpage", 1);

	String searchKey = pr.getString("searchKey", "");

	/*****시간설정*****/
	//일자
	String sDate = pr.getString("sDate",
			du.addDay_v2(du.getCurrentDate(), -30));
	String eDate = pr.getString("eDate",
			du.addDay_v2(du.getCurrentDate(), +30));

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

	ArrayList arList = pMgr.getPressgroupDataList(iNowPage, rowCnt,
			sDate, eDate, sTimeSet, eTimeSet, searchKey);

	int iTotalCnt = pMgr.getTotalCnt();
	int iTotalPage = iTotalCnt / rowCnt;
	if ((iTotalCnt % rowCnt) > 0)
		iTotalPage++;

	String strMsg = "";
	strMsg = " <b>" + iTotalCnt + "건</b>, " + iNowPage + "/"
			+ iTotalPage + " pages";
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
<script language="javascript">

	function pageClick(paramUrl) {
		document.fSend.nowpage.value = paramUrl.substr(
				paramUrl.indexOf("=") + 1, paramUrl.length);
		document.fSend.submit();
	}

	function PressgroupDataAdd() {
		location.href = 'pressgroup_data_detail.jsp?mode=add';
	}

	function PressgroupDataDetal(pg_seq) {
		location.href = 'pressgroup_data_detail.jsp?mode=update&target_seq='
				+ pg_seq;
	}

	function reloding() {
		var f = document.fSend;
		f.target = '';
		f.action = 'pressgroup_data_list.jsp';
		f.submit();
	}

	function PressgroupDataDel() {

		var f = document.fSend;

		if (f.pg_seq.length) {
			var cnt = 0;
			for (var i = 0; i < f.pg_seq.length; i++) {
				if (f.pg_seq[i].checked) {
					cnt++;
				}
			}

			if (cnt == 0) {
				alert("배포기사를 선택하세요.");
				return;
			}
		} else {
			if (!f.pg_seq.checked) {
				alert("배포기사를 선택하세요.");
				return;
			}
		}

		if (confirm("삭제 하시겠습니까?")) {

			f.mode.value = 'delete';
			f.target = 'iframePrc';
			f.action = 'pressgroup_data_prc.jsp';
			f.submit();

		}

	}

	var allcheck = 0;
	function allselect() {
		var frm = document.all;
		if (frm.tt) {
			if (allcheck == 0) {
				if (frm.tt.length > 1) {
					for (i = 0; i < frm.tt.length; i++) {
						frm.tt[i].checked = true;
					}
				} else {
					frm.tt.checked = true;
				}
				allcheck = 1;
			} else {
				if (frm.tt.length > 1) {
					for (i = 0; i < frm.tt.length; i++) {
						frm.tt[i].checked = false;
					}
				} else {
					frm.tt.checked = false;
				}
				allcheck = 0;
			}
		}
	}

</script>
</head>
<body style="margin-left: 15px;">
	<form name="fSend" method="post">
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
											onkeydown="javascript:if (event.keyCode == 13) { reloding(); }">
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
											onclick="reloding();"
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
										<td style="width: 76px;"><img
											src="../../../images/admin/receiver/btn_allselect.gif"
											onclick="allselect();" style="cursor: pointer;" /></td>
										<td><img src="../../../images/admin/receiver/btn_del.gif"
											onclick="PressgroupDataDel();" style="cursor: pointer;" /></td>
										<td style="width: 88px;" align="right"><img
											src="../../../images/admin/pressgroup/btn_file_plus.gif"
											onclick="PressgroupDataAdd();" style="cursor: pointer;" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- 게시판 시작 -->
						<tr>
							<td>
								<table id="board_01" border="0" cellpadding="0" cellspacing="0"
									style="table-layout: fixed;">
									<col width="5%">
									<col width="15%">
									<col width="*">
									<col width="15%">
									<col width="15%">
									<tr>
										<th><input type="checkbox" id="tt" value="checkbox"
											onclick="allselect();"></th>
										<th>담당부서</th>
										<th>제목</th>
										<th>배포일자</th>
										<th>수정일자</th>
									</tr>
									<%
										for (int i = 0; i < arList.size(); i++) {
											pBean = (PressgroupDataBean) arList.get(i);
									%>
									<tr>
										<td><input type="checkbox" name="pg_seq" id="tt"
											value=<%=pBean.getPg_seq()%>></td>
										<td><%=pBean.getPg_dept()%> </td>	
										<td><p
												style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;"
												title="<%=pBean.getPg_title()%>">
												<a
													href="javascript:PressgroupDataDetal('<%=pBean.getPg_seq()%>');"><%=pBean.getPg_title()%></a>
											</p></td>
										<td><%=du.getDate(pBean.getPg_date(), "yyyy-MM-dd")%></td>
										<td><%=du.getDate(pBean.getPg_regdate(), "yyyy-MM-dd")%></td>
									</tr>
									<%
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
													<td><img
														src="../../../images/admin/receiver/btn_allselect.gif"
														onclick="allselect();" style="cursor: pointer;" /></td>
													<td><img
														src="../../../images/admin/receiver/btn_del.gif"
														onclick="PressgroupDataDel();" style="cursor: pointer;" /></td>
												</tr>
											</table>
										</td>
										<td align="right"><img
											src="../../../images/admin/pressgroup/btn_file_plus.gif"
											onclick="PressgroupDataAdd();" style="cursor: pointer;" /></td>
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