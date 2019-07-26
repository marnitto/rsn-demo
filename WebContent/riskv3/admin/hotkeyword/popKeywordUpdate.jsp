<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.*"%>
<%@ page import="risk.admin.hotkeyword.hotkeywordMgr" %>
<%@ page import="risk.admin.hotkeyword.hotkeywordBean" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest pr = new ParseRequest(request);

	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();

	String h_seqs = pr.getString("h_seqs", "");
	
	ArrayList dataList = new ArrayList();
	dataList = hm.getHotkeyword(h_seqs, "");
	hb = (hotkeywordBean)dataList.get(0);
%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
	function update_keyword(){
		var f = document.getElementById('fSend');
		f.mode.value = 'update';
		f.target = 'prc';
		f.action = 'hotkeywordPrc.jsp';
		f.submit();
	}
</script>
</head>
<body>
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="h_seqs" id="h_seqs" value="<%=h_seqs%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;HOT 키워드 추가</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>키워드명 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<input type="text" name="h_name" id="h_name" value="<%=hb.getH_name()%>" onkeyup="Javascript:if(event.keyCode == 13){add_kg();}"><input type="text" style="display:none">
				</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>사용여부 :</strong></td>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
					<select name="h_useyn" id="h_useyn">
						<option value="Y" <%if(hb.getH_useyn().equals("Y")){out.print("selected");}%>>Y</option>
						<option value="N" <%if(hb.getH_useyn().equals("N")){out.print("selected");}%>>N</option>
					</select>
				</td>
			</tr>
		</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="update_keyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
<iframe name="prc" id="prc" style="display:none"></iframe>
</form>
</body>
</html>