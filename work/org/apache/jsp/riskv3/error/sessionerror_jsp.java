package org.apache.jsp.riskv3.error;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sessionerror_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

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

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title>NCS System</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");

/*
HttpSession sess = request.getSession(true);
         out.print("<b>[Requested Session유효여부]:</b>" +  request.isRequestedSessionIdValid() + "<BR>");
         out.print("<b>[Session 쿠키사용여부]:</b>" + request.isRequestedSessionIdFromCookie() + "<BR>"); 
         out.print("<b>[Session URL사용여부]:</b>" + request.isRequestedSessionIdFromURL() + "<BR>"); 
         out.print("<b>[Requested Session Id]:</b>" + request.getRequestedSessionId() + "<BR>");
         out.print("<b>[Session ID]:</b>" + sess.getId() + "<BR>");
		 out.print("<b>[Session Max Time]:</b>" + session.getMaxInactiveInterval() + "<BR>");


		 String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;

	out.print("SS_M_NO : " + SS_M_NO + "<br>");
	out.print("SS_M_ID : " + SS_M_ID + "<br>");
	out.print("SS_M_NAME : " + SS_M_NAME + "<br>");
	out.print("SS_MG_NO : " + SS_MG_NO + "<br>");
	out.print("SS_TITLE : " + SS_TITLE + "<br>");
	out.print("SS_URL : " + SS_URL + "<br>");


	if ((SS_M_ID.equals("")) ) {

		out.print("세션 없음");
    }else {
	}
	*/

      out.write("\n");
      out.write("</br>\n");
      out.write("</br>\n");
      out.write("</br>\n");
      out.write("\n");
      out.write("<h2 align=\"center\">\n");
      out.write("세션에서 사용자 정보를 찾을수 없습니다.\n");
      out.write("</h2>\n");
      out.write("\n");
      out.write("</br>\n");
      out.write("<center>\n");
      out.write("시스템에 다시 접속하시기 바랍니다.\n");
      out.write("</center>\n");
      out.write("</body>\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("\ttop.document.location = '../../index.jsp';\r\n");
      out.write("</SCRIPT>");
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
