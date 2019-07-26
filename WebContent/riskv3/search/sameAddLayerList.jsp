<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%
	ParseRequest pr = new ParseRequest(request);
	MetaMgr metaMgr = new MetaMgr();
	MetaBean metaBean = new MetaBean();
	DateUtil du = new DateUtil();
	
	String keyword = pr.getString("keyword", "");
	String md_seq = pr.getString("md_seq", "");
	
	String today = "";
	today = du.getCurrentDate("yyyy-MM-dd");
	
	ArrayList dataList = new ArrayList();
	dataList = metaMgr.getMetaDataBySameData(keyword, today, md_seq);
%>
<%@include file="../inc/sessioncheck.jsp"%>
<div style="height:150px;overflow-y:auto">
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cccccc" style="table-layout: fixed;">
<%
if(dataList.size() > 0){
%>
	<col width="7%"><col width="20%"><col width="53%"><col width="20%">
<%
	for(int i = 0; i < dataList.size(); i++){
		MetaBean mb = (MetaBean)dataList.get(i);
%>
	<tr bgcolor="ffffff" style="padding: 5px 3px 5px 3px">
		<td><input type="checkbox" name="same_md_pseq" id="same_md_pseq" value="<%=mb.getMd_pseq()%>"></td>
		<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%=mb.getMd_site_name()%>"><%=mb.getMd_site_name()%></td>
		<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%=mb.getMd_title()%>"><%=mb.getMd_title()%></td>
		<td><%=mb.getMd_date().substring(5, 16)%></td>
	</tr>
<%
	}
}else{
%>
	<tr bgcolor="ffffff" style="padding: 5px 3px 5px 3px">
		<td align="center" valign="top" style="font-weight:bold;height:135px;vertical-align: middle"><font color="red" style="font-weigth:bold"><%=keyword%></font>에 해당하는 데이터가 없습니다.</td>
	</tr>
<%
}
%>
</table>
</div>