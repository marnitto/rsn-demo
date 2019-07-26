package org.apache.jsp.riskv3.admin.relation_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.admin.relation_manager.RelationManagerBean;
import risk.admin.relation_manager.RelationManagerMgr;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class relation_005fmanager_005fedit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/relation_manager/../../inc/sessioncheck.jsp");
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
	RelationManagerMgr rmMgr = new RelationManagerMgr();
	RelationManagerBean rmBean = null;
	pr.printParams();
	
	String rk_seq = pr.getString("rk_seq");
	String rk_name = pr.getString("rk_name");
	String mode = pr.getString("mode");
		
	String[] arr_rk_seq = rk_seq.split(",");
	String[] arr_rk_name = rk_name.split(",");
	
	rmBean = rmMgr.getKeyword(rk_seq);
		

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction insertKeyword()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.editForm;\r\n");
      out.write("\t\tif(f.rk_name.value=='')\r\n");
      out.write("\t\t{\t\r\n");
      out.write("\t\t\talert('키워드 를 입력해주세요.'); \r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t//앞뒤 공백 제거\r\n");
      out.write("\t\t\tf.rk_name.value = f.rk_name.value.replace(/^\\s*/,'');\r\n");
      out.write("\t\t\tf.rk_name.value = f.rk_name.value.replace(/\\s*$/,'');\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action='relation_manager_prc.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction selectedKeyword(keyVal){\r\n");
      out.write("\t\teditForm.rk_name.value = keyVal;\t\t\r\n");
      out.write("\t}\t\r\n");
      out.write("\t\r\n");
      out.write("\t//팝업창 크기 자동조절\r\n");
      out.write("\tfunction winResize()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar Dwidth = parseInt(document.body.scrollWidth);\r\n");
      out.write("\t  \tvar Dheight = parseInt(document.body.scrollHeight);\r\n");
      out.write("\t  \tvar divEl = document.createElement(\"div\");\r\n");
      out.write("\t  \tdivEl.style.position = \"absolute\";\r\n");
      out.write("\t  \tdivEl.style.left = \"0px\";\r\n");
      out.write("\t  \tdivEl.style.top = \"0px\";\r\n");
      out.write("\t  \tdivEl.style.width = \"100%\";\r\n");
      out.write("\t  \tdivEl.style.height = \"100%\";\r\n");
      out.write("\t  \r\n");
      out.write("\t  \tdocument.body.appendChild(divEl);\r\n");
      out.write("\t  \r\n");
      out.write("\t  \twindow.resizeBy(Dwidth-divEl.offsetWidth, Dheight-divEl.offsetHeight);\r\n");
      out.write("\t  \tdocument.body.removeChild(divEl);\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"winResize();\">\r\n");
      out.write("<form name=\"editForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"");
      out.print(mode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"rk_seq\" value=\"");
      out.print(rk_seq);
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=\"3\" id=\"pop_head\">\r\n");
      out.write("\t\t\t<p>연관키워드</p>\r\n");
      out.write("\t\t\t<span><a href=\"javascript:close();\"><img src=\"../../../images/search/pop_tit_close.gif\"></a></span>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
 if(mode.equals("insert") || mode.equals("update")) { 
      out.write("\r\n");
      out.write("<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td style=\"padding-left:10px\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"100\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>연관키워드 :</strong></td>\r\n");
      out.write("\t\t\t<td align=\"left\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("\t\t\t<input class=\"textbox\" type=\"text\" name=\"rk_name\" \r\n");
      out.write("\t\t\t");
 if(mode.equals("add")) { 
      out.write(" value=\"\" ");
 } 
      out.write("\r\n");
      out.write("\t\t\t");
 if(mode.equals("update")){ 
      out.write(" value=\"");
      out.print(rmBean.getRk_name() );
      out.write('"');
      out.write(' ');
 } 
      out.write("></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
 } 
      out.write('\r');
      out.write('\n');
if(mode.equals("sum")) { 
      out.write("\r\n");
      out.write("<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td style=\"padding-left:10px\"><table width=\"370\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t");
 for(int i=0; i<arr_rk_seq.length; i++) { 
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>연관키워드 :</strong></td>\r\n");
      out.write("\t\t\t<td width=\"311\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\"><a href=\"javascript:selectedKeyword('");
      out.print(arr_rk_name[i]);
      out.write("');\">");
      out.print(arr_rk_name[i]);
      out.write("</a>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>키워드 :</strong></td>\r\n");
      out.write("\t\t\t<td width=\"311\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("\t\t\t<input class=\"textbox\" type=\"text\" id=\"rk_name\" name=\"rk_name\" value=\"\">\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\t\r\n");
      out.write("\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
 } 
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table width=\"378\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"40\" align=\"right\">\r\n");
      out.write("\t\t<img src=\"../../../images/admin/member/btn_save2.gif\"  hspace=\"5\" onclick=\"insertKeyword();\" style=\"cursor:pointer;\">\r\n");
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
