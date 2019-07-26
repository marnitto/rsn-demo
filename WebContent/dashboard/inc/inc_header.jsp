<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<script type="text/javascript">
	function navFormSend( $e ){
		var tg = $e ? $e.currentTarget : event.currentTarget;
		$( "#subCode" ).val( $( tg ).attr( "data-idx" ) );
		$( "#gnbForm" ).attr( "action", $( tg ).attr( "href" ) );
		if( $e.shiftKey ) $( "#gnbForm" ).attr( "target", "_blank" );
		else $( "#gnbForm" ).attr( "target", "_self" );
		
		var digitalHref = $( tg ).attr( "href" );
		
		//if(digitalHref == "../search/search.jsp"){
		//	
		//	$.ajax({
		//		crossOrigin: true,
		//        url:"http://lucyapi.realsn.com/systemCheck?i_type=check",
		//        dataType:"json",
		//        success:function(data){
		//        	var jsonObj = JSON.parse(data);
		//        	var checektim = jsonObj.chktime.split(",");
		//        	if(jsonObj.code == 1){
		//        		alert("시스템 정기 점검중입니다. (점검시간 "+checektim[0]+":00 ~ "+checektim[1]+":00)");
		//        	}else{
		//        		$( "#gnbForm" ).serialize();
		//    			$( "#gnbForm" ).submit();
		//        	}
		//        }
	    //   });
		//	
		//}else{
		//	$( "#gnbForm" ).serialize();
		//	$( "#gnbForm" ).submit();	
		//}
		
		$( "#gnbForm" ).serialize();
		$( "#gnbForm" ).submit();
	}
</script>


<form id="gnbForm" action="#" method="post">
	<input id="subCode" name="subCode" type="hidden">
</form>

<header>
	<div class="wrap">
		<h1>
			<a href="../summary/summary.jsp"><img src="../asset/img/h1_logo.gif" alt="대구시청"></a>
		</h1>
		<div class="util">
			<div class="user fr">
				<span class="user_name"><%=(String)session.getAttribute("SS_M_NAME") %> 님</span>
<!-- 				<a href="../../riskv3/logout.jsp" class="ui_btn_00" title="로그아웃"><span>로그아웃</span></a> -->
				<a href="../../riskv3/main.jsp" class="ui_btn_00" title="시스템 바로가기"><span>시스템</span></a>
				<a href="../../riskv3/logout.jsp" class="ui_btn_00" title="로그아웃"><span>로그아웃</span></a>
			</div>
		</div>
		<nav>
			<ul>
				<li><a href="../summary/summary.jsp" data-idx="01" onclick="navFormSend( event );return false;"><span>Summary</span></a></li>
				<li><a href="../mrt/mrt.jsp" data-idx="02" onclick="navFormSend( event );return false;"><span>주요시정 모니터링</span></a></li>
				<li><a href="../realstate/realstate.jsp" data-idx="03" onclick="navFormSend(event);return false;"><span>실국별 현황 분석</span></a></li>
				<li><a href="../media/media.jsp" data-idx="04" onclick="navFormSend(event);return false;"><span>온라인 홍보 매체 현황</span></a></li>				
				<!-- <li><a href="../opinion/opinion.jsp" data-idx="03" onclick="navFormSend( event );return false;"><span>여론동향</span></a></li> -->
				<!-- <li><a href="../search/search_new.jsp" data-idx="05" onclick="navFormSend( event );return false;"><span>디지털 동향 분석</span></a></li> -->
				<li><a href="#" data-idx="05"  onclick="window.open('http://lucy2search.realsn.com/?key=8d1526fabe754a9187129043867df581'); return false;"><span>트렌드 분석</span></a><li>
			</ul>
		</nav>
		<script type="text/javascript">
			if( $( "header nav > ul > li > a[data-idx='09']" ).length > 0 ) $( "header nav" ).addClass( "in_datadn" )
		</script>
	</div>
</header>