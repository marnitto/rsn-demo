<%/*******************************************************
*  1. 분    류    명 : RSN
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 리스트 엑셀파일 저장
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/%>
<%
	//@ page contentType="text/html; charset="
%>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>

<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaMgr
                 ,risk.search.MetaBean
                 ,risk.search.userEnvInfo"
%>
<%
	DateUtil du = new DateUtil();

    String sCurrDate    = du.getCurrentDate();

    response.setContentType("application/vnd.ms-excel; charset=UTF-8") ;
    response.setHeader("Content-Disposition", "attachment;filename=" + sCurrDate + ".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
%>

<%@include file="../inc/sessioncheck.jsp" %>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    //페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr     smgr = new MetaMgr();

    String sOrder       = pr.getString("sOrder","MT_DATE");       //
    uei.setOrder( pr.getString("sOrder","MT_DATE") );

    String sOrderAlign  = pr.getString("sOrderAlign","DESC");     //
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );

    ArrayList alData = null;
    alData = smgr.getSearchSaveList(
                                       uei.getOrder() + " " + uei.getOrderAlign()   ,   //String psOrder
                                       pr.getString("SaveList")
                                    );
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <table width="780" border="1" cellspacing="0" cellpadding="0">
      <!--
      <tr>
        <td colspan="6" align="center" bgcolor="E8F2F2" class="menu_gray" style="padding: 3px 0px 0px 0px;" >
            검색기간 : <%=uei.getDateFrom()%> ~ <%=uei.getDateTo()%> 키워드 그룹 : <%=smgr.msKeyTitle%>
        </td>
      </tr>
      -->
      <tr>
        <td width="119" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처     </strong></td>
        <td width="119" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 정보유형 </strong></td>
        <td width="440" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="49" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 유사     </strong></td>
        <!--<td width="49" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 댓글     </strong></td>-->
        <td width="84" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수집시간 </strong></td>
      </tr>
    </table>
    <%
    	for( int i = 0 ; i < alData.size() ; i++ ) {
                MetaBean mi = (MetaBean) alData.get(i);
    %>
    <table width="780" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="119" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=mi.getMd_site_name()%>
        </td>
        <td width="119" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        <%
            if ( mi.getMd_type().equals("1") ) {
                out.print("기사");
            } else if ( mi.getMd_type().equals("2") ) {
                out.print("게시");
            } else if ( mi.getMd_type().equals("3") ) {
                out.print("공지");
            }
        %>
        </td>
        <td width="440" align="left" style="padding: 3px 0px 0px 5px;">
          <a href="<%=mi.getMd_url()%>" target="_blank" ><%=su.nvl(mi.getMd_title(),"제목없음")%></a>
        </td>

        <td width="49" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=mi.getMd_same_count()%>
        </td>
        <!--<td width="49" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=mi.getMd_reply_count()%>
        </td>-->
        <td width="84" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=mi.getFormatMd_date("yyyy-MM-dd")%>
        </td>
      </tr>
    </table>
    <%  }%>
</body>
</html>