<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>

<%@ page import="risk.util.*,
				 java.util.List,
				 risk.search.userEnvMgr,
				 risk.search.keywordInfo,
				 risk.admin.site.SitegroupBean,
				 risk.admin.site.SiteMng,
				 risk.admin.membergroup.membergroupBean,
				 risk.admin.membergroup.membergroupMng
				 "%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	int i;
	List KGlist = null;
	List sglist = null;
	String mgseq = null;
	String[] menufd = null;
	String[] xpfd = null;
	String[] sgfd = null;
	membergroupBean mginfo = null;

	StringUtil su = new StringUtil();
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();

	SiteMng sitemng = new SiteMng();
	membergroupMng mgmng = membergroupMng.getInstance();

	if( request.getParameter("mgseq") != null ) {
		mgseq = request.getParameter("mgseq");
		System.out.println(mgseq);
		mginfo = (membergroupBean)mgmng.getMGBean(mgseq);
		if( mginfo.getMGmenu() != null ) menufd = mginfo.getMGmenu().split(",");
		if( mginfo.getMGxp() != null ) xpfd = mginfo.getMGxp().split(",");
		if( mginfo.getMGsite() != null ) sgfd = mginfo.getMGsite().split(",");
	}
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	function mnu_click(index){
	if(index == 1){//키워드 그룹 클릭
		//탭 위치가 키워드 그룹으로 와야지
		document.getElementById('tab1').style.display = 'none';
		document.getElementById('tab2').style.display = 'block';
		document.getElementById('tab3').style.display = 'none';
		document.getElementById('tabImg1').className = 'tab_off';
		document.getElementById('tabImg2').className = 'tab_on';
		document.getElementById('tabImg3').className = 'tab_off';
		document.getElementById('tabImg1').innerHTML = '기능';
		document.getElementById('tabImg2').innerHTML = '<span class="tab_on_txt">키워드그룹</span>';
		document.getElementById('tabImg3').innerHTML = '사이트그룹';
	}else if(index == 2){
		document.getElementById('tab1').style.display = 'none';
		document.getElementById('tab2').style.display = 'none';
		document.getElementById('tab3').style.display = 'block';
		document.getElementById('tabImg1').className = 'tab_off';
		document.getElementById('tabImg2').className = 'tab_off';
		document.getElementById('tabImg3').className = 'tab_on';
		document.getElementById('tabImg1').innerHTML = '기능';
		document.getElementById('tabImg2').innerHTML = '키워드그룹';
		document.getElementById('tabImg3').innerHTML = '<span class="tab_on_txt">사이트그룹</span>';
	}else{
		document.getElementById('tab1').style.display = 'block';
		document.getElementById('tab2').style.display = 'none';
		document.getElementById('tab3').style.display = 'none';
		document.getElementById('tabImg1').className = 'tab_on';
		document.getElementById('tabImg2').className = 'tab_off';
		document.getElementById('tabImg3').className = 'tab_off';
		document.getElementById('tabImg1').innerHTML = '<span class="tab_on_txt">기능</span>';
		document.getElementById('tabImg2').innerHTML = '키워드그룹';
		document.getElementById('tabImg3').innerHTML = '사이트그룹';
	}
		
	/*
		var t = document.getElementsByName('tabImg');
		for(var i = 0; i < t.length; i++){
			t[i].className = 'tab_off';
			document.getElementById('tab'+(i+1)).style.display = 'none';
		}
		t[0].innerHTML = '기능';
		t[1].innerHTML = '키워드그룹';
		t[2].innerHTML = '사이트그룹';
		
		obj.className = 'tab_on';
		if(index == 0){
			obj.innerHTML = '<span class="tab_on_txt">기 능</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}else if(index == 1){
			obj.innerHTML = '<span class="tab_on_txt">키워드그룹</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}else if(index == 2){
			obj.innerHTML = '<span class="tab_on_txt">사이트그룹</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}
	*/
	}


	function curr_save() {
		if( mgset.mgseq.value ) {
			mgset.submit();
		}
	}
	
//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="mgset" action="ifram_usergroup_right_prc.jsp" method="post">
<input type="hidden" name="mgseq" value="<%=mgseq%>">
<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th id="tabImg1" style="cursor:pointer; width:33%" class="tab_on" onclick="mnu_click(0)"><span class="tab_on_txt">기 능</span></th>
		<th class="tab_off" id="tabImg2" style="cursor:pointer" onclick="mnu_click(1)">키워드그룹</th>
		<th class="tab_off" id="tabImg3" style="cursor:pointer" onclick="mnu_click(2)">사이트그룹</th>
	</tr>
	<tr>
		<td id="tab1" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="1" <%if( menufd != null ){ if( su.inarray(menufd, "1") ) out.println("checked");}%>>정보검색</td>
			</tr>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="2" <%if( menufd != null ){ if( su.inarray(menufd, "2") ) out.println("checked");}%>>이슈관리</td>
			</tr>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="3" <%if( menufd != null ){ if( su.inarray(menufd, "3") ) out.println("checked");}%>>보고서관리</td>
			</tr>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="4" <%if( menufd != null ){ if( su.inarray(menufd, "4") ) out.println("checked");}%>>통계분석</td>
			</tr>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="5" <%if( menufd != null ){ if( su.inarray(menufd, "5") ) out.println("checked");}%>>관리자</td>
			</tr>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="6" <%if( menufd != null ){ if( su.inarray(menufd, "6") ) out.println("checked");}%>>대시보드</td>
			</tr>
			<%-- <tr><td>
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="30"></td>
						<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="7" <%if( menufd != null ){ if( su.inarray(menufd, "7") ) out.println("checked");}%>>대시보드 메인</td>
					</tr>
					<tr>
						<td width="30"></td>
						<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="8" <%if( menufd != null ){ if( su.inarray(menufd, "8") ) out.println("checked");}%>>대시보드 언론</td>
					</tr>
					<tr>
						<td width="30"></td>
						<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="9" <%if( menufd != null ){ if( su.inarray(menufd, "9") ) out.println("checked");}%>>대시보드 품질</td>
					</tr>
					<tr>
						<td width="30"></td>
						<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="10" <%if( menufd != null ){ if( su.inarray(menufd, "10") ) out.println("checked");}%>>대시보드 Compliance</td>
					</tr>											
				</table>
			</td></tr> --%>
		</table>
		</td>
		<td id="tab2" style="display:none;" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
<%
	for( i=0 ; i < KGlist.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)KGlist.get(i);
%>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_kg" value="<%=KGset.getK_Xp()%>" <%if( xpfd != null ){ if( su.inarray(xpfd, KGset.getK_Xp()) ) out.println("checked");}%>><%=KGset.getK_Value()%></td>
			</tr>
<%
	}
%>
		</table>
		</td>
		<td id="tab3" style="display:none;" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
<%
	sglist = sitemng.getSGList();
	for( i=0 ; i < sglist.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
%>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_site" value="<%=SGinfo.get_seq()%>" <%if( sgfd != null ){ if( su.inarray(sgfd, String.valueOf(SGinfo.get_seq())) ) out.println("checked");}%>><%= SGinfo.get_name()%></td>
			</tr>
<%
	}
%>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>