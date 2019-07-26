package org.apache.jsp.riskv3.admin.alimi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.admin.site.SitegroupBean;
import risk.search.siteDataInfo;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.search.keywordInfo;
import risk.mobile.AlimiSettingBean;
import risk.mobile.AlimiSettingDao;

public final class sub_005fkeywordgroup_005fradio_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	ParseRequest pr  = new ParseRequest(request);
	pr.printParams();
	
	String mode = pr.getString("mode");
	String k_xp = pr.getString("k_xp","0");
	String as_seq = pr.getString("as_seq");
	String selectedXp = "";
	String selectedYp = "";
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");    
    userEnvMgr uemng = new userEnvMgr();
    
    AlimiSettingDao asDao = new AlimiSettingDao();
    ArrayList alimiSetList = new ArrayList();
    AlimiSettingBean asBean = null;	
    
	
 	 //하위 키워드 그룹
    ArrayList arrGroup = new ArrayList();    
    arrGroup = uemng.getSubKeywordGroup(k_xp);
        
    if(mode.equals("UPDATE") && as_seq.length()>0)
	{	    		
   		alimiSetList = asDao.getAlimiSetList(1,0,as_seq,"Y");
   		asBean = (AlimiSettingBean)alimiSetList.get(0);
    	
   		selectedXp = asBean.getKg_xps();
		selectedYp = asBean.getKg_yps();
	}
    
    
    
	String[] arrSelected = null;
	String aSelected = "";
	String sSelected = "";						
	

      out.write("\r\n");
      out.write("\t\t<th><span class=\"board_write_tit\">키워드그룹</span></th>\r\n");
      out.write("\t\t<td>\r\n");

	if(!k_xp.equals("0")){		
		for( int i = 0; i < arrGroup.size() ; i++ ) {							
			keywordInfo kInfo = (keywordInfo)arrGroup.get(i);                    		
			sSelected = "";
			if(mode.equals("UPDATE")){
				arrSelected = selectedYp.split(",");					
				for(int j=0;j<arrSelected.length; j++)
				{
					if(selectedXp.equals(k_xp)&& arrSelected[j].equals(String.valueOf(kInfo.getK_Yp())))sSelected ="checked";
				}
				
			}				

      out.write("\r\n");
      out.write("\t\t<div style=\"width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left\" title=\"");
      out.print(kInfo.getK_Value());
      out.write("\"><input type=\"checkbox\" name=\"k_yp\" value=\"");
      out.print(kInfo.getK_Yp());
      out.write('"');
      out.write(' ');
      out.print(sSelected);
      out.write('>');
      out.print(kInfo.getK_Value());
      out.write("</div>\r\n");

		}
	}

      out.write("\r\n");
      out.write("\t\t</td>");
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
