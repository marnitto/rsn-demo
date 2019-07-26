package org.apache.jsp.riskv3.admin.logs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.util.ParseRequest;
import java.util.*;
import java.lang.*;
import risk.admin.log.LogMgr;
import risk.util.PageIndex;
import risk.admin.log.LogBean;

public final class user_005flog_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/logs/../../inc/sessioncheck.jsp");
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
      response.setContentType("text/html; charset=utf-8");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	int PAGE_SIZE = 10;		//페이지당 리스트 수
	int totpage = 0;		//총 페이지수
	int nowpage = 1;		//현재 페이지번호
	int count = 0;			//총 게시물 수
	
	String sch_mode = null;	//검색조건 분류
	String sch_val = null;	//조건 값
	String logKinds = null; //로그 종류
	String logType = null; //로그 상세 종류
	
	ArrayList logKindsList = new ArrayList();
	ArrayList logTypeList = new ArrayList();
	ArrayList logList = new ArrayList();
	
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	nowpage = pr.getInt("nowpage",1);
	sch_mode = pr.getString("sch_mode","");
	sch_val = pr.getString("sch_val","");
	logKinds = pr.getString("logKinds","");
	logType = pr.getString("logType","");
	
	
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	logKindsList = logMgr.getLogKindsList();
	logTypeList = logMgr.getLogTypeList();
	logList = logMgr.getLogList(nowpage,10,logKinds,logType,sch_mode,sch_val);
	
	totpage = logMgr.getListCnt()/10;
	if(logMgr.getListCnt()%10!=0) totpage++;

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction init(){\r\n");
      out.write("\t\tsch_log.sch_val.focus();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    function chg_svc() {\t\t\r\n");
      out.write("\t\tsch_log.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function pageClick( paramUrl ) {\r\n");
      out.write("    \tvar f = document.getElementById('sch_log');\r\n");
      out.write("        f.action = \"user_log_list.jsp\" + paramUrl;\r\n");
      out.write("        f.submit();\r\n");
      out.write("        /*\r\n");
      out.write("        alert(paramUrl);\r\n");
      out.write("\t\tsch_log.nowpage.value = paramUrl.substr(paramUrl.indexOf(\"=\")+1, paramUrl.length);\r\n");
      out.write("\t\tsch_log.submit();\r\n");
      out.write("\t\t*/\r\n");
      out.write("    }\r\n");
      out.write("\tfunction seach() {\r\n");
      out.write("\t\tsch_log.submit();\r\n");
      out.write("\t}\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form id=\"sch_log\" name=\"sch_log\" action=\"user_log_list.jsp\" method=\"post\">\r\n");
      out.write("<input id=\"nowpage\" name=\"nowpage\" type=\"hidden\" value=\"");
      out.print(nowpage);
      out.write("\">\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/log/tit_icon.gif\" /><img src=\"../../../images/admin/log/tit_0503.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">로그보기</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"search_box\">\r\n");
      out.write("\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th style=\"height:30px;\">검색 조건</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"sch_mode\" style=\"vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\"  ");
 if( sch_mode.equals("") ) out.print("selected");
      out.write(">전체</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"1\" ");
 if( sch_mode.equals("1") ) out.print("selected");
      out.write(">아이디</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"2\" ");
 if( sch_mode.equals("2") ) out.print("selected");
      out.write(">이름</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"3\" ");
 if( sch_mode.equals("3") ) out.print("selected");
      out.write(">IP</option>\r\n");
      out.write("\t\t\t\t\t\t\t</select> \r\n");
      out.write("\t\t\t\t\t\t\t<input style=\"width:460px;vertical-align:middle\" class=\"textbox\" type=\"text\" name=\"sch_val\" value=\"");
      out.print(sch_val);
      out.write("\"><img src=\"../../../images/admin/log/btn_search.gif\" onclick=\"seach();\" style=\"cursor:pointer;vertical-align:middle\"/>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th style=\"height:30px;\">검색 조건</th>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table style=\"color:#2f5065;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<select name=\"logKinds\" style=\"width:100px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"\">전체</option>\r\n");

		for(int i=0;i<logKindsList.size();i++){	
			logBean = new LogBean();
			logBean = (LogBean)logKindsList.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(logBean.getL_kinds());
      out.write('"');
      out.write(' ');
 if( logBean.getL_kinds().equals(logKinds) ) out.print("selected");
      out.write('>');
      out.print(logBean.getL_kindsName());
      out.write("</option>\r\n");

		}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th style=\"height:30px;\">로그 상세</th>\r\n");
      out.write("\t\t\t\t\t\t<td><select name=\"logType\" style=\"width:100px;\">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"\">전체</option>\r\n");

		for(int i=0;i<logTypeList.size();i++){	
			logBean = new LogBean();
			logBean = (LogBean)logTypeList.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"");
      out.print(logBean.getL_type());
      out.write('"');
      out.write(' ');
 if( logBean.getL_type().equals(logType) ) out.print("selected");
      out.write('>');
      out.print(logBean.getL_typeName());
      out.write("</option>\r\n");

		}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:45px;\"><b>");
      out.print(logMgr.getListCnt());
      out.write("</b>건 , <b>");
      out.print(nowpage);
      out.write('/');
      out.print((logMgr.getListCnt()/PAGE_SIZE));
      out.write("</b>page</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("\t\t\t\t<col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\">\r\n");
      out.write("\t\t\t\t\t<tr>    \r\n");
      out.write("\t\t\t\t\t\t<th>로그 종류</th>\r\n");
      out.write("\t\t\t\t\t\t<th>아이디</th>\r\n");
      out.write("\t\t\t\t\t\t<th>이름</th>\r\n");
      out.write("\t\t\t\t\t\t<th>IP</th>\r\n");
      out.write("\t\t\t\t\t\t<th>날짜</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	if(logList.size() > 0){
		for(int i = 0; i < logList.size(); i++){
			logBean = new LogBean();
			logBean = (LogBean)logList.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(logBean.getL_kindsName()+"/"+logBean.getL_typeName());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(logBean.getM_id());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(logBean.getM_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(logBean.getL_ip());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(logBean.getL_date());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		}
	}else{

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"5\" style=\"font-weight:bold;height:40px\" align=\"center\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(nowpage, totpage,"","" ));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
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
