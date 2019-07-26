<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.info.*,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	pr.printParams();
	
	String i_seq= pr.getString("i_seq");	
	String mode = pr.getString("mode");
		
	if(mode.equals("insert"))
	{
		
	}else if (mode.equals("update")){
		igBean = igMgr.getInfoGroupBean(i_seq);
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="javascript">
	function insertKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.i_nm.value=='')
		{	
			alert('정보그룹을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.i_nm.value = f.i_nm.value.replace(/^\s*/,'');
			f.i_nm.value = f.i_nm.value.replace(/\s*$/,'');	
		}
					
		f.target='';
		f.action='infogroup_prc.jsp';
		f.submit();
	}

	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.i_nm.value=='')
		{	
			alert('정보그룹을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.i_nm.value = f.i_nm.value.replace(/^\s*/,'');
			f.i_nm.value = f.i_nm.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='infogroup_prc.jsp';
		f.submit();
	}
</script>
<title>Insert title here</title>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="i_seq" value="<%=i_seq%>">

<%
	if(mode.equals("insert")){
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;정보그룹 등록</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>정보그룹 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<input type="text" name="i_nm" value=""><input type="text" style="display:none">
				</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>사용여부 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<select name="i_useyn" id="i_useyn">
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
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

<%
	}else if(mode.equals("update")){
%>

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;정보그룹 등록</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>정보그룹 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<input type="text" name="i_nm" value="<%=igBean.getI_nm()%>"><input type="text" style="display:none">
				</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>사용여부 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<select name="i_useyn" id="i_useyn">
						<option value="Y" <%if(igBean.getI_useyn().equals("Y")){out.print("selected");}%>>Y</option>
						<option value="N" <%if(igBean.getI_useyn().equals("N")){out.print("selected");}%>>N</option>
					</select>
				</td>
			</tr>
		</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updateKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>

<%
	}
%>
</form>
</body>
</html>