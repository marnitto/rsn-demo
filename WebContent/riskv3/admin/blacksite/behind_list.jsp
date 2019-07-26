<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.blacksite.BlackSiteBean,
                 risk.admin.blacksite.BlackSiteMgr,
                 risk.util.ParseRequest,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "BS_VALUE ASC");	//정렬 순서
	
	String str_word = "";
	String str_weight = "";
	String str_writer = "";
	String str_date = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = "";
	
	if( order.equals("BS_VALUE ASC") ) {
		str_word = "BS_VALUE DESC";		
		str_writer = "BS_FWRITER ASC";
		str_date = "BS_DATE DESC";
		ico_word = "▲";
	}else if( order.equals("BS_VALUE DESC") ) {
		str_word = "BS_VALUE ASC";		
		str_writer = "BS_FWRITER ASC";
		str_date = "BS_DATE DESC";
		ico_word = "▼";
	}else if( order.equals("BS_FWRITER ASC") ) {
		str_word = "BS_VALUE ASC";		
		str_writer = "BS_FWRITER DESC";
		str_date = "BS_DATE DESC";
		ico_writer = "▲";
	}else if( order.equals("BS_FWRITER DESC") ) {
		str_word = "BS_VALUE ASC";		
		str_writer = "BS_FWRITER ASC";
		str_date = "BS_DATE DESC";
		ico_writer = "▼";
	}else if( order.equals("BS_DATE DESC") ) {
		str_word = "BS_VALUE ASC";		
		str_writer = "BS_FWRITER ASC";
		str_date = "BS_DATE ASC";
		ico_date = "▼";
	}else if( order.equals("BS_DATE ASC") ) {
		str_word = "BS_VALUE ASC";
		str_weight = "BS_FWRITER DESC";		
		str_date = "BS_DATE DESC";
		ico_date = "▲";
	}
	
	BlackSiteMgr ekMgr = new BlackSiteMgr();
	BlackSiteBean[] arrEKbean = null;
	arrEKbean = ekMgr.getKeywordList(searchWord, order);	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="55%"><col width="10%"><col width="10%"><col width="10%"><col width="10%">
	<tr>               
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th onclick="excuteOrder('<%=str_word%>');" style="cursor:pointer">URL</th>
		<th onclick="excuteOrder('<%=str_writer%>');" style="cursor:pointer">등록자</th>
		<th onclick="excuteOrder('<%=str_date%>');" style="cursor:pointer">등록일</th>
		<th>수정자</th>
		<th>수정일</th>
	</tr>
<%
	if(arrEKbean != null && arrEKbean.length > 0){
		for(int i = 0; i < arrEKbean.length; i++){
%> 
	<tr>         
		<td><input type="checkbox" name="checkId" value="<%=arrEKbean[i].getBsSeq()%>"></td>
		<td><p class="board_01_tit" onclick="popupEdit('<%=arrEKbean[i].getBsSeq()%>','update');" style="cursor:pointer"><%=arrEKbean[i].getBsValue()%></p></td>
		<td><%=arrEKbean[i].getBsFwriter()%></td>
		<td><%=arrEKbean[i].getBsDate()%></td>
		<td><%=arrEKbean[i].getBsLwriter()%></td>
		<td><%=arrEKbean[i].getBsUpdate()%></td>
	</tr>
<%
		}
	}else{
%>
	<tr>
		<td colspan="6" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
	</tr>
<%
	}
%>
</table>