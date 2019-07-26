<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu
                 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%

    ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	pr.printParams();
    String sInit = (String)session.getAttribute("INIT");

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode() == null ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/left_design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!--[if (gte IE 6)&(lte IE 8)]>
  <script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
</head>
<body>
<div id="wrap">
	<div id="snb">
		<h2><img src="../../images/left/left_title01.gif" alt="정보검색"></h2>
		<div class="snb">
			<ul>
			<li>
				<a href="javascript:chageSearchMode('SOLR',document.all.solrkey);"><span class="icon">-</span>전체 검색</a>
			</li>
			<li>
				<a href="javascript:chageSearchMode('TOP',document.all.solrkey);"><span class="icon">-</span>포탈 TOP</a>
			</li>
			<li>
				<a href="javascript:chageSearchMode('ALLKEY',document.all.solrkey);" class="active"><span class="icon">-</span>전체 키워드 검색</a>
			</li>
			<li>
				<a href="javascript:chageSearchMode('EX_ALLKEY',document.all.solrkey);"><span class="icon">-</span>제외 키워드 검색</a>
			</li>
			</ul>
			<div>
			</div>
			<iframe name="ifr_menu"  id="ifr_menu" src="inc_keyword_menu.jsp" frameborder="0" border="0" style="width:295px;min-height:300px"></iframe>
		</div>
		<script type="text/javascript">
		(function($) {
			//iframe 사이즈 
			hndl_resize();
			$( window ).resize( hndl_resize );
			function hndl_resize($e) {
				$( "#snb iframe" ).each(function(){
					if( $( this ).parent().find( "a" ).hasClass( "active" ) ) {
						var hgt = $( window ).height() - ( $( "#snb h2" ).outerHeight() + ( $( "#snb li a" ).outerHeight() * $( "#snb li a" ).length ) ) - 100;
						$( this ).show();
						$( this ).height( hgt );
					} else {
						$( this ).hide();
						$( this ).height(0);
					}
				})
			}
			
			// 레프트 메뉴 클릭 - 활성화
			$( "#snb li a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				$( this ).addClass( "active" );
				hndl_resize();
			});
		})(jQuery);
		
		//검색 모드 변경
	    function chageSearchMode( value, obj ) {
	        
	        	selectedObj = obj;
	        	
	        if ( value == "ALLKEY" ) {
	        	add = '&xp=0&yp=0&zp=0';
	        	
	            top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
				
				ifr_menu.location = "inc_keyword_menu.jsp?menu=" + value + add;
				
	        }else if ( value == "EX_ALLKEY" ) {
	        	add = '&xp=0&yp=0&zp=0';
	        	
	            top.bottomFrame.contentsFrame.document.location ='search_main_exception.jsp?searchmode=' + value + add ;

				ifr_menu.location = "inc_keyword_menu.jsp?menu=" + value + add;
	     
			}else if( value == "SOLR"){
		        top.bottomFrame.contentsFrame.document.location ='solr/search_main_contents_solr.jsp';
			}else if( value == "TOP"){
				top.bottomFrame.contentsFrame.document.location ='search_main_top.jsp';
			} else{
				add = '&xp=0&yp=0&zp=0';
		        top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
			}

	    }
		</script>
	</div>
</div>
</body>
</html>
