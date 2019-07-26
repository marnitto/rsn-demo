<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;	
	//pr.printParams();
	
	int abSeq        = pr.getInt("abSeq",0);
	String mode = pr.getString("mode","");
	
	List KGlist = null;
	List sglist = null;
	
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();
	SiteMng sitemng = new SiteMng();
	sglist = sitemng.getSGList();
	
	classificationMgr cm = new classificationMgr();
    List cmList = null;
    
    cmList = cm.GetDetailList( 4, 0);
	
	
	String abName = "";
	String abDept = "";
	String abPos = "";
	String abMobile = "";
	String abMail = "";
	String abK_xp = "";
	String abSg_seq = "";
	String abMt_type = "";
	String abIssue_dept = "";
	String abSms_receiverchk = "";
	String abIssue_receivechk = "";
	String abReport_daychk = "";
	String abReport_weekchk = "";
	String abReport_monthchk = "";
	
	String smsCheck = "";
	String reportDayCheck = "";
	String reportWeekCheck = "";
	String reportMonthCheck = "";
	String ab_sms_limit="";
	
	if( abSeq > 0 && mode.equals("edit")) {
		AddBean = AddDao.Getaddress(abSeq);
		
		abName = AddBean.getMab_name();
		abDept = AddBean.getMab_dept();
		abPos = AddBean.getMab_pos();
		abMobile = AddBean.getMab_mobile();
		abMail = AddBean.getMab_mail();
		smsCheck = AddBean.getMab_sms_receivechk();
		
		abK_xp = AddBean.getMk_xp();
		abSg_seq = AddBean.getMsg_seq();
		abMt_type = AddBean.getMmt_type();
		abIssue_dept = AddBean.getMab_issue_dept();
		abSms_receiverchk = AddBean.getMab_sms_receivechk();
		abIssue_receivechk = AddBean.getMab_issue_receivechk();
		abReport_daychk = AddBean.getMab_report_day_chk();
		abReport_weekchk = AddBean.getMab_report_week_chk();		
		ab_sms_limit = AddBean.getMab_sms_limit();


		if( abSms_receiverchk.equals("1") ) {
			smsCheck = " checked";
		}
		if( abReport_daychk.equals("1") ) {
			reportDayCheck = " checked";
		}
		if( abReport_weekchk.equals("1") ) {
			reportWeekCheck = " checked";
		}
		
	}
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function checkAllmg_kg(chk) {
	var o=document.all.mg_kgTemp;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
	}
}

function checkAllmg_site(chk) {
	var o=document.all.mg_siteTemp;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
	}
}	
		
//-->
</SCRIPT>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="" >
<form name="address_book" action="receiver_prc.jsp" method="post"></form>
<form name="alimi_detail" action="" method="post">
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="../images2/top_title_bg.gif"><img src="../images2/admin_toptitle02.gif" width="137" height="42"></td>
    <td align="right" background="../images2/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">SMS알리미, Online Report를 수신할 대상 및 수신조건을 관리합니다.</td>
  </tr>
  <tr>
    <td colspan="2" height="15"></td>
  </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;">
	    <table width="750" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="15"><img src="../images2/ico_won_blue.gif" width="11" height="11"></td>
	              <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>기본 설정</strong></td>
	            </tr>
	        </table></td>
	      </tr>
	      <tr>
		     <td colspan="2" height="2">&nbsp;</td>
		  </tr>
	      <tr>
	        <td colspan="2" bgcolor="#9CBBE5" height="3"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목</strong></td>
		  	<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_title" type="text" class="txtbox" size="50"></td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3" width="1" height="1"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송매체</strong></td>
		  	<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_type" type="radio" value="1" checked>MAIL <input name="as_type" type="radio" value="2">SMS</td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3" width="1" height="1"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보 수신 여부</strong></td>
		  	<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_chk" type="radio" value="1" checked>수신 <input name="as_chk" type="radio" value="2">수신 안함</td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3" width="1" height="1"></td>
	      </tr>
	      <tr>
	        <td width="120" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송조건</strong></td>
	        <td><table width="630" border="0" cellspacing="1" cellpadding="0">
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>키워드그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;"><table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="" id="mg_kgAll" onclick="checkAllmg_kg(this.checked);"> 전체</td>
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
			          
							  <%
									int intUubun = 4;
									String[] xp = abK_xp.split(",");						
									for( int i=0 ; i < KGlist.size() ; i++ ) {
										keywordInfo KGset = (keywordInfo)KGlist.get(i);
			
										if(i == 0) out.println("<tr height=22>");
			
										if(i>0)
											if((i%intUubun)==0) out.println("<tr height=22>");
										
									%>	<td width="<%=600/intUubun%>">
										<input type="checkbox" name="mg_kgTemp" value="<%=KGset.getK_Xp()%>" 
											<%for(int j=0; j < xp.length; j++){
												if( xp[j].equals(KGset.getK_Xp())){
													out.println("checked");
												}
											}
											%>><%=KGset.getK_Value()%></td><%
			
										if((i%intUubun)==(intUubun-1)) out.println("</tr>");
									}
			
									if((KGlist.size() % intUubun) > 0){
										out.println("<td colspan='"+ KGlist.size() % intUubun+"'></td></tr>");
									}
							%>		
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td colspan="2" bgcolor="#D3D3D3" width="1" height="1"></td></tr>
			    <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>관심 사이트</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;">
						<table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="" id="mg_siteAll" onclick="checkAllmg_site(this.checked);">전체
								</td>
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
							  <%
								String[] site = abSg_seq.split(",");
			
								intUubun = 4;
								for( int i=0 ; i < sglist.size() ; i++ ) {
									SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
			
									if(i == 0) out.println("<tr height=22>");
			
										if(i>0)
											if((i%intUubun)==0) out.println("<tr height=22>");
										
									%>	<td width="<%=600/intUubun%>"><input type="checkbox" name="mg_siteTemp" value="<%=SGinfo.get_seq()%>" 
									<%
										  for( int j=0; j < site.length; j++){
											  if(site[j].equals(Integer.toString(SGinfo.get_seq()))){ 
												  out.println("checked");
											  }else{out.println("");}
										  }					  
								  
								  %>><%= SGinfo.get_name()%></td><%				
									if((i%intUubun)==(intUubun-1)) out.println("</tr>");				  
								}
			
								if((sglist.size() % intUubun) > 0){
									out.println("<td colspan='"+ sglist.size() % intUubun+"'></td></tr>");
								}
			
							  %>
							  		</table>
								</td>
							</tr>
						</table></td>
			      </tr>
			      <tr>
			        <td colspan="2" bgcolor="#D3D3D3" width="1" height="1"></td>
			      </tr>
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보유형</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;"><table width="500" border="0" cellspacing="0" cellpadding="0">
			          <!--<tr>
			            <td height="20"><input type="checkbox" name="mtType" value="">
			      전체게시물</td>
			          </tr>
			          --><% String[] mt = abMt_type.split(",");	%>
			          <tr>
			            <td height="20">
			            <input type="checkbox" name="" id="mtTypeAll" onclick="checkAllmtType(this.checked);">전체
			            
			            <input type="checkbox" name="mtTypeTemp" value="1"<% for(int i=0; i < mt.length; i++){ 
			            	if( mt[i].equals("1")) {out.println(" checked");}
			            }
			            	%>>언론사 정규기사&nbsp;&nbsp;
			      		<input type="checkbox" name="mtTypeTemp" value="2"<% for(int i=0; i < mt.length; i++){ 
			            	if( mt[i].equals("2")) {out.println(" checked");}
			            }
			            	%>>네티즌게시물(카페/블로그)&nbsp;&nbsp;
			      		<input type="checkbox" name="mtTypeTemp" value="3"<% for(int i=0; i < mt.length; i++){ 
			            	if( mt[i].equals("3")) {out.println(" checked");}
			            }
			            	%>>사이트공지</td>
			          </tr>
			        </table>
			     </td>
			   </tr>
      		</table>
      	</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#D3D3D3" width="1" height="2"></td>
      </tr>      
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;">
		<table width="750" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
		         <tr>
		           <td width="15"><img src="../images2/ico_won_blue.gif" width="11" height="11"></td>
		           <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>수신자 설정</strong></td>
		         </tr>
		     </table></td>
		   </tr>
		   <tr>
	         <td colspan="2" height="2">&nbsp;</td>
	       </tr>
		</table>
		<table width="750" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td width="55"><img src="images/btn_del.gif" width="55" height="24"  style="cursor:hand;" onclick="">&nbsp;</td>
		   	<td width="97"><img src="images/btn_receive_add.gif" width="97" height="24" hspace="5" onclick="" style="cursor:hand;">&nbsp;</td>
		   	<td width="457" align="right">수신자 <b><span id="">0</span></b>/<span id="">0 명 </span></td>   	
		   	<td width="156" align="right"><img src="images/btn_mailopen.gif" width="141" height="24" hspace="5" style="cursor:hand;" onclick="" title="메일수신자 불러오기 : 부서별 메일수신자를 불러옵니다."></td>
			</tr>
		</table>
		<table width="750" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td width="5" align="center" bgcolor="#F0F0F0" background="images/issue_table_bg02.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><input type="checkbox" name="checkall" id="ab_seqALL" onclick="checkAll(this.checked);chkReciver2();"></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="images/issue_table_bar02.gif" width="3" height="32"></td>
			  <td width="150" align="center" bgcolor="#F0F0F0" background="images/issue_table_bg02.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>성명</strong></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="images/issue_table_bar02.gif" width="3" height="32"></td>
			  <td width="268" align="center" bgcolor="#F0F0F0" background="images/issue_table_bg02.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>부서</strong></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="images/issue_table_bar02.gif" width="3" height="32"></td>
			  <td width="150" align="center" bgcolor="#F0F0F0" background="images/issue_table_bg02.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>메일주소</strong></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="images/issue_table_bar02.gif" width="3" height="32"></td>
			  <td width="165" align="center" bgcolor="#F0F0F0" background="images/issue_table_bg02.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>발송시간</strong></td>
			</tr>
		</table>
		<table width="730" border="0" cellspacing="0" cellpadding="0">
			<tr height="25" align="center">
			  <td width="5" style="padding: 3px 0px 0px 0px;"><input type="checkbox" name="ab_seq" id="ab_seq" value="" ></td>
			  <td width="3">&nbsp;</td>
			  <td width="150" style="padding: 3px 0px 0px 0px;"></td>
			  <td width="3">&nbsp;</td>
			  <td width="268" style="padding: 3px 0px 0px 0px;"><span style="text-overflow:ellipsis; overflow:hidden; width:250;white-space: nowrap;" title=""></span></td>
			  <td width="3">&nbsp;</td>
			  <td width="150" style="padding: 3px 0px 0px 0px;"><span style="text-overflow:ellipsis; overflow:hidden; width:130;white-space: nowrap;" title=""></span></td>
			  <td width="3">&nbsp;</td>
			  <td width="145" style="padding: 3px 0px 0px 0px;">&nbsp;</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
  <td height="20" colspan="2">&nbsp;</td>
</tr>
<tr align="center">
  <td height="50" colspan="2"><img src="../images2/btn_save.gif" width="55" height="24" hspace="5" onclick="addReceiver();" style="cursor:hand;"><img src="../images2/btn_cancel.gif" width="55" height="24" hspace="5" onclick="cancel();" style="cursor:hand;"></td>
</tr>
</table>
</body>
</html>
