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

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>서울시청</title>
<link rel="shortcut icon" href="http://fss.realsn.com/dashboard/asset/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="css/design.css">
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="js/design.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
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
<body>
<form name="asLogin" action="login.jsp" method="POST">
<div id="wrap" class="login">
	<div class="content_wrap">
		<div class="c_top"></div>
		<div class="c_mid">
			<div class="rows"><span class="title"><img src="img/login_img_01.gif" alt="USER ID"></span>
				<input name="FimUserID" id="input_id" type="text" class="ui_input_01" <%if (!Saveid.equals("")) out.print("value="+Saveid+"");%>>
				<label for="input_id" class="invisible">아이디 입력</label>
			</div>
			<div class="rows"><span class="title"><img src="img/login_img_02.gif" alt="PASSWORD"></span>
				<input name="FimUserPasswd" id="input_pass" type="password" class="ui_input_01" OnKeyDown="Javascript:if (event.keyCode == 13) { id_check();}">
				<label for="input_pass" class="invisible">비밀번호 입력</label>
			</div>
			<div class="rows shared">
				<input id="shared_id" name="SaveId" type="checkbox" value="checkbox" <%if (Saveid != "")out.print("checked");%>><label for="shared_id"><img src="img/login_img_03.gif" alt="Remember my ID on this computer."></label>
			</div>
			<button type="button" class="ui_login ui_shadow_00" onclick="id_check();" >로그인</button>
		</div>
		<div class="c_bot">
			<span class="icon">-</span>시스템 문의는 <a href="mailto:solution@realsn.com">solution@realsn.com</a> 으로  보내주시기 바랍니다.
		</div>
	</div>
</div>
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