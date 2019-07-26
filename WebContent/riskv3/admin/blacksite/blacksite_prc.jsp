<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.blacksite.BlackSiteBean,
                 risk.admin.blacksite.BlackSiteMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	
	String mName = SS_M_NAME;
	String ekSeq = pr.getString("ekSeq");
	String ekValue = pr.getString("ekValue");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String targetSeq = pr.getString("targetSeq");
	
	BlackSiteMgr ekMgr = new BlackSiteMgr();
	if(mode.equals("insert")){
		if(ekMgr.insertKeyword(ekValue,mName))
		{
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.loadList();
			 	window.close();
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('중복된 URL 입니다.');
			 	window.close();
			 </script>
<%
		}
	}else if(mode.equals("update")){		
		
		if(ekMgr.updateKeyword(ekSeq,ekValue,mName))
		{
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.loadList();
			 	window.close();	
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('기존 정보와 같거나 중복된 URL 입니다.');
			 	window.close();
			 </script>
<%
		}
		
	}else if(mode.equals("delete")){
		if(ekMgr.delKeyword(checkIds))
		{
%>
			 <script type="text/javascript">
			 	alert('삭제하였습니다.');
			 	parent.loadList();
			 	window.close();	
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('1000개이상 삭제 할 수 없습니다.');
			 	window.close();			 	
			 </script>
<%
		}
	}
	
%>