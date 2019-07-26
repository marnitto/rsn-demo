package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.search.MetaMgr;
import risk.search.MetaBean;

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

    String sMtPno    = pr.getString("md_pseq");
    String sMtno     = pr.getString("md_seq");
    String mode      = pr.getString("mode", "");
    String nowpage   = pr.getString("nowpage","1");
    String searchMode = pr.getString("searchMode");
    MetaMgr smgr = new MetaMgr();
    boolean bRtnValue = false;

	
    ArrayList alData = smgr.getSameList( sMtPno , sMtno ) ;
    
    
    String colspan = "";
    if ( searchMode.equals("ALLKEY") ) {
		colspan = "colspan=\"11\"";
		
    } else if ( searchMode.equals("ALLDB") ) {
    	colspan = "colspan=\"9\"";    	        

    }else if(searchMode.equals("DELIDX")){
    	colspan = "colspan=\"11\"";
    }

      out.write("\r\n");
      out.write("<div id=\"zzFilter\">\r\n");
      out.write("\r\n");
      out.write("<div style=\"width:820px;padding-top:3px\" align=\"left\">\r\n");
      out.write("\t<!--<img src=\"../../images/search/btn_multi.gif\" onclick=\"send_issue('samemulti');\" style=\"cursor:pointer\"/>-->\r\n");
      out.write("</div>\r\n");
      out.write("<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("<!--<table width=\"1000\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">-->\r\n");
      out.write("<!--<col width=\"5%\"><col width=\"20%\"><col width=\"50%\"><col width=\"5%\"><col width=\"5%\"><col width=\"15%\">-->\r\n");
      out.write("<col width=\"5%\"><col width=\"15%\"><col width=\"51%\"><col width=\"4%\"><col width=\"5%\"><col width=\"5%\"><col width=\"15%\">\r\n");

if(alData.size() > 0){
	
	String star = "";
	 
	for(int i = 0; i < alData.size(); i++){
		MetaBean mBean = (MetaBean) alData.get(i);
		
	   	//내용 강조
	   	String Html = "";	   	
	   	Html=mBean.getHighrightHtml(200,"0000CC");
	   	
	   	//관리버튼
	   	String managerButton = "";
	   	String freeButton = "";
		if(searchMode.equals("ALLKEY")){
			
			if(mBean.getIssue_check()!=null){
				/*
				managerButton ="<img id=\"issue_menu_icon"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" style=\"cursor:hand;\" ";
				if(mBean.getIssue_check().equals("Y")){
					managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\">";
				}else{
					managerButton += "src=\"../../images/search/btn_manage_off.gif\" title=\"이슈 미등록 정보입니다.\" onclick=\"send_issue('insert','"+mBean.getMd_seq()+"');\">";
				}
				*/
				
				star = "";
	        	if(mBean.getS_seq().equals("3555") || mBean.getS_seq().equals("4943")){
	        		star = "<img src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+mBean.getS_seq()+"','"+java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")+"')>";
	        	}
				
				managerButton ="<img id=\"issue_menu_icon2"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" ";
				if(mBean.getMd_seq().equals(sMtno)){
					managerButton += "src=\"../../images/search/up_btn_on.gif\">";
				}else{
					managerButton += "src=\"../../images/search/up_btn_off.gif\">";
				}	
				
				
				freeButton ="<img id=\"issue_menu_icon3"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" ";
				if(mBean.getMd_seq().equals(sMtPno) || mBean.getMd_seq().equals(sMtno)){
					freeButton += "src=\"../../images/search/free_btn_off.gif\">";
				}else{
					freeButton += "src=\"../../images/search/free_btn.gif\"  onclick=\"listAlter('" + mBean.getMd_seq() + "', '" + sMtPno + "');\" style=\"cursor:hand;\">";	
				}
				
				
				
				
			}
			
		}
		

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr bgcolor=\"F2F7FB\">\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓체크박스-->\r\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("<!--\t\t\t\t\t\t\t");
if(mBean.getIssue_check()!=null){ 
      out.write("<input type=\"checkbox\" name=\"sameChk\" value=\"");
      out.print(mBean.getMd_seq());
      out.write('"');
      out.write('>');
}
      out.write("-->\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓출처-->\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\"><font style=\"font-size:11px;\" color=\"#225bd1\">");
      out.print(mBean.getMd_site_name() + "(" + mBean.getSg_name() + ")");
      out.write("</font></td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓제목-->\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\">\r\n");
      out.write("\t\t\t\t\t\t\t<p class=\"board_01_tit_0");
      out.print(mBean.getMd_type());
      out.write("\"><a onClick=\"hrefPop('");
      out.print(mBean.getMd_url());
      out.write("');\" onfocus=\"this.blur();\" href=\"javascript:void(0);\" onmouseover=\"showSamelist('");
      out.print(mBean.getMd_seq());
      out.write("')\" onmouseout=\"closeSamelist('");
      out.print(i);
      out.write("')\">");
      out.print(mBean.getHtmlMd_title());
      out.write("</a>");
      out.print(star);
      out.write("</p>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=sameContent");
      out.print(mBean.getMd_seq());
      out.write(" style=\"display:none;width:200px;height:80px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(Html);
      out.write("       \r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓원문보기버튼-->\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\">\r\n");
      out.write("\t\t\t\t\t\t\t<img src=\"../../images/search/ico_original.gif\" style=\"cursor:pointer\" onclick=\"originalView('");
      out.print(mBean.getMd_seq());
      out.write("');\">\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓관리버튼-->\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(managerButton);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓유사-->\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(freeButton);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓수집시간-->\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(mBean.getFormatMd_date("MM-dd HH:mm"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t");
      out.write('\r');
      out.write('\n');

	}
}

      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("</div>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\t\r\n");
      out.write("\tparent.fillSameList('");
      out.print(sMtPno);
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
