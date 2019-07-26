<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.util.ParseRequest"%>
<%
	ParseRequest pr = new ParseRequest(request);
	MetaMgr metaMgr = new MetaMgr();
	MetaBean metaBean = new MetaBean();
	
	String md_seq = pr.getString("md_seq", "");
	
	if(!md_seq.equals("")){
		metaBean = metaMgr.getMetaData(md_seq);
	}
%>
<%@include file="../inc/sessioncheck.jsp"%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>유사묶기</p>
			<span><a href="javascript:showLayer();"><img src="../../images/search/pop_tit_close.gif"></span>
		</td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cccccc">
			<tr bgcolor="ffffff" style="padding: 5px 3px 5px 3px">
				<td bgcolor="#e7f9ff" style="font-weight:bold;width:80px">모기사 제목</td>
				<td><%=metaBean.getMd_title()%></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="3"></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cccccc">
			<tr bgcolor="ffffff" style="padding: 5px 3px 5px 3px">
				<td bgcolor="#e7f9ff" style="font-weight:bold;width:80px">기사 검색</td>
				<td><input type="text" name="same_keyword" id="same_keyword" class="txtbox" onkeyup="javascript:if(event.keyCode == 13){searchData();}"><input type="text" style="display:none"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="3"></td>
	</tr>
	<tr>
		<td style="height:150px" id="listArea"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cccccc">
			<tr bgcolor="ffffff" style="padding: 5px 3px 5px 3px">
				<td align="center" valign="top" style="font-weight:bold;height:135px;vertical-align: middle">기사를 검색해주세요.</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="3"></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr style="padding: 5px 3px 5px 3px">
				<td style="text-align:center;">
					<img src="../../images/search/btn_save_2.gif" onclick="sameAddSave();" style="cursor:pointer;"/>
					<img src="../../images/search/btn_cancel.gif" onclick="showLayer();" style="cursor:pointer;"/>
				</td>
			</tr>
		</table></td>
	</tr>
</table>
