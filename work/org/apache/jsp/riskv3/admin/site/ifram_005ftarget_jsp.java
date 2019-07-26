package org.apache.jsp.riskv3.admin.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import java.lang.*;
import risk.admin.site.SiteBean;
import risk.admin.site.SitegroupBean;
import risk.admin.site.SiteMng;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class ifram_005ftarget_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/site/../../inc/sessioncheck.jsp");
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

	List sglist = null;
	List sitelist = null;
	int sg=0;
	String sch = "";
	ParseRequest pr = new ParseRequest(request);

	//if( request.getParameter("sg_seq") != null ) sg = Integer.parseInt(request.getParameter("sg_seq"));
	
	//20110511 미확인된 에러 난다고 하여 int변환 숫자체크 보강
	String strSg = pr.getString("sg_seq","0");
	
	int language = pr.getInt("language",0); 
	
	char ch;
	for(int i = 0 ; i < strSg.length(); i++){
		ch = strSg.charAt(i);
		if(ch < 48 || ch >57){
			strSg = "0";
			break; 		
		}
	}
	sg = Integer.parseInt(strSg);
	
	
	
	if( pr.getString("tg_sch") != null ) sch = pr.getString("tg_sch");

	SiteMng sitemng = new SiteMng();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(SS_URL);
      out.write("css/basic.css\" type=\"text/css\">\r\n");
      out.write("<style>\r\n");
      out.write("\t<!--\r\n");
      out.write("\ttd { font-size:12px; color:#333333; font-family:\"dotum\"; ; line-height: 18px}\r\n");
      out.write("    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }\r\n");
      out.write("\t.t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("    .tCopy { font-family: \"Tahoma\"; font-size: 12px; color: #000000; font-weight: bold}\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 4px;\r\n");
      out.write("\tmargin-top: 5px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("\tbackground-color: #F3F3F3;\r\n");
      out.write("}\r\n");
      out.write("\t-->\r\n");
      out.write("\t</style>\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction edit_sg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( tg_site.sg_seq.value ) {\r\n");
      out.write("\t\t\tvar url = \"pop_sitegroup.jsp?mode=edit&sgseq=\"+tg_site.sg_seq.value+\"&language=\"+ tg_site.language.value;\r\n");
      out.write("\t\t\twindow.open(url, \"pop_sitegroup\", \"width=400,height=150\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction del_sg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( tg_site.sg_seq.value ) {\r\n");
      out.write("\t\t\tif( confirm('사이트그룹을 삭제하시겠습니까?') ) {\r\n");
      out.write("\t\t\t\tlocation.href = \"ifram_target_prc.jsp?mode=del&sgseq=\"+tg_site.sg_seq.value+\"&language=\"+ tg_site.language.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction search_TargetSite_frm()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( parent.target_form.tg_sch.value ) {\r\n");
      out.write("\t\t\ttg_site.tg_sch.value = parent.target_form.tg_sch.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\ttg_site.submit();\r\n");
      out.write("\t}\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<table width=\"315\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td align=\"right\" style=\"padding-left:10px\">\r\n");
      out.write("    <table width=\"300\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"1\" height=\"5\"><!-- <img src=\"../../../images/admin/site/brank.gif\" > --></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t<form name=\"tg_site\" action=\"ifram_target.jsp\" method=\"GET\">\r\n");
      out.write("\t\t\t<input name=\"tg_sch\" type=\"hidden\">\r\n");
      out.write("\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<img src=\"../../../images/admin/site/admin_ico03.gif\"  hspace=\"3\">언어선택\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td width=\"30\"></td>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<img src=\"../../../images/admin/site/admin_ico03.gif\"  hspace=\"3\">사이트 그룹 선택\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr height=\"3\"><td></td></tr>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td width=\"140\">\r\n");
      out.write("\t\t\t\t\t\t<select name=\"language\" class=\"t\" style=\"width:140px;\" onchange=\"search_TargetSite_frm();\">\r\n");
      out.write("\t\t\t\t\t       <option value=\"0\" ");
if( language == 0) out.println("selected");
      out.write(">전체\r\n");
      out.write("\t\t\t\t\t       <option value=\"1\" ");
if( language == 1) out.println("selected");
      out.write(">한국어\r\n");
      out.write("\t\t\t\t\t       <option value=\"2\" ");
if( language == 2) out.println("selected");
      out.write(">영어\r\n");
      out.write("    \t\t\t\t\t </select>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td width=\"30\"></td>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<td width=\"140\">\r\n");
      out.write("\t\t\t\t\t\t<select name=\"sg_seq\" class=\"t\"  style=\"width:140px;\" onchange=\"search_TargetSite_frm();\">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"0\">전체</option>\r\n");
      out.write("\t\t\t\t\t");

						sglist = sitemng.getSGList();
					
						for(int i=0; i < sglist.size();i++) {
							SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
					
      out.write("\r\n");
      out.write("\t\t\t\t\t<option value=\"");
      out.print(SGinfo.get_seq());
      out.write('"');
      out.write(' ');
if( SGinfo.get_seq() == sg) out.println("selected");
      out.write('>');
      out.print( SGinfo.get_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t");

						}
					
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr height=\"3\"><td></td></tr>\r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("<!--      <tr>-->\r\n");
      out.write("<!--        <td><img src=\"../../../images/admin/site/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">사이트 그룹 선택</td>-->\r\n");
      out.write("<!--      </tr>-->\r\n");
      out.write("<!--\t  <form name=\"tg_site\" action=\"ifram_target.jsp\" method=\"GET\">-->\r\n");
      out.write("<!--\t  <input name=\"tg_sch\" type=\"hidden\">-->\r\n");
      out.write("<!--      <tr>-->\r\n");
      out.write("<!--        <td height=\"25\">-->\r\n");
      out.write("<!--\t\t<select name=\"sg_seq\" class=\"t\"  style=\"width:310px;\" onchange=\"search_TargetSite_frm();\">-->\r\n");
      out.write("<!--\t\t<option value=\"0\">전체</option>-->\r\n");
      out.write("<!--");
      out.write("-->\r\n");
      out.write("<!--<option value=\"");
//=SGinfo.get_seq()
      out.write('"');
      out.write(' ');
//if( SGinfo.get_seq() == sg) out.println("selected");
      out.write('>');
//= SGinfo.get_name()
      out.write("</option>-->\r\n");
      out.write("<!--");
      out.write("-->\r\n");
      out.write("<!--\t\t</select></td>-->\r\n");
      out.write("<!--      </tr>-->\r\n");
      out.write("<!--\t  </form>-->\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td align=\"right\" ><img src=\"../../../images/admin/site/btn_groupedit.gif\" width=\"72\" height=\"21\" onclick=\"edit_sg();\" style=\"cursor:hand;\"><img src=\"../../../images/admin/site/btn_groupdel.gif\" width=\"59\" height=\"21\" hspace=\"5\" onclick=\"del_sg();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"1\" height=\"6\"><!-- <img src=\"../../../images/admin/site/brank.gif\" > --></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"20\"><img src=\"../../../images/admin/site/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">수집사이트 리스트</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("\t   <form name=\"sg_mng\" action=\"ifram_source_prc.jsp\" method=\"POST\">\r\n");
      out.write("\t   <input type=\"hidden\" name=\"mode\" value=\"del\">\r\n");
      out.write("\t   <input type=\"hidden\" name=\"sgseq\">\r\n");
      out.write("\t   <input type=\"hidden\" name=\"code1\" >\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td><select name=\"gsn\"  multiple style=\"width:310px;height:200px;\" class=\"t\">강동건설\r\n");

	
	sitelist = sitemng.getList(sch,sg,language);

	/*
	if( sch == null  ) {
		sitelist = sitemng.getList();
	}
	
	else if( sch != null && sg > 0 ) {
		sitelist = sitemng.getList(sch,sg);
	}else if( sch != null ) {
		sitelist = sitemng.getList(sch);
	} else if( sg == 0 ) {
		sitelist = sitemng.getList();
	} else {
		sitelist = sitemng.getList(sg);
	}
     */
        

	for(int i=0; i < sitelist.size();i++) {
		SiteBean siteinfo = (SiteBean)sitelist.get(i);

      out.write("\r\n");
      out.write("<option value=\"");
      out.print( siteinfo.get_gsn());
      out.write('"');
      out.write('>');
      out.print( siteinfo.get_name());
      out.write("</option>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t</select></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("\t  </form>\r\n");
      out.write("\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
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
