package org.apache.jsp.riskv3.admin.classification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.admin.classification.classificationMgr;
import risk.admin.classification.clfBean;
import risk.util.DateUtil;
import risk.util.ParseRequest;
import risk.issue.IssueMgr;
import risk.issue.IssueCodeMgr;
import java.util.List;
import java.util.ArrayList;

public final class frm_005fclassification_005fdetail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/classification/../../inc/sessioncheck.jsp");
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


    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    pr.printParams();
    
    classificationMgr cm = new classificationMgr();
    List cmList = null;
    
    ArrayList arrICB = new ArrayList();
    IssueMgr idm = new IssueMgr();
    IssueCodeMgr icm;// = new IssueCodeMgr();
    String typename[];
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);
	
	
	//코드 값을 가져와 상단 타이틀을 작성한다.
	 
	icm = IssueCodeMgr.getInstance();	
	icm.init(0);
	arrICB = icm.GetType(itype);
	//System.out.println("arrICBsize :" +arrICB.size());
	typename = icm.GetCodeName(arrICB,itype).split(",");
	//System.out.println("typename :" +typename);
	
	String codeNm = icm.getCodeName2(itype, icode);

	if( itype > 0 ) {
		cmList = cm.GetDetailList( itype, icode);
	}
	String flag1 = "";
	String flag2 = "";
	List list = null;
	if( itype==4 && icode > 0 ){
		list = cm.getTypeCodeFlag( itype , icode);
		if(list.size() > 0 ){
			for(int i = 0; i < list.size(); i++){
				String tmp = (String)list.get(i);
				
				if(tmp.equals("1")){
					flag1 = "checked";
				}
				if(tmp.equals("2")){
					flag2 = "checked";
				}
			}
		}
	}
	
	String ic_regdate = du.getCurrentDate("yyyy-MM-dd");
	String ic_regtime = du.getCurrentDate("HH:mm:ss");
	
	// 하위 분류를 추가할수 있는 항목인지 체크 한다.
	if( cm.GetSubClf( itype, icode) > 0 ) {

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../basic.css\" type=\"text/css\">\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.8.3.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar allcheck = 0;\r\n");
      out.write("\t\r\n");
      out.write("\tfunction addClf()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( frm_clf.itype.value > 0 ) {\r\n");
      out.write("\t\t\tif( frm_clf.clf_name.value ) {\r\n");
      out.write("\t\t\t\tfrm_clf.mode.value = 'add';\r\n");
      out.write("\t\t\t\tfrm_clf.action = 'classification_prc.jsp';\r\n");
      out.write("\t\t\t\tfrm_clf.target = '';\r\n");
      out.write("\t\t\t\tfrm_clf.submit();\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t\t\t\talert('분류명을 입력하세요');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else {\r\n");
      out.write("\t\t\talert('분류를 선택하세요');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction allselect()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar frm = document.frm_clf;\r\n");
      out.write("\t\tif( frm.icSeq ) {\r\n");
      out.write("\t\t\tif( allcheck == 0 ) {\r\n");
      out.write("\t\t\t\tif( frm.icSeq.length > 1 ) {\r\n");
      out.write("\t\t\t\t\tfor( i=0; i< frm.icSeq.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.icSeq[i].checked = true;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.icSeq.checked = true;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 1;\r\n");
      out.write("\t\t   \t}else {\r\n");
      out.write("\t\t   \t\tif( frm.icSeq.length > 1 ) {\r\n");
      out.write("\t\t\t   \t\tfor( i=0; i< frm.icSeq.length; i++ )\r\n");
      out.write("\t\t\t   \t\t{\r\n");
      out.write("\t\t\t   \t\t\t frm.icSeq[i].checked = false;\r\n");
      out.write("\t\t\t   \t\t}\r\n");
      out.write("\t\t\t   \t}else {\r\n");
      out.write("\t\t\t   \t\tfrm.icSeq.checked = false;\r\n");
      out.write("\t\t\t   \t}\r\n");
      out.write("\t\t   \t\tallcheck = 0;\r\n");
      out.write("\t\t   \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction delList()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar tmp_list = '';\r\n");
      out.write("    \tvar i = 0;\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar frm = document.frm_clf;\r\n");
      out.write("    \t\r\n");
      out.write("    \tif ( confirm(\"삭제 하시겠습니까?\" ) ) {\r\n");
      out.write("    \t\r\n");
      out.write("    \t\tif( frm.icSeq ) {\r\n");
      out.write("\t    \t\tif( frm.icSeq.length > 1 ) {\r\n");
      out.write("\t\t    \t\tfor( i=0; i< frm.icSeq.length; i++ )\r\n");
      out.write("\t\t    \t\t{\r\n");
      out.write("\t\t    \t\t\tif( frm.icSeq[i].checked == true ) {\r\n");
      out.write("\t\t    \t\t\t\tif( tmp_list.length > 0 ) {\r\n");
      out.write("\t\t    \t\t\t\t\ttmp_list = tmp_list+','+frm.icSeq[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}else {\r\n");
      out.write("\t\t    \t\t\t\t\ttmp_list = frm.icSeq[i].value;\r\n");
      out.write("\t\t    \t\t\t\t}\r\n");
      out.write("\t\t    \t\t\t}\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}else {\r\n");
      out.write("\t\t    \t\tif( frm.icSeq.checked == true ) {\r\n");
      out.write("\t\t    \t\t\ttmp_list = frm.icSeq.value;\r\n");
      out.write("\t\t    \t\t}\r\n");
      out.write("\t\t    \t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t    if( tmp_list.length > 0 ) {\r\n");
      out.write("\t\t    \tfrm.icSeqList.value = tmp_list;\r\n");
      out.write("\t\t    \tfrm.mode.value = 'del';\r\n");
      out.write("\t\t    \tfrm.target = '';\r\n");
      out.write("\t\t    \tfrm.action = 'classification_prc.jsp';\r\n");
      out.write("\t\t    \tfrm.submit();\r\n");
      out.write("\t\t    }else {\r\n");
      out.write("\t\t   \t\talert('분류 항목을 선택 하세요');\r\n");
      out.write("\t\t   \t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction showModify(ic_seq, ic_name, ic_regdate){\r\n");
      out.write("\r\n");
      out.write("\t\tfrm_clf.ic_seq.value = ic_seq; \r\n");
      out.write("\t\tfrm_clf.ic_name.value = ic_name;\r\n");
      out.write("\t\tfrm_clf.edit_ic_regdate.value = ic_regdate;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tpopup.openByPost('frm_clf','pop_classification_modify.jsp',400,400,false,false,false,'trendPop');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<form id=\"frm_clf\" name=\"frm_clf\" action=\"\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"itype\" value=\"");
      out.print(itype);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"icode\" value=\"");
      out.print(icode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"icSeqList\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"ic_seq\">\r\n");
      out.write("<input type=\"hidden\" name=\"ic_name\">\r\n");
      out.write("<input type=\"hidden\" name=\"ic_regtime\" value=\"");
      out.print(ic_regtime);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"edit_ic_regdate\">\r\n");
      out.write("\r\n");
      out.write("<table width=\"380\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" bgcolor=\"#F8F8F8\">\r\n");
      out.write("\t\t<table width=\"350\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"35\" style=\"padding: 5px 0px 0px 0px;\"><img src=\"../../../images/admin/classification/ico_arrow05.gif\" width=\"16\" height=\"14\" align=\"absmiddle\"><strong>");
      out.print(codeNm);
      out.write("</strong> - 분류체계</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td background=\"../../../images/admin/classification/report_line01.gif\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"2\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"35\">\r\n");
      out.write("\t\t\t\t<table width=\"345\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t<td style=\"padding: 3px 0px 0px 0px;\"><img src=\"../../../images/admin/classification/ico_arrow06.gif\" width=\"11\" height=\"9\" align=\"absmiddle\">분류항목관리 </td>\r\n");
      out.write("\t\t\t\t\t<td align=\"center\"><input name=\"clf_name\" type=\"text\" class=\"txtbox\" size=\"29\" OnKeyDown=\"Javascript:if (event.keyCode == 13) {addClf();}\"></td>\r\n");
      out.write("\t\t\t\t\t<td align=\"right\"><img src=\"../../../images/admin/classification/btn_add.gif\" id=\"addbtn\" width=\"57\" height=\"19\" align=\"absmiddle\" onclick=\"addClf();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t</table></td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t  ");
if(itype == 7){ 
      out.write("\r\n");
      out.write("\t\t  <tr>\r\n");
      out.write("\t\t\t\t<td height=\"35\">\r\n");
      out.write("\t\t\t\t<table width=\"345\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t<td style=\"padding: 3px 0px 0px 0px;width:65px;\"><img src=\"../../../images/admin/classification/ico_arrow06.gif\" width=\"11\" height=\"9\" align=\"absmiddle\">등록날짜 </td>\r\n");
      out.write("\t\t\t\t\t<td align=\"left\" style=\"width:90px\"><input id=\"ic_regdate\" name=\"ic_regdate\" type=\"text\" class=\"txtbox\" value=\"");
      out.print(ic_regdate );
      out.write("\" readonly style=\"cursor:pointer;width:80px;text-align:center\" onclick=\"calendar_sh(document.getElementById('ic_regdate'))\"></td>\r\n");
      out.write("\t\t\t\t\t<td align=\"left\"><img src=\"../../../images/admin/classification/btn_calendar.gif\" id=\"addbtn\" align=\"absmiddle\" onclick=\"calendar_sh(document.getElementById('ic_regdate'))\" style=\"cursor:pointer;\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t</table></td>\r\n");
      out.write("\t\t  \t</tr>\t\r\n");
      out.write("\t\t  \t");
} 
      out.write("\r\n");
      out.write("\t\t  <tr>\r\n");
      out.write("\t\t\t<td background=\"../../../images/admin/classification/report_line01.gif\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"2\"></td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t  <tr>\r\n");
      out.write("\t\t\t<td height=\"30\"><img src=\"../../../images/admin/classification/btn_del02.gif\" width=\"57\" height=\"19\" onclick=\"delList();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t  <tr>\r\n");
      out.write("\t\t\t<td bgcolor=\"#C6C6C6\">\r\n");
      out.write("\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\">\r\n");
      out.write("\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t<td width=\"72\" height=\"25\" align=\"center\" background=\"../../../images/admin/classification/report_tbg01.gif\" style=\"padding: 4px 0px 0px 0px;\"><input type=\"checkbox\" name=\"allCheck\" value=\"checkbox\" onclick=\"allselect();\"></td>\r\n");
      out.write("\t\t\t\t<td width=\"278\" align=\"center\" background=\"../../../images/admin/classification/report_tbg01.gif\" style=\"padding: 4px 0px 0px 0px;\"><strong>분류항목</strong></td>\r\n");
      out.write("\t\t\t  </tr>\r\n");
      out.write("\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t<td colspan=\"2\" bgcolor=\"#FFFFFF\">\r\n");
      out.write("\t\t\t\t<div name=\"aa\" style=\"width:346;height:120;overflow-y:auto\">\r\n");
      out.write("\t\t\t\t<table width=\"326\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t");

				if( cmList != null ) {
					for( int i=0; i<cmList.size(); i++ ) {
						clfBean clfb = (clfBean)cmList.get(i);						
						
      out.write("\r\n");
      out.write("\t\t\t\t  ");
if(itype == 7){ 
      out.write("\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t<td width=\"76\" height=\"25\" align=\"center\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"checkbox\" name=\"icSeq\" value=\"");
      out.print(clfb.getIc_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t<td width=\"200\" style=\"padding: 3px 0px 0px 10px;\" background=\"../../../images/admin/classification/report_tline01.gif\">");
      out.print(clfb.getIc_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t<td width=\"60\" style=\"padding: 3px 0px 0px 10px;\" background=\"../../../images/admin/classification/report_tline01.gif\"><img src=\"../../../images/admin/classification/btn_modify_001.gif\" style=\"cursor: pointer;\" onclick=\"showModify('");
      out.print(clfb.getIc_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(clfb.getIc_name());
      out.write("', '");
      out.print(clfb.getIc_wdate());
      out.write("');\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\" bgcolor=\"#E5E5E5\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  ");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t  \t<tr>\r\n");
      out.write("\t\t\t\t\t<td width=\"76\" height=\"25\" align=\"center\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"checkbox\" name=\"icSeq\" value=\"");
      out.print(clfb.getIc_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t<td width=\"260\" style=\"padding: 3px 0px 0px 10px;\" background=\"../../../images/admin/classification/report_tline01.gif\">");
      out.print(clfb.getIc_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"2\" bgcolor=\"#E5E5E5\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t  ");
} 
      out.write("\t\t\r\n");
      out.write("\t\t\t\t\t\t");

					}
				}
				
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t  </tr>\r\n");
      out.write("\t\t\t</table></td>\r\n");
      out.write("\t  </tr>\r\n");
      out.write("\t  <tr>\r\n");
      out.write("\t\t<td height=\"36\"><img src=\"../../../images/admin/classification/btn_del02.gif\" width=\"57\" height=\"19\" onclick=\"delList();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t  </tr>\r\n");
      out.write("\t  <tr>\r\n");
      out.write("\t\t<td height=\"20\">&nbsp;</td>\r\n");
      out.write("\t  </tr>\r\n");
      out.write("\t  </table>\r\n");
      out.write("\t   </td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
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
      out.write("</html>\r\n");

	}else {
		String msg_str = "";
		if( itype == 0 && icode == 0 ) {
			msg_str = "분류를 선택하세요";
		}else {
			if(itype == 4){
				msg_str = "주요이슈의 구분을 선택하세요.";
			}else{
				msg_str = "하위 분류항목이 없습니다.";	
			}
			
		}
		
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../css/basic.css\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<form name=\"frm_flag\" action=\"\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" name=\"itype\" value=\"");
      out.print(itype);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"icode\" value=\"");
      out.print(icode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"iflag\" value=\"\">\r\n");
      out.write("\r\n");
      out.write("<table width=\"380\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" bgcolor=\"#F8F8F8\">\r\n");
      out.write("\t\t<table width=\"350\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td colspan=\"3\" height=\"35\" style=\"padding: 5px 0px 0px 0px;\"><img src=\"../../../images/admin/classification/ico_arrow05.gif\" width=\"16\" height=\"14\" align=\"absmiddle\"><strong>");
      out.print(msg_str);
      out.write("</strong></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
if(itype == 7){
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td colspan=\"3\" background=\"../../../images/admin/classification/report_line01.gif\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"2\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:5px;\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"padding: 3px 0px 0px 0px;\"><img src=\"../../../images/admin/classification/ico_arrow06.gif\" width=\"11\" height=\"9\" align=\"absmiddle\">구분 </td>\r\n");
      out.write("\t\t\t\t<td><input type=\"checkbox\" name=\"flag\" value=\"1\" ");
      out.print(flag1 );
      out.write(" style=\"vertical-align: middle;\" /><span>서울시</span> <input type=\"checkbox\" name=\"flag\" value=\"2\" ");
      out.print(flag2 );
      out.write(" style=\"vertical-align: middle;\" /><span>서울시장</span></td>\r\n");
      out.write("\t\t\t\t<td align=\"right\"><img src=\"../../../images/admin/classification/btn_save.gif\" id=\"addbtn\" width=\"57\" height=\"19\" align=\"absmiddle\" onclick=\"saveFlag();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\tfunction saveFlag(){\r\n");
      out.write("\t\t\t\tvar frm = document.frm_flag\r\n");
      out.write("\t\t\t\tvar length = frm.flag.length;\r\n");
      out.write("\t\t\t\tvar flag = \"\";\r\n");
      out.write("\t\t\t\tfor(var i=0; i< length; i++){\r\n");
      out.write("\t\t\t\t\tif( frm.flag[i].checked ){\r\n");
      out.write("\t\t\t\t\t\tif(flag == '' ){\r\n");
      out.write("\t\t\t\t\t\t\tflag = frm.flag[i].value;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tflag += \",\"+frm.flag[i].value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\t\t\t\t\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tfrm.iflag.value = flag;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tfrm.mode.value = 'flag';\r\n");
      out.write("\t\t\t\tfrm.action = 'classification_prc.jsp';\r\n");
      out.write("\t\t\t\tfrm.target = '';\r\n");
      out.write("\t\t\t\tfrm.submit();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t</script>\r\n");
      out.write("\t\t\t");
}else{
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td background=\"../../../images/admin/classification/report_line01.gif\"><img src=\"../../../images/admin/classification/brank.gif\" width=\"1\" height=\"2\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"273\">&nbsp;</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t  \t</table>\r\n");
      out.write("\t   </td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\t\t");

	
	}

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
