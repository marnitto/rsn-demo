<%@ page contentType="text/html; charset=EUC-KR" %>
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

    //����� �⺻ ȯ���� ��ȸ�Ѵ�.
    //������  NCS�⺻ ������ ȯ���� ��ȸ�Ͽ� �����´�.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sInit = (String)session.getAttribute("INIT");

    //Top�޴����� �ΰ� �����˻� �޴� Ŭ���ÿ��� �˻������� �ʱ�ȭ ��Ų��.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode().equals("") ) {
        uei.setSearchMode("ALLKEY");
    }

    //���ݱ��� ���� ������ ���ǿ� ����
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/left_design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<body>
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<div id="wrap">
	<div id="snb">
		<h2><img src="../../images/left/left_title04.gif" alt="������"></h2>
		<div class="snb">
			<ul>
			<li>
				<a href="javascript:changeAction('statistics');" class="active"><span class="icon">-</span>������</a>
			</li>
			</ul>
		</div>
		<script type="text/javascript">
		(function($) {
			
			// ����Ʈ �޴� Ŭ�� - Ȱ��ȭ
			$( "#snb li a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				$( this ).addClass( "active" );
			});
		})(jQuery);
		
		function changeAction(value)
	    {
	    	var f = document.fSend;
	    	f.target= 'contentsFrame';
		    f.action= 'statistics_main.jsp';
		    f.submit();
		}
		
		</script>
	</div>
</div>
</body>
</html>
<!-- <script>
if (selectedObj==""){
	selectedObj=document.all.issueData;
	selectedObj.background='../../images/left/top_left_onbg.gif';
}
</script>
 -->


