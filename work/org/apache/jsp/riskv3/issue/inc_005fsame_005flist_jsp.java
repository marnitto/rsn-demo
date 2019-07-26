package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.issue.IssueDataBean;
import risk.issue.IssueMgr;

public final class inc_005fsame_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

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

      out.write("\r\n");
      out.write("\r\n");

	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.

    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    pr.printParams();

    IssueDataBean idBean = new IssueDataBean();
    
    String md_pseq    = pr.getString("md_pseq");
    String ic_seq    = pr.getString("ic_seq");
    String ic_name    = pr.getString("ic_name");
    String sDateFrom   = pr.getString("sDateFrom");
    String sDateTo     = pr.getString("sDateTo");
    String ir_stime    = pr.getString("ir_stime","16");
    String ir_etime    = pr.getString("ir_etime","16");
    
   	IssueMgr imgr = new IssueMgr();
   	ArrayList alData = imgr.getSourceData( md_pseq , ic_seq, sDateFrom, ir_stime+":00:00", sDateTo, ir_etime+":00:00") ;

      out.write("\r\n");
      out.write("<div id=\"zzFilter\">\r\n");
      out.write("<table width=\"820px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("<col width=\"5%\"><col width=\"13%\"><col width=\"*\"><col width=\"5%\"><col width=\"5%\"><col width=\"10%\"><col width=\"11%\"><col width=\"5%\">\r\n");

String sunghyang ="";
if(alData.size() > 0){
	for(int i = 0; i < alData.size(); i++){
		idBean = (IssueDataBean)alData.get(i);
		sunghyang = idBean.getTemp1(); 
		//제목 색갈변경하기
		String titleColor = "";
    	if(idBean.getMd_seq().equals(idBean.getMd_pseq())){
    		titleColor = "<font color='0000CC'/>";
    	}else{
    		titleColor = "";
    	}
		
    	//출처 색갈변경하기
		String sourchColor = "";
    	System.out.println(idBean.getF_news());
    	if(idBean.getF_news() != null && idBean.getF_news().equals("Y")){
    		sourchColor = "<font color='F41B2F'/>";
    	}else{
    		sourchColor = "";
    	}
		

      out.write("\r\n");
      out.write("\t\t\t<tr bgcolor=\"F2F7FB\">\r\n");
      out.write("\t\t\t\t<td><img src=\"../../images/issue/icon.gif\" /></td>\r\n");
      out.write("\t\t\t\t<td><span style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" class=\"board01_category\" title=\"");
      out.print(idBean.getMd_site_name());
      out.write('"');
      out.write('>');
      out.print(idBean.getMd_site_name());
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t<td style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t<div style=\"width:350px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" class=\"board_01_tit_01\" title=\"");
      out.print(idBean.getId_title());
      out.write("\"><a href=\"javascript:PopIssueDataForm('");
      out.print(idBean.getMd_seq());
      out.write("','N');\">");
      out.print(titleColor);
      out.print(idBean.getId_title());
      out.write("</a></div></td>\r\n");
      out.write("\t\t\t\t<td><a onClick=\"hrefPop('");
      out.print(idBean.getId_url());
      out.write("');\" href=\"javascript:void(0);\" ><img src=\"../../images/issue/ico_original.gif\" align=\"absmiddle\" /></a></td>\r\n");
      out.write("\t\t\t\t<td><a href=\"javascript:goSmsTo('");
      out.print(idBean.getId_seq());
      out.write("');\" ><img src=\"../../images/issue/ico_sms.gif\" align=\"absmiddle\" /></a></td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(sourchColor);
      out.print(ic_name);
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(idBean.getFormatMd_date("yyyy-MM-dd"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td><span class=\"tendency_0");
if(sunghyang.equals("긍정")){out.print("1");}else if(sunghyang.equals("중립")){out.print("2");}else{out.print("3");}
      out.write('"');
      out.write('>');
      out.print(sunghyang);
      out.write("</span></td>\r\n");
      out.write("\t\t\t</tr>\r\n");

	}
}

      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("</div>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\t\r\n");
      out.write("\tparent.fillSameList('");
      out.print(md_pseq);
      out.write("');\r\n");
      out.write("//-->\r\n");
      out.write("</script>");
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
