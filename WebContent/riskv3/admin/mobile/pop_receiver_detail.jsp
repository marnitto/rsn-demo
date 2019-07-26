<%@ page contentType = "text/html; charset=euc-kr"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">	
<!--
	window.resizeTo(840,400);

	function save()
	{
		var check = false;
		if(document.receiver_detail.ab_name.value.length==0) {alert('이름을 입력하여 주십시오.'); return;}
		if(document.receiver_detail.ab_dept.value.length==0) {alert('부서를 입력하여 주십시오.'); return;}
		if(document.receiver_detail.ab_pos.value.length==0) {alert('직급을을 입력하여 주십시오.'); return;}
		if(document.receiver_detail.ab_mobile.value.length==0) {alert('핸드폰을 입력하여 주십시오.'); return;}
		if(document.receiver_detail.ab_email.value.length==0) {alert('을 입력하여 주십시오.'); return;}

		var f = document.receiver_detail;
		f.target ='';
		f.action ='pop_receiver_detail_prc.jsp';
		f.submit();
	}
		
	
//-->
</SCRIPT>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="">
<form name="receiver_detail" action="" method="post">
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="images/top_title_bg.gif"><img src="images/admin_toptitle02.gif" width="137" height="42"></td>
    <td align="right" background="images/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">SMS알리미, Online Report를 수신할 대상 및 수신조건을 관리합니다.</td>
  </tr>
  <tr>
    <td colspan="2"><img src="images/brank.gif" width="1" height="15"></td>
  </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;"><table width="750" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15"><img src="images/ico_won_blue.gif" width="11" height="11"></td>
              <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>기본정보</strong></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#9CBBE5"><img src="images/brank.gif" width="1" height="2"></td>
      </tr>
      <tr>
        <td width="210" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</strong></td>
        <td width="540"  style="padding: 2px 0px 0px 10px;"><input name="ab_name" type="text" class="txtbox02" value="" size="60"></td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#D3D3D3"><img src="images/brank.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td width="210" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</strong></td>
        <td width="540"  style="padding: 4px 0px 0px 10px;"><input name="ab_dept" type="text" class="txtbox02" value="" size="60"></td>
      </tr>
      <tr>
        <td colspan="2"  bgcolor="#D3D3D3"><img src="images/brank.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td width="210" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>직&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</strong></td>
        <td width="540"  style="padding: 4px 0px 0px 10px;"><input name="ab_pos" type="text" class="txtbox02" value="" size="60"></td>
      </tr>
      <tr>
        <td colspan="2"  bgcolor="#D3D3D3"><img src="images/brank.gif" width="1" height="1"></td>
      </tr>
	        <tr>
        <td width="210" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>핸드폰 번호</strong></td>
        <td width="540"  style="padding: 4px 0px 0px 10px;"><input name="ab_mobile" type="text" class="txtbox02" value="" size="60"></td>
      </tr>
      <tr>
        <td colspan="2"  bgcolor="#D3D3D3"><img src="images/brank.gif" width="1" height="1"></td>
      </tr>
	        <tr>
        <td width="210" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>Email 주소</strong></td>
        <td width="540"  style="padding: 4px 0px 0px 10px;"><input name="ab_email" type="text" class="txtbox02" value="" size="60"></td>
      </tr>
      <tr>
        <td colspan="2"  bgcolor="#D3D3D3"><img src="images/brank.gif" width="1" height="1"></td>
      </tr>  
      <tr align="center">
        <td height="50" colspan="2"><img src="../images2/btn_save.gif" width="55" height="24" hspace="5" onclick="save();" style="cursor:hand;"><img src="../images2/btn_cancel.gif" width="55" height="24" hspace="5" onclick="window.close();" style="cursor:hand;"></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>