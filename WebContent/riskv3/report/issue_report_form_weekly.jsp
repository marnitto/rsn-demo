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
	
	
	HashMap hm = null;
	
	
	ArrayList arrIssueDataList5 = new ArrayList();
	ArrayList arrIssueDataList6 = new ArrayList();
	ArrayList arrIssueDataList7 = new ArrayList();
	ArrayList arrIssueDataList8 = new ArrayList();
	ArrayList arrIssueDataList9 = new ArrayList();
	
	ArrayList arrSiteOrder = new ArrayList();
	
	//코드초기화
	icMgr.init(1);
	
	 
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"images/report/";
	
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


	//일일 보고서 관련
	
	
	//주간 보고서 관련
	today = du.getCurrentDate("yyyy년M월dd일 E요일");
	id_seq = pr.getString("id_seq","");
	formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
	formatEdate = du.getDate(ir_edate+" "+ir_etime,"yyyy.MM.dd HH시");		
	
	
	
	String id_seq_press = pr.getString("id_seq_press","");
	String id_seq_public = pr.getString("id_seq_public","");
	String id_seq_portal = pr.getString("id_seq_portal","");
	
	arrSiteOrder = iMgr.getSiteGroup_order2();
	
	
	
	
	//언론불륨(언론사)
	//arrIssueDataList5 = iMgr.getIssueDataList(0,0,id_seq_press,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@6,1@6,6","","","","","","Y");
	arrIssueDataList5 = iMgr.DailyReport4(id_seq_press,ir_sdate,ir_stime,ir_edate,ir_etime);
	
	
	//언론불륨(공공게시판)
	arrIssueDataList6 = iMgr.getIssueDataList(0,0,id_seq_public,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@6,5","","","","","","Y");
	
	//언론확산(포탈)
	arrIssueDataList7 = iMgr.DailyReport3(id_seq_portal,ir_sdate,ir_stime,ir_edate,ir_etime);
	
	//언론확산 (개인미디어)
	String idSeqs = "";
	for(int i =0; i< arrIssueDataList5.size(); i++){
		
		if(idSeqs.equals("")){
			idSeqs = ((IssueDataBean)arrIssueDataList5.get(i)).getId_seq();
		}else{
			idSeqs += "," + ((IssueDataBean)arrIssueDataList5.get(i)).getId_seq();
		}
		
	}
	if(!idSeqs.equals("")){
		arrIssueDataList8 = iMgr.DailyReport1(idSeqs);	
	}
	
	
	//개인미디어 기준
	arrIssueDataList9 = iMgr.DailyReport2(ir_sdate,ir_stime,ir_edate,ir_etime);
	
	

	
	ArrayList codeList = new ArrayList();
	icMgr.init(0);
	
	//차트1번
	
	codeList = icMgr.getSouceCodeOrder();
	
	
	arrGdataA = iMgr.getChartData(ir_sdate,ir_stime,ir_edate,ir_etime, codeList, false);
	chart1 = irChart.makeCategoryBar(1, arrGdataA, codeList, "chart1"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 446, 162);
	
	//차트2번
	arrGdataB = iMgr.TrendPieChartData("13", ir_sdate,ir_stime,ir_edate,ir_etime);
	chart2 = irChart.make3DPieChart(1, arrGdataB, "chart2"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 214, 119);
	
	//차트3번
	codeList = icMgr.GetType(9);
	arrGdataC = iMgr.getChartData_type9(ir_sdate, ir_stime, ir_edate, ir_etime, codeList, false);
	chart3 = irChart.createChartCS(arrGdataC, codeList, "chart3"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 426, 138);
	
	//차트4번
	arrGdataD = iMgr.TrendPieChartData("9", ir_sdate,ir_stime,ir_edate,ir_etime);
	chart4 = irChart.make3DPieChart(1, arrGdataD, "chart4"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 222, 138);
	
	//차트5번
	codeList = icMgr.getSouceCodeOrder();
	
	arrGdataE = iMgr.getChartData(ir_sdate,ir_stime,ir_edate,ir_etime, codeList, true);
	chart5 = irChart.makeCategoryBar(1, arrGdataE, codeList, "chart5"+du.getCurrentDate("yyyyMMddHHmmss"), filePath, chartUrl, 638, 138);
	
	
	
	
	//관심 정보	
	arrIssueDataList1 = new ArrayList();		
	arrIssueDataList1 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,1@"+typeCode,"","","","","Y","");

	arrIssueDataList2 = new ArrayList();	
	arrIssueDataList2 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,2@"+typeCode,"","","","","Y","");
			
	arrIssueDataList3 = new ArrayList();	
	arrIssueDataList3 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,3@"+typeCode,"","","","","Y","");
	
	arrIssueDataList4 = new ArrayList();	
	arrIssueDataList4 = iMgr.getIssueDataList(0,0,id_seq,i_seq,"","","","2",ir_sdate,ir_stime,ir_edate,ir_etime,"4,4@"+typeCode,"","","","","Y","");
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


<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
TD {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #666666; LINE-HEIGHT: 18px; FONT-FAMILY: "돋움"
}
.w_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #FFFFFF; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"
}
-->
.b_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #3E67A6; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"
}
.t_text {
	FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: #666666; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"
}
.tt_text {
	FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: #A5533D; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"
}
.r_text {
	FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #C92323; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"
}

</style>
</head>
<body>

<table width="750" border="0" cellpadding="0" cellspacing="1" bgcolor="DADADA">
  <tr>
    <td bgcolor="#FFFFFF"><table width="748" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="<%=imgUrl%>top_img_01.jpg" width="748" height="75" /></td>
      </tr>
      <tr>
        <td height="28" background="<%=imgUrl%>bg_date.gif" style="padding:0px 10px 0px 0px"><table width="730" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="430" class="t_text" style="padding:0px 0px 0px 10px"><img src="<%=imgUrl%>emer_icon.gif" width="10" height="10" /> 본 내용은 대외비로 보안에 유의하시기 바랍니다.</td>
            <td width="300" align="right"><img src="<%=imgUrl%>icon_clock.gif" width="12" height="13" align="absmiddle" /> <%=formatSdate%> ~ <%=formatEdate%></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td><table width="710" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><table width="710" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="<%=imgUrl%>title_05.gif" width="169" height="22" /></td>
              </tr>
              <tr>
                <td height="4"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="1" bgcolor="0470B0"></td>
                    </tr>
                    <tr>
                      <td height="3" bgcolor="0C88CE"></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr>
                <td height="30" background="<%=imgUrl%>title_bar.gif" class="w_text" style="padding:0px 0px 0px 25px"><strong>언론 보도 수량 기준</strong></td>
              </tr>
              <%
              	if(arrIssueDataList5.size() > 0){
              %>
              
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="2" bgcolor="4B74AA"></td>
                    </tr>
                    <tr>
                      <td><table width="710" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                      	  <tr>
                            <td width="62" height="30" align="center" bgcolor="F5F5F5"><strong>구분</strong></td>
                            <td width="238" align="center" bgcolor="F5F5F5"><strong>제목</strong></td>
                            <td width="71" align="center" bgcolor="F5F5F5"><strong>내용구분</strong></td>
                            <td width="99" align="center" bgcolor="F5F5F5"><strong>최초보도</strong></td>
                            <td width="67" align="center" bgcolor="F5F5F5"><strong>보도시간</strong></td>
                            <td width="123" align="center" bgcolor="F5F5F5"><strong>스크랩 언론</strong></td>
                            <td width="44" align="center" bgcolor="F5F5F5"><strong>수량</strong></td>
                          </tr>
                      <%
                      	//String[] arrSameCt = null;
                      	//int intSameCt = 0;
                      	for(int i =0; i < arrIssueDataList5.size(); i++){
                      		idBean = (IssueDataBean)arrIssueDataList5.get(i);	
                      		//arrIdcBean = (ArrayList) idBean.getArrCodeList();
                      		
                      		//arrSameCt = idBean.getMd_same_ct().split(","); 
                      		//intSameCt = Integer.parseInt(arrSameCt[iMgr.GetSearchSourceOrder(arrSiteOrder, "1")-1]) - 1; 
                      %>
                          <tr>
                          
                          	<%if(i == 0){%>
                            <td rowspan="<%=arrIssueDataList5.size()%>" align="center" bgcolor="F5F5F5"><strong>언론사</strong></td>
                            <%} %>
                            
                            
                            <td height="30" bgcolor="#FFFFFF" style="padding:0px 0px 0px 10px" ><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>"><%=su.cutString(idBean.getId_title(), 25, "...")%></a></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp1()%></td>
                            <td align="center" bgcolor="#FFFFFF" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 6, "...")%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=du.getDate(idBean.getMd_date(), "yyyy-MM-dd")%></td>
                            <td align="center" bgcolor="#FFFFFF"></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getMd_same_ct()%></td>
                          </tr>
                          
                      <%}%>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              <%} %>
              
              
              
              
              <%
              	if(arrIssueDataList6.size() > 0){
              %>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="10"></td>
                    </tr>
                    <tr>
                      <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="2" bgcolor="4B74AA"></td>
                          </tr>
                          <tr>
                            <td><table width="710" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                                <tr>
                                  <td width="62" height="30" align="center" bgcolor="F5F5F5"><strong>구분</strong></td>
                                  <td width="238" align="center" bgcolor="F5F5F5"><strong>제목</strong></td>
                                  <td width="71" align="center" bgcolor="F5F5F5"><strong>내용구분</strong></td>
                                  <td width="136" align="center" bgcolor="F5F5F5"><strong>최초게시</strong></td>
                                  <td width="68" align="center" bgcolor="F5F5F5"><strong>게시시간</strong></td>
                                  <td width="130" align="center" bgcolor="F5F5F5"><strong>최초보도</strong></td>
                                </tr>
                                
                                <%
                                	for(int i =0; i < arrIssueDataList6.size(); i++){ 
                                		idBean = (IssueDataBean)arrIssueDataList6.get(i);	
                                  		arrIdcBean = (ArrayList) idBean.getArrCodeList();
                                %>
                                <tr>
                                  <%if(i==0){ %>
                                  <td rowspan="<%=arrIssueDataList6.size()%>" align="center" bgcolor="F5F5F5"><strong>공공<br />게시판</strong></td>
                                  <%} %>
                                  <td height="30" bgcolor="#FFFFFF" style="padding:0px 0px 0px 10px" ><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>"><%=su.cutString(idBean.getId_title(), 30, "...")%></a></td>
                                  <td align="center" bgcolor="#FFFFFF"><%=icMgr.GetCodeNameType(arrIdcBean,14)%></td>
                                  <td align="center" bgcolor="#FFFFFF" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 6, "...")%></td>
                                  <td align="center" bgcolor="#FFFFFF"></td>
                                  <td align="center" bgcolor="#FFFFFF"></td>
                                </tr>
                                <%	} %>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              <%} %>
              
              <%
              	if(arrIssueDataList7.size() > 0){
              		
              %>
              <tr>
                <td height="10"></td>
              </tr>
              
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="2" bgcolor="4B74AA"></td>
                    </tr>
                    <tr>
                      <td><table width="710" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                          <tr>
                            <td width="62" height="30" align="center" bgcolor="F5F5F5"><strong>구분</strong></td>
                            <td width="92" align="center" bgcolor="F5F5F5"><strong>종류</strong></td>
                            <td width="270" align="center" bgcolor="F5F5F5"><strong>제목</strong></td>
                            <td width="105" align="center" bgcolor="F5F5F5"><strong>노출위치</strong></td>
                            <td width="86" align="center" bgcolor="F5F5F5"><strong> 노출시간</strong></td>
                            <td width="90" align="center" bgcolor="F5F5F5"><strong> 삭제시간</strong></td>
                          </tr>
                          <%
                          	for(int i =0; i < arrIssueDataList7.size(); i++){ 
                          		idBean = (IssueDataBean)arrIssueDataList7.get(i);	
                          %>
                          <tr>
                          	<%if(i==0){ %>
                            <td rowspan="<%=arrIssueDataList7.size()%>" align="center" bgcolor="F5F5F5"><strong>포탈메인</strong></td>
                            <%} %>
                            <td height="30" align="center" bgcolor="#FFFFFF" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name().replaceAll("TOP","").trim()%></td>
                            <td bgcolor="#FFFFFF"  style="padding:0px 0px 0px 10px" ><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>"><%=su.cutString(idBean.getId_title(), 30, "...")%></a></td>
                            <td align="center" bgcolor="#FFFFFF" title="<%=idBean.getMd_site_menu()%>"><%=su.cutString(idBean.getMd_site_menu(), 6, "...")%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getFormatMd_date("HH:mm")%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp1() != null ?  du.getDate(idBean.getTemp1(),"HH:mm") : ""%></td>
                          </tr>
                          <%
                          	}
                          %>
                      </table></td>
                    </tr>
             
           
                </table></td>
              </tr>
              <%} %>
              
              
              
              
              
              
              
              <tr>
		               <td height="6"></td>
		             </tr>
		             
                    <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="42" valign="top" background="<%=imgUrl%>write_bg_01.gif"><table width="300" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="b_text" style="padding:18px 0px 0px 40px"><strong>요약</strong></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td background="<%=imgUrl%>write_bg_02.gif" style="padding:10px 10px 8px 18px">
                      
                     </td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>write_bg_03.gif" width="710" height="7" /></td>
                  </tr>
                </table></td>
              </tr>
              
              
              
              
              <tr>
                <td height="10"></td>
              </tr>
              
              <tr>
                <td height="30" background="<%=imgUrl%>title_bar_01.gif" class="w_text" style="padding:0px 0px 0px 25px"><strong>개인미디어 확산 기준(단순 스크랩)</strong></td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="2" bgcolor="2A92BD"></td>
                    </tr>
                    <tr>
                      <td><table width="710" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                          <tr>
                            <td width="62" height="30" align="center" bgcolor="F5F5F5"><strong>구분</strong></td>
                            <td width="92" align="center" bgcolor="F5F5F5"><strong>게시자</strong></td>
                            <td width="270" align="center" bgcolor="F5F5F5"><strong>제목</strong></td>
                            <td width="90" align="center" bgcolor="F5F5F5"><strong> 게시시간</strong></td>
                            <td width="105" align="center" bgcolor="F5F5F5"><strong>영향력</strong></td>
                            <td width="86" align="center" bgcolor="F5F5F5"><strong> 유사수량</strong></td>
                            
                          </tr>
                          
                          
                          <%
                          	for(int i =0; i < arrIssueDataList8.size(); i++){
                          		idBean = (IssueDataBean)arrIssueDataList8.get(i);
                          %>		
	                          <tr>  
	                            <td height="30" align="center" bgcolor="#FFFFFF" title="<%=idBean.getMd_site_name()%>"><%=su.cutString(idBean.getMd_site_name(), 6, "...")%></td>
	                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getMedia_info()%></td>
	                            <td bgcolor="#FFFFFF"  style="padding:0px 0px 0px 10px" ><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>"><%=su.cutString(idBean.getId_title(), 30, "...")%></a></td>
	                            <td align="center" bgcolor="#FFFFFF"><%//=du.getDate(idBean.getMd_date(),"HH:mm")%></td>
	                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp1()%></td>
	                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getMd_same_ct()%></td>
	                          </tr>		
	                          
                          <%} %>
                       	<%
                       		if(arrIssueDataList8.size() < 3){
                       			for(int i =0; i < (3 - arrIssueDataList8.size()); i++){ 
                       	%>
		                         <tr>
		                            <td height="30" align="center" bgcolor="#FFFFFF"></td>
		                            <td align="center" bgcolor="#FFFFFF"></td>
		                            <td bgcolor="#FFFFFF"  style="padding:0px 0px 0px 10px"></td>
		                            <td align="center" bgcolor="#FFFFFF"></td>
		                            <td align="center" bgcolor="#FFFFFF"></td>
		                            <td align="center" bgcolor="#FFFFFF"></td>
		                         </tr>
                        <%		
                        		}
                       		}
                       	%> 
                      </table></td>
                    </tr>
                    
                    
                    <tr>
		               <td height="6"></td>
		             </tr>
                    <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="42" valign="top" background="<%=imgUrl%>write_bg_01.gif"><table width="300" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="b_text" style="padding:18px 0px 0px 40px"><strong>요약</strong></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td background="<%=imgUrl%>write_bg_02.gif" style="padding:10px 10px 8px 18px"></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>write_bg_03.gif" width="710" height="7" /></td>
                  </tr>
                </table></td>
              </tr>
               
                    
                </table></td>
              </tr>
  
              
              <%
              	if(arrIssueDataList9.size() > 0){
              %>
              
              <tr>
                <td height="20"></td>
              </tr>
              <tr>
                <td height="30" background="<%=imgUrl%>title_bar_02.gif" class="w_text" style="padding:0px 0px 0px 25px"><strong>개인미디어 확산 기준(기사인용/개인의견)</strong></td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="2" bgcolor="2CA787"></td>
                    </tr>
                    <tr>
                      <td><table width="710" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                          <tr>
                            <td width="52" height="30" align="center" bgcolor="F5F5F5"><strong>구분</strong></td>
                            <td width="80" align="center" bgcolor="F5F5F5"><strong>게시자</strong></td>
                            <td width="270" align="center" bgcolor="F5F5F5"><strong>제목</strong></td>
                            <td width="75" align="center" bgcolor="F5F5F5"><strong>게시시간</strong></td>
                            <td width="56" align="center" bgcolor="F5F5F5"><strong>영향력</strong></td>
                            <td width="76" align="center" bgcolor="F5F5F5"><strong>정보유형</strong></td>
                            <td width="54" align="center" bgcolor="F5F5F5"><strong>유사수량</strong></td>
                            <td width="44" align="center" bgcolor="F5F5F5"><strong>성향</strong></td>
                          </tr>
                          <%
                          	for(int i =0; i < arrIssueDataList9.size(); i++){
                          		idBean = (IssueDataBean)arrIssueDataList9.get(i);
                          %>
                          <tr>
                            <td align="center" bgcolor="F5F5F5"><strong><%=idBean.getTemp1()%></strong></td>
                            <td height="30" align="center" bgcolor="#FFFFFF"><%=idBean.getMedia_info()%></td>
                            <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 10px"><a href="<%=idBean.getId_url()%>" target="_blank" title="<%=idBean.getId_title()%>"><%=su.cutString(idBean.getId_title(), 30, "...")%></a></td>
                            <td align="center" bgcolor="#FFFFFF"><%//=du.getDate(idBean.getMd_date(),"HH:mm")%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp2()%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp3()%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getMd_same_ct()%></td>
                            <td align="center" bgcolor="#FFFFFF"><%=idBean.getTemp4()%></td>
                          </tr>
                          <%} %>
                      </table></td>
                    </tr>
                    
                    <tr>
		               <td height="6"></td>
		             </tr>
                    <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="42" valign="top" background="<%=imgUrl%>write_bg_01.gif"><table width="300" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="b_text" style="padding:18px 0px 0px 40px"><strong>요약</strong></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td background="<%=imgUrl%>write_bg_02.gif" style="padding:10px 10px 8px 18px"></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>write_bg_03.gif" width="710" height="7" /></td>
                  </tr>
                </table></td>
              </tr>
                    
                    
                </table></td>
              </tr>
              
               <%} %>
              
                  <tr>
                      <td height="10"></td>
                    </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="15" valign="top" style="padding:3px 0px 0px 0px"><img src="<%=imgUrl%>icon_001.gif" width="10" height="10" /></td>
                    <td width="695"><table width="695" border="0" align="right" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="tt_text"><strong>개인미디어 영향력 구분 기준 (파워 vs. 일반)</strong></td>
                      </tr>
                      <tr>
                        <td height="5"></td>
                      </tr>
                      <tr>
                        <td class="t_text">활성화 여부(정기적으로 다량의 콘텐츠 포스팅 여부) / 콘텐츠 질(이미지&amp;텍스트의 수준과 양) / 팔로워수 혹은 회원수(콘텐츠 확산력) / <br />
                          콘텐츠 신뢰도(포탈에서 공식적으로 부여한 타이틀 여부)</td>
                      </tr>
                    </table></td>
                  </tr>
                  </table></td>
              </tr>
              
              
              <tr>
                <td height="20"></td>
              </tr>
              
              
              
              
              
              <tr>
            <td height="30"></td>
          </tr>
          <tr>
            <td><table width="710" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="<%=imgUrl%>title_06.gif" width="253" height="22" /></td>
              </tr>
              <tr>
                <td height="4"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="1" bgcolor="0470B0"></td>
                    </tr>
                    <tr>
                      <td height="3" bgcolor="0C88CE"></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              
              
              
              
            
              
              <tr>
                <td height="6"><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="320" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="6" height="29"><img src="<%=imgUrl%>tap_01.gif" width="6" height="29" /></td>
                          <td background="<%=imgUrl%>tap_01_01.gif" class="w_text" style="padding:2px 0px 0px 10px"><strong>주간 채널별 변화 및 정보 유형별 점유율</strong></td>
                          <td width="28"><img src="<%=imgUrl%>tap_01_02.gif" width="28" height="29" /></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>tap_bg_04.gif" width="710" height="2" /></td>
                  </tr>
                  <tr>
                    <td background="<%=imgUrl%>tap_bg_02.gif"><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                                <td width="490" height="32" background="<%=imgUrl%>table_bg_01.gif" style="padding:0px 0px 0px 18px"><img src="<%=imgUrl%>arrow.gif" width="10" height="7" /><strong>주간 채널별 변화표</strong></td>
                                <td width="1"><img src="<%=imgUrl%>line.gif" width="1" height="32" /></td>
                            	<td width="215" background="<%=imgUrl%>table_bg_01.gif"style="padding:0px 0px 0px 18px"><img src="<%=imgUrl%>arrow.gif" width="10" height="7" /><strong>주간 유형별 비율</strong></td>
                                </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td height="1" bgcolor="DBDBDB"></td>
                        </tr>
                        <tr>
                          <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="470" align="center" style="padding:15px 0px 15px 0px"><img src="<%=chart1%>" /></td>
<!--                              <td width="20">&nbsp;</td>-->
                              <td width="1" background="<%=imgUrl%>line.gif"><img src="<%=imgUrl%>line.gif" width="1" /></td>
                              <td width="218" style="padding:15px 0px 15px 0px"><img src="<%=chart2%>"  /></td>
                            </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td><table width="680" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td><img src="<%=imgUrl%>bg_001.gif" width="680" height="10" /></td>
                            </tr>
                            <tr>
                              <td background="<%=imgUrl%>bg_002.gif"><table width="650" border="0" align="center" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="10" valign="baseline"></td>
                                    <td>
                                      </td>
                                  </tr>
                              </table></td>
                            </tr>
                            <tr>
                              <td><img src="<%=imgUrl%>bg_003.gif" width="680" height="10" /></td>
                            </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td>&nbsp;</td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>tap_bg_05.gif" width="710" height="2" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="360" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="6" height="29"><img src="<%=imgUrl%>tap_01.gif" width="6" height="29" /></td>
                          <td background="<%=imgUrl%>tap_01_01.gif" class="w_text" style="padding:2px 0px 0px 10px"><strong>주간 성향 변화 및 채널별 온라인 긍정 여론 변화 </strong></td>
                          <td width="28"><img src="<%=imgUrl%>tap_01_02.gif" width="28" height="29" /></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>tap_bg_04.gif" width="710" height="2" /></td>
                  </tr>
                  <tr>
                    <td background="<%=imgUrl%>tap_bg_02.gif"><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="435" height="32" background="<%=imgUrl%>table_bg_01.gif" style="padding:0px 0px 0px 18px"><img src="<%=imgUrl%>arrow.gif" width="10" height="7" /><strong>주간 여론 성향 변화표</strong></td>
                            <td width="1"><img src="<%=imgUrl%>line.gif" width="1" height="32" /></td>
                            <td width="275" background="<%=imgUrl%>table_bg_01.gif"style="padding:0px 0px 0px 18px"><img src="<%=imgUrl%>arrow.gif" width="10" height="7" /><strong>주간 성향 비율</strong></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td height="1" bgcolor="DBDBDB"></td>
                      </tr>
                      <tr>
                        <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="426" height="32" align="center" style="padding:15px 0px 15px 0px"><img src="<%=chart3%>"  /></td>
                            <td width="1" background="<%=imgUrl%>line.gif"><img src="<%=imgUrl%>line.gif" width="1" /></td>
                            <td width="275" align="center" style="padding:15px 0px 15px 0px"><img src="<%=chart4%>"  /></td>
                          </tr>
                        </table></td>
                      </tr>
                       <tr>
                          <td height="1" bgcolor="DBDBDB"></td>
                        </tr>
                      <tr>
                        <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="430" height="32" background="<%=imgUrl%>table_bg_01.gif" style="padding:0px 0px 0px 18px"><img src="<%=imgUrl%>arrow.gif" width="10" height="7" /><strong>주간 “긍정” 여론 변화표</strong></td>
                          </tr>
                        </table></td>
                      </tr>
                        <tr>
                          <td height="1" bgcolor="DBDBDB"></td>
                        </tr>
                        <tr>
                          <td><table width="706" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td align="center" style="padding:15px 0px 15px 0px"><img src="<%=chart5%>"  /></td>
                              </tr>
                          </table></td>
                        </tr>
                        <tr>
                        <td><table width="680" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td><img src="<%=imgUrl%>bg_001.gif" width="680" height="10" /></td>
                          </tr>
                          <tr>
                            <td background="<%=imgUrl%>bg_002.gif">

                            </td>
                          </tr>
                          <tr>
                            <td><img src="<%=imgUrl%>bg_003.gif" width="680" height="10" /></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><img src="<%=imgUrl%>tap_bg_05.gif" width="710" height="2" /></td>
                  </tr>
                </table></td>
              </tr></table></td>
          </tr>
          <tr>
            <td height="30"></td>
          </tr>
          <tr>
            <td><table width="710" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="<%=imgUrl%>title_07.gif" width="148" height="22" /></td>
              </tr>
              <tr>
                <td height="4"></td>
              </tr>
              <tr>
                <td><table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="1" bgcolor="0470B0"></td>
                    </tr>
                    <tr>
                      <td height="3" bgcolor="0C88CE"></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="10"></td>
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
           
           
           
           
           
           
           
           
           
           
           
              </table>
              </td>
          </tr>
        
          <tr>
            <td height="30"></td>
          </tr> 
          </table></td>
      </tr>
      <tr>
        <td height="1" bgcolor="DADADA"></td>
      </tr>
      <tr>
        <td height="40" bgcolor="F0F0F0" style="padding:0px 0px 0px 20px"><img src="<%=imgUrl%>bottom_logo.gif" /></td>
      </tr>    
    </table></td>
  </tr>
</table>



<div style="display: none"><img src="report_receipt"></div>
</body>
</html>
