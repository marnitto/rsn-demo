<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String itype = pr.getString("itype");
	String icode = pr.getString("icode");
	
	String ic_seq = pr.getString("ic_seq");	
	String ic_name = pr.getString("ic_name");
	
	String edit_ic_regdate = pr.getString("edit_ic_regdate");
	String ic_regtime = pr.getString("ic_regtime");
%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<style type="text/css">
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
	iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
-->
	</style>
<script language="javascript">
<!--
	function modify()
	{
		var f = document.editForm;
		
		if(f.ic_name.value=='')
		{	
			alert('명칭 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ic_name.value = f.ic_name.value.replace(/^\s*/,'');
			f.ic_name.value = f.ic_name.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='classification_prc.jsp';
		f.submit();
	}

//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="itype" value="<%=itype%>">
<input type="hidden" name="icode" value="<%=icode%>">
<input type="hidden" name="ic_seq" value="<%=ic_seq%>">
<input type="hidden" name="mode" value="update">
<input type="hidden" name="ic_regtime" value="<%=ic_regtime%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
						<td><img
											src="../../../images/admin/receiver/tit_icon.gif" /><img
											src="../../../images/admin/pressgroup/tit_002222.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">보도자료 관리</td>
							</tr>
						</table>
						</td>
					</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="60" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>분류명 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" class="txtbox" size="40" name="ic_name" value="<%=ic_name%>"></td>
		</tr>
	</table></td>
	</tr>
	<%
	if(itype.equals("7")){
	%>
	<tr>
    <td style="padding-left:10px;padding-top:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="60" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>게시일 :</strong></td>
			<td width="140" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" id="ic_regdate" name="ic_regdate" value="<%=edit_ic_regdate%>" readonly></td>
			<td align="left"><img src="../../../images/admin/classification/btn_calendar.gif" id="addbtn" align="absmiddle" onclick="calendar_sh(document.getElementById('ic_regdate'))" style="cursor:pointer;"></td>
		</tr>
	</table></td>
	</tr>
	<%	
	}
	%>
	
</table>
	<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="modify();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>