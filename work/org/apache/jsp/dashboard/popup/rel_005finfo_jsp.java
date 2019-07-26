package org.apache.jsp.dashboard.popup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.StringUtil;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.dashboard.popup.CmnPopupMgr;
import risk.util.ParseRequest;

public final class rel_005finfo_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");

	StringUtil su = new StringUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	
	CmnPopupMgr cPopupMgr = new CmnPopupMgr();
	
	String scope = pr.getString("scope");
	String keyword = pr.getString("keyword");
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String codeList = pr.getString("codeList");
	String relationKeyword = pr.getString("relationKeyword");
	String source = pr.getString("source"); 	/*	 주요 매체 현황 매체 구분 값	 */
	String category = pr.getString("category");
	String tier = pr.getString("tier");
	String s_seq = pr.getString("s_seq");
	String excelParam = "";
	
	int nowPage = pr.getInt("nowPage", 1);
	int rowCnt = pr.getInt("rowCnt", 10);
	
	if(!"".equals(category)){  /* 주요 매체 현황(주요시정 모니터링 메뉴) 팝업 */ 
		obj = cPopupMgr.getPopupData2(nowPage, rowCnt, scope, keyword, sDate, eDate, codeList, source, category);
		excelParam = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&source=" + source + "&category=" + category;
	}else{
		obj = cPopupMgr.getPopupData(nowPage, rowCnt, scope, keyword, sDate, eDate, codeList, relationKeyword,tier,s_seq);
		excelParam = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&relationKeyword=" + relationKeyword+"&tier="+tier+"&s_seq="+s_seq;
	}
	arr = obj.getJSONArray("data");
	
	int totalCount = obj.getInt("total");
	double totalPage = (double)totalCount / (double)rowCnt;

	String totalCountComma = su.digitFormat(totalCount, "###,###");
	String nowPageComma = su.digitFormat(nowPage, "###,###");
	String totalPageComma = su.digitFormat((int)Math.ceil(totalPage), "###,###");
	
	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<title>팝업컨텐츠</title>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../asset/css/normalize.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../asset/css/design.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<!-- Popup -->\r\n");
      out.write("\t<div id=\"popup\" style=\"display:block\">\r\n");
      out.write("\t\t<h2 class=\"invisible\">팝업 컨텐츠</h2>\r\n");
      out.write("\t\t<div class=\"bg\"></div>\r\n");
      out.write("\t\t<div class=\"container ui_shadow\">\r\n");
      out.write("\t\t\t<section style=\"width:700px\">\r\n");
      out.write("\t\t\t\t<div class=\"header\">\r\n");
      out.write("\t\t\t\t\t<h3>관련정보</h3>\r\n");
      out.write("\t\t\t\t\t<a href=\"#\" class=\"close\" onclick=\"hndl_popupClose();return false;\">팝업닫기</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"content\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_board_list_00\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_board_header f_clear\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"fl\"><span><strong><span class=\"ui_bullet_02\">-</span> 총 ");
      out.print(totalCountComma);
      out.write("건</strong>, ");
      out.print(nowPageComma);
      out.write('/');
      out.print(totalPageComma);
      out.write(" pages</span></div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"fr\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- <div class=\"ui_searchbox\"><input id=\"pop_search_00\" type=\"text\" placeholder=\"제목검색\"><label for=\"pop_search_00\"><button type=\"button\"><span>검색</span></button></label></div> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"getExcel(event, '../popup/excel/excelDao.jsp', '");
      out.print(excelParam);
      out.write("', '관련정보'); return false;\"><span class=\"icon excel\">Excel Download</span></button>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t<caption>연관키워드 목록</caption>\r\n");
      out.write("\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:150px\">\r\n");
      out.write("\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:70px\">\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:80px\">\r\n");
      out.write("\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>출처</span></th>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>제목</span></th>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>감성</span></th>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>수집시간</span></th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t");

							int tmpRow = rowCnt - arr.length();
						
							JSONObject obj2 = null;
							for(int i=0; i<arr.length(); i++){
								obj2 = new  JSONObject();
								obj2 = (JSONObject)arr.get(i);
								
								String caffeImg = "";
								String colorClass ="";
								if(("3555").equals(obj2.getString("sitecode"))  || ("4943").equals(obj2.getString("sitecode"))){
									caffeImg = "<a href=\"javascript:portalSearch('" + obj2.getString("sitecode") + "" + "','" + obj2.getString("title").replaceAll("'", "") + "')\" class=\"ui_bullet_cafe\">Cafe</a>";
								}else{
									caffeImg= "";
								}
								if(("긍정").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_blue";
								}else if(("부정").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_red";
								}else if(("중립").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_gray";
								}
								
								String encodeUrl = java.net.URLEncoder.encode(obj2.getString("url"));
								System.out.println("############"+encodeUrl);
								String title = (obj2.getString("title")).replaceAll("\\\"", "&#34;");
								title = title.replaceAll("\\\'", "&#39;");
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<tr> \r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(obj2.getString("source"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"al\">");
      out.print(caffeImg);
      out.write("<a href=\"http://hub.buzzms.co.kr?url=");
      out.print(encodeUrl);
      out.write("\" target=\"_blank\" data-txts=\"");
      out.print(title);
      out.write("\" onmouseenter=\"previewOn( this, $( '.popup_item .container' ) )\" onmouseleave=\"previewOff( this )\">");
      out.print(obj2.getString("title"));
      out.write("</a></td>\r\n");
      out.write("\t\t\t\t\t\t<td><span class=\"");
      out.print(colorClass);
      out.write('"');
      out.write('>');
      out.print(obj2.getString("senti"));
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(obj2.getString("date"));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t");

							}
							
							for(int j=0; j<tmpRow; j++){
						
      out.write("\t\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"al\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\t\r\n");
      out.write("\t\t\t\t\t\t");

						}
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"cmnPopup_paging\" class=\"paginate\">\r\n");
      out.write("\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\t\t\tfunction cmnPopupPaging(nowPage){\r\n");
      out.write("\t\t\t\t\t\t\t\tpopupOpen( \"../popup/rel_info.jsp?nowPage=\" + nowPage + \"&rowCnt=\" + \"");
      out.print(rowCnt);
      out.write("\" + \"&scope=\" + \"");
      out.print(scope);
      out.write("\" + \"&keyword=\" + \"");
      out.print(keyword);
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t+ \"&sDate=\" + \"");
      out.print(sDate);
      out.write("\" +\"&eDate=\" + \"");
      out.print(eDate);
      out.write("\" + \"&codeList=\" + \"");
      out.print(codeList);
      out.write("\"+ \"&relationKeyword=\" + \"");
      out.print(relationKeyword);
      out.write("\"+\"&tier=\"+\"");
      out.print(tier);
      out.write("\"+\"&s_seq=\"+\"");
      out.print(s_seq);
      out.write("\");\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tpaging(10, ");
      out.print(rowCnt);
      out.write(',');
      out.write(' ');
      out.print(obj.getString("total"));
      out.write(',');
      out.write(' ');
      out.print(nowPage);
      out.write(", $(\"#cmnPopup_paging\"), \"cmnPopupPaging\");\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</script>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<!-- Loader -->\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_loader v0\"><span class=\"loader\">Load</span></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_loader v1\"><span class=\"loader\"></span><p>saving...</p></div>\r\n");
      out.write("\t\t\t\t\t<!-- // Loader -->\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</section>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- // Popup -->\r\n");
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
