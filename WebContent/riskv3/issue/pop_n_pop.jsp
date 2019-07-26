<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.admin.info.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%

	ParseRequest pr = new ParseRequest(request);
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	pr.printParams();
	
	String i_seq= pr.getString("i_seq");	
	String mode = pr.getString("mode");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<title>Insert title here</title>
<script language="javascript">

	function infoinsert(){
		var form = document.getElementById('infogroup_insert');
		form.mode.value = 'infoinsert';
		if(form.i_nm.value=='')
		{	
			alert('정보그룹을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			form.i_nm.value = form.i_nm.value.replace(/^\s*/,'');
			form.i_nm.value = form.i_nm.value.replace(/\s*$/,'');	
		}
					
		form.target='';
		form.action='../admin/infogroup/infogroup_prc.jsp';
		form.submit();
	}

</script>
</head>
<body>
<form name="infogroup_insert" id="infogroup_insert" method="post">
<input type="hidden" name="mode" id="mode" value="<%=mode%>">
<input type="hidden" name="i_seq" value="<%=i_seq%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;정보그룹 등록</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
<tr>
		<td style="padding-left:10px">
		<table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" class="menu_gray"><strong>정보그룹 :</strong></td>
				<td width="311" align="left">
					<input type="text" name="i_nm" id="i_nm">
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding-left:10px">
		<table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" class="menu_gray"><strong>사용여부 :</strong></td>
				<td width="311" align="left">
					<select name="i_useyn" id="i_useyn">
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif" onclick="infoinsert()" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>