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
	TierSiteMgr msMgr = new TierSiteMgr();
	TierSiteBean msBean = new TierSiteBean();
	
	String ts_seq = pr.getString("ts_seq");		
	String ts_name = pr.getString("ts_name");	
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


	/* function fnTextCheck(obj) 
	{		
	    if (/[^0-9.,]/g.test(obj.value))
	    {	    	
	        var text1 = obj.value.substring(0, obj.value.length - 1);       
	        obj.value='';
	        obj.focus();
	        alert('숫자만 입력 가능합니다.');
	        return false;
	    }else{
	        return true;
	    }
	} */

	script.append("opener.parent.init(); self.close();");
	
	function insertTierSite()
	{					
		$('#mode').val("insert");
		$('#tierForm').attr('target','processFrm');
		$('#tierForm').attr('action','tier_prc.jsp');
		$('#tierForm').submit();

		setTimeout('opener.search()', '500');
		setTimeout('opener.opener.searchTierSite()', '1000');
		setTimeout('self.close()','1500');					
	}
	
</script>
</head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe name="processFrm" height="0" border="0"></iframe>
<form id="tierForm" name="tierForm" method="post" onsubmit="return false;">
<input type="hidden" id="mode" name="mode">
<input type="hidden" id="ms_seq" name="ts_seq" value="<%=ts_seq%>">



<table width="300" height="40" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="38" background="../../../images/admin/tier/pop_top.gif"><table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="240" class="textwbig" style="padding-left: 35px"><strong>매체 등록</strong></td>
        <td width="60" align="right" class="pop_titleBIG"><img src="../../../images/admin/tier/pop_close_01.gif" width="60" height="24" onclick="window.close();"></td>
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
                <td width="100" height="32" colspan="3" align="center" bgcolor="f6f6f6">사이트이름</td>
                <td width="160" height="32" colspan="3" align="center" bgcolor="ffffff"><input type="text" class="input_text" id="ts_name" name="ts_name" value="<%=ts_name%>"></td>
              </tr>              
              <tr>
                <td width="100" height="32" colspan="3" align="center" bgcolor="f6f6f6">Tiering</td>
                <td width="160" height="32" colspan="3" align="center" bgcolor="ffffff"><select id="ts_type" name="ts_type"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select></td>
              </tr>
              <!-- <tr>
                <td width="100" height="32" colspan="3" align="center" bgcolor="f6f6f6">Rank</td>
                <td width="160" height="32" colspan="3" align="center" bgcolor="ffffff"><input type="text" class="input_text" id="ts_rank" name="ts_rank" value="" onkeyup="fnTextCheck(this);"></td>
              </tr> -->                            
          </table></td>
        </tr>
        <tr>
	   		<td height="10">&nbsp;</td>
	    </tr>
	    <tr>
	   		<td align="center"><img src="../../../images/admin/tier/write_btn.gif" width="55" height="23" style="cursor:pointer" onclick="insertTierSite();"></td>
	    </tr>
      </table>
      </td>
  </tr>
</table>
</form>
</body>
</html>

