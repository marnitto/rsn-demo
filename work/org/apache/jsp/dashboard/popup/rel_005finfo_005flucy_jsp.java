package org.apache.jsp.dashboard.popup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.StringUtil;
import risk.dashboard.popup.CmnPopupLucyMgr;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.dashboard.popup.CmnPopupMgr;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class rel_005finfo_005flucy_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/dashboard/popup/../../riskv3/inc/sessioncheck.jsp");
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

	StringUtil su = new StringUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	
	CmnPopupLucyMgr cPopupLucyMgr = new CmnPopupLucyMgr();
	
	String i_sdate = pr.getString("i_sdate");
	String i_edate = pr.getString("i_edate");
	String i_and_text = pr.getString("i_and_text");							// AND 검색 키워드
	String i_exact_text = pr.getString("i_exact_text");						// 구문 검색 키워드
	String i_or_text = pr.getString("i_or_text");							// OR 검색 키워드
	String i_exclude_text = pr.getString("i_exclude_text");				// 제외단어 키워드
	String i_sourcetype = pr.getString("i_sourcetype");					// 채널
	String i_trend = pr.getString("i_trend", "");							// 감성
	String I_relation_word = pr.getString("I_relation_word", "");		// 연관키워드
	String excelParam = "";
	
	int rowCnt = pr.getInt("rowCnt", 10);
	int nowPage = pr.getInt("nowPage", 1);

	obj = cPopupLucyMgr.getPopupData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, i_trend, I_relation_word, rowCnt, nowPage, SS_M_ID);
	excelParam = "i_sdate=" + i_sdate + "&i_edate=" + i_edate + "&i_and_text=" + i_and_text + "&i_exact_text=" + i_exact_text + "&i_or_text=" + i_or_text + "&i_exclude_text=" + i_exclude_text
			+ "&i_sourcetype=" + i_sourcetype + "&i_trend=" + i_trend + "&I_relation_word=" + I_relation_word;
	
	int totalCount = obj.getInt("total");
	double totalPage = (double)totalCount / (double)rowCnt;
	
	String totalCountComma = su.digitFormat(totalCount, "###,###");
	String nowPageComma = su.digitFormat(nowPage, "###,###");
	String totalPageComma = su.digitFormat((int)Math.ceil(totalPage), "###,###");
	
	arr = obj.getJSONArray("data");

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
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"getExcel(event, '../popup/excel/excelLucyDao.jsp', '");
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
								String senti ="";
								if(("3555").equals(obj2.getString("sitecode"))  || ("4943").equals(obj2.getString("sitecode"))){
									caffeImg = "<a href=\"javascript:portalSearch('" + obj2.getString("sitecode") + "" + "','" + obj2.getString("title").replaceAll("'", "") + "')\" class=\"ui_bullet_cafe\">Cafe</a>";
								}else{
									caffeImg= "";
								}
								if(("1").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_blue";
									senti = "긍정";
								}else if(("2").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_red";
									senti = "부정";
								}else if(("3").equals(obj2.getString("senti"))){
									colorClass = "ui_fc_gray";
									senti = "중립";
								}
								
								String encodeUrl = java.net.URLEncoder.encode(obj2.getString("url"));
								
								//String title = (obj2.getString("title")).replaceAll("\\\"", "&#34;");								
								//title = title.replaceAll("\\\'", "&#39;");
								String title = "pr".equals(String.valueOf(obj2.get("i_sourcetype")).toLowerCase()) ? String.valueOf(obj2.get("i_content")) : String.valueOf(obj2.get("title"));
								
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<tr> \r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(obj2.getString("sitename"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"al\">");
      out.print(caffeImg);
      out.write("<a href=\"http://hub.buzzms.co.kr?url=");
      out.print(encodeUrl);
      out.write("\" target=\"_blank\" data-txts=\"");
      out.print(title.replaceAll("\"", "'"));
      out.write("\" onmouseenter=\"previewOn( this, $( '.popup_item .container' ) )\" onmouseleave=\"previewOff( this )\">");
      out.print(obj2.getString("title"));
      out.write("</a></td>\r\n");
      out.write("\t\t\t\t\t\t<td><span class=\"");
      out.print(colorClass);
      out.write('"');
      out.write('>');
      out.print(senti);
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
      out.write("\t\t\t\t\t<div style=\"text-align: right;color: #999999;font-size: 11px;padding: 8px 0 0 0;\">* 트위터 RT 미포함</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"cmnPopup_paging\" class=\"paginate\">\r\n");
      out.write("\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\t\t\tfunction cmnPopupPaging(nowPage){\r\n");
      out.write("\t\t\t\t\t\t\t\tvar param = \"i_sdate=\" + \"");
      out.print(i_sdate);
      out.write("\" + \"&i_edate=\" +  \"");
      out.print(i_edate);
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t+ \"&i_and_text=\" + \"");
      out.print(i_and_text);
      out.write("\" + \"&i_exact_text=\" + \"");
      out.print(i_exact_text);
      out.write("\" + \"&i_or_text=\" + \"");
      out.print(i_or_text);
      out.write("\" + \"&i_exclude_text=\" + \"");
      out.print(i_exclude_text);
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t+ \"&i_sourcetype=\" + \"");
      out.print(i_sourcetype);
      out.write("\" + \"&i_trend=\" + \"");
      out.print(i_trend);
      out.write("\" + \"&I_relation_word=\" + \"");
      out.print(I_relation_word);
      out.write("\" + \"&nowPage=\" + nowPage;\r\n");
      out.write("\t\t\t\t\t\t\t\tpopupOpen( \"../popup/rel_info_lucy.jsp?\" + param);\r\n");
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
