<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/left_design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<script type="text/JavaScript">
function init()
{
	mnu_chick(document.all.admin_menu1);
}

var link = new Array(
"",
"user/admin_user_list.jsp",
"usergroup/admin_usergroup.jsp",
"logs/user_log_list.jsp?sch_svc=1",
"keyword/admin_keyword.jsp",
"site/admin_site.jsp",
"receiver/receiver_list.jsp",
"classification/classification_mgr.jsp"
,"mobile/mobile_setting.jsp"
,"pcalimi/pcalimi_setting.jsp"
,"alimi/alimi_setting_list.jsp"
,"aekeyword/aekeyword_manager.jsp"
,"tier/tier_main.jsp"
,"tokeyword/tokeyword_manager.jsp"
,"alimi/alimi_log_list.jsp"
,"infogroup/infogroup_manager.jsp"
,"blacksite/blacksite_manager.jsp"
,"hotkeyword/hotkeywordMain.jsp"
,"social/social_manager.jsp"
,"relation_manager/relation_manager.jsp"
,"press_mng/pressMng_list.jsp"
,"sns_manager/sns_manager.jsp"
);

</script>
<body>
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<div id="wrap">
	<div id="snb">
		<h2><img src="../../images/left/left_title05.gif" alt="관리자"></h2>
		<div class="snb">
			<ul>
			<li>
				<a href="javascript:mnu_chick(1);" class="active"><span class="icon">-</span>사용자 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(2);" ><span class="icon">-</span>사용자 그룹/권한 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(3);" ><span class="icon">-</span>로그 보기</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(4);" ><span class="icon">-</span>키워드 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(11);" ><span class="icon">-</span>제외 키워드 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(5);" ><span class="icon">-</span>수집 사이트 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(12);" ><span class="icon">-</span>매체 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(7);" ><span class="icon">-</span>보고서 분류체계 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(18);" ><span class="icon">-</span>소셜 통계 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(6);" ><span class="icon">-</span>정보 수신자 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(10);" ><span class="icon">-</span>알리미 설정 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(14);" ><span class="icon">-</span>알리미 로그 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(19);" ><span class="icon">-</span>연관키워드 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(20);" ><span class="icon">-</span>보도자료 관리</a>
			</li>
			<li>
				<a href="javascript:mnu_chick(21);" ><span class="icon">-</span>SNS정보추이 현황 관리</a>
			</li>
			</ul>
		</div>
		<script type="text/javascript">
		(function($) {
			
			// 레프트 메뉴 클릭 - 활성화
			$( "#snb li a" ).click(function($e) {
				$( "#snb li a" ).removeClass( "active" );
				$( this ).addClass( "active" );
			});
		})(jQuery);
		
		function mnu_chick(target) {
			parent.contentsFrame.location.href=link[target];
		}
		
		</script>
	</div>
</div>
</body>
</html>
