<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean							
				,risk.util.PageIndex
				,risk.admin.log.LogBean
				,risk.sms.*
				,java.util.List
				,risk.sms.AddressBookDao
               	,risk.sms.AddressBookGroupBean
               	,risk.admin.membergroup.membergroupBean              
               	,java.util.Iterator
				"%>  				 
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ArrayList arrIrBean = new ArrayList();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	LogBean logBean = new LogBean();
	
	String ir_type = pr.getString("ir_type");
	irBean = irMgr.getReportBean(pr.getString("ir_seq"));// 리포트 정보
	logBean = irMgr.getIssueReportLogBean(pr.getString("ir_seq")); // 리포트 로그 정보
	
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
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script type="text/javascript">
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

	//리스트 목록
	function goList(ir_type){
 		var f = document.fSend;
 		f.ir_type.value = ir_type;
 		f.action="issue_report_list.jsp";
		f.target='';
		f.submit(); 	
	}
	
	//저장된 보고서 보기 페이지를 띄운다
 	function showReport(ir_seq){
	 	var f = document.fSend;		
		f.ir_seq.value = ir_seq;
		popup.openByPost('fSend','pop_report.jsp',800,945,false,true,false,'goReportView');		
 	}

 	//보고서 수정
 	function showReportEditor(ir_seq, reportType){
 	 	
	 	var f = document.fSend;
	 	f.mode.value = 'update';		
		f.ir_seq.value = ir_seq;	

		window.open('about:blank', 'PopUp', 'width=820,height=1000,scrollbars=yes,status=yes');		
		//f.mode.value = 'view';
		f.action = 'pop_report_editform.jsp?reportType=' + reportType;
		f.target = 'PopUp';
		f.submit();	
 	}

 	//보고서 재발송
	function sendReport(mode){
		var f = document.fSend;
		if(document.getElementById('receiverTool').style.display=='none'){
			document.getElementById('receiverTool').style.display ='';
		}else{
			f.mailreceiver.value = document.report_selectedReceiver.selectedAbSeq.value;
			if(f.mailreceiver.value.length==0){alert('수신자를 선택해주세요'); return;}

			var smsChk = document.getElementsByName("smsChk");

			var streamSms ="";
			if(smsChk){
				if(smsChk.length){
					for(var i = 0; i < smsChk.length; i++){
						if(smsChk[i].checked){
							if(streamSms == ""){
								streamSms = smsChk[i].value; 
							}else{
								streamSms += "," + smsChk[i].value;
							}
						}
					}  
				}else{
					if(smsChk.checked){
						streamSms = smsChk.value; 
					}
				}
			}
			
			f.smsreceiver.value = streamSms;

			//센딩이미지 처리
			
			var imgObj = document.getElementById("sending");
			var sendBtn = document.getElementById("sendBtn");
			imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);			
			rect = sendBtn.getBoundingClientRect();
			imgObj.style.top = rect.top + document.body.scrollTop - 50;			  
			//imgObj.style.left = rect.left + 190;
			imgObj.style.left = rect.left - 80;
			imgObj.style.display = '';

			
			f.mode.value = mode;
			f.target = 'processFrm';
			f.action = 'issue_report_prc.jsp';
			f.submit();		
			
		}			
	}

</script>
</head>
<body>
<img  id="sending" src="../../images/report/sending.gif" style="position: absolute; display: none;" >
<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table style="width:100%;padding-left:20px;" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="body_bg_top" valign="top">
					<form name="fSend" id="fSend" action="" method="post">
					<iframe id="processFrm" name ="processFrm" width="0" height="0" ></iframe>
					<input type="hidden" name="mode">
					<input type="hidden" name="delseq">
					<input type="hidden" name="ir_seq" value="<%=pr.getString("ir_seq")%>">
					<input type="hidden" name="ir_type" value="<%=ir_type %>">
					<input type="hidden" name="mailreceiver" value="">   
					<input type="hidden" name="smsreceiver" value="">   
					<table align="left" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
					<!-- 타이틀 시작 -->
						<tr>
							<td class="tit_bg" style="height:37px;padding-top:0px;">
							<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="../../images/report/tit_icon.gif" />
			<%
				if(ir_type.equals("E")){out.print("<img src=\"../../images/report/tit_0302.gif\" />");}
				else{out.print("<img src=\"../../images/report/tit_0304.gif\" />");}
			%>
									</td>
									<td align="right">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="navi_home">HOME</td>
											<td class="navi_arrow">보고서관리</td>
											<td class="navi_arrow2">
			<%
				if(ir_type.equals("E")){out.print("이슈보고서");}
				else{out.print("주간보고서");}
			%>
											</td>
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
						<tr>
							<td style="height:30px;"><span class="sub_tit">보고서 상세정보</span></td>
						</tr>
						<tr>
							<td>
							<table id="board_write" border="0" cellspacing="0" style="table-layout:fixed;">
							<col width="20%"><col width="80%">
								<tr>
									<th><span class="board_write_tit">제     목</span></th>
									<td><span style="width:500px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%=irBean.getIr_title()%>"><%=irBean.getIr_title()%></span>
									 <img src="../../images/report/btn_report.gif" style="cursor:pointer" onclick="showReport(<%=irBean.getIr_seq()%>);" hspace="2" />
									 <img src="../../images/report/btn_report_edit.gif" style="cursor:pointer" onclick="showReportEditor(<%=irBean.getIr_seq()%>,1);" />
									 <img src="../../images/report/btn_report_edit2.gif" style="cursor:pointer" onclick="showReportEditor(<%=irBean.getIr_seq()%>,2);" />
									 </td>
								</tr>
								<tr>
									<th><span class="board_write_tit">작성일</span></th>
									<td><%=irBean.getFormatIr_regdate("MM/dd HH:mm")%></td>
								</tr>
							</table>
							</td>
						</tr>
						<!-- 게시판 시작 -->
						<tr>
							<td style="padding-top:20px;height:50px;"><span class="sub_tit">메일수신자</span></td>
						</tr>
						<tr>
							<td>       
							<table id="board_01" border="0" cellpadding="0" cellspacing="0">
							<col width="17%"><col width="17%"><col width="40%"><col width="15%"><col width="11%">
								<tr>
									<th>성명</th>
									<th>부서</th>
									<th>메일주소</th>
									<th>발송시간</th>
									<th>수신확인여부</th>
								</tr>
			<% 
				ArrayList arrABBean;
				arrABBean =	logBean.getArrArrReceiver();
				AddressBookBean ABBean = null;
				String mailReceiver = "";
				String receipt = "";
				if (arrABBean!=null) {
					for(int i=0; i < arrABBean.size(); i++){
						ABBean  = new AddressBookBean();
						ABBean = (AddressBookBean)arrABBean.get(i);	
						mailReceiver += mailReceiver.equals("") ? ABBean.getMab_seq() : ","+ABBean.getMab_seq();
						
						
						receipt = "미확인";
						if(ABBean.getL_count() != null){
							if(Integer.parseInt(ABBean.getL_count()) > 0){
								receipt = "확인";
							}
						}
						
			%>
								<tr>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean.getMab_name()%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean.getMab_dept()%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><span class="mail"><%=ABBean.getMab_mail()%></span></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean.getFormatMab_send_date("yy/MM/dd HH:mm:ss")%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=receipt%></td>
								</tr>
			<%
					}
				}
			%>
							</table>
							</td>
						</tr>
						<!-- 게시판 끝 -->
						<tr>
							<td style="text-align:center;height:40px;"><img src="../../images/report/btn_list.gif" onclick="goList('<%=ir_type%>');" style="cursor:pointer"/><img id="sendBtn" src="../../images/report/btn_reportresend.gif" style="cursor:pointer;" onclick="sendReport('mail');"/></td>
						</tr>
					</table>
					</form> 
					</td>
				</tr>
				<tr>
					<td style="padding-left:20px">
					<table style="width:820px;height:350px;" border="0" cellpadding="0" cellspacing="0" id="receiverTool" style="display:none;">
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
			</td>
	</tr>
</table>
</body>
</html>