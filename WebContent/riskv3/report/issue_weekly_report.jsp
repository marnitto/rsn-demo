<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.util.ConfigUtil"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueReportMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@ page import="risk.issue.ReportTypeConstants"%>
<%@ page import="risk.JfreeChart.MakeTypeChart"%>
<%
	ParseRequest pr = new ParseRequest(request);
	IssueReportMgr rMgr = new IssueReportMgr();
	IssueCodeMgr icMgr = new IssueCodeMgr();
	MakeTypeChart irChart = new MakeTypeChart();
	IssueMgr iMgr = new IssueMgr();
	IssueDataBean idBean = new IssueDataBean();
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
	
	ArrayList arrIDC = null;
	
	//보고서 날짜
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	String issue_yn = pr.getString("issue_yn", "y");
	
	String typeCode = pr.getString("typeCode","");
	String i_seq = pr.getString("i_seq","");
	
	//차트 경로
	String filePath = cu.getConfig("CHARTPATH")+"report"+ReportTypeConstants.peoridVal+"/";
	String chartUrl = cu.getConfig("CHARTURL")+"report"+ReportTypeConstants.peoridVal+"/";
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"images/report/weeklyreport/";

	//주간 보고서 관련
	String today = du.getCurrentDate("yyyy년M월dd일 HH:mm");
	String formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
	String formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");		
	String id_seq = pr.getString("id_seq","");
	
	
	//주간 차트 데이터 		
	HashMap dataHM = rMgr.getWeeklyChartData(ir_sdate,ir_stime,ir_edate,ir_etime,typeCode);		
	ArrayList arrGdataA = (ArrayList)dataHM.get("A");
	ArrayList arrGdataB = (ArrayList)dataHM.get("B");
	ArrayList arrGdataC = (ArrayList)dataHM.get("C");
	ArrayList arrGdataD = (ArrayList)dataHM.get("D");
	
	//주간 우호도 동향
	String chart1 = irChart.makeLineBar(1,arrGdataA,"WeeklyLineBarChartA"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
	
	//주간 출처별 관심도
	String chart2 = irChart.makeLine(2,arrGdataB,"WeeklyLineChartB"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,600,200);
	
	//주간 사업부분별 
	String chart3 = irChart.make3DPieChart(1,arrGdataC,"WeeklyPieChartC"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);
	
	//주간 정보유형별
	String chart4 = irChart.make3DPieChart(1,arrGdataD,"WeeklyPieChartD"+du.getCurrentDate("yyyyMMddHHmmss"),filePath,chartUrl,250,150);	
	
	//중요도가 상중이고 자사인거
	if(!typeCode.equals("")) typeCode +="@10,1@10,2";
	else typeCode +="10,1@10,2"; 
	
	//관심 정보	
	ArrayList arrIssueDataList1 = new ArrayList();		
	arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@"+typeCode,"9","");
	
	ArrayList arrIssueDataList2 = new ArrayList();	
	arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,2@"+typeCode,"9","");
			
	ArrayList arrIssueDataList3 = new ArrayList();	
	arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,3@"+typeCode,"9","");
			
	ArrayList arrIssueDataList4 = new ArrayList();	
	arrIssueDataList4 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,4@"+typeCode,"9","");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<style type="text/css">
body {
	margin-left: 10px;
	margin-top: 10px;
	margin-right: 0px;
	margin-bottom: 0px;
}
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
TD {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #333333; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.w_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #FFFFFF; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.ww_text {
	FONT-WEIGHT: normal; FONT-SIZE: 14px; COLOR: #FFFFFF; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.s_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #666666; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.o_text {
	FONT-WEIGHT: normal; FONT-SIZE: 16px; COLOR: #EC700B; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}	
.b_text {
	FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: #478BB6; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.bb_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #478BB6; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.bbb_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #3478CC; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.r_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #EC1240; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"
}
.style1 {FONT-WEIGHT: bold; FONT-SIZE: 16px; COLOR: #EC700B; LINE-HEIGHT: 18px; FONT-FAMILY: "malgun Gothic"; }
</style>
</head>
<body>
<table width="800" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="<%=imgUrl%>top_img.jpg" width="800" height="85" /></td>
	</tr>
	<tr>
		<td><table width="800" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><table width="15" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="1" bgcolor="035D84"></td>
						<td width="2" bgcolor="#1B7BB7"></td>
						<td width="12" height="48" bgcolor="#FEFEFE"></td>
					</tr>
				</table></td>
				<td width="699" bgcolor="#FEFEFE"><table width="699" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="34" bgcolor="#FEFEFE"><table width="698" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="445" class="s_text" style="padding:0px 0px 0px 4px">본 내용은 <strong>대외비로 보안에 유의</strong>하시기 바랍니다.</td>
								<td width="253" align="right"><table width="223" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="21"><img src="<%=imgUrl%>icon_clock.gif" width="21" height="19" /></td>
										<td><strong><%=formatSdate %> ~ <%=formatEdate %></strong></td>
									</tr>
								</table></td>
							</tr>
						</table></td>
					</tr>
					<tr>
						<td><img src="<%=imgUrl%>dotline.gif" width="699" height="1" /></td>
					</tr>
					<tr>
						<td height="13" bgcolor="#FEFEFE"></td>
					</tr>
				</table></td>
				<td width="86"><img src="<%=imgUrl%>top_img_bg.jpg" width="86" height="48" /></td>
			</tr>
		</table></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1C7CB4"></td>
		<td width="12">&nbsp;</td>
		<td width="770"><img src="<%=imgUrl%>title_01.gif" width="157" height="29" /></td>
		<td width="12"></td>
		<td width="2" bgcolor="#7BBE20"></td>
		<td width="1" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1B7BB7"></td>
		<td width="12" bgcolor="#FEFEFE"></td>
		<td width="770"><table width="770" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_01.gif" width="770" height="10" /></td>
			</tr>
			<tr>
				<td><table width="770" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="2" bgcolor="#D0D0D0"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td><table>
							<tr>
								<td>내용입력</td>
							</tr>
						</table></td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="2" bgcolor="#D0D0D0"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_03.gif" width="770" height="10" /></td>
			</tr>
		</table></td>
		<td width="12" align="right" bgcolor="#FEFEFE"></td>
		<td width="2" align="right" bgcolor="#76B822"></td>
		<td width="1" align="right" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1C7CB4"></td>
		<td width="12">&nbsp;</td>
		<td width="770" style="padding:25px 0px 0px 0px"><img src="<%=imgUrl%>title_02.gif" width="127" height="29" /></td>
		<td width="12"></td>
		<td width="2" bgcolor="#7BBE20"></td>
		<td width="1" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1B7BB7"></td>
		<td width="12" bgcolor="#FEFEFE"></td>
		<td width="770"><table width="770" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_01.gif" width="770" height="10" /></td>
			</tr>
			<tr>
				<td><table width="770" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="2" bgcolor="#D0D0D0"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td style="padding:10px 0px 10px 0px">
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td height="15"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="center" valign="top"><img src="<%=chart1%>"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td height="15"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="<%=imgUrl%>summary_bg_01.gif" width="730" height="6" /></td>
								</tr>
								<tr>
									<td><table width="730" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="2" bgcolor="#CDCDCD"></td>
											<td bgcolor="#F2F2F2" style="padding:5px 10px 5px 10px"><span class="bb_text"><strong>내용입력</strong></span></td>
											<td width="2" bgcolor="#CDCDCD"></td>
										</tr>
									</table></td>
								</tr>
								<tr>
									<td><img src="<%=imgUrl%>summary_bg_02.gif" width="730" height="6" /></td>
								</tr>
							</table>
						</td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="2" bgcolor="#D0D0D0"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_03.gif" width="770" height="10" /></td>
			</tr>
		</table></td>
		<td width="12" align="right" bgcolor="#FEFEFE"></td>
		<td width="2" align="right" bgcolor="#76B822"></td>
		<td width="1" align="right" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1C7CB4"></td>
		<td width="12">&nbsp;</td>
		<td width="770" style="padding:25px 0px 0px 0px"><img src="<%=imgUrl%>title_03.gif" width="182" height="29" /></td>
		<td width="12"></td>
		<td width="2" bgcolor="#7BBE20"></td>
		<td width="1" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1B7BB7"></td>
		<td width="12" bgcolor="#FEFEFE"></td>
		<td width="770"><table width="770" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_01.gif" width="770" height="10" /></td>
			</tr>
			<tr>
				<td><table width="770" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="2" bgcolor="#D0D0D0"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td style="padding:10px 0px 10px 0px">
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td height="15"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="center" valign="top"><img src="<%=chart2%>"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td height="15"></td>
								</tr>
							</table>
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="<%=imgUrl%>summary_bg_01.gif" width="730" height="6" /></td>
								</tr>
								<tr>
									<td><table width="730" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="2" bgcolor="#CDCDCD"></td>
											<td bgcolor="#F2F2F2" style="padding:5px 10px 5px 10px"><span class="bb_text"><strong>내용입력</strong></span></td>
											<td width="2" bgcolor="#CDCDCD"></td>
										</tr>
									</table></td>
								</tr>
								<tr>
									<td><img src="<%=imgUrl%>summary_bg_02.gif" width="730" height="6" /></td>
								</tr>
							</table>
						</td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="2" bgcolor="#D0D0D0"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_03.gif" width="770" height="10" /></td>
			</tr>
		</table></td>
		<td width="12" align="right" bgcolor="#FEFEFE"></td>
		<td width="2" align="right" bgcolor="#76B822"></td>
		<td width="1" align="right" bgcolor="#035D84"></td>
	</tr>
</table>
<%
if(issue_yn.equals("y")){
%>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1C7CB4"></td>
		<td width="12">&nbsp;</td>
		<td width="770" valign="bottom" style="padding:25px 0px 0px 0px"><table width="200" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="101"><img src="<%=imgUrl%>title_04.gif" width="101" height="29" /></td>
				<td valign="top" class="style1">(<%=arrIssueDataList1.size()+arrIssueDataList2.size()+arrIssueDataList3.size()+arrIssueDataList4.size()%>건)</td>
			</tr>
		</table></td>
		<td width="12"></td>
		<td width="2" bgcolor="#7BBE20"></td>
		<td width="1" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1B7BB7"></td>
		<td width="12" bgcolor="#FEFEFE"></td>
		<td width="770"><table width="770" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_01.gif" width="770" height="10" /></td>
			</tr>
<%
imgUrl = siteUrl+"images/report/";
%>
			<tr>
				<td><table width="770" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="2" bgcolor="#D0D0D0"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td style="padding:10px 0px 10px 0px">
							<table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
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
							</table>
						</td>
						<td width="1" bgcolor="#E7E7E7"></td>
						<td width="2" bgcolor="#FFFFFF"></td>
						<td width="2" bgcolor="#D0D0D0"></td>
					</tr>
				</table></td>
			</tr>
<%
imgUrl = siteUrl+"images/report/weeklyreport/";
%>
			<tr>
				<td><img src="<%=imgUrl%>tb_bg_03.gif" width="770" height="10" /></td>
			</tr>
		</table></td>
		<td width="12" align="right" bgcolor="#FEFEFE"></td>
		<td width="2" align="right" bgcolor="#76B822"></td>
		<td width="1" align="right" bgcolor="#035D84"></td>
	</tr>
</table>
<%
}
%>
<table width="800" border="0" cellspacing="0" cellpadding="0" valign="top" >
	<tr>
		<td width="1" bgcolor="#035D84"></td>
		<td width="2" bgcolor="#1C7CB4"></td>
		<td>&nbsp;</td>
		<td width="2" bgcolor="#7BBE20"></td>
		<td width="1" bgcolor="#035D84"></td>
	</tr>
</table>
<table width="800" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><img src="<%=imgUrl%>copy.gif" width="800" height="40" /></td>
	</tr>
</table>
</body>
</html>