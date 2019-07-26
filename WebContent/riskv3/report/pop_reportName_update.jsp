<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 java.util.List,
				 risk.admin.keyword.KeywordMng
                 "
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);
	String ir_title = pr.getString("ir_title");
	String ir_seq = pr.getString("ir_seq");
	String ir_type = pr.getString("ir_type");
    

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
	.textwbig { font-family: "", "u"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal}
	-->
	</style>

<script language="JavaScript" type="text/JavaScript">
<!--
	function update_name(ir_seq){
		var f = document.fSend;
		f.ir_seq.value = ir_seq;
		f.target = '';
		f.action = 'pop_reportName_update_prc.jsp';
		f.submit();
	}
//-->
</script>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init();">
<form name="fSend" action="" method="post">
<input type="hidden" name="ir_seq" value="">
<input type="hidden" name="ir_type" value="<%=ir_type%>">
<input type="hidden" name="past_ir_title" value="<%=ir_title%>">
<table width="400" height="40" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13" background="<%=SS_URL%>images/report/pop_topbg.gif"><img src="<%=SS_URL%>images/report/pop_topimg01.gif" width="13" height="40"></td>
    <td width="374" background="<%=SS_URL%>images/report/pop_topbg.gif" class="textwbig"><img src="<%=SS_URL%>images/report/pop_ico01.gif" width="3" height="11"><strong>  제목수정</strong></td>
    <td width="13" align="right" background="<%=SS_URL%>images/report/pop_topbg.gif"><img src="<%=SS_URL%>images/report/pop_topimg02.gif" width="13" height="40"></td>
  </tr>
</table>
<table width="400" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#E8E8E8"><table width="100%" border="0" cellspacing="5" cellpadding="5">
      <tr>
        <td height="20" align="center" valign="top" bgcolor="#FFFFFF"><table width="378" border="0" cellspacing="0" cellpadding="0">
        <!-- 
          <tr>
            <td width="8"><img src="<%=SS_URL%>images/report/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td width="90" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>수정전 제목명</strong></td>
            <td width="281" align="left" style="padding: 3px 0px 0px 0px;">: <%=ir_title%></td> -->
          </tr>
        </table>
          <table width="378" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="<%=SS_URL%>images/report/brank.gif" width="1" height="5"></td>
            </tr>
            <tr>
              <td><img src="<%=SS_URL%>images/report/pop_dotline.gif" width="378" height="1"></td>
            </tr>
            <tr>
              <td height="50" bgcolor="#F6F6F6"><table width="370" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="8"><img src="<%=SS_URL%>images/report/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
                  <td width="90" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>제목 :</strong></td>
                  <td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ir_title" style="ime-mode:active" OnKeyDown="Javascript:if (event.keyCode == 13) { update_name(<%=ir_seq%>);}"></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td><img src="<%=SS_URL%>images/report/pop_dotline.gif" width="378" height="1"></td>
            </tr>
          </table>
          <table width="378" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="40" align="right">
              <%-- <img src="<%=SS_URL%>images/report/ico_002.gif" width="39" height="23" hspace="5" onclick="update_name('<%=ir_seq%>');" style="cursor:pointer;">
              <img src="<%=SS_URL%>images/report/form_cancel_btn.gif" width="39" height="23" onclick="window.close();" style="cursor:pointer;"> --%>
              <a href="javascript:update_name('<%=ir_seq%>');" style="display:inline-block;*display:inline;*zoom:1;height:23px;padding:0 15px;border:1px solid #C3C3C3;border-radius:3px;font-size:11px;font-weight:bold;line-height:23px">저장</a>
              <a href="javascript:window.close();" style="display:inline-block;*display:inline;*zoom:1;height:23px;padding:0 15px;margin:0 10px 0 5px;border:1px solid #C3C3C3;border-radius:3px;font-size:11px;font-weight:bold;line-height:23px">취소</a>
              </td>
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
