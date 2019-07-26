package org.apache.jsp.riskv3.admin.tier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.util.*;

public final class tier_005fmain_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/tier/../../inc/sessioncheck.jsp");
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

	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 10px;\r\n");
      out.write("\tmargin-top: 0px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("}\r\n");
      out.write(".style1 {COLOR: #2D2D2D; LINE-HEIGHT: 16px; FONT-FAMILY: \"Dotum\"; font-size: 12px;}\r\n");
      out.write("</style>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/tier/basic.css\" type=\"text/css\">\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("\r\n");
      out.write("\t$(document).ready(init);\r\n");
      out.write("\r\n");
      out.write("\t//검색 텍스트 활성화\r\n");
      out.write("\tfunction init(){\t\t\r\n");
      out.write("\t\tloadTierSite();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction loadTierSite(){\r\n");
      out.write("\t\tajax.post3('tier_list.jsp','tierForm','tierSite_list','');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction tsTypeCheck(){\r\n");
      out.write("\t\tvar obj = $('#ts_type');\r\n");
      out.write("\t\tobj.val('');\t\t\r\n");
      out.write("\t\t$('input[name=ts_types]:checked').each(function(){\r\n");
      out.write("\t\t\tif(obj.val()==''){\r\n");
      out.write("\t\t\t\tobj.val($(this).val());\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tobj.val(obj.val()+','+$(this).val());\r\n");
      out.write("\t\t\t};\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\t\r\n");
      out.write("\r\n");
      out.write("\tfunction searchTierSite(){\r\n");
      out.write("\t\ttsTypeCheck();\r\n");
      out.write("\t\tloadTierSite();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\tfunction fnTextCheck(obj) \r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t    if (/[^0-9.,]/g.test(obj.value))\r\n");
      out.write("\t    {\t    \t\r\n");
      out.write("\t        obj.value='';\r\n");
      out.write("\t        obj.focus();\r\n");
      out.write("\t        alert('숫자만 입력 가능합니다.');\r\n");
      out.write("\t        return false;\r\n");
      out.write("\t    }else{\r\n");
      out.write("\t        if( obj.value >= -128 && obj.value <= 127 ){\r\n");
      out.write("\t\t        return true;\r\n");
      out.write("\t        }else{\r\n");
      out.write("\t\t        var text1 = obj.value.substring(0, obj.value.length - 1);       \r\n");
      out.write("\t\t        obj.value = text1;\r\n");
      out.write("\t\t        alert('-128에서 127 까지 입력하세요.');\r\n");
      out.write("\t\t        return false;\r\n");
      out.write("\t        }\r\n");
      out.write("\t    }\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction updateTierSite(ts_seq,typeId,rankId)\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\t$('#ts_seq').val(ts_seq);\r\n");
      out.write("\t\t$('#ts_type').val($('#'+typeId+\" option:selected\").val());\t\r\n");
      out.write("\t\t$('#ts_rank').val($('#'+rankId).val());\t\t\r\n");
      out.write("\t\t$('#mode').val(\"update\");\r\n");
      out.write("\t\t$('#tierForm').attr('target','processFrm');\r\n");
      out.write("\t\t$('#tierForm').attr('action','tier_prc.jsp');\r\n");
      out.write("\t\t$('#tierForm').submit();\r\n");
      out.write("\r\n");
      out.write("\t\tsetTimeout('loadTierSite()', '200');\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction deleteTierSite(ts_seq)\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\t$('#ts_seq').val(ts_seq);\t\t\r\n");
      out.write("\t\t$('#mode').val(\"delete\");\r\n");
      out.write("\t\t$('#tierForm').attr('target','processFrm');\r\n");
      out.write("\t\t$('#tierForm').attr('action','tier_prc.jsp');\r\n");
      out.write("\t\t$('#tierForm').submit();\r\n");
      out.write("\r\n");
      out.write("\t\tsetTimeout('loadTierSite()', '200');\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction popTierSiteSearch()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tpopup.openByPost('tierForm','pop_tier_search.jsp',320,450,false,true,false,'pop_tiersite_search1');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction excelSave(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tdocument.ifr_excel.location = 'tier_site_excel_down.jsp?ts_types='+document.tierForm.ts_type.value;\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body  leftmargin=\"10\" topmargin=\"10\" marginwidth=\"0\" marginheight=\"0\">\r\n");
      out.write("<iframe name=\"processFrm\" height=\"0\" border=\"0\" style=\"display:none\"></iframe>\r\n");
      out.write("<form id=\"tierForm\" name=\"tierForm\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input type=\"hidden\" id=\"mode\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" id=\"ts_seq\" name=\"ts_seq\">\r\n");
      out.write("<input type=\"hidden\" id=\"ts_type\" name=\"ts_type\" value=\"1,2,3,4,5\">\r\n");
      out.write("<input type=\"hidden\" id=\"ts_rank\" name=\"ts_rank\" value=\"\">\r\n");
      out.write("\r\n");
      out.write("<!-- <div style=\"width:850px\"> -->\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\" style=\"padding-left:20px\">\r\n");
      out.write("\t\t<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:0 auto\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t          <td>\r\n");
      out.write("\t\t      \t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tbody><tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\"><img src=\"../../../images/admin/tier/tit_1224.gif\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tbody><tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">매체관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody></table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</tbody></table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t  </tr>  \r\n");
      out.write("\t\t\t\t  <tr>\r\n");
      out.write("\t\t\t\t    <td><img src=\"../../../images/admin/tier/brank.gif\" width=\"1\" height=\"20\"></td>\r\n");
      out.write("\t\t\t\t  </tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t  </td>        \r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("\t\r\n");
      out.write("\t<table width=\"780\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:0 auto\">\r\n");
      out.write("\t  <tr>\r\n");
      out.write("\t    <td>\r\n");
      out.write("\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t            <tr>\r\n");
      out.write("\t              <td height=\"2\" bgcolor=\"7ca5dd\"></td>\r\n");
      out.write("\t            </tr>\r\n");
      out.write("\t            <tr>\r\n");
      out.write("\t              <td>\r\n");
      out.write("\t              \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"cdd4d6\">\r\n");
      out.write("\t                  <tr>\r\n");
      out.write("\t                    <td width=\"100\" height=\"41\" align=\"center\" bgcolor=\"f6f6f6\">Tiering 선택</td>\r\n");
      out.write("\t                    <td bgcolor=\"#FFFFFF\" style=\"text-align: left;\"><span  style=\"padding:10px 10px 10px 30px;\"><input type=\"checkbox\" id=\"ts_types\" name=\"ts_types\" value=\"1\" checked=\"checked\" style=\"vertical-align: middle;\" />1</span>\r\n");
      out.write("\t\t\t\t\t\t<span  style=\"padding:10px 10px 10px 30px;\"><input type=\"checkbox\" id=\"ts_types\" name=\"ts_types\" value=\"2\" checked=\"checked\" style=\"vertical-align: middle;\" />2</span>\r\n");
      out.write("\t\t\t\t\t\t<span  style=\"padding:10px 10px 10px 30px;\"><input type=\"checkbox\" id=\"ts_types\" name=\"ts_types\" value=\"3\" checked=\"checked\" style=\"vertical-align: middle;\" />3</span>\r\n");
      out.write("\t\t\t\t\t\t<span  style=\"padding:10px 10px 10px 30px;\"><input type=\"checkbox\" id=\"ts_types\" name=\"ts_types\" value=\"4\" checked=\"checked\" style=\"vertical-align: middle;\" />4</span>\r\n");
      out.write("\t\t\t\t\t\t<span  style=\"padding:10px 10px 10px 30px;\"><input type=\"checkbox\" id=\"ts_types\" name=\"ts_types\" value=\"5\" checked=\"checked\" style=\"vertical-align: middle;\" />5</span>\r\n");
      out.write("\t\t\t\t\t\t<span style=\"padding: 10px 10px 10px 50px;\"><a href=\"javascript:searchTierSite();\"><img src=\"../../../images/admin/tier/search_btn.gif\" width=\"50\" height=\"23\" style=\"cursor:pointer\" align=\"absmiddle\"></a></span></td>\r\n");
      out.write("\t                  </tr>\r\n");
      out.write("\t              </table>\r\n");
      out.write("\t              </td>\r\n");
      out.write("\t            </tr>\r\n");
      out.write("\t        </table>\r\n");
      out.write("\t        </td>\r\n");
      out.write("\t   </tr>\r\n");
      out.write("\t   <tr>\r\n");
      out.write("\t   \t<td height=\"15\">&nbsp;</td>\r\n");
      out.write("\t   </tr>\r\n");
      out.write("\t   <tr>\r\n");
      out.write("\t    <td height=\"32\" colspan=\"4\" style=\"padding: 0px 0px 0px 8px;\">   \r\n");
      out.write("\t    <img src=\"../../../images/admin/tier/ecxel_btn.gif\" width=\"79\" height=\"24\" align=\"absmiddle\" style=\"cursor:pointer;\" onclick=\"excelSave();\">\r\n");
      out.write("\t\t<img src=\"../../../images/admin/tier/write_btn_01.gif\" width=\"79\" height=\"24\" align=\"absmiddle\" style=\"cursor:pointer;\" onclick=\"popTierSiteSearch();\"></td>\r\n");
      out.write("\t   </tr>\r\n");
      out.write("\t   <tr>    \r\n");
      out.write("\t    <td valign=\"top\">\r\n");
      out.write("\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td height=\"2\" bgcolor=\"7ca5dd\"></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"cdd4d6\">            \r\n");
      out.write("\t            <tr>\r\n");
      out.write("\t              <td width=\"32\"  align=\"center\"bgcolor=\"#F7FDFF\" class=\"table_top\" ><strong>순번</strong></td>\r\n");
      out.write("\t              <td width=\"57\" height=\"30\" align=\"center\"bgcolor=\"#F7FDFF\" class=\"table_top\"><strong>Tiering</strong></td>\r\n");
      out.write("\t              <td align=\"center\" bgcolor=\"#F7FDFF\" class=\"table_top\"><strong>사이트명</strong></td>\r\n");
      out.write("\t              <td width=\"170\" align=\"center\" bgcolor=\"#F7FDFF\" class=\"table_top\"><strong></strong></td>\r\n");
      out.write("\t            </tr>            \r\n");
      out.write("\t        </table></td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t      <tr>\r\n");
      out.write("\t        <td id=\"tierSite_list\">\r\n");
      out.write("\t        \r\n");
      out.write("\t        </td>\r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t    </table></td>\r\n");
      out.write("\t  </tr>\r\n");
      out.write("\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("</form>\r\n");
      out.write("<iframe name=\"ifr_excel\" height=\"0\" border=\"0\" style=\"display:none\"></iframe>\r\n");
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
