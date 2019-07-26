<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.search.userEnvInfo"%>
<%@ include file="../sessioncheck.jsp" %>
<%
	userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
	String sms = "";
	if( request.getParameter("sms") != null ) sms = request.getParameter("sms");
	StringUtil su = new StringUtil();
	
	String MGmenu = "";
	String[] arrMGmenu = null;
	
	MGmenu = env.getMg_menu();
	System.out.println("MGmenu:"+MGmenu);
	if( MGmenu != null )
	arrMGmenu = MGmenu.split(",");
	
	//대쉬보드 시간 초기화
	session.setAttribute("SS_SEARCHDATE", "");
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../css/basic.css" type="text/css">

<script type="text/JavaScript">
var sg_no = <%=SS_MG_NO%>;
// 현재 선택된 메뉴를 저장한다.
// 초기에 지정해 주어야 한다. 지정하지 않으면 검색메뉴를 지정한다.
var selectedObj = "";
// 각 메뉴 클릭시 이동할 페이지 정보를 지정한다.

var link = new Array(
"../../search/search.jsp?INIT=INIT",
"../../issue/issue.jsp",
"../../report/report.jsp",
"../../statistics/statistics.jsp",
"../../admin/admin_main.jsp"
);
// 메뉴 클릭시 이벤트
// 클릭한 메뉴를 선택상태로 만들고, 하단 프레임을 변경한다.
function mnu_chick(obj,seq) {
	var tmpObj;
	for (var i=1;i<6 ;i++ )
	{
		tmpObj = eval("document.all.img"+i);
		if( tmpObj ) setOff(tmpObj);
	}
	setOn(obj);
	selectedObj = obj;
	parent.bottomFrame.location.href=link[seq];
	
}


function dashboard_chick() {
	parent.location.href = '../../../dashboard/summary/summary.jsp';	
}

// 각 메뉴의 마우스 오버시 이벤트
function mnu_over(obj) {
	setOn(obj);
}

// 각 메뉴의 마우스 아웃시 이벤트
// 현재 선택된 메뉴면 
function mnu_out(obj) {
	if (selectedObj != obj)	{ setOff(obj);	}
}

function setOn(obj)
{
	var oSrc = obj.src;
	re = /_off./i
	var nSrc = obj.src.replace(re, "_on.");
	obj.src=nSrc;
}
function setOff(obj)
{
	var oSrc = obj.src;
	re = /_on./i
	var nSrc = obj.src.replace(re, "_off.");
	obj.src=nSrc;
}
function system_wait() {
	alert('준비중입니다.');
}

function sms_check() {
	setOn(document.all.img3);
}

</script>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
        <td width="300" height="51"><img src="../../../images/top/top_logo.gif" onclick="mnu_chick(img1, 0);" style="cursor:hand;height:100%"></td>
        <td height="51"><table height="51" width="840" border="0" cellspacing="0" cellpadding="0">
  <tr>
      
<%
	
	if( arrMGmenu != null ) {
		//권한 1이면 정보검색만
		if( su.inarray(arrMGmenu, "1") ) {
%> 
              <td height="51" <%if(arrMGmenu.length > 1){out.println("width=\"138\"");}%> style="padding: 0px 0px 0px 0px;"><img  onclick="mnu_chick(this, 0);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m01_off.gif" name="img1" height="51"  border="0" style="cursor:hand;"></td>
	<!-- 
              <td width="88"><img onclick="mnu_chick(this, 1);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m02_off.gif" name="img2" width="88" height="37" border="0" style="cursor:hand;"></a></td>
              <td width="2"><img src="../../../images/top/top_menu_bar.gif" width="2" height="37"></td>
     -->
<%
		}
//		//권한 2이면 정보검색과 이슈관리
		if( su.inarray(arrMGmenu, "2") ) {
			
%>
			  <td height="51" width="138" style="padding: 0px 0px 0px 0px;"><img height="51" onclick="mnu_chick(this, 1);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m02_off.gif" name="img2" border="0" style="cursor:hand;"></td>

<%
		}

		if( su.inarray(arrMGmenu, "3") ) {
			
%>	
		
              <td height="51" width="138" style="padding: 0px 0px 0px 0px;"><img height="51" onclick="mnu_chick(this, 2);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m03_off.gif" name="img3" border="0" style="cursor:hand;"></td>
                      
<%
		}

 		if( su.inarray(arrMGmenu, "4") ) {
%>	
	 		  <td height="51" width="138" style="padding: 0px 0px 0px 0px;"><img height="51" onclick="mnu_chick(this, 3);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m04_off.gif" name="img4"  border="0" style="cursor:hand;"></td> 
        
<%
		}
		if( su.inarray(arrMGmenu, "6") ) {		
%>

		<td height="51" width="138" style="padding: 0px 0px 0px 0px;"><img height="51" onclick="dashboard_chick();" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m06_off.gif" name="img6" border="0" style="cursor:hand;"></td>
<%		
		}
		if( su.inarray(arrMGmenu, "5") ) {		
%>			
			  <td height="51" width="138" style="padding: 0px 0px 0px 0px;"><img height="51" onclick="mnu_chick(this, 4);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" src="../../../images/top/top_m05_off.gif" name="img5" border="0" style="cursor:hand;"></td>	
	
<%		
		}
	} 
%> 
			  <td width="100%" background="../../../images/top/menu_bg.jpg" align="right" style="padding:10px 10px 0px 0px;vertical-align:top">
			    <table border="0" cellspacing="0" cellpadding="0">
		          <tr>
					<td class="textwhite" style="vertical-align:middle"><img src="../../../images/top/top_ico01.gif" style="vertical-align:middle"> <strong><%=SS_M_NAME%></strong>님<br> 반갑습니다.</td>
		          	<td valign="middle" style="padding-left:10px"><a href="../../logout.jsp" target="_top"><img src="../../../images/top/top_logout.gif" /></a></td>
		          	<td valign="middle"><a href="../../search/search_env_setting.jsp" target="contentsFrame"><img src="../../../images/top/setting.gif" /></a></td>
		          	<!-- <td  align="right"  valign="middle"><img src="../../../images/top/top_logout.gif"  hspace="5" style="padding: 0px 0px 0px 0px;"><img src="../../../images/top/setting.gif" hspace="5" style="padding: 0px 0px 0px 0px;" ></td> -->
		          </tr>
	            </table>
	          </td>
	          </tr>
        </table>
     </td>
    </tr>
   </table>
 </body>
</html>
                

<script type="text/javascript">
<!--
// 최초 선택된 메뉴를 선택상태로 만든다.
// 선택된 메뉴가 없으면 검색메뉴를 선택상태로 만든다.
if (selectedObj==""){setOn(document.all.img1);}
else{setOn(selectedObj);}
//-->
</script>
