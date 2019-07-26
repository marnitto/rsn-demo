<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@page import="risk.issue.ReportTypeConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.issue.IssueReportMgr"%>
<%@ page import="risk.issue.IssueReportBean"%>						
<%@ page import="risk.util.PageIndex"%>
<%
	ParseRequest pr = new ParseRequest(request);
	ArrayList arrIrBean = new ArrayList();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	DateUtil du = new DateUtil();
	
	String title = "정보에 대한 보고서를 작성합니다.";
	
	int nowpage = 1;	
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	String ir_type = pr.getString("ir_type");
	
	nowpage = pr.getInt("nowpage",1);
	arrIrBean = irMgr.getIssueReportList(nowpage,pageCnt,"",ir_type,"","","");
	totalCnt = irMgr.getTotalCnt();
	
	//페이징 처리
	totalPage = 0;	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script type="text/javascript">
 	function pageClick( paramUrl ) {
		//document.fSend.nowPage.value = paramUrl;
		document.fSend.action='issue_report_list.jsp' + paramUrl;
		document.fSend.target='';
		document.fSend.submit();
    }
 	
 	function checkAll(chk) {
 		/* var o=document.all.reportchk;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		} */
 		
 		if($("#reportChk").prop("checked")){
 			$("[name=reportchk]").prop("checked",true);
 		}else{
 			$("[name=reportchk]").prop("checked",false);
 		}
 	}
 	
 	function deleteReport() {
 		var o=document.fSend.reportchk;
 		var chkedseq="";
 		
 		$("[name=reportchk]:checked").each(function(){
 			if(chkedseq==""){
 				chkedseq = $(this).val();
 			}else{
 				chkedseq += ','+$(this).val();
 			}
 		});
 		
 		/* for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = o(i).value;
	 				}else{
	 					chkedseq = chkedseq+','+o(i).value;
	 				}
	 			}
 			}
 		} */
 		
		var f = document.fSend;
		if(chkedseq == ''){alert('선택된 정보가 없습니다.'); return;}
		f.mode.value = 'delete';
		f.delseq.value = chkedseq;
		f.action="issue_report_prc.jsp";
		f.target='processFrm';
		f.submit();
 	}

 	//보고서 정보 상세보기
 	function showDetail(ir_seq, ir_type){
 		var f = document.fSend; 
 		f.ir_seq.value = ir_seq;		
		f.action="issue_report_detail.jsp";
		f.target='';
		f.submit(); 	
 	}

 	//저장된 보고서 보기 페이지를 띄운다
 	function showReport(ir_seq){
	 	var f = document.fSend;
		f.mode.value = 'view';
		f.ir_seq.value = ir_seq;
		popup.openByPost('fSend','pop_report.jsp',840,945,false,true,false,'goReportView');		
 	}
 	
 	function goReportCreate(){
 		var f = document.fSend;
 		f.ir_type.value='D1';
 		f.action='issue_report_creater.jsp';
 		f.target='';
 		f.submit();
 	}

 	function ChangeTitle(ir_seq){
 	 	var f = document.fSend;
 	 	f.ir_seq.value = ir_seq;
 	 	//f.ir_title.value = ir_title;
 	 	window.open("about:blank",'nameUpdate', 'width=400,height=180,scrollbars=no');
 	 	f.action = 'pop_reportName_update.jsp';
        f.target='nameUpdate';
        f.submit();
 	}
 	
</script>
</head>
<body>
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="mode" >
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="delseq">
<input type="hidden" name="ir_seq">
<input type="hidden" name="ir_title">
<input type="hidden" name="ir_type" value="<%=ir_type %>">
<table style="width:850px%;height:100%;" border="0" cellpadding="0" cellspacing="0"> <!-- width:100%; -->
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/report/tit_icon.gif" />
<%
	if(ir_type.equals("E")){out.print("<img src=\"../../images/report/tit_0302.gif\" />");}
	else {out.print("<img src=\"../../images/report/tit_0304.gif\" />");}
	//else if(ir_type.equals("D1")){out.print("<img src=\"../../images/report/tit_0303.gif\" />");}
	//else if(ir_type.equals("W")){out.print("<img src=\"../../images/report/tit_0304.gif\" />");}
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
	else {out.print("주간보고서");}
	//else if(ir_type.equals("D1")){out.print("일일보고서");}
	//else if(ir_type.equals("W")){out.print("주간보고서");}
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
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../images/report/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer"/></td>
						<td><img src="../../images/report/btn_del.gif" onclick="deleteReport();" style="cursor:pointer;"/></td>
						<td style="width:88px;">
							<%if(!ir_type.equals(ReportTypeConstants.emergencyVal)){%><img src="../../images/report/btn_report_write.gif" style="cursor:pointer" onclick="goReportCreate();"/><%}%>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<col width="5%"><col width="35%"><col width="5%"><col width="10%"><col width="15%"><col width="15%"><col width="15%">
					<tr>
						<th><input type="checkbox" name="checkall" id="reportChk"  value="" onclick="checkAll();"></th>
						<th>제목</th>
						<th></th>
						<th></th>
						<th>작성일시</th>
						<th>발송일시</th>
						<th>메일발송</th>
					</tr>
<%
	String wdate = "";
	String wtime = "";
	String date = "";
	String time = "";

	if(arrIrBean.size() > 0){
		for(int i=0; i < arrIrBean.size(); i++){
			irBean = new IssueReportBean();
			irBean = (IssueReportBean)arrIrBean.get(i);
%>
					<tr>
						<td><input type="checkbox" name="reportchk" value="<%=irBean.getIr_seq()%>"></td>
						<td style="text-align:left;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer" onclick="showDetail('<%=irBean.getIr_seq()%>','<%=irBean.getIr_type()%>');"><span style="width:100%;"><%=irBean.getIr_title()%></span></td>
						<td><img src="../../images/report/btn_report_tit_mondify.gif" style="cursor:pointer" onclick="ChangeTitle('<%=irBean.getIr_seq()%>')"/></td>
						<td><img src="../../images/report/btn_report.gif" onclick="showReport(<%=irBean.getIr_seq()%>);" style="cursor:pointer"/></td>
						<td><%=irBean.getFormatIr_regdate("MM/dd HH:mm")%></td>
						<td><%if(irBean.getIr_mailyn().equals("Y")){if(irBean.getIr_maildate()!=null){out.print(du.getDate(irBean.getIr_maildate(), "MM/dd HH:mm:ss"));}else{out.print("-");}}else{out.print("-");}%></td>
						<td><%if(irBean.getIr_mailyn().equals("Y")){out.print("발송 완료");}else if(irBean.getIr_mailyn().equals("F")){out.print("발송실패");}else{out.print("발송대기");}%></td>
					</tr>
<%}}else{%>
					<tr><td align="center" colspan="7" style="height:40px;font-weight:bold">저장된 보고서가 없습니다.</td></tr>
<%}%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../images/report/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer"/></td>
						<td><img src="../../images/report/btn_del.gif"  onclick="deleteReport();" style="cursor:pointer;"/></td>
						<td style="width:88px;">
							<%if(!ir_type.equals(ReportTypeConstants.emergencyVal)){%><img src="../../images/report/btn_report_write.gif" style="cursor:pointer" onclick="goReportCreate();"/><%}%>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
			<tr>
				<td style="text-align:center;">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1" align="center">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage, totalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td>
	</tr>
</table>
</form>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;"></iframe>
</body>
</html>
