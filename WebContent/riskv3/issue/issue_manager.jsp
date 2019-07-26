<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueDataBean
					,risk.search.MetaMgr
					,risk.issue.IssueMgr
					,java.util.ArrayList
					,risk.util.ParseRequest
					,risk.issue.IssueCodeMgr
					,risk.issue.IssueCodeBean
					,risk.issue.IssueBean					
					,risk.search.userEnvMgr
                 	,risk.search.userEnvInfo
                 	,risk.util.DateUtil
                 	,risk.util.PageIndex
                 	,risk.util.StringUtil" %>
<%
	
	ParseRequest 	pr 		= new ParseRequest(request);
	IssueMgr 	issueMgr 	= new IssueMgr();
	MetaMgr    	smgr 	= new MetaMgr();
	DateUtil 		du 		= new DateUtil();
	StringUtil		su		= new StringUtil();		
	IssueBean iBean;
	pr.printParams();
	
	//캘린더 날짜 셋팅
	String sDateFrom = pr.getString("sDateFrom",du.addDay(du.getCurrentDate("yyyy-MM-dd"),-6,"yyyy-MM-dd"));
	String sDateTo = pr.getString("sDateTo",du.getCurrentDate("yyyy-MM-dd"));	

%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>

<script language="JavaScript" type="text/JavaScript">

	$(document).ready(loadIssue);

	
	function loadIssue()
	{
		ajax.post('issue_list.jsp','fSend','issueContent');
	}
	
	function search()
	{
		loadIssue();
	}

	function loadIssueTitle()
	{
		$('#it_seq').val('');		
		ajax.post('issue_title_list.jsp','fSend','issueTitleContent');
	}

	function clickIssue(i_seq)
	{
		//이슈번호 설정
		$('#i_seq').val(i_seq);	
		
		ajax.post('issue_title_list.jsp','fSend','issueTitleContent');
	}
	
	function insertIssue()
	{
		//이슈명 설정		
		if($('#i_title').val().length==0){alert('이슈를 입력 해주세요.');	return;}
					
		//모드 설정
		$('#mode').val('issue');

		//서브모드 설정
		$('#subMode').val('insert');
		
		ajax.update('issue_manager_prc.jsp','fSend');

		loadIssue();
	}

	function deleteIssue(i_seq)
	{			
		//모드 설정
		$('#mode').val('issue');

		//서브모드 설정
		$('#subMode').val('delete');

		//이슈번호
		$('#i_seq').val(i_seq);	
		
		ajax.update('issue_manager_prc.jsp','fSend');

		loadIssue();

		$('#issueTitleContent').html('');

	}

	function updateIssueFlag(i_seq)
	{			
		//모드 설정
		$('#mode').val('issue');

		//서브모드 설정
		$('#subMode').val('updateFlag');

		//이슈번호
		$('#i_seq').val(i_seq);	
		
		ajax.update('issue_manager_prc.jsp','fSend');

		loadIssue();

		$('#issueTitleContent').html('');

	}

	function deleteIssueTitle(it_seq)
	{			
		//모드 설정
		$('#mode').val('issueTitle');

		//서브모드 설정
		$('#subMode').val('delete');

		//타이틀번호
		$('#it_seq').val(it_seq);	
		
		ajax.update('issue_manager_prc.jsp','fSend');

		//타이틀번호 초기화
		$('#it_seq').val('');	
		
		loadIssueTitle();
	}

	function updateIssueTitleFlag(it_seq)
	{			
		//모드 설정
		$('#mode').val('issueTitle');

		//서브모드 설정
		$('#subMode').val('updateFlag');

		//타이틀번호
		$('#it_seq').val(it_seq);	
		
		ajax.update('issue_manager_prc.jsp','fSend');

		//타이틀번호 초기화
		$('#it_seq').val('');	
		
		loadIssueTitle();
	}
	
	function popIssueEditform(i_seq)
	{
		//이슈번호
		$('#i_seq').val(i_seq);	

		//팝업호출			
		popup.openByPost('fSend','pop_issue_editform.jsp',400,200,false,false,false,'send_issue');	
	}

	function popTitleEditform(mode,it_seq)
	{
		//모드		
		$('#mode').val(mode);
		
		//이슈번호
		$('#it_seq').val(it_seq);	

		//팝업호출			
		popup.openByPost('fSend','pop_issue_title_editform.jsp',400,200,false,false,false,'send_issue');	
		
	}

	function pageClick(pageNum,url,type)
	{
		//페이지 현재 번호	
		$('#nowpage').val(pageNum);

		if(type==1){
			loadIssue();
		}else{
			loadIssueTitle();
		}
	}
//popup.openByPost('send_mb','pop_issue_form.jsp',680,845,false,false,false,'send_issue');	

</script>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<form name="fSend" id="fSend" method="post" onsubmit="return false;">
<input name="nowpage" id="nowpage" type="hidden" value="1"></input>
<input name="mode" id="mode" type="hidden" value=""></input>
<input name="subMode" id="subMode" type="hidden" value=""></input>
<input name="i_seq" id="i_seq" type="hidden" value=""></input>
<input name="it_seq" id="it_seq" type="hidden" value=""></input>

<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="images/top_title_bg.gif"><img src="images/issue_toptitle001.gif" width="89" height="42"></td>
    <td align="right" background="images/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">등록된 이슈를 개별 열람하고 수정/삭제 할 수 있습니다.</td>
  </tr>
  <tr>
    <td colspan="2"><img src="images/brank.gif" width="1" height="5"></td>
  </tr>
</table>


<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="285" align="left" style="padding: 3px 0px 8px 0px;">&nbsp;</td>
	</tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="center" background="images/issue_search_bg01.gif">
    <table width="780" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td width="16" style="padding:0 0 0 8;"><img src="images/issue_ico01.gif" width="11" height="12"></td>
        <td style="padding: 3px 0px 0px 0px;"><strong>기간 : </strong></td>
        <td width="110" style="padding:0 0 0 3;"><input name="sDateFrom" type="text" size="10"  maxlength="10" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDateFrom%>"></td>
        <td width="15" align="center">~</td>
        <td width="110"><input name="sDateTo" type="text" size="10"  maxlength="10" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDateTo%>" size="11"></td>
        <td width="66" align="right"><img src="images/issue_ico01.gif" width="11" height="12"></td>
        <td style="padding: 3px 0px 0px 5px;"><strong>이슈명 : </strong></td>
        <td style="padding:0 0 0 3;"><input name="schKey" type="text" class="txtbox" size="40" maxlength="15" value="" OnKeyDown="Javascript:if (event.keyCode == 13) { search(); }"></td>
        <td width="110" align="center"><img src="images/btn_search.gif" width="61" height="22" onclick="search();"  style="cursor:hand;"></td>
      </tr> 
    </table>
    </td>
  </tr>
  <tr>
    <td align="right" id="simple" style="display:none;"><a href="javascript:openDetail();"><img src="images/search_down.gif" width="56" height="8"></a></td>
  </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="100" align="left" style="padding:0px 0px 5px 0px;"><img src="images/pop_ico01.gif" width="13" height="13" align="absmiddle"> <strong>이슈</strong></td>
		<td width="680" align="right" style="padding:0px 0px 5px 0px;"><strong>이슈추가</strong>&nbsp;
		<input type="text" class="txtbox" style="width:300px" name="i_title" id="i_title" value="" OnKeyDown="if(event.keyCode == 13){Javascript:insertIssue();}">
		&nbsp;<img src="images/issue_btn_02.gif" align="absmiddle" width="76" height="24" style="cursor:hand;" onclick="insertIssue();"></td>
	</tr>	
</table>
<div id="issueContent">
 
</div>
<br></br>
<div id="issueTitleContent">

</div>  
</form>
</body>
</html>
