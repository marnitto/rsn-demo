package org.apache.jsp.riskv3.admin.classification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.admin.classification.classificationMgr;
import risk.admin.classification.clfBean;
import risk.util.DateUtil;
import risk.util.ParseRequest;
import java.util.List;
import risk.util.ConfigUtil;

public final class classification_005fprc_jsp extends org.apache.jasper.runtime.HttpJspBase
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


    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
    List cmList = null;
    String script_str = "";
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);
	String clf_name = pr.getString("clf_name","");
	String mode = pr.getString("mode","");
	String icSeqList = pr.getString("icSeqList","");
	
	

	if( mode.equals("add") ) {
		//out.println("itype = "+itype);
		//out.println("icode = "+icode);
		//out.println("clf_name = "+clf_name);
		
		if( itype > 0 ) {
			if( cm.InsertClf( itype, icode, clf_name, SS_M_NO ) ) {
				script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
							 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println("  ");
			}else {
				script_str = "parent.frm_menu.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println(" 입력 실패 ");
			}
		}
	}else if( mode.equals("del") ) {
		//out.println("icSeqList = "+icSeqList);
		if( cm.DelClf(icSeqList) ) {
			script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
			 			 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			out.println(" 삭제 실패 ");
		}
		
	}else if( mode.equals("flag")){
		String  iflag = pr.getString("iflag", "");
		if( cm.insertFlag(itype, icode, iflag) ){
			script_str = "alert('저장 되었습니다.');";
			script_str += "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';\n"
		 			   +  "parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';\n";
		}else{
			script_str = "parent.frm_menu.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';\n";
			out.println(" 입력 실패 ");
		}
	}else if( mode.equals("update") ) {
		String ic_seq = pr.getString("ic_seq");
		String ic_name = pr.getString("ic_name");
		String ic_regdate = pr.getString("ic_regdate");
		String ic_regtime = pr.getString("ic_regtime");
		
		cm.ModifyName(ic_seq, ic_name, ic_regdate, ic_regtime);
		script_str = "parent.opener.parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	parent.close();\n";
		
	}

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t");
      out.print(script_str);
      out.write("\r\n");
      out.write("</script>");
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
