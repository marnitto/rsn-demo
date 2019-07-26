<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%
 	Cookie[] cookies = request.getCookies(); //쿠키를 가져옴
	String Saveid = "";  				 // 쿠키에서 가져온 id저장
	String script = "";  				 // javascript 저장
	if(cookies != null){ 

		for(int i = 0;i<cookies.length;i++){
			System.out.println("cookies[i].getName():"+cookies[i].getName());
			System.out.println("cookies[i].getValue():"+cookies[i].getValue());
			if(cookies[i].getName().equals("lifeRSNid")){
				Saveid = URLDecoder.decode (cookies[i].getValue() , "utf-8" );
			}			
		}			
	}else{
		System.out.println("not cookies!!");
	}
%>

<!-- Include PAGE TOP -->
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<!-- // Include PAGE TOP -->

<body style="background:#ffffff">
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
	<!-- Include Message Box -->
	<jsp:include page="../inc/inc_message_box.jsp" flush="false" />
	<!-- // Include Message Box -->
	<form name="asLogin" action="loginDao.jsp" method="POST">
		<div id="wrap" class="login">
			<div class="content_wrap">
				<div class="c_top"></div>
				<div class="c_mid">
					<div class="rows"><span class="title"><img src="../asset/img/login/login_img_01.gif" alt="USER ID"></span>
						<input name="FimUserID" id="input_id" type="text" class="ui_input_login" placeholder="ID" <%if (!Saveid.equals("")) out.print("value="+Saveid+"");%>>
						<label for="input_id" class="invisible">아이디 입력</label>
					</div>
					<div class="rows"><span class="title"><img src="../asset/img/login/login_img_02.gif" alt="PASSWORD"></span>
						<input name="FimUserPasswd" id="input_pass" type="password" class="ui_input_login" placeholder="Password" OnKeyDown="Javascript:if (event.keyCode == 13) { id_check();}">
						<label for="input_pass" class="invisible">비밀번호 입력</label>
					</div>
					<div class="rows shared">
						<input id="shared_id" name="SaveId" type="checkbox" value="checkbox" <%if (Saveid != "")out.print("checked");%>><label for="shared_id">Remember my ID on this computer.</label>
					</div>
					<button type="button" class="ui_btn_00 ui_login ui_shadow_00" onclick="id_check();">Login</button>
				</div>
				<div class="c_bot"></div>
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








