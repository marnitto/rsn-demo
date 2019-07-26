<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 risk.admin.site.*
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

	String mode = pr.getString("mode", "");
	String abSeq = null;
	
	abSeq = pr.getString("abSeq");    
    String sg_name = pr.getString("sg_name",""); 
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=SS_URL%>css/base.css" type="text/css">

<script language="JavaScript" type="text/JavaScript">
<!--
	function init(){
		var f = document.address_group;
		f.ag_name.focus();	
	}
	window.onload = init;
	
	
	function add_ag(){
		var f = document.address_group;
		if( !f.ag_name.value ){
			alert('그룹명을 입력하십시요.');
		} else {
			f.mode.value='ins';
			f.target='';
			f.submit();
			
			parent.location.href.reload();
			window.close();
		}
	}

//-->
</script>
</head>
<body>
<form name="address_group" action="adressbook_group_prc.jsp" method="POST" onsubmit="return false;">
<input type="hidden" name="abSeq" value="<%=abSeq %>">
<input type="hidden" name="mode">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;정보 수신자 그룹 추가</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>그룹명 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ag_name" OnKeyDown="Javascript:if (event.keyCode == 13) { add_ag();}"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="add_ag();" style="cursor:hand;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:hand;"></td>
	</tr>
</table>
</form>
</body>
</html>