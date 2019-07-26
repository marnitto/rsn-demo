package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.search.GetKGMenu;
import risk.util.ConfigUtil;

public final class search_005fmain_005fleft_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/search/../inc/sessioncheck.jsp");
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


    ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	pr.printParams();
    String sInit = (String)session.getAttribute("INIT");

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode() == null ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);


      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>");
      out.print(SS_TITLE);
      out.write("</title>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/left_design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("\t<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("  <script type=\"text/javascript\" src=\"../asset/js/selectivizr-min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"wrap\">\r\n");
      out.write("\t<div id=\"snb\">\r\n");
      out.write("\t\t<h2><img src=\"../../images/left/left_title01.gif\" alt=\"정보검색\"></h2>\r\n");
      out.write("\t\t<div class=\"snb\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:chageSearchMode('SOLR',document.all.solrkey);\"><span class=\"icon\">-</span>전체 검색</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:chageSearchMode('TOP',document.all.solrkey);\"><span class=\"icon\">-</span>포탈 TOP</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:chageSearchMode('ALLKEY',document.all.solrkey);\" class=\"active\"><span class=\"icon\">-</span>전체 키워드 검색</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:chageSearchMode('EX_ALLKEY',document.all.solrkey);\"><span class=\"icon\">-</span>제외 키워드 검색</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<iframe name=\"ifr_menu\"  id=\"ifr_menu\" src=\"inc_keyword_menu.jsp\" frameborder=\"0\" border=\"0\" style=\"width:295px;min-height:300px\"></iframe>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t(function($) {\r\n");
      out.write("\t\t\t//iframe 사이즈 \r\n");
      out.write("\t\t\thndl_resize();\r\n");
      out.write("\t\t\t$( window ).resize( hndl_resize );\r\n");
      out.write("\t\t\tfunction hndl_resize($e) {\r\n");
      out.write("\t\t\t\t$( \"#snb iframe\" ).each(function(){\r\n");
      out.write("\t\t\t\t\tif( $( this ).parent().find( \"a\" ).hasClass( \"active\" ) ) {\r\n");
      out.write("\t\t\t\t\t\tvar hgt = $( window ).height() - ( $( \"#snb h2\" ).outerHeight() + ( $( \"#snb li a\" ).outerHeight() * $( \"#snb li a\" ).length ) ) - 100;\r\n");
      out.write("\t\t\t\t\t\t$( this ).show();\r\n");
      out.write("\t\t\t\t\t\t$( this ).height( hgt );\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t$( this ).hide();\r\n");
      out.write("\t\t\t\t\t\t$( this ).height(0);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t})\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t// 레프트 메뉴 클릭 - 활성화\r\n");
      out.write("\t\t\t$( \"#snb li a\" ).click(function($e) {\r\n");
      out.write("\t\t\t\t$( \"#snb li a\" ).removeClass( \"active\" );\r\n");
      out.write("\t\t\t\t$( this ).addClass( \"active\" );\r\n");
      out.write("\t\t\t\thndl_resize();\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t})(jQuery);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//검색 모드 변경\r\n");
      out.write("\t    function chageSearchMode( value, obj ) {\r\n");
      out.write("\t        \r\n");
      out.write("\t        \tselectedObj = obj;\r\n");
      out.write("\t        \t\r\n");
      out.write("\t        if ( value == \"ALLKEY\" ) {\r\n");
      out.write("\t        \tadd = '&xp=0&yp=0&zp=0';\r\n");
      out.write("\t        \t\r\n");
      out.write("\t            top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tifr_menu.location = \"inc_keyword_menu.jsp?menu=\" + value + add;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t        }else if ( value == \"EX_ALLKEY\" ) {\r\n");
      out.write("\t        \tadd = '&xp=0&yp=0&zp=0';\r\n");
      out.write("\t        \t\r\n");
      out.write("\t            top.bottomFrame.contentsFrame.document.location ='search_main_exception.jsp?searchmode=' + value + add ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tifr_menu.location = \"inc_keyword_menu.jsp?menu=\" + value + add;\r\n");
      out.write("\t     \r\n");
      out.write("\t\t\t}else if( value == \"SOLR\"){\r\n");
      out.write("\t\t        top.bottomFrame.contentsFrame.document.location ='solr/search_main_contents_solr.jsp';\r\n");
      out.write("\t\t\t}else if( value == \"TOP\"){\r\n");
      out.write("\t\t\t\ttop.bottomFrame.contentsFrame.document.location ='search_main_top.jsp';\r\n");
      out.write("\t\t\t} else{\r\n");
      out.write("\t\t\t\tadd = '&xp=0&yp=0&zp=0';\r\n");
      out.write("\t\t        top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t    }\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
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
