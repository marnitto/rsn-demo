<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.aekeyword.ExceptionKeywordBean,
                 risk.admin.aekeyword.ExceptionKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean eKeywordBean = null;
	pr.printParams();
	
	String ekSeq= pr.getString("ekSeq");	
	String mode = pr.getString("mode");
	

%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style>
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

-->
	</style>
<script language="javascript">
<!--
	function insertKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.ekValue.value=='')
		{	
			alert('Ű���� �� �Է����ּ���.'); return;
		}else{
			//�յ� ���� ����
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='aekeyword_prc.jsp';
		f.submit();
	}

	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.ekValue.value=='')
		{	
			alert('Ű���� �� �Է����ּ���.'); return;
		}else{
			//�յ� ���� ����
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='aekeyword_prc.jsp';
		f.submit();
	}
//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ekSeq" value="<%=ekSeq%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;����Ű������</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>����Ű���� :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ekValue" value=""></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="insertKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>