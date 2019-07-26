<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.issue.IssueMgr
				 ,risk.issue.IssueDataBean
				 ,risk.issue.IssueCodeBean
				 ,risk.issue.IssueCodeMgr
				 ,risk.issue.IssueCommentBean
				 ,risk.util.StringUtil
                 ,risk.util.DateUtil
                 ,risk.util.ParseRequest
                 ,risk.admin.member.MemberBean
                 ,risk.admin.member.MemberDao
                 ,risk.sms.AddressBookDao
               	 ,risk.sms.AddressBookGroupBean
               	 ,risk.admin.membergroup.membergroupBean              
               	 ,java.util.Iterator
               	 ,java.util.List                    
                 ,java.util.ArrayList"                  
%>
<%@ include file="../inc/sessioncheck.jsp" %>			 
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowPage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = pr.getString("searchKey","");
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String srtMsg = null;
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
		
	//관련정보 리스트
	IssueDataBean idBean = null;
	arrIdBean = issueMgr.getIssueDataList(nowPage,pageCnt,check_no,i_seq,it_seq,"","","1",sDateFrom,"",sDateTo,"",typeCode,"","Y","","Y","","");
	
	//시스템 멤버 그룹 
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
	Iterator it = abgGroupList.iterator();	
		
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
	////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////

	////////////////////수신자 리스트 //////////////////////
	$(document).ready(pageInit);
	
	function pageInit()
	{
		loadList1();
		loadList2();
	}
	
	function loadList1()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');		
	}
	
	function findList1()
	{
		var f = document.report_receiver;
		var f2 = document.report_selectedReceiver;
	
		f.selectedAbSeq.value = f2.selectedAbSeq.value;		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');			
	}	
	/////////////////////////////////////////////////////	
	///////////////////선택된 수신자 리스트/////////////////						
	function selectedListCheck(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var check = true;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');
	
		for(var i =0; i<list.length; i++)
		{
			if(list[i]==ab_seq)
			{
				check = false;
				break;
			}
		}
		return check;
	}	
	
	function selectRightMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
	
		if(!selectedListCheck(ab_seq)){alert('이미 선택된 수신자 입니다.');	return;}	
		
		if(f.selectedAbSeq.value!='')
		{
			f.selectedAbSeq.value += ","+ ab_seq;
		}else{
			f.selectedAbSeq.value = ab_seq;
		}
		findList1(); 		
		findList2();
	}
	
	function addReceiver() 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=add','adduser', 'width=800,height=500,scrollbars=no');
	}
	
	
	function editReceiver(ab_seq) 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=edit&abSeq='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');
	}
	
	function delReceiver(ab_seq) 
	{
		if(window.confirm("삭제하시겠습니까?"))
		{
	 		window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_prc.jsp?mode=del&seqList='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');
		}else{
			return;
		}
	}
	
	function delAbSeq(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');
	
		f.selectedAbSeq.value = '';
		for(var i =0; i<list.length; i++)
		{
			if(list[i]!=ab_seq)
			{				
				if(f.selectedAbSeq.value!='')
				{
					f.selectedAbSeq.value += ","+ list[i];
				}else{
					f.selectedAbSeq.value = list[i];
				} 
			}
		}
	}
	
	function selectLeftMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
		delAbSeq(ab_seq);
		findList1();		
		findList2();
	}	
	
	function loadList2()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');		
	}
	
	function findList2()
	{		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');					
	}				
	//////////////////////////////////////////////////////

	
 	//보고서 발송
	function sendReport(mode){
		var f = document.fSend;
		if(document.getElementById('receiverTool').style.display=='none'){
			document.getElementById('receiverTool').style.display ='';
		}else{
			f.mailreceiver.value = document.report_selectedReceiver.selectedAbSeq.value;
			if(f.mailreceiver.value.length==0){alert('수신자를 선택해주세요'); return;}


			document.getElementById("sending").style.display = '';
			
			f.mode.value = mode;
			f.target = 'processFrm';
			f.action = 'issue_data_prc.jsp';
			f.submit();		

		}		
		
	}

	function loding(){
		 var imgObj = document.getElementById("sending");
		 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
		 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
		 //imgObj.style.display = 'none'; 
	}
//-->
</script>
</head>
<body onclick="loding();">
<img id="sending" src="../../images/issue/sending.gif" style="position: absolute; display: none;" >
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head" style="line-height: 1 !important">
			<p>메일발송</p>
			<span><a href="javascript:close();"><img src="../../images/issue/pop_tit_close.gif"></a></span>
		</td>
	</tr>
	<tr>
		<td><span class="sub_tit">관련 정보</span></td>
	</tr>
	<tr>
		<td>
		<!-- 게시판 시작 -->
			<form name="fSend" id="fSend" action="" method="post">
			<input type="hidden" name="mode" >
			<input type="hidden" name="id_seq" value="<%=check_no%>">
			<input type="hidden" name="ir_type">
			<input type="hidden" name="mailreceiver" value="">
			<iframe id="processFrm" name ="processFrm" width="0" height="0" ></iframe>
			<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<col width="15%"><col width="50%"><col width="5%"><col width="15%"><col width="5%"><col width="10%">
					<tr>
						<th>출처</th>
						<th>제목</th>
						<th></th>
						<th>수집일시</th>
						<th>성향</th>
						<th>중요도</th>
					</tr>
<%
		String sunghyang = "";
		String important = "";
		String gubun = "";
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (IssueDataBean)arrIdBean.get(i);
				arrIdcBean = (ArrayList) idBean.getArrCodeList();
				
				sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
				important = icMgr.GetCodeNameType(arrIdcBean,10);
				gubun = icMgr.GetCodeNameType(arrIdcBean,1);
%>
					<tr>
						<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></p></td>
						<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=idBean.getId_title()%>"><a href="javascript:PopIssueDataForm('<%=idBean.getMd_seq()%>');"><%=idBean.getId_title()%></a></p><input type="hidden" name="ir_title" value="<%=idBean.getId_title()%>"></td>
						<td><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a></td>
						<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
						<td><p class="tendency_0<%if(sunghyang.equals("긍정")){out.print("1");}else if(sunghyang.equals("중립")){out.print("2");}else{out.print("3");}%>"><%=sunghyang%></p></td>
						<td>
							<dl>
								<dt>
<%
				if(important.equals("상")){
%>
									<dd><img src="../../images/issue/statusbar_03.gif" /></dd><dd><img src="../../images/issue/statusbar_03.gif" /></dd><dd><img src="../../images/issue/statusbar_03.gif" /></dd>
<%					
				}else if(important.equals("중")){
%>
									<dd><img src="../../images/issue/statusbar_03.gif" /></dd><dd><img src="../../images/issue/statusbar_03.gif" /></dd><dd><img src="../../images/issue/statusbar_01.gif" /></dd>
<%					
				}else{
%>
									<dd><img src="../../images/issue/statusbar_03.gif" /></dd><dd><img src="../../images/issue/statusbar_01.gif" /></dd><dd><img src="../../images/issue/statusbar_01.gif" /></dd>
<%					
				}
%>
								</dt>
							</dl>
						</td>
					</tr>
<%
			}
		}
%>
			</table>
			</form>
		<!-- 게시판 끝 -->
		</td>
	</tr>
	<tr>
		<td style="text-align:center;"><img src="../../images/issue/btn_mailsend.gif" onclick="sendReport('mail');" style="cursor:pointer"></td>
	</tr>
	<!---------------->
	<tr>
		<td>
		<table style="width:100%;height:350px;" border="0" cellpadding="0" cellspacing="0" id="receiverTool" style="display:none;">
			<tr>
				<td colspan="3" style="padding:3px 0px 3px 0px"><span class="sub_tit">메일 발송 설정</span></td>
			</tr>
			<tr>
				<td style="width:340px;">
				<form name="report_receiver" id="report_receiver">
				<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
				<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>메일 수신자 그룹</th>
					</tr>
					<tr>
						<td style="padding:10px;" valign="top">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="pop_mail_group_top">그룹선택</td>
								<td style="text-align:right;" class="pop_mail_group_top2">
									<select name="ag_seq" onchange="findList1();">
										<option value="" selected>전체 수신자 그룹</option>
<%
	while(it.hasNext()){
		abgBean = new AddressBookGroupBean();
		abgBean = (AddressBookGroupBean)it.next();
%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
<%
	}
%>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2"><div id="receiverList"></div></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</form>
				</td>
				<td style="text-align:center;"><!--<img src="../../images/issue/btn_newadd.gif" />--></td>
				<td style="width:340px;">
				<form name="report_selectedReceiver" id="report_selectedReceiver">
				<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
				<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>메일 대상자 그룹</th>
					</tr>
					<tr>
						<td style="padding:10px;" valign="top">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><div id="selectedList"></div></td>
							</tr>
							<%--
							<tr>
								<td class="pop_mail_group_td">임승철 <span style="font-weight:bold;color:#1886f7;">devel</span></td>
								<td style="text-align:right;" class="pop_mail_group_td"><img src="../../images/common/btn_cancel.gif" /></td>
							</tr>
							<tr>
								<td class="pop_mail_group_td">임승철 <span style="font-weight:bold;color:#1886f7;">devel</span></td>
								<td style="text-align:right;" class="pop_mail_group_td"><img src="../../images/common/btn_cancel.gif" /></td>
							</tr>
							--%>
						</table>
						</td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
		</table>

		</td>
	</tr>
</table>
</body>
</html>