<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.blacksite.BlackSiteBean,
                 risk.admin.blacksite.BlackSiteMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	BlackSiteMgr ekMgr = new BlackSiteMgr();
	BlackSiteBean eKeywordBean = null;
	
	
	String ekSeq= pr.getString("ekSeq");	
	String mode = pr.getString("mode");
		
	eKeywordBean = ekMgr.getKeyword(ekSeq);

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
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--
function updateKeyword()
{
	var f = document.editForm;
	f.mode.value = 'update';
	if(f.ekValue.value=='')
	{	
		alert('URL를 입력해주세요.'); return;
	}else{
		//앞뒤 공백 제거
		f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
		f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
	}	
	f.target='';
	f.action='blacksite_prc.jsp';
	f.submit();
}
	
//-->
</script>
</head>
<body>
<form id="editForm" name="editForm" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ekSeq" value="<%=ekSeq%>">

 
 <table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;블랙리스트 수정</font>
			<span><a href="javascript:close();"><img src="../../../images/admin/aekeyword/pop_tit_close.gif"></span>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
			  <td style="height:30px;"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E0E0E0">
                <tr>
                  <td bgcolor="#F6F6F6" style="padding:10px 10px 10px 10px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">
                      <span class="blue_text"><strong>URL :&nbsp; 
                      <input type="text" name="ekValue" value="<%=eKeywordBean.getBsValue()%>" size="50"></strong></span></td>
                    </tr>
                   
                  </table></td>
                </tr>
              </table></td>
		  </tr>
			<tr>
			  <td height="5" align="center">
			  	<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updateKeyword();" style="cursor:pointer;">
				<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
			  </td>
		  </tr>
			
			
		</table>
		<!-- 게시판 끝 -->		</td>
	</tr>
	
	<!---------------->
</table>
</form>
</body>
</html>