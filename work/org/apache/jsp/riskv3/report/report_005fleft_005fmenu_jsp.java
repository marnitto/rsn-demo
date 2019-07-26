package org.apache.jsp.riskv3.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.search.GetKGMenu;

public final class report_005fleft_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/report/../inc/sessioncheck.jsp");
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
      response.setContentType("text/html; charset=UTF-8");
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
      out.write("    \r\n");

	ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sInit = (String)session.getAttribute("INIT");

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode().equals("") ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);


      out.write("\r\n");
      out.write("<!-- <html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../css/basic.css\" type=\"text/css\">\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("\r\n");
      out.write("\tvar selectedObj = \"\";\r\n");
      out.write("\r\n");
      out.write("    function changeAction(value, obj)\r\n");
      out.write("    {\r\n");
      out.write("    \tselectedObj = obj;\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar f = document.fSend;\r\n");
      out.write("    \tf.target= 'contentsFrame';\r\n");
      out.write("    \r\n");
      out.write("        if(value=='report'){\r\n");
      out.write("        \tchageColor(value);\r\n");
      out.write("        \tf.action= 'issue_report_list.jsp?ir_type=E';    \t\t\r\n");
      out.write("    \t\tf.submit();\r\n");
      out.write("\t\t} else if (value=='reportWrite'){\r\n");
      out.write("\t\t\tchageColor(value);\r\n");
      out.write("        \tf.action= 'issue_report_creater.jsp';    \t\t\r\n");
      out.write("    \t\tf.submit();\r\n");
      out.write("\t\t} else if (value=='reportOld'){\r\n");
      out.write("\t\t\tchageColor(value);\r\n");
      out.write("        \tf.action= 'issue_report_list_old.jsp?ir_type=D';    \t\t\r\n");
      out.write("    \t\tf.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\tfunction subChangeAction(contents,value)\r\n");
      out.write("\t{\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(contents=='issue')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\t\r\n");
      out.write("\r\n");
      out.write("\t\t\tdocument.all.report.background = '../../images/left/top_left_onbg.gif';   \r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#ffffff';\t\r\n");
      out.write("\t\t\tdocument.all.reportWrite.background = '../../images/left/top_left_offbg.gif';   \r\n");
      out.write("\t\t\tdocument.all.reportWriteText.style.color='#000000';\t\r\n");
      out.write("\t\t\t//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\r\n");
      out.write("\r\n");
      out.write("\t\t\tselectedObj = document.all.report;\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\tf.action= 'issue_report_list.jsp?ir_type='+value;    \t\t\r\n");
      out.write("\t\t\tf.submit();\r\n");
      out.write("\t\t}else if(contents=='statistics'){\r\n");
      out.write("\t\t\tif(value=='A')\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\talert('변경 중 입니다.');\r\n");
      out.write("\t\t\t\t/*\r\n");
      out.write("\t\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\t\tf.action= '../statistics/statistics_issue.jsp';  \t\t\r\n");
      out.write("\t\t\t\tf.submit();\r\n");
      out.write("\t\t\t\t*/\r\n");
      out.write("\t\t\t}else if(value=='B'){\r\n");
      out.write("\t\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\t\tf.action= '../statistics/statistics_analysis.jsp';    \t\t\r\n");
      out.write("\t\t\t\tf.submit();\t\t\r\n");
      out.write("\t\t\t}else if(value=='C'){\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\t\tf.action= '../statistics/statistics_collect_keyword.jsp';    \t\t\r\n");
      out.write("\t\t\t\tf.submit();\t\t\t\t\r\n");
      out.write("\t\t\t}else if(value=='D'){\t\t\t\t\r\n");
      out.write("\t\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\t\tf.action= '../statistics/statistics_collect_site.jsp';    \t\t\r\n");
      out.write("\t\t\t\tf.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else if(contents=='issueOld'){\r\n");
      out.write("\r\n");
      out.write("\t\t\tdocument.all.report.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\r\n");
      out.write("\t\t\tdocument.all.reportWrite.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportWriteText.style.color='#000000';\r\n");
      out.write("\t\t\tdocument.all.reportOld.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#ffffff';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tselectedObj = document.all.report;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\tf.action= 'issue_report_list_old.jsp?ir_type='+value;    \t\t\r\n");
      out.write("\t\t\tf.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction chageColor( value ) {\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\r\n");
      out.write("\t    if( value == 'report' ){\r\n");
      out.write("\t\t\tdocument.all.report.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#ffffff';\r\n");
      out.write("\t\t\tdocument.all.reportWrite.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportWriteText.style.color='#000000';\r\n");
      out.write("\t\t\t//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\t\r\n");
      out.write("\t\t} else if( value == 'reportWrite' ){\r\n");
      out.write("\t\t\tdocument.all.reportWrite.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportWriteText.style.color='#ffffff';\r\n");
      out.write("\t\t\tdocument.all.report.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\r\n");
      out.write("\t\t\t//document.all.reportOld.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\t\r\n");
      out.write("\t\t} else if( value == 'reportOld' ){\r\n");
      out.write("\t\t\tdocument.all.report.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#000000';\r\n");
      out.write("\t\t\tdocument.all.reportWrite.background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportWriteText.style.color='#000000';\r\n");
      out.write("\t\t\t//document.all.reportOld.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t\t\tdocument.all.reportText.style.color='#ffffff';\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tfunction saveEnv()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( parent.contentsFrame.fCheck )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tparent.contentsFrame.env_save();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tfunction goReportList(type)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.fSend;\t\t\r\n");
      out.write("\t\tchageColor('report');\r\n");
      out.write("\t\tf.action = 'issue_report_list.jsp';\r\n");
      out.write("\t\tf.target = 'contentsFrame';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction onMs(obj){\r\n");
      out.write("\t\tobj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction outMs(obj){\r\n");
      out.write("\t\tobj.background='../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\tselectedObj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("/*\r\n");
      out.write("\tfunction igList_resize(){\r\n");
      out.write("  \t\tvar ch = document.body.clientHeight;\r\n");
      out.write("  \t\tvar ifmObj = document.all.ifr_menu.style;\r\n");
      out.write("\t\tvar newH = ch - 230;\r\n");
      out.write("  \t\tif (newH <100) newH = 100;\r\n");
      out.write("  \t\tifmObj.height = newH;\r\n");
      out.write("  \t}\r\n");
      out.write("*/\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<form name=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"type\" id=\"type\" value=\"\">\r\n");
      out.write("</form>\r\n");
      out.write("<table width=\"300\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("   <td align=\"right\" valign=\"top\" background=\"../../images/left/top_left_mbg.gif\">\r\n");
      out.write("    <td align=\"right\" valign=\"top\">\r\n");
      out.write("      <table width=\"295\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td><img src=\"../../images/left/left_title03.gif\" width=\"295\" height=\"52\"></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td bgcolor=\"#FFFFFF\"><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"4\"></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("      </table>\t  \r\n");
      out.write("      \r\n");
      out.write("      <table width=\"295\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("        \t<td height=\"1\" bgcolor=\"B7B7B7\"></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      \r\n");
      out.write("\t  <table width=\"295\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" id=\"report\" class=\"leftM_on\" style=\"padding: 0px 5px 0px 0px;\" background=\"../../images/left/top_left_offbg.gif\"  onMouseover=\"onMs(this);\" onmouseout=\"outMs(this);\"><table width=\"280\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td width=\"20\"><img src=\"../../images/left/left_mico03.gif\" width=\"16\" height=\"15\"></td>\r\n");
      out.write("                <td class=\"leftM_off\" style=\"padding: 2px 0px 0px 3px; font-weight: bold;\"><a  id=\"reportText\" style=\"color:000000;\" href=\"javascript:changeAction('report', document.all.report);\">보고서 관리</a></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("          </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>      \t\t        \r\n");
      out.write("\t  <table width=\"295\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td width=\"28\" height=\"30\" align=\"right\"><img src=\"../../images/left/left_mico04.gif\" width=\"14\" height=\"11\"></td>\r\n");
      out.write("          <td width=\"267\" style=\"padding: 3px 0px 0px 5px;\"><a href=\"javascript:subChangeAction('issue','E');\"><strong>긴급보고서</strong></a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td colspan=\"2\" ><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("          <td width=\"28\" height=\"30\" align=\"right\"><img src=\"../../images/left/left_mico04.gif\" width=\"14\" height=\"11\"></td>\r\n");
      out.write("          <td width=\"267\" style=\"padding: 3px 0px 0px 5px;\"><a href=\"javascript:subChangeAction('issue','D1\\',\\'I');\"><strong>일일보고서</strong></a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td colspan=\"2\" ><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        \r\n");
      out.write("        <tr style=\"display: none;\">\r\n");
      out.write("          <td width=\"28\" height=\"30\" align=\"right\"><img src=\"../../images/left/left_mico04.gif\" width=\"14\" height=\"11\"></td>\r\n");
      out.write("          <td width=\"267\" style=\"padding: 3px 0px 0px 5px;\"><a href=\"javascript:subChangeAction('issue','W');\"><strong>주간보고서</strong></a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr style=\"display: none;\">\r\n");
      out.write("          <td colspan=\"2\" ><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("        </tr> \r\n");
      out.write("        <tr style=\"display: none;\">\r\n");
      out.write("          <td width=\"28\" height=\"30\" align=\"right\"><img src=\"../../images/left/left_mico04.gif\" width=\"14\" height=\"11\"></td>\r\n");
      out.write("          <td width=\"267\" style=\"padding: 3px 0px 0px 5px;\"><a href=\"javascript:subChangeAction('issue','D1');\"><strong>온라인이슈 일일 보고서</strong></a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        \r\n");
      out.write("        <tr>\r\n");
      out.write("          <td colspan=\"2\" ><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      \r\n");
      out.write("      <table width=\"295\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" id=\"reportWrite\" class=\"leftM_on\" style=\"padding: 0px 5px 0px 0px;\" background=\"../../images/left/top_left_offbg.gif\"  onMouseover=\"onMs(this);\" onmouseout=\"outMs(this);\"><table width=\"280\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td width=\"20\"><img src=\"../../images/left/left_mico03.gif\" width=\"16\" height=\"15\"></td>\r\n");
      out.write("                <td class=\"leftM_off\" style=\"padding: 2px 0px 0px 3px; font-weight: bold;\"><a  id=\"reportWriteText\" style=\"color:000000;\" href=\"javascript:changeAction('reportWrite', document.all.reportWrite);\">보고서 작성</a></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("          </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script>\r\n");
      out.write("if (selectedObj==\"\"){\r\n");
      out.write("\tselectedObj=document.all.report;\r\n");
      out.write("\tselectedObj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("}\r\n");
      out.write("</script> -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/left_design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("\t<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("  <script type=\"text/javascript\" src=\"../asset/js/selectivizr-min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"wrap\">\r\n");
      out.write("<form name=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"type\" id=\"type\" value=\"\">\r\n");
      out.write("</form>\r\n");
      out.write("\t<div id=\"snb\">\r\n");
      out.write("\t\t<h2><img src=\"");
      out.print(SS_URL);
      out.write("images/left/left_title03.gif\" alt=\"보고서관리\"></h2>\r\n");
      out.write("\t\t<div class=\"snb\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:subChangeAction('issue','D1\\',\\'I');\"><span class=\"icon\">-</span>보고서 관리</a>\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:subChangeAction('issue','D1\\',\\'I');\" class=\"active\"><span class=\"icon\">-</span>주간보고서</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"last_child\"><a href=\"javascript:subChangeAction('issue','E');\"><span class=\"icon\">-</span>이슈보고서</a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:changeAction('reportWrite', document.all.reportWrite);\"><span class=\"icon\">-</span>보고서 작성</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t(function($) {\r\n");
      out.write("\t\t\thndl_nav();\r\n");
      out.write("\t\t\tfunction hndl_nav () {\r\n");
      out.write("\t\t\t\t$( this ).parent().parent().parent().find( \"> a\" ).removeClass( \"active\" );\r\n");
      out.write("\t\t\t\t$( \"#snb .snb > ul > li > ul > li > a\" ).each(function(){\r\n");
      out.write("\t\t\t\t\tif( $( this ).hasClass( \"active\" ) ) {\r\n");
      out.write("\t\t\t\t\t\t$( this ).parent().parent().parent().find( \"> a\" ).addClass( \"active\" );\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$( \"#snb > .snb > ul > li > a\" ).click(function($e) {\r\n");
      out.write("\t\t\t\t$( \"#snb li a\" ).removeClass( \"active\" );\r\n");
      out.write("\t\t\t\tif( $( this ).parent().find( \"ul\" ).length > 0 ) {\r\n");
      out.write("\t\t\t\t\t$( this ).parent().find( \"ul > li > a\" ).eq( 0 ).addClass( \"active\" );\r\n");
      out.write("\t\t\t\t\thndl_nav();\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t$( this ).addClass( \"active\" );\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$( \"#snb > .snb > ul > li > ul > li > a\" ).click(function($e) {\r\n");
      out.write("\t\t\t\t$( \"#snb li a\" ).removeClass( \"active\" );\r\n");
      out.write("\t\t\t\t$( this ).addClass( \"active\" );\r\n");
      out.write("\t\t\t\thndl_nav();\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t})(jQuery);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction changeAction(value, obj)\r\n");
      out.write("\t    {\r\n");
      out.write("\t    \tselectedObj = obj;\r\n");
      out.write("\t    \t\r\n");
      out.write("\t    \tvar f = document.fSend;\r\n");
      out.write("\t    \tf.target= 'contentsFrame';\r\n");
      out.write("\t    \r\n");
      out.write("\t        if(value=='report'){\r\n");
      out.write("\t        \t\r\n");
      out.write("\t        \tf.action= 'issue_report_list.jsp?ir_type=E';    \t\t\r\n");
      out.write("\t    \t\tf.submit();\r\n");
      out.write("\t\t\t} else if (value=='reportWrite'){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t        \tf.action= 'issue_report_creater.jsp';    \t\t\r\n");
      out.write("\t    \t\tf.submit();\r\n");
      out.write("\t\t\t} else if (value=='reportOld'){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t        \tf.action= 'issue_report_list_old.jsp?ir_type=D';    \t\t\r\n");
      out.write("\t    \t\tf.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t    }\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction subChangeAction(contents,value)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\tf.target= 'contentsFrame';\t\t\r\n");
      out.write("\t\t\tf.action= 'issue_report_list.jsp?ir_type='+value;    \t\t\r\n");
      out.write("\t\t\tf.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
