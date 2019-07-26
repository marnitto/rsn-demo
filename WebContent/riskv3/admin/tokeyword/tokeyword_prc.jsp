<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.tokeyword.ToKeywordBean,
                 risk.admin.tokeyword.ToKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String mName = SS_M_NAME;
	String ekSeq = pr.getString("ekSeq");
	String ekValue = pr.getString("ekValue");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	ToKeywordMgr ekMgr = new ToKeywordMgr();
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
	}else if(mode.equals("usechk")){
		
		//String result = ekMgr.updateUseYn(ekSeq, pr.getString("useyn"));
		
		out.print(ekMgr.updateUseYn(ekSeq, pr.getString("useyn")));
	}
%>