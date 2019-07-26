package org.apache.jsp.riskv3.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.issue.IssueDataBean;
import risk.issue.IssueMgr;
import java.util.ArrayList;
import java.util.List;
import risk.util.ParseRequest;
import risk.admin.member.MemberGroupBean;
import risk.admin.member.MemberDao;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.search.MetaMgr;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.util.DateUtil;
import risk.sms.AddressBookDao;
import risk.sms.AddressBookGroupBean;
import risk.admin.membergroup.membergroupBean;
import java.util.Iterator;
import risk.admin.keyword.KeywordMng;
import risk.admin.info.*;
import risk.admin.site.SiteBean;
import risk.admin.site.SiteMng;
import risk.admin.keyword.KeywordBean;
import risk.admin.site.SitegroupBean;

public final class issue_005freport_005fcreater_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");

	DateUtil 		du 		= new DateUtil();
	ParseRequest pr = new ParseRequest(request);
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();	
	IssueCodeBean ICBean = null;
	MemberDao mDao = new MemberDao();
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	ArrayList allist = new ArrayList();
	
	pr.printParams();
	String sDate = null;		
	String eDate = null;
	String ir_type = null;
	String ir_title = null;
	
	//이슈 코드
	icMgr.init(0);
	
	ir_type = pr.getString("ir_type", "D1");
	boolean listViewChk = false;
	
	if(ir_type.equals("D1"))
	{
		ir_title = "주간 보고서 ("+du.getCurrentDate("yyyy.MM.dd")+")";		
		sDate    = du.addDay(du.getCurrentDate(),-1,"yyyy-MM-dd");
		eDate    = du.addDay(du.getCurrentDate(),0,"yyyy-MM-dd");		
		listViewChk = false;
	
	}else if(ir_type.equals("I")){
		ir_title = "주간 보고서 - 주요 이슈 포함("+du.getCurrentDate("MM/dd")+")";		
		sDate    = du.addDay(du.getCurrentDate(),-1,"yyyy-MM-dd");
		eDate    = du.addDay(du.getCurrentDate(),0,"yyyy-MM-dd");		
		listViewChk = true;
	}
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/base.css\" />\r\n");
      out.write("<style>\r\n");
      out.write("iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("</style>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/jquery-ui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("dashboard/asset/js/design.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/reportCalendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("\tvar list_view_chk = '");
      out.print(listViewChk);
      out.write("';\r\n");
      out.write("\tvar tempDateTime = 0;\r\n");
      out.write("\r\n");
      out.write("\tfunction loadList()\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\thideSetDate(checkIrTypeValue());\r\n");
      out.write("\t\tilu.sendRequest();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction checkIrTypeValue(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar irType;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=radioChange]\").each(function(){\r\n");
      out.write("\t\t\tif($(this).is(\":checked\")){\r\n");
      out.write("\t\t\t\tirType = $(this).val(); \t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=ir_type]\").val(irType);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn irType;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction java_all_trim(a) {\r\n");
      out.write("        for (; a.indexOf(' ') != -1 ;) {\r\n");
      out.write("          a = a.replace(' ','');\r\n");
      out.write("        }\r\n");
      out.write("        return a;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction AddDate( day ) {\r\n");
      out.write("\r\n");
      out.write("        var newdate = new Date();\r\n");
      out.write("\t\tvar resultDateTime;\r\n");
      out.write("\r\n");
      out.write("\t\tif(tempDateTime==0)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\ttempDateTime = newdate.getTime();\r\n");
      out.write("\t\t}  \r\n");
      out.write("\t\t\r\n");
      out.write("              \r\n");
      out.write("\t\tresultDateTime = tempDateTime + ( day * 24 * 60 * 60 * 1000);      \r\n");
      out.write("        newdate.setTime(resultDateTime);\r\n");
      out.write("\t\t\r\n");
      out.write("        last_ndate = newdate.toLocaleString();\r\n");
      out.write("\r\n");
      out.write("        last_date = java_all_trim(last_ndate);\r\n");
      out.write("        last_year = last_date.substr(0,4);\r\n");
      out.write("        last_month = last_date.substr(5,2);\r\n");
      out.write("        last_mon = last_month.replace('월','');\r\n");
      out.write("\r\n");
      out.write("        if(last_mon < 10) {\r\n");
      out.write("\r\n");
      out.write("            last_m = 0+last_mon;\r\n");
      out.write("            last_day = last_date.substr(7,2);\r\n");
      out.write("            last_da = last_day.replace('일','');\r\n");
      out.write("\r\n");
      out.write("            if(last_da < 10) {\r\n");
      out.write("                last_d = 0+last_da;\r\n");
      out.write("            }else{\r\n");
      out.write("                last_d = last_da;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("        }else{\r\n");
      out.write("            last_m = last_mon;\r\n");
      out.write("\r\n");
      out.write("            last_day = last_date.substr(8,2);\r\n");
      out.write("            last_da = last_day.replace(\"일\",\"\");\r\n");
      out.write("\r\n");
      out.write("            if(last_da < 10) {\r\n");
      out.write("                last_d = 0+last_da;\r\n");
      out.write("            }else{\r\n");
      out.write("                last_d = last_da;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        last_time = last_year + '-' + last_m + '-' + last_d;\r\n");
      out.write("\r\n");
      out.write("        return last_time;\r\n");
      out.write("        \r\n");
      out.write("        \r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\tfunction changeType(ir_type)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\tif(ir_type=='D1')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tf.ir_type.value = 'D1';\r\n");
      out.write("\t\t\tf.ir_title.value='주간보고서(");
      out.print(du.getCurrentDate("MM/dd"));
      out.write(")';\r\n");
      out.write("\t\t\t$(\"#tr_issue_date\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else if(ir_type=='I'){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tf.ir_type.value = 'I';\r\n");
      out.write("\t\t\tf.ir_title.value='주간 보고서 - 주요 이슈 포함(");
      out.print(du.getCurrentDate("MM/dd"));
      out.write(")';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#tr_issue_date\").css(\"display\", \"\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tissueListload();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t//관련 정보 리스트 관련\t\r\n");
      out.write("\t$(document).ready(issueListload);\r\n");
      out.write("\r\n");
      out.write("\tfunction issueListload()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tajax.post2('aj_ceoDataList.jsp','fSend','ceoDataList','ajax-loader2.gif');\t\r\n");
      out.write("\t\tajax.post2('aj_productDataList.jsp','fSend','productDataList','ajax-loader2.gif');\r\n");
      out.write("\t\tajax.post2('aj_issueDataList.jsp','fSend','issueDataList','ajax-loader2.gif');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//Url 링크\r\n");
      out.write(" \tvar chkPop = 1;\r\n");
      out.write(" \t\r\n");
      out.write("\tfunction hrefPop(url){\r\n");
      out.write("\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');\r\n");
      out.write("\t\tchkPop++;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//체크박스\r\n");
      out.write("    function checkAll(chk) {\r\n");
      out.write("\t\tif(chk == \"ceo\"){\r\n");
      out.write("\t\t\tif($(\"[name=checkall_ceo]\").is(\":checked\")){\r\n");
      out.write("\t\t\t\t$(\"[name=ceoCheck]\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$(\"[name=ceoCheck]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else if(chk == \"pro\"){\r\n");
      out.write("\t\t\tif($(\"[name=checkall_pro]\").is(\":checked\")){\r\n");
      out.write("\t\t\t\t$(\"[name=proCheck]\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$(\"[name=proCheck]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tif($(\"[name=checkall_isu]\").is(\":checked\")){\r\n");
      out.write("\t\t\t\t$(\"[name=isuCheck]\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$(\"[name=isuCheck]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write(" \t}\r\n");
      out.write("\r\n");
      out.write(" \t////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////\r\n");
      out.write("\t\r\n");
      out.write("\t////////////////////수신자 리스트 //////////////////////\r\n");
      out.write("\t//$(document).ready(pageInit);\r\n");
      out.write("\r\n");
      out.write("\tfunction pageInit()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tloadList1();\r\n");
      out.write("\t\tloadList2();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction loadList1()\r\n");
      out.write("\t{\t\r\n");
      out.write("\t\tajax.post('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction findList1()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.report_receiver;\r\n");
      out.write("\t\tvar f2 = document.report_selectedReceiver;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tf.selectedAbSeq.value = f2.selectedAbSeq.value;\t\t\r\n");
      out.write("\t\tajax.post('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');\t\t\t\r\n");
      out.write("\t}\t\r\n");
      out.write("\t/////////////////////////////////////////////////////\t\r\n");
      out.write("\t///////////////////선택된 수신자 리스트/////////////////\t\t\t\t\t\t\r\n");
      out.write("\tfunction selectedListCheck(ab_seq)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.report_selectedReceiver;\r\n");
      out.write("\t\tvar check = true;\r\n");
      out.write("\t\tvar list = new Array();\r\n");
      out.write("\t\tlist = f.selectedAbSeq.value.split(',');\r\n");
      out.write("\r\n");
      out.write("\t\tfor(var i =0; i<list.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(list[i]==ab_seq)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tcheck = false;\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn check;\r\n");
      out.write("\t}\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction selectRightMove(ab_seq)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.report_selectedReceiver;\r\n");
      out.write("\t\r\n");
      out.write("\t\tif(!selectedListCheck(ab_seq)){alert('이미 선택된 수신자 입니다.');\treturn;}\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(f.selectedAbSeq.value!='')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tf.selectedAbSeq.value += \",\"+ ab_seq;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tf.selectedAbSeq.value = ab_seq;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfindList1(); \t\t\r\n");
      out.write("\t\tfindList2();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction addReceiver() \r\n");
      out.write("\t{\r\n");
      out.write("\t \twindow.open('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/receiver_detail.jsp?mode=add','adduser', 'width=800,height=500,scrollbars=no');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tfunction editReceiver(ab_seq) \r\n");
      out.write("\t{\r\n");
      out.write("\t \twindow.open('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/receiver_detail.jsp?mode=edit&abSeq='+ab_seq,'adduser', 'width=800,height=1000,scrollbars=no');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction delReceiver(ab_seq) \r\n");
      out.write("\t{\r\n");
      out.write("\t\tif(window.confirm(\"삭제하시겠습니까?\"))\r\n");
      out.write("\t\t{\r\n");
      out.write("\t \t\twindow.open('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/receiver_prc.jsp?mode=del&seqList='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction delAbSeq(ab_seq)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.report_selectedReceiver;\r\n");
      out.write("\t\tvar list = new Array();\r\n");
      out.write("\t\tlist = f.selectedAbSeq.value.split(',');\r\n");
      out.write("\r\n");
      out.write("\t\tf.selectedAbSeq.value = '';\r\n");
      out.write("\t\tfor(var i =0; i<list.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(list[i]!=ab_seq)\r\n");
      out.write("\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\tif(f.selectedAbSeq.value!='')\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tf.selectedAbSeq.value += \",\"+ list[i];\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tf.selectedAbSeq.value = list[i];\r\n");
      out.write("\t\t\t\t} \r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction selectLeftMove(ab_seq)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.report_selectedReceiver;\r\n");
      out.write("\t\tdelAbSeq(ab_seq);\r\n");
      out.write("\t\tfindList1();\t\t\r\n");
      out.write("\t\tfindList2();\r\n");
      out.write("\t}\t\r\n");
      out.write("\r\n");
      out.write("\tfunction loadList2()\r\n");
      out.write("\t{\t\r\n");
      out.write("\t\tajax.post('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction findList2()\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\tajax.post('");
      out.print(SS_URL);
      out.write("riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');\t\t\t\t\t\r\n");
      out.write("\t}\t\t\t\t\r\n");
      out.write("\t//////////////////////////////////////////////////////\r\n");
      out.write("\r\n");
      out.write("\tfunction chkIssueRisk(no){\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tvar risk1='';\r\n");
      out.write("\t\tvar obj = eval('document.all.issue_0'+no+'_risk');\r\n");
      out.write("\t\t//var obj = document.getElementById('issue_0'+no+'_risk');\r\n");
      out.write("\t\tif(obj){\r\n");
      out.write("\t\t\tif(obj.length){\r\n");
      out.write("\t\t\t\tfor( var i = 0; i<obj.length; i++)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tif(obj[i].checked == true )\r\n");
      out.write("\t\t\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\t\t\tif(risk1==''){\r\n");
      out.write("\t\t\t\t\t\t\trisk1 = obj[i].value;\t\t\t\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\trisk1 += ','+obj[i].value;\r\n");
      out.write("\t\t\t\t\t\t}\t\t\t\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\trisk1 = obj.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn risk1;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction preview(reType)\r\n");
      out.write("\t{\t\r\n");
      out.write("\t\tdocument.fSend.reportType.value = reType;\r\n");
      out.write("\t\tvar sdate = $(\"#issue_sDate\").val();\r\n");
      out.write("\t\tvar edate = $(\"#issue_eDate\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar sDateArray = sdate.split(\"-\"); \r\n");
      out.write("\t\tvar sdateObj = new Date(sDateArray[0], Number(sDateArray[1])-1, sDateArray[2]);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar eDateArray = edate.split(\"-\"); \r\n");
      out.write("\t\tvar edateObj = new Date(eDateArray[0], Number(eDateArray[1])-1, eDateArray[2]);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar betweenDay =  ((edateObj.getTime()  - sdateObj.getTime())/1000/60/60/24)+1;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(betweenDay > 14){\r\n");
      out.write("\t\t\talert(\"주요이슈 기간은 최대 14일 조회 가능 합니다. 다시 입력해주세요.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(betweenDay < 0){\r\n");
      out.write("\t\t\talert(\"주요이슈 기간을 잘 못 입력하셨습니다. 다시 입력해주세요.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tvar ir_type = checkIrTypeValue();\t\t\r\n");
      out.write("\t\t//checkSetting();\r\n");
      out.write("\t\t//setCodeName();\r\n");
      out.write("\t\tvar id_seqs = \"\";\r\n");
      out.write("\t\t$(\"[name=ceoCheck]:checked\").each(function(){\r\n");
      out.write("\t\t\tif(id_seqs ==\"\"){\r\n");
      out.write("\t\t\t\tid_seqs = $(this).val();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tid_seqs += \",\"+$(this).val();\r\n");
      out.write("\t\t\t}\t\t\t\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$(\"[name=id_seqs_ceo]\").val(id_seqs);\r\n");
      out.write("\t\tid_seqs = \"\";\r\n");
      out.write("\t\t$(\"[name=proCheck]:checked\").each(function(){\r\n");
      out.write("\t\t\tif(id_seqs ==\"\"){\r\n");
      out.write("\t\t\t\tid_seqs = $(this).val();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tid_seqs += \",\"+$(this).val();\t\r\n");
      out.write("\t\t\t}\t\t\t\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$(\"[name=id_seqs_pro]\").val(id_seqs);\r\n");
      out.write("\t\tid_seqs = \"\";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=isuCheck]:checked\").each(function(){\r\n");
      out.write("\t\t\tif(id_seqs ==\"\"){\r\n");
      out.write("\t\t\t\tid_seqs = $(this).val();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tid_seqs += \",\"+$(this).val();\t\r\n");
      out.write("\t\t\t}\t\t\t\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$(\"[name=id_seqs_isu]\").val(id_seqs);\r\n");
      out.write("\t\tid_seqs = \"\";\r\n");
      out.write("\t\twindow.open('about:blank', 'reportpagefornamo', 'width=860,height=850,scrollbars=yes,status=no,resizable=no');\t\t\r\n");
      out.write("\t\t//f.mode.value = 'view';\r\n");
      out.write("\t\tf.ir_type.valvalue = ir_type;\r\n");
      out.write("\t\tf.action = 'pop_report_editform.jsp';\r\n");
      out.write("\t\tf.target = 'reportpagefornamo';\r\n");
      out.write("\t\tf.method= 'post';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction check_re(target){\r\n");
      out.write("\t\tif(target ==\"1\"){\r\n");
      out.write("\t\t\t$(\"#d_radio\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t$(\"#i_radio\").attr(\"checked\", false);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"#d_radio\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t$(\"#i_radio\").attr(\"checked\", true);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" method=\"post\">\r\n");
      out.write("<!-- 주간보고서 - 보고서 유형 값 세팅 -->\r\n");
      out.write("<input name=\"ir_type\" id=\"ir_type\" value=\"\" type=\"hidden\" />\r\n");
      out.write("<input name=\"id_seqs_ceo\" id=\"id_seqs_ceo\" value=\"\" type=\"hidden\" /><!-- 자사/CEO 관련  -->\r\n");
      out.write("<input name=\"id_seqs_pro\" id=\"id_seqs_pro\" value=\"\" type=\"hidden\" /><!-- 상품 관련  -->\r\n");
      out.write("<input name=\"id_seqs_isu\" id=\"id_seqs_isu\" value=\"\" type=\"hidden\" /><!-- 주요이슈 관련  -->\r\n");
      out.write("<input name=\"reportType\" type=\"hidden\">\r\n");
      out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\" style=\"width:auto\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;margin-left:20px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"al\"><img src=\"../../images/report/tit_icon.gif\" /><img src=\"../../images/report/tit_0301.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">보고서관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">보고서작성</td>\r\n");
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
      out.write("\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"al\"><span class=\"sub_tit2\">보고서 설정</span></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<!-- <td class=\"search_box\"> -->\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 검색 -->\r\n");
      out.write("\t\t<div class=\"article\">\r\n");
      out.write("\t\t\t<div class=\" ui_searchbox_00\">\r\n");
      out.write("\t\t\t\t<div class=\"c_sort visible\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"title wid_100px\"><span class=\"icon\">-</span>보고서 유형</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"radio\" value=\"D1\" name=\"radioChange\" id=\"d_radio\" onclick=\"changeType('D1');\" ");
if(ir_type.equals("D1")){out.print("checked");}
      out.write(" ><label for=\"d_radio\"><span class=\"radio_00\"></span>주간 보고서</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"dcp\"><input type=\"radio\" value=\"I\" name=\"radioChange\" id=i_radio onclick=\"changeType('I');\" ");
if(ir_type.equals("I")){out.print("checked");}
      out.write(" ></span><label for=\"i_radio\"><span class=\"radio_00\"></span>주간 보고서(주요 이슈 포함)</label></div>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"title wid_100px\"><span class=\"icon\">-</span>보고서 제목</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"input_title_00\" class=\"ui_input_02\" name=\"ir_title\" value=\"");
      out.print(ir_title);
      out.write("\"><label for=\"input_title_00\" class=\"invisible\">보고서 제목</label>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"title wid_100px\"><span class=\"icon\">-</span>보고서 기간</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t<input onchange=\"issueListload();\" name=\"ir_sdate\" id=\"ir_sdate\" value=\"");
      out.print(sDate);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_first\" readonly><label for=\"ir_sdate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"ir_stime\" class=\"ui_select_04\" onchange=\"issueListload();\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t~\r\n");
      out.write("\t\t\t\t\t\t\t<input onchange=\"issueListload();\" name=\"ir_edate\" id=\"ir_edate\" value=\"");
      out.print(eDate);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_last\" readonly><label for=\"ir_edate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"ir_etime\" class=\"ui_select_04\" onchange=\"issueListload();\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"fs11\">※ 수집시간 기준으로 작성됩니다.</span>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"issue_date_row\" class=\"ui_row_00 last-child\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"title wid_100px\"><span class=\"icon\">-</span>주요이슈 기간</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t<input name=\"issue_sDate\" id=\"issue_sDate\" value=\"");
      out.print(sDate);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_first\" readonly><label for=\"issue_sDate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t~\r\n");
      out.write("\t\t\t\t\t\t\t<input name=\"issue_eDate\" id=\"issue_eDate\" value=\"");
      out.print(eDate);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_last\" readonly><label for=\"issue_eDate\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"fs11\">※ 최대 조회가능 기간은 14일 입니다.</span>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\treport_change($( \"input[name='radioChange']\" ).val());\t\t// 최초에 한번 실행(불필요시 삭제 하고 사용)\r\n");
      out.write("\t\t\t\t\t$( \"input[name='radioChange']\" ).change(function($e){\r\n");
      out.write("\t\t\t\t\t\treport_change( this.value );\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\tfunction report_change($value){\r\n");
      out.write("\t\t\t\t\t\tif($value == \"I\") {\r\n");
      out.write("\t\t\t\t\t\t\t$( \"#issue_date_row\" ).prev().removeClass( \"last-child\" );\r\n");
      out.write("\t\t\t\t\t\t\t$( \"#issue_date_row\" ).show();\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t$( \"#issue_date_row\" ).prev().addClass( \"last-child\" );\r\n");
      out.write("\t\t\t\t\t\t\t$( \"#issue_date_row\" ).hide();\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t</script>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- <div class=\"ui_searchbox_toggle\">\r\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"btn_toggle active invisible\">검색조건 열기/닫기</a>\r\n");
      out.write("\t\t\t</div> -->\r\n");
      out.write("\t\t<!-- // 검색 -->\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"al\" style=\"height:50px;padding-top:20px;\"><span class=\"sub_tit2\">자사/CEO 관련</span></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"ceoDataList\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"20\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"al\" style=\"height:50px;padding-top:20px;\"><span class=\"sub_tit2\">상품 관련</span></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"productDataList\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"20\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"al\" style=\"height:50px;padding-top:20px;\"><span class=\"sub_tit2\">온라인 관련 정보</span></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"issueDataList\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t<img src=\"../../images/report/btn_report_write.gif\" onclick=\"preview(1);\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t<img src=\"../../images/report/btn_report_write2.gif\" onclick=\"preview(2);\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr><td><img src=\"../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td></tr>\r\n");
      out.write("\t<tr><td align=\"center\" background=\"../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr><td height=\"6\"></td></tr>\r\n");
      out.write("\t\t\t<tr><td id=\"calendar_calclass\"></td></tr>\r\n");
      out.write("\t\t\t<tr><td height=\"5\"></td></tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr><td><img src=\"../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td></tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
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
