package org.apache.jsp.dashboard.popup.excel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import risk.json.JSONArray;
import risk.dashboard.excel.ExcelDownload;
import risk.util.ParseRequest;

public final class excelDao_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONArray arr = new JSONArray();

ExcelDownload excel = new ExcelDownload();

String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String codeList = pr.getString("codeList");
String relationKeyword = pr.getString("relationKeyword");
String source = pr.getString("source"); 	/*	 주요 매체 현황 매체 구분 값	 */
String category = pr.getString("category");

int nowPage = pr.getInt("nowPage", 1);
int rowCnt = pr.getInt("rowCnt", 10);

if(!"".equals(category)){  /* 주요시정 모니터링 메뉴 - 주요 매체 현황 팝업 */ 
	arr = excel.getExcelData2(scope, keyword, sDate, eDate, codeList, source, category);
}else{
	arr = excel.getExcelData(scope, keyword, sDate, eDate, codeList, relationKeyword);
}


      out.write("\r\n");
      out.write("\r\n");
      out.write("<table style=\"width:1200px; table-layout:fixed;\" border=\"1\" cellspacing=\"1\" >\r\n");
      out.write("    <caption></caption>\r\n");
      out.write("\t<colgroup>\r\n");
      out.write("\t\t<col style=\"width:200px\">\r\n");
      out.write("\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t<col style=\"width:400px\">\r\n");
      out.write("\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t<col>\r\n");
      out.write("\t</colgroup>\r\n");
      out.write("\t<thead>\r\n");
      out.write("\t\t<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->\r\n");
      out.write("\t\t");

			int colspan = 5;
		
      out.write("\r\n");
      out.write("\t\t<tr style=\"height:40px\">\r\n");
      out.write("\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write("\">관련정보</th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr style=\"height:30px\">\r\n");
      out.write("\t\t\t<th scope=\"col\" colspan=\"");
      out.print(colspan);
      out.write('"');
      out.write('>');
      out.print(sDate );
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(eDate );
      out.write("</th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th scope=\"col\"><span>출처</span></th>\r\n");
      out.write("\t\t<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("\t\t<th scope=\"col\"><span>URL</span></th>\r\n");
      out.write("\t\t<th scope=\"col\"><span>감성</span></th>\r\n");
      out.write("\t\t<th scope=\"col\"><span>수집시간</span></th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</thead>\r\n");
      out.write("    <tbody>\r\n");
      out.write("    \r\n");
      out.write("    ");

    	for(int i=0; i<arr.length(); i++){
    		JSONObject obj = new  JSONObject();
			obj = (JSONObject)arr.get(i);
    
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><span>");
      out.print(obj.get("source"));
      out.write("</span></td>\r\n");
      out.write("\t\t<td><span>");
      out.print(obj.get("title"));
      out.write("</span></td>\r\n");
      out.write("\t\t<td><span>");
      out.print(obj.get("url"));
      out.write("</span></td>\r\n");
      out.write("\t\t<td><span>");
      out.print(obj.get("senti"));
      out.write("</span></td>\r\n");
      out.write("\t\t<td><span>");
      out.print(obj.get("date"));
      out.write("</span></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");

    	}
    
      out.write("\r\n");
      out.write("    </tbody>\r\n");
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
