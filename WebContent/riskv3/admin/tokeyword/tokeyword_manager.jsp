<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.tokeyword.ToKeywordBean,
                 risk.admin.tokeyword.ToKeywordMgr,
                 risk.util.ParseRequest,
                 java.net.*"
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String searchKey = pr.getString("searchKey");			//검색어
	String order = pr.getString("order", "EK_KEYWORD ASC");	//정렬 순서
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<link rel="stylesheet" href="<%=SS_URL%>/css/common.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--


	$(document).ready(loadList);
	
	//첨 로드시~
	function loadList()
	{		
		ajax.post('behind_list.jsp?subCodeName=issueCode','managerForm','behindList');
	}

	//단어 검색시
	function Search()
	{
		var f = document.managerForm;
		//AJAX 사용시 한글처리
		//f.searchKey.value = encodeURI(f.searchWord.value);
		//앞뒤 공백 제거
		f.searchWord.value = f.searchWord.value.replace(/^\s*/,'');
		f.searchWord.value = f.searchWord.value.replace(/\s*$/,'');				
		
		loadList();
	}

	//정렬시
	function excuteOrder(order)
	{
		var f = document.managerForm;		
		f.order.value = order;		
		Search(); 
	}

	//성향단어 에디터팝
	function popupEdit(id,mode)
	{				
		var f = document.managerForm;		
		f.ekSeq.value = id;
		f.mode.value = mode;
		if(mode=='update')
		{
			popup.openByPost('managerForm','tokeyword_edit.jsp',400,250,false,false,false,'trendPop');
		}else{
			popup.openByPost('managerForm','tokeyword_edit.jsp',400,155,false,false,false,'trendPop');
		}
		
	}

	//체크 박스 전체 선택시
	function selectAll(result)
	{			
		var f = document.managerForm;
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

	//체크된  ID
	function checkId()
	{
		var f = document.managerForm;
		f.checkIds.value = '';
		if( f.checkId ) {
			if(f.checkId.length){				
				for( i=0; i< f.checkId.length; i++ )
		   		{
			   		if(f.checkId[i].checked==true)
			   		{				   		
				   		f.checkIds.value == '' ? f.checkIds.value = f.checkId[i].value : f.checkIds.value += ','+ f.checkId[i].value;
			   		}
		   		}
			}else{
				if(f.checkId.checked==true)
		   		{	
					f.checkIds.value = f.checkId.value; 
		   		}
			}	 
		}
		//alert(f.checkIds.value);
	}

	//단어 삭제
	function deleteWord()
	{
		var f = document.managerForm;

		checkId();		
		if(f.checkIds.value==''){alert('선택된 리스트가 없습니다.'); return;}		
		f.mode.value = 'delete';
		f.target='processFrm';
		f.action='tokeyword_prc.jsp';
		f.submit();		
	}


	var ynImg = null;
	function showUse(obj, use, ekSeq){

		ynImg = obj;

		var useyn = '';
		if(use == 'on'){
			useyn = 'N';
		}else{
			useyn = 'Y';
		}

		$.ajax(
				{
						type : "post",
						url : "tokeyword_prc.jsp", //여기 에러~~
						data : "mode=usechk&useyn="+useyn+"&ekSeq="+ekSeq+"",
						success : useResult,
						error : function(){alert('에러 : showUse');}
				}
		);
		
	}

	String.prototype.trim = function(){
		return this.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
    }

	function useResult(result){

		var tr_result = result.trim(); 
		
		if(tr_result != ""){

			if(tr_result == "on" || tr_result == "off"){	
				ynImg.src = "../../../images/admin/aekeyword/"+tr_result+"_btn.gif";
			}
		}
	}
	
		
//-->
</script>
</head>
<body>
<form name="managerForm" id="managerForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="ekSeq" id="ekSeq">
<input type="hidden" name="checkIds" id ="checkIds">
<input type="hidden" name="order" id="order">
<input type="hidden" name="searchKey" id="searchKey">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/aekeyword/tit_icon.gif" /><img src="../../../images/admin/aekeyword/tit_today.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">투데이키워드관리</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->

			<!-- 검색 시작 -->
			<tr>
				<td class="search_box">
				<table id="search_box" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>검색 단어</th>
						<td style="vertical-align:middle"><input style="width:500px;vertical-align:middle" class="textbox" type="text" name="searchWord" onkeypress="Javascript:if (event.keyCode == 13) { Search(); }" style="vertical-align:middle"><img src="../../../images/admin/aekeyword/btn_search.gif" style="vertical-align:middle;cursor:pointer" onclick="Search();"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/aekeyword/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.managerForm.checkAll.checked);"/></td>
						<td><img src="../../../images/admin/aekeyword/btn_add2.gif" style="cursor:pointer" onclick="popupEdit('','insert');"/></td>
						<td><img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteWord();"/></td>
					</tr>
				</table>				
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td id="behindList"></td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</td>
	</tr>
</table>
</form>
<iframe name="processFrm" height="0" border="0" style="display: none;"></iframe>
</body>
</html>