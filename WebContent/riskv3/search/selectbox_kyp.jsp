<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest
				,risk.util.StringUtil
				,java.util.ArrayList
				,risk.search.MetaMgr
				,risk.admin.keyword.KeywordBean
				"%>
<%

	//이슈데이터 등록 관련
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	MetaMgr mgr = new MetaMgr();
	KeywordBean kBean = null;
	
	
	String md_seq = pr.getString("md_seq");
	String keyTypeXp = pr.getString("keyTypeXp");
	String subMode = pr.getString("subMode");
	
	ArrayList arrYp = mgr.getK_YPData(md_seq, keyTypeXp, subMode);
	
	String selectChk = "";
	
	if(arrYp.size() == 1){
		selectChk = "selected";
	}
	
%>           			
<select name="keyTypeYp" style="width:120px;">
	<option value="0">선택하세요</option>
<%							
			if(arrYp!=null && arrYp.size()>0){
				for (int j = 0; j < arrYp.size(); j++) {
					kBean = (KeywordBean)arrYp.get(j);
%>
	<option value="<%=kBean.getKGyp()%>" <%=selectChk%>><%=kBean.getKGvalue()%></option>
<%
				}
			}
%>
</select>

