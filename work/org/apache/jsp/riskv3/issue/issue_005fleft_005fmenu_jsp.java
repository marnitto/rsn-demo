package org.apache.jsp.riskv3.issue;

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

public final class issue_005fleft_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/issue/../inc/sessioncheck.jsp");
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
      response.setContentType("text/html; charset=EUC-KR");
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


      out.write("\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/left_design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<!-- <script type=\"text/JavaScript\">\r\n");
      out.write("\tvar selectedObj = \"\";\r\n");
      out.write("\r\n");
      out.write("    function changeAction(value, obj)\r\n");
      out.write("    {\r\n");
      out.write("    \tselectedObj = obj;\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar f = document.fSend;\r\n");
      out.write("    \tf.target= 'contentsFrame';\r\n");
      out.write("    \t\r\n");
      out.write("    \tif(value=='issueData'){\r\n");
      out.write("        \tobj.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("        \tdocument.getElementById('issueDataText').style.color = 'ffffff';\r\n");
      out.write("        \tdocument.getElementById('issueDataOld').background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("        \tdocument.getElementById('issueDataTextOld').style.color = '000000';\r\n");
      out.write("        \tf.action= 'issue_data_list.jsp';\r\n");
      out.write("    \t\tf.submit();\r\n");
      out.write("        }else if(value == 'issueDataOld'){\r\n");
      out.write("        \tobj.background = '../../images/left/top_left_onbg.gif';\r\n");
      out.write("        \tdocument.getElementById('issueDataTextOld').style.color = 'ffffff';\r\n");
      out.write("        \tdocument.getElementById('issueData').background = '../../images/left/top_left_offbg.gif';\r\n");
      out.write("        \tdocument.getElementById('issueDataText').style.color = '000000';\r\n");
      out.write("        \tf.action= 'issue_data_list_old.jsp';    \t\t\r\n");
      out.write("    \t\tf.submit();\r\n");
      out.write("        }\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\tfunction onMs(obj){\r\n");
      out.write("\t\tobj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction outMs(obj){\r\n");
      out.write("\t\tobj.background='../../images/left/top_left_offbg.gif';\r\n");
      out.write("\t\tselectedObj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("</script> -->\r\n");
      out.write("</head>\r\n");
      out.write("<!-- <body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<form name=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"type\" id=\"type\" value=\"\">\r\n");
      out.write("</form> -->\r\n");
      out.write("<!-- <table width=\"300\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("   <td align=\"right\" valign=\"top\" background=\"../../images/left/top_left_mbg.gif\">\r\n");
      out.write("    <td align=\"right\" valign=\"top\">\r\n");
      out.write("      <table width=\"295\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td><img src=\"../../images/left/left_title02.gif\"></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td bgcolor=\"#FFFFFF\"><img src=\"../../images/left/brank.gif\" width=\"1\" height=\"4\"></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("      </table>\t  \r\n");
      out.write("      \r\n");
      out.write("      <table width=\"295\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" style=\"padding: 0px 5px 0px 0px;\" id=\"issueData\" background=\"../../images/left/top_left_onbg.gif\"  onMouseover=\"onMs(this);\" onmouseout=\"outMs(this);\"><table width=\"280\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"20\"><img src=\"../../images/left/left_mico01.gif\" width=\"16\" height=\"15\"></td>\r\n");
      out.write("              <td class=\"leftM_on\" style=\"padding: 2px 0px 0px 3px;\"><strong><a  id=\"issueDataText\" style=\"color:ffffff;\" href=\"javascript:changeAction('issueData', document.all.issueData);\">관련정보</a></strong></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      \r\n");
      out.write("      <table width=\"295\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" style=\"padding: 0px 5px 0px 0px;\" id=\"issueDataOld\" background=\"../../images/left/top_left_offbg.gif\"  onMouseover=\"onMs(this);\" onmouseout=\"outMs(this);\"><table width=\"280\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"20\"><img src=\"../../images/left/left_mico01.gif\" width=\"16\" height=\"15\"></td>\r\n");
      out.write("              <td class=\"leftM_on\" style=\"padding: 2px 0px 0px 3px;\"><strong><a  id=\"issueDataTextOld\" style=\"color:000000;\" href=\"javascript:changeAction('issueDataOld', document.all.issueDataOld);\">(구)관련정보</a></strong></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      \r\n");
      out.write("      <table width=\"295\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("        \t<td height=\"1\" bgcolor=\"B7B7B7\"></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table> -->\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"type\" id=\"type\" value=\"\">\r\n");
      out.write("</form>\r\n");
      out.write("<div id=\"wrap\">\r\n");
      out.write("\t<div id=\"snb\">\r\n");
      out.write("\t\t<h2><img src=\"../../images/left/left_title02.gif\" alt=\"이슈관리\"></h2>\r\n");
      out.write("\t\t<div class=\"snb\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:changeAction('issueData', document.all.issueData);\" class=\"active\"><span class=\"icon\">-</span>관련정보</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<!-- <li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:changeAction('replyMngr', document.all.replyMngr);\"><span class=\"icon\">-</span>댓글분석</a>\r\n");
      out.write("\t\t\t</li> -->\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t(function($) {\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t// 레프트 메뉴 클릭 - 활성화\r\n");
      out.write("\t\t\t$( \"#snb li a\" ).click(function($e) {\r\n");
      out.write("\t\t\t\t$( \"#snb li a\" ).removeClass( \"active\" );\r\n");
      out.write("\t\t\t\t$( this ).addClass( \"active\" );\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t})(jQuery);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction changeAction(value, obj)\r\n");
      out.write("\t    {\r\n");
      out.write("\t    \tvar f = document.fSend;\r\n");
      out.write("\t    \tf.target= 'contentsFrame';\r\n");
      out.write("\t    \t\r\n");
      out.write("\t    \tif(value == 'issueData'){\r\n");
      out.write("\t\t        f.action= 'issue_data_list.jsp';\r\n");
      out.write("\t\t    \tf.submit();\r\n");
      out.write("\t    \t}else{\r\n");
      out.write("\t    \t\tf.action= 'issue_reply_mngr.jsp';\r\n");
      out.write("\t\t    \tf.submit();\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t       \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<!-- <script>\r\n");
      out.write("if (selectedObj==\"\"){\r\n");
      out.write("\tselectedObj=document.all.issueData;\r\n");
      out.write("\tselectedObj.background='../../images/left/top_left_onbg.gif';\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write(" -->\r\n");
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
