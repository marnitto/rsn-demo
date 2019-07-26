<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu
                 "
%>
    
<%
	ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sInit = (String)session.getAttribute("INIT");

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode().equals("") ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../css/left.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--


	function subChangeAction(contents, value, obj){
		var menu = document.getElementsByName('left_menu');
		if(menu){
	    	if(menu.length){
	        	for(var i = 0; i < menu.length; i++){
	        		menu[i].className = 'menu';
	        	}
	    	}
		}
		obj.className = 'menu_select';
		
		var f = document.getElementById('fSend');
		f.target= 'contentsFrame';
		if(contents == 'issue'){
			f.action= 'issue_report_list.jsp?ir_type='+value;    		
			f.submit();
		}else{
			f.action= 'issue_report_creater.jsp';    		
    		f.submit();
		}
	}

//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<table style="width:215px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" style="text-align:center;">
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td style="background:#FFFFFF;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/common/tit_03.gif" /></td>
					</tr>
					<tr>
						<td id="left_menu" class="menu_select" onclick="subChangeAction('issue', 'E', this);"><a href="#" onfocus="this.blur()">긴급보고서</a></td>
					</tr>
					<tr>
						<td id="left_menu" class="menu" onclick="subChangeAction('issue', 'D', this);"><a href="#" onfocus="this.blur()">일일보고서</a></td>
					</tr>
					<tr>
						<td id="left_menu" class="menu" onclick="subChangeAction('issue', 'W', this);"><a href="#" onfocus="this.blur()">주간보고서</a></td>
					</tr>
					<%--
					<tr>
						<td id="left_menu" class="menu" onclick="subChangeAction('issue', 'I', this);"><a href="#" onfocus="this.blur()">이슈보고서</a></td>
					</tr>
					--%>
					<tr>
						<td id="left_menu" class="menu" onclick="subChangeAction('report', '', this);"><a href="#" onfocus="this.blur()">보고서작성</a></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="center">
					<img src="../../images/common/btn_manual.gif" style="display: none;">
					<a href="../search/search_env_setting.jsp" target="contentsFrame"><img src="../../images/common/btn_setting.gif"></a>
					<a href="../logout.jsp" target="_top"><img src="../../images/common/btn_logout.gif"></a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="height:60px;text-align:center;"><img src="../../images/common/copyright.gif" /></td>
	</tr>
</table>


</body>
</html>
