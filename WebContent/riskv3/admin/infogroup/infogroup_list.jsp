<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.admin.info.*"%>
<%@ page import="risk.util.*"%>
<%@ page import="java.net.*"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pr.printParams();
	String mgseq = pr.getString("mgseq",SS_MG_NO);
	
	ArrayList arr = new ArrayList();
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	//arr = igMgr.getInfoGroup("");
	arr = igMgr.infoinfo(mgseq);
	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="55%"><col width="10%"><col width="15%"><col width="15%">
	<tr>           
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th>정보그룹</th>
		<th>사용여부</th>
		<th>등록자</th>
		<th>등록일</th>
	</tr>
<%
		if(arr.size() > 0)
		{
			for(int i =0; i<arr.size(); i++)
			{
				igBean = (InfoGroupBean)arr.get(i);
%> 
	<tr>
		<td><input type="checkbox" name="checkId" value="<%=igBean.getI_seq()%>"></td>
		<td style="cursor:pointer" onclick="popupEdit('<%=igBean.getI_seq()%>','update');"><p class="board_01_tit"><%=igBean.getI_nm()%></p></td>
		<td><%=igBean.getI_useyn()%></td>
		<td><%=igBean.getM_name()%></td>
		<td><%=du.getDate(igBean.getI_regdate(),"yyyy/MM/dd")%></td>
	</tr>
<%
				}
		}else{
%>
	<table align="center">
	<tr>
		<td align="center">선택 하신 사용자그룹 데이터가 없습니다.</td>
	</tr>
	</table>
<%
		}
%>
</table>