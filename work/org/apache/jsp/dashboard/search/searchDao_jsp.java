package org.apache.jsp.dashboard.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.dashboard.search.SearchMgr;
import risk.json.JSONObject;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.ConfigUtil;

public final class searchDao_jsp extends org.apache.jasper.runtime.HttpJspBase
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
JSONObject object = new JSONObject();
JSONObject object2 = new JSONObject();
SearchMgr searchMgr = new SearchMgr();

String section = pr.getString("section");
String i_sdate = pr.getString("i_sdate");
String i_edate = pr.getString("i_edate");
String i_sourcetype = pr.getString("i_sourcetype");			// 채널

/* 		첫번째 검색 키워드의 파라미터		 */ 
String i_and_text = pr.getString("i_and_text");					// AND 검색 키워드
String i_exact_text = pr.getString("i_exact_text");				// 구문 검색 키워드
String i_or_text = pr.getString("i_or_text");					// OR 검색 키워드
String i_exclude_text = pr.getString("i_exclude_text");			// 제외단어 키워드


/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text2 = pr.getString("i_and_text2");				// AND 검색 키워드
String i_exact_text2 = pr.getString("i_exact_text2");			// 구문 검색 키워드
String i_or_text2 = pr.getString("i_or_text2");					// OR 검색 키워드
String i_exclude_text2 = pr.getString("i_exclude_text2");		// 제외단어 키워드

/* 		세번째 검색 키워드의 파라미터		 */ 
String i_and_text3 = pr.getString("i_and_text3");				// AND 검색 키워드
String i_exact_text3 = pr.getString("i_exact_text3");			// 구문 검색 키워드
String i_or_text3 = pr.getString("i_or_text3");					// OR 검색 키워드
String i_exclude_text3 = pr.getString("i_exclude_text3");		// 제외단어 키워드

String i_remove_rt = pr.getString("i_remove_rt");

int nowPage = pr.getInt("nowPage");
int limit = pr.getInt("limit");										// 연관키워드 개수

System.out.println(" i_and_text = "+ i_and_text );

//연관키워드 일때 최대 검색기간을 31일로 제한 한다. -lucy api 공통 사항

if("1".equals(section)){
	String naverBoardCode = "2305157";
	String daumBoardCode = "2305159";
	object2.put("naver", searchMgr.getRealTimeList(naverBoardCode));
	object2.put("daum", searchMgr.getRealTimeList(daumBoardCode));
	object.put("sectionData1", object2);
	
}else if("2".equals(section)){			// 정보검색 - 검색키워드1 & 검색키워드2
	object2.put("firstKeyword", searchMgr.getInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, "1", SS_M_ID));
	object2.put("secondKeyword", searchMgr.getInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, "2", SS_M_ID));
	object2.put("thirdKeyword", searchMgr.getInfoData(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, "3", SS_M_ID));
	object.put("sectionData2", object2);
	
}else if("3_1".equals(section)){		// 감성현황(정보 분포)
	object2.put("leftData", searchMgr.getInfoDistribution_left(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID));
	object2.put("rightData", searchMgr.getInfoDistribution_right(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID));
	object.put("sectionData3_1", object2);
	
}else if("3_2".equals(section)){		// 감성현황(감성 추이)
	object.put("sectionData3_2", searchMgr.getSentiTrend(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID));
	
}else if("3_3".equals(section)){		// 감성현황(채널별 추이)
	object.put("sectionData3_3", searchMgr.getChannelTrend(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, SS_M_ID));
	
}else if("4".equals(section)){			// 연관키워드
	long diffCnt = du.DateDiff("yyyyMMdd",i_edate,i_sdate);
	if(diffCnt > 31) i_sdate = du.addDay_v2(i_edate, -31).replaceAll("-", "");
	object.put("sectionData4", searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, limit, SS_M_ID));

}else if("5".equals(section)){			// 관련정보
	String tw_rt_chk = pr.getString("tw_rt_chk", "");
	if("1".equals(tw_rt_chk)){
		//트위터 전용 검색 일 경우
		object.put("sectionData5", searchMgr.getRelationInfoDataForTw(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 10, nowPage, SS_M_ID));
	}else{
		object.put("sectionData5", searchMgr.getRelationInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 10, nowPage, SS_M_ID));	
	}

}
out.println(object);


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
