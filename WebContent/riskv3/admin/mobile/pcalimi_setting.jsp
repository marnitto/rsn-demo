<%@ page contentType = "text/html; charset=euc-kr"  pageEncoding="EUC-KR"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,risk.admin.alimi.AlimiSettingDao
				 	,java.util.List
				 	,java.util.Hashtable
					" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	
	pr.printParams();
	
	// 키워드 그룹
	List KGlist = null;
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();
	
	// 사이트 그룹
	List sglist = null;
	SiteMng sitemng = new SiteMng();
	sglist = sitemng.getSGList();
	
	
	AlimiSettingDao asDao = new AlimiSettingDao();
	String mode = pr.getString("mode","");
	String script = "";
	
	//저장
	if(mode.equals("save")){
		
		Hashtable htInput = new Hashtable();
		
		htInput.put("K_XP", pr.getString("K_XP", ""));
		htInput.put("SG_SEQ", pr.getString("SG_SEQ", ""));
		htInput.put("MT_TYPE", pr.getString("MT_TYPE", ""));
		htInput.put("MA_COUNT", pr.getString("MA_COUNT", ""));
		
		int result = asDao.setMobileAlimiSetting(pr.getString("MA_TYPE",""), htInput);
		
		if(result > 0){
			script = "alert('설정이 저장되었습니다.' );";
		}else{
			script = "alert('저장 실패 하였습니다.' );";
		}
	}
	
	
	// 세팅 정보 가져오기 Type = 1 pc alimi setting
	Hashtable htAlimiSetting = asDao.getMobileAlimiSetting("1");
	
	String MA_SEQ = "";
	String MA_ACTIVE = "";
	String K_XP = "";
	String SG_SEQ = "";
	String MT_TYPE = "";
	String MA_COUNT = "";
	String MA_TYPE = "";
	
	MA_SEQ = (String)htAlimiSetting.get("MA_SEQ");	
	MA_ACTIVE = (String)htAlimiSetting.get("MA_ACTIVE");	
	K_XP = (String)htAlimiSetting.get("K_XP");	
	SG_SEQ = (String)htAlimiSetting.get("SG_SEQ");	
	MT_TYPE = (String)htAlimiSetting.get("MT_TYPE");	
	MA_COUNT = (String)htAlimiSetting.get("MA_COUNT");	
	MA_TYPE = (String)htAlimiSetting.get("MA_TYPE");
	
	String[] arrMtType = MT_TYPE.split(",");
	
	/*
	System.out.println("MA_SEQ:"+MA_SEQ);
	System.out.println("MA_ACTIVE:"+MA_ACTIVE);
	System.out.println("K_XP:"+K_XP);
	System.out.println("SG_SEQ:"+SG_SEQ);
	System.out.println("MT_TYPE:"+MT_TYPE);
	System.out.println("MA_COUNT:"+MA_COUNT);
	System.out.println("MA_TYPE:"+MA_TYPE);
	*/
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">


<SCRIPT LANGUAGE="JavaScript">
	<%=script%>
	
	function checkAllKgSeq(chk) {
		var o=document.all.k_xp;
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					o(i).checked = chk;
				}
			}else{ o.checked = chk; }
		}
	}
	
	function checkAllSgSeq(chk) {
		var o=document.all.sg_seq;
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					o(i).checked = chk;
				}
			}else{ o.checked = chk; }
		}
	}
	
	function checkAllMtTypeSeq(chk) {
		var o=document.all.mt_type;
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					o(i).checked = chk;
				}
			}else{ o.checked = chk; }
		}
	}

	function save(){
		
		var o = null;
		var length = null;

		var saveKey = '';
		var saveSite = '';
		var saveMtType = '';
		var saveMaCount = '';
		
		//키워드	그룹 	
		o=document.all.k_xp;
		length = 0; 
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					if(o(i).checked == true)
					{
						if(length >= 1){
							saveKey = saveKey + ',' + o(i).value;
						}else{
							saveKey = saveKey + o(i).value;		
						}
						
						length = length + 1;
					}
				}
			}
		}
		if(length == 0){alert('키워드 그룹을 선택해 주세요'); return;}

		//사이트 그룹
		o = null;
		lenhth = null;
			
		o=document.all.sg_seq;
		length = 0; 
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					if(o(i).checked == true)
					{
						if(length >= 1){
							saveSite = saveSite + ',' + o(i).value;
						}else{
							saveSite = saveSite + o(i).value;		
						}
						
						length = length + 1;
					}
				}
			}
		}
		if(length == 0){alert('사이트 그룹을 선택해 주세요'); return;}

		//정보유형
		o = null;
		lenhth = null;
			
		o=document.all.mt_type;
		length = 0; 
		if(o){
			if(o.length){
				for(i=0; i<o.length; i++) {
					if(o(i).checked == true)
					{
						if(length >= 1){
							saveMtType = saveMtType + ',' + o(i).value;
						}else{
							saveMtType = saveMtType + o(i).value;		
						}
						
						length = length + 1;
					}
				}
			}
		}
		if(length == 0){alert('정보유형 그룹을 선택해 주세요'); return;}

		//정보갯수
		saveMaCount = mobileSend.ma_count.value;

		document.all.K_XP.value = saveKey;
		document.all.SG_SEQ.value = saveSite;
		document.all.MT_TYPE.value = saveMtType;
		document.all.MA_COUNT.value = saveMaCount;
		document.all.mode.value = 'save';
		
		document.mobileSend.action = 'pcalimi_setting.jsp';
		document.mobileSend.target = '';
		document.mobileSend.submit();
		
	}

</SCRIPT>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0"  >
<form name="mobileSend" method="post">
<input type="hidden" name="MA_SEQ" value="<%=MA_SEQ%>">
<input type="hidden" name="MA_TYPE" value="<%=MA_TYPE%>">
<input type="hidden" name="K_XP">
<input type="hidden" name="SG_SEQ">
<input type="hidden" name="MT_TYPE">
<input type="hidden" name="MA_COUNT">
<input type="hidden" name="mode" value="">



<table width="780" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td background="../images2/top_title_bg.gif"><img src="../images/admin_title_09.gif" ></td>
    <td align="right" background="../images2/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">PC 알리미 조건을 관리합니다.</td>
  </tr>
  <tr>
    <td colspan="2"><img src="../images2/brank.gif" width="1" height="15"></td>
  </tr>
</table>
<%
		
%>
 
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
	        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
	      </tr>
	      
	      <tr>
	        <td width="120" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>조건</strong></td>
	        
	        <td>
	      	  <table id="mailFilter"  width="630" border="0" cellspacing="1" cellpadding="0">     
			      
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>키워드그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;">
					
						<table width="510" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="k_xpALL" onclick="checkAllKgSeq(this.checked);"> <B>전체</B></td>
			
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
									<tr>
									<Td>
									<%
									for(int i=0 ; i < KGlist.size() ; i++ ) {
										keywordInfo KGset = (keywordInfo)KGlist.get(i);
									%>
									<span style="width:100;"><input type="checkbox" name="k_xp" value="<%=KGset.getK_Xp()%>" 
										<%  
										  String[] arrKeyCode = K_XP.split(",");
									
											for(int j = 0; j < arrKeyCode.length; j++)
											{
												if(KGset.getK_Xp().equals(arrKeyCode[j])){
													out.println("checked");
												}else{
													out.println("");		
												}
											}
										%>
										
										><%=KGset.getK_Value()%></span>
									<%
									}
									%>
									</Td>
									<tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				 <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>사이트그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;">
					
					
						<table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="sgSeqALL" value ="0" onclick="checkAllSgSeq(this.checked);"><B>전체</B>
								</td>
							</tr>
							
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
									<tr>
									<Td>
									<%
									for(int i=0 ; i < sglist.size() ; i++ ) {
										SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
									%>
									<span style="width:100;"><input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>"
										<%
											String[] arrSiteCode = SG_SEQ.split(",");
										
											for(int j = 0; j < arrSiteCode.length; j++)
											{
												if(Integer.toString(SGinfo.get_seq()).equals(arrSiteCode[j])){
													out.println("checked");
												}else{
													out.println("");		
												}
											}
										%>
									><%=SGinfo.get_name()%></span>
									<%
									}
									%>
									</Td>
									<tr>
									</table>
								</td>
							</tr>

						</table></td>
			      </tr>
			      <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>
				 
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보유형</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;"><table width="500" border="0" cellspacing="0" cellpadding="0">
			          <!--<tr>
			            <td height="20"><input type="checkbox" name="mtType" value="">
			      전체게시물</td>
			          </tr>
			          -->	
			          
			          <tr>
			        	<td width="510" height="25">
			          		<input type="checkbox" name="mt_type_All" value="0" onclick="checkAllMtTypeSeq(this.checked);"><B>전체</B>
			          	</td>
			          </tr>     
			          <tr>
			            <td height="20">
		            	  <input type="checkbox" name="mt_type" value="1" 
		            	  	<%
		            	  		for(int i = 0; i < arrMtType.length; i++)
		            	  		{
		            	  			if(arrMtType[i].equals("1"))
		            	  			{
		            	  				out.println("checked");	
		            	  			}
		            	  		}
		            	  	%> >기사&nbsp;&nbsp;
			      		  <input type="checkbox" name="mt_type" value="2" 
			      		  	<%
			      				for(int i = 0; i < arrMtType.length; i++)
	            	  			{
	            	  				if(arrMtType[i].equals("2"))
	            	  				{
	            	  					out.println("checked");	
	            	  				}
	            	  			}
			      		  	%> >게시물&nbsp;&nbsp;		       
			      		  <input type="checkbox" name="mt_type" value="3" 
			      		  	<%
			      				for(int i = 0; i < arrMtType.length; i++)
          	  					{
          	  						if(arrMtType[i].equals("3"))
          	  						{
          	  							out.println("checked");	
          	  						}
          	  					}
			      		  	%> >공지		       
			      	  	</td>
			      		
			          </tr>
			        </table>			        
			     </td>
			   </tr>
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보갯수</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;"><table width="500" border="0" cellspacing="0" cellpadding="0">
			          <!--<tr>
			            <td height="20"><input type="checkbox" name="mtType" value="">
			      전체게시물</td>
			          </tr>
			          -->			        
			          <tr>
			            <td height="20">
			            <select name="ma_count">
			            <%
			            
			            String selected = "";
			            for(int i=10; i<201; i+=10){
			            	/*
			            	selected = "";
			            	if(pasBean.getPa_count()==i){
			            		selected = "selected";
			            	}
			            	*/
			            %>
			            	<option value="<%=i%>" <%if(MA_COUNT.equals(Integer.toString(i)))out.println("selected");%>><%=i%>건</option>
		            	<%
			            }
		            	%>
			            </select>
			      	  	</td>
			      		
			          </tr>
			        </table>			        
			     </td>
			   </tr>
      		</table>
      	</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
      </tr>      
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>

</table>

<%
	//}
%>
<table width="780" border="0" cellspacing="0" cellpadding="0">
	<tr height="40" align="center">
	  <td valign="middle"><img src="../images2/btn_save.gif" width="55" height="24" hspace="5" onclick="save();" style="cursor:hand;"><img src="../images2/btn_cancel.gif" width="55" height="24" hspace="5" onclick="history.back();" style="cursor:hand;"></td>
	</tr>
</table>
</form>
</body>
</html>
