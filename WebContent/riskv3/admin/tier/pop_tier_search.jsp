<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="
                java.util.ArrayList
                ,risk.util.*
                ,risk.admin.tier.*        
                "
%>  
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	ArrayList arr = new ArrayList();	
	TierSiteMgr tsMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String seach_ts_name = pr.getString("seach_ts_name");
	String ms_year = pr.getString("ms_year");
	arr = tsMgr.getSiteList(0,0,seach_ts_name);	
	
%>
<html>
<head>
<title>메체등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/tier/basic.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">

	function search()
	{
		$('#tierForm').attr('target','');
		$('#tierForm').attr('action','pop_tier_search.jsp');
		$('#tierForm').submit();
	}

	function popSiteEditForm(ts_seq,ts_name)
	{		
		
		$('#ts_seq').val(ts_seq);
		$('#ts_name').val(ts_name);		
		
		popup.openByPost('tierForm','pop_tier_edit.jsp',320,280,false,true,false,'pop_tier_edit1');
	}

	
</script>
</head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe name="processFrm" height="0" border="0"></iframe>
<form id="tierForm" name="tierForm" method="post" onsubmit="return false;">
<input type="hidden" id="mode" name="mode">
<input type="hidden" id="ts_seq" name="ts_seq">
<input type="hidden" id="ts_name" name="ts_name">


<table width="300" height="40" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="38" background="../../../images/admin/tier/pop_top.gif"><table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="240" class="textwbig" style="padding-left: 35px"><strong>매체 검색</strong></td>
        <td width="60" align="right" class="pop_titleBIG"><img src="../../../images/admin/tier/pop_close_01.gif" width="60" height="24" style="cursor:pointer;" onclick="window.close();"></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="300" cellSpacing=5 cellPadding=0  bgColor=#e8e8e8 border=0>
  <tr>
    <td align="center" bgcolor="#FFFFFF" style="padding: 10px 10px 10px 10px;">
      <table width="260" border="0" cellspacing="0" cellpadding="0">        
        <tr>
          <td><table width="260" border="0" cellpadding="0" cellspacing="1" bgcolor="cdd4d6">
              
              <tr>
                <td height="32" colspan="3" align="center" bgcolor="f6f6f6"><input id="seach_ts_name" name="seach_ts_name" type="text" class="input_text" style="width:200"  onkeydown="if(event.keyCode==13)search();"/>
                    <img src="../../../images/admin/tier/search_btn.gif" width="51" height="23" align="absmiddle" style="cursor:pointer;" onclick="search();">
                </td>
              </tr>
              
<%
	if(arr.size()>0)
	{

			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);
%> 		
		
				<tr>
	              <td width="205" height="30" align="left" bgcolor="#FFFFFF" title="<%=tsBean.getTs_name()%>"><nobr style ="text-overflow:ellipsis; overflow:hidden; width:100px"><%=tsBean.getTs_name()%></nobr></td>	             
	              <td width="55" align="center" bgcolor="#FFFFFF"><img src="../../../images/admin/tier/write_btn.gif" width="55" height="23" style="cursor:pointer" onclick="popSiteEditForm('<%=tsBean.getTs_seq()%>','<%=tsBean.getTs_name()%>');"></td>
	            </tr>                    
<%
			}

	}
%>
            
          </table></td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</form>
</body>
</html>

