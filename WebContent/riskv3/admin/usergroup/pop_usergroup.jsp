<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*,
				 risk.admin.membergroup.*
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

    String mode          = pr.getString("mode");
    String mgseq          = pr.getString("mgseq");
	String title = "";
	String mgval = "";

	membergroupMng MGmng = membergroupMng.getInstance();

	if( mode.equals("ins") ) {
		title = "사용자그룹 추가";
	} else if( mode.equals("edit") ) {
		title = "사용자그룹 수정";
		mgval = MGmng.getMG(mgseq);
	}
%>
<html>
<head>
<title>RISK V3 - RSN</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">

<script language="JavaScript" type="text/JavaScript">
<!--
	function init()
	{
		membergroup.val.focus();		
	}
	function add_kg()
	{
		if( !membergroup.val.value )
		{
			alert('사용자그룹명을 입력하십시요.');
		} else {
			membergroup.mode.value = 'ins';
			membergroup.submit();
			window.close();
		}
	}

	function edit_kg()
	{
		if( !membergroup.val.value )
		{
			alert('사용자그룹명을 입력하십시요.');
		} else {
			membergroup.mode.value = 'edit';
			membergroup.submit();
			window.close();
		}
	}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init();">
<form name="membergroup" action="ifram_usergroup_left_prc.jsp" method="get" target="usergroup_left">
<input type="hidden" name="mode">
<input type="hidden" name="mgseq" value="<%=mgseq%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;사용자그룹추가</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>사용자그룹명 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="val" value="<%=mgval%>" OnKeyDown="Javascript:if (event.keyCode == 13) { add_kg();}"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
<%
	if( mode.equals("ins") ) {
%>
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="add_kg();" style="cursor:hand;">
<%
	} else if( mode.equals("edit") ) {
%>
		<img src="../../../images/admin/member_group/btn_modify.gif" hspace="5" onclick="edit_kg();" style="cursor:hand;">
<%
	}
%>
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:hand;"></td>
	</tr>
</table>
</form>
</body>
</html>
