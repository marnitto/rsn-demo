package org.apache.jsp.riskv3.admin.tier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.*;
import risk.admin.tier.*;
import risk.util.ConfigUtil;

public final class pop_005ftier_005fsearch_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/tier/../../inc/sessioncheck.jsp");
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

      out.write("\r\n");
      out.write("  \r\n");
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
      out.write('\r');
      out.write('\n');

	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	ArrayList arr = new ArrayList();	
	TierSiteMgr tsMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String seach_ts_name = pr.getString("seach_ts_name");
	String ms_year = pr.getString("ms_year");
	arr = tsMgr.getSiteList(0,0,seach_ts_name);	
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>메체등록</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/tier/basic.css\" type=\"text/css\">\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction search()\r\n");
      out.write("\t{\r\n");
      out.write("\t\t$('#tierForm').attr('target','');\r\n");
      out.write("\t\t$('#tierForm').attr('action','pop_tier_search.jsp');\r\n");
      out.write("\t\t$('#tierForm').submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction popSiteEditForm(ts_seq,ts_name)\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$('#ts_seq').val(ts_seq);\r\n");
      out.write("\t\t$('#ts_name').val(ts_name);\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tpopup.openByPost('tierForm','pop_tier_edit.jsp',320,280,false,true,false,'pop_tier_edit1');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body  leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<iframe name=\"processFrm\" height=\"0\" border=\"0\"></iframe>\r\n");
      out.write("<form id=\"tierForm\" name=\"tierForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" id=\"mode\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" id=\"ts_seq\" name=\"ts_seq\">\r\n");
      out.write("<input type=\"hidden\" id=\"ts_name\" name=\"ts_name\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table width=\"300\" height=\"40\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"38\" background=\"../../../images/admin/tier/pop_top.gif\"><table width=\"300\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"240\" class=\"textwbig\" style=\"padding-left: 35px\"><strong>매체 검색</strong></td>\r\n");
      out.write("        <td width=\"60\" align=\"right\" class=\"pop_titleBIG\"><img src=\"../../../images/admin/tier/pop_close_01.gif\" width=\"60\" height=\"24\" style=\"cursor:pointer;\" onclick=\"window.close();\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table width=\"300\" cellSpacing=5 cellPadding=0  bgColor=#e8e8e8 border=0>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 10px 10px 10px 10px;\">\r\n");
      out.write("      <table width=\"260\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">        \r\n");
      out.write("        <tr>\r\n");
      out.write("          <td><table width=\"260\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"cdd4d6\">\r\n");
      out.write("              \r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"32\" colspan=\"3\" align=\"center\" bgcolor=\"f6f6f6\"><input id=\"seach_ts_name\" name=\"seach_ts_name\" type=\"text\" class=\"input_text\" style=\"width:200\"  onkeydown=\"if(event.keyCode==13)search();\"/>\r\n");
      out.write("                    <img src=\"../../../images/admin/tier/search_btn.gif\" width=\"51\" height=\"23\" align=\"absmiddle\" style=\"cursor:pointer;\" onclick=\"search();\">\r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              \r\n");

	if(arr.size()>0)
	{

			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);

      out.write(" \t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t              <td width=\"205\" height=\"30\" align=\"left\" bgcolor=\"#FFFFFF\" title=\"");
      out.print(tsBean.getTs_name());
      out.write("\"><nobr style =\"text-overflow:ellipsis; overflow:hidden; width:100px\">");
      out.print(tsBean.getTs_name());
      out.write("</nobr></td>\t             \r\n");
      out.write("\t              <td width=\"55\" align=\"center\" bgcolor=\"#FFFFFF\"><img src=\"../../../images/admin/tier/write_btn.gif\" width=\"55\" height=\"23\" style=\"cursor:pointer\" onclick=\"popSiteEditForm('");
      out.print(tsBean.getTs_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(tsBean.getTs_name());
      out.write("');\"></td>\r\n");
      out.write("\t            </tr>                    \r\n");

			}

	}

      out.write("\r\n");
      out.write("            \r\n");
      out.write("          </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
