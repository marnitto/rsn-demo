
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*
			,java.util.Hashtable
			,java.util.ArrayList
			,risk.search.userEnvMgr
			,risk.search.userEnvInfo
			,risk.admin.member.MemberDao
			,risk.admin.member.MemberBean
			,risk.search.PortalBean
			,risk.util.*
			,risk.DBconn.DBconn
			,risk.admin.alimi.AlimiSettingDao
            ,risk.search.MetaMgr
			,risk.admin.log.LogMgr
			,risk.admin.log.LogBean
			,risk.admin.log.LogConstants
			,risk.search.siteGroupInfo
			,risk.search.keywordInfo"%>
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	MetaMgr     smgr = new MetaMgr();
	userEnvMgr uemng = new userEnvMgr();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	
	
	ArrayList alData = null;
	
	String dateFrom = "";
	String dateTo = "";
	String keyWord = "";
	String order = "";
	int iNowPage = 0;
	
	iNowPage = pr.getInt("nowpage",1);
	dateFrom = pr.getString("dateFrom",du.getCurrentDate("yyyy-MM-dd"));
	dateTo = pr.getString("dateTo",du.getCurrentDate("yyyy-MM-dd"));
	keyWord = pr.getString("keyWord","");
	order = pr.getString("order","MD_DATE DESC");
	
	//세팅 정보 가져오기 Type = 2 app setting
	Hashtable htAlimiSetting = asDao.getMobileAlimiSetting("2");
	
	String script = "";
	
	String MA_SEQ = "";
	String MA_ACTIVE = "";
	//String K_XP = "";
	//String SG_SEQ = "";
	String MT_TYPE = "";
	String MA_COUNT = "";
	String MA_TYPE = "";
	
	MA_SEQ = (String)htAlimiSetting.get("MA_SEQ");	
	MA_ACTIVE = (String)htAlimiSetting.get("MA_ACTIVE");	
	MT_TYPE = (String)htAlimiSetting.get("MT_TYPE");	
	MA_COUNT = (String)htAlimiSetting.get("MA_COUNT");	
	MA_TYPE = (String)htAlimiSetting.get("MA_TYPE");
	
	alData = smgr.getPortalList(
					            iNowPage,
					            Integer.parseInt(MA_COUNT), 
					            dateFrom,   
					            dateTo,   
					            "");
	
	int mTotalPage  = smgr.portalCnt / Integer.parseInt(MA_COUNT) + (smgr.portalCnt % Integer.parseInt(MA_COUNT) > 0 ? 1 : 0);
%>
<html>
<head>
<title>SPEED</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<script language="JavaScript" type="text/javascript">

function fn_before()
{
	var nowPage = Number(document.mobile_list.nowpage.value);
	
	if( nowPage > 1)
	{
		document.mobile_list.nowpage.value = nowPage - 1;
		document.mobile_list.submit();
	}
}
function fn_next()
{
	var nowPage = Number(document.mobile_list.nowpage.value);
	var totalPage = Number(document.mobile_list.totalpage.value);
	
	if( nowPage < totalPage)
	{
		document.mobile_list.nowpage.value = nowPage + 1;
		document.mobile_list.submit();
	}
}

</script>
<style type="text/css">
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(images/login_bg01.gif);
	background-color: #384b5a;
}
-->
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="mobile_list" method="post">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="totalpage" value="<%=mTotalPage%>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="42" background="images/list_top_gray.gif" style="padding:4px 13px 0px 13px">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    
        <td align="right"><strong class="menu_gray" >총 <%=smgr.portalCnt%>건&nbsp;&nbsp;</strong><strong class="menu_gray" > <%=Integer.toString(iNowPage)%>/<%=mTotalPage%>Page</strong></td>
        
        </tr>
        
<!--        <tr>-->
<!--        <td align="right"><strong class="menu_gray" > <%=Integer.toString(iNowPage)%>/<%=mTotalPage%>Page</strong></td>-->
<!--        </tr>-->
        
        </table>
        </td>
        
  </tr>
  <tr>
    <td height="1" bgcolor="#d3d3d3" style="padding:0px 13px 0px 13px"></td>
  </tr>
  
  <%
  for(int i = 0; i < alData.size(); i++)
  {
	  //MetaBean mBean = (MetaBean) alData.get(i);
	  PortalBean pBean = (PortalBean) alData.get(i);
  %>
  <tr>
  
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue">
        <a href="<%=pBean.getUrl()%>" target="_blank"><%=su.subStrTitle(pBean.getTitle(), 20)%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=pBean.getHighrightHtml(50,"0000CC", "삼성 sds")%>
        <%//=su.subStrTitle(mBean.getMd_content(), 100) %></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=pBean.getName()%></span> <span class="menu_gray02"><%=pBean.getFormatMd_date("yy/MM/dd HH:mm")%></span></td>
      </tr>
    </table></td>
    
  </tr>
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  
  </tr>  
  <%
  }
  %>
  

  <tr>
    <td height="2" background="images/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="40" background="images/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" background="images/list_bar01.gif" class="<%
        
             if(iNowPage <= 1){
            	 out.print("menu_gray");
             }else{
            	 out.print("textwhite02");
             }
 
         %>" onclick="fn_before();" style="background-repeat:no-repeat; font-weight: bold;">&lt; 이전</td>
         
        <td width="50%" align="center" background="images/list_bar01.gif" class="<%
        
             if(iNowPage >= mTotalPage){
            	 out.print("menu_gray");
             }else{
            	 out.print("textwhite02");
             }
 
         %>" onclick="fn_next();" style="background-repeat:no-repeat; font-weight: bold;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="images/list_line02.gif"></td>
  </tr>

</table>
</form>
</body>
</html>
