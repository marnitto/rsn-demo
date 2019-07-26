<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu"
%>
<%@ include file="inc/sessioncheck.jsp" %>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title><%=SS_TITLE%></title>
<link rel="shortcut icon" href="<%=SS_URL%>dashboard/asset/img/favicon.ico" type="image/x-icon" />
<FRAMESET COLS="*" frameborder="0" border="0">
	<FRAMESET ROWS="51,*" id="mainset" frameborder="0" border="0">
		<FRAME SRC="inc/topmenu/inc_topmenu.jsp" NAME="topFrame" scrolling="no" noresize>
		<FRAME SRC="search/search.jsp?INIT=INIT" NAME="bottomFrame" noresize>	
	</FRAMESET>
</FRAMESET>
<%-- <%
ParseRequest    pr = new ParseRequest(request);
session.setAttribute("INIT","INIT");

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
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>dashboard/asset/css/design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/design.js"></script>
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!--[if (gte IE 6)&(lte IE 8)]>
  <script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
<script type="text/javascript">
	
	
	//검색 모드 변경
    function chageSearchMode( value, obj ) {
        
        	selectedObj = obj;
        	
        if( value == "ALLKEY" ) {
        	add = '&xp=0&yp=0&zp=0';
        	var url = "search/search_main_contents.jsp?searchmode="+value+add;
        	$("[name=ifr]").attr("src","");
            //top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
			//ifr_menu.location = "inc_keyword_menu.jsp?menu=" + value + add;
        }else if ( value == "EX_ALLKEY" ) {
        	add = '&xp=0&yp=0&zp=0';       
        	var url = "search/search/search_main_exception.jsp?searchmode="+value+add;
        	$("[name=ifr]").attr("src","");
            //top.bottomFrame.contentsFrame.document.location ='search_main_exception.jsp?searchmode=' + value + add ;
			//ifr_menu.location = "inc_keyword_menu.jsp?menu=";
			//ifr_exmenu.location = "inc_keyword_menu.jsp?menu=" + value + add;
		}else if( value == "SOLR"){
	        //top.bottomFrame.contentsFrame.document.location ='solr/search_main_contents_solr.jsp';
			var url = "search/solr/search_main_contents_solr.jsp";
        	$("[name=ifr]").attr("src","");
		}else{
			add = '&xp=0&yp=0&zp=0';
			var url = "search/search_main_contents.jsp?searchmode="+value+add;
        	$("[name=ifr]").attr("src","");
	        //top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
		}

    }
</script>
</head>
<body>
	<div id="wrap">
		<div id="header" class="ui_shadow_00">
			<div class="header_wrap">
				<h1><a href="index.html"><img src="<%=SS_URL%>dashboard/asset/img/h1_logo.gif" alt=""></a></h1>
				<nav>
					<ul>
						<li class="gnb_00">
							<a href="<%=SS_URL%>dashboard/main/dashboard.jsp">대시보드</a>
						</li>
						<li class="gnb_01">
							<a href="search/search_main_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0" target="content_ifr" data-role-idx="1">정보검색</a>
							<div class="gnb_sub ui_shadow_00">
								<ul>
									<li><a href="search/solr/search_main_contents_solr.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>전체검색</a></li>
									<li><a href="search/search_main_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0" target="content_ifr" data-role-idx="0"><span class="icon">-</span>전체 키워드 검색</a></li>
									<li><a href="search/search_main_exception.jsp?searchmode=EX_ALLKEY&xp=0&yp=0&zp=0" target="content_ifr" data-role-idx="2"><span class="icon">-</span>제외 키워드 검색</a></li>
								</ul>
							</div>
						</li>
						<li class="gnb_02">
							<a href="issue/issue_data_list.jsp" target="content_ifr" data-role-idx="2">이슈관리</a>
							<div class="gnb_sub ui_shadow_00">
								<ul>
									<li><a href="issue/issue_data_list.jsp" target="content_ifr" data-role-idx="0"><span class="icon">-</span>관련정보</a></li>
								</ul>
							</div>
						</li>
						<li class="gnb_03">
							<a href="report/issue_report_list.jsp?ir_type=D1,I" target="content_ifr" data-role-idx="3">보고서관리</a>
							<div class="gnb_sub ui_shadow_00">
								<ul>
									<li><a href="report/issue_report_list.jsp?ir_type=D1,I" target="content_ifr" data-role-idx="0"><span class="icon">-</span>보고서 관리</a></li>
									<li><a href="report/issue_report_creater.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>보고서 작성</a></li>
								</ul>
							</div>
						</li>
						<li class="gnb_04">
							<a href="admin/user/admin_user_list.jsp" target="content_ifr" data-role-idx="4">관리자</a>
							<div class="gnb_sub ui_shadow_00">
								<ul>
									<li><a href="admin/user/admin_user_list.jsp" target="content_ifr" data-role-idx="0"><span class="icon">-</span>사용자 관리</a></li>
									<li><a href="admin/usergroup/admin_usergroup.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>사용자 그룹/권한 관리</a></li>
									<li><a href="admin/logs/user_log_list.jsp?sch_svc=1" target="content_ifr" data-role-idx="2"><span class="icon">-</span>로그 보기</a></li>
									<li><a href="admin/keyword/admin_keyword.jsp" target="content_ifr" data-role-idx="3"><span class="icon">-</span>키워드 관리</a></li>
									<li><a href="admin/aekeyword/aekeyword_manager.jsp" target="content_ifr" data-role-idx="4"><span class="icon">-</span>제외 키워드 관리</a></li>
									<li><a href="admin/site/admin_site.jsp" target="content_ifr" data-role-idx="5"><span class="icon">-</span>수집 사이트 관리</a></li>
									<li><a href="admin/tier/tier_main.jsp" target="content_ifr" data-role-idx="6"><span class="icon">-</span>매체 관리</a></li>
									<li><a href="admin/classification/classification_mgr.jsp" target="content_ifr" data-role-idx="7"><span class="icon">-</span>보고서 분류체계 관리</a></li>
									<li><a href="admin/social/social_manager.jsp" target="content_ifr" data-role-idx="8"><span class="icon">-</span>소셜 통계 관리</a></li>
									<li><a href="admin/receiver/receiver_list.jsp" target="content_ifr" data-role-idx="9"><span class="icon">-</span>정보 수신자 관리</a></li>
									<li><a href="admin/alimi/alimi_setting_list.jsp" target="content_ifr" data-role-idx="10"><span class="icon">-</span>알리미 설정 관리</a></li>
									<li><a href="admin/alimi/alimi_log_list.jsp" target="content_ifr" data-role-idx="11"><span class="icon">-</span>알리미 로그 관리</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</nav>
				<div class="user_info">
					<h2 class="invisible">사용자 정보</h2>
					<span class="user_icon">-</span>
					<span><strong><%=SS_M_NAME%></strong>님 반갑습니다.</span>
					<a href="logout.jsp" class="ui_btn_logout">로그아웃</a>
					<a href="search/search_env_setting.jsp" target="content_ifr" class="ui_btn_setting" data-role-idx="5">설정</a>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="aside">
				<!-- 대시 보드 -->
				<div class="snb" data-role-idx="0">
					대시보드는 LNB가 없습니다
				</div>
				<!-- // 대시 보드 -->

				<!-- 정보 검색 -->
				<div class="snb" data-role-idx="1">
					<h2><img src="<%=SS_URL%>dashboard/asset/img/left_title01.gif" alt="정보검색"></h2>
					<div class="snb_box">
						<ul>
						<li><a href="search/solr/search_main_contents_solr.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>전체 검색</a></li>
						<li><a href="search/search_main_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0" target="content_ifr" data-role-idx="0"><span class="icon">-</span>전체 키워드 검색</a>
							<iframe src="search/inc_keyword_menu.jsp?menu=ALLKEY&xp=0&yp=0&zp=0" frameborder="0"></iframe>
						</li>
						<li><a href="search/search_main_exception.jsp?searchmode=EX_ALLKEY&xp=0&yp=0&zp=0" target="content_ifr" data-role-idx="2"><span class="icon">-</span>제외 키워드 검색</a>
							<iframe src="search/inc_keyword_menu.jsp?menu=ALLKEY&xp=0&yp=0&zp=0" frameborder="0"></iframe>
						</li>
						</ul>
					</div>
				</div>
				<!-- // 정보 검색 -->

				<!-- 이슈 관리 -->
				<div class="snb" data-role-idx="2">
					<h2><img src="<%=SS_URL%>dashboard/asset/img/left_title02.gif" alt="이슈관리"></h2>
					<div class="snb_box">
						<ul>
						<li><a href="issue/issue_data_list.jsp" target="content_ifr" data-role-idx="0"><span class="icon">-</span>관련정보</a></li>
						</ul>
					</div>
				</div>
				<!-- // 이슈 관리 -->

				<!-- 보고서 관리 -->
				<div class="snb" data-role-idx="3">
					<h2><img src="<%=SS_URL%>dashboard/asset/img/left_title03.gif" alt="보고서관리"></h2>
					<div class="snb_box">
						<ul>
						<li><a href="report/issue_report_list.jsp?ir_type=D1,I" target="content_ifr" data-role-idx="0"><span class="icon">-</span>보고서 관리</a>
							<ul>
							<li><a href="report/issue_report_list.jsp?ir_type=D1,I" target="content_ifr" data-role-idx="0"><span class="icon">-</span>일일보고서</a></li>
							<li><a href="report/issue_report_list.jsp?ir_type=E" target="content_ifr" data-role-idx="1"><span class="icon">-</span>긴급보고서</a></li>
							</ul>
						</li>
						<li><a href="report/issue_report_creater.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>보고서 작성</a></li>
						</ul>
					</div>
				</div>
				<div class="snb" data-role-idx="4">
					<h2><img src="<%=SS_URL%>dashboard/asset/img/left_title04.gif" alt="관리자"></h2>
					<div class="snb_box">
						<ul>
						<li><a href="admin/user/admin_user_list.jsp" target="content_ifr" data-role-idx="0"><span class="icon">-</span>사용자 관리</a></li>
						<li><a href="admin/usergroup/admin_usergroup.jsp" target="content_ifr" data-role-idx="1"><span class="icon">-</span>사용자 그룹/권한 관리</a></li>
						<li><a href="admin/logs/user_log_list.jsp?sch_svc=1" target="content_ifr" data-role-idx="2"><span class="icon">-</span>로그 보기</a></li>
						<li><a href="admin/keyword/admin_keyword.jsp" target="content_ifr" data-role-idx="3"><span class="icon">-</span>키워드 관리</a></li>
						<li><a href="admin/aekeyword/aekeyword_manager.jsp" target="content_ifr" data-role-idx="4"><span class="icon">-</span>제외 키워드 관리</a></li>
						<li><a href="admin/site/admin_site.jsp" target="content_ifr" data-role-idx="5"><span class="icon">-</span>수집 사이트 관리</a></li>
						<li><a href="admin/tier/tier_main.jsp" target="content_ifr" data-role-idx="6"><span class="icon">-</span>매체 관리</a></li>
						<li><a href="admin/classification/classification_mgr.jsp" target="content_ifr" data-role-idx="7"><span class="icon">-</span>보고서 분류체계 관리</a></li>
						<li><a href="admin/social/social_manager.jsp" target="content_ifr" data-role-idx="8"><span class="icon">-</span>소셜 통계 관리</a></li>
						<li><a href="admin/receiver/receiver_list.jsp" target="content_ifr" data-role-idx="9"><span class="icon">-</span>정보 수신자 관리</a></li>
						<li><a href="admin/alimi/alimi_setting_list.jsp" target="content_ifr" data-role-idx="10"><span class="icon">-</span>알리미 설정 관리</a></li>
						<li><a href="admin/alimi/alimi_log_list.jsp" target="content_ifr" data-role-idx="11"><span class="icon">-</span>알리미 로그 관리</a></li>
						</ul>
					</div>
				</div>

				<!-- 환경설정 -->
				<div class="snb" data-role-idx="5">
					<h2><img src="<%=SS_URL%>dashboard/asset/img/left_title05.gif" alt="관리자"></h2>
					<div class="snb_box">
						<ul>
						<li><a href="search/search_env_setting.jsp" target="content_ifr" data-role-idx="0"><span class="icon">-</span>환경설정</a></li>
						</ul>
					</div>
				</div>
				<!-- // 환경설정 -->
			</div>
			<div id="content">
				<iframe src="search/search_main_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0" id="content_ifr" name="content_ifr" frameborder="0" class="ui_con_frame_00" scrolling="no"></iframe>
			</div>
		</div>
	</div>
</body>
</html> --%>
