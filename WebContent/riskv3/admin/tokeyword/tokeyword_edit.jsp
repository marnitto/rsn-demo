<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.tokeyword.ToKeywordBean,
                 risk.admin.tokeyword.ToKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	ToKeywordMgr ekMgr = new ToKeywordMgr();
	ToKeywordBean eKeywordBean = null;
	pr.printParams();
	
	String ekSeq= pr.getString("ekSeq");	
	String mode = pr.getString("mode");
		
	if(mode.equals("insert"))
	{
		
	}else if (mode.equals("update")){
		eKeywordBean = ekMgr.getKeyword(ekSeq);
	}

%>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="javascript">
<!--
	function insertKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.ekValue.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='tokeyword_prc.jsp';
		f.submit();
	}

	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.ekValue.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='tokeyword_prc.jsp';
		f.submit();
	}
//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ekSeq" value="<%=ekSeq%>">
<%
	if(mode.equals("insert")){
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>투데이키워드등록</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>투데이키워드 :</strong></td>
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
<%
	}else if(mode.equals("update")){
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>투데이키워드수정</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>투데이키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ekValue" value="<%=eKeywordBean.getEkValue()%>"></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>등록자 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" value="<%=eKeywordBean.getEkFwriter()%>" readonly="readonly"></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>입력일자 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" value="<%=eKeywordBean.getEkDate()%>" readonly="readonly"></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>수정자 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" value="<%=eKeywordBean.getEkLwriter()%>" readonly="readonly"></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>수정일자 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" value="<%=eKeywordBean.getEkUpdate()%>" readonly="readonly"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member_group/btn_modify.gif"  hspace="5" onclick="updateKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
<%
	}
%>
</form>
</body>
</html>