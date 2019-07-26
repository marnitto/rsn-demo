<%@page import="java.net.URLEncoder"%>
<%@page import="javax.sound.sampled.AudioFormat.Encoding"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@page import="risk.json.*"%>
<%@ page import="java.util.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.statistics.StatisticsMgr"%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");

StatisticsMgr stMgr = new StatisticsMgr();

String result = stMgr.ReplyDataStart(sDate, eDate);

out.println(result);
%>
