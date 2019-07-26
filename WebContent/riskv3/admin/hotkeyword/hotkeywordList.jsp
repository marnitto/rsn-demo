<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="risk.util.*" %>
<%@ page import="risk.admin.hotkeyword.hotkeywordMgr" %>
<%@ page import="risk.admin.hotkeyword.hotkeywordBean" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pr.printParams();
	
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();
	
	ArrayList dataList = new ArrayList();
	dataList = hm.getHotkeyword("", "");
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="50%"><col width="15%"><col width="15%"><col width="15%">
	<tr>           
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th>키워드명</th>
		<th>사용여부</th>
		<th>등록자</th>
		<th>등록일</th>
	</tr>
<%
if(dataList.size() > 0){
	for(int i = 0; i < dataList.size(); i++){
		hb = (hotkeywordBean)dataList.get(i);
%> 
	<tr>
		<td><input type="checkbox" name="checkId" value="<%=hb.getH_seq()%>"></td>
		<td style="cursor:pointer" onclick="popUpdate('<%=hb.getH_seq()%>')"><p class="board_01_tit"><%=hb.getH_name()%></p></td>
		<td><%=hb.getH_useyn()%></td>
		<td><%=hb.getM_name()%></td>
		<td><%=hb.getH_regdate().substring(5, 16)%></td>
	</tr>
<%
	}
}else{
%>
	<tr>
		<td colspan="5" style="font-weight: bold" align="center">조건에 맞는 데이터가 없습니다.</td>
	</tr>
<%
}
%>
</table>