<%@ page contentType = "text/html; charset=euc-kr"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import=" risk.util.ParseRequest
			    , java.util.ArrayList
			    , risk.mobile.AlimiSettingDao
			    , risk.mobile.AlimiSettingBean
			    , risk.util.PageIndex
				" %>			
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = new AlimiSettingBean();
	ArrayList arrAlimiSetList = new ArrayList();
	
	//pr.printParams();
	
	int rowCnt = 20;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	arrAlimiSetList = asDao.getAlimiSetList(iNowPage , rowCnt ,"","Y");
	
	int iTotalCnt= asDao.getListCnt();	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
	
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
	var allcheck = 0;

	function asSetInsert()
	{
		var f = document.alimi_setting_list;
		f.mode.value='INSERT';
		f.target='';
		f.action = 'alimi_setting_detail.jsp';
		f.submit();
	}
		
	function asSetUpdate( as_seq )
	{
		var f = document.alimi_setting_list;
		f.as_seq.value = as_seq;
		f.mode.value ='UPDATE';
		f.target='';
		f.action = 'alimi_setting_detail.jsp';
		f.submit();		
	}
		
	function allselect()
	{
		var frm = document.all;
		if( frm.chkNum ) {
			if( allcheck == 0 ) {
				if( frm.chkNum.length > 1 ) {
					for( i=0; i< frm.chkNum.length; i++ )
			   		{
			   			 frm.chkNum[i].checked = true;
			   		}
			   	}else {
			   		frm.chkNum.checked = true;
			   	}
		   		allcheck = 1;
		   	}else {
		   		if( frm.chkNum.length > 1 ) {
			   		for( i=0; i< frm.chkNum.length; i++ )
			   		{
			   			 frm.chkNum[i].checked = false;
			   		}
			   	}else {
			   		frm.chkNum.checked = false;
			   	}
		   		allcheck = 0;
		   	}
		}
	}
	
	function delList()
	{
		var selectNum = '';
    	var i = 0;
    	
    	var frm = document.alimi_setting_list;
    	
    	if ( confirm("삭제 하시겠습니까?" ) ) {
    		if( frm.chkNum ) {
	    		if( frm.chkNum.length > 1 ) {
		    		for( i=0; i< frm.chkNum.length; i++ )
		    		{
		    			if( frm.chkNum[i].checked == true ) {
		    				if( selectNum.length > 0 ) {
		    					selectNum = selectNum+','+frm.chkNum[i].value;
		    				}else {
		    					selectNum = frm.chkNum[i].value;
		    				}
		    			}
		    		}
		    	}else {
		    		if( frm.chkNum.checked == true ) {
		    			selectNum = frm.chkNum.value;
		    		}
		    	}
			}
			//alert(selectNum);
		    if( selectNum.length > 0 ) {
			    frm.as_seqs.value = selectNum;
			    frm.mode.value = 'DELETE';
			    frm.target = '';
			    frm.action = 'alimi_setting_prc.jsp';
			    frm.submit();
			}else {
	    		alert('삭제할 수신자를 선택하세요');
	    	}
		}
	}
	
	function pageClick( paramUrl ) {
		document.alimi_setting_list.nowpage.value = paramUrl;
		document.alimi_setting_list.submit();
    }
	
//-->
</SCRIPT>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" >
<form name="alimi_setting_list" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="as_seq">
<input type="hidden" name="as_seqs">
<table width="780" border="0" cellspacing="0" cellpadding="0">

  <tr>
     <td background="../images2/top_title_bg.gif"><img src="../images/admin_title_09.gif" ></td>
    <td align="right" background="../images2/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">수신할 대상 및 수신조건을 관리합니다.</td>
  </tr>
  <tr>
    <td colspan="2"><img src="../images2/brank.gif" width="1" height="40"></td>
  </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24"> 
    <img src="../images/con_btn01.gif" width="82"  height="24" onclick="allselect();" style="cursor:hand;">
    <img src="../images2/btn_del.gif" width="55" height="24" hspace="5" onclick="delList();" style="cursor:hand;">    
    </td>
    <td align="right"><img src="../images/new_btn.gif" width="55" height="24" hspace="5" onclick="asSetInsert();" style="cursor:hand;">
    </td>
  </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="44" align="center" background="../images2/admin_table_bg01.gif"><input type="checkbox" name="tt" value="checkbox" onclick="allselect();"></td>
	<td width="3" background="../images2/admin_table_bg01.gif"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>
    <td width="200" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>제 목</strong></td>
    <td width="3" background="../images2/admin_table_bg01.gif"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>   
    <td width="74" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>상 태</strong></td>
    <td width="3" background="../images2/admin_table_bg01.gif"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>   
    <td width="110" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>발송매체</strong></td>
    <td width="3" background="../images2/admin_table_bg01.gif" class="table_top"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>
    <td width="110" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong><strong>발송간격</strong></td>
    <td width="3" background="../images2/admin_table_bg01.gif" class="table_top"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>
    <td width="82" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>수신자</strong></td>
    <td width="3" background="../images2/admin_table_bg01.gif" class="table_top"><img src="../images2/admin_table_bar.gif" width="3" height="33"></td>
    <td width="142" align="center" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>발송 게시 일자</strong></td>
  </tr>
<%
	
	if(arrAlimiSetList.size()>0){
	  	for(int i=0; i < arrAlimiSetList.size(); i++)
		{
	  		asBean = new AlimiSettingBean();  	
	  		asBean = 	(AlimiSettingBean)arrAlimiSetList.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		if(asBean.getArrReceiver()!=null)
	  		{
	  			arrReceiverList = asBean.getArrReceiver();
	  		}
		
%>
  <tr>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;"><input type="checkbox" name="chkNum" value="<%=asBean.getAs_seq()%>"></td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;" onclick="asSetUpdate( '<%=asBean.getAs_seq()%>' );" style="cursor:hand;padding: 5px 0px 0px 0px;" align="left"><nobr style ="text-overflow:ellipsis; overflow:hidden; width:200;text-align:left"><%=asBean.getAs_title()%></nobr></td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>    
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;" style="cursor:hand;padding: 5px 0px 0px 0px;" align="left"><%if(asBean.getAs_chk().equals("1")){out.print("발송");}else{out.print("중지");} %> </td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>    
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;"><%=asBean.getAsTypeName()%></td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;"><%=asBean.getasIntervalName()%></td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;"><%=arrReceiverList.size()+"명"%></td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;">&nbsp;</td>
    <td height="26" align="center"  style="padding: 5px 0px 0px 0px;"><%=asBean.getLastSendDate()%></td>
  </tr>
<%
			if(i!=arrAlimiSetList.size()-1)
			{
%>
  <tr>
    <td colspan="13"  background="../images2/admin_table_dot.gif"><img src="../images2/brank.gif" width="1" height="1"></td>
  </tr>
<%
			}
		}
	}else{
%>
  <tr>
    <td colspan="13" >&nbsp;</td>
  </tr>
  <tr>
    <td colspan="13" align="center">설정한  리스트가 없습니다.</td>
  </tr>
  <tr>
    <td colspan="13" >&nbsp;</td>
  </tr>
<%
	}
%>

  <tr>
	<td colspan="13" background="../images2/admin_table_bg01.gif" height="2"></td>
  </tr>
	
 
  <tr>
    <td height="46" colspan="13"><table width="780" border="0" cellspacing="0" cellpadding="0">      
      <tr>      
        <td colspan="2" align="center">	      
	        <table width="780" border="0" cellpadding="0" cellspacing="0">
		      <tr>
		        <td bgcolor="#F2F2F2"><table width="100%" border="0" cellspacing="3" cellpadding="3">
		          <tr>
		            <td height="25" align="center" bgcolor="#FFFFFF"><table width="250" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td align="center" class="t"><%=PageIndex.getPageIndex(iNowPage,iTotalPage,"","" )  %></td>
		              </tr>
		            </table></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table>
        </td>
      </tr>
    </table></td>
  </tr>  
  <tr>
    <td colspan="13">&nbsp;</td>
  </tr>
</table>
</body>
</html>
