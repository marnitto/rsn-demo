<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="risk.util.ParseRequest" 
%>    
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String selectedSdGsn = pr.getString("selectedSdGsn","");
	String mode = pr.getString("mode","");
	String as_seq = pr.getString("as_seq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script src="/js/jquery.js"  type="text/javascript"></script>
<script src="/js/ajax.js"  type="text/javascript"></script>
<script src="/js/popupUtil-1.0.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">

	window.onload = loadList;
	
	//좌측 사이트 리스트~
	function loadList()
	{		
		selectedList();			
	}

	function findList()
	{
		var f = document.siteList;
		if(f.tempSiteName.value.length < 2 ){alert('2자 이상  입력해주세요.'); return;}					
		f.siteName.value = encodeURI( f.tempSiteName.value);
		//alert(f.siteName.value);
		ajax.post('behind_sitelist.jsp','siteList','siteFilterList');	
	}

	function selectedList()
	{
		//alert($("#selectedSdGsn").val());
		ajax.post('behind_select_sitelist.jsp','siteList','siteSelectList');	
	}


	function selectedListCheck(sd_gsn)
	{
		var f = document.siteList;
		var check = true;
		var list = new Array();
		list = f.selectedSdGsn.value.split(',');

		for(var i =0; i<list.length; i++)
		{
			if(list[i]==sd_gsn)
			{
				check = false;
				break;
			}
		}
		return check;
	}	
	
	function selectRightMove(sd_gsn)
	{
		var f = document.siteList;
	
		if(!selectedListCheck(sd_gsn)){alert('이미 선택된 사이트 입니다.');	return;}	
		
		if(f.selectedSdGsn.value!='')
		{
			f.selectedSdGsn.value += ","+ sd_gsn;
		}else{
			f.selectedSdGsn.value = sd_gsn;
		} 		
		selectedList();
	}

	function delGsn(sd_gsn)
	{
		var f = document.siteList;
		var list = new Array();
		list = f.selectedSdGsn.value.split(',');

		f.selectedSdGsn.value = '';
		for(var i =0; i<list.length; i++)
		{
			if(list[i]!=sd_gsn)
			{				
				if(f.selectedSdGsn.value!='')
				{
					f.selectedSdGsn.value += ","+ list[i];
				}else{
					f.selectedSdGsn.value = list[i];
				} 
			}
		}
	}

	function selectLeftMove(sd_gsn)
	{	
		delGsn(sd_gsn);		
		selectedList();
	}

	function selectedListCnt()
	{
		var f = document.siteList;
		var check = true;
		var list = new Array();
		if(f.selectedSdGsn.value!='')
		{
			list = f.selectedSdGsn.value.split(',');
		}
		
		return list.length;
	}	
	
	function save()
	{
		var f = document.siteList;
		var selectedCnt;
		selectedCnt = String(selectedListCnt());
		opener.document.getElementById('siteListCnt').innerHTML = selectedCnt+'개의 사이트가 선택되었습니다.';
		opener.document.alimi_detail.sd_gsns.value = document.siteList.selectedSdGsn.value;
		//alert(opener.document.alimi_detail.sd_gsns.value);
		
		var targetUrl = "site_prc.jsp";	
		var param = $("#siteList").serialize();
		$.post(targetUrl, param, function(data){				
					if(data > 0){
						alert("저장되었습니다.");
					}										
				});
		
		window.close();
	}
		
</script>

<title>Insert title here</title>
</head>
<body>
<form id="siteList" name="siteList" method="post" onSubmit ="return false;">
<input type="hidden" id="mode" name="mode" value="<%=mode%>">
<input type="hidden" id="siteName" name="siteName">
<input type="hidden" id="as_seq" name="as_seq" value="<%=as_seq%>">
<input type="hidden" id="selectedSdGsn" name="selectedSdGsn" value="<%=selectedSdGsn%>">

<table width="850" border="0" cellspacing="0" cellpadding="0">	
	<tr>
		<td colspan="3" style="padding: 5px 0px 5px 0px;"><input  class="txtbox" style="width:150px;height:15px;" name="tempSiteName" type="text" value=""  OnKeyDown="Javascript:if (event.keyCode == 13) { findList(); return false;}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*찾을 사이트이름을 입력후 엔터를 누르세요.</td>
	</tr>
	<tr>
        <td colspan="3" bgcolor="#9CBBE5" width="1" height="2"></td>
    </tr>														
	<tr height="450" valign="top">
		<td width="400"  id="siteFilterList">								
		
		</td>
		<td width="50">								
		
		</td>
		<td width="400" id="siteSelectList">
		
		</td>
	</tr>
	<tr>		
		<td colspan="3" align="center">&nbsp;</td>
	</tr>
	<tr>
        <td colspan="3" bgcolor="#9CBBE5" width="1" height="2"></td>
    </tr>
    <tr>
        <td colspan="3" width="1" height="5"></td>
    </tr>
	<tr>		
		<td colspan="3" align="center">	
			<img src="../../../images/admin/alimi/btn_save2.gif"  hspace="5" onclick="save();" style="cursor:hand;"><img src="../../../images/admin/alimi/btn_cancel.gif"  hspace="5" onclick="window.close();" style="cursor:hand;">
		</td>
	</tr>
</table>
</form>
</body>
</html>