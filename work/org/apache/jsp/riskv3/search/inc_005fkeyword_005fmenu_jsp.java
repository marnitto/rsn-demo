package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.search.GetKGMenu;
import risk.util.DateUtil;
import risk.util.ParseRequest;
import risk.util.StringUtil;
import risk.search.MetaMgr;
import risk.search.userEnvInfo;
import risk.search.userEnvMgr;
import risk.util.ConfigUtil;
import risk.util.ConfigUtil;

public final class inc_005fkeyword_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
    ConfigUtil cu = new ConfigUtil(); 
    String showCount=cu.getConfig("ShowKeywrodCount");
    
    if ( uei.getK_xp().equals("0")) uei.setK_xp("");   	        
    if ( uei.getK_yp().equals("0")) uei.setK_yp("");
    if ( uei.getK_yp().equals("0")) uei.setK_zp("");

    DateUtil        du = new DateUtil();
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr       sMgr = new MetaMgr();
    userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
    String sideMenu = pr.getString("menu", "ALLKEY");
    String stName = "";
    
    int xp = Integer.parseInt(su.nvl(uei.getK_xp(),"0"));  
	int yp = Integer.parseInt(su.nvl(uei.getK_yp(),"0"));  
	int zp = Integer.parseInt(su.nvl(uei.getK_zp(),"0")); 
    
    GetKGMenu kg = new GetKGMenu();
    kg.setSelected(xp,yp,zp);
    //kg.setBaseTarget("this.parent.content_ifr.document");
    kg.setBaseTarget("top.bottomFrame.contentsFrame.document");
    
    
    if(sideMenu.equals("ALLKEY")){
    	stName = "";
    	//kg.setBaseURL("search/search_main_contents.jsp?searchmode=ALLKEY");
    	kg.setBaseURL("search_main_contents.jsp?searchmode=ALLKEY");
    }else if(sideMenu.equals("EX_ALLKEY")){
    	stName = "EXCEPTION_";
    	//kg.setBaseURL("search/search_main_exception.jsp?searchmode=EX_ALLKEY");
    	kg.setBaseURL("search_main_exception.jsp?searchmode=EX_ALLKEY");
    }
    
    String IDXcnt = sMgr.getIdxDelCNT(SS_M_NO,stName);
    
	String sCurrentDate = du.getCurrentDate("yyyy-MM-dd");
	
    if ( uei.getDateFrom()==null) uei.setDateFrom(sCurrentDate);
    if ( uei.getDateTo()==null) uei.setDateTo(sCurrentDate);
    
		
	
	String kgHtml="";
	kgHtml   = kg.GetHtml( uei.getDateFrom(), uei.getDateTo(), uei.getMg_xp(), showCount, stName);
    
	String kgScript = kg.GetScript();
	String kgStyle  = kg.GetStyle();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<script>\r\n");
      out.write("\tfunction delete_all(){\r\n");
      out.write("\t\tif(confirm('휴지통의 정보를 모두 삭제하시겠습니까? \\n 삭제된 정보는 복구할 수 없습니다.')){\r\n");
      out.write("\t\t\ttop.bottomFrame.contentsFrame.idxProcess('delAll');\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../css/basic.css\" type=\"text/css\">\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write("\tscrollbar-face-color: #FFFFFF;\r\n");
      out.write("\tscrollbar-shadow-color:#B3B3B3;\r\n");
      out.write("\tscrollbar-highlight-color:#B3B3B3;\r\n");
      out.write("\tscrollbar-3dlight-color: #FFFFFF;\r\n");
      out.write("\tscrollbar-darkshadow-color: #EEEEEE;\r\n");
      out.write("\tscrollbar-track-color: #F6F6F6;\r\n");
      out.write("\tscrollbar-arrow-color: #8B9EA6;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-1.11.0.min.js\"></script>\r\n");

	out.println(kgStyle);
	out.println(kgScript);

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function getPageGo(url){\r\n");
      out.write("\t//var target = $( this.parent.document.getElementById(\"content_ifr\") );\r\n");
      out.write("\t//target.contents().get(0).location.href = url;\r\n");
      out.write("\tthis.parent.document.getElementById(\"content_ifr\").src = url;\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<body bgcolor=\"#FFFFFF\" leftmargin=\"5\" topmargin=\"4\" style=\"line-height:18px\" >\r\n");
      out.write("<form name=\"leftmenu\">\r\n");
      out.write("<table style=\"width:100%;table-layout: fixed;\">\r\n");
      out.write("\r\n");

    out.println(kgHtml);

      out.write("\r\n");
      out.write("</table>\r\n");

	boolean menuCheck = false;
	String[] mg_menu = env.getMg_menu().split(","); 
	for(int i=0; i<mg_menu.length; i++){
		if(mg_menu[i].equals("2") || mg_menu[i].equals("3")){
			menuCheck = true;
		}
	}

	if(menuCheck){

      out.write("\r\n");
      out.write("<div style=\"width:100%;padding-top:10px;line-height:18px\">\r\n");
      out.write("\t<span><img class=\"kgmenu_img\" src=\"../../images/search/del_ico.gif\" onClick=\"parent.chageSearchMode('DELIDX', parent.document.all.allkey);\" border=\"0\" align=absmiddle></span>\r\n");
      out.write("\t<span class=\"kgmenu\" id=\"imgID_0_0_0\" onclick=\"parent.chageSearchMode('DELIDX', this);kg_click(this);\" onmouseover=\"kg_over(this);\"onmouseout=\"kg_out(this);\"><b>휴지통</b>\r\n");
      out.write("\t<span class=\"kgmenu_cnt\">[");
      out.print(IDXcnt);
      out.write("]</span></span>\r\n");
      out.write("\t<span><img src=\"../../images/search/empty_ico.gif\" style=\"cursor:pointer;vertical-align:middle\" onclick=\"delete_all();\" /></span>\r\n");
      out.write("</div>\r\n");
} 
      out.write("\r\n");
      out.write(" <br>\r\n");
      out.write("<table>\r\n");
      out.write("   <tr>\r\n");
      out.write("     <td style=\"font-size:11px; color:#666666; padding-left:0px; padding-bottom:10px;\">* 키워드별 카운트는 오늘 날짜의 유사기사를 포함한 수치입니다.</td>\r\n");
      out.write("   </tr>      \r\n");
      out.write("</table>   \r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
