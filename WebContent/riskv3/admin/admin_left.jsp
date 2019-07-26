<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<script type="text/JavaScript">
function init()
{
	mnu_chick(document.all.admin_menu1);
}

var selectedObj = "";

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
,"social/statistics_social_manager.jsp"
);
// 메뉴 클릭시 이벤트
// 클릭한 메뉴를 선택상태로 만들고, 하단 프레임을 변경한다.



function mnu_chick(obj) {
	var tmpObj;
	for(var i=1;i<link.length ;i++ )
	{	
		tmpObj = eval("document.all.admin_menu"+i);
		setOff(tmpObj);		
	}
	setOn(obj);
	parent.contentsFrame.location.href=link[obj.id.substr(10,2)];
}
function setOn(obj)
{
	var tmpObj1;
	var tmpObj2;
	var i=obj.id.substring(10,12);
    tmpObj1 =eval("document.all.tdbg"+ i);
    //tmpObj2 =eval("document.all.tdclass"+ i);    
    tmpObj2 =eval("document.all.aclass"+ i);
    selectedObj = tmpObj1;
    
    getOn(tmpObj1,tmpObj2);      
}

function setOff(obj)
{
	var tmpObj1;
	var tmpObj2;
	var i=obj.id.substring(10,12);
    tmpObj1 =eval("document.all.tdbg"+ i);
    //tmpObj2 =eval("document.all.tdclass"+ i);
    tmpObj2 =eval("document.all.aclass"+ i);

    getOff(tmpObj1,tmpObj2);          
}


function getOn(obj1,obj2)
{	
	obj2.style.color="#ffffff";
	//obj2.className="textwhite";
	obj1.background="../../images/left/top_left_onbg.gif";	
	
}

function getOff(obj1,obj2)
{   
	obj2.style.color="#000000";
	//obj2.className="leftM_off";
	obj1.background="../../images/left/top_left_offbg.gif";		
}

function onMs(obj){
	obj.background='../../images/left/top_left_onbg.gif';
}
function outMs(obj){
	obj.background='../../images/left/top_left_offbg.gif';
	selectedObj.background='../../images/left/top_left_onbg.gif';
}

</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="300" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
<!--    <td align="right" valign="top" background="../../images/left/top_left_mbg.gif">-->
    <td align="right" valign="top">
    <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="../../images/left/admin_ltitle.gif"><!--/// LEFT MENU TITLE
 		//// 정보검색 타이틀 <img src="../../images/left/left_title01.gif" width="205" height="52">
		//// 이슈관리 타이틀 <img src="../../images/left/left_title02.gif" width="205" height="52">
		//// 관리자 타이틀<img src="../../images/left/left_title03.gif" width="205" height="52">
		//// 알리미 타이틀<img src="../../images/left/left_title04.gif" width="205" height="52"> ////--></td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF"><img src="../../images/left/brank.gif" width="1" height="4"></td>
      </tr>
    </table>
    <table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
    </table>	
    
    
    
    
    <!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu1">
          <td id="tdbg1" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_onbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass1" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass1" style="color:ffffff;" href="javascript:mnu_chick(document.all.admin_menu1);">사용자 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
    
    
    
    
    <table width="205" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
    </table>	
    <!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu2">
          <td id="tdbg2" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass2" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass2" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu2);">사용자그룹/권한 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
    <table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
    </table>  
	<!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu3">
          <td id="tdbg3" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass3" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass3" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu3);">로그보기</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
     <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
      </tr>
    </table>	  
	<!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu11">
          <td id="tdbg11" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass11" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass11" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu11);">전체제외키워드 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
    <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
      </tr>
    </table>	  
	<!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu4">
          <td id="tdbg4" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass4" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass4" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu4);">키워드 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
    <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
      </tr>
    </table>
    <!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu5">
          <td id="tdbg5" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass5" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass5" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu5);">수집사이트관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->
    <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
      </tr>
    </table>
    <!--/// LEFT MENU OFF ////--><table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu6">
          <td id="tdbg6" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass6" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass6" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu6);">정보수신자 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	  
    <table width="295" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    <!--/// LEFT MENU OFF ////-->
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu7">
          <td id="tdbg7" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass7" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass7" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu7);">보고서 분류체계 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	  
	<table width="295" border="0" cellspacing="0" cellpadding="0" style="display: none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display: none;">
        <tr id="admin_menu8">
          <td id="tdbg8" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass8" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass8" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu8);">앱설정관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	  
	<table width="295" border="0" cellspacing="0" cellpadding="0" style="display: none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
       </table>
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display: none;">
        <tr id="admin_menu9">
          <td id="tdbg9" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass9" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass9" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu9);">PC알리미관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	  
	
<table width="295" border="0" cellspacing="0" cellpadding="0" style="display: none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:;">
        <tr id="admin_menu10">
          <td id="tdbg10" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass10" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass10" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu10);">알리미설정관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	 
<table width="295" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:;">
        <tr id="admin_menu14">
          <td id="tdbg14" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass14" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass14" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu14);">알리미로그관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	 
    
    <table width="295" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    
    
    
    
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:;">
        <tr id="admin_menu12">
          <td id="tdbg12" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass12" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass12" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu12);">매체관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	 
    
    <table width="295" border="0" cellspacing="0" cellpadding="0" style="display:none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    
    <%--
    --%>
     <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:none;">
        <tr id="admin_menu13">
          <td id="tdbg13" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass13" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass13" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu13);">투데이 키워드 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table> <!--/// LEFT MENU OFF ////-->	 
    
    <table width="295" border="0" cellspacing="0" cellpadding="0" style="display:none">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    
    
     <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:none;">
        <tr id="admin_menu15">
          <td id="tdbg15" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass15" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass15" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu15);">정보그룹관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table><!--/// LEFT MENU OFF ////-->	 
    <table width="295" border="0" cellspacing="0" cellpadding="0" style="display:none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table><!--/// LEFT MENU OFF ////-->
    <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:none;">
        <tr id="admin_menu16">
          <td id="tdbg16" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass16" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass16" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu16);">블랙사이트관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table><!--/// LEFT MENU OFF ////-->	 
    
    <table width="295" border="0" cellspacing="0" cellpadding="0" style="display:none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>
     <table width="295" height="33" border="0" cellpadding="0" cellspacing="0" style="display:none;">
        <tr id="admin_menu17">
          <td id="tdbg17" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass17" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass17" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu17);">HOT 키워드 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table><!--/// LEFT MENU OFF ////-->
    
    <table width="295" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>
     <table width="295" height="33" border="0" cellpadding="0" cellspacing="0">
        <tr id="admin_menu18">
          <td id="tdbg18" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico07.gif" width="20" height="18"></td>
              <td id="tdclass18" class="leftM_off" style="padding: 2px 0px 0px 3px;"><strong><a id="aclass18" style="color:000000;" href="javascript:mnu_chick(document.all.admin_menu18);">소셜 통계 관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
    </table>
    <table width="295" border="0" cellspacing="0" cellpadding="0" style="display:none;">
       <tr>
         <td bgcolor="#C9C9C9"><img src="../../images/left/brank.gif" width="1" height="1"></td>
       </tr>
    </table>	  <!--/// LEFT MENU OFF ////-->
    </td></tr></table>
</body>
</html>
<script>
if (selectedObj==""){
	selectedObj=document.all.tdbg1;
	selectedObj.background='../../images/left/top_left_onbg.gif';
}
</script>