package org.apache.jsp.riskv3.admin.classification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;

public final class classification_005fmgr_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"10\" topmargin=\"10\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<table style=\"width:100%;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\" style=\"padding-left:20px\">\r\n");
      out.write("\t\t\t<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/classification/tit_icon.gif\" /><img src=\"../../../images/admin/classification/tit_0507.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">보고서 분류체계 관리</td>\r\n");
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
      out.write("\t\t\t<table width=\"780\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t  <tr>\r\n");
      out.write("\t\t\t    <td style=\"padding: 0px 0px 0px 10px;\"><table width=\"740\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t      <tr>\r\n");
      out.write("\t\t\t        <td width=\"300\" valign=\"top\"><table width=\"300\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t          <tr>\r\n");
      out.write("\t\t\t            <td height=\"25\"><img src=\"../../../images/admin/classification/ico_won_blue02.gif\" width=\"16\" height=\"14\" align=\"absmiddle\"><strong>분류체계</strong></td>\r\n");
      out.write("\t\t\t          </tr>\r\n");
      out.write("\t\t\t          <tr>\r\n");
      out.write("\t\t\t            <td><table cellSpacing=1 cellPadding=2 width=300 bgColor=#cccccc border=0>\r\n");
      out.write("\t\t\t              <tbody>\r\n");
      out.write("\t\t\t                <tr bgColor=#d5d5d5>\r\n");
      out.write("\t\t\t                  <td style=\"padding-right: 0px; padding-left: 10px; padding-bottom: 5px;padding-top: 5px\" Align=top bgColor=#ffffff colSpan=5>\r\n");
      out.write("\t\t\t                    <iframe id=frm_menu style=\"width: 285px; height: 300px\" border=0  name=frm_menu src=\"frm_classification.jsp\"  frameBorder=0></iframe>\r\n");
      out.write("\t\t\t                  </td>\r\n");
      out.write("\t\t\t               </tr>\r\n");
      out.write("\t\t\t              </tbody>\r\n");
      out.write("\t\t\t            </table></td>\r\n");
      out.write("\t\t\t          </tr>\r\n");
      out.write("\t\t\t        </table></td>\r\n");
      out.write("\t\t\t        <td width=\"60\">&nbsp;</td>\r\n");
      out.write("\t\t\t        <td width=\"380\" valign=\"top\"><table width=\"380\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t          <tr>\r\n");
      out.write("\t\t\t            <td height=\"25\"><img src=\"../../../images/admin/classification/ico_won_blue02.gif\" width=\"16\" height=\"14\" align=\"absmiddle\"><strong>분류항목</strong></td>\r\n");
      out.write("\t\t\t          </tr>\r\n");
      out.write("\t\t\t          <tr>\r\n");
      out.write("\t\t\t            <td>\r\n");
      out.write("\t\t\t           \t<table cellSpacing=1 cellPadding=2 width=300 bgColor=#cccccc border=0>\r\n");
      out.write("\t\t\t              <tbody>\r\n");
      out.write("\t\t\t                <tr bgColor=#d5d5d5>\r\n");
      out.write("\t\t\t                  <td style=\"padding-right: 0px; padding-left: 0px; padding-bottom: 0px;padding-top: 0px\" align=top bgColor=#ffffff colSpan=5>\r\n");
      out.write("\t\t\t                    <iframe id=\"frm_detail\" name=\"frm_detail\" style=\"width: 396px; height: 310px\" scrolling=\"auto\" src=\"frm_classification_detail.jsp\"  frameBorder=\"0\"></iframe>\r\n");
      out.write("\t\t\t                  </td>\r\n");
      out.write("\t\t\t               </tr>\r\n");
      out.write("\t\t\t              </tbody>\r\n");
      out.write("\t\t\t            </table>\r\n");
      out.write("\t\t\t           </td>\r\n");
      out.write("\t\t\t          </tr>\r\n");
      out.write("\t\t\t        </table></td>\r\n");
      out.write("\t\t\t      </tr>\r\n");
      out.write("\t\t\t    </table>\r\n");
      out.write("\t\t\t      <table width=\"740\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t        <tr>\r\n");
      out.write("\t\t\t          <td height=\"75\">※ 분류체계는 보고서등과 연동되므로 추가/삭제 할수 없습니다.<br>\r\n");
      out.write("\t\t\t            ※ 각 분류체계의 하위 분류항목만 추가/삭제 할 수 있습니다.<br>\r\n");
      out.write("\t\t\t          ※ 분류항목을 변경하여도 기존에 발송된 보고서에는 영향을 미치지 않습니다.</td>\r\n");
      out.write("\t\t\t        </tr>\r\n");
      out.write("\t\t\t      </table>\r\n");
      out.write("\t\t\t    </td>\r\n");
      out.write("\t\t\t  </tr>\r\n");
      out.write("\t\t\t  <tr>\r\n");
      out.write("\t\t\t    <td>&nbsp;</td>\r\n");
      out.write("\t\t\t  </tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
