<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaBean
                 ,risk.search.MetaMgr
                 ,risk.search.siteGroupInfo
                 ,risk.search.siteDataInfo
                 ,risk.search.userEnvMgr
                 ,risk.search.userEnvInfo"
%>
<%@page import="risk.issue.IssueCodeMgr"%>
<%@page import="risk.issue.IssueCodeBean"%>
<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.issue.IssueBean"%>
<%@page import="risk.issue.IssueTitleBean"%>
<%@page import="risk.util.PageIndex"%>

<%@include file="../inc/sessioncheck.jsp" %>
<%
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

        if ( Integer.parseInt(uei.getSt_interval_day()) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
            uei.setDateFrom( sCurrDate  ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        }
        
        uei.setStime("0");
        uei.setEtime("23");
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
    
    uei.setDateFrom( pr.getString("sDateFrom",uei.getDateFrom() ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,uei.getDateTo()   ) ) ;
    uei.setStime( pr.getString("stime"  ,uei.getStime() ) ) ;
    uei.setEtime( pr.getString("etime"  ,uei.getEtime() ) ) ;

    if ( uei.getDateFrom()== null ) {

        if ( Integer.parseInt( uei.getSt_interval_day() ) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
            uei.setDateFrom( sCurrDate  ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        }
        
        uei.setStime("0");
        uei.setEtime("23");
    }
    
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
    uei.setSearchMode(  pr.getString("searchmode", su.nvl(uei.getSearchMode(),"EX_ALLKEY") ) );
    
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
	
    
    
    ArrayList alData = null;
    
	if ( uei.getSearchMode().equals("EX_ALLKEY") ) {
		colspan = "colspan=\"11\"";
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
		                                       "EXCEPTION_");
				
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
		                                       "EXCEPTION_");
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
                "EXCEPTION_");
    	
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
%>



<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}

</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/timer.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/input_date.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
    var selecedOrder = "<%=uei.getOrder()%>";
    var click = "";
    var sCurrDate = '<%=sCurrDate%>';

    /**
    * 검색 SUBMIT
    */
    function doSubmit(){
    	var f = document.getElementById('fSend');

        var typeValue   = 0;

        if(f.chkGisa.checked == false && f.chkGesi.checked == false && f.chkGongji.checked == false && f.chkAll.checked == false){
			f.chkAll.checked = true;
        }

        if(f.chkGisa.checked == true)typeValue += 1;
        if(f.chkGesi.checked == true)typeValue += 2;
        if(f.chkGongji.checked == true)typeValue += 4;
        if(f.chkAll.checked == true)typeValue = 7;

        f.type.value = typeValue;
    	
		if((f.sOrder.value == selecedOrder) && (click == "TRUE")){
			if(f.sOrderAlign.value == "ASC"){
				f.sOrderAlign.value = "DESC";
			}else{
				f.sOrderAlign.value = "ASC";
			}
		}else{
			f.sOrder.value = selecedOrder;
		}
		f.sg_seq.value = chkSiteGroup();
		f.submit();
    }


    /**
    * 정보유형
    */
    function checkAll() {
    	var f = document.getElementById('fSend');

        if(f.chkAll.checked == true){
			f.chkGisa.checked = false;
			f.chkGesi.checked = false;
			f.chkGongji.checked = false;
        }
		doSubmit();
    }
    function checkEtc() {
    	var f = document.getElementById('fSend');
    	
        if(f.chkGisa.checked == true && f.chkGesi.checked == true && f.chkGongji.checked == true){
            f.chkAll.checked = true;
            f.chkGisa.checked = false;
            f.chkGesi.checked = false;
            f.chkGongji.checked = false;
		}else{
            f.chkAll.checked = false;
        }
		doSubmit();
    }


    /**
    * 기사 삭제 관련
    */
    function idxProcess(mode) {
    	var f = document.getElementById('fSend');
		var typeValue   = 0;

        frm = document.getElementById('fCheck');
        var selectedList = "";
        
        var first = 0;
        
        if(mode == 'truncate'){
        	 if ( !confirm("선택된 목록을 삭제 하시겠습니까?" ) ) {
		            return;
		     }        
        }else if(mode=='revert'){            		        
			if ( !confirm("선택된 목록을 복원 하시겠습니까?" ) ) {
	            return;
	        }		       
		}else if(mode=='del'){
			 if ( !confirm("선택된 목록을 영구 삭제 하시겠습니까?" ) ) {
		            return;
		     }			
		}

        if(frm.chkData){
			if(frm.chkData.length>1){					
			 	for(var i=0; i<frm.chkData.length; i++)
			 	{
				 	if(frm.chkData[i].checked){
			 			selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
				 	}
			 	}
			}else{
				selectedList = frm.chkData.value;
			}
		}		
        
        f.SaveList.value = selectedList;

        f.action = "idx_prc.jsp?idxMode="+mode+"&st_name=EXCEPTION_";
        f.target = 'if_samelist';
        f.submit();
		f.action = 'search_main_exception.jsp';
		f.target = '';
    }


    /**
     * 기사 삭제 관련
     */
     function idxProcess(mode) {
     	var f = document.getElementById('fSend');
 		var typeValue   = 0;

         frm = document.getElementById('fCheck');
         var selectedList = "";
         
         var first = 0;
         
         if(mode == 'truncate'){
         	 if ( !confirm("선택된 목록을 삭제 하시겠습니까?" ) ) {
 		            return;
 		     }        
         }else if(mode=='revert'){            		        
 			if ( !confirm("선택된 목록을 복원 하시겠습니까?" ) ) {
 	            return;
 	        }		       
 		}else if(mode=='del'){
 			 if ( !confirm("선택된 목록을 영구 삭제 하시겠습니까?" ) ) {
 		            return;
 		     }			
 		}

         if(frm.chkData){
 			if(frm.chkData.length>1){					
 			 	for(var i=0; i<frm.chkData.length; i++)
 			 	{
 				 	if(frm.chkData[i].checked){
 			 			selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
 				 	}
 			 	}
 			}else{
 				selectedList = frm.chkData.value;
 			}
 		}		
         
         f.SaveList.value = selectedList;

         f.action = "idx_prc.jsp?idxMode="+mode+"&st_name=EXCEPTION_";
         f.target = 'if_samelist';
         f.submit();
 		f.action = 'search_main_exception.jsp';
 		f.target = '';
     }


     /**
      * 기사 삭제 관련
      */
      function exceptionRestore(md_seq) {
      	var f = document.getElementById('fSend');

      	f.mode.value = 'insert';
        f.action = "restore_prc.jsp?md_seq="+ md_seq;
        f.target = 'if_samelist';
        f.submit();
      }
     
    

    /**
    * 페이지처리
    */
    function pageClick(paramUrl){
    	var f = document.getElementById('fSend');
        f.action = "search_main_exception.jsp" + paramUrl;
        doSubmit();
    }

    /**
    * 체크된 사이트 코드 세팅
    */
	function chkSiteGroup(){
		var f = document.getElementById('fSend');
		var seqs = '';
		var obj = f.sltSiteGroup;
		for(var i =0; i<obj.length; i++){
			if(obj[i].checked){
				seqs += seqs == '' ?  obj[i].value : ',' + obj[i].value;
			}
		}
		return seqs;
	}
    
    /**
    * 사이트로 정렬
    */
    function setSiteData(sg_seq, sd_gsn){
    	var f = document.getElementById('fSend');
        f.sg_seq.value = sg_seq;
        f.sd_gsn.value = sd_gsn;
        doSubmit();
    }

    /**
    * 휴지통 삭제자 정렬
    */
    function setDelname(delname){
    	var f = document.getElementById('fSend');
        f.del_mname.value = delname;
        doSubmit();
    }

    /**
    * 검색
    */
    function doSearch(){
        document.fSend.nowpage.value = '1';
        doSubmit();
    }

    /**
    * 헤드라인 이벤트
    */
    function show_me(i){
		div_show.innerHTML = eval('div'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
    }
    function close_me(i){
    	div_show.style.display='none';
    }

    /**
    * 유사기사 헤드라인 이벤트
    */
    function showSamelist(i){
		div_show.innerHTML = eval('sameContent'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
    }
    function closeSamelist(i){
    	div_show.style.display='none';
    }

    /**
    * 검색기간
    */
    function setDateTerm( day ) {
        var f = document.getElementById('fSend');
        f.sDateFrom.value   = getdate( day );
        f.sDateTo.value     = sCurrDate;
        f.Period.value      = day + 1;
    }
	function getdate(day){
		var newdate = new Date();
		var resultDateTime;
		var tempDateTime = 0;
		
		if(tempDateTime==0)
		{
			tempDateTime = newdate.getTime();
		}  
              
		resultDateTime = tempDateTime - ( day * 24 * 60 * 60 * 1000);
        newdate.setTime(resultDateTime);

        var year;
        var month;
        var day;
		var result_date;
		year = newdate.getFullYear();
		month = newdate.getMonth()+1;
		day = newdate.getDate();
		if(month < 10){
			month = '0'+month;
		}
		if(day < 10){
			day = '0'+day;
		}
        result_date = year + '-' + month + '-' + day;
        return result_date;
	}

    /**
    * 필드정렬
    */
    function setOrder(order){
        selecedOrder = order;
        click = "TRUE";
        doSubmit();
    }

    /**
    * 기사 전체 체크
    */
    function reverseAll( chked ) {
        var frm = document.getElementById('fCheck');
        var first = 0;
        for (var i = 0; i < frm.elements.length; i++) {
            var e = frm.elements[i];
            if ( e.type == "checkbox" ) {
                e.checked = chked;
            }
        }
    }

    /**
    * 유사기사 리스트 보기
    */
    var pre_SameList = '';
    function openSameList( md_pseq, md_seq,searchMode){
        var sameLayer = document.getElementById('SameList_' + md_pseq);

    	if(pre_SameList == sameLayer && pre_SameList.style.display==''){
    		pre_SameList.style.display = 'none';
    		return;
    	}

    	if(pre_SameList){
    		pre_SameList.style.display = 'none';
    	}

    	pre_SameList = sameLayer;
    	sameLayer.innerHTML = '로딩중...';
    	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq + "&md_seq=" + md_seq + "&searchMode=" + searchMode;
    	sameLayer.style.display = '';
    }
    function fillSameList(no){
    	var ly = document.getElementById('SameList_'+no);    	
	    ly.innerHTML = if_samelist.zzFilter.innerHTML;
    }
	
	
	/**
	* URL열기
	*/
	var chkPop = 1;
	function hrefPop(url, m_seq, md_seq){
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');
		chkPop++;

		
		// 열어본 페이지는 DB저장 및 색상변경
		send_comfirm.mode.value = 'insert';
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.md_seq.value = md_seq;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();	
	}

	function listClear(m_seq){
		var f = document.getElementById('fCheck');

		var selectedMdSeq = '';

		if(f.chkData){
			if(f.chkData.length){					
			 	for(var i=0; i<f.chkData.length; i++)
			 	{
				 	if(f.chkData[i].checked){
				 		if(selectedMdSeq == ''){
				 			selectedMdSeq = f.chkData[i].value;
				 		}else{
				 			selectedMdSeq += "," +  f.chkData[i].value;
				 		}
				 	}
			 	}
			}else{
				selectedMdSeq = f.chkData.value;
			}
		}
	
		if(selectedMdSeq == ''){
			alert("선택된 기사가 없습니다.");
			return;
		}
		
		send_comfirm.md_seqs.value = selectedMdSeq;
		send_comfirm.mode.value = 'delete';	
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();
	}
	
    /**
	* 이슈 타이틀 변경
	*/
    function changeIssueTitle(){
		ajax.post('selectbox_issue_title.jsp','send_mb','td_it_title');
	}

	
	/**
	* 북마크 저장 관련
	*/
	function saveBookMark(){
		var count = 0;
		var obj = document.getElementById('fCheck');
		for (var i = 0; i <obj.length; i++) {
			if(obj[i].checked){send_bookmark.bookMarkSeq.value = obj[i].value; count++;}        
  	    }
  	    if(count>1){alert('하나 이상 선택하셨습니다.'); return;}
		if(send_bookmark.bookMarkSeq.value=='') {alert('정보를 선택을 해주세요.'); return;}  	    
		send_bookmark.target = 'proceeFrame';
		send_bookmark.action = 'search_bookmark_prc.jsp';
		send_bookmark.submit();			  	
	}

	/**
	* 북마크 검색 관련
	*/
    function doBookMarkSearch() {
    	var f = document.getElementById('fSend');
        f.bookMarkYn.value = 'Y';
        doSubmit();
    }

	/**
	* 검색유형 전체 체크
	*/	
	function chkSiteGroupAll(chk){
		var group = document.getElementsByName('sltSiteGroup');
		if(group){
			if(group.length){
				for(var i = 0; i < group.length; i++){
					group[i].checked = chk;
				}
			}
		} 
	}


	function timerStart(){
		var f = document.getElementById('fSend');
		if(f.timer.value == 'Y'){
			setTimer(<%=uei.getSt_reload_time()%>);
		}
	}
	window.onload = timerStart;

	function actionLayer(obj){
		if(obj.src.indexOf("close") > -1){

			/*
			for(var i = 1; i < 9; i++){
				document.getElementById('hide'+i).style.display = 'none';
			}
			*/
			document.getElementById('hide1').style.display = 'none';
			document.getElementById('hide2').style.display = 'none';
			obj.src = '../../images/search/btn_searchbox_open.gif';
			document.getElementById('fSend').searchtype.value = "0";
		}else{
			/*
			for(var i = 1; i < 9; i++){
				document.getElementById('hide'+i).style.display = '';
			}
			*/
			document.getElementById('hide1').style.display = '';
			document.getElementById('hide2').style.display = '';
			obj.src = '../../images/search/btn_searchbox_close.gif';
			document.getElementById('fSend').searchtype.value = "1";
		}
	}

	function goExcelTo(){

 		var f = document.fSend;
 		
		f.action = 'search_download_excel.jsp';
		f.target = 'proceeFrame';
		f.submit();
		//f.action = 'issue_list.jsp';
		//f.target = 'proceeFrame';
 	}

	function listAlter(md_seq, md_pseq){

		var f = document.fSend;

		f.action = "alterList.jsp?md_seq="+md_seq+ "&md_pseq=" + md_pseq +"&nowpage=" + f.nowpage.value;
        f.target = 'if_samelist';
        f.submit();
		
	}

	
	function originalView(md_seq, s_seq, md_title){
		
		var url = '';
		url = "originalView.jsp?md_seq="+md_seq+"&st_name=EXCEPTION_";	
		window.open(url, "originalPop", "width=708, height=672, scrollbars=yes");		
	}

	var chkOriginal = 1;
	function portalSearch(s_seq, md_title){
		if(s_seq == '3555'){
			//네이버까페
			url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + md_title;
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}else if(s_seq == '4943'){
			//다음까페
			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}

		chkOriginal ++;
	}

	function sendMail(){
		var count = 0;
		var obj = document.getElementById('fCheck');
		for (var i = 0; i <obj.length; i++) {
			if(obj[i].checked){send_mail.md_seqs.value = obj[i].value; count++;}        
  	    }
  	    if(count>1){alert('하나 이상 선택할수 없습니다.'); return;}
		if(send_mail.md_seqs.value=='') {alert('정보를 선택을 해주세요.'); return;}  	    
		send_mail.target = 'proceeFrame';
		send_mail.action = 'pop_mail_send.jsp';
		send_mail.submit();
	}

	 //멀티복원하기  
 	function exceptionRestore_multi() {
 		var f = document.getElementById('fSend');

 		var frm = document.getElementById('fCheck');
 		var selectedList = "";
 		if(frm.chkData){
 			if(frm.chkData.length>1){					
 			 	for(var i=0; i<frm.chkData.length; i++)
 			 	{
 				 	if(frm.chkData[i].checked){
 			 			selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
 				 	}
 			 	}
 			}else{
 				selectedList = frm.chkData.value;
 			}
 		}

 		if(selectedList == ''){
 			alert("선택된 기사가 없습니다.");
 			return;
 		}
 		
 		f.mode.value = 'multi';
 		f.action = "restore_prc.jsp?md_seqs="+ selectedList;
 		f.target = 'if_samelist';
 		f.submit();
 		
 	}
	
//-->

</script>
</head>
<body>
<!--헤드라인 레이어-->
<div id=div_show style="font-size: 12px; padding-right: 5px; display: none; padding-left: 5px; left: 140px; width:500px; height:100px; padding-bottom: 5px; padding-top: 5px; position: absolute; background-color: #ffffcc; border: #0000CC 2px solid;"></div>

<!--북마크 정보 FORM-->
<form name="send_bookmark" id="send_bookmark" method="post">
	<input name="bookMarkContents" type="hidden" value="search">
	<input name="bookMarkSeq" type="hidden" value="">
</form>

<!--열어본 페이지 색상변경 및  DB저장-->
<form name="send_comfirm" id="send_comfirm" method="post">
	<input name="m_seq" type="hidden">
	<input name="md_seq" type="hidden">
	<input name="md_seqs" type="hidden">
	<input name="mode" type="hidden">
</form>

<!--메일발송-->
<form name="send_mail" id="send_mail" method="post">
	<input name="md_seqs" type="hidden">
</form>

<form name="send_mb" id="send_mb" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="md_seq">
<input type="hidden" name="md_seqs">
</form>

<table style="height:100%; width:850px;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/tit_icon.gif" /><img src="../../images/search/tit_1126.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">전체키워드검색</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<form name="fSend" id="fSend" action="search_main_exception.jsp" method="post"  style="margin-bottom: 0px">
					<input type="hidden" name="xp" value="<%=uei.getK_xp()%>">
					<input type="hidden" name="yp" value="<%=uei.getK_yp()%>">
					<input type="hidden" name="zp" value="<%=uei.getK_zp()%>">
					<input type="hidden" name="timer" value="<%=sTimer%>" >
					<input type="hidden" name="interval" value="<%=uei.getSt_reload_time()%>" >
					<input type="hidden" name="type" value="<%=uei.getMd_type()%>">
					<input type="hidden" name="searchtype" value="<%=uei.getSt_menu()%>">
					<input type="hidden" name="sOrder" value="<%=uei.getOrder()%>">
					<input type="hidden" name="sOrderAlign" value="<%=uei.getOrderAlign()%>">
					<input type="hidden" name="sg_seq" value="<%=uei.getSg_seq()%>">
					<input type="hidden" name="sd_gsn" value="<%=uei.getS_seq()%>">
					<input type="hidden" name="searchmode" value="<%=uei.getSearchMode()%>">
					<input type="hidden" name="Period" value="<%=uei.getSt_reload_time()%>">
					<input name="nowpage" type="hidden" value="<%=iNowPage%>">          
					<input name="del_mname" type="hidden" value="<%=uei.getDelName()%>">
					<input type="hidden" name="SaveList">
					<input name="bookMarkYn" type="hidden">
					<input type="hidden" name="mode">

				
				
				
				
				<table width="820" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../images/search/table_bg_01.gif" width="15" height="35" /></td>
				        <td width="793" background="../../images/search/table_bg_02.gif"><table width="700" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="16"><img src="../../images/search/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="63" class="b_text"><strong>검색단어 </strong></td>
				            <td width="621"><input name="sKeyword" type="text" class="textbox3" value="<%=uei.getKeyword()%>" style="width:400px;" onkeydown="javascript:if(event.keyCode == 13){doSearch();}"/>
				            
				              <img src="../../images/search/search_btn.gif" align="absmiddle" onclick="doSearch();"  style="cursor: pointer;"/></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../images/search/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr id="hide1" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="#93a6b4"></td>
				        <td width="818" bgcolor="#e6eaed" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif"  /> <strong>검색기간</strong></td>
				                <td><table width="550" border="0" cellpadding="0" cellspacing="0">
				                  <tr>
				                    <td width="95"><input style="width:90px;text-align:center;" class="textbox" type="text" name="sDateFrom" id="sDateFrom" value="<%=uei.getDateFrom()%>"></td>
				                    <td width="30"><img src="../../images/search/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                    <td width="55"><select name="stime"><%for(int i=0; i<24; i++){ if(uei.getStime() != null && !uei.getStime().equals("") && i==Integer.parseInt(uei.getStime())){out.print("<option value="+ i +" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select> </td>
				                    <td>~</td>
				                    <td width="95"><input style="width:90px;text-align:center;" class="textbox" type="text" name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>"></td>
				                    <td width="30"><img src="../../images/search/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                    <td width="55"><select name="etime"><%for(int i=0; i<24; i++){ if(uei.getEtime() != null && !uei.getEtime().equals("") && i==Integer.parseInt(uei.getEtime())){out.print("<option value="+ i +" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
				                    <td><img src="../../images/search/btn_calendar_1day.gif" onclick="setDateTerm(0);" style="cursor:pointer;padding-left:10px;"/><img src="../../images/search/btn_calendar_3day.gif" onclick="setDateTerm(2);" style="cursor:pointer;padding-left:5px;"/><img src="../../images/search/btn_calendar_7day.gif" onclick="setDateTerm(6);" style="cursor:pointer;padding-left:5px;"/></td>
				                  </tr>
				                </table></td>
				                </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif"  /> <strong>정보유형</strong></td>
				                <td style="color:#2f5065;"><input name="chkAll" type="checkbox" onclick="javascript:checkAll();" <%=sTypeAll%>/><strong>전체</strong>&nbsp;
				                  <input name="chkGisa" type="checkbox" onclick="javascript:checkEtc();" <%=sType1%>/><img src="../../images/search/con_icon01.gif"  align="absmiddle" /> 기사&nbsp;
				                  <input name="chkGesi"  type="checkbox" onclick="javascript:checkEtc();" <%=sType2%>/><img src="../../images/search/con_icon02.gif"  align="absmiddle" /> 게시&nbsp;
				                  <input name="chkGongji" type="checkbox" onclick="javascript:checkEtc();" <%=sType3%>/><img src="../../images/search/con_icon03.gif"  align="absmiddle" /> 공지</td>
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif"  /> <strong>검색유형</strong></td>
				                <td><table width="500" style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
												<%
								String[] arrSelected = null;
								if(!uei.getSg_seq().equals("")){
									arrSelected = uei.getSg_seq().split(",");
								}
								%>
													<div style="width:100px;float:left"><input type="checkbox" name="sltSiteGroupAll" value="0" onclick="chkSiteGroupAll(this.checked);" <%if(alGroup.size() == arrSelected.length){out.print("checked");}%>><strong>전체</strong></div>
													<%
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
								%> 
													<div style="width:100px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left"><input type="checkbox" name="sltSiteGroup" value="<%=sgi.getSg_seq()%>" <%=checked%>><%=sgi.getSd_name()%></div>
<%
									checked="";
								}
								%>
												</td>
											</tr>
										</table></td>
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif"  /> <strong>검색언어</strong></td>
				                <td style="color:#2f5065;" >
				                	<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td><input name="language" type="radio" value="0" <%if(language.equals("") || language.equals("0"))out.print("checked");%>><strong>전체</strong>&nbsp;</td>
												<td><input name="language" type="radio" value="KOR" <%if(language.equals("KOR"))out.print("checked");%>>한국어&nbsp;</td>
												<td><input name="language" type="radio" value="ENG" <%if(language.equals("ENG"))out.print("checked");%>>영어&nbsp;</td>
											</tr>
										</table></td>
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="1" bgcolor="#93a6b4"></td>
				      </tr>
				      
				    </table></td>
				  </tr>
				  <tr id="hide2" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
				    <td height="1" bgcolor="#93a6b4"></td>
				  </tr>
				  <tr>
				    <td align="center"><img src="<%if(uei.getSt_menu().equals("0")){out.print("../../images/search/btn_searchbox_open.gif");}else{out.print("../../images/search/btn_searchbox_close.gif");}%>" style="cursor:pointer" onclick="actionLayer(this)"/></td>
				  </tr>
				</table>
				</form>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:45px;background:url('../../images/search/list_top_line.gif') bottom repeat-x;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/icon_search_bullet.gif"> <strong><%=smgr.msKeyTitle%></strong>에 대한 검색결과 /<strong> <%=iTotalCnt%></strong> 건, <strong><%=iTotalCnt==0?0:iNowPage%></strong>/<strong><%=iTotalPage%></strong> pages</td>
						<td width="150" align="left" style="padding: 2px 0px 0px 3px;">
							<span class="search_reset_time" id="watch"><strong>새로고침 중지중..</strong></span>
						</td>
						<td width="90">
							<select name="slttimer" class="t" OnChange="setTimer(this.value);" >
								<option value="0"  <%if(uei.getSt_reload_time().equals("0")) out.println("selected");%>>사용하지 않음</option>
								<option value="5"  <%if(uei.getSt_reload_time().equals("5")) out.println("selected");%>>5분마다 새로고침</option>
								<option value="10" <%if(uei.getSt_reload_time().equals("10")) out.println("selected");%>>10분마다 새로고침</option>
								<option value="20" <%if(uei.getSt_reload_time().equals("20")) out.println("selected");%>>20분마다 새로고침</option>
								<option value="30" <%if(uei.getSt_reload_time().equals("30")) out.println("selected");%>>30분마다 새로고침</option>
							</select>
						</td>
					</tr>
				</table>	
				</td>
			</tr>
		</table>
		<form name="fCheck" id="fCheck" target="_blank" method="post" style="margin-top: 0px;">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/btn_allselect.gif" onclick="reverseAll(!document.getElementById('fCheck').chkAllCheck.checked);"/></td>
<%
	if(uei.getSearchMode().equals("EX_ALLKEY")){
%>  
						<td><img src="../../images/search/btn_del.gif" onclick="idxProcess('truncate');" style="cursor:pointer"/></td>
						<td><img src="../../images/search/btn_bookmark.gif" onclick="saveBookMark();" style="cursor:pointer"/></td>
						<td><img src="../../images/search/btn_bookmark_search.gif" onclick="doBookMarkSearch();" style="cursor:pointer"/></td>
						<td><img src="../../images/search/btn_multire.gif" onclick="exceptionRestore_multi();" style="cursor:pointer"/></td>
<!--						<td><input type="button" value="지우개" style="width: 88px; height: 24px" onclick="listClear('<%=uei.getM_seq()%>')"></td>-->
<%
	}else if(uei.getSearchMode().equals("DELIDX")){
%>
						<td><img src="../../images/search/btn_restoration.gif" onclick="idxProcess('revert');" style="cursor:pointer"/></td>
						<td><img src="../../images/search/btn_del3.gif" onclick="idxProcess('del');" style="cursor:pointer"/></td>
						<td align="right"><b style="color:navy">* 휴지통의 기사는 삭제 7일 후 영구삭제 됩니다.</b></td>
<%
	}
%>
					</tr>
				</table>				
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
<%
	if(!uei.getSearchMode().equals("DELIDX")){
%>
				<col width="5%"><col width="15%"><col width="49%"><col width="6%"><col width="10%"><col width="15%">
<%
	}else{
%>
				<col width="5%"><col width="15%"><col width="50%"><col width="10%"><col width="10%"><col width="10%">
<%
	}
%>
					<tr>
						<th><input type="checkbox" name="chkAllCheck" value="" onclick="reverseAll(this.checked);"></th>
						<th style="cursor:pointer" onclick="setOrder('MD_SITE_NAME');" style="cursor:pointer">출처<%=sOrderMark1%></th>
<%
	if(!uei.getSearchMode().equals("DELIDX")){
%>
						<th style="cursor:pointer" onclick="setOrder('MD_TITLE');" style="cursor:pointer">제목<%=sOrderMark2%></th>
						<th></th>
<%
		if(uei.getSearchMode().equals("EX_ALLKEY")){
%>
						<th></th>
<%
		}
%>
						<th style="cursor:pointer" onclick="setOrder('MD_DATE');" style="cursor:pointer">수집시간<%=sOrderMark5%></th>
<%
	}else{
%>
						<th style="cursor:pointer" onclick="setOrder('MD_TITLE');" style="cursor:pointer">제목<%=sOrderMark2%></th>
						<th style="cursor:pointer" onclick="setOrder('MD_DATE');" style="cursor:pointer">수집시간<%=sOrderMark5%></th>
						<th style="cursor:pointer" onclick="setOrder('M_NAME');" style="cursor:pointer">삭제자<%=sOrderMark7%></th>
						<th style="cursor:pointer" onclick="setOrder('I_DELDATE');" style="cursor:pointer">삭제시간<%=sOrderMark6%></th>
<%
	}
%>
					</tr>
<%
if(alData.size() > 0){
	
		String star = "";
		String follower = "";
	
	for(int i = 0; i < alData.size(); i++){
		MetaBean mBean = (MetaBean) alData.get(i);
		
	   	//내용 강조
	   	String Html = "";	   	
	   	if(!uei.getKeyword().equals("")) {
	   		Html=mBean.getHighrightHtml(200,"D72D2D");
	   	}else{
	   		Html=mBean.getHighrightHtml(200,"D72D2D");
	   	}
	   	
		//전체키워드검색
		if(uei.getSearchMode().equals("EX_ALLKEY")){
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
%>
					<tr style="background-color:<%=bookMarkColor%>" onmouseover="this.style.backgroundColor='<%=overColor%>';" onmouseout="this.style.backgroundColor='<%=outColor%>';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" value="<%=mBean.getMd_seq()%>"></td>
						<!--↓↓출처-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><font style="font-size:11px;" color="#225bd1"><%=mBean.getMd_site_name()%></font></td>
						<!--↓↓제목-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<p class="board_01_tit_0<%=mBean.getMd_type()%>"><a onClick="hrefPop('<%=mBean.getMd_url()%>', '<%=uei.getM_seq()%>', '<%=mBean.getMd_seq()%>');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(<%=i%>);" onmouseout="close_me(<%=i%>);"><%=comfirmColor%><%=mBean.getHtmlMd_title()%></a><%=star%></p>
							<div id=div<%=i%> style="display:none;width:200px;height:80px;">
								<%=follower + Html%>     
							</div>
						</td>
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/ico_original.gif" style="cursor:pointer" onclick="originalView('<%=mBean.getMd_seq()%>','<%=mBean.getS_seq()%>','<%=java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")%>');">
						</td>
						<!--↓↓복원버튼-->
						<td><img src="../../images/search/btn_re.gif" style="cursor:pointer" onclick="exceptionRestore('<%=mBean.getMd_seq()%>')"></td>

						<!--↓↓수집시간-->
						<td><%=mBean.getFormatMd_date("MM-dd HH:mm")%></td>
					</tr>
					<tr>
						<td class="same" colspan="7">
							<div id="SameList_<%=mBean.getMd_pseq()%>" style="display:none;"></div>
						</td>
					</tr>
<%
		//휴지통
		}else if(uei.getSearchMode().equals("DELIDX")){
%>
					<tr>
						<td><input type="checkbox" name="chkData" value="<%=mBean.getMd_seq()%>"></td>
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><%=mBean.getMd_site_name()%></td>
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<p class="board_01_tit_01">
								<%=mBean.getHtmlMd_title()%>
							</p>
							<div id=div<%=i%> style="display:none;width:200px;height:80px;">
								<%=Html%>       
							</div>
						</td>
						<td><%=mBean.getFormatMd_date("MM-dd HH:mm")%></td>
						<td><%=mBean.getM_name()%></td>
						<td><%=mBean.getFormatI_deldate("MM-dd HH:mm")%></td>
					</tr>
<%
		}
	}
}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</form>
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 페이징 -->
			<tr>
				<td align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(iNowPage,iTotalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td>
		<!-- 퀵 -->
		<!-- <td style="background:#eaeaea;" >
			<table class="quick_bg" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="quick_bg2" valign="top"><img src="../../images/common/quick_bg_top.gif" /></td>
				</tr>
			</table>
		</td> -->
		<!-- 퀵 -->
	</tr>
</table>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>

<br><br><br><br>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank"></iframe>
<iframe id="proceeFrame" name="proceeFrame" width="0" height="0"></iframe>
</body>
</html>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>