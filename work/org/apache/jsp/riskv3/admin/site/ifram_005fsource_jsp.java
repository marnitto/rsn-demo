package org.apache.jsp.riskv3.admin.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import java.lang.*;
import risk.admin.site.SiteBean;
import risk.admin.site.SiteMng;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class ifram_005fsource_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/site/../../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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

	List SGlist = null;
	int code1=0;
	int language=0;
	String sch_key = "";
	
	// kor / new
	String order  = "";
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	

		
try{	

	if( request.getParameter("code1") != null && !request.getParameter("code1").equals("") ) code1 = Integer.parseInt(request.getParameter("code1"));
	if( request.getParameter("language") != null && !request.getParameter("language").equals("") ) language = Integer.parseInt(request.getParameter("language"));
	if( request.getParameter("sch_key") != null && !request.getParameter("sch_key").equals("") ) sch_key = request.getParameter("sch_key");
	if( request.getParameter("order") != null && !request.getParameter("order").equals("") ) {order = request.getParameter("order");} else{order = "kor";}
	
	SiteMng sitemng = new SiteMng();


	SGlist = sitemng.get46List(sch_key, code1, language, order);
	
	/*
	if( sch_key != null && code1 > 0 ) {
		SGlist = sitemng.get46List(sch_key, code1);
	}else if( sch_key != null ) {
		SGlist = sitemng.get46List(sch_key);
	}else {
		if( code1 > 0 ) {
			SGlist = sitemng.get46List(code1);
		}else {
			SGlist = sitemng.get46List();
		}
	}
	*/


      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>X-MAS Solution</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(SS_URL);
      out.write("css/basic.css\" type=\"text/css\">\r\n");
      out.write("<style>\r\n");
      out.write("\t<!--\r\n");
      out.write("\ttd { font-size:12px; color:#333333; font-family:\"dotum\"; ; line-height: 18px}\r\n");
      out.write("    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }\r\n");
      out.write("\t.t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("    .tCopy { font-family: \"Tahoma\"; font-size: 12px; color: #000000; font-weight: bold}\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 14px;\r\n");
      out.write("\tmargin-top: 10px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("}\r\n");
      out.write("\t-->\r\n");
      out.write("\t</style>\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction search_SourceSite_frm()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( parent.source_form.sch_key.value ) {\r\n");
      out.write("\t\t\tsg_change.sch_key.value = parent.source_form.sch_key.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tsg_change.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction search_order_frm(order)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tsg_change.order.value = order;\r\n");
      out.write("\t\tsg_change.submit();\r\n");
      out.write("\t}\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<img src=\"../../../images/admin/site/admin_ico03.gif\"  hspace=\"3\">언어선택\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td width=\"30\"></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<img src=\"../../../images/admin/site/admin_ico03.gif\"  hspace=\"3\">사이트 그룹 선택\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr height=\"3\"><td></td></tr>\r\n");
      out.write("\t\t\t\t<form name=\"sg_change\" action=\"ifram_source.jsp\" method=\"GET\">\r\n");
      out.write(" \t\t\t\t\t<input name=\"sch_key\" type=\"hidden\">\r\n");
      out.write(" \t\t\t\t\t<input name=\"order\" type=\"hidden\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td width=\"140\">\r\n");
      out.write("\t\t\t\t\t\t<select name=\"language\" class=\"t\" style=\"width:140px;\" onchange=\"search_SourceSite_frm();\">\r\n");
      out.write("\t\t\t\t\t       <option value=\"0\" ");
if( language == 0) out.println("selected");
      out.write(">전체\r\n");
      out.write("\t\t\t\t\t       <option value=\"1\" ");
if( language == 1) out.println("selected");
      out.write(">한국어\r\n");
      out.write("\t\t\t\t\t       <option value=\"2\" ");
if( language == 2) out.println("selected");
      out.write(">영어\r\n");
      out.write("    \t\t\t\t\t </select>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td width=\"30\"></td>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<td width=\"140\">\r\n");
      out.write("\t\t\t\t\t\t<select name=\"code1\" class=\"t\" style=\"width:140px;\" onchange=\"search_SourceSite_frm();\">\r\n");
      out.write("\t\t\t\t\t       <option value=\"0\" ");
if( code1 == 0) out.println("selected");
      out.write(">전체\r\n");
      out.write("\t\t\t\t\t       <option value=\"1\" ");
if( code1 == 1) out.println("selected");
      out.write(">언론사\r\n");
      out.write("\t\t\t\t\t       <option value=\"2\" ");
if( code1 == 2) out.println("selected");
      out.write(">정부공공기관\r\n");
      out.write("\t\t\t\t\t       <option value=\"3\" ");
if( code1 == 3) out.println("selected");
      out.write(">금융\r\n");
      out.write("\t\t\t\t\t       <option value=\"4\" ");
if( code1 == 4) out.println("selected");
      out.write(">NGO\r\n");
      out.write("\t\t\t\t\t       <option value=\"5\" ");
if( code1 == 5) out.println("selected");
      out.write(">기업/단체\r\n");
      out.write("     \t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr height=\"3\"><td></td></tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("        <td align=\"right\" ><img src=\"../../../images/admin/site/btn_order_kor.gif\" height=\"21\" onclick=\"search_order_frm('kor');\" style=\"cursor:hand;\"><img src=\"../../../images/admin/site/btn_order_update.gif\" height=\"21\" hspace=\"5\" onclick=\"search_order_frm('new');\" style=\"cursor:hand;\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"1\" height=\"6\"><!-- <img src=\"../../../images/admin/site/brank.gif\" > --></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("\r\n");
      out.write("<!--   <tr>-->\r\n");
      out.write("<!--    <td><img src=\"../../../images/admin/site/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">사이트 그룹 선택</td>-->\r\n");
      out.write("<!-- </tr>-->\r\n");
      out.write("<!-- <form name=\"sg_change\" action=\"ifram_source.jsp\" method=\"GET\">-->\r\n");
      out.write("<!-- <input name=\"sch_key\" type=\"hidden\">-->\r\n");
      out.write("<!--   <tr>-->\r\n");
      out.write("<!--     <td height=\"25\">-->\r\n");
      out.write("<!--\t <select name=\"code1\" class=\"t\" style=\"width:310px;\" onchange=\"search_SourceSite_frm();\">-->\r\n");
      out.write("<!--       <option value=\"0\" ");
if( code1 == 0) out.println("selected");
      out.write(">전체-->\r\n");
      out.write("<!--       <option value=\"1\" ");
if( code1 == 1) out.println("selected");
      out.write(">언론사-->\r\n");
      out.write("<!--       <option value=\"2\" ");
if( code1 == 2) out.println("selected");
      out.write(">정부공공기관-->\r\n");
      out.write("<!--       <option value=\"3\" ");
if( code1 == 3) out.println("selected");
      out.write(">금융-->\r\n");
      out.write("<!--       <option value=\"4\" ");
if( code1 == 4) out.println("selected");
      out.write(">NGO-->\r\n");
      out.write("<!--       <option value=\"5\" ");
if( code1 == 5) out.println("selected");
      out.write(">기업/단체-->\r\n");
      out.write("<!--     </select></td>-->\r\n");
      out.write("<!--   </tr>-->\r\n");
      out.write("<!--   </form>-->\r\n");
      out.write("   <tr>\r\n");
      out.write("     <td width=\"1\" height=\"5\"><!-- <img src=\"../../../images/admin/site/brank.gif\" > --></td>\r\n");
      out.write("   </tr>\r\n");
      out.write("   <tr>\r\n");
      out.write("     <td height=\"18\"><img src=\"../../../images/admin/site/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">사이트 리스트</td>\r\n");
      out.write("   </tr>\r\n");
      out.write("   <form name=\"sg_mng\" action=\"ifram_source_prc.jsp\" method=\"post\">\r\n");
      out.write("   <input type=\"hidden\" name=\"mode\" value=\"ins\">\r\n");
      out.write("   <input type=\"hidden\" name=\"sgseq\">\r\n");
      out.write("   <input type=\"hidden\" name=\"code1\" >\r\n");
      out.write("\r\n");
      out.write("   <tr>\r\n");
      out.write("     <td><select name=\"gsn\" multiple style=\"width:310px;height:200px;\" class=\"t\">\r\n");

	for(int i=0; i < SGlist.size();i++) {
		SiteBean SGinfo = (SiteBean)SGlist.get(i);

      out.write("\r\n");
      out.write("<option value=\"");
      out.print( SGinfo.get_gsn());
      out.write('"');
      out.write('>');
      out.print( SGinfo.get_name());
      out.write("</option>\r\n");

	}
	}catch(Exception e){System.out.println("error ifram_source line: 103"+e);}

      out.write("\r\n");
      out.write("</select></td>\r\n");
      out.write("   </tr>\r\n");
      out.write("   </form>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
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
