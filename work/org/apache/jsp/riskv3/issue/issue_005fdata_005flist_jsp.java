package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import risk.issue.IssueDataBean;
import risk.issue.IssueMgr;
import java.util.*;
import risk.util.ParseRequest;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.issue.IssueBean;
import risk.search.MetaMgr;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.util.StringUtil;
import risk.util.DateUtil;
import risk.util.PageIndex;
import risk.admin.info.*;
import java.net.URLDecoder;

public final class issue_005fdata_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	ArrayList arrSiteGroup = null;
	
	int chk_cnt = 0;
	int nowpage = 1;	
	int pageCnt = 20;
	int totalCnt = 0;
	int totalPage = 0;	
	int sameCnt = 0;
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String id_mobile = pr.getString("id_mobile");
	String searchType = pr.getString("searchType", "1");
	
	
	String ir_stime = pr.getString("ir_stime","8");
	String ir_etime = pr.getString("ir_etime","18");
	
	String pseqVal = pr.getString("pseqVal", "");
	
	
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String issue_m_seq = pr.getString("issue_m_seq","");
	
	String check_no = pr.getString("check_no","");
	String srtMsg = null;
	String language = pr.getString("language");
	String parents = pr.getString("parents");
	
	//매체관리 
    String tiers = pr.getString("tiers", "");
    String temp[] = null;
    if(!tiers.equals("")){
    	temp = tiers.split(","); 
    }
    String tier1 = "";
    String tier2 = "";
    String tier3 = "";
    String tier4 = "";
    String tier5 = "";
    //if(temp.length > 0 && temp != null){
    if(temp != null){    	
    	for(int i =0; i < temp.length; i++){
        	if(temp[i].equals("1")){
        		tier1 = "checked=checked";
        	}else if(temp[i].equals("2")){
        		tier2 = "checked=checked";
        	}else if(temp[i].equals("3")){
        		tier3 = "checked=checked";
        	}else if(temp[i].equals("4")){
        		tier4 = "checked=checked";
        	}else if(temp[i].equals("5")){
        		tier5 = "checked=checked";
        	}
        }
    }
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}	
	
	//체크 박스 유지
	String[] check_cnt = null;
	String tmp = "";
	if(!check_no.equals("")){
		check_cnt 	= check_no.split(",");
		for(int i=0; i<check_cnt.length; i++){
			if(check_cnt[i].length()>1){
				chk_cnt++;
				if(tmp.equals("")){
					tmp = check_cnt[i];
				}else{
					tmp += ","+check_cnt[i];
				}
			}
		}
	}
	
	//관련정보 리스트
	IssueDataBean idBean = null;
	//if( request.getParameter("nowPage") != null ) nowPage = Integer.parseInt(request.getParameter("nowPage"));
	nowpage = pr.getInt("nowpage",1);
	arrIdBean = issueMgr.getIssueDataList_groupBy(nowpage,pageCnt,"",i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","", id_mobile, searchType, pseqVal,tiers, issue_m_seq);	
	totalCnt =  issueMgr.getTotalIssueDataCnt();
	//sameCnt = issueMgr.getTotalSameDataCnt();
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}
	
	
	
	//출처별 카운터 수 
	ArrayList arrSourceCount = new ArrayList();
	arrSourceCount = issueMgr.getSourceCnt();
	String strCnt = "";
	String arrCnt [] = new String[6];
	int totalDataCnt = 0;
	if(arrSourceCount.size() > 0){
		for(int i =0; i < arrSourceCount.size(); i++ ){
			arrCnt = (String[])arrSourceCount.get(i);
			strCnt += arrCnt[0]+": <strong>"+arrCnt[1]+"</strong> 건";
			totalDataCnt += Integer.parseInt(arrCnt[1]);
			if(i != arrSourceCount.size()-1){
				strCnt +=" / ";
			}
		}
	}
	
	
	
	
	srtMsg = " <b> 총 건수 : "+su.addComma(totalCnt+"")+" 건 (유사포함 : "+su.addComma(totalDataCnt+"")+" 건)  </b> "+nowpage+"/"+totalPage+" pages";
	
	//이슈 리스트를 불러온다.
	ArrayList arrIssueBean = new ArrayList();
	IssueBean isBean = new IssueBean();
	arrIssueBean = issueMgr.getIssueList(0,0, "", "", "", "","Y");
	
	//페이징 처리
	totalPage = 0;	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}
	
	// 사이트 그룹 정렬관련
	arrSiteGroup = issueMgr.getSiteGroup_order();
	String SiteGroups = "";
	for(int i =0; i < arrSiteGroup.size(); i++){
		if(SiteGroups.equals("")){
			SiteGroups = "[" + ((String[])arrSiteGroup.get(i))[1] + "]"; 
		}else{
			SiteGroups += " " + "[" + ((String[])arrSiteGroup.get(i))[1] + "]";
		}
	}
	
   	//정보그룹 관련
   	ArrayList igArr = new ArrayList();
   	InfoGroupMgr igMgr = new InfoGroupMgr();
   	InfoGroupBean igBean = new InfoGroupBean();
   	igArr = igMgr.getInfoGroup("Y");
   	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/base.css\" />\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}\r\n");
      out.write("</style>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/design.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery-ui-1.9.2.custom.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/design.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("\t$(document).ready(init);\r\n");
      out.write("\r\n");
      out.write("\t//검색 텍스트 활성화\r\n");
      out.write("\tfunction init(){\r\n");
      out.write("\t\tdocument.fSend.searchKey.focus();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//Url 링크\r\n");
      out.write(" \tvar chkPop = 1;\r\n");
      out.write("\tfunction hrefPop(url){\r\n");
      out.write("\t\t//window.open(url,'hrefPop'+chkPop,'');\r\n");
      out.write("\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');\r\n");
      out.write("\t\tchkPop++;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//페이징\r\n");
      out.write("\tfunction pageClick( paramUrl ) {\t\r\n");
      out.write("       \tvar f = document.fSend; \r\n");
      out.write("       \t\r\n");
      out.write("        f.action = 'issue_data_list.jsp' + paramUrl;\r\n");
      out.write("        f.target='';\r\n");
      out.write("        f.submit();\t        \r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    // 전체 선택 및 해제\r\n");
      out.write("    function checkAll(chk) {\r\n");
      out.write("    \t\r\n");
      out.write("    \tif($(\"[name=checkall]\").is(\":checked\")){\r\n");
      out.write("    \t\t$(\"[name=check]\").attr(\"checked\", true);\r\n");
      out.write("    \t}else{\r\n");
      out.write("    \t\t$(\"[name=check]\").attr(\"checked\", false);\r\n");
      out.write("    \t}\r\n");
      out.write(" \t}\r\n");
      out.write("\r\n");
      out.write("    // 각 메뉴의 마우스 오버시 이벤트\r\n");
      out.write("\tfunction mnu_over(obj) {\r\n");
      out.write("\t\tobj.style.backgroundColor = \"F3F3F3\";\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t// 각 메뉴의 마우스 아웃시 이벤트\r\n");
      out.write("\t// 현재 선택된 메뉴면\r\n");
      out.write("\tfunction mnu_out(obj) {\r\n");
      out.write("\t\tobj.style.backgroundColor = \"#FFFFFF\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getIssue(obj) {\r\n");
      out.write("\t\r\n");
      out.write("\t\tvar mt_no2=\"\";\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tif(obj.checked==true){\r\n");
      out.write("\t\t\tif(document.fSend.check_no.value==\"\"){\r\n");
      out.write("\t\t\t\tdocument.fSend.check_no.value = obj.value;\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tdocument.fSend.check_no.value += \",\"+obj.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tmt_no2 = document.fSend.check_no.value;\r\n");
      out.write("\t\t\tif(mt_no2.length<10){\r\n");
      out.write("\t\t\t\tdocument.fSend.check_no.value =\tmt_no2.replace(obj.value,\"\");\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tdocument.fSend.check_no.value =\tmt_no2.replace(\",\"+obj.value,\"\");\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write(" \t//타입코드 셋팅\r\n");
      out.write("\tfunction settingTypeCode()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar form = document.fSend;\r\n");
      out.write("\r\n");
      out.write("\t\tform.typeCode.value = '';\r\n");
      out.write("\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode1.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode1[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode1[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode1[i].value : '@'+form.typeCode1[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var  i=0;i<form.typeCode2.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode2[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode2[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode2[i].value : '@'+form.typeCode2[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode21.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode21[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode21[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode21[i].value : '@'+form.typeCode21[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode3.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode3[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode3[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode3[i].value : '@'+form.typeCode3[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode31.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode31[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode31[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode31[i].value : '@'+form.typeCode31[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t for(var i=0;i<form.typeCode4.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode4[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode4[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t for(var  i=0;i<form.typeCode5.length;i++){\r\n");
      out.write("\t\t\t\tif(form.typeCode5[i].selected)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tif(form.typeCode5[i].value!=''){\r\n");
      out.write("\t\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tfor(var i=0;i<form.typeCode51.length;i++){\r\n");
      out.write("\t\t\t\tif(form.typeCode51[i].selected)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tif(form.typeCode51[i].value!=''){\r\n");
      out.write("\t\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode51[i].value : '@'+form.typeCode51[i].value ;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t /*\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode5.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode5[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode5[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode6.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode6[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode6[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode6[i].value : '@'+form.typeCode6[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfor(var i=0;i<form.tier.length;i++){\r\n");
      out.write("\t\t\tif(form.tier[i].checked)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tform.tiers.value += form.tiers.value=='' ? form.tier[i].value : ','+form.tier[i].value ;\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t for(var i=0;i<form.typeCode7.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode7[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode7[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode7[i].value : '@'+form.typeCode7[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t /*\r\n");
      out.write("\t\t//추가분\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode8.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode8[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode8[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode8[i].value : '@'+form.typeCode8[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode9.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode9[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode9[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode9[i].value : '@'+form.typeCode9[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t/* \tfor(var i=0;i<form.typeCode10.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode10[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode10[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode10[i].value : '@'+form.typeCode10[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode11.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode11[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode11[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode11[i].value : '@'+form.typeCode11[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode12.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode12[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode12[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode12[i].value : '@'+form.typeCode12[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode14.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode14[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode14[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode14[i].value : '@'+form.typeCode14[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t} \r\n");
      out.write("\t\tfor(var i=0;i<form.typeCode31.length;i++){\r\n");
      out.write("\t\t\tif(form.typeCode31[i].selected)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(form.typeCode31[i].value!=''){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode31[i].value : '@'+form.typeCode31[i].value ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tform.issue_m_seq.value = $(\"#issueWriter option:selected\").val(); \r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//셀렉트 박스 이슈로 이슈 타이틀 변경\r\n");
      out.write("    function changeIssueTitle()\r\n");
      out.write("\t{    \t\r\n");
      out.write("\t\tajax.post('selectbox_issue_title.jsp','fSend','td_it_title');\r\n");
      out.write("\t}\t\r\n");
      out.write(" \t\r\n");
      out.write(" \t//검색을 기본검색에서 상세검색으로 보여준다.\r\n");
      out.write(" \tfunction openDetail() {\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("        var detail = document.getElementById(\"Detail\");\r\n");
      out.write("        var open = document.getElementById(\"open\");\r\n");
      out.write("        detail.style.display = '';\r\n");
      out.write("        open.style.display = 'none';             \r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\t//검색을 상세검색에서 기본검색으로 보여준다.\r\n");
      out.write("    function closeDetail() {\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("        var detail = document.getElementById(\"Detail\");\r\n");
      out.write("        var open = document.getElementById(\"open\");\t\r\n");
      out.write("        var close = document.getElementById(\"close\");\r\n");
      out.write("        detail.style.display = 'none';        \r\n");
      out.write("        open.style.display = '';  \r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    //검색\r\n");
      out.write(" \tfunction search(page){ \t\t\r\n");
      out.write(" \t\tvar f = document.fSend;\t\t\r\n");
      out.write("\t\t//타입코드 셋팅\r\n");
      out.write("\t\tsettingTypeCode();\r\n");
      out.write("\t\tf.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(page != null && page !=''){\r\n");
      out.write("\t\t\tf.nowpage.value = page;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tf.nowpage.value = '1';\r\n");
      out.write("\t\t}\r\n");
      out.write(" \t\tf.action = 'issue_data_list.jsp';\r\n");
      out.write(" \t\tf.target = '';\r\n");
      out.write(" \t\tf.submit();\r\n");
      out.write(" \t}    \r\n");
      out.write("    \r\n");
      out.write("      \r\n");
      out.write("    function deleteIssueData(){\r\n");
      out.write(" \t\tvar chkedseq = '';\r\n");
      out.write(" \t\t$(\"[name=check]\").each(function(){\r\n");
      out.write("\t\t\tif(this.checked){\r\n");
      out.write("\t\t\t\tif(this.value != ''){\r\n");
      out.write("\t\t\t\t\tif(chkedseq == ''){\r\n");
      out.write("\t\t\t\t\t\tchkedseq = this.value;\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tchkedseq += ','+this.value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tif(chkedseq == '') {\r\n");
      out.write("\t\t\talert('선택된 정보가 없습니다.'); return;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t//if(confirm(cnt+'건의 이슈를 삭제합니다.')) {\r\n");
      out.write("\t\t\tif(confirm('이슈를 삭제합니다.')) {\r\n");
      out.write("\t\t\t\t//f.mode.value = 'delete';\r\n");
      out.write("\t\t\t\t//f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);\t\t\t\t\r\n");
      out.write("\t\t\t\t//f.check_no.value = chkedseq;\r\n");
      out.write("\t\t\t\t//f.action=\"issue_data_prc.jsp\";\r\n");
      out.write("\t\t\t\t//f.target='processFrm';\r\n");
      out.write("\t\t\t\t//f.submit();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"[name=mode]\").val('delete');\r\n");
      out.write("\t\t\t\t$(\"[name=check_no]\").val(chkedseq);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t \t\t\ttype: 'POST',\r\n");
      out.write("\t\t \t\t\turl: 'issue_data_prc.jsp',\r\n");
      out.write("\t\t \t\t\tdataType: 'text',\r\n");
      out.write("\t\t \t\t\tdata: $(\"#fSend\").serialize(),\r\n");
      out.write("\t\t \t\t\tsuccess:function(data){\r\n");
      out.write("\t\t \t\t\t\t\r\n");
      out.write("\t\t \t\t\t\talert(\"삭제 되었습니다.\");\r\n");
      out.write("\t \t\t\t\t\t$(\"[name=mode]\").val('');\r\n");
      out.write("\t \t\t\t\t\t$(\"[name=check_no]\").val('');\r\n");
      out.write("\t \t\t\t\t\t$(\"#btn_search\").click();\r\n");
      out.write("\t \t\t\t\t\t//$(\"#fSend\").submit();\r\n");
      out.write("\t\t \t\t\t\t\r\n");
      out.write("\t\t \t\t\t\t//var result = data.trim();\r\n");
      out.write("\t\t \t\t\t\t//if(result == \"success\"){\r\n");
      out.write("\t\t \t\t\t\t//\talert(\"삭제 되었습니다.\");\r\n");
      out.write("\t\t \t\t\t\t//\t$(\"[name=mode]\").val('');\r\n");
      out.write("\t\t \t\t\t\t//\t$(\"[name=check_no]\").val('');\r\n");
      out.write("\t\t \t\t\t\t//\t$(\"#fSend\").submit();\r\n");
      out.write("\t\t \t\t\t\t//}else{\r\n");
      out.write("\t\t \t\t\t\t//\talert(\"삭제에 실패했습니다. 다시 시도해주세요. 문제가 계속 될 경우 담당자에게 연락주시기 바랍니다.\");\r\n");
      out.write("\t\t \t\t\t\t//\t//$(\"#fSend\").submit();\r\n");
      out.write("\t\t \t\t\t\t//}\r\n");
      out.write("\t\t \t\t\t}\r\n");
      out.write("\t\t \t\t\t,error:function(){\r\n");
      out.write("\t\t \t\t\t\talert(\"삭제에 실패했습니다. 다시 시도해주세요. 문제가 계속 될 경우 담당자에게 연락주시기 바랍니다.\");\r\n");
      out.write("\t\t \t\t\t}\r\n");
      out.write("\t\t \t\t});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction goMailTo() {\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar o = $(\"[name=check]\");\r\n");
      out.write(" \t\tvar chkedseq='';\r\n");
      out.write(" \t\tvar cnt = ");
      out.print(chk_cnt);
      out.write(";\r\n");
      out.write(" \t\tvar check_no = \"\";\r\n");
      out.write(" \t\tcheck_no = '");
      out.print(check_no);
      out.write("';\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tfor(i=0; i<o.length; i++) {\r\n");
      out.write(" \t\t\tif(o[i].checked == true){\r\n");
      out.write("\t \t\t\tif(o[i].value!=\"\"){\r\n");
      out.write("\t \t\t\t\tif(chkedseq == ''){\r\n");
      out.write("\t \t\t\t\t\tchkedseq = o[i].value;\r\n");
      out.write("\t \t\t\t\t}else{\r\n");
      out.write("\t \t\t\t\t\tchkedseq = chkedseq+','+o[i].value;\r\n");
      out.write("\t \t\t\t\t}\r\n");
      out.write("\t \t\t\t\tcnt++;\r\n");
      out.write("\t \t\t\t}\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t}\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write("\t\t\t\tif(chkedseq==\"\"){\r\n");
      out.write("\t\t\t\t\tchkedseq = check_no;\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tif(check_no!=\"\"){\r\n");
      out.write("\t\t\t\t\t\tchkedseq += \",\"+check_no;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\tif(chkedseq == '') {\r\n");
      out.write("\t\t\talert('선택된 정보가 없습니다.'); return;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse {\r\n");
      out.write("\t\t\tif(confirm(cnt+'건의 이슈를 발송합니다.')) {\r\n");
      out.write("\t\t\t\tf.mode.value = 'mail';\r\n");
      out.write("\t\t\t\tf.check_no.value = chkedseq;\r\n");
      out.write("\t\t\t\tf.ir_type.value = 'E';\r\n");
      out.write("\t\t\t\twindow.open(\"about:blank\",'goMailTo', 'width=850,height=700,scrollbars=yes');\r\n");
      out.write("\t       \t\tf.action = 'pop_urgent_mail.jsp';\r\n");
      out.write("\t       \t\t//f.action = 'pop_emergency_report_form.jsp';\r\n");
      out.write("\t       \t\tf.target='goMailTo';\r\n");
      out.write("\t\t\t\tf.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction goSmsTo(idseq){\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write(" \t\tf.mode.value = 'sms';\r\n");
      out.write("\t\tf.check_no.value = idseq;\r\n");
      out.write("\t\tf.ir_type.value = 'E';\r\n");
      out.write("\t\twindow.open(\"about:blank\",'goSmsTo', 'width=850,height=700,scrollbars=yes');\r\n");
      out.write("   \t\tf.action = 'pop_urgent_sms.jsp';\r\n");
      out.write("   \t\tf.target='goSmsTo';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction goExcelTo(kind){\r\n");
      out.write(" \t\tvar o=document.getElementsByName('tt');\r\n");
      out.write(" \t\tvar chkedseq='';\r\n");
      out.write(" \t\tvar cnt = 0;\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tfor(i=0; i<o.length; i++) {\r\n");
      out.write(" \t\t\tif(o[i].checked == true){\r\n");
      out.write("\t \t\t\tif(o[i].value!=\"\"){\r\n");
      out.write("\t \t\t\t\tif(chkedseq == ''){\r\n");
      out.write("\t \t\t\t\t\tchkedseq = o[i].value;\r\n");
      out.write("\t \t\t\t\t}else{\r\n");
      out.write("\t \t\t\t\t\tchkedseq = chkedseq+','+o[i].value;\r\n");
      out.write("\t \t\t\t\t}\r\n");
      out.write("\t \t\t\t\tcnt++;\r\n");
      out.write("\t \t\t\t}\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t}\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tf.check_no.value = chkedseq;\r\n");
      out.write("\t\tf.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tf.action = 'issue_data_excel.jsp?kind='+kind;\r\n");
      out.write("\t\tf.target = 'processFrm';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\tf.action = 'issue_list.jsp';\r\n");
      out.write("\t\tf.target = 'processFrm';\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction excelForPOI(){\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar o=document.getElementsByName('tt');\r\n");
      out.write(" \t\tvar chkedseq='';\r\n");
      out.write(" \t\tvar cnt = 0;\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tfor(i=0; i<o.length; i++) {\r\n");
      out.write(" \t\t\tif(o[i].checked == true){\r\n");
      out.write("\t \t\t\tif(o[i].value!=\"\"){\r\n");
      out.write("\t \t\t\t\tif(chkedseq == ''){\r\n");
      out.write("\t \t\t\t\t\tchkedseq = o[i].value;\r\n");
      out.write("\t \t\t\t\t}else{\r\n");
      out.write("\t \t\t\t\t\tchkedseq = chkedseq+','+o[i].value;\r\n");
      out.write("\t \t\t\t\t}\r\n");
      out.write("\t \t\t\t\tcnt++;\r\n");
      out.write("\t \t\t\t}\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t}\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tf.check_no.value = chkedseq;\r\n");
      out.write("\t\tf.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//f.action = '/ExcelView';\r\n");
      out.write("\t\tf.action = 'getExcelData.jsp';\r\n");
      out.write("\t\tf.target = 'processFrm';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\t//f.action = 'issue_list.jsp';\r\n");
      out.write("\t\t//f.target = 'processFrm';\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\t//$.ajax({\r\n");
      out.write(" \t\t//\ttype:'POST',\r\n");
      out.write(" \t\t//\turl:\"getExcelData.jsp\",\r\n");
      out.write(" \t\t//\tdataType:'text',\r\n");
      out.write(" \t\t//\tdata:$(\"#fSend\").serialize(),\r\n");
      out.write(" \t\t//\tsuccess:function(data){\r\n");
      out.write(" \t\t//\t\tconsole.log(data);\r\n");
      out.write(" \t\t//\t\t$(\"#excelView\").append(data);\r\n");
      out.write(" \t\t//\t\t\r\n");
      out.write(" \t\t//\t\t$(\"#exportTable\").tableExport({type:'csv',escape:'false'});\r\n");
      out.write(" \t\t//\t}\r\n");
      out.write(" \t\t//\t,error:function(){\r\n");
      out.write(" \t\t//\t\talert(\"다시 선택해주세요\");\r\n");
      out.write(" \t\t//\t}\r\n");
      out.write(" \t\t//});\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t}\r\n");
      out.write("\r\n");
      out.write(" \t//관심정보 변경 폼\r\n");
      out.write(" \tfunction PopIssueDataForm(md_seq, child){\r\n");
      out.write(" \t\tvar f = document.fSend; \r\n");
      out.write(" \t\tf.md_seq.value = md_seq;\r\n");
      out.write(" \t\tf.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);\r\n");
      out.write(" \t\tf.mode.value = 'update';\r\n");
      out.write(" \t\tf.child.value = child;\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\t//popup.openByPost('fSend','pop_issueData_form_checkBox.jsp',1380,860,false,true,false,'send_issue');\r\n");
      out.write(" \t\tpopup.openByPost('fSend','new_pop_issueData_form.jsp',1380,860,false,true,false,'send_issue');\r\n");
      out.write(" \t\r\n");
      out.write(" \t}\r\n");
      out.write("\r\n");
      out.write(" \tvar pre_SameList = '';\r\n");
      out.write(" \tvar pre_ic_seq = '';\r\n");
      out.write(" \tfunction getSourceData(md_pseq, ic_seq, ic_name){\r\n");
      out.write("\r\n");
      out.write(" \t\tvar f = document.fSend; \r\n");
      out.write(" \t \t\r\n");
      out.write(" \t\tvar sameLayer = document.getElementById('SameList_' + md_pseq);\r\n");
      out.write("\r\n");
      out.write(" \t\tif(pre_SameList == sameLayer && pre_ic_seq == ic_seq && pre_SameList.style.display==''){\r\n");
      out.write("     \t\tpre_SameList.style.display = 'none';\r\n");
      out.write("     \t\treturn;\r\n");
      out.write("     \t}\r\n");
      out.write("\r\n");
      out.write(" \t\tif(pre_SameList){\r\n");
      out.write("     \t\tpre_SameList.style.display = 'none';\r\n");
      out.write("     \t}\r\n");
      out.write("\r\n");
      out.write(" \t\t\r\n");
      out.write("\r\n");
      out.write(" \t\tpre_SameList = sameLayer;\r\n");
      out.write(" \t\tpre_ic_seq = ic_seq;\r\n");
      out.write("     \tsameLayer.innerHTML = '로딩중...';\r\n");
      out.write("     \tif_samelist.location.href = \"inc_same_list.jsp?md_pseq=\" + md_pseq \r\n");
      out.write("     \t                                           + \"&ic_seq=\" + ic_seq \r\n");
      out.write("     \t                                           + \"&ic_name=\" + encodeURI(ic_name) \r\n");
      out.write("     \t                                           + \"&sDateFrom=\" + f.sDateFrom.value\r\n");
      out.write("     \t                                           + \"&sDateTo=\" + f.sDateTo.value\r\n");
      out.write("     \t                                           + \"&ir_stime=\" + f.ir_stime.value\r\n");
      out.write("     \t                                           + \"&ir_etime=\" + f.ir_etime.value;\r\n");
      out.write("     \tsameLayer.style.display = '';\r\n");
      out.write(" \t}\r\n");
      out.write(" \tfunction fillSameList(no){\r\n");
      out.write("     \tvar ly = document.getElementById('SameList_'+no);    \t\r\n");
      out.write(" \t    ly.innerHTML = if_samelist.zzFilter.innerHTML;\r\n");
      out.write("     }\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction getOption(now, target){\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar icType1 = \"\";\r\n");
      out.write(" \t\tvar code = $(\"[name=\"+now+\"] option:selected\").val();\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tif(code == \"\"){\r\n");
      out.write(" \t\t\treturn;\r\n");
      out.write(" \t\t}\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar temp = code.split(\",\");\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar option = \"<option value=''>선택하세요</option>\";\r\n");
      out.write(" \t\t$.ajax({\r\n");
      out.write(" \t\t\ttype:'POST',\r\n");
      out.write(" \t\t\turl:\"getJsonData.jsp\",\r\n");
      out.write(" \t\t\tdataType:'json',\r\n");
      out.write(" \t\t\tdata:{ic_Ptype:temp[0], ic_Pcode:temp[1], flag: \"2death\", icType1:icType1},\r\n");
      out.write(" \t\t\tsuccess:function(data){\r\n");
      out.write(" \t\t\t\t$.each(data.list, function(index){\r\n");
      out.write(" \t\t\t\t\toption += \"<option value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"'>\"+data.list[index].icName+\"</option>\";\r\n");
      out.write(" \t\t\t\t});\r\n");
      out.write(" \t\t\t\t$(\"[name=\"+target+\"]\").empty().append(option);\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t\t,error:function(){\r\n");
      out.write(" \t\t\t\talert(\"다시 선택해주세요\");\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t});\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tfunction getOptionType2(){\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tvar valueis = $(\"[name=typeCode1] option:selected\").val();\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tif(valueis == \"\"){\r\n");
      out.write(" \t\t\treturn;\r\n");
      out.write(" \t\t}\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tvar option = \"<option value=''>선택하세요</option>\";\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write(" \t\t\ttype:'POST',\r\n");
      out.write(" \t\t\turl:\"getJsonData.jsp\",\r\n");
      out.write(" \t\t\tdataType:'json',\r\n");
      out.write(" \t\t\tdata:{flag: \"0death\", icType1:icType1},\r\n");
      out.write(" \t\t\tsuccess:function(data){\r\n");
      out.write(" \t\t\t\t$.each(data.list, function(index){\r\n");
      out.write(" \t\t\t\t\toption += \"<option value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"'>\"+data.list[index].icName+\"</option>\";\r\n");
      out.write(" \t\t\t\t});\r\n");
      out.write(" \t\t\t\t\r\n");
      out.write(" \t\t\t\t$(\"[name=typeCode2]\").empty().append(option);\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t\t,error:function(){\r\n");
      out.write(" \t\t\t\talert(\"다시 선택해주세요\");\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t});\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \t//멀티 수정\r\n");
      out.write(" \tfunction muti_updateIssueData(mode){\r\n");
      out.write(" \t\tvar idSeqs = \"\";\r\n");
      out.write(" \t\t$(\"[name=check]\").each(function(){\r\n");
      out.write(" \t\t\tif( $(this).prop(\"checked\") ){\r\n");
      out.write(" \t\t\t\tif(idSeqs == \"\"){\r\n");
      out.write(" \t\t\t\t\tidSeqs = $(this).val();\r\n");
      out.write(" \t\t\t\t}else{\r\n");
      out.write(" \t\t\t\t\tidSeqs += \",\"+$(this).val();\t\r\n");
      out.write(" \t\t\t\t}\r\n");
      out.write(" \t\t\t}\r\n");
      out.write(" \t\t});\r\n");
      out.write(" \t\t\r\n");
      out.write(" \t\tdocument.fSend.mode.value = mode;\r\n");
      out.write(" \t\t$(\"[name=issue_m_seq]\").val(idSeqs);\r\n");
      out.write(" \t\tif(document.fSend.issue_m_seq.value==''){alert('정보를 선택해주세요.'); return;}\r\n");
      out.write(" \t\tpopup.openByPost('fSend','pop_multi_issueData_form_.jsp',650,450,false,true,false,'send_issue');\r\n");
      out.write(" \t}\r\n");
      out.write(" \t\r\n");
      out.write(" \tvar chkOriginal = 1;\r\n");
      out.write("\tfunction portalSearch(s_seq, md_title){\r\n");
      out.write("\t\tif(s_seq == '3555'){\r\n");
      out.write("\t\t\t//네이버까페\r\n");
      out.write("\t\t\t//url = \"http://section.cafe.naver.com/CombinationSearch.nhn?query=\" + md_title;\r\n");
      out.write("\t\t\turl = \"https://section.cafe.naver.com/cafe-home/search/articles?query=\\\"\"+md_title+\"\\\"\";\t\t\r\n");
      out.write("\t\t\t//window.open(url,'hrefPop'+chkOriginal,'');\r\n");
      out.write("\t\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');\r\n");
      out.write("\t\t}else if(s_seq == '4943'){\r\n");
      out.write("\t\t\t//다음까페\r\n");
      out.write("\t\t\turl = \"http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=\" + md_title;\r\n");
      out.write("\t\t\t//window.open(url,'hrefPop'+chkOriginal,'');\r\n");
      out.write("\t\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tchkOriginal ++;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkTiers(){\r\n");
      out.write("\t\tvar legnth = 0;\r\n");
      out.write("\t\t$(\"[name=tier]\").each(function(){\r\n");
      out.write("\t\t\tif($(this).attr(\"checked\")){\r\n");
      out.write("\t\t\t\tlegnth += 1;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t});\t\r\n");
      out.write("\t\r\n");
      out.write("\t\tif(legnth == 4 ){\r\n");
      out.write("\t\t\t$(\"[name=tierAll]\").attr(\"checked\", true);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=tierAll]\").attr(\"checked\", false);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" action=\"\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"encodingSearchKey\" value=\"");
      out.print(searchKey);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(nowpage );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"md_seq\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"typeCode\" value=\"");
      out.print(typeCode );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"id_seq\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"ir_type\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"check_no\" value=\"");
      out.print(check_no);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"selItseq\" value=\"");
      out.print(it_seq);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"child\" >\r\n");
      out.write("<input type=\"hidden\" name=\"issue_m_seq\" value=\"\">\r\n");
      out.write("<input name=\"tiers\" type=\"hidden\">\r\n");
      out.write("<table style=\"height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\" style=\"width:auto\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;margin-left:20px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"al\"><img src=\"../../images/issue/tit_icon.gif\" /><img src=\"../../images/issue/tit_0201.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">이슈관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">관련정보</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<!-- 검색 -->\r\n");
      out.write("\t\t\t<div class=\"article\">\r\n");
      out.write("\t\t\t\t<div class=\" ui_searchbox_00\">\r\n");
      out.write("\t\t\t\t\t<div class=\"c_wrap\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title\"><!-- <span class=\"icon\">-</span>검색단어</span> -->\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"searchType\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"1\" ");
if(searchType.equals("1"))out.print("selected");
      out.write(">제목</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"2\" ");
if(searchType.equals("2"))out.print("selected");
      out.write(">제목+내용</option></select>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input name=\"searchKey\" value=\"");
      out.print(searchKey);
      out.write("\" onkeydown=\"Javascript:if(event.keyCode == 13){search();}\" id=\"input_search_char\" type=\"text\" class=\"ui_input_02\" style=\"width:160px\"><label for=\"input_search_char\" class=\"invisible\">검색어 입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title m_l_20\"><span class=\"icon\">-</span>검색기간</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"sDateFrom\" name=\"sDateFrom\" value=\"");
      out.print(sDateFrom);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_first\" style=\"width:70px\" readonly><label for=\"sDateFrom\">날짜입력</label\r\n");
      out.write("\t\t\t\t\t\t\t\t><select id=\"ir_stime\" name=\"ir_stime\" class=\"ui_select_04\" style=\"margin-right:0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
 
								String sBasics  = ir_stime.equals("") ? "8" :  ir_stime;
			                	String eBasics  = ir_etime.equals("") ? "18" :  ir_etime;
								for(int i=0; i< 24; i++){
								
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"");
      out.print(i);
      out.write('"');
      out.write(' ');
if(sBasics.equals(i+"")){out.print("selected");} 
      out.write('>');
      out.print(i);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"ir_stime\"></label\r\n");
      out.write("\t\t\t\t\t\t\t\t> ~ <input id=\"sDateTo\" name=\"sDateTo\" value=\"");
      out.print(sDateTo);
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_last\" style=\"width:70px\" readonly><label for=\"sDateTo\">날짜입력</label\r\n");
      out.write("\t\t\t\t\t\t\t\t><select id=\"ir_etime\" name=\"ir_etime\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
 
								for(int i=0; i< 24; i++){
								
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"");
      out.print(i);
      out.write('"');
      out.write(' ');
if(eBasics.equals(i+"")){out.print("selected");} 
      out.write('>');
      out.print(i);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
 
								}	
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"ir_etime\"></label\r\n");
      out.write("\t\t\t\t\t\t\t\t><button class=\"ui_btn_02\" id=\"btn_search\" onclick=\"search();return false;\"><span class=\"icon search_01\">-</span>검색</button>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"c_sort\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t");

								String selected = "";
								String codeTypeName = "";
								arrIcBean = icMgr.GetType(1);
								codeTypeName = icMgr.GetCodeName(arrIcBean,0);
							
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode1\" name=\"typeCode1\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
							
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
}
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode1\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write(" 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t<!-- </div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_row_00\"> -->\r\n");
      out.write("\t\t\t\t\t\t");

							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(2);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode2\" name=\"typeCode2\" class=\"ui_select_04\" onchange=\"getOption('typeCode2','typeCode21')\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode2\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("1 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(21);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode21\" name=\"typeCode21\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode21\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("2 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(9);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode9\" name=\"typeCode9\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(!"0".equals(icBean.getIc_code()+"") ){
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode9\" class=\"invisible\"> 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(6);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode6\" name=\"typeCode6\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(!"0".equals(icBean.getIc_code()+"") ){
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode6\" class=\"invisible\"> 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");

							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(3);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode3\" name=\"typeCode3\" class=\"ui_select_04\" onchange=\"getOption('typeCode3','typeCode31')\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode3\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("1 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(31);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode31\" name=\"typeCode31\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode31\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("2 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");

							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(5);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode5\" name=\"typeCode5\" class=\"ui_select_04\" onchange=\"getOption('typeCode5','typeCode51')\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode5\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("1 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(51);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode51\" name=\"typeCode51\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode51\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write("2 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(4);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode4\" name=\"typeCode4\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode4\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write(" 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");

									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(7);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>");
      out.print(codeTypeName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"typeCode7\" name=\"typeCode7\" class=\"ui_select_07\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"typeCode7\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write(" 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"title wid_70px\"><span class=\"icon\">-</span>등록자</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"txts wid_120px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"issueWriter\" name=\"issueWriter\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								List listMember = issueMgr.getMember("2,3");
								for(int i = 0; i < listMember.size(); i++){
									String mem[] = (String[])listMember.get(i);
									if(issue_m_seq.equals(mem[0])) selected = "selected";
									else selected = "";
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(mem[0]);
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write('>');
      out.print(mem[1]);
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
}
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</select><label for=\"issueWriter\" class=\"invisible\">");
      out.print(codeTypeName);
      out.write(" 선택</label>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>매체관리</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" id=\"ui_input_chk_02_00\" class=\"boardAllChecker\" value=\"\"><label for=\"ui_input_chk_02_00\"><span class=\"chkbox_00\"></span><strong>Tier 전체</strong></label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" onclick=\"checkTiers();\" ");
      out.print(tier1 );
      out.write(" id=\"ui_input_chk_02_01\" value=\"1\"><label for=\"ui_input_chk_02_01\"><span class=\"chkbox_00\"></span>Tiering 1</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" onclick=\"checkTiers();\" ");
      out.print(tier2 );
      out.write(" id=\"ui_input_chk_02_02\" value=\"2\"><label for=\"ui_input_chk_02_02\"><span class=\"chkbox_00\"></span>Tiering 2</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" onclick=\"checkTiers();\" ");
      out.print(tier3 );
      out.write(" id=\"ui_input_chk_02_03\" value=\"3\"><label for=\"ui_input_chk_02_03\"><span class=\"chkbox_00\"></span>Tiering 3</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" onclick=\"checkTiers();\" ");
      out.print(tier4 );
      out.write(" id=\"ui_input_chk_02_04\" value=\"4\"><label for=\"ui_input_chk_02_04\"><span class=\"chkbox_00\"></span>Tiering 4</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input name=\"tier\" type=\"checkbox\" onclick=\"checkTiers();\" ");
      out.print(tier5 );
      out.write(" id=\"ui_input_chk_02_05\" value=\"5\"><label for=\"ui_input_chk_02_05\"><span class=\"chkbox_00\"></span>Tiering 5</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"ui_searchbox_toggle\">\r\n");
      out.write("\t\t\t\t\t<a href=\"#\" class=\"btn_toggle active\">검색조건 열기/닫기</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t<!-- // 검색 -->\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"100%\" align=\"left\" style=\"padding-right: 10px; padding-top: 5px\"><img src=\"../../images/search/icon_search_bullet.gif\" style=\"vertical-align: middle;\">");
      out.print(srtMsg);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"100%\" align=\"left\" style=\"padding-right: 10px; padding-top: 5px\"><img src=\"../../images/search/icon_search_bullet.gif\" style=\"vertical-align: middle;\"> \r\n");
      out.write("\t\t\t\t\t\t<strong>출처별</strong> 검색결과: ");
      out.print(strCnt);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</table>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/issue/btn_allselect.gif\" onclick=\"checkAll(!document.fSend.checkall.checked);\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/issue/btn_mailsend.gif\" onclick=\"goMailTo();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<!-- <td><img src=\"../../images/issue/btn_exelsave.gif\" onclick=\"goExcelTo('group');\" style=\"cursor:pointer;\"/></td> -->\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/issue/btn_allexelsave.gif\" onclick=\"goExcelTo('all');\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<!-- <td><img src=\"../../images/issue/btn_allexelsave.gif\" onclick=\"excelForPOI();\" style=\"cursor:pointer;\"/></td> -->\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/issue/btn_del.gif\" onclick=\"deleteIssueData();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/issue/btn_multi.gif\" onclick=\"muti_updateIssueData('update_multi');\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"100%\" align=\"right\" style=\"padding-right: 10px; padding-top: 15px\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout: fixed;\">\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"22%\"><col width=\"*\"><col width=\"5%\"><!-- <col width=\"5%\"> --><col width=\"12%\"><col width=\"10%\"><col width=\"5%\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th><input type=\"checkbox\" name=\"checkall\" id=\"tt\" onclick=\"checkAll(this.checked);\" value=\"\"></th>\r\n");
      out.write("\t\t\t\t\t\t<th>출처</th>\r\n");
      out.write("\t\t\t\t\t\t<th>제목</th>\r\n");
      out.write("\t\t\t\t\t\t<th></th>\r\n");
      out.write("\t\t\t\t\t\t<!-- <th></th> -->\r\n");
      out.write("\t\t\t\t\t\t<th>유사</th>\r\n");
      out.write("\t\t\t\t\t\t<th>수집일시</th>\r\n");
      out.write("\t\t\t\t\t\t<th>성향</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		String sunghyang = "";
		String update_writer = "";	//수정완료된 글
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (IssueDataBean)arrIdBean.get(i);
				arrIdcBean = (ArrayList) idBean.getArrCodeList();
				
				//성향
				sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
				update_writer = idBean.getId_writter();
				//유사 타이틀 만들기~~
				String strSites = "";
				String strSitesValue = "";
				String[] ar_SiteCnt = idBean.getSite_group_same_ct().split(",");
				String siteCheck = "";
				String child = "";
				
				int sameCt = 0;
				if(arrSiteGroup.size() == ar_SiteCnt.length){
				 	for(int j = 0; j < arrSiteGroup.size(); j++){
				 		siteCheck = ar_SiteCnt[j];
				 		sameCt += Integer.parseInt(siteCheck); 
				 		if(strSites.equals("")){
				 			strSites = ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
				 			if(ar_SiteCnt[j].equals("0")){
				 				strSitesValue = ar_SiteCnt[j];
				 			}else{
				 				strSitesValue = "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">"+ siteCheck + "</a>";
				 			}
				 		}else{
				 			strSites += ", " + ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
				 			if(ar_SiteCnt[j].equals("0")){
				 				strSitesValue += "," + ar_SiteCnt[j];
				 			}else{
				 				strSitesValue += "," + "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">" + siteCheck +"</a>";
				 			}
				 		}
				 	}
				}
				if(sameCt > 0){
					child = "Y";
				}else{
					child = "N";
				}
				//TR 색갈변경하기
				String titleColor = "";
				if( !"".equals(update_writer) && update_writer != null && !"null".equals(update_writer) ){ 
					titleColor = "";
				}else{
					titleColor = "";
				}
				
				String star = "";
				//카페 일경우~	        	
	        	if(idBean.getS_seq().equals("3555") || idBean.getS_seq().equals("4943")){
	        		star = "<img src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+idBean.getS_seq()+"','"+java.net.URLEncoder.encode(idBean.getId_title(),"utf-8")+"')>";
	        	}
				

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr style=\"background-color: ");
      out.print(titleColor);
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t<td><input name=\"check\" type=\"checkbox\" value=\"");
      out.print(idBean.getId_seq());
      out.write("\" onclick=\"getIssue(this);\" ");
if(check_cnt!=null){for(int j=0; j<check_cnt.length; j++){if(check_cnt[j].equals(idBean.getMd_seq())){out.print("checked");}}}
      out.write("></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align: left;\"><span style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" class=\"board_01_tit_0");
      out.print(idBean.getMd_type());
      out.write("\" title=\"");
      out.print(idBean.getMd_site_name());
      out.write('"');
      out.write('>');
      out.print(idBean.getMd_site_name());
      out.write("</span></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align: left;\"><div style=\"width:320px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" title=\"");
      out.print(idBean.getId_title());
      out.write('"');
      out.write('>');
      out.print(star);
      out.write("<a href=\"javascript:PopIssueDataForm('");
      out.print(idBean.getMd_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(child);
      out.write("');\">");
      out.print(idBean.getId_title());
      out.write("</a></div></td>\r\n");
      out.write("\t\t\t\t\t\t<td><a onClick=\"hrefPop('");
      out.print(idBean.getId_url());
      out.write("');\" href=\"javascript:void(0);\" ><img src=\"../../images/issue/ico_original.gif\" align=\"absmiddle\" /></a></td>\r\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td title=\"");
      out.print(strSites);
      out.write('"');
      out.write('>');
      out.print(strSitesValue);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(idBean.getFormatMd_date("yyyy-MM-dd"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td><p class=\"tendency_0");
if(sunghyang.equals("긍정")){out.print("1");}else if(sunghyang.equals("중립")){out.print("2");}else{out.print("3");}
      out.write('"');
      out.write('>');
      out.print(sunghyang);
      out.write("</p></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"same\" colspan=\"6\">\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"SameList_");
      out.print(idBean.getMd_pseq());
      out.write("\" style=\"display:none;\"></div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

			}
		}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"al\" style=\"height:40px\">\r\n");
      out.write("\t\t\t\t<img src=\"../../images/issue/btn_allselect.gif\" onclick=\"checkAll(!document.fSend.checkall.checked);\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t<img src=\"../../images/issue/btn_mailsend.gif\" onclick=\"goMailTo();\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t<!-- <img src=\"../../images/issue/btn_exelsave.gif\" onclick=\"goExcelTo('group');\" style=\"cursor:pointer;\"/> -->\r\n");
      out.write("\t\t\t\t<img src=\"../../images/issue/btn_allexelsave.gif\" onclick=\"goExcelTo('all');\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t<img src=\"../../images/issue/btn_del.gif\" onclick=\"deleteIssueData();\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 페이징 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"padding-top:10px\">\r\n");
      out.write("\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" style=\"margin:0 auto\">\r\n");
      out.write("\t\t\t\t\t<tr>");
      out.print(PageIndex.getPageIndex(nowpage, totalPage,"","" ));
      out.write("</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 페이징 -->\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("</form>\r\n");
      out.write("<div id=\"excelView\" style=\"display: none;\"></div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr><td><img src=\"../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td></tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" background=\"../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr><td height=\"6\"></td></tr>\r\n");
      out.write("\t\t\t<tr><td id=\"calendar_calclass\"></td></tr>\r\n");
      out.write("\t\t\t<tr><td height=\"5\"></td></tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr><td><img src=\"../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td></tr>\r\n");
      out.write("</table>\r\n");
      out.write("<iframe id=\"processFrm\" name =\"processFrm\" width=\"0\" height=\"0\" style=\"display: none;\" ></iframe>\r\n");
      out.write("<iframe id=\"if_samelist\" name=\"if_samelist\" width=\"0\" height=\"0\" src=\"about:blank\" style=\"display: none;\"></iframe>\r\n");
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
