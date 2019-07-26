package org.apache.jsp.riskv3.admin.social;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.*;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.issue.IssueMgr;

public final class social_005fmanager_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/social/../../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	DateUtil du = new DateUtil();
	IssueMgr im = new IssueMgr();
	StringUtil su = new StringUtil();

	//검색날짜 설정 : 전일 대비 결과를 위해 조회날짜와 그 전일 날짜를 세팅한다.
	String cDate = du.getCurrentDate();
	String currentDate = pr.getString("sDate", du.getCurrentDate());
	du.setDate(currentDate);
	du.addDay(-1);
	String startDate = du.getDate();

	HashMap facebook = null;
	HashMap twitter = null;
	HashMap kakao = null;
	HashMap blog = null;

	blog = im.getSocialData(currentDate, "B");
	facebook = im.getSocialData(currentDate, "F");
	twitter = im.getSocialData(currentDate, "T");
	kakao = im.getSocialData(currentDate, "K");
	


      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-ui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/design.js\"></script>\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("\t<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"../asset/js/selectivizr-min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction socialMod(pflag, ptarget){\r\n");
      out.write("\t\tvar flag = pflag;\r\n");
      out.write("\t\tvar target = ptarget;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\".\"+flag+\"_time_\"+target).removeAttr('disabled');\r\n");
      out.write("\t\t$(\".\"+flag+\"_input_\"+target).removeAttr('readonly');\r\n");
      out.write("\t\t$(\"#\"+pflag+\"_modBtn_\"+ptarget).css('display','none');\r\n");
      out.write("\t\t$(\"#\"+pflag+\"_savBtn_\"+ptarget).css('display','');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction socialSave(pflag, ptarget){\r\n");
      out.write("\t\tvar date = $(\"[name=\"+pflag+\"_date_\"+ptarget+\"]\").val();\r\n");
      out.write("\t\tvar time = $(\".\"+pflag+\"_time_\"+ptarget+\" option:selected\").val();\r\n");
      out.write("\t\tvar data1 = $(\"[name=\"+pflag+\"_data1_\"+ptarget+\"]\").val();\r\n");
      out.write("\t\tvar data2 = $(\"[name=\"+pflag+\"_data2_\"+ptarget+\"]\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tdata1 = data1.replace(\",\" , \"\");\r\n");
      out.write("\t\tdata2 = data2.replace(\",\" , \"\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=type]\").val(pflag);\r\n");
      out.write("\t\t$(\"[name=date]\").val(date);\r\n");
      out.write("\t\t$(\"[name=time]\").val(time);\r\n");
      out.write("\t\t$(\"[name=data1]\").val(data1);\r\n");
      out.write("\t\t$(\"[name=data2]\").val(data2);\r\n");
      out.write("\t\t$(\"#frm\").attr(\"action\", \"social_manager_prc.jsp\");\r\n");
      out.write("\t\t$(\"#frm\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getSearch(){\r\n");
      out.write("\t\tvar sDate = $(\"#sDate\").val();\r\n");
      out.write("\t\tlocation.href=\"social_manager.jsp?sDate=\"+sDate;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction save(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar pflag = $(\"ul > li > a.active\").attr(\"id\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=type]\").val(pflag);\r\n");
      out.write("\t\t$(\"[name=date]\").val($(\"[name=\"+pflag+\"_insert_date]\").val());\r\n");
      out.write("\t\t$(\"[name=time]\").val($(\"[name=\"+pflag+\"_insert_time]\").val());\r\n");
      out.write("\t\t$(\"[name=data1]\").val($(\"[name=\"+pflag+\"_insert_data1]\").val());\r\n");
      out.write("\t\t$(\"[name=data2]\").val($(\"[name=\"+pflag+\"_insert_data2]\").val());\r\n");
      out.write("\t\t$(\"[name=data3]\").val($(\"[name=\"+pflag+\"_insert_data3]\").val());\r\n");
      out.write("\t\t$(\"#frm\").attr(\"action\", \"social_manager_prc.jsp\");\r\n");
      out.write("\t\t$(\"#frm\").submit();\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction del(el){\r\n");
      out.write("\t\tvar type = $(el).parent(\"div\").data(\"flag\");\r\n");
      out.write("\t\tvar date = $(el).parent(\"div\").find(\"table > tbody > tr > td:nth-child(1)\").text();\r\n");
      out.write("\r\n");
      out.write("\t\tvar param = \"op=delete&type=\" + type + \"&date=\" + date;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(date!=\"-\"){\r\n");
      out.write("\t\t\t$.ajax({      \r\n");
      out.write("\t\t        type:\"POST\",  \r\n");
      out.write("\t\t        url:\"social_manager_prc.jsp\",\r\n");
      out.write("\t\t        data:param,\r\n");
      out.write("\t\t        dataType:\"text\",\r\n");
      out.write("\t\t        success:function(){\r\n");
      out.write("\t\t        \tvar $tbody = $(el).parent(\"div\").find(\"table > tbody > tr\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(1)\").text(\"-\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(2)\").css(\"height\", \"20px\").text(\"-시\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(3)\").text(\"0\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(4)\").html(\"<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(5)\").text(\"0\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(6)\").html(\"<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(7)\").text(\"0\");\r\n");
      out.write("\t\t        \t$tbody.find(\"td:nth-child(8)\").html(\"<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>\");\r\n");
      out.write("\t\t        }\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(\"삭제할 데이터가 없습니다.\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form id=\"frm\" name=\"frm\" target=\"prcFrame\">\r\n");
      out.write("<input type=\"hidden\" name=\"insert_date\" value=\"");
      out.print(cDate);
      out.write("\" >\r\n");
      out.write("<input type=\"hidden\" name=\"type\" />\r\n");
      out.write("<input type=\"hidden\" name=\"date\" />\r\n");
      out.write("<input type=\"hidden\" name=\"time\" />\r\n");
      out.write("<input type=\"hidden\" name=\"data1\" />\r\n");
      out.write("<input type=\"hidden\" name=\"data2\" />\r\n");
      out.write("<input type=\"hidden\" name=\"data3\" />\r\n");
      out.write("</form>\r\n");
      out.write("\t<div id=\"fr_content\">\r\n");
      out.write("\t\t<!-- 페이지 타이틀 -->\r\n");
      out.write("\t\t<h3><span class=\"icon\">-</span><img src=\"");
      out.print(SS_URL);
      out.write("img/h3_admin_social_stat.gif\" alt=\"\"></h3>\r\n");
      out.write("\t\t<!-- // 페이지 타이틀 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- Locator -->\r\n");
      out.write("\t\t<div class=\"ui_locator\">\r\n");
      out.write("\t\t\t<span class=\"home\"></span>HOME<span class=\"arrow\">></span>관리자<span class=\"arrow\">></span><strong>소셜 통계 관리</strong>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- // Locator -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 검색 -->\r\n");
      out.write("\t\t<div class=\"article ui_searchbox_00\">\r\n");
      out.write("\t\t\t<div class=\"c_wrap\">\r\n");
      out.write("\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>검색기간</span>\r\n");
      out.write("\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t<input id=\"sDate\" type=\"text\" class=\"ui_datepicker_input input_date_first\" value=\"");
      out.print(currentDate);
      out.write("\" readonly><label for=\"sDate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t<button class=\"ui_btn_02\" onclick=\"getSearch();\">검색</button>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- // 검색 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 입력 -->\r\n");
      out.write("\t\t<div class=\"article m_t_30\" style=\"display:;\">\r\n");
      out.write("\t\t\t<div class=\"ui_tab_00\">\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"tab active\" id=\"B\">블로그</a>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tab_content ui_table_01\">\r\n");
      out.write("\t\t\t\t\t\t\t<table summary=\"블로그\">\r\n");
      out.write("\t\t\t\t\t\t\t<caption>블로그</caption>\r\n");
      out.write("\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>날짜</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=\"text\" name=\"B_insert_date\" value=\"");
      out.print(cDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>기준시간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select id=\"insert_time_03\" name=\"B_insert_time\" class=\"ui_select_02\" style=\"width:190px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
if(blog != null){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(blog.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select><label for=\"insert_time_03\" class=\"invisible\">기준시간 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>팬수</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_bl_00\" type=\"text\" class=\"ui_input_00\" name=\"B_insert_data1\" value=\"0\" ><label for=\"ui_input_bl_00\" class=\"invisible\">팔로잉 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>확산도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_bl_01\" type=\"text\" class=\"ui_input_00\" name=\"B_insert_data2\" value=\"0\" ><label for=\"ui_input_bl_01\" class=\"invisible\">팬수 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>반응도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_bl_02\" type=\"text\" class=\"ui_input_00\" name=\"B_insert_data3\" value=\"0\" ><label for=\"ui_input_bl_02\" class=\"invisible\">팬수 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"tab\" id=\"F\">페이스북</a>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tab_content ui_table_01\">\r\n");
      out.write("\t\t\t\t\t\t\t<table summary=\"페이스북\">\r\n");
      out.write("\t\t\t\t\t\t\t<caption>페이스북</caption>\r\n");
      out.write("\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>날짜</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=\"text\" name=\"F_insert_date\" value=\"");
      out.print(cDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>기준시간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select id=\"insert_time_00\" name=\"F_insert_time\" class=\"ui_select_02\" style=\"width:190px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
if(facebook != null){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(facebook.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select><label for=\"insert_time_00\" class=\"invisible\">기준시간 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>팬수</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_fb_00\" type=\"text\" class=\"ui_input_00\" name=\"F_insert_data1\" value=\"0\" ><label for=\"ui_input_fb_00\" class=\"invisible\">페이스북</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>확산도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_fb_01\" type=\"text\" class=\"ui_input_00\" name=\"F_insert_data2\" value=\"0\" ><label for=\"ui_input_fb_01\" class=\"invisible\">페이스북</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>반응도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_fb_02\" type=\"text\" class=\"ui_input_00\" name=\"F_insert_data3\" value=\"0\" ><label for=\"ui_input_fb_02\" class=\"invisible\">페이스북</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"tab\" id=\"T\">트위터</a>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tab_content ui_table_01\">\r\n");
      out.write("\t\t\t\t\t\t\t<table summary=\"트위터\">\r\n");
      out.write("\t\t\t\t\t\t\t<caption>트위터</caption>\r\n");
      out.write("\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>날짜</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=\"text\" name=\"T_insert_date\" value=\"");
      out.print(cDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>기준시간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select id=\"insert_time_01\" name=\"T_insert_time\" class=\"ui_select_02\" style=\"width:190px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
if(twitter != null){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(twitter.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select><label for=\"insert_time_01\" class=\"invisible\">기준시간 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>팬수</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_tw_00\" type=\"text\" class=\"ui_input_00\" name=\"T_insert_data1\" value=\"0\"><label for=\"ui_input_tw_00\" class=\"invisible\">트위터</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>확산도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_tw_01\" type=\"text\" class=\"ui_input_00\" name=\"T_insert_data2\" value=\"0\"><label for=\"ui_input_tw_01\" class=\"invisible\">트위터</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>반응도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_tw_02\" type=\"text\" class=\"ui_input_00\" name=\"T_insert_data3\" value=\"0\"><label for=\"ui_input_tw_02\" class=\"invisible\">트위터</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"tab\" id=\"K\">카카오톡</a>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tab_content ui_table_01\">\r\n");
      out.write("\t\t\t\t\t\t\t<table summary=\"카카오톡\">\r\n");
      out.write("\t\t\t\t\t\t\t<caption>카카오톡</caption>\r\n");
      out.write("\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col style=\"width:110px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>날짜</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=\"text\" name=\"K_insert_date\" value=\"");
      out.print(cDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>기준시간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select id=\"insert_time_02\" name=\"K_insert_time\" class=\"ui_select_02\" style=\"width:190px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
if(kakao != null){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(kakao.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select><label for=\"insert_time_02\" class=\"invisible\">기준시간 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>팬수</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_ka_00\" type=\"text\" class=\"ui_input_00\" name=\"K_insert_data1\" value=\"0\"><label for=\"ui_input_ka_00\" class=\"invisible\">팔로잉 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>확산도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_ka_01\" type=\"text\" class=\"ui_input_00\" name=\"K_insert_data2\" value=\"0\"><label for=\"ui_input_ka_01\" class=\"invisible\">팬수 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th>반응도</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input id=\"ui_input_ka_02\" type=\"text\" class=\"ui_input_00\" name=\"K_insert_data3\" value=\"0\"><label for=\"ui_input_ka_02\" class=\"invisible\">팬수 입력</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"ar p_r_10\">\r\n");
      out.write("\t\t\t\t<a href=\"javascript:save();\" class=\"ui_btn_04 ui_shadow_00\"><span class=\"icon confirm\">-</span>저장</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- // 입력 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- 블로그 -->\r\n");
      out.write("\t\t<div data-flag=\"B\" class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline\"><span class=\"icon\">-</span>블로그</h4>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"ui_btn_04 ui_shadow_00\" onclick=\"del(this);\" style=\"float:right\"><span class=\"icon confirm\">-</span>삭제</a>\r\n");
      out.write("\t\t\t<table summary=\"블로그\">\r\n");
      out.write("\t\t\t<caption>블로그</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<!-- <col style=\"width:10%\"> -->\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">날짜</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">기준시간</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">팬수</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">확산도</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">반응도</th>\r\n");
      out.write("\t\t\t\t\t<!-- <th scope=\"col\">처리</th> -->\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			if(blog != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (blog.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = blog.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = blog.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = blog.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = blog.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = blog.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = blog.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = blog.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = blog.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(blog.get("DATE"));
      out.write("<input type=\"hidden\" name=\"B_date_");
      out.print(blog.get("TIME"));
      out.write("\" value=\"");
      out.print(blog.get("DATE"));
      out.write("\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<select id=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"ui_select_02 B_time_");
      out.print(blog.get("TIME"));
      out.write("\" style=\"width:70px\" disabled=\"disabled\">\r\n");
      out.write("\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(blog.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select><label for=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"invisible\">기준시간</label><span>");
      out.print(blog.get("TIME"));
      out.write("시</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"B_data1_");
      out.print(blog.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 B_input_");
      out.print(blog.get("TIME"));
      out.write("\" value=\"");
      out.print(blog.get("SM_CNT1"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">팬수</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr1[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr1[1]);
      out.write('"');
      out.write('>');
      out.print(blog.get("DIF_SM_CNT1"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"B_data2_");
      out.print(blog.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 B_input_");
      out.print(blog.get("TIME"));
      out.write("\" value=\"");
      out.print(blog.get("SM_CNT2"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">확산도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr2[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr2[1]);
      out.write('"');
      out.write('>');
      out.print(blog.get("DIF_SM_CNT2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"B_data2_");
      out.print(blog.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 B_input_");
      out.print(blog.get("TIME"));
      out.write("\" value=\"");
      out.print(blog.get("SM_CNT3"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">반응도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr3[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr3[1]);
      out.write('"');
      out.write('>');
      out.print(blog.get("DIF_SM_CNT3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t");
	}
		}else{
      out.write("\r\n");
      out.write("\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"8\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
		
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- 블로그 -->\t\t\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 페이스북 -->\r\n");
      out.write("\t\t<div data-flag=\"F\" class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline\"><span class=\"icon\">-</span>페이스북</h4>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"ui_btn_04 ui_shadow_00\" onclick=\"del(this);\" style=\"float:right\"><span class=\"icon confirm\">-</span>삭제</a>\r\n");
      out.write("\t\t\t<table summary=\"페이스북\">\r\n");
      out.write("\t\t\t<caption>페이스북</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<!-- <col style=\"width:10%\"> -->\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">날짜</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">기준시간</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">팬수</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">확산도</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">반응도</th>\r\n");
      out.write("\t\t\t\t\t<!-- <th scope=\"col\">처리</th> -->\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			if(facebook != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (facebook.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = facebook.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = facebook.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = facebook.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = facebook.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = facebook.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = facebook.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = facebook.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = facebook.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(facebook.get("DATE"));
      out.write("<input type=\"hidden\" name=\"F_date_");
      out.print(facebook.get("TIME"));
      out.write("\" value=\"");
      out.print(facebook.get("DATE"));
      out.write("\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<select id=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"ui_select_02 F_time_");
      out.print(facebook.get("TIME"));
      out.write("\" style=\"width:70px\" disabled=\"disabled\">\r\n");
      out.write("\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(facebook.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select><label for=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"invisible\">기준시간</label><span>");
      out.print(facebook.get("TIME"));
      out.write("시</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"F_data1_");
      out.print(facebook.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 F_input_");
      out.print(facebook.get("TIME"));
      out.write("\" value=\"");
      out.print(facebook.get("SM_CNT1"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">팬수</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr1[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr1[1]);
      out.write('"');
      out.write('>');
      out.print(facebook.get("DIF_SM_CNT1"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"F_data2_");
      out.print(facebook.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 F_input_");
      out.print(facebook.get("TIME"));
      out.write("\" value=\"");
      out.print(facebook.get("SM_CNT2"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">확산도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr2[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr2[1]);
      out.write('"');
      out.write('>');
      out.print(facebook.get("DIF_SM_CNT2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"F_data2_");
      out.print(facebook.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 F_input_");
      out.print(facebook.get("TIME"));
      out.write("\" value=\"");
      out.print(facebook.get("SM_CNT3"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">반응도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr3[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr3[1]);
      out.write('"');
      out.write('>');
      out.print(facebook.get("DIF_SM_CNT3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t");
	}
		}else{
      out.write("\r\n");
      out.write("\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"8\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
		
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- // 기업 페이스북 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 트위터 -->\r\n");
      out.write("\t\t<div data-flag=\"T\" class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline\"><span class=\"icon\">-</span>트위터</h4>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"ui_btn_04 ui_shadow_00\" onclick=\"del(this);\" style=\"float:right\"><span class=\"icon confirm\">-</span>삭제</a>\r\n");
      out.write("\t\t\t<table summary=\"트위터\">\r\n");
      out.write("\t\t\t<caption>트위터</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<!-- <col style=\"width:10%\"> -->\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">날짜</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">기준시간</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">팬수</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">확산도</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">반응도</th>\r\n");
      out.write("\t\t\t\t\t<!-- <th scope=\"col\">처리</th> -->\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			if(twitter != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (twitter.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = twitter.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = twitter.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = twitter.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = twitter.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = twitter.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = twitter.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = twitter.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = twitter.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(twitter.get("DATE"));
      out.write("<input type=\"hidden\" name=\"T_date_");
      out.print(twitter.get("TIME"));
      out.write("\" value=\"");
      out.print(twitter.get("DATE"));
      out.write("\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<select id=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"ui_select_02 T_time_");
      out.print(twitter.get("TIME"));
      out.write("\" style=\"width:70px\" disabled=\"disabled\">\r\n");
      out.write("\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(twitter.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select><label for=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"invisible\">기준시간</label><span>");
      out.print(twitter.get("TIME"));
      out.write("시</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"T_data1_");
      out.print(twitter.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 T_input_");
      out.print(twitter.get("TIME"));
      out.write("\" value=\"");
      out.print(twitter.get("SM_CNT1"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">팬수</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr1[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr1[1]);
      out.write('"');
      out.write('>');
      out.print(twitter.get("DIF_SM_CNT1"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"T_data2_");
      out.print(twitter.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 T_input_");
      out.print(twitter.get("TIME"));
      out.write("\" value=\"");
      out.print(twitter.get("SM_CNT2"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">확산도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr2[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr2[1]);
      out.write('"');
      out.write('>');
      out.print(twitter.get("DIF_SM_CNT2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"T_data2_");
      out.print(twitter.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 T_input_");
      out.print(twitter.get("TIME"));
      out.write("\" value=\"");
      out.print(twitter.get("SM_CNT3"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">반응도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr3[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr3[1]);
      out.write('"');
      out.write('>');
      out.print(twitter.get("DIF_SM_CNT3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t");
	}
		}else{
      out.write("\r\n");
      out.write("\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"8\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
		
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- 트위터 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 카카오 -->\r\n");
      out.write("\t\t<div data-flag=\"K\" class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline\"><span class=\"icon\">-</span>카카오톡</h4>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"ui_btn_04 ui_shadow_00\" onclick=\"del(this);\" style=\"float:right\"><span class=\"icon confirm\">-</span>삭제</a>\r\n");
      out.write("\t\t\t<table summary=\"카카오톡\">\r\n");
      out.write("\t\t\t<caption>카카오톡</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<!-- <col style=\"width:10%\"> -->\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">날짜</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">기준시간</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">팬수</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">확산도</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">반응도</th>\r\n");
      out.write("\t\t\t\t\t<!-- <th scope=\"col\">처리</th> -->\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			if(kakao != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (kakao.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = kakao.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = kakao.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = kakao.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = kakao.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = kakao.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = kakao.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = kakao.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = kakao.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print(kakao.get("DATE"));
      out.write("<input type=\"hidden\" name=\"K_date_");
      out.print(kakao.get("TIME"));
      out.write("\" value=\"");
      out.print(kakao.get("DATE"));
      out.write("\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<select id=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"ui_select_02 K_time_");
      out.print(kakao.get("TIME"));
      out.write("\" style=\"width:70px\" disabled=\"disabled\">\r\n");
      out.write("\t\t\t\t\t\t\t");
for(int t=1; t < 25; t++) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"");
      out.print(t);
      out.write('"');
      out.write(' ');
if(kakao.get("TIME").toString().equals(t+"")){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.write(' ');
      out.print(t);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select><label for=\"input_select_time_00_0");
      out.print(i);
      out.write("\" class=\"invisible\">기준시간</label><span>");
      out.print(kakao.get("TIME"));
      out.write("시</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"K_data1_");
      out.print(kakao.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 K_input_");
      out.print(kakao.get("TIME"));
      out.write("\" value=\"");
      out.print(kakao.get("SM_CNT1"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">팬수</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr1[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr1[1]);
      out.write('"');
      out.write('>');
      out.print(kakao.get("DIF_SM_CNT1"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"K_data2_");
      out.print(kakao.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 K_input_");
      out.print(kakao.get("TIME"));
      out.write("\" value=\"");
      out.print(kakao.get("SM_CNT2"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">확산도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr2[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr2[1]);
      out.write('"');
      out.write('>');
      out.print(kakao.get("DIF_SM_CNT2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"K_data2_");
      out.print(kakao.get("TIME"));
      out.write("\" id=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" type=\"text\" class=\"ui_input_00 K_input_");
      out.print(kakao.get("TIME"));
      out.write("\" value=\"");
      out.print(kakao.get("SM_CNT3"));
      out.write("\" readonly><label for=\"ui_input_0");
      out.print(i+1);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"invisible\">반응도</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"");
      out.print(classStr3[0]);
      out.write("\"> </span><span class=\"");
      out.print(classStr3[1]);
      out.write('"');
      out.write('>');
      out.print(kakao.get("DIF_SM_CNT3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t");
	}
		}else{
      out.write("\r\n");
      out.write("\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"8\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
		
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- 카카오 -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("<iframe id=\"prcFrame\" name=\"prcFrame\" width=\"0\" height=\"0\"></iframe>\t\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
