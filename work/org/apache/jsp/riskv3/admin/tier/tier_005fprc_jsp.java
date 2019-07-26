package org.apache.jsp.riskv3.admin.tier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.*;
import risk.admin.tier.*;

public final class tier_005fprc_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("  \r\n");

	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	TierSiteMgr tsMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String mode = pr.getString("mode");
	StringBuffer script = null;

	
	tsBean.setTs_seq(pr.getString("ts_seq"));
	tsBean.setTs_name(pr.getString("ts_name"));
	tsBean.setTs_type(pr.getString("ts_type"));
	//tsBean.setTs_rank(pr.getString("ts_rank"));
		
	
	if(mode.equals("insert")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('저장하였습니다');");			
		script.append("</script>");
		
		tsMgr.insertTierSite(tsBean);
		
		out.print(script.toString());	
	}else if(mode.equals("update")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('수정하였습니다');");
		script.append("</script>");	
		
		tsMgr.updateTierSite(tsBean);
		
		out.print(script.toString());	
	}else if(mode.equals("delete")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('삭제하였습니다');");
		script.append("</script>");	
		
		tsMgr.deleteTierSite(tsBean);
	}

      out.write('\r');
      out.write('\n');
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
