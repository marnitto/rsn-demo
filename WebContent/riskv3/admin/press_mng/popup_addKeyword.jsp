<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page import="java.util.*
                 ,risk.admin.pressMng.pressMng
				 ,risk.util.*"%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

String searchKey = pr.getString("searchKey", "");
String sDate = pr.getString("sDate", "");
String eDate = pr.getString("eDate", "");
String d_seq = pr.getString("d_seq", "");

%>


<html>
<head>
<title>주요키워드</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style>
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
</style>
</head>
<script src="<%=SS_URL%>js/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
function updateKeyword(){
	
	var d_seq = $("[name=d_seq]").val();
	var keywords = $("[name=keywords]").val();
	
	
	$.ajax({
		type:'POST',
		url:"pressMng_data_prc.jsp",
		dataType:'text',
		data:{d_seq:d_seq, keywords:keywords},
		success:function(data){
			alert("저장되었습니다.");
			windows.close();
		}
		,error:function(){
			alert("저장에 실패 했습니다. 다시 시도하시기 바랍니다. 문제가 계속 될 경우 시스템 관리자에게 문의 하시기 바랍니다.");
		}
	
	});
}
</script>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="d_seq" value="<%=d_seq%>">
<input type="hidden" name="sDate" value="<%=sDate%>">
<input type="hidden" name="eDate" value="<%=eDate%>">
<input type="hidden" name="searchKey" value="<%=searchKey%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;주요키워드 편집</font>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="120" align="center" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>주요키워드 :</strong></td>
		<td width="500" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="keywords" value="" size="100px" ></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><br><strong style="padding-top: 5px;">(예) 대구시  대구시장 대수시청  (단어와 단어 사이를 공백으로 구분하여 작성.)</strong></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updateKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>