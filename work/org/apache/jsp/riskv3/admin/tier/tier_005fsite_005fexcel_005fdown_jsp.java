package org.apache.jsp.riskv3.admin.tier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.admin.tier.*;
import risk.util.DateUtil;
import risk.util.ParseRequest;
import java.util.ArrayList;
import risk.util.ConfigUtil;

public final class tier_005fsite_005fexcel_005fdown_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write('\r');
      out.write('\n');


    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    String ts_types = pr.getString("ts_types");
    System.out.println("@@@@@@@@@@@@@"+ts_types);
    TierSiteMgr tr = new TierSiteMgr();
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=site_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    TierSiteBean tsBean = new TierSiteBean();
    ArrayList arr = new ArrayList();
    arr = tr.getTierSiteList(0,0,"",ts_types);
    if(arr.size()>0)
	{


      out.write("\r\n");
      out.write("<table style=\"width:700px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:200px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 3;
			
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>순번</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span> Tiering</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span> 사이트명</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);
		
      out.write("\r\n");
      out.write("\t\t\t<tr>\t\t\r\n");
      out.write("\t\t\t  <td align=\"center\"bgcolor=\"#FFFFFF\">");
      out.print(i+1);
      out.write("</td>\t  \r\n");
      out.write("              <td align=\"center\"bgcolor=\"#FFFFFF\" class=\"table_top\">");
      out.print(tsBean.getTs_type() );
      out.write("</td>\r\n");
      out.write("              <td align=\"center\" bgcolor=\"#FFFFFF\" class=\"table_top\">");
      out.print(tsBean.getTs_name());
      out.write("</td>\r\n");
      out.write("            </tr>                    \r\n");

			}

      out.write("\t\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");
} 
      out.write("\r\n");
      out.write("\t\t");
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
