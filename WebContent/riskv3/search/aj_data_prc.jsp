<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest"%>
<%@page import="risk.search.MetaMgr"%>
<%
ParseRequest pr = new ParseRequest(request);

String t_seq = pr.getString("t_seq");
String issue_check = pr.getString("issue_check", "");

System.out.println(issue_check);

MetaMgr smgr = new MetaMgr();

String result = "";
result = smgr.setUpdateIssueCheck(t_seq , issue_check);

out.print(result);
%>