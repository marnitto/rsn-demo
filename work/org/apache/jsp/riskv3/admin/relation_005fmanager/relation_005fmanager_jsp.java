package org.apache.jsp.riskv3.admin.relation_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.ArrayList;
import risk.admin.relation_manager.RelationManagerBean;
import risk.admin.relation_manager.RelationManagerMgr;
import risk.util.ParseRequest;
import java.net.*;
import risk.util.ConfigUtil;

public final class relation_005fmanager_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	RelationManagerMgr rmMgr = new RelationManagerMgr();
	
	String searchKey = pr.getString("searchKey", "");	
	String kor_bt = pr.getString("kor_bt", "");
	String eng_bt = pr.getString("eng_bt", "");
	String num_bt = pr.getString("num_bt", "");
	
	String kor_temp[] = new String[]{"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
	String eng_temp[] = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	String num_temp[] = new String[]{"0","1","2","3","4","5","6","7","8","9"};
	
	ArrayList keywordArrList = new ArrayList();
	keywordArrList = rmMgr.getRelationKeywordList(searchKey, kor_bt, eng_bt, num_bt);	
	

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
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t\t.btn_tk{\r\n");
      out.write("\t\tdisplay:inline-block; *display:inline; *zoom:1; height:19px;\r\n");
      out.write("\t\tborder:1px solid #DDDDDD; border-radius:2px; background:#eaeaea;\r\n");
      out.write("\t\tling-height:16px !important; outline:none;\r\n");
      out.write("\t\tcursor:pointer;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.btn_tk:hover, .btn_tk.active{border:1px solid #585858; background:#767676; color:#ffffff;}\r\n");
      out.write("\t\tiframe{display:none;}\r\n");
      out.write("</style>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t//단어 검색시\r\n");
      out.write("\tfunction Search(){\r\n");
      out.write("\t\t$(\"#searchKey\").val( $(\"[name=searchWord]\").val() );\r\n");
      out.write("\t\t$(\"#managerForm\").attr(\"action\", \"relation_manager.jsp\");\r\n");
      out.write("\t\t$(\"#managerForm\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//한글 검색시\r\n");
      out.write("\tfunction keywordSearch_kor(thisVal){\r\n");
      out.write("\t\t$(\"#kor_bt\").val(thisVal);\r\n");
      out.write("\t\t$(\"#eng_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#num_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#searchKey\").val(\"\");\r\n");
      out.write("\t\t$(\"#managerForm\").attr(\"action\", \"relation_manager.jsp\");\r\n");
      out.write("\t\t$(\"#managerForm\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//영어 검색시\r\n");
      out.write("\tfunction keywordSearch_eng(thisVal){\r\n");
      out.write("\t\t$(\"#kor_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#eng_bt\").val(thisVal);\r\n");
      out.write("\t\t$(\"#num_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#searchKey\").val(\"\");\r\n");
      out.write("\t\t$(\"#managerForm\").attr(\"action\", \"relation_manager.jsp\");\r\n");
      out.write("\t\t$(\"#managerForm\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//숫자 검색시\r\n");
      out.write("\tfunction keywordSearch_num(thisVal){\r\n");
      out.write("\t\t$(\"#kor_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#eng_bt\").val(\"\");\r\n");
      out.write("\t\t$(\"#num_bt\").val(thisVal);\r\n");
      out.write("\t\t$(\"#searchKey\").val(\"\");\r\n");
      out.write("\t\t$(\"#managerForm\").attr(\"action\", \"relation_manager.jsp\");\r\n");
      out.write("\t\t$(\"#managerForm\").submit();\r\n");
      out.write("\t}\t\r\n");
      out.write("\r\n");
      out.write("\t//버튼 팝업\r\n");
      out.write("\tfunction popupEdit(id,mode)\r\n");
      out.write("\t{\t\t\t\t\r\n");
      out.write("\t\tvar f = document.editFrm;\t\t\r\n");
      out.write("\t\tf.rk_name.value = id;\r\n");
      out.write("\t\tf.mode.value = mode;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar sel_length = $(\"#relation_select option:selected\").length;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\tif(mode == 'sum'){\r\n");
      out.write("\t\t\tif(sel_length == 0){\r\n");
      out.write("\t\t\t\talert(\"연관키워드가 선택되지 않았습니다.\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}else if(sel_length == 1){\r\n");
      out.write("\t\t\t\talert(\"연관키워드는 2개이상 선택되어야 합치기 가능 합니다.\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tvar str = \"\";\r\n");
      out.write("\t\t\t\t$(\"#relation_select option:selected\").each(function () {\r\n");
      out.write("\t\t\t\t\tif(str == \"\"){\r\n");
      out.write("\t\t\t\t\t\tstr += $(this).text();\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tstr += \",\" + $(this).text();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"[name=rk_name]\").val(str);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tstr = \"\";\r\n");
      out.write("\t\t\t\t$(\"#relation_select option:selected\").each(function () {\r\n");
      out.write("\t\t\t\t\tif(str == \"\"){\r\n");
      out.write("\t\t\t\t\t\tstr += $(this).val();\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tstr += \",\" + $(this).val();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t$(\"[name=rk_seq]\").val(str);\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tpopup.openByPost('editFrm', 'relation_manager_edit.jsp', 400, 170, false, false, false, 'trendPop');\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\telse if(mode=='insert'){\r\n");
      out.write("\t\t\tpopup.openByPost('editFrm','relation_manager_edit.jsp',400,170,false,false,false,'trendPop');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse if(mode=='update'){\r\n");
      out.write("\t\t\tif(sel_length == 0){\r\n");
      out.write("\t\t\t\talert(\"연관키워드가 선택되지 않았습니다.\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse if(sel_length == 1){\r\n");
      out.write("\t\t\t\t$(\"[name=rk_seq]\").val($(\"#relation_select option:selected\").val());\r\n");
      out.write("\t\t\t\t$(\"[name=rk_name]\").val($(\"#relation_select option:selected\").text());\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tpopup.openByPost('editFrm','relation_manager_edit.jsp',400,170,false,false,false,'trendPop');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse{\r\n");
      out.write("\t\t\t\talert(\"연관키워드가 복수 선택되었습니다.\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse if(mode=='delete'){\r\n");
      out.write("\t\t\tif(sel_length == 0 ){\r\n");
      out.write("\t\t\t\talert(\"연관키워드가 선택되지 않았습니다.\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse{\r\n");
      out.write("\t\t\t\tvar str = \"\";\r\n");
      out.write("\t\t\t\t$(\"#relation_select option:selected\").each(function () {\r\n");
      out.write("\t\t\t\t\tif(str == \"\"){\r\n");
      out.write("\t\t\t\t\t\tstr += $(this).text();\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tstr += \",\" + $(this).text();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"[name=rk_name]\").val(str);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tstr = \"\";\r\n");
      out.write("\t\t\t\t$(\"#relation_select option:selected\").each(function () {\r\n");
      out.write("\t\t\t\t\tif(str == \"\"){\r\n");
      out.write("\t\t\t\t\t\tstr += $(this).val();\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tstr += \",\" + $(this).val();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t$(\"[name=rk_seq]\").val(str);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tvar i = window.confirm(\"삭제하시겠습니까?\")\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(i){\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tf.action = 'relation_manager_prc.jsp';\r\n");
      out.write("\t\t\t\t\tf.target = 'iframePrc';\r\n");
      out.write("\t\t\t\t\tf.submit();\t\t\t\t\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("\t}\t\t\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"margin-left: 15px\">\r\n");
      out.write("<iframe name=\"iframePrc\" style=\"width:0;height:0;\"></iframe>\r\n");
      out.write("<form name=\"managerForm\" id=\"managerForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"kor_bt\" id=\"kor_bt\">\r\n");
      out.write("<input type=\"hidden\" name=\"eng_bt\" id=\"eng_bt\">\r\n");
      out.write("<input type=\"hidden\" name=\"num_bt\" id=\"num_bt\">\r\n");
      out.write("<input type=\"hidden\" name=\"searchKey\" id=\"searchKey\">\r\n");
      out.write("</form>\r\n");
      out.write("<table style=\"width:100%;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:67px;padding-top:20px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\" /><img src=\"../../../images/admin/relation_manager/tit_0803.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">연관키워드 관리</td>\r\n");
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
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t");
if(searchKey == "") { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input style=\"width:500px; vertical-align:middle\" class=\"textbox\" type=\"text\" name=\"searchWord\" onkeypress=\"Javascript:if (event.keyCode == 13) { Search(); }\" style=\"vertical-align:middle\"><img src=\"../../../images/admin/relation_manager/btn_search.gif\" style=\"vertical-align:middle;cursor:pointer\" onclick=\"Search();\"/></td>\r\n");
      out.write("\t\t\t\t\t\t");
 } else { 
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<input style=\"width:500px; vertical-align:middle\" class=\"textbox\" type=\"text\" name=\"searchWord\" value=\"");
      out.print(request.getParameter("searchKey") );
      out.write("\" onkeypress=\"Javascript:if (event.keyCode == 13) { Search(); }\" style=\"vertical-align:middle\"><img src=\"../../../images/admin/relation_manager/btn_search.gif\" style=\"vertical-align:middle;cursor:pointer\" onclick=\"Search();\"/></td>\r\n");
      out.write("\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>한글</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle; height:24px;\">\r\n");
      out.write("\t\t\t\t\t\t");
for(int i=0; i<kor_temp.length; i++){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" class=\"btn_tk ");
if (kor_bt.equals(kor_temp[i]))out.print("active"); 
      out.write("\" value=\"");
      out.print(kor_temp[i]);
      out.write("\" onclick=\"keywordSearch_kor('");
      out.print(kor_temp[i]);
      out.write("');\" style=\"width:23px; text-align:center; padding-left:4px;\"\t/>\r\n");
      out.write("\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>영어</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle; height:24px;\">\r\n");
      out.write("\t\t\t\t\t\t");
for(int i=0; i<eng_temp.length; i++) { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" class=\"btn_tk ");
if (eng_bt.equals(eng_temp[i]))out.print("active"); 
      out.write("\" value=\"");
      out.print(eng_temp[i]);
      out.write("\" onclick=\"keywordSearch_eng('");
      out.print(eng_temp[i]);
      out.write("');\" style=\"width:23px; padding-left:6px;\" />\r\n");
      out.write("\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>숫자</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle; height:24px;\">\r\n");
      out.write("\t\t\t\t\t\t");
for(int i=0; i<num_temp.length; i++) { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" class=\"btn_tk ");
if (num_bt.equals(num_temp[i]))out.print("active"); 
      out.write("\" value=\"");
      out.print(num_temp[i]);
      out.write("\" onclick=\"keywordSearch_num('");
      out.print(num_temp[i]);
      out.write("');\" style=\"width:23px;\" />\r\n");
      out.write("\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/relation_manager/btn_merge.gif\" style=\"cursor:pointer\" onclick=\"popupEdit('','sum');\"/></td>");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/relation_manager/btn_add.gif\" style=\"cursor:pointer\" onclick=\"popupEdit('','insert');\"/></td>");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/relation_manager/btn_modify.gif\" style=\"cursor:pointer\" onclick=\"popupEdit('','update');\"/></td>");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/relation_manager/btn_delete.gif\" style=\"cursor:pointer\" onclick=\"popupEdit('','delete');\"/></td>");
      out.write("\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<form name=\"editFrm\" id=\"editFrm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"mode\" id=\"mode\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"rk_seq\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"rk_name\">\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<select multiple name=\"relation_select\" id=\"relation_select\" style=\"display:block; width:100%; height:400px; padding:5px; border:1px solid #CFCFCF; border-top:2px solid #7CA5DD; outline:none; cursor:pointer;\">\r\n");
      out.write("\t\t\t\t");
 if(keywordArrList.size() > 0) {
					HashMap map = new HashMap();
					for(int i=0; i<keywordArrList.size(); i++){
						map = (HashMap)keywordArrList.get(i);
				
      out.write("\r\n");
      out.write("\t\t\t\t<option value=\"");
      out.print(map.get("rk_seq") );
      out.write('"');
      out.write('>');
      out.print(map.get("rk_name") );
      out.write("</option>\r\n");
      out.write("\t\t\t\t");

					}
				}
				
      out.write("\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t</table>\r\n");
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
