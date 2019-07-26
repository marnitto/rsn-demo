package org.apache.jsp.riskv3.inc.topmenu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.StringUtil;
import risk.search.userEnvInfo;
import risk.util.ConfigUtil;

public final class inc_005ftopmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/inc/topmenu/../sessioncheck.jsp");
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

	userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
	String sms = "";
	if( request.getParameter("sms") != null ) sms = request.getParameter("sms");
	StringUtil su = new StringUtil();
	
	String MGmenu = "";
	String[] arrMGmenu = null;
	
	MGmenu = env.getMg_menu();
	System.out.println("MGmenu:"+MGmenu);
	if( MGmenu != null )
	arrMGmenu = MGmenu.split(",");
	
	//대쉬보드 시간 초기화
	session.setAttribute("SS_SEARCHDATE", "");

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css/basic.css\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("var sg_no = ");
      out.print(SS_MG_NO);
      out.write(";\r\n");
      out.write("// 현재 선택된 메뉴를 저장한다.\r\n");
      out.write("// 초기에 지정해 주어야 한다. 지정하지 않으면 검색메뉴를 지정한다.\r\n");
      out.write("var selectedObj = \"\";\r\n");
      out.write("// 각 메뉴 클릭시 이동할 페이지 정보를 지정한다.\r\n");
      out.write("\r\n");
      out.write("var link = new Array(\r\n");
      out.write("\"../../search/search.jsp?INIT=INIT\",\r\n");
      out.write("\"../../issue/issue.jsp\",\r\n");
      out.write("\"../../report/report.jsp\",\r\n");
      out.write("\"../../statistics/statistics.jsp\",\r\n");
      out.write("\"../../admin/admin_main.jsp\"\r\n");
      out.write(");\r\n");
      out.write("// 메뉴 클릭시 이벤트\r\n");
      out.write("// 클릭한 메뉴를 선택상태로 만들고, 하단 프레임을 변경한다.\r\n");
      out.write("function mnu_chick(obj,seq) {\r\n");
      out.write("\tvar tmpObj;\r\n");
      out.write("\tfor (var i=1;i<6 ;i++ )\r\n");
      out.write("\t{\r\n");
      out.write("\t\ttmpObj = eval(\"document.all.img\"+i);\r\n");
      out.write("\t\tif( tmpObj ) setOff(tmpObj);\r\n");
      out.write("\t}\r\n");
      out.write("\tsetOn(obj);\r\n");
      out.write("\tselectedObj = obj;\r\n");
      out.write("\tparent.bottomFrame.location.href=link[seq];\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function dashboard_chick() {\r\n");
      out.write("\tparent.location.href = '../../../dashboard/summary/summary.jsp';\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// 각 메뉴의 마우스 오버시 이벤트\r\n");
      out.write("function mnu_over(obj) {\r\n");
      out.write("\tsetOn(obj);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// 각 메뉴의 마우스 아웃시 이벤트\r\n");
      out.write("// 현재 선택된 메뉴면 \r\n");
      out.write("function mnu_out(obj) {\r\n");
      out.write("\tif (selectedObj != obj)\t{ setOff(obj);\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function setOn(obj)\r\n");
      out.write("{\r\n");
      out.write("\tvar oSrc = obj.src;\r\n");
      out.write("\tre = /_off./i\r\n");
      out.write("\tvar nSrc = obj.src.replace(re, \"_on.\");\r\n");
      out.write("\tobj.src=nSrc;\r\n");
      out.write("}\r\n");
      out.write("function setOff(obj)\r\n");
      out.write("{\r\n");
      out.write("\tvar oSrc = obj.src;\r\n");
      out.write("\tre = /_on./i\r\n");
      out.write("\tvar nSrc = obj.src.replace(re, \"_off.\");\r\n");
      out.write("\tobj.src=nSrc;\r\n");
      out.write("}\r\n");
      out.write("function system_wait() {\r\n");
      out.write("\talert('준비중입니다.');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function sms_check() {\r\n");
      out.write("\tsetOn(document.all.img3);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<body  leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("        <td width=\"300\" height=\"51\"><img src=\"../../../images/top/top_logo.gif\" onclick=\"mnu_chick(img1, 0);\" style=\"cursor:hand;height:100%\"></td>\r\n");
      out.write("        <td height=\"51\"><table height=\"51\" width=\"840\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("      \r\n");

	
	if( arrMGmenu != null ) {
		//권한 1이면 정보검색만
		if( su.inarray(arrMGmenu, "1") ) {

      out.write(" \r\n");
      out.write("              <td height=\"51\" ");
if(arrMGmenu.length > 1){out.println("width=\"138\"");}
      out.write(" style=\"padding: 0px 0px 0px 0px;\"><img  onclick=\"mnu_chick(this, 0);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m01_off.gif\" name=\"img1\" height=\"51\"  border=\"0\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t<!-- \r\n");
      out.write("              <td width=\"88\"><img onclick=\"mnu_chick(this, 1);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m02_off.gif\" name=\"img2\" width=\"88\" height=\"37\" border=\"0\" style=\"cursor:hand;\"></a></td>\r\n");
      out.write("              <td width=\"2\"><img src=\"../../../images/top/top_menu_bar.gif\" width=\"2\" height=\"37\"></td>\r\n");
      out.write("     -->\r\n");

		}
//		//권한 2이면 정보검색과 이슈관리
		if( su.inarray(arrMGmenu, "2") ) {
			

      out.write("\r\n");
      out.write("\t\t\t  <td height=\"51\" width=\"138\" style=\"padding: 0px 0px 0px 0px;\"><img height=\"51\" onclick=\"mnu_chick(this, 1);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m02_off.gif\" name=\"img2\" border=\"0\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\r\n");

		}

		if( su.inarray(arrMGmenu, "3") ) {
			

      out.write("\t\r\n");
      out.write("\t\t\r\n");
      out.write("              <td height=\"51\" width=\"138\" style=\"padding: 0px 0px 0px 0px;\"><img height=\"51\" onclick=\"mnu_chick(this, 2);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m03_off.gif\" name=\"img3\" border=\"0\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                      \r\n");

		}

 		if( su.inarray(arrMGmenu, "4") ) {

      out.write("\t\r\n");
      out.write("\t \t\t  <td height=\"51\" width=\"138\" style=\"padding: 0px 0px 0px 0px;\"><img height=\"51\" onclick=\"mnu_chick(this, 3);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m04_off.gif\" name=\"img4\"  border=\"0\" style=\"cursor:hand;\"></td> \r\n");
      out.write("        \r\n");

		}
		if( su.inarray(arrMGmenu, "6") ) {		

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t<td height=\"51\" width=\"138\" style=\"padding: 0px 0px 0px 0px;\"><img height=\"51\" onclick=\"dashboard_chick();\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m06_off.gif\" name=\"img6\" border=\"0\" style=\"cursor:hand;\"></td>\r\n");
		
		}
		if( su.inarray(arrMGmenu, "5") ) {		

      out.write("\t\t\t\r\n");
      out.write("\t\t\t  <td height=\"51\" width=\"138\" style=\"padding: 0px 0px 0px 0px;\"><img height=\"51\" onclick=\"mnu_chick(this, 4);\" onMouseOut=\"mnu_out(this);\" onMouseOver=\"mnu_over(this);\" src=\"../../../images/top/top_m05_off.gif\" name=\"img5\" border=\"0\" style=\"cursor:hand;\"></td>\t\r\n");
      out.write("\t\r\n");
		
		}
	} 

      out.write(" \r\n");
      out.write("\t\t\t  <td width=\"100%\" background=\"../../../images/top/menu_bg.jpg\" align=\"right\" style=\"padding:10px 10px 0px 0px;vertical-align:top\">\r\n");
      out.write("\t\t\t    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t          <tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"textwhite\" style=\"vertical-align:middle\"><img src=\"../../../images/top/top_ico01.gif\" style=\"vertical-align:middle\"> <strong>");
      out.print(SS_M_NAME);
      out.write("</strong>님<br> 반갑습니다.</td>\r\n");
      out.write("\t\t          \t<td valign=\"middle\" style=\"padding-left:10px\"><a href=\"../../logout.jsp\" target=\"_top\"><img src=\"../../../images/top/top_logout.gif\" /></a></td>\r\n");
      out.write("\t\t          \t<td valign=\"middle\"><a href=\"../../search/search_env_setting.jsp\" target=\"contentsFrame\"><img src=\"../../../images/top/setting.gif\" /></a></td>\r\n");
      out.write("\t\t          \t<!-- <td  align=\"right\"  valign=\"middle\"><img src=\"../../../images/top/top_logout.gif\"  hspace=\"5\" style=\"padding: 0px 0px 0px 0px;\"><img src=\"../../../images/top/setting.gif\" hspace=\"5\" style=\"padding: 0px 0px 0px 0px;\" ></td> -->\r\n");
      out.write("\t\t          </tr>\r\n");
      out.write("\t            </table>\r\n");
      out.write("\t          </td>\r\n");
      out.write("\t          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("     </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("   </table>\r\n");
      out.write(" </body>\r\n");
      out.write("</html>\r\n");
      out.write("                \r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("// 최초 선택된 메뉴를 선택상태로 만든다.\r\n");
      out.write("// 선택된 메뉴가 없으면 검색메뉴를 선택상태로 만든다.\r\n");
      out.write("if (selectedObj==\"\"){setOn(document.all.img1);}\r\n");
      out.write("else{setOn(selectedObj);}\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
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
