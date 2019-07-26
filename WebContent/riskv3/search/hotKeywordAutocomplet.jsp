<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.hotkeyword.hotkeywordMgr" %>
<%@ page import="risk.admin.hotkeyword.hotkeywordBean" %>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();
	
	
	ArrayList dataList = new ArrayList();
	dataList = hm.getHotkeyword("", "Y");
	
	String outTag = "";
	if(dataList.size() > 0){
		for(int i = 0;i < dataList.size(); i++){
			hb = (hotkeywordBean)dataList.get(i);
			if(outTag.equals("")){
				outTag = hb.getH_seq()+","+hb.getH_name();
			}else{
				outTag += "@"+hb.getH_seq()+","+hb.getH_name();
			}
		}
		out.print(outTag);
	}else{
		out.print("");
	}
%>