package org.apache.jsp.riskv3.admin.mainissue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import risk.json.JSONArray;
import risk.util.ConfigUtil;
import risk.util.ParseRequest;
import risk.admin.mainissue.MainIssueMgr;
import java.util.ArrayList;
import java.util.*;
import risk.util.PageIndex;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.util.DateUtil;

public final class mainissue_005fedit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/mainissue/../../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	
	ParseRequest pr = new ParseRequest(request);	
	pr.printParams();
	DateUtil du = new DateUtil();
	
	
	String searchKey = pr.getString("searchKey");
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String mode = pr.getString("mode");
	String code 	= pr.getString("code");
	String name 	= pr.getString("name");
	JSONObject obj = null;
	
	
	if("insert".equals(mode)){
		//검색날짜 설정 : 기본 1일간 검색한다.
		String sCurrDate    = du.getCurrentDate();
		if (eDate.equals("")) eDate = du.getCurrentDate();
		if (sDate.equals("")) {
			du.addDay(-1);
			sDate = du.getDate();
		}
		
	}else if("update".equals(mode)){
		MainIssueMgr mi = new MainIssueMgr();
		
		obj = mi.getUpdateIssue(code);
	}
	
	IssueCodeMgr 	icm = new IssueCodeMgr();	
	IssueCodeBean icBean = new IssueCodeBean();

	ArrayList arrIcBean = new ArrayList();

	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);	
	
	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>POSCO</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<style>\r\n");
      out.write("iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("</style>\r\n");
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
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction goEdit(){\r\n");
      out.write("\t\tvar f = document.editForm;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(f.issue.value == \"\")\r\n");
      out.write("\t\t{\t\r\n");
      out.write("\t\t\talert('이슈를 선택해주세요'); return;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action='mainissue_prc.jsp';\r\n");
      out.write("\t\tf.submit();\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<iframe id=\"ifrm\" name=\"ifrm\" style=\"display: none;\"></iframe>\r\n");
      out.write("<form id=editForm name=editForm method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"");
      out.print(mode);
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<!-- <input type=\"hidden\" name=\"as_seq\"> -->\r\n");
      out.write("<input type=\"hidden\" name=\"as_seqs\">\r\n");
      out.write("<table style=\"width:400px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:370px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/receiver/tit_icon.gif\" /><img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tsrc=\"../../../images/admin/pressgroup/tit_002222.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">보도자료 관리</td>\r\n");
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
      out.write("\t\t\t<tr style=\"display: none\">\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/alimi/btn_allselect.gif\" onclick=\"allselect();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/btn_del.gif\" onclick=\"delList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t");
if("insert".equals(mode)){ 
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width: 80x;\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t            <td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>주요이슈 </strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"30\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t            \t<select id=\"issue\" name=\"issue\" style=\"min-width: 100;\">\r\n");
      out.write("\t\t\t\t            \t<option value='' >선택하세요</option>\r\n");
      out.write("\t\t\t\t            \t");

									arrIcBean = new ArrayList();
									arrIcBean = icm.GetType(13);
									String selected  = "selected";
									for (int i = 0; i < arrIcBean.size(); i++) {
									icBean = (IssueCodeBean) arrIcBean.get(i);
									
									if(!"0".equals(icBean.getIc_code()+"") ){
												
								
      out.write("  \t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value='");
      out.print(icBean.getIc_code() );
      out.write('\'');
      out.write('>');
      out.print(icBean.getIc_name() );
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}}
      out.write("\t\r\n");
      out.write("\t\t\t\t            \t</select>\r\n");
      out.write("\t\t\t\t            </td>\r\n");
      out.write("\t\t\t\t     </tr>\r\n");
      out.write("\t\t\t\t     <tr>       \r\n");
      out.write("\t\t\t\t            <td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>등록일자</strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"150\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t              <tr>\r\n");
      out.write("\t\t\t\t                <td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"sDate\" name=\"sDate\" value=\"");
      out.print(sDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('sDate'))\"/></td>\r\n");
      out.write("\t\t\t\t              </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t\t </tr>\t\t\r\n");
      out.write("\t\t\t\t\t <tr>\t\r\n");
      out.write("\t\t\t\t\t \t\t<td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\t            \r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>종료일자</strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"150\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t            \t<tr>\r\n");
      out.write("\t\t\t\t            \t<td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"eDate\" name=\"eDate\" value=\"");
      out.print(eDate);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('eDate'))\"/></td>\t\t\t\t                \r\n");
      out.write("\t\t\t\t                </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t     </tr>      \r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t");
}else if("update".equals(mode)){ 
      out.write("\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width: 80x;\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t            <td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>주요이슈 </strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"30\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t            \t<select id=\"issue\" name=\"issue\" style=\"min-width: 100;\">\r\n");
      out.write("\t\t\t\t            \t<option value=\"");
      out.print(obj.getString("code"));
      out.write('"');
      out.write('>');
      out.print(obj.getString("name"));
      out.write("</option>\r\n");
      out.write("\t\t\t\t            \t</select>\r\n");
      out.write("\t\t\t\t            </td>\r\n");
      out.write("\t\t\t\t     </tr>\r\n");
      out.write("\t\t\t\t     <tr>       \r\n");
      out.write("\t\t\t\t            <td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>등록일자</strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"150\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t              <tr>\r\n");
      out.write("\t\t\t\t                <td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"sDate\" name=\"sDate\" value=\"");
      out.print(obj.getString("sDate"));
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('sDate'))\"/></td>\r\n");
      out.write("\t\t\t\t              </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t\t </tr>\t\t\r\n");
      out.write("\t\t\t\t\t <tr>\t\r\n");
      out.write("\t\t\t\t\t \t\t<td width=\"5\"><img src=\"../../../images/issue/table_icon_01.gif\" width=\"16\" height=\"12\" /></td>\t            \r\n");
      out.write("\t\t\t\t            <td width=\"10\" class=\"b_text\"><strong>종료일자</strong></td>\r\n");
      out.write("\t\t\t\t            <td width=\"150\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t            \t<tr>\r\n");
      out.write("\t\t\t\t            \t<td width=\"86\"><input style=\"width:90px;text-align:center\" class=\"textbox\" type=\"text\" id=\"eDate\" name=\"eDate\" value=\"");
      out.print(obj.getString("eDate"));
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t                <td width=\"27\"><img src=\"../../../images/issue/btn_calendar.gif\" style=\"cursor:pointer\" onclick=\"calendar_sh(document.getElementById('eDate'))\"/></td>\t\t\t\t                \r\n");
      out.write("\t\t\t\t                </tr>\r\n");
      out.write("\t\t\t\t            </table></td>\r\n");
      out.write("\t\t\t\t     </tr>      \r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t<tr height=\"30px\">\r\n");
      out.write("\t\t\t\t<td style=\"text-align: right;vertical-align: middle;\">\r\n");
      out.write("\t\t\t\t\t<img src=\"../../../images/admin/mainissue/btn_save2.gif\" style=\"cursor: pointer;\" onclick=\"goEdit();\">\r\n");
      out.write("\t\t\t\t\t<img src=\"../../../images/admin/mainissue/btn_cancel.gif\" onclick=\"window.close();\" style=\"cursor:pointer;\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</table>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\t\t            \r\n");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" background=\"../../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>                                \t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"calendar_calclass\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"5\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
