package org.apache.jsp.riskv3.admin.press_005fmng;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.*;
import risk.admin.pressMng.pressMng;
import risk.util.ParseRequest;
import risk.util.PageIndex;
import risk.util.DateUtil;

public final class pressMng_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/press_mng/../../inc/sessioncheck.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
//@ page contentType="text/html; charset=euc-kr"
      out.write("\r\n");
      out.write("\r\n");

    
	String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;
    String SS_M_DEPT =   (String)session.getAttribute("SS_M_DEPT")    == null ? "": (String)session.getAttribute("SS_M_DEPT") ;    
    String SS_M_IP =   (String)session.getAttribute("SS_M_IP")    == null ? "": (String)session.getAttribute("SS_M_IP") ;    
    String SS_M_MAIL =   (String)session.getAttribute("SS_M_MAIL")    == null ? "": (String)session.getAttribute("SS_M_MAIL") ;    
    
	String SS_ML_URL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	
	String SS_SEARCHDATE = (String)session.getAttribute("SS_SEARCHDATE")    == null ? "": (String)session.getAttribute("SS_SEARCHDATE") ;
	
	
    

	if ((SS_M_ID.equals("")) || !SS_M_IP.equals(request.getRemoteAddr()) ) {
		ConfigUtil cu = new ConfigUtil();
		out.print("<SCRIPT Language=JavaScript>");
		//out.print("window.setTimeout( \" top.document.location = "+cu.getConfig("URL")+"'riskv3/error/sessionerror.jsp' \") ");
		out.print("top.document.location = '"+cu.getConfig("URL")+"riskv3/error/sessionerror.jsp'");
        out.print("</SCRIPT>");
    }
	

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pressMng pMgr = new pressMng();
	pr.printParams();

	int rowCnt = 10;
	int iNowPage = pr.getInt("nowpage", 1);

	String searchKey = pr.getString("searchKey", "");

	/*****시간설정*****/
	//일자
	String sDate = pr.getString("sDate",
			du.addDay_v2(du.getCurrentDate(), -7));
	String eDate = pr.getString("eDate", du.addDay_v2(du.getCurrentDate(), -1));

	//시간
	String sTime = pr.getString("sTime", "00");
	String eTime = pr.getString("eTime", "24");

	String sTimeSet = sTime + ":00:00";
	String eTimeSet = "";
	if (eTime.equals("24")) {
		eTimeSet = "23:59:59";
	} else {
		eTimeSet = eTime + ":00:00";
	}
	/*****************/

	ArrayList arList = pMgr.getPressDataList(iNowPage, rowCnt,
			sDate, eDate, sTimeSet, eTimeSet, searchKey);

	int iTotalCnt = pMgr.getTotalCnt();
	int iTotalPage = iTotalCnt / rowCnt;
	if ((iTotalCnt % rowCnt) > 0)
		iTotalPage++;

/* 	String strMsg = "";
	strMsg = " <b>" + iTotalCnt + "건</b>, " + iNowPage + "/"
			+ iTotalPage + " pages"; */

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>RISK</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<style>\r\n");
      out.write("iframe.hide {\r\n");
      out.write("\tborder: 0px solid red;\r\n");
      out.write("\tposition: absolute;\r\n");
      out.write("\ttop: 0px;\r\n");
      out.write("\tleft: 0px;\r\n");
      out.write("\tz-index: -99;\r\n");
      out.write("\twidth: 148px;\r\n");
      out.write("\theight: 150px;\r\n");
      out.write("\tfilter: alpha(opacity = 0);\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function pageClick(paramUrl) {\r\n");
      out.write("\t//document.fSend.nowpage.value = paramUrl.substr(\tparamUrl.indexOf(\"=\") + 1, paramUrl.length);\r\n");
      out.write("\tdocument.fSend.action = \"pressMng_list.jsp\"+paramUrl;\r\n");
      out.write("\tdocument.fSend.target = \"\";\r\n");
      out.write("\tdocument.fSend.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function search_list(){\r\n");
      out.write("\tdocument.fSend.nowpage.value = 1;\r\n");
      out.write("\tdocument.fSend.action = \"pressMng_list.jsp\";\r\n");
      out.write("\tdocument.fSend.target = \"\";\r\n");
      out.write("\tdocument.fSend.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function excel(){\r\n");
      out.write("\t//document.iframePrc.location = \"press_excel.jsp\";\r\n");
      out.write("\tdocument.fSend.action = \"press_excel.jsp\";\r\n");
      out.write("\tdocument.fSend.target = \"iframePrc\";\r\n");
      out.write("\tdocument.fSend.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function popup(flag, d_seq, wDate){\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tdocument.fSend.nowpage.value = 1;\r\n");
      out.write("\twindow.open(\"about:blank\",'popup_press', 'width=850,height=700,scrollbars=yes');\r\n");
      out.write("\tf.action = 'popup_press_list.jsp?d_type='+flag+\"&d_seq=\"+d_seq+\"&wDate=\"+wDate;\r\n");
      out.write("\tf.target='popup_press';\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function updateKeyword(d_seq){\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\twindow.open(\"about:blank\",'popup_press_update', 'width=850,,height=150,scrollbars=yes');\r\n");
      out.write("\tf.action = 'popup_addKeyword.jsp?d_seq='+d_seq;\r\n");
      out.write("\tf.target='popup_press_update';\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"margin-left: 15px;\">\r\n");
      out.write("\t<form name=\"fSend\" method=\"post\" action=\"pressMng_list.jsp\">\r\n");
      out.write("\t\t<input type=\"hidden\" name=\"mode\" value=\"\"> <input\r\n");
      out.write("\t\t\ttype=\"hidden\" name=\"nowpage\" value=\"");
      out.print(iNowPage);
      out.write("\">\r\n");
      out.write("\t\t<table style=\"width: 100%; height: 100%;\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t\t\t\t<table style=\"width: 820px;\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\t\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"tit_bg\" style=\"height: 67px; padding-top: 20px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<table style=\"width: 100%;\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/tit_icon.gif\" /><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/pressgroup/tit_002222.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">보도자료관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"search_box\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th style=\"height: 30px;\">검색단어</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"vertical-align: middle\"><input\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 300px; vertical-align: middle;\" class=\"textbox\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\ttype=\"text\" name=\"searchKey\" value=\"");
      out.print(searchKey);
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonkeydown=\"javascript:if (event.keyCode == 13) { search_list(); }\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"width: 10px\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th style=\"height: 30px;\">검색기간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"86\"><input\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 90px; text-align: center\" class=\"textbox\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\ttype=\"text\" id=\"sDate\" name=\"sDate\" value=\"");
      out.print(sDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"27\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/pressgroup/btn_calendar01.png\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"cursor: pointer\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"calendar_sh(document.getElementById('sDate'))\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"11\" align=\"center\">~</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"86\"><input\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 90px; text-align: center\" class=\"textbox\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\ttype=\"text\" id=\"eDate\" name=\"eDate\" value=\"");
      out.print(eDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"27\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/pressgroup/btn_calendar01.png\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"cursor: pointer\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"calendar_sh(document.getElementById('eDate'))\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"width: 5px\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/btn_search.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"search_list();\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tstyle=\"cursor: pointer; vertical-align: middle\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td style=\"height: 40px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<table style=\"width: 100%;\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<!-- <td style=\"width: 76px;\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/btn_allselect.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"allselect();\" style=\"cursor: pointer;\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/receiver/btn_del.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"PressgroupDataDel();\" style=\"cursor: pointer;\" /></td> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"width: 88px;\" align=\"right\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/issue/btn_allexelsave.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"excel();\" style=\"cursor: pointer;\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"table-layout: fixed;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"8%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"15%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"*\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"12%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"5%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"13%\">\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"8%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>문서번호</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>작성일자</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>제목</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th colspan=\"2\">주요키워드</th>\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>유사<br>(News/Twitter)</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>전체<br>댓글수</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

									Map map = null;
									if(	arList.size() > 0	){
										String keyword ="";
										for(int i=0; i < arList.size(); i++){
											map = new HashMap();
											map = (HashMap)arList.get(i);
											keyword = map.get("d_keyword").toString();											
											//keyword = keyword.split(" ")[0];
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px\">");
      out.print(map.get("d_seq"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px\">");
      out.print(map.get("d_date"));
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px\"><p style=\"text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: left;\" title=\"");
      out.print(map.get("d_title"));
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://hub.buzzms.co.kr?url=");
      out.print(map.get("encode_url"));
      out.write("\" target=\"_blank\">");
      out.print(map.get("d_title"));
      out.write("</a>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px;text-align: left;\"><p title=\"");
      out.print(map.get("d_keyword").toString());
      out.write("\" style=\"text-overflow: ellipsis; overflow: hidden; white-space: nowrap;display:inlie-block;padding:0 8px 0 0;line-height:16px;vertical-align:top\">");
      out.print(keyword);
      out.write("</p></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px;\"><div><img src=\"../../../images/admin/pressgroup/btn_modify.gif\" title=\"");
      out.print(map.get("d_keyword").toString());
      out.write("\" onclick=\"updateKeyword('");
      out.print(map.get("d_seq"));
      out.write("');\" style=\"cursor: pointer;vertical-align:top\"></div></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"line-height:16px\"><a href=\"javascript:popup(1,");
      out.print(map.get("d_seq"));
      out.write(',');
      out.write('\'');
      out.print(map.get("d_date"));
      out.write("');\" >");
      out.print(map.get("d_same_count"));
      out.write("</a> / <a href=\"javascript:popup(2,");
      out.print(map.get("d_seq"));
      out.write(',');
      out.write('\'');
      out.print(map.get("d_date"));
      out.write("');\">");
      out.print(map.get("d_tw_count"));
      out.write("</a></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td  style=\"line-height:16px\">");
      out.print(map.get("reply_count") );
      out.write(" </td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

										}
									}
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td style=\"height: 40px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<table style=\"width: 100%;\" border=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"width: 128px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<!-- <td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/btn_allselect.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"allselect();\" style=\"cursor: pointer;\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/btn_del.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"PressgroupDataDel();\" style=\"cursor: pointer;\" /></td> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td align=\"right\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/issue/btn_allexelsave.gif\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"excel();\" style=\"cursor: pointer;\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<table align=\"center\" style=\"padding-top: 10px;\" border=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellpadding=\"0\" cellspacing=\"1\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(iNowPage, iTotalPage, "", ""));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t<iframe id=\"iframePrc\" name=\"iframePrc\" style=\"display: none;\"\r\n");
      out.write("\t\twidth=\"0px\" height=\"0px\"></iframe>\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("\t<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\r\n");
      out.write("\t\tid=\"calendar_conclass\" style=\"position: absolute; display: none;\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><img src=\"../../../images/calendar/menu_bg_004.gif\"\r\n");
      out.write("\t\t\t\twidth=\"162\" height=\"2\"></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td align=\"center\"\r\n");
      out.write("\t\t\t\tbackground=\"../../../images/calendar/menu_bg_005.gif\"><table\r\n");
      out.write("\t\t\t\t\twidth=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td height=\"6\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td id=\"calendar_calclass\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td height=\"5\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><img src=\"../../../images/calendar/menu_bg_006.gif\"\r\n");
      out.write("\t\t\t\twidth=\"162\" height=\"2\"></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
