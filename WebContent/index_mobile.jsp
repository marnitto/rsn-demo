<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>서울시청</title>
<link rel="stylesheet" type="text/css" href="riskv3/mobile_web/asset/css/m_design.css">
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script type="text/javascript">
function setCookie (name, value, expires) {
	  document.cookie = name + "=" + escape (value) +
	    "; path=/; expires=" + expires.toGMTString();
	}

	function isValid(){
		//var f = document.LoginForm;
		//	if( ! checkFormValidate(f)  ) {
		//		return false ;
		//	}
		return true; 
	}

	function saveid() {
	  	var expdate = new Date();
	 // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
	  	var f = document.LoginForm;
	  	if (f.idsaveid.checked)
	    	expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);  // 30일
	  	else
	    	expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
		setCookie("saveid", f.m_id.value, expdate);
	}

	function saveid2() {
	  	var expdate = new Date();
	 // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
	  	var f = document.LoginForm;
	  	if (f.idsaveid.checked)
	    	expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);  // 30일
	  	else
	    	expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
		setCookie("password", f.m_pass.value, expdate);
	}

	function getCookie(Name) {
		  var search = Name + "="
		  if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면�
		    offset = document.cookie.indexOf(search)
		    if (offset != -1) { // 쿠키가 존재하면
		      offset += search.length
		      // set index of beginning of value
		      end = document.cookie.indexOf(";", offset)
		      // 쿠키 값의 마지막 위치 인덱스 번호 설정
		      if (end == -1)
		        end = document.cookie.length
		      return unescape(document.cookie.substring(offset, end))
		    }
		  }
		  return "";
		}

	function getid() {
		var f = document.LoginForm;
	  	f.idsaveid.checked = ((f.m_id.value = getCookie("saveid")) != "");  
	  	f.passsaveid.checked = ((f.m_pass.value = getCookie("password")) != "");  
	  	if(f.idsaveid.checked==true){
	  		f.m_pass.focus(); 
	  	}else{
	  		f.m_id.focus(); 
	  	}
	}

	function doSubmit(){
		var f = document.LoginForm;
		if(isValid()){
			if(f.idsaveid.checked==true){
				saveid();
		  	}
			if(f.passsaveid.checked==true){
				saveid2();
		  	}
			f.submit();
		}
	}
	window.onload = getid;
</script>
</head>
<body>
<div id="wrap" class="login">
	<form name="LoginForm" action="m_login.jsp" method="post">
	<input type="hidden" name="cmd" value="mDoLogin">
		<div class="content_wrap">
			<div class="c_top">
				<h1>금융감독원</h1>
				<h2>Member Login</h2>
			</div>
			<div class="c_mid">
				<div class="rows"><input id="input_id" name="m_id" type="text" class="ui_input_01" placeholder="아이디"><label for="input_id" class="invisible">아이디 입력</label></div>
				<div class="rows"><input id="input_pass" name="m_pass" type="password" class="ui_input_01" placeholder="비밀번호"><label for="input_pass" class="invisible">비밀번호 입력</label></div>
				<div class="rows shared"><input id="shared_id" name="idsaveid" type="checkbox"><label for="shared_id">Remember my ID on this mobile.</label></div>
				<button class="ui_login ui_shadow_00" onclick="doSubmit();">로그인</button>
			</div>
		</div>
	</form>	
</div>
</body>
</html>
