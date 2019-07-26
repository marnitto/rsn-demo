<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*
				 ,risk.admin.bookmark.*           
                 "
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://sdspr.devel.com/js/jquery.js" type="text/javascript"></script>
<script src="http://sdspr.devel.com/js/ajax.js" type="text/javascript"></script>
<script src="http://sdspr.devel.com/js/popup.js" type="text/javascript"></script>
<link rel="stylesheet"  type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	
	var $jq = jQuery.noConflict();

	
	function inputCheck(formId)
	{
		var cursor  = null;
		var fields = '';	
			
		$jq('input:not(.required)').each(function(){
			if($jq.trim($jq(this).val())=='')
			{															
				$jq(this).css('background-color', '#ff7');
				//$jq(this).parent().find('span.error-message').remove();			
				//alert($jq(this).attr('msg')+'를 입력해주세요.');				
				//$jq('<span></span>').addClass('error-message').text($jq(this).attr('msg')+'를 입력해주세요.').appendTo($jq(this).parent());				
				fields += fields == '' ? $jq(this).attr('field') : ', '+$jq(this).attr('field');
				if(cursor==null)
				{
					cursor = $jq(this);
				}					
			}
		});	
			
		if(cursor==null)
		{
			return true;
		}else{
			alert(fields+'를 입력해주세요.');
			cursor.focus();
			return false;
		}	
	}

	function insertMember(formId){
		if(formCheck(formId)){
			$jq('#'+formId).attr({target:'',method:'post', action:'jquery1.jsp'});
			$jq('#'+formId).submit();
		}
	}




//-->
</script>
</head>
<body>
<form id="memberForm" method="post">
<table>
	<tr>
		<td>주민번호:<input id="a" type="text" field="주민번호" value="" required></input></td>
	</tr>
	<tr>
		<td>이름:    <input id="b" type="text" field="이름" value="" required></input></td>
	</tr>
	<tr>
		<td>아이디:  <input id="c" type="text" field="아이디" value="" required></input></td>
	</tr>
</table>
<a href="javascript:insertMember('memberForm');">전송하기</a>
</form>

</body>
</html>