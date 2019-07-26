<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.admin.alimi.AlimiLogMgr
              	,risk.admin.alimi.AlimiLogSuperBean
              	,risk.admin.alimi.AlimiSettingDao
              	,risk.mobile.AlimiSettingBean
				,risk.util.ParseRequest
				,java.util.ArrayList
				,java.util.*				
				" %>
<%@page import="risk.util.PageIndex"%>
<%
	
	ParseRequest pr = new ParseRequest(request);	
	pr.printParams();
	DateUtil du = new DateUtil();
	
	
	String searchKey = pr.getString("searchKey");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	//검색날짜 설정 : 기본 1일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}
	
	String mal_type = pr.getString("mal_type","1");
	String as_type = pr.getString("as_type","");
	String as_seq = pr.getString("as_seq","");
	
	int rowCnt = 10;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	
	AlimiLogMgr lMgr = new AlimiLogMgr();
	
	ArrayList reData = lMgr.getAlimiLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, as_type, as_seq);
	
	
	int iTotalPage      = lMgr.getFullCnt() / rowCnt;
    if ( ( lMgr.getFullCnt() % rowCnt ) > 0 ) iTotalPage++;
    
    AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = new AlimiSettingBean();
	ArrayList arrAlimiSetList = new ArrayList();
	arrAlimiSetList = asDao.getAlimiSttingList();
	
%>

<%@page import="risk.util.DateUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>POSCO</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script type="text/javascript">
	function pageClick( paramUrl ) {
	var f = document.getElementById('fSend');
    f.action = "alimi_log_list.jsp" + paramUrl;
    f.submit();
		/*
		var f = document.fSend;
		f.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		f.action='alimi_log_list.jsp';
		f.submit();
	*/
    }

	//Url 링크
 	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');
		chkPop++;
	}

	//검색
 	function search(){ 		
 		var f = document.fSend;
 				
		f.nowpage.value = '1';
 		f.action = 'alimi_log_list.jsp';
 		f.target = '';
 		f.submit(); 	
 	}
	
	function setExcel(){
		var f = document.fSend;
		f.target = 'ifrm';
		f.action = 'ex_alimi_log_list.jsp';
 		f.submit();
	}
	
	function setSMSExcel(){
		var f = document.fSend;
		f.target = 'ifrm';
		f.action = 'alimi_log_sns_cntList.jsp';
 		f.submit();
	}
</script>
</head>
<body>
<iframe id="ifrm" name="ifrm" style="display: none;"></iframe>
<form id="fSend" name=fSend method="post">
<input type="hidden" name="mode">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<!-- <input type="hidden" name="as_seq"> -->
<input type="hidden" name="as_seqs">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0918.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">알리미 로그관리</td>
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
			<tr style="display: none">
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/alimi/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
						<td><img src="../../../images/admin/alimi/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="820" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
				        <td width="793" background="../../../images/issue/table_bg_02.gif"><table width="790" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="17"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="62" class="b_text"><strong>검색단어 </strong></td>
				            <td width="323"><input type="text" class="textbox3" style="width:310px;" name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
				            <td width="16"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="60" class="b_text"><strong>검색기간</strong></td>
				            <td width="312"><table border="0" cellpadding="0" cellspacing="0">
				              <tr>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                <td width="11" align="center">~</td>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                <td width="75"><img src="../../../images/issue/search_btn.gif" width="61" height="22" hspace="5" align="absmiddle" onclick="search()" style="cursor:pointer"/></td>
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="#93a6b4"></td>
				        <td width="818" bgcolor="#e6eaed" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="7%" class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong>상     태</strong></td>
				                <td width="22%"><select name="mal_type" class="textbox3" style="width: 105px">
								                  <option value="">선택하세요</option>
								                  <option value="1" <%if(mal_type.equals("1")){out.print("selected");}%>>발송</option>
								                  <option value="0" <%if(mal_type.equals("0")){out.print("selected");}%>>실패</option>
								                  <option value="2" <%if(mal_type.equals("2")){out.print("selected");}%>>유사</option>
				                                </select></td>
				                <td width="10%" class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong>발송구분</strong></td>
				                <td width="22%"><select name="as_type" class="textbox3" style="width: 105px">
								                  <option value="">선택하세요</option>
								                  <option value="1" <%if(as_type.equals("1")){out.print("selected");}%>>이메일</option>
								                  <option value="2" <%if(as_type.equals("2")){out.print("selected");}%>>SMS</option>
								                  <option value="3" <%if(as_type.equals("3")){out.print("selected");}%>>R-rimi</option>
				                                </select></td>
				                <td width="12%" class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong>알리미설정</strong></td>
				                <td width="*"><select name="as_seq" class="textbox3" style="width: 105px">
					                	<option value="">선택하세요</option>
					                	<%
					                	String tmp[] = new String[2];
				                		if(arrAlimiSetList.size() > 0){
				                			for(int i =0; i < arrAlimiSetList.size(); i ++){
				                				tmp = (String[])arrAlimiSetList.get(i);
					                	%>
									    <option value="<%=tmp[0]%>" <%if(as_seq.equals(tmp[0])){out.print("selected");}%>><%=tmp[1]%></option>
									    <%} }%>
									    
				                    </select></td>                
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="1" bgcolor="#93a6b4"></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="1" bgcolor="#93a6b4"></td>
				  </tr>
				</table>
				</td>
			</tr>
			<tr height="30px">
				<td style="text-align: right;vertical-align: middle;">
					<!-- 특정 아이디만 사용할 수 있도록 제한  시작-->
					<%if(SS_M_ID.equals("devel") || SS_M_ID.equals("bizdev")){ %><span style="cursor: pointer;vertical-align: middle;" onclick="setSMSExcel();">SMS 발송량 내려받기</span><%} %>
					<!-- 특정 아이디만 사용할 수 있도록 제한  끝-->
					<img alt="엑셀다운로드" src="../../../images/admin/site/excel_save.gif" style="cursor: pointer;" onclick="setExcel();">
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;width: 820px;">
					<colgroup>
						<col width="131"/><col width="49"/><col width="477"/><col width="32"/><col width="49"/><col width="82"/>
					</colgroup>
					<tr>
						<th>제 목</th>
						<th>수신자</th>
						<th>내     용</th>
						<th></th>
						<th>상 태</th>
						<th>발송시간</th>
					</tr>
<%
	
	if(reData.size()>0){
		AlimiLogSuperBean.AlimiLogList abean = null;
		String status = "";
		String source = "";
	  	for(int i=0; i < reData.size(); i++)
		{
	  		abean = 	(AlimiLogSuperBean.AlimiLogList)reData.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		
	  		if(abean.getMal_type().equals("1")){
	  			status = "발송";
	  		}else if(abean.getMal_type().equals("0")){
	  			status = "실패";
	  		}else if(abean.getMal_type().equals("2")){
	  			status = "유사";
	  		}else{
	  			status = "";
	  		}
	  		if("1".equals(abean.getAs_type())){
	  			source = "ico_email.gif";
	  		}else if("2".equals(abean.getAs_type())){
	  			source = "ico_sms.gif";
	  		}else if("3".equals(abean.getAs_type())){
	  			source = "ico_Rrimi.gif";
	  		}else{
	  			source = "";
	  		}
	  		
%>
				<tr>
					<td><%=abean.getAs_title()%></td>
					<td><%=abean.getAs_cnt()+"명"%></td>
					<td style="text-align: left;">
						<div style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:470px;">
							<a onClick="hrefPop('<%=abean.getMt_url()%>');" href="javascript:void(0);"><%=abean.getSend_message()%></a>
						</div>
					</td>
					<td>
					<%	if(!source.equals("")){	%>
						<img src="<%=SS_URL%>images/admin/alimi/<%=source%>" >
					<%} %>
					</td>
					<td style=""><%=status%></td>
					<td><%= du.getDate(abean.getMal_send_date(), "MM-dd HH:mm")%></td>
				</tr>
<%
		}
	}else{
%>
				<tr>
					<td colspan="7" width="820" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
				</tr>
<%		
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
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
</table>
</form>		            
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
</body>
</html>