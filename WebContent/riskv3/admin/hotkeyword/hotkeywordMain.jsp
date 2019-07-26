<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--
	$(document).ready(loadList);
	
	function loadList()	{
		ajax.post('hotkeywordList.jsp','fSend','listArea');
	}

	//체크 박스 전체 선택시
	function selectAll(result){			
		var f = document.fSend;
		if( f.checkId ) {
			if(f.checkId.length){
				for( i=0; i< f.checkId.length; i++ )
		   		{
		   			 f.checkId[i].checked = result;
		   		}
			}else{
				f.checkId.checked = result;
			}	 
		}
		f.checkAll.checked = result;
	}

	function popInsert(){
		var f = document.fSend;
		window.open("about:blank", "pop_insert", "width=400, height=150");
		f.target = 'pop_insert';
		f.action = "popKeywordInsert.jsp";
		f.submit();
	}

	function dataDelete(){
		var f = document.fSend;
		var h_seqs = '';
		if(f.checkId){
			if(f.checkId.length){
				for(var i = 0; i < f.checkId.length; i++){
					if(f.checkId[i].checked){
						if(h_seqs == ''){
							h_seqs = f.checkId[i].value;
						}else{
							h_seqs += ','+f.checkId[i].value;
						}
					}
				}
			}else{
				if(h_seqs == ''){
					h_seqs = f.checkId.value;
				}else{
					h_seqs += ','+f.checkId.value;
				}
			}
		}
		f.h_seqs.value = h_seqs;
		f.mode.value = 'delete';
		f.target = 'prc';
		f.action = 'hotkeywordPrc.jsp';
		f.submit();
	}

	function popUpdate(h_seq){
		var f = document.fSend;
		f.h_seqs.value = h_seq;
		window.open("about:blank", "pop_update", "width=400, height=150");
		f.target = 'pop_update';
		f.action = "popKeywordUpdate.jsp";
		f.submit();
	}

//-->
</script>
</head>
<body>
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="h_seqs" id="h_seqs">
<input type="hidden" name="mode" id="mode">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/infogroup/tit_icon.gif" /><img src="../../../images/admin/infogroup/tit_0512.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">HOT 키워드관리</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 타이틀 끝 -->
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/infogroup/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.fSend.checkAll.checked);"/></td>
						<td><img src="../../../images/admin/infogroup/btn_add2.gif" style="cursor:pointer" onclick="popInsert()"/></td>
						<td><img src="../../../images/admin/infogroup/btn_del.gif" style="cursor:pointer" onclick="dataDelete()"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td id="listArea">       

				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</td>
	</tr>
</table>
<iframe name="prc" id="prc" style="display:none"></iframe>
</form>
</body>
</html>