package org.apache.jsp.riskv3.admin.tier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.*;
import risk.admin.tier.*;

public final class tier_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	ArrayList arr = new ArrayList();
	TierSiteMgr msMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String ts_types = pr.getString("ts_type","");	
	arr = msMgr.getTierSiteList(0,0,"",ts_types);
	
	if(arr.size()>0)
	{


      out.write("\r\n");
      out.write("\t\t<div style=\"height:290px;overflow: auto;\">\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"cdd4d6\">\r\n");
      out.write("\t\t\t<col width=\"32\"><col width=\"57\"><col width=\"*\"><!-- <col width=\"236\"> --><col width=\"170\">\r\n");

			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);

      out.write(" \t\t\r\n");
      out.write("\t\t\t<tr>\t\t\r\n");
      out.write("\t\t\t  <td align=\"center\"bgcolor=\"#FFFFFF\">");
      out.print(i+1);
      out.write("</td>\t  \r\n");
      out.write("              <td align=\"center\"bgcolor=\"#FFFFFF\" class=\"table_top\"><select id=\"ts_type");
      out.print(i);
      out.write("\" name=\"ts_type");
      out.print(i);
      out.write("\"><option value=\"1\" ");
if(tsBean.getTs_type().equals("1"))out.print("selected");
      out.write(">1</option><option value=\"2\" ");
if(tsBean.getTs_type().equals("2"))out.print("selected");
      out.write(">2</option><option value=\"3\" ");
if(tsBean.getTs_type().equals("3"))out.print("selected");
      out.write(">3</option><option value=\"4\" ");
if(tsBean.getTs_type().equals("4"))out.print("selected");
      out.write(">4</option><option value=\"4\" ");
if(tsBean.getTs_type().equals("5"))out.print("selected");
      out.write(">5</option></select></td>\r\n");
      out.write("              <td align=\"center\" bgcolor=\"#FFFFFF\" class=\"table_top\">");
      out.print(tsBean.getTs_name());
      out.write("</td>              \r\n");
      out.write("              ");
      out.write("             \r\n");
      out.write("              <td align=\"center\" bgcolor=\"#FFFFFF\"><img src=\"../../../images/admin/tier/modify_btn.gif\" width=\"39\" height=\"23\" align=\"absmiddle\" style=\"cursor:pointer;\" onclick=\"updateTierSite('");
      out.print(tsBean.getTs_seq());
      out.write("','ts_type");
      out.print(i);
      out.write("','ts_rank");
      out.print(i);
      out.write("');\"><img src=\"../../../images/admin/tier/delete_btn.gif\" width=\"39\" height=\"23\" align=\"absmiddle\" style=\"cursor:pointer;\" onclick=\"deleteTierSite('");
      out.print(tsBean.getTs_seq());
      out.write("');\"></td>\r\n");
      out.write("            </tr>                    \r\n");

			}

      out.write("\t\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");

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
