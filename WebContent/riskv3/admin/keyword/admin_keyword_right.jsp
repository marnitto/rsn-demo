<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="risk.admin.keyword.KeywordMng" %>
<%@ page import="risk.admin.keyword.KeywordBean" %>
				 

<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

    String xp          = pr.getString("xp");
    String yp          = pr.getString("yp");
    String zp          = pr.getString("zp");

	String value = "";
	String part = "total";
	String partname = "전체키워드그룹";
	List exkeylist = null;
	
	ArrayList arSgSeq = null;

	if( xp.equals("") ) xp = "0";
	if( yp.equals("") ) yp = "0";
	if( zp.equals("") ) zp = "0";

	KeywordMng keymng = KeywordMng.getInstance();
	

	if( xp.equals("0") || xp.equals("") ) {
	} else if( yp.equals("0") || yp.equals("") ) {
		part = "upkg";
		partname = "대분류";
		
		String[] arValue =  keymng.getKG(xp); 
		
		value = arValue[0];
		arSgSeq = keymng.getSgSeq(arValue[1]);
		
	} else if( zp.equals("0") || zp.equals("") ) {
		part = "downkg";
		partname = "소분류";
		value = keymng.getKG(xp,yp);
	} else {
		part = "kg";
		partname = "키워드";
		value = keymng.getKG(xp,yp,zp);
		exkeylist = keymng.getEXKG(xp,yp,zp);
	}
%>

<%@page import="java.util.ArrayList"%><html>
<head>
<title>RISK V3 - RSN</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"dotum"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F3F3F3;
}
	-->
	</style>
<script language="JavaScript" type="text/JavaScript">
<!--
	function edit_kg()
	{
		if( !keygroup.val.value )
		{
			alert('<%=partname%>명을 입력하십시요. ');
		} else {
			keygroup.mode.value = 'edit';
			keygroup.submit();
		}
	}

	function del_kg()
	{
		if( !keygroup.xp.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}

	function up_kg()
	{
		if( keygroup.yp.value > 0 ) {
			alert('위치변경은 대분류만 가능합니다.');
		} else if( !keygroup.xp.value ){
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'up';
			keygroup.submit();
		}
	}

	function down_kg()
	{
		if( keygroup.yp.value > 0 ) {
			alert('위치변경은 대분류만 가능합니다.');
		} else if( !keygroup.xp.value ){
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'down';
			keygroup.submit();
		}
	}

	function del_exkg()
	{
		if( !keygroup.type.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.tg.value = 'exkey';
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}

	function del_exkg()
	{
		if( !keygroup.type.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.tg.value = 'exkey';
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}


	function saveSiteGroup()
	{

		var f = document.keygroup;

		var strTmp = '';
		if(f.sgSeq){
			if(f.sgSeq.length){
				for(var i = 0; i < f.sgSeq.length; i++){
					if(f.sgSeq[i].checked == true){
						if(strTmp == ''){
							strTmp = f.sgSeq[i].value;
						}else{
							strTmp += ',' + f.sgSeq[i].value;
						}
					}
				} 
			}else{
				if(f.sgSeq.checked == true){
					strTmp = f.sgSeq.value;		
				}	
			}
		}		
		f.sg_seqs.value = strTmp;		

		keygroup.mode.value = 'sitegroup';
		keygroup.submit();

	}
//-->
</script>
</head>

<body>
<table width="315" border="0" cellspacing="0" cellpadding="0">
<form name="keygroup" action="admin_keyword_prc.jsp" method="post">
<input type="hidden" name="tg" value="<%=part%>">
<input type="hidden" name="mode">
<input type="hidden" name="xp" value="<%=xp%>">
<input type="hidden" name="yp" value="<%=yp%>">
<input type="hidden" name="zp" value="<%=zp%>">
<input type="hidden" name="sg_seqs">
  <tr>
    <td height="20" style="padding-left:7px"><img src="../../../images/admin/keyword/admin_ico02.gif" width="8" height="5" hspace="3" align="absmiddle"><span class="menu_black"><strong><%=value%><%if(!part.equals("total")) out.print(" - ");%><%=partname%></strong></span></td>
  </tr>
</table>
<table width="315" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" style="padding-left:10px">
	<table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr height="10">
        <td><img src="../../../images/admin/keyword/brank.gif" width="1" height="5"></td>
      </tr>
<%
	if( part.equals("upkg") || part.equals("downkg") )
	{
%>
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3"><%=partname%>명</td>
      </tr>
      <tr>
        <td height="25"><input name="val" type="text" size="36" value="<%=value%>" OnKeyDown="Javascript:if (event.keyCode == 13) { edit_kg();}">
          <img src="../../../images/admin/keyword/admin_modify.gif" width="45" height="18" align="absmiddle" onclick="edit_kg();" style="cursor:hand;"></td>
      </tr>
<%
	}
%>
<%
	if( part.equals("kg") )
	{
%>
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">제외단어 리스트</td>
      </tr>
      <tr height="10">
        <td></td>
      </tr>
      <tr>
        <td valign="top">
		<select name="type" multiple style="width=225px;height=200px;" class="t">
<%
		for(int i=0; i < exkeylist.size();i++) {
			KeywordBean exkeyinfo = (KeywordBean)exkeylist.get(i);
%>
			<option value="<%=exkeyinfo.getKGtype()%>"><%=exkeyinfo.getKGvalue()%></option>
<%
		}
%>
		</select><img src="../../../images/admin/keyword/admin_del.gif" width="45" height="18" border="0" onclick="del_exkg();" style="cursor:pointer;">
		</td>
      </tr>
<%
	} else {
%>
      <tr>
        <td>
		<table width="100%" height="200" border="1" cellspacing="0" cellpadding="10" bordercolor="#DDDDDD">
		  <tr >
		    <td valign="top">
		    <%
		    	
		    	//if(part.equals("upkg")){
		    		/*
		    	out.println("<table border='0' width='100%'>");
		    	out.println("	<tr>");
		    	out.println("	<td><table width='100%'><tr>");
		    	out.println("		<td><img src='../../../images/admin/keyword/admin_ico03.gif' width='10' height='11' hspace='3'>사이트 그룹</td>");
		    	out.println("		<td align='right'><img src='../../../images/admin/keyword/admin_save.gif' style='cursor:pointer;' onclick='saveSiteGroup();'></td>");
		    	out.println("	</tr></table></td>");
		    	out.println("	</tr>");
		    	out.println("	<tr><td><table width='100%'>");
		    	
		    	if(arSgSeq != null && arSgSeq.size() > 0){
		    		
		    		String[] arBean = null;
		    		for(int i = 0; i < arSgSeq.size(); i++){
		    			arBean = (String[])arSgSeq.get(i);
		    			
		    			if(i % 2 == 0){
		    				out.println("<tr>");
		    			}
		    			
		    			out.println("<td>");
		    			out.println("<input style='border:0px;' type='checkbox' name='sgSeq' value='"+arBean[0]+"' "+arBean[2]+">" + arBean[1]);
		    			out.println("</td>");
		    			
		    			if(i % 2 == 1){
		    				out.println("</tr>");
		    			}
		    		}
		    	
		    	}
		    	out.println("</table></td></tr>");
		    	
		    		
		    	out.print("</table>");	
		    	*/
		    	//}else{
		    %>
				<strong>키워드그룹 설정안내</strong><br><br>
				<b>추가</b> - 좌측메뉴에서 선택한 뒤 하단의 추가버튼을 누르시면 하위 그룹 및 키워드가 추가됩니다.<br>
				<b>수정</b> - 좌측메뉴에서 수정을 원하시는 그룹 및 키워드를 선택한뒤 우측메뉴에서 수정하시면 됩니다.<br>
				<b>삭제</b> - 대분류,소분류,키워드의 삭제는 좌측메뉴에서 제외키워드의 삭제는 우측메뉴에서 가능합니다.<br>
				<b>위치변경</b> - 변경을 원하시는 대분류를 선택한 뒤 좌측메뉴 하단의 상,하 버튼을 누르시면 됩니다.		    
		    
		    <%		
		    //	}
		    %>

			</td>
		  </tr>
		</table>
		</td>
      </tr>
<%
	}
%>
    </table></td>
  </tr>
</form>
</table>
</body>
</html>