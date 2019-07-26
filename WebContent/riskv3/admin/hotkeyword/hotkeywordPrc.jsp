<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.hotkeyword.hotkeywordMgr"%>
<%@ page import="risk.admin.hotkeyword.hotkeywordBean"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%	
	ParseRequest pr = new ParseRequest(request);
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();

	String h_seqs = pr.getString("h_seqs", "");
	String mode = pr.getString("mode", "");
	String h_name = pr.getString("h_name", "");
	String h_useyn = pr.getString("h_useyn", "");
	String m_seq = SS_M_NO;
	
	System.out.println("h_name : "+h_name);
	System.out.println("h_useyn : "+h_useyn);
	System.out.println("m_seq : "+m_seq);
	
	hb.setH_seq(h_seqs);
	hb.setH_name(h_name);
	hb.setH_useyn(h_useyn);
	hb.setM_seq(m_seq);
	
	if(mode.equals("insert")){
		if(hm.regkeyword(hb) > 0){
			out.print("<script>try{parent.opener.loadList();parent.window.close();}catch(e){parent.window.close();}</script>");
		}
	}else if(mode.equals("update")){
		if(hm.updatekeyword(hb) > 0){
			out.print("<script>parent.opener.loadList();parent.window.close();</script>");
		}
	}else if(mode.equals("delete")){
		if(hm.delkeyword(h_seqs) > 0){
			out.print("<script>parent.loadList()</script>");
		}
	}
%>
