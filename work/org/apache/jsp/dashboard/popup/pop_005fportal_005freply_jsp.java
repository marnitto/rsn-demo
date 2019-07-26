package org.apache.jsp.dashboard.popup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import java.util.*;
import risk.util.PageIndex;
import risk.util.StringUtil;
import risk.util.ConfigUtil;
import risk.dashboard.summary.SummaryMgr;
import java.net.*;

public final class pop_005fportal_005freply_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	SummaryMgr sMgr = new SummaryMgr();
	
	String p_date = pr.getString("p_date");
	String doc_id = pr.getString("doc_id");
	String r_trnd = pr.getString("r_trnd", "");
	String r_relation_word = pr.getString("r_relation_word", "");
	int totalCnt = pr.getInt("totalCnt",0);
	String excelParam = "";
	System.out.println("========================================");
	pr.printParams();
	
	int nowpage = Integer.parseInt( pr.getString("popPage","1") );
	int rowCnt = 10;
	int totalPage = 0;
	
	sMgr.getLucySwitch();
	
	ArrayList arData = new ArrayList();
	arData = sMgr.getReplyDataList(nowpage, rowCnt, p_date, doc_id, r_trnd, r_relation_word, SS_M_ID);
	excelParam = "&p_docid=" + doc_id + "&p_date=" + p_date + "&r_trend=" + r_trnd + "&r_relation_word=" + URLEncoder.encode(r_relation_word, "UTF-8");
	
	if(totalCnt == 0){
		totalCnt = sMgr.getReplyTotalCnt();
	}
	totalPage = totalCnt / rowCnt;
	if(totalCnt % rowCnt > 0){
		totalPage += 1;
	}
	
	String srtMsg = "총 건수 : "+su.digitFormat(totalCnt)+" 건, "+su.digitFormat(nowpage)+"/"+su.digitFormat(totalPage)+" pages";

      out.write("\r\n");
      out.write("\t<!-- Popup -->\r\n");
      out.write("\t<div id=\"popup\">\r\n");
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
      out.write("\t\t\t\t\t\t\t<div class=\"fl\"><strong><!-- <span class=\"ui_bullet_01\">-</span> -->총 ");
      out.print(su.digitFormat(totalCnt) );
      out.write("건</strong>, ");
      out.print(su.digitFormat(nowpage)+"/"+su.digitFormat(totalPage) );
      out.write(" pages</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"fr\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Excel Download\" onclick=\"getExcel_POI(event, '../popup/excel/potal_reply_ExcelDao.jsp', '");
      out.print(excelParam);
      out.write("', deleteExcelFile); return false;\"><span class=\"icon excel\">Excel Download</span></button>\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- <button type=\"button\" class=\"ui_btn_02 ui_shadow_01\" title=\"Raw Data Download\"><span class=\"icon dn\">Raw Data Download</span></button> -->\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t<caption>관련정보 목록</caption>\r\n");
      out.write("\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:120px\">\r\n");
      out.write("\t\t\t\t\t\t<!-- <col style=\"width:100px\"> -->\r\n");
      out.write("\t\t\t\t\t\t<col>\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:70px\">\r\n");
      out.write("\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>날짜</span></th>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>내용</span></th>\r\n");
      out.write("\t\t\t\t\t\t<th scope=\"col\"><span>성향</span></th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");

   		
   	
      out.write("\r\n");
      out.write("   \t\r\n");
      out.write("   \t");
if( arData.size() > 0 ){ 
   		Map map = null;
   	
      out.write("\r\n");
      out.write("   \t\r\n");
      out.write("   \t");

   		for(int i = 0; i < arData.size(); i++){
   			map = new HashMap();
   			map = (HashMap)arData.get(i);
   			String senti = "";
   			if( "1".equals( map.get("r_trend").toString()) ){
   				senti = "긍정";
   			}else if( "2".equals( map.get("r_trend").toString()) ){
   				senti = "부정";
   			}else if( "3".equals( map.get("r_trend").toString()) ){
   				senti = "중립";
   			}
   			
   	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(map.get("r_datetime"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"title\"\r\n");
      out.write("\t\t\t\t\t\t\t>");
      out.print(map.get("r_content"));
      out.write("<!-- </a> --></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(senti);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t");

	   		}
   	
   	if(arData.size() < 10){
		for(int k=arData.size(); k < 10; k++){
			
      out.write("\r\n");
      out.write("\t\t\t<tr><td></td><td></td><td></td></tr>\r\n");
      out.write("\t\t\t");

		}
	}
		
      out.write("\r\n");
      out.write("\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td colspan=\"3\"> 검색된 자료가 없습니다. </td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
} 
      out.write("\t\t\r\n");
      out.write("\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t");

	/*** 페이징 처리 ***/
	String pageHtml = "<div class=\"paginate\">";
    
	int lastPage = totalPage;
	String strNowPage = Integer.toString(nowpage - 1); 
	String display = "";
	String AndDis = "";
	
	int ptPage = Integer.parseInt(strNowPage.substring(strNowPage.length() - 1 , strNowPage.length()));

	int startPage = (nowpage - 1) - ptPage;         // 0 : 10 : 20 ...       
	int endPage   = (nowpage - 1) + (10 - ptPage);  //10 : 20 : 30 ...
	if(endPage > lastPage) endPage = lastPage;
	
	//왼쪽화살표
	String href = "";
	page = "";
	if(startPage <= 0){
		display = "disabled";
		//href = "#";
	}else{
		href = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+startPage+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	
	if(nowpage == 1){
		AndDis = "disabled";
	}else{
		page = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+1+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	
	pageHtml += "<a "+page+" class=\"page_first "+AndDis+"\">첫 페이지</a>";
	pageHtml += "<a "+href+" class=\"page_prev "+display+"\">이전페이지</a>";
	
	if(startPage < endPage){
		//숫자
		do{
			startPage++;
			if(startPage == nowpage){
				pageHtml += "<a class=\"active\">"+ startPage +"</a>"; //강조
				
			}else{
				pageHtml += "<a href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+startPage+",'"+r_trnd+"','"+r_relation_word+"')\">"+startPage+"</a>"; //기본
				
			}
			
		}while(startPage != endPage);
	}
	
	//오른쪽화살표
	display = "";
	href = "";
	page = "";
	if(endPage >= lastPage){
		display = "disabled";
		//href = "#";
	}else{
		href = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+(endPage + 1)+",'"+r_trnd+"','"+r_relation_word+"')\"";
		page = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+totalPage+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	pageHtml += "<a "+href+" class=\"page_next "+display+"\">다음페이지</a> ";
	pageHtml += "<a "+page+" class=\"page_last "+display+"\">다음페이지</a> ";
	
	pageHtml += "</div>";
	out.println(pageHtml);

      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<!-- Loader -->\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_loader v0\"><span class=\"loader\">Load</span></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_loader v1\"><span class=\"loader\"></span><p>saving...</p></div>\r\n");
      out.write("\t\t\t\t\t<!-- // Loader -->\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</section>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- // Popup -->\r\n");
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
