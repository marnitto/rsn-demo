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
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/left_design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<!-- <script type="text/JavaScript">
	var selectedObj = "";

    function changeAction(value, obj)
    {
    	selectedObj = obj;
    	
    	var f = document.fSend;
    	f.target= 'contentsFrame';
    	
    	if(value=='issueData'){
        	obj.background = '../../images/left/top_left_onbg.gif';
        	document.getElementById('issueDataText').style.color = 'ffffff';
        	document.getElementById('issueDataOld').background = '../../images/left/top_left_offbg.gif';
        	document.getElementById('issueDataTextOld').style.color = '000000';
        	f.action= 'issue_data_list.jsp';
    		f.submit();
        }else if(value == 'issueDataOld'){
        	obj.background = '../../images/left/top_left_onbg.gif';
        	document.getElementById('issueDataTextOld').style.color = 'ffffff';
        	document.getElementById('issueData').background = '../../images/left/top_left_offbg.gif';
        	document.getElementById('issueDataText').style.color = '000000';
        	f.action= 'issue_data_list_old.jsp';    		
    		f.submit();
        }
	}
	
	

	function onMs(obj){
		obj.background='../../images/left/top_left_onbg.gif';
	}
	function outMs(obj){
		obj.background='../../images/left/top_left_offbg.gif';
		selectedObj.background='../../images/left/top_left_onbg.gif';
	}
	

</script> -->
</head>
<!-- <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form> -->
<!-- <table width="300" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td align="right" valign="top" background="../../images/left/top_left_mbg.gif">
    <td align="right" valign="top">
      <table width="295" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="../../images/left/left_title02.gif"></td>
	      </tr>
	      <tr>
	        <td bgcolor="#FFFFFF"><img src="../../images/left/brank.gif" width="1" height="4"></td>
	      </tr>
      </table>	  
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" style="padding: 0px 5px 0px 0px;" id="issueData" background="../../images/left/top_left_onbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_on" style="padding: 2px 0px 0px 3px;"><strong><a  id="issueDataText" style="color:ffffff;" href="javascript:changeAction('issueData', document.all.issueData);">관련정보</a></strong></td>
            </tr>
          </table></td>
        </tr>
      </table>
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" style="padding: 0px 5px 0px 0px;" id="issueDataOld" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_on" style="padding: 2px 0px 0px 3px;"><strong><a  id="issueDataTextOld" style="color:000000;" href="javascript:changeAction('issueDataOld', document.all.issueDataOld);">(구)관련정보</a></strong></td>
            </tr>
          </table></td>
        </tr>
      </table>
      
      <table width="295" border="0" cellpadding="0" cellspacing="0">
        <tr>
        	<td height="1" bgcolor="B7B7B7"></td>
        </tr>
      </table>
      </td>
  </tr>
</table> -->

<body>
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<div id="wrap">
	<div id="snb">
		<h2><img src="../../images/left/left_title02.gif" alt="이슈관리"></h2>
		<div class="snb">
			<ul>
			<li>
				<a href="javascript:changeAction('issueData', document.all.issueData);" class="active"><span class="icon">-</span>관련정보</a>
			</li>
			<!-- <li>
				<a href="javascript:changeAction('replyMngr', document.all.replyMngr);"><span class="icon">-</span>댓글분석</a>
			</li> -->
			</ul>
		</div>
		<script type="text/javascript">
		(function($) {
			
			// 레프트 메뉴 클릭 - 활성화
			$( "#snb li a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				$( this ).addClass( "active" );
			});
		})(jQuery);
		
		function changeAction(value, obj)
	    {
	    	var f = document.fSend;
	    	f.target= 'contentsFrame';
	    	
	    	if(value == 'issueData'){
		        f.action= 'issue_data_list.jsp';
		    	f.submit();
	    	}else{
	    		f.action= 'issue_reply_mngr.jsp';
		    	f.submit();
	    	}
	       
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


