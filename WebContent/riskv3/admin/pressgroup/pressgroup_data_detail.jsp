<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.admin.pressgroup.PressgroupDataBean
                 	,risk.admin.pressgroup.PressgroupDataMgr
					,risk.util.ParseRequest
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	PressgroupDataMgr pMgr = new PressgroupDataMgr();
	PressgroupDataBean pBean = null;
	DateUtil du = new DateUtil();
	pr.printParams();
	
	String mode = pr.getString("mode");
	String target_seq = pr.getString("target_seq","");
	
	
	String title = "";
	String content = "";
	String date = du.getDate("yyyy-MM-dd");
	String regdate = "";
	String p_dept = ""; 
	
	if(mode.equals("update")){
		
		pBean = pMgr.getPressgroupDataDetail(target_seq);
		
		title = pBean.getPg_title();
		content = pBean.getPg_content();
		date = pBean.getPg_date();
		regdate = pBean.getPg_regdate();
		p_dept = pBean.getPg_dept();
	}
	
%>

<%@page import="risk.util.DateUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script language="javascript">
<!--

	function save(){
		var f = document.fSend;
		f.action = 'pressgroup_data_prc.jsp';
		f.target = 'processFrm';
		f.submit();
	}

//-->
</script>
</head>
<body style="margin-left: 15px;">
<form name="fSend" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="target_seq" value="<%=target_seq%>">

<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/receiver/tit_icon.gif" /><img src="../../../images/admin/pressgroup/tit_002222.gif" /></td>
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
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table id="board_write" border="0" cellspacing="0">
					<tr>
						<th><span class="board_write_tit">제목</span></th>
						<td><input style="width:100%;" type="text" class="textbox2" name="title" value="<%=title%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">배포일</span></th>
						<td>
							<input style="width:90px;text-align:center; vertical-align: middle;" class="textbox" type="text" id="date" name="date" value="<%=du.getDate(date,"yyyy-MM-dd")%>">
							<img src="../../../images/admin/pressgroup/btn_calendar01.png" style="cursor:pointer; vertical-align: middle;" onclick="calendar_sh(document.getElementById('date'))"/>
						</td>
					</tr>
					<tr>
						<th><span class="board_write_tit">담당부서</span></th>
						<td><input style="width:20%;" type="text" class="textbox2" name="p_dept" value="<%=p_dept%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">내용</span></th>
						<td><textarea name="content" class="textbox2" style="width: 100%; height: 500px;"><%=content%></textarea></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
					<img src="../../../images/admin/receiver/btn_save2.gif" onclick="save();" style="cursor:pointer;"/>
					<img src="../../../images/admin/receiver/btn_cancel.gif" onclick="cancel();" style="cursor:pointer;"/>
				</td>
			</tr>
		</table>
		</td>

	</tr>
</table>

</form>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
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
		<td><img src="../../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
</body>
</html>
