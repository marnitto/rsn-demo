package org.apache.jsp.dashboard.inc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class inc_005fmessage_005fbox_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Excel Download Form & IFrame -->\r\n");
      out.write("<div class=\"excel_prc_container\">\r\n");
      out.write("\t<form name=\"proceeExcel\" id=\"proceeExcel\" method=\"post\" action=\"excel_down_prc.jsp\" target=\"_blank\">\r\n");
      out.write("\t\t<input type=\"hidden\" id=\"dataToDisplay\" name=\"dataToDisplay\"/>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t<iFrame id=\"processFrm\" name =\"processFrm\" width=\"0\" height=\"0\" title=\"excel data frame\"></iFrame>\r\n");
      out.write("</div>\r\n");
      out.write("<!-- // Excel Download Form & IFrame -->\r\n");
      out.write("\r\n");
      out.write("<!-- Message Box -->\r\n");
      out.write("<section id=\"msg_box\">\r\n");
      out.write("\t<div class=\"bg\"></div>\r\n");
      out.write("\t<div class=\"box ui_shadow_00\"></div>\r\n");
      out.write("</section>\r\n");
      out.write("<!-- // Message Box -->");
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
