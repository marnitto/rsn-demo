package org.apache.jsp.riskv3.statistics;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import javax.sound.sampled.AudioFormat.Encoding;
import risk.json.*;
import java.util.*;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.statistics.StatisticsMgr;

public final class statistics_005fexcel_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("application/vnd.ms-excel;charset=UTF-8");
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

String type = pr.getString("type");


StatisticsMgr stMgr = new StatisticsMgr();

response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
response.setHeader("Content-Description", "JSP Generated Data");



      out.write("    \r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<style>\r\n");
      out.write("td { font-size:12px; color:#333333; font-family:\"gulim\"; ; line-height: 18px}\r\n");
      out.write("   input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }\r\n");
      out.write(".t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("   .tCopy { font-family: \"Tahoma\"; font-size: 12px; color: #000000; font-weight: bold}\r\n");
      out.write("a:link { color: #333333; text-decoration: none; }\r\n");
      out.write("a:visited { text-decoration: none; color: #000000; }\r\n");
      out.write("a:hover { text-decoration: none; color: #FF9900; }\r\n");
      out.write("a:active { text-decoration: none; }\r\n");
      out.write("\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 0px;\r\n");
      out.write("\tmargin-top: 0px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("\tSCROLLBAR-face-color: #F2F2F2;\r\n");
      out.write("\tSCROLLBAR-shadow-color: #999999;\r\n");
      out.write("\tSCROLLBAR-highlight-color: #999999;\r\n");
      out.write("\tSCROLLBAR-3dlight-color: #FFFFFF;\r\n");
      out.write("\tSCROLLBAR-darkshadow-color: #FFFFFF;\r\n");
      out.write("\tSCROLLBAR-track-color: #F2F2F2;\r\n");
      out.write("\tSCROLLBAR-arrow-color: #333333;\r\n");
      out.write("     }\r\n");
      out.write(".menu_black {  font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #000000}\r\n");
      out.write(".textw { font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}\r\n");
      out.write("\r\n");
      out.write(".menu_blue {\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3D679C;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".menu_gray {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #4B4B4B\r\n");
      out.write("}\r\n");
      out.write(".menu_red {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #CC0000\r\n");
      out.write("}\r\n");
      out.write(".menu_blueOver {\r\n");
      out.write("\r\n");
      out.write("\tfont-family: Tahoma;\r\n");
      out.write("\tfont-size: 11px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3D679C;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".menu_blueTEXTover {\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3366CC;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".textwbig {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal\r\n");
      out.write("}\r\n");
      out.write(".textBbig {\r\n");
      out.write("\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal\r\n");
      out.write("}\r\n");
      out.write(".menu_grayline {\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #4B4B4B;\r\n");
      out.write("\ttext-decoration: underline;\r\n");
      out.write("}\r\n");
      out.write(".menu_grayS {\r\n");
      out.write("\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 11px; line-height: 16px; color: #4B4B4B\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" border=\"1\" >\r\n");
      out.write("\t\t\t");

			if( "1".equals(type) ){
				
				JSONArray list1 = stMgr.getWeeklyChannelVolumn(sDate, eDate); 
				
			
      out.write("\r\n");
      out.write("\t\t\t<table summary=\"주간 정보량 및 부정율\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
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
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">일자</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">언론+포탈</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">블로그</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">카페</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">커뮤니티</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">트위터</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">페이스북</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">대구광역시sns</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총 합계</th>\t\t\t\t\t\r\n");
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
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");

			}else if("4".equals(type)){
				JSONArray list4 = stMgr.getWeeklyInfoVolumn2(sDate, eDate);
				
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 대분류/소분류별 정보량 현황\" id=\"weekly_info2\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<caption>주간 대분류/소분류별 정보량 현황</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">상세구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보량</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"weekly_info_tb2\">\r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list4.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list4.length(); i++){
					obj = (JSONObject)list4.get(i);
					
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("type3"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg1")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt3")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("type31"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg2")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt31")));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
				}
			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"6\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
			}
			
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");

			}else if("5".equals(type)){
				JSONArray list5 = stMgr.getWeeklyInfoVolumn3(sDate, eDate);
				
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 실국/부서별 정보량 현황\" id=\"weekly_info2\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<caption>주간 실국/부서별 정보량 현황</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">상세구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보량</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"weekly_info_tb2\">\r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list5.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list5.length(); i++){
					obj = (JSONObject)list5.get(i);
					
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("type5"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg1")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt5")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("type51"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg2")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt51")));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
				}
			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"6\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
			}
			
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");

			}else if("2".equals(type)){
				JSONArray list2 = stMgr.getWeeklyInfoVolumn(sDate, eDate);
				
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 주요시정별 정보량 현황\" id=\"weekly_info\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<caption>주간 주요시정별 정보량 현황</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col >\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">총 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">상세구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">부정 정보량</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">정보량</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"weekly_info_tb\">\r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list2.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list2.length(); i++){
					obj = (JSONObject)list2.get(i);
					
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("type2"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg1")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt2")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.getString("type21"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("neg2")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt21")));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
				}
			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"6\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
			}
			
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");

			
			}else if("3".equals(type)){
				JSONArray list3 = stMgr.getWeeklyInfluencerTop10(sDate, eDate);
			
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t\t\t<table summary=\"주간 주요 영향력자 Top10\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<caption>주간 주요 영향력자 Top10</caption>\r\n");
      out.write("\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t<col style=\"width:120\">\r\n");
      out.write("\t\t\t\t<col style=\"width:120\">\r\n");
      out.write("\t\t\t\t<col style=\"width:400\">\r\n");
      out.write("\t\t\t\t<col style=\"width:120\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr style=\"background: #f3f3f3;\">\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">순위</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">출처</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">제목</th> \r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">확산수량</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody> \r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list3.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list3.length(); i++){
					obj = (JSONObject)list3.get(i);
					String url = obj.getString("id_url");
					url = URLEncoder.encode(url, "UTF-8");
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td align=\"center\">");
      out.print((i+1));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td align=\"center\">");
      out.print(obj.get("source"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td align=\"left\"><a href='http://hub.buzzms.co.kr?url=");
      out.print(url);
      out.write("' target=\"_blank\"> ");
      out.print(obj.get("id_title"));
      out.write(" </a></td>\r\n");
      out.write("\t\t\t\t<td align=\"right\">");
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
      out.write("\t\t\t");

			}else if("6".equals(type)){ 
				JSONObject obj1 = stMgr.getSentiVolume(sDate, eDate);
			
      out.write("\r\n");
      out.write("\t\t\t<table summary=\"성향별 정보량 현황\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
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
      out.write("\t\t\t\t<td style=\"background: #fff2cc;\">");
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
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t");
}else if("7".equals(type)){
			
      out.write("\r\n");
      out.write("\t\t\t<p style=\"text-align: left;\">실국별 온라인 관심도</p>\r\n");
      out.write("\t\t\t");
	
			JSONObject obj2 = stMgr.getOniineVolume(sDate, eDate);
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
			
      out.write("\t\t\r\n");
      out.write("\t\t\t<table summary=\"주간 실국/부서별 정보량 현황\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t");
if(x==0){
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<caption>○ 정보건수 : 뉴스 ");
      out.print(su.addComma(obj2.getString("news")));
      out.write("건 / 개인버즈 ");
      out.print(su.addComma(obj2.getString("indi")));
      out.write("건 / 공식SNS ");
      out.print(su.addComma(obj2.getString("sns")));
      out.write("건 / 댓글 ");
      out.print(su.addComma(obj2.getString("reply")));
      out.write("건</caption>\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");
}
      out.write("\r\n");
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
				
      out.write("\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" colspan=\"2\">구분</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\" >계</th>\r\n");
      out.write("\t\t\t\t\t");
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
      out.write("\t\t\t<tbody id=\"weekly_info_tb3\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t");
if(x==0){ 
      out.write("\r\n");
      out.write("\t\t\t\t<td rowspan=\"4\">정보구분</td>\r\n");
      out.write("\t\t\t\t<td>뉴스</td>\r\n");
      out.write("\t\t\t\t");
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
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t");
					
				for(int i=0 ; i<news.length ; i++){
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<td>");
      out.print(news[i] );
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
      out.print(indi[i] );
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
      out.print(sns[i] );
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
      out.print(reply[i] );
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
      out.write("\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t");
}else if("8".equals(type)){
				JSONArray list8 = stMgr.ReplyDataExcel(sDate, eDate);
				
			
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<table summary=\"댓글 상세정보\" id=\"weekly_info2\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<caption>댓글 상세정보</caption>\r\n");
      out.write("\t\t\t<colgroup>\t\t\t\t\r\n");
      out.write("\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t\t<col style=\"width:12%\">\r\n");
      out.write("\t\t\t</colgroup>\r\n");
      out.write("\t\t\t<thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">제목</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">URL</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">댓글수</th>\r\n");
      out.write("\t\t\t\t\t<th scope=\"col\">실국</th>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</thead>\r\n");
      out.write("\t\t\t<tbody id=\"weekly_info_tb2\">\r\n");
      out.write("\t\t\t");

			JSONObject obj = null;
			if(list8.length() > 0){
				obj = new JSONObject();
				for(int i=0; i < list8.length(); i++){
					obj = (JSONObject)list8.get(i);
					
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("title"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("url"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(su.addComma(obj.getString("cnt")));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(obj.get("name"));
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
				}
			}else{
			
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"25\">\r\n");
      out.write("\t\t\t<td align=\"center\" bgcolor=\"#FFFFFF\" colspan=\"4\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
	
			}
			}
			
      out.write("\r\n");
      out.write("\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
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
