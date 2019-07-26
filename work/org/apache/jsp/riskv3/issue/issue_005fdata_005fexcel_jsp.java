package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.issue.IssueMgr;
import risk.issue.IssueDataBean;
import risk.issue.IssueCodeBean;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCommentBean;
import risk.util.StringUtil;
import risk.util.DateUtil;
import risk.util.ParseRequest;
import risk.admin.member.MemberBean;
import risk.admin.member.MemberDao;
import java.util.ArrayList;
import java.net.URLDecoder;

public final class issue_005fdata_005fexcel_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/issue/../inc/sessioncheck.jsp");
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
      response.setContentType("application/vnd.ms-excel;charset=UTF-8");
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

//try{
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowPage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	System.out.println("searchKey : "+searchKey);
	
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String language = pr.getString("language","");
	String searchType = pr.getString("searchType", "1");
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	
	String id_mobile = pr.getString("id_mobile");
	
	String kind = pr.getString("kind");
	
	
	String srtMsg = null;
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		

	//관련정보 리스트
	IssueDataBean idBean = null;
	if(kind.equals("group")){
		arrIdBean = issueMgr.getIssueDataList_excel(nowPage,pageCnt,check_no,i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","",id_mobile,kind, searchType);
	}else{
		arrIdBean = issueMgr.getIssueDataList_excel(nowPage,pageCnt,check_no,i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","",id_mobile,kind, searchType
				);		
	}
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
   

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n");
      out.write("<style>\r\n");
      out.write("td { font-size:12px; color:#333333; font-family:\"gulim\"; ; line-height: 18px}\r\n");
      out.write("   input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }\r\n");
      out.write(".t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("   .tCopy { font-family: \"Tahoma\"; font-size: 12px; color: #000000; font-weight: bold}\r\n");
      out.write("a:link { color: #333333; text-decoration: none; }\r\n");
      out.write("a:visited { text-decoration: none; color: #000000; }\r\n");
      out.write("a:hover { text-decoration: none; color: #FF9900; }\r\n");
      out.write("a:active { text-decoration: none; }\r\n");
      out.write("\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 0px;\r\n");
      out.write("\tmargin-top: 0px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("\tSCROLLBAR-face-color: #F2F2F2;\r\n");
      out.write("\tSCROLLBAR-shadow-color: #999999;\r\n");
      out.write("\tSCROLLBAR-highlight-color: #999999;\r\n");
      out.write("\tSCROLLBAR-3dlight-color: #FFFFFF;\r\n");
      out.write("\tSCROLLBAR-darkshadow-color: #FFFFFF;\r\n");
      out.write("\tSCROLLBAR-track-color: #F2F2F2;\r\n");
      out.write("\tSCROLLBAR-arrow-color: #333333;\r\n");
      out.write("     }\r\n");
      out.write(".menu_black {  font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #000000}\r\n");
      out.write(".textw { font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}\r\n");
      out.write("\r\n");
      out.write(".menu_blue {\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3D679C;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".menu_gray {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #4B4B4B\r\n");
      out.write("}\r\n");
      out.write(".menu_red {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 12px; line-height: 16px; color: #CC0000\r\n");
      out.write("}\r\n");
      out.write(".menu_blueOver {\r\n");
      out.write("\r\n");
      out.write("\tfont-family: Tahoma;\r\n");
      out.write("\tfont-size: 11px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3D679C;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".menu_blueTEXTover {\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #3366CC;\r\n");
      out.write("\tfont-weight: normal;\r\n");
      out.write("}\r\n");
      out.write(".textwbig {\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal\r\n");
      out.write("}\r\n");
      out.write(".textBbig {\r\n");
      out.write("\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal\r\n");
      out.write("}\r\n");
      out.write(".menu_grayline {\r\n");
      out.write("\tfont-family: \"돋움\", \"돋움체\";\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("\tline-height: 16px;\r\n");
      out.write("\tcolor: #4B4B4B;\r\n");
      out.write("\ttext-decoration: underline;\r\n");
      out.write("}\r\n");
      out.write(".menu_grayS {\r\n");
      out.write("\r\n");
      out.write("font-family: \"돋움\", \"돋움체\"; font-size: 11px; line-height: 16px; color: #4B4B4B\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" border=\"1\" >\r\n");
      out.write("    <table width=\"1470\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("     \r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"120\" align=\"center\" bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 출처     </strong></td>\r\n");
      out.write("        <td width=\"440\" align=\"center\" bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 제목     </strong></td>\r\n");
      out.write("        <td width=\"200\" align=\"center\" bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> URL     </strong></td>\r\n");
      out.write("        <td width=\"120\" align=\"center\" bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 등록시간</strong></td>\r\n");
      out.write("        <td width=\"120\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 수집 시간 </strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 구분 </strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 정보구분 </strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 정보구분 상세 </strong></td>        \r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 대분류</strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 소분류</strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 실국</strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 부서</strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 성향 </strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 출처 </strong></td>\r\n");
      out.write("        <td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 주요이슈</strong></td>       \r\n");
      out.write("        \r\n");
      out.write("        ");

        	if(kind.equals("group")){
      out.write("\r\n");
      out.write("        \t\t<td width=\"100\" align=\"center\"  bgcolor=\"#C0C0C0\"  style=\"padding: 3px 0px 0px 0px;\"> <strong> 유사건수</strong></td>\r\n");
      out.write("        \t\t\r\n");
      out.write("        ");
	}
        
      out.write("\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    ");
  
    try{
    	for( int i = 0 ; i < arrIdBean.size() ; i++ ) {
    	
    	IssueDataBean IDBean = new IssueDataBean();
    	IDBean = (IssueDataBean) arrIdBean.get(i);
    	ArrayList arrIDCBean = new ArrayList();
    	arrIDCBean = IDBean.getArrCodeList();
    	
    	
    	String type1 = "";
      	String type2 = "";
      	String type21 = "";
      	String type3 = "";
      	String type31 = "";
      	String type5 = "";
      	String type51 = "";      	
      	String type4 = "";
      	String type6 = "";
      	String type9 = "";
		            	
      	//System.out.println(arrIDCBean.size()+" arrIDCBean.size() ");
      	
        if(arrIDCBean != null &&  arrIDCBean.size()>0){
        	type1 = icMgr.GetCodeNameType(arrIDCBean,1);
        	type2 = icMgr.GetCodeNameType(arrIDCBean,2);
        	type21 = icMgr.GetCodeNameType(arrIDCBean,21);
        	type3 = icMgr.GetCodeNameType(arrIDCBean,3);
        	type31 = icMgr.GetCodeNameType(arrIDCBean,31);
        	type5 = icMgr.GetCodeNameType(arrIDCBean,5);
        	type51 = icMgr.GetCodeNameType(arrIDCBean,51);        	
        	type4 = icMgr.GetCodeNameType(arrIDCBean,4);
        	type6 = icMgr.GetCodeNameType(arrIDCBean,6);
        	type9 = icMgr.GetCodeNameType(arrIDCBean,9);
        } 	
        //MemberBean mbean = dao.getMember("M_SEQ",IDBean.getM_seq());
    	
    
      out.write("\r\n");
      out.write("    <table width=\"1470\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"120\" align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(IDBean.getMd_site_name());
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td width=\"440\" align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          <a href=\"");
      out.print(IDBean.getId_url());
      out.write("\" target=\"_blank\" >");
      out.print(su.nvl(IDBean.getId_title(),"제목없음"));
      out.write("</a>\r\n");
      out.write("        </td>\r\n");
      out.write("        <td width=\"200\" align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          <a href=\"");
      out.print(IDBean.getId_url());
      out.write('"');
      out.write('>');
      out.print(IDBean.getId_url());
      out.write("</a>\r\n");
      out.write("        </td>\r\n");
      out.write("        <td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 5px;\">\r\n");
      out.write("          ");
      out.print(IDBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 5px;\">\r\n");
      out.write("          ");
      out.print(IDBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type1);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type2);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type21);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type3);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type31);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type5);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("          ");
      out.print(type51);
      out.write("\r\n");
      out.write("        </td>        \r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("           ");
      out.print(type9);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("           ");
      out.print(type6);
      out.write("\r\n");
      out.write("        </td>      \r\n");
      out.write("        <td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">\r\n");
      out.write("           ");
      out.print(type4);
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("        ");

        if(kind.equals("group")){
      out.write("\r\n");
      out.write("\t\t\t<td align=\"center\" class=\"menu_gray\" style=\"padding: 3px 0px 0px 0px;\">");
      out.print(IDBean.getMd_same_ct());
      out.write("</td>\r\n");
      out.write("        ");
	}
        
      out.write("      \r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    ");
  }
      out.write("\r\n");
      out.write("          ");
}catch(Exception e){System.out.println("issue_data_excel.jsp : "+e);} 
      out.write("\r\n");
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
