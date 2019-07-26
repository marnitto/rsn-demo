<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.util.ParseRequest
					" %>
<%@page import="risk.util.PageIndex"%>

<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;
	pr.printParams();
	
	int rowCnt = 10;
	int iNowPage        = pr.getInt("nowpage",1);
	String searchWord = pr.getString("searchWord","");
	
	if( !pr.getString("seqList").equals("") ){
		seqList = pr.getString("seqList");
	}
	
	int iTotalCnt= AddDao.addressCount( searchWord );
	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
		
	ArrayList ArrMailUserBean;
	ArrMailUserBean = AddDao.addressList( iNowPage, rowCnt, searchWord );
	
	String strMsg = "";
	strMsg = " <b>"+iTotalCnt+"건</b>, "+iNowPage+"/"+iTotalPage+" pages";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="javascript">
<!--
	var allcheck = 0;
	function receverAdd()
	{
		location.href = 'receiver_detail.jsp?mode=add';
	}
	
	function receverDetail( seq )
	{
		location.href = 'receiver_detail.jsp?abSeq='+seq+'&mode=edit';
	}
	function allselect()
	{
		var frm = document.all;
		if( frm.tt ) {
			if( allcheck == 0 ) {
				if( frm.tt.length > 1 ) {
					for( i=0; i< frm.tt.length; i++ )
			   		{
			   			 frm.tt[i].checked = true;
			   		}
			   	}else {
			   		frm.tt.checked = true;
			   	}
		   		allcheck = 1;
		   	}else {
		   		if( frm.tt.length > 1 ) {
			   		for( i=0; i< frm.tt.length; i++ )
			   		{
			   			 frm.tt[i].checked = false;
			   		}
			   	}else {
			   		frm.tt.checked = false;
			   	}
		   		allcheck = 0;
		   	}
		}
	}
	
	function delList()
	{
		var kg_list = '';
    	var i = 0;
    	
    	var frm = document.fSend;
    	
    	if ( confirm("삭제 하시겠습니까?" ) ) {
    		if( frm.ab_seq ) {
	    		if( frm.ab_seq.length > 1 ) {
		    		for( i=0; i< frm.ab_seq.length; i++ )
		    		{
		    			if( frm.ab_seq[i].checked == true ) {
		    				if( kg_list.length > 0 ) {
		    					kg_list = kg_list+','+frm.ab_seq[i].value;
		    				}else {
		    					kg_list = frm.ab_seq[i].value;
		    				}
		    			}
		    		}
		    	}else {
		    		if( frm.ab_seq.checked == true ) {
		    			kg_list = frm.ab_seq.value;
		    		}
		    	}
			}
			    
		    if( kg_list.length > 0 ) {
			    frm.seqList.value = kg_list;
			    frm.mode.value = 'del';
			    frm.target = '';
			    frm.action = 'receiver_prc.jsp';
			    frm.submit();
			}else {
	    		alert('삭제할 수신자를 선택하세요');
	    	}
		}
	}
	
	function pageClick( paramUrl ) {
		var f = document.getElementById('fSend');
        f.action = "receiver_list.jsp" + paramUrl;
        f.submit();
		/*
		document.fSend.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		document.fSend.submit();
		*/
    }
	
//-->
</script>
</head>
<body>
<form id="fSend" name="fSend" action="receiver_list.jsp" method="post">
<input type="hidden" name="mode" value="">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="seqList" value="">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/receiver/tit_icon.gif" /><img src="../../../images/admin/receiver/tit_0506.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">정보수신자관리</td>
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
						<th style="height:30px;">이름검색</th>
						<td style="vertical-align:middle"><input style="width:460px;vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=searchWord%>" onkeydown="javascript:if (event.keycode == 13) { fsend.submit(); }"><img src="../../../images/admin/receiver/btn_search.gif" onclick="fSend.submit();" style="cursor:pointer;vertical-align:middle"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/receiver/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
						<td><img src="../../../images/admin/receiver/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
						<td style="width:88px;"><img src="../../../images/admin/receiver/btn_useradd2.gif" onclick="receverAdd();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="5%"><col width="15%"><col width="15%"><col width="15%"><col width="20%"><col width="30%">
					<tr>      
						<th><input type="checkbox" id="tt" value="checkbox" onclick="allselect();"></th>       
						<th>성명</th>
						<th>부서</th>
						<th>직급</th>
						<th>보고서 수신</th>
						<th>연락처</th>
					</tr>
<%
  	for(int i=0; i < ArrMailUserBean.size(); i++){
		AddBean = new AddressBookBean();
		AddBean = 	(AddressBookBean)ArrMailUserBean.get(i);
		
		String smsReceive = "";
		String reportReceive = "";
		String[] sms = AddBean.getMab_sms_receivechk().split(",");
		for(int j=0; j < sms.length; j++){
			if(sms[j].equals("1") |	sms[j].equals("2") | sms[j].equals("3") | sms[j].equals("4")){
				smsReceive = "Y";
			}else {
			smsReceive = "N";
			}
		}
		if(AddBean.getMab_issue_receivechk().equals("1"))reportReceive = "I";
		if(AddBean.getMab_report_day_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/D" ; 
		if(AddBean.getMab_report_week_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/W" ;
%>
					<tr>
						<td><input type="checkbox" name="ab_seq" id="tt" value=<%=AddBean.getMab_seq()%>></td>
						<td onclick="receverDetail( <%=AddBean.getMab_seq()%> );" style="cursor:pointer;"><%=AddBean.getMab_name()%></td>
						<td><%=AddBean.getMab_dept()%></td>
						<td><%=AddBean.getMab_pos()%></td>
						<td><%=reportReceive%></td>
						<td><%=AddBean.getMab_mobile()%> </td>
					</tr>
<%
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:128px;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><img src="../../../images/admin/receiver/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
								<td><img src="../../../images/admin/receiver/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
							</tr>
						</table>
						</td>
						<td align="right"><img src="../../../images/admin/receiver/btn_useradd2.gif" onclick="receverAdd();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1" align="center">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(iNowPage, iTotalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>

	</tr>
</table>
</form>
</body>
</html>