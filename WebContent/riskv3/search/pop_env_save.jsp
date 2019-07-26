<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.ParseRequest,
				 risk.admin.keyword.KeywordMng,
				 risk.admin.site.SiteMng,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo
                 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
//	전송된 인자값을 SYSTEM에 뿌린다.

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	KeywordMng kgmng = new KeywordMng();
	SiteMng sgmng = new SiteMng();
	userEnvMgr env = new userEnvMgr();
	String interval = "0";

	String xp = pr.getString("xp");
	String type = pr.getString("type");
	String sg_seq = pr.getString("sg_seq");
	String searchtype = pr.getString("searchtype");
	
	if(pr.getString("interval")!=null);
	interval = pr.getString("interval");

	String xp_txt = null;
	String type_txt = null;
	String sg_seq_txt = null;
	String interval_txt = null;
	String searchtype_txt = null;

	userEnvInfo userenv = env.queryUserEnv(SS_M_ID);

	if( xp.length() == 0 ) {
		xp_txt = "전체키워드그룹";
		xp = "0";
	} else {
		xp_txt = kgmng.getKG(xp)[0];
	}

	if( type.equals("1") )
	type_txt = "기사";
	else if( type.equals("2") )
	type_txt = "게시";
	else if( type.equals("3") )
	type_txt = "기사, 게시";
	else if( type.equals("4") )
	type_txt = "공지";
	else if( type.equals("5") )
	type_txt = "기사, 공지";
	else if( type.equals("6") )
	type_txt = "게시, 공지";
	else 
	type_txt = "전체";

	if( sg_seq.length() == 0 ) sg_seq_txt = "전체사이트그룹";
	else sg_seq_txt = sgmng.getSG(sg_seq);

	if( interval.length() == 0 ) interval_txt = "중지";
	else interval_txt = interval+" 분 마다";

	if( searchtype.equals("1") ) searchtype_txt = "보이기";
	else searchtype_txt = "보이지 않기";

	env.saveUserEnv( xp, userenv.getSt_interval_day(), type, sg_seq,"", interval, userenv.getSt_list_cnt(), searchtype, userenv.getM_seq());

%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
	.textwbig { font-family: "", "u"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal}
	-->
	</style>

<script language="JavaScript" type="text/JavaScript">
<!--
	//window.setTimeout("window.close();",1000);
//-->
</script>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="400" height="40" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13" background="images/pop_topbg.gif"><img src="images/pop_topimg01.gif" width="13" height="40"></td>
    <td width="374" background="images/pop_topbg.gif" class="textwbig"><img src="images/pop_ico01.gif" width="3" height="11"> <strong>현재검색조건 기본설정</strong></td>
    <td width="13" align="right" background="images/pop_topbg.gif"><img src="images/pop_topimg02.gif" width="13" height="40"></td>
  </tr>
</table>
<table width="400" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#E8E8E8"><table width="100%" border="0" cellspacing="5" cellpadding="5">
      <tr>
        <td height="20" align="center" valign="top" bgcolor="#FFFFFF">
		<table width="378" border="0" cellspacing="0" cellpadding="0">
		<col width="8"><col width="90"><col width="281">
          <tr>
            <td><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>키워드그룹</strong></td>
            <td align="left" style="padding: 3px 0px 0px 0px;">: <%=xp_txt%></td>
          </tr>
		  <tr>
            <td><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>정보유형</strong></td>
            <td align="left" style="padding: 3px 0px 0px 0px;">: <%=type_txt%></td>
          </tr>
          <tr>
            <td><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>검색대상</strong></td>
            <td align="left" style="padding: 3px 0px 0px 0px;">: <%=sg_seq_txt%></td>
          </tr>
          <tr>
            <td><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>자동 새로고침</strong></td>
            <td align="left" style="padding: 3px 0px 0px 0px;">: <%=interval_txt%></td>
          </tr>
          <tr>
            <td><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
            <td align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>메뉴표시</strong></td>
            <td align="left" style="padding: 3px 0px 0px 0px;">: <%=searchtype_txt%></td>
          </tr>
        </table>
          <table width="378" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="20"><img src="images/brank.gif"></td>
            </tr>
            <tr>
              <td> ※ 결과내검색 환경을 저장하였습니다.</td><td><img src="images/pop_btn_ok.gif" height="23" width="39" style="cursor:hand;" onclick="window.close();"></td>
            </tr>
          </table>
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>