package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;
import risk.search.keywordInfo;
import risk.search.siteGroupInfo;
import risk.search.siteDataInfo;
import risk.search.MetaMgr;
import risk.util.ConfigUtil;

public final class search_005fenv_005fsetting_jsp extends org.apache.jasper.runtime.HttpJspBase
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

/*******************************************************
*  1. 분    류    명 : 정보검색 메인
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 사용자 환경설정
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/
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

	ArrayList arrList = null;

    userEnvMgr uem = new userEnvMgr();
    ParseRequest    pr = new ParseRequest(request);
    StringUtil      su = new StringUtil();


    String sRtnMsg  = "";


    //System.out.println( "pr.getString(JOB)      : " + pr.getString("JOB")     );
    if ( pr.getString("JOB").equals("SAVE") ) {

        String sK_Xp        = pr.getString("rdoKeyGroup");
        String sSt_interval_day    = pr.getString("sltPeriod");
        String sMd_type      = pr.getString("type");
        String sSg_seq      = pr.getString("sites");
        String sSg_seq_al      = pr.getString("solrSites");

        String sSt_reload_time   = pr.getString("sltRefresh","0");
        String sSt_list_cnt   = pr.getString("sltRowCnt");
        String sSt_menu      = pr.getString("rdoMenu");
        String sM_Seq       = SS_M_NO;
        
        if ( uem.saveUserEnv( sK_Xp       ,
                sSt_interval_day   ,
                sMd_type     ,
                sSg_seq     ,
                sSg_seq_al,
                sSt_reload_time  ,
                sSt_list_cnt  ,
                sSt_menu     ,
                sM_Seq )
)
        {
            //저장 성공이면 세션에있는 사용자 환경도 변경해준다.
            userEnvInfo ueiSession  = (userEnvInfo) session.getAttribute("ENV");

            ueiSession.setK_xp	     ( sK_Xp       );
            ueiSession.setSt_interval_day ( sSt_interval_day   );
            ueiSession.setMd_type    ( sMd_type     );
            ueiSession.setSg_seq	 ( sSg_seq     );
            ueiSession.setSg_seq_al	 ( sSg_seq_al     );
            ueiSession.setSt_reload_time( sSt_reload_time  );
            ueiSession.setSt_list_cnt ( sSt_list_cnt  );
            ueiSession.setSt_menu    ( sSt_menu     );

            session.removeAttribute("ENV");
            session.setAttribute("ENV",ueiSession);
            
            sRtnMsg = "top.bottomFrame.leftFrame.document.location.href=\"search_main_left.jsp\"; document.location.href = \"search_main_contents.jsp\";";
            out.print("<script language=\"JavaScript\" type=\"text/JavaScript\">"+sRtnMsg+"</script>");
        } else {
            sRtnMsg = "alert(\"저장 실패 하였습니다.\");";  
        }

    } else if ( pr.getString("JOB").equals("CURSAVE") ) {

        //저장 성공이면 세션에있는 사용자 환경도 변경해준다.
        userEnvInfo ueiSession  = (userEnvInfo) session.getAttribute("ENV");

        if ( uem.saveUserEnv( su.nvl(ueiSession.getK_xp(),"0")      ,
                              ueiSession.getSt_interval_day  ()            ,
                              ueiSession.getMd_type    ()            ,
                              su.nvl(ueiSession.getSg_seq(),"0" )   ,
                              su.nvl(ueiSession.getSg_seq_al(),"0" )   ,
                              ueiSession.getSt_reload_time ()            ,
                              ueiSession.getSt_list_cnt ()            ,
                              ueiSession.getSt_menu    ()            ,
                              SS_M_NO
                              )
           )
        {
            sRtnMsg = "alert(\"현재의 검색조건을 기본조건으로 저장하였습니다.\");";
        } else {
            sRtnMsg = "alert(\"저장 실패 하였습니다.\");";
        }
    }

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.

    userEnvInfo uei = null;

    uei = uem.getUserEnv( SS_M_ID );

    arrList = uem.getKeywordGroup( uei.getMg_xp() );

    String sType1       = "";
    String sType2       = "";
    String sType3       = "";
    String sTypeAll     = "";

    //Uiix의 파일권한과 동일한 개념으로 간다.
    //기사 1. 게시 2. 공지 4.
    if          ( uei.getMd_type().equals("1") ) {
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
        //sTypeAll= "checked";
        sType1  = "checked";
        sType2  = "checked";
        sType3  = "checked";
    }



    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    MetaMgr     smgr = new MetaMgr();
	
    System.out.println("uei.getMg_site():"+uei.getMg_site());
    
    ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );

    //그룹에 등록된 사이트 리스트
    ArrayList alSite = smgr.getSiteData(uei.getSg_seq());
    
    

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/base.css\" />\r\n");
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
      out.write("\tfunction MM_preloadImages() { //v3.0\r\n");
      out.write("      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();\r\n");
      out.write("        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)\r\n");
      out.write("        if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function checkAll() {\r\n");
      out.write("        var f = document.fSend;\r\n");
      out.write("\r\n");
      out.write("        /* if (f.chkAll.checked    == true ) {\r\n");
      out.write("            f.chkGisa.checked   = false;\r\n");
      out.write("            f.chkGesi.checked   = false;\r\n");
      out.write("            f.chkGongji.checked = false;\r\n");
      out.write("        } */\r\n");
      out.write("        \r\n");
      out.write("        if($(\"[name=chkAll]\").is(\":checked\")){\r\n");
      out.write("        \tf.chkGisa.checked   = true;\r\n");
      out.write("            f.chkGesi.checked   = true;\r\n");
      out.write("            f.chkGongji.checked = true;\r\n");
      out.write("        }else{\r\n");
      out.write("        \tf.chkGisa.checked   = false;\r\n");
      out.write("            f.chkGesi.checked   = false;\r\n");
      out.write("            f.chkGongji.checked = false;\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function checkEtc() {\r\n");
      out.write("\r\n");
      out.write("        var f = document.fSend;\r\n");
      out.write("        if ( f.chkGisa.checked == true &&\r\n");
      out.write("             f.chkGesi.checked == true &&\r\n");
      out.write("             f.chkGongji.checked == true )\r\n");
      out.write("        {\r\n");
      out.write("            f.chkAll.checked = true;\r\n");
      out.write("            f.chkGisa.checked   = false;\r\n");
      out.write("            f.chkGesi.checked   = false;\r\n");
      out.write("            f.chkGongji.checked = false;\r\n");
      out.write("\r\n");
      out.write("        } else {\r\n");
      out.write("            f.chkAll.checked = false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function save() {\r\n");
      out.write("\r\n");
      out.write("        f = document.fSend;\r\n");
      out.write("\r\n");
      out.write("        var typeValue   = 0;\r\n");
      out.write("\r\n");
      out.write("        if ( f.chkGisa.checked == false &&\r\n");
      out.write("             f.chkGesi.checked == false &&\r\n");
      out.write("             f.chkGongji.checked == false &&\r\n");
      out.write("             f.chkAll.checked == false  ) {\r\n");
      out.write("\r\n");
      out.write("             alert(\"정보유형을 선택하여 주십시오\");\r\n");
      out.write("             return;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        f.JOB.value = \"SAVE\";\r\n");
      out.write("\r\n");
      out.write("        if ( f.chkGisa.checked == true      ) typeValue += 1;\r\n");
      out.write("        if ( f.chkGesi.checked == true      ) typeValue += 2;\r\n");
      out.write("        if ( f.chkGongji.checked == true    ) typeValue += 4;\r\n");
      out.write("        if ( f.chkAll.checked == true    )    typeValue = 7;\r\n");
      out.write("\r\n");
      out.write("        f.type.value = typeValue;\r\n");
      out.write("\r\n");
      out.write("        /* 검색 대상 - 키워드 검색 */\r\n");
      out.write("        \r\n");
      out.write("        /* var group = document.all.chkSite;\r\n");
      out.write("        var strGroup = \"\";\r\n");
      out.write("        \t\t\t        \r\n");
      out.write("\t\tif(f.chkSiteAll.checked == true){\r\n");
      out.write("\t\t\tstrGroup = f.chkSiteAll.value;\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tstrGroup = \"\";\r\n");
      out.write("\t\t\tif(group){\r\n");
      out.write("\t\t\t\tfor(var i=0; i<group.length; i++){\r\n");
      out.write("\t\t\t\t\tif(group(i).checked){\r\n");
      out.write("\t\t\t\t\t\tstrGroup += \",\" + group(i).value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(strGroup.length > 0){\r\n");
      out.write("\t\t\t\tstrGroup = strGroup.substring(1);\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\talert(\"검색대상을 선택하여 주십시오\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\tvar strGroup = \"\";\r\n");
      out.write("\t\t$(\"[name=chkSite]\").each(function(){\r\n");
      out.write("\t\t\tif($(this).is(\":checked\")){\r\n");
      out.write("\t\t\t\tif(strGroup == \"\"){\r\n");
      out.write("\t\t\t\t\tstrGroup = $(this).val();\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tstrGroup += \",\" + $(this).val();\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tif(strGroup.length < 1){\r\n");
      out.write("\t\t\talert(\"검색대상을 선택하여 주십시오\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tf.sites.value = strGroup;\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t/* 검색 대상  - 전체 검색*/\r\n");
      out.write("\t\tgroup = null;\r\n");
      out.write("        group = document.all.chkSiteSolr;\r\n");
      out.write("        strGroup = \"\";\r\n");
      out.write("        \t\t\t        \r\n");
      out.write("\t\t/* if(f.chkSiteSolrAll.checked == true){\r\n");
      out.write("\t\t\tstrGroup = f.chkSiteSolrAll.value;\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tstrGroup = \"\";\r\n");
      out.write("\t\t\tif(group){\r\n");
      out.write("\t\t\t\tfor(i=0; i<group.length; i++){\r\n");
      out.write("\t\t\t\t\tif(group(i).checked){\r\n");
      out.write("\t\t\t\t\t\tstrGroup += \",\" + group(i).value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(strGroup.length > 0){\r\n");
      out.write("\t\t\t\tstrGroup = strGroup.substring(1);\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\talert(\"검색대상을 선택하여 주십시오\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} */\r\n");
      out.write("        /* $(\"[name=chkSiteSolr]\").each(function(){\r\n");
      out.write("\t\t\tif($(this).is(\":checked\")){\r\n");
      out.write("\t\t\t\tif(strGroup == \"\"){\r\n");
      out.write("\t\t\t\t\tstrGroup = $(this).val();\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tstrGroup += \",\" + $(this).val();\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tif(strGroup.length < 1){\r\n");
      out.write("\t\t\talert(\"검색대상을 선택하여 주십시오\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\tf.solrSites.value = strGroup;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("        \r\n");
      out.write("\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function cancel() {\r\n");
      out.write("        document.location.href = \"search_main_contents.jsp\";\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function siteCheckAll() {\r\n");
      out.write("    \t/* \r\n");
      out.write("    \tvar group = document.all.chkSite;\r\n");
      out.write("\t\tif(group != null && group.length > 0){\r\n");
      out.write("\t    \tfor(var i = 0; i < group.length; i++){\r\n");
      out.write("\t    \t\tgroup(i).checked = false;\r\n");
      out.write("\t    \t} \r\n");
      out.write("\t\t} \r\n");
      out.write("\t\t*/\r\n");
      out.write("\t\tif($(\"[name=chkSiteAll]\").is(\":checked\")){\r\n");
      out.write("\t\t\t$(\"[name=chkSite]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr('checked' , true);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=chkSite]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr('checked' , false);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("    }\r\n");
      out.write("    function siteSolrCheckAll() {\r\n");
      out.write("    \t/* var group = document.all.chkSiteSolr;\r\n");
      out.write("\r\n");
      out.write("\t\tif(group != null && group.length > 0){\r\n");
      out.write("\t    \tfor(var i = 0; i < group.length; i++){\r\n");
      out.write("\t    \t\tgroup(i).checked = false;\r\n");
      out.write("\t    \t} \r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\t\r\n");
      out.write("    \tif($(\"[name=chkSiteSolrAll]\").is(\":checked\")){\r\n");
      out.write("\t\t\t$(\"[name=chkSiteSolr]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr('checked' , true);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=chkSiteSolr]\").each(function(){\r\n");
      out.write("\t\t\t\t$(this).attr('checked' , false);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("    \t        \r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    function siteCheck() {        \r\n");
      out.write("    \tvar f = document.fSend;\r\n");
      out.write("\t\tvar group = document.all.chkSite;\r\n");
      out.write("\t\tvar chkCount = 0;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\tfor(var i = 0; i < group.length; i++){\r\n");
      out.write("\t\t\tif(group(i).checked == true){\r\n");
      out.write("\t\t\t\tchkCount++;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif(group.length <= chkCount){\r\n");
      out.write("\t\t\tfor(var j = 0; j < group.length; j++){\r\n");
      out.write("\t    \t\tgroup(j).checked = false;\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t\t\tf.chkSiteAll.checked = true;    \t \r\n");
      out.write("\t\t}else{\r\n");
      out.write("    \t\tf.chkSiteAll.checked = false;\r\n");
      out.write("\t\t}        \r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    function siteSolrCheck() {        \r\n");
      out.write("    \tvar f = document.fSend;\r\n");
      out.write("\t\tvar group = document.all.chkSiteSolr;\r\n");
      out.write("\t\tvar chkCount = 0;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\tfor(var i = 0; i < group.length; i++){\r\n");
      out.write("\t\t\tif(group(i).checked == true){\r\n");
      out.write("\t\t\t\tchkCount++;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif(group.length <= chkCount){\r\n");
      out.write("\t\t\tfor(var j = 0; j < group.length; j++){\r\n");
      out.write("\t    \t\tgroup(j).checked = false;\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t\t\tf.chkSiteSolrAll.checked = true;    \t \r\n");
      out.write("\t\t}else{\r\n");
      out.write("    \t\tf.chkSiteSolrAll.checked = false;\r\n");
      out.write("\t\t}        \r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    function showMsg() {\r\n");
      out.write("\r\n");
      out.write("        if ( '");
      out.print(sRtnMsg);
      out.write("' != '' ) {\r\n");
      out.write("        \t");
      out.print(sRtnMsg);
      out.write("\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<body onLoad=\"javascript:showMsg();\" >\r\n");
      out.write("<form name=\"fSend\" action=\"search_env_setting.jsp\" method=\"post\" >\r\n");
      out.write("    <input type=\"hidden\" name=\"type\" value=\"\" >\r\n");
      out.write("    <input type=\"hidden\" name=\"JOB\"  value=\"\" >\r\n");
      out.write("    <input type=\"hidden\" name=\"sites\" value=\"\">\r\n");
      out.write("    <input type=\"hidden\" name=\"solrSites\" value=\"\">     \r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:67px;padding-top:20px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../images/search/tit_icon.gif\" /><img src=\"../../images/search/tit_0001.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">환경설정</td>\r\n");
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
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<table width=\"770\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          \r\n");
      out.write("          <td align=\"right\" style=\"padding-left:10px\"><table width=\"750\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td height=\"40\" align=\"left\"> 주로 사용하시는 검색조건을 저장해 두시면 , 로그인 하실 때마다 기본설정으로 적용됩니다 . &#13;&#13;</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"25\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>키워드그룹</strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" valign=\"top\" style=\"padding-left:15px\" >\r\n");
      out.write("                  <table width=\"530\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td style=\"padding: 5px 5px 5px 5px;\" style=\"border:1px solid; border-color:#cccccc\" >\r\n");
      out.write("                        <table width=\"500\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td width=\"15\" height=\"20\"><img src=\"../../images/search/search_folder.gif\" width=\"12\" height=\"10\" align=\"absmiddle\"></td>\r\n");
      out.write("                            <td width=\"15\"><input type=\"radio\" name=\"rdoKeyGroup\" value=\"0\" checked ></td>\r\n");
      out.write("                            <td style=\"padding: 2px 0px 0px 0px;\">전체키워드 그룹</td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                          ");

                            for (int i = 0; i < arrList.size() ; i ++ ) {
                                keywordInfo kwi = (keywordInfo)arrList.get(i);
                          
      out.write("\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td width=\"15\" height=\"20\"><img src=\"../../images/search/search_folder.gif\" width=\"12\" height=\"10\" align=\"absmiddle\"></td>\r\n");
      out.write("                            <td width=\"15\">\r\n");
      out.write("                              <input type=\"radio\" name=\"rdoKeyGroup\" value=\"");
      out.print(kwi.getK_Xp());
      out.write('"');
      out.write(' ');
 if ( kwi.getK_Xp().equals( uei.getK_xp() )  ) out.print("checked");  
      out.write("  >\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td style=\"padding: 2px 0px 0px 0px;\">");
      out.print(kwi.getK_Value());
      out.write("</td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                          ");

                            }
                          
      out.write("\r\n");
      out.write("                        </table>\r\n");
      out.write("                      </td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                  </table>\r\n");
      out.write("                <!--\r\n");
      out.write("                <select name=\"slt_grouplist\">\r\n");
      out.write("                <option value=\"\"></option>\r\n");
      out.write("                </select>\r\n");
      out.write("                -->\r\n");
      out.write("                <!--\r\n");
      out.write("                <iframe src=\"ifram_search02.htm\" width=\"550\" height=\"90\" scrolling=\"auto\" frameborder=\"0\" style=\"border:1px solid; border-color:#cccccc\"></iframe>\r\n");
      out.write("                -->\r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write(" \t\t\t <tr>\r\n");
      out.write("\t\t\t    <td colspan=\"3\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"10\"></td>\r\n");
      out.write(" \t\t\t </tr>\r\n");
      out.write(" \t\t\t <tr>\r\n");
      out.write("\t\t\t    <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write(" \t\t\t </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"25\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>검색기간</strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" valign=\"top\" style=\"padding-left:15px\"> 최근\r\n");
      out.write("                ");
 System.out.println("uei.getSt_interval_day() : "  + uei.getSt_interval_day() ); 
      out.write("\r\n");
      out.write("                  <select name=\"sltPeriod\" class=\"menu_gray\">\r\n");
      out.write("                    <option value=\"1\" ");
 if ( uei.getSt_interval_day().equals("1") ) out.print("selected"); 
      out.write(" >1일</option>\r\n");
      out.write("                    <option value=\"3\" ");
 if ( uei.getSt_interval_day().equals("3") ) out.print("selected"); 
      out.write(" >3일</option>\r\n");
      out.write("                    <option value=\"7\" ");
 if ( uei.getSt_interval_day().equals("7") ) out.print("selected"); 
      out.write(" >7일</option>\r\n");
      out.write("                    </select>\r\n");
      out.write("                    일간의 정보 중에서 검색합니다 .</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"10\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"25\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>정보유형</strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" valign=\"top\" style=\"padding-left:15px\">\r\n");
      out.write("                <input type=\"checkbox\" name=\"chkAll\" value=\"checkbox\" OnClick=\"javascript:checkAll();\" ");
      out.print(sTypeAll);
      out.write("  >\r\n");
      out.write("                <strong>전체</strong>\r\n");
      out.write("                <input type=\"checkbox\" name=\"chkGisa\" value=\"checkbox\" OnClick=\"javascript:checkEtc();\" ");
      out.print(sType1);
      out.write("  >\r\n");
      out.write("                \t기사\r\n");
      out.write("                <input type=\"checkbox\" name=\"chkGesi\" value=\"checkbox\" OnClick=\"javascript:checkEtc();\" ");
      out.print(sType2);
      out.write(" >\r\n");
      out.write("                \t게시\r\n");
      out.write("                <input type=\"checkbox\" name=\"chkGongji\" value=\"checkbox\" OnClick=\"javascript:checkEtc();\" ");
      out.print(sType3);
      out.write(" >\r\n");
      out.write("                \t공지</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"10\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            \r\n");
      out.write("            \r\n");
      out.write("            \r\n");
      out.write("\t\t\r\n");
      out.write("            ");
      out.write(" \r\n");
      out.write("            \r\n");
      out.write("            \r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"25\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>검색대상 - 사이트검색</strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" valign=\"top\" style=\"padding-left:15px\">\r\n");
      out.write("                \r\n");
      out.write("             \r\n");
      out.write("              \t");

              	String[] arrSgSeq = uei.getSg_seq().split(",");
              	String allChecked = "";
              	
              	if(arrSgSeq.length == alGroup.size()){
              		allChecked = "checked";
              	}
              	
        		String allValue="";
              	for(int i = 0; i < alGroup.size(); i++){
              		sgi = (siteGroupInfo)alGroup.get(i);
              		allValue += ","+ Integer.toString(sgi.getSg_seq());
              	}
              	if(allValue.length() > 0){
              		allValue = allValue.substring(1);
              	}
              	
      out.write("\r\n");
      out.write("              \t");
      out.write("\r\n");
      out.write("              \t<input type=\"checkbox\" name=\"chkSiteAll\" onclick=\"siteCheckAll();\" ><strong>전체</strong>\r\n");
      out.write("              \t");

              	
          		for(int i = 0; i < alGroup.size(); i++){
          			String sSelected = "";
                    sgi = (siteGroupInfo)alGroup.get(i);
                    
                    //if(!allChecked.equals("checked")){
	                    for(int j= 0; j < arrSgSeq.length; j++){
	                    	if(Integer.toString(sgi.getSg_seq()).equals(arrSgSeq[j])){
	                    		sSelected = "checked";
	                    	}
	                    }
                    //}
              	
      out.write("\r\n");
      out.write("           \t\t<input type=\"checkbox\" name=\"chkSite\" value=\"");
      out.print(sgi.getSg_seq());
      out.write('"');
      out.write(' ');
      out.print(sSelected);
      out.write('>');
      out.print(sgi.getSd_name());
      out.write("\r\n");
      out.write("           \t\t");
 } 
      out.write("\r\n");
      out.write("                  \r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"10\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"25\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>자동 새로고침</strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td align=\"left\" valign=\"top\" style=\"padding-left:15px\">\r\n");
      out.write("                  <select name=\"sltRefresh\" class=\"menu_gray\">\r\n");
      out.write("                    <option value=\"0\" >사용안함</option>\r\n");
      out.write("                    <option value=\"5\"  ");
 if ( uei.getSt_reload_time().equals("5" ) ) out.print("selected"); 
      out.write(" >5분</option>\r\n");
      out.write("                    <option value=\"10\" ");
 if ( uei.getSt_reload_time().equals("10") ) out.print("selected"); 
      out.write(" >10분</option>\r\n");
      out.write("                    <option value=\"20\" ");
 if ( uei.getSt_reload_time().equals("20") ) out.print("selected"); 
      out.write(" >20분</option>\r\n");
      out.write("                    <option value=\"30\" ");
 if ( uei.getSt_reload_time().equals("30") ) out.print("selected"); 
      out.write(" >30분</option>\r\n");
      out.write("                  </select>\r\n");
      out.write("                  <!--\r\n");
      out.write("                  <img src=\"../../images/search/search_reset.gif\" width=\"59\" height=\"18\" align=\"absmiddle\">\r\n");
      out.write("                  -->\r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"10\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"32\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>화면에 출력할 기사 수 :\r\n");
      out.write("                    <select name=\"sltRowCnt\" class=\"menu_gray\">\r\n");
      out.write("                    <option ");
 if ( uei.getSt_list_cnt().equals("10") ) out.print("selected"); 
      out.write(" >10</option>\r\n");
      out.write("                    <option ");
 if ( uei.getSt_list_cnt().equals("20") ) out.print("selected"); 
      out.write(" >20</option>\r\n");
      out.write("                    <option ");
 if ( uei.getSt_list_cnt().equals("30") ) out.print("selected"); 
      out.write(" >30</option>\r\n");
      out.write("                    <option ");
 if ( uei.getSt_list_cnt().equals("50") ) out.print("selected"); 
      out.write(" >50</option>\r\n");
      out.write("                    <option ");
 if ( uei.getSt_list_cnt().equals("100") ) out.print("selected"); 
      out.write(" >100</option>\r\n");
      out.write("                  </select>\r\n");
      out.write("                </strong></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" background=\"../../images/search/pop_mailt_dotbg.gif\"><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"32\" align=\"left\" class=\"menu_black\" ><img src=\"../../images/search/search_dot01.gif\" width=\"9\" height=\"9\" hspace=\"2\"><strong>결과내 검색 메뉴 보이기</strong> :\r\n");
      out.write("                  <input name=\"rdoMenu\" type=\"radio\" value=\"0\" ");
if ( uei.getSt_menu().equals("0") ) out.print("checked"); 
      out.write("><strong>보이지 않기</strong>\r\n");
      out.write("                  <input name=\"rdoMenu\" type=\"radio\" value=\"1\" ");
if ( uei.getSt_menu().equals("1") ) out.print("checked"); 
      out.write("><strong>보이기</strong>\r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td colspan=\"3\" bgcolor=\"#DDDDDD\" ><img src=\"../../images/search/brank.gif\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            <table width=\"750\" height=\"30\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"50\" align=\"center\">\r\n");
      out.write("                  <a href=\"javascript:save();\" ><img src=\"../../images/search/btn_save_2.gif\" width=\"55\" height=\"24\" hspace=\"5\" border=\"0\" ></a>\r\n");
      out.write("                  <a href=\"javascript:cancel();\" ><img src=\"../../images/search/btn_cancel.gif\" width=\"55\" height=\"24\" border=\"0\" ></a>\r\n");
      out.write("                </td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table></td>\r\n");
      out.write("          \r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\t\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
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
