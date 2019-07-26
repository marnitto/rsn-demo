<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%//@ page contentType="text/html; charset=EUC-KR" %>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueMgr,
				 risk.issue.IssueDataBean,
				 risk.issue.IssueCodeBean,
				 risk.issue.IssueCodeMgr,
				 risk.issue.IssueCommentBean,
				 risk.util.StringUtil,
                 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 risk.admin.member.MemberBean,
                 risk.admin.member.MemberDao,                 
                 java.util.ArrayList,
                 java.net.URLDecoder"
                  
%>
<%
//try{
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr isMgr = new IssueMgr();
	
	//int chk_cnt = 0;
	int nowpage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	System.out.println("searchKey : "+searchKey);
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String pressYn = pr.getString("pressYn");
	String pseqVal = pr.getString("pseqVal");
	String r_trend = pr.getString("r_trend");
	String mode = "excel";
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		

	ArrayList replyList = new ArrayList();
	replyList = isMgr.replyAnalysisList(sDateFrom, sDateTo, nowpage, searchKey, pressYn, pseqVal, r_trend, mode);
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Reply_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
   
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
<!--
td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
   input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
   .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
a:link { color: #333333; text-decoration: none; }
a:visited { text-decoration: none; color: #000000; }
a:hover { text-decoration: none; color: #FF9900; }
a:active { text-decoration: none; }

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	SCROLLBAR-face-color: #F2F2F2;
	SCROLLBAR-shadow-color: #999999;
	SCROLLBAR-highlight-color: #999999;
	SCROLLBAR-3dlight-color: #FFFFFF;
	SCROLLBAR-darkshadow-color: #FFFFFF;
	SCROLLBAR-track-color: #F2F2F2;
	SCROLLBAR-arrow-color: #333333;
     }
.menu_black {  font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #000000}
.textw { font-family: "돋움", "돋움체"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}

.menu_blue {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_gray {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #4B4B4B
}
.menu_red {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #CC0000
}
.menu_blueOver {

	font-family: Tahoma;
	font-size: 11px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_blueTEXTover {


	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3366CC;
	font-weight: normal;
}
.textwbig {
font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal
}
.textBbig {

font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal
}
.menu_grayline {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #4B4B4B;
	text-decoration: underline;
}
.menu_grayS {

font-family: "돋움", "돋움체"; font-size: 11px; line-height: 16px; color: #4B4B4B
}
-->

</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" border="1" >
    <table width="1470" border="1" cellspacing="0" cellpadding="0">
     
      <tr>
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 일자     </strong></td>
        <td width="440" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 원문 제목     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 매체명     </strong></td>
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 원문 문서번호</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 보도자료명 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 링크 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 댓글내용 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 자동성향 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수동성향 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 긍정연관어</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 부정연관어</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 연관단어</strong></td>
      </tr>
    </table>
    <%  
    try{
    	//Map rMap = null;
    	Map rMap = null;
    	for( int i = 0 ; i < replyList.size() ; i++ ) {
    		rMap = new HashMap();
    		rMap = (HashMap)replyList.get(i);
    		String r_trend_tmp = rMap.get("R_TREND").toString();
    		String m_trend_tmp = rMap.get("M_TREND").toString();
    		if(r_trend_tmp.equals("1")){
				r_trend_tmp = "긍정";
			}else if(r_trend_tmp.equals("2")){
				r_trend_tmp = "부정";
			}else{
				r_trend_tmp = "중립";
			}
    		if(m_trend_tmp.equals("1")){
    			m_trend_tmp = "긍정";
			}else if(m_trend_tmp.equals("2")){
				m_trend_tmp = "부정";
			}else{
				m_trend_tmp = "중립";
			}
    	
    %>
    <table width="1470" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=rMap.get("R_SDATE")%>
        </td>
        <td width="440" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%-- <a href="<%=IDBean.getId_url()%>" target="_blank" ><%=su.nvl(IDBean.getId_title(),"제목없음")%></a> --%>
          <%=rMap.get("R_TITLE")%>
        </td>
        <td width="200" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=rMap.get("P_SITENAME")%>
        </td>
        <td width="120" align="left" style="padding: 3px 0px 0px 5px;">
          <%=rMap.get("R_DOCID")%>
        </td>
        <td width="120" align="left" style="padding: 3px 0px 0px 5px;">
          <%=rMap.get("P_TITLE")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=rMap.get("P_URL")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=rMap.get("R_CONTENT")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=r_trend_tmp%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=m_trend_tmp%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=rMap.get("R_TRENDWORD_POS")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=rMap.get("R_TRENDWORD_NEG")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=rMap.get("R_RELATION_WORD")%>
        </td>
      </tr>
    </table>
    <%  }%>
          <%}catch(Exception e){System.out.println("issue_reply_excel.jsp : "+e);} %>
</body>
</html>