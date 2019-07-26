package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.search.MetaBean;
import risk.search.MetaMgr;
import risk.search.siteGroupInfo;
import risk.search.siteDataInfo;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import java.text.DecimalFormat;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.issue.IssueMgr;
import risk.issue.IssueBean;
import risk.issue.IssueTitleBean;
import risk.util.PageIndex;

public final class search_005fmain_005fcontents_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/search/../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	//페이지에 사용할 변수 선언 부분
	StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    DateUtil        du = new DateUtil();
    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    MetaMgr     smgr = new MetaMgr();
    pr.printParams();

    
try{    
    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");


    String sCurrDate    = du.getCurrentDate();
    String sInit = (String)session.getAttribute("INIT");


    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");

       /*  if ( Integer.parseInt(uei.getSt_interval_day()) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
        	du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        } */
        
        uei.setDateFrom( sCurrDate );
        uei.setDateTo  ( sCurrDate    ) ;
    }

	if ( request.getParameter("xp") != null ) uei.setK_xp( pr.getString("xp") );
      
    // 키워드그룹 일련번호
    if ( request.getParameter("yp") != null ) uei.setK_yp( pr.getString("yp") );

    // 키워드 일련번호
    if ( request.getParameter("zp") != null ) uei.setK_zp( pr.getString("zp") );

    if ( uei.getK_xp().equals("0") ) uei.setK_xp("");
    if ( uei.getK_yp().equals("0") ) uei.setK_yp("");
    if ( uei.getK_zp().equals("0") ) uei.setK_zp("");
  

    // 검색단어    
    if( pr.getString("sKeyword","").equals(""))
       uei.setKeyword("");
    else{
    	uei.setKeyword(su.Trim(request.getParameter("sKeyword")).replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&").replaceAll("\\&\\&|\\&[ ]{1,}|[ ]{1,}&\\&","&").replaceAll("\\&\\||\\|\\&","|").replaceAll("\\&\\-|\\-\\&","-"));
    }
    
    String sDataRange   = pr.getString("");         // 검색 범위 ( 전체,전체,기사,게시,공지 )
    
    if ( uei.getDateFrom()== null ) {

        if ( Integer.parseInt( uei.getSt_interval_day() ) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( du.getDate() ) ;
        } else {
        	du.addDay(-1);
            uei.setDateFrom( du.getDate()  ) ;
            uei.setDateTo  ( du.getDate()  ) ;
        }
        
    }
    
    uei.setStime("0");
    uei.setEtime("23");
    
    uei.setDateFrom( pr.getString("sDateFrom",uei.getDateFrom() ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,uei.getDateTo()   ) ) ;
    uei.setStime( pr.getString("stime"  ,uei.getStime() ) ) ;
    uei.setEtime( pr.getString("etime"  ,uei.getEtime() ) ) ;

    uei.setSt_interval_day( pr.getString("Period",uei.getSt_interval_day())) ;
    uei.setMd_type( pr.getString("type", uei.getMd_type() ) );
    uei.setSg_seq( pr.getString("sg_seq",uei.getSg_seq())  );
    uei.setS_seq(pr.getString("sd_gsn", uei.getS_seq()));
    
    //전체 선택시 빈문자열로 대체
    if ( uei.getSg_seq().equals("") ) uei.setSg_seq("");
    if ( uei.getS_seq().equals("0") ) uei.setS_seq("");


    String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
    
    uei.setOrder( pr.getString("sOrder","MD_DATE") );
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );
    
    uei.setSt_list_cnt( pr.getString("rowcnt",uei.getSt_list_cnt()) );   
    int iNowPage        = pr.getInt("nowpage",1);
    
    String bookMarkYn = pr.getString("bookMarkYn","N");

    uei.setSt_menu( pr.getString("searchtype",uei.getSt_menu()) );   // 상세 검색창 표출 여부

 	//데이터 검색 모드 ALLKEY, ALLDB
    uei.setSearchMode(  pr.getString("searchmode", su.nvl(uei.getSearchMode(),"ALLKEY") ) );
    
    if(uei.getSearchMode().equals("DELIDX")){   	    	
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));    	
    }else{
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));
    }

    //타이머 설정 변수
    String sTimer       = "Y";
    uei.setSt_reload_time(pr.getString("interval", uei.getSt_reload_time() ));


    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);


    //Uiix의 파일권한과 동일한 개념으로 간다.
    //기사 1. 게시 2. 공지 4.
    String sType1       = "";
    String sType2       = "";
    String sType3       = "";
    String sTypeAll     = "";
    if( uei.getMd_type().equals("1") ) {
        sType1  = "checked";
    } else if   ( uei.getMd_type().equals("2") ) {
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("4") ) {
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("3") ) {
        sType1  = "checked";
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("5") ) {
        sType1  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("6") ) {
        sType2  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("7") ) {
        sTypeAll= "checked";
        sType1  = "checked";
        sType2  = "checked";
        sType3  = "checked";
    }

    
    /**
    * 필드별 정렬
    */
   	String sOrderImg    = "";
    String sOrderMark1 = "";
    String sOrderMark2 = "";
    String sOrderMark3 = "";
    String sOrderMark4 = "";
    String sOrderMark5 = "";
    String sOrderMark6 = "";
    String sOrderMark7 = "";

    if ( uei.getOrderAlign().equals("ASC" ) ) {
        sOrderImg = "▲";
    } else if( uei.getOrderAlign().equals("DESC" ) ) {
        sOrderImg = "▼";
    }   
    
    if(uei.getOrder()!=null){
	    if ( uei.getOrder().equals("MD_SITE_NAME") )             {
	        sOrderMark1 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_TITLE") )     {
	        sOrderMark2 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_SAME_COUNT") )   {
	        sOrderMark3 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_REPLY_CT") )  {
	        sOrderMark4 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_DATE") )      {
	        sOrderMark5 = sOrderImg;
	    }else if ( uei.getOrder().equals("I_DELDATE") )      {
	        sOrderMark6 = sOrderImg;
	    }else if ( uei.getOrder().equals("M_NAME") )      {
	        sOrderMark7 = sOrderImg;
	    }
    }
 

    //HTML Colspan
    String colspan = "";
    
    //검색 사이트 검색
    ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );

    //그룹에 등록된 사이트 리스트
    ArrayList alSite = smgr.getSiteData(uei.getSg_seq());
    
    // 검색언어
	String language = pr.getString("language", uei.getLnaguage());
    if(language.equals("0")){
    	uei.setLnaguage("");	
    }else{
    	uei.setLnaguage(language);
    }
	
    //issue_check
    String issue_check = pr.getString("issue_check", "");
    String searchType = pr.getString("searchType", "1");
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
    
    
    ArrayList alData = null;
    
	if ( uei.getSearchMode().equals("ALLKEY") ) {
		colspan = "colspan=\"11\"";
		System.out.println("uei.getK_xp().length() "+uei.getK_xp().length());
		if(uei.getK_xp().length()<1){
		System.out.println("uei.getK_xp().length() "+uei.getK_xp().length());
				alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       uei.getMg_xp()                                ,   //String psXp      ,
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()           ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign(),      //String psOrder
		                                       "",
		                                       uei.getDelName(),
		                                       bookMarkYn,
		                                       uei.getLnaguage(),
		                                       uei.getM_seq(),
		                                       uei.getStime(),
		                                       uei.getEtime(),
		                                       "",
		                                       issue_check,
		                                       tiers,
		                                       searchType);
											   
		}else{
		        alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       uei.getK_xp()                                ,   //String psXp      ,
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()       ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign()    ,  //String psOrder
		                                       "",
		                                       uei.getDelName(),
		                                       bookMarkYn,
		                                       uei.getLnaguage(),
		                                       uei.getM_seq(),
		                                       uei.getStime(),
		                                       uei.getEtime(),
		                                       "",
		                                       issue_check,
		                                       tiers,
		                                       searchType);
		}
    }else if ( uei.getSearchMode().equals("ALLDB") ) {
    	colspan = "colspan=\"9\"";
    	alData = smgr.getAllSearchList(
                                       iNowPage                                     ,   //int    piNowpage ,
                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
                                       uei.getK_xp()                                ,   //String psXp      ,
                                       uei.getK_yp()                                ,   //String psYp      ,
                                       uei.getK_zp()                                ,   //String psZp      ,
                                       uei.getSg_seq()                              ,   //String psSGseq   ,
                                       uei.getS_seq()                              ,   //String psSDgsn   ,
                                       uei.getDateFrom()       ,   //String psDateFrom,
                                       uei.getDateTo()         ,   //String psDateTo  ,
                                       uei.getKeyword()                             ,   //String psKeyWord ,
                                       uei.getMd_type()                              ,   //String psType
                                       uei.getOrder() + " " + uei.getOrderAlign()   ,   //String psOrder
                                       uei.getSearchMode()						
                                        );
        

    }else if(uei.getSearchMode().equals("DELIDX")){
    	colspan = "colspan=\"11\"";
    	
    	alData = smgr.getKeySearchList(
                iNowPage                                     ,   //int    piNowpage ,
                Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
                uei.getMg_xp()                               ,   //String psXp      ,
                uei.getK_yp()                                ,   //String psYp      ,
                uei.getK_zp()                                ,   //String psZp      ,
                uei.getSg_seq()                              ,   //String psSGseq   ,
                uei.getS_seq()                              ,   //String psSDgsn   ,
                uei.getDateFrom()         ,   //String psDateFrom,
                uei.getDateTo()           ,   //String psDateTo  ,
                uei.getKeyword()                             ,   //String psKeyWord ,
                uei.getMd_type()                              ,   //String psType
                uei.getOrder() + " " + uei.getOrderAlign(),      //String psOrder
                uei.getSearchMode(),
    			uei.getDelName(),
    			bookMarkYn,
    			uei.getLnaguage(),
    			uei.getM_seq(),
                uei.getStime(),
                uei.getEtime(),
                "");
    	
    }
	
	
	//북마크일 시 현재 페이지 설정
	String bookMarkNum  = "";
	if(bookMarkYn.equals("Y"))
	{	
		iNowPage = smgr.getBookMarkPage();
		bookMarkNum = smgr.getBookMarkNum();		
	}
	
	int iPageCnt = smgr.mBeanPageCnt;
    int iDataCnt = smgr.mBeanDataCnt;
    int iTotalCnt= smgr.mBeanTotalCnt;

    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
    
    
   	//이슈데이터 등록 관련
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
   	ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y");
   	
   	
	String[] menu;
	int issue = 0;
	menu = uei.getMg_menu().split(",");
	for(int i=0; i < menu.length; i++){
		if(menu[i].equals("2")){
			issue = 1;
		}
	}
	
	DecimalFormat df = new DecimalFormat("###,###,###");
	//출처별 건수 구하기(유사포함)
		ArrayList arr_sourceCnt = smgr.getArrSourceCnt();
		String strSourceCnts = "";
		String[] arScnt = null;
		for(int i =0; i <arr_sourceCnt.size(); i++){
			arScnt = ((String)arr_sourceCnt.get(i)).split(",");
			if(i!=0 && (i+1)==(arr_sourceCnt.size())){
				strSourceCnts = "" +  arScnt[0] + ":<strong>"  + df.format(Integer.parseInt(arScnt[1])) + "</strong>건 / "+strSourceCnts;
			}else{
				if(strSourceCnts.equals("")){
					strSourceCnts =  arScnt[0] + ":<strong>" + df.format(Integer.parseInt(arScnt[1])) + "</strong>건"; 	
 				}else{
					strSourceCnts += " / " +  arScnt[0] + ":<strong>"  + df.format(Integer.parseInt(arScnt[1])) + "</strong>건";
				}
			}
		}

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>");
      out.print(SS_TITLE);
      out.write("</title>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<style>\r\n");
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
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/Calendar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/timer.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/input_date.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("\t<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"../../dashboard/asset/js/selectivizr-min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<script type=\"text/JavaScript\">\r\n");
      out.write("    var selecedOrder = \"");
      out.print(uei.getOrder());
      out.write("\";\r\n");
      out.write("    var click = \"\";\r\n");
      out.write("    var sCurrDate = '");
      out.print(sCurrDate);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 검색 SUBMIT\r\n");
      out.write("    */\r\n");
      out.write("    function doSubmit(){\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("\r\n");
      out.write("    \t//정보유형\r\n");
      out.write("        var typeValue   = 0;        \r\n");
      out.write("        if(f.chkGisa.checked == false && f.chkGesi.checked == false && f.chkGongji.checked == false && f.chkAll.checked == false){\r\n");
      out.write("\t\t\t//f.chkAll.checked = true;\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("        }\r\n");
      out.write("        if(f.chkGisa.checked == true)typeValue += 1;\r\n");
      out.write("        if(f.chkGesi.checked == true)typeValue += 2;\r\n");
      out.write("        if(f.chkGongji.checked == true)typeValue += 4;\r\n");
      out.write("        if(f.chkAll.checked == true)typeValue = 7;\r\n");
      out.write("        f.type.value = typeValue;\r\n");
      out.write("        \r\n");
      out.write("        //매체관리\r\n");
      out.write("        var tiers = \"\";\r\n");
      out.write("    \t$(\"[name=tier]\").each(function(){\r\n");
      out.write("    \t\tif($(this).is(\":checked\")){\r\n");
      out.write("    \t\t\tif(tiers == \"\"){tiers = $(this).val();\r\n");
      out.write("    \t\t\t}else{tiers += \",\"+$(this).val();}\r\n");
      out.write("    \t\t}\r\n");
      out.write("    \t});\r\n");
      out.write("    \tdocument.fSend.tiers.value = tiers;\r\n");
      out.write("    \t\r\n");
      out.write("    \t//이슈등록 여부\r\n");
      out.write("    \tvar check=\"\";\r\n");
      out.write("    \tif( $(\"#issue_check\").is(\":checked\") ){\r\n");
      out.write("    \t\tcheck= \"Y\";\r\n");
      out.write("    \t}\r\n");
      out.write("    \tif( $(\"#issue_check2\").is(\":checked\") ){\r\n");
      out.write("    \t\tif(check == \"\"){\r\n");
      out.write("    \t\t\tcheck = \"N\";\r\n");
      out.write("    \t\t}else{\r\n");
      out.write("    \t\t\tcheck += \"','\"+\"N\";\r\n");
      out.write("    \t\t}\r\n");
      out.write("    \t}\r\n");
      out.write("    \t$(\"[name=issue_check]\").val(check);\r\n");
      out.write("    \t\r\n");
      out.write("    \t//정렬\r\n");
      out.write("\t\tif((f.sOrder.value == selecedOrder) && (click == \"TRUE\")){\r\n");
      out.write("\t\t\tif(f.sOrderAlign.value == \"ASC\"){\r\n");
      out.write("\t\t\t\tf.sOrderAlign.value = \"DESC\";\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tf.sOrderAlign.value = \"ASC\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tf.sOrder.value = selecedOrder;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tf.sg_seq.value = chkSiteGroup();\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 정보유형\r\n");
      out.write("    */\r\n");
      out.write("    function checkAll() {\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("\r\n");
      out.write("        if(f.chkAll.checked == true){\r\n");
      out.write("\t\t\tf.chkGisa.checked = true;\r\n");
      out.write("\t\t\tf.chkGesi.checked = true;\r\n");
      out.write("\t\t\tf.chkGongji.checked = true;\r\n");
      out.write("        }else{\r\n");
      out.write("        \tf.chkGisa.checked = false;\r\n");
      out.write("\t\t\tf.chkGesi.checked = false;\r\n");
      out.write("\t\t\tf.chkGongji.checked = false;\r\n");
      out.write("        }\r\n");
      out.write("\t\t//doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("    function checkEtc() {\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("    \t\r\n");
      out.write("        if(f.chkGisa.checked == true && f.chkGesi.checked == true && f.chkGongji.checked == true){\r\n");
      out.write("            f.chkAll.checked = true;\r\n");
      out.write("            f.chkGisa.checked = true;\r\n");
      out.write("            f.chkGesi.checked = true;\r\n");
      out.write("            f.chkGongji.checked = true;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("            f.chkAll.checked = false;\r\n");
      out.write("        }\r\n");
      out.write("\t\t//doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 기사 삭제 관련\r\n");
      out.write("    */\r\n");
      out.write("    function idxProcess(mode) {\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("\t\tvar typeValue   = 0;\r\n");
      out.write("\r\n");
      out.write("        frm = document.getElementById('fCheck');\r\n");
      out.write("        var selectedList = \"\";\r\n");
      out.write("        \r\n");
      out.write("        var first = 0;\r\n");
      out.write("        \r\n");
      out.write("        if(mode == 'truncate'){\r\n");
      out.write("        \t if ( !confirm(\"선택된 목록을 삭제 하시겠습니까?\" ) ) {\r\n");
      out.write("\t\t            return;\r\n");
      out.write("\t\t     }        \r\n");
      out.write("        }else if(mode=='revert'){            \t\t        \r\n");
      out.write("\t\t\tif ( !confirm(\"선택된 목록을 복원 하시겠습니까?\" ) ) {\r\n");
      out.write("\t            return;\r\n");
      out.write("\t        }\t\t       \r\n");
      out.write("\t\t}else if(mode=='del'){\r\n");
      out.write("\t\t\t if ( !confirm(\"선택된 목록을 영구 삭제 하시겠습니까?\" ) ) {\r\n");
      out.write("\t\t            return;\r\n");
      out.write("\t\t     }\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("        if(frm.chkData){\r\n");
      out.write("\t\t\tif(frm.chkData.length>1){\t\t\t\t\t\r\n");
      out.write("\t\t\t \tfor(var i=0; i<frm.chkData.length; i++)\r\n");
      out.write("\t\t\t \t{\r\n");
      out.write("\t\t\t\t \tif(frm.chkData[i].checked){\r\n");
      out.write("\t\t\t \t\t\tselectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;\r\n");
      out.write("\t\t\t\t \t}\r\n");
      out.write("\t\t\t \t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tselectedList = frm.chkData.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("        \r\n");
      out.write("        f.SaveList.value = selectedList;\r\n");
      out.write("\r\n");
      out.write("        f.action = \"idx_prc.jsp?idxMode=\"+mode;\r\n");
      out.write("        f.target = 'if_samelist';\r\n");
      out.write("        f.submit();\r\n");
      out.write("\t\tf.action = 'search_main_contents.jsp';\r\n");
      out.write("\t\tf.target = '';\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 페이지처리\r\n");
      out.write("    */\r\n");
      out.write("    function pageClick(paramUrl){\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("        f.action = \"search_main_contents.jsp\" + paramUrl;\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 체크된 사이트 코드 세팅\r\n");
      out.write("    */\r\n");
      out.write("\tfunction chkSiteGroup(){\r\n");
      out.write("\t\tvar f = document.getElementById('fSend');\r\n");
      out.write("\t\tvar seqs = '';\r\n");
      out.write("\t\tvar obj = f.sltSiteGroup;\r\n");
      out.write("\t\tfor(var i =0; i<obj.length; i++){\r\n");
      out.write("\t\t\tif(obj[i].checked){\r\n");
      out.write("\t\t\t\tseqs += seqs == '' ?  obj[i].value : ',' + obj[i].value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn seqs;\r\n");
      out.write("\t}\r\n");
      out.write("    \r\n");
      out.write("    /**\r\n");
      out.write("    * 사이트로 정렬\r\n");
      out.write("    */\r\n");
      out.write("    function setSiteData(sg_seq, sd_gsn){\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("        f.sg_seq.value = sg_seq;\r\n");
      out.write("        f.sd_gsn.value = sd_gsn;\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 휴지통 삭제자 정렬\r\n");
      out.write("    */\r\n");
      out.write("    function setDelname(delname){\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("        f.del_mname.value = delname;\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 검색\r\n");
      out.write("    */\r\n");
      out.write("    function doSearch(){\r\n");
      out.write("        document.fSend.nowpage.value = '1';\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 헤드라인 이벤트\r\n");
      out.write("    */\r\n");
      out.write("    function show_me(i){\r\n");
      out.write("\t\tdiv_show.innerHTML = eval('div'+i).innerHTML;\r\n");
      out.write("    \tdiv_show.style.top = event.y + document.body.scrollTop + 20 + \"px\";\r\n");
      out.write("    \tdiv_show.style.display='';\r\n");
      out.write("    }\r\n");
      out.write("    function close_me(i){\r\n");
      out.write("    \tdiv_show.style.display='none';\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 유사기사 헤드라인 이벤트\r\n");
      out.write("    */\r\n");
      out.write("    function showSamelist(i){\r\n");
      out.write("\t\tdiv_show.innerHTML = eval('sameContent'+i).innerHTML;\r\n");
      out.write("    \tdiv_show.style.top = event.y + document.body.scrollTop + 15;\r\n");
      out.write("    \tdiv_show.style.display='';\r\n");
      out.write("    }\r\n");
      out.write("    function closeSamelist(i){\r\n");
      out.write("    \tdiv_show.style.display='none';\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 검색기간\r\n");
      out.write("    */\r\n");
      out.write("    function setDateTerm( day ) {\r\n");
      out.write("        var f = document.getElementById('fSend');\r\n");
      out.write("        f.sDateFrom.value   = getdate( day );\r\n");
      out.write("        f.sDateTo.value     = sCurrDate;\r\n");
      out.write("        f.Period.value      = day + 1;\r\n");
      out.write("    }\r\n");
      out.write("\tfunction getdate(day){\r\n");
      out.write("\t\tvar newdate = new Date();\r\n");
      out.write("\t\tvar resultDateTime;\r\n");
      out.write("\t\tvar tempDateTime = 0;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(tempDateTime==0)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\ttempDateTime = newdate.getTime();\r\n");
      out.write("\t\t}  \r\n");
      out.write("              \r\n");
      out.write("\t\tresultDateTime = tempDateTime - ( day * 24 * 60 * 60 * 1000);\r\n");
      out.write("        newdate.setTime(resultDateTime);\r\n");
      out.write("\r\n");
      out.write("        var year;\r\n");
      out.write("        var month;\r\n");
      out.write("        var day;\r\n");
      out.write("\t\tvar result_date;\r\n");
      out.write("\t\tyear = newdate.getFullYear();\r\n");
      out.write("\t\tmonth = newdate.getMonth()+1;\r\n");
      out.write("\t\tday = newdate.getDate();\r\n");
      out.write("\t\tif(month < 10){\r\n");
      out.write("\t\t\tmonth = '0'+month;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(day < 10){\r\n");
      out.write("\t\t\tday = '0'+day;\r\n");
      out.write("\t\t}\r\n");
      out.write("        result_date = year + '-' + month + '-' + day;\r\n");
      out.write("        return result_date;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 필드정렬\r\n");
      out.write("    */\r\n");
      out.write("    function setOrder(order){\r\n");
      out.write("        selecedOrder = order;\r\n");
      out.write("        click = \"TRUE\";\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 기사 전체 체크\r\n");
      out.write("    */\r\n");
      out.write("    function reverseAll( chked ) {\r\n");
      out.write("        var frm = document.getElementById('fCheck');\r\n");
      out.write("        var first = 0;\r\n");
      out.write("        for (var i = 0; i < frm.elements.length; i++) {\r\n");
      out.write("            var e = frm.elements[i];\r\n");
      out.write("            if ( e.type == \"checkbox\" ) {\r\n");
      out.write("                e.checked = chked;\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    * 유사기사 리스트 보기\r\n");
      out.write("    */\r\n");
      out.write("    var pre_SameList = '';\r\n");
      out.write("    function openSameList( md_pseq, md_seq,searchMode){\r\n");
      out.write("        var sameLayer = document.getElementById('SameList_' + md_pseq);\r\n");
      out.write("\r\n");
      out.write("    \tif(pre_SameList == sameLayer && pre_SameList.style.display==''){\r\n");
      out.write("    \t\tpre_SameList.style.display = 'none';\r\n");
      out.write("    \t\treturn;\r\n");
      out.write("    \t}\r\n");
      out.write("\r\n");
      out.write("    \tif(pre_SameList){\r\n");
      out.write("    \t\tpre_SameList.style.display = 'none';\r\n");
      out.write("    \t}\r\n");
      out.write("\r\n");
      out.write("    \tpre_SameList = sameLayer;\r\n");
      out.write("    \tsameLayer.innerHTML = '로딩중...';\r\n");
      out.write("    \tif_samelist.location.href = \"inc_same_list.jsp?md_pseq=\" + md_pseq + \"&md_seq=\" + md_seq + \"&searchMode=\" + searchMode;\r\n");
      out.write("    \tsameLayer.style.display = '';\r\n");
      out.write("    }\r\n");
      out.write("    function fillSameList(no){\r\n");
      out.write("    \tvar ly = document.getElementById('SameList_'+no);    \t\r\n");
      out.write("\t    ly.innerHTML = if_samelist.zzFilter.innerHTML;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t* 이슈등록 팝업 열기\r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction send_issue(mode, md_seq){\r\n");
      out.write("\t\t//close_menu();\r\n");
      out.write("\t\tvar f = document.getElementById('fCheck');\t\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.send_mb.md_seqs.value = \"\";\t\r\n");
      out.write("\r\n");
      out.write("\t\tif(mode=='insert'){\r\n");
      out.write("\t\t\tdocument.send_mb.mode.value = mode;\r\n");
      out.write("\t\t\tdocument.send_mb.md_seq.value = md_seq;\t\t\r\n");
      out.write("        \tpopup.openByPost('send_mb','../issue/new_pop_issueData_form.jsp',1058,920,false,true,false,'pop_send_issue');\r\n");
      out.write("\t\t}else if(mode=='multi'){\t\t\r\n");
      out.write("\t\t\tdocument.send_mb.mode.value = mode;\t\t\t\t\r\n");
      out.write("\t\t\tif(f.chkData){\r\n");
      out.write("\t\t\t\tif(f.chkData.length>1){\t\t\t\t\t\r\n");
      out.write("\t\t\t\t \tfor(var i=0; i<f.chkData.length; i++)\r\n");
      out.write("\t\t\t\t \t{\r\n");
      out.write("\t\t\t\t\t \tif(f.chkData[i].checked){\r\n");
      out.write("\t\t\t\t \t\t\tdocument.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.chkData[i].value : document.send_mb.md_seqs.value+','+f.chkData[i].value;\r\n");
      out.write("\t\t\t\t\t \t}\r\n");
      out.write("\t\t\t\t \t}\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tdocument.send_mb.md_seqs.value = f.chkData.value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\t\t\r\n");
      out.write("\t\t\tif(document.send_mb.md_seqs.value==''){alert('정보를 선택해주세요.'); return;}\r\n");
      out.write("\t\t\tpopup.openByPost('send_mb','pop_multi_issueData_form.jsp',650,450,false,true,false,'pop_send_issue');\r\n");
      out.write("\t\t}else if(mode=='samemulti'){\t\t\r\n");
      out.write("\t\t\tdocument.send_mb.mode.value = mode;\t\t\t\t\r\n");
      out.write("\t\t\tif(f.sameChk){\r\n");
      out.write("\t\t\t\tif(f.sameChk.length>1){\t\t\t\t\t\r\n");
      out.write("\t\t\t\t \tfor(var i=0; i<f.sameChk.length; i++)\r\n");
      out.write("\t\t\t\t \t{\r\n");
      out.write("\t\t\t\t\t \tif(f.sameChk[i].checked){\r\n");
      out.write("\t\t\t\t \t\t\tdocument.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.sameChk[i].value : document.send_mb.md_seqs.value+','+f.sameChk[i].value;\r\n");
      out.write("\t\t\t\t\t \t}\r\n");
      out.write("\t\t\t\t \t}\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tdocument.send_mb.md_seqs.value = f.sameChk.value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\t\t\r\n");
      out.write("\t\t\tif(document.send_mb.md_seqs.value==''){alert('정보를 선택해주세요.'); return;}\r\n");
      out.write("\t\t\tpopup.openByPost('send_mb','pop_multi_issue_data_form.jsp',650,650,false,true,false,'pop_send_issue');\r\n");
      out.write("\t\t}else if(mode == 'update'){\r\n");
      out.write("\t\t\tdocument.send_mb.mode.value = mode;\r\n");
      out.write("\t\t\tdocument.send_mb.md_seq.value = md_seq;\r\n");
      out.write("\t\t\tvar targetUrl = \"issueChk.jsp\";\r\n");
      out.write("\t\t\tvar param = $(\"#send_mb\").serialize();\r\n");
      out.write("\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\ttype : \"POST\"\r\n");
      out.write("\t\t\t\t,async : true\r\n");
      out.write("\t\t\t\t,url: targetUrl\r\n");
      out.write("\t\t\t\t,timeout: 30000\r\n");
      out.write("\t\t\t\t,data : param\r\n");
      out.write("\t\t\t\t,dataType : 'text'\r\n");
      out.write("\t\t\t\t,success : function(data){\r\n");
      out.write("\t\t\t\t\tdata = trim(data);\r\n");
      out.write("\t\t\t\t\tif(data != '0'){\r\n");
      out.write("\t\t\t\t\t\talert(\"등록된 데이터 입니다.\\n수정 화면으로 이동합니다.\");\r\n");
      out.write("\t\t\t\t\t\tpopup.openByPost('send_mb','../issue/new_pop_issueData_form.jsp',1058,920,false,true,false,'pop_send_issue');\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\talert(\"등록된 데이터가 아닙니다.\\n등록 화면으로 이동합니다.\");\r\n");
      out.write("\t\t\t\t\t\tpopup.openByPost('send_mb','../issue/new_pop_issueData_form.jsp',1058,920,false,true,false,'pop_send_issue');\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction trim(str){\r\n");
      out.write("\t\treturn str.replace(/^\\s\\s*/, '').replace(/\\s\\s*$/, '');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t* URL열기\r\n");
      out.write("\t*/\r\n");
      out.write("\tvar chkPop = 1;\r\n");
      out.write("\tfunction hrefPop(url, m_seq, md_seq){\r\n");
      out.write("\t\twindow.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');\r\n");
      out.write("\t\tchkPop++;\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t// 열어본 페이지는 DB저장 및 색상변경\r\n");
      out.write("\t\tsend_comfirm.mode.value = 'insert';\r\n");
      out.write("\t\tsend_comfirm.m_seq.value = m_seq;\r\n");
      out.write("\t\tsend_comfirm.md_seq.value = md_seq;\r\n");
      out.write("\t\tsend_comfirm.target = 'proceeFrame';\r\n");
      out.write("\t\tsend_comfirm.action = 'search_comfirm_prc.jsp';\r\n");
      out.write("\t\tsend_comfirm.submit();\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction listClear(m_seq){\r\n");
      out.write("\t\tvar f = document.getElementById('fCheck');\r\n");
      out.write("\r\n");
      out.write("\t\tvar selectedMdSeq = '';\r\n");
      out.write("\r\n");
      out.write("\t\tif(f.chkData){\r\n");
      out.write("\t\t\tif(f.chkData.length){\t\t\t\t\t\r\n");
      out.write("\t\t\t \tfor(var i=0; i<f.chkData.length; i++)\r\n");
      out.write("\t\t\t \t{\r\n");
      out.write("\t\t\t\t \tif(f.chkData[i].checked){\r\n");
      out.write("\t\t\t\t \t\tif(selectedMdSeq == ''){\r\n");
      out.write("\t\t\t\t \t\t\tselectedMdSeq = f.chkData[i].value;\r\n");
      out.write("\t\t\t\t \t\t}else{\r\n");
      out.write("\t\t\t\t \t\t\tselectedMdSeq += \",\" +  f.chkData[i].value;\r\n");
      out.write("\t\t\t\t \t\t}\r\n");
      out.write("\t\t\t\t \t}\r\n");
      out.write("\t\t\t \t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tselectedMdSeq = f.chkData.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\tif(selectedMdSeq == ''){\r\n");
      out.write("\t\t\talert(\"선택된 기사가 없습니다.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tsend_comfirm.md_seqs.value = selectedMdSeq;\r\n");
      out.write("\t\tsend_comfirm.mode.value = 'delete';\t\r\n");
      out.write("\t\tsend_comfirm.m_seq.value = m_seq;\r\n");
      out.write("\t\tsend_comfirm.target = 'proceeFrame';\r\n");
      out.write("\t\tsend_comfirm.action = 'search_comfirm_prc.jsp';\r\n");
      out.write("\t\tsend_comfirm.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    /**\r\n");
      out.write("\t* 이슈 타이틀 변경\r\n");
      out.write("\t*/\r\n");
      out.write("    function changeIssueTitle(){\r\n");
      out.write("\t\tajax.post('selectbox_issue_title.jsp','send_mb','td_it_title');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t* 북마크 저장 관련\r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction saveBookMark(){\r\n");
      out.write("\t\tvar count = 0;\r\n");
      out.write("\t\tvar obj = document.getElementById('fCheck');\r\n");
      out.write("\t\tfor (var i = 0; i <obj.length; i++) {\r\n");
      out.write("\t\t\tif(obj[i].checked){send_bookmark.bookMarkSeq.value = obj[i].value; count++;}        \r\n");
      out.write("  \t    }\r\n");
      out.write("  \t    if(count>1){alert('하나 이상 선택하셨습니다.'); return;}\r\n");
      out.write("\t\tif(send_bookmark.bookMarkSeq.value=='') {alert('정보를 선택을 해주세요.'); return;}  \t    \r\n");
      out.write("\t\tsend_bookmark.target = 'proceeFrame';\r\n");
      out.write("\t\tsend_bookmark.action = 'search_bookmark_prc.jsp';\r\n");
      out.write("\t\tsend_bookmark.submit();\t\t\t  \t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t* 북마크 검색 관련\r\n");
      out.write("\t*/\r\n");
      out.write("    function doBookMarkSearch() {\r\n");
      out.write("    \tvar f = document.getElementById('fSend');\r\n");
      out.write("        f.bookMarkYn.value = 'Y';\r\n");
      out.write("        doSubmit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t* 검색유형 전체 체크\r\n");
      out.write("\t*/\t\r\n");
      out.write("\tfunction chkSiteGroupAll(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif($(\"[name=sltSiteGroupAll]\").is(\":checked\")){\r\n");
      out.write("\t\t\t$(\"[name=sltSiteGroup]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr(\"checked\", true);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=sltSiteGroup]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr(\"checked\", false);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkSite(){\r\n");
      out.write("\t\tvar chk = 0; \r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=sltSiteGroup]\").each(function(){\r\n");
      out.write("\t\t\tif(!$(this).attr(\"checked\")){ \r\n");
      out.write("\t\t\t\t\tchk += 1;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tif(chk > 0){\r\n");
      out.write("\t\t\t$(\"[name=sltSiteGroupAll]\").attr(\"checked\", false);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=sltSiteGroupAll]\").attr(\"checked\", true);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction timerStart(){\r\n");
      out.write("\t\tvar f = document.getElementById('fSend');\r\n");
      out.write("\t\tif(f.timer.value == 'Y'){\r\n");
      out.write("\t\t\tsetTimer(");
      out.print(uei.getSt_reload_time());
      out.write(");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\twindow.onload = timerStart;\r\n");
      out.write("\r\n");
      out.write("\tfunction actionLayer(obj){\r\n");
      out.write("\t\tif(obj.src.indexOf(\"close\") > -1){\r\n");
      out.write("\r\n");
      out.write("\t\t\t/*\r\n");
      out.write("\t\t\tfor(var i = 1; i < 9; i++){\r\n");
      out.write("\t\t\t\tdocument.getElementById('hide'+i).style.display = 'none';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t*/\r\n");
      out.write("\t\t\tdocument.getElementById('hide1').style.display = 'none';\r\n");
      out.write("\t\t\tdocument.getElementById('hide2').style.display = 'none';\r\n");
      out.write("\t\t\tobj.src = '../../images/search/btn_searchbox_open.gif';\r\n");
      out.write("\t\t\tdocument.getElementById('fSend').searchtype.value = \"0\";\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t/*\r\n");
      out.write("\t\t\tfor(var i = 1; i < 9; i++){\r\n");
      out.write("\t\t\t\tdocument.getElementById('hide'+i).style.display = '';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t*/\r\n");
      out.write("\t\t\tdocument.getElementById('hide1').style.display = '';\r\n");
      out.write("\t\t\tdocument.getElementById('hide2').style.display = '';\r\n");
      out.write("\t\t\tobj.src = '../../images/search/btn_searchbox_close.gif';\r\n");
      out.write("\t\t\tdocument.getElementById('fSend').searchtype.value = \"1\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction goExcelTo(){\r\n");
      out.write("\r\n");
      out.write(" \t\tvar f = document.fSend;\r\n");
      out.write(" \t\t\r\n");
      out.write("\t\tf.action = 'search_download_excel.jsp';\r\n");
      out.write("\t\tf.target = 'proceeFrame';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\t//f.action = 'issue_list.jsp';\r\n");
      out.write("\t\t//f.target = 'proceeFrame';\r\n");
      out.write(" \t}\r\n");
      out.write("\r\n");
      out.write("\tfunction listAlter(md_seq, md_pseq){\r\n");
      out.write("\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\r\n");
      out.write("\t\tf.action = \"alterList.jsp?md_seq=\"+md_seq+ \"&md_pseq=\" + md_pseq +\"&nowpage=\" + f.nowpage.value+\"&sKeyword=\"+f.sKeyword.value;\r\n");
      out.write("        f.target = 'if_samelist';\r\n");
      out.write("        f.submit();\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\tfunction originalView(md_seq, s_seq, md_title){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar url = '';\r\n");
      out.write("\t\turl = \"originalView.jsp?md_seq=\"+md_seq;\t\r\n");
      out.write("\t\twindow.open(url, \"originalPop\", \"width=708, height=672, scrollbars=yes\");\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar chkOriginal = 1;\r\n");
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
      out.write("\r\n");
      out.write("\tfunction sendMail(){\r\n");
      out.write("\t\tvar count = 0;\r\n");
      out.write("\t\tvar obj = document.getElementById('fCheck');\r\n");
      out.write("\t\tfor (var i = 0; i <obj.length; i++) {\r\n");
      out.write("\t\t\tif(obj[i].checked){send_mail.md_seqs.value = obj[i].value; count++;}        \r\n");
      out.write("  \t    }\r\n");
      out.write("  \t    if(count>1){alert('하나 이상 선택할수 없습니다.'); return;}\r\n");
      out.write("\t\tif(send_mail.md_seqs.value=='') {alert('정보를 선택을 해주세요.'); return;}  \t    \r\n");
      out.write("\t\tsend_mail.target = 'proceeFrame';\r\n");
      out.write("\t\tsend_mail.action = 'pop_mail_send.jsp';\r\n");
      out.write("\t\tsend_mail.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction sameAdd(obj){\r\n");
      out.write("\t\tvar f = document.getElementById(\"send_same\");\r\n");
      out.write("\t\tvar form = document.getElementById(\"sameAddForm\");\r\n");
      out.write("\r\n");
      out.write("\t\tvar objdata = obj.getBoundingClientRect();\r\n");
      out.write("\t\tform.style.top = objdata.top+document.body.scrollTop+33;\r\n");
      out.write("\t\tform.style.left = objdata.left+document.body.scrollLeft-100;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar chkCount = 0;\r\n");
      out.write("\t\t$('input[name=chkData]').each(\r\n");
      out.write("\t\t\tfunction(){\r\n");
      out.write("\t\t\t\tif(this.checked){\r\n");
      out.write("\t\t\t\t\tchkCount++;\r\n");
      out.write("\t\t\t\t\tf.md_seq.value = this.value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t);\r\n");
      out.write("\t\tif(chkCount == 0){\r\n");
      out.write("\t\t\talert('선택된 정보가 없습니다.');return;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif(chkCount > 1){\r\n");
      out.write("\t\t\talert('한개의 정보만 선택할 수 있습니다.');return;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tajax.post('sameAddLayer.jsp', 'send_same', 'sameAddForm');\r\n");
      out.write("\t\t\tshowLayer();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction showLayer(){\r\n");
      out.write("\t\tvar options = {};\r\n");
      out.write("\t\t$(\"#sameAddForm\").toggle(\"fold\", options, 900);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction searchData(){\r\n");
      out.write("\t\tvar f = document.getElementById('send_same');\r\n");
      out.write("\t\tf.keyword.value = $(\"#same_keyword\").val();\r\n");
      out.write("\t\tajax.post('sameAddLayerList.jsp', 'send_same', 'listArea');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction sameAddSave(){\r\n");
      out.write("\t\tvar md_pseqs = '';\r\n");
      out.write("\t\t$('input[name=same_md_pseq]').each(\r\n");
      out.write("\t\t\tfunction(){\r\n");
      out.write("\t\t\t\tif(this.checked){\r\n");
      out.write("\t\t\t\t\tif(md_pseqs == ''){\r\n");
      out.write("\t\t\t\t\t\tmd_pseqs = this.value;\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tmd_pseqs += ','+this.value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t);\r\n");
      out.write("\t\t$(\"#md_pseqs\").val(md_pseqs);\r\n");
      out.write("\t\talert($(\"#md_pseqs\").val());\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//매체관리\r\n");
      out.write("\tfunction checkTiersAll(){\r\n");
      out.write("\t\tif($(\"[name=tierAll]\").is(\":checked\")){\r\n");
      out.write("\t\t\t$(\"[name=tier]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr(\"checked\", true);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=tier]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr(\"checked\", false);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
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
      out.write("\tfunction samePackage(){\r\n");
      out.write("    \tif ( !confirm(\"선택된 항목을 묶으시겠습니까?\" ) ) {\r\n");
      out.write("            return;\r\n");
      out.write("     \t}\r\n");
      out.write("\r\n");
      out.write("    \tvar f = document.getElementById('fCheck');\r\n");
      out.write("    \tdocument.send_mb.md_seqs.value = '';\r\n");
      out.write("    \tif(f.chkData){\r\n");
      out.write("    \t\tif(f.chkData.length>1){\t\t\t\t\t\r\n");
      out.write("    \t\t \tfor(var i=0; i<f.chkData.length; i++)\r\n");
      out.write("    \t\t \t{\r\n");
      out.write("    \t\t\t \tif(f.chkData[i].checked){\r\n");
      out.write("    \t\t \t\t\tdocument.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.chkData[i].value : document.send_mb.md_seqs.value+','+f.chkData[i].value;\r\n");
      out.write("    \t\t\t \t}\r\n");
      out.write("    \t\t \t}\r\n");
      out.write("    \t\t}else{\r\n");
      out.write("    \t\t\tdocument.send_mb.md_seqs.value = f.chkData.value;\r\n");
      out.write("    \t\t}\r\n");
      out.write("    \t}\r\n");
      out.write("    \t\r\n");
      out.write("    \tdocument.send_mb.target = 'proceeFrame';\r\n");
      out.write("    \tdocument.send_mb.action = 'search_same_package_prc.jsp?nowpage=' + document.fSend.nowpage.value;\r\n");
      out.write("    \tdocument.send_mb.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<!--헤드라인 레이어-->\r\n");
      out.write("<div id=div_show style=\"font-size: 12px; padding-right: 5px; display: none; padding-left: 5px; top:0; left: 140px; width:500px; height:100px; padding-bottom: 5px; padding-top: 5px; position: absolute; background-color: #ffffcc; border: #ff6600 2px solid;\"></div>\r\n");
      out.write("\r\n");
      out.write("<!--유사묶기 레이어-->\r\n");
      out.write("<div id=\"sameAddForm\" style=\"width:400px;height:150px; position: absolute; display:none; border: 1px solid cccccc; background-color: ffffff\"></div>\r\n");
      out.write("\r\n");
      out.write("<!--북마크 정보 FORM-->\r\n");
      out.write("<form name=\"send_bookmark\" id=\"send_bookmark\" method=\"post\">\r\n");
      out.write("\t<input name=\"bookMarkContents\" type=\"hidden\" value=\"search\">\r\n");
      out.write("\t<input name=\"bookMarkSeq\" type=\"hidden\" value=\"\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<!--열어본 페이지 색상변경 및  DB저장-->\r\n");
      out.write("<form name=\"send_comfirm\" id=\"send_comfirm\" method=\"post\">\r\n");
      out.write("\t<input name=\"m_seq\" type=\"hidden\">\r\n");
      out.write("\t<input name=\"md_seq\" type=\"hidden\">\r\n");
      out.write("\t<input name=\"md_seqs\" type=\"hidden\">\r\n");
      out.write("\t<input name=\"mode\" type=\"hidden\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<!--메일발송-->\r\n");
      out.write("<form name=\"send_mail\" id=\"send_mail\" method=\"post\">\r\n");
      out.write("\t<input name=\"md_seqs\" type=\"hidden\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<!--유사묶기폼-->\r\n");
      out.write("<form name=\"send_same\" id=\"send_same\" method=\"post\">\r\n");
      out.write("\t<input name=\"md_seq\" id=\"md_seq\" type=\"hidden\">\r\n");
      out.write("\t<input name=\"keyword\" id=\"keyword\" type=\"hidden\">\r\n");
      out.write("\t<input name=\"md_pseqs\" id=\"md_pseqs\" type=\"hidden\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<form name=\"send_mb\" id=\"send_mb\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" name=\"md_seq\">\r\n");
      out.write("<input type=\"hidden\" name=\"md_seqs\">\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<table style=\"height:100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><!-- width:100% -->\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\" style=\"width:auto\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;margin-left:20px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align: left;\"><img src=\"../../images/search/tit_icon.gif\" /><img src=\"../../images/search/tit_0102.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">정보검색</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">전체키워드검색</td>\r\n");
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
      out.write("\t\t\t\t<form name=\"fSend\" id=\"fSend\" action=\"search_main_contents.jsp\" method=\"post\"  style=\"margin-bottom: 0px\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"xp\" value=\"");
      out.print(uei.getK_xp());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"yp\" value=\"");
      out.print(uei.getK_yp());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"zp\" value=\"");
      out.print(uei.getK_zp());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"timer\" value=\"");
      out.print(sTimer);
      out.write("\" >\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"interval\" value=\"");
      out.print(uei.getSt_reload_time());
      out.write("\" >\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"type\" value=\"");
      out.print(uei.getMd_type());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"searchtype\" value=\"");
      out.print(uei.getSt_menu());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"sOrder\" value=\"");
      out.print(uei.getOrder());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"sOrderAlign\" value=\"");
      out.print(uei.getOrderAlign());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"sg_seq\" value=\"");
      out.print(uei.getSg_seq());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"sd_gsn\" value=\"");
      out.print(uei.getS_seq());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"searchmode\" value=\"");
      out.print(uei.getSearchMode());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"Period\" value=\"");
      out.print(uei.getSt_reload_time());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input name=\"nowpage\" type=\"hidden\" value=\"");
      out.print(iNowPage);
      out.write("\">          \r\n");
      out.write("\t\t\t\t\t<input name=\"del_mname\" type=\"hidden\" value=\"");
      out.print(uei.getDelName());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"SaveList\">\r\n");
      out.write("\t\t\t\t\t<input name=\"bookMarkYn\" type=\"hidden\">\r\n");
      out.write("\t\t\t\t\t<input name=\"tiers\" type=\"hidden\">\r\n");
      out.write("\t\t\t\t\t<input name=\"language\" type=\"hidden\" value=\"KOR\">\r\n");
      out.write("\t\t\t\t<div class=\"article\">\r\n");
      out.write("\t\t\t\t \t<div class=\" ui_searchbox_00\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"c_wrap\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>검색단어</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<select class=\"ui_select_04\" name=\"searchType\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<option value=\"1\" ");
if(searchType.equals("1"))out.print("selected=selected"); 
      out.write(">제목</option>\r\n");
      out.write("\t\t\t\t\t\t            \t<option value=\"2\" ");
if(searchType.equals("2"))out.print("selected=selected"); 
      out.write(">제목+내용</option>\r\n");
      out.write("\t\t\t\t\t\t            \t<option value=\"3\" ");
if(searchType.equals("3"))out.print("selected=selected"); 
      out.write(">출처</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select\r\n");
      out.write("\t\t\t\t\t\t\t\t\t><input id=\"input_date_00\" type=\"text\" class=\"ui_input_02\" name=\"sKeyword\"  value=\"");
      out.print(uei.getKeyword());
      out.write("\" onkeydown=\"javascript:if(event.keyCode == 13){doSearch();}\"><label for=\"input_date_00\" class=\"invisible\">검색어입력</label\r\n");
      out.write("\t\t\t\t\t\t\t\t\t><button class=\"ui_btn_02\" onclick=\"doSearch();\" ><span class=\"icon search_01\">-</span>검색</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"c_sort\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>검색기간</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"sDateFrom\" id=\"sDateFrom\" value=\"");
      out.print(uei.getDateFrom());
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_first\" readonly><label for=\"sDateFrom\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<select id=\"stime\" name=\"stime\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

									  String timeSelected = "";	
									  for(int i =0; i < 24; i ++){
										if(Integer.parseInt(uei.getStime()) == i){
											timeSelected = "selected";
										}else{
											timeSelected = "";
										}
										
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(i);
      out.write('"');
      out.write(' ');
      out.print(timeSelected);
      out.write(' ');
      out.write('>');
      out.print(i);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select><label for=\"stime\"></label>~\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"sDateTo\" id=\"sDateTo\" value=\"");
      out.print(uei.getDateTo());
      out.write("\" type=\"text\" class=\"ui_datepicker_input input_date_last\" readonly><label for=\"sDateTo\">날짜입력</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<select id=\"etime\" name=\"etime\" class=\"ui_select_04\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

									  timeSelected = "";	
									  for(int i =0; i < 24; i ++){
										if(Integer.parseInt(uei.getEtime()) == i){
											timeSelected = "selected";
										}else{
											timeSelected = "";
										}
										
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(i);
      out.write('"');
      out.write(' ');
      out.print(timeSelected);
      out.write(' ');
      out.write('>');
      out.print(i);
      out.write("시</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select><label for=\"etime\"></label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<a href=\"javascript:setDateTerm(0);\" class=\"ui_btn_06\">금일</a>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<a href=\"javascript:setDateTerm(2);\" class=\"ui_btn_06\">3일</a>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<a href=\"javascript:setDateTerm(6);\" class=\"ui_btn_06\">7일</a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>정보유형</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"checkbox\" id=\"ui_input_chk_00_00\" name=\"chkAll\" onclick=\"javascript:checkAll();\" ");
      out.print(sTypeAll);
      out.write(" value=\"\"  ><label for=\"ui_input_chk_00_00\"><span class=\"chkbox_00\"></span><strong>전체</strong></label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"checkbox\" name=\"chkGisa\"  onclick=\"javascript:checkEtc();\" ");
      out.print(sType1);
      out.write(" id=\"ui_input_chk_00_01\" value=\"\"  ><label for=\"ui_input_chk_00_01\"><span class=\"chkbox_00\"></span><span class=\"ui_ico_news\">-</span>기사</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"checkbox\" name=\"chkGesi\"   onclick=\"javascript:checkEtc();\" ");
      out.print(sType2);
      out.write(" id=\"ui_input_chk_00_02\" value=\"\" ><label for=\"ui_input_chk_00_02\"><span class=\"chkbox_00\"></span><span class=\"ui_ico_post\">-</span>게시</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp\">\t\t<input type=\"checkbox\" name=\"chkGongji\" onclick=\"javascript:checkEtc();\" ");
      out.print(sType3);
      out.write(" id=\"ui_input_chk_00_03\" value=\"\" ><label for=\"ui_input_chk_00_03\"><span class=\"chkbox_00\"></span><span class=\"ui_ico_notice\">-</span>공지</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>검색유형</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								String[] arrSelected = null;
								if(!uei.getSg_seq().equals("")){
									arrSelected = uei.getSg_seq().split(",");
								}
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"checkbox\" id=\"ui_input_chk_01_00\" name=\"sltSiteGroup\" value=\"\" class=\"boardAllChecker\" ");
if(alGroup.size() == arrSelected.length){out.print("checked");}
      out.write("><label for=\"ui_input_chk_01_00\"><span class=\"chkbox_00\"></span><strong>전체</strong></label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								String checked = "";
								for( int i = 0; i < alGroup.size() ; i++ ) {
									sgi = (siteGroupInfo)alGroup.get(i);
									 if(arrSelected != null){
										for(int j = 0; j < arrSelected.length; j++){
											if(arrSelected[j].equals(sgi.getSg_seq()+"")){
												checked = "checked";
											}
										}
									}
								
      out.write(" \r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input type=\"checkbox\" id=\"ui_input_chk_01_0");
      out.print(i+1);
      out.write("\" name=\"sltSiteGroup\" value=\"");
      out.print(sgi.getSg_seq());
      out.write('"');
      out.write(' ');
      out.print(checked);
      out.write(" ><label for=\"ui_input_chk_01_0");
      out.print(i+1);
      out.write("\"><span class=\"chkbox_00\"></span>");
      out.print(sgi.getSd_name());
      out.write("</label></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									checked="";
								}
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
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
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ui_row_00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"title\"><span class=\"icon\">-</span>이슈등록여부</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"txts\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"issue_check\" value=\"\" type=\"hidden\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input id=\"issue_check\" type=\"checkbox\" value=\"Y\" ");
if("Y".equals(issue_check)){out.print("checked");}else if("Y','N".equals(issue_check)){out.print("checked");}
      out.write("><label for=\"issue_check\"><span class=\"chkbox_00\"></span>등록</label></div>&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"dcp m_r_10\"><input id=\"issue_check2\" type=\"checkbox\" value=\"N\" ");
if("N".equals(issue_check)){out.print("checked");}else if("Y','N".equals(issue_check)){out.print("checked");}
      out.write("><label for=\"issue_check2\"><span class=\"chkbox_00\"></span>미등록</label></div>&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"ui_searchbox_toggle\">\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"btn_toggle active\">검색조건 열기/닫기</a>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t<!-- // 검색 -->\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 검색 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<!-- <td style=\"height:45px;background:url('../../images/search/list_top_line.gif') bottom repeat-x;\"> -->\r\n");
      out.write("\t\t\t\t<td style=\"height:25px;padding-top:15px\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td  style=\"text-align:left\"><img src=\"../../images/search/icon_search_bullet.gif\" style=\"vertical-align:middle\"> <strong>");
      out.print(smgr.msKeyTitle);
      out.write("</strong>에 대한 검색결과 /<strong> ");
      out.print(iTotalCnt);
      out.write("</strong> 건, <strong>");
      out.print(iTotalCnt==0?0:iNowPage);
      out.write("</strong>/<strong>");
      out.print(iTotalPage);
      out.write("</strong> pages</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"150\" align=\"right\" style=\"padding: 2px 0px 0px 3px;\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"search_reset_time\" id=\"watch\"><strong>새로고침 중지중..</strong></span>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"140\" style=\"text-align:right\">\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"slttimer\" class=\"t\" OnChange=\"setTimer(this.value);\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"0\"  ");
if(uei.getSt_reload_time().equals("0")) out.println("selected");
      out.write(">사용하지 않음</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"5\"  ");
if(uei.getSt_reload_time().equals("5")) out.println("selected");
      out.write(">5분마다 새로고침</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"10\" ");
if(uei.getSt_reload_time().equals("10")) out.println("selected");
      out.write(">10분마다 새로고침</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"20\" ");
if(uei.getSt_reload_time().equals("20")) out.println("selected");
      out.write(">20분마다 새로고침</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"30\" ");
if(uei.getSt_reload_time().equals("30")) out.println("selected");
      out.write(">30분마다 새로고침</option>\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:25px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align:left\"><img src=\"../../images/search/icon_search_bullet.gif\" style=\"vertical-align:middle\"> <strong>출처별</strong> 검색결과(유사포함) : ");
      out.print(strSourceCnts);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<form name=\"fCheck\" id=\"fCheck\" target=\"_blank\" method=\"post\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;margin-left:20px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_allselect.gif\" onclick=\"reverseAll(!document.getElementById('fCheck').chkAllCheck.checked);\"/></td>\r\n");

	if(uei.getSearchMode().equals("ALLKEY")){

      out.write("  \r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_del.gif\" onclick=\"idxProcess('truncate');\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_bookmark.gif\" onclick=\"saveBookMark();\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_bookmark_search.gif\" onclick=\"doBookMarkSearch();\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_multi.gif\" onclick=\"send_issue('multi');\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_mailsend.gif\" onclick=\"sendMail();\" style=\"cursor:pointer; display: none;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_sames.gif\" onclick=\"samePackage();\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("<!--\t\t\t\t\t\t<td><input type=\"button\" value=\"지우개\" style=\"width: 88px; height: 24px\" onclick=\"listClear('");
      out.print(uei.getM_seq());
      out.write("')\"></td>-->\r\n");

	}else if(uei.getSearchMode().equals("DELIDX")){

      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_restoration.gif\" onclick=\"idxProcess('revert');\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/btn_del3.gif\" onclick=\"idxProcess('del');\" style=\"cursor:pointer\"/></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\"><b style=\"color:navy\">* 휴지통의 기사는 삭제 7일 후 영구삭제 됩니다.</b></td>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout: fixed;\">\r\n");

	if(!uei.getSearchMode().equals("DELIDX")){

      out.write("\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"20%\"><col width=\"*\"><col width=\"6%\"><col width=\"5%\"><col width=\"5%\"><col width=\"10%\">\r\n");

	}else{

      out.write("\r\n");
      out.write("\t\t\t\t<col width=\"5%\"><col width=\"15%\"><col width=\"50%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\">\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th><input type=\"checkbox\" name=\"chkAllCheck\" value=\"\" onclick=\"reverseAll(this.checked);\"></th>\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_SITE_NAME');\" style=\"cursor:pointer\">출처");
      out.print(sOrderMark1);
      out.write("</th>\r\n");

	if(!uei.getSearchMode().equals("DELIDX")){

      out.write("\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_TITLE');\" style=\"cursor:pointer\">제목");
      out.print(sOrderMark2);
      out.write("</th>\r\n");
      out.write("\t\t\t\t\t\t<th></th>\r\n");

		if(uei.getSearchMode().equals("ALLKEY")){

      out.write("\r\n");
      out.write("\t\t\t\t\t\t<th>관리</th>\r\n");

		}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_SAME_COUNT');\" style=\"cursor:pointer\">유사");
      out.print(sOrderMark3);
      out.write("</th>\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_DATE');\" style=\"cursor:pointer\">수집시간");
      out.print(sOrderMark5);
      out.write("</th>\r\n");

	}else{

      out.write("\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_TITLE');\" style=\"cursor:pointer\">제목");
      out.print(sOrderMark2);
      out.write("</th>\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('MD_DATE');\" style=\"cursor:pointer\">수집시간");
      out.print(sOrderMark5);
      out.write("</th>\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('M_NAME');\" style=\"cursor:pointer\">삭제자");
      out.print(sOrderMark7);
      out.write("</th>\r\n");
      out.write("\t\t\t\t\t\t<th onclick=\"setOrder('I_DELDATE');\" style=\"cursor:pointer\">삭제시간");
      out.print(sOrderMark6);
      out.write("</th>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

if(alData.size() > 0){
	
		String star = "";
		String follower = "";
	
	for(int i = 0; i < alData.size(); i++){
		MetaBean mBean = (MetaBean) alData.get(i);
		
	   	//내용 강조
	   	String Html = "";	   	
	   	if(!uei.getKeyword().equals("")) {
	   		Html=mBean.getHighrightHtml(200,"0000CC");
	   	}else{
	   		Html=mBean.getHighrightHtml(200,"0000CC");
	   	}
	   	
	   	//관리버튼
	   	String managerButton = "";
		if(uei.getSearchMode().equals("ALLKEY")){	  		
			managerButton ="<img id=\"issue_menu_icon"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" style=\"cursor:pointer\" ";
			if(mBean.getIssue_check().equals("Y")){
				managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\" onclick=\"send_issue('update','"+mBean.getMd_seq()+"');\">";
			}else{
				managerButton += "src=\"../../images/search/btn_manage_off.gif\" title=\"이슈 미등록 정보입니다.\" onclick=\"send_issue('insert','"+mBean.getMd_seq()+"');\">";
			}
		}
		
		//전체키워드검색
		if(uei.getSearchMode().equals("ALLKEY")){
			String overColor = "#F3F3F3";
        	String outColor = "";
        	String bookMarkColor = "";
        	String comfirmColor = "";
        	
        	if(bookMarkYn.equals("Y")&&mBean.getMd_seq().equals(bookMarkNum)){
        		overColor = "#00bfff";
        		outColor = "#00bfff";
        		bookMarkColor = "#00bfff";
        	}
        	
        	//카페 일경우~
        	star = "";
        	if(mBean.getS_seq().equals("3555") || mBean.getS_seq().equals("4943")){
        		star = "<img src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+mBean.getS_seq()+"','"+java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")+"')>";
        	}
        	
        	//트위터 일경우
        	follower = "";
        	if(!mBean.getT_followers().equals("")){
        		follower = "<font color=\"D72D2D\"><strong>[팔로워 : "+mBean.getT_followers()+"개]&nbsp;&nbsp;</strong></font>";   
        	}else{
        		follower = mBean.getT_followers();
        	}
        	
        	
        	
        	if(mBean.getMd_comfirm().equals("Y")){
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"' color='0000CC'/>"; 
        	}else if(mBean.getMd_comfirm().equals("M")){
        		//comfirmColor = "<font id ='list"+mBean.getMd_seq()+"' color='977d46'/>";
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"'/>";
        	}else{
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"'/>";
        	}
        	String md_site_name = "";
        	
        	md_site_name = mBean.getMd_site_name();
        	/* if(md_site_name.length() > 10){
        		md_site_name = md_site_name.substring(0,10);
        	} */

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr style=\"background-color:");
      out.print(bookMarkColor);
      out.write("\" onmouseover=\"this.style.backgroundColor='");
      out.print(overColor);
      out.write("';\" onmouseout=\"this.style.backgroundColor='");
      out.print(outColor);
      out.write("';\">\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓체크박스-->\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"chkData\" value=\"");
      out.print(mBean.getMd_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓출처-->\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-align: left;\"><font class=\"board_01_tit_0");
      out.print(mBean.getMd_type());
      out.write("\" style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:11px;color:#225bd1;width:110px;\" title=\"");
      out.print(mBean.getMd_site_name());
      out.write('"');
      out.write('>');
      out.print(md_site_name);
      out.write("</font></td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓제목-->\r\n");
      out.write("\t\t\t\t\t\t<td> \r\n");
      out.write("\t\t\t\t\t\t\t<div style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:380px;\"  >\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.print(star);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<a onClick=\"hrefPop('");
      out.print(mBean.getMd_url());
      out.write("', '");
      out.print(uei.getM_seq());
      out.write("', '");
      out.print(mBean.getMd_seq());
      out.write("');\" onfocus=\"this.blur();\" \r\n");
      out.write("\t\t\t\t\t\t\thref=\"javascript:void(0);\" onmouseover=\"show_me(");
      out.print(i);
      out.write(");\" onmouseout=\"close_me(");
      out.print(i);
      out.write(");\">");
      out.print(comfirmColor);
      out.print(mBean.getHtmlMd_title());
      out.write("</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=div");
      out.print(i);
      out.write(" style=\"display:none;width:200px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(follower + Html);
      out.write("     \r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓원문보기버튼-->\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\">\r\n");
      out.write("\t\t\t\t\t\t\t<img src=\"../../images/search/ico_original.gif\" style=\"cursor:pointer\" onclick=\"originalView('");
      out.print(mBean.getMd_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(mBean.getS_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8"));
      out.write("');\">\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓관리버튼-->\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(managerButton);
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓유사-->\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");

	if(mBean.getMd_same_count().equals("0")){
							out.print(mBean.getMd_same_count());
	}else{

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<a href=\"javascript:openSameList('");
      out.print(mBean.getMd_pseq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(mBean.getMd_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(uei.getSearchMode());
      out.write("');\">");
      out.print(mBean.getMd_same_count());
      out.write("</a>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<!--↓↓수집시간-->\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(mBean.getFormatMd_date("MM-dd HH:mm"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"same\" colspan=\"7\">\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"SameList_");
      out.print(mBean.getMd_pseq());
      out.write("\" style=\"display:none;\"></div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		//휴지통
		}else if(uei.getSearchMode().equals("DELIDX")){

      out.write("\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"chkData\" value=\"");
      out.print(mBean.getMd_seq());
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\">");
      out.print(mBean.getMd_site_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\">\r\n");
      out.write("\t\t\t\t\t\t\t<p class=\"board_01_tit_01\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(mBean.getHtmlMd_title());
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=div");
      out.print(i);
      out.write(" style=\"display:none;width:200px;height:80px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(Html);
      out.write("       \r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(mBean.getFormatMd_date("MM-dd HH:mm"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(mBean.getM_name());
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td>");
      out.print(mBean.getFormatI_deldate("MM-dd HH:mm"));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");

		}
	}
}

      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 페이징 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t<table style=\"padding-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"paging\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print(PageIndex.getPageIndex(iNowPage,iTotalPage,"","" ));
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
      out.write("\t\t<!-- 퀵 -->\r\n");
      out.write("\t\t<!-- <td style=\"background:#eaeaea;\" >\r\n");
      out.write("\t\t\t<table class=\"quick_bg\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"quick_bg2\" valign=\"top\"><img src=\"../../images/common/quick_bg_top.gif\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</td> -->\r\n");
      out.write("\t\t<!-- 퀵 -->\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<table width=\"162\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"calendar_conclass\" style=\"position:absolute;display:none;\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../images/calendar/menu_bg_004.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\" background=\"../../images/calendar/menu_bg_005.gif\"><table width=\"148\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>                                \t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td id=\"calendar_calclass\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"5\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><img src=\"../../images/calendar/menu_bg_006.gif\" width=\"162\" height=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<br><br><br><br>\r\n");
      out.write("<iframe id=\"if_samelist\" name=\"if_samelist\" width=\"0\" height=\"0\" src=\"about:blank\"></iframe>\r\n");
      out.write("<iframe id=\"proceeFrame\" name=\"proceeFrame\" width=\"0\" height=\"0\"></iframe>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");

	}catch(Exception e){
		e.printStackTrace();
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
