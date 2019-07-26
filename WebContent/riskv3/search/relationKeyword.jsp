<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);

	IssueMgr im = new IssueMgr();
	IssueDataBean idb = new IssueDataBean();

	String h_seq = pr.getString("h_seq", "");
	
	ArrayList relationKeyword = new ArrayList();
	if(!h_seq.equals("")){
		relationKeyword = im.getRelationKeyword(h_seq);
	}
	
	if(relationKeyword.size() > 0){
		for(int i = 0; i < relationKeyword.size(); i++){
			idb = (IssueDataBean)relationKeyword.get(i);
			if(i != 0){
				out.print("&nbsp;");
			}
			out.print("<span style=\"cursor:pointer;font-weight:bold\" onclick=\"addTagKeySel('"+idb.getItc_name()+"', '0');\">["+idb.getItc_name()+"]</span>");
		}
	}
%>