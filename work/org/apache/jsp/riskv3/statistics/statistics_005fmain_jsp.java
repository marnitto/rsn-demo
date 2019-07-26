package org.apache.jsp.riskv3.statistics;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import javax.sound.sampled.AudioFormat.Encoding;
import risk.util.ConfigUtil;
import risk.json.*;
import java.util.*;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.statistics.StatisticsMgr;

public final class statistics_005fmain_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/statistics/../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

ParseRequest pr = new ParseRequest(request);
pr.printParams();

DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

String eDate = pr.getString("eDate",du.getCurrentDate());

String sDate = pr.getString("sDate",du.addDay_v2(eDate, -7));


StatisticsMgr stMgr = new StatisticsMgr();
JSONArray list1 = stMgr.getWeeklyChannelVolumn(sDate, eDate); 
//JSONArray list2 = stMgr.getWeeklyInfoVolumn(sDate, eDate);
JSONArray list3 = stMgr.getWeeklyInfluencerTop10(sDate, eDate);

//JSONArray list4 = stMgr.getWeeklyInfoVolumn2(sDate, eDate);
JSONArray list5 = stMgr.getWeeklyInfoVolumn3(sDate, eDate);

JSONObject obj1 = stMgr.getSentiVolume(sDate, eDate);

JSONObject obj2 = stMgr.getOniineVolume(sDate, eDate);



      out.write("    \r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>RIS-K</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/base.css\" />\r\n");
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
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/devel.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("$(function(){\r\n");
      out.write("\t$(\"#weekly_info_tb tr\").each(function(row){\r\n");
      out.write("\t\t$(\"#weekly_info\").rowspan2(row,3);\r\n");
      out.write("\t});\t\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#weekly_info_tb2 tr\").each(function(row){\r\n");
      out.write("\t\t$(\"#weekly_info2\").rowspan2(row,3);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#weekly_info_tb3 tr\").each(function(row){\r\n");
      out.write("\t\t$(\"#weekly_info3\").rowspan2(row,3);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("function getSearch(){\r\n");
      out.write("\t$(\"#frm\").attr(\"action\",\"statistics_main.jsp\");\r\n");
      out.write("\t$(\"#frm\").attr(\"target\",\"\");\r\n");
      out.write("\t$(\"#frm\").find(\"[name=sDate]\").val( $(\"#sDate\").val() );\r\n");
      out.write("\t$(\"#frm\").find(\"[name=eDate]\").val( $(\"#eDate\").val() );\r\n");
      out.write("\t$(\"#frm\").submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function excelDownload(type){\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#frm\").attr(\"action\",\"statistics_excel.jsp\");\r\n");
      out.write("\t$(\"#frm\").find(\"[name=type]\").val( type );\r\n");
      out.write("\t$(\"#frm\").find(\"[name=sDate]\").val( $(\"#sDate\").val() );\r\n");
      out.write("\t$(\"#frm\").find(\"[name=eDate]\").val( $(\"#eDate\").val() );\r\n");
      out.write("\t$(\"#frm\").attr(\"target\",\"ifr_empty\");\r\n");
      out.write("\t$(\"#frm\").submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getReplyCnt(){\r\n");
      out.write("\t$('#fr_content').css(\"cursor\",\"wait\");\r\n");
      out.write("\tvar param = \"sDate=\"+$(\"#sDate\").val()+\"&eDate=\"+$(\"#eDate\").val();\r\n");
      out.write("\t\r\n");
      out.write("\t$.ajax({      \r\n");
      out.write("        type:\"POST\",  \r\n");
      out.write("        url:\"statistics_reply.jsp\",\r\n");
      out.write("        data:param,\r\n");
      out.write("        dataType:\"text\",        \r\n");
      out.write("        success:function(data){        \t\r\n");
      out.write("        \tif(data.trim() == \"success\"){\r\n");
      out.write("        \t\t$('#fr_content').css('cursor','auto');\r\n");
      out.write("        \t\talert(\"업데이트하였습니다.\");\r\n");
      out.write("        \t\tlocation.reload();\r\n");
      out.write("        \t}\r\n");
      out.write("        }\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<form action=\"statistics_main.jsp\" id=\"frm\">\r\n");
      out.write("<input type=\"hidden\" name=\"sDate\" >\r\n");
      out.write("<input type=\"hidden\" name=\"eDate\" >\r\n");
      out.write("<input type=\"hidden\" name=\"type\" >\r\n");
      out.write("</form>\r\n");
      out.write("<div id=\"fr_content\">\r\n");
      out.write("\t\t<!-- 페이지 타이틀 -->\r\n");
      out.write("\t\t<h3><span class=\"icon\">-</span><img src=\"../../images/statistics/tit_0201.gif\" alt=\"\"></h3>\r\n");
      out.write("\t\t<!-- // 페이지 타이틀 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- Locator -->\r\n");
      out.write("\t\t<div class=\"ui_locator\">\r\n");
      out.write("\t\t\t<span class=\"home\"></span>HOME<span class=\"arrow\">></span>관리자<span class=\"arrow\">></span><strong>통계 관리</strong>\r\n");
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
      out.print(sDate);
      out.write("\" readonly><label for=\"sDate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t<span>~</span>\r\n");
      out.write("\t\t\t\t\t\t <input id=\"eDate\" type=\"text\" class=\"ui_datepicker_input input_date_last\" value=\"");
      out.print(eDate);
      out.write("\" readonly><label for=\"eDate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t<button class=\"ui_btn_02\" onclick=\"getSearch();\">검색</button>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- // 검색 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- 블로그 -->\r\n");
      out.write("\t\t<div class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline;margin-bottom: 10px;\"><span class=\"icon\">-</span>주간 정보량 및 부정율</h4>\t\t\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"excelDownload('1'); return false;\" style=\"float: right;margin-bottom: 2px;\"><span class=\"icon excel\">Excel Download</span></button>\t\r\n");
      out.write("\t\t\t<table summary=\"주간 정보량 및 부정율\">\r\n");
      out.write("\t\t\t<caption>주간 정보량 및 부정율</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<!-- <col style=\"width:10%\">  -->\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">일자</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">언론+포탈</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">블로그</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">카페</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">커뮤니티</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">트위터</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">페이스북</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">대구광역시sns</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총 합계</th>\r\n");
      out.write("\t\t\t\t\t<!-- <th scope=\"col\">부정율</th> -->\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list1.length() > 0){
				int count[] = new int[8];
				
				for(int i=0; i < list1.length(); i++){
					obj = (JSONObject)list1.get(i);
					
					count[0] += obj.getInt("news");
					count[1] += obj.getInt("blog");
					count[2] += obj.getInt("caffe");
					count[3] += obj.getInt("community");
					count[4] += obj.getInt("twitter");
					count[5] += obj.getInt("facebook");
					count[6] += obj.getInt("daegu");
					count[7] += obj.getInt("total");
					
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("md_date"));
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("news")) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("blog")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("caffe")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("community")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("twitter")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("facebook")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("daegu")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("total")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t</tr>\t\t\r\n");
      out.write("\t\t\t");

					
				}
				
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t<td>총 합계</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[0]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[1]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[2]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[3]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[4]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[5]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[6]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(count[7]+""));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

			float persent[] = new float[8];
			int a = 8;
			float b = 0;
			
			b = (a/3)*100;
			System.out.println( b );
			
			persent[0] = Math.round((Double.parseDouble(count[0]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[1] = Math.round((Double.parseDouble(count[1]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[2] = Math.round((Double.parseDouble(count[2]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[3] = Math.round((Double.parseDouble(count[3]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[4] = Math.round((Double.parseDouble(count[4]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[5] = Math.round((Double.parseDouble(count[5]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[6] = Math.round((Double.parseDouble(count[6]+"")/Double.parseDouble(count[7]+""))*100.00);
			persent[7] = Math.round((Double.parseDouble(count[7]+"")/Double.parseDouble(count[7]+""))*100.00);
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t<td>백분율</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[0]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[1]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[2]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[3]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[4]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[5]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[6]);
      out.write("%</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(persent[7]);
      out.write("%</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"10\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\t\r\n");
      out.write("\t\t\t");

			}
			 
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline;margin-bottom: 10px;\"><span class=\"icon\">-</span>성향별 정보량 현황</h4>\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"excelDownload('6'); return false;\" style=\"float: right;margin-bottom: 2px;\"><span class=\"icon excel\">Excel Download</span></button>\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"성향별 정보량 현황\" id=\"senti_info\">\r\n");
      out.write("\t\t\t<caption>성향별 정보량 현황</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">성향</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">백분율</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"weekly_info_tb2\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>긍정</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj1.getString("POS")) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(Math.round(obj1.getDouble("POS")/obj1.getDouble("TOTAL_CNT")*100.00 ) );
      out.write("%</td>\t\t\t\t\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>부정</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj1.getString("NEG")) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(Math.round(obj1.getDouble("NEG")/obj1.getDouble("TOTAL_CNT")*100.00 ) );
      out.write("%</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>중립</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj1.getString("NEU")) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(Math.round(obj1.getDouble("NEU")/obj1.getDouble("TOTAL_CNT")*100.00 ) );
      out.write("%</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\" style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t<td>총계</td>\r\n");
      out.write("\t\t\t\t<td >");
      out.print(su.addComma(obj1.getString("TOTAL_CNT")) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(Math.round(obj1.getDouble("TOTAL_CNT")/obj1.getDouble("TOTAL_CNT")*100.00 ) );
      out.write("%</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline;margin-bottom: 10px;\"><span class=\"icon\">-</span>실국별 온라인 관심도</h4>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"excelDownload('7'); return false;\" style=\"float: right;margin-bottom: 2px;\"><span class=\"icon excel\">Excel Download</span></button>\r\n");
      out.write("\t\t\t<button class=\"ui_btn_02 ui_shadow_01\" onclick=\"excelDownload('8'); return false;\" style=\"float: right;margin-bottom: 2px;margin-right: 5px\">\r\n");
      out.write("\t\t\t\t<span>댓글 상세 엑셀 다운로드</span>\r\n");
      out.write("\t\t\t</button>\r\n");
      out.write("\t\t\t<button class=\"ui_btn_02 ui_shadow_01\" onclick=\"getReplyCnt(); return false;\" style=\"float: right;margin-bottom: 2px;margin-right: 5px\">\r\n");
      out.write("\t\t\t\t<span>댓글 업데이트</span>\r\n");
      out.write("\t\t\t</button>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t<p style=\"margin-top: 8px;margin-bottom: 8px;\">○ 정보건수 : 뉴스 ");
      out.print(su.addComma(obj2.getString("news")));
      out.write("건 / 개인버즈 ");
      out.print(su.addComma(obj2.getString("indi")));
      out.write("건 / 공식SNS ");
      out.print(su.addComma(obj2.getString("sns")));
      out.write("건 / 댓글 ");
      out.print(su.addComma(obj2.getString("reply")));
      out.write("건</p>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t");

			JSONArray arr = obj2.getJSONArray("list");
			int size = 0;
			int[] length = new int [2];
			int[] strNum = new int [2];
			if(arr.length()%2==0){
				size = arr.length()/2;
				length[0] = size;
				length[1] = arr.length();
				strNum[0] = 0;
				strNum[1] = size;
			}else{
				size = (arr.length()/2);
				length[0] = size+1;
				length[1] = arr.length();
				strNum[0] = 0;
				strNum[1] = size+1;
			}
			for(int x=0 ; x<2 ; x++){
			
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 실국/부서별 정보량 현황\" id=\"weekly_info3\">\r\n");
      out.write("\t\t\t<caption>실국별 온라인 관심도</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t<col style=\"width:15%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");

				
				String[] name = new String[size];
				int[] news = new int[size];
				int[] indi = new int[size];
				int[] sns = new int[size];
				int[] reply = new int[size];
				
				if(arr.length()%2==0){
					name = new String[size];
					news = new int[size]; 
					indi = new int[size]; 
					sns = new int[size];
					reply = new int[size];
				}else{
					if(x==0){
						name = new String[size+1];
						news = new int[size+1]; 
						indi = new int[size+1];
						sns = new int[size+1];
						reply = new int[size+1];	
					}else{
						name = new String[size];
						news = new int[size]; 
						indi = new int[size]; 
						sns = new int[size];
						reply = new int[size];
					}
					
				}
				
				int index  = 0;
				
				for(int i=strNum[x] ; i<length[x] ; i++){
					JSONObject object = (JSONObject)arr.get(i);
					name[index] = object.getString("name");
					news[index] = object.getInt("news");
					indi[index] = object.getInt("indi");
					sns[index] = object.getInt("sns");
					reply[index] = object.getInt("reply");
					index++;
				
      out.write("\r\n");
      out.write("\t\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t");

				}
				
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" >계</th>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t");
					
					for(int i=0 ; i<name.length ; i++){
					
      out.write("\t\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">");
      out.print(name[i] );
      out.write("</th>\t\r\n");
      out.write("\t\t\t\t\t");
	
					}	
					
      out.write("\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t<td rowspan=\"4\">정보구분</td>\r\n");
      out.write("\t\t\t\t<td>뉴스</td>\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");

				if(x==0){
				
      out.write("\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj2.getString("news")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");
					
				for(int i=0 ; i<news.length ; i++){
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(news[i]+"") );
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t");
	
				}	
				
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t");

				if(x==0){
				
      out.write("\r\n");
      out.write("\t\t\t\t<td>개인</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj2.getString("indi")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");
					
				for(int i=0 ; i<indi.length ; i++){
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(indi[i]+"") );
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t");
	
				}	
				
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t");

				if(x==0){
				
      out.write("\r\n");
      out.write("\t\t\t\t<td>공식SNS</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj2.getString("sns")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");
					
				for(int i=0 ; i<sns.length ; i++){
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(sns[i]+"") );
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t");
	
				}	
				
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t");

				if(x==0){
				
      out.write("\r\n");
      out.write("\t\t\t\t<td>댓글</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj2.getString("reply")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");
					
				for(int i=0 ; i<reply.length ; i++){
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(reply[i]+"") );
      out.write("</td>\t\r\n");
      out.write("\t\t\t\t");
	
				}	
				
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"article ui_table_00 m_t_30\">\r\n");
      out.write("\t\t\t<h4 style=\"display:inline;margin-bottom: 10px;\"><span class=\"icon\">-</span>주간 주요 영향력자 Top10</h4>\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"excelDownload('3'); return false;\" style=\"float: right;margin-bottom: 2px;\"><span class=\"icon excel\">Excel Download</span></button>\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 주요 영향력자 Top10\">\r\n");
      out.write("\t\t\t<caption>주간 주요 영향력자 Top10</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">순위</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">출처</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">제목</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">확산수량</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody>\r\n");
      out.write("\t\t\t");

			if(list3.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list3.length(); i++){
					obj = (JSONObject)list3.get(i);
					String url = obj.getString("id_url");
					url = URLEncoder.encode(url, "UTF-8");
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print((i+1));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("source"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td><a href='http://hub.buzzms.co.kr?url=");
      out.print(url);
      out.write("' target=\"_blank\"> ");
      out.print(obj.get("id_title"));
      out.write(" </a></td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt")));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\t\r\n");
      out.write("\t\t\t");

			
				}
			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"4\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\t\r\n");
      out.write("\t\t\t");
	
			}
			
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\t\t\t\t\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<br/>\r\n");
      out.write("\t<br/>\r\n");
      out.write("\t<br/>\r\n");
      out.write("<iframe style=\"display: none\" width=\"0\" id=\"ifr_empty\"></iframe>\t\r\n");
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
