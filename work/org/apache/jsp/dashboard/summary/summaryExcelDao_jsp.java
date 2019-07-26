package org.apache.jsp.dashboard.summary;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.dashboard.summary.SummaryMgr;
import risk.json.JSONObject;
import risk.json.JSONArray;
import risk.dashboard.mrt.MrtMgr;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class summaryExcelDao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/dashboard/summary/../../riskv3/inc/sessioncheck.jsp");
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

SummaryMgr sMgr = new SummaryMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String portal_date = pr.getString("portal_date", "");
String title = pr.getString("title");
String docid = pr.getString("docid","");
String pos_cnt = pr.getString("pos_cnt","");
String neg_cnt = pr.getString("neg_cnt","");
String neu_cnt = pr.getString("neu_cnt","");
String tab = pr.getString("tab", "0");
String tier = pr.getString("tier", "");
JSONArray arr = new JSONArray();

if("1".equals(section)){
	arr = sMgr.getOnlineTrendData(scope, keyword, sDate, eDate,tier);

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table style=\"width:300px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 2;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>날짜</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>대구광역시</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("date"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-2"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");

}else if("2".equals(section)){
	arr = sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "02",tier);

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table style=\"width:400px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 4;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>매체</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>긍정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>부정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>중립</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	int pos = 0;
	    	int neg = 0;
	    	int neu = 0;
	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				pos += Integer.parseInt((String)obj.get("column-2"));
				neg += Integer.parseInt((String)obj.get("column-3"));
				neu += Integer.parseInt((String)obj.get("column-4"));
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("category"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-4"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    <tr>\r\n");
      out.write("\t\t\t<td><span>총 정보량</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(pos);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neg);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neu);
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");

}else if("3".equals(section)){
	arr = sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "03",tier);

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table style=\"width:400px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 4;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>매체</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>긍정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>부정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>중립</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	int pos = 0;
	    	int neg = 0;
	    	int neu = 0;
	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				pos += Integer.parseInt((String)obj.get("column-2"));
				neg += Integer.parseInt((String)obj.get("column-3"));
				neu += Integer.parseInt((String)obj.get("column-4"));
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("category"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-3"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("column-4"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    <tr>\r\n");
      out.write("\t\t\t<td><span>총 정보량</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(pos);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neg);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neu);
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");

}else if("4".equals(section)){
	JSONObject obj2 = new  JSONObject();
	obj2 = sMgr.getPotalTopList(scope, keyword, sDate, eDate, tab, 1, true);
	arr = obj2.getJSONArray("list"); 

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table style=\"width:1200px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:200px\">\r\n");
      out.write("\t\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 5;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>포탈구분</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>영역</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>URL</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>최초노출일시</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("sitename"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("boardname"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("title"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("url"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("date"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");
}else if("5_1".equals(section)){ 
	//arr = sMgr.getSummaryPortalList_excel(portal_date, scope, keyword, tab);
	arr = sMgr.getSummaryPortalList3_Excel(portal_date, keyword,SS_M_ID);

      out.write("\r\n");
      out.write("<table style=\"width:1240px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("<colgroup>\r\n");
      out.write("<col style=\"width:120px\">\r\n");
      out.write("<col style=\"width:220px\">\r\n");
      out.write("<col style=\"width:400px\">\r\n");
      out.write("<col style=\"width:400px\">\r\n");
      out.write("<col style=\"width:100px\">\r\n");
      out.write("</colgroup>\r\n");
      out.write("<thead>\r\n");

	int colspan = 5;

      out.write("\r\n");
      out.write("<tr style=\"height:40px\">\r\n");
      out.write("\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("</tr>\r\n");
      out.write("<tr style=\"height:30px\">\r\n");
      out.write("\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("</tr>\r\n");
      out.write("<tr>\r\n");
      out.write("<th scope=\"col\"><span>포탈구분</span></th>\r\n");
      out.write("<th scope=\"col\"><span>댓글</span></th>\r\n");
      out.write("<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("<th scope=\"col\"><span>URL</span></th>\r\n");
      out.write("<th scope=\"col\"><span>일자</span></th>\r\n");
      out.write("</tr>\r\n");
      out.write("</thead>\r\n");
      out.write("<tbody>\r\n");

if(arr.length() > 0){
	for(int i=0; i < arr.length(); i++){
		JSONObject obj = new  JSONObject();
		obj = (JSONObject)arr.get(i);

      out.write("\t\t\r\n");
      out.write("<tr>\r\n");
      out.write("\t<td>");
      out.print(obj.get("MD_SITE_NAME"));
      out.write("</td>\r\n");
      out.write("\t<td>");
      out.print(obj.get("DOC_CNT"));
      out.write("(긍정:");
      out.print(obj.get("POS_CNT"));
      out.write("/부정:");
      out.print(obj.get("NEG_CNT"));
      out.write("/중립:");
      out.print(obj.get("NEU_CNT"));
      out.write(")</td>\r\n");
      out.write("\t<td>");
      out.print(obj.get("MD_TITLE"));
      out.write("</td>\r\n");
      out.write("\t<td>");
      out.print(obj.get("EX_URL"));
      out.write("</td>\r\n");
      out.write("\t<td>");
      out.print(obj.get("MD_DATE"));
      out.write("</td>\r\n");
      out.write("</tr>\t\r\n");
		
	}
}

      out.write("\r\n");
      out.write("</tbody>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
}else if("5_2".equals(section)){ 
      out.write("\r\n");
      out.write("<table style=\"width:400px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 4;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>총정보량</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>긍정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>부정</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>중립</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(Integer.parseInt(pos_cnt)+Integer.parseInt(neg_cnt)+Integer.parseInt(neu_cnt) );
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(pos_cnt);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neg_cnt);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(neu_cnt);
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");
      out.write("\r\n");
}else if("5_3".equals(section)){ 
	arr = sMgr.getRelationKeyword(docid, eDate, SS_M_ID);


      out.write("\r\n");
      out.write("<table style=\"width:400px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 2;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>연관 클라우드</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>총 건수</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    ");
if(arr.length() > 0){
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
	    		obj = (JSONObject)arr.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("word_nm") );
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("cnt") );
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
		}else{
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"2\">검색 된 데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
	    
      out.write("\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");

}else if("6".equals(section)){
	arr = sMgr.getSocialIssueList_excel(scope, keyword, sDate, eDate);

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table style=\"width:1100px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = 5;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>출처</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>URL</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>확산건수</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>수집일</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("sitename"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("title"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("url"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("samecnt"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("date").toString());
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");

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
