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

public final class aekeyword_005fmanager_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	String searchKey = pr.getString("searchKey");			//검색어
	String order = pr.getString("order", "EK_KEYWORD ASC");	//정렬 순서
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>RIS-K</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(SS_URL);
      out.write("/css/common.css\" type=\"text/css\">\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t$(document).ready(loadList);\r\n");
      out.write("\t\r\n");
      out.write("\t//첨 로드시~\r\n");
      out.write("\tfunction loadList()\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\tajax.post('behind_list.jsp?subCodeName=issueCode','managerForm','behindList');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//단어 검색시\r\n");
      out.write("\tfunction Search()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.managerForm;\r\n");
      out.write("\t\t//AJAX 사용시 한글처리\r\n");
      out.write("\t\t//f.searchKey.value = encodeURI(f.searchWord.value);\r\n");
      out.write("\t\t//앞뒤 공백 제거\r\n");
      out.write("\t\tf.searchWord.value = f.searchWord.value.replace(/^\\s*/,'');\r\n");
      out.write("\t\tf.searchWord.value = f.searchWord.value.replace(/\\s*$/,'');\t\t\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tloadList();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//정렬시\r\n");
      out.write("\tfunction excuteOrder(order)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.managerForm;\t\t\r\n");
      out.write("\t\tf.order.value = order;\t\t\r\n");
      out.write("\t\tSearch(); \r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//성향단어 에디터팝\r\n");
      out.write("\tfunction popupEdit(id,mode)\r\n");
      out.write("\t{\t\t\t\t\r\n");
      out.write("\t\tvar f = document.managerForm;\t\t\r\n");
      out.write("\t\tf.ekSeq.value = id;\r\n");
      out.write("\t\tf.mode.value = mode;\r\n");
      out.write("\t\tif(mode=='update')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tpopup.openByPost('managerForm','aekeyword_edit.jsp',680,700,false,false,false,'trendPop');\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tpopup.openByPost('managerForm','aekeyword_add.jsp',400,155,false,false,false,'trendPop');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//체크 박스 전체 선택시\r\n");
      out.write("\tfunction selectAll(result)\r\n");
      out.write("\t{\t\t\t\r\n");
      out.write("\t\tvar f = document.managerForm;\r\n");
      out.write("\t\tif( f.checkId ) {\r\n");
      out.write("\t\t\tif(f.checkId.length){\r\n");
      out.write("\t\t\t\tfor( i=0; i< f.checkId.length; i++ )\r\n");
      out.write("\t\t   \t\t{\r\n");
      out.write("\t\t   \t\t\t f.checkId[i].checked = result;\r\n");
      out.write("\t\t   \t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tf.checkId.checked = result;\r\n");
      out.write("\t\t\t}\t \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tf.checkAll.checked = result;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//체크된  ID\r\n");
      out.write("\tfunction checkId()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.managerForm;\r\n");
      out.write("\t\tf.checkIds.value = '';\r\n");
      out.write("\t\tif( f.checkId ) {\r\n");
      out.write("\t\t\tif(f.checkId.length){\t\t\t\t\r\n");
      out.write("\t\t\t\tfor( i=0; i< f.checkId.length; i++ )\r\n");
      out.write("\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\tif(f.checkId[i].checked==true)\r\n");
      out.write("\t\t\t   \t\t{\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t   \t\tf.checkIds.value == '' ? f.checkIds.value = f.checkId[i].value : f.checkIds.value += ','+ f.checkId[i].value;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t   \t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tif(f.checkId.checked==true)\r\n");
      out.write("\t\t   \t\t{\t\r\n");
      out.write("\t\t\t\t\tf.checkIds.value = f.checkId.value; \r\n");
      out.write("\t\t   \t\t}\r\n");
      out.write("\t\t\t}\t \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//alert(f.checkIds.value);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//단어 삭제\r\n");
      out.write("\tfunction deleteWord()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.managerForm;\r\n");
      out.write("\r\n");
      out.write("\t\tcheckId();\t\t\r\n");
      out.write("\t\tif(f.checkIds.value==''){alert('선택된 리스트가 없습니다.'); return;}\t\t\r\n");
      out.write("\t\tf.mode.value = 'delete';\r\n");
      out.write("\t\tf.target='processFrm';\r\n");
      out.write("\t\tf.action='aekeyword_prc.jsp';\r\n");
      out.write("\t\tf.submit();\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"managerForm\" id=\"managerForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" id=\"mode\">\r\n");
      out.write("<input type=\"hidden\" name=\"ekSeq\" id=\"ekSeq\">\r\n");
      out.write("<input type=\"hidden\" name=\"checkIds\" id =\"checkIds\">\r\n");
      out.write("<input type=\"hidden\" name=\"order\" id=\"order\">\r\n");
      out.write("<input type=\"hidden\" name=\"searchKey\" id=\"searchKey\">\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/aekeyword/tit_icon.gif\" /><img src=\"../../../images/admin/aekeyword/tit_0504.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">전체제외키워드관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"search_box\">\r\n");
      out.write("\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>검색 단어</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle\"><input style=\"width:500px;vertical-align:middle\" class=\"textbox\" type=\"text\" name=\"searchWord\" onkeypress=\"Javascript:if (event.keyCode == 13) { Search(); }\" style=\"vertical-align:middle\"><img src=\"../../../images/admin/aekeyword/btn_search.gif\" style=\"vertical-align:middle;cursor:pointer\" onclick=\"Search();\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/aekeyword/btn_allselect.gif\" style=\"cursor:pointer\" onclick=\"selectAll(!document.managerForm.checkAll.checked);\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/aekeyword/btn_add2.gif\" style=\"cursor:pointer\" onclick=\"popupEdit('','insert');\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/aekeyword/btn_del.gif\" style=\"cursor:pointer\" onclick=\"deleteWord();\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"behindList\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("<iframe name=\"processFrm\" height=\"0\" border=\"0\" style=\"display: none;\"></iframe>\r\n");
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
