<%@ page contentType="text/html; charset=UTF-8" %>
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
<!-- <html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script type="text/JavaScript">

	var selectedObj = "";

    function changeAction(value, obj)
    {
    	selectedObj = obj;
    	
    	var f = document.fSend;
    	f.target= 'contentsFrame';
    
        if(value=='report'){
        	chageColor(value);
        	f.action= 'issue_report_list.jsp?ir_type=E';    		
    		f.submit();
		} else if (value=='reportWrite'){
			chageColor(value);
        	f.action= 'issue_report_creater.jsp';    		
    		f.submit();
		} else if (value=='reportOld'){
			chageColor(value);
        	f.action= 'issue_report_list_old.jsp?ir_type=D';    		
    		f.submit();
		}
    }

	function subChangeAction(contents,value)
	{
		
		if(contents=='issue')
		{
			

			document.all.report.background = '../../images/left/top_left_onbg.gif';   
			document.all.reportText.style.color='#ffffff';	
			document.all.reportWrite.background = '../../images/left/top_left_offbg.gif';   
			document.all.reportWriteText.style.color='#000000';	
			//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';

			selectedObj = document.all.report;
						

			var f = document.fSend;
			f.target= 'contentsFrame';		
			f.action= 'issue_report_list.jsp?ir_type='+value;    		
			f.submit();
		}else if(contents=='statistics'){
			if(value=='A')
			{
				alert('변경 중 입니다.');
				/*
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_issue.jsp';  		
				f.submit();
				*/
			}else if(value=='B'){
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_analysis.jsp';    		
				f.submit();		
			}else if(value=='C'){							
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_collect_keyword.jsp';    		
				f.submit();				
			}else if(value=='D'){				
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_collect_site.jsp';    		
				f.submit();
			}
		}else if(contents=='issueOld'){

			document.all.report.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';
			document.all.reportWrite.background = '../../images/left/top_left_offbg.gif';
			document.all.reportWriteText.style.color='#000000';
			document.all.reportOld.background = '../../images/left/top_left_onbg.gif';
			document.all.reportText.style.color='#ffffff';
			
			selectedObj = document.all.report;
			

			var f = document.fSend;
			f.target= 'contentsFrame';		
			f.action= 'issue_report_list_old.jsp?ir_type='+value;    		
			f.submit();
		}
	}

	function chageColor( value ) {
		var f = document.fSend;

	    if( value == 'report' ){
			document.all.report.background = '../../images/left/top_left_onbg.gif';
			document.all.reportText.style.color='#ffffff';
			document.all.reportWrite.background = '../../images/left/top_left_offbg.gif';
			document.all.reportWriteText.style.color='#000000';
			//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';	
		} else if( value == 'reportWrite' ){
			document.all.reportWrite.background = '../../images/left/top_left_onbg.gif';
			document.all.reportWriteText.style.color='#ffffff';
			document.all.report.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';
			//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';	
		} else if( value == 'reportOld' ){
			document.all.report.background = '../../images/left/top_left_offbg.gif';
			document.all.reportText.style.color='#000000';
			document.all.reportWrite.background = '../../images/left/top_left_offbg.gif';
			document.all.reportWriteText.style.color='#000000';
			//document.all.reportOld.background = '../../images/left/top_left_onbg.gif';
			document.all.reportText.style.color='#ffffff';
		}
	}
		
	function saveEnv()
	{
		if( parent.contentsFrame.fCheck )
		{
			parent.contentsFrame.env_save();
		}
	}
		
	function goReportList(type)
	{
		var f = document.fSend;		
		chageColor('report');
		f.action = 'issue_report_list.jsp';
		f.target = 'contentsFrame';
		f.submit();
	
	}

	function onMs(obj){
		obj.background='../../images/left/top_left_onbg.gif';
	}
	function outMs(obj){
		obj.background='../../images/left/top_left_offbg.gif';
		selectedObj.background='../../images/left/top_left_onbg.gif';
	}
	
/*
	function igList_resize(){
  		var ch = document.body.clientHeight;
  		var ifmObj = document.all.ifr_menu.style;
		var newH = ch - 230;
  		if (newH <100) newH = 100;
  		ifmObj.height = newH;
  	}
*/
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<table width="300" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td align="right" valign="top" background="../../images/left/top_left_mbg.gif">
    <td align="right" valign="top">
      <table width="295" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="../../images/left/left_title03.gif" width="295" height="52"></td>
	      </tr>
	      <tr>
	        <td bgcolor="#FFFFFF"><img src="../../images/left/brank.gif" width="1" height="4"></td>
	      </tr>
      </table>	  
      
      <table width="295" border="0" cellpadding="0" cellspacing="0">
        <tr>
        	<td height="1" bgcolor="B7B7B7"></td>
        </tr>
      </table>
      
	  <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" id="report" class="leftM_on" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../images/left/left_mico03.gif" width="16" height="15"></td>
                <td class="leftM_off" style="padding: 2px 0px 0px 3px; font-weight: bold;"><a  id="reportText" style="color:000000;" href="javascript:changeAction('report', document.all.report);">보고서 관리</a></td>
              </tr>
          </table></td>
        </tr>
      </table>      		        
	  <table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="267" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','E');"><strong>긴급보고서</strong></a></td>
        </tr>
        <tr>
          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
		<tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="267" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','D1\',\'I');"><strong>일일보고서</strong></a></td>
        </tr>
        <tr>
          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
        
        <tr style="display: none;">
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="267" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','W');"><strong>주간보고서</strong></a></td>
        </tr>
        <tr style="display: none;">
          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr> 
        <tr style="display: none;">
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="267" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','D1');"><strong>온라인이슈 일일 보고서</strong></a></td>
        </tr>
        
        <tr>
          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
      </table>
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" id="reportWrite" class="leftM_on" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../images/left/left_mico03.gif" width="16" height="15"></td>
                <td class="leftM_off" style="padding: 2px 0px 0px 3px; font-weight: bold;"><a  id="reportWriteText" style="color:000000;" href="javascript:changeAction('reportWrite', document.all.reportWrite);">보고서 작성</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      </td>
  </tr>
</table>

</body>
</html>
<script>
if (selectedObj==""){
	selectedObj=document.all.report;
	selectedObj.background='../../images/left/top_left_onbg.gif';
}
</script> -->


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/left_design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<%-- <script type="text/javascript" src="<%=SS_URL%>js/jquery-ui.min.js"></script> --%>
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!--[if (gte IE 6)&(lte IE 8)]>
  <script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
</head>
<body>
<div id="wrap">
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
	<div id="snb">
		<h2><img src="<%=SS_URL%>images/left/left_title03.gif" alt="보고서관리"></h2>
		<div class="snb">
			<ul>
			<li>
				<a href="javascript:subChangeAction('issue','D1\',\'I');"><span class="icon">-</span>보고서 관리</a>
				<ul>
				<li><a href="javascript:subChangeAction('issue','D1\',\'I');" class="active"><span class="icon">-</span>주간보고서</a></li>
				<li class="last_child"><a href="javascript:subChangeAction('issue','E');"><span class="icon">-</span>이슈보고서</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript:changeAction('reportWrite', document.all.reportWrite);"><span class="icon">-</span>보고서 작성</a>
			</li>
			</ul>
		</div>
		<script type="text/javascript">
		(function($) {
			hndl_nav();
			function hndl_nav () {
				$( this ).parent().parent().parent().find( "> a" ).removeClass( "active" );
				$( "#snb .snb > ul > li > ul > li > a" ).each(function(){
					if( $( this ).hasClass( "active" ) ) {
						$( this ).parent().parent().parent().find( "> a" ).addClass( "active" );
					}
				});
			}
			$( "#snb > .snb > ul > li > a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				if( $( this ).parent().find( "ul" ).length > 0 ) {
					$( this ).parent().find( "ul > li > a" ).eq( 0 ).addClass( "active" );
					hndl_nav();
				} else {
					$( this ).addClass( "active" );
				}
			});
			$( "#snb > .snb > ul > li > ul > li > a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				$( this ).addClass( "active" );
				hndl_nav();
			});
		})(jQuery);
		
		function changeAction(value, obj)
	    {
	    	selectedObj = obj;
	    	
	    	var f = document.fSend;
	    	f.target= 'contentsFrame';
	    
	        if(value=='report'){
	        	
	        	f.action= 'issue_report_list.jsp?ir_type=E';    		
	    		f.submit();
			} else if (value=='reportWrite'){
				
	        	f.action= 'issue_report_creater.jsp';    		
	    		f.submit();
			} else if (value=='reportOld'){
				
	        	f.action= 'issue_report_list_old.jsp?ir_type=D';    		
	    		f.submit();
			}
	    }
		
		function subChangeAction(contents,value)
		{
			var f = document.fSend;
			f.target= 'contentsFrame';		
			f.action= 'issue_report_list.jsp?ir_type='+value;    		
			f.submit();
		}
		</script>
	</div>
</div>
</body>
</html>



