package org.apache.jsp.riskv3.admin.usergroup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import java.util.List;
import risk.search.userEnvMgr;
import risk.search.keywordInfo;
import risk.admin.site.SitegroupBean;
import risk.admin.site.SiteMng;
import risk.admin.membergroup.membergroupBean;
import risk.admin.membergroup.membergroupMng;
import risk.util.ConfigUtil;

public final class ifram_005fusergroup_005fright_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/usergroup/../../inc/sessioncheck.jsp");
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

      out.write('\n');
      out.write('\n');
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
      out.write('\n');

	int i;
	List KGlist = null;
	List sglist = null;
	String mgseq = null;
	String[] menufd = null;
	String[] xpfd = null;
	String[] sgfd = null;
	membergroupBean mginfo = null;

	StringUtil su = new StringUtil();
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();

	SiteMng sitemng = new SiteMng();
	membergroupMng mgmng = membergroupMng.getInstance();

	if( request.getParameter("mgseq") != null ) {
		mgseq = request.getParameter("mgseq");
		System.out.println(mgseq);
		mginfo = (membergroupBean)mgmng.getMGBean(mgseq);
		if( mginfo.getMGmenu() != null ) menufd = mginfo.getMGmenu().split(",");
		if( mginfo.getMGxp() != null ) xpfd = mginfo.getMGxp().split(",");
		if( mginfo.getMGsite() != null ) sgfd = mginfo.getMGsite().split(",");
	}

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title></title>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\n");
      out.write("<!--\n");
      out.write("\tfunction mnu_click(index){\n");
      out.write("\tif(index == 1){//키워드 그룹 클릭\n");
      out.write("\t\t//탭 위치가 키워드 그룹으로 와야지\n");
      out.write("\t\tdocument.getElementById('tab1').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tab2').style.display = 'block';\n");
      out.write("\t\tdocument.getElementById('tab3').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tabImg1').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg2').className = 'tab_on';\n");
      out.write("\t\tdocument.getElementById('tabImg3').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg1').innerHTML = '기능';\n");
      out.write("\t\tdocument.getElementById('tabImg2').innerHTML = '<span class=\"tab_on_txt\">키워드그룹</span>';\n");
      out.write("\t\tdocument.getElementById('tabImg3').innerHTML = '사이트그룹';\n");
      out.write("\t}else if(index == 2){\n");
      out.write("\t\tdocument.getElementById('tab1').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tab2').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tab3').style.display = 'block';\n");
      out.write("\t\tdocument.getElementById('tabImg1').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg2').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg3').className = 'tab_on';\n");
      out.write("\t\tdocument.getElementById('tabImg1').innerHTML = '기능';\n");
      out.write("\t\tdocument.getElementById('tabImg2').innerHTML = '키워드그룹';\n");
      out.write("\t\tdocument.getElementById('tabImg3').innerHTML = '<span class=\"tab_on_txt\">사이트그룹</span>';\n");
      out.write("\t}else{\n");
      out.write("\t\tdocument.getElementById('tab1').style.display = 'block';\n");
      out.write("\t\tdocument.getElementById('tab2').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tab3').style.display = 'none';\n");
      out.write("\t\tdocument.getElementById('tabImg1').className = 'tab_on';\n");
      out.write("\t\tdocument.getElementById('tabImg2').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg3').className = 'tab_off';\n");
      out.write("\t\tdocument.getElementById('tabImg1').innerHTML = '<span class=\"tab_on_txt\">기능</span>';\n");
      out.write("\t\tdocument.getElementById('tabImg2').innerHTML = '키워드그룹';\n");
      out.write("\t\tdocument.getElementById('tabImg3').innerHTML = '사이트그룹';\n");
      out.write("\t}\n");
      out.write("\t\t\n");
      out.write("\t/*\n");
      out.write("\t\tvar t = document.getElementsByName('tabImg');\n");
      out.write("\t\tfor(var i = 0; i < t.length; i++){\n");
      out.write("\t\t\tt[i].className = 'tab_off';\n");
      out.write("\t\t\tdocument.getElementById('tab'+(i+1)).style.display = 'none';\n");
      out.write("\t\t}\n");
      out.write("\t\tt[0].innerHTML = '기능';\n");
      out.write("\t\tt[1].innerHTML = '키워드그룹';\n");
      out.write("\t\tt[2].innerHTML = '사이트그룹';\n");
      out.write("\t\t\n");
      out.write("\t\tobj.className = 'tab_on';\n");
      out.write("\t\tif(index == 0){\n");
      out.write("\t\t\tobj.innerHTML = '<span class=\"tab_on_txt\">기 능</span>';\n");
      out.write("\t\t\tdocument.getElementById('tab'+(index+1)).style.display = '';\n");
      out.write("\t\t}else if(index == 1){\n");
      out.write("\t\t\tobj.innerHTML = '<span class=\"tab_on_txt\">키워드그룹</span>';\n");
      out.write("\t\t\tdocument.getElementById('tab'+(index+1)).style.display = '';\n");
      out.write("\t\t}else if(index == 2){\n");
      out.write("\t\t\tobj.innerHTML = '<span class=\"tab_on_txt\">사이트그룹</span>';\n");
      out.write("\t\t\tdocument.getElementById('tab'+(index+1)).style.display = '';\n");
      out.write("\t\t}\n");
      out.write("\t*/\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\n");
      out.write("\tfunction curr_save() {\n");
      out.write("\t\tif( mgset.mgseq.value ) {\n");
      out.write("\t\t\tmgset.submit();\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("//-->\n");
      out.write("</script>\n");
      out.write("</head>\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
      out.write("<form name=\"mgset\" action=\"ifram_usergroup_right_prc.jsp\" method=\"post\">\n");
      out.write("<input type=\"hidden\" name=\"mgseq\" value=\"");
      out.print(mgseq);
      out.write("\">\n");
      out.write("<table id=\"pop_mail_group\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t<tr>\n");
      out.write("\t\t<th id=\"tabImg1\" style=\"cursor:pointer; width:33%\" class=\"tab_on\" onclick=\"mnu_click(0)\"><span class=\"tab_on_txt\">기 능</span></th>\n");
      out.write("\t\t<th class=\"tab_off\" id=\"tabImg2\" style=\"cursor:pointer\" onclick=\"mnu_click(1)\">키워드그룹</th>\n");
      out.write("\t\t<th class=\"tab_off\" id=\"tabImg3\" style=\"cursor:pointer\" onclick=\"mnu_click(2)\">사이트그룹</th>\n");
      out.write("\t</tr>\n");
      out.write("\t<tr>\n");
      out.write("\t\t<td id=\"tab1\" colspan=\"3\" style=\"padding:10px;\" valign=\"top\">\n");
      out.write("\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"1\" ");
if( menufd != null ){ if( su.inarray(menufd, "1") ) out.println("checked");}
      out.write(">정보검색</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"2\" ");
if( menufd != null ){ if( su.inarray(menufd, "2") ) out.println("checked");}
      out.write(">이슈관리</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"3\" ");
if( menufd != null ){ if( su.inarray(menufd, "3") ) out.println("checked");}
      out.write(">보고서관리</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"4\" ");
if( menufd != null ){ if( su.inarray(menufd, "4") ) out.println("checked");}
      out.write(">통계분석</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"5\" ");
if( menufd != null ){ if( su.inarray(menufd, "5") ) out.println("checked");}
      out.write(">관리자</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_menu\" value=\"6\" ");
if( menufd != null ){ if( su.inarray(menufd, "6") ) out.println("checked");}
      out.write(">대시보드</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t");
      out.write("\n");
      out.write("\t\t</table>\n");
      out.write("\t\t</td>\n");
      out.write("\t\t<td id=\"tab2\" style=\"display:none;\" colspan=\"3\" style=\"padding:10px;\" valign=\"top\">\n");
      out.write("\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");

	for( i=0 ; i < KGlist.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)KGlist.get(i);

      out.write("\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_kg\" value=\"");
      out.print(KGset.getK_Xp());
      out.write('"');
      out.write(' ');
if( xpfd != null ){ if( su.inarray(xpfd, KGset.getK_Xp()) ) out.println("checked");}
      out.write('>');
      out.print(KGset.getK_Value());
      out.write("</td>\n");
      out.write("\t\t\t</tr>\n");

	}

      out.write("\n");
      out.write("\t\t</table>\n");
      out.write("\t\t</td>\n");
      out.write("\t\t<td id=\"tab3\" style=\"display:none;\" colspan=\"3\" style=\"padding:10px;\" valign=\"top\">\n");
      out.write("\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");

	sglist = sitemng.getSGList();
	for( i=0 ; i < sglist.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);

      out.write("\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td class=\"pop_mail_group_td\"><input type=\"checkbox\" name=\"mg_site\" value=\"");
      out.print(SGinfo.get_seq());
      out.write('"');
      out.write(' ');
if( sgfd != null ){ if( su.inarray(sgfd, String.valueOf(SGinfo.get_seq())) ) out.println("checked");}
      out.write('>');
      out.print( SGinfo.get_name());
      out.write("</td>\n");
      out.write("\t\t\t</tr>\n");

	}

      out.write("\n");
      out.write("\t\t</table>\n");
      out.write("\t\t</td>\n");
      out.write("\t</tr>\n");
      out.write("</table>\n");
      out.write("</form>\n");
      out.write("</body>\n");
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
