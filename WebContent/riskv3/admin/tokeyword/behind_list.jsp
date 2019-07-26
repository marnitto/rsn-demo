<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.tokeyword.ToKeywordBean,
                 risk.admin.tokeyword.ToKeywordMgr,
                 risk.util.ParseRequest,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "EK_VALUE ASC");	//정렬 순서
	
	String str_word = "";
	String str_weight = "";
	String str_writer = "";
	String str_date = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = "";
	
	if( order.equals("EK_VALUE ASC") ) {
		str_word = "EK_VALUE DESC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▲";
	}else if( order.equals("EK_VALUE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▼";
	}else if( order.equals("EK_FWRITER ASC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER DESC";
		str_date = "EK_DATE DESC";
		ico_writer = "▲";
	}else if( order.equals("EK_FWRITER DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_writer = "▼";
	}else if( order.equals("EK_DATE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE ASC";
		ico_date = "▼";
	}else if( order.equals("EK_DATE ASC") ) {
		str_word = "EK_VALUE ASC";
		str_weight = "EK_FWRITER DESC";		
		str_date = "EK_DATE DESC";
		ico_date = "▲";
	}
	
	ToKeywordMgr ekMgr = new ToKeywordMgr();
	ToKeywordBean[] arrEKbean = null;
	arrEKbean = ekMgr.getKeywordList(searchWord, order);	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="32%"><col width="3%"><col width="15%"><col width="15%"><col width="15%"><col width="15%">
	<tr>               
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th onclick="excuteOrder('<%=str_word%>');" style="cursor:pointer">키워드</th>
		<th></th>
		<th onclick="excuteOrder('<%=str_writer%>');" style="cursor:pointer">등록자</th>
		<th onclick="excuteOrder('<%=str_date%>');" style="cursor:pointer">등록일</th>
		<th>수정자</th>
		<th>수정일</th>
	</tr>
<%
	if(arrEKbean != null && arrEKbean.length > 0){
		String imgkey = "";
		for(int i = 0; i < arrEKbean.length; i++){
			
			
			if(arrEKbean[i].getEkUseyn().equals("Y")){
				imgkey = "on";
			}else{
				imgkey = "off";
			}
			
%> 
	<tr>         
		<td><input type="checkbox" name="checkId" value="<%=arrEKbean[i].getEkSeq()%>"></td>
		<td><p class="board_01_tit" onclick="popupEdit('<%=arrEKbean[i].getEkSeq()%>','update');" style="cursor:pointer"><%=arrEKbean[i].getEkValue()%></p></td>
		<td><img src="../../../images/admin/aekeyword/<%=imgkey%>_btn.gif" style="cursor: pointer;" onclick="showUse(this, '<%=imgkey%>', '<%=arrEKbean[i].getEkSeq()%>')"></td>
		<td><%=arrEKbean[i].getEkFwriter()%></td>
		<td><%=arrEKbean[i].getEkDate()%></td>
		<td><%=arrEKbean[i].getEkLwriter()%></td>
		<td><%=arrEKbean[i].getEkUpdate()%></td>
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