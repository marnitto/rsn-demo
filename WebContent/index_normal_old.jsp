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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../css/base.css" />
<script language="JavaScript" type="text/JavaScript">
<!--
	
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
//-->
</script>
</head>
<body topmargin="0" leftmargin="0">
<form name="asLogin" action="login.jsp" method="POST">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td id="login_visual"><p></p></td>
	</tr>
	<tr>
		<td style="padding-bottom:20px;">

		<table align="center" style="width:700px;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="login_box">

				<table align="center" border="0" cellpadding="0" cellspacing="6">
					<tr>
						<td><img src="../images/login/txt_id.gif" /></td>
						<td><input type="text" class="login_input_box" tabindex="1" name="FimUserID" style="width:150px;" <%if(!Saveid.equals("")) out.print("value="+Saveid+"");%>></td>
						<td rowspan="2"><input type="image" src="../images/login/btn_login.gif" tabindex="3" onclick="id_check();" style="cursor:pointer;"></td> 
					</tr>
					<tr>
						<td><img src="../images/login/txt_pw.gif" /></td>
						<td><input type="password" name="FimUserPasswd" class="login_input_box" tabindex="2" style="width:150px;" OnKeyDown="Javascript:if (event.keyCode == 13) { id_check();}"></td>
					</tr>
					<tr>
						<td></td>
						<td><label><input type="checkbox" tabindex="4" name="SaveId" <%if(Saveid != "")out.print("checked");%>> <img src="../images/login/idsave_txt.gif"/></label></td>
					</tr>
				</table>

				</td>
			</tr>
			<tr><td class="login_idpw_search">&nbsp;</td></tr>
		</table>

		</td>
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