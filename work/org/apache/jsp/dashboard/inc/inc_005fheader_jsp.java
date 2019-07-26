package org.apache.jsp.dashboard.inc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class inc_005fheader_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/dashboard/inc/../../riskv3/inc/sessioncheck.jsp");
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
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction navFormSend( $e ){\r\n");
      out.write("\t\tvar tg = $e ? $e.currentTarget : event.currentTarget;\r\n");
      out.write("\t\t$( \"#subCode\" ).val( $( tg ).attr( \"data-idx\" ) );\r\n");
      out.write("\t\t$( \"#gnbForm\" ).attr( \"action\", $( tg ).attr( \"href\" ) );\r\n");
      out.write("\t\tif( $e.shiftKey ) $( \"#gnbForm\" ).attr( \"target\", \"_blank\" );\r\n");
      out.write("\t\telse $( \"#gnbForm\" ).attr( \"target\", \"_self\" );\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar digitalHref = $( tg ).attr( \"href\" );\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//if(digitalHref == \"../search/search.jsp\"){\r\n");
      out.write("\t\t//\t\r\n");
      out.write("\t\t//\t$.ajax({\r\n");
      out.write("\t\t//\t\tcrossOrigin: true,\r\n");
      out.write("\t\t//        url:\"http://lucyapi.realsn.com/systemCheck?i_type=check\",\r\n");
      out.write("\t\t//        dataType:\"json\",\r\n");
      out.write("\t\t//        success:function(data){\r\n");
      out.write("\t\t//        \tvar jsonObj = JSON.parse(data);\r\n");
      out.write("\t\t//        \tvar checektim = jsonObj.chktime.split(\",\");\r\n");
      out.write("\t\t//        \tif(jsonObj.code == 1){\r\n");
      out.write("\t\t//        \t\talert(\"시스템 정기 점검중입니다. (점검시간 \"+checektim[0]+\":00 ~ \"+checektim[1]+\":00)\");\r\n");
      out.write("\t\t//        \t}else{\r\n");
      out.write("\t\t//        \t\t$( \"#gnbForm\" ).serialize();\r\n");
      out.write("\t\t//    \t\t\t$( \"#gnbForm\" ).submit();\r\n");
      out.write("\t\t//        \t}\r\n");
      out.write("\t\t//        }\r\n");
      out.write("\t    //   });\r\n");
      out.write("\t\t//\t\r\n");
      out.write("\t\t//}else{\r\n");
      out.write("\t\t//\t$( \"#gnbForm\" ).serialize();\r\n");
      out.write("\t\t//\t$( \"#gnbForm\" ).submit();\t\r\n");
      out.write("\t\t//}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#gnbForm\" ).serialize();\r\n");
      out.write("\t\t$( \"#gnbForm\" ).submit();\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<form id=\"gnbForm\" action=\"#\" method=\"post\">\r\n");
      out.write("\t<input id=\"subCode\" name=\"subCode\" type=\"hidden\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<header>\r\n");
      out.write("\t<div class=\"wrap\">\r\n");
      out.write("\t\t<h1>\r\n");
      out.write("\t\t\t<a href=\"../summary/summary.jsp\"><img src=\"../asset/img/h1_logo.gif\" alt=\"대구시청\"></a>\r\n");
      out.write("\t\t</h1>\r\n");
      out.write("\t\t<div class=\"util\">\r\n");
      out.write("\t\t\t<div class=\"user fr\">\r\n");
      out.write("\t\t\t\t<span class=\"user_name\">");
      out.print((String)session.getAttribute("SS_M_NAME") );
      out.write(" 님</span>\r\n");
      out.write("<!-- \t\t\t\t<a href=\"../../riskv3/logout.jsp\" class=\"ui_btn_00\" title=\"로그아웃\"><span>로그아웃</span></a> -->\r\n");
      out.write("\t\t\t\t<a href=\"../../riskv3/main.jsp\" class=\"ui_btn_00\" title=\"시스템 바로가기\"><span>시스템</span></a>\r\n");
      out.write("\t\t\t\t<a href=\"../../riskv3/logout.jsp\" class=\"ui_btn_00\" title=\"로그아웃\"><span>로그아웃</span></a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<nav>\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li><a href=\"../summary/summary.jsp\" data-idx=\"01\" onclick=\"navFormSend( event );return false;\"><span>Summary</span></a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"../mrt/mrt.jsp\" data-idx=\"02\" onclick=\"navFormSend( event );return false;\"><span>주요시정 모니터링</span></a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"../realstate/realstate.jsp\" data-idx=\"03\" onclick=\"navFormSend(event);return false;\"><span>실국별 현황 분석</span></a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"../media/media.jsp\" data-idx=\"04\" onclick=\"navFormSend(event);return false;\"><span>온라인 홍보 매체 현황</span></a></li>\t\t\t\t\r\n");
      out.write("\t\t\t\t<!-- <li><a href=\"../opinion/opinion.jsp\" data-idx=\"03\" onclick=\"navFormSend( event );return false;\"><span>여론동향</span></a></li> -->\r\n");
      out.write("\t\t\t\t<!-- <li><a href=\"../search/search_new.jsp\" data-idx=\"05\" onclick=\"navFormSend( event );return false;\"><span>디지털 동향 분석</span></a></li> -->\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" data-idx=\"05\"  onclick=\"window.open('http://lucy2search.realsn.com/?key=8d1526fabe754a9187129043867df581'); return false;\"><span>트렌드 분석</span></a><li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</nav>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\tif( $( \"header nav > ul > li > a[data-idx='09']\" ).length > 0 ) $( \"header nav\" ).addClass( \"in_datadn\" )\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</header>");
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
