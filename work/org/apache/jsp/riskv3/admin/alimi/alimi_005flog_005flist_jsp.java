package org.apache.jsp.riskv3.admin.alimi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.admin.alimi.AlimiLogMgr;
import risk.admin.alimi.AlimiLogSuperBean;
import risk.admin.alimi.AlimiSettingDao;
import risk.mobile.AlimiSettingBean;
import risk.util.ParseRequest;
import java.util.ArrayList;
import java.util.*;
import risk.util.PageIndex;
import risk.util.DateUtil;

public final class alimi_005flog_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/alimi/../../inc/sessioncheck.jsp");
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

	
	ParseRequest pr = new ParseRequest(request);	
	pr.printParams();
	DateUtil du = new DateUtil();
	
	
	String searchKey = pr.getString("searchKey");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	//검색날짜 설정 : 기본 1일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}
	
	String mal_type = pr.getString("mal_type","1");
	String as_type = pr.getString("as_type","");
	String as_seq = pr.getString("as_seq","");
	
	int rowCnt = 10;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	
	AlimiLogMgr lMgr = new AlimiLogMgr();
	
	ArrayList reData = lMgr.getAlimiLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, as_type, as_seq);
	
	
	int iTotalPage      = lMgr.getFullCnt() / rowCnt;
    if ( ( lMgr.getFullCnt() % rowCnt ) > 0 ) iTotalPage++;
    
    AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = new AlimiSettingBean();
	ArrayList arrAlimiSetList = new ArrayList();
	arrAlimiSetList = asDao.getAlimiSttingList();
	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>POSCO</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<style>\r\n");
      out.write("iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("</style>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction pageClick( paramUrl ) {\r\n");
      out.write("\tvar f = document.getElementById('fSend');\r\n");
      out.write("    f.action = \"alimi_log_list.jsp\" + paramUrl;\r\n");
      out.write("    f.submit();\r\n");
      out.write("\t\t/*\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tf.nowpage.value = paramUrl.substr(paramUrl.indexOf(\"=\")+1, paramUrl.length);\r\n");
      out.write("\t\tf.action='alimi_log_list.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t*/\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\t//Url 링크\r\n");
      out.write(" \tvar chkPop = 1;\r\n");
      out.write("\tfunction hrefPop(url){\r\n");
      out.write("\t\t//window.open(url,'hrefPop'+chkPop,'');\r\n");
      out.write("\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');\r\n");
      out.write("\t\tchkPop++;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//검색\r\n");
      out.write(" \tfunction search(){ \t\t\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write(" \t\t\t\t\r\n");
      out.write("\t\tf.nowpage.value = '1';\r\n");
      out.write(" \t\tf.action = 'alimi_log_list.jsp';\r\n");
      out.write(" \t\tf.target = '';\r\n");
      out.write(" \t\tf.submit(); \t\r\n");
      out.write(" \t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction setExcel(){\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tf.target = 'ifrm';\r\n");
      out.write("\t\tf.action = 'ex_alimi_log_list.jsp';\r\n");
      out.write(" \t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction setSMSExcel(){\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tf.target = 'ifrm';\r\n");
      out.write("\t\tf.action = 'alimi_log_sns_cntList.jsp';\r\n");
      out.write(" \t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<iframe id=\"ifrm\" name=\"ifrm\" style=\"display: none;\"></iframe>\r\n");
      out.write("<form id=\"fSend\" name=fSend method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(iNowPage);
      out.write("\">\r\n");
      out.write("<!-- <input type=\"hidden\" name=\"as_seq\"> -->\r\n");
      out.write("<input type=\"hidden\" name=\"as_seqs\">\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\" /><img src=\"../../../images/admin/alimi/tit_0918.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">알리미 로그관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\t\t\t<tr style=\"display: none\">\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/alimi/btn_allselect.gif\" onclick=\"allselect();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/btn_del.gif\" onclick=\"delList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t    <td><table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t      <tr>\r\n");
      out.write("\t\t\t\t        <td width=\"15\"><img src=\"../../../images/issue/table_bg_01.gif\" width=\"15\" height=\"35\" /></td>\r\n");
      out.write("\t\t\t\t        <td width=\"793\" background=\"../../../images/issue/table_bg_02.gif\"><table width=\"790\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t          <tr>\r\n");
      out.write("\t\t\t\t            <td width=\"17\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"62\" class=\"b_text\"><strong>검색단어 </strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"323\"><input type=\"text\" class=\"textbox3\" style=\"width:310px;\" name=\"searchKey\" value=\"");
      out.print(searchKey);
      out.write("\" onkeydown=\"Javascript:if(event.keyCode == 13){search();}\"/></td>\r\n");
      out.write("\t\t\t\t            <td width=\"16\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"60\" class=\"b_text\"><strong>검색기간</strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"312\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t              <tr>\r\n");
      out.write("\t\t\t\t                <td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"sDateFrom\" name=\"sDateFrom\" value=\"");
      out.print(sDateFrom);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('sDateFrom'))\"/></td>\r\n");
      out.write("\t\t\t\t                <td width=\"11\" align=\"center\">~</td>\r\n");
      out.write("\t\t\t\t                <td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"sDateTo\" name=\"sDateTo\" value=\"");
      out.print(sDateTo);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('sDateTo'))\"/></td>\r\n");
      out.write("\t\t\t\t                <td width=\"75\"><img src=\"../../../images/issue/search_btn.gif\" width=\"61\" height=\"22\" hspace=\"5\" align=\"absmiddle\" onclick=\"search()\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t              </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t          </tr>\r\n");
      out.write("\t\t\t\t        </table></td>\r\n");
      out.write("\t\t\t\t        <td width=\"12\"><img src=\"../../../images/issue/table_bg_03.gif\" width=\"12\" height=\"35\" /></td>\r\n");
      out.write("\t\t\t\t      </tr>\r\n");
      out.write("\t\t\t\t    </table></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t    <td><table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t      <tr>\r\n");
      out.write("\t\t\t\t        <td width=\"1\" bgcolor=\"#93a6b4\"></td>\r\n");
      out.write("\t\t\t\t        <td width=\"818\" bgcolor=\"#e6eaed\" style=\"padding:10px 0px 5px 0px\"><table width=\"798\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t          <tr>\r\n");
      out.write("\t\t\t\t            <td height=\"24\" style=\"padding:0px 0px 5px 7px\"><table width=\"790\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t              <tr>\r\n");
      out.write("\t\t\t\t                <td width=\"7%\" class=\"b_text\"><img src=\"../../../images/issue/icon_search_bullet.gif\" width=\"9\" height=\"9\" /> <strong>상     태</strong></td>\r\n");
      out.write("\t\t\t\t                <td width=\"22%\"><select name=\"mal_type\" class=\"textbox3\" style=\"width: 105px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"1\" ");
if(mal_type.equals("1")){out.print("selected");}
      out.write(">발송</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"0\" ");
if(mal_type.equals("0")){out.print("selected");}
      out.write(">실패</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"2\" ");
if(mal_type.equals("2")){out.print("selected");}
      out.write(">유사</option>\r\n");
      out.write("\t\t\t\t                                </select></td>\r\n");
      out.write("\t\t\t\t                <td width=\"10%\" class=\"b_text\"><img src=\"../../../images/issue/icon_search_bullet.gif\" width=\"9\" height=\"9\" /> <strong>발송구분</strong></td>\r\n");
      out.write("\t\t\t\t                <td width=\"22%\"><select name=\"as_type\" class=\"textbox3\" style=\"width: 105px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"1\" ");
if(as_type.equals("1")){out.print("selected");}
      out.write(">이메일</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"2\" ");
if(as_type.equals("2")){out.print("selected");}
      out.write(">SMS</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t                  <option value=\"3\" ");
if(as_type.equals("3")){out.print("selected");}
      out.write(">R-rimi</option>\r\n");
      out.write("\t\t\t\t                                </select></td>\r\n");
      out.write("\t\t\t\t                <td width=\"12%\" class=\"b_text\"><img src=\"../../../images/issue/icon_search_bullet.gif\" width=\"9\" height=\"9\" /> <strong>알리미설정</strong></td>\r\n");
      out.write("\t\t\t\t                <td width=\"*\"><select name=\"as_seq\" class=\"textbox3\" style=\"width: 105px\">\r\n");
      out.write("\t\t\t\t\t                \t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t                \t");

					                	String tmp[] = new String[2];
				                		if(arrAlimiSetList.size() > 0){
				                			for(int i =0; i < arrAlimiSetList.size(); i ++){
				                				tmp = (String[])arrAlimiSetList.get(i);
					                	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t    <option value=\"");
      out.print(tmp[0]);
      out.write('"');
      out.write(' ');
if(as_seq.equals(tmp[0])){out.print("selected");}
      out.write('>');
      out.print(tmp[1]);
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t    ");
} }
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t    \r\n");
      out.write("\t\t\t\t                    </select></td>                \r\n");
      out.write("\t\t\t\t              </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t          </tr>\r\n");
      out.write("\t\t\t\t        </table></td>\r\n");
      out.write("\t\t\t\t        <td width=\"1\" bgcolor=\"#93a6b4\"></td>\r\n");
      out.write("\t\t\t\t      </tr>\r\n");
      out.write("\t\t\t\t    </table></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t    <td height=\"1\" bgcolor=\"#93a6b4\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr height=\"30px\">\r\n");
      out.write("\t\t\t\t<td style=\"text-align: right;vertical-align: middle;\">\r\n");
      out.write("\t\t\t\t\t<!-- 특정 아이디만 사용할 수 있도록 제한  시작-->\r\n");
      out.write("\t\t\t\t\t");
if(SS_M_ID.equals("devel") || SS_M_ID.equals("bizdev")){ 
      out.write("<span style=\"cursor: pointer;vertical-align: middle;\" onclick=\"setSMSExcel();\">SMS 발송량 내려받기</span>");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t<!-- 특정 아이디만 사용할 수 있도록 제한  끝-->\r\n");
      out.write("\t\t\t\t\t<img alt=\"엑셀다운로드\" src=\"../../../images/admin/site/excel_save.gif\" style=\"cursor: pointer;\" onclick=\"setExcel();\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width: 820px;\">\r\n");
      out.write("\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t<col width=\"131\"/><col width=\"49\"/><col width=\"477\"/><col width=\"32\"/><col width=\"49\"/><col width=\"82\"/>\r\n");
      out.write("\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>제 목</th>\r\n");
      out.write("\t\t\t\t\t\t<th>수신자</th>\r\n");
      out.write("\t\t\t\t\t\t<th>내     용</th>\r\n");
      out.write("\t\t\t\t\t\t<th></th>\r\n");
      out.write("\t\t\t\t\t\t<th>상 태</th>\r\n");
      out.write("\t\t\t\t\t\t<th>발송시간</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	
	if(reData.size()>0){
		AlimiLogSuperBean.AlimiLogList abean = null;
		String status = "";
		String source = "";
	  	for(int i=0; i < reData.size(); i++)
		{
	  		abean = 	(AlimiLogSuperBean.AlimiLogList)reData.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		
	  		if(abean.getMal_type().equals("1")){
	  			status = "발송";
	  		}else if(abean.getMal_type().equals("0")){
	  			status = "실패";
	  		}else if(abean.getMal_type().equals("2")){
	  			status = "유사";
	  		}else{
	  			status = "";
	  		}
	  		if("1".equals(abean.getAs_type())){
	  			source = "ico_email.gif";
	  		}else if("2".equals(abean.getAs_type())){
	  			source = "ico_sms.gif";
	  		}else if("3".equals(abean.getAs_type())){
	  			source = "ico_Rrimi.gif";
	  		}else{
	  			source = "";
	  		}
	  		

      out.write("\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(abean.getAs_title());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(abean.getAs_cnt()+"명");
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t<td style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t\t<div style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:470px;\">\r\n");
      out.write("\t\t\t\t\t\t\t<a onClick=\"hrefPop('");
      out.print(abean.getMt_url());
      out.write("');\" href=\"javascript:void(0);\">");
      out.print(abean.getSend_message());
      out.write("</a>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t");
	if(!source.equals("")){	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<img src=\"");
      out.print(SS_URL);
      out.write("images/admin/alimi/");
      out.print(source);
      out.write("\" >\r\n");
      out.write("\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td style=\"\">");
      out.print(status);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print( du.getDate(abean.getMal_send_date(), "MM-dd HH:mm"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");

		}
	}else{

      out.write("\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"7\" width=\"820\" style=\"font-weight:bold;height:40px\" align=\"center\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
		
	}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(iNowPage, iTotalPage,"","" ));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\t\t            \r\n");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" background=\"../../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>                                \t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"calendar_calclass\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"5\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
