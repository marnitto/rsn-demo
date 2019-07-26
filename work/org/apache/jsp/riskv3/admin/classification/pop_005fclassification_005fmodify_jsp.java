package org.apache.jsp.riskv3.admin.classification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class pop_005fclassification_005fmodify_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	pr.printParams();
	
	String itype = pr.getString("itype");
	String icode = pr.getString("icode");
	
	String ic_seq = pr.getString("ic_seq");	
	String ic_name = pr.getString("ic_name");
	
	String edit_ic_regdate = pr.getString("edit_ic_regdate");
	String ic_regtime = pr.getString("ic_regtime");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("<!--\t\r\n");
      out.write("\t.t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("\tiframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("-->\r\n");
      out.write("\t</style>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction modify()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.editForm;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(f.ic_name.value=='')\r\n");
      out.write("\t\t{\t\r\n");
      out.write("\t\t\talert('명칭 를 입력해주세요.'); return;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t//앞뒤 공백 제거\r\n");
      out.write("\t\t\tf.ic_name.value = f.ic_name.value.replace(/^\\s*/,'');\r\n");
      out.write("\t\t\tf.ic_name.value = f.ic_name.value.replace(/\\s*$/,'');\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action='classification_prc.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"editForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"itype\" value=\"");
      out.print(itype);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"icode\" value=\"");
      out.print(icode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ic_seq\" value=\"");
      out.print(ic_seq);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"update\">\r\n");
      out.write("<input type=\"hidden\" name=\"ic_regtime\" value=\"");
      out.print(ic_regtime);
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/tit_icon.gif\" /><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/pressgroup/tit_002222.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">보도자료 관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td style=\"padding-left:10px\"><table width=\"370\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"60\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>분류명 :</strong></td>\r\n");
      out.write("\t\t\t<td width=\"311\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"text\" class=\"txtbox\" size=\"40\" name=\"ic_name\" value=\"");
      out.print(ic_name);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");

	if(itype.equals("7")){
	
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("    <td style=\"padding-left:10px;padding-top:10px\"><table width=\"370\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"60\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>게시일 :</strong></td>\r\n");
      out.write("\t\t\t<td width=\"140\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"text\" id=\"ic_regdate\" name=\"ic_regdate\" value=\"");
      out.print(edit_ic_regdate);
      out.write("\" readonly></td>\r\n");
      out.write("\t\t\t<td align=\"left\"><img src=\"../../../images/admin/classification/btn_calendar.gif\" id=\"addbtn\" align=\"absmiddle\" onclick=\"calendar_sh(document.getElementById('ic_regdate'))\" style=\"cursor:pointer;\"></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");
	
	}
	
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("</table>\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" background=\"../../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>                                \t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"calendar_calclass\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"5\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table width=\"378\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"40\" align=\"right\">\r\n");
      out.write("\t\t<img src=\"../../../images/admin/member/btn_save2.gif\"  hspace=\"5\" onclick=\"modify();\" style=\"cursor:pointer;\">\r\n");
      out.write("\t\t<img src=\"../../../images/admin/member/btn_cancel.gif\" onclick=\"window.close();\" style=\"cursor:pointer;\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
