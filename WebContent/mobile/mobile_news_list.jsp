
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
			,risk.mobile.MobileMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	MetaMgr     smgr = new MetaMgr();
	userEnvMgr uemng = new userEnvMgr();
	DateUtil du = new DateUtil();
	MobileMgr mMgr = new MobileMgr();
	StringUtil su = new StringUtil();
	
	
	ArrayList alData = null;
	
	String dateFrom = "";
	String dateTo = "";
	String keyWord = "";
	String order = "";
	int sgSeq = 0;
	int iNowPage = 0;
	
	iNowPage = pr.getInt("nowpage",1);
	dateFrom = pr.getString("dateFrom",du.getCurrentDate("yyyy-MM-dd"));
	dateTo = pr.getString("dateTo",du.getCurrentDate("yyyy-MM-dd"));
	keyWord = pr.getString("keyWord","");
	order = pr.getString("order","MD_DATE DESC");
	sgSeq = Integer.parseInt(pr.getString("SG_SEQ","0"));
	String kXp = pr.getString("K_XP","0");
	
	
	
	
	//세팅 정보 가져오기 Type = 2 app setting
	Hashtable htAlimiSetting = asDao.getMobileAlimiSetting("2");
	
	String script = "";
	
	String MA_SEQ = "";
	String MA_ACTIVE = "";
	String K_XP = "";
	String SG_SEQ = "";
	String MT_TYPE = "";
	String MA_COUNT = "";
	String MA_TYPE = "";
	
	MA_SEQ = (String)htAlimiSetting.get("MA_SEQ");	
	MA_ACTIVE = (String)htAlimiSetting.get("MA_ACTIVE");	
	K_XP = (String)htAlimiSetting.get("K_XP");	
	SG_SEQ = (String)htAlimiSetting.get("SG_SEQ");	
	MT_TYPE = (String)htAlimiSetting.get("MT_TYPE");	
	MA_COUNT = (String)htAlimiSetting.get("MA_COUNT");	
	MA_TYPE = (String)htAlimiSetting.get("MA_TYPE");
	
	
	/* 키워드 그룹 *********************************/
	ArrayList KGlist = uemng.getKeywordGroup(K_XP);
	keywordInfo kgi = null; 
	
	if(!kXp.equals("0")){
		K_XP = kXp;
	}
	/*********************************************/
	
	/* 사이트 그룹 *********************************/
    ArrayList alGroup = smgr.getSiteGroup(SG_SEQ);
    siteGroupInfo sgi = null;
    
    if(sgSeq != 0){
    	SG_SEQ = Integer.toString(sgSeq);	
    }
	/*********************************************/
	
	
	
    alData = mMgr.getKeySearchList(
                                   iNowPage                                     ,   //int    piNowpage ,
                                   Integer.parseInt(MA_COUNT)         ,   //int    piRowCnt  ,
                                   K_XP                                ,   //String psXp      ,
                                   ""                                ,   //String psYp      ,
                                   ""                                ,   //String psZp      ,
                                   SG_SEQ                              ,   //String psSGseq   ,
                                   ""                              ,   //String psSDgsn   ,
                                   dateFrom         ,   //String psDateFrom,
                                   dateTo           ,   //String psDateTo  ,
                                   keyWord                             ,   //String psKeyWord ,
                                   MT_TYPE                              ,   //String psType
                                   order      //String psOrder
                                   ,""
                                   ,"");
	
	// 총 사이즈를 반환한다.
	/*
	int mTotalConut = mMgr.TotalCount( K_XP     // 키워드 그룹 //
			                         , SG_SEQ   // 사이트 그룹 //
			                         , MT_TYPE  // 정보유형       //
			                         , keyWord  // 키워드            //
			                         , dateFrom // 시작일자       //
			                         , dateTo   // 종료일자       //
			                         );
	*/	
	
	int mTotalPage  = mMgr.mBeanPageCnt + (mMgr.mBeanTotalCnt % Integer.parseInt(MA_COUNT) > 0 ? 1 : 0); 
	
// 휴대폰 화면 사이즈 불러와서 width 지정
%>
<html>
<head>
<title>SPEED</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<script language="JavaScript" type="text/javascript">

function fn_selectBox_Change()
{	
	document.mobile_list.nowpage.value = 1;
	document.all.SG_SEQ.value = document.all.sgSeq.value;
	document.all.K_XP.value = document.all.kXp.value;
	document.mobile_list.submit();
}
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
<input type="hidden" name="SG_SEQ" value = "<%=sgSeq%>">
<input type="hidden" name="K_XP" value = "<%=kXp%>">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="totalpage" value="<%=mTotalPage%>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="42" background="images/list_top_gray.gif" style="padding:4px 13px 0px 13px">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    
    <td rowspan="2" align="left"><select name="kXp" onchange="fn_selectBox_Change();">
      <option value="0" selected>전체 키워드</option>
      <%
      for(int i = 0; i < KGlist.size(); i++)
      {
      	kgi = (keywordInfo)KGlist.get(i);	
      %>
      <option value="<%=kgi.getK_Xp()%>" <% if(kgi.getK_Xp().equals(kXp)) out.print("selected"); %>><%=kgi.getK_Value()%></option>
      <%
      }
      %>
    </select>
      <select name="sgSeq" onchange="fn_selectBox_Change();">
        <option value="0" >전체 사이트</option>
        <%
        for(int i = 0; i < alGroup.size(); i++)
        {
        	sgi = (siteGroupInfo)alGroup.get(i);
        	
        %>
        <option value="<%=sgi.getSg_seq()%>" <% if(sgi.getSg_seq() == sgSeq) out.print("selected"); %>><%=sgi.getSd_name()%></option>
        <%
        }
        %>
        </select></td>
        <td align="right"><strong class="menu_gray" >총 <%=Integer.toString(mMgr.mBeanTotalCnt)%>건</strong></td>
        
        </tr>
        
        <tr>
        <td align="right"><strong class="menu_gray" > <%=Integer.toString(iNowPage)%>/<%=mTotalPage%>Page</strong></td>
        </tr>
        
        </table>
        </td>
        
  </tr>
  <tr>
    <td height="1" bgcolor="#d3d3d3" style="padding:0px 13px 0px 13px"></td>
  </tr>
  
  <%
  for(int i = 0; i < alData.size(); i++)
  {
	  MetaBean mBean = (MetaBean) alData.get(i);
  %>
  <tr>
  
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue">
        <a href="<%=mBean.getMd_url()%>" target="_blank"><%=su.subStrTitle(mBean.getMd_title(), 20)%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=su.cutKey(su.toHtmlString(mBean.getMd_content()), mBean.getK_value(), 50, "0000CC")%>
        <%//=su.subStrTitle(mBean.getMd_content(), 100) %></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=mBean.getMd_site_name()%></span> <span class="menu_gray02"><%=mBean.getFormatMd_date("MM-dd HH:mm")%></span></td>
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
