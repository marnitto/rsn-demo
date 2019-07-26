package org.apache.jsp.riskv3.admin.usergroup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import java.util.List;
import risk.admin.membergroup.*;
import risk.util.ConfigUtil;

public final class ifram_005fusergroup_005fleft_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	String fir_seq = null;
	List MGlist = null;
	membergroupMng MGmng = membergroupMng.getInstance();
	MGlist = MGmng.getMGList();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>X-MAS Solution</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("var selobj;\r\n");
      out.write("function kg_over(obj) {\r\n");
      out.write("\tif( selobj != obj ) {\r\n");
      out.write("\t\ttmpspan.className=obj.className;\r\n");
      out.write("\t\tobj.className='kgmenu_over';\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function kg_out(obj){\r\n");
      out.write("\tif( selobj != obj ) {\r\n");
      out.write("\t\tobj.className=tmpspan.className;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function sel(seq, index){\r\n");
      out.write("\tvar t = document.getElementsByName('sel_bg');\r\n");
      out.write("\tfor(var i = 0; i < t.length; i++){\r\n");
      out.write("\t\tt[i].className = 'pop_mail_group_td';\r\n");
      out.write("\t}\r\n");
      out.write("\tt[index].className = 'pop_mail_group_td_on';\r\n");
      out.write("\tmembergroup.mgseq.value = seq;\r\n");
      out.write("\tparent.usergroup_right.location.href = 'ifram_usergroup_right.jsp?mgseq='+seq;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function del()\r\n");
      out.write("{\r\n");
      out.write("\tif( !membergroup.mgseq.value ) {\r\n");
      out.write("\t\talert('사용자그룹을 선택하십시요.');\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\tmembergroup.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"membergroup\" action=\"ifram_usergroup_left_prc.jsp\" method=\"get\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"del\">\r\n");
      out.write("<input type=\"hidden\" name=\"mgseq\">\r\n");
      out.write("</form>\r\n");
      out.write("<span id='tmpspan' style='display:none' class=''></span>\r\n");
      out.write("<table id=\"pop_mail_group\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td style=\"padding:10px;\" valign=\"top\">\r\n");
      out.write("\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");

	for(int i=0; i < MGlist.size();i++) {
		membergroupBean MGinfo = (membergroupBean)MGlist.get(i);
		if( fir_seq == null ) fir_seq = MGinfo.getMGseq();

      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"sel_bg\" name=\"sel_bg\" class=\"");
if(i == 0){out.print("pop_mail_group_td_on");}else{out.print("pop_mail_group_td");}
      out.write("\" onclick=\"sel(");
      out.print(MGinfo.getMGseq());
      out.write(',');
      out.write(' ');
      out.print(i);
      out.write(");\" style=\"cursor:pointer;\">");
      out.print(MGinfo.getMGname());
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");

	if( fir_seq != null ) {
		out.println("sel("+fir_seq+", 0);");
	}

      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>");
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
