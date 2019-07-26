package org.apache.jsp.riskv3.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.issue.IssueDataBean;
import risk.issue.IssueMgr;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.issue.IssueDataBean;
import risk.issue.IssueBean;
import risk.search.MetaMgr;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.util.StringUtil;
import risk.util.DateUtil;
import risk.util.PageIndex;

public final class aj_005fproductDataList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/report/../inc/sessioncheck.jsp");
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

	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	IssueDataBean idBean= new IssueDataBean();
	
	ArrayList arrIdBean = new ArrayList();

	//보고서 날짜
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	//시간값 자릿수에 따른 처리
	if(ir_stime.length()==1){
		ir_stime = "0"+ir_stime+":00:00";
	}else if(ir_stime.length()==2){
		ir_stime = ir_stime+":00:00";
	}
	
	if(ir_etime.length()==1){
		ir_etime = "0"+ir_etime+":00:00";
	}else if(ir_etime.length()==2){
		ir_etime = ir_etime+":00:00";
	}	
	
		
	arrIdBean = issueMgr.getIssueDataforTypeCode31(ir_sdate,ir_stime,ir_edate,ir_etime, "1", "8");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout: fixed;\">\r\n");
      out.write("<col width=\"5%\"><col width=\"15%\"><col width=\"*\"><col width=\"10%\"><col width=\"15%\"><col width=\"10%\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th><input type=\"checkbox\" name=\"checkall_pro\" value=\"\" onclick=\"checkAll('pro');\"></th>\r\n");
      out.write("\t\t<th>출처</th>\r\n");
      out.write("\t\t<th>제목</th>\r\n");
      out.write("\t\t<th>유사</th>\r\n");
      out.write("\t\t<th>수집일시</th>\r\n");
      out.write("\t\t<th>성향</th>\r\n");
      out.write("\t</tr>\r\n");

		String sunghyang = "";
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (IssueDataBean)arrIdBean.get(i);
				if(idBean.getIc_code().equals("1")){
					sunghyang = "긍정";
				}else if(idBean.getIc_code().equals("2")){
					sunghyang = "중립";
				}else{
					sunghyang = "부정";
				}				

      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><input type=\"checkbox\" name=\"proCheck\" value=\"");
      out.print(idBean.getId_seq());
      out.write("\" onclick=\"\"></td>\r\n");
      out.write("\t\t<td><p style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" class=\"board01_category\" title=\"");
      out.print(idBean.getMd_site_name());
      out.write('"');
      out.write('>');
      out.print(idBean.getMd_site_name());
      out.write("</p></td>\r\n");
      out.write("\t\t<td><p style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" class=\"board_01_tit_01\" title=\"");
      out.print(idBean.getId_title());
      out.write("\"><a href=\"javascript:hrefPop('");
      out.print(idBean.getId_url());
      out.write("');\">");
      out.print(idBean.getId_title());
      out.write("</a></p></td>\r\n");
      out.write("\t\t<td>");
      out.print(idBean.getMd_same_ct());
      out.write("</td>\r\n");
      out.write("\t\t<td>");
      out.print(idBean.getFormatMd_date("yyyy-MM-dd"));
      out.write("</td>\r\n");
      out.write("\t\t<td><p class=\"tendency_0");
      out.print(idBean.getIc_code());
      out.write('"');
      out.write('>');
      out.print(sunghyang);
      out.write("</p></td>\r\n");
      out.write("\t</tr>\r\n");

			}
		}else{
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"6\" style=\"text-align: center;\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t");
}

      out.write("\r\n");
      out.write("</table>\r\n");
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
