package org.apache.jsp.dashboard.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.dashboard.search.SearchMgr;
import risk.json.JSONObject;
import risk.json.JSONArray;
import java.util.*;
import risk.util.*;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.ConfigUtil;

public final class searchExcelPOI_005fDao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/dashboard/search/../../riskv3/inc/sessioncheck.jsp");
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


ParseRequest pr = new ParseRequest(request);
pr.printParams();
DateUtil du = new DateUtil();
SearchMgr searchMgr = new SearchMgr();

//엑셀용 기본 세팅
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	POIExcelAdd poiAdd = new POIExcelAdd();
	String filePath = cu.getConfig("FILEPATH");
	String excelPath = filePath + "excel/" + SS_M_NO + "/";
	String yyyy = du.getDate("yyyy");
	String MMdd = du.getDate("MMdd");
	String nowTime = du.getDate("HHmmss");
	String fileName = pr.getString("fileName", "daeguExcel" + yyyy + MMdd + nowTime + ".xlsx");
	String subject = "";
	String[] titleArr = null;
	String tmp[] = null;
	List excelList = new ArrayList();
/**************************/

String section = pr.getString("section");
String i_sdate = pr.getString("i_sdate");
String i_edate = pr.getString("i_edate");
String i_sourcetype = pr.getString("i_sourcetype");				// 채널

/* 		첫번째 검색 키워드의 파라미터		 */ 
String i_and_text = pr.getString("i_and_text");					// AND 검색 키워드
String i_exact_text = pr.getString("i_exact_text");				// 구문 검색 키워드
String i_or_text = pr.getString("i_or_text");						// OR 검색 키워드
String i_exclude_text = pr.getString("i_exclude_text");			// 제외단어 키워드


/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text2 = pr.getString("i_and_text2");				// AND 검색 키워드
String i_exact_text2 = pr.getString("i_exact_text2");				// 구문 검색 키워드
String i_or_text2 = pr.getString("i_or_text2");						// OR 검색 키워드
String i_exclude_text2 = pr.getString("i_exclude_text2");		// 제외단어 키워드

/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text3 = pr.getString("i_and_text3");				// AND 검색 키워드
String i_exact_text3 = pr.getString("i_exact_text3");				// 구문 검색 키워드
String i_or_text3 = pr.getString("i_or_text3");						// OR 검색 키워드
String i_exclude_text3 = pr.getString("i_exclude_text3");		// 제외단어 키워드

String title = pr.getString("title");
String keyword = pr.getString("keyword");
String keyword1 = pr.getString("keyword1");
String keyword2 = pr.getString("keyword2");
String keyword3 = pr.getString("keyword3");
int limit = pr.getInt("limit");											// 연관키워드 개수

JSONArray jArray = new JSONArray();
JSONObject result = new JSONObject();


if("5_1".equals(section) || "5_2".equals(section) || "5_3".equals(section)){
	JSONObject tmpObj = null;
	if("5_1".equals(section)){
		result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, 3000, 1, SS_M_ID);
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		titleArr = new String[] {"출처","제목","URL","수집시간"};
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			tmp[3] = object.getString("date");			
			
			excelList.add(tmp);
		}
		
	}else if("5_2".equals(section)){
		result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, 3000, 1, SS_M_ID);
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		titleArr = new String[] {"출처","제목","URL","수집시간"};
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			tmp[3] = object.getString("date");			
			
			excelList.add(tmp);
		}
		
	}else if("5_3".equals(section)){
		result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype, 3000, 1, SS_M_ID);
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		titleArr = new String[] {"출처","제목","URL","수집시간"};
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			tmp[3] = object.getString("date");			
			
			excelList.add(tmp);
		}		
	}
}

poiAdd.addExcel(excelPath, fileName, subject, titleArr, excelList);

String fullName = SS_URL + "dashboard/file/" + "excel/" + SS_M_NO + "/" + fileName;
fullName = fullName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
System.out.println("fullName=> " + fullName);


      out.write("\r\n");
      out.write("\r\n");
      out.print(fullName);
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
