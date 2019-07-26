<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%
 	Cookie[] cookies = request.getCookies(); //쿠키를 가져옴
	String Saveid = "";  				 // 쿠키에서 가져온 id저장
	String script = "";  				 // javascript 저장
	if(cookies != null){ 

		for(int i = 0;i<cookies.length;i++){
			System.out.println("cookies[i].getName():"+cookies[i].getName());
			if(cookies[i].getName().equals("lifeRSNid")){
				Saveid = cookies[i].getValue();				
			}			
		}			
	}else{
		System.out.println("not cookies!!");
	}
%>

<html>
<head>
<title>Social Media Monitoring PARTNER</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style type="text/css"> 
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url();
	background-color: #75929a;
}
</style>

<script type="text/JavaScript">
	function id_check() {
		if( !asLogin.FimUserID.value )
		{
			alert('ID를  입력하십시오.');
			asLogin.FimUserID.focus();
		}else if( !asLogin.FimUserPasswd.value ){
	
			alert('비밀번호를 입력하십시오.');
			asLogin.FimUserPasswd.focus();
		}else{		
			asLogin.submit();
			
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="asLogin" action="login.jsp" method="POST">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="456" valign="top" background="images/login/login_visual_bg.gif"><table width="456"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="456" height="203" background="images/login/login_visual01.jpg" ></td>
      </tr>
      <tr>
        <td width="456" height="174" background="images/login/login_visual02.jpg" ></td>
      </tr>
      <tr>
        <td width="456" height="127" background="images/login/login_visual03.jpg" ></td>
      </tr>
      <tr>
        <td width="456" height="233" background="images/login/login_visual04.jpg" ></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td width="438" valign="top" background="images/login/login_centerbg01.gif"><table width="438"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="203" colspan="2" valign="bottom"><img src="images/login/login_visual08.jpg" width="116" height="105"></td>
      </tr>
      <tr>
        <td colspan="2" width="438" height="174" background="images/login/login_visual07.jpg"></td>
      </tr>
      <tr>
        <td width="56" height="127" background="images/login/login_visual06.jpg" ></td>
        <td width="382" valign="bottom"><table width="366" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="65"><img src="images/login/login_id.gif" width="65" height="12" ></td>
            <td width="178"><input name="FimUserID" type="text" class="input01" style="width:150px;" <%if (!Saveid.equals("")) out.print("value="+Saveid+"");%>></td>
            <td width="123" rowspan="2"><img src="images/login/login_btn.gif" width="89" height="45" onclick="id_check();" style="cursor:hand;"></td>
          </tr>
          <tr>
            <td><img src="images/login/login_pass.gif" width="65" height="12"></td>
            <td><input name="FimUserPasswd" type="password" class="input01" style="width:150px;" OnKeyDown="Javascript:if (event.keyCode == 13) { id_check();}"></td>
          </tr>
          <tr>
            <td height="40">&nbsp;</td>
            <td colspan="2"><table width="224" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20" class="t_gray"><input type="checkbox" name="SaveId" value="checkbox" <%if (Saveid != "")out.print("checked");%>></td>
                  <td width="169" class="t">Remember my ID on this computer.</td>
                </tr>
                
            </table></td>
          </tr>
          <tr>
              <td class="t" colspan="3">시스템 문의는 <a href="mailto:solution@realsn.com">solution@realsn.com</a>으로 보내주시기 바랍니다.</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td height="144" background="images/login/login_visual09.jpg"></td>
      </tr>
    </table>
      </td>
    <td background="images/login/login_visual_bg.gif">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>
<% 
if (Saveid.equals(""))  {
	script += "<SCRIPT LANGUAGE=\"JavaScript\">";
	script +=  "asLogin.FimUserID.focus();" ;
	script += "</SCRIPT>";
	out.print(script);
}else{
	script += "<SCRIPT LANGUAGE=\"JavaScript\">";
	script +=  "asLogin.FimUserPasswd.focus();" ;
	script += "</SCRIPT>";
	out.print(script);
} 
	
%>