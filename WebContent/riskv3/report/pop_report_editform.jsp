<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean							
				,risk.util.PageIndex
				,risk.issue.IssueReportBean
				,risk.util.*
				,java.net.URLEncoder
				,risk.namo.NamoMime
"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	NamoMime namoMime = new NamoMime();
	
	String mode = pr.getString("mode","insert");
	String reportType = pr.getString("reportType");
	
	String url = "";
	String param = "";
	
	String i_seq = "";
	String ir_seq = "";
	
	String ir_html = null;
	String ir_title = null;
	String ir_type = null;
	String ir_sdate = null;
	String ir_edate = null;
	String ir_stime = null;
	String ir_etime = null;
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl + "crosseditor/upload";
	
	if(mode.equals("update")){
		
		IssueReportMgr irMgr = new IssueReportMgr();
		IssueReportBean irBean = new IssueReportBean();
		
		ir_type = pr.getString("ir_type","");
		ir_seq = pr.getString("ir_seq");
		irBean = irMgr.getReportBean(ir_seq);	
		ir_html = irBean.getIr_html();
		
	}else{
		
		//보고서 종류
		ir_type = pr.getString("ir_type","");		
		ir_title = pr.getString("ir_title","");
		
		//보고서 날짜
		ir_sdate = pr.getString("ir_sdate");
		ir_edate = pr.getString("ir_edate");
		
		ir_stime = pr.getString("ir_stime");
		ir_etime = pr.getString("ir_etime");
		
		//시간값 자릿수에 따른 처리
		//ir_stime = ir_stime+":00:00";
		//ir_etime = ir_etime+":00:00";
		
		if(ir_type.equals("D")){
			//String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			//String id_seqs_pro = pr.getString("id_seqs_pro", "");
			//String id_seqs_isu = pr.getString("id_seqs_isu", "");
			//url = siteUrl+"riskv3/report/report_day.jsp";
			//param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+ir_stime.replaceAll(":", "")+"&edate="+ir_edate.replaceAll("-", "")+ir_etime.replaceAll(":", "");
			//ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");	
		}else if(ir_type.equals("I")){//일일보고서 (주요 이슈 포함)
			String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			String id_seqs_pro = pr.getString("id_seqs_pro", "");
			String id_seqs_isu = pr.getString("id_seqs_isu", "");
			String issue_sDate = pr.getString("issue_sDate");
			String issue_eDate = pr.getString("issue_eDate");
			url = siteUrl+"riskv3/report/import_issue_daily_report.jsp";
			param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+"&stime="+ir_stime+"&edate="+ir_edate.replaceAll("-", "")+"&etime="+ir_etime+"&issue_sDate="+issue_sDate+"&issue_eDate="+issue_eDate;
			ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");
		}else if(ir_type.equals("D1")){//일일보고서 
			String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			String id_seqs_pro = pr.getString("id_seqs_pro", "");
			String id_seqs_isu = pr.getString("id_seqs_isu", "");
			url = siteUrl+"riskv3/report/issue_daily_report.jsp";
			param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+"&stime="+ir_stime+"&edate="+ir_edate.replaceAll("-", "")+"&etime="+ir_etime;
			ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");
		}
	}
	//최종 특수문자 태그 처리
	ir_html = su.ChangeString(ir_html.trim());	
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=SS_TITLE%></title>
<script type="text/javascript" src="../../crosseditor/js/namo_scripteditor.js"></script>
<script type="text/JavaScript">

	function saveReport()
	{
		var f = document.fSend;
		//f.mode.value = 'insert';		
		if(f.reportType.value == "1"){
			if(document.Wec.MIMEValue){
				f.ir_html.value = document.Wec.MIMEValue;
				f.IEuse.value = 'Y';
			}else{
				f.IEuse.value = 'N';
			}	
		}else if(f.reportType.value == "2"){
			
			f.ir_html.value = CrossEditor.GetValue();
			
		}
		f.action = 'issue_report_prc.jsp';
		f.target = 'processFrm';
		f.submit();
	}

	function viewChart(){
		var f = document.fSend;
		f.action = 'relationMap.jsp';
		f.target = '';
		f.submit();
	}

</script>
<%
if(ir_type.equals("D") && !mode.equals("update")){
%>
<OBJECT codebase="http://java.sun.com/update/1.6.0/jinstall-6-windows-i586.cab#Version=6,0,0,0" classid="clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284" height=0 width=0>
<PARAM name="back" value="true">
</OBJECT>
<%
}
%>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe id="processFrm" name ="processFrm" width="0" height="0" ></iframe>
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ir_title" value="<%=pr.getString("ir_title","")%>">
<input type="hidden" name="ir_type" value="<%=ir_type%>">
<input type="hidden" name="ir_html" value="<%=ir_html%>">
<input type="hidden" name="ir_seq" value="<%=ir_seq%>">
<input type="hidden" name="IEuse" value="Y">
<input type="hidden" name="reportType" value="<%=reportType%>">
<%
	if(!mode.equals("update")){
%>
	<input type="hidden" name="sdate" id="sdate" value="<%=ir_sdate.replaceAll("-", "")%><%=ir_stime.replaceAll(":", "")%>">
	<input type="hidden" name="edate" id="edate" value="<%=ir_edate.replaceAll("-", "")%><%=ir_etime.replaceAll(":", "")%>">
<%
	}
%>

<table width="730" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="10">
			
		</td>
	</tr>
	<tr>
		<td align="right">
			<!-- <img src="../../images/report/btn_save2.gif"  hspace="5" onclick="viewChart();" style="cursor:pointer;"> -->
			&nbsp;<img src="../../images/report/btn_save2.gif"  hspace="5" onclick="saveReport();" style="cursor:pointer;">
			&nbsp;<img src="../../images/report/btn_cancel.gif" onClick="window.close();" style="cursor:pointer;">&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td height="5">
			
		</td>
	</tr>
</table>
<%
	if(reportType.equals("1")){
%>
<SCRIPT language="javascript" src="/namo/NamoWec7.js"></SCRIPT>			
<SCRIPT language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
			  var form = document.fSend;				
			  var wec = document.Wec;
			  wec.Value = form.ir_html.value;				  	 
</SCRIPT>
<%
	}else if(reportType.equals("2")){
%>		
		<script type="text/javascript">

		var CrossEditor = new NamoSE("namo");
		CrossEditor.params.ImageSavePath = "<%=imgUrl%>";
		CrossEditor.EditorStart();
		
		function OnInitCompleted(e){
		  e.editorTarget.SetValue(document.fSend.ir_html.value); // 컨텐츠 내용 에디터에 삽입
		}

		</script>
		
<%		
	}
%>
</form>
</body>    
</html>

 

