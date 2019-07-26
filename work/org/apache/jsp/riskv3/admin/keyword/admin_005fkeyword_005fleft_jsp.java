package org.apache.jsp.riskv3.admin.keyword;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.search.GetKGMenu;
import risk.util.DateUtil;
import risk.admin.keyword.KeywordMng;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class admin_005fkeyword_005fleft_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/keyword/../../inc/sessioncheck.jsp");
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
      out.write('\r');
      out.write('\n');


    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    //pr.printParams();
	KeywordMng kg = new KeywordMng();
	
	int xp = pr.getInt("xp",0);
	int yp = pr.getInt("yp",0);
	int zp = pr.getInt("zp",0);
	
	String sCurrentDate = du.getCurrentDate("yyyy-MM-dd");
	//System.out.println("sCurrentDate : " + sCurrentDate );
	
	String sDateFrom = pr.getString("sDateFrom",sCurrentDate );
	String sDateTo   = pr.getString("sDateFrom",sCurrentDate );
	
	kg.setImagesURL("../../../images/admin/keyword/");
	kg.setSelected(xp,yp,zp);	
	kg.setBaseTarget("parent.keyword_right");
	kg.setBaseURL("admin_keyword_right.jsp?mod=");
	
	String kgHtml   = kg.GetHtml( sDateFrom , sDateTo, "","false" );
	String kgScript = kg.GetScript();
	String kgStyle  = kg.GetStyle();
	

      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<style>\r\n");
      out.write("<!--\r\n");
      out.write("body {\r\n");
      out.write("\tscrollbar-face-color: #FFFFFF; \r\n");
      out.write("\tscrollbar-shadow-color:#B3B3B3; \r\n");
      out.write("\tscrollbar-highlight-color:#B3B3B3; \r\n");
      out.write("\tscrollbar-3dlight-color: #FFFFFF; \r\n");
      out.write("\tscrollbar-darkshadow-color: #EEEEEE; \r\n");
      out.write("\tscrollbar-track-color: #F6F6F6; \r\n");
      out.write("\tscrollbar-arrow-color: #8B9EA6;\r\n");
      out.write("}\r\n");
      out.write("-->\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-1.11.0.min.js\"></script>\r\n");

	out.println(kgStyle);	
	out.println(kgScript);	

      out.write("\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction clk_total()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tparent.keyword_right.location.href = 'admin_keyword_right.jsp';\r\n");
      out.write("\t}\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("<body bgcolor=\"#FFFFFF\" leftmargin=\"15\" topmargin=\"5\">\r\n");
      out.write("<form name=\"leftmenu\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"xp\">\r\n");
      out.write("<input type=\"hidden\" name=\"yp\">\r\n");
      out.write("<input type=\"hidden\" name=\"zp\">\r\n");
      out.write("<input type=\"hidden\" name=\"onoffmode\">\r\n");
      out.write("</form>\r\n");
      out.write("<span id=\"total\" class=\"kgmenu\" onclick=\"clk_total();\" onmouseover=\"kg_over(this);\"onmouseout=\"kg_out(this);\"><b>전체 키워드그룹</b></span></br>\r\n");

	out.println(kgHtml);

      out.write("\r\n");
      out.write("</body>");
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
