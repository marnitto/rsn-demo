package org.apache.jsp.riskv3.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.issue.ReportTypeConstants;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.util.StringUtil;
import risk.util.DateUtil;
import risk.issue.IssueReportMgr;
import risk.issue.IssueReportBean;
import risk.util.PageIndex;

public final class issue_005freport_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\n");
      out.write("\n");

	ParseRequest pr = new ParseRequest(request);
	ArrayList arrIrBean = new ArrayList();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	DateUtil du = new DateUtil();
	
	String title = "정보에 대한 보고서를 작성합니다.";
	
	int nowpage = 1;	
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	String ir_type = pr.getString("ir_type");
	
	nowpage = pr.getInt("nowpage",1);
	arrIrBean = irMgr.getIssueReportList(nowpage,pageCnt,"",ir_type,"","","");
	totalCnt = irMgr.getTotalCnt();
	
	//페이징 처리
	totalPage = 0;	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}


      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");
      out.write("<title>RIS-K</title>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/base.css\" />\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.8.3.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write(" \tfunction pageClick( paramUrl ) {\n");
      out.write("\t\t//document.fSend.nowPage.value = paramUrl;\n");
      out.write("\t\tdocument.fSend.action='issue_report_list.jsp' + paramUrl;\n");
      out.write("\t\tdocument.fSend.target='';\n");
      out.write("\t\tdocument.fSend.submit();\n");
      out.write("    }\n");
      out.write(" \t\n");
      out.write(" \tfunction checkAll(chk) {\n");
      out.write(" \t\t/* var o=document.all.reportchk;\n");
      out.write(" \t\tfor(i=0; i<o.length; i++) {\n");
      out.write(" \t\t\to(i).checked = chk;\n");
      out.write(" \t\t} */\n");
      out.write(" \t\t\n");
      out.write(" \t\tif($(\"#reportChk\").prop(\"checked\")){\n");
      out.write(" \t\t\t$(\"[name=reportchk]\").prop(\"checked\",true);\n");
      out.write(" \t\t}else{\n");
      out.write(" \t\t\t$(\"[name=reportchk]\").prop(\"checked\",false);\n");
      out.write(" \t\t}\n");
      out.write(" \t}\n");
      out.write(" \t\n");
      out.write(" \tfunction deleteReport() {\n");
      out.write(" \t\tvar o=document.fSend.reportchk;\n");
      out.write(" \t\tvar chkedseq=\"\";\n");
      out.write(" \t\t\n");
      out.write(" \t\t$(\"[name=reportchk]:checked\").each(function(){\n");
      out.write(" \t\t\tif(chkedseq==\"\"){\n");
      out.write(" \t\t\t\tchkedseq = $(this).val();\n");
      out.write(" \t\t\t}else{\n");
      out.write(" \t\t\t\tchkedseq += ','+$(this).val();\n");
      out.write(" \t\t\t}\n");
      out.write(" \t\t});\n");
      out.write(" \t\t\n");
      out.write(" \t\t/* for(i=0; i<o.length; i++) {\n");
      out.write(" \t\t\tif(o(i).checked == true){\n");
      out.write("\t \t\t\tif(o(i).value!=\"\"){\n");
      out.write("\t \t\t\t\tif(chkedseq == ''){\n");
      out.write("\t \t\t\t\t\tchkedseq = o(i).value;\n");
      out.write("\t \t\t\t\t}else{\n");
      out.write("\t \t\t\t\t\tchkedseq = chkedseq+','+o(i).value;\n");
      out.write("\t \t\t\t\t}\n");
      out.write("\t \t\t\t}\n");
      out.write(" \t\t\t}\n");
      out.write(" \t\t} */\n");
      out.write(" \t\t\n");
      out.write("\t\tvar f = document.fSend;\n");
      out.write("\t\tif(chkedseq == ''){alert('선택된 정보가 없습니다.'); return;}\n");
      out.write("\t\tf.mode.value = 'delete';\n");
      out.write("\t\tf.delseq.value = chkedseq;\n");
      out.write("\t\tf.action=\"issue_report_prc.jsp\";\n");
      out.write("\t\tf.target='processFrm';\n");
      out.write("\t\tf.submit();\n");
      out.write(" \t}\n");
      out.write("\n");
      out.write(" \t//보고서 정보 상세보기\n");
      out.write(" \tfunction showDetail(ir_seq, ir_type){\n");
      out.write(" \t\tvar f = document.fSend; \n");
      out.write(" \t\tf.ir_seq.value = ir_seq;\t\t\n");
      out.write("\t\tf.action=\"issue_report_detail.jsp\";\n");
      out.write("\t\tf.target='';\n");
      out.write("\t\tf.submit(); \t\n");
      out.write(" \t}\n");
      out.write("\n");
      out.write(" \t//저장된 보고서 보기 페이지를 띄운다\n");
      out.write(" \tfunction showReport(ir_seq){\n");
      out.write("\t \tvar f = document.fSend;\n");
      out.write("\t\tf.mode.value = 'view';\n");
      out.write("\t\tf.ir_seq.value = ir_seq;\n");
      out.write("\t\tpopup.openByPost('fSend','pop_report.jsp',840,945,false,true,false,'goReportView');\t\t\n");
      out.write(" \t}\n");
      out.write(" \t\n");
      out.write(" \tfunction goReportCreate(){\n");
      out.write(" \t\tvar f = document.fSend;\n");
      out.write(" \t\tf.ir_type.value='D1';\n");
      out.write(" \t\tf.action='issue_report_creater.jsp';\n");
      out.write(" \t\tf.target='';\n");
      out.write(" \t\tf.submit();\n");
      out.write(" \t}\n");
      out.write("\n");
      out.write(" \tfunction ChangeTitle(ir_seq){\n");
      out.write(" \t \tvar f = document.fSend;\n");
      out.write(" \t \tf.ir_seq.value = ir_seq;\n");
      out.write(" \t \t//f.ir_title.value = ir_title;\n");
      out.write(" \t \twindow.open(\"about:blank\",'nameUpdate', 'width=400,height=180,scrollbars=no');\n");
      out.write(" \t \tf.action = 'pop_reportName_update.jsp';\n");
      out.write("        f.target='nameUpdate';\n");
      out.write("        f.submit();\n");
      out.write(" \t}\n");
      out.write(" \t\n");
      out.write("</script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<form name=\"fSend\" id=\"fSend\" action=\"\" method=\"post\">\n");
      out.write("<input type=\"hidden\" name=\"mode\" >\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(nowpage);
      out.write("\">\n");
      out.write("<input type=\"hidden\" name=\"delseq\">\n");
      out.write("<input type=\"hidden\" name=\"ir_seq\">\n");
      out.write("<input type=\"hidden\" name=\"ir_title\">\n");
      out.write("<input type=\"hidden\" name=\"ir_type\" value=\"");
      out.print(ir_type );
      out.write("\">\n");
      out.write("<table style=\"width:850px%;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> <!-- width:100%; -->\n");
      out.write("\t<tr>\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/report/tit_icon.gif\" />\n");

	if(ir_type.equals("E")){out.print("<img src=\"../../images/report/tit_0302.gif\" />");}
	else {out.print("<img src=\"../../images/report/tit_0304.gif\" />");}
	//else if(ir_type.equals("D1")){out.print("<img src=\"../../images/report/tit_0303.gif\" />");}
	//else if(ir_type.equals("W")){out.print("<img src=\"../../images/report/tit_0304.gif\" />");}

      out.write("\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">보고서관리</td>\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">\n");

	if(ir_type.equals("E")){out.print("이슈보고서");}
	else {out.print("주간보고서");}
	//else if(ir_type.equals("D1")){out.print("일일보고서");}
	//else if(ir_type.equals("W")){out.print("주간보고서");}

      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../images/report/btn_allselect.gif\" onclick=\"checkAll(!document.fSend.checkall.checked);\" style=\"cursor:pointer\"/></td>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/report/btn_del.gif\" onclick=\"deleteReport();\" style=\"cursor:pointer;\"/></td>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:88px;\">\n");
      out.write("\t\t\t\t\t\t\t");
if(!ir_type.equals(ReportTypeConstants.emergencyVal)){
      out.write("<img src=\"../../images/report/btn_report_write.gif\" style=\"cursor:pointer\" onclick=\"goReportCreate();\"/>");
}
      out.write("\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td>       \n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout: fixed;\">\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"35%\"><col width=\"5%\"><col width=\"10%\"><col width=\"15%\"><col width=\"15%\"><col width=\"15%\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<th><input type=\"checkbox\" name=\"checkall\" id=\"reportChk\"  value=\"\" onclick=\"checkAll();\"></th>\n");
      out.write("\t\t\t\t\t\t<th>제목</th>\n");
      out.write("\t\t\t\t\t\t<th></th>\n");
      out.write("\t\t\t\t\t\t<th></th>\n");
      out.write("\t\t\t\t\t\t<th>작성일시</th>\n");
      out.write("\t\t\t\t\t\t<th>발송일시</th>\n");
      out.write("\t\t\t\t\t\t<th>메일발송</th>\n");
      out.write("\t\t\t\t\t</tr>\n");

	String wdate = "";
	String wtime = "";
	String date = "";
	String time = "";

	if(arrIrBean.size() > 0){
		for(int i=0; i < arrIrBean.size(); i++){
			irBean = new IssueReportBean();
			irBean = (IssueReportBean)arrIrBean.get(i);

      out.write("\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"reportchk\" value=\"");
      out.print(irBean.getIr_seq());
      out.write("\"></td>\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align:left;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer\" onclick=\"showDetail('");
      out.print(irBean.getIr_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(irBean.getIr_type());
      out.write("');\"><span style=\"width:100%;\">");
      out.print(irBean.getIr_title());
      out.write("</span></td>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/report/btn_report_tit_mondify.gif\" style=\"cursor:pointer\" onclick=\"ChangeTitle('");
      out.print(irBean.getIr_seq());
      out.write("')\"/></td>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/report/btn_report.gif\" onclick=\"showReport(");
      out.print(irBean.getIr_seq());
      out.write(");\" style=\"cursor:pointer\"/></td>\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(irBean.getFormatIr_regdate("MM/dd HH:mm"));
      out.write("</td>\n");
      out.write("\t\t\t\t\t\t<td>");
if(irBean.getIr_mailyn().equals("Y")){if(irBean.getIr_maildate()!=null){out.print(du.getDate(irBean.getIr_maildate(), "MM/dd HH:mm:ss"));}else{out.print("-");}}else{out.print("-");}
      out.write("</td>\n");
      out.write("\t\t\t\t\t\t<td>");
if(irBean.getIr_mailyn().equals("Y")){out.print("발송 완료");}else if(irBean.getIr_mailyn().equals("F")){out.print("발송실패");}else{out.print("발송대기");}
      out.write("</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
}}else{
      out.write("\n");
      out.write("\t\t\t\t\t<tr><td align=\"center\" colspan=\"7\" style=\"height:40px;font-weight:bold\">저장된 보고서가 없습니다.</td></tr>\n");
}
      out.write("\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../images/report/btn_allselect.gif\" onclick=\"checkAll(!document.fSend.checkall.checked);\" style=\"cursor:pointer\"/></td>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/report/btn_del.gif\"  onclick=\"deleteReport();\" style=\"cursor:pointer;\"/></td>\n");
      out.write("\t\t\t\t\t\t<td style=\"width:88px;\">\n");
      out.write("\t\t\t\t\t\t\t");
if(!ir_type.equals(ReportTypeConstants.emergencyVal)){
      out.write("<img src=\"../../images/report/btn_report_write.gif\" style=\"cursor:pointer\" onclick=\"goReportCreate();\"/>");
}
      out.write("\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 페이징 -->\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td style=\"text-align:center;\">\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" align=\"center\">\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<td>\n");
      out.write("\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(nowpage, totalPage,"","" ));
      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<!-- 페이징 -->\n");
      out.write("\t\t</table>\n");
      out.write("\t\t</td>\n");
      out.write("\t</tr>\n");
      out.write("</table>\n");
      out.write("</form>\n");
      out.write("<iframe id=\"processFrm\" name =\"processFrm\" width=\"0\" height=\"0\" style=\"display: none;\"></iframe>\n");
      out.write("</body>\n");
      out.write("</html>\r\n");
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
