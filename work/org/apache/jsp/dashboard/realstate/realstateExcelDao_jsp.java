package org.apache.jsp.dashboard.realstate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import risk.json.JSONArray;
import risk.dashboard.realstate.RealstateMgr;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class realstateExcelDao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/dashboard/realstate/../../riskv3/inc/sessioncheck.jsp");
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

RealstateMgr rMgr = new RealstateMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String title = pr.getString("title");

if("1".equals(section)){
	String type51 = pr.getString("type51");
	String[] type51_arr = type51.split(",");
	JSONArray rList = rMgr.getOnlineTrend(sDate,eDate,scope,keyword,type51);	

      out.write("\r\n");
      out.write("<table style=\"width:300px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("\t    <caption></caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t\t");

				int colspan = type51_arr.length;
			
      out.write("\r\n");
      out.write("\t\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan+1);
      out.write('"');
      out.write('>');
      out.print(title);
      out.write("</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan+1);
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
      out.write("\t\t\t");

			for(int i=0 ; i<type51_arr.length ; i++){
			
      out.write("\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>");
      out.print(type51_arr[i].split("@@")[0] );
      out.write("</span></th>\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	for(int i=0; i<rList.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)rList.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("full_date"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t");

			for(int x=0 ; x<type51_arr.length ; x++){
			
      out.write("\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.getString("CNT_"+(x+1)));
      out.write("</span></td>\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");
      out.write("\r\n");
}else if("2".equals(section)){
	String code = pr.getString("code");
	JSONObject object = rMgr.getChannelInfo(sDate,eDate,scope,keyword,code);
	
	JSONObject 	obj = object.getJSONObject("pie");
	JSONArray 	arr = object.getJSONArray("graph");

      out.write("\r\n");
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

	    	
	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj1 = new  JSONObject();
				obj1 = (JSONObject)arr.get(i);
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj1.get("category"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj1.get("column-1"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj1.get("column-2"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj1.get("column-3"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

	    	}
	    
      out.write("\r\n");
      out.write("\t    <tr>\r\n");
      out.write("\t\t\t<td><span>총 정보량</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("pos"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("neg"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("neu"));
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t    </tbody>\r\n");
      out.write("\t</table>\r\n");
}else if("3".equals(section)){
	String senti = pr.getString("senti");	
	String code = pr.getString("code");
	JSONObject object = rMgr.getRelInfo(sDate,eDate,scope,keyword,senti,code,1,10,"true");
	JSONArray arr = object.getJSONArray("data");

      out.write("\t\r\n");
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

				int colspan = 6;
			
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
      out.write("\t\t\t<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>URL</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>출처</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>성향</span></th>\r\n");
      out.write("\t\t\t<th scope=\"col\"><span>부서</span></th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t    <tbody>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");

	    	for(int i=0; i<arr.length(); i++){
	    		JSONObject obj = new  JSONObject();
				obj = (JSONObject)arr.get(i);
				String type9 = "";
				if("1".equals(obj.getString("senti"))){
					type9 = "긍정";
				}else if("2".equals(obj.getString("senti"))){
					type9 = "부정";
				}else{
					type9 = "중립";
				}
	    
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("date").toString());
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("title"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("url"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("site_name"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(type9);
      out.write("</span></td>\r\n");
      out.write("\t\t\t<td><span>");
      out.print(obj.get("name"));
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
