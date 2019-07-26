package org.apache.jsp.riskv3.admin.classification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.admin.classification.classificationMgr;
import risk.util.DateUtil;
import risk.util.ParseRequest;

public final class frm_005fclassification_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/classification/../../inc/sessioncheck.jsp");
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
      out.write("\r\n");


    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);

	
	String sCurrentDate = du.getCurrentDate("yyyyMMdd");
	//System.out.println("sCurrentDate : " + sCurrentDate );
	
	String sDateFrom = pr.getString("sDateFrom",sCurrentDate );
	String sDateTo   = pr.getString("sDateFrom",sCurrentDate );
	
	//kg.setSelected(xp,yp,zp);
	
	//kg.setBaseTarget("parent.keyword_right");
	//kg.setBaseURL("admin_keyword_right.jsp?mod=");
	
	cm.setBaseTarget("parent.frm_detail");
	cm.setBaseURL("frm_classification_detail.jsp?mod=");
	cm.setSelected( itype, icode );
	
	String cmHtml   = cm.GetHtml( itype,icode );
	//String kgScript = kg.GetScript();
	//String kgStyle  = kg.GetStyle();
	
	//System.out.println("itype = "+itype);
	//System.out.println("icode = "+icode);
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write("\tscrollbar-face-color: #FFFFFF; \r\n");
      out.write("\tscrollbar-shadow-color:#B3B3B3; \r\n");
      out.write("\tscrollbar-highlight-color:#B3B3B3; \r\n");
      out.write("\tscrollbar-3dlight-color: #FFFFFF; \r\n");
      out.write("\tscrollbar-darkshadow-color: #EEEEEE; \r\n");
      out.write("\tscrollbar-track-color: #F6F6F6; \r\n");
      out.write("\tscrollbar-arrow-color: #8B9EA6;\r\n");
      out.write("}\r\n");
      out.write(".kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #FFFFFF; }\r\n");
      out.write(".kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: \"tahoma\"; padding:0 0 0 3 }\r\n");
      out.write(".kgmenu_selected { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; border:1px solid #999999; background-color:#F3F3F3; cursor:hand;}\r\n");
      out.write(".kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }\r\n");
      out.write(".kgmenu_img { cursor:hand; }\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\t\t\r\n");
      out.write("\r\n");
      out.write("function saveFlag(){\r\n");
      out.write("\talert(1);\r\n");
      out.write("\t$(\"[name=iflag]\").each(function(){\r\n");
      out.write("\t\t$(this).val();\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\tfunction toggleme(object, subo) {\r\n");
      out.write("\t\tvar obj = document.getElementById(object);\t\r\n");
      out.write("\t\tvar subObj = eval(\"document.all.\"+subo);\t\t\r\n");
      out.write("\t\tvar i = 0;\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\tvar subcnt = subObj.length;\t\t\t\t\t\t\r\n");
      out.write("\t\tif (obj.src.indexOf('ico03')>0) {\t\t\t\t\r\n");
      out.write("\t\t\tif (subObj)\t{\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\tobj.src = '../../../images/search/left_ico04.gif';\t\t\r\n");
      out.write("\t\t\t\tsubObj.style.display='';\t\t\t\t\r\n");
      out.write("\t\t\t}\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t} else {\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\tif (subObj)\t{\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\tobj.src = '../../../images/search/left_ico03.gif';\t\t\r\n");
      out.write("\t\t\t\tsubObj.style.display='none';\t\t\t\r\n");
      out.write("\t\t\t}\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t}\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t}\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\tfunction kg_over(obj) {\t\t\t\t\t\t\t\r\n");
      out.write("\t\ttmpspan.className=obj.className;\t\t\t\t\r\n");
      out.write("\t\tobj.className='kgmenu_over';\t\t\t\t\t\r\n");
      out.write("\t}\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\tfunction kg_out(obj){\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\tobj.className=tmpspan.className;\t\t\t\t\r\n");
      out.write("\t}\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\tfunction kg_click(obj){\t\t\t\t\t\t\t\r\n");
      out.write("\t\tvar prvObj = eval('document.all.'+document.all.kgmenu_id.value);\t\r\n");
      out.write("\t\tif (prvObj)\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\tprvObj.className = 'kgmenu';\t\t\t\t\r\n");
      out.write("\t\t}\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\tdocument.all.kgmenu_id.value = obj.id;\t\t\t\r\n");
      out.write("\t\tobj.className='kgmenu_selected';\t\t\t\t\r\n");
      out.write("\t\ttmpspan.className=obj.className;\t\t\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction clk_total()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tparent.keyword_right.location.href = 'admin_keyword_right.jsp';\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body bgcolor=\"#FFFFFF\" leftmargin=\"15\" topmargin=\"5\">\r\n");
      out.print(cmHtml);
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
