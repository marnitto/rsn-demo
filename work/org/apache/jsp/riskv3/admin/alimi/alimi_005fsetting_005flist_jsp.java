package org.apache.jsp.riskv3.admin.alimi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.mobile.AlimiSettingDao;
import risk.mobile.AlimiSettingBean;
import risk.util.ParseRequest;
import java.util.ArrayList;
import java.util.*;
import risk.util.PageIndex;

public final class alimi_005fsetting_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/alimi/../../inc/sessioncheck.jsp");
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

	
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = new AlimiSettingBean();
	ArrayList arrAlimiSetList = new ArrayList();
	
	pr.printParams();
	
	int rowCnt = 10;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	arrAlimiSetList = asDao.getAlimiSetList(iNowPage , rowCnt ,"","Y");
	
	int iTotalCnt= asDao.getListCnt();	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tvar allcheck = 0;\r\n");
      out.write("\r\n");
      out.write("\tfunction asSetInsert()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_setting_list;\r\n");
      out.write("\t\tf.mode.value='INSERT';\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action = 'alimi_setting_detail.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tfunction asSetUpdate( as_seq )\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_setting_list;\r\n");
      out.write("\t\tf.as_seq.value = as_seq;\r\n");
      out.write("\t\tf.mode.value ='UPDATE';\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action = 'alimi_setting_detail.jsp';\r\n");
      out.write("\t\tf.submit();\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tfunction allselect()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar frm = document.all;\r\n");
      out.write("\t\tif( frm.chkNum ) {\r\n");
      out.write("\t\t\tif( allcheck == 0 ) {\r\n");
      out.write("\t\t\t\tif( frm.chkNum.length > 1 ) {\r\n");
      out.write("\t\t\t\t\tfor( i=0; i< frm.chkNum.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.chkNum[i].checked = true;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.chkNum.checked = true;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 1;\r\n");
      out.write("\t\t   \t}else {\r\n");
      out.write("\t\t   \t\tif( frm.chkNum.length > 1 ) {\r\n");
      out.write("\t\t\t   \t\tfor( i=0; i< frm.chkNum.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.chkNum[i].checked = false;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.chkNum.checked = false;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 0;\r\n");
      out.write("\t\t   \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction delList()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar selectNum = '';\r\n");
      out.write("    \tvar i = 0;\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar frm = document.alimi_setting_list;\r\n");
      out.write("    \t\r\n");
      out.write("    \tif ( confirm(\"삭제 하시겠습니까?\" ) ) {\r\n");
      out.write("    \t\tif( frm.chkNum ) {\r\n");
      out.write("\t    \t\tif( frm.chkNum.length > 1 ) {\r\n");
      out.write("\t\t    \t\tfor( i=0; i< frm.chkNum.length; i++ )\r\n");
      out.write("\t\t    \t\t{\r\n");
      out.write("\t\t    \t\t\tif( frm.chkNum[i].checked == true ) {\r\n");
      out.write("\t\t    \t\t\t\tif( selectNum.length > 0 ) {\r\n");
      out.write("\t\t    \t\t\t\t\tselectNum = selectNum+','+frm.chkNum[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}else {\r\n");
      out.write("\t\t    \t\t\t\t\tselectNum = frm.chkNum[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}\r\n");
      out.write("\t\t    \t\t\t}\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}else {\r\n");
      out.write("\t\t    \t\tif( frm.chkNum.checked == true ) {\r\n");
      out.write("\t\t    \t\t\tselectNum = frm.chkNum.value;\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t//alert(selectNum);\r\n");
      out.write("\t\t    if( selectNum.length > 0 ) {\r\n");
      out.write("\t\t\t    frm.as_seqs.value = selectNum;\r\n");
      out.write("\t\t\t    frm.mode.value = 'DELETE';\r\n");
      out.write("\t\t\t    frm.target = '';\r\n");
      out.write("\t\t\t    frm.action = 'alimi_setting_prc.jsp';\r\n");
      out.write("\t\t\t    frm.submit();\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t    \t\talert('삭제할 수신자를 선택하세요');\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction pageClick( paramUrl ) {\r\n");
      out.write("\t\tvar f = document.getElementById('alimi_setting_list');\r\n");
      out.write("        f.action = \"alimi_setting_list.jsp\" + paramUrl;\r\n");
      out.write("        f.submit();\r\n");
      out.write("\t\t/*\r\n");
      out.write("\t\tvar f = document.alimi_setting_list;\r\n");
      out.write("\t\tf.nowpage.value = paramUrl.substr(paramUrl.indexOf(\"=\")+1, paramUrl.length);\r\n");
      out.write("\t\tf.action='alimi_setting_list.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\t*/\r\n");
      out.write("    }\r\n");
      out.write("\t\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"alimi_setting_list\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(iNowPage);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_seq\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_seqs\">\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\" /><img src=\"../../../images/admin/alimi/tit_0509.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">알리미 설정관리</td>\r\n");
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
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/alimi/btn_allselect.gif\" onclick=\"allselect();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/btn_del.gif\" onclick=\"delList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:52px;\"><img src=\"../../../images/admin/alimi/btn_alrimiadd.gif\" onclick=\"asSetInsert();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>       \r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"45%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\">\r\n");
      out.write("\t\t\t\t\t<tr>           \r\n");
      out.write("\t\t\t\t\t\t<th><input type=\"checkbox\" name=\"tt\" value=\"checkbox\" onclick=\"allselect();\"></th>\r\n");
      out.write("\t\t\t\t\t\t<th>제 목</th>\r\n");
      out.write("\t\t\t\t\t\t<th>발송매체</th>\r\n");
      out.write("\t\t\t\t\t\t<th>발송간격</th>\r\n");
      out.write("\t\t\t\t\t\t<th>수신자</th>\r\n");
      out.write("\t\t\t\t\t\t<th>발송 게시 일자</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	
	if(arrAlimiSetList.size()>0){
	  	for(int i=0; i < arrAlimiSetList.size(); i++)
		{
	  		asBean = new AlimiSettingBean();  	
	  		asBean = 	(AlimiSettingBean)arrAlimiSetList.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		if(asBean.getArrReceiver()!=null)
	  		{
	  			arrReceiverList = asBean.getArrReceiver();
	  		}

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"chkNum\" value=\"");
      out.print(asBean.getAs_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td onclick=\"asSetUpdate( '");
      out.print(asBean.getAs_seq());
      out.write("' );\" style=\"cursor:pointer\"><p class=\"board_01_tit\">");
      out.print(asBean.getAs_title());
      out.write("</p></td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(asBean.getAsTypeName());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(asBean.getasIntervalName());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(arrReceiverList.size()+"명");
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(asBean.getLastSendDate());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		}
	}else{

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"6\" style=\"font-weight:bold;height:40px\" align=\"center\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
		
	}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\r\n");
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
