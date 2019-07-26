package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import risk.search.*;

public final class search_005fcomfirm_005fprc_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String m_seq = pr.getString("m_seq");
	String md_seq = pr.getString("md_seq");
	String mode = pr.getString("mode");
	
	//삭제 전용
	String md_seqs = pr.getString("md_seqs");
	
	MetaMgr mgr = new MetaMgr();
	
	if(mode.equals("insert")){
		mgr.insertComfirm(m_seq, md_seq);	
	
	
	

      out.write("\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("var obj = parent.document.getElementById(\"list");
      out.print(md_seq);
      out.write("\");\r\n");
      out.write("obj.color = '0000CC';\r\n");
      out.write("</script>\r\n");

	}else{
		String html = "";
		String result = mgr.deleteComfirm(m_seq, md_seqs);
		
		String[] arrMdSeq = md_seqs.split(",");
		for(int i =0; i < arrMdSeq.length; i++){
			if(result.indexOf(arrMdSeq[i]) >= 0){
				//html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = '977d46'; \n";
				html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = ''; \n";
			}else{
				html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = ''; \n";
			}
		}
		
		html += "var objChk = parent.document.getElementsByName('chkData'); for(var i =0; i < objChk.length; i++){ objChk[i].checked = false;} \n";
		System.out.println(html);	 	
 
      out.write("\r\n");
      out.write(" <script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write(" ");
      out.print(html);
      out.write("\r\n");
      out.write(" </script>\r\n");
      out.write(" ");
		
	}

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
