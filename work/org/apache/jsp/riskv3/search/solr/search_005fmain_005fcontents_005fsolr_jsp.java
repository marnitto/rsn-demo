package org.apache.jsp.riskv3.search.solr;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.search.MetaBean;
import risk.search.MetaMgr;
import risk.util.PageIndex;
import risk.search.solr.SolrSearch;
import risk.search.solr.SearchForm;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.issue.IssueMgr;
import risk.issue.IssueBean;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.search.solr.SolrMgr;
import risk.util.ConfigUtil;

public final class search_005fmain_005fcontents_005fsolr_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/search/solr/../../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");

	ParseRequest pr = new ParseRequest(request);
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	pr.printParams();
	
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	
	String startDate   = "";
	String endDate     = "";
	//String xml         = "";
	String reSearchKey = "";
	String Sort        = "";
	String sgroup      = "";
	String emphasisKey = "";
	String sort        = "";
	String sort_order  = "";

	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	String toDay = date.format(new Date());
	
	
	String selectType = pr.getString("selectType", "1"); //1:제목+내용, 2:제목
	
	String SearchKey     = (pr.getString("txtSearch","")).trim(); //기본
	String txtSubSearch1 = (pr.getString("txtSubSearch1", "")).trim();  // 기본
	String txtSubSearch2 = (pr.getString("txtSubSearch2", "")).trim();  // OR
	String txtSubSearch3 = (pr.getString("txtSubSearch3", "")).trim();  // 구문
	String txtSubSearch4 = (pr.getString("txtSubSearch4", "")).trim();  // 인접	
	String type ="1";
	String txtSubSearch5 = (pr.getString("txtSubSearch5", "")).trim();  // 제외		
	String searchDate = pr.getString("searchDate", toDay);
	
	String[] groups = null;	
	String chkgroups_init = "1 OR 2 OR 3 OR 4 OR 5 OR 6";
	String chkgroups = pr.getString("chkgroups");
	String s_code = "N OR B OR C OR K OR T";
	 
	int nowpage = pr.getInt("nowpage", 1); 
	int grfCheck = pr.getInt("grfCheck", 1); //그래프 화면표시 유무
	int listCheck = pr.getInt("listCheck", 1); //설명 화면표시 유무
	int detailSearch = pr.getInt("detailSearch", 0); // 상세검색 표시 유무
	int searchType = pr.getInt("searchType", 1); //1:최신순, 2:정확도순, 3:유사순
	
	String listCnt = uei.getSt_list_cnt();
	int pLength = Integer.parseInt(listCnt);
	
	endDate   = du.getDate(toDay, "yyyy-MM-dd");
	startDate = du.addDay(toDay, -20, "yyyy-MM-dd");
	
	
	ArrayList arlist = null;
	SolrSearch solr = new SolrSearch();
	
	

/**키워드 상세 조합(호출 할 키워드)**/
	
	//일반
	if(!SearchKey.equals("")){
		reSearchKey += SearchKey.replaceAll(" "," AND "); 	
	}
	
	//다음 단어 모두 포함
	if(!txtSubSearch1.equals("")){ 
		if(reSearchKey.equals("")){
			reSearchKey += txtSubSearch1.replaceAll(" "," AND "); 	
		}else{
			reSearchKey += " AND " + txtSubSearch1.replaceAll(" "," AND ");
		}
	}
	
	//다음 문구 정확하게 포함   
	if(!txtSubSearch2.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey +=  "(\""+ txtSubSearch2 +"\")" ; 
		}else{
			reSearchKey += " AND "+ "(\"" + txtSubSearch2 + "\")"  ;
		}
	}


	
	//다음 단어 적어도 하나 포함 
	if(!txtSubSearch3.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "(" + txtSubSearch3.replaceAll(" "," OR ") + ")"; 	
		}else{
			reSearchKey += " AND " + "(" + txtSubSearch3.replaceAll(" "," OR ") + ")";
		}
	}
	
	//인접 하여 검색
	if(!txtSubSearch4.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "\""+ txtSubSearch4 +"\"~10";  	
		}else{
			reSearchKey += " AND " + "\""+ txtSubSearch4 +"\"~10"; 
		}		
	}
	
	
	//다음 단어 제외 
	if(!txtSubSearch5.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "NOT(" + txtSubSearch5.replaceAll(" "," OR ") + ")"; 	
		}else{
			reSearchKey = "(" + reSearchKey + ")";
			reSearchKey += "NOT(" + txtSubSearch5.replaceAll(" "," OR ") + ")";
		}
	}
	
	
	//제목인지 제목+내용인지 판별
	if(selectType.equals("2")){
		reSearchKey = "title:" + reSearchKey;
	}

	
	//화면에 보이는 키워드 강조
	if(SearchKey.trim().length() == 0){
		if(	txtSubSearch1.trim().length() > 0){ //상세 일반
			//emphasisKey = txtSubSearch1.split(" ")[0];
			emphasisKey = txtSubSearch1;
		} else if ( txtSubSearch2.trim().length() > 0){ //상세 정확
			emphasisKey = "\""+ txtSubSearch2 + "\"";
		} else if( txtSubSearch3.trim().length() > 0){ //상세 OR
			emphasisKey = txtSubSearch3;
		} else if( txtSubSearch4.trim().length() > 0){ //인접
			emphasisKey = txtSubSearch4;
		}
	}
	else{
		emphasisKey = SearchKey;
	}
	

	
	
	if(searchType == 1){ //최신순
		sort = "docid";
		sort_order = "desc";
	}else if(searchType == 2){ //옛글순
		sort = "docid";
		sort_order = "asc";
	}else if(searchType == 3){ //정확도순
		sort = "score";
		sort_order = "desc";
	}	
	
	groups = chkgroups.split("OR");
	
	if(!chkgroups.equals("")){	
		s_code = "";
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("1")){
				s_code  += s_code.equals("") ?  "N" :" OR N";	
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("2")){
				s_code  += s_code.equals("") ?  "B" : " OR B";				
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("3")){
				s_code  += s_code.equals("") ?  "C" : " OR C";				
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("4")){
				s_code  += s_code.equals("") ?  "K" : " OR K";		
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("5")){
				s_code  += s_code.equals("") ?  "T" : " OR T";		
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("6")){
				s_code  += s_code.equals("") ?  "E" : " OR E";		
			}
		}
	}
	
	sgroup="s_code=("+s_code+")";
	
	//SOLA데이터 가져오기
	arlist = solr.getDataList(reSearchKey, nowpage, pLength, searchDate.replaceAll("-",""),sgroup, sort, sort_order, SS_M_ID);
	System.out.println("#########################################");
	System.out.println("##리스트데이터 가져오기 완료##");
	System.out.println("#########################################");
	//리스트의 사이즈가 0보가 작거나 null이면 error;
	if(arlist != null && arlist.size() > 0){
	
	int iTotalPage = solr.hitCnt / pLength + (solr.hitCnt % pLength > 0 ? 1 : 0);
	
	String strLength = "(총 "+ su.digitFormat(solr.hitCnt) + " 건)";
	
	//이슈데이터 등록 관련
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	SolrMgr smgr = new SolrMgr(); 
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
   	ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y");
   	
   	//ArrayList<String> arrIssueSolr = smgr.getIssueSolr(searchDate);
   	
   	ArrayList arrIssueSolr = smgr.getIssueSolr(searchDate);
   	
   	uei.setSt_menu( pr.getString("layerType",uei.getSt_menu()) );
   	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("<script language=\"JavaScript\" src=\"chart/js/FusionCharts.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("$(function(){\r\n");
      out.write("\t$(\"[name=chkgroup]\").prop(\"checked\", true);\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("function lineOver(docid) {\r\n");
      out.write("\t\t\r\n");
      out.write("\tvar obj1 = document.getElementById('divDocList'+docid);\r\n");
      out.write("\tobj1.className='line_change02';\r\n");
      out.write("\t//var obj2 = document.getElementById('divIssueChk'+docid);\r\n");
      out.write("\t//obj2.style.display= '';\r\n");
      out.write("\t//var obj3 = document.getElementById('divIssueChktd'+docid);\r\n");
      out.write("\t//obj3.style.display= '';\r\n");
      out.write("}\r\n");
      out.write("function lineOut(docid){\r\n");
      out.write("\tvar obj1 = document.getElementById(\"divDocList\"+docid);\r\n");
      out.write("\tobj1.className='line_change01';\r\n");
      out.write("\t//var obj2 = document.getElementById('divIssueChk'+docid);\r\n");
      out.write("\t//obj2.style.display= 'none';\r\n");
      out.write("\t//var obj3 = document.getElementById('divIssueChktd'+docid);\r\n");
      out.write("\t//obj3.style.display= 'none';\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t//var obj = document.getElementById(objName); \r\n");
      out.write("\t//obj.className='line_change01';\t\t\t\t\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function search(){\t\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\r\n");
      out.write("\tif(f.detailSearch.value == 1){\t\r\n");
      out.write("\t}\r\n");
      out.write("\tvar siteGroup = '';\r\n");
      out.write("\t//var o =document.all.chkgroup;\r\n");
      out.write("\t$(\"[name=chkgroup]\").each(function(){\r\n");
      out.write("\t\tif ( $(this).is(\":checked\") ){\r\n");
      out.write("\t\t\tif(siteGroup == ''){\r\n");
      out.write("\t\t\t\tsiteGroup = $(this).val();\t\t\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tsiteGroup = siteGroup + ' OR ' + $(this).val();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t \r\n");
      out.write("\t//var length = 0;\r\n");
      out.write("\t//if(o != null && o.length > 0){\r\n");
      out.write("\t//\tfor(i=0; i<o.length; i++) {\r\n");
      out.write("\t//\t\tif(o(i).checked == true)\r\n");
      out.write("\t//\t\t{\r\n");
      out.write("\t//\t\t\tlength = length + 1;\r\n");
      out.write("\t//\t\t\tif(siteGroup == ''){\r\n");
      out.write("\t//\t\t\t\tsiteGroup = o(i).value;\t\t\r\n");
      out.write("\t//\t\t\t}else{\r\n");
      out.write("\t//\t\t\t\tsiteGroup = siteGroup + ' OR ' + o(i).value;\r\n");
      out.write("\t//\t\t\t}\t\t\t\t\t\r\n");
      out.write("\t//\t\t}\r\n");
      out.write("\t//\t}\r\n");
      out.write("\t//}\r\n");
      out.write("\t\r\n");
      out.write("\tif( siteGroup == ''){\r\n");
      out.write("\t\talert('검색대상은 최소1개 이상 입니다.'); \r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tf.chkgroups.value = siteGroup;\r\n");
      out.write("\t\r\n");
      out.write("\tf.nowpage.value = 1;\r\n");
      out.write("\tf.searchDate.value = \"\";\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function pageClick( paramUrl ) {\r\n");
      out.write("\tvar f = document.fSolr; \r\n");
      out.write("    f.action = \"search_main_contents_solr.jsp\" + paramUrl;\r\n");
      out.write("    f.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function responseXml()\r\n");
      out.write("{\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tvar ChartXml = f.xml.value;\r\n");
      out.write("\t\r\n");
      out.write("\tif(ChartXml != \"\"){\r\n");
      out.write("\t\t\r\n");
      out.write("\tvar chart = new FusionCharts(\"chart/charts/Column2D.swf\", \"ChartId\", \"750\", \"230\");\r\n");
      out.write("\t\tchart.setDataXML(ChartXml);\r\n");
      out.write("\t\tchart.render(\"chartdiv\");\r\n");
      out.write("\t\t \r\n");
      out.write("\t}else{\t\r\n");
      out.write("\t\tvar chart2 = document.getElementById(\"chartdiv\");//첫로딩이미지\r\n");
      out.write("\t\tchart2.innerHTML = \"<img id=\\\"loadingBar\\\" src=\\\"images/search/no_data.gif\\\" width=\\\"115\\\" height=\\\"92\\\">\";\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function searchDataList(date) {\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tf.searchDate.value = date;\r\n");
      out.write("    f.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function chartView(check){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tvar Obj = document.getElementById(\"listChart\");\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tif(check){\r\n");
      out.write("\t\tObj.style.position = '';\r\n");
      out.write("\t\tObj.style.display = '';\r\n");
      out.write("\t\tf.grfCheck.value = 1;\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tObj.style.position = 'absolute';\r\n");
      out.write("\t\tObj.style.display = 'none';\r\n");
      out.write("\t\tf.grfCheck.value = 0;\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}  \r\n");
      out.write("\r\n");
      out.write("function htmlView(check){\r\n");
      out.write("\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tfor(var i = 0; i < f.lineCount.value; i++)\r\n");
      out.write("\t{\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar obj = document.getElementById(\"htmlList\" + i);\r\n");
      out.write("\r\n");
      out.write("\t\tif(check){\r\n");
      out.write("\t\t\tobj.style.display = '';\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tobj.style.display = 'none';\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tif(check){\r\n");
      out.write("\t\tf.listCheck.value = 1;\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tf.listCheck.value = 0;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function listNum(value){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("function searchReSet(){\t\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tf.nowpage.value = 1;\r\n");
      out.write("\tf.searchDate.value = \"\";\r\n");
      out.write("\tf.grfCheck.value = 1;\r\n");
      out.write("\tf.listCheck.value = 1;\r\n");
      out.write("\tf.lineCount.value = \"\";\r\n");
      out.write("\tf.searchType.value = 1;\r\n");
      out.write("\tf.detailSearch.value = 0;\r\n");
      out.write("\tf.chkgroups.value = \"\";\r\n");
      out.write("\r\n");
      out.write("\tf.txtSearch.value = \"\";\r\n");
      out.write("\tf.txtSubSearch1.value = \"\";\r\n");
      out.write("\tf.txtSubSearch2.value = \"\";\r\n");
      out.write("\tf.txtSubSearch3.value = \"\";\r\n");
      out.write("\tf.txtSubSearch4.value = \"\";\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("function changeOrder(index){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tf.searchType.value = index;\t\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function trim(str) {\r\n");
      out.write("    return str.replace(/^\\s+|\\s+$/g, \"\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function DetailSearch(){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\r\n");
      out.write("\tvar detail1 = document.getElementById(\"detail1\");\r\n");
      out.write("    var detail2 = document.getElementById(\"detail2\");\r\n");
      out.write("    var detail3 = document.getElementById(\"detail3\");\r\n");
      out.write("    var detail4 = document.getElementById(\"detail4\");\r\n");
      out.write("\r\n");
      out.write("\tf.txtSubSearch1.value = '';\r\n");
      out.write("\tf.txtSubSearch2.value = '';\r\n");
      out.write("\tf.txtSubSearch3.value = '';\r\n");
      out.write("\tf.txtSubSearch4.value = '';\r\n");
      out.write("\tf.txtSubSearch5.value = '';\r\n");
      out.write("\r\n");
      out.write("\tvar o = null;\r\n");
      out.write("\to = document.all.chkgroup;\r\n");
      out.write("\r\n");
      out.write("\tf.chkgroups.value = f.chkgroups_init.value;\r\n");
      out.write("\tvar arr =  f.chkgroups.value.split(\"OR\");\r\n");
      out.write("\t\r\n");
      out.write("\tif(o != null && o.length > 0){\r\n");
      out.write("\t\tfor(var i=0; i<o.length; i++) {\r\n");
      out.write("\t\t\to(i).checked = false;\r\n");
      out.write("\t\t\tfor(var j=0; j<arr.length; j++){\r\n");
      out.write("\t\t\t\tif(o(i).value == trim(arr[j])){\r\n");
      out.write("\t\t\t\t\to(i).checked = true;\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\tif( f.detailSearch.value == 1){\r\n");
      out.write("\t\tf.detailSearch.value = 0;\r\n");
      out.write("\t\tf.txtSearch.readOnly = '';\r\n");
      out.write("\t\tdetail1.style.display = 'none';\r\n");
      out.write("\t\tdetail2.style.display = 'none';\r\n");
      out.write("\t\tdetail3.style.display = 'none';\r\n");
      out.write("\t\tdetail4.style.display = 'none';\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tf.detailSearch.value = 1;\r\n");
      out.write("\t\tf.txtSearch.value = '';\r\n");
      out.write("\t\tf.txtSearch.readOnly = 'true';\r\n");
      out.write("\t\tdetail1.style.display = 'block';\r\n");
      out.write("\t\tdetail2.style.display = 'block';\r\n");
      out.write("\t\tdetail3.style.display = 'block';\r\n");
      out.write("\t\tdetail4.style.display = 'block';\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getGraph(){\r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\tf.target='listChart';\r\n");
      out.write("\tf.action='inc_list_chart.jsp';\r\n");
      out.write("\tf.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//******************* 이슈 등록 관련********************************\r\n");
      out.write("function show_menu(md_seq, cnt){\r\n");
      out.write("\t\r\n");
      out.write("\tvar obj = document.getElementById(\"issueChkImg\"+md_seq); \r\n");
      out.write("\tvar f = document.fSolr;\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tif( document.all.floater.style.display == 'none' ) {\t\t\r\n");
      out.write("\t\tobjdata = obj.getBoundingClientRect();\r\n");
      out.write("\t\tdocument.all.floater.style.top = objdata.top+document.body.scrollTop+20;\r\n");
      out.write("\t\tdocument.all.floater.style.left = objdata.left+document.body.scrollLeft;\r\n");
      out.write("\t\tdocument.send_mb.md_seq.value = md_seq;\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.send_mb.md_title.value = f.rowTitle[cnt].value;\r\n");
      out.write("\t\tdocument.send_mb.md_url.value = f.rowUrl[cnt].value;\r\n");
      out.write("\t\tdocument.send_mb.s_seq.value = f.rowGsn[cnt].value;\r\n");
      out.write("\t\tdocument.send_mb.md_site_name.value = f.rowGsnName[cnt].value;\r\n");
      out.write("\t\tdocument.send_mb.md_date.value = f.rowDate[cnt].value;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tdocument.all.floater.style.display = '';\r\n");
      out.write("\t\t\r\n");
      out.write("\t}else {\r\n");
      out.write("\t\tdocument.all.floater.style.display = 'none';\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//타입코드 셋팅\r\n");
      out.write("function settingTypeCode()\r\n");
      out.write("{\r\n");
      out.write("\tvar form = document.send_mb;\r\n");
      out.write("\tvar result = false;\r\n");
      out.write("\r\n");
      out.write("\tform.typeCode.value = '';\r\n");
      out.write("\t\r\n");
      out.write("\tfor(var i=0;i<form.typeCode1.length;i++){\r\n");
      out.write("\t\tif(form.typeCode1[i].checked)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode1[i].value : '@'+form.typeCode1[i].value ;\t\t\t\t\r\n");
      out.write("\t\t\tresult = true;\r\n");
      out.write("\t\t}\t\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\tif(!result)\t{alert('중요를 선택해주세요.'); return result;}\t\r\n");
      out.write("\tresult = false;\t\r\n");
      out.write("\tfor(var i=0;i<form.typeCode4.length;i++){\r\n");
      out.write("\t\tif(form.typeCode4[i].checked)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;\r\n");
      out.write("\t\t\tresult = true;\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t}\r\n");
      out.write("\tif(!result)\t{alert('성향을 선택해주세요.'); return result;}\t\r\n");
      out.write("\tresult = false;\t\r\n");
      out.write("\tfor(var i=0;i<form.typeCode5.length;i++){\r\n");
      out.write("\t\tif(form.typeCode5[i].checked)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;\r\n");
      out.write("\t\t\tresult = true;\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t}\r\n");
      out.write("\tif(!result)\t{alert('구분을 선택해주세요.'); return result;}\t\r\n");
      out.write("\tresult = false;\t\t\r\n");
      out.write("\tfor(var i=0;i<form.typeCode6.length;i++){\r\n");
      out.write("\t\tif(form.typeCode6[i].checked)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tform.typeCode.value += form.typeCode.value=='' ? form.typeCode6[i].value : '@'+form.typeCode6[i].value ;\r\n");
      out.write("\t\t\tresult = true;\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t}\r\n");
      out.write("\tif(!result)\t{alert('구분2를 선택해주세요.'); return result;}\r\n");
      out.write("\r\n");
      out.write("\treturn true;\t\r\n");
      out.write("}\t\t\r\n");
      out.write("\r\n");
      out.write("//이슈 타이틀 변경\r\n");
      out.write("function changeIssueTitle()\r\n");
      out.write("{    \r\n");
      out.write("\tajax.post('../selectbox_issue_title.jsp','send_mb','td_it_title');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//이슈 데이터 즉시등록\r\n");
      out.write("function saveIssueData()\r\n");
      out.write("{\r\n");
      out.write("\tvar form = document.send_mb;\r\n");
      out.write("\tif(!settingTypeCode()) return;\r\n");
      out.write("\tif(form.i_seq[form.i_seq.selectedIndex].value=='0') {alert('이슈를 선택해주세요.'); return;}\r\n");
      out.write("\tif(form.it_seq[form.it_seq.selectedIndex].value=='0') {alert('주제를 선택해주세요.'); return;}\t\t\r\n");
      out.write("\tform.mode.value='solrsave';\r\n");
      out.write("\tform.method = 'post';\t\t\t\t\t  \r\n");
      out.write("\tform.target='proceeFrame';\r\n");
      out.write("\tform.action='../issue_data_prc.jsp';\r\n");
      out.write("\tform.submit();\r\n");
      out.write("\tclose_menu();\t\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function close_menu()\r\n");
      out.write("{\r\n");
      out.write("\tdocument.all.floater.style.display = 'none';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function changeIssueIcon(docid){\r\n");
      out.write("\tvar obj1 = document.getElementById(\"issueChkImg\"+docid);\r\n");
      out.write("\tobj1.src='images/search/yellow_star.gif';\r\n");
      out.write("\tobj1.title='이슈로 등록된 정보 입니다.';\r\n");
      out.write("    obj1.onclick='';\r\n");
      out.write("\tvar obj2 = document.getElementById(\"issueChkText\"+docid);\r\n");
      out.write("\tobj2.href = \"javascript:\";\r\n");
      out.write("}\r\n");
      out.write("function send_issue(mode, subMode)\r\n");
      out.write("{\r\n");
      out.write("\tclose_menu();\r\n");
      out.write("\tdocument.send_mb.mode.value = mode;\r\n");
      out.write("\tdocument.send_mb.subMode.value = subMode;\r\n");
      out.write("\tif(mode=='insert'){\t\t\r\n");
      out.write("    \tpopup.openByPost('send_mb','../pop_issue_data_form.jsp',680,945,false,false,false,'send_issue');\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tdocument.send_mb.md_seq.value = md_seq;\r\n");
      out.write("\t\tpopup.openByPost('send_mb','../pop_issue_data_form.jsp',680,945,false,false,false,'send_issue');\r\n");
      out.write("\t}\t\r\n");
      out.write("}\r\n");
      out.write("//****************************************************************\r\n");
      out.write("\t\r\n");
      out.write("function chkSiteAll(chk){\r\n");
      out.write("\t\r\n");
      out.write("\tif ( $(\"[name=chkgroupAll]\").is(\":checked\") ) {\r\n");
      out.write("\t\t$(\"[name=chkgroup]\").prop(\"checked\", true);\t\r\n");
      out.write("\t}else {\r\n");
      out.write("\t\t$(\"[name=chkgroup]\").prop(\"checked\", false);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write(" \t//var group = document.all.chkgroup;\r\n");
      out.write("\t//if(group){\r\n");
      out.write("\t//\tif(group.length){\r\n");
      out.write("\t//\t\tfor(var i=0; i<group.length; i++){\r\n");
      out.write("\t//\t\t\tgroup(i).checked = chk;\r\n");
      out.write("\t//\t\t}  \t\r\n");
      out.write("\t//\t} else {\r\n");
      out.write("\t//\t\tgroup.checked = chk;\r\n");
      out.write("\t//\t}\r\n");
      out.write("\t//}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function actionLayer(obj){\r\n");
      out.write("\tif(obj.src.indexOf(\"close\") > -1){\r\n");
      out.write("\t\tfor(var i = 1; i < 5; i++){\r\n");
      out.write("\t\t\tdocument.getElementById('hide'+i).style.display = 'none';\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tobj.src = '../../../images/search/btn_searchbox_open.gif';\r\n");
      out.write("\t\tdocument.getElementById('fSolr').layerType.value = \"0\";\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tfor(var i = 1; i < 5; i++){\r\n");
      out.write("\t\t\tdocument.getElementById('hide'+i).style.display = '';\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tobj.src = '../../../images/search/btn_searchbox_close.gif';\r\n");
      out.write("\t\tdocument.getElementById('fSolr').layerType.value = \"1\";\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSolr\" action =\"search_main_contents_solr.jsp\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value =\"1\">\r\n");
      out.write("<input type=\"hidden\" name=\"searchDate\" value=\"");
      out.print(searchDate);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"grfCheck\" value=\"");
      out.print(grfCheck);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"listCheck\" value=\"");
      out.print(listCheck);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"lineCount\" value=\"");
      out.print(arlist.size());
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"searchType\" value=\"");
      out.print(searchType);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"layerType\" value=\"");
      out.print(uei.getSt_menu());
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"detailSearch\" value=\"");
      out.print(detailSearch);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"chkgroups\" value=\"");
      out.print(chkgroups);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"chkgroups_init\" value=\"");
      out.print(chkgroups_init);
      out.write("\">\r\n");
      out.write("<table style=\"width:850px; height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/search/tit_icon.gif\" /><img src=\"../../../images/search/tit_0101.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">정보검색</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">전체검색</td>\r\n");
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
      out.write("\t\t\t<!-- 검색 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"search_box\">\r\n");
      out.write("\t\t\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<select name=\"selectType\" id=\"mySelect\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"1\" ");
if(selectType.equals("1")){ out.print("selected"); }
      out.write(">제목 + 내용에서 검색</option>\r\n");
      out.write("        \t\t\t\t\t\t\t\t\t\t<option value=\"2\" ");
if(selectType.equals("2")){ out.print("selected"); }
      out.write(">제목에서만 검색</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</select> <input style=\"width:460px;\" name=\"txtSearch\" class=\"textbox\" type=\"text\" ");
if(detailSearch==1){out.print("readonly");}
      out.write(" onkeypress=\"if(event.keyCode==13){return search();}\" value=\"");
      out.print(SearchKey);
      out.write("\"><img src=\"../../../images/search/btn_search.gif\" onclick=\"search();\" style=\"cursor:pointer\" align=\"absmiddle\" /><img src=\"../../../images/search/btn_reset.gif\" onclick=\"searchReSet();\" style=\"cursor:pointer;\" align=\"absmiddle\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"hide1\" ");
if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}
      out.write("><td style=\"height:15px;background:url('../../../images/search/searchbox_dot_width.gif') 50% repeat-x;\"></td></tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"hide2\" ");
if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>검색어 설정</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>다음 단어 <b>모두</b> 포함</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input style=\"width:350px;\" name=\"txtSubSearch1\" onkeypress=\"if(event.keyCode==13){return search();}\" class=\"textbox\" type=\"text\" value=\"");
      out.print(txtSubSearch1);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>다음 <b>문구 정확하게</b> 포함</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input style=\"width:350px;\" name=\"txtSubSearch2\" onkeypress=\"if(event.keyCode==13){return search();}\" class=\"textbox\" type=\"text\" value=\"");
      out.print(txtSubSearch2);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>다음 단어 <b>적어도 하나</b> 포함 &nbsp; </td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input style=\"width:350px;\" name=\"txtSubSearch3\" onkeypress=\"if(event.keyCode==13){return search();}\" class=\"textbox\" type=\"text\" value=\"");
      out.print(txtSubSearch3);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>다음 단어 <b>인접하여</b></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input style=\"width:350px;\" name=\"txtSubSearch4\" onkeypress=\"if(event.keyCode==13){return search();}\" class=\"textbox\" type=\"text\" value=\"");
      out.print(txtSubSearch4);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>다음 단어 <b>제외</b></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input style=\"width:350px;\" name=\"txtSubSearch5\" onkeypress=\"if(event.keyCode==13){return search();}\" class=\"textbox\" type=\"text\" value=\"");
      out.print(txtSubSearch5);
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"hide3\" ");
if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}
      out.write("><td style=\"height:10px;\"></td></tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"hide4\" ");
if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<table id=\"search_box\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th><a href=\"#\">검 색 대 상</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:30px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<table style=\"color:#2f5065;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroupAll\" value=\"0\" onclick=\"chkSiteAll(this.checked);\">전체</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"1\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("1")){out.print("checked");}}
      out.write(">언론사</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"2\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("2")){out.print("checked");}}
      out.write(">블로그/카페</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"3\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("3")){out.print("checked");}}
      out.write(">커뮤니티</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"4\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("4")){out.print("checked");}}
      out.write(">공공기관</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"5\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("5")){out.print("checked");}}
      out.write(">SNS</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-right:10px;\"><input type=\"checkbox\" name=\"chkgroup\" value=\"6\" ");
for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("6")){out.print("checked");}}
      out.write(">기타</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align:center;\"><img src=\"");
if(uei.getSt_menu().equals("0")){out.print("../../../images/search/btn_searchbox_open.gif");}else{out.print("../../../images/search/btn_searchbox_close.gif");}
      out.write("\" style=\"cursor:pointer\" onclick=\"actionLayer(this)\"/></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"vertical-align:middle\"><img src=\"../../../images/search/table_title_001.gif\" width=\"86\" height=\"13\" style=\"vertical-align:middle\"> (");
      out.print(startDate);
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(endDate);
      out.write(")</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"left\">\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"divLoading\" style=\"display:none; width:196px; height:14px;  background:url(images/search/load_01.gif) no-repeat;\"></div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"*\" align=\"right\"><table width=\"140\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td height=\"15\" align=\"center\" background=\"images/search/bg_011.gif\"><input name=\"chartchk\" type=\"checkbox\" onclick=\"chartView(this.checked)\" ");
if(grfCheck == 1){ out.print("checked");}
      out.write(" ><span class=\"menu_gray_s\"><strong>Trend Chart 보기</strong></span></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"3\">\r\n");
      out.write("\t\t\t\t\t\t\t<iframe id=\"listChart\" name=\"listChart\" style=\"display:");
if(grfCheck == 0){out.print("none");}
      out.write("\" src=\"inc_list_chart.jsp?reSearchKey=");
      out.print(solr.changeEncode(reSearchKey,"utf-8"));
      out.write("&startDate=");
      out.print(startDate.replaceAll("-",""));
      out.write("&endDate=");
      out.print(endDate.replaceAll("-","") );
      out.write("&sgroup=");
      out.print(sgroup);
      out.write("&searchDate=");
      out.print(searchDate.replaceAll("-",""));
      out.write("\" width=\"800\" height=\"260\" frameborder=\"0\" scrolling=\"no\"></iframe>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:45px;background:url('../../../images/search/list_top_line.gif') bottom repeat-x;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"sort\" style=\"cursor:pointer\" onclick=\"changeOrder(1);\">");
if(searchType == 1){out.print("<span class=\"sort_on\">최신순</span>");}else{out.print("최신순");}
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"sort\" style=\"cursor:pointer\" onclick=\"changeOrder(2);\">");
if(searchType == 2){out.print("<span class=\"sort_on\">옛글순</span>");}else{out.print("옛글순");}
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"sort\" style=\"cursor:pointer\" onclick=\"changeOrder(3);\">");
if(searchType == 3){out.print("<span class=\"sort_on\">정확도순</span>");}else{out.print("정확도순");}
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align:right;\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"sort\"><input type=\"checkbox\" name=\"listchk\" onclick=\"htmlView(this.checked);\" ");
if(listCheck == 1){ out.print("checked"); }
      out.write(">주요내용보기</span>");
      out.print(strLength);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\r\n");

if(arlist.size() > 0){
	for(int i = 0; i < arlist.size(); i++){
		SearchForm form = (SearchForm)arlist.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"rowTitle\" name=\"rowTitle\" value=\"");
      out.print(form.getTitle());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"rowUrl\" name=\"rowUrl\" value=\"");
      out.print(form.getUrl());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"rowGsn\" name=\"rowGsn\" value=\"");
      out.print(form.getGsn());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"rowGsnName\" name=\"rowGsnName\" value=\"");
      out.print(form.getName());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"rowDate\" name=\"rowDate\" value=\"");
      out.print(form.getSdate()+form.getStime());
      out.write("\">\r\n");

		if(i == 0){

      out.write("\r\n");
      out.write("\t\t\t\t\t<table id=\"board_02\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");

		}
		if(!form.getImg_name().equals("NON") && !form.getImg_name().equals("Y") && form.getGsn() != 10464){

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th rowspan=\"2\"><a href=\"");
      out.print(form.getUrl());
      out.write("\" target=\"_blank\"><img src=\"http://image.realsn.co.kr/");
      out.print(form.getSdate());
      out.write('/');
      out.print(form.getImg_name());
      out.write("\"></a></th>\r\n");
      out.write("\t\t\t\t\t\t<th ");
if(i == 0){out.print("class=\"top\"");}
      out.write("><a href=\"");
      out.print(form.getUrl());
      out.write("\" target=\"_blank\"><strong class=\"menu_blue_b\">");
      out.print(su.subStrTitle(form.getTitle(), 30));
      out.write("&nbsp;&nbsp;</strong></a><span class=\"source\">");
      out.print(form.getName().length() > 8 ?  form.getName().substring(0,6) + "..." : form.getName());
      out.write("</span><span class=\"date\">");
      out.print(du.getDate(form.getSdate(), "MM-dd"));
      out.write(' ');
      out.print(DateUtil.addHour(form.getStime(),9,"HHmmss","HH:mm"));
      out.write("</span></th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td ");
if(i == arlist.size()-1){out.print("class=\"end\"");}
      out.write("><div id=\"htmlList");
      out.print(i);
      out.write("\" style=\"display: ");
if(listCheck != 1){ out.print("none"); } 
      out.write('"');
      out.write('>');
      out.print(su.cutKey(su.toHtmlString(form.getHtml()), emphasisKey, 120, "0000CC"));
      out.write("</div></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		}else{

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th colspan=\"2\" ");
if(i == 0){out.print("class=\"top\"");}
      out.write("><a href=\"");
      out.print(form.getUrl());
      out.write("\" target=\"_blank\"><strong class=\"menu_blue_b\">");
      out.print(su.subStrTitle(form.getTitle(), 40));
      out.write("&nbsp;&nbsp;</strong></a><span class=\"source\">");
      out.print(form.getName().length() > 8 ?  form.getName().substring(0,6) + "..." : form.getName());
      out.write("</span><span class=\"date\">");
      out.print(du.getDate(form.getSdate(), "MM-dd"));
      out.write(' ');
      out.print(DateUtil.addHour(form.getStime(),9,"HHmmss","HH:mm"));
      out.write("</span></th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"2\" ");
if(i == arlist.size()-1){out.print("class=\"end\"");}
      out.write("><div id=\"htmlList");
      out.print(i);
      out.write("\" style=\"display: ");
if(listCheck != 1){ out.print("none"); } 
      out.write('"');
      out.write('>');
      out.print(su.cutKey(su.toHtmlString(form.getHtml()), emphasisKey, 130, "0000CC"));
      out.write("</div></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		}
	}
}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<!-- 페이징 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"width:820px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;text-align:center;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(nowpage,iTotalPage,"","" ));
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 페이징 -->\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("<iframe id=\"proceeFrame\" name=\"proceeFrame\" width=\"0\" height=\"0\"></iframe>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
}else{

	String errorCode = solr.getErrorCode();
	String errorMsg = solr.getErrorMsg();
	String errorAlertMsg = solr.getErrorAlertMsg(); 

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("\t<title></title>\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/base.css\" />\r\n");
      out.write("\t<script src=\"chart/js/FusionCharts.js\"></script>\r\n");
      out.write("\t<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\t<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\t<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\t<script type=\"text/JavaScript\">\r\n");
      out.write("    var code = \"");
      out.print(errorCode);
      out.write("\";\r\n");
      out.write("    var msg = \"");
      out.print(errorMsg);
      out.write("\";\r\n");
      out.write("    var alertMsg = \"");
      out.print(errorAlertMsg);
      out.write("\";\r\n");
      out.write("\t$(function(){\r\n");
      out.write("\t\talert(alertMsg);\r\n");
      out.write("\t\ttop.bottomFrame.leftFrame.document.location ='../../search/search_main_left.jsp';\r\n");
      out.write("\t\tlocation.href='../../search/search_main_contents.jsp';\r\n");
      out.write("\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("\t\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>\r\n");
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
