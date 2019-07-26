package org.apache.jsp.riskv3.search.solr;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.search.solr.SolrSearch;
import risk.util.ConfigUtil;
import java.net.URLDecoder;

public final class inc_005flist_005fchart_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/search/solr/../../inc/sessioncheck.jsp");
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

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	//String reSearchKey = new String(request.getParameter("reSearchKey").getBytes("iso-8859-1"), "UTF-8");
	String reSearchKey = pr.getString("reSearchKey", "");
	String xml = "";	
	String startDate  = pr.getString("startDate", "");
	String endDate = pr.getString("endDate", "");
	String sgroup = pr.getString("sgroup", "");
	String searchDate = pr.getString("searchDate");
	String type=pr.getString("type");
	SolrSearch solr = new SolrSearch();
	
	
	System.out.println("#########################################");
	System.out.println(reSearchKey);
	System.out.println("#########################################");
	
	
	if(sgroup.length() > 0){
		sgroup += " ";
	}
	
	//차트 XML가져오기
	xml = solr.getChartXml(reSearchKey, startDate, endDate, searchDate, sgroup, SS_M_ID);
	
	System.out.println("#########################################");
	System.out.println("##차트XML 가져오기 완료##");
	System.out.println("#########################################");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=EUC-KR\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css/basic.css\" type=\"text/css\">\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".selectBox1 {behavior:url('./selectbox.htc'); }\r\n");
      out.write(".line_change01 {  border:1px solid #FFFFFF; background-color:#FFFFFF;}\r\n");
      out.write(".line_change02{  border:1px solid #E4E4E4; background-color:#F7F7F7;}\r\n");
      out.write("</style>\r\n");
      out.write("<script language=\"JavaScript\" src=\"chart/js/FusionCharts.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\r\n");
      out.write("function responseXml()\r\n");
      out.write("{\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tvar ChartXml = f.xml.value;\r\n");
      out.write("\t\r\n");
      out.write("\tif(ChartXml != \"\"){\r\n");
      out.write("\t\tvar chart = new FusionCharts(\"chart/charts/Column2D.swf\", \"ChartId\", \"800\", \"230\");\r\n");
      out.write("\t\tchart.setDataXML(ChartXml);\r\n");
      out.write("\t\tchart.render(\"chartdiv\");\r\n");
      out.write("\t\t \r\n");
      out.write("\t}else{\t\r\n");
      out.write("\t\tvar chart2 = document.getElementById(\"chartdiv\");//첫로딩이미지\r\n");
      out.write("\t\tchart2.innerHTML = \"<img id=\\\"loadingBar\\\" src=\\\"images/search/no_data.gif\\\" width=\\\"115\\\" height=\\\"92\\\">\";\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function chartView(check){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tvar Obj = document.getElementById(\"chartview\");\r\n");
      out.write("\t \r\n");
      out.write("\tif(check){\r\n");
      out.write("\t\tObj.style.position = '';\r\n");
      out.write("\t\tObj.style.display = '';\r\n");
      out.write("\t\tf.grfCheck.value = 1;\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tObj.style.position = 'absolute';\r\n");
      out.write("\t\tObj.style.display = 'none';\r\n");
      out.write("\t\tf.grfCheck.value = 0;\t\r\n");
      out.write("\t}\r\n");
      out.write("}   \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" >\r\n");
      out.write("\r\n");
      out.write("<!-- 기사 분류 메뉴  -->\r\n");
      out.write("\r\n");
      out.write("<form name=\"fSolr\" action =\"search_main_contents_solr.jsp\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value =\"1\">\r\n");
      out.write("<input type=\"hidden\" name=\"xml\" value=\"");
      out.print(xml);
      out.write("\">\r\n");
      out.write("<table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td style=\"padding:0px 0px 0px 0px\" align=\"left\" valign=\"top\">\r\n");
      out.write("\t\t<table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("        \t<tr>\r\n");
      out.write("        \t\t<td width=\"800\" valign=\"top\">\r\n");
      out.write("        \t\t<div id=\"chartBox\" >\r\n");
      out.write("\t\t\t\t<table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"chartview\" >\r\n");
      out.write("\t\t\t\t\t\t\t<table width=\"800\" height=\"205\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t               \t<tr>\r\n");
      out.write("\t\t\t\t               \t\t<td></td>\r\n");
      out.write("\t\t\t\t              \t</tr>\r\n");
      out.write("\t\t\t\t               \t<tr>\r\n");
      out.write("\t\t\t\t               \t\t<td align=\"center\" style=\"padding:0px 0px 0px 0px\">\r\n");
      out.write("\t\t\t\t               \t\t\t<div id=\"chartdiv\" align=\"center\">\r\n");
      out.write("\t\t\t\t               \t\t\t\r\n");
      out.write("\t\t\t\t               \t\t\t</div>\r\n");
      out.write("\t\t\t\t               \t\t</td>\r\n");
      out.write("\t\t\t\t              \t\t</tr>\r\n");
      out.write("\t\t\t\t               \t<tr>\r\n");
      out.write("\t\t\t\t               \t\t<td></td>\r\n");
      out.write("\t\t\t\t              \t</tr>\r\n");
      out.write("\t\t\t\t               </table>\r\n");
      out.write("\t\t\t\t              </div>\r\n");
      out.write("\t\t\t\t        </td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\t\r\n");
      out.write("        \t\t</td>\r\n");
      out.write("\r\n");
      out.write("       \t\t</tr>\r\n");
      out.write("       \t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script>\r\n");
      out.write("responseXml();\r\n");
      out.write("</script>");
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
