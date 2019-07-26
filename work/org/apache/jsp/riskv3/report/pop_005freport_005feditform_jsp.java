package org.apache.jsp.riskv3.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.util.StringUtil;
import risk.issue.IssueReportMgr;
import risk.issue.IssueReportBean;
import risk.util.PageIndex;
import risk.issue.IssueReportBean;
import risk.util.*;
import java.net.URLEncoder;
import risk.namo.NamoMime;

public final class pop_005freport_005feditform_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	NamoMime namoMime = new NamoMime();
	
	String mode = pr.getString("mode","insert");
	String reportType = pr.getString("reportType");
	
	String url = "";
	String param = "";
	
	String i_seq = "";
	String ir_seq = "";
	
	String ir_html = null;
	String ir_title = null;
	String ir_type = null;
	String ir_sdate = null;
	String ir_edate = null;
	String ir_stime = null;
	String ir_etime = null;
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl + "crosseditor/upload";
	
	if(mode.equals("update")){
		
		IssueReportMgr irMgr = new IssueReportMgr();
		IssueReportBean irBean = new IssueReportBean();
		
		ir_type = pr.getString("ir_type","");
		ir_seq = pr.getString("ir_seq");
		irBean = irMgr.getReportBean(ir_seq);	
		ir_html = irBean.getIr_html();
		
	}else{
		
		//보고서 종류
		ir_type = pr.getString("ir_type","");		
		ir_title = pr.getString("ir_title","");
		
		//보고서 날짜
		ir_sdate = pr.getString("ir_sdate");
		ir_edate = pr.getString("ir_edate");
		
		ir_stime = pr.getString("ir_stime");
		ir_etime = pr.getString("ir_etime");
		
		//시간값 자릿수에 따른 처리
		//ir_stime = ir_stime+":00:00";
		//ir_etime = ir_etime+":00:00";
		
		if(ir_type.equals("D")){
			//String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			//String id_seqs_pro = pr.getString("id_seqs_pro", "");
			//String id_seqs_isu = pr.getString("id_seqs_isu", "");
			//url = siteUrl+"riskv3/report/report_day.jsp";
			//param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+ir_stime.replaceAll(":", "")+"&edate="+ir_edate.replaceAll("-", "")+ir_etime.replaceAll(":", "");
			//ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");	
		}else if(ir_type.equals("I")){//일일보고서 (주요 이슈 포함)
			String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			String id_seqs_pro = pr.getString("id_seqs_pro", "");
			String id_seqs_isu = pr.getString("id_seqs_isu", "");
			String issue_sDate = pr.getString("issue_sDate");
			String issue_eDate = pr.getString("issue_eDate");
			url = siteUrl+"riskv3/report/import_issue_daily_report.jsp";
			param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+"&stime="+ir_stime+"&edate="+ir_edate.replaceAll("-", "")+"&etime="+ir_etime+"&issue_sDate="+issue_sDate+"&issue_eDate="+issue_eDate;
			ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");
		}else if(ir_type.equals("D1")){//일일보고서 
			String id_seqs_ceo = pr.getString("id_seqs_ceo", "");
			String id_seqs_pro = pr.getString("id_seqs_pro", "");
			String id_seqs_isu = pr.getString("id_seqs_isu", "");
			url = siteUrl+"riskv3/report/issue_daily_report.jsp";
			param = "id_seqs_ceo="+id_seqs_ceo+"&id_seqs_pro="+id_seqs_pro+"&id_seqs_isu="+id_seqs_isu+"&sdate="+ir_sdate.replaceAll("-", "")+"&stime="+ir_stime+"&edate="+ir_edate.replaceAll("-", "")+"&etime="+ir_etime;
			ir_html = su.encodingRequestPageByPost(url+"?"+param, "utf-8");
		}
	}
	//최종 특수문자 태그 처리
	ir_html = su.ChangeString(ir_html.trim());	
	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>");
      out.print(SS_TITLE);
      out.write("</title>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../crosseditor/js/namo_scripteditor.js\"></script>\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction saveReport()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\t//f.mode.value = 'insert';\t\t\r\n");
      out.write("\t\tif(f.reportType.value == \"1\"){\r\n");
      out.write("\t\t\tif(document.Wec.MIMEValue){\r\n");
      out.write("\t\t\t\tf.ir_html.value = document.Wec.MIMEValue;\r\n");
      out.write("\t\t\t\tf.IEuse.value = 'Y';\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tf.IEuse.value = 'N';\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}else if(f.reportType.value == \"2\"){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tf.ir_html.value = CrossEditor.GetValue();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tf.action = 'issue_report_prc.jsp';\r\n");
      out.write("\t\tf.target = 'processFrm';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction viewChart(){\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tf.action = 'relationMap.jsp';\r\n");
      out.write("\t\tf.target = '';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");

if(ir_type.equals("D") && !mode.equals("update")){

      out.write("\r\n");
      out.write("<OBJECT codebase=\"http://java.sun.com/update/1.6.0/jinstall-6-windows-i586.cab#Version=6,0,0,0\" classid=\"clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284\" height=0 width=0>\r\n");
      out.write("<PARAM name=\"back\" value=\"true\">\r\n");
      out.write("</OBJECT>\r\n");

}

      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<iframe id=\"processFrm\" name =\"processFrm\" width=\"0\" height=\"0\" ></iframe>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"");
      out.print(mode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ir_title\" value=\"");
      out.print(pr.getString("ir_title",""));
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ir_type\" value=\"");
      out.print(ir_type);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ir_html\" value=\"");
      out.print(ir_html);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ir_seq\" value=\"");
      out.print(ir_seq);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"IEuse\" value=\"Y\">\r\n");
      out.write("<input type=\"hidden\" name=\"reportType\" value=\"");
      out.print(reportType);
      out.write("\">\r\n");

	if(!mode.equals("update")){

      out.write("\r\n");
      out.write("\t<input type=\"hidden\" name=\"sdate\" id=\"sdate\" value=\"");
      out.print(ir_sdate.replaceAll("-", ""));
      out.print(ir_stime.replaceAll(":", ""));
      out.write("\">\r\n");
      out.write("\t<input type=\"hidden\" name=\"edate\" id=\"edate\" value=\"");
      out.print(ir_edate.replaceAll("-", ""));
      out.print(ir_etime.replaceAll(":", ""));
      out.write("\">\r\n");

	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<table width=\"730\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t<!-- <img src=\"../../images/report/btn_save2.gif\"  hspace=\"5\" onclick=\"viewChart();\" style=\"cursor:pointer;\"> -->\r\n");
      out.write("\t\t\t&nbsp;<img src=\"../../images/report/btn_save2.gif\"  hspace=\"5\" onclick=\"saveReport();\" style=\"cursor:pointer;\">\r\n");
      out.write("\t\t\t&nbsp;<img src=\"../../images/report/btn_cancel.gif\" onClick=\"window.close();\" style=\"cursor:pointer;\">&nbsp;&nbsp;\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"5\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");

	if(reportType.equals("1")){

      out.write("\r\n");
      out.write("<SCRIPT language=\"javascript\" src=\"/namo/NamoWec7.js\"></SCRIPT>\t\t\t\r\n");
      out.write("<SCRIPT language=\"JScript\" FOR=\"Wec\" EVENT=\"OnInitCompleted()\">\r\n");
      out.write("\t\t\t  var form = document.fSend;\t\t\t\t\r\n");
      out.write("\t\t\t  var wec = document.Wec;\r\n");
      out.write("\t\t\t  wec.Value = form.ir_html.value;\t\t\t\t  \t \r\n");
      out.write("</SCRIPT>\r\n");

	}else if(reportType.equals("2")){

      out.write("\t\t\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\t\tvar CrossEditor = new NamoSE(\"namo\");\r\n");
      out.write("\t\tCrossEditor.params.ImageSavePath = \"");
      out.print(imgUrl);
      out.write("\";\r\n");
      out.write("\t\tCrossEditor.EditorStart();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction OnInitCompleted(e){\r\n");
      out.write("\t\t  e.editorTarget.SetValue(document.fSend.ir_html.value); // 컨텐츠 내용 에디터에 삽입\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t\t\r\n");
		
	}

      out.write("\r\n");
      out.write("</form>\r\n");
      out.write("</body>    \r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
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
