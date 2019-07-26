package org.apache.jsp.riskv3.admin.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class admin_005fsite_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(SS_URL);
      out.write("css/base.css\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write(" function add_sg() {\r\n");
      out.write("\t\tfrm = tg_sitemng.tg_site;\r\n");
      out.write("\t\tvar url = \"pop_sitegroup.jsp?mode=ins\";\r\n");
      out.write("\t\twindow.open(url, \"pop_sitegroup\", \"width=400,height=156\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function right_move() {\r\n");
      out.write("\tlfrm = sc_sitemng;\r\n");
      out.write("\trfrm = tg_sitemng;\r\n");
      out.write("\tif( !lfrm.sg_mng.gsn.value ) {\r\n");
      out.write("\t\talert('대상을 선택하여야 합니다.');\r\n");
      out.write("\t} else if( rfrm.tg_site.sg_seq.value > 0 ) {\r\n");
      out.write("\t\tlfrm.sg_mng.sgseq.value = rfrm.tg_site.sg_seq.value;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif( frames.sc_sitemng.sg_change.code1.value ) {\r\n");
      out.write("\t\t\tsource_form.code1.value = frames.sc_sitemng.sg_change.code1.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif( frames.sc_sitemng.sg_change.language.value ) {\r\n");
      out.write("\t\t\tsource_form.language.value = frames.sc_sitemng.sg_change.language.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.sg_seq.value ) {\r\n");
      out.write("\t\t\ttarget_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.language.value ) {\r\n");
      out.write("\t\t\ttarget_form.language.value = frames.tg_sitemng.tg_site.language.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tlfrm.sg_mng.submit();\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\talert(\"우측 사이트 그룹을 선택하여 주십시요.\");\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function left_move() {\r\n");
      out.write("\tlfrm = sc_sitemng;\r\n");
      out.write("\trfrm = tg_sitemng;\r\n");
      out.write("\tif( !rfrm.sg_mng.gsn.value ) {\r\n");
      out.write("\t\talert('대상을 선택하여야 합니다.');\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\trfrm.sg_mng.sgseq.value = rfrm.tg_site.sg_seq.value;\r\n");
      out.write("\t\tif( frames.sc_sitemng.sg_change.code1.value ) {\r\n");
      out.write("\t\t\tsource_form.code1.value = frames.sc_sitemng.sg_change.code1.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif( frames.sc_sitemng.sg_change.language.value ) {\r\n");
      out.write("\t\t\tsource_form.language.value = frames.sc_sitemng.sg_change.language.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.sg_seq.value ) {\r\n");
      out.write("\t\t\ttarget_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.language.value ) {\r\n");
      out.write("\t\t\ttarget_form.language.value = frames.tg_sitemng.tg_site.language.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\trfrm.sg_mng.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function search_SourceSite()\r\n");
      out.write("{\r\n");
      out.write("\tif( frames.sc_sitemng.sg_change.code1.value ) {\r\n");
      out.write("\t\tsource_form.code1.value = frames.sc_sitemng.sg_change.code1.value;\r\n");
      out.write("\t}\r\n");
      out.write("\tif( frames.sc_sitemng.sg_change.language.value ) {\r\n");
      out.write("\t\tsource_form.language.value = frames.sc_sitemng.sg_change.language.value;\r\n");
      out.write("\t}\r\n");
      out.write("\tsource_form.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\tfunction search_TargetSite()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.sg_seq.value ) {\r\n");
      out.write("\t\t\ttarget_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif( frames.tg_sitemng.tg_site.language.value ) {\r\n");
      out.write("\t\t\ttarget_form.language.value = frames.tg_sitemng.tg_site.language.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\ttarget_form.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction siteSave()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.frm_site_get.location = 'site_download_excel.jsp';\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction siteAllSave()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.frm_site_get.location = 'site_download_excel.jsp?mode=all';\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("<body>\r\n");
      out.write("<table style=\"height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t\t<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:0 auto\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/site/tit_icon.gif\" /><img src=\"../../../images/admin/site/tit_0510.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">수집사이트관리</td>\r\n");
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
      out.write("      <table width=\"770\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:0 auto\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td align=\"right\" style=\"padding-left:10px\"><table width=\"750\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td height=\"50\" align=\"left\"> 정보를 수집할 대상 사이트를 관리합니다 .&#13;<br>\r\n");
      out.write("              좌측의 사이트 리스트에서 수집을 원하는 사이트를 우측리스트로 이동하시면 ,&#13;대상 사이트에서 정보를 수집합니다 .&#13;&#13;</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"1\" height=\"5\"><!-- <img src=\"../../../images/admin/site/brank.gif\" > --></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table>\r\n");
      out.write("            <table width=\"750\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td width=\"335\" align=\"left\" valign=\"top\">\r\n");
      out.write("                <form name=\"source_form\" action=\"ifram_source.jsp\" method=\"post\" target=\"sc_sitemng\">\r\n");
      out.write("\t\t\t\t<input name=\"code1\" type=\"hidden\">\r\n");
      out.write("\t\t\t\t<input name=\"language\" type=\"hidden\">\r\n");
      out.write("\t\t\t\t<table width=\"335\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"25\" class=\"textBbig\">\r\n");
      out.write("                    \t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                    \t\t<tr>\r\n");
      out.write("                    \t\t\t<td align=\"left\"><img src=\"../../../images/admin/site/admin_ico01.gif\" width=\"12\" height=\"10\" hspace=\"2\"><strong>전체 사이트 리스트</strong></td>\r\n");
      out.write("                    \t\t\t<td align=\"right\"><img src=\"../../../images/admin/site/excel_save.gif\" width=\"94\" height=\"24\" hspace=\"2\" onclick=\"siteAllSave();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                    \t\t</tr>\r\n");
      out.write("                    \t</table>\r\n");
      out.write("                    </td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"30\"><input  name=\"sch_key\" type=\"text\" size=\"27\" style=\"border:1 solid; border-color:#CCCCCC\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { search_SourceSite();}\">\r\n");
      out.write("                    <img src=\"../../../images/admin/site/search_btn.gif\" width=\"41\" height=\"19\" align=\"absmiddle\" onclick=\"search_SourceSite();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("\t\t\t\t  \r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><iframe name=\"sc_sitemng\" src=\"ifram_source.jsp\" width=\"340\" height=\"310\" scrolling=\"auto\" frameborder=\"0\" style=\"border:1px solid; border-color:#CCCCCC\"></iframe></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                </table></form></td>\r\n");
      out.write("                <td width=\"30\" align=\"center\" valign=\"middle\"><img src=\"../../../images/admin/site/btn_left.gif\" width=\"18\" height=\"18\" onclick=\"left_move();\" style=\"cursor:hand;\"><br>\r\n");
      out.write("                <img src=\"../../../images/admin/site/btn_right.gif\" width=\"18\" height=\"18\" vspace=\"6\" onclick=\"right_move();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("                <td width=\"335\" align=\"left\" valign=\"top\">\r\n");
      out.write("                <form name=\"target_form\" action=\"ifram_target.jsp\" method=\"get\" target=\"tg_sitemng\">\r\n");
      out.write("                <input type=\"hidden\" name=\"sg_seq\" value=\"0\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"language\" value=\"0\">\r\n");
      out.write("\t\t\t\t<table width=\"335\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"25\" align=\"left\" class=\"textBbig\" >\r\n");
      out.write("                    \t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                    \t\t<tr>\r\n");
      out.write("                    \t\t\t<td align=\"left\"><img src=\"../../../images/admin/site/admin_ico01.gif\" width=\"12\" height=\"10\" hspace=\"2\"><strong>수집대상 사이트 리스트</strong></td>\r\n");
      out.write("                    \t\t\t<td align=\"right\"><img src=\"../../../images/admin/site/excel_save.gif\" width=\"94\" height=\"24\" hspace=\"2\" onclick=\"siteSave();\" style=\"cursor:pointer;\"></td>\r\n");
      out.write("                    \t\t</tr>\r\n");
      out.write("                    \t</table>\r\n");
      out.write("                    </td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"30\">\r\n");
      out.write("\t\t\t\t\t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"tg_sch\" type=\"text\" size=\"27\" style=\"border:1 solid; border-color:#CCCCCC\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { search_TargetSite();}\"></td>\r\n");
      out.write("                    <td width=\"64\" align=\"left\"><img src=\"../../../images/admin/site/search_btn.gif\" width=\"41\" height=\"19\" align=\"absmiddle\" onclick=\"search_TargetSite();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t\t\t\t\t<td width=\"99\" align=\"right\"><img src=\"../../../images/admin/site/btn_groupadd.gif\" width=\"94\" height=\"24\" onclick=\"add_sg();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><iframe name=\"tg_sitemng\" src=\"ifram_target.jsp\" width=\"340\" height=\"310\" scrolling=\"auto\" frameborder=\"0\" style=\"border:1px solid; border-color:#CCCCCC\"></iframe></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                </table></form></td>\r\n");
      out.write("                <td align=\"left\">&nbsp;</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\" height=\"30\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" style=\"padding-left:30px;\">* Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다 .&#13;&#13;</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<iframe name=\"frm_site_get\" id=\"frm_site_get\" src=\"\" width=\"0\" height=\"0\" frameborder=\"0\"></iframe>\r\n");
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
