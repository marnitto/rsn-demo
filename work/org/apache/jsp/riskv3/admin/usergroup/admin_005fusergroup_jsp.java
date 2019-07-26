package org.apache.jsp.riskv3.admin.usergroup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class admin_005fusergroup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/usergroup/../../inc/sessioncheck.jsp");
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
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");
      out.write("<title></title>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\n");
      out.write("<!--\n");
      out.write("\tfunction ins_mg() {\n");
      out.write("\t\tvar url = \"pop_usergroup.jsp?mode=ins\";\n");
      out.write("\t\twindow.open(url, \"pop_usergroup\", \"width=400,height=150\");\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction edit_mg() {\n");
      out.write("\t\t\tfrm = usergroup_left.membergroup;\n");
      out.write("\t\t\tif( !frm.mgseq.value ) {\n");
      out.write("\t\t\t\talert('사용자그룹을 선택하십시요.');\n");
      out.write("\t\t\t} else {\n");
      out.write("\t\t\t\tvar url = \"pop_usergroup.jsp?mode=edit&mgseq=\"+frm.mgseq.value;\n");
      out.write("\t\t\t\twindow.open(url, \"pop_usergroup\", \"width=400,height=150\");\n");
      out.write("\t\t\t}\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction del_mg() {\n");
      out.write("\t\tif( confirm('사용자그룹을 삭제하시겠습니까?') )\n");
      out.write("\t\tusergroup_left.del();\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction alert_msg() {\n");
      out.write("\t\talert(\"사용자그룹에 속한 사용자가 있습니다.\");\n");
      out.write("\t}\n");
      out.write("//-->\n");
      out.write("</script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t<tr>\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/member_group/tit_icon.gif\" /><img src=\"../../../images/admin/member_group/tit_0502.gif\" /></td>\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">사용자그룹/권한관리</td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td align=\"center\">\n");
      out.write("\t\t\t\t<table style=\"height:350px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:340px;\">\n");
      out.write("\t\t\t\t\t\t<table style=\"width:100%;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td style=\"height:30px;\"><span class=\"sub_tit\">사용자그룹</span></td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td>\n");
      out.write("\t\t\t\t\t\t\t\t\t<iframe id=\"usergroup_left\" name=\"usergroup_left\" src=\"ifram_usergroup_left.jsp\" width=\"340\" height=\"300\" scrolling=\"auto\" frameborder=\"0\"></iframe>\n");
      out.write("\t\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td style=\"height:30px;\"><img src=\"../../../images/admin/member_group/btn_add.gif\" onclick=\"ins_mg();\" style=\"cursor:pointer;\"/><img src=\"../../../images/admin/member_group/btn_modify.gif\" onclick=\"edit_mg();\" style=\"cursor:pointer;\"/><img src=\"../../../images/admin/member_group/btn_del2.gif\" onclick=\"del_mg();\" style=\"cursor:pointer;\"/></td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:30px;\"></td>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:340px;\">\n");
      out.write("\t\t\t\t\t\t<table style=\"width:100%;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td style=\"height:30px;\"><span class=\"sub_tit\">그룹별 권한설정</span></td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td>\n");
      out.write("\t\t\t\t\t\t\t\t\t<iframe id=\"usergroup_right\" name=\"usergroup_right\" src=\"ifram_usergroup_right.jsp\" width=\"340\" height=\"300\" scrolling=\"auto\" frameborder=\"0\"></iframe>\n");
      out.write("\t\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td style=\"height:30px;\"><img src=\"../../../images/admin/member_group/btn_save.gif\" onclick=\"usergroup_right.curr_save();\" style=\"cursor:pointer;\"/></td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\n");
      out.write("\t\t</table>\n");
      out.write("\t\t</td>\n");
      out.write("\t</tr>\n");
      out.write("</table>\n");
      out.write("</body>\n");
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
