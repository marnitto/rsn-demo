<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.aekeyword.ExceptionKeywordBean,
                 risk.admin.aekeyword.ExceptionKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean eKeywordBean = null;
	pr.printParams();
		
	String mode = pr.getString("mode","");
	String type = pr.getString("type","");
	String ekSeq = pr.getString("ekSeq","");
	
	ArrayList ar_seq = null;
	
	if(mode.equals("keyword")){
		if(type.equals("left")){
			ar_seq =  ekMgr.getAllKeyword(ekSeq, ExceptionKeywordBean.Type.LEFT);		
		}else if(type.equals("right")){
			ar_seq =  ekMgr.getAllKeyword(ekSeq, ExceptionKeywordBean.Type.RIGHT);		
		}
	}else if (mode.equals("site")){
		if(type.equals("left")){
			ar_seq =  ekMgr.getAllSite(ekSeq, ExceptionKeywordBean.Type.LEFT);	
		}else if(type.equals("right")){
			ar_seq =  ekMgr.getAllSite(ekSeq, ExceptionKeywordBean.Type.RIGHT);		
		}
	}

%>

<%
if(mode.equals("keyword")){
	if(type.equals("left")){
%>

	<select name="k_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		eKeywordBean = (ExceptionKeywordBean)ar_seq.get(i);
		out.println("<option value='"+ eKeywordBean.getK_seq() +"'>"+ eKeywordBean.getK_value1() + " => "+ eKeywordBean.getK_value2() + " => "+ eKeywordBean.getK_value3() +"</option>");
	}
	%>
	</select>	
	
<%		
	}else if(type.equals("right")){
%>		
	
	<select name="k_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		eKeywordBean = (ExceptionKeywordBean)ar_seq.get(i);
		out.println("<option value='"+ eKeywordBean.getK_seq() +"'>"+ eKeywordBean.getK_value1() + " => "+ eKeywordBean.getK_value2() + " => "+ eKeywordBean.getK_value3() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}else if (mode.equals("site")){
	if(type.equals("left")){
%>	

	<select name="s_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		eKeywordBean = (ExceptionKeywordBean)ar_seq.get(i);
		out.println("<option value='"+ eKeywordBean.getS_seq() +"'>"+ eKeywordBean.getS_value1()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + eKeywordBean.getS_url() +"</option>");
	}
	%>
	</select>
		
<%		
	}else if(type.equals("right")){
%>		

	<select name="s_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		eKeywordBean = (ExceptionKeywordBean)ar_seq.get(i);
		out.println("<option value='"+ eKeywordBean.getS_seq() +"'>"+ eKeywordBean.getS_value1()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + eKeywordBean.getS_url() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}
%>


