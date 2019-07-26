package org.apache.jsp.riskv3.admin.receiver;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.sms.AddressBookDao;
import risk.sms.AddressBookBean;
import risk.util.ParseRequest;
import risk.util.PageIndex;

public final class receiver_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/receiver/../../inc/sessioncheck.jsp");
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

	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;
	pr.printParams();
	
	int rowCnt = 10;
	int iNowPage        = pr.getInt("nowpage",1);
	String searchWord = pr.getString("searchWord","");
	
	if( !pr.getString("seqList").equals("") ){
		seqList = pr.getString("seqList");
	}
	
	int iTotalCnt= AddDao.addressCount( searchWord );
	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
		
	ArrayList ArrMailUserBean;
	ArrMailUserBean = AddDao.addressList( iNowPage, rowCnt, searchWord );
	
	String strMsg = "";
	strMsg = " <b>"+iTotalCnt+"건</b>, "+iNowPage+"/"+iTotalPage+" pages";

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tvar allcheck = 0;\r\n");
      out.write("\tfunction receverAdd()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tlocation.href = 'receiver_detail.jsp?mode=add';\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction receverDetail( seq )\r\n");
      out.write("\t{\r\n");
      out.write("\t\tlocation.href = 'receiver_detail.jsp?abSeq='+seq+'&mode=edit';\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction allselect()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar frm = document.all;\r\n");
      out.write("\t\tif( frm.tt ) {\r\n");
      out.write("\t\t\tif( allcheck == 0 ) {\r\n");
      out.write("\t\t\t\tif( frm.tt.length > 1 ) {\r\n");
      out.write("\t\t\t\t\tfor( i=0; i< frm.tt.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.tt[i].checked = true;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.tt.checked = true;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 1;\r\n");
      out.write("\t\t   \t}else {\r\n");
      out.write("\t\t   \t\tif( frm.tt.length > 1 ) {\r\n");
      out.write("\t\t\t   \t\tfor( i=0; i< frm.tt.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.tt[i].checked = false;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.tt.checked = false;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 0;\r\n");
      out.write("\t\t   \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction delList()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar kg_list = '';\r\n");
      out.write("    \tvar i = 0;\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar frm = document.fSend;\r\n");
      out.write("    \t\r\n");
      out.write("    \tif ( confirm(\"삭제 하시겠습니까?\" ) ) {\r\n");
      out.write("    \t\tif( frm.ab_seq ) {\r\n");
      out.write("\t    \t\tif( frm.ab_seq.length > 1 ) {\r\n");
      out.write("\t\t    \t\tfor( i=0; i< frm.ab_seq.length; i++ )\r\n");
      out.write("\t\t    \t\t{\r\n");
      out.write("\t\t    \t\t\tif( frm.ab_seq[i].checked == true ) {\r\n");
      out.write("\t\t    \t\t\t\tif( kg_list.length > 0 ) {\r\n");
      out.write("\t\t    \t\t\t\t\tkg_list = kg_list+','+frm.ab_seq[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}else {\r\n");
      out.write("\t\t    \t\t\t\t\tkg_list = frm.ab_seq[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}\r\n");
      out.write("\t\t    \t\t\t}\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}else {\r\n");
      out.write("\t\t    \t\tif( frm.ab_seq.checked == true ) {\r\n");
      out.write("\t\t    \t\t\tkg_list = frm.ab_seq.value;\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t    \r\n");
      out.write("\t\t    if( kg_list.length > 0 ) {\r\n");
      out.write("\t\t\t    frm.seqList.value = kg_list;\r\n");
      out.write("\t\t\t    frm.mode.value = 'del';\r\n");
      out.write("\t\t\t    frm.target = '';\r\n");
      out.write("\t\t\t    frm.action = 'receiver_prc.jsp';\r\n");
      out.write("\t\t\t    frm.submit();\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t    \t\talert('삭제할 수신자를 선택하세요');\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction pageClick( paramUrl ) {\r\n");
      out.write("\t\tvar f = document.getElementById('fSend');\r\n");
      out.write("        f.action = \"receiver_list.jsp\" + paramUrl;\r\n");
      out.write("        f.submit();\r\n");
      out.write("\t\t/*\r\n");
      out.write("\t\tdocument.fSend.nowpage.value = paramUrl.substr(paramUrl.indexOf(\"=\")+1, paramUrl.length);\r\n");
      out.write("\t\tdocument.fSend.submit();\r\n");
      out.write("\t\t*/\r\n");
      out.write("    }\r\n");
      out.write("\t\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form id=\"fSend\" name=\"fSend\" action=\"receiver_list.jsp\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(iNowPage);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"seqList\" value=\"\">\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/receiver/tit_icon.gif\" /><img src=\"../../../images/admin/receiver/tit_0506.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">정보수신자관리</td>\r\n");
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
      out.write("\t\t\t\t\t\t<th style=\"height:30px;\">이름검색</th>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle\"><input style=\"width:460px;vertical-align:middle\" class=\"textbox\" type=\"text\" name=\"searchWord\" value=\"");
      out.print(searchWord);
      out.write("\" onkeydown=\"javascript:if (event.keycode == 13) { fsend.submit(); }\"><img src=\"../../../images/admin/receiver/btn_search.gif\" onclick=\"fSend.submit();\" style=\"cursor:pointer;vertical-align:middle\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/receiver/btn_allselect.gif\" onclick=\"allselect();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/receiver/btn_del.gif\" onclick=\"delList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:88px;\"><img src=\"../../../images/admin/receiver/btn_useradd2.gif\" onclick=\"receverAdd();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"15%\"><col width=\"15%\"><col width=\"15%\"><col width=\"20%\"><col width=\"30%\">\r\n");
      out.write("\t\t\t\t\t<tr>      \r\n");
      out.write("\t\t\t\t\t\t<th><input type=\"checkbox\" id=\"tt\" value=\"checkbox\" onclick=\"allselect();\"></th>       \r\n");
      out.write("\t\t\t\t\t\t<th>성명</th>\r\n");
      out.write("\t\t\t\t\t\t<th>부서</th>\r\n");
      out.write("\t\t\t\t\t\t<th>직급</th>\r\n");
      out.write("\t\t\t\t\t\t<th>보고서 수신</th>\r\n");
      out.write("\t\t\t\t\t\t<th>연락처</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

  	for(int i=0; i < ArrMailUserBean.size(); i++){
		AddBean = new AddressBookBean();
		AddBean = 	(AddressBookBean)ArrMailUserBean.get(i);
		
		String smsReceive = "";
		String reportReceive = "";
		String[] sms = AddBean.getMab_sms_receivechk().split(",");
		for(int j=0; j < sms.length; j++){
			if(sms[j].equals("1") |	sms[j].equals("2") | sms[j].equals("3") | sms[j].equals("4")){
				smsReceive = "Y";
			}else {
			smsReceive = "N";
			}
		}
		if(AddBean.getMab_issue_receivechk().equals("1"))reportReceive = "I";
		if(AddBean.getMab_report_day_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/D" ; 
		if(AddBean.getMab_report_week_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/W" ;

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"ab_seq\" id=\"tt\" value=");
      out.print(AddBean.getMab_seq());
      out.write("></td>\r\n");
      out.write("\t\t\t\t\t\t<td onclick=\"receverDetail( ");
      out.print(AddBean.getMab_seq());
      out.write(" );\" style=\"cursor:pointer;\">");
      out.print(AddBean.getMab_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(AddBean.getMab_dept());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(AddBean.getMab_pos());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(reportReceive);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(AddBean.getMab_mobile());
      out.write(" </td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:128px;\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/receiver/btn_allselect.gif\" onclick=\"allselect();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/receiver/btn_del.gif\" onclick=\"delList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\"><img src=\"../../../images/admin/receiver/btn_useradd2.gif\" onclick=\"receverAdd();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" align=\"center\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(iNowPage, iTotalPage,"","" ));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\r\n");
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
