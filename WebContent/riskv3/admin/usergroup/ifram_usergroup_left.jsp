<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 java.util.List,
				 risk.admin.membergroup.*
                 "%>

<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	String fir_seq = null;
	List MGlist = null;
	membergroupMng MGmng = membergroupMng.getInstance();
	MGlist = MGmng.getMGList();
%>
<html>
<head>
<title>X-MAS Solution</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
var selobj;
function kg_over(obj) {
	if( selobj != obj ) {
		tmpspan.className=obj.className;
		obj.className='kgmenu_over';
	}
}

function kg_out(obj){
	if( selobj != obj ) {
		obj.className=tmpspan.className;
	}
}

function sel(seq, index){
	var t = document.getElementsByName('sel_bg');
	for(var i = 0; i < t.length; i++){
		t[i].className = 'pop_mail_group_td';
	}
	t[index].className = 'pop_mail_group_td_on';
	membergroup.mgseq.value = seq;
	parent.usergroup_right.location.href = 'ifram_usergroup_right.jsp?mgseq='+seq;
}

function del()
{
	if( !membergroup.mgseq.value ) {
		alert('사용자그룹을 선택하십시요.');
	} else {
		membergroup.submit();
	}
}

//-->
</SCRIPT>
</head>
<body>
<form name="membergroup" action="ifram_usergroup_left_prc.jsp" method="get">
<input type="hidden" name="mode" value="del">
<input type="hidden" name="mgseq">
</form>
<span id='tmpspan' style='display:none' class=''></span>
<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
<%
	for(int i=0; i < MGlist.size();i++) {
		membergroupBean MGinfo = (membergroupBean)MGlist.get(i);
		if( fir_seq == null ) fir_seq = MGinfo.getMGseq();
%>
			<tr>
				<td id="sel_bg" name="sel_bg" class="<%if(i == 0){out.print("pop_mail_group_td_on");}else{out.print("pop_mail_group_td");}%>" onclick="sel(<%=MGinfo.getMGseq()%>, <%=i%>);" style="cursor:pointer;"><%=MGinfo.getMGname()%></td>
			</tr>
<%
	}
%>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%
	if( fir_seq != null ) {
		out.println("sel("+fir_seq+", 0);");
	}
%>
//-->
</SCRIPT>