<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.search.GetKGMenu_t
                 ,risk.util.DateUtil
                 ,risk.util.ParseRequest
                 ,risk.util.StringUtil
                 ,risk.search.MetaMgr
                 ,risk.search.userEnvInfo
                 ,risk.search.userEnvMgr
                 ,risk.util.ConfigUtil"

%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
    ConfigUtil cu = new ConfigUtil(); 
    String showCount=cu.getConfig("ShowKeywrodCount");
    
    if ( uei.getK_xp().equals("0")) uei.setK_xp("");   	        
    if ( uei.getK_yp().equals("0")) uei.setK_yp("");
    if ( uei.getK_yp().equals("0")) uei.setK_zp("");

    DateUtil        du = new DateUtil();
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr       sMgr = new MetaMgr();
    userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
    
    String IDXcnt = sMgr.getIdxDelCNT(SS_M_NO);

	GetKGMenu_t kg = new GetKGMenu_t();

	int xp = Integer.parseInt(su.nvl(uei.getK_xp(),"0"));  
	int yp = Integer.parseInt(su.nvl(uei.getK_yp(),"0"));  
	int zp = Integer.parseInt(su.nvl(uei.getK_zp(),"0")); 

	String sideMenu = pr.getString("menu", "ALLKEY");
	
	String sCurrentDate = du.getCurrentDate("yyyy-MM-dd");
	
    if ( uei.getDateFrom()==null) uei.setDateFrom(sCurrentDate);
    if ( uei.getDateTo()==null) uei.setDateTo(sCurrentDate);
	kg.setSelected(xp,yp,zp);
	kg.setBaseTarget("top.bottomFrame.contentsFrame.document");
	kg.setBaseURL("search_main_contents_t.jsp?searchmode=ALLKEY");	
	
	String kgHtml="";
	kgHtml   = kg.GetHtml( uei.getDateFrom(), uei.getDateTo(), uei.getMg_xp(), showCount );
    
	String kgScript = kg.GetScript();
	String kgStyle  = kg.GetStyle();
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
<!--
	function delete_all(){
		
		if(confirm('휴지통의 정보를 모두 삭제하시겠습니까? \n 삭제된 정보는 복구할 수 없습니다.')){
			
			top.bottomFrame.contentsFrame.idxProcess('delAll');
			
		}else{
			return;
		}
	}

-->
</script>
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<style>
<!--
body {
	scrollbar-face-color: #FFFFFF;
	scrollbar-shadow-color:#B3B3B3;
	scrollbar-highlight-color:#B3B3B3;
	scrollbar-3dlight-color: #FFFFFF;
	scrollbar-darkshadow-color: #EEEEEE;
	scrollbar-track-color: #F6F6F6;
	scrollbar-arrow-color: #8B9EA6;
}
-->
</style>
<%
	out.println(kgStyle);
	out.println(kgScript);
%>
<body bgcolor="#FFFFFF" leftmargin="5" topmargin="4" >
<form name="leftmenu" style="display: <%if(sideMenu.equals("SOLR") || sideMenu.equals("PORTAL") || sideMenu.equals("INITALL")){out.print("none");}%>">
<table style="width:100%;table-layout: fixed;">

<%
    out.println(kgHtml);
%>
</table>
<%
	boolean menuCheck = false;
	String[] mg_menu = env.getMg_menu().split(","); 
	for(int i=0; i<mg_menu.length; i++){
		if(mg_menu[i].equals("2") || mg_menu[i].equals("3")){
			menuCheck = true;
		}
	}

	if(menuCheck){
%>
<div style="width:100%;">
<span style="width:10%;">
<img class="kgmenu_img" src="../../images/search/del_ico.gif" onClick="parent.chageSearchMode('DELIDX', parent.document.all.allkey);" border="0" align=absmiddle>
</span>
<span style="width:60%;" class="kgmenu" id="imgID_0_0_0" onclick="parent.chageSearchMode('DELIDX', this);kg_click(this);" onmouseover="kg_over(this);"onmouseout="kg_out(this);"><b>휴지통</b> 
<span class="kgmenu_cnt">[<%=IDXcnt%>]</span></span>
<span style="width:20%;valign:bottom;padding: 0 0 0 9;" class="kgmenu_cnt">
	<a style="cursor:hand;" onclick="delete_all();"><img src="../../images/search/empty_ico.gif" ></a>
</span>
</div>

<%} %>
 <br>
<table>
   <tr>
     <td style="font-size:11px; color:#666666; padding-left:0px; padding-bottom:10px;">* 키워드별 카운트는 오늘 날짜의 유사기사를 포함한 수치입니다.</td>
   </tr>      
</table>   
</form>
</body>
</html>