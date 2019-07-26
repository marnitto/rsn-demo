<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="	risk.issue.*
					,java.util.ArrayList
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.JfreeChart.MakeTypeChart
					,risk.issue.IssueReportBean
					,risk.issue.IssueReportMgr
					,risk.util.DateUtil	
					,java.util.HashMap
					,java.util.Iterator" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	IssueMgr iMgr = new IssueMgr();
	IssueReportMgr rMgr = new IssueReportMgr();
	MakeTypeChart irChart = new MakeTypeChart();
	IssueCodeMgr icMgr =new IssueCodeMgr();
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	IssueCommentBean icmBean = new IssueCommentBean();
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();	
	ArrayList arrIssueList = new ArrayList();
	ArrayList arrIDC = new ArrayList();
	ArrayList arrICM = null;	
	
	String ir_type = null;
	String typeCode = null;
	String brandCode = null;
	String siteCode = null;
	String ir_sdate = null;
	String ir_edate = null;
	String ir_stime = null;
	String ir_etime = null;
	String formatSdate = null;
	String formatEdate = null;
	String today = null;
	String i_seq = null;
	String id_seq = null;  
	String[] arrI_seq = null;
	String k_xp = null;
	String k_yp = null;
	String k_zp = null;
	String sg_seq = null;

	HashMap dataHM = new HashMap();
	HashMap listHmt = new HashMap();
	Iterator it = null;		
	ArrayList arrGdataA = new ArrayList();
	ArrayList arrGdataB = new ArrayList();
	ArrayList arrGdataC = new ArrayList();
	ArrayList arrGdataD = new ArrayList();
	ArrayList arrGdataE = new ArrayList();	
	
	ArrayList arrIdcBean =  null;
	ArrayList arrIssueDataList1 = new ArrayList();
	ArrayList arrIssueDataList2 = new ArrayList();
	ArrayList arrIssueDataList3 = new ArrayList();
	ArrayList arrIssueDataList4 = new ArrayList();
	String chart1 = "";
	String chart2 = "";
	String chart3 = "";
	String chart4 = "";
	String chart5 = "";
	String issueType1Name = "";
	
	
	//코드초기화
	icMgr.init(1);
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"images/report/";
	
	//관련정보 출력여부
	String issue_yn = pr.getString("issue_yn", "y");
	
	//차트 경로
	String filePath = cu.getConfig("CHARTPATH")+"report"+ReportTypeConstants.peoridVal+"/";
	String chartUrl = cu.getConfig("CHARTURL")+"report"+ReportTypeConstants.peoridVal+"/";
	
	//보고서 종류	
	ir_type = pr.getString("ir_type","");
	i_seq = pr.getString("i_seq","");
	
	//보고서 작성 코드(브랜드, 사업부문)
	typeCode = pr.getString("typeCode","");

	
	//보고서 날짜
	ir_sdate = pr.getString("ir_sdate");
	ir_edate = pr.getString("ir_edate");
	
	ir_stime = pr.getString("ir_stime");
	ir_etime = pr.getString("ir_etime");
	
	//시간값 자릿수에 따른 처리
	if(ir_stime.length()==1){
		ir_stime = "0"+ir_stime+":00:00";
	}else if(ir_stime.length()==2){
		ir_stime = ir_stime+":00:00";
	}
	
	if(ir_etime.length()==1){
		ir_etime = "0"+ir_etime+":00:00";
	}else if(ir_etime.length()==2){
		ir_etime = ir_etime+":00:00";
	}
	
	String issueType1 = "";
	String issueType2 = "";
	String issueType3 = "";
	String issueType4 = "";
	String issueType5 = "";
	String issueType6 = "";
	String issueType7 = "";
	
	String sungHyangImg = "";
	String jungYoDoImg = "";

	
	//보고서 종류별 셋팅
	if(ir_type.equals(ReportTypeConstants.getEmergencyVal())){			
		
		//긴급 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일(E)");
		id_seq = pr.getString("id_seq","");	
		arrIssueList = new ArrayList();
		arrIDC = new ArrayList();		
		arrIssueList = iMgr.getIssueDataList(0,0,id_seq,"","","","","2","","","","","","");
		
	}else if(ir_type.equals(ReportTypeConstants.getDailyVal())){	
		
		//일일 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일(E)");
		id_seq = pr.getString("id_seq","");			
		
		//일일 차트 데이터	
		dataHM = rMgr.getDailyChartData(ir_sdate,ir_stime,ir_edate,ir_etime,typeCode);		
		arrGdataA = (ArrayList)dataHM.get("A");
		arrGdataB = (ArrayList)dataHM.get("B");
		
		//성향 
		chart1 = irChart.make3DPieChart(1,arrGdataA,"DailyPieChartA"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,205,114);
		
		//출처별
		chart2 = irChart.make3DPieChart(1,arrGdataB,"DailyPieChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,180,114);	
		
		//중요도가 상중이고 자사인거
		if(!typeCode.equals("")) typeCode +="@10,1@10,2";
		else typeCode +="10,1@10,2";  
		
		//관심 정보	
		arrIssueDataList1 = new ArrayList();		
		arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@"+typeCode,"9","");

		arrIssueDataList2 = new ArrayList();	
		arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,2@"+typeCode,"9","");
				
		arrIssueDataList3 = new ArrayList();	
		arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,3@"+typeCode,"9","");
		
		arrIssueDataList4 = new ArrayList();	
		arrIssueDataList4 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,4@"+typeCode,"9","");
		/*
		*/
		
	}else if(ir_type.equals(ReportTypeConstants.getWeeklyVal())){	
		
		//주간 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일 HH:mm");
		formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
		formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");		
		id_seq = pr.getString("id_seq","");
		
		
		//주간 차트 데이터 		
		dataHM = rMgr.getWeeklyChartData(ir_sdate,ir_stime,ir_edate,ir_etime,typeCode);		
		arrGdataA = (ArrayList)dataHM.get("A");
		arrGdataB = (ArrayList)dataHM.get("B");
		arrGdataC = (ArrayList)dataHM.get("C");
		arrGdataD = (ArrayList)dataHM.get("D");
		
		//주간 우호도 동향
		chart1 = irChart.makeLineBar(1,arrGdataA,"WeeklyLineBarChartA"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
		
		//주간 출처별 관심도
		chart2 = irChart.makeLine(2,arrGdataB,"WeeklyLineChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
		
		//주간 사업부분별 
		chart3 = irChart.make3DPieChart(1,arrGdataC,"WeeklyPieChartC"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);
		
		//주간 정보유형별
		chart4 = irChart.make3DPieChart(1,arrGdataD,"WeeklyPieChartD"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);	
		
		//중요도가 상중이고 자사인거
		if(!typeCode.equals("")) typeCode +="@10,1@10,2";
		else typeCode +="10,1@10,2"; 
		
		//관심 정보	
		arrIssueDataList1 = new ArrayList();		
		arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@"+typeCode,"9","");

		arrIssueDataList2 = new ArrayList();	
		arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,2@"+typeCode,"9","");
				
		arrIssueDataList3 = new ArrayList();	
		arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,3@"+typeCode,"9","");
				
		arrIssueDataList4 = new ArrayList();	
		arrIssueDataList4 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,4@"+typeCode,"9","");
		/*
		*/
		
	}else if(ir_type.equals(ReportTypeConstants.getMonthlyVal())){	
		
		//주간 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일 HH:mm");
		formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
		formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");		
		id_seq = pr.getString("id_seq","");
				
		//주간 차트 데이터 		
		dataHM = rMgr.getWeeklyChartData(ir_sdate,ir_stime,ir_edate,ir_etime,typeCode);		
		arrGdataA = (ArrayList)dataHM.get("A");
		arrGdataB = (ArrayList)dataHM.get("B");
		arrGdataC = (ArrayList)dataHM.get("C");
		arrGdataD = (ArrayList)dataHM.get("D");
		
		//주간 우호도 동향
		chart1 = irChart.makeLineBar(1,arrGdataA,"WeeklyLineBarChartA"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
		
		//주간 출처별 관심도
		chart2 = irChart.makeLine(2,arrGdataB,"WeeklyLineChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
		
		//주간 사업부분별 
		chart3 = irChart.make3DPieChart(1,arrGdataC,"WeeklyPieChartC"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);
		
		//주간 정보유형별
		chart4 = irChart.make3DPieChart(1,arrGdataD,"WeeklyPieChartD"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);	
		
		//중요도가 상중이고 자사인거
		if(!typeCode.equals("")) typeCode +="@10,1@10,2";
		else typeCode +="10,1@10,2"; 
		
		//관심 정보	
		arrIssueDataList1 = new ArrayList();
		arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"1,1@"+typeCode,"9","");
				
		arrIssueDataList2 = new ArrayList();	
		arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"1,2@"+typeCode,"9","");
				
		arrIssueDataList3 = new ArrayList();	
		arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"1,3@"+typeCode,"9","");
		
	}else if(ir_type.equals(ReportTypeConstants.getIssueVal())){	
		
		//이슈 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일 HH:mm");
		formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
		formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");		
		id_seq = pr.getString("id_seq","");	
		
		//이슈 차트 데이터 		
		dataHM = rMgr.getIssueChartData(ir_sdate,ir_stime,ir_edate,ir_etime,i_seq);		
		arrGdataA = (ArrayList)dataHM.get("A");
		arrGdataB = (ArrayList)dataHM.get("B");
		arrGdataC = (ArrayList)dataHM.get("C");
		
		
		//이슈 전체 우호도
		chart1 = irChart.make3DPieChart(1,arrGdataB,"IssuePieChartA"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,300,150);
		
		//이슈 일자별 우호도
		chart2 = irChart.makeLineBar(1,arrGdataA,"IssueLineChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
		
		//이슈 출처별 우호도 
		chart3 = irChart.makeStackBar(1,arrGdataC,"IssueStackBarChartC"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,400,250);
		
		//중요도가 상중이고 자사인거
		if(!typeCode.equals("")) typeCode +="@10,1@10,2";
		else typeCode +="10,1@10,2"; 
		
		//관심 정보		
		////긍정인거 중에  중요도 상
		arrIssueDataList1 = new ArrayList();		
		arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"9,1@"+typeCode,"9","");
		
		////부정인거 중에  중요도 상
		arrIssueDataList2 = new ArrayList();	
		arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"9,2@"+typeCode,"9","");
		
		////중립인거 중에  중요도 상
		arrIssueDataList3 = new ArrayList();	
		arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"9,3@"+typeCode,"9","");
		
	}else if(ir_type.equals(ReportTypeConstants.getPeoridVal())){		
		
		//단기 보고서 관련
		today = du.getCurrentDate("yyyy년M월dd일 HH:mm");
		formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
		formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");	
		
		//사이트그룹 관련
		sg_seq = pr.getString("sg_seq","");
		
		//키워드 관련
		k_xp = pr.getString("k_xp","");
		k_yp = pr.getString("k_yp","");
		k_zp = pr.getString("k_zp","");		
		
		dataHM = rMgr.getPeoridChartData(ir_sdate,ir_stime,ir_edate,ir_etime,k_xp,k_yp,k_zp,sg_seq);		
		arrGdataA = (ArrayList)dataHM.get("A");		
		
		//주간 출처별 관심도
		chart1 = irChart.makeLineByTime(2,arrGdataA,"WeeklyLineChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,250);
						
		
	}
%>                 	
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
td,table,body{font-size:12px;font-family:dotum,돋움;}
A:link {
	COLOR: #333333; TEXT-DECORATION: none
}
A:visited {
	COLOR: #000000; TEXT-DECORATION: none
}
A:hover {
	COLOR: #ff9900; TEXT-DECORATION: none
}
A:active {
	TEXT-DECORATION: none
}
A.sch_title:link {
	COLOR: #333333; TEXT-DECORATION: none
}
A.sch_title:visited {
	COLOR: #aaaaaa; TEXT-DECORATION: none
}
A.sch_title:hover {
	COLOR: #ff9900; TEXT-DECORATION: none
}
A.sch_title:active {
	TEXT-DECORATION: none
}
</style>
</head>
<body>
<%
	if(ir_type.equals(ReportTypeConstants.getEmergencyVal())){	
%>
<table width="650" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=du.getCurrentDate("yyyy년MM월dd일") %></td>
						<td width="508"><img src="<%=imgUrl%>top_02.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="620" border="0" cellpadding="0" cellspacing="0">
			
<%
			for(int i = 0; i < arrIssueList.size(); i++){
			idBean = new IssueDataBean();
			idBean = (IssueDataBean)arrIssueList.get(i);
			arrIDC = (ArrayList)idBean.getArrCodeList();
			
			arrICM = new ArrayList();
			arrICM = idBean.getArrCommentList();	
			
			if(arrIDC.size() > 0){
				issueType1 = icMgr.GetCodeNameType(arrIDC,2);	//회사	
				issueType2 = icMgr.GetCodeNameType(arrIDC,9);	//성향
				//issueType3 = icMgr.GetCodeNameType(arrIDC,7);
				//issueType4 = icMgr.GetCodeNameType(arrIDC,8);
				//issueType6 = icMgr.GetCodeNameType(arrIDC,9);
				//issueType7 = icMgr.GetCodeNameType(arrIDC,10);		
				
				if(issueType2.trim().equals("긍정")){
					sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
				}else if(issueType2.trim().equals("부정")){
					sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
				}else if(issueType2.trim().equals("중립")){
					sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
				}
				
				//System.out.println("sungHyangImg:"+sungHyangImg);
				
			/* 	
				if(issueType7.trim().equals("상")){
					jungYoDoImg = "<img src= \""+imgUrl+"icon_lv03.gif\" align=\"absmiddle\" />";
				}else if(issueType7.trim().equals("중")){
					jungYoDoImg = "<img src= \""+imgUrl+"icon_lv02.gif\" align=\"absmiddle\" />";	
				}else if(issueType7.trim().equals("하")){
					jungYoDoImg = "<img src= \""+imgUrl+"icon_lv01.gif\" align=\"absmiddle\" />";
				}
				System.out.println("jungYoDoImg:"+sungHyangImg); 
			*/
			}
%>	       
			<tr>
				<td style="line-height:30px;font-size:14px;color:#164a8a;font-weight:bold;"><%=i+1%>.<%=su.cutString(idBean.getId_title(),50,"....")%></td>
			</tr>
			<tr><td style="height:10px;"></td></tr>		
			<tr>
				<td>
				<table style="border-top:2px #1e90c6 solid;" width="100%" border="0" cellpadding="10" cellspacing="0">
					<tr>
						<th style="width:80px;background:#f5f5f5;border-right:1px #e3e3e3 solid;">정보출처</th>
						<td style="background:#ffffff;border-right:1px #e3e3e3 solid;"><%=idBean.getMd_site_name()%></td>
						<th style="background:#f5f5f5;border-right:1px #e3e3e3 solid;">수집시간</th>
						<td style="background:#ffffff;"><%=idBean.getFormatMd_date("yyyy년MM월dd일(E)")%></td>
					</tr>
					<tr>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">Site 출처</th>
						<td colspan="3" style="background:#ffffff;border-top:1px #dcdcdc solid;"><a href="<%=idBean.getId_url()%>" target="_blank"><%=idBean.getId_url()%></a></td>
					</tr>
					<tr>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">회사</th>
						<td style="background:#ffffff;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;"><%=issueType1%>&nbsp;</td>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">성향</th>
						<td style="background:#ffffff;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;"><%=sungHyangImg%>&nbsp;<%=issueType2%></td>
					</tr>
					<%-- <tr>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">유형</th>
						<td style="background:#ffffff;border-top:1px #dcdcdc solid;"><%=issueType3%>&nbsp;</td>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">중요도</th>
						<td style="background:#ffffff;border-top:1px #dcdcdc solid;"><%=jungYoDoImg%>&nbsp;<%=issueType7%></td>
					</tr> --%>
					
					<tr>
						<th style="background:#f5f5f5;border-top:1px #dcdcdc solid;border-bottom:1px #dcdcdc solid;border-right:1px #e3e3e3 solid;">요약내용</th>
						<td colspan="3" style="line-height:150%;background:#ffffff;border-top:1px #dcdcdc solid;border-bottom:1px #dcdcdc solid;">
						<%=idBean.getId_content()%>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			
<%
			}
%>			
		</table>

		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>
<div style="display: none"><img src="report_receipt"></div>

<%
	}else if(ir_type.equals(ReportTypeConstants.getDailyVal())){
		
	
%>
<style>
td,table,body{font-size:12px;font-family:dotum,돋움;}
</style>
<table width="750" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=today%></td>
						<td width="508"><img src="<%=imgUrl%>top_02.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03_2.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="720" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:250px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_01.gif" align="absmiddle" />&nbsp;금일온라인동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						.....				
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:280px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_02.gif" align="absmiddle" />&nbsp;금일 온라인 성향 및 출처별 점유율</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:305px;height:150px;border: 1px #DDDDDD solid;text-align:center;">
						<table style="width:100%;" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td style="width:205px;"><img src="<%=chart1%>"></img></td>
								<td style="width:100px;">
								<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
<%
								int sum = 0;
								if(arrGdataA!=null)
								{
									it = arrGdataA.iterator();
									while(it.hasNext())
									{
										listHmt = new HashMap();
										listHmt = (HashMap)it.next();
										sum += Integer.parseInt((String)listHmt.get("CNT"));

												 
									}			
								}

%>	
									<tr>
										<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;">합계</th>
										<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
						<td style="width:10px;">&nbsp;</td>
						<td style="width:305px;height:150px;border: 1px #DDDDDD solid;text-align:center;">
						<table style="width:100%;" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td style="width:180px;"><img src="<%=chart2%>"></img></td>
								<td style="width:125px;">
								<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
<%
								sum = 0;
								if(arrGdataB!=null)
								{
									it = arrGdataB.iterator();
									while(it.hasNext())
									{
										listHmt = new HashMap();
										listHmt = (HashMap)it.next();
										sum += Integer.parseInt((String)listHmt.get("CNT"));
								%>
									<tr>
										<th style="width:90px;height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;"><%=listHmt.get("CATEGORY")%></th>
										<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=listHmt.get("CNT")%></td>
									</tr>
								<%
										 
									}			
								}

%>	
									<tr>
										<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;">합계</th>
										<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
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
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....			
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
<%
if(issue_yn.equals("y")){
%>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:190px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_03.gif" align="absmiddle" />&nbsp;관련 정보<span style="color:#ff5a00;">(<%=arrIssueDataList1.size()+arrIssueDataList2.size()+arrIssueDataList3.size()+arrIssueDataList4.size()%>건)</span></td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">자사  관련 졍보<span style="color:#ff5a00;">(<%=arrIssueDataList1.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList1.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList1.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList1.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList1.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList1.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">관계사 관련 정보<span style="color:#ff5a00;">(<%=arrIssueDataList2.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList2.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList2.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList2.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList2.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList2.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">경쟁사 관련 정보<span style="color:#ff5a00;">(<%=arrIssueDataList3.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList3.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList3.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList3.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList3.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList3.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">산업전반 관련 정보<span style="color:#ff5a00;">(<%=arrIssueDataList4.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList4.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList4.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList4.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList4.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList4.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
<%
}
%>
		</table>
		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>
<%
	
	}if(ir_type.equals(ReportTypeConstants.getWeeklyVal())){		
		
		imgUrl = siteUrl+"images/report/";
%>	
<style>
td,table,body{font-size:12px;font-family:dotum,돋움;}
</style>
<table width="750" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=formatSdate %> ~ <%=formatEdate %></td>
						<td width="390"><img src="<%=imgUrl%>top_02_2.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03_3.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="720" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:250px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_01.gif" align="absmiddle" />&nbsp;주간 온라인 동향(:codeName) </td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:150px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_02.gif" align="absmiddle" />&nbsp;우호도 동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:300px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart1 %>">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....				
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>

			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:200px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_03.gif" align="absmiddle" />&nbsp;출처별 관심도 동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:300px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart2 %>">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....				
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<%--
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:300px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_04.gif" align="absmiddle" />&nbsp;유형별 점유율</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>						
				<td style="width:100%;height:150px;border: 1px #DDDDDD solid;text-align:center;">
					<table style="width:100%;" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td align="center" style="width:70%;"><img src="<%=chart4%>"></img></td>
							<td style="width:30%;">
							<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
<%
								int sum = 0;
								if(arrGdataD!=null)
								{
									it = arrGdataD.iterator();
									while(it.hasNext())
									{
										listHmt = new HashMap();
										listHmt = (HashMap)it.next();
										sum += Integer.parseInt((String)listHmt.get("CNT"));
								%>
								<tr>
									<th style="width:150px;height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;"><%=listHmt.get("CATEGORY")%></th>
									<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=listHmt.get("CNT")%></td>
								</tr>
								<%
										 
									}			
								}

%>	
								<tr>
									<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;">합계</th>
									<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....		
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			--%>
<%
if(issue_yn.equals("y")){
%>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:190px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_04.gif" align="absmiddle" />&nbsp;관련 정보<span style="color:#ff5a00;">(<%=arrIssueDataList1.size()+arrIssueDataList2.size()+arrIssueDataList3.size()+arrIssueDataList4.size()%>건)</span></td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">자사  관련 졍보<span style="color:#ff5a00;">(<%=arrIssueDataList1.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList1.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList1.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList1.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList1.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList1.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">관계사  관련 졍보<span style="color:#ff5a00;">(<%=arrIssueDataList2.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList2.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList2.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList2.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList2.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList2.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">경쟁사  관련 졍보<span style="color:#ff5a00;">(<%=arrIssueDataList3.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList3.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList3.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList3.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList3.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList3.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">산업전반  관련 졍보<span style="color:#ff5a00;">(<%=arrIssueDataList4.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
<%
	if(arrIssueDataList4.size()>0){
%>
			<tr>
				<td>
				<table style="width:100%;background:#dddddd;" border="0" cellpadding="5" cellspacing="1">
					<tr>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">구분</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">제목</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">출처</td>
						<td style="height:34;background:url(<%=imgUrl%>board_head_bg.gif);text-align:center;font-weight:bold;">수집시간</td>
					</tr>
<%
					int rowspan_1 = 0;
					int rowspan_2 = 0;
					boolean chk = false;
					for(int i=0; i<arrIssueDataList4.size(); i++){
						idBean = new IssueDataBean();
						idBean = (IssueDataBean)arrIssueDataList4.get(i);
						if(idBean.getMd_type().equals("1")){
							rowspan_1++;
						}
						if(idBean.getMd_type().equals("2")){
							rowspan_2++;
						}
					}
					for(int z = 1; z < 3; z++){
						String code = "";
						String imageName = "";
						for(int i=0; i<arrIssueDataList4.size(); i++){
							idBean = new IssueDataBean();
							idBean = (IssueDataBean)arrIssueDataList4.get(i);
							if(z == Integer.parseInt(idBean.getMd_type())){
								arrIDC = idBean.getArrCodeList();
								
								code = icMgr.getCodeVal(arrIDC,9);
								if(code.equals("1")){
									imageName = "icon_tendency_01.gif";
								}else if(code.equals("2")){
									imageName = "icon_tendency_02.gif";
								}else if(code.equals("3")){
									imageName = "icon_tendency_04.gif";
								}
				%>
					<tr>
<%
			if(rowspan_1 != 0 && !chk && z == 1){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_1+"\">언론</td>"); chk = true;}
			if(rowspan_2 != 0 && !chk && z == 2){out.print("<td style=\"background:#fbfbfb;text-align:center;\" rowspan=\""+rowspan_2+"\">개인</td>"); chk = true;}
%>
						<td align="left" style="height:20px;padding-left:5px;background:#ffffff;border-bottom:1px solid #f4f4f4;cursor:pointer"><img src="<%=imgUrl%><%=imageName%>" align="absmiddle" /><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>">&nbsp;<%=su.cutString(idBean.getId_title(), 31, "...")%></a></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;font-weight:bold;" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 7, "...")%></td>
						<td style="background:#ffffff;border-bottom:1px solid #f4f4f4;text-align:center;font-size:11px;"><%=idBean.getFormatMd_date("MM/dd HH:mm") %></td>
					</tr>	
				<%
							}
						}
						chk = false;
					}
				%>			
				</table>
				</td>
			</tr>
<%
	}
%>
<%
}
%>
		</table>
		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>		
<%
	}if(ir_type.equals(ReportTypeConstants.getMonthlyVal())){		
		
		imgUrl = siteUrl+"images/report/";
%>	
<style type="text/css">
td,table,body{font-size:12px;font-family:dotum,돋움;}
</style>
<table width="650" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=formatSdate %> ~ <%=formatEdate %></td>
						<td width="390"><img src="<%=imgUrl%>top_02_2.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03_6.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="620" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:250px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_01.gif" align="absmiddle" />&nbsp;월간 온라인 동향(:codeName) </td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:150px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_02.gif" align="absmiddle" />&nbsp;자사 우호도 동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:300px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart1 %>">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....				
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>

			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:200px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_03.gif" align="absmiddle" />&nbsp;자사 출처별 관심도 동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:300px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart2 %>">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....				
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:300px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_04.gif" align="absmiddle" />&nbsp;자사 정보유형별 점유율</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>						
				<td style="width:100%;height:150px;border: 1px #DDDDDD solid;text-align:center;">
					<table style="width:100%;" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td align="center" style="width:70%;"><img src="<%=chart4%>"></img></td>
							<td style="width:30%;">
							<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
<%
								int sum = 0;
								if(arrGdataD!=null)
								{
									it = arrGdataD.iterator();
									while(it.hasNext())
									{
										listHmt = new HashMap();
										listHmt = (HashMap)it.next();
										sum += Integer.parseInt((String)listHmt.get("CNT"));
								%>
								<tr>
									<th style="width:150px;height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;"><%=listHmt.get("CATEGORY")%></th>
									<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=listHmt.get("CNT")%></td>
								</tr>
								<%
										 
									}			
								}

%>	
								<tr>
									<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;">합계</th>
									<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....		
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:100px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_05.gif" align="absmiddle" />&nbsp;관련 정보</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
						<tr>
				<td align="left" style="text-align:left;font-weight:bold;">자사<span style="color:#ff5a00;">(<%=arrIssueDataList1.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th bgcolor="#ebebeb" style="height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">제목</th>
						<th bgcolor="#ebebeb" style="width:100px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">출처</th>
						<th bgcolor="#ebebeb" style="width:80px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">날짜</th>
					</tr>
<%				
		String sunghyang = "";
		String important = "";
		String gubun = "";
		for(int i=0; i<arrIssueDataList1.size(); i++){
			
			idBean = (IssueDataBean)arrIssueDataList1.get(i);
			arrIdcBean = new ArrayList();
			arrIdcBean = (ArrayList) idBean.getArrCodeList();
			
			sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
			important = icMgr.GetCodeNameType(arrIdcBean,10);
			gubun = icMgr.GetCodeNameType(arrIdcBean,1);
			
			if(sunghyang.trim().equals("긍정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
			}else if(sunghyang.trim().equals("부정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
			}else if(sunghyang.trim().equals("중립")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
			}
			
			
%>
			<tr>
				<td align="left" style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:left;">&nbsp;<%=sungHyangImg%>&nbsp;<a href="<%=idBean.getId_url()%>" target="_blank"><font style="color:#4d77d2;text-decoration:none;"><%=su.cutString(idBean.getId_title(),35,"...")%></font></a></td>
				<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;"><%=su.cutString(idBean.getMd_site_name(),10,"...")%></td>
				<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;color:#198bf6;"><%=idBean.getFormatMd_date("yy.MM.dd") %></td>
			</tr>
<%
			
		}
%>
				</table>
				</td>
			</tr>
			<tr><td style="height:20px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">경쟁사 <span style="color:#ff5a00;">(<%=arrIssueDataList2.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th bgcolor="#ebebeb" style="height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">제목</th>
						<th bgcolor="#ebebeb" style="width:100px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">출처</th>
						<th bgcolor="#ebebeb" style="width:80px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">날짜</th>
					</tr>
<%				
		sunghyang = "";
		important = "";
		gubun = "";
		for(int i=0; i<arrIssueDataList2.size(); i++){
			
			idBean = (IssueDataBean)arrIssueDataList2.get(i);
			arrIdcBean = new ArrayList();
			arrIdcBean = (ArrayList) idBean.getArrCodeList();			
			sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
			important = icMgr.GetCodeNameType(arrIdcBean,10);
			gubun = icMgr.GetCodeNameType(arrIdcBean,1);
			
			if(sunghyang.trim().equals("긍정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
			}else if(sunghyang.trim().equals("부정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
			}else if(sunghyang.trim().equals("중립")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
			}
			
%>
					<tr>
						<td align="left" style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:left;">&nbsp;<%=sungHyangImg%>&nbsp;<a href="<%=idBean.getId_url()%>" target="_blank"><font style="color:#4d77d2;text-decoration:none;"><%=su.cutString(idBean.getId_title(),35,"...")%></font></a></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;"><%=su.cutString(idBean.getMd_site_name(),10,"...")%></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;color:#198bf6;"><%=idBean.getFormatMd_date("yy.MM.dd") %></td>
					</tr>
<%
			
		}
%>
				</table>
				</td>
			</tr>
			<tr><td style="height:20px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">기타 <span style="color:#ff5a00;">(<%=arrIssueDataList3.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th bgcolor="#ebebeb" style="height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">제목</th>
						<th bgcolor="#ebebeb" style="width:100px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">출처</th>
						<th bgcolor="#ebebeb" style="width:80px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">날짜</th>
					</tr>
<%				
		sunghyang = "";
		important = "";
		gubun = "";
		for(int i=0; i<arrIssueDataList3.size(); i++){
			
			idBean = (IssueDataBean)arrIssueDataList3.get(i);
			arrIdcBean = new ArrayList();
			arrIdcBean = (ArrayList) idBean.getArrCodeList();			
			sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
			important = icMgr.GetCodeNameType(arrIdcBean,10);
			gubun = icMgr.GetCodeNameType(arrIdcBean,1);
			
			if(sunghyang.trim().equals("긍정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
			}else if(sunghyang.trim().equals("부정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
			}else if(sunghyang.trim().equals("중립")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
			}
			
%>
					<tr>
						<td align="left" style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:left;">&nbsp;<%=sungHyangImg%>&nbsp;<a href="<%=idBean.getId_url()%>" target="_blank"><font style="color:#4d77d2;text-decoration:none;"><%=su.cutString(idBean.getId_title(),35,"...")%></font></a></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;"><%=su.cutString(idBean.getMd_site_name(),10,"...")%></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;color:#198bf6;"><%=idBean.getFormatMd_date("yy.MM.dd") %></td>
					</tr>
<%
			
		}
%>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>		
<%
	}if(ir_type.equals(ReportTypeConstants.getIssueVal())){		
		
		imgUrl = siteUrl+"images/report/";
%>	
<style>
td,table,body{font-size:12px;font-family:dotum,돋움;}
</style>
<table width="650" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=formatSdate %> ~ <%=formatEdate %></td>
						<td width="390"><img src="<%=imgUrl%>top_02_2.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03_4.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="620" border="0" cellpadding="0" cellspacing="0">
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td style="text-align:center;font-size:20px;font-weight:bold;"><img src="<%=imgUrl%>icon_tick.gif" align="absmiddle" />:ir_title</td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr><td><img src="<%=imgUrl%>tit_bg.gif" width="620" height="8"></td></tr>
			<tr><td style="height:20px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:115px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_01.gif" align="absmiddle" />&nbsp;온라인 동향 </td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:115px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_02.gif" align="absmiddle" />&nbsp;전체 우호도</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:150px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart1 %>">
						
						</td>
						<td style="width:10px;">&nbsp;</td>
						<td style="width:150px;">
						<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">긍정</th>
								<th style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">중립</th>
								<th style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">부정</th>
								<th style="border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">합계</th>
							</tr>
<%
			int sum = 0;
			if(arrGdataB!=null)
			{
				it = arrGdataB.iterator();
%>
							<tr style="height:25px;">
<%
				while(it.hasNext())
				{
					listHmt = new HashMap();
					listHmt = (HashMap)it.next();
					sum += Integer.parseInt((String)listHmt.get("CNT"));
%>
							
								<td style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=(String)listHmt.get("CNT")%></td>
									
<%
				 
				}
%>
								<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
							</tr>	
<%
		}
%>	

<%
			it = null;
			double avg = 0;	
			double tavg = 0;
			String percent = "";		
			String tpercnet = "";
			if(arrGdataB!=null)
			{
				it = arrGdataB.iterator();
%>
							<tr style="height:25px;">
<%
				while(it.hasNext())
				{
					listHmt = new HashMap();
					listHmt = (HashMap)it.next();
					System.out.println("CNT:"+(String)listHmt.get("CNT"));
					avg = Math.round((Double.parseDouble((String)listHmt.get("CNT")) / sum) * 100) ;
					tavg +=	avg;		
					percent = su.digitFormat(String.valueOf(avg).substring(0,String.valueOf(avg).length()-2),"###");
					tpercnet = su.digitFormat(String.valueOf(tavg).substring(0,String.valueOf(tavg).length()-2),"###");
%>
							
								<td style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=percent%>%</td>
									
<%
				 
				}
%>
								<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=tpercnet%>%</td>
							</tr>	
<%
			}
%>												
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>

			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:170px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_03.gif" align="absmiddle" />&nbsp;일자별 우호도 동향</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:150px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart2 %>">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....		
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:130px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_04.gif" align="absmiddle" />&nbsp;출처별 우호도</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:150px;border: 1px #DDDDDD solid;text-align:center;">
						
						<img src="<%=chart3 %>">
						
						</td>
						<td style="width:10px;">&nbsp;</td>
						<td style="width:220px;">
						<table style="width:100%;border-top:1px #d6d6d6 solid;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">출처</th>
								<th style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">긍정</th>
								<th style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">중립</th>
								<th style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">부정</th>
								<th style="border-bottom:1px #e1e1e1 solid;background:#f9f9f9;font-size:12px;">합계</th>
							</tr>
<%
			sum = 0;
			if(arrGdataC!=null)
			{
				it = arrGdataC.iterator();
				while(it.hasNext())
				{
					listHmt = new HashMap();
					listHmt = (HashMap)it.next();
					sum = 0;
					sum += Integer.parseInt((String)listHmt.get("PCNT"))+Integer.parseInt((String)listHmt.get("NCNT"))+Integer.parseInt((String)listHmt.get("ECNT"));
%>
							<tr>
								<th style="height:25px;border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;background:#fafafa;font-size:12px;"><%=(String)listHmt.get("CATEGORY")%></th>
								<td style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=(String)listHmt.get("PCNT")%></td>
								<td style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=(String)listHmt.get("NCNT")%></td>
								<td style="border-right:1px #e1e1e1 solid;border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=(String)listHmt.get("ECNT")%></td>
								<td style="border-bottom:1px #e1e1e1 solid;text-align:center;font-size:12px;"><%=sum%></td>
							</tr>
<%
				 
			}			
		}
%>
			
						</table>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:100px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_05.gif" align="absmiddle" />&nbsp;관련 정보</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">긍정 <span style="color:#ff5a00;">(<%=arrIssueDataList1.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th bgcolor="#ebebeb" style="height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">제목</th>
						<th bgcolor="#ebebeb" style="width:100px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">출처</th>
						<th bgcolor="#ebebeb" style="width:80px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">날짜</th>
					</tr>
<%				
		String sunghyang = "";
		String important = "";
		String gubun = "";
		for(int i=0; i<arrIssueDataList1.size(); i++){
			
			idBean = (IssueDataBean)arrIssueDataList1.get(i);
			arrIdcBean = new ArrayList();
			arrIdcBean = (ArrayList) idBean.getArrCodeList();
			
			sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
			important = icMgr.GetCodeNameType(arrIdcBean,10);
			gubun = icMgr.GetCodeNameType(arrIdcBean,1);
			
			if(sunghyang.trim().equals("긍정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
			}else if(sunghyang.trim().equals("부정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
			}else if(sunghyang.trim().equals("중립")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
			}
			
			
%>
					<tr>
						<td align="left" style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:left;">&nbsp;<%=sungHyangImg%>&nbsp;<a href="<%=idBean.getId_url()%>" target="_blank"><font style="color:#4d77d2;text-decoration:none;"><%=su.cutString(idBean.getId_title(),35,"...")%></font></a></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;"><%=su.cutString(idBean.getMd_site_name(),10,"...")%></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;color:#198bf6;"><%=idBean.getFormatMd_date("yy.MM.dd") %></td>
					</tr>
<%
			
		}
%>
				</table>
				</td>
			</tr>
			<tr><td style="height:20px;"></td></tr>
			<tr>
				<td align="left" style="text-align:left;font-weight:bold;">부정 <span style="color:#ff5a00;">(<%=arrIssueDataList2.size()%>건)</span></td>
			</tr>
			<tr><td style="height:5px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th bgcolor="#ebebeb" style="height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">제목</th>
						<th bgcolor="#ebebeb" style="width:100px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">출처</th>
						<th bgcolor="#ebebeb" style="width:80px;height:30px;background:url('<%=imgUrl%>board_top_bg.gif');">날짜</th>
					</tr>
<%				
		sunghyang = "";
		important = "";
		gubun = "";
		for(int i=0; i<arrIssueDataList2.size(); i++){
			
			idBean = (IssueDataBean)arrIssueDataList2.get(i);
			arrIdcBean = new ArrayList();
			arrIdcBean = (ArrayList) idBean.getArrCodeList();
			
			sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
			important = icMgr.GetCodeNameType(arrIdcBean,10);
			gubun = icMgr.GetCodeNameType(arrIdcBean,1);
			
			if(sunghyang.trim().equals("긍정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_01.gif\" align=\"absmiddle\" />";
			}else if(sunghyang.trim().equals("부정")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_03.gif\" align=\"absmiddle\" />";	
			}else if(sunghyang.trim().equals("중립")){
				sungHyangImg = "<img src= \""+imgUrl+"icon_tend_02.gif\" align=\"absmiddle\" />";
			}
			
			
%>
					<tr>
						<td align="left" style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:left;">&nbsp;<%=sungHyangImg%>&nbsp;<a href="<%=idBean.getId_url()%>" target="_blank"><font style="color:#4d77d2;text-decoration:none;"><%=su.cutString(idBean.getId_title(),35,"...")%></font></a></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;"><%=su.cutString(idBean.getMd_site_name(),10,"...")%></td>
						<td style="height:25px;border-bottom:1px #f0f0f0 solid;text-align:center;color:#198bf6;"><%=idBean.getFormatMd_date("yy.MM.dd") %></td>
					</tr>
<%
			
		}
%>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>		
<%
	}if(ir_type.equals(ReportTypeConstants.getPeoridVal())){		
		
		imgUrl = siteUrl+"images/report/";
%>	
<style>
td,table,body{font-size:12px;font-family:dotum,돋움;}
</style>
<table width="650" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="27"><img src="<%=imgUrl%>top_01.gif" /></td>
						<td style="background:#143b6b;text-align:center;font-size:12px;color:#c0ff00;font-weight:bold;"><%=formatSdate%> ~ <%=formatEdate%></td>
						<td width="390"><img src="<%=imgUrl%>top_02_2.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>top_03_5.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="border-left:4px #2a77d4 solid;border-right:4px #2a77d4 solid;">

		<table width="620" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:280px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_01.gif" align="absmiddle" />&nbsp;발생 콘텐츠 추이 (:codeName) </td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:150px;border: 1px #DDDDDD solid;text-align:center;">
						<img src="<%=chart1 %>">									
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:30px;"></td></tr>
			<tr>
				<td>
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="line-height:30px;width:110px;border-bottom:2px #1e90c6 solid;font-size:14px;color:#164a8a;font-weight:bold;"><img src="<%=imgUrl%>icon_02.gif" align="absmiddle" />&nbsp;SUMMARY</td>
						<td style="border-bottom:2px #dbdbdb solid;">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="height:10px;"></td></tr>
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="<%=imgUrl%>round_box_top.gif" /></td>
					</tr>
					<tr>
						<td align="left" style="text-align:left;border-left:3px #f1ecd6 solid;border-right:3px #f1ecd6 solid;background:#fffcf1;padding-left:10px;padding-right:10px;color:#5d4901;line-height:160%;">
						....
						</td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>round_box_btm.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td><img src="<%=imgUrl%>btm.gif" /></td>
	</tr>
</table>		
<%
	}
%>

</body>
</html>
