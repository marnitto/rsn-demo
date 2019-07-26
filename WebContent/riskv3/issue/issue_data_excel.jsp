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
	IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowPage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	System.out.println("searchKey : "+searchKey);
	
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String language = pr.getString("language","");
	String searchType = pr.getString("searchType", "1");
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	
	String id_mobile = pr.getString("id_mobile");
	
	String kind = pr.getString("kind");
	
	
	String srtMsg = null;
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		

	//관련정보 리스트
	IssueDataBean idBean = null;
	if(kind.equals("group")){
		arrIdBean = issueMgr.getIssueDataList_excel(nowPage,pageCnt,check_no,i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","",id_mobile,kind, searchType);
	}else{
		arrIdBean = issueMgr.getIssueDataList_excel(nowPage,pageCnt,check_no,i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","",id_mobile,kind, searchType
				);		
	}
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
   
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
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
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" border="1" >
    <table width="1470" border="1" cellspacing="0" cellpadding="0">
     
      <tr>
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처     </strong></td>
        <td width="440" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> URL     </strong></td>
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 등록시간</strong></td>
        <td width="120" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수집 시간 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 구분 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 정보구분 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 정보구분 상세 </strong></td>        
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 대분류</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 소분류</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 실국</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 부서</strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 성향 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처 </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 주요이슈</strong></td>       
        
        <%
        	if(kind.equals("group")){%>
        		<td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 유사건수</strong></td>
        		
        <%	}
        %>
      </tr>
    </table>
    <%  
    try{
    	for( int i = 0 ; i < arrIdBean.size() ; i++ ) {
    	
    	IssueDataBean IDBean = new IssueDataBean();
    	IDBean = (IssueDataBean) arrIdBean.get(i);
    	ArrayList arrIDCBean = new ArrayList();
    	arrIDCBean = IDBean.getArrCodeList();
    	
    	
    	String type1 = "";
      	String type2 = "";
      	String type21 = "";
      	String type3 = "";
      	String type31 = "";
      	String type5 = "";
      	String type51 = "";      	
      	String type4 = "";
      	String type6 = "";
      	String type9 = "";
		            	
      	//System.out.println(arrIDCBean.size()+" arrIDCBean.size() ");
      	
        if(arrIDCBean != null &&  arrIDCBean.size()>0){
        	type1 = icMgr.GetCodeNameType(arrIDCBean,1);
        	type2 = icMgr.GetCodeNameType(arrIDCBean,2);
        	type21 = icMgr.GetCodeNameType(arrIDCBean,21);
        	type3 = icMgr.GetCodeNameType(arrIDCBean,3);
        	type31 = icMgr.GetCodeNameType(arrIDCBean,31);
        	type5 = icMgr.GetCodeNameType(arrIDCBean,5);
        	type51 = icMgr.GetCodeNameType(arrIDCBean,51);        	
        	type4 = icMgr.GetCodeNameType(arrIDCBean,4);
        	type6 = icMgr.GetCodeNameType(arrIDCBean,6);
        	type9 = icMgr.GetCodeNameType(arrIDCBean,9);
        } 	
        //MemberBean mbean = dao.getMember("M_SEQ",IDBean.getM_seq());
    	
    %>
    <table width="1470" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getMd_site_name()%>
        </td>
        <td width="440" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <a href="<%=IDBean.getId_url()%>" target="_blank" ><%=su.nvl(IDBean.getId_title(),"제목없음")%></a>
        </td>
        <td width="200" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <a href="<%=IDBean.getId_url()%>"><%=IDBean.getId_url()%></a>
        </td>
        <td width="120" align="left" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss")%>
        </td>
        <td width="120" align="left" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss")%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type1%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type2%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type21%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type3%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type31%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type5%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=type51%>
        </td>        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=type9%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=type6%>
        </td>      
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <%=type4%>
        </td>
        <%-- <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
           <% if ( IDBean.getRelationkeys() != null ) {out.print(IDBean.getRelationkeys());}%>
        </td> --%>
        <%
        if(kind.equals("group")){%>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;"><%=IDBean.getMd_same_ct()%></td>
        <%	}
        %>      
        <%-- <td  align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        	<%=IDBean.getMedia_name()%>
        </td> --%>
      </tr>
    </table>
    <%  }%>
          <%}catch(Exception e){System.out.println("issue_data_excel.jsp : "+e);} %>
</body>
</html>