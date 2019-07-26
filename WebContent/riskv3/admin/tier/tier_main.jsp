<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="
                java.util.ArrayList
                ,risk.util.*                
                "
%>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style1 {COLOR: #2D2D2D; LINE-HEIGHT: 16px; FONT-FAMILY: "Dotum"; font-size: 12px;}
</style>
<link rel="stylesheet" type="text/css" href="../../../css/base.css">
<link rel="stylesheet" href="../../../css/tier/basic.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script type="text/JavaScript">

	$(document).ready(init);

	//검색 텍스트 활성화
	function init(){		
		loadTierSite();
	}

	function loadTierSite(){
		ajax.post3('tier_list.jsp','tierForm','tierSite_list','');
	}

	function tsTypeCheck(){
		var obj = $('#ts_type');
		obj.val('');		
		$('input[name=ts_types]:checked').each(function(){
			if(obj.val()==''){
				obj.val($(this).val());
			}else{
				obj.val(obj.val()+','+$(this).val());
			};
		});
	}	

	function searchTierSite(){
		tsTypeCheck();
		loadTierSite();
	}
	

	function fnTextCheck(obj) 
	{		
	    if (/[^0-9.,]/g.test(obj.value))
	    {	    	
	        obj.value='';
	        obj.focus();
	        alert('숫자만 입력 가능합니다.');
	        return false;
	    }else{
	        if( obj.value >= -128 && obj.value <= 127 ){
		        return true;
	        }else{
		        var text1 = obj.value.substring(0, obj.value.length - 1);       
		        obj.value = text1;
		        alert('-128에서 127 까지 입력하세요.');
		        return false;
	        }
	    }
	}

	function updateTierSite(ts_seq,typeId,rankId)
	{		
		$('#ts_seq').val(ts_seq);
		$('#ts_type').val($('#'+typeId+" option:selected").val());	
		$('#ts_rank').val($('#'+rankId).val());		
		$('#mode').val("update");
		$('#tierForm').attr('target','processFrm');
		$('#tierForm').attr('action','tier_prc.jsp');
		$('#tierForm').submit();

		setTimeout('loadTierSite()', '200');		
	}

	function deleteTierSite(ts_seq)
	{		
		$('#ts_seq').val(ts_seq);		
		$('#mode').val("delete");
		$('#tierForm').attr('target','processFrm');
		$('#tierForm').attr('action','tier_prc.jsp');
		$('#tierForm').submit();

		setTimeout('loadTierSite()', '200');		
	}

	function popTierSiteSearch()
	{
		popup.openByPost('tierForm','pop_tier_search.jsp',320,450,false,true,false,'pop_tiersite_search1');
	}
	
	
	function excelSave(){
		
		document.ifr_excel.location = 'tier_site_excel_down.jsp?ts_types='+document.tierForm.ts_type.value;		
	}

</script>
</head>
<body  leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<iframe name="processFrm" height="0" border="0" style="display:none"></iframe>
<form id="tierForm" name="tierForm" method="post" onsubmit="return false;">
<input type="hidden" id="mode" name="mode">
<input type="hidden" id="ts_seq" name="ts_seq">
<input type="hidden" id="ts_type" name="ts_type" value="1,2,3,4,5">
<input type="hidden" id="ts_rank" name="ts_rank" value="">

<!-- <div style="width:850px"> -->
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top" style="padding-left:20px">
		<table width="820" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto">
		<tr>
	          <td>
		      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
						<td class="tit_bg" style="height:37px;padding-top:0px;">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr>
								<td><img src="../../../images/admin/alimi/tit_icon.gif"><img src="../../../images/admin/tier/tit_1224.gif"></td>
								<td align="right">
									<table border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="navi_home">HOME</td>
										<td class="navi_arrow">관리자</td>
										<td class="navi_arrow2">매체관리</td>
									</tr>
								</tbody></table>
								</td>
							</tr>
						</tbody></table>
						</td>
				  </tr>  
				  <tr>
				    <td><img src="../../../images/admin/tier/brank.gif" width="1" height="20"></td>
				  </tr>
				</table>
			  </td>        
		</tr>
	</table>
	
	<table width="780" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto">
	  <tr>
	    <td>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td height="2" bgcolor="7ca5dd"></td>
	            </tr>
	            <tr>
	              <td>
	              	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cdd4d6">
	                  <tr>
	                    <td width="100" height="41" align="center" bgcolor="f6f6f6">Tiering 선택</td>
	                    <td bgcolor="#FFFFFF" style="text-align: left;"><span  style="padding:10px 10px 10px 30px;"><input type="checkbox" id="ts_types" name="ts_types" value="1" checked="checked" style="vertical-align: middle;" />1</span>
						<span  style="padding:10px 10px 10px 30px;"><input type="checkbox" id="ts_types" name="ts_types" value="2" checked="checked" style="vertical-align: middle;" />2</span>
						<span  style="padding:10px 10px 10px 30px;"><input type="checkbox" id="ts_types" name="ts_types" value="3" checked="checked" style="vertical-align: middle;" />3</span>
						<span  style="padding:10px 10px 10px 30px;"><input type="checkbox" id="ts_types" name="ts_types" value="4" checked="checked" style="vertical-align: middle;" />4</span>
						<span  style="padding:10px 10px 10px 30px;"><input type="checkbox" id="ts_types" name="ts_types" value="5" checked="checked" style="vertical-align: middle;" />5</span>
						<span style="padding: 10px 10px 10px 50px;"><a href="javascript:searchTierSite();"><img src="../../../images/admin/tier/search_btn.gif" width="50" height="23" style="cursor:pointer" align="absmiddle"></a></span></td>
	                  </tr>
	              </table>
	              </td>
	            </tr>
	        </table>
	        </td>
	   </tr>
	   <tr>
	   	<td height="15">&nbsp;</td>
	   </tr>
	   <tr>
	    <td height="32" colspan="4" style="padding: 0px 0px 0px 8px;">   
	    <img src="../../../images/admin/tier/ecxel_btn.gif" width="79" height="24" align="absmiddle" style="cursor:pointer;" onclick="excelSave();">
		<img src="../../../images/admin/tier/write_btn_01.gif" width="79" height="24" align="absmiddle" style="cursor:pointer;" onclick="popTierSiteSearch();"></td>
	   </tr>
	   <tr>    
	    <td valign="top">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="2" bgcolor="7ca5dd"></td>
	      </tr>
	      <tr>
	        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cdd4d6">            
	            <tr>
	              <td width="32"  align="center"bgcolor="#F7FDFF" class="table_top" ><strong>순번</strong></td>
	              <td width="57" height="30" align="center"bgcolor="#F7FDFF" class="table_top"><strong>Tiering</strong></td>
	              <td align="center" bgcolor="#F7FDFF" class="table_top"><strong>사이트명</strong></td>
	              <td width="170" align="center" bgcolor="#F7FDFF" class="table_top"><strong></strong></td>
	            </tr>            
	        </table></td>
	      </tr>
	      <tr>
	        <td id="tierSite_list">
	        
	        </td>
	      </tr>
	    </table></td>
	  </tr>
	</table></td>
	</tr>
</table>

</form>
<iframe name="ifr_excel" height="0" border="0" style="display:none"></iframe>
</body>
</html>