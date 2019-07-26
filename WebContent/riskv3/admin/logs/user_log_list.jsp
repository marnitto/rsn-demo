<%@page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@page import = "risk.util.ParseRequest" %>
<%@page import = "java.util.*" %>
<%@page import = "java.lang.*" %>
<%@page import="risk.admin.log.LogMgr"%>
<%@page import="risk.util.PageIndex"%>
<%@page import="risk.admin.log.LogBean"%>
<%
	int PAGE_SIZE = 10;		//페이지당 리스트 수
	int totpage = 0;		//총 페이지수
	int nowpage = 1;		//현재 페이지번호
	int count = 0;			//총 게시물 수
	
	String sch_mode = null;	//검색조건 분류
	String sch_val = null;	//조건 값
	String logKinds = null; //로그 종류
	String logType = null; //로그 상세 종류
	
	ArrayList logKindsList = new ArrayList();
	ArrayList logTypeList = new ArrayList();
	ArrayList logList = new ArrayList();
	
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	nowpage = pr.getInt("nowpage",1);
	sch_mode = pr.getString("sch_mode","");
	sch_val = pr.getString("sch_val","");
	logKinds = pr.getString("logKinds","");
	logType = pr.getString("logType","");
	
	
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	logKindsList = logMgr.getLogKindsList();
	logTypeList = logMgr.getLogTypeList();
	logList = logMgr.getLogList(nowpage,10,logKinds,logType,sch_mode,sch_val);
	
	totpage = logMgr.getListCnt()/10;
	if(logMgr.getListCnt()%10!=0) totpage++;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="JavaScript" type="text/JavaScript">
<!--
	function init(){
		sch_log.sch_val.focus();
	}

    function chg_svc() {		
		sch_log.submit();
    }

    function pageClick( paramUrl ) {
    	var f = document.getElementById('sch_log');
        f.action = "user_log_list.jsp" + paramUrl;
        f.submit();
        /*
        alert(paramUrl);
		sch_log.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		sch_log.submit();
		*/
    }
	function seach() {
		sch_log.submit();
	}
//-->
</script>
</head>
<body>
<form id="sch_log" name="sch_log" action="user_log_list.jsp" method="post">
<input id="nowpage" name="nowpage" type="hidden" value="<%=nowpage%>">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/log/tit_icon.gif" /><img src="../../../images/admin/log/tit_0503.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">로그보기</td>
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
				<table id="search_box" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th style="height:30px;">검색 조건</th>
						<td style="vertical-align:middle">
							<select name="sch_mode" style="vertical-align:middle">
								<option value=""  <% if( sch_mode.equals("") ) out.print("selected");%>>전체</option>
								<option value="1" <% if( sch_mode.equals("1") ) out.print("selected");%>>아이디</option>
								<option value="2" <% if( sch_mode.equals("2") ) out.print("selected");%>>이름</option>
								<option value="3" <% if( sch_mode.equals("3") ) out.print("selected");%>>IP</option>
							</select> 
							<input style="width:460px;vertical-align:middle" class="textbox" type="text" name="sch_val" value="<%=sch_val%>"><img src="../../../images/admin/log/btn_search.gif" onclick="seach();" style="cursor:pointer;vertical-align:middle"/>
						</td>
					</tr>
					<tr>
						<th style="height:30px;">검색 조건</th>
						<td>
						<table style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<select name="logKinds" style="width:100px;">
									<option value="">전체</option>
<%
		for(int i=0;i<logKindsList.size();i++){	
			logBean = new LogBean();
			logBean = (LogBean)logKindsList.get(i);
%>
									<option value="<%=logBean.getL_kinds()%>" <% if( logBean.getL_kinds().equals(logKinds) ) out.print("selected");%>><%=logBean.getL_kindsName()%></option>
<%
		}
%>
								</select>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<th style="height:30px;">로그 상세</th>
						<td><select name="logType" style="width:100px;">
							<option value="">전체</option>
<%
		for(int i=0;i<logTypeList.size();i++){	
			logBean = new LogBean();
			logBean = (LogBean)logTypeList.get(i);
%>
							<option value="<%=logBean.getL_type()%>" <% if( logBean.getL_type().equals(logType) ) out.print("selected");%>><%=logBean.getL_typeName()%></option>
<%
		}
%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:45px;"><b><%=logMgr.getListCnt()%></b>건 , <b><%=nowpage%>/<%=(logMgr.getListCnt()/PAGE_SIZE)%></b>page</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="20%"><col width="20%"><col width="20%"><col width="20%"><col width="20%">
					<tr>    
						<th>로그 종류</th>
						<th>아이디</th>
						<th>이름</th>
						<th>IP</th>
						<th>날짜</th>
					</tr>
<%
	if(logList.size() > 0){
		for(int i = 0; i < logList.size(); i++){
			logBean = new LogBean();
			logBean = (LogBean)logList.get(i);
%>
					<tr>
						<td><%=logBean.getL_kindsName()+"/"+logBean.getL_typeName()%></td>
						<td><%=logBean.getM_id()%></td>
						<td><%=logBean.getM_name()%></td>
						<td><%=logBean.getL_ip()%></td>
						<td><%=logBean.getL_date()%></td>
					</tr>
<%
		}
	}else{
%>
					<tr>
						<td colspan="5" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
					</tr>
<%
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
						<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<table id="paging" border="0" cellpadding="0" cellspacing="2">
										<tr>
											<%=PageIndex.getPageIndex(nowpage, totpage,"","" )%>
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
</body>
</html>