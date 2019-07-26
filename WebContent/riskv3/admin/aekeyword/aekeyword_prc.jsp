<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.aekeyword.ExceptionKeywordBean,
                 risk.admin.aekeyword.ExceptionKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String mName = SS_M_NAME;
	String ekSeq = pr.getString("ekSeq");
	String ekValue = pr.getString("ekValue");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String targetSeq = pr.getString("targetSeq");
	
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
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
			 	alert('중복된 단어 입니다.');
			 	window.close();
			 </script>
<%
		}
	}else if(mode.equals("update")){		
		
	 	 System.out.println("1111111");
		
		if(ekMgr.updateKeyword(ekSeq,ekValue,mName))
		{
			System.out.println("222222");
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.loadList();
			 	window.close();	
			 </script>
<%
		}else{
			System.out.println("333333");
%>
			 <script type="text/javascript">
			 	alert('기존 정보와 같거나 중복된 단어 입니다.');
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
	}else if(mode.equals("keyword")){
		if(type2.equals("add"))
		{
			
			ekMgr.UpdateExkeyword(ekSeq, targetSeq, ExceptionKeywordBean.Mode.KEYWORD, ExceptionKeywordBean.Type2.ADD); 
%>
			 <script type="text/javascript">	
				parent.showDataList('keyword', "left");
			 	parent.showDataList('keyword', "right");
			 </script>
<%
		}else if(type2.equals("del")){
			ekMgr.UpdateExkeyword(ekSeq, targetSeq, ExceptionKeywordBean.Mode.KEYWORD, ExceptionKeywordBean.Type2.DEL);
%>
			 <script type="text/javascript">
			 	parent.showDataList('keyword', "left");
			 	parent.showDataList('keyword', "right");			 	
			 </script>
<%
		}
	}else if(mode.equals("site")){
		if(type2.equals("add")){
			ekMgr.UpdateExkeyword(ekSeq, targetSeq, ExceptionKeywordBean.Mode.SITE, ExceptionKeywordBean.Type2.ADD);
%>
			 <script type="text/javascript">
			 	parent.showDataList('site', "left");
			 	parent.showDataList('site', "right");	
			 </script>
<%
		}else if(type2.equals("del")){
			ekMgr.UpdateExkeyword(ekSeq, targetSeq, ExceptionKeywordBean.Mode.SITE, ExceptionKeywordBean.Type2.DEL);
%>
			 <script type="text/javascript">
			 	parent.showDataList('site', "left");
			 	parent.showDataList('site', "right");			 	
			 </script>
<%
		}
	}
%>