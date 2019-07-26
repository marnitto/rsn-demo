package org.apache.jsp.dashboard.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

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

 	Cookie[] cookies = request.getCookies(); //쿠키를 가져옴
	String Saveid = "";  				 // 쿠키에서 가져온 id저장
	String script = "";  				 // javascript 저장
	if(cookies != null){ 

		for(int i = 0;i<cookies.length;i++){
			System.out.println("cookies[i].getName():"+cookies[i].getName());
			System.out.println("cookies[i].getValue():"+cookies[i].getValue());
			if(cookies[i].getName().equals("lifeRSNid")){
				Saveid = URLDecoder.decode (cookies[i].getValue() , "utf-8" );
			}			
		}			
	}else{
		System.out.println("not cookies!!");
	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Include PAGE TOP -->\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../inc/inc_page_top.jsp", out, false);
      out.write("\r\n");
      out.write("<!-- // Include PAGE TOP -->\r\n");
      out.write("\r\n");
      out.write("<body style=\"background:#ffffff\">\r\n");
      out.write("\t<script type=\"text/JavaScript\">\r\n");
      out.write("\tfunction id_check() {\r\n");
      out.write("\t\tif( !asLogin.FimUserID.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('ID를  입력하십시오.');\r\n");
      out.write("\t\t\tasLogin.FimUserID.focus();\r\n");
      out.write("\t\t}else if( !asLogin.FimUserPasswd.value ){\r\n");
      out.write("\t\r\n");
      out.write("\t\t\talert('비밀번호를 입력하십시오.');\r\n");
      out.write("\t\t\tasLogin.FimUserPasswd.focus();\r\n");
      out.write("\t\t}else{\t\t\r\n");
      out.write("\t\t\tasLogin.submit();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\t<!-- Include Message Box -->\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../inc/inc_message_box.jsp", out, false);
      out.write("\r\n");
      out.write("\t<!-- // Include Message Box -->\r\n");
      out.write("\t<form name=\"asLogin\" action=\"loginDao.jsp\" method=\"POST\">\r\n");
      out.write("\t\t<div id=\"wrap\" class=\"login\">\r\n");
      out.write("\t\t\t<div class=\"content_wrap\">\r\n");
      out.write("\t\t\t\t<div class=\"c_top\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"c_mid\">\r\n");
      out.write("\t\t\t\t\t<div class=\"rows\"><span class=\"title\"><img src=\"../asset/img/login/login_img_01.gif\" alt=\"USER ID\"></span>\r\n");
      out.write("\t\t\t\t\t\t<input name=\"FimUserID\" id=\"input_id\" type=\"text\" class=\"ui_input_login\" placeholder=\"ID\" ");
if (!Saveid.equals("")) out.print("value="+Saveid+"");
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t<label for=\"input_id\" class=\"invisible\">아이디 입력</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"rows\"><span class=\"title\"><img src=\"../asset/img/login/login_img_02.gif\" alt=\"PASSWORD\"></span>\r\n");
      out.write("\t\t\t\t\t\t<input name=\"FimUserPasswd\" id=\"input_pass\" type=\"password\" class=\"ui_input_login\" placeholder=\"Password\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { id_check();}\">\r\n");
      out.write("\t\t\t\t\t\t<label for=\"input_pass\" class=\"invisible\">비밀번호 입력</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"rows shared\">\r\n");
      out.write("\t\t\t\t\t\t<input id=\"shared_id\" name=\"SaveId\" type=\"checkbox\" value=\"checkbox\" ");
if (Saveid != "")out.print("checked");
      out.write("><label for=\"shared_id\">Remember my ID on this computer.</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"ui_btn_00 ui_login ui_shadow_00\" onclick=\"id_check();\">Login</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"c_bot\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
 
if (Saveid.equals(""))  {
	script += "<SCRIPT LANGUAGE=\"JavaScript\">";
	script +=  "asLogin.FimUserID.focus();" ;
	script += "</SCRIPT>";
	out.print(script);
}else{
	script += "<SCRIPT LANGUAGE=\"JavaScript\">";
	script +=  "asLogin.FimUserPasswd.focus();" ;
	script += "</SCRIPT>";
	out.print(script);
} 

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
