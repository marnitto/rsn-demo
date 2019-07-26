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
					,java.util.Iterator
					"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	
	MakeTypeChart irChart = new MakeTypeChart();
	
	IssueMgr ismgr = new IssueMgr();
	IssueDataBean idBean = null;

	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"images/report/";
	
	//차트 경로
	String filePath = cu.getConfig("CHARTPATH")+"report"+ReportTypeConstants.peoridVal+"/";
	String chartUrl = cu.getConfig("CHARTURL")+"report"+ReportTypeConstants.peoridVal+"/";
	
	String id_seqs_ceo = pr.getString("id_seqs_ceo","");
	String id_seqs_pro = pr.getString("id_seqs_pro","");
	String id_seqs_isu = pr.getString("id_seqs_isu","");
	
	String sdate = pr.getString("sdate");
	String edate = pr.getString("edate");
	sdate = du.getDate(sdate,"yyyy-MM-dd");
	edate = du.getDate(edate,"yyyy-MM-dd");
	
	String issue_sDate = pr.getString("issue_sDate");
	String issue_eDate = pr.getString("issue_eDate");
	
	//보고서 날짜
	String ir_stime = pr.getString("stime");
	String ir_etime = pr.getString("etime");

	//시간값 자릿수에 따른 처리
	ir_stime = ir_stime+":00:00";
	ir_etime = ir_etime+":00:00";

	ArrayList ceoList_1 = new ArrayList(); 
	ArrayList ceoList_2 = new ArrayList();
	ArrayList proList_1 = new ArrayList();
	ArrayList proList_2 = new ArrayList();
	ArrayList isuList1 = new ArrayList();
	ArrayList isuList2 = new ArrayList();
	
	ArrayList chartList = new ArrayList();
	ArrayList chartList2 = new ArrayList();
	ArrayList chartList2_1 = new ArrayList();
	ArrayList chartList3 = new ArrayList();
	ArrayList chartList3_1 = new ArrayList();
	String Chart1 ="";
	String Chart2 ="";
	String Chart3 ="";
	String Chart4 ="";
	
	//자사 관련 
	if(!id_seqs_ceo.equals("")){
		ceoList_1 = ismgr.getReportDataList(id_seqs_ceo, "1", sdate, edate, ir_stime, ir_etime, "A"); //언론
		ceoList_2 = ismgr.getReportDataList(id_seqs_ceo, "2", sdate, edate, ir_stime, ir_etime, "A"); //개인
	}
	//상품 관련
	if(!id_seqs_pro.equals("")){
		proList_1 = ismgr.getReportDataList(id_seqs_pro, "1", sdate, edate, ir_stime, ir_etime, "B"); //언론
		proList_2 = ismgr.getReportDataList(id_seqs_pro, "2", sdate, edate, ir_stime, ir_etime, "B"); //개인
	}
	
	//온라인 관련정보
		if(!id_seqs_isu.equals("")){
			isuList1 = ismgr.getReportDataList(id_seqs_isu, "1", sdate, edate, ir_stime, ir_etime, "C");
			isuList2 = ismgr.getReportDataList(id_seqs_isu, "2", sdate, edate, ir_stime, ir_etime, "C");
		}
	
	//정보그룹별성향
	chartList = ismgr.getChartDataListSet("3", sdate+" "+ir_stime, edate+" "+ir_etime);
	Chart1 = irChart.makeStackBar(1, chartList, "Chart1"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 346, 223, "vertical", "percentage");
	
	//정보속성별 차트
	chartList2_1 = ismgr.getChartDataListSet2("3", issue_sDate+" "+ir_stime, issue_eDate+" "+ir_etime, "chart");	
	Chart2 = irChart.make3DPieChart(1, chartList2_1, "Chart2"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 334, 225);
	
	//이슈의 출처별 정보량 점유율 및 부정율
	//표
	chartList2 = ismgr.getChartDataListSet2("3", issue_sDate+" "+ir_stime, issue_eDate+" "+ir_etime, "list");
	double totals = 0.0;
	String cnt = "";
	HashMap map = new HashMap();
	if(chartList2.size() > 0){
		for(int i=0; i < chartList2.size(); i++){
			map = (HashMap)chartList2.get(i);
			cnt = map.get("TOTAL")+"";
			totals += Double.parseDouble(cnt);
		}
	}
	
	//이슈의 정보량 및 성향 추이
	chartList3 = ismgr.getChartDataListSet3("3", issue_sDate+" "+ir_stime, issue_eDate+" "+ir_etime, "list");
	chartList3_1 = ismgr.getChartDataListSet3("3", issue_sDate+" "+ir_stime, issue_eDate+" "+ir_etime, "chart");
	Chart4 = irChart.makeStackBar_2015(1, chartList3_1, "Chart4"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 681, 249, "vertical", "");
	System.out.println(Chart1);
	System.out.println(Chart2);
	System.out.println(Chart3);
	System.out.println(Chart4);
	
	//정보량합계
	int hap = 0;
	//평균부정율
	double ever = 0.0;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="GENERATOR" content="ActiveSquare">
<title>금융감독원 보고서</title>
<style type="text/css">
	*{font-family:'맑은고딕','Malgun gothic'}
</style>
</head>
<body style="padding:0;margin:0">
<table border="0" cellSpacing="0" cellPadding="0" width="750" align="center" style="table-layout:fixed">
<tbody>
<tr>
<td><img src="<%=imgUrl%>report_header_logo_del.gif" alt="Online Daily Buzz Report - Samsungfire" /></td>
</tr>
<tr>
<td style="height:31px;padding-top:0;padding-right:13px;padding-bottom:0;padding-left:13px;border-top:1px solid #dfdfdf;border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;border-left:1px solid #dfdfdf;background-color:#ededed">
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="margin-top:2px">
	<tbody>
	<tr>
	<td align="left" style="width:15px"><img src="<%=imgUrl%>bullet_00.gif" alt="!" /></td>
	<td align="left" style="width:362px;color:#666666;font-size:11px">본 내용은 <strong style="color:#106b87;text-decoration:underline">대외비</strong>로 <strong style="color:#106b87">보안에 유의</strong>하시기 바랍니다.</td>
	<td align="right">
		<table border="0" cellSpacing="0" cellPadding="0">
		<tr>
		<td style="color:#666666;font-size:11px"><strong>보고기간</strong></td>
		<td style="padding-left:8px;padding-right:8px;color:#666666;font-size:11px"><img src="<%=imgUrl%>line_00.gif" alt="|" /></td>
		<td style="color:#666666;font-size:11px"><%=sdate.replaceAll("-",".")%> <%=pr.getString("stime")%>시 ~ <%=edate.replaceAll("-",".")%> <%=pr.getString("etime")%>시</td>
		</tr>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
</td>
</tr>
<tr>
<td style="padding-top:18px;padding-right:16px;padding-bottom:40px;padding-left:16px;border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;border-left:1px solid #dfdfdf">
	<!-- 자사/CEO 관련 콘텐츠 현황 -->
	<p style="margin-bottom:15px"><img src="<%=imgUrl%>h2_title_00.gif" alt="자사/CEO 관련 콘텐츠 현황" /></p>
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
	<tbody>
	<tr>
	<td align="left" style="width:600px;padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>언론기사</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:12px;padding-right:0;padding-bottom:0;padding-left:0;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tbody>
		<tr><td style="height:2px;background:#6189a4;font-size:0;line-height:0;overflow:hidden"></td></tr>
		</tbody>
		</table>
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		
		<%
		if(ceoList_1.size() > 0){%>
			<tr>
			<td align="center" style="width:60px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>성향</strong></td>
			<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>제목</strong></td>
			<td align="center" style="width:90px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>최초보도</strong></td>
			<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>수집시간</strong></td>
			<td align="center" style="width:100px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>확산언론</strong></td>
			<td align="center" style="width:80px;padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>유사기사건수</strong></td>
			</tr>	
			<%
			String type9 = "";
			String color = "";
			for(int i=0; i <ceoList_1.size(); i++){
				idBean = (IssueDataBean)ceoList_1.get(i);
				if(idBean.getIc_code().equals("1")){
					type9 ="긍정";
					color = "color:#348cc2";
				}else if(idBean.getIc_code().equals("2")){
					type9 ="중립";
					color = "color:#5a9634";
				}else{
					type9 ="부정";
					color = "color:#c63d3c";
				}
		%>
		<tr>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;<%=color%>;font-size:11px;word-break:break-all"><strong><%=type9%></strong></td>
		<td align="left" style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><a href="http://hub.buzzms.co.kr?url=<%=idBean.getId_urlEncoding()%>" target="_blank" style="display:block;color:#666666;text-decoration:none;overflow:hidden;white-space:nowrap;text-overflow:ellipsis"><%=idBean.getId_title()%></a></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_site_name()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_date()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_same_ct()%></td>
		</tr>
		<% }
		}else{ %>
		<tr>
		<td style="height:60px;font-size:9pt;text-align:center">관련정보없음</td>
		</tr>
		<%} %>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:10px;padding-bottom:20px;color:#333333;font-size:11px">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
		<tbody>
		<tr>
		<td style="padding-top:20px;padding-right:15px;padding-left:15px;border-top:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;border-left:1px solid #d7d7d7;background-color:#f1f1f1">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
			<tbody>
			<tr>
			<td style="height:30px;padding-left:5px;vertical-align:top"><img src="<%=imgUrl%>bullet_03.gif" alt="-" /></td>
			</tr>
			<tr>
			<td style="height:1px;background:#c5c5c5;font-size:0;line-height:0;overflow:hidden"></td>
			</tr>
			<tr>
			<td style="padding-top:8px;color:#666666;font-weight:bold;font-size:11px">
				<ol>
					<li style="margin-bottom:15px">내용입력</li>
				</ol>
			</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td align="left" style="padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>SNS</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:12px;padding-right:0;padding-bottom:0;padding-left:0;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tbody>
		<tr><td style="height:2px;background:#6189a4;font-size:0;line-height:0;overflow:hidden"></td></tr>
		</tbody>
		</table>
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		
		<%
		if(ceoList_2.size() > 0){
			%>
		<tr>
		<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>구분</strong></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>제목</strong></td>
		<td align="center" style="width:90px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>게시자</strong></td>
		<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>수집시간</strong></td>
		<td align="center" style="width:60px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>영향력</strong></td>
		<td align="center" style="width:70px;padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>확산 건수</strong></td>
		</tr>
			<%
			String type9 = "";
			String url = "";
			String caffeUrl = ""; 
			for(int i=0; i <ceoList_2.size(); i++){
				idBean = (IssueDataBean)ceoList_2.get(i);
				if(idBean.getS_seq().equals("3555")){
					//네이버까페
					//caffeUrl = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + java.net.URLEncoder.encode(idBean.getId_title(),"utf-8");
					url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + idBean.getId_title();
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
					
				}else if(idBean.getS_seq().equals("4943")){
					//다음까페
					url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + idBean.getId_title().replaceAll(" ", "");
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
				}else{
					caffeUrl = idBean.getId_url();
				}
		%>
		<tr>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_site_name() %></td>
		<td align="left" style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><a href="<%=caffeUrl%>" target="_blank" style="display:block;color:#666666;text-decoration:none;overflow:hidden;white-space:nowrap;text-overflow:ellipsis"><%=idBean.getId_title()%></a></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_date()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getIc_influence()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_same_ct()%></td>
		</tr>
		<%}
			}else{ %>
		<tr>
		<td style="height:60px;font-size:9pt;text-align:center">관련정보없음</td>
		</tr>
		<%} %>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:10px;padding-bottom:20px;color:#333333;font-size:11px">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
		<tbody>
		<tr>
		<td style="padding-top:20px;padding-right:15px;padding-left:15px;border-top:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;border-left:1px solid #d7d7d7;background-color:#f1f1f1">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
			<tbody>
			<tr>
			<td style="height:30px;padding-left:5px;vertical-align:top"><img src="<%=imgUrl%>bullet_03.gif" alt="-" /></td>
			</tr>
			<tr>
			<td style="height:1px;background:#c5c5c5;font-size:0;line-height:0;overflow:hidden"></td>
			</tr>
			<tr>
			<td style="padding-top:8px;color:#666666;font-weight:bold;font-size:11px">
				<ol>
					<li style="margin-bottom:15px">내용입력</li>
				</ol>
			</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
	
	<!-- 상품 관련 콘텐츠 현황 -->
	<p style="margin-bottom:15px"><img src="<%=imgUrl%>h2_title_01.gif" alt="상품 관련 콘텐츠 현황" /></p>
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
	<tbody>
	<tr>
	<td align="left" style="width:600px;padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>언론기사</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:12px;padding-right:0;padding-bottom:0;padding-left:0;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tbody>
		<tr><td style="height:2px;background:#6189a4;font-size:0;line-height:0;overflow:hidden"></td></tr>
		</tbody>
		</table>
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		
		<%
		if(proList_1.size() > 0){%>
			<tr>
			<td align="center" style="width:60px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>성향</strong></td>
			<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>제목</strong></td>
			<td align="center" style="width:90px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>최초보도</strong></td>
			<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>수집시간</strong></td>
			<td align="center" style="width:100px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>확산언론</strong></td>
			<td align="center" style="width:80px;padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>유사기사건수</strong></td>
			</tr>
			<%
			String type9 = "";
			String color = "";
			for(int i=0; i <proList_1.size(); i++){
				idBean = (IssueDataBean)proList_1.get(i);
				if(idBean.getIc_code().equals("1")){
					type9 ="긍정";
					color = "color:#348cc2";
				}else if(idBean.getIc_code().equals("2")){
					type9 ="중립";
					color = "color:#5a9634";
				}else{
					type9 ="부정";
					color = "color:#c63d3c";
				}
		%>
		<tr>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;<%=color%>;font-size:11px;word-break:break-all"><strong><%=type9%></strong></td>
		<td align="left" style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><a href="http://hub.buzzms.co.kr?url=<%=idBean.getId_urlEncoding()%>" target="_blank" style="display:block;color:#666666;text-decoration:none;overflow:hidden;white-space:nowrap;text-overflow:ellipsis"><%=idBean.getId_title()%></a></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_site_name()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_date()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_same_ct()%></td>
		</tr>
		<% }
		}else{ %>
		<tr>
		<td style="height:60px;font-size:9pt;text-align:center">관련정보없음</td>
		</tr>
		<%} %>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:10px;padding-bottom:20px;color:#333333;font-size:11px">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
		<tbody>
		<tr>
		<td style="padding-top:20px;padding-right:15px;padding-left:15px;border-top:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;border-left:1px solid #d7d7d7;background-color:#f1f1f1">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
			<tbody>
			<tr>
			<td style="height:30px;padding-left:5px;vertical-align:top"><img src="<%=imgUrl%>bullet_03.gif" alt="-" /></td>
			</tr>
			<tr>
			<td style="height:1px;background:#c5c5c5;font-size:0;line-height:0;overflow:hidden"></td>
			</tr>
			<tr>
			<td style="padding-top:8px;color:#666666;font-weight:bold;font-size:11px">
				<ol>
					<li style="margin-bottom:15px">내용입력</li>
				</ol>
			</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td align="left" style="padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>SNS</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:12px;padding-right:0;padding-bottom:0;padding-left:0;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tbody>
		<tr><td style="height:2px;background:#6189a4;font-size:0;line-height:0;overflow:hidden"></td></tr>
		</tbody>
		</table>
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		<%
		if(proList_2.size() > 0){%>
		<tr>
		<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px">구분</td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>제목</strong></td>
		<td align="center" style="width:90px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>게시자</strong></td>
		<td align="center" style="width:120px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>수집시간</strong></td>
		<td align="center" style="width:60px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>영향력</strong></td>
		<td align="center" style="width:70px;padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;background-color:#eeeeee;color:#333333;font-size:11px;line-height:14px"><strong>확산 건수</strong></td>
		</tr>
		<%
			String type9 = "";
			String url = "";
			String caffeUrl = "";
			for(int i=0; i <proList_2.size(); i++){
				idBean = (IssueDataBean)proList_2.get(i);
				if(idBean.getS_seq().equals("3555")){
					//네이버까페
					//caffeUrl = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + java.net.URLEncoder.encode(idBean.getId_title(),"utf-8");
					url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + idBean.getId_title();
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
					
				}else if(idBean.getS_seq().equals("4943")){
					//다음까페
					url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + idBean.getId_title().replaceAll(" ", "");
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
				}else{
					caffeUrl = idBean.getId_url();
				}
		%>
		<tr>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_site_name() %></td>
		<td align="left" style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><a href="<%=caffeUrl%>" target="_blank" style="display:block;color:#666666;text-decoration:none;overflow:hidden;white-space:nowrap;text-overflow:ellipsis"><%=idBean.getId_title()%></a></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_date()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getIc_influence()%></td>
		<td align="center" style="padding-top:10px;padding-bottom:10px;border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;border-left:1px solid #e0e0e0;color:#666666;font-size:11px;word-break:break-all"><%=idBean.getMd_same_ct()%></td>
		</tr>
		<%}
			}else{ %>
		<tr>
		<td style="height:60px;font-size:9pt;text-align:center">관련정보없음</td>
		</tr>
		<%} %>
		</tbody>
		</table>
	</td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:10px;padding-bottom:20px;color:#333333;font-size:11px">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
		<tbody>
		<tr>
		<td style="padding-top:20px;padding-right:15px;padding-left:15px;border-top:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;border-left:1px solid #d7d7d7;background-color:#f1f1f1">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:18px;table-layout:fixed">
			<tbody>
			<tr>
			<td style="height:30px;padding-left:5px;vertical-align:top"><img src="<%=imgUrl%>bullet_03.gif" alt="-" /></td>
			</tr>
			<tr>
			<td style="height:1px;background:#c5c5c5;font-size:0;line-height:0;overflow:hidden"></td>
			</tr>
			<tr>
			<td style="padding-top:8px;color:#666666;font-weight:bold;font-size:11px">
				<ol>
					<li style="margin-bottom:15px">내용입력</li>
				</ol>
			</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>

	<!-- 주요 이슈 동향 -->
	<p style="margin-bottom:15px"><img src="<%=imgUrl%>h2_title_02.gif" alt="주요 이슈 동향" /></p>
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
	<tbody>
	<tr>
	<td colspan="2" align="left" style="padding-top:8px;padding-bottom:8px;padding-left:13px;border-top:1px solid #b6cbd7;border-right:1px solid #b6cbd7;border-bottom:1px solid #b6cbd7;border-left:1px solid #b6cbd7;background:#d1e0e9;font-size:0;line-height:0">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="height:18px;overflow:hidden">
		<tbody>
		<tr>
		<td style="width:20px"><img src="<%=imgUrl%>bullet_04.gif" alt="-" /></td>
		<td><strong style="padding-left:5px;color:#040000;font-size:14px;vertical-align:middle">금융감독원, 부정적 관찰 대상 이슈</strong></td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	<tr><td style="height:5px;font-size:0;line-height:0"></td></tr>
	<tr>
	<td align="left" style="width:600px;padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>주요 이슈 분석</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-bottom:20px;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="border-left:2px solid #e1e1e1;border-right:2px solid #e1e1e1;border-bottom:2px solid #e1e1e1;font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		<tr>
		<td height="30" style="padding-left:16px;border-bottom:1px solid #e1e1e1;background:#f0f0f0"><img src="<%=imgUrl%>bullet_02.gif" alt="." style="vertical-align:middle" /><strong style="color:#333333;font-size:11px">이슈의 정보량 및 성향 추이</strong></td>
		</tr>
		<tr>
		<td style="padding-top:30px;text-align:center"><img src="<%=Chart4%>" alt="이슈의 정보량 및 성향 추이 그래프" style="max-width:680px" /></td>
		</tr>
		<tr>
		<td style="padding-top:15px;padding-right:15px;padding-bottom:20px;padding-left:15px">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
			<tbody>
			<tr><td style="height:2px;background:#8ba8bc;font-size:0;line-height:0;overflow:hidden"></td></tr>
			</tbody>
			</table>
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
			<tbody>
			<tr>
			<th style="width:48px;padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee"></th>
			<% if(chartList3.size() > 0){
				HashMap mapData= new HashMap();
				for(int i=0; i < chartList3.size(); i++){
					mapData = (HashMap)chartList3.get(i);
			%>
			<th style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"><%=mapData.get("MD_DATE")%></th>
			<%}}else{%>
			<th style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"></th>
			<%} %>
			<th style="width:87px;padding-top:10px;padding-bottom:10px;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden">정보량합계<br />및<br />평균부정률</th>
			</tr>
			<tr>
			<th style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break:break-all">정보량</th>
			<% if(chartList3.size() > 0){
				HashMap mapData= new HashMap();
				for(int i=0; i < chartList3.size(); i++){
					mapData = (HashMap)chartList3.get(i);
					hap +=  Integer.parseInt(mapData.get("TOTAL")+"");
			%>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"><%=mapData.get("TOTAL")%></td>
			<%}}else{%>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"></td>
			<%} %>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"><%=hap%></td>
			</tr>
			<tr>
			<th style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#c32a29;font-size:11px;font-weight:bold;text-align:center;word-break:break-all;overflow:hidden">부정율</th>
			<% if(chartList3.size() > 0){
				HashMap mapData= new HashMap();
				hap=0;
				int length = chartList3.size();
				for(int i=0; i < chartList3.size(); i++){
					mapData = (HashMap)chartList3.get(i);
					hap +=  Integer.parseInt(mapData.get("NEG")+"");
					ever =  hap/length;
					ever = Math.round(ever*10)/10;
			%>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"><%=mapData.get("NEG")%>%</td>
			<%}}else{%>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"></td>
			<%} %>
			<td style="padding-top:10px;padding-bottom:10px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all;overflow:hidden"><%=ever%>%</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		<tr>
		<td height="30" style="padding-left:16px;border-top:1px solid #e1e1e1;border-bottom:1px solid #e1e1e1;background:#f0f0f0"><img src="<%=imgUrl%>bullet_02.gif" alt="." style="vertical-align:middle" /><strong style="color:#333333;font-size:11px">이슈의 출처별 정보량 점유율 및 부정율</strong></td>
		</tr>
		<tr>
		<td style="padding-top:25px;padding-right:16px;padding-bottom:25px;padding-left:16px">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
			<colgroup>
			<col style="width:334px">
			<col>
			</colgroup>
			<tbody>
			<tr>
			<td style="vertical-align:middle;text-align:center"><img src="<%=Chart2 %>" alt="차트"></td>
			<td style="padding-left:20px;vertical-align:top">
				<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
				<tbody>
				<tr><td style="height:2px;background:#8ba8bc;font-size:0;line-height:0;overflow:hidden"></td></tr>
				</tbody>
				</table>
				<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
				<tbody>
				<tr>
				<td style="width:97px;height:37px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee"></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break:break-all">정보량</td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break:break-all">점유율</td>
				<td style="width:70px;border-bottom:1px solid #dedddd;background:#eeeeee;color:#c32a29;font-size:11px;font-weight:bold;text-align:center;word-break:break-all">부정율</td>
				</tr>
				<%
				double percent = 0.0; 
				double negPer = 0.0;
				double jungboNum = 0.0;
				
				String jungbo ="";
				String negative = "";
				if(chartList2.size() > 0){
					HashMap dataMap = new HashMap(); 
					for(int i =0; i < chartList2.size(); i++){
						dataMap = (HashMap)chartList2.get(i);
						jungbo = dataMap.get("TOTAL")+"";
						
						negative = dataMap.get("NEG")+"";
						jungboNum = Double.parseDouble(jungbo);
						if(!jungbo.equals("0")){
							percent = (Double.parseDouble(jungbo)/totals)*100;
							percent = (Math.floor(percent*10))/10;	
						}
						//negPer = (Double.parseDouble(negative)/jungboNum)*100;
						//negPer = Math.floor(negPer*10)/10;
				%>
				<tr>
				<th style="height:31px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break:break-all"><%=dataMap.get("SITE")%></th>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all"><%=dataMap.get("TOTAL")%></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all"><%=percent%>%</td>
				<td style="border-bottom:1px solid #dedddd;color:#c32a29;font-size:11px;text-align:center;word-break:break-all"><%=dataMap.get("RATE")%>%</td>
				</tr>
				
				<%	}
				}else{ %>
				<tr>
				<th style="height:31px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break:break-all"></th>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all"></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break:break-all"></td>
				<td style="border-bottom:1px solid #dedddd;color:#c32a29;font-size:11px;text-align:center;word-break:break-all">5,467</td>
				</tr>
				
				<%} %>
				</tbody>
				</table>
			</td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
	<!-- 추가 컨텐츠(2015.03.03) -->
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tr>
		<td style="padding-top:15px;padding-right:15px;padding-bottom:20px;padding-left:15px">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="background:#f2f2f2;table-layout:fixed">
			<tr>
			<td style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px">
			<span style="font-size:12px;font-weight:bold">※ 보고서 참고사항</span>
			<ul style="list-style-type:none;padding:0;margin:0;margin-top:5px;color:#666666;font-size:11px;line-height:18px">
			<li>1. 언론기사 확산언론 기준 – 주요 관리 매체(Tier)에 포함되어 있는 매체</li>
			<li>2. SNS 영향력 구분 기준 – 파워(트위터,팔로워 5,000명 이상/파워블로거), 일반(트위터,팔로워 5,000명 미만/일반블로거)</li>
			<li>3. 유사 기사 건수, 확산 건수, 정보 그룹별 성향의 상세 기사 리스트는 대시보드 Main 탭에서 확인하실 수 있습니다.</li>
			</ul>
			</td>
			</tr>
			</table>
		</td>
		</tr>
		<!-- // 추가 컨텐츠(2015.03.03) -->
	</table>
	<!-- Daily Analysis -->
	<p style="margin-bottom:15px"><img src="<%=imgUrl%>h2_title_04.gif" alt="Daily Analysis" /></p>
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
	<tbody>
	<tr>
	<td align="left" style="width:600px;padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>정보 속성별 성향 및 수량</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-bottom:20px;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="border-left:2px solid #e1e1e1;border-right:2px solid #e1e1e1;border-bottom:2px solid #e1e1e1;font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
		<tbody>
		<tr>
		<td height="30" style="padding-left:16px;border-bottom:1px solid #e1e1e1;background:#f0f0f0"><img src="<%=imgUrl%>bullet_02.gif" alt="." style="vertical-align:middle" /><strong style="color:#333333;font-size:11px">정보 그룹별 성향</strong></td>
		</tr>
		<tr>
		<td style="padding-top:15px;padding-right:15px;padding-bottom:20px;padding-left:15px">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
			<tbody>
			<tr>
			<td style="padding-right:20px;vertical-align:top">
				<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
				<tbody>
				<tr><td style="height:2px;background:#8ba8bc;font-size:0;line-height:0;overflow:hidden"></td></tr>
				</tbody>
				</table>
				<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;line-height:inherit;table-layout:fixed">
				<tbody>
				<tr>
				<td style="width:87px;height:37px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;text-align:center;word-break;break-all">구분</td>
				<td style="width:58px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#386bb4;font-size:11px;text-align:center;word-break;break-all">긍정</td>
				<td style="width:58px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#c32a29;font-size:11px;text-align:center;word-break;break-all">부정</td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;background:#eeeeee;color:#6d9031;font-size:11px;text-align:center;word-break;break-all">중립</td>
				<td style="width:57px;border-bottom:1px solid #dedddd;background:#eeeeee;color:#666666;font-size:11px;text-align:center;word-break;break-all">합계</td>
				</tr>
				<%
				if(chartList.size() > 0 ){
					HashMap dataMap = new HashMap();
					int total = 0;
					String pos="";
					String neg="";
					String neu="";
					for(int i=0 ; i < chartList.size(); i ++){
						dataMap = (HashMap)chartList.get(i);
						pos = dataMap.get("PCNT")+"";
						neg = dataMap.get("NCNT")+"";
						neu = dataMap.get("ECNT")+"";
						total = Integer.parseInt(pos)+Integer.parseInt(neg)+Integer.parseInt(neu);
				%>
				<tr>
				<th style="height:31px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break;break-all"><%=dataMap.get("CATEGORY")%></th>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"><%=dataMap.get("PCNT")%></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"><%=dataMap.get("NCNT")%></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"><%=dataMap.get("ECNT")%></td>
				<td style="border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break;break-all"><%=total%></td>
				</tr>
				<%
					}
				}else{ %>
				<tr>
				<th style="height:31px;border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break;break-all"></th>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"></td>
				<td style="border-right:1px solid #dedddd;border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:normal;text-align:center;word-break;break-all"></td>
				<td style="border-bottom:1px solid #dedddd;color:#666666;font-size:11px;font-weight:bold;text-align:center;word-break;break-all"></td>
				</tr>
				<%} %>
				</tbody>
				</table>
			</td>
			<td width="336" style="vertical-align:middle;text-align:center"><img src="<%=Chart1 %>" alt="차트"></td>
			</tr>
			</tbody>
			</table>
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
	
	<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
	<tbody>
	<tr>
	<td align="left" style="width:600px;padding-left:10px;height:30px;position:relative;border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-left:1px solid #1e6ea8;background:#297eb9;color:#ffffff;font-size:14px"><strong>온라인 관련 정보</strong></td>
	<td align="right" style="border-top:1px solid #1e6ea8;border-bottom:1px solid #1e6ea8;border-right:1px solid #1e6ea8;background:#297eb9"><img src="<%=imgUrl%>bullet_01.gif" alt="." style="vertical-align:middle" /></td>
	</tr>
	<tr>
	<td colspan="2" style="padding-top:9px;padding-bottom:20px;border-top:2px solid #d9d9d9">
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="height:2px;font-size:0;line-height:0;overflow:hidden;table-layout:fixed">
		<tbody>
		<tr><td style="height:2px;background:#8ba8bc;font-size:0;line-height:0;overflow:hidden"></td></tr>
		</tbody>
		</table>
		<table border="0" cellSpacing="0" cellPadding="0" width="100%" style="font-family:inherit;color:inherit;font-size:inherit;table-layout:fixed">
		<tbody>
		<tr>
		<td style="width:60px;padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;border-bottom:1px solid #dfdedf;border-left:1px solid #dfdedf;background-color:#f0efef;color:#666666;font-size:9pt;font-weight:bold;text-align:center">구분</td>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;border-bottom:1px solid #dfdedf;background-color:#f0efef;color:#666666;font-size:9pt;font-weight:bold;text-align:center">제목</td>
		<td style="width:113px;padding-top:9px;border-right:1px solid #dfdedf;padding-bottom:9px;border-bottom:1px solid #dfdedf;background-color:#f0efef;color:#666666;font-size:9pt;font-weight:bold;text-align:center">출처</td>
		<td style="width:124px;padding-top:9px;border-right:1px solid #dfdedf;padding-bottom:9px;border-bottom:1px solid #dfdedf;background-color:#f0efef;color:#666666;font-size:9pt;font-weight:bold;text-align:center">수집시간</td>
		</tr>
		<%if(isuList1.size() > 0){ 
			String alt = "";
			String num = isuList1.size()+"";
			for(int i=0; i < isuList1.size(); i++){
				idBean = (IssueDataBean)isuList1.get(i);
				if(idBean.getIc_code().equals("1")){
					alt = "긍정";
				}else if(idBean.getIc_code().equals("2")){
					alt = "중립";
				}else{
					alt = "부정";
				}
		%>
		<tr>
		<%if(i == 0){ %>		
		<td rowspan="<%=num%>" style="padding-top:9px;padding-bottom:9px;border-right:1px solid #e8e8e8;border-bottom:1px solid #e8e8e8;border-left:1px solid #e8e8e8;color:#919191;font-size:9pt;line-height:16px;text-align:center;word-break;break-all">언론</td>
		<%} %>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;border-bottom:1px solid #e8e8e8">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="table-layout:fixed">
			<tbody>
			<tr>
			<td style="width:50px;padding-top:3px;vertical-align:top"><span style="padding-left:11px"><img src="<%=imgUrl%>icon_<%=idBean.getIc_code()%>.gif" alt="<%=alt%>"></span></td>
			<td><a href="http://hub.buzzms.co.kr?url=<%=idBean.getId_urlEncoding()%>" target="_blank" style="color:#666666;font-size:9pt;line-height:15px;text-decoration:none;word-break;break-all"><%=idBean.getId_title()%></a></td>
			</tr>
			</tbody>
			</table>
		</td>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;color:#3082bd;font-size:9pt;line-height:16px;text-align:center;border-bottom:1px solid #e8e8e8;word-break;break-all"><%=idBean.getMd_site_name()%></td>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;color:#919191;font-size:9pt;line-height:16px;text-align:center;border-bottom:1px solid #e8e8e8;word-break;break-all"><%=idBean.getMd_date()%></td>
		</tr>
		<%}}%>
		<%if(isuList2.size() > 0){ 
			String alt = "";
			String num = isuList2.size()+"";
			String url = "";
			String caffeUrl = "";
			for(int i=0; i < isuList2.size(); i++){
				idBean = (IssueDataBean)isuList2.get(i);
				if(idBean.getIc_code().equals("1")){
					alt = "긍정";
				}else if(idBean.getIc_code().equals("2")){
					alt = "중립";
				}else{
					alt = "부정";
				}
				if(idBean.getMd_site_name().equals("네이버 카페")){
					//네이버까페
					url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + idBean.getId_title();
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
					
				}else if(idBean.getMd_site_name().equals("다음 카페")){
					//다음까페
					url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + idBean.getId_title().replaceAll(" ", "");
					caffeUrl = "http://hub.buzzms.co.kr?url="+java.net.URLEncoder.encode(url, "utf-8");
				}else{
					caffeUrl = idBean.getId_urlEncoding();
				}
		%>
		<tr>
		<%if(i == 0){ %>		
		<td rowspan="<%=num%>" style="padding-top:9px;padding-bottom:9px;border-right:1px solid #e8e8e8;border-bottom:1px solid #e8e8e8;border-left:1px solid #e8e8e8;color:#919191;font-size:9pt;line-height:16px;text-align:center;word-break;break-all">개인</td>
		<%} %>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;border-bottom:1px solid #e8e8e8">
			<table border="0" cellSpacing="0" cellPadding="0" width="100%" height="2" style="table-layout:fixed">
			<tbody>
			<tr>
			<td style="width:50px;padding-top:3px;vertical-align:top"><span style="padding-left:11px"><img src="<%=imgUrl%>icon_<%=idBean.getIc_code()%>.gif" alt="<%=alt%>"></span></td>
			<td><a href="http://hub.buzzms.co.kr?url=<%=idBean.getId_urlEncoding()%>" target="_blank" style="color:#666666;font-size:9pt;line-height:15px;text-decoration:none;word-break;break-all"><%=idBean.getId_title()%></a></td>
			</tr>
			</tbody>
			</table>
		</td>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;color:#3082bd;font-size:9pt;line-height:16px;text-align:center;border-bottom:1px solid #e8e8e8;word-break;break-all"><%=idBean.getMd_site_name()%></td>
		<td style="padding-top:9px;padding-bottom:9px;border-right:1px solid #dfdedf;color:#919191;font-size:9pt;line-height:16px;text-align:center;border-bottom:1px solid #e8e8e8;word-break;break-all"><%=idBean.getMd_date()%></td>
		</tr>
		<%}}%>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
	
	
</td>
</tr>
<tr>
<td style="height:46px;padding-left:20px;border-top:1px solid #dfdfdf;border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;border-left:1px solid #dfdfdf;background-color:#efefef"><img src="<%=imgUrl%>mail_copy.gif" alt="Samsungfire" /></td>
</tr>
</tbody>
</table>
<!-- 이메일 수신 확인을 위한 img 태그 시작 -->
<div style="width:0;height:0;font-size:0;line-height:0;text-indent:-9999px"><img src="report_receipt" style="display:none;width:0;height:0;visibility:hidden"></div>
<!-- 이메일 수신 확인을 위한 img 태그 끝-->
</body>
</html>