package org.apache.jsp.riskv3.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class admin_005fmain_005fleft_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/../inc/sessioncheck.jsp");
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
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/left_design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("function init()\r\n");
      out.write("{\r\n");
      out.write("\tmnu_chick(document.all.admin_menu1);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var link = new Array(\r\n");
      out.write("\"\",\r\n");
      out.write("\"user/admin_user_list.jsp\",\r\n");
      out.write("\"usergroup/admin_usergroup.jsp\",\r\n");
      out.write("\"logs/user_log_list.jsp?sch_svc=1\",\r\n");
      out.write("\"keyword/admin_keyword.jsp\",\r\n");
      out.write("\"site/admin_site.jsp\",\r\n");
      out.write("\"receiver/receiver_list.jsp\",\r\n");
      out.write("\"classification/classification_mgr.jsp\"\r\n");
      out.write(",\"mobile/mobile_setting.jsp\"\r\n");
      out.write(",\"pcalimi/pcalimi_setting.jsp\"\r\n");
      out.write(",\"alimi/alimi_setting_list.jsp\"\r\n");
      out.write(",\"aekeyword/aekeyword_manager.jsp\"\r\n");
      out.write(",\"tier/tier_main.jsp\"\r\n");
      out.write(",\"tokeyword/tokeyword_manager.jsp\"\r\n");
      out.write(",\"alimi/alimi_log_list.jsp\"\r\n");
      out.write(",\"infogroup/infogroup_manager.jsp\"\r\n");
      out.write(",\"blacksite/blacksite_manager.jsp\"\r\n");
      out.write(",\"hotkeyword/hotkeywordMain.jsp\"\r\n");
      out.write(",\"social/social_manager.jsp\"\r\n");
      out.write(",\"relation_manager/relation_manager.jsp\"\r\n");
      out.write(",\"press_mng/pressMng_list.jsp\"\r\n");
      out.write(",\"sns_manager/sns_manager.jsp\"\r\n");
      out.write(");\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"type\" id=\"type\" value=\"\">\r\n");
      out.write("</form>\r\n");
      out.write("<div id=\"wrap\">\r\n");
      out.write("\t<div id=\"snb\">\r\n");
      out.write("\t\t<h2><img src=\"../../images/left/left_title05.gif\" alt=\"관리자\"></h2>\r\n");
      out.write("\t\t<div class=\"snb\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(1);\" class=\"active\"><span class=\"icon\">-</span>사용자 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(2);\" ><span class=\"icon\">-</span>사용자 그룹/권한 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(3);\" ><span class=\"icon\">-</span>로그 보기</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(4);\" ><span class=\"icon\">-</span>키워드 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(11);\" ><span class=\"icon\">-</span>제외 키워드 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(5);\" ><span class=\"icon\">-</span>수집 사이트 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(12);\" ><span class=\"icon\">-</span>매체 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(7);\" ><span class=\"icon\">-</span>보고서 분류체계 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(18);\" ><span class=\"icon\">-</span>소셜 통계 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(6);\" ><span class=\"icon\">-</span>정보 수신자 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(10);\" ><span class=\"icon\">-</span>알리미 설정 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(14);\" ><span class=\"icon\">-</span>알리미 로그 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(19);\" ><span class=\"icon\">-</span>연관키워드 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(20);\" ><span class=\"icon\">-</span>보도자료 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:mnu_chick(21);\" ><span class=\"icon\">-</span>SNS정보추이 현황 관리</a>\r\n");
      out.write("\t\t\t</li>\r\n");
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
      out.write("\t\tfunction mnu_chick(target) {\r\n");
      out.write("\t\t\tparent.contentsFrame.location.href=link[target];\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
