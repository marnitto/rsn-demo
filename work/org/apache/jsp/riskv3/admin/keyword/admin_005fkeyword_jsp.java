package org.apache.jsp.riskv3.admin.keyword;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class admin_005fkeyword_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(SS_URL);
      out.write("css/base.css\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction add_key()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tfrm = keyword_right.keygroup;\r\n");
      out.write("\t\tfrm.mode.value = 'add';\r\n");
      out.write("\t\tvar url = \"pop_keyword_add.jsp?xp=\"+frm.xp.value+\"&yp=\"+frm.yp.value+\"&zp=\"+frm.zp.value;\r\n");
      out.write("\t\twindow.open(url, \"pop_keyword\", \"width=400,height=400\");\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction del_key()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( confirm(\"하위분류 및 키워드까지 삭제됩니다.\\n삭제하시겠습니까?\") )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tkeyword_right.del_kg();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction up_key()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tkeyword_right.up_kg();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction down_key()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tkeyword_right.down_kg();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction keySave()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.frm_keyword_get.location = 'keyword_download_excel.jsp';\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction openLayer()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.getElementById(\"floater\").style.display='';\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction closeLayer()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.getElementById(\"floater\").style.display='none';\r\n");
      out.write("\t}\r\n");
      out.write("\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"floater\" style=\"width:328px;height:168px; top:485px; left=490px; cellspacing:0; cellpadding:0; position: absolute;border:black 0px solid; display:none;\">  \r\n");
      out.write("<img src=\"../../../images/admin/keyword/layer_img.gif\" style=\"cursor:hand\" onclick=\"closeLayer();\"></img>\r\n");
      out.write("</div>\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t\t<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td width=\"15px\"></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/keyword/tit_icon.gif\" /><img src=\"../../../images/admin/keyword/tit_0505.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">키워드관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("      <table width=\"770\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" style=\"padding-left:10px\"><table width=\"750\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td height=\"35\" align=\"left\">수집된 정보의 필터링 및 분류에 사용될 키워드 그룹 및 키워드를 관리합니다.</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("             <tr>\r\n");
      out.write("              <td height=\"10\">&nbsp;</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table>\r\n");
      out.write("            <table width=\"750\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("\t\t\t    <td align=\"left\">&nbsp;</td>\r\n");
      out.write("                <td width=\"220\" align=\"left\" valign=\"top\"><table width=\"220\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"25\" class=\"textBbig\" >\r\n");
      out.write("                    \t<table width=\"100%\" border=\"0\">\r\n");
      out.write("                    \t\t<tr>\r\n");
      out.write("                    \t\t\t<td align=\"left\"><img src=\"../../../images/admin/keyword/admin_ico01.gif\" width=\"12\" height=\"10\" hspace=\"2\"><strong>키워드그룹</strong></td>\r\n");
      out.write("                    \t\t\t<td align=\"right\"><img src=\"../../../images/admin/keyword/excel_save.gif\" width=\"94\" height=\"24\" hspace=\"2\" onclick=\"keySave();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                    \t\t</tr>\r\n");
      out.write("                    \t</table>\r\n");
      out.write("                    </td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><iframe name=\"keyword_left\" id=\"keyword_left\" src=\"admin_keyword_left.jsp\" width=\"240\" height=\"300\" scrolling=\"auto\" frameborder=\"0\" style=\"border:1px solid; border-color:#CCCCCC\"></iframe></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                </table>\r\n");
      out.write("                  <table width=\"240\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td><img src=\"../../../images/admin/keyword/form_add.gif\" vspace=\"5\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { add_key();}\" onclick=\"add_key();\" style=\"cursor:hand;\"><img src=\"../../../images/admin/keyword/form_del.gif\" hspace=\"5\" vspace=\"5\" onclick=\"del_key();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                      <td align=\"right\"><img src=\"../../../images/admin/keyword/up_btn.gif\" width=\"18\" height=\"18\" onclick=\"up_key();\" style=\"cursor:hand;\"><img src=\"../../../images/admin/keyword/down_btn.gif\" width=\"18\" height=\"18\" hspace=\"5\" onclick=\"down_key();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                  </table></td>\r\n");
      out.write("                <td width=\"30\" align=\"left\">&nbsp;</td>\r\n");
      out.write("                <td width=\"335\" align=\"left\" valign=\"top\"><table width=\"335\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"25\" align=\"left\" class=\"textBbig\" ><img src=\"../../../images/admin/keyword/admin_ico01.gif\" width=\"12\" height=\"10\" hspace=\"2\"><strong>세부정보</strong></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><iframe name=\"keyword_right\" id=\"keyword_right\" src=\"admin_keyword_right.jsp\" width=\"335\" height=\"300\" scrolling=\"auto\" frameborder=\"0\" style=\"border:1px solid; border-color:#CCCCCC\"></iframe></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("\t\t          \t<td height=\"10\" align=\"right\">&nbsp;</td>\r\n");
      out.write("\t\t          </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("\t\t          \t<td height=\"10\" align=\"right\"><img src=\"../../../images/admin/keyword/banner_01.gif\" style=\"cursor:hand\" onclick=\"openLayer();\"></td>\r\n");
      out.write("\t\t          </tr>\r\n");
      out.write("                </table>                  \r\n");
      out.write("                  <table width=\"335\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td align=\"right\"><!--<img src=\"../../../images/admin/keyword/btn_save.gif\" width=\"48\" height=\"21\" vspace=\"5\">--></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                  </table></td>\r\n");
      out.write("                <td align=\"left\">&nbsp;</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<iframe name=\"frm_keyword_get\" id=\"frm_keyword_get\" src=\"\" width=\"0\" height=\"0\" frameborder=\"0\"></iframe>\r\n");
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
