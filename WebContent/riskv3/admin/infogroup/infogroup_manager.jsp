<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.admin.info.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="java.net.*"%>
<%@ page import="risk.admin.membergroup.membergroupMng" %>
<%@ page import="risk.admin.membergroup.membergroupBean" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();		

	membergroupMng mgm = new membergroupMng();
	membergroupBean mgb = new membergroupBean();
	ArrayList array = new ArrayList();
	String search_type = pr.getString("search_type", SS_MG_NO);
	if(search_type==null){search_type="0";}
	System.out.println("search_type : "+search_type);
	array = mgm.mginfo();
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
	
	//첨 로드시~
	function loadList()
	{		
		ajax.post('infogroup_list.jsp','managerForm','behindList');
	}

	function loadList2(mgSeq)
	{		
		ajax.post('infogroup_list.jsp?mgseq='+mgSeq,'managerForm','behindList');
	}


	//성향단어 에디터팝
	function popupEdit(id,mode)
	{				
		var f = document.managerForm;		
		f.i_seq.value = id;
		f.mode.value = mode;
		if(mode=='update')
		{
			popup.openByPost('managerForm','infogroup_edit.jsp',400,155,false,false,false,'trendPop');
		}else{
			popup.openByPost('managerForm','infogroup_edit.jsp',400,155,false,false,false,'trendPop');
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

	//정보그룹 삭제
	function deleteInfoGroup()
	{
		var f = document.managerForm;

		checkId();		
		if(f.checkIds.value==''){alert('선택된 리스트가 없습니다.'); return;}		
		f.mode.value = 'delete';
		f.target='processFrm';
		f.action='infogroup_prc.jsp';
		f.submit();		
	}

	function getInfoGroup(mgSeq){
		loadList2(mgSeq);
	}
		
//-->
</script>
</head>
<body>
<form name="managerForm" id="managerForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="i_seq" id="ekSeq">
<input type="hidden" name="checkIds" id ="checkIds">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/infogroup/tit_icon.gif" /><img src="../../../images/admin/infogroup/tit_0511.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">정보그룹관리</td>
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
						<td style="width:76px;"><img src="../../../images/admin/infogroup/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.managerForm.checkAll.checked);"/></td>
						<td><img src="../../../images/admin/infogroup/btn_add2.gif" style="cursor:pointer" onclick="popupEdit('','insert');"/></td>
						<td><img src="../../../images/admin/infogroup/btn_del.gif" style="cursor:pointer" onclick="deleteInfoGroup();"/></td>
						<td><select name="search_type" id="search_type" onChange="getInfoGroup(this.value);">
						<%
							if(array.size() >0){
								for(int i = 0; i < array.size(); i++){
									mgb = (membergroupBean)array.get(i);
									System.out.println(mgb.getMGseq()+"/"+mgb.getMGname());
						%>
							<option value="<%=mgb.getMGseq()%>" <%if(search_type.equals(mgb.getMGseq())){out.print("selected");}%>><%=mgb.getMGname()%></option>
						<%
								}
							}
						%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td id="behindList">       

				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</td>
	</tr>
</table>
<iframe name="processFrm" id="processFrm" style="display:none"></iframe>
</form>
</body>
</html>