package org.apache.jsp.riskv3.admin.user;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.admin.member.MemberDao;
import risk.admin.member.MemberBean;
import risk.util.ParseRequest;
import java.util.*;
import java.lang.*;
import risk.util.PageIndex;
import risk.util.ConfigUtil;

public final class admin_005fuser_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/user/../../inc/sessioncheck.jsp");
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
      out.write('\r');
      out.write('\n');

	int totpage = 0;
	int nowpage = 1;
	int count = 0;
	String echomsg = "";
	String sch_mod = "";
	String sch_val = "";

	List memberlist = null;
	MemberDao dao = new MemberDao();
    ParseRequest    pr = new ParseRequest(request);

try{	
	if( request.getParameter("nowpage") != null ) nowpage = Integer.parseInt(request.getParameter("nowpage"));
	if( pr.getString("sch_mod") != null ) sch_mod = pr.getString("sch_mod");
	if( pr.getString("sch_val") != null ) sch_val = pr.getString("sch_val");
	
	if( sch_val.equals("") )
	memberlist = dao.getList( String.valueOf( nowpage ) );
	else if( sch_mod.equals("1") )
	memberlist = dao.getList( String.valueOf( nowpage ), "mg_name", sch_val );
	else if( sch_mod.equals("2") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_id", sch_val );
	else if( sch_mod.equals("3") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_name", sch_val );
	else if( sch_mod.equals("4") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_dept", sch_val );
	else if( sch_mod.equals("5") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_hp", sch_val );
	else if( sch_mod.equals("6") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_mail", sch_val );

}catch(Exception e){System.out.println("[admin_user_list] error1 : "+e);}
		
try{	
	count = dao.totalct;
	if( count > 0 ) {
		totpage = count/10;
		if( (count % 10) > 0 ) totpage++;
	}
}catch(Exception e){System.out.println("error2 : "+e);}


      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("\tfunction init(){\r\n");
      out.write("\t\tsearch.sch_val.focus();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    function pageClick( paramUrl ) {\r\n");
      out.write("\t\tsearch.nowpage.value = paramUrl.substr(paramUrl.indexOf(\"=\")+1, paramUrl.length);\r\n");
      out.write("\t\tsearch.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\tfunction sch_sub()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( search.sch_val.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tsearch.nowpage.value = 1;\r\n");
      out.write("\t\t\tsearch.submit();\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\talert('검색조건이 없습니다.');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar val = 0;\r\n");
      out.write("\tfunction sel_all()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar form = document.getElementById('userlist');\r\n");
      out.write("\t\tif( form.m_seq ) {\r\n");
      out.write("\t\t\tif(val == 0){\r\n");
      out.write("\t\t\t\tif(form.m_seq.length > 1){\r\n");
      out.write("\t\t\t\t\tfor(var i=0 ; i<form.m_seq.length ; i++ )\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tform.m_seq[i].checked = true;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\tform.m_seq.checked = true;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tval = 1;\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tif(form.m_seq.length > 1){\r\n");
      out.write("\t\t\t\t\tfor( i=0; i < form.m_seq.length; i++ ){\r\n");
      out.write("\t\t\t\t\t\tform.m_seq[i].checked = false;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tform.m_seq.checked = false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tval = 0;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction multy_del()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar chk = false;\r\n");
      out.write("\t\tif( userlist.m_seq.length ) {\r\n");
      out.write("\t\t\tfor(var i=0 ; i<userlist.m_seq.length ; i++ )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif( userlist.m_seq[i].checked == true ) {\r\n");
      out.write("\t\t\t\t\tchk = true;\r\n");
      out.write("\t\t\t\t\tbreak;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tif( userlist.m_seq.checked == true ) chk = true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif( chk ) {\r\n");
      out.write("\t\t\tif( confirm('삭제하시겠습니까?') ) {\r\n");
      out.write("\t\t\t\tuserlist.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\talert(\"삭제할 대상을 선택하여 주십시요.\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t// 각 메뉴의 마우스 오버시 이벤트\r\n");
      out.write("function mnu_over(obj) {\r\n");
      out.write("\tobj.style.backgroundColor = \"F3F3F3\";\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// 각 메뉴의 마우스 아웃시 이벤트\r\n");
      out.write("// 현재 선택된 메뉴면\r\n");
      out.write("function mnu_out(obj) {\r\n");
      out.write("\tobj.style.backgroundColor = \"#FFFFFF\";\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/member/tit_icon.gif\" /><img src=\"../../../images/admin/member/tit_0501.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">사용자관리</td>\r\n");
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
      out.write("\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/member/btn_allselect.gif\" onclick=\"sel_all();\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/member/btn_del.gif\" onclick=\"multy_del();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"width:88px;\"><a href=\"admin_user_add.jsp\"><img src=\"../../../images/admin/member/btn_useradd.gif\" /></a></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<form name=\"userlist\" id=\"userlist\" action=\"admin_user_prc.jsp\" method=\"post\">\r\n");
      out.write("\t\t\t\t<input name=\"mode\" type=\"hidden\" value=\"del\">\r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout: fixed;\">\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"15%\"><col width=\"15%\"><col width=\"35%\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>선택</th>\r\n");
      out.write("\t\t\t\t\t\t<th>사용자그룹</th>\r\n");
      out.write("\t\t\t\t\t\t<th>아이디</th>\r\n");
      out.write("\t\t\t\t\t\t<th>이름</th>\r\n");
      out.write("\t\t\t\t\t\t<th>부서</th>\r\n");
      out.write("\t\t\t\t\t\t<th>핸드폰</th>\r\n");
      out.write("\t\t\t\t\t\t<th> E-Mail</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

	if(memberlist != null && memberlist.size() > 0){
		for(int i = 0; i < memberlist.size(); i++) {
			MemberBean memberinfo = (MemberBean)memberlist.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" id=\"m_seq\" name=\"m_seq\" value=\"");
      out.print(memberinfo.getM_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(memberinfo.getMg_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td><a href=\"#\" onclick=\"location.href='admin_user_view.jsp?id=");
      out.print(memberinfo.getM_id());
      out.write("'\"><strong>");
      out.print(memberinfo.getM_id());
      out.write("</strong></a></td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(memberinfo.getM_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(memberinfo.getM_dept());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td><span class=\"phone\">");
      out.print(memberinfo.getM_hp());
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\"><span class=\"mail\">");
      out.print(memberinfo.getM_mail());
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
}}else{
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"7\" align=\"center\" style=\"font-weight:bold;height:40px\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</form>\r\n");
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
      out.write("\t\t\t\t\t\t\t\t<td style=\"width:76px;\"><img src=\"../../../images/admin/member/btn_allselect.gif\" onclick=\"sel_all();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/member/btn_del.gif\" onclick=\"multy_del();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\"><a href=\"admin_user_add.jsp\"><img src=\"../../../images/admin/member/btn_useradd.gif\" /></a></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<form name=\"search\" action=\"admin_user_list.jsp\" method=\"post\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(nowpage);
      out.write("\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" style=\"vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"sch_mod\" style=\"vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"3\" ");
 if( sch_mod.equals("3") ) out.print("selected");
      out.write(">이름</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"1\" ");
 if( sch_mod.equals("1") ) out.print("selected");
      out.write(">사용자그룹</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"2\" ");
 if( sch_mod.equals("2") ) out.print("selected");
      out.write(">아이디</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"4\" ");
 if( sch_mod.equals("4") ) out.print("selected");
      out.write(">부서</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"5\" ");
 if( sch_mod.equals("5") ) out.print("selected");
      out.write(">핸드폰</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"6\" ");
 if( sch_mod.equals("6") ) out.print("selected");
      out.write(">E-mail</option>\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t<input class=\"txtbox\" align=\"middle\" type=\"text\" style=\"vertical-align:middle\" name=\"sch_val\" value=\"");
      out.print(sch_val);
      out.write("\"  onkeydown=\"javascript:if (event.keycode == 13) { sch_sub();}\">\r\n");
      out.write("\t\t\t\t\t\t\t<img src=\"../../../images/admin/member/search_btn.gif\" width=\"41\" height=\"19\" hspace=\"5\" onclick=\"sch_sub();\" style=\"cursor:pointer;vertical-align:middle\">\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</form>\r\n");
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
      out.print(PageIndex.getPageIndex(nowpage, totpage,"","" ));
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
