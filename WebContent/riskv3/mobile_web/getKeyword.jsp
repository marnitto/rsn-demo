<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.keyword.KeywordMng"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%@include file="../inc/mobile_sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);
	KeywordMng km = new KeywordMng();
	KeywordBean kb = new KeywordBean();
	
	String xp = pr.getString("xp");
	String yp = pr.getString("yp");
	
	ArrayList keyword = new ArrayList();
	keyword = km.getKeyword(xp, yp);
	System.out.println("keyword.size() : "+keyword.size());
%>
<select name="kzp" id="kzp" onchange="search()" style="width:100px" class="select_he">
	<option value="">선택하세요</option>
<%
if(keyword.size() > 0){
	for(int i = 0; i < keyword.size(); i++){
		kb = (KeywordBean)keyword.get(i);
		if(!kb.getK_zp().equals("0")){
%>
	<option value="<%=kb.getK_zp()%>"><%=kb.getK_value()%></option>
<%
		}
	}
}
%>
</select>