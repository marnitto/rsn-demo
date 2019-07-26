package org.apache.jsp.riskv3.admin.aekeyword;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.admin.aekeyword.ExceptionKeywordBean;
import risk.admin.aekeyword.ExceptionKeywordMgr;
import risk.util.ParseRequest;
import java.net.*;
import risk.util.ConfigUtil;

public final class behind_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/aekeyword/../../inc/sessioncheck.jsp");
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

	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "EK_VALUE ASC");	//정렬 순서
	
	String str_word = "";
	String str_weight = "";
	String str_writer = "";
	String str_date = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = "";
	
	if( order.equals("EK_VALUE ASC") ) {
		str_word = "EK_VALUE DESC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▲";
	}else if( order.equals("EK_VALUE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▼";
	}else if( order.equals("EK_FWRITER ASC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER DESC";
		str_date = "EK_DATE DESC";
		ico_writer = "▲";
	}else if( order.equals("EK_FWRITER DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_writer = "▼";
	}else if( order.equals("EK_DATE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE ASC";
		ico_date = "▼";
	}else if( order.equals("EK_DATE ASC") ) {
		str_word = "EK_VALUE ASC";
		str_weight = "EK_FWRITER DESC";		
		str_date = "EK_DATE DESC";
		ico_date = "▲";
	}
	
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean[] arrEKbean = null;
	arrEKbean = ekMgr.getKeywordList(searchWord, order);	

      out.write("\r\n");
      out.write("<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("<col width=\"5%\"><col width=\"35%\"><col width=\"15%\"><col width=\"15%\"><col width=\"15%\"><col width=\"15%\">\r\n");
      out.write("\t<tr>               \r\n");
      out.write("\t\t<th><input type=\"checkbox\" name=\"checkAll\" onclick=\"selectAll(this.checked);\"></th>\r\n");
      out.write("\t\t<th onclick=\"excuteOrder('");
      out.print(str_word);
      out.write("');\" style=\"cursor:pointer\">키워드</th>\r\n");
      out.write("\t\t<th onclick=\"excuteOrder('");
      out.print(str_writer);
      out.write("');\" style=\"cursor:pointer\">등록자</th>\r\n");
      out.write("\t\t<th onclick=\"excuteOrder('");
      out.print(str_date);
      out.write("');\" style=\"cursor:pointer\">등록일</th>\r\n");
      out.write("\t\t<th>수정자</th>\r\n");
      out.write("\t\t<th>수정일</th>\r\n");
      out.write("\t</tr>\r\n");

	if(arrEKbean != null && arrEKbean.length > 0){
		for(int i = 0; i < arrEKbean.length; i++){

      out.write(" \r\n");
      out.write("\t<tr>         \r\n");
      out.write("\t\t<td><input type=\"checkbox\" name=\"checkId\" value=\"");
      out.print(arrEKbean[i].getEkSeq());
      out.write("\"></td>\r\n");
      out.write("\t\t<td><p class=\"board_01_tit\" onclick=\"popupEdit('");
      out.print(arrEKbean[i].getEkSeq());
      out.write("','update');\" style=\"cursor:pointer\">");
      out.print(arrEKbean[i].getEkValue());
      out.write("</p></td>\r\n");
      out.write("\t\t<td>");
      out.print(arrEKbean[i].getEkFwriter());
      out.write("</td>\r\n");
      out.write("\t\t<td>");
      out.print(arrEKbean[i].getEkDate());
      out.write("</td>\r\n");
      out.write("\t\t<td>");
      out.print(arrEKbean[i].getEkLwriter());
      out.write("</td>\r\n");
      out.write("\t\t<td>");
      out.print(arrEKbean[i].getEkUpdate());
      out.write("</td>\r\n");
      out.write("\t</tr>\r\n");

		}
	}else{

      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=\"6\" style=\"height:40px;font-weight:bold\" align=\"center\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("</table>");
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
