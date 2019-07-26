
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*
			,java.util.Hashtable
			,java.util.ArrayList
			,risk.search.userEnvMgr
			,risk.search.userEnvInfo
			,risk.admin.member.MemberDao
			,risk.admin.member.MemberBean
			,risk.util.*
			,risk.DBconn.DBconn
			,risk.admin.alimi.AlimiSettingDao
			,risk.search.MetaBean
            ,risk.search.MetaMgr
			,risk.admin.log.LogMgr
			,risk.admin.log.LogBean
			,risk.admin.log.LogConstants
			,risk.search.siteGroupInfo
			,risk.search.keywordInfo
			,risk.mobile.AlimiMgr
			,risk.mobile.AlimiBean"%>
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiMgr aMgr = new AlimiMgr();
	DateUtil du = new DateUtil();
	
	StringUtil su = new StringUtil();
	
	
	ArrayList alData = null;
	
	String dateFrom = "";
	String dateTo = "";
	String keyWord = "";
	String order = "";
	int sgSeq = 0;
	int iNowPage = 0;
	int mPageSize = 20;
	
	 
	
	String TEL = pr.getString("tel","");
	iNowPage = pr.getInt("nowpage",1);

    alData = aMgr.getAlimiLog( TEL, "6", iNowPage, mPageSize);
    
    
    int mTotalPage = aMgr.pageCount + (aMgr.totalCount % mPageSize > 0 ? 1 : 0);
    

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
<input type="hidden" name="tel" value = "<%=TEL%>">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="totalpage" value="<%=mTotalPage%>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="1" bgcolor="#d3d3d3" style="padding:0px 13px 0px 13px"></td>
  </tr>
  
  <%
  for(int i = 0; i < alData.size(); i++)
  {
	   AlimiBean aBean = (AlimiBean)alData.get(i);
  
  %>
  <tr>
  
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue">
        <a href="<%=aBean.getMtUrl()%>" target="_blank"><%=su.subStrTitle(aBean.getMtTitle(), 20)%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=aBean.getMtContent() %></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=aBean.getSdName()%></span> <span class="menu_gray02"><%=aBean.getFormatMtDate("MM-dd HH:mm")%></span></td>
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
